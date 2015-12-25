package com.avit.ads.dtmb.service;


public interface DOcgService {
	
	/**
	 * 建立FTP连接
	 */
	public boolean connectFtpServer(String ip, int port, String username, String password);
	
	/**
	 * 删除目录下所有文件
	 */
	public void deleteFtpDirFiles(String dirPath);
	
	/*
	 * 删除目录下指定文件
	 */
	public void deleteFtpFileIfExist(String fileName, String remoteDir);
	
	
	/**
	 * 上传目录下所有文件到FTP
	 */
	public void sendDirFilesToFtp(String localDirPath, String remotDirPath);
	
	
	/**
	 * 上传文件到FTP
	 */
	public void sendFileToFtp(String localFilePath, String remotDirPath);
	
	
	/**
	 * 断开FTP连接
	 */
	public void disConnectFtpServer();
	
	
	/**
	 * OCG投放广告
	 */
	public boolean startOcgPlayByIp(String ip, String sendPath, String sendType, String adsType);
	
	/**
	 * UNT更新通知集成到OCG，
	 * @param areaCode
	 * @param sendType
	 * @param filename
	 * @param uiId
	 * @return
	 */
	
	public boolean sendDtmbUntUpdateByAreaCode(int sendType, Object message, String areaCode, String tsId);
	
}
