package com.dvnchina.advertDelivery.action;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.dvnchina.advertDelivery.constant.PageConstant;
import com.dvnchina.advertDelivery.model.ImageSpecification;
import com.dvnchina.advertDelivery.service.ImageSpecificationService;
import com.dvnchina.advertDelivery.utils.page.PageBean;
import com.dvnchina.advertDelivery.utils.page.PageUtils;

public class ImageSpecificationAction extends BaseActionSupport<Object>{
	
	private static Logger logger = Logger.getLogger(ImageSpecificationAction.class);

	private static final long serialVersionUID = 585143703351399627L;

	private ImageSpecification object;
	
	private ImageSpecificationService imageSpecificationService;
	
	public ImageSpecification getObject() {
		return object;
	}

	public void setObject(ImageSpecification object) {
		this.object = object;
	}

	public ImageSpecificationService getImageSpecificationService() {
		return imageSpecificationService;
	}

	public void setImageSpecificationService(
			ImageSpecificationService imageSpecificationService) {
		this.imageSpecificationService = imageSpecificationService;
	}

	@SuppressWarnings("unchecked")
	public String save(){
		Map map = null;
		String result = null;
		try {
			map=imageSpecificationService.save(object);
			result = JSON.toJSONString(map);
		} catch (Exception e) {
			map.put("flag","error");
			logger.error("添加图片规格异常",e);
		}
//		this.renderHtml(result);
		return SUCCESS;
	}
	
	public String queryManager(){
		
		int count = 0;
		int pageNB = 1;
		PageBean pageBean = null;
		int pageSize = PageConstant.PAGE_SIZE;
		
		HttpServletRequest request = this.getRequest();
		
		String currentPage=request.getParameter("currentPage");
		
		String imageDescQuery = request.getParameter("imageDescQuery");
		
		if(StringUtils.isNotBlank(imageDescQuery)){
			request.setAttribute("imageDescQuery",imageDescQuery);
		}
		
		String isHd = request.getParameter("isHd");
		if(StringUtils.isNotBlank(isHd)){
			request.setAttribute("isHd",isHd);
		}
		String sdAndhd = request.getParameter("sdAndhd");
		if(StringUtils.isNotBlank(sdAndhd)){
			request.setAttribute("sdAndhd",sdAndhd);
		}
		
		if (StringUtils.isNotBlank(currentPage)) {
			pageNB = Integer.valueOf(currentPage);
		}
		
		Map conditionMap = new HashMap();
		
		try {
			if (StringUtils.isNotBlank(imageDescQuery)) {
				conditionMap.put("IMAGE_DESC", imageDescQuery);
			}
			count = imageSpecificationService.queryCount(conditionMap);
			List<ImageSpecification> pictureMaterialSpeciList = imageSpecificationService.page(conditionMap, (pageNB - 1) * PageConstant.PAGE_SIZE, pageNB
							* PageConstant.PAGE_SIZE);
			pageBean = PageUtils.getPageBean2(pageNB, pageSize, count,pictureMaterialSpeciList);
			request.setAttribute("objectList", pageBean.getList());
			request.setAttribute("page", pageBean);
		} catch (Exception e) {
			log.error("获取数据列表时出现异常",e);
		}
		return SUCCESS;
	}
	
	public String query(){
		
		int count = 0;
		int pageNB = 1;
		PageBean pageBean = null;
		int pageSize = PageConstant.PAGE_SIZE;
		
		HttpServletRequest request = this.getRequest();
		
		String currentPage=request.getParameter("currentPage");
		
		String imageDescQuery = request.getParameter("imageDescQuery");
		
		if(StringUtils.isNotBlank(imageDescQuery)){
			request.setAttribute("imageDescQuery",imageDescQuery);
		}
		
		String isHd = request.getParameter("isHd");
		if(StringUtils.isNotBlank(isHd)){
			request.setAttribute("isHd",isHd);
		}
		String sdAndhd = request.getParameter("sdAndhd");
		if(StringUtils.isNotBlank(sdAndhd)){
			request.setAttribute("sdAndhd",sdAndhd);
		}
		
		if (StringUtils.isNotBlank(currentPage)) {
			pageNB = Integer.valueOf(currentPage);
		}
		
		Map conditionMap = new HashMap();
		
		try {
			if (StringUtils.isNotBlank(imageDescQuery)) {
				conditionMap.put("IMAGE_DESC", imageDescQuery);
			}
			count = imageSpecificationService.queryCount(conditionMap);
			List<ImageSpecification> pictureMaterialSpeciList = imageSpecificationService.page(conditionMap, (pageNB - 1) * PageConstant.PAGE_SIZE, pageNB
							* PageConstant.PAGE_SIZE);
			pageBean = PageUtils.getPageBean2(pageNB, pageSize, count,pictureMaterialSpeciList);
			request.setAttribute("objectList", pageBean.getList());
			request.setAttribute("page", pageBean);
		} catch (Exception e) {
			log.error("获取数据列表时出现异常",e);
		}
		return SUCCESS;
	}
	
	public String add(){
		
		HttpServletRequest request = this.getRequest();
		
		if (request.getParameter("id") != null
				&& !"".equals(request.getParameter("id"))) {
			
			request.setAttribute("operate", "edit");

			String id = request.getParameter("id");
			String imageDesc = "";
			String imageLength = request.getParameter("imageLength");
			String imageWidth = request.getParameter("imageWidth");
			String fileSize = request.getParameter("fileSize");
			String type = "";
			String isLink = request.getParameter("isLink");
			
			try {
				imageDesc = new String(request.getParameter("imageDesc").getBytes("ISO-8859-1"),"utf-8");
				type = new String(request.getParameter("type").getBytes("ISO-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("转码异常",e);
			}

			ImageSpecification object = new ImageSpecification();
			object.setId(new Integer(id));
			object.setImageDesc(imageDesc);
			object.setImageLength(imageLength);
			object.setImageWidth(imageWidth);
			object.setFileSize(fileSize);
			object.setType(type);
			object.setIsLink(new Integer(isLink));

			request.setAttribute("object", object);
		}
		else
		{
			request.setAttribute("operate", "add");
		}
		
		
		return SUCCESS;
	}
	
	public String remove(){
		boolean flag = false;
		try {
			flag = imageSpecificationService.remove(object.getId());
			log.info("删除结果"+flag);
		} catch (Exception e) {
			log.error("删除数据时出现异常",e);
		}
		return SUCCESS;
	}
	
	
}
