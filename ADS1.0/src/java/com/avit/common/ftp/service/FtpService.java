package com.avit.common.ftp.service;

import java.io.IOException;
import java.io.InputStream;


public interface FtpService {
	
	/**
	 * 发送文件到资源服务器上
	 * @param fromFile
	 * @param toDir
	 * @param systemName
	 */
	public void sendAFile2ResourceServer(String fromFile, String toDir);
	/**
	 * 发送文件到资源服务器上
	 * @param fromFile
	 * @param toDir
	 * @param systemName
	 */
	public void sendAFile2ResourceServer(InputStream is,String fileName, String toDir);
	
	/**
	 * 发送文件夹下所有文件到资源服务器上
	 * @param fromFile
	 * @param toDir
	 * @param systemName
	 */
	public void sendAFilePath2ResourceServer(String fromDir, String toDir);
	
	public boolean sendAFileToFtp(String localFilePath, String romteFileDir) throws Exception;
	
	public void disConnectFtpServer();
	
	/**
	 * 删除资源服务器上的文件
	 * @param fileName
	 * @param fileDir
	 * @param systemName
	 */
	public void deleteFile(String fileName, String fileDir);
	
	/**
	 * 重命名远程文件
	 * @param fromFile
	 * @param toFile
	 * @param systemName
	 */
	public void rename(String path,String fromFile, String toFile);
	
	/**
	 * 删除目录下所有文件
	 * @param dir
	 * @param systemName
	 */
	public void deleteDirFile(String dir);
	public void setServer(String ip, int port, String username, String password)
	throws IOException ;
	public boolean download(String remoteFileName, String localFileName,String remoteDirectory,String localDirectory);
}
