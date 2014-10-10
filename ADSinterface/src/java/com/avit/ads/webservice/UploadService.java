package com.avit.ads.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

// TODO: Auto-generated Javadoc
/**
 * The Interface UploadService.
 */
@WebService
public interface UploadService {

	//public String SendFile(String fileName,String uploadType);
	/**
	 * upload file.
	 *
	 * @param fileName 带完整路径的文件名
	 * @param adsTypeCode 广告位编码   上传类型 1代表上传到投放子系统，2代表上传到HttpServer,3代表上传到VideoPump
	 * @return 1 成功  0失败 
	 */
	public String deleteFile(String fileName,String adsTypeCode);
	
	/**
	 * Send file.
	 *
	 * @param resourceFile 上载结构体
	 * @return 返回 上载文件名
	 */
	public String sendFile(@WebParam(name="resourceFile") ResourceFile resourceFile);
	
	/**
	 * 获取广告位投放提前量  秒.
	 *
	 * @param adsTypeCode 广告位编码 
	 * @return  
	 */
	public String getPreSecond(String adsTypeCode);
	
	
	/**
	 * DTV 订单审核时调用. 
	 * @param areaCode 区域编码
	 * @param sourcefile 原文件
	 * @param targetpath 目标目录
	 * @return 1 成功  0失败 
	 */
	public String sendFileDTV(String areaCode,String sourcefile,String targetpath);
	/**
	 * 非开机，DTV视频素材审核时调用.
	 *
	 * @param sourcefile 原文件
	 * @param targetpath 目标目录
	 * @return 1 成功  0失败 
	 */
	public String sendFileVideoPump(String sourcefile,String targetpath);
	/**
	 * 双向图片素材审核时调用.
	 *
	 * @param sourcefile 原文件
	 * @param targetpath 目标目录
	 * @return 1 成功  0失败 
	 */
	public String sendFtpFileToCps(String sourcefile,String targetpath);
}
