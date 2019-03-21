package com.kim.quality.business.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.kim.quality.business.dao.MainTaskDao;
import com.kim.quality.business.entity.EvaluationDetailEntity;
import com.kim.quality.business.entity.EvaluationEntity;
import com.kim.quality.business.entity.MainTaskDigestInfo;
import com.kim.quality.business.entity.MainTaskEntity;
import com.kim.quality.business.enums.MainTask;
import com.kim.quality.business.service.cache.EvaluationCacheService;
import com.kim.quality.business.service.cache.MainTaskCacheService;
import com.kim.quality.business.service.cache.SampleTapeCacheService;
import com.kim.quality.business.vo.MainTaskVo;
import com.kim.quality.setting.entity.EvaluationSettingEntity;
import com.kim.quality.setting.service.EvaluationSettingService;
import com.kim.quality.setting.vo.EvaluationSettingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.common.page.PageRes;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.impexp.excel.exp.handler.AbstractObjectContentWriterHandler;
import com.kim.impexp.util.DownloadUtil;

/**
 * 质检任务表服务实现类
 * @date 2018-8-28 18:24:20
 * @author bo.liu01
 */
@Service
public class MainTaskService extends BaseService {

	@Autowired
	private MainTaskDao mainTaskDao;
	@Autowired
	private MainTaskCacheService mainTaskCacheService;
	@Autowired
	private EvaluationCacheService evaluationCacheService;
	@Autowired
	private SampleTapeCacheService sampleTapeCacheService;
	@Autowired
	private EvaluationSettingService evaluationSettingService;
	
	/**
	 * 单条记录查询
	 *
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */

	public MainTaskEntity findById(String id) {
		
		return mainTaskDao.find(new MainTaskVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 *
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	public MainTaskEntity find(MainTaskVo vo) {

		return mainTaskDao.find(vo);
	}
	
	public MainTaskEntity findTask(String tenantId, String taskId) {
		
		MainTaskEntity entity = mainTaskCacheService.findWithCache(tenantId, taskId);
		if (entity == null) {
			throw new BusinessException("该任务不存在");
		}
		entity.setTape(sampleTapeCacheService.findWithCache(tenantId, taskId));
		return entity;
	}

	public MainTaskDigestInfo digestInfo(String taskId) {
		
		String tenantId = TokenUtil.getTenantId();
		List<String> keyList = new ArrayList<>();
		//获取任务信息key
		MainTaskEntity task = findTask(tenantId, taskId);
//		keyList.add(task.getKey())

		MainTaskDigestInfo digestInfo = new MainTaskDigestInfo(taskId, keyList);
		digestInfo.setTask(task);

		keyList.addAll(mainTaskCacheService.getTaskRelateKey(tenantId, taskId));
		return digestInfo;
	}
	
	/**
	 * 带分页的查询
	 *
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	public PageRes<MainTaskEntity> listByPage(MainTaskVo vo) {

		PageRes<MainTaskEntity> pageRes = mainTaskDao.listByPage(vo, vo.getPage());
		setSoreDetail(pageRes.getList());
		return MainTask.convertStatus(pageRes);
	}
	
	/**
	 * 带分页的查询
	 *
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	public PageRes<MainTaskEntity> listSampleDetail(MainTaskVo vo) {
		
		return MainTask.convertStatus(mainTaskDao.listSampleDetail(vo, vo.getPage()));
	}

	/**
	 * 分页查询质检结果列表
	 * @param vo
	 * @return
	 */
	public PageRes<MainTaskEntity> listResult(MainTaskVo vo) {
		
		return MainTask.convertStatus(mainTaskDao.listResult(vo, vo.getPage()));
	}
	
	/**
	 * 分页查询质检审核任务列表
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 */
	public PageRes<MainTaskEntity> listApproval(MainTaskVo vo) {

		return MainTask.convertStatus(mainTaskDao.listApproval(vo, vo.getPage()));
	}
	
	/**
	 * 待评分页面
	 *
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 */
	public PageRes<MainTaskEntity> listEvaluation(MainTaskVo vo) {

		return MainTask.convertStatus(mainTaskDao.listEvaluation(vo, vo.getPage()));
	}
	
	private void setSoreDetail(List<MainTaskEntity> list){
		
		if(CollectionUtil.isNotEmpty(list)){
			for (MainTaskEntity mainTask : list) {
				if(hasEval(mainTask)){
					setScoreDetail(mainTask);
				}
			}
		}
	}
	
	private void setScoreDetail(MainTaskEntity mainTask){
		
		EvaluationEntity eval = getTaskEval(mainTask);
		if(eval == null || NumberUtil.equals(eval.getStatus(), EvaluationEntity.STATUS_REJECTED)){
			return ;
		}
		if(NumberUtil.equals(eval.getDamaged(), EvaluationEntity.DAMAGED_YES)){
			mainTask.setDamagedName(EvaluationEntity.DAMAGED_YES_NAME);
			mainTask.setScoreDetail("录音已损坏");
			return ;
		}
		mainTask.setDamagedName(EvaluationEntity.DAMAGED_NO_NAME);
		if(CollectionUtil.isEmpty(eval.getEvaluationDetailList())){
			return ;
		}
		String format = "%s(%s)";
		StringBuilder scoreDetail = new StringBuilder();
		for (EvaluationDetailEntity detail : eval.getEvaluationDetailList()) {
			scoreDetail.append(String.format(format, detail.getEvalItemName(), detail.getScoreStr()));
			if(StringUtil.isNotBlank(detail.getRemark())){
				scoreDetail.append(" - ").append(detail.getRemark());
			}
			scoreDetail.append(", \n");
		}
		if(scoreDetail.length() > 0){
			scoreDetail.deleteCharAt(scoreDetail.length()-1);
			scoreDetail.deleteCharAt(scoreDetail.length()-1);
			scoreDetail.deleteCharAt(scoreDetail.length()-1);
		}
		mainTask.setScoreDetail(scoreDetail.toString());
	}

	/**
	 * 是否已经质检过
	 * @param mainTask
	 * @return
	 */
	private boolean hasEval(MainTaskEntity mainTask) {
		return mainTask.getStatus() != null && mainTask.getStatus() > MainTask.STATUS_TO_EVALUATION;
	}

	private EvaluationEntity getTaskEval(MainTaskEntity mainTask) {
		
		List<String> relateKeys = mainTaskCacheService.getTaskRelateKey(mainTask.getTenantId(), mainTask.getId());
		if(CollectionUtil.isEmpty(relateKeys)){
			return null;
		}
		for(int i=relateKeys.size()-1;i>-1;i--){
			String keyType = relateKeys.get(i);
			if(StringUtil.isNotEmpty(keyType ) && keyType.startsWith(MainTask.KEY_TYPE_EVALUATION)){
				String id = keyType.replace(MainTask.KEY_TYPE_EVALUATION, "").replace("-", "");
				return evaluationCacheService.findWithCache(mainTask.getTenantId(), id);
			}
		}
		return null;
	}
	
	/**
	 * 不带带分页的查询
	 *
	 * @param vo vo查询对象
	 * @return
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	public List<MainTaskEntity> list(MainTaskVo vo) {

		List<MainTaskEntity> list = mainTaskDao.list(vo);
		return MainTask.convertStatus(list);
	}

	/**
	 * 新增记录
	 *
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly = false)
	public MainTaskEntity insert(MainTaskEntity entity) {

		int n = mainTaskDao.insert(entity);
		return n > 0 ? entity : null;
	}
	
	public int update(MainTaskEntity task) {
		
		return mainTaskDao.update(task);
	}
	
	public int updateStatus(MainTaskEntity task) {
		
		return mainTaskDao.updateStatus(task);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(MainTaskVo vo) {

		return mainTaskDao.deleteLogic(vo);
	}
	
	@Transactional(readOnly=false)
	public int allocate(MainTaskVo vo){
		
		if(CollectionUtil.isEmpty(vo.getIds())){
			return 0;
		}
		List<List<String>> list = CollectionUtil.list2BatchList(vo.getIds());
		int n = 0;
		for (List<String> tmpList : list) {
			vo.setIds(tmpList);
			n += mainTaskDao.allocate(vo);
		}
		return n;
	}

	@Transactional(readOnly=false)
	public int recycle(MainTaskVo vo) {
		if(CollectionUtil.isEmpty(vo.getIds())){
			return 0;
		}
		List<List<String>> list = CollectionUtil.list2BatchList(vo.getIds());
		int n = 0;
		for (List<String> tmpList : list) {
			vo.setIds(tmpList);
			n += mainTaskDao.recycle(vo);
		}
		return n;
	}

	private String[] calcSoreDetailColumnData(List<MainTaskEntity> list, MainTaskVo vo){
		
		if(CollectionUtil.isEmpty(list)){
			return new String[0];
		}
		
		EvaluationSettingVo evalVo = new EvaluationSettingVo().tenantId(vo.getTenantId());
		evalVo.setOrderBy("parent_id");
		List<EvaluationSettingEntity> esList = evaluationSettingService.list(evalVo);
		int length = esList.size();
		//Map<String, EvaluationSettingEntity> evalSettingMap = new LinkedHashMap<>(length)
		Map<String, Integer> esHeaderMap = new LinkedHashMap<>(length);
		String[] userHeader = new String[length];
		int i=0;
		for (EvaluationSettingEntity es : esList) {
			//evalSettingMap.put(es.getId(), es)
			esHeaderMap.put(es.getId(), i);
			userHeader[i++] = es.getName();
		}
		
		for (MainTaskEntity mainTask : list) {
			mainTask.setEvalDetailArr(getEvalDetailArr(mainTask, length, esHeaderMap));
		}
		return userHeader;
	}

	private Object[] getEvalDetailArr(MainTaskEntity mainTask, int length, Map<String, Integer> esHeaderMap) {
		Object[] detailArr = new Object[length];
		EvaluationEntity eval = getTaskEval(mainTask);
		if(eval == null){
			return detailArr;
		}
		
		if(NumberUtil.equals(eval.getDamaged(), EvaluationEntity.DAMAGED_YES)){
			mainTask.setDamagedName(EvaluationEntity.DAMAGED_YES_NAME);
			return detailArr;
		}
		
		mainTask.setDamagedName(EvaluationEntity.DAMAGED_NO_NAME);
		List<EvaluationDetailEntity> list = eval.getEvaluationDetailList();
		if(CollectionUtil.isEmpty(list)){
			return detailArr;
		}
			
		StringBuilder scoreDetail = new StringBuilder();
		Integer i;
		int n=1;
		for (EvaluationDetailEntity dt : list) {
			//收集评语
			if(StringUtil.isNotBlank(dt.getRemark())){
				scoreDetail.append(n++).append(". ").append(dt.getRemark());
				scoreDetail.append(", \n");
			}
			
			//收集评分项和评分值
			if(StringUtil.isBlank(dt.getEvalItemId()) ||
					(i = esHeaderMap.get(dt.getEvalItemId())) == null){
				logger.error("该评分详情没有对应的评分规则详情, evalItemId:{}", dt.getEvalItemId());
				continue;
			}
			//带有符号的分值
			detailArr[i] = dt.getScoreStr();
		}
		if(scoreDetail.length() > 0){
			scoreDetail.deleteCharAt(scoreDetail.length()-1);
			scoreDetail.deleteCharAt(scoreDetail.length()-1);
			scoreDetail.deleteCharAt(scoreDetail.length()-1);
		}
		mainTask.setScoreDetail(scoreDetail.toString());
		return detailArr;
	}

	public JSONObject downloadTask(MainTaskVo vo) {
		
		String[] header = new String[]{"抽检批次号", "业务线", "录音流水", "客户电话", "录音时长", "开始时间", "结束时间",
				"业务小组", "坐席", "坐席工号", "质检小组", "质检员", "质检员工号", "当前处理人", "状态", "得分", "操作时间", "录音损坏", "评语"};
		
		List<MainTaskEntity> list = list(vo);
		String[] esHeader = calcSoreDetailColumnData(list, vo);
		
		if(CollectionUtil.isNotEmpty(esHeader)){
			
			List<String> headerList = CollectionUtil.array2List(header);
			CollectionUtil.addAll(headerList, esHeader);
			header = headerList.toArray(new String[headerList.size()]);
		}
		
		return DownloadUtil.simpleDownload("质检任务结果数据导出", header, getTaskBodyWriterHandler(list));
	}
	
	private AbstractObjectContentWriterHandler<MainTaskEntity> getTaskBodyWriterHandler(List<MainTaskEntity> list) {
		return new AbstractObjectContentWriterHandler<MainTaskEntity>(list){
			@Override
			public Object[] java2ObjectArr(MainTaskEntity t) {
				Object[] data = new Object[]{
						t.getBatchNo(), t.getBusinessCode(), t.getTape().getSerialNumber(), 
						t.getTape().getCustPhone(), t.getTape().getRecordTime(), t.getTape().getStartTime(),
						t.getTape().getEndTime(), t.getBusiGroupName(),t.getAgentName(),
						t.getAgentId(), t.getQualityGroupName(), t.getInspectorName(), 
						t.getInspector(), t.getCurProcesserName(), t.getStatusName(), 
						t.getScore(), t.getOperTime(), t.getDamagedName(), t.getScoreDetail()
				};
				return CollectionUtil.addAll(data, t.getEvalDetailArr());
			}
		};
	}
	
	public JSONObject download(MainTaskVo vo) {
		
		List<MainTaskEntity> list = list(vo);
		setSoreDetail(list);
		
		String[] header = new String[]{"抽检批次号", "业务线", "录音流水", "客户电话", "录音时长", "开始时间", "结束时间",
				"业务小组", "坐席", "坐席工号", "质检小组", "质检员", "质检员工号", "当前处理人", "状态", "得分", "录音损坏", 
				"得分明细"};
		return DownloadUtil.simpleDownload("质检任务数据导出", header, getBodyWriterHandler(list));
	}
	
	private AbstractObjectContentWriterHandler<MainTaskEntity> getBodyWriterHandler(List<MainTaskEntity> list) {
		return new AbstractObjectContentWriterHandler<MainTaskEntity>(list){
			@Override
			public Object[] java2ObjectArr(MainTaskEntity t) {
				return new Object[]{t.getBatchNo(), t.getBusinessCode(), t.getTape().getSerialNumber(), 
						t.getTape().getCustPhone(), t.getTape().getRecordTime(), t.getTape().getStartTime(),
						t.getTape().getEndTime(), t.getBusiGroupName(),t.getAgentName(),
						t.getAgentId(), t.getQualityGroupName(), t.getInspectorName(), 
						t.getInspector(), t.getCurProcesserName(), t.getStatusName(), 
						t.getScore(), t.getDamagedName(), t.getScoreDetail()
				};
			}
		};
	}

}