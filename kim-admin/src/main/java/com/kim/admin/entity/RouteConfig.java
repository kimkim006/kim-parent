package com.kim.admin.entity;

import java.util.ArrayList;
import java.util.List;

import com.kim.common.base.BaseObject;

/**
 * Created by bo.liu01 on 2017/11/17.
 */
public class RouteConfig extends BaseObject{
	private static final long serialVersionUID = 1L;
	
	//路由配置属性,说明参考菜单实体类
    private String name;// 命名路由
    private String code;//编码，唯一标识
    private String path;//跳转路径，相对路径
    private String absolutePath;//跳转绝对路径
    private String parentCode;//父路径
    private String icon;//图标
    private Integer type;//菜单类型
    private Integer sortNum;//排序号
    private String component;//组件
    private String redirect;// string | Location | Function
    private String alias;//别名
    private List<RouteConfig> children;// Array<RouteConfig> // 嵌套路由
    //beforeEnter?: (to: Route, from: Route, next: Function) => void
    private RouteConfigMeta meta;// any
    // vue 2.6.0+
    private boolean caseSensitive; // 匹配规则是否大小写敏感？(默认值：false)
    private Object pathToRegexpOptions; // 编译正则的选项

    //过滤路由菜单时的条件字段

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<RouteConfig> getChildren() {
        return children;
    }

    public void add(RouteConfig routeConfig) {
        if(this.children == null){
            this.children = new ArrayList<>();
        }
        this.children.add(routeConfig);
    }

    public void setChildren(List<RouteConfig> children) {
        this.children = children;
    }

    public RouteConfigMeta getMeta() {
        return meta;
    }

    public void setMeta(RouteConfigMeta meta) {
        this.meta = meta;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public Object getPathToRegexpOptions() {
        return pathToRegexpOptions;
    }

    public void setPathToRegexpOptions(Object pathToRegexpOptions) {
        this.pathToRegexpOptions = pathToRegexpOptions;
    }
}
