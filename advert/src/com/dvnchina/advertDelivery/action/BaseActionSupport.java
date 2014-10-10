package com.dvnchina.advertDelivery.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.utils.GenericsUtil;
import com.dvnchina.advertDelivery.utils.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

public class BaseActionSupport<T> extends ActionSupport{
	
	private static final long serialVersionUID = 1510137542622373146L;
	
	protected final Log log = LogFactory.getLog(getClass());
	
	protected final static String SPE = File.separator;
	
	/**
	 * id主键
	 */
	protected Integer id;
	
	/**
	 * 多个id字符串，以"逗号"分割
	 */
	protected String ids;
	
	/**
	 * 工程的contextPath
	 */
	protected String contextPath;
	
	/**
	 * extJs的页号
	 */
	protected int start;
	
	/**
	 * extJs的分页记录
	 */
	protected int limit;
	
	/**
	 * 页号
	 */
	protected int pageNo;
	
	/**
	 * 每页记录数
	 */
	protected int pageSize;
	
	/**
	 * 表格查询参数
	 */
	protected String query;
	
	protected String message;
	
	
	/**
	 * json格式数据
	 */
	protected String dataJson;
	
	
	@SuppressWarnings("unchecked")
	protected Class clazz;

	/** 操作结果消息 */
	//protected String message;
	/** 操作类型 */
	public String operType;
	/** 操作结果 */
	public int operResult = Constant.OPERATE_SECCESS;
	/** 操作详情 */
	public String operInfo;
	
	@SuppressWarnings("unchecked")
	protected Class getClazz() {
		return clazz;
	}
	
	@SuppressWarnings("unchecked")
	public BaseActionSupport() {
		Class genericClass = GenericsUtil.getGenericClass(getClass());
		clazz = genericClass;
	}
	
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
	 * render XML
	 */
	public void renderXML(String content) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/xml");
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
	
	public PrintWriter getWriter(String encoding) throws IOException{
		   HttpServletResponse response = ServletActionContext.getResponse();
		   response.setContentType("text/html");
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
	 * 取得MultipartRequest
	 * 上传文件使用
	 * @return
	 */
	public MultiPartRequestWrapper getMutipartRequest(){
		MultiPartRequestWrapper mulRequest = (MultiPartRequestWrapper) ServletActionContext.getRequest();
		return mulRequest;
	}
	
	/**
	 * get userName from session
	 */
	public static String getUserNameFromSesssion() {
		String name = (String) ServletActionContext.getRequest().getSession().getAttribute("username");
		return name;
		
	}
	
	/**
	 * @param address 跳转地址
	 * @param message 提示信息
	 * @return
	 */
	public String generateMessage(String address,String message){
		StringBuffer result = new StringBuffer();
		result.append("<script>");
		result.append("window.location.href='");
		result.append(address);
		result.append("';");
		result.append("alert('");
		result.append(message);
		result.append("')");
		result.append("</script>");
		return result.toString();
	}
	
	public String generateMessage(String message){
		StringBuffer result = new StringBuffer();
		result.append("<script>");
		result.append("alert('");
		result.append(message);
		result.append("')");
		result.append("</script>");
		return result.toString();
	}
	
	/**
	 * get conf from configuration.properties
	 */
	public static String getPropertiesValue(String key) {
//		PropertiesUtil.init("configuration.properties");
//		String value = PropertiesUtil.getProperty(key,"");
//		return value;
		return null;
	}
	
	/**
	 * 页面跳转
	 */
	public String forWard() throws Exception {
		return SUCCESS;
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
	
	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getDataJson() {
		return dataJson;
	}

	public void setDataJson(String dataJson) {
		this.dataJson = dataJson;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
	 * 获取操作员ID
	 * @return
	 */
	public Integer getUserId(){
		return Integer.valueOf((String)getSession().getAttribute("userId"));
	}
}
