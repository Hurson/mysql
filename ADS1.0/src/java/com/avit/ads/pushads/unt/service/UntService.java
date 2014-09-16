package com.avit.ads.pushads.unt.service;

import java.util.List;

import com.avit.ads.pushads.unt.bean.AdsLink;
import com.avit.ads.pushads.unt.bean.AdsPicture;
import com.avit.ads.pushads.unt.bean.AdsSubtitle;
import com.avit.ads.pushads.unt.bean.ResourceUrl;
import com.avit.ads.pushads.unt.bean.UntConfigFile;

public interface UntService {
	
	/**
	 * 添加推荐链接
	 * @param linkList
	 */
	public void addLink(List<AdsLink> linkList);
	
	/**
	 * 添加单向广告图片
	 * @param pictureList
	 */
	public void addPicture(List<AdsPicture> pictureList);
	
	/**
	 * 添加滚动字幕
	 * @param subtitleList
	 */
	public void addSubtitle(List<AdsSubtitle> subtitleList);
	
	/**
	 * 设置广告配置文件名 ，路径 
	 * @param configFile
	 * 
	 */
	public void setConfigFile(UntConfigFile configFile);
	
	/**
	 * 发送更新通知.	
	 * @param updateType 更新标识 
	 * @param filename 更新的文件名包括相对路径  1：字幕  2：推荐链接 3：配置文件 
	 */
	public void sendUpdateFlag(String updateType,String filename);
	
	public boolean sendUpdateFlag(String areaCode, String updateType,String filename);
	
	 public void addResourceUrl(ResourceUrl resourceUrl);
}
