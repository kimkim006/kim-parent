package com.kim.quality.business.dao;

import com.kim.quality.business.entity.SampleTapeEntity;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.quality.business.vo.SampleTapeVo;

 /**
 * 抽检录音表数据接口类
 * @date 2018-9-28 9:22:10
 * @author bo.liu01
 */
@Repository
public interface SampleTapeDao extends BaseDao<SampleTapeEntity, SampleTapeVo>{

}