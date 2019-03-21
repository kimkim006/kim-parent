package com.kim.quality.business.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.quality.business.entity.AdjustScoreEntity;
import com.kim.quality.business.vo.AdjustScoreVo;

 /**
 * 调整分数记录表数据接口类
 * @date 2018-10-15 10:03:07
 * @author bo.liu01
 */
@Repository
public interface AdjustScoreDao extends BaseDao<AdjustScoreEntity, AdjustScoreVo>{
	
	int clearPre(AdjustScoreVo vo);
	
	List<AdjustScoreEntity> listKey(AdjustScoreVo vo);

}