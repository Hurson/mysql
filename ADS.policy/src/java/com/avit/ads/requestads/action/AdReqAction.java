package com.avit.ads.requestads.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.avit.ads.requestads.bean.AdQuestionnaireData;
import com.avit.ads.requestads.bean.Question;
import com.avit.ads.requestads.bean.request.AdReportList;
import com.avit.ads.requestads.bean.request.AdStatus;
import com.avit.ads.requestads.bean.request.AdStatusReportReqXmlBean;
import com.avit.ads.requestads.service.ADPlayListService;
import com.avit.ads.requestads.service.AdrequestProcessService;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.xml.HttpClientUtil;
import com.avit.ads.xml.JaxbXmlObjectConvertor;
import com.avit.ads.xml.beans.ServerResponse;
import com.avit.common.util.CookieUtils;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("BasePackage")
@Namespace("/interface")
@Controller
public class AdReqAction extends ActionSupport {
	private static final long serialVersionUID = UUID.randomUUID()
			.getMostSignificantBits();
	static Logger logger = LoggerFactory.getLogger(AdReqAction.class);
//	@Inject
//	ADPlayListService aDPlayListService;
	@Inject
	AdrequestProcessService adrequestProcessService; 
//	public ADPlayListService getaDPlayListService() {
//		return aDPlayListService;
//	}
//
//	public void setaDPlayListService(ADPlayListService aDPlayListService) {
//		this.aDPlayListService = aDPlayListService;
//	}

	/**
	 *  对于gateway发送到策略子系统的请求（XML）接收，并处理后返回播出单列表
	 */
	@SuppressWarnings("unused")
	@Action(value = "AdInsertReq")
	public void AdInsertReq() {
		logger.info("coming AdReqAction AdInsertReq!");
		String responseXml = "";
		JaxbXmlObjectConvertor helper = JaxbXmlObjectConvertor.getInstance();
		try {
			
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setCharacterEncoding("UTF-8");
			String requestXml = HttpClientUtil.getRequestContent(request);
			logger.info("requestXml is:"+requestXml);
			// 使用请求的XML处理生成 播出单列表
			//responseXml = aDPlayListService.GenerateADPlayList(requestXml);
			responseXml = adrequestProcessService.GenerateADPlayList(requestXml);
			
			if(responseXml.isEmpty() || responseXml == null){
				ServerResponse response = new ServerResponse();
				response.setCode(1001);
				response.setMessage(super.getText("invalid_request_message"));
				responseXml = helper.toXML(response);
			}
			// 将得到的响应XML写入response\
			logger.info("responseXml is:"+responseXml);
			this.print(responseXml);
		} catch (IOException e) {
			logger.error(super.getText("read.http.request.content.fail"));
			logger.error(e.getMessage());
			ServerResponse response = new ServerResponse();
			response.setCode(6001);
			response.setMessage(super.getText("read.http.request.content.fail"));
			responseXml = helper.toXML(response);
			logger.info("responseXml is:"+responseXml);
			this.print(responseXml);
		}
	}
	
	/**
	 *  处理广告投放情况报告
	 *  
	 */
	@Action(value = "AdStatusReportReq")
	public void AdStatusReportReq() {
		String responseXml = "";
		JaxbXmlObjectConvertor helper = JaxbXmlObjectConvertor.getInstance();
			HttpServletRequest request = ServletActionContext.getRequest();
			try {
			request.setCharacterEncoding("UTF-8");
			String requestXml;
			//requestXml = HttpClientUtil.getRequestContent(request);
			
//			AdStatusReportReqXmlBean tt = new AdStatusReportReqXmlBean();
//			AdReportList adReportList=new AdReportList();
//			List ttemp =  new ArrayList();
//			
//			AdStatus ads = new AdStatus();
//			ads.setSeq("1");
//			ads.setStatus(1);
//			ttemp.add(ads);
//			adReportList.setAdStatus(ttemp);
//			tt.setAdReportList(adReportList);
//			tt.setPausePic(1);
//			tt.setRightTopPicStatus(1);
//			tt.setSubTitleStaus(1);
//			tt.setTvnId("tvn");
//			tt.setToken("to");
//			
//			//	String report = request.getParameter("report");
//			AdStatusReportReqXmlBean ttt=(AdStatusReportReqXmlBean) JaxbXmlObjectConvertor.getInstance().fromXML(requestXml);
			String report= request.getParameter("report");
			logger.info(report);
			// 保存投放结果
			boolean success = adrequestProcessService.StartReportRequest(report);
			if(success){
				ServerResponse response = new ServerResponse();
				response.setCode(ConstantsHelper.SUCCESSFUL_MESSAGE_CODE);
				response.setMessage(super.getText("save.successfully"));
				responseXml = helper.toXML(response);
				this.print(responseXml);
			}else {
				ServerResponse response = new ServerResponse();
				response.setCode(1011);
				response.setMessage(super.getText("save.fail"));
				responseXml = helper.toXML(response);
				this.print(responseXml);
				logger.error(super.getText("save.fail"));
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(super.getText("invalid_request_message"));
		} catch (Exception e) {
			logger.error(super.getText("read.http.request.content.fail"));
			ServerResponse response = new ServerResponse();
			response.setCode(6001);
			response.setMessage(super.getText("read.http.request.content.fail"));
			responseXml = helper.toXML(response);
			this.print(responseXml);
		}
	}
	/**
	 * 插播的广告列表转换成json
	 * @param list 插播的广告列表
	 * @return json json格式的广告列表
	 */
//	private String adListToJson(List<InsertedAd> list) {
//		if(list != null && list.size() > 0){
//			StringBuffer sb = new StringBuffer();
//			sb.append("[");
//			for (int i = 0, j = list.size(); i < j; i++) {
//				
//			 
//				InsertedAd insertedAd = list.get(i);
//				sb.append("{").append("\"adSeq\":").append(insertedAd.getAdSeq()).append(",");
//				sb.append("\"adUrl\":\"").append(insertedAd.getAdUrl()).append("\",");
//				sb.append("\"insertedTime\":").append(insertedAd.getAdUrl()).append("}");
//				if(j > i+1){
//					sb.append(",");
//				}
//			}
//			sb.append("]");
//			return sb.toString();
//		}
//		return "";
//	}
	
	/**
	 * 将内容写入对应的response中
	 * @param str 存有播出单列表的字符串
	 */
	private void print(String str) {
		try {
			ServletActionContext.getResponse().setCharacterEncoding("utf-8");
			ServletActionContext.getResponse().setContentType(
					"text/xml;charset=utf-8");
			ServletActionContext.getResponse().setHeader("Cache-Control",
					"no-cache");
			ServletActionContext.getResponse().getOutputStream()
					.write(str.getBytes("utf-8"));
		} catch (Exception e) {
			System.out.println("send response error");
		}
		
	}
}