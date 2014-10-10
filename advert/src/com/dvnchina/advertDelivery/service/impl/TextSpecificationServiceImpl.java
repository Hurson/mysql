package com.dvnchina.advertDelivery.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.constant.PageConstant;
import com.dvnchina.advertDelivery.dao.TextSpecificationDao;
import com.dvnchina.advertDelivery.model.AdvertPositionType;
import com.dvnchina.advertDelivery.model.TextSpecification;
import com.dvnchina.advertDelivery.service.TextSpecificationService;
import com.dvnchina.advertDelivery.utils.page.PageBean;
import com.dvnchina.advertDelivery.utils.page.PageUtils;

public class TextSpecificationServiceImpl implements TextSpecificationService {
	
	private static Logger logger = Logger.getLogger(TextSpecificationServiceImpl.class);
	
	private TextSpecificationDao textSpecificationDao;
	
	public TextSpecificationDao getTextSpecificationDao() {
		return textSpecificationDao;
	}

	public void setTextSpecificationDao(TextSpecificationDao textSpecificationDao) {
		this.textSpecificationDao = textSpecificationDao;
	}

	public List<TextSpecification> page(Map condition, int start, int end) {
		StringBuffer queryContentMaterialSpeci = new StringBuffer();
		queryContentMaterialSpeci.append("SELECT * FROM T_TEXT_SPECIFICATION");

		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			int mapSize = conditionSet.size();
			int count = 1;
			queryContentMaterialSpeci.append(" WHERE ");
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				queryContentMaterialSpeci.append(columnName).append(" like ").append("'%").append(entry.getValue()).append("%'");
				count++;
				if(count<mapSize){
					queryContentMaterialSpeci.append(" AND ");
				}
			}
		}
		
		return textSpecificationDao.page(queryContentMaterialSpeci.toString(), start, end);
	}

	
	public int queryCount(Map condition) {
		StringBuffer queryContentMaterialSpeci = new StringBuffer();
		queryContentMaterialSpeci.append("SELECT COUNT(*) FROM T_TEXT_SPECIFICATION");
		
		
		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			int mapSize = conditionSet.size();
			int count = 1;
			queryContentMaterialSpeci.append(" WHERE ");
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				queryContentMaterialSpeci.append(columnName).append(" like '%").append(entry.getValue()).append("%'");
				count++;
				if(count<mapSize){
					queryContentMaterialSpeci.append(" AND ");
				}
			}
		}
		return textSpecificationDao.queryTotalCount(queryContentMaterialSpeci.toString());
	}

	
	public Map<String,String> save(
			TextSpecification object) {
		String message = "";
		String method = "";
		Map resultMap = new HashMap();
		String type = "content";
		Integer currentSequence = null;
		resultMap.put("dataType",type);
		int num;
		
		if (object!=null&&object.getId()==null){
			//增加
			try {
				method = "save";
				resultMap.put("method",method);
				currentSequence=textSpecificationDao.getCurrentSequence();
				object.setId(currentSequence);
				num = textSpecificationDao.save(object);
				
				if (num>0) {
					message = "success";
				} else {
					message = "failure";
				}
				resultMap.put("flag",message);
			} catch (Exception e) {
				message = "error";
				resultMap.put("flag",message);
				logger.error(message, e);
			}
		}else if(object!=null&&object.getId()!=null){
			//更新
			try {
				method = "update";
				resultMap.put("method",method);
				num = textSpecificationDao.update(object);
				if (num>0) {
					message = "success";
				} else {
					message = "failure";
				}
				resultMap.put("flag",message);
			} catch (Exception e) {
				message = "error";
				resultMap.put("flag",message);
				logger.error(message, e);
			}
		}
		resultMap.put("speciObject",object);
		return resultMap;
	}

	public boolean remove(int objectId) {
		boolean flag = false;
		int result = textSpecificationDao.remove(objectId);
		if(result>0){
			flag = true;
		}
		return flag;
	}

	
	public boolean update(TextSpecification object){
			boolean flag = false;
			int result = textSpecificationDao.update(object);
			if(result>0){
				flag = true;
			}
			return flag;
	}

	@Override
	public PageBean page(HttpServletRequest request) {
		int count = 0;
		int pageNB = 1;
		PageBean pageBean = null;
		int pageSize = PageConstant.PAGE_SIZE;
		
		String currentPage=request.getParameter("currentPage");
		
		String saveAgainFlag = request.getParameter("flag");
		
		String contentDescQuery = request.getParameter("contentDescQuery");
		
		if(StringUtils.isNotBlank(contentDescQuery)){
			request.setAttribute("contentDescQuery",contentDescQuery);
		}
		
		
		if(StringUtils.isBlank(saveAgainFlag)){
			request.setAttribute("contentMaterialSpeci",new TextSpecification());
		}else if("saveSuccess".equals(saveAgainFlag)){
			request.setAttribute("contentMaterialSpeci",new TextSpecification());
		}
		
		if (StringUtils.isNotBlank(currentPage)) {
			pageNB = Integer.valueOf(currentPage);
		}
		
		Map conditionMap = new HashMap();
		
		try {
			if (StringUtils.isNotBlank(contentDescQuery)) {
				conditionMap.put("TEXT_DESC", contentDescQuery);
			}
			count = this.queryCount(conditionMap);
			List<TextSpecification> contentMaterialSpeciList = this.page(conditionMap, (pageNB - 1) * PageConstant.PAGE_SIZE, pageNB
							* PageConstant.PAGE_SIZE);
			pageBean = PageUtils.getPageBean2(pageNB, pageSize, count,contentMaterialSpeciList);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常",e);
		}
		return pageBean;
	}

	@Override
	public TextSpecification get(Integer id) {
		return textSpecificationDao.get(id);
	}
}
