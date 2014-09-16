package com.avit.ads.pushads.video.service;

public interface VideoPumpService {

	/**
	 * 发送文件到OCG系统
	 * @param areaCode 区域ID
	 * @param 源目文件
	 * @param 目标文件
	 */
	public void sendFile(String sourceFile,String targetPath);
	/**
	 * 发送文件到OCG系统
	 * @param areaCode 区域ID
	 * @param 源目录
	 * @param 目标目录
	 */
	public void sendPath(String sourcePath,String targetPath);
	
	
}
