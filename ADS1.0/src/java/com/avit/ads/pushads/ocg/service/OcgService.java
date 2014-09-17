package com.avit.ads.pushads.ocg.service;

public interface OcgService {
	
	/**
	 * 建立FTP连接
	 */
	public boolean connectFtpServer(String areaCode);
	
	/**
	 * 删除目录下所有文件
	 */
	public void deleteFtpDirFiles(String dirPath);
	
	
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
	public boolean startOcgPlay(String areaCode, String sendPath, String sendType);
	
	
	//public boolean sendUiDesc(String area)
	
	/**
	 * 发送文件到OCG系统
	 * @param areaCode 区域ID
	 * @param 源目文件
	 * @param 目标文件
	 */
	public boolean sendFile(String areaCode,String sourceFile,String targetPath);
	/**
	 * 发送文件到OCG系统
	 * @param areaCode 区域ID
	 * @param 源目文件
	 * @param 目标文件
	 */
	public boolean deleteFile(String areaCode,String sourceFile,String targetPath);
	/**
	 * 发送文件到OCG系统
	 * @param areaCode 区域ID
	 * @param 源目录
	 * @param 目标目录
	 */
	public boolean sendPath(String areaCode,String sourcePath,String targetPath);
	
	
	
	/**
	 * 发送文件到OCG系统
	 * @param fileName 文件名
	 * @param adsTypeCode 广告位编码
	 * @param areaCode 区域编码   标清initPic-a.iframe initVideo-a.ts   高清initPic-b.iframe initVideo-b.ts
	 */
//	public void sendFile(String fileName,String adsTypeCode,String adsIdentification,String areaCode);
	
	/**
	 * 发送文件到OCG系统
	 * @param fileName 文件名
	 * @param adsTypeCode 广告位编码
	 * @param areaCode 区域编码   标清initPic-a.iframe initVideo-a.ts   高清initPic-b.iframe initVideo-b.ts
	 */
//	public void sendFile(String fileName,String adsTypeCode,String adsIdentification,String areaCode,String contentType);
	
	/**
	 * 删除文件
	 * @param fileName 文件名
	 * @param adsTypeCode 广告位编码
	 * @param areaCode 区域编码
	 */
//	public void deleteFile(String fileName,String adsTypeCode,String adsIdentification,String areaCode);
	/**
	 * 删除文件
	 * @param fileName 文件名
	 * @param adsTypeCode 广告位编码
	 * @param areaCode 区域编码
	 */
//	public void deleteFile(String fileName,String adsTypeCode,String adsIdentification,String areaCode,String contentType);
	
	/**
	 * 启动OCG发送
	 * 
	 */
	public void startPlay(String areaCode,String name);
	/**
	 * 设置OCG更新标识.
	 *
	 * @param updateType 1:开机画面 (initPic.iframe)更新   5:开机视频或动画（initVideo.ts)更新
	 */
//	public void startOcg(String updateType );
	/**
	 * 启动OCG发送
	 * 
	 */
//	public void startPlayPgm(String pgmname,String outputname);
	
	/**
	 * 分区域启动OCG发送
	 * 
	 */
	public void startPlayPgm(String areaCode, String pgmname,String outputname);
	
	
	
	/**
	 * 
	 * 从OCG下载文件夹到本地系统，和原始文件进行比较，作为startPlay之前的校验
	 * @param areaCode 区域ID
	 * @param 源目录
	 * @param 目标目录
	 */
	public void downloadDir(String areaCode, String savePath, String serverPath);
	
	
	public void downloadFile(String areaCode, String savePath, String serverPath);
	
	/**
	 * UNT更新通知集成到OCG，
	 * @param areaCode
	 * @param sendType
	 * @param filename
	 * @param uiId
	 * @return
	 */
	public boolean sendUNTMessageUpdate(String areaCode,int sendType,Object message);
	
	
	
	
	
	
	
	
	
	
	
}
