package com.dvnchina.advertDelivery.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 文字，视频，图片，调查问卷公用Action
 * 包含基本属性的增、删、改、查
 * 页面所有操作都是用ajax方式完成
 * @author lester
 * @param <T>
 */
public class BaseActionSupportCommon<T> extends ActionSupport{
	
	public final Log logger = LogFactory.getLog(getClass());
	
	public Class<T> entityClass;
	
	public BaseActionSupportCommon(){
		ParameterizedType type = (ParameterizedType)getClass().getGenericSuperclass();
		entityClass = (Class<T>)type.getActualTypeArguments()[0];
	}
	
	/**
	 * render Text
	 */
	public void renderText(String content) {
		PrintWriter out = null;
		try {
			out = this.getWriter("text/plain", "UTF-8");
			out.print(content);
			out.flush();
		} catch (IOException e) {
			logger.error("输出提示信息时出现异常",e);
		}finally{
			if(out!=null){
				out.close();
			}
		}
	}

	/**
	 * render HTML
	 */
	public void renderHtml(String content) {
		PrintWriter out  = null;
		try {
			out = this.getWriter("text/xml", "UTF-8");
			out.print(content);
			out.flush();
		} catch (IOException e) {
			logger.error("输出提示信息时出现异常",e);
		} finally{
			if (out!=null) {
				out.close();
			}
		}
	}

	/**
	 * render XML
	 */
	public void renderXML(String content) {
		PrintWriter out  = null;
		try {
			out =this.getWriter("text/xml", "UTF-8");
			out.print(content);
			out.flush();
		} catch (IOException e) {
			logger.error("输出提示信息时出现异常",e);
		}finally{
			if (out!=null) {
				out.close();
			}
		}
	}

	/**
	 * render json
	 */
	public void renderJson(String content) {
		
		PrintWriter out = null;
		
		try {
			out = this.getWriter("text/json","UTF-8");
			out.print(content);
			out.flush();
		} catch (IOException e) {
			logger.error("输出提示信息时出现异常",e);
		}finally{
			if (out!=null) {
				out.close();
			}
		}
	}
	
	public PrintWriter getWriter(String contentType,String encoding) throws IOException{
		   HttpServletResponse response = ServletActionContext.getResponse();
		   response.setContentType(contentType);
		   response.setCharacterEncoding(encoding);
		   PrintWriter out = response.getWriter();
		   return out;
	}

	/**
	 * 取得request
	 * @return
	 */
	public HttpServletRequest getRequest(){
		HttpServletRequest request = ServletActionContext.getRequest();
		return request;
	}
	
	/**
	 * 获取session
	 * @return
	 */
	public HttpSession getSession(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		return session;
	}
	
	/**
	 * get project realPath
	 */
	public String getRealPath() {
		HttpServletRequest request = (HttpServletRequest) ServletActionContext.getRequest();
		String path = request.getSession().getServletContext().getRealPath("/");
		return path;
	}
	
	/**
	 * 获取Response
	 * @return
	 */
	public HttpServletResponse getResponse(){
		HttpServletResponse response = ServletActionContext.getResponse();
		return response;
	}
	
}
