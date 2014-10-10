package com.dvnchina.advertDelivery.service.common.tools.support;

import java.io.File;
import java.util.Map;

/**
 * 封装操作本地文件中的内容
 * @author lester
 *
 */
public interface OperatorLocalFileService {
	
	/**
	 * 拷贝本地文件至指定目录
	 * @param file
	 * @param targetDirectory
	 * @param targetFileName
	 * @return
	 */
	public String copyLocalFile2TargetDirectory(File file,String targetDirectory,String targetFileName);
	
	/**
	 * 拷贝本地文件至指定目录
	 * @param file
	 * @param targetDirectory
	 * @param targetFileName
	 * @return
	 */
	public String copyLocalFile2TargetDirectory(String localFilePath,String targetDirectory, String targetFileName);
	/**
	 * 拷贝本地文件至指定目录
	 * @param localFilePath
	 * @param targetDirectory
	 * @param targetFileName
	 * @return
	 */
	public String copyLocalFile2TargetDirectory(String localFilePath,String targetDirectory,String targetFileName,Map param);
	/**
	 * 拷贝本地文件至指定目录  不修改文件名
	 * @param localFilePath
	 * @param targetDirectory
	 * @param targetFileName
	 * @return
	 */
	public String copyLocalFileTargetDirectory(String localFilePath,
			String targetDirectory, String targetFileName, Map param);
	/**
	 * 创建本地目录
	 * @param localDirectory 本地目录
	 */
	public String mkdirLocalDirectory(String localDirectory);
}
