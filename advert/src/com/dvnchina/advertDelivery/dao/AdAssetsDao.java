package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.AdAssetsForm;
import com.dvnchina.advertDelivery.model.AdvertPositionType;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.ImageReal;
import com.dvnchina.advertDelivery.model.MessageMeta;
import com.dvnchina.advertDelivery.model.MessageReal;
import com.dvnchina.advertDelivery.model.Resource;
import com.dvnchina.advertDelivery.model.ResourceReal;
import com.dvnchina.advertDelivery.model.VideoMeta;
import com.dvnchina.advertDelivery.model.VideoReal;


public interface AdAssetsDao {
	
	/**
	 * 
	 * 批量删除素材
	 */
	public int deleteResourceAbatch(Integer id,Integer resourceType);
	
	/**
	 * 通过广告位Id 得到记录
	 * 
	 */
	public ResourceReal getResourceRealByAdvertPositionId(Integer id);
	
	
	/**
	 * 通过广告位id 得到记录
	 * @param id
	 * @return
	 */
	public Resource getResourceByAdvertPositionId(Integer id);
	
	/**
	 * 通过主键得到广告所对应的广告位类型
	 */
	public AdvertPositionType getAdvertPositionTypeById(Integer id);
	
	/**
	 * 查询维护期表单中的结果集 
	 * @return
	 */
	public List listAdAssestsMgr(Resource resource, int x, int y ,String state,List<Integer> cIds);
	
	/**
	 * 查询运行期表单中的结果集
	 * @return
	 */
	public List listAdAssestsMgrReal(ResourceReal resourceReal, int x, int y ,String state);

	/**
	 * 得到维护期表单中的总的记录数
	 * @return
	 */
	public int getAdContentCount(Resource resource,String state,List<Integer> cIds);
	
	/**
	 * 得到运行期表单中的总的记录数
	 * @return
	 */
	public int getAdContentCountReal(ResourceReal resourceReal,String state);
	
	
	/**
	 * 删除运行表单中的资源信息
	 * @return
	 */
	public void deleteResourceReal(ResourceReal resourceReal);
	
	/**
	 * 删除运行表单中的图片信息
	 * @return
	 */
	public void deleteImageMetaReal(ImageReal imageReal);
	
	/**
	 * 删除运行表单中的视频信息
	 * @return
	 */
	public void deleteVideoMetaReal(VideoReal videoRal);
	
	/**
	 * 删除运行表单中的文字信息
	 * @return
	 */
	public void deleteMessageMetaReal(MessageReal messageReal);
	
	/**
	 * 删除维护表单中的资源信息
	 * @return
	 */
	public void deleteResource(Resource resource);
	
	/**
	 * 删除维护表单中的图片信息
	 * @return
	 */
	public void deleteImageMeta(ImageMeta imageMeta);
	
	/**
	 *删除维护表单中的视频信息 
	 * @return
	 */
	public void deleteVideoMeta(VideoMeta videoMeta);
	
	/**
	 * 删除维护表单中的文字信息
	 * @return
	 */
	public void deleteMessageMeta(MessageMeta messageMeta);
	
	/**
	 * 添加文字信息到图片维护表单
	 * @return
	 */
	public void insertImagMetaInfo(ImageMeta imageMeta);
	
	/**
	 * 添加文字信息到视频维护表单
	 * @return
	 */
	public void insertVideoMetaInfo(VideoMeta videoMeta);
	
	/**
	 * 添加文字信息到文字维护表单
	 * @return
	 */
	public void insertMessageMetaInfo(MessageMeta messageMeta);
	
	/**
	 * 通过id获得运行表资源信息
	 * @return
	 */
	public ResourceReal getResourceRealById(Integer id);
	
	/**
	 * 通过id获得运行表图片信息
	 * @return
	 */
	public ImageReal getImageMetaRealById(Integer id);
	
	/**
	 * 通过id获得运行表视频信息
	 * @return
	 */
	public VideoReal getVideoMetaRealById(Integer id);
	
	/**
	 * 通过id获得运行表文字信息
	 * @return
	 */
	public MessageReal getMessageMetaRealById(Integer id);
	
	/**
	 * 通过id获得维护表资源信息
	 * @return
	 */
	public Resource getResourceById(Integer id);
	
	/**
	 * 通过id获得维护表图片信息
	 * @return
	 */
	public ImageMeta getImageMetaById(Integer id);
	
	/**
	 * 通过id获得维护表视频信息
	 * @return
	 */
	public VideoMeta getVideoMetaById(Integer id);
	
	/**
	 * 通过id获得维护表文字信息
	 * @return
	 */
	public MessageMeta getMessageMetaById(Integer id);
	
	/**
	 * 更新修改完成维护期表资源信息
	 * @return
	 */
	public void updateResource(Resource resource);
	
	/**
	 * 更新修改完成运行期表资源信息
	 * @return
	 */
	public void updateResourceReal(ResourceReal resourceReal);
	
	
	/**
	 * 更新修改完成维护期表图片信息
	 * @return
	 */
	public void updateImageMeta(ImageMeta imageMeta);
	
	/**
	 * 更新修改完成维护期表视频信息
	 * @return
	 */
	public void updateVideoMeta(VideoMeta videoMeta);
	
	/**
	 * 更新修改完成维护期表文字信息
	 * @return
	 */
	public void updateMessageMeta(MessageMeta messageMeta);

	/**
	 * 审核通过添加资源信息到资源运行期表单
	 * @return
	 */
	public void insertAuditResourceReal(ResourceReal resourceReal);
	
	/**
	 * 审核通过添加图片信息到图片运行期表单
	 * @return 
	 */
	public void insertAuditImageReal(ImageReal imageReal);
	
	/**
	 * 审核通过添加视频信息到视频运行期表单 
	 * @return
	 */
	public void insertAuditVideoReal(VideoReal videoReal);
	
	/**
	 * 审核通过添加文字信息到文字运行期表单
	 * @return
	 */
	public void insertAuditMessageReal(MessageReal messageReal);
	
}

