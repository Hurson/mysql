package com.dvnchina.advertDelivery.service.impl;

import java.util.List;

import com.dvnchina.advertDelivery.dao.GetPositionDao;
import com.dvnchina.advertDelivery.dao.UploadMaterialDao;
import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.MessageMeta;
import com.dvnchina.advertDelivery.model.ImageSpecification;
import com.dvnchina.advertDelivery.model.Resource;
import com.dvnchina.advertDelivery.model.TextSpecification;
import com.dvnchina.advertDelivery.model.VideoMeta;
import com.dvnchina.advertDelivery.model.VideoSpecification;
import com.dvnchina.advertDelivery.service.UploadMaterialService;

public class UploadMaterialServiceImpl implements UploadMaterialService {
	
	private UploadMaterialDao uploadMaterialDao;
	
	private GetPositionDao getPositionDao;

	@Override
	public void addVideoMaterial(VideoMeta material) {
		uploadMaterialDao.addVideoMaterial(material);
	}
	
	@Override
	public void addImageMaterial(ImageMeta material) {
		uploadMaterialDao.addImageMaterial(material);
	}

	@Override
	public void addMessageMaterial(MessageMeta material) {
		uploadMaterialDao.addMessageMaterial(material);
	}

	@Override
	public List<AdvertPosition> getAllPosition() {
		List<AdvertPosition> positionList = getPositionDao.getAllPosition();
		return positionList;
	}

	@Override
	public Integer getResourceId(Integer type,String materialName,String uploadPath) {
		return uploadMaterialDao.getResourceId(type,materialName,uploadPath);
	}
	
	@Override
	public MessageMeta getMessageMeta(String messageName) {
		return uploadMaterialDao.getMessageMeta(messageName);
	}

	@Override
	public void addResource(Resource resource) {
		uploadMaterialDao.addResource(resource);
	}

	@Override
	public List<ImageSpecification> getImageFileSpeci(Integer positionId) {
		return getPositionDao.getImageFileSpeci(positionId);
	}

	@Override
	public List<VideoSpecification> getVideoFileSpeci(Integer positionId) {
		return getPositionDao.getVideoFileSpeci(positionId);
	}

	@Override
	public List<TextSpecification> getMessageSpeci(Integer positionId) {
		return getPositionDao.getMessageSpeci(positionId);
	}
	
	public void setUploadMaterialDao(UploadMaterialDao uploadMaterialDao) {
		this.uploadMaterialDao = uploadMaterialDao;
	}
	
	public void setGetPositionDao(GetPositionDao getPositionDao) {
		this.getPositionDao = getPositionDao;
	}

	@Override
	public Resource getResource(String messageName,Integer resourceId) {
		return uploadMaterialDao.getResource(messageName,resourceId);
	}
	
	@Override
	public List<ContractBackup> getAllContract(){
		return uploadMaterialDao.getAllContract();
	}
	
	@Override
	public ContractBackup getContract(String contractCode){
		return uploadMaterialDao.getContract(contractCode);
	}
	
	@Override
	public String getMetarialPath(String contractId){
		return uploadMaterialDao.getMetarialPath(contractId);
	}
}
