package com.avit.ads.pushads.cps.service;

public interface CpsService {

	/**
	 * 发送本地文件到OCG系统
	 * @param areaCode 区域ID
	 * @param 源目文件
	 * @param 目标文件
	 */
	public boolean sendLocalFile(String sourceFile,String targetPath);
	
	/**
	 * 发送FTP文件到OCG系统
	 * @param areaCode 区域ID
	 * @param 源目文件
	 * @param 目标文件
	 */
	public void sendFtpFile(String sourceFile,String targetPath);
	/**
	 * 发送文件到OCG系统
	 * @param areaCode 区域ID
	 * @param 源目录
	 * @param 目标目录
	 */
	public void sendPath(String sourcePath,String targetPath);
	
	/**
	 * 启动CPS发送
	 * 
	 */
	public void startCps();
}
