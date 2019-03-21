package com.kim.admin.common;

import com.kim.log.operatelog.Operation;

/**
 * Created by bo.liu01 on 2017/11/1.
 */
public class AdminOperation extends Operation {

    //自定义操作名称
    public static final String FRONT_USER = "首页用户";
    public static final String FRONT_MENUS = "首页菜单";
                  
    public static final String CHECK_UNIQUE_USERNAME= "用户名唯一校验";
                  
    public static final String TEMPLATE_MENU_TREE= "模板菜单树查询";
    public static final String TEMPLATE_MENU_ADD= "模板添加菜单";
                  
    public static final String TENANT_MENU_TREE= "租户菜单树查询";
    public static final String TENANT_MENU_ADD= "租户添加菜单";
                  
    public static final String AUTH_MENU_TREE= "权限树查询";
    public static final String AUTH_ADD_ROLE= "角色权限添加";
    public static final String AUTH_ADD_USER= "用户权限添加";
                  
    public static final String USER_DEPART_ADD= "用户部门添加";
                  
    public static final String DEPART_TREE_CON= "选择部门树控件";
                  
    public static final String PAGE_DEPART_USER= "查询机构的人员";
    public static final String PAGE_DEPART_USER_= "查询非机构的人员";
    public static final String DEPART_ADD_USER= "机构添加人员";
    public static final String DEPART_REMOVE_USER= "机构删除人员";
                  
    public static final String PAGE_ROLE_USER= "查询角色的人员";
    public static final String PAGE_ROLE_USER_= "查询非角色的人员";
    public static final String ROLE_ADD_USER= "角色添加人员";
    public static final String ROLE_REMOVE_USER= "角色删除人员";
                  
    public static final String USER_ORG_ADD_USER= "添加用户关系";
    public static final String USER_ORG_REMOVE_USER= "删除用户关系";
                  
    public static final String PAGE_GROUP_USER= "查询小组的人员";
    public static final String PAGE_GROUP_USER_= "查询非小组的人员";
    public static final String GROUP_ADD_USER= "小组添加人员";
    public static final String GROUP_REMOVE_USER= "小组删除人员";
                  
    public static final String CHECK_UNIQUE_USERAGENTNO= "话务工号唯一校验";
                  
    public static final String CHANGE_PASSWORD= "修改密码";
    public static final String FIND_CUR_INFO= "查询当前用户";

}
