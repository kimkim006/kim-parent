package com.kim.quality.business.service;

import java.util.List;

import com.kim.quality.business.dao.AdjustScoreDao;
import com.kim.quality.business.dao.MainTaskDao;
import com.kim.quality.business.entity.MainTaskEntity;
import com.kim.quality.business.enums.MainTask;
import com.kim.quality.business.service.cache.AdjustScoreCacheService;
import com.kim.quality.business.service.cache.MainTaskCacheService;
import com.kim.quality.business.vo.AdjustScoreVo;
import com.kim.quality.business.vo.MainTaskVo;
import com.kim.quality.common.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.common.page.PageRes;
import com.kim.common.util.DateUtil;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.TokenUtil;
import com.kim.quality.business.entity.AdjustScoreEntity;

/**
 * 任务处理记录表服务实现类
 * @date 2018-12-12 14:44:32
 * @author liubo
 */
@Service
public class AdjustScoreService extends BaseService {
	
	@Autowired
	private AdjustScoreDao adjustScoreDao;
	@Autowired
	private AdjustScoreCacheService adjustScoreCacheService;
	@Autowired
	private MainTaskCacheService mainTaskCacheService;
	@Autowired
	private MainTaskDao mainTaskDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-12-12 14:44:32
	 * @author liubo
	 */
	public AdjustScoreEntity findById(String id) {
	
		return adjustScoreDao.find(new AdjustScoreVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-12-12 14:44:32
	 * @author liubo
	 */
	public AdjustScoreEntity find(AdjustScoreVo vo) {
	
		return adjustScoreCacheService.findWithCache(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-12-12 14:44:32
	 * @author liubo
	 */
	public PageRes<AdjustScoreEntity> listByPage(AdjustScoreVo vo) {
		
		return adjustScoreDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-12-12 14:44:32
	 * @author liubo
	 */
	public List<AdjustScoreEntity> list(AdjustScoreVo vo) {
		
		return adjustScoreDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-12-12 14:44:32
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public AdjustScoreEntity insert(AdjustScoreEntity entity) {
		
		MainTaskEntity mainTask = checkTask(entity);
		
		//保存前先将之前的改为非最后一次的记录
		AdjustScoreVo vo = new AdjustScoreVo().tenantId(entity.getTenantId());
		vo.setTaskId(entity.getTaskId());
		adjustScoreDao.clearPre(vo);
		
		entity.setAgentId(mainTask.getAgentId());
		entity.setInspector(mainTask.getInspector());
		entity.setSubmitter(entity.getOperUser());
		entity.setSubmitTime(DateUtil.getCurrentTime());
		entity.setIsLast(MainTask.ITEM_IS_LAST_Y);
		entity.setCalScore(NumberUtil.sum(mainTask.getScore(), entity.getAdScore()));
		entity.setScore(MainTaskUtil.getActualScore(entity.getCalScore(), entity.getTenantId()));
		adjustScoreDao.insert(entity);
		
		updateMainTask(entity, mainTask);
		//设置评分与审核redis缓存
		updateRedis(entity);
		return entity;
	}
	
	/**
	 * 刷新缓存数据
	 * @param evaluation
	 * @param approval
	 * @param appeal 
	 * @param mainTask
	 */
	private void updateRedis(AdjustScoreEntity adjustScore) {
		//插入调整分数数据,追加
		mainTaskCacheService.mainTaskDigestAdd(adjustScore.getTenantId(), adjustScore.getTaskId(), adjustScore.getKey());
		//刷新数据缓存
		adjustScoreCacheService.reloadAdjustScore(adjustScore.getTenantId(), adjustScore.getId());
		//刷新任务记录缓存
		mainTaskCacheService.reloadMainTask(adjustScore.getTenantId(), adjustScore.getTaskId());
	}
	
	private int updateMainTask(AdjustScoreEntity entity, MainTaskEntity mainTask) {
		
		MainTaskEntity taskEntity = new MainTaskEntity().tenantId(mainTask.getTenantId());
		taskEntity.setId(mainTask.getId());
		taskEntity.setOldStatus(mainTask.getStatus());
		taskEntity.setAdjustScoreCount(1);
		taskEntity.setScore(entity.getScore());
		taskEntity.copyBaseField(entity);
		return mainTaskDao.updateStatus(taskEntity);
	}

	private MainTaskEntity checkTask(AdjustScoreEntity entity) {
		MainTaskVo vo = new MainTaskVo().tenantId(entity.getTenantId());
		vo.setId(entity.getTaskId());
		MainTaskEntity task = mainTaskCacheService.findWithCache(vo);
		if(task == null){
			logger.error("该任务记录不存在，taskId:{}, tenantId:{}", vo.getId(), vo.getTenantId());
			throw new BusinessException("该任务记录不存在");
		}
		
		if(task.getStatus() == null){
			logger.error("该任务记录缺失状态值，taskId:{}, tenantId:{}", vo.getId(), vo.getTenantId());
			throw new BusinessException("该任务记录状态异常");
		}
		
		if(!NumberUtil.equals(task.getStatus(), MainTask.STATUS_RESULT_FEEDBACK)
				&& !NumberUtil.equals(task.getStatus(), MainTask.STATUS_APPEAL_PASS)
				&& !NumberUtil.equals(task.getStatus(), MainTask.STATUS_APPEAL_REJECTED)
				&& !NumberUtil.equals(task.getStatus(), MainTask.STATUS_RESULT_CONFIRM)){
			logger.error("该任务当前状态不允许调整分数，taskId:{}, status:{}, tenantId:{}", 
					vo.getId(), task.getStatus(), vo.getTenantId());
			throw new BusinessException("该任务当前状态不允许调整分数");
		}
		
		int maxCount = NumberUtil.toInt(BaseCacheUtil.getParam(CommonConstant.ADJUST_SCORE_LIMIT_MAX_COUNT_KEY, entity.getTenantId()),
				CommonConstant.ADJUST_SCORE_LIMIT_MAX_COUNT_DEFAULT);
		if(NumberUtil.compare(task.getAdjustScoreCount(), maxCount) > 0){
			logger.error("该任务记录调整评分的次数已超过最大限制，curCount:{}, maxCount:{}, taskId:{}, tenantId:{}", 
					task.getAdjustScoreCount(), maxCount, vo.getId(), vo.getTenantId());
			throw new BusinessException("调整评分的次数已超过最大限制("+maxCount+"次)");
		}
		
		return task;
	}

	/**
	 * 修改记录
	 * @date 2018-12-12 14:44:32
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int update(AdjustScoreEntity entity) {

		return adjustScoreDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-12-12 14:44:32
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int delete(AdjustScoreVo vo) {

		return adjustScoreDao.deleteLogic(vo);
	}

}
