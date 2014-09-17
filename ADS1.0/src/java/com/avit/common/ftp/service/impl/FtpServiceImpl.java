package com.avit.common.ftp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.InitConfig;
import com.avit.common.ftp.service.FtpService;

@Service("FtpService")
@Scope("prototype")
public class FtpServiceImpl extends FtpBase implements FtpService{
	
	public FtpServiceImpl(){
	}
	
	public FtpServiceImpl(String ip, int port, String username,
			String password) throws IOException {
		super(ip, port, username, password);
	}
	
	/**
	 * 发送文件到资源服务器上
	 * @param fromFile 发送文件
	 * @param toDir    目标目录
	 * @param systemName  系统名
	 */
	public void sendAFile2ResourceServer(String fromFile, String toDir){
		
		try {
			sendAFileToFtp(fromFile, toDir);
		} catch (IOException e) {
			logger.error("***** FtpServiceImpl sendAFile2ResourceServer occur a exception : {} ***** ", e);
		} catch (Exception e) {
			logger.error("***** FtpServiceImpl sendAFile2ResourceServer occur a exception : {} ***** ", e);
		} finally {
		}
	}

	public void sendAFilePath2ResourceServer(String fromDir, String toDir)
	{
		 File a=new File(fromDir);
		  String[] file=a.list();
		  File temp=null;
		  String filename;
		  for (int i = 0; i < file.length; i++) 
		  {
			  
			  if(fromDir.endsWith(File.separator))
			  {
				  temp=new File(fromDir+file[i]);
				  filename =fromDir+file[i];
			  }
			  else
			  { 
				  temp=new File(fromDir+File.separator+file[i]);
				  filename =fromDir+File.separator+file[i];
			  }
			  if(temp.isFile())
			  { 
				  try {
						sendAFileToFtp(filename, toDir);
				  } catch (Exception e) {
					logger.error("***** FtpServiceImpl sendAFile2ResourceServer occur a exception : {} ***** ", e);
				  } finally {
				  }
			  }
		  }
	}
	

	
	/**
	 * @param localFilePath
	 * @param romteFileDir
	 * @return true发送成功 false发送失败
	 * @throws Exception
	 */
	public boolean sendAFileToFtp(String localFilePath, String romteFileDir) throws Exception {
		String workingDir = FtpClient.printWorkingDirectory();
		romteFileDir = this.getPathRegular(romteFileDir);
		if (!workingDir.equals(romteFileDir)) {
			if (!romteFileDir.contains("/"))
				this.makeDirectory(romteFileDir);
			else
				this.changeDirectory(romteFileDir);
		}
		File file = new File(localFilePath);
		InputStream is = new FileInputStream(file);

		//如已存在先删除
		try {
			FtpClient.deleteFile(file.getName());
		} catch (IOException e) {
			logger.info("删除FTP上的文件【"+file.getName()+"】时发生错误：", e);
		}

		boolean flag = FtpClient.storeFile(file.getName(), is);
		is.close();
		return flag;
	}
	/**
	 * 发送文件到资源服务器上
	 * @param fromFile
	 * @param toDir
	 * @param systemName
	 */
	public void sendAFile2ResourceServer(InputStream  is,String fileName,String toDir)
	{
		try
		{
			String workingDir = FtpClient.printWorkingDirectory();
			toDir = this.getPathRegular(toDir);
			if (!workingDir.equals(toDir)) {
				if (!toDir.contains("/"))
					this.makeDirectory(toDir);
				else
					this.changeDirectory(toDir);
			}
			//File file = new File(localFilePath);			
			//InputStream is = new FileInputStream(fromFile);
			logger.debug(fileName);
			boolean flag = FtpClient.storeFile(fileName, is);
			is.close();
		}
		catch (IOException e) {
			logger.error("***** FtpServiceImpl sendAFile2ResourceServer occur a exception : {} ***** ", e);
		} catch (Exception e) {
			logger.error("***** FtpServiceImpl sendAFile2ResourceServer occur a exception : {} ***** ", e);
		} finally {
		}
	}
	
	/**
	 * 删除资源服务器上的文件
	 * @param fileName
	 * @param fileDir
	 */
	public void deleteFile(String fileName, String fileDir){
		
		try {
			deleAFileOnFtp(fileName, fileDir);
		} catch (IOException e) {
			logger.error("***** FtpServiceImpl deleteFile occur a exception : {} ***** ", e);
		} catch (Exception e) {
			logger.error("***** FtpServiceImpl deleteFile occur a exception : {} ***** ", e);
		} finally {
			
		}
	}
	
	/**
	 * 重命名远程文件
	 * @param fromFile
	 * @param toFile
	 * @param systemName
	 */
	public void rename(String path,String fromFile, String toFile){
		
		try {
			//FtpClient.changeWorkingDirectory(path);
			String workingDir = FtpClient.printWorkingDirectory();
			path = this.getPathRegular(path);
			if (!workingDir.equals(path)) {
				if (!path.contains("/"))
					this.makeDirectory(path);
				else
					this.changeDirectory(path);
			}			
			FtpClient.rename(fromFile, toFile);
		} catch (IOException e) {
			logger.error("***** FtpServiceImpl rename occur a exception : {} ***** ", e);
		} catch (Exception e) {
			logger.error("***** FtpServiceImpl rename occur a exception : {} ***** ", e);
		} finally {
		}
	}
	
	/**
	 * 删除目录下所有文件
	 * @param dir
	 * @param systemName
	 */
	public void deleteDirFile(String dir){
		
		try {
			FTPFile[] f = FtpClient.listFiles(dir);
			int size  = f.length;
			for (int i = 0; i < size; i++) {
				FtpClient.deleteFile(dir+f[i].getName());
			}
		} catch (IOException e) {
			logger.error("***** FtpServiceImpl deleteDirFile occur a exception : {} ***** ", e);
		} catch (Exception e) {
			logger.error("***** FtpServiceImpl deleteDirFile occur a exception : {} ***** ", e);
		} finally {
		}
	}
	
	/**
	 * 获取FTP服务
	 * @param systemName
	 * @return
	 * @throws IOException
	 */
	private FtpServiceImpl getFtpService(String systemName)
			throws IOException {
		FtpServiceImpl ftp = null;
		if(ConstantsHelper.SYSTEM_OCG.equals(systemName)){
			String ip = InitConfig.getConfigMap().get("ocg_ftp_ip");
			int port = Integer.parseInt(InitConfig.getConfigMap().get("ocg_ftp_port"));
			String username = InitConfig.getConfigMap().get("ocg_ftp_username");
			String password = InitConfig.getConfigMap().get("ocg_ftp_password");
			ftp = new FtpServiceImpl(ip, port, username, password);
		}else if(ConstantsHelper.SYSTEM_CPS.equals(systemName)){
			String ip = InitConfig.getConfigMap().get("cps_ftp_ip");
			int port = Integer.parseInt(InitConfig.getConfigMap().get("cps_ftp_port"));
			String username = InitConfig.getConfigMap().get("cps_ftp_username");
			String password = InitConfig.getConfigMap().get("cps_ftp_password");
			ftp = new FtpServiceImpl(ip, port, username, password);
		}
		return ftp;
	}
	public void setServer(String ip, int port, String username, String password)
	throws IOException {
		super.setServer(ip, port, username, password);
	}
	
	public void disConnectFtpServer() {
		closeFTP();
	}

	/**
	 * 下载文件至本地目录中 
	 * @param remoteFileName
	 *            远程文件名
	 * @param localFileName
	 *            本地文件名
	 * @param remoteDirectory 远程FTP目录
	 * @param localDirectory 本地目录
	 * @return 返回操作结果
	 * @author lester
	 */
	public boolean download(String remoteFileName, String localFileName,String remoteDirectory,String localDirectory) {
		boolean flag = false;
		File outfile = null;
		OutputStream outputStream = null;
		
		if (StringUtils.isNotBlank(remoteFileName)&&remoteFileName.contains(".")) {
			
			if (StringUtils.isNotBlank(localDirectory)) {
				if (StringUtils.isNotBlank(remoteDirectory)) {
					remoteDirectory = getPathRegular(remoteDirectory);
					if (!changeDirectory(remoteDirectory)) {
						logger.info("切换远程目录失败，请检查所传路径是否正确，具体路径为"
								+ remoteDirectory);
						return flag;
					}
				} else {
					logger.info("传入的远程路径为空，将在FTP宿主目录中寻找文件");
				}
				
				if (StringUtils.isNotBlank(localFileName)) {
					outfile = new File(localDirectory+File.separator+localFileName);
				} else {
					outfile = new File(localDirectory+File.separator+remoteFileName);
				}
				
				try {
					outputStream = new FileOutputStream(outfile);
					
					flag = FtpClient.retrieveFile(remoteFileName, outputStream);
				} catch (IOException e) {
					logger.error("下载文件时出现IO异常，远程路劲为：" + remoteFileName, e);
					flag = false;
				} finally {
					try {
						if (outputStream != null) {
							outputStream.close();
						}
					} catch (IOException e) {
						logger.error("关闭输出流时出现异常！", e);
						flag = false;
					}
				}
			}else{
				logger.info("传入的本地路径为空，将不予下载");
			}
		}else{
			logger.info("远程文件名为空或者不是文件类型，将不予下载");
		}
		return flag;
	}
}
