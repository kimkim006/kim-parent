package com.kim.quality.setting.dao;

import com.kim.quality.setting.vo.DarkSettingVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.quality.setting.entity.DarkSettingEntity;

/**
 * 抽检小黑屋表数据接口类
 * @date 2018-11-21 17:34:22
 * @author bo.liu01
 */
@Repository
public interface DarkSettingDao extends BaseDao<DarkSettingEntity, DarkSettingVo>{
	
	int active(DarkSettingVo vo);

}