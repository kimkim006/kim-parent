package com.kim.quality.setting.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kim.quality.setting.dao.EvaluationSettingDao;
import com.kim.quality.setting.vo.EvaluationSettingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.common.base.BaseService;
import com.kim.common.page.PageRes;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.quality.setting.entity.EvaluationSettingEntity;

/**
 * 质检评分选项表服务实现类
 * @date 2018-9-10 14:40:52
 * @author jianming.chen
 */
@Service
public class EvaluationSettingService extends BaseService {
	
	@Autowired
	private EvaluationSettingDao evaluationSettingDao;

	public List<EvaluationSettingEntity> listTree(EvaluationSettingVo vo) {
		
		List<EvaluationSettingEntity> list = list(vo);
		if(CollectionUtil.isEmpty(list)) {
			return new ArrayList<>();
		}
		
		return calcTree(list);
	}

	private List<EvaluationSettingEntity> calcTree(List<EvaluationSettingEntity> list) {
		List<EvaluationSettingEntity> resultList = new ArrayList<>();
		Map<String, EvaluationSettingEntity> evalSettingMap = CollectionUtil.getMap(list, EvaluationSettingEntity::getId);
		EvaluationSettingEntity pDir;
		for (EvaluationSettingEntity entity : list) {
			if(StringUtil.isBlank(entity.getParentId())){
				resultList.add(entity);
				continue;
			}
			pDir = evalSettingMap.get(entity.getParentId());
			if(pDir != null) {
				pDir.addChildren(entity);
			}
		}
		return resultList;
	}
	
	private String calc(EvaluationSettingEntity st, Map<String, EvaluationSettingEntity> settingMap) {
		if(st == null){
			return null;
		}
		if(StringUtil.isNotBlank(st.getEvalItemName())){
			return st.getEvalItemName();
		}
		if(StringUtil.isBlank(st.getParentId())){
			st.setEvalItemName(st.getName());
		}else{
			String pEvalItemName = calc(settingMap.get(st.getParentId()), settingMap);
			if(StringUtil.isBlank(pEvalItemName)){
				st.setEvalItemName(st.getName());
			}else{
				st.setEvalItemName(pEvalItemName + " / " + st.getName());
			}
		}
		return st.getEvalItemName();
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-9-10 14:40:52
	 * @author jianming.chen
	 */
	public List<EvaluationSettingEntity> list(EvaluationSettingVo vo) {
		
		List<EvaluationSettingEntity> list = evaluationSettingDao.list(vo);
		if(CollectionUtil.isNotEmpty(list)){
			Map<String, EvaluationSettingEntity> settingMap = CollectionUtil.getMap(list, EvaluationSettingEntity::getId);
			for (EvaluationSettingEntity entity : list) {
				calc(entity, settingMap);
			}
		}
		return list;
	}

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-9-10 14:40:52
	 * @author jianming.chen
	 */
	public EvaluationSettingEntity findById(String id) {
	
		return evaluationSettingDao.find(new EvaluationSettingVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-9-10 14:40:52
	 * @author jianming.chen
	 */
	public EvaluationSettingEntity find(EvaluationSettingVo vo) {
	
		return evaluationSettingDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-9-10 14:40:52
	 * @author jianming.chen
	 */
	public PageRes<EvaluationSettingEntity> listByPage(EvaluationSettingVo vo) {
		
		return evaluationSettingDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 新增记录
	 * @date 2018-9-10 14:40:52
	 * @author jianming.chen
	 */
	@Transactional(readOnly=false)
	public EvaluationSettingEntity insert(EvaluationSettingEntity entity) {
		
		if(NumberUtil.equals(entity.getType(), EvaluationSettingEntity.TYPE_EVASET_DIR)) {
			entity.setScore(null);
			entity.setEvalType(null);
		}
		int n = evaluationSettingDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-9-10 14:40:52
	 * @author jianming.chen
	 */
	@Transactional(readOnly=false)
	public int update(EvaluationSettingEntity entity) {

		return evaluationSettingDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-9-10 14:40:52
	 * @author jianming.chen
	 */
	@Transactional(readOnly=false)
	public int delete(EvaluationSettingVo vo) {

		return evaluationSettingDao.deleteLogic(vo);
	}

	public List<EvaluationSettingEntity> listForCom(EvaluationSettingVo vo) {
		
		EvaluationSettingVo evo = new EvaluationSettingVo().tenantId(vo.getTenantId());
		evo.setType(EvaluationSettingEntity.TYPE_EVASET_DIR);
		List<EvaluationSettingEntity> list = evaluationSettingDao.list(evo);
		
		if(StringUtil.isBlank(vo.getId())){
			return list;
		} else {
			//过滤所有的子节点，子节点的子节点，防止出现树关联死循环
			Set<String> childrenIds = getChildrenIdSet(new HashSet<>(), list, vo.getId());
			childrenIds.add(vo.getId());
			Iterator<EvaluationSettingEntity> it = list.iterator();
			while(it.hasNext()){
				if(childrenIds.contains(it.next().getId())){
					it.remove();
				}
			}
			return list;
		}
	}

	/**
	 * 获取某个父节点下面的所有子节点
	 * @param list
	 * @param pid
	 * @return
	 */
	private Set<String> getChildrenIdSet(Set<String> childIdSet, List<EvaluationSettingEntity> list, String pid){
		for(EvaluationSettingEntity es : list){
			//遍历出父id等于参数的id，add进子节点集合
			if(StringUtil.isNotBlank(es.getParentId()) && es.getParentId().equals(pid)) {
				//递归遍历下一级
				getChildrenIdSet(childIdSet, list, es.getId());
				childIdSet.add(es.getId());
			}
		}
		return childIdSet;
	}

}
