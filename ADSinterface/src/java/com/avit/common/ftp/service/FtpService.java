package com.avit.common.ftp.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public interface FtpService {
	
	/**
	 * 发送文件到资源服务器上
	 * @param fromFile
	 * @param toDir
	 * @param systemName
	 */
	public boolean sendAFile2ResourceServer(String fromFile, String toDir);
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
	public boolean sendAFilePath2ResourceServer(String fromDir, String toDir);
	
	
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
	public boolean downloadDir(String remoteDirectory,String localDirectory);
	
	public String getLatestFileName(String remoteDirectory);
	
	public List<String> getLatestFiles(String remoteDirectory);
	
	public boolean downloadFile(String remoteFileName, String localFileName,String remoteDirectory,String localDirectory);
	
	public boolean downloadFile(String remoteAbsoluteFilePath, String localFileName, String localDirectory);
	
	public List<String> getNvodFiles(String remoteDirectory);
	
	
}
