package com.kim.sp.service;

import com.kim.sp.entity.SmsSendHistoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;
import com.kim.sp.dao.SmsSendHistoryDao;
import com.kim.sp.vo.SmsSendHistoryVo;
import com.kim.common.util.TokenUtil;

/**
 * 上下行短信发送记录服务实现类
 * @date 2019-3-7 15:41:35
 * @author liubo
 */
@Service
public class SmsSendHistoryService extends BaseService {
	
	@Autowired
	private SmsSendHistoryDao smsSendHistoryDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	public SmsSendHistoryEntity findById(String id) {
	
		return smsSendHistoryDao.find(new SmsSendHistoryVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	public SmsSendHistoryEntity find(SmsSendHistoryVo vo) {
	
		return smsSendHistoryDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	public PageRes<SmsSendHistoryEntity> listByPage(SmsSendHistoryVo vo) {
		
		return smsSendHistoryDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	public List<SmsSendHistoryEntity> list(SmsSendHistoryVo vo) {
		
		return smsSendHistoryDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public SmsSendHistoryEntity insert(SmsSendHistoryEntity entity) {
		
		int n = smsSendHistoryDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int update(SmsSendHistoryEntity entity) {

		return smsSendHistoryDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int delete(SmsSendHistoryVo vo) {

		return smsSendHistoryDao.deleteLogic(vo);
	}

}
