package com.kim.quality.business.dao;

import com.kim.quality.business.entity.IvrInfoEntity;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.quality.business.vo.IvrInfoVo;

 /**
 * 录音IVR信息数据接口类
 * @date 2018-11-16 15:55:07
 * @author bo.liu01
 */
@Repository
public interface IvrInfoDao extends BaseDao<IvrInfoEntity, IvrInfoVo>{

}