package com.dvnchina.advertDelivery.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.model.UserLogin;
import com.dvnchina.advertDelivery.utils.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport{
	
	private static final long serialVersionUID = 5696983661483871363L;

	protected final Log log = LogFactory.getLog(getClass());
	
	/** id主键 */
	protected Integer id;
	
	/** 多个id字符串，以"逗号"分割 */
	protected String ids;
	/** 操作结果消息 */
	protected String message;
	/** 操作类型 */
	public String operType;
	/** 操作结果 */
	public int operResult = Constant.OPERATE_SECCESS;
	/** 操作详情 */
	public String operInfo;
	
	/**
	 * render Text
	 */
	public void renderText(String content) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(content);
			out.flush();
			out.close();
			
		} catch (IOException e) {
			log.error(e);
		}
	}
	
	/**
	 * render json
	 */
	public void renderJson(String content) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(content);
			out.flush();
			out.close();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * render HTML
	 */
	public void renderHtml(String content) {
		HttpServletResponse response = null;
		PrintWriter out  = null;
		try {
			response = ServletActionContext.getResponse();
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			out.print(content);
			out.flush();
		} catch (IOException e) {
			log.error("输出提示信息时出现异常",e);
		} finally{
			if (out!=null) {
				out.close();
			}
		}
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
	 * 获取操作员ID
	 * @return
	 */
	public Integer getUserId(){
		return Integer.valueOf((String)getSession().getAttribute("userId"));
	}
	
	/**
	 * 获取登录用户信息
	 * @return
	 */
	public UserLogin getLoginUser(){
		return (UserLogin)getSession().getAttribute("USER_LOGIN_INFO");
	}
	
	public OperateLog setOperationLog(String moduleName){
		OperateLog opLog = new OperateLog();
		opLog.setUserId(getUserId());
		opLog.setModuleName(moduleName);
		opLog.setOperateType(operType);
		opLog.setOperateResult(operResult);
		opLog.setOperateIP(getRequest().getRemoteAddr());
		opLog.setOperateInfo(StringUtil.ellipsis(operInfo, 1000));
		return opLog;
	}
	
	/**
	 * 检查会话是否过期
	 */
	public void checkSession(){
		Object obj = getSession().getAttribute("userId");
        if (obj == null){
        	renderText("1");
        }
		renderText("0");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
