package com.kim.common.util.collectioninter;

/**
 * Created by bo.liu01 on 2017/11/10.
 */
public interface FilterCondition<T>{
    /**
     * 过滤条件
     * @param t
     * @return true将会保留，false将会被过滤掉
     */
    boolean evaluate(T t);
}

