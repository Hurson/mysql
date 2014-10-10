package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.MessageMeta;
import com.dvnchina.advertDelivery.model.Resource;
import com.dvnchina.advertDelivery.model.VideoMeta;

public interface UploadMaterialDao {
	public void addVideoMaterial(VideoMeta material);
	public void addImageMaterial(ImageMeta material);
	public void addMessageMaterial(MessageMeta material);
	public Integer getResourceId(Integer type,String materialName,String uploadPath);
	public MessageMeta getMessageMeta(String messageName);
	public void addResource(Resource resource);
	public Resource getResource(String messageName,Integer resourceId);
	public ContractBackup getContract(String contractCode);
	public String getMetarialPath(String contractId);
	public List<ContractBackup> getAllContract();
}
