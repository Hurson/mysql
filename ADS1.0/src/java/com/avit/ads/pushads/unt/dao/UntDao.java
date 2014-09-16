package com.avit.ads.pushads.unt.dao;

import java.util.List;

import com.avit.ads.pushads.unt.bean.AdsLink;
import com.avit.ads.pushads.unt.bean.AdsPicture;
import com.avit.ads.pushads.unt.bean.AdsSubtitle;
import com.avit.ads.pushads.unt.bean.ResourceUrl;
import com.avit.ads.pushads.unt.bean.UntConfigFile;

public interface UntDao {
	
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
	
	public void setConfigFile(UntConfigFile configFile);
    public void addResourceUrl(ResourceUrl resourceUrl);
}
