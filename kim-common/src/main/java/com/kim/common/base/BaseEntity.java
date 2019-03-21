package com.kim.common.base;

/**
* 实体基类
 */
public class BaseEntity extends BaseTable{

    private static final long serialVersionUID = 6805277017469099069L;

    public BaseEntity(){
        setAtvFlag(ATV_FLAG_YES);
    }

}
