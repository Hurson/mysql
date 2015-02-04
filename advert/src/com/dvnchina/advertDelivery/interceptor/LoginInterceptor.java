package com.dvnchina.advertDelivery.interceptor;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 
 * 登录拦截器，可以拦截ads后台管理系统的所有struts的Action动作。
 * 
 */
public class LoginInterceptor implements Interceptor {
    
	private static final long serialVersionUID = 8317600420657773448L;
	private Logger logger = Logger.getLogger(LoginInterceptor.class);
	
	/**
     * 应用启动的时候，初始化拦截器
     */
    public void init(){
    	
    }
    
    /**
     * 拦截器对Action进行拦截。
     * 
     * 1、从Session中获取用户的登录对象，如果登录对象为空，直接执行拦截器后，返回登录页面。
     */
    @Override
    public String intercept(ActionInvocation invoke) throws Exception{
        try
        {
	        Map<String, Object> session = ServletActionContext.getContext()
	                .getSession();
	        Object obj = session.get("USER_LOGIN_INFO");
	        if (obj == null){
	        	return Action.LOGIN;
	        }
	        
	        return invoke.invoke();
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	logger.error(e);
        	throw e;
        }
    }

    public void destroy() {
		
	}
}
