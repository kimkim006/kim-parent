package com.kim.schedule.task.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface MainTaskDao {
	
	int updateTaskConfirm(Map<String, String> map);

}
