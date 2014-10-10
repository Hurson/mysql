package com.dvnchina.advertDelivery.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.constant.PageConstant;
import com.dvnchina.advertDelivery.model.AdvertPositionType;
import com.dvnchina.advertDelivery.service.AdvertPositionTypeService;
import com.dvnchina.advertDelivery.utils.page.PageBean;
import com.dvnchina.advertDelivery.utils.page.PageUtils;

public class AdvertPositionTypeAction extends BaseActionSupport<Object>{
	
	private static final long serialVersionUID = -3666982468062423696L;

	private Logger logger = Logger.getLogger(AdvertPositionTypeAction.class);
	
	private AdvertPositionType object;
	
	private AdvertPositionTypeService advertPositionTypeService;

	public AdvertPositionType getObject() {
		return object;
	}

	public void setObject(AdvertPositionType object) {
		this.object = object;
	}

	public AdvertPositionTypeService getAdvertPositionTypeService() {
		return advertPositionTypeService;
	}

	public void setAdvertPositionTypeService(
			AdvertPositionTypeService advertPositionTypeService) {
		this.advertPositionTypeService = advertPositionTypeService;
	}
	
	public String save(){
		boolean flag = false;
		String message = "";
		StringBuffer url = new StringBuffer();
		
		if (object!=null&&object.getId()==null) {
			//增加
			try {
				flag = advertPositionTypeService.checkAdvertPositionType(object.getPositionTypeCode(),object.getId());
				message = "该类型编码已经存在";
				url.append("queryPositionTypePage.do?method=queryPage");
				//url = "queryPositionTypePage.do?method=queryPage&flag=saveAgain";
				if (flag) {
					url.append("&flag=saveAgain");
					this.getRequest().setAttribute("advertPositionType",
							object);
					this.renderHtml(generateMessage(url.toString(), message));
					return null;
				}

			} catch (Exception e) {
				message = "检查广告位类型码时出现异常";
				url.append("&flag=saveAgain");
				this.getRequest().setAttribute("advertPositionType",
						object);
				this.renderHtml(generateMessage(url.toString(), message));
				logger.error(message, e);
				return null;
			}
			try {
				flag = advertPositionTypeService.save(object);
				if (flag) {
					message = "保存成功";
					url.append("&flag=saveSuccess");
					this.renderHtml(generateMessage(url.toString(), message));
					return null;
				} else {
					message = "保存失败";
					url.append("&flag=saveFailure");
					this.renderHtml(generateMessage(url.toString(), message));
					return null;
				}
			} catch (Exception e) {
				message = "保存广告位类型信息时出现异常";
				url.append("&flag=saveFailure");
				this.renderHtml(generateMessage(url.toString(), message));
				logger.error(message, e);
				return null;
			}
		}else if(object!=null&&object.getId()!=null){
			//更新
			try {
				flag = advertPositionTypeService.checkAdvertPositionType(object.getPositionTypeCode(),object.getId());
				message = "该类型编码已经存在";
				url.append("queryPositionTypePage.do?method=queryPage");
				//url = "queryPositionTypePage.do?method=queryPage&flag=saveAgain";
				if (flag) {
					url.append("&flag=saveAgain");
					this.getRequest().setAttribute("advertPositionType",
							object);
					this.renderHtml(generateMessage(url.toString(), message));
					return null;
				}

			} catch (Exception e) {
				message = "检查广告位类型码时出现异常";
				url.append("&flag=saveAgain");
				this.getRequest().setAttribute("advertPositionType",
						object);
				this.renderHtml(generateMessage(url.toString(), message));
				logger.error(message, e);
				return null;
			}
			try {
				flag = advertPositionTypeService.update(object);
				if (flag) {
					message = "保存成功";
					url.append("&flag=saveSuccess");
					this.renderHtml(generateMessage(url.toString(), message));
					return null;
				} else {
					message = "保存失败";
					url.append("&flag=saveFailure");
					this.renderHtml(generateMessage(url.toString(), message));
					return null;
				}
			} catch (Exception e) {
				message = "保存广告位类型信息时出现异常";
				url.append("&flag=saveFailure");
				this.renderHtml(generateMessage(url.toString(), message));
				logger.error(message, e);
				return null;
			}
		}
		return null;
	}
	
	public String query(){
		
		HttpServletRequest request = this.getRequest();
		
		String currentPage=request.getParameter("currentPage");
		
		String positionTypeName = request.getParameter("positionTypeName");
		
		Map<String,String> conditionMap = new HashMap<String,String>();
		
		if(StringUtils.isNotBlank(positionTypeName)){
			request.setAttribute("positionTypeName",positionTypeName);
			conditionMap.put("POSITION_TYPE_NAME", positionTypeName);
		}
		
		String positionTypeCode = request.getParameter("positionTypeCode");
		
		if(StringUtils.isNotBlank(positionTypeCode)){
			request.setAttribute("positionTypeCode",positionTypeCode);
			conditionMap.put("POSITION_TYPE_CODE", positionTypeCode);
		}
		
		String contractBindingFlag = request.getParameter("contractBindingFlag");
		
		String returnPath = "";
		
		if(StringUtils.isNotBlank(contractBindingFlag)&&"1".equals(contractBindingFlag)){
			returnPath = "binding";
		}else{
			returnPath = SUCCESS;
		}
		
		int count = 0;
		int pageNB = 1;
		PageBean pageBean = null;
		int pageSize = PageConstant.PAGE_SIZE;
		
		if (StringUtils.isNotBlank(currentPage)) {
			pageNB = Integer.valueOf(currentPage);
		}
		
		try {
			count = advertPositionTypeService.queryCount(conditionMap);
			List<AdvertPositionType> advertPositionTypeList = advertPositionTypeService.page(
					conditionMap, (pageNB - 1) * PageConstant.PAGE_SIZE, pageNB
							* PageConstant.PAGE_SIZE);
			pageBean = PageUtils.getPageBean2(pageNB, pageSize, count,advertPositionTypeList);
			request.setAttribute("objectList", pageBean.getList());
			request.setAttribute("page", pageBean);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常",e);
		}
		
		
		return returnPath;
	}
	
	public String add(){
		return SUCCESS;
	}
	
	public String remove(){
		boolean flag = false;
		try {
			flag = advertPositionTypeService.remove(object.getId());
			logger.info("删除结果"+flag);
		} catch (Exception e) {
			logger.error("删除数据时出现异常",e);
		}
		return SUCCESS;
	}
	
}
