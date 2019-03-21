package com.kim.quality.business.dao;

import java.util.List;
import java.util.Map;

import com.kim.quality.business.vo.ApprovalVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.quality.business.entity.ApprovalEntity;

/**
 * 审批记录明细表数据接口类
 * @date 2018-9-19 10:11:34
 * @author bo.liu01
 */
@Repository
public interface ApprovalDao extends BaseDao<ApprovalEntity, ApprovalVo>{

	List<ApprovalEntity> listKey(ApprovalVo vo);

     /**获取审批记录ID
      * @param vo
      * @return
      */
     List<String> getApprovalIds(@Param("obj")ApprovalVo vo);

	int clearPre(ApprovalVo vo);

	List<String> listLast(ApprovalVo vo);

	int updateLastById(Map<String, Object> param);

}