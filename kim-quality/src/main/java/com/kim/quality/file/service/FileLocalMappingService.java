package com.kim.quality.file.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.kim.quality.business.entity.SampleTapeEntity;
import com.kim.quality.business.service.cache.SampleTapeCacheService;
import com.kim.quality.common.BeanConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.kim.common.base.BaseService;
import com.kim.common.page.PageRes;
import com.kim.common.redis.RedisUtil;
import com.kim.common.util.Base64Util;
import com.kim.common.util.DateUtil;
import com.kim.common.util.HttpClientUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.impexp.util.DownloadUtil;
import com.kim.quality.business.vo.SampleTapeVo;
import com.kim.quality.common.QualityRedisConstant;
import com.kim.quality.file.dao.FileLocalMappingDao;
import com.kim.quality.file.entity.FileLocalMappingEntity;
import com.kim.quality.file.vo.FileLocalMappingVo;

/**
 * 录音本地映射表服务实现类
 * @date 2018-9-28 13:42:47
 * @author bo.liu01
 */
@Service
public class FileLocalMappingService extends BaseService {
	
	@Autowired
	private FileLocalMappingDao fileLocalMappingDao;
	@Autowired
	private SampleTapeCacheService sampleTapeCacheService;
	@Autowired
	private BeanConfig beanConfig;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-9-28 13:42:47
	 * @author bo.liu01
	 */
	public FileLocalMappingEntity findById(String id) {
	
		return fileLocalMappingDao.find(new FileLocalMappingVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	public FileLocalMappingEntity find(FileLocalMappingVo vo) {
		
		return fileLocalMappingDao.find(vo);
	}
	
	/**
	 * 单条本地的下载地址
	 * @param vo vo查询对象
	 * @date 2018-9-28 13:42:47
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public JSONObject findLocalPath(FileLocalMappingVo vo) {
		SampleTapeVo tapeVo = new SampleTapeVo().tenantId(vo.getTenantId());
		tapeVo.setMainTaskId(vo.getMainTaskId());
		SampleTapeEntity tape = sampleTapeCacheService.findWithCache(tapeVo);
		if(tape == null){
			return null;
		}
		if(StringUtil.isBlank(tape.getLocalAddress())){
			if(StringUtil.isBlank(tape.getAddress())){
				return null;
			}
			getLocalPath(vo, tape);
		}
		if(StringUtil.isBlank(tape.getLocalAddress())){
			return null;
		}
		
		JSONObject json = new JSONObject();
		json.put("f", tape.getLocalAddress());
		json.put("s", tape.getSign());
		return json;
	}

	private void getLocalPath(FileLocalMappingVo vo, SampleTapeEntity tape) {
		vo.setActive(FileLocalMappingEntity.ACTIVE_YES);
		FileLocalMappingEntity mapping = fileLocalMappingDao.find(vo);
		if(mapping == null){
			mapping = download(vo, tape);
		}
		if(mapping == null){
			return;
		}
		String key = QualityRedisConstant.getMainTaskTapeKey(vo.getTenantId(), vo.getMainTaskId());
		String path = Base64Util.encode(mapping.getLocalPath());
		tape.setLocalAddress(path);
		tape.setSign(DownloadUtil.sign(mapping.getLocalPath()));
		Long expire = RedisUtil.getExpire(key);
		RedisUtil.opsForValue().set(key, JSONObject.toJSONString(tape), expire, TimeUnit.SECONDS);
	}
	
	private FileLocalMappingEntity download(FileLocalMappingVo vo, SampleTapeEntity tape){

		String fileName = getFileName(tape.getAddress());
		String localPath = beanConfig.getFileTmpDir() + File.separator 
				+ DateUtil.formatDate(new Date(), DateUtil.PATTERN_YYYYMMDD) + File.separator + fileName;
        try {
            HttpClientUtil.download(tape.getAddress(), localPath);

            FileLocalMappingEntity mapping = new FileLocalMappingEntity();
            mapping.setMainTaskId(vo.getMainTaskId());
            mapping.setTapeId(tape.getTapeId());
            mapping.setSerialNumber(tape.getSerialNumber());
            mapping.setPlatform(tape.getPlatform());
            mapping.setHttpAddress(tape.getAddress());
            mapping.setLocalPath(localPath);
            mapping.setFileName(fileName);
            mapping.setActive(FileLocalMappingEntity.ACTIVE_YES);
            mapping.copyBaseField(vo);
            fileLocalMappingDao.insert(mapping);
            return mapping;
        } catch (IOException e) {
            logger.error("文件下载失败", e);
            return null;
        }
    }
	
	private String getFileName(String path){
		int i1 = path.lastIndexOf('/');
		int i2 = path.lastIndexOf('\\');
		return path.substring((i1 > i2 ? i1 : i2) + 1);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-9-28 13:42:47
	 * @author bo.liu01
	 */
	public PageRes<FileLocalMappingEntity> listByPage(FileLocalMappingVo vo) {
		
		return fileLocalMappingDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-9-28 13:42:47
	 * @author bo.liu01
	 */
	public List<FileLocalMappingEntity> list(FileLocalMappingVo vo) {
		
		return fileLocalMappingDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-9-28 13:42:47
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public FileLocalMappingEntity insert(FileLocalMappingEntity entity) {
		
		int n = fileLocalMappingDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-9-28 13:42:47
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(FileLocalMappingEntity entity) {

		return fileLocalMappingDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-9-28 13:42:47
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(FileLocalMappingVo vo) {

		return fileLocalMappingDao.deleteLogic(vo);
	}

}
