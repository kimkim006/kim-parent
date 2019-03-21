package com.kim.sp.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.kim.sp.dao.AgentInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.kim.admin.entity.GroupUserEntity;
import com.kim.admin.entity.UserAgentEntity;
import com.kim.base.common.PlatformEnum;
import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.common.page.PageRes;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.DateUtil;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.sp.common.CommonConstant;
import com.kim.sp.entity.AgentFront;
import com.kim.sp.entity.AgentFunctionEntity;
import com.kim.sp.entity.AgentInfoEntity;
import com.kim.sp.entity.PlatformEntity;
import com.kim.sp.entity.RestSettingEntity;
import com.kim.sp.vo.AgentFunctionVo;
import com.kim.sp.vo.AgentInfoVo;
import com.kim.sp.vo.PlatformVo;

/**
 * 工号信息表服务实现类
 * @date 2019-3-7 15:41:34
 * @author liubo
 */
@Service
public class AgentInfoService extends BaseService {
	
	@Autowired
	private AgentInfoDao agentInfoDao;
	@Autowired
	private RestSettingService restSettingService;
	@Autowired
	private AgentFunctionService agentFunctionService;
	@Autowired
	private PlatformService platformService;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	public AgentInfoEntity findById(String id) {
	
		return agentInfoDao.find(new AgentInfoVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	public AgentInfoEntity find(AgentInfoVo vo) {
	
		return agentInfoDao.find(vo);
	}
	
	/**
	 * 加载坐席软电话配置
	 * @param vo
	 * @return
	 */
	/**
	 * @param vo
	 * @return
	 */
	public AgentFront initAgentInfo(AgentInfoVo vo) {
		
		AgentFront agentFront = initConfigParam(vo, new AgentFront());
		//initXtoolbox(vo, agentFront)
		//加载话务平台信息
		PlatformVo platformVo = new PlatformVo().tenantId(vo.getTenantId());
		platformVo.setPlatformType(PlatformEnum.CISCO.getKey());
		List<PlatformEntity> platformList = platformService.list(platformVo);
		if(CollectionUtil.isEmpty(platformList)){
			logger.error("尚未配置话务平台信息，无法加载软电话!");
			throw new BusinessException("尚未配置话务平台信息，无法加载软电话!");
		}
		if(platformList.size() > 1){
			logger.warn("话务平台[{}]信息有{}条，默认将选择第一条！", PlatformEnum.CISCO.getKey(), platformList.size());
		}
		agentFront.putAgentConfigAll(CollectionUtil.java2Map(platformList.get(0), 
				"platformId", "platformType", "platformName", "ctiIpPort", "ctiIpPortSpare=>ctiIpPortExt", 
				"enableLog", "enableSendStateChgData=>enableSendStatus", "sendStateChgDataServerAdd=>sendStatusAddress",
				"recordPlatformId", "recordPlatformName", "recordType", "recordFormat", "httpAddress"));
		
		logger.info("坐席加载软电话配置：{}", JSONObject.toJSONString(agentFront));
		return agentFront;
	}

	/*//此处代码为迁移软电话前的数据格式需要，但是迁移过来后发现，这个Xtoolbox数据没有不影响软电话的使用，所以此处代码暂时注释掉，以后需要再开启
	private void initXtoolbox(AgentInfoVo vo, AgentFront agentFront) {
		JSONObject xtoolboxConfig = BaseCacheUtil.getDict(CommonConstant.DICT_XTOOLBOX_CONFIG, vo.getTenantId());
		agentFront.setXtoolboxConfig(xtoolboxConfig);
		for (Entry<String, Object> entry : xtoolboxConfig.entrySet()) {
			if ("enableLog".equals(entry.getKey())) {
				xtoolboxConfig.put("enableLog", Boolean.parseBoolean(String.valueOf(entry.getValue())));
			} else if ("logCycle".equals(entry.getKey())) {
				xtoolboxConfig.put("logCycle", Long.parseLong(String.valueOf(entry.getValue())));
			} else if ("logPath".equals(entry.getKey())) {
				xtoolboxConfig.put("logPath", String.valueOf(entry.getValue()));
			} else if ("enableDebugConsole".equals(entry.getKey())) {
				xtoolboxConfig.put("enableDebugConsole",Boolean.parseBoolean(String.valueOf(entry.getValue())));
			} else if ("soxFilePath".equals(entry.getKey())) {
				xtoolboxConfig.put("soxFilePath", String.valueOf(entry.getValue()));
			} else if ("webServiceUrlForRecFile".equals(entry.getKey())) {
				xtoolboxConfig.put("webServiceUrlForRecFile", String.valueOf(entry.getValue()));
			} else if ("recFileUrlFieldName".equals(entry.getKey())) {
				xtoolboxConfig.put("recFileUrlFieldName", String.valueOf(entry.getValue()));
			} else if ("httpListenPort".equals(entry.getKey())) {
				xtoolboxConfig.put("httpListenPort", Long.parseLong(String.valueOf(entry.getValue())));
			} else if ("xtoolBoxClsid".equals(entry.getKey())) {
				xtoolboxConfig.put("xtoolBoxClsid", String.valueOf(entry.getValue()));
			} else if ("xtoolBoxVersion".equals(entry.getKey())) {
				xtoolboxConfig.put("xtoolBoxVersion", String.valueOf(entry.getValue()));
			} else if ("xtoolBoxCodebase".equals(entry.getKey())) {
				xtoolboxConfig.put("xtoolBoxCodebase", String.valueOf(entry.getValue()));
			}
		}
	}*/

	private AgentFront initConfigParam(AgentInfoVo vo, AgentFront agentFront) {
		agentFront.putAgentConfig(CommonConstant.PARAM_AGENT_READY_TIME, 
				BaseCacheUtil.getParam(CommonConstant.PARAM_AGENT_READY_TIME, vo.getTenantId(), CommonConstant.DEFAULT_AGENT_READY_TIME));
		
		agentFront.putAgentConfig(CommonConstant.AGENT_FOR_REST, "false");
		GroupUserEntity group = BaseCacheUtil.getCurGroup();
		if(group != null){
			RestSettingEntity restSetting = restSettingService.findByOrg(group.getGroupCode(), vo.getTenantId());
			if(restSetting != null && NumberUtil.equals(restSetting.getIsNeedApply(), RestSettingEntity.IS_NEED_APPLY_YES)){
				agentFront.putAgentConfig(CommonConstant.AGENT_FOR_REST, "true");
			}
		}
		
		UserAgentEntity agent = BaseCacheUtil.getCurAgent(PlatformEnum.CISCO);
		if(agent != null){
			agentFront.putAgentConfig(CommonConstant.AGENT_ID, agent.getAgentNo());
			
			//查询功能值
			AgentFunctionVo agentFunctionVo = new AgentFunctionVo().tenantId(vo.getTenantId());
			agentFunctionVo.setAgentId(agent.getAgentNo());
			agentFunctionVo.setPlatformType(PlatformEnum.CISCO.getKey());
			List<AgentFunctionEntity> agentFunctionList = agentFunctionService.list(agentFunctionVo);
			if(CollectionUtil.isNotEmpty(agentFunctionList)){
				for (AgentFunctionEntity entity : agentFunctionList) {
					agentFront.putAgentConfig(entity.getFuncCode(), 
							convert(entity.getFuncValueType(), entity.getFuncValue()));
				}
			}
		}
		return agentFront;
	}
	
	private Object convert(String type, String value){
		value = StringUtil.trim(value);
		if(StringUtil.isBlank(type)){
			return value;
		}
		switch (type.toLowerCase()) {
		case "string": return value;
		case "boolean": return Boolean.parseBoolean(value);
		case "short": return Short.parseShort(value);
		case "integer": 
		case "int": return Integer.parseInt(value);
		case "long": return Long.parseLong(value);
		case "double": return Double.parseDouble(value);
		case "bigdecimal": return new BigDecimal(value);
		case "datetime": return DateUtil.getCurrentTime();
		case "date": return DateUtil.formatDate(new Date());
		case "time": return DateUtil.formatDate(new Date(), DateUtil.PATTERN_HH_MM_SS);
		default:return value;
		}
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	public PageRes<AgentInfoEntity> listByPage(AgentInfoVo vo) {
		
		return agentInfoDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	public List<AgentInfoEntity> list(AgentInfoVo vo) {
		
		return agentInfoDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public AgentInfoEntity insert(AgentInfoEntity entity) {
		
		int n = agentInfoDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int update(AgentInfoEntity entity) {

		return agentInfoDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int delete(AgentInfoVo vo) {

		return agentInfoDao.deleteLogic(vo);
	}

}
