package com.dvnchina.advertDelivery.service;

import java.util.List;

import com.dvnchina.advertDelivery.model.AdvertPositionType;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.ImageReal;
import com.dvnchina.advertDelivery.model.MessageMeta;
import com.dvnchina.advertDelivery.model.MessageReal;
import com.dvnchina.advertDelivery.model.Resource;
import com.dvnchina.advertDelivery.model.ResourceReal;
import com.dvnchina.advertDelivery.model.VideoMeta;
import com.dvnchina.advertDelivery.model.VideoReal;


public interface AdAssetsService {
	
	/**
	 * 
	 * 批量删除素材
	 */
	public int deleteResourceAbatch(Integer id,Integer resourceType);
	
	/**
	 * 上线通过审核
	 * 
	 */
	public int upResourceMetra(Integer id,Integer resorceId,Integer resourceType,String upOpintions);
	
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
	 * 对已经上线的图片素材，进行下线操作
	 * 
	 */
	public int deleteDownImageMeta(Integer id);
	
	/**
	 * 对已经上线的视频素材，进行下线操作
	 * 
	 */
	public int deleteDownVideoMeta(Integer id);
	
	/**
	 * 对已经上线的文字素材，进行下线操作
	 * 
	 */
	public int deleteDownMessageMeta(Integer id);
	
	
	/**
	 * 
	 * 删除通过审核的资产素材，修改其在维护期表中的状态
	 * 
	 */
	public int deleteAuditResource(Integer id);
	
	/**
	 * 查询资源维护期表结果集 
	 * @return
	 */
	public List listAdAssestsMgr(Resource resource,int x,int y,String state);
	
	/**
	 * 查询资源运行期表结果集 
	 * @return
	 */
	public List listAdAssestsMgrReal(ResourceReal resourceReal,int x,int y,String state);
	
	/**
	 * 得到维护期表总的记录数
	 * @return
	 */
	public int getAdContentCount(Resource resource,String state);
	
	/**
	 * 得到运行期表总的记录数
	 * @return
	 */
	public int getAdContentCountReal(ResourceReal resourceReal,String state);
	
	/**
	 * 通过 id 删除资源运行期表单中记录
	 * @return
	 */
	public int deleteResourceRealById(Integer id);
	
	/**
	 * 通过 id 删除图片运行期表单中记录
	 * @return
	 */
	public int deleteImageMetaRealById(Integer id);
	
	/**
	 * 通过 id 删除视频运行期表单中记录
	 * @return
	 */
	public int deleteVideoMetaRealById(Integer id);
	
	/**
	 * 通过 id 删除文字运行期表单中记录
	 * @return
	 */
	public int deleteMessageMetaRealById(Integer id); 
	
	/**
	 * 通过 id 删除资源维护期表单中记录
	 * @return
	 */
	public int deleteResourceById(Integer id);
	
	/**
	 *通过 id 删除图片维护期表单中记录 
	 * @return
	 */
	public int deleteImageMetaById(Integer id);
	
	/**
	 * 通过 id 删除视频维护期表单中记录
	 * @return
	 */
	public int deleteVideoMetaById(Integer id);
	
	/**
	 * 通过 id 删除文字维护期表单中记录
	 * @return
	 */
	public int deleteMessageMetaById(Integer id); 
	
	/**
	 * 添加图片信息到图片维护表单 
	 * @return
	 */
	public int insertImagMetaInfo(ImageMeta imageMeta);
	
	/**
	 * 添加视频信息到视频维护表单
	 * @return
	 */
	public int insertVideoMetaInfo(VideoMeta videoMeta);

	/**
	 * 添加文字信息到文字维护表单
	 * @return
	 */
	public int insertMessageMetaInfo(MessageMeta messageMeta);
	
	/**
	 * 通过id获得运行期表资源信息
	 * @return
	 */
	public ResourceReal getResourceRealById(Integer id);
	
	/**
	 * 通过id获得运行期表图片信息
	 * @return
	 */
	public ImageReal getImageMetaRealById(Integer id);
	
	/**
	 * 通过id获得运行期表视频信息
	 * @return
	 */
	public VideoReal getVideoMetaRealById(Integer id);
	
	/**
	 * 通过id获得运行期表文字信息
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
	 * 更新修改完成维护表资源信息 
	 * @return
	 */
	public int updateResource(Resource resource);
	
	/**
	 * 更新修改完成维护表图片信息
	 * @return
	 */
	public int updateImageMeta(ImageMeta imageMeta);
	
	/**
	 * 更新修改完成维护表视频信息
	 * @return
	 */
	public int updateVideoMeta(VideoMeta videoMeta);
	
	/**
	 * 更新修改完成维护表文字信息
	 * @return
	 */
	public int updateMessageMeta(MessageMeta messageMeta);
	
	/**
	 * 审核时，更新资源维护期素材表单方法
	 * @return
	 */
	public int insertAuditResource(Integer id,Integer resourceId,String examinationOpintions);
	
	/**
	 * 更新图片素材表单审核通过的方法
	 * @return
	 */
	public int insertAuditImageMetas(Integer id,Integer resourceId,String examinationOpintions,String clientCode,String contractNumber);
	
	/**
	 * 更新视频素材表单审核通过的方法 
	 * @return
	 */
	public int insertAuditVideoMetas(Integer id,Integer resourceId,String examinationOpintions,String clientCode,String contractNumber);
	
	/**
	 * 更新文字素材表单审核通过的方法 
	 * @return
	 */
	public int insertAuditMessageMetas(Integer id,Integer resourceId,String examinationOpintions,String clientCode,String contractNumber);
	
	/**
	 * 更新图片素材表单没有审核通过的方法
	 * @return
	 */
	public int updateNoAuditImageMetas(Integer id,String examinationOpintions);
	
	/**
	 * 更新视频素材表单没有审核通过的方法
	 * @return
	 */
	public int updateNoAuditVideoMetas(Integer id,String examinationOpintions);
	
	/**
	 * 更新文字素材表单没有审核通过的方法
	 * @return
	 */
	public int updateNoAuditMessageMetas(Integer id,String examinationOpintions);
	
}



