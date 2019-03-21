package com.kim.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.kim.common.response.MsgCode;
import com.kim.common.response.MsgResponse;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.zuul.common.CommonConstant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 
 * 
 */
@Component
public class AccessSessionFilter extends ZuulFilter {
    private static Logger logger = LoggerFactory.getLogger(AccessSessionFilter.class);
    
    @Value("${zuul.ignore.url:}")
    private String ignoreUrl;
    
//    private PathMatcher matcher = new AntPathMatcher();
    private final PathMatchingResourcePatternResolver matcher = new PathMatchingResourcePatternResolver();
    
    @Override
    public String filterType() {
        /**前置过滤器*/
        return FilterConstants.PRE_TYPE;
    }
    
    @Override
    public int filterOrder() {
        /**优先级，数字越大，优先级越低*/
        return 0;
    }
    
    @Override
    public boolean shouldFilter() {
    	RequestContext ctx = RequestContext.getCurrentContext();
    	HttpServletRequest request = ctx.getRequest();
    	String path = TokenUtil.getPath(request);
    	logger.info("zuul forward path:{}", path);
    	request.setAttribute(CommonConstant.REQUEST_PATH, path);
        //是否执行该过滤器，renturn true代表需要过滤  */
    	if(StringUtil.isBlank(ignoreUrl)){
    		return true;
    	}
        return !matcher.getPathMatcher().match(ignoreUrl, path);
    }
    
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if (request.getCharacterEncoding() == null) {
            try {
                request.setCharacterEncoding("UTF-8");
            } catch (Exception e) {
                logger.error("zuul access session filter 设置编码异常", e);
            }
        }
        // 请求的token认证 */
        if (!checkToken(request)) {
            //过滤该请求，不往下级服务去转发请求，到此结束  */
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.getResponse().setCharacterEncoding("UTF-8");
            ctx.getResponse().setContentType("text/html;charset=UTF-8");
            ctx.setResponseBody(JSONObject.toJSONString(new MsgResponse(MsgCode.UN_LOGIN_IN)));
            return null;
        }
        return null;
    }
    
    /**
     * 校验token 有效性
     * @param request
     * @return true 校验成功，token有效；false失败
     */
    private boolean checkToken(HttpServletRequest request) {
    	String token = TokenUtil.parseToken(request);
        if (StringUtil.isBlank(token)) {
            return true;
        }
        return TokenUtil.sessionExpire(token);
    }
    
}
