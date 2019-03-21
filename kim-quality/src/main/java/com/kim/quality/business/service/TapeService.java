package com.kim.quality.business.service;

import java.util.List;

import com.kim.quality.business.entity.SampleTapeEntity;
import com.kim.quality.business.entity.TapeEntity;
import com.kim.quality.business.vo.TapeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.common.base.BaseService;
import com.kim.common.page.PageRes;
import com.kim.common.util.TokenUtil;
import com.kim.quality.business.dao.TapeDao;

/**
 * 录音池表服务实现类
 * @date 2018-8-16 18:34:17
 * @author bo.liu01
 */
@Service
public class TapeService extends BaseService {
	
	@Autowired
	private TapeDao tapeDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-16 18:34:17
	 * @author bo.liu01
	 */
	public TapeEntity findById(String id) {
	
		return tapeDao.find(new TapeVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-16 18:34:17
	 * @author bo.liu01
	 */
	public TapeEntity find(TapeVo vo) {
	
		return tapeDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-8-16 18:34:17
	 * @author bo.liu01
	 */
	public PageRes<TapeEntity> listByPage(TapeVo vo) {
		
		return tapeDao.listByPage(vo, vo.getPage());
	}
	
	
	/**
	 * 查询每个坐席N条记录
	 * @param vo
	 * @return
	 */
	public List<SampleTapeEntity> listByAgent(TapeVo vo) {
		
		return tapeDao.listByAgent(vo);
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-8-16 18:34:17
	 * @author bo.liu01
	 */
	public List<SampleTapeEntity> listForSample(TapeVo vo) {
		
		return tapeDao.listForSample(vo);
	}
	
	public int listCount(TapeVo vo) {
		
		return tapeDao.listCount(vo);
	}
	
	/**
	 * 查询当天在线坐席
	 * @param vo
	 * @return
	 */
	public List<String> listOnline(TapeVo vo) {
		
		return tapeDao.listOnline(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-8-16 18:34:17
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public TapeEntity insert(TapeEntity entity) {
		
		int n = tapeDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-8-16 18:34:17
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(TapeEntity entity) {

		return tapeDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-8-16 18:34:17
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(TapeVo vo) {

		return tapeDao.deleteLogic(vo);
	}
	
	

}
