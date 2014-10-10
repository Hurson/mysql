package com.dvnchina.advertDelivery.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.dvnchina.advertDelivery.bean.UserReleaseArea.UserReleaseAreaBean;
import com.dvnchina.advertDelivery.constant.PageConstant;
import com.dvnchina.advertDelivery.constant.UserAreaConstant;
import com.dvnchina.advertDelivery.model.UserArea;
import com.dvnchina.advertDelivery.service.ReleaseAreaService;
import com.dvnchina.advertDelivery.service.UserAreaService;
import com.dvnchina.advertDelivery.utils.page.PageBean;
import com.dvnchina.advertDelivery.utils.page.PageUtils;

public class UserAreaAction extends BaseActionSupport<Object>{

	private static final long serialVersionUID = -2617111621400191274L;
	
	private Logger logger = Logger.getLogger(UserAreaAction.class);
	
	private UserArea userArea;
	
	private List<UserArea> listUserArea;
	
	private List<UserReleaseAreaBean> listUserReleaseAreaBean;
	
	private UserAreaService userAreaService;
	
	
	private ReleaseAreaService releaseAreaService;
	
	private String cId;
	
	private UserReleaseAreaBean userReleaseAreaBean;
	
	private PageBean page = new PageBean();
	
	/**
	 * 页号
	 */
	protected int pageNo;


	/**
	 * 删除
	 * 
	 */
	public String deleteUserArea(){
		
		String userId = this.getRequest().getParameter("cId");
		
		int count = 0;
		
		if(userId != null){
			try {
				count = userAreaService.deleteUserAreaByUserId(Integer.parseInt(userId));
			} catch (Exception e) {
				logger.debug("删除记录时发生异常，用户id为：" + id);
			}
		}else{
			logger.debug("----UserArea的id 值 为空 ");
		}
		
		logger.debug("---deleteUserArea()调用完毕------");
		this.returnMessage("删除成功");
		return null;
	}
	
	
	
	/**
	 *  查询结果集
	 * @throws Exception 
	 */
	
	public String list() throws Exception{
		
		int count=0 ,pageNB = 1;
		
		if(userReleaseAreaBean == null){
			userReleaseAreaBean = new UserReleaseAreaBean();
		}
		
		if( null == userArea  ){
			userArea = new UserArea();
		}
		
		String userName = userArea.getUserNamePage();
		if(StringUtils.isNotBlank(userName)){
			this.getRequest().setAttribute("userName",userName);
		}
		
		String releaseAreaName = userArea.getReleaseAreaNamePage();
		if(StringUtils.isNotBlank(releaseAreaName)){
			this.getRequest().setAttribute("releaseAreaName",releaseAreaName);
		}
		
		String userNameStr = this.getRequest().getParameter("userNamePage");
		if(StringUtils.isNotBlank(userNameStr)){
			userNameStr = new String(userNameStr.getBytes("ISO8859-1"),"UTF-8");
		}
		
		String releaseAreaNameStr = this.getRequest().getParameter("releaseAreaNamePage");
		if(StringUtils.isNotBlank(releaseAreaNameStr)){
			releaseAreaNameStr = new String(releaseAreaNameStr.getBytes("IS08859-1"),"UTF-8");
		}
		
		Map conditionMap = new HashMap();
		
		if(StringUtils.isNotBlank(userNameStr)){
			conditionMap.put(UserAreaConstant.USER_NAME, userNameStr);
		}
		
		if(StringUtils.isNotBlank(releaseAreaNameStr)){
			conditionMap.put(UserAreaConstant.RELEASE_AREA,releaseAreaNameStr);
		}
		
		if(StringUtils.isNotBlank(userName)){
			conditionMap.put(UserAreaConstant.USER_NAME, userName);
		}
		
		if(StringUtils.isNotBlank(releaseAreaName)){
			conditionMap.put(UserAreaConstant.RELEASE_AREA,releaseAreaName);
		}
		
		String currentPage = this.getRequest().getParameter("currentPage");
		if (StringUtils.isNotBlank(currentPage)) {
			pageNB = Integer.valueOf(currentPage);//页号
		}
		
		int startIndex = (pageNB - 1) * PageConstant.PAGE_SIZE;//开始位置
		
	//	count = userAreaService.getUserAndReleaseAreaList(conditionMap).size();
		List<UserReleaseAreaBean> list = userAreaService.getUserAndReleaseAreaList(conditionMap);
		
		if(list != null){
			count = list.size();
		}else{
			count = 0;
		}

		listUserReleaseAreaBean = userAreaService.getUserAndReleaseAreaList(conditionMap,startIndex, PageConstant.PAGE_SIZE);
		
		//if(null ==listUserReleaseAreaBean)count = 0;
		
		page = PageUtils.getPageBean2(pageNB, PageConstant.PAGE_SIZE, count,listUserReleaseAreaBean);
		
		if(userNameStr != null){
			this.getRequest().setAttribute("userName",userNameStr);
		}
		
		if(releaseAreaNameStr != null){
			this.getRequest().setAttribute("releaseAreaName",releaseAreaNameStr );
		}
		
		return "list";
	}


	
	/**
	 * 查询结果集
	 * @return
	 */
	public String listAll(){
		
		
		/*if(areaName!= null){
			this.getRequest().setAttribute("AreaName",areaName );
		}
		 
		if(userName!= null){
			this.getRequest().setAttribute("userName", userName);
		}*/
		
		
//		this.getRequest().setAttribute("AreaName",areaName );
//		this.getRequest().setAttribute("userName", userName);
		
		/*int count = userAreaService.getUserAreaCount(userArea,"");
		
		PageBeanDB page = new PageBeanDB(count, pageNo);
		
		listUserArea = userAreaService.listUserAreaMgr(userArea,page.getBegin(), page.getPageSize(), "");
		
		this.getRequest().setAttribute("page", page);*/
		return "list";
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
	
	// AJAX 返回客户端方法
	public void returnMessage(String msg) {
		try {

			logger.debug("returnMessage 被调用");

			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	public UserAreaService getUserAreaService() {
		return userAreaService;
	}
	public void setUserAreaService(UserAreaService userAreaService) {
		this.userAreaService = userAreaService;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public UserArea getUserArea() {
		return userArea;
	}

	public void setUserArea(UserArea userArea) {
		this.userArea = userArea;
	}

	public List<UserArea> getListUserArea() {
		return listUserArea;
	}

	public void setListUserArea(List<UserArea> listUserArea) {
		this.listUserArea = listUserArea;
	}

	public String getCId() {
		return cId;
	}

	public void setCId(String id) {
		cId = id;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public ReleaseAreaService getReleaseAreaService() {
		return releaseAreaService;
	}
	public void setReleaseAreaService(ReleaseAreaService releaseAreaService) {
		this.releaseAreaService = releaseAreaService;
	}
	public List<UserReleaseAreaBean> getListUserReleaseAreaBean() {
		return listUserReleaseAreaBean;
	}
	public void setListUserReleaseAreaBean(
			List<UserReleaseAreaBean> listUserReleaseAreaBean) {
		this.listUserReleaseAreaBean = listUserReleaseAreaBean;
	}
	public UserReleaseAreaBean getUserReleaseAreaBean() {
		return userReleaseAreaBean;
	}
	public void setUserReleaseAreaBean(UserReleaseAreaBean userReleaseAreaBean) {
		this.userReleaseAreaBean = userReleaseAreaBean;
	}



	public PageBean getPage() {
		return page;
	}



	public void setPage(PageBean page) {
		this.page = page;
	}
	
}
