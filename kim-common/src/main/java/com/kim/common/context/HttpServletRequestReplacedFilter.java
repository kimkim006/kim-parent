package com.kim.common.context;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author bo.liu01
 *
 */
@Order(1)
@Component
@WebFilter(filterName = "httpServletRequestReplacedFilter", urlPatterns = "/*")
public class HttpServletRequestReplacedFilter implements Filter {  
  
    @Override  
    public void destroy() {  
    }  
  
    @Override  
    public void doFilter(ServletRequest request, ServletResponse response,  
            FilterChain chain) throws IOException, ServletException {  
    	
    	try {
    		if(request instanceof HttpServletRequest) {    
    			HttpServletRequest requestMultiReadWrapper = new MultiReadHttpServletRequestWrapper((HttpServletRequest) request);
				ContextHolder.initContext(requestMultiReadWrapper, response);
			    chain.doFilter(requestMultiReadWrapper, response);    
			}else{
				chain.doFilter(request, response);    
			}
		} finally {
			destroyRequestContext();
		}
    } 
    
    private void destroyRequestContext() {
    	ContextHolder.clearCurrentContext();
    }
  
    @Override  
    public void init(FilterConfig arg0) throws ServletException {  
          
    }  
  
}  