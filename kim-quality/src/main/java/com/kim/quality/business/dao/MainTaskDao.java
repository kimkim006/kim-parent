package com.kim.quality.business.dao;

import com.kim.quality.business.entity.MainTaskEntity;
import com.kim.quality.business.vo.MainTaskVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.common.page.PageRes;
import com.kim.common.page.PageVo;

/**
 * 质检任务表数据接口类
 *
 * @author bo.liu01
 * @date 2018-8-28 18:24:20
 */
@Repository
public interface MainTaskDao extends BaseDao<MainTaskEntity, MainTaskVo> {

	int allocate(MainTaskVo vo);
	
	int recycle(MainTaskVo vo);

	/**
	 * 分页查询质检结果列表
	 * @param vo
	 * @param page
	 * @return
	 */
	PageRes<MainTaskEntity> listResult(@Param("obj")MainTaskVo vo, @Param("page")PageVo page);
	
	/**
	 * 待评分页面
	 * @param vo
	 * @param page
	 * @return
	 */
    PageRes<MainTaskEntity> listEvaluation(@Param("obj") MainTaskVo vo, @Param("page") PageVo page);

	/**
	 * 分页查询质检审核任务列表
	 * @param vo
	 * @param page
	 * @return
	 */
	PageRes<MainTaskEntity> listApproval(@Param("obj")MainTaskVo vo, @Param("page")PageVo page);
	
	/**
	 * 修改任务状态
	 * @param task
	 * @return
	 */
	int updateStatus(MainTaskEntity task);

	PageRes<MainTaskEntity> listSampleDetail(@Param("obj")MainTaskVo vo, @Param("page")PageVo page);


}