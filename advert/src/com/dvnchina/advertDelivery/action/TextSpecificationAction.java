package com.dvnchina.advertDelivery.action;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.dvnchina.advertDelivery.model.TextSpecification;
import com.dvnchina.advertDelivery.service.TextSpecificationService;
import com.dvnchina.advertDelivery.utils.page.PageBean;

public class TextSpecificationAction extends BaseActionSupport<Object>{
	
	private static final long serialVersionUID = 7898196280791183998L;

	private TextSpecificationService textSpecificationService;
	
	private TextSpecification object;
	
	public TextSpecification getObject() {
		return object;
	}

	public void setObject(TextSpecification object) {
		this.object = object;
	}

	public TextSpecificationService getTextSpecificationService() {
		return textSpecificationService;
	}

	public void setTextSpecificationService(
			TextSpecificationService textSpecificationService) {
		this.textSpecificationService = textSpecificationService;
	}

	@SuppressWarnings("unchecked")
	public String save(){
		try {
			Map resultMap = textSpecificationService.save(object);
			this.renderHtml(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			
		}
		return null;
	}
	
	public String query(){
		try {
			HttpServletRequest request = getRequest();
			String isHd = request.getParameter("isHd");
			request.setAttribute("isHd", isHd);
			String sdAndhd = request.getParameter("sdAndhd");
			request.setAttribute("sdAndhd", sdAndhd);
			PageBean pageBean = textSpecificationService.page(request);
			request.setAttribute("objectList", pageBean.getList());
			request.setAttribute("page", pageBean);
		} catch (Exception e) {
			
		}
		return SUCCESS;
	}
	
	public String queryManager(){
		try {
			HttpServletRequest request = getRequest();
			String isHd = request.getParameter("isHd");
			request.setAttribute("isHd", isHd);
			String sdAndhd = request.getParameter("sdAndhd");
			request.setAttribute("sdAndhd", sdAndhd);
			PageBean pageBean = textSpecificationService.page(request);
			request.setAttribute("objectList", pageBean.getList());
			request.setAttribute("page", pageBean);
		} catch (Exception e) {
			
		}
		return SUCCESS;
	}
	
	public String add() {
		HttpServletRequest request = this.getRequest();

		if (request.getParameter("id") != null
				&& !"".equals(request.getParameter("id"))) {
			request.setAttribute("operate", "edit");
			
			String id = request.getParameter("id");			
			String textDesc = "";
			String textLength = request.getParameter("textLength");
			String isLink = request.getParameter("isLink");
			
			try {
				textDesc = new String(request.getParameter("textDesc").getBytes("ISO-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("转码异常",e);
			}
			
			TextSpecification object = new TextSpecification();
			
			object.setId(new Integer(id));
			object.setTextDesc(textDesc);
			object.setTextLength(textLength);
			object.setIsLink(new Integer(isLink));
			
			request.setAttribute("object", object);
			
			
		} else {
			request.setAttribute("operate", "add");
		}
		return SUCCESS;
	}
	
	/**
	 * 删除广告位
	 * @return
	 */
	public String remove(){
		boolean flag = false;
		try {
			flag = textSpecificationService.remove(object.getId());
			//logger.info("删除结果"+flag);
		} catch (Exception e) {
			//logger.error("删除数据时出现异常",e);
		}
		return SUCCESS;
	}
}
