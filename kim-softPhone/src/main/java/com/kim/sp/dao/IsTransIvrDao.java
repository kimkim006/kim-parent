package com.kim.sp.dao;

import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.sp.entity.IsTransIvrEntity;
import com.kim.sp.vo.IsTransIvrVo;

 /**
 * 转IVR信息表数据接口类
 * @date 2019-3-7 15:41:34
 * @author liubo
 */
@Repository
public interface IsTransIvrDao extends BaseDao<IsTransIvrEntity, IsTransIvrVo>{

}