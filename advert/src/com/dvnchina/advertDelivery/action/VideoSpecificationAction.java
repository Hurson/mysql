package com.dvnchina.advertDelivery.action;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.dvnchina.advertDelivery.model.VideoSpecification;
import com.dvnchina.advertDelivery.service.VideoSpecificationService;
import com.dvnchina.advertDelivery.utils.page.PageBean;

public class VideoSpecificationAction extends BaseActionSupport<Object>{
	
	private static Logger logger = Logger.getLogger(VideoSpecificationAction.class);
	
	private static final long serialVersionUID = -3139114460574833376L;
	
	private VideoSpecificationService videoSpecificationService;
	
	private VideoSpecification object;
	
	public VideoSpecificationService getVideoSpecificationService() {
		return videoSpecificationService;
	}

	public void setVideoSpecificationService(
			VideoSpecificationService videoSpecificationService) {
		this.videoSpecificationService = videoSpecificationService;
	}

	@SuppressWarnings("unchecked")
	public String save(){
		Map resultMap = null;
		String json = null;
		try {
			resultMap = videoSpecificationService.save(object);
			json = JSON.toJSONString(resultMap);
		} catch (Exception e) {
			logger.error("保存失败",e);
		}
//		this.renderHtml(json);
		return SUCCESS;
	}
	
	public String query(){
		HttpServletRequest request = this.getRequest();
		
		String isHd = request.getParameter("isHd");
		request.setAttribute("isHd",isHd);
		
		String sdAndhd = request.getParameter("sdAndhd");
		request.setAttribute("sdAndhd",sdAndhd);
		
		try {
			PageBean pageBean = videoSpecificationService.page(request);
			request.setAttribute("objectList", pageBean.getList());
			request.setAttribute("page", pageBean);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return SUCCESS;
	}
	
	public String queryManager(){
		HttpServletRequest request = this.getRequest();
		
		String isHd = request.getParameter("isHd");
		request.setAttribute("isHd",isHd);
		
		String sdAndhd = request.getParameter("sdAndhd");
		request.setAttribute("sdAndhd",sdAndhd);
		
		try {
			PageBean pageBean = videoSpecificationService.page(request);
			request.setAttribute("objectList", pageBean.getList());
			request.setAttribute("page", pageBean);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return SUCCESS;
	}
	
	public String add(){
		HttpServletRequest request = this.getRequest();
		
		if (request.getParameter("id") != null
				&& !"".equals(request.getParameter("id"))) {
			request.setAttribute("operate", "edit");
			
			String id = request.getParameter("id");
			String movieDesc ="";
			String resolution = request.getParameter("resolution");
			String duration = request.getParameter("duration");
			String fileSize = request.getParameter("fileSize");
			String type = "";
			
			try {
				movieDesc = new String(request.getParameter("movieDesc").getBytes("ISO-8859-1"),"utf-8");
				type = new String(request.getParameter("type").getBytes("ISO-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("转码异常",e);
			}

			VideoSpecification object = new VideoSpecification();
			
			object.setId(new Integer(id));
			
			object.setMovieDesc(movieDesc);
			object.setResolution(resolution);
			object.setDuration(new Integer(duration));
			object.setFileSize(fileSize);
			object.setType(type);
			
			request.setAttribute("object", object);
		}
		else
		{
			request.setAttribute("operate", "add");
		}
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String remove(){
		boolean flag = false;
		try {
			flag = videoSpecificationService.remove(object.getId());
			log.info("删除结果"+flag);
		} catch (Exception e) {
			log.error("删除数据时出现异常",e);
		}
		return SUCCESS;
	}

	public VideoSpecification getObject() {
		return object;
	}

	public void setObject(VideoSpecification object) {
		this.object = object;
	}
}
