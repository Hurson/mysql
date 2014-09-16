package com.avit.ads.pushads.unt.bean;

import java.io.Serializable;

/**
 * UNT配置文件设置信息
 *
 */
public class UntConfigFile implements Serializable{
	
	private static final long serialVersionUID = -240480328095738311L;
	private String adsConfigFilename;//配置文件名
	private String adsConfigFilepath;//配置文件路径
	public String getAdsConfigFilename() {
		return adsConfigFilename;
	}
	public void setAdsConfigFilename(String adsConfigFilename) {
		this.adsConfigFilename = adsConfigFilename;
	}
	public String getAdsConfigFilepath() {
		return adsConfigFilepath;
	}
	public void setAdsConfigFilepath(String adsConfigFilepath) {
		this.adsConfigFilepath = adsConfigFilepath;
	}
	

}
