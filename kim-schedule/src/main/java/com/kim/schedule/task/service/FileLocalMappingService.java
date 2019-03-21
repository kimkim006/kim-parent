package com.kim.schedule.task.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.kim.schedule.common.CommonConstant;
import com.kim.schedule.job.annotation.TaskTarget;
import com.kim.schedule.task.dao.FileLocalMappingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.base.service.BaseCacheUtil;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.DateUtil;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;
import com.kim.quality.file.entity.FileLocalMappingEntity;
import com.kim.quality.file.vo.FileLocalMappingVo;

@TaskTarget
@Service
public class FileLocalMappingService extends BaseTaskService{
	
	@Autowired
	private FileLocalMappingDao fileLocalMappingDao;
	
	private static final int DEFAULT_DAYS = 7;
	
	@TaskTarget
	public int clearTmpFile(Map<String, String> param) {
		
		dealDate(param);
		return execute(param, (tmpParam, tenantId)->clearTmpFileSingle(tmpParam, tenantId));
		
	}

	@Transactional(readOnly=false)
	public int clearTmpFileSingle(Map<String, String> param, String tenantId) {
		String limitTime = param.get("date");
		if(StringUtil.isBlank(limitTime)){
			limitTime = getLimitTime(tenantId);
		}
		
		FileLocalMappingVo v = new FileLocalMappingVo().tenantId(tenantId);
		v.setLimitTime(limitTime);
		v.setActive(FileLocalMappingEntity.ACTIVE_YES);
		List<FileLocalMappingEntity> list = fileLocalMappingDao.list(v);
		int n = 0;
		if(CollectionUtil.isNotEmpty(list)){
			for (FileLocalMappingEntity mapping : list) {
				if(StringUtil.isNotBlank(mapping.getLocalPath())){
					File file = new File(mapping.getLocalPath());
					if(!file.exists() || !file.isFile() || file.delete()){
						n += fileLocalMappingDao.deleteLogic(v.id(mapping.getId()));
					}					
				}
			}
		}
		return n;
	}

	private String getLimitTime(String tenantId) {
		//15天前的日期
		String daysStr = BaseCacheUtil.getParam(CommonConstant.TAPE_FILE_TMP_CACHE_DAYS, tenantId);
		int day = DEFAULT_DAYS;
		if(StringUtil.isNotBlank(daysStr) && NumberUtil.isNumber(daysStr)){
			day = Integer.parseInt(daysStr); 
		}
		return DateUtil.getDurationDateStr(-day);
	}

}
