package com.avit.ads.requestads.action;

import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.avit.ads.requestads.service.ADPlayListService;
import com.avit.ads.xml.HttpClientUtil;
import com.avit.ads.xml.JaxbXmlObjectConvertor;
import com.avit.ads.xml.beans.ServerResponse;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("BasePackage")
@Namespace("/interface")
@Controller
public class AdReqAction extends ActionSupport {
	private static final long serialVersionUID = UUID.randomUUID()
			.getMostSignificantBits();
	static Logger logger = LoggerFactory.getLogger(AdReqAction.class);
	@Inject
	ADPlayListService aDPlayListService;

	public ADPlayListService getaDPlayListService() {
		return aDPlayListService;
	}

	public void setaDPlayListService(ADPlayListService aDPlayListService) {
		this.aDPlayListService = aDPlayListService;
	}

	/**
	 *  对于gateway发送到策略子系统的请求（XML）接收，并处理后返回播出单列表
	 */
	@Action(value = "AdInsertReq")
	public void AdInsertReq() {
		
		String responseXml = "";
		JaxbXmlObjectConvertor helper = JaxbXmlObjectConvertor.getInstance();
		try {

			HttpServletRequest request = ServletActionContext.getRequest();
			request.setCharacterEncoding("UTF-8");
			String requestXml = HttpClientUtil.getRequestContent(request);
			// 使用请求的XML处理生成 播出单列表
			responseXml = aDPlayListService.GenerateADPlayList(requestXml);
			if("".equals(responseXml)){
				ServerResponse response = new ServerResponse();
				response.setCode(1001);
				response.setMessage("ResponseCodeMessage.invalid_request_message");
				responseXml = helper.toXML(response);
			}
			// 将得到的响应XML写入response
			this.print(responseXml);
			logger.info("对请求给出响应");
		} catch (IOException e) {
			logger.error(e.toString());
			ServerResponse response = new ServerResponse();
			response.setCode(6001);
			response.setMessage("ResponseCodeMessage.unknown_error_message");
			responseXml = helper.toXML(response);
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
		try {
			logger.info("发起处理广告投放报告的请求");
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setCharacterEncoding("UTF-8");
			String requestXml = HttpClientUtil.getRequestContent(request);
			// 保存投放结果
			boolean success = aDPlayListService.StartReportRequest(requestXml);
			if(success){
				ServerResponse response = new ServerResponse();
				response.setCode(0);
				response.setMessage("ResponseCodeMessage.succeed_message");
				responseXml = helper.toXML(response);
				this.print(responseXml);
				logger.info("保存成功");
			}else {
				ServerResponse response = new ServerResponse();
				response.setCode(1011);
				response.setMessage("ResponseCodeMessage.database_access_message");
				responseXml = helper.toXML(response);
				this.print(responseXml);
				logger.error("保存失败");
			}
		} catch (Exception e){
			
		}
	}

	/**
	 * 将xml内容写入对应的response中
	 * @param str 存有播出单列表的xml字符串
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