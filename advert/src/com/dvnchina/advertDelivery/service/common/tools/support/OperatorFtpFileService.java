package com.dvnchina.advertDelivery.service.common.tools.support;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;

/**
 * 封装操作FTP中的内容
 * @author lester
 *
 */
public interface OperatorFtpFileService {
	/**
	 * 获取连接
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 * @return
	 */
	public FTPClient connection(String ip,String port,String username,String password);
	/**
	 * 关闭连接
	 * @return
	 */
	public String close(FTPClient ftpClient);
	/**
	 * 创建目录
	 * @param directory
	 * @return
	 */
	public String makeDirectory(String directory);
	/**
	 * 上传文件
	 * @param file
	 * @param remotePath
	 * @return
	 */
	public String uploadFile(File file,String remotePath);
	/**
	 * 上传文件
	 * @param is
	 * @param remotePath
	 * @return
	 */
	public String uploadFile(InputStream is,String remotePath);
	/**
	 * 上传文件
	 * @param file
	 * @param remotePath
	 * @return
	 */
	public String uploadFile(File file,String remotePath,String fileName);
	/**
	 * 上传文件
	 * @param is
	 * @param remotePath
	 * @return
	 */
	public String uploadFile(InputStream is,String remotePath,String fileName);
	/**
	 * 下载文件
	 * @param remotePath
	 * @param localPath
	 * @return
	 */
	public String downloadFile(String remotePath,String localPath);
	/**
	 * 下载文件
	 * @param remocePath
	 * @param localPath
	 * @param fileName
	 * @return
	 */
	public String downloadFile(String remocePath,String localPath,String fileName);
	/**
	 * 删除文件
	 * @param filePath
	 */
	public String removeFile(String filePath);
	/**
	 * 批量删除文件
	 * @param filePath
	 * @return
	 */
	public String batchRemoveFile(List<String> filePath);
	/**
	 * 获取指定远程路径中的结构
	 * @return
	 */
	public String getDirectoryPath(String remotePath);
	/**
	 * 将当前文件拷贝到其他Ftp中
	 * @return
	 */
	public String copyFtpFile2OtherFtp(File file,Map map);
	/**
	 * 将当前文件拷贝到其他Ftp中
	 * @return
	 */
	public String copyFtpFile2OtherFtp(InputStream file,Map map);
	/**
	 * 将多个文件拷贝到其他Ftp中
	 * @return
	 */
	public String copyFtpFile2OtherFtp(List<File> file,Map map);
}

