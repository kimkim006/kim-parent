package com.kim.quality.business.service.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.kim.quality.business.entity.AttachmentEntity;
import com.kim.quality.business.service.AttachmentService;
import com.kim.quality.business.vo.AppealVo;
import com.kim.quality.business.vo.AttachmentVo;
import com.kim.quality.common.QualityRedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.kim.common.base.BaseService;
import com.kim.common.redis.RedisUtil;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.StringUtil;
import com.kim.quality.business.dao.AppealDao;
import com.kim.quality.business.entity.AppealEntity;

@Service
public class AppealCacheService extends BaseService{
	
	@Autowired
	private AppealDao appealDao;
	@Autowired
	private CacheKeyService cacheKeyService;
	@Autowired
	private AttachmentService attachmentService;
	
	public AppealEntity findWithCache(String tenantId, String id) {
		
		return findWithCache(new AppealVo().tenantId(tenantId).id(id));
	}
	
	public AppealEntity findWithCache(AppealVo vo) {
		
		String key = QualityRedisConstant.getAppealKey(vo.getTenantId(), vo.getId());
		String value = RedisUtil.opsForValue().get(key);
		if(StringUtil.isNotBlank(value)){
			return JSONObject.parseObject(value, AppealEntity.class);
		}
		return loadAppeal(vo, key);
	}

	private AppealEntity loadAppeal(AppealVo vo, String key) {
		AppealEntity entity = appealDao.find(vo);
		if(entity == null){
			logger.error("该任务录不存在, tenantId:{}, id:{}", vo.getTenantId(), vo.getId());
			return null;
		}
		
		//查询附件信息
		AttachmentVo attaVo = new AttachmentVo().tenantId(vo.getTenantId());
		attaVo.setTaskId(vo.getTaskId());
		attaVo.setItemId(vo.getId());
		attaVo.setItemType(AttachmentEntity.ITEM_TYPE_APPEAL);
		List<AttachmentEntity> attaList = attachmentService.list(attaVo);
		if(CollectionUtil.isNotEmpty(attaList)){
			entity.setAttaList(attaList);
		}
		
		RedisUtil.opsForValue().set(key, JSONObject.toJSONString(entity), 
				cacheKeyService.getTaskExpire(entity.getTenantId()), TimeUnit.SECONDS);
		return entity;
	}
	
	public void reloadAppeal(String tenantId, String id){
		String key = QualityRedisConstant.getAppealKey(tenantId, id);
		RedisUtil.delete(key);
		loadAppeal(new AppealVo().tenantId(tenantId).id(id) , key);
	}

}
