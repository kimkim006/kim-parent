package com.kim.log.aop;


import com.kim.log.interceptor.OperateLogHandlerUtil;
import com.kim.log.interceptor.ParamSet;
import com.kim.log.operatelog.OperationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kim.common.base.BaseTable;
import com.kim.common.util.DateUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;

public class CurrentUserArgumentResolver {

	private static final Logger logger = LoggerFactory.getLogger(CurrentUserArgumentResolver.class);

	private static boolean check(ParamSet paramSet){
		if (paramSet == null) {
			logger.warn("未启用OperateLog注解");
			return false;
		}
		if (!(paramSet.isInjectCurntTenant() || paramSet.isInjectCurntDate() || paramSet.isInjectCurntUser())) {
			return false;
		}
		return true;
	}
	
	public static void inject(Object[] args) {
		if(args == null || args.length == 0){
			return ;
		}
		ParamSet paramSet = OperateLogHandlerUtil.getCurrentParamSet();

		if(!check(paramSet)){
			return;
		}
		
		logger.debug("注入当前用户所在的租户、当前用户、当前时间");
		for (Object param : args) {
			if(param instanceof BaseTable){
				BaseTable pm = (BaseTable)param;
				if(paramSet.isInjectCurntTenant()){
					String tenantId = TokenUtil.getTenantId();
					if(StringUtil.isNotBlank(tenantId )){
						pm.setTenantId(tenantId);
					}
				}
				if (paramSet.isInjectCurntDate() && needInject(paramSet)) {
					pm.setOperTime(DateUtil.getCurrentTime());
				}
				if (paramSet.isInjectCurntUser() && needInject(paramSet)) {
					pm.setOperUser(TokenUtil.getUsername());
					pm.setOperName(TokenUtil.getCurrentName());
				}
			}
		}
	}

	private static boolean needInject(ParamSet paramSet) {
		return paramSet.getOperType() != OperationType.QUERY;
	}
}
