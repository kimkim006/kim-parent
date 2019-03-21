package com.kim.sp.entity;

import java.util.HashMap;
import java.util.Map;

import com.kim.common.base.BaseObject;

public class AgentFront extends BaseObject{
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> agentConfig = new HashMap<>();
	private Map<String, Object> xtoolboxConfig = new HashMap<>();
	
	public void putAgentConfig(String key, Object value){
		agentConfig.put(key, value);
	}
	
	public void putAgentConfigAll(Map<String, Object> map){
		agentConfig.putAll(map);
	}
	
	public void putXtoolboxConfig(String key, Object value){
		xtoolboxConfig.put(key, value);
	}
	
	public void putXtoolboxConfigAll(Map<String, Object> map){
		xtoolboxConfig.putAll(map);
	}
	
	public Map<String, Object> getAgentConfig() {
		return agentConfig;
	}
	
	public void setAgentConfig(Map<String, Object> agentConfig) {
		this.agentConfig = agentConfig;
	}
	
	public Map<String, Object> getXtoolboxConfig() {
		return xtoolboxConfig;
	}
	public void setXtoolboxConfig(Map<String, Object> xtoolboxConfig) {
		this.xtoolboxConfig = xtoolboxConfig;
	}

}
