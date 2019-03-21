package com.kim.sp.dao;

import com.kim.sp.entity.SmsSendHistoryEntity;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.sp.vo.SmsSendHistoryVo;

 /**
 * 上下行短信发送记录数据接口类
 * @date 2019-3-7 15:41:35
 * @author liubo
 */
@Repository
public interface SmsSendHistoryDao extends BaseDao<SmsSendHistoryEntity, SmsSendHistoryVo>{

}