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
import com.dvnchina.advertDelivery.dao.VideoSpecificationDao;
import com.dvnchina.advertDelivery.model.VideoSpecification;
import com.dvnchina.advertDelivery.service.VideoSpecificationService;
import com.dvnchina.advertDelivery.utils.page.PageBean;
import com.dvnchina.advertDelivery.utils.page.PageUtils;

public class VideoSpecificationServiceImpl implements VideoSpecificationService {
	
	private static Logger logger = Logger.getLogger(VideoSpecificationServiceImpl.class);
	
	private VideoSpecificationDao videoSpecificationDao;
	
	@Override
	public List<VideoSpecification> page(Map condition, int start, int end) {
		StringBuffer queryVideoSpecification = new StringBuffer();
		queryVideoSpecification.append("SELECT * FROM T_VIDEO_SPECIFICATION");
		
		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			int mapSize = conditionSet.size();
			int count = 1;
			queryVideoSpecification.append(" WHERE ");
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				queryVideoSpecification.append(columnName).append(" like '%").append(entry.getValue()).append("%' ");
				count++;
				if(count<mapSize){
					queryVideoSpecification.append(" AND ");
				}
			}
		}
		return videoSpecificationDao.page(queryVideoSpecification.toString(), start, end);
	}

	@Override
	public int queryCount(Map condition) {
		StringBuffer queryVideoSpecification = new StringBuffer();
		queryVideoSpecification.append("SELECT COUNT(*) FROM T_VIDEO_SPECIFICATION");
		
		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			int mapSize = conditionSet.size();
			int count = 1;
			queryVideoSpecification.append(" WHERE ");
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				System.out.println("CanonicalName="+entry.getValue().getClass().getCanonicalName());
				System.out.println("Modifiers="+entry.getValue().getClass().getModifiers());
				System.out.println("Name="+entry.getValue().getClass().getName());
				System.out.println("SimpleName="+entry.getValue().getClass().getSimpleName());
				
				queryVideoSpecification.append(columnName).append(" like '%").append(entry.getValue()).append("%'");
				count++;
				if(count<mapSize){
					queryVideoSpecification.append(" AND ");
				}
			}
		}
		return videoSpecificationDao.queryTotalCount(queryVideoSpecification.toString());
	}

	@Override
	public Map<String,String> save(VideoSpecification object) {
		String message = "";
		String method = "";
		Map resultMap = new HashMap();
		String result = "";
		String type = "video";
		Integer videoMaterialSpeciId = null;
		int num;
		resultMap.put("dataType",type);
		
		if (object!=null&&object.getId()==null){
			//增加
			try {
				method = "save";
				resultMap.put("method",method);
				
				videoMaterialSpeciId = videoSpecificationDao.getCurrentSequence();
				object.setId(videoMaterialSpeciId);
				num = videoSpecificationDao.save(object);
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
				num = videoSpecificationDao.update(object);
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

	@Override
	public boolean remove(int objectId) {
		boolean flag = false;
		int result = videoSpecificationDao.remove(objectId);
		if(result>0){
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean update(VideoSpecification object) {
		boolean flag = false;
		int result = videoSpecificationDao.update(object);
		if(result>0){
			flag = true;
		}
		return flag;
	}

	public VideoSpecificationDao getVideoSpecificationDao() {
		return videoSpecificationDao;
	}

	public void setVideoSpecificationDao(VideoSpecificationDao videoSpecificationDao) {
		this.videoSpecificationDao = videoSpecificationDao;
	}

	@Override
	public PageBean page(HttpServletRequest request) {
		String currentPage=request.getParameter("currentPage");
		String saveAgainFlag = request.getParameter("flag");
		String videoDescQuery = request.getParameter("videoDescQuery");
		
		if(StringUtils.isNotBlank(videoDescQuery)){
			request.setAttribute("videoDescQuery",videoDescQuery);
		}
		
		int count = 0;
		int pageNB = 1;
		PageBean pageBean = null;
		int pageSize = PageConstant.PAGE_SIZE;
		
		if (StringUtils.isNotBlank(currentPage)) {
			pageNB = Integer.valueOf(currentPage);
		}
		
		Map conditionMap = new HashMap();
		
		try {
			if (StringUtils.isNotBlank(videoDescQuery)) {
				conditionMap.put("MOVIE_DESC", videoDescQuery);
			}
			count = this.queryCount(conditionMap);
			List<VideoSpecification> videoMaterialSpeciList = this.page(
					conditionMap, (pageNB - 1) * PageConstant.PAGE_SIZE, pageNB
							* PageConstant.PAGE_SIZE);
			pageBean = PageUtils.getPageBean2(pageNB, pageSize, count,videoMaterialSpeciList);	
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常",e);
		}
		return pageBean;
	}

	@Override
	public VideoSpecification get(Integer id) {
		return videoSpecificationDao.get(id);
	}
}
