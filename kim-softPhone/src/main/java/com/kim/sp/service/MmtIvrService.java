package com.kim.sp.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kim.sp.common.CommonConstant;
import com.kim.sp.vo.MmtContractVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.common.page.PageRes;
import com.kim.common.page.PageRet;
import com.kim.common.page.PageVo;
import com.kim.common.util.HttpClientUtil;
import com.kim.common.util.StringUtil;
import com.kim.sp.entity.MmtContract;

@Service
public class MmtIvrService extends BaseService {

	public PageRet<MmtContract> contractList(MmtContractVo vo) {
		String idCard = vo.getIdCard();
		String contractNo = vo.getContractNo();
		if(StringUtils.isBlank(idCard) && StringUtils.isBlank(contractNo)){
			return new PageRet<>();
		}
		//去掉身份证号码的*号
		if (StringUtil.isNotBlank(idCard) && idCard.contains(CommonConstant.ID_CORD_XH)) {
			idCard = idCard.substring(0, idCard.length() - 1) + CommonConstant.ID_CORD_SUFFIX_X;
		}
		// mobile 预留手机号码 也可以查询
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("certId", idCard);
		paramMap.put("contractNo", contractNo);
		PageVo page = vo.getPage();
		paramMap.put("pageNo", page.getCurPage());
		paramMap.put("pageSize", page.getPageSize());
		paramMap.put("systemCode", MmtContract.SYSTEM_CODE_ZORE);
		paramMap.put("serviceId", CommonConstant.SERVICEID_CONTRACT_LIST_QUERY);
		String url = BaseCacheUtil.getParam(CommonConstant.PARAM_MMT_URL, vo.getTenantId());
		if(StringUtil.isBlank(url)){
			logger.error("未配置买买提URL，无法查询合同列表, tenantId:{}", vo.getTenantId());
			throw new BusinessException("未配置买买提URL，无法查询合同列表");
		}
		PageRes<MmtContract> pageRes = new PageRes<>(page);
		logger.info("---------- 合同列表查询开始, param:{}", JSON.toJSONString(paramMap));
		JSONObject result;
		try {
			result = HttpClientUtil.post(url, paramMap);
		} catch (IOException e) {
			logger.error("---------- 合同列表查询异常", e);
			throw new BusinessException("合同列表查询异常");
		}
		logger.info("---------- 合同列表查询结束, result:{}", JSON.toJSONString(result));
		String bizData = result.getString("bizData");
		if(StringUtil.isBlank(bizData)){
			logger.info("合同查询接口返回合同数据为空, tenantId:{}", vo.getTenantId());
		}
		List<MmtContract> contractList = JSONArray.parseArray(bizData, MmtContract.class);
		pageRes.setTotalSize(contractList.size());
		pageRes.setList(contractList);
		return PageRet.convert(pageRes);
	}
	
	

}
