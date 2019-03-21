package com.kim.quality.business.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kim.quality.business.entity.AllocateDetailEntity;
import com.kim.quality.business.entity.MainTaskEntity;
import com.kim.quality.business.service.cache.MainTaskCacheService;
import com.kim.quality.business.vo.AllocateVo;
import com.kim.quality.business.vo.MainTaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.kim.admin.entity.GroupUserEntity;
import com.kim.base.service.BaseUserGroupService;
import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.common.page.PageRes;
import com.kim.common.util.BatchUtil;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.StringUtil;
import com.kim.quality.business.dao.AllocateDao;
import com.kim.quality.business.dao.AllocateDetailDao;
import com.kim.quality.business.entity.AllocateEntity;
import com.kim.quality.business.enums.MainTask;

/**
 * 质检任务分配记录表服务实现类
 * @date 2018-9-10 10:10:10
 * @author bo.liu01
 */
@Service
public class AllocateService extends BaseService {
	
	@Autowired
	private AllocateDao allocateDao;
	@Autowired
	private AllocateDetailDao allocateDetailDao;
	@Autowired
	private MainTaskService mainTaskService;
	@Autowired
	private BaseUserGroupService baseUserGroupService;
	@Autowired
	private SampleService sampleService;
	@Autowired
	private MainTaskCacheService mainTaskCacheService;

	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-9-10 10:10:10
	 * @author bo.liu01
	 */
	public AllocateEntity find(AllocateVo vo) {
	
		return allocateDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-9-10 10:10:10
	 * @author bo.liu01
	 */
	public PageRes<AllocateEntity> listByPage(AllocateVo vo) {
		if(StringUtil.isNotBlank(vo.getOperTimeStart())){
			vo.setOperTimeStart(vo.getOperTimeStart()+" 00:00:00");
		}
		if(StringUtil.isNotBlank(vo.getOperTimeEnd())){
			vo.setOperTimeEnd(vo.getOperTimeEnd()+" 23:59:59");
		}
		return allocateDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-9-10 10:10:10
	 * @author bo.liu01
	 */
	public List<AllocateEntity> list(AllocateVo vo) {
		
		return allocateDao.list(vo);
	}
	
	private List<MainTaskEntity> listTask(AllocateVo vo){
		MainTaskVo taskVo = new MainTaskVo().tenantId(vo.getTenantId());
		taskVo.setBatchNos(Arrays.asList(vo.getBatchNo().split(",")));
		taskVo.statusAdd(MainTask.STATUS_TO_ALLOCATE, MainTask.STATUS_RECYCLED);
		return mainTaskService.list(taskVo);
		
	}
	
	private AllocateEntity initAllocate(AllocateVo vo) {
		AllocateEntity entity = new AllocateEntity().tenantId(vo.getTenantId());
		entity.createActBatchNo();
		entity.setUserCount(vo.getUsers().size());
		entity.copyOperField(vo);
		return entity;
	}

	/**
	 * 新增记录
	 * @date 2018-9-10 10:10:10
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int exe(AllocateVo vo) {
		//查询任务
		List<MainTaskEntity> taskList = listTask(vo);
		if(CollectionUtil.isEmpty(taskList)){
			logger.error("该批次任务已分配完毕, batchNos:{}", vo.getBatchNo());
			throw new BusinessException("该批次任务已分配完毕, 请刷新列表重试!");
		}
		if(taskList.size() != vo.getTaskNum()){
			logger.error("该批次任务有部分已分配, batchNos:{}", vo.getBatchNo());
			throw new BusinessException("该批次任务有部分已分配, 请先刷新列表重试!");
		}
		
		//初始化分配批次记录
		AllocateEntity entity = initAllocate(vo);
		//分配
		return allocate(vo, taskList, entity);
	}

	private int allocate(AllocateVo vo, List<MainTaskEntity> taskList, AllocateEntity entity){
		//转换并检查总任务数量
		Map<String, Integer> cfgMap = checkTotalNum(vo, taskList, entity);
		
		logger.info("开始分配任务，actBatchNo:{}", entity.getActBatchNo());
		Iterator<Entry<String, Integer>> it = cfgMap.entrySet().iterator();
		Entry<String, Integer> cfgEntry = it.next();
		Map<String, List<String>> userTaskGroup = new HashMap<>(cfgMap.size());
		List<String> taskIdTmpList = new ArrayList<>();
		userTaskGroup.put(cfgEntry.getKey(), taskIdTmpList);
		
		//分配计算
		List<AllocateDetailEntity> detailList = new ArrayList<>();
		Map<String, Integer> batchMap = new HashMap<>();
		int i = 0;
		for (MainTaskEntity taskTmp : taskList) {
			if(i >= cfgEntry.getValue()){
				if(!it.hasNext()){
					break;
				}
				i = 0;
				cfgEntry = it.next();
				userTaskGroup.put(cfgEntry.getKey(), taskIdTmpList = new ArrayList<>());
			}
			
			taskIdTmpList.add(taskTmp.getId());
			detailList.add(createDetail(entity, cfgEntry, taskTmp));
			
			Integer numTmp = batchMap.get(taskTmp.getBatchNo());
			batchMap.put(taskTmp.getBatchNo(), numTmp == null ? 1 : numTmp + 1);
			i++;
		}
		
		int n = save(entity, userTaskGroup, detailList, batchMap, cfgMap);
		notifyUser(userTaskGroup);
		logger.info("分配任务结束，actBatchNo:{}", entity.getActBatchNo());
		return n;
	}

	
	/**
	 * 发送通知消息
	 */
	private void notifyUser(Map<String, List<String>> userTaskGroup) {
		//TODO
	}

	/**
	 * 保存数据
	 * @param entity
	 * @param userTaskGroup
	 * @param detailList
	 * @param batchMap
	 * @return
	 */
	private int save(AllocateEntity entity, Map<String, List<String>> userTaskGroup,
			List<AllocateDetailEntity> detailList, Map<String, Integer> batchMap, Map<String, Integer> cfgMap) {
		
		StringBuilder sb = new StringBuilder();
		int actualCount = 0;
		int actualTotal = 0;
		int num = 0;
		MainTaskVo taskVo = new MainTaskVo().tenantId(entity.getTenantId());
		taskVo.copyOperField(entity);
		List<String> ids = new ArrayList<>();
		for(Entry<String, List<String>> entry : userTaskGroup.entrySet()){
			GroupUserEntity group = baseUserGroupService.getGroup(entry.getKey(), entity.getTenantId());
			if(group != null){
				taskVo.setQualityGroupCode(group.getGroupCode());
			}
			taskVo.setInspector(entry.getKey());
			taskVo.setIds(entry.getValue());
			ids.addAll(entry.getValue());
			actualCount = mainTaskService.allocate(taskVo);
			num = cfgMap.get(entry.getKey());
			if(actualCount != num){
				logger.error("回收任务时数据不对等, inspector:{}, 预计回收:{}, 实际回收:{}", entry.getKey(), num, actualCount);
				throw new BusinessException("【"+entry.getKey()+"】的任务状态已发生改变, 请刷新列表重试!");
			}
			actualTotal += actualCount;
			sb.append(String.format(" %s-%s个,", entry.getKey(), actualCount));
		}
		if(sb.length() > 0){
			sb.deleteCharAt(sb.length()-1);
		}
		logger.info("分配批次号:{}, 分配详情:{}", entity.getActBatchNo(), sb);
		entity.setDetail("分配详情:"+sb.toString());
		allocateDao.insert(entity);
		BatchUtil.batchInsert(allocateDetailDao, detailList);
		
		sampleService.updateForAllocate(batchMap);
		
		mainTaskCacheService.clearCacheById(taskVo.getTenantId(), ids);
		return actualTotal;
	}
	
	private Map<String, Integer> checkTotalNum(AllocateVo vo, List<MainTaskEntity> taskList, AllocateEntity entity) {
		List<Object> users = vo.getUsers();
		Map<String, Integer> cfgMap = new HashMap<>(users.size());
		int totalNum = 0;
		for(Object object:users){
			totalNum +=convertCfg(cfgMap, object);
		}
		if(taskList.size() < totalNum){
			logger.error("分配任务量不能超过可分配任务量，taskList:{}, totalNum:{}", taskList.size(), totalNum);
			throw new BusinessException("分配任务量不能超过可分配任务量, 请刷新列表重试!");
		}
		entity.setTotalCount(totalNum);
		return cfgMap;
	}

	private AllocateDetailEntity createDetail( AllocateEntity entity, Entry<String, Integer> entry, MainTaskEntity taskTmp) {
		AllocateDetailEntity detail = new AllocateDetailEntity().tenantId(entity.getTenantId());
		detail.setActBatchNo(entity.getActBatchNo());
		detail.setBatchNo(taskTmp.getBatchNo());
		detail.setMainTaskId(taskTmp.getId());
		detail.setInspector(entry.getKey());
		detail.copyBaseField(entity);
		return detail;
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
