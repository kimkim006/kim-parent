package com.kim.schedule.task.dao;

import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.quality.file.entity.FileLocalMappingEntity;
import com.kim.quality.file.vo.FileLocalMappingVo;

 /**
 * 录音本地映射表数据接口类
 * @date 2018-9-28 13:42:48
 * @author bo.liu01
 */
@Repository
public interface FileLocalMappingDao extends BaseDao<FileLocalMappingEntity, FileLocalMappingVo>{

}