package com.kim.sp.service;

import com.kim.sp.entity.RecordPlatformEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;
import com.kim.sp.dao.RecordPlatformDao;
import com.kim.sp.vo.RecordPlatformVo;
import com.kim.common.util.TokenUtil;

/**
 * 录音平台信息表服务实现类
 * @date 2019-3-7 15:41:35
 * @author liubo
 */
@Service
public class RecordPlatformService extends BaseService {
	
	@Autowired
	private RecordPlatformDao recordPlatformDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	public RecordPlatformEntity findById(String id) {
	
		return recordPlatformDao.find(new RecordPlatformVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	public RecordPlatformEntity find(RecordPlatformVo vo) {
	
		return recordPlatformDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	public PageRes<RecordPlatformEntity> listByPage(RecordPlatformVo vo) {
		
		return recordPlatformDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	public List<RecordPlatformEntity> list(RecordPlatformVo vo) {
		
		return recordPlatformDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public RecordPlatformEntity insert(RecordPlatformEntity entity) {
		
		int n = recordPlatformDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int update(RecordPlatformEntity entity) {

		return recordPlatformDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int delete(RecordPlatformVo vo) {

		return recordPlatformDao.deleteLogic(vo);
	}

}
