package com.dvnchina.advertDelivery.service;

import java.util.List;

import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.MessageMeta;
import com.dvnchina.advertDelivery.model.ImageSpecification;
import com.dvnchina.advertDelivery.model.Resource;
import com.dvnchina.advertDelivery.model.TextSpecification;
import com.dvnchina.advertDelivery.model.VideoMeta;
import com.dvnchina.advertDelivery.model.VideoSpecification;

/**
 * 素材管理业务逻辑接口
 * 
 * @author Administrator
 *
 */
public interface UploadMaterialService {
	
	/**
	 * 添加资产
	 * @param resource 资产对象
	 * @return 操作状态码（0-添加成功，1-添加失败）
	 */
	public void addResource(Resource resource);
	
	/**
	 * 添加视频素材
	 * @param material 素材对象
	 * @return 操作状态码（0-添加成功，1-添加失败）
	 */
	public void addVideoMaterial(VideoMeta material);
	
	/**
	 * 添加视频素材
	 * @param material 素材对象
	 * @return 操作状态码（0-添加成功，1-添加失败）
	 */
	public void addImageMaterial(ImageMeta material);
	
	/**
	 * 添加视频素材
	 * @param material 素材对象
	 * @return 操作状态码（0-添加成功，1-添加失败）
	 */
	public void addMessageMaterial(MessageMeta material);
	
	/**
	 * 根据广告位id获得广告位对应的视频文件的规格属性
	 * @param positionId 广告位id
	 * @return 视频规格
	 */
	public List<VideoSpecification> getVideoFileSpeci(Integer positionId);
	
	/**
	 * 根据广告位id获得广告位对应的图片文件的规格属性
	 * @param positionId 广告位id
	 * @return 图片规格
	 */
	public List<ImageSpecification> getImageFileSpeci(Integer positionId);
	
	/**
	 * 根据广告位id获得广告位对应的文字的规格属性
	 * @param positionId 广告位id
	 * @return 文字规格
	 */
	public List<TextSpecification> getMessageSpeci(Integer positionId);
	
	/**
	 * 获取所有广告位的名字
	 * 
	 * @return
	 */
	public List<AdvertPosition> getAllPosition();
	
	/**
	 * 获取resourceId
	 * 
	 * @return
	 */
	public Integer getResourceId(Integer type,String materialName,String uploadPath);
	
	/**
	 * 获取文字信息
	 * 
	 * @return
	 */
	public MessageMeta getMessageMeta(String messageName);
	
	/**
	 * 获取资产信息
	 * 
	 * @return
	 */
	public Resource getResource(String messageName,Integer resourceId);
	
	/**
	 * 获取所有合同的信息
	 * 
	 * @return
	 */
	public List<ContractBackup> getAllContract();
	
	/**
	 * 根据合同编号获取合同信息
	 * 
	 * @return
	 */
	public ContractBackup getContract(String contractCode);
	
	/**
	 * 根据合同编号获取素材路径
	 * 
	 * @return
	 */
	public String getMetarialPath(String contractId);
	
}
