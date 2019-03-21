package com.kim.admin.entity;

import java.util.ArrayList;
import java.util.List;

import com.kim.common.base.BaseObject;

/**
 * Created by bo.liu01 on 2017/11/17.
 */
public class FrontUser extends BaseObject {

	private static final long serialVersionUID = 1L;
	
	private UserEntity info;
    private List<String> roles = new ArrayList<>();
    private List<RouteConfig> routeConfigs = new ArrayList<>();
    private List<String> permissions = new ArrayList<>();

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public UserEntity getInfo() {
        return info;
    }

    public void setInfo(UserEntity info) {
        this.info = info;
    }

    public List<RouteConfig> getRouteConfigs() {
        return routeConfigs;
    }

    public void setRouteConfigs(List<RouteConfig> routeConfigs) {
        this.routeConfigs = routeConfigs;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
