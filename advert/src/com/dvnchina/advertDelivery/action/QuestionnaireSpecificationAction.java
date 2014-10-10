package com.dvnchina.advertDelivery.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.constant.PageConstant;
import com.dvnchina.advertDelivery.model.QuestionnaireSpecification;
import com.dvnchina.advertDelivery.service.QuestionnaireSpecificationService;
import com.dvnchina.advertDelivery.utils.json.Obj2JsonUtil;
import com.dvnchina.advertDelivery.utils.page.PageBean;
import com.dvnchina.advertDelivery.utils.page.PageUtils;

public class QuestionnaireSpecificationAction extends BaseActionSupport<Object>{
	
	private static final long serialVersionUID = 6886701623440460247L;
	
	private Logger logger = Logger.getLogger(QuestionnaireSpecificationAction.class);
	
	private QuestionnaireSpecification object;
	
	private QuestionnaireSpecificationService questionnaireSpecificationService;


	public QuestionnaireSpecificationService getQuestionnaireSpecificationService() {
		return questionnaireSpecificationService;
	}

	public void setQuestionnaireSpecificationService(
			QuestionnaireSpecificationService questionnaireSpecificationService) {
		this.questionnaireSpecificationService = questionnaireSpecificationService;
	}

	@SuppressWarnings("unchecked")
	public String save(){
		try {
			Map map = questionnaireSpecificationService.save(object);
			String result = Obj2JsonUtil.object2json(map);
			this.renderHtml(result);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public String add(){
		
		
		return SUCCESS;
	}
	
	public String query(){
		
		HttpServletRequest request = this.getRequest();
		
		String currentPage=request.getParameter("currentPage");
		
		String saveAgainFlag = request.getParameter("flag");
		
		String typeQuery = request.getParameter("typeQuery");
		
		if(StringUtils.isNotBlank(typeQuery)){
			
			request.setAttribute("typeQuery", typeQuery);
			
		}
		
		String isHd = request.getParameter("isHd");
		request.setAttribute("isHd",isHd);
		
		String sdAndhd = request.getParameter("sdAndhd");
		request.setAttribute("sdAndhd",sdAndhd);
		
		int count = 0;
		int pageNB = 1;
		PageBean pageBean = null;
		int pageSize = PageConstant.PAGE_SIZE;
		
		if (StringUtils.isNotBlank(currentPage)) {
			pageNB = Integer.valueOf(currentPage);
		}
		
		Map conditionMap = new HashMap();
		
		try {
			if (StringUtils.isNotBlank(typeQuery)) {
				conditionMap.put("TYPE", typeQuery);
			}
			count = questionnaireSpecificationService.queryCount(conditionMap);
			List<QuestionnaireSpecification> questionMaterialSpeciList = questionnaireSpecificationService.page(
					conditionMap, (pageNB - 1) * PageConstant.PAGE_SIZE, pageNB
							* PageConstant.PAGE_SIZE);
			pageBean = PageUtils.getPageBean2(pageNB, pageSize, count,questionMaterialSpeciList);
			request.setAttribute("objectList", pageBean.getList());
			request.setAttribute("page", pageBean);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常",e);
		}
		return SUCCESS;
	}
	
public String queryManager(){
		
		HttpServletRequest request = this.getRequest();
		
		String currentPage=request.getParameter("currentPage");
		
		String saveAgainFlag = request.getParameter("flag");
		
		String typeQuery = request.getParameter("typeQuery");
		
		if(StringUtils.isNotBlank(typeQuery)){
			
			request.setAttribute("typeQuery", typeQuery);
			
		}
		
		String isHd = request.getParameter("isHd");
		request.setAttribute("isHd",isHd);
		
		String sdAndhd = request.getParameter("sdAndhd");
		request.setAttribute("sdAndhd",sdAndhd);
		
		int count = 0;
		int pageNB = 1;
		PageBean pageBean = null;
		int pageSize = PageConstant.PAGE_SIZE;
		
		if (StringUtils.isNotBlank(currentPage)) {
			pageNB = Integer.valueOf(currentPage);
		}
		
		Map conditionMap = new HashMap();
		
		try {
			if (StringUtils.isNotBlank(typeQuery)) {
				conditionMap.put("TYPE", typeQuery);
			}
			count = questionnaireSpecificationService.queryCount(conditionMap);
			List<QuestionnaireSpecification> questionMaterialSpeciList = questionnaireSpecificationService.page(
					conditionMap, (pageNB - 1) * PageConstant.PAGE_SIZE, pageNB
							* PageConstant.PAGE_SIZE);
			pageBean = PageUtils.getPageBean2(pageNB, pageSize, count,questionMaterialSpeciList);
			request.setAttribute("objectList", pageBean.getList());
			request.setAttribute("page", pageBean);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常",e);
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
			flag = questionnaireSpecificationService.remove(object.getId());
			logger.info("删除结果"+flag);
		} catch (Exception e) {
			logger.error("删除数据时出现异常",e);
		}
		return SUCCESS;
	}

	public QuestionnaireSpecification getObject() {
		return object;
	}

	public void setObject(QuestionnaireSpecification object) {
		this.object = object;
	}
	
}
