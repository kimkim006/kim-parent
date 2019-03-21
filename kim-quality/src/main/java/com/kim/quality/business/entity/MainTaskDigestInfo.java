package com.kim.quality.business.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.kim.common.base.BaseObject;

public class MainTaskDigestInfo extends BaseObject{

	private static final long serialVersionUID = 1L;
	
	private String taskId;
	private MainTaskEntity task;
	private List<String> relateKeys;
	
	public MainTaskDigestInfo() {
		super();
	}
	
	public MainTaskDigestInfo(String taskId, List<String> keyList) {
		super();
		this.taskId = taskId;
		this.relateKeys = keyList;
	}
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public List<String> getRelateKeys() {
		return relateKeys;
	}
	
	public void setRelateKeys(List<String> relateKeys) {
		this.relateKeys = relateKeys;
	}
	
	public void relateKeyAdd(String key) {
		if(this.relateKeys == null){
			this.relateKeys = new ArrayList<>();
		}
		this.relateKeys.add(key);
	}
	
	public void relateKeyAddAll(Collection<String> c) {
		if(this.relateKeys == null){
			this.relateKeys = new ArrayList<>();
		}
		this.relateKeys.addAll(c);
	}

	public MainTaskEntity getTask() {
		return task;
	}

	public void setTask(MainTaskEntity task) {
		this.task = task;
	}


}
