package com.dvnchina.advertDelivery.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.model.ChannelInfo;
import com.dvnchina.advertDelivery.service.ChannelInfoService;

/**
 * 为广告位提供频道信息
 * 
 * @author lester
 */
public class ChannelInfoAction extends BaseActionSupport<Object> {

	private static final long serialVersionUID = -5835114479463751813L;

	private static Logger logger = Logger.getLogger(ChannelInfoAction.class);
	
	private List<ChannelInfo> listChannelInfo;
	private ChannelInfo channelInfo;
	private ChannelInfoService channelInfoService;
	
	/**
	 * 页号
	 */
	protected int pageNo;
	
	
	
	/**
	 * 删除记录
	 */
	
	public String deleteChannelInfo(){
		
		int count = 0;
		
		String id = this.getRequest().getParameter("cId");
		
		
		if(id != null){
			try {
				count = channelInfoService.deleteChannelInfoById(Integer.parseInt(id));
			} catch (Exception e) {
				logger.debug("删除记录时发生异常，用户id为：" + id);
			}
		}else{
			logger.debug("----channelInof的id 值 为空 ");
		}
		
		logger.debug("---deleteChannelInfo()调用完毕------");
		
		this.returnMessage("删除成功");
		return null;
	}
	
	/**
	 * 查询结果集
	 * @throws Exception 
	 */
	//     
	public String list() throws Exception{
		
		if(channelInfo == null){
			channelInfo = new ChannelInfo();
		}
		
		
		String channelNameStr = channelInfo.getChannelName();
		if(StringUtils.isNotBlank(channelNameStr)){
			channelInfo.setChannelName(channelNameStr);
			this.getRequest().setAttribute("channelName",channelNameStr);
		}
		
		String cName = this.getRequest().getParameter("channelName");
		if(StringUtils.isNotBlank(cName)){
			cName = new String(cName.getBytes("ISO8859-1"),"UTF-8");
			channelInfo.setChannelName(cName);
		//	this.getRequest().setAttribute("channelName",cName);
		}
		
		String channelTypeStr = channelInfo.getChannelType();
		if(StringUtils.isNotBlank(channelTypeStr)){
			channelInfo.setChannelType(channelTypeStr);
			this.getRequest().setAttribute("channelType",channelTypeStr);
		}
		String sChannelType = this.getRequest().getParameter("channelType");
		if(StringUtils.isNotBlank(channelTypeStr)){
			channelInfo.setChannelType(sChannelType);
		}

		String locationNameStr = channelInfo.getLocationName();
		if(StringUtils.isNotBlank(locationNameStr)){
			channelInfo.setLocationName(locationNameStr);
			this.getRequest().setAttribute("locationName",locationNameStr);
		}
		String cLocationName = this.getRequest().getParameter("locationName");
		if(StringUtils.isNotBlank(cLocationName)){
			cLocationName = new String(cLocationName.getBytes("ISO8859-1"),"UTF-8");
			channelInfo.setLocationName(cLocationName);
		}
		
		String locationCodeStr = channelInfo.getLocationCode();
		if(StringUtils.isNotBlank(locationCodeStr)){
			channelInfo.setLocationCode(locationCodeStr);
			this.getRequest().setAttribute("locationCode",locationCodeStr);
		}
		String cLocationCode = this.getRequest().getParameter("locationCode");
		if(StringUtils.isNotBlank(cLocationCode)){
			cLocationCode = new String(cLocationCode.getBytes("ISO8859-1"),"UTF-8");
			channelInfo.setLocationCode(cLocationCode);
		}
		
		String infoStateStr = channelInfo.getInfoState();
		if(StringUtils.isNotBlank(infoStateStr)){
			channelInfo.setInfoState(infoStateStr);
			this.getRequest().setAttribute("infoState",infoStateStr);
		}
		String cInfoStateStr = this.getRequest().getParameter("infoState");
		if(StringUtils.isNotBlank(cInfoStateStr)){
			channelInfo.setInfoState(cInfoStateStr);
		}
		
		int count = channelInfoService.getChannelInfoCount(channelInfo,"");
		
		PageBeanDB page = new PageBeanDB(count, pageNo);
		
		String  str = channelInfo.getChannelType();
		
		listChannelInfo = channelInfoService.listChannelInfoMgr(channelInfo,page.getBegin(), page.getPageSize(), "");
		
		if(cName != null){
			this.getRequest().setAttribute("channelName",cName);
		}
		
		if(sChannelType != null){
			this.getRequest().setAttribute("channelType",sChannelType);
		}
		if(cLocationName != null){
			this.getRequest().setAttribute("locationName",cLocationName );
		}
		
		if(cLocationCode != null){
			this.getRequest().setAttribute("locationCode",cLocationCode);
		}
		
		if(cInfoStateStr != null){
			this.getRequest().setAttribute("infoStateStr",cInfoStateStr);
		}
		
		this.getRequest().setAttribute("page", page);
		
		return "list";
	}
	
	/**
	 * 
	 */
	private String locationNameFromAddPositionQueryChannel;
	/**
	 * 
	 */
	private String channelNameFromAddPositionQueryChannel;

	/**
	 * 获取频道和地区的关系
	 * 
	 * @return
	 */
	public String getChannelAndLocationInfo() {
		String channelLocationInfo = "{'result':'success','currentPage':'1','totalPage':'1','channelList':[{'channelId':'1','channelName':'CCTV1','locationId':'01','locationName':'郑州'},{'channelId':'1','channelName':'CCTV1','locationId':'02','locationName':'开封'},{'channelId':'1','channelName':'CCTV1','locationId':'03','locationName':'洛阳'},{'channelId':'1','channelName':'CCTV1','locationId':'04','locationName':'平顶山'},{'channelId':'1','channelName':'CCTV1','locationId':'01','locationName':'安阳'}]}";
		renderText(channelLocationInfo);
		return null;
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
	
	public String getLocationNameFromAddPositionQueryChannel() {
		return locationNameFromAddPositionQueryChannel;
	}

	public void setLocationNameFromAddPositionQueryChannel(
			String locationNameFromAddPositionQueryChannel) {
		this.locationNameFromAddPositionQueryChannel = locationNameFromAddPositionQueryChannel;
	}

	public String getChannelNameFromAddPositionQueryChannel() {
		return channelNameFromAddPositionQueryChannel;
	}

	public void setChannelNameFromAddPositionQueryChannel(
			String channelNameFromAddPositionQueryChannel) {
		this.channelNameFromAddPositionQueryChannel = channelNameFromAddPositionQueryChannel;
	}

	public List<ChannelInfo> getListChannelInfo() {
		return listChannelInfo;
	}

	public void setListChannelInfo(List<ChannelInfo> listChannelInfo) {
		this.listChannelInfo = listChannelInfo;
	}

	public ChannelInfo getChannelInfo() {
		return channelInfo;
	}

	public void setChannelInfo(ChannelInfo channelInfo) {
		this.channelInfo = channelInfo;
	}

	public ChannelInfoService getChannelInfoService() {
		return channelInfoService;
	}

	public void setChannelInfoService(ChannelInfoService channelInfoService) {
		this.channelInfoService = channelInfoService;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
}
