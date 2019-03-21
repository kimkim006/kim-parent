package com.kim.quality.business.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kim.quality.business.dao.RecycleDao;
import com.kim.quality.business.entity.MainTaskEntity;
import com.kim.quality.business.entity.RecycleDetailEntity;
import com.kim.quality.business.enums.MainTask;
import com.kim.quality.business.service.cache.MainTaskCacheService;
import com.kim.quality.business.vo.MainTaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.common.page.PageRes;
import com.kim.common.util.BatchUtil;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.quality.business.dao.RecycleDetailDao;
import com.kim.quality.business.entity.RecycleEntity;
import com.kim.quality.business.vo.RecycleVo;

/**
 * 任务回收记录表服务实现类
 * @date 2018-9-10 10:10:11
 * @author bo.liu01
 */
@Service
public class RecycleService extends BaseService {
	
	@Autowired
	private RecycleDao recycleDao;
	@Autowired
	private RecycleDetailDao recycleDetailDao;
	@Autowired
	private MainTaskService mainTaskService;
	@Autowired
	private SampleService sampleService;
	@Autowired
	private MainTaskCacheService mainTaskCacheService;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	public RecycleEntity findById(String id) {
	
		return recycleDao.find(new RecycleVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	public RecycleEntity find(RecycleVo vo) {
	
		return recycleDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	public PageRes<RecycleEntity> listByPage(RecycleVo vo) {
		if(StringUtil.isNotBlank(vo.getOperTimeStart())){
			vo.setOperTimeStart(vo.getOperTimeStart()+" 00:00:00");
		}
		if(StringUtil.isNotBlank(vo.getOperTimeEnd())){
			vo.setOperTimeEnd(vo.getOperTimeEnd()+" 23:59:59");
		}
		return recycleDao.listByPage(vo, vo.getPage());
	}
	
	public PageRes<Map<String, String>> listUserByBacth(RecycleVo vo) {
		if(vo.getBatchNo().contains(",")){
			vo.setBatchNos(Arrays.asList(vo.getBatchNo().split(",")));
		}
		return recycleDao.listUserByBacth(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	public List<RecycleEntity> list(RecycleVo vo) {
		
		return recycleDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public RecycleEntity insert(RecycleEntity entity) {
		
		int n = recycleDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(RecycleEntity entity) {

		return recycleDao.update(entity);
	}

	private List<MainTaskEntity> listTask(RecycleVo vo){
		MainTaskVo taskVo = new MainTaskVo().tenantId(vo.getTenantId());
		taskVo.setBatchNos(Arrays.asList(vo.getBatchNo().split(",")));
		taskVo.statusAdd(MainTask.STATUS_TO_EVALUATION);
		return mainTaskService.list(taskVo);
	}
	
	private RecycleEntity initRecycle(RecycleVo vo) {
		RecycleEntity entity = new RecycleEntity().tenantId(vo.getTenantId());
		entity.createRcyBatchNo();
		entity.setUserCount(vo.getUsers().size());
		entity.copyOperField(vo);
		return entity;
	}

	@Transactional(readOnly=false)
	public int exe(RecycleVo vo) {
		
		//查询任务
		List<MainTaskEntity> taskList = listTask(vo);
		if(CollectionUtil.isEmpty(taskList)){
			logger.error("该批次没有可回收的任务, batchNos:{}", vo.getBatchNo());
			throw new BusinessException("该批次没有可回收的任务, 请刷新列表重试!");
		}
		if(taskList.size() != vo.getAvailable()){
			logger.error("该批次任务有部分已回收或已质检, batchNos:{}, available:{}, 实际taskList:{}", 
					vo.getBatchNo(), vo.getAvailable(), taskList.size());
			throw new BusinessException("该批次任务有部分已回收或已质检, 请先刷新列表重试!");
		}
		
		//初始化回收批次记录
		RecycleEntity entity = initRecycle(vo);
		//回收
		return recycle(vo, taskList, entity);
	}

	private int recycle(RecycleVo vo, List<MainTaskEntity> taskList, RecycleEntity entity) {
		//转换并检查总任务数量
		Map<String, Integer> cfgMap = checkTotalNum(vo, taskList, entity);
		
		logger.info("开始回收任务，rcyBatchNo:{}", entity.getRcyBatchNo());
		Map<String, List<String>> userTaskGroup = new HashMap<>(cfgMap.size());
		
		//回收计算
		List<String> taskIdTmpList;
		List<RecycleDetailEntity> detailList = new ArrayList<>();
		Map<String, Integer> batchMap = new HashMap<>();
		Integer num;
		for (MainTaskEntity taskTmp : taskList) {
			num = cfgMap.get(taskTmp.getInspector());
			if(num == null){
				continue;
			}
			taskIdTmpList = userTaskGroup.get(taskTmp.getInspector());
			if(taskIdTmpList == null){
				taskIdTmpList = new ArrayList<>(num);
				userTaskGroup.put(taskTmp.getInspector(), taskIdTmpList);
			}else if(taskIdTmpList.size() >= num){
				continue;
			}
			
			taskIdTmpList.add(taskTmp.getId());
			detailList.add(createDetail(entity, taskTmp));
			
			Integer numTmp = batchMap.get(taskTmp.getBatchNo());
			batchMap.put(taskTmp.getBatchNo(), numTmp == null ? 1 : numTmp + 1);
		}
		
		int n = save(entity, userTaskGroup, detailList, batchMap, cfgMap);
		notifyUser(userTaskGroup);
		logger.info("回收任务结束，rcyBatchNo:{}", entity.getRcyBatchNo());
		return n;
	}
	
	private void notifyUser(Map<String, List<String>> userTaskGroup) {
		// TODO Auto-generated method stub
	}
	
	private RecycleDetailEntity createDetail(RecycleEntity entity, MainTaskEntity taskTmp) {
		RecycleDetailEntity detail = new RecycleDetailEntity().tenantId(entity.getTenantId());
		detail.setRcyBatchNo(entity.getRcyBatchNo());
		detail.setBatchNo(taskTmp.getBatchNo());
		detail.setMainTaskId(taskTmp.getId());
		detail.setInspector(taskTmp.getInspector());
		detail.setStatus(taskTmp.getStatus());
		detail.copyBaseField(entity);
		return detail;
	}

	private int save(RecycleEntity entity, Map<String, List<String>> userTaskGroup,
			List<RecycleDetailEntity> detailList, Map<String, Integer> batchMap, Map<String, Integer> cfgMap) {
		
		StringBuilder sb = new StringBuilder();
		int actualCount = 0;
		int actualTotal = 0;
		MainTaskVo taskVo = new MainTaskVo().tenantId(entity.getTenantId());
		taskVo.copyOperField(entity);
		int num = 0;
		List<String> ids = new ArrayList<>();
		for(Entry<String, List<String>> entry : userTaskGroup.entrySet()){
			taskVo.setIds(entry.getValue());
			ids.addAll(entry.getValue());
			actualCount = mainTaskService.recycle(taskVo);
			num = cfgMap.get(entry.getKey());
			if(actualCount != num){
				logger.error("回收任务时数据不对等, inspector:{}, 预计回收:{}, 实际回收:{}", entry.getKey(), num, actualCount);
				throw new BusinessException("【"+entry.getKey()+"】的任务状态已发生改变, 请刷新列表重试!");
			}
			actualTotal+=actualCount;
			sb.append(String.format(" %s-%s个,", entry.getKey(), actualCount));
		}
		if(sb.length() > 0){
			sb.deleteCharAt(sb.length()-1);
		}
		logger.info("回收批次号:{}, 回收详情:{}", entity.getRcyBatchNo(), sb);
		entity.setDetail("回收详情:"+sb.toString());
		recycleDao.insert(entity);
		BatchUtil.batchInsert(recycleDetailDao, detailList);
		
		sampleService.updateForRecycle(batchMap);
		
		mainTaskCacheService.clearCacheById(taskVo.getTenantId(), ids);
		return actualTotal;
	}

	private Map<String, Integer> checkTotalNum(RecycleVo vo, List<MainTaskEntity> taskList, RecycleEntity entity) {
		List<Object> users = vo.getUsers();
		Map<String, Integer> cfgMap = new HashMap<>(users.size());
		int totalNum = 0;
		for(Object object:users){
			totalNum +=convertCfg(cfgMap, object);
		}
		if(totalNum > taskList.size()){
			logger.error("回收任务量不能超过可回收任务量，taskList:{}, totalNum:{}", taskList.size(), totalNum);
			throw new BusinessException("回收任务量不能超过可回收任务量, 请刷新列表重试!");
		}
		entity.setTotalCount(totalNum);
		return cfgMap;
	}
	
	private int convertCfg(Map<String, Integer> cfg, Object object) {
		JSONObject userConfig = JSONObject.parseObject(JSONObject.toJSONString(object));
		String username = userConfig.getString("username");
		Integer num = userConfig.getInteger("num");
		if(StringUtil.isBlank(username) || num == null){
			logger.error("数据参数格式有误，存在空字符或空数字，cfg:{}", JSONObject.toJSONString(object));
			throw new BusinessException("数据参数格式有误，存在空字符或空数字");
		}
		cfg.put(username, num);
		return num;
	}

}
