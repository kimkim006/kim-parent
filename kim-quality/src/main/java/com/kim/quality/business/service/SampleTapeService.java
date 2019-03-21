package com.kim.quality.business.service;

import java.util.List;

import com.kim.quality.business.dao.SampleTapeDao;
import com.kim.quality.business.entity.SampleTapeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.common.base.BaseService;
import com.kim.common.page.PageRes;
import com.kim.common.util.TokenUtil;
import com.kim.quality.business.vo.SampleTapeVo;

/**
 * 抽检录音表服务实现类
 * @date 2018-9-28 9:22:09
 * @author bo.liu01
 */
@Service
public class SampleTapeService extends BaseService {
	
	@Autowired
	private SampleTapeDao sampleTapeDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-9-28 9:22:09
	 * @author bo.liu01
	 */
	public SampleTapeEntity findById(String id) {
	
		return sampleTapeDao.find(new SampleTapeVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-9-28 9:22:09
	 * @author bo.liu01
	 */
	public SampleTapeEntity find(SampleTapeVo vo) {
	
		return sampleTapeDao.find(vo);
	}
	
	
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-9-28 9:22:09
	 * @author bo.liu01
	 */
	public PageRes<SampleTapeEntity> listByPage(SampleTapeVo vo) {
		
		return sampleTapeDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-9-28 9:22:09
	 * @author bo.liu01
	 */
	public List<SampleTapeEntity> list(SampleTapeVo vo) {
		
		return sampleTapeDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-9-28 9:22:09
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public SampleTapeEntity insert(SampleTapeEntity entity) {
		
		int n = sampleTapeDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-9-28 9:22:09
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(SampleTapeEntity entity) {

		return sampleTapeDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-9-28 9:22:09
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(SampleTapeVo vo) {

		return sampleTapeDao.deleteLogic(vo);
	}

}
