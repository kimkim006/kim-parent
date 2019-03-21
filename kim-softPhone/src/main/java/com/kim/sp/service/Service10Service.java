package com.kim.sp.service;

import java.util.List;
import java.util.regex.Pattern;

import com.kim.sp.common.CommonConstant;
import com.kim.sp.dao.Service10Dao;
import com.kim.sp.vo.Service10Vo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.admin.entity.DepartmentUserEntity;
import com.kim.base.common.PlatformEnum;
import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.common.page.PageRes;
import com.kim.common.util.DateUtil;
import com.kim.common.util.HttpClientUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.common.util.UUIDUtils;
import com.kim.sp.entity.IsTransIvrEntity;
import com.kim.sp.entity.SendMsgRequest;
import com.kim.sp.entity.SendMsgResponse;
import com.kim.sp.entity.Service10Entity;
import com.kim.sp.entity.SmsSendHistoryEntity;
import com.kim.sp.entity.SmsTemplateEntity;

/**
 * 服务记录信息表服务实现类
 * @date 2019-3-6 19:35:36
 * @author liubo
 */
@Service
public class Service10Service extends BaseService {
	
	@Autowired
	private Service10Dao service10Dao;
	@Autowired
	private IsTransIvrService isTransIvrService;
	@Autowired
	private SmsTemplateService smsTemplateService;
	@Autowired
	private SmsSendHistoryService smsSendHistoryService;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-6 19:35:36
	 * @author liubo
	 */
	public Service10Entity findById(String id) {
	
		return service10Dao.find(new Service10Vo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-6 19:35:36
	 * @author liubo
	 */
	public Service10Entity find(Service10Vo vo) {
	
		return service10Dao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2019-3-6 19:35:36
	 * @author liubo
	 */
	public PageRes<Service10Entity> listByPage(Service10Vo vo) {
		
		return service10Dao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2019-3-6 19:35:36
	 * @author liubo
	 */
	public List<Service10Entity> list(Service10Vo vo) {
		
		return service10Dao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2019-3-6 19:35:36
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public Service10Entity insert(Service10Entity entity) {
		
		entity.setServiceId(UUIDUtils.getUuid());
		entity.setStartTime(DateUtil.getCurrentTime());
		entity.setName(entity.getOperName());
		entity.setUsername(entity.getOperUser());
		DepartmentUserEntity depart = BaseCacheUtil.getCurDepart();
		if(depart != null){
			entity.setDepartmentCode(depart.getDepartmentCode());
			entity.setDepartmentName(depart.getDepartmentName());
		}
		entity.setAgentId(BaseCacheUtil.getCurAgentId(PlatformEnum.CISCO));
		
		int n = service10Dao.insert(entity);
		return n > 0 ? entity : null;
	}

	
	@Transactional(readOnly=false)
	public int updateConnect(Service10Entity entity) {

		//1、更新服务记录的recordid与连接时间
		entity.setConnectTime(entity.getOperTime());
		int n = service10Dao.update(entity);
		
		if(n > 0){
			//保存转Ivr记录
			isTransIvrService.insertByService10(entity);
		}
				
		return n;
	}
	
	@Transactional(readOnly=false)
	public int disConnect(Service10Entity entity) {
		
		//1、更新服务记录的挂断时间
		entity.setDisconnectTime(entity.getOperTime());
		return service10Dao.update(entity);
	}
	
	/**
	 * 修改记录
	 * @date 2019-3-6 19:35:36
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int update(Service10Entity entity) {
		
		return service10Dao.update(entity);
	}

	@Transactional(readOnly=false)
	public int sendMessage(Service10Vo vo) {
		
		if(StringUtil.isBlank(vo.getAgentId())){
			vo.setAgentId(BaseCacheUtil.getCurAgentId(PlatformEnum.CISCO));
		}
		String phone = vo.getPhone();//客户号码
//		phone="18676786470"
		if(StringUtils.isBlank(phone)){
			logger.info("发送满意度短信时检查到 联系号码为空, agentId:{}, customerCallId:{}, tenantId:{}", 
					vo.getAgentId(), vo.getCustomerCallId(), vo.getTenantId());
			updateIVR(vo, IsTransIvrEntity.IS_SEND_SUCCESS_K);
			return 0;
		}
		
		phone = phone.trim().startsWith("0")? phone.substring(1, phone.length()) : phone;
		//验证手机号
		Pattern p = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$"); // 验证手机号
		if (!p.matcher(phone).matches()) {
			logger.info("发送满意度短信时检查到 联系号码不是手机号, phone:{}, agentId:{}, customerCallId:{}, tenantId:{}", 
					phone, vo.getAgentId(), vo.getCustomerCallId(), vo.getTenantId());
			updateIVR(vo, IsTransIvrEntity.IS_SEND_SUCCESS_K);
			return 0;
		}
		
		return send(vo, phone);
	}

	private int send(Service10Vo vo, String phone){
		//开始发送满意度短信
		logger.info("开始准备发送满意度短信, phone:{}, agentId:{}, customerCallId:{}, tenantId:{}",
				phone, vo.getAgentId(), vo.getCustomerCallId(), vo.getTenantId());
		
		SendMsgRequest sendMsgRequest = new SendMsgRequest();
		String appCode = BaseCacheUtil.getParam(CommonConstant.PARAM_SMS_APP_CODE, vo.getTenantId());
		if(StringUtil.isBlank(appCode)){
			logger.error("短信平台应用编码APPcode未配置，无法发送短信，tenantId:{}", vo.getTenantId());
			throw new BusinessException("短信应用编码未配置, 无法发送短信");
		}
		sendMsgRequest.setAppCode(appCode);
		sendMsgRequest.setBusinessNo(String.valueOf(System.currentTimeMillis()));//业务系统请求批次号
		sendMsgRequest.setPhoneNums(phone);//手机号码

		//查询发送类型、短信内容、模板编码
		String businessCode = BaseCacheUtil.getParam(CommonConstant.PARAM_SMS_UP_DOWN_BUSINESS_CODE, vo.getTenantId());
		if(StringUtil.isBlank(businessCode)){
			logger.error("上下行短信模板-业务编码 未配置，无法发送短信，tenantId:{}", vo.getTenantId());
			throw new BusinessException("上下行短信模板-业务编码 未配置, 无法发送短信");
		}
		SmsTemplateEntity smsTemplate = smsTemplateService.getTemplate(businessCode, vo.getTenantId());
		if(smsTemplate == null){
			logger.error("未找到上下行短信模板，无法发送短信，businessCode:{}, tenantId:{}", businessCode, vo.getTenantId());
			throw new BusinessException("未找到上下行短信模板, 无法发送短信");
		}
		String smsUrl = BaseCacheUtil.getParam(CommonConstant.PARAM_SMS_URL, vo.getTenantId());
		if(StringUtil.isBlank(smsUrl)){
			logger.error("未配置短信接口URL，无法发送短信，tenantId:{}", vo.getTenantId());
			throw new BusinessException("未配置短信接口URL, 无法发送短信");
		}
		
		sendMsgRequest.setBusinessType(smsTemplate.getSendType());//发送类型
		sendMsgRequest.setTemplCode(smsTemplate.getTemplCode());//模板编号
		sendMsgRequest.addParams(vo.getAgentId());//座席工号

		SmsSendHistoryEntity smsSendHistory = createSmsSendHistoryEntity(vo, sendMsgRequest, phone);
		logger.info("-----下行短信发送开始, smsUrl:{}, sendMsgRequest:{}, tenantId:{}", smsUrl, sendMsgRequest, vo.getTenantId());
		try {
			SendMsgResponse response = HttpClientUtil.post(smsUrl, sendMsgRequest, SendMsgResponse.class);
			logger.info("-----下行短信返回:response:{}", response);
			if(response == null){
				logger.error("发送短信返回结果为空!");
				smsSendHistory.setSmsRespRes(SmsSendHistoryEntity.SMS_RESP_RES_UNKNOWN);
				smsSendHistory.setRemark("发送短信返回结果为空!");
				updateIVR(vo, IsTransIvrEntity.IS_SEND_SUCCESS_NO);
			}else{
				if (StringUtil.equals(SendMsgResponse.RESP_CODE_SUCCESS, response.getRespCode())) {
					smsSendHistory.setSmsRespNo(response.getSmsRespNo());
					smsSendHistory.setSmsRespRes(SmsSendHistoryEntity.SMS_RESP_RES_SUCCESS);
					//接口发送成功
					updateIVR(vo, IsTransIvrEntity.IS_SEND_SUCCESS_YES);
				}else{
					updateIVR(vo, IsTransIvrEntity.IS_SEND_SUCCESS_NO);
					smsSendHistory.setSmsRespRes(SmsSendHistoryEntity.SMS_RESP_RES_FAIL);
					smsSendHistory.setRemark(StringUtil.substring(response.getRespMessage(), 0, 999));
				}
			}
			smsSendHistoryService.insert(smsSendHistory);
		} catch (Exception e) {
			logger.info("-----下行短信请求异常:", e);
			smsSendHistory.setSmsRespRes(SmsSendHistoryEntity.SMS_RESP_RES_EXCEPTION);
			smsSendHistory.setRemark(StringUtil.substring(e.getMessage(), 0, 999));
			smsSendHistoryService.insert(smsSendHistory);
			updateIVR(vo, IsTransIvrEntity.IS_SEND_SUCCESS_NO);
		}
		return 1;
	}
	
	private SmsSendHistoryEntity createSmsSendHistoryEntity(Service10Vo vo, SendMsgRequest sendMsgRequest, String phone){
		SmsSendHistoryEntity smsSendHistory = new SmsSendHistoryEntity();
		smsSendHistory.copyBaseField(vo);

		smsSendHistory.setAppCode(sendMsgRequest.getAppCode());//买买提新业务系统代码
		smsSendHistory.setBusinessNo(sendMsgRequest.getBusinessNo());
		smsSendHistory.setCustomerCallId(vo.getCustomerCallId());
		smsSendHistory.setPhone(phone);
		smsSendHistory.setBusinessType(sendMsgRequest.getBusinessType());//发送类型
		smsSendHistory.setTempCode(sendMsgRequest.getTemplCode());//模板编号
		smsSendHistory.setName(vo.getOperName());
		smsSendHistory.setUsername(vo.getOperUser());
		smsSendHistory.setAgentId(vo.getAgentId());//座席工号
		smsSendHistory.setSubCode(SmsSendHistoryEntity.SUB_CODE_MS);
		return smsSendHistory;
	}

	private void updateIVR(Service10Vo vo, String isSendSuccess) {
		IsTransIvrEntity ivr = new IsTransIvrEntity();
		ivr.copyBaseField(vo);
		ivr.setIsSendSuccess(isSendSuccess);
		ivr.setCustomerCallId(vo.getCustomerCallId());
		ivr.setAgentId(vo.getAgentId());
		ivr.setUsername(vo.getOperUser());
		isTransIvrService.update(ivr);
	}

}
