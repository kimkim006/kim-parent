package com.kim.quality.setting.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kim.quality.setting.entity.RuleDirEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseService;
import com.kim.common.redis.RedisUtil;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.quality.common.CommonConstant;
import com.kim.quality.common.QualityRedisConstant;
import com.kim.quality.setting.dao.RuleDirDao;
import com.kim.quality.setting.dao.SampleRuleDao;
import com.kim.quality.setting.entity.RuleEntity;
import com.kim.quality.setting.entity.SampleRuleEntity;
import com.kim.quality.setting.vo.RuleDirVo;
import com.kim.quality.setting.vo.SampleRuleDetailVo;
import com.kim.quality.setting.vo.SampleRuleVo;

/**
 * 抽检规则表服务实现类
 * @date 2018-8-23 14:52:49
 * @author bo.liu01
 */
@Service
public class SampleRuleService extends BaseService {
	
	@Autowired
	private SampleRuleDao sampleRuleDao;
	@Autowired
	private SampleRuleDetailService sampleRuleDetailService;
	@Autowired
	private RuleDirDao ruleDirDao;
	
	private List<RuleDirEntity> getRuleDirs(){
		
		String key = QualityRedisConstant.getRuleDirKey(TokenUtil.getTenantId());
		String value = RedisUtil.opsForValue().get(key);
		if(StringUtil.isNotBlank(value)){
			return JSONObject.parseArray(value, RuleDirEntity.class);
		}
		
		List<RuleDirEntity> list = ruleDirDao.list(new RuleDirVo().tenantId(TokenUtil.getTenantId()));
		if(CollectionUtil.isEmpty(list)){
			return new ArrayList<>();
		}
		RedisUtil.opsForValue().set(key, JSONObject.toJSONString(list));
		return list;
	}
	
	private void clearCache(){
		RedisUtil.delete(QualityRedisConstant.getRuleDirKey(TokenUtil.getTenantId()));
	}
	
	private List<RuleDirEntity> getRuleDirs(RuleDirEntity top){
		
		List<RuleDirEntity> list = getRuleDirs();
		for (RuleDirEntity entity : list) {
			entity.setSampleType(top.getSampleType());
			if(StringUtil.isBlank(entity.getParentCode())){
				entity.setParentCode(top.getCode());
				entity.setType(RuleEntity.TYPE_MODE);
			}else{
				entity.setType(RuleEntity.TYPE_HOST_LINE);
			}
		}
		list.add(top);
		
		//处理父子关系
		Map<String, RuleDirEntity> map = CollectionUtil.getMapByProperty(list, "code");
		RuleDirEntity p;
		for (RuleDirEntity entity : list) {
			if(StringUtil.isBlank(entity.getParentCode())){
				continue;
			}
			p = map.get(entity.getParentCode());
			if(p != null){
				p.addChildren(entity);
			}
		}
		return list;
	}
	
	public List<RuleEntity> listManualRuleTree() {
		List<RuleEntity> list = new ArrayList<>();
		RuleDirEntity manualTop = RuleDirEntity.createManualTop();
		SampleRuleVo vo = new SampleRuleVo().tenantId(TokenUtil.getTenantId());
		vo.setActive(SampleRuleEntity.ACTIVE_YES);
		vo.setSampleType(RuleEntity.SAMPLE_TYPE_MANUAL);
		calc(list(vo), getRuleDirs(manualTop));
		list.add(manualTop);
		return list;
	}
	
	public List<RuleEntity> listTree(SampleRuleVo vo) {
		
		List<RuleEntity> list = new ArrayList<>();
		RuleDirEntity systemTop = RuleDirEntity.createSystemTop();
		RuleDirEntity manualTop = RuleDirEntity.createManualTop();
		vo.setSampleType(RuleEntity.SAMPLE_TYPE_SYSTEM);
		calc(list(vo), getRuleDirs(systemTop));
		vo.setSampleType(RuleEntity.SAMPLE_TYPE_MANUAL);
		calc(list(vo), getRuleDirs(manualTop));
		list.add(systemTop);
		list.add(manualTop);
		return list;
	}
	
	private void calc(List<? extends SampleRuleEntity> list, List<RuleDirEntity> dirList){
		if(CollectionUtil.isEmpty(list)){
			return ;
		}
		Map<String, RuleDirEntity> map = CollectionUtil.getMapByProperty(dirList, "code");
		RuleDirEntity p;
		for (SampleRuleEntity entity : list) {
			p = map.get(entity.getParentCode());
			if(p != null){
				p.addChildren(entity);
			}
		}
	}

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-23 14:52:49
	 * @author bo.liu01
	 */
	public SampleRuleEntity findById(String id) {
	
		return sampleRuleDao.find(new SampleRuleVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-23 14:52:49
	 * @author bo.liu01
	 */
	public SampleRuleEntity find(SampleRuleVo vo) {
		
		SampleRuleEntity entity = sampleRuleDao.find(vo);
		if(entity == null){
			return null;
		}
		SampleRuleDetailVo ruleDetailVo = new SampleRuleDetailVo().tenantId(vo.getTenantId());
		ruleDetailVo.setRuleId(vo.getId());
		entity.setRuleDetail(sampleRuleDetailService.list(ruleDetailVo ));
		
		if(RuleEntity.SAMPLE_TYPE_SYSTEM.equals(entity.getSampleType())){
			 SampleRuleEntity e = sampleRuleDao.findAtta(vo);
			 if(e!=null){
				 entity.setPersonAvg(e.getPersonAvg());
			 }
		}
		
		return entity;
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-8-23 14:52:49
	 * @author bo.liu01
	 */
	public List<SampleRuleEntity> list(SampleRuleVo vo) {
		
		return sampleRuleDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-8-23 14:52:49
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public SampleRuleEntity insert(SampleRuleEntity entity) {
		
		checkDir(entity);
		int n = sampleRuleDao.insert(entity);
		if(n > 0){
			if(RuleEntity.SAMPLE_TYPE_SYSTEM.equals(entity.getSampleType())){
				sampleRuleDao.insertAtta(entity);
				if(entity.getActive() != null && entity.getActive() == SampleRuleEntity.ACTIVE_YES){
					SampleRuleVo vo = new SampleRuleVo().id(entity.getId()).tenantId(entity.getTenantId());
					vo.setModeCode(entity.getModeCode());
					vo.setBusinessCode(entity.getBusinessCode());
					sampleRuleDao.clearSystemActiveRule(vo);
				}
			}
			sampleRuleDetailService.insert(entity);
		}
		return n > 0 ? entity : null;
	}

	private void checkDir(SampleRuleEntity entity) {
		String modeCode = entity.getModeCode();
		String businessCode = entity.getBusinessCode();
		
		RuleDirVo v = new RuleDirVo().tenantId(entity.getTenantId());
		v.setCode(modeCode);
		
		boolean needReloadCache = false;
		if(ruleDirDao.find(v ) == null){
			RuleDirEntity e = new RuleDirEntity().tenantId(entity.getTenantId());
			e.setOperName(entity.getOperName());
			e.setOperUser(entity.getOperUser());
			
			e.setCode(modeCode);
			e.setName(SampleRuleEntity.getModeName().get(modeCode));
			e.setType(RuleEntity.TYPE_MODE);
			ruleDirDao.insert(e);
			needReloadCache = true;
		}
		
		v.setCode(businessCode);
		if(ruleDirDao.find(v) == null){
			RuleDirEntity e = new RuleDirEntity().tenantId(entity.getTenantId());
			e.setOperName(entity.getOperName());
			e.setOperUser(entity.getOperUser());
			
			e.setParentCode(modeCode);
			e.setCode(businessCode);
			if(CommonConstant.BUSINESS_CALL_OUT.equals(businessCode)){
				e.setName(CommonConstant.BUSINESS_CALL_OUT_NAME);
			}else{
				e.setName(BaseCacheUtil.getDictValue(
						CommonConstant.DICT_BUSINESS_KEY, businessCode, entity.getTenantId()));
			}
			e.setType(RuleEntity.TYPE_HOST_LINE);
			ruleDirDao.insert(e);
			needReloadCache = true;
		}
		
		if(needReloadCache){
			clearCache();
		}
	}

	/**
	 * 修改记录
	 * @date 2018-8-23 14:52:49
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(SampleRuleEntity entity) {

		checkDir(entity);
		
		int n = sampleRuleDao.update(entity);
		if(n > 0){
			if(RuleEntity.SAMPLE_TYPE_SYSTEM.equals(entity.getSampleType())){
				sampleRuleDao.updateAtta(entity);
				if(entity.getActive() != null && entity.getActive() == SampleRuleEntity.ACTIVE_YES){
					SampleRuleVo vo = new SampleRuleVo().id(entity.getId()).tenantId(entity.getTenantId());
					vo.setModeCode(entity.getModeCode());
					vo.setBusinessCode(entity.getBusinessCode());
					sampleRuleDao.clearSystemActiveRule(vo);
				}
			}
			sampleRuleDetailService.update(entity);
		}
		return n;
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-8-23 14:52:49
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(SampleRuleVo vo) {
		
		int n = sampleRuleDao.deleteLogic(vo);
		if(n > 0){
			sampleRuleDao.deleteLogicAtta(vo);
			sampleRuleDetailService.deleteByRuleId(vo.getId());
		}

		return n;
	}

}
