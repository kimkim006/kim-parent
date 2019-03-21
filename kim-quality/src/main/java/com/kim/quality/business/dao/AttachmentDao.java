package com.kim.quality.business.dao;

import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.quality.business.entity.AttachmentEntity;
import com.kim.quality.business.vo.AttachmentVo;

 /**
 * 附件信息表数据接口类
 * @date 2018-12-7 11:29:20
 * @author liubo
 */
@Repository
public interface AttachmentDao extends BaseDao<AttachmentEntity, AttachmentVo>{

}