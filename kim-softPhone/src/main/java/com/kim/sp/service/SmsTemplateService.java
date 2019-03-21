package com.kim.sp.service;

import com.kim.sp.dao.SmsTemplateDao;
import com.kim.sp.vo.SmsTemplateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;
import com.kim.sp.entity.SmsTemplateEntity;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.TokenUtil;

/**
 * 短信模板表服务实现类
 * @date 2019-3-11 14:31:51
 * @author liubo
 */
@Service
public class SmsTemplateService extends BaseService {
	
	@Autowired
	private SmsTemplateDao smsTemplateDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-11 14:31:51
	 * @author liubo
	 */
	public SmsTemplateEntity findById(String id) {
	
		return smsTemplateDao.find(new SmsTemplateVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-11 14:31:51
	 * @author liubo
	 */
	public SmsTemplateEntity find(SmsTemplateVo vo) {
	
		return smsTemplateDao.find(vo);
	}
	
	public SmsTemplateEntity getTemplate(String businessCode, String tenantId) {
		
		SmsTemplateVo vo = new SmsTemplateVo().tenantId(tenantId);
		vo.setBusinessCode(businessCode);
		List<SmsTemplateEntity> list = list(vo);
		if(CollectionUtil.isEmpty(list)){
			return null;
		}
		if(list.size() > 1){
			logger.warn("业务编号为{}的短信模板有{}条，默认将使用第一条,tenantId:{}", businessCode, list.size(), tenantId);
		}
		return list.get(0);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2019-3-11 14:31:51
	 * @author liubo
	 */
	public PageRes<SmsTemplateEntity> listByPage(SmsTemplateVo vo) {
		
		return smsTemplateDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2019-3-11 14:31:51
	 * @author liubo
	 */
	public List<SmsTemplateEntity> list(SmsTemplateVo vo) {
		
		return smsTemplateDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2019-3-11 14:31:51
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public SmsTemplateEntity insert(SmsTemplateEntity entity) {
		
		int n = smsTemplateDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2019-3-11 14:31:51
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int update(SmsTemplateEntity entity) {

		return smsTemplateDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2019-3-11 14:31:51
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int delete(SmsTemplateVo vo) {

		return smsTemplateDao.deleteLogic(vo);
	}

}
