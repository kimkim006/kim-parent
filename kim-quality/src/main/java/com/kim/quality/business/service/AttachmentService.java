package com.kim.quality.business.service;

import com.kim.quality.business.dao.AttachmentDao;
import com.kim.quality.business.entity.AttachmentEntity;
import com.kim.quality.business.vo.AttachmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;
import com.kim.common.util.TokenUtil;
import com.kim.impexp.util.DownloadUtil;

/**
 * 附件信息表服务实现类
 * @date 2018-12-7 11:29:20
 * @author liubo
 */
@Service
public class AttachmentService extends BaseService {
	
	@Autowired
	private AttachmentDao attachmentDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-12-7 11:29:20
	 * @author liubo
	 */
	public AttachmentEntity findById(String id) {
	
		return attachmentDao.find(new AttachmentVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-12-7 11:29:20
	 * @author liubo
	 */
	public AttachmentEntity find(AttachmentVo vo) {
	
		return attachmentDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-12-7 11:29:20
	 * @author liubo
	 */
	public PageRes<AttachmentEntity> listByPage(AttachmentVo vo) {
		
		return attachmentDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-12-7 11:29:20
	 * @author liubo
	 */
	public List<AttachmentEntity> list(AttachmentVo vo) {
		
		List<AttachmentEntity> list = attachmentDao.list(vo);
		for (AttachmentEntity entity : list) {
			entity.setF(DownloadUtil.encode(entity.getPath()));
			entity.setS(DownloadUtil.sign(entity.getPath()));
		}
		return list;
	}

	/**
	 * 新增记录
	 * @date 2018-12-7 11:29:20
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public AttachmentEntity insert(AttachmentEntity entity) {
		
		int n = attachmentDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-12-7 11:29:20
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int update(AttachmentEntity entity) {

		return attachmentDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-12-7 11:29:20
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int delete(AttachmentVo vo) {

		return attachmentDao.deleteLogic(vo);
	}

}
