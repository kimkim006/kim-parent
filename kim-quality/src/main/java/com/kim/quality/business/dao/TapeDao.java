package com.kim.quality.business.dao;

import java.util.List;

import com.kim.quality.business.entity.SampleTapeEntity;
import com.kim.quality.business.entity.TapeEntity;
import com.kim.quality.business.vo.TapeVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 录音池表数据接口类
 * @date 2018-8-16 18:34:17
 * @author bo.liu01
 */
@Repository
public interface TapeDao extends BaseDao<TapeEntity, TapeVo>{
	
	int listCount(@Param("obj")TapeVo vo);
	
	List<String> listOnline(@Param("obj")TapeVo vo);

	List<SampleTapeEntity> listByAgent(@Param("obj")TapeVo vo);

	//TapeEntity findSampleTape(TapeVo vo);

	List<SampleTapeEntity> listForSample(@Param("obj")TapeVo vo);

}