package com.kim.log.service;

import com.kim.log.entity.OperateLogsEntity;

/**
 * Created by bo.liu01 on 2017/11/1.
 */
public interface OperateLogsService<T extends OperateLogsEntity> {

    int insert(T t);
}
