package com.kim.sp.dao;

import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.sp.entity.SmsTemplateEntity;
import com.kim.sp.vo.SmsTemplateVo;

 /**
 * 短信模板表数据接口类
 * @date 2019-3-11 14:31:51
 * @author liubo
 */
@Repository
public interface SmsTemplateDao extends BaseDao<SmsTemplateEntity, SmsTemplateVo>{

}