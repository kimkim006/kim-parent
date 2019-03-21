package com.kim.admin.dao;

import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.admin.entity.PostEntity;
import com.kim.admin.vo.PostVo;

 /**
 * 职位表数据接口类
 * @date 2017-11-16 14:28:31
 * @author bo.liu01
 */
@Repository
public interface PostDao extends BaseDao<PostEntity, PostVo>{

}