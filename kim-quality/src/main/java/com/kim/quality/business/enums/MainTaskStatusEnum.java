package com.kim.quality.business.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * @Desc: 主任务状态转换
 * @Author: yonghui.wu
 * @Date: 2018/9/14 9:23
 */
public enum MainTaskStatusEnum {
	
	STATUS_TO_ALLOCATE(MainTask.STATUS_TO_ALLOCATE,"待分配"),
	STATUS_RECYCLED(MainTask.STATUS_RECYCLED,"已回收"),
	STATUS_TO_EVALUATION(MainTask.STATUS_TO_EVALUATION,"待质检"),
	STATUS_TO_APPROVAL(MainTask.STATUS_TO_APPROVAL,"待审批"),
	STATUS_RESULT_FEEDBACK(MainTask.STATUS_RESULT_FEEDBACK,"反馈结果"),
	STATUS_APPROVAL_REJECTED(MainTask.STATUS_APPROVAL_REJECTED,"审批驳回"),
	STATUS_TO_APPROVAL_APPEAL(MainTask.STATUS_TO_APPROVAL_APPEAL,"申诉待审批"),
	STATUS_TO_APPEAL_DEAL(MainTask.STATUS_TO_APPEAL_DEAL,"申诉待处理"),
	STATUS_APPEAL_PASS(MainTask.STATUS_APPEAL_PASS,"申诉成功"),
	STATUS_APPEAL_REJECTED(MainTask.STATUS_APPEAL_REJECTED,"申诉驳回"),
	STATUS_RESULT_CONFIRM(MainTask.STATUS_RESULT_CONFIRM,"坐席已确认");

	private Integer key;
	private String value;
	
	private MainTaskStatusEnum(int key,String value){
		this.key = key;
		this.value = value;
	}
	
	public static List<JSONObject> list(){
		return list(null);
	}
	
	public static List<JSONObject> list(int ... keys){
		MainTaskStatusEnum[] values = MainTaskStatusEnum.values();
		List<JSONObject> list = new ArrayList<>(values.length);
		JSONObject obj;
		if(keys == null || keys.length == 0){
			for(MainTaskStatusEnum s : values){
				obj = new JSONObject();
				obj.put("value", s.getKey());
				obj.put("name", s.getValue());
				list.add(obj);
			}
		}else{
			for(MainTaskStatusEnum s : values){
				if(ArrayUtils.contains(keys, s.getKey())){
					obj = new JSONObject();
					obj.put("value", s.getKey());
					obj.put("name", s.getValue());
					list.add(obj);
				}
			}
		}
		return list;
	}
	
	public static Map<Integer, String> getMap(){
		Map<Integer, String> map = new HashMap<>(MainTaskStatusEnum.values().length);
		for(MainTaskStatusEnum s:MainTaskStatusEnum.values()){
			map.put(s.getKey(), s.getValue());
		}
		return map;
	}
	
	public static String getValueByKey(Integer key){
		for(MainTaskStatusEnum s:MainTaskStatusEnum.values()){
			if(s.getKey().equals(key)){
				return s.getValue();
			}
		}
		return "";
	}
	
	public static Integer getKeyByValue(String value){
		for(MainTaskStatusEnum s:MainTaskStatusEnum.values()){
			if(s.getValue().equals(value)){
				return s.getKey();
			}
		}
		return null;
	}

	public Integer getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
