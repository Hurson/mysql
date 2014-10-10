package com.dvnchina.advertDelivery.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dvnchina.advertDelivery.dao.ImageSpecificationDao;
import com.dvnchina.advertDelivery.model.AdvertPositionType;
import com.dvnchina.advertDelivery.model.ImageSpecification;
import com.dvnchina.advertDelivery.service.ImageSpecificationService;

public class ImageSpecificationServiceImpl implements
	ImageSpecificationService {
	
	private ImageSpecificationDao imageSpecificationDao;

	@Override
	public List<ImageSpecification> page(Map condition, int start, int end) {
		StringBuffer queryPictureMaterialSpeci = new StringBuffer();
		queryPictureMaterialSpeci.append("SELECT * FROM T_IMAGE_SPECIFICATION");
		
		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			int mapSize = conditionSet.size();
			int count = 1;
			queryPictureMaterialSpeci.append(" WHERE ");
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				queryPictureMaterialSpeci.append(columnName).append(" like '%").append(entry.getValue()).append("%'");
				count++;
				if(count<mapSize){
					queryPictureMaterialSpeci.append(" AND ");
				}
			}
		}
		
		return imageSpecificationDao.page(queryPictureMaterialSpeci.toString(), start, end);
	}

	@Override
	public int queryCount(Map condition) {
		StringBuffer queryPictureMaterialSpeci = new StringBuffer();
		queryPictureMaterialSpeci.append("SELECT COUNT(*) FROM T_IMAGE_SPECIFICATION");
		
		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			int mapSize = conditionSet.size();
			int count = 1;
			queryPictureMaterialSpeci.append(" WHERE ");
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				queryPictureMaterialSpeci.append(columnName).append(" like '%").append(entry.getValue()).append("%'");
				count++;
				if(count<mapSize){
					queryPictureMaterialSpeci.append(" AND ");
				}
			}
		}
		return imageSpecificationDao.queryTotalCount(queryPictureMaterialSpeci.toString());
	}

	@Override
	public Map<String,String> save(
			ImageSpecification object) {
		boolean flag = false;
		String message = "";
		String method = "";
		Map resultMap = new HashMap();
		String result = "";
		String type = "picture";
		Integer pictureMaterialSpeciId = null;
		resultMap.put("dataType",type);
		int currentSequence;
		int num;
		if (object!=null&&object.getId()==null){
			//增加
			try {
				method = "save";
				resultMap.put("method",method);
				currentSequence=imageSpecificationDao.getCurrentSequence();
				object.setId(currentSequence);
				num = imageSpecificationDao.save(object);

				if (num>0) {
					message = "success";
				} else {
					message = "failure";
				}
				resultMap.put("flag",message);
			} catch (Exception e) {
				message = "error";
				resultMap.put("flag",message);
			}
		}else if(object!=null&&object.getId()!=null){
			//更新
			try {
				method = "update";
				resultMap.put("method",method);
				num = imageSpecificationDao.update(object);
				if (num>0) {
					message = "success";
				} else {
					message = "failure";
				}
				resultMap.put("flag",message);
			} catch (Exception e) {
				message = "error";
				resultMap.put("flag",message);
			}
		}
		resultMap.put("speciObject",object);
		return resultMap;
	}

	@Override
	public boolean remove(int objectId) {
		boolean flag = false;
		int result = imageSpecificationDao.remove(objectId);
		if(result>0){
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean update(
			ImageSpecification object) {
		boolean flag = false;
		int result = imageSpecificationDao.update(object);
		if(result>0){
			flag = true;
		}
		return flag;
	}

	@Override
	public Integer getPictureMaterialSpeciIdByCondition(
			ImageSpecification object) {
		StringBuffer querySql = new StringBuffer();
		querySql.append("SELECT * FROM T_IMAGE_SPECIFICATION WHERE ");
		querySql.append("IMAGE_DESC='");
		querySql.append(object.getImageDesc()).append("'");
		querySql.append(" AND ");
		querySql.append("IMAGE_LENGTH='");
		querySql.append(object.getImageLength()).append("'");
		querySql.append(" AND ");
		querySql.append("IMAGE_WIDTH='");
		querySql.append(object.getImageWidth()).append("'");
		querySql.append(" AND ");
		querySql.append("TYPE='");
		querySql.append(object.getType()).append("'");
		querySql.append(" AND ");
		querySql.append("FILE_SIZE='");
		querySql.append(object.getFileSize()).append("'");
		querySql.append(" ORDER BY ID DESC");
		System.out.println(querySql.toString());
		List<ImageSpecification> pictureMaterialSpeciList = imageSpecificationDao.query(querySql.toString());
		return (pictureMaterialSpeciList!=null)?pictureMaterialSpeciList.get(0).getId():null;
	}

	public ImageSpecificationDao getImageSpecificationDao() {
		return imageSpecificationDao;
	}

	public void setImageSpecificationDao(ImageSpecificationDao imageSpecificationDao) {
		this.imageSpecificationDao = imageSpecificationDao;
	}

	@Override
	public ImageSpecification get(Integer id) {
		return imageSpecificationDao.get(id);
	}

}
