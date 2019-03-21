package com.kim.quality.business.dao;

import com.kim.quality.business.entity.TapeDamagedEntity;
import com.kim.quality.business.vo.TapeDamagedVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 录音损坏明细表数据接口类
 * @date 2018-9-26 15:01:14
 * @author yonghui.wu
 */
@Repository
public interface TapeDamagedDao extends BaseDao<TapeDamagedEntity, TapeDamagedVo>{

}