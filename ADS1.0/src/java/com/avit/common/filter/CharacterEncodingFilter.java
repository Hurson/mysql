package com.avit.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Weimmy

 * @date:2011-8-4
 * @version :1.0
 * 
 */
public class CharacterEncodingFilter implements Filter{
	// 编码的字符串 
    protected String encoding = null; 
    
	public void destroy() {
		 this.encoding = null; 
	}

	public void doFilter(ServletRequest request, ServletResponse response, 
            FilterChain chain) throws IOException, ServletException {
		// 如果使用过滤器，忽略客户端的编码，那么使用通过过滤器设定编码 
		if(this.encoding != null){
			request.setCharacterEncoding(this.encoding);
			response.setCharacterEncoding(this.encoding);
		}
        chain.doFilter(request, response); 
		
	}

	public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = filterConfig.getInitParameter("encoding"); 
	}
	
	// 返回过滤器设定的编码 
    protected String selectEncoding(ServletRequest request) { 
        return (this.encoding); 
    }

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	} 
    
    

}
