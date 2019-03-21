package com.kim.admin.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kim.admin.entity.MenuEntity;
import com.kim.admin.entity.RouteConfig;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;

public class MenuTreeUtil {
	
	public static void getMenus(String code, List<MenuEntity> menuList, Map<String, MenuEntity> allMenuMap, Set<String> menuCodeSet) {
		if(menuCodeSet.contains(code)){
			return ;
		}
		menuCodeSet.add(code);
		MenuEntity menu = allMenuMap.get(code);
		if(menu != null){
			menuList.add(menu);
			if(StringUtil.isBlank(menu.getParentCode())){
				return ;
			}
			getMenus(menu.getParentCode(), menuList, allMenuMap, menuCodeSet);
		}
	}
	
	public static List<MenuEntity> treeFilter(List<MenuEntity> list){
		return CollectionUtil.filter(list, (MenuEntity t)->StringUtil.isBlank(t.getParentCode()));
	}
	
	public static List<MenuEntity> buildTree(List<MenuEntity> list, Set<String> menuCodeSet) {
		Map<String, MenuEntity> menuMap = CollectionUtil.getMapByProperty(list, "code");
		
		MenuEntity pmenu;
		boolean flag = CollectionUtil.isNotEmpty(menuCodeSet);
		for(MenuEntity menu:list){
			if(flag && menuCodeSet.contains(menu.getCode())){
				menu.setDisabled(true);
			}
			pmenu = menuMap.get(menu.getParentCode());
			if(pmenu == null){
				continue;
			}
			menu.setParentName(pmenu.getName());
			pmenu.add(menu);
		}
		return list;
	}
	
	public static List<RouteConfig> sortByAsc(List<RouteConfig> list){
		return sort(list, (RouteConfig o1, RouteConfig o2)->NumberUtil.compare(o1.getSortNum(), o2.getSortNum()));
	}
	
	public static List<RouteConfig> sort(List<RouteConfig> list, Comparator<RouteConfig> comparator){
		
		if(CollectionUtil.isEmpty(list)){
			return list;
		}
		Collections.sort(list, comparator);
		//子节点排序
		for(RouteConfig config : list){
			if(CollectionUtil.isEmpty(config.getChildren())){
				continue;
			}
			sort(config.getChildren(), comparator);
		}
		return list;
	}

}
