package com.kim.quality.business.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.common.page.PageRes;
import com.kim.common.page.PageVo;
import com.kim.quality.business.entity.RecycleEntity;
import com.kim.quality.business.vo.RecycleVo;

 /**
 * 任务回收记录表数据接口类
 * @date 2018-9-10 10:10:11
 * @author bo.liu01
 */
@Repository
public interface RecycleDao extends BaseDao<RecycleEntity, RecycleVo>{
	
	PageRes<Map<String, String>> listUserByBacth(@Param("obj")RecycleVo vo, @Param("page")PageVo page);

}