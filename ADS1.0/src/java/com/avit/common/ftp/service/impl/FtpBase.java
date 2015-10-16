package com.avit.common.ftp.service.impl;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.net.ftp.FtpClient;

/**
 * ftp基类
 * 
 */
public class FtpBase {
	static Logger logger = LoggerFactory.getLogger(FtpBase.class);
	private static final String ENCODING = "UTF-8";

	protected String ip;
	protected int port = 21;
	protected String username;
	protected String password;
	protected String workingDirectory;
	protected FTPClient FtpClient = null;

	public FtpBase() {
		super();
	}

	public FtpBase(String ip, String username, String password) {
		// TODO Auto-generated constructor stub
		this.ip = ip;
		this.username = username;
		this.password = password;
		try {
			this.FtpClient = this.connectServer();
			workingDirectory = FtpClient.printWorkingDirectory();
			FtpClient.setFileTransferMode(2);
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		}
	}

	public FtpBase(String ip, int port, String username, String password)
			throws IOException {
		// TODO Auto-generated constructor stub
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
		try {
			this.FtpClient = this.connectServer();
			workingDirectory = FtpClient.printWorkingDirectory();
			FtpClient.setFileTransferMode(2);
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
			throw e;
		}
	}

	/**
	 * @param path
	 * @return 去掉//或者\ 开始或结尾 的字符
	 */
	public String getPathRegular(String path) {
		int length = path.length();
		if (length <= 0)
			return path;
		char ch = path.charAt(0);
	//	logger.debug("去掉头部的特殊字符");
		while (ch == '/' || ch == '\\') {
			try {
				path = path.substring(1, length);
			} catch (Exception e) {
				return path;
			}
			length = path.length();
			if (length <= 0)
				return path;
			try {
				ch = path.charAt(0);
			} catch (Exception e) {
				return path;
			}
		}
	//	logger.debug("去掉尾部的特殊字符");
		try {
			ch = path.charAt(length - 1);
		} catch (Exception e) {
		}
		while (ch == '/' || ch == '\\') {
			length = path.length();
			if (length <= 0) {
				return path;
			}
			try {
				path = path.substring(0, length - 1);
				ch = path.charAt(length - 2);
			} catch (Exception e) {
				return path;
			}
		}
		path = path.replaceAll("//", "/");
		path = path.replaceAll("///", "/");
		path = path.replaceAll("////", "/");
		path = path.replaceAll("/////", "/");
		return path;
	}

	/**
	 * 连接到服务器
	 * 
	 * @return 连接成功返回ftpclient
	 * @throws IOException
	 */
	protected FTPClient connectServer() throws IOException {
		// boolean flag = true;
		if (FtpClient == null) {
			int reply;
			try {

				FtpClient = new FTPClient();
				FtpClient.setControlEncoding(ENCODING);
				FtpClient.setDefaultPort(port);
				FtpClient.setConnectTimeout(10000);
				FtpClient.connect(this.ip);
				FtpClient.login(this.username, this.password);

				FtpClient.setBufferSize(1024);
				FtpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

				reply = FtpClient.getReplyCode();
				FtpClient.setDataTimeout(120000);

				if (!FTPReply.isPositiveCompletion(reply)) {
					FtpClient.disconnect();
					logger.warn("ftp连接拒绝");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("登录ftp服务器" + this.ip + "】失败");
			}

		}
		return FtpClient;
	}

	/**
	 * 删除ftp上的一个文件，name是文件名，不包含路径。 romteFileDir是此文件在ftp上的路径。不带“\”开头
	 * 
	 * @param name
	 * @param romteFileDir
	 * @return
	 * @throws Exception
	 */
	public boolean deleAFileOnFtp(String name, String romteFileDir)
			throws Exception {
		String workingDir = FtpClient.printWorkingDirectory();
		romteFileDir = this.getPathRegular(romteFileDir);
		if (!workingDir.equals(romteFileDir)) {
			if (!romteFileDir.contains("/"))
				this.makeDirectory(romteFileDir);
			else
				this.changeDirectory(romteFileDir);
		}
		boolean flag = FtpClient.deleteFile(name);
		return flag;
	}

	protected FTPClient connectServerWithException() throws Exception {
		// boolean flag = true;
		if (FtpClient == null) {
			int reply;
			FtpClient = new FTPClient();
			FtpClient.setControlEncoding("GBK");
			FtpClient.setDefaultPort(port);
			FtpClient.connect(this.ip);
			FtpClient.login(this.username, this.password);
			FtpClient.setDefaultPort(port);

			FtpClient.setBufferSize(1024);
			FtpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

			reply = FtpClient.getReplyCode();
			FtpClient.setDataTimeout(120000);
			if (!FTPReply.isPositiveCompletion(reply)) {
				FtpClient.disconnect();
				logger.debug("ftp连接拒绝");
				throw new Exception("ftp连接拒绝");
			}
		}
		return FtpClient;
	}

	/**
	 * 在服务器上创建一个文件夹
	 * 
	 * @param dir
	 * @return
	 */
	protected void makeDirectory(String dir) throws Exception {
		FtpClient.changeWorkingDirectory("/");
		FtpClient.makeDirectory(dir);
		FtpClient.changeWorkingDirectory(dir);

	}

	/**
	 * 进入ftp的子目录，支持多级目录
	 * 
	 * @param dir
	 * @return
	 */
	protected boolean changeDirectory(String dir){
		
		int start = 0;
		int end = -1;
		String tempdir = null;
		try {
			FtpClient.changeWorkingDirectory("/");
			while ((end = dir.indexOf("/", start)) > 0) {
				tempdir = dir.substring(start, end);
				// FtpClient.changeWorkingDirectory(tempdir);
				start = end + 1;
				FtpClient.makeDirectory(tempdir);
				FtpClient.changeWorkingDirectory(tempdir);
			}
			if (start > 0) {
				tempdir = dir.substring(start);
				// FtpClient.changeWorkingDirectory(tempdir);
				FtpClient.makeDirectory(tempdir);
				FtpClient.changeWorkingDirectory(tempdir);
			}
		} catch (Exception e) {
			logger
					.error(
							"***** FtpBase changeDirectory occur a exception : {} ***** ",
							e);
			return false;
		}
		return true;
	}
	
   protected boolean changeDirectoryIsExsits(String dir){
		
		int start = 0;
		int end = -1;
		String tempdir = null;
		boolean flag = false;
		try {
			FtpClient.changeWorkingDirectory("/");
			while ((end = dir.indexOf("/", start)) > 0) {
				tempdir = dir.substring(start, end);
				start = end + 1;
				flag = FtpClient.changeWorkingDirectory(tempdir);
			}
			if (start > 0) {
				tempdir = dir.substring(start);
				flag = FtpClient.changeWorkingDirectory(tempdir);
			}
		} catch (Exception e) {
			logger.error("***** FtpBase changeDirectory occur a exception : {} ***** ",e);
			return false;
		}
		return flag;
	}
	
	

	/**
	 * 关闭FTP连接
	 */
	public void closeFTP() {
		try {
			if (FtpClient != null) {
				FtpClient.disconnect();
				FtpClient = null;
			}
		} catch (Exception e) {
			logger.error(
					"***** FtpBase closeFTP occur a exception : {} ***** ", e);
		}
	}
	public void setServer(String ip, int port, String username, String password) throws IOException {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
		
		closeFTP();

		this.FtpClient = this.connectServer();
		workingDirectory = FtpClient.printWorkingDirectory();
		FtpClient.setFileTransferMode(2);
		
	}
	
	/*
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
	
	
	public boolean downloadToLocal(String remoteFileName, String localFileName,String remoteDirectory,String localDirectory) {
        boolean flag = false;
        File outfile = null;
        OutputStream outputStream = null;
                    
        //outfile = new File(localDirectory+SEPARATOR+localFileName);
        outfile = new File(".././"+localFileName);
        try {
            
            outputStream = new FileOutputStream(outfile);
            FtpClient.changeWorkingDirectory(remoteDirectory);
            flag = FtpClient.retrieveFile(remoteFileName, outputStream);
            } catch (IOException e) {
                    logger.error("下载文件时出现IO异常，远程文件名为：" + remoteFileName, e);
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


        return flag;
    }
	*/
	
	
	public static void main(String[] args) {
		
		String dir = "/usr/sbin";
		//String mkDir = "/root/advertres/bbb/ccc";
		
	
			FtpBase ftp = null;
			try {
				ftp = new FtpBase("192.168.2.224", 21, "root", "111111");
			
				FTPClient client = ftp.getFtpClient();
			
				System.out.println( client.changeWorkingDirectory(dir) );
			
				System.out.println(	client.printWorkingDirectory() );
				
//				String f1 = "1.txt";
//				String f2 = "2.txt";
//				
//				System.out.println(  client.deleteFile(f1) );
//				System.out.println(  client.deleteFile(f2) );
			
				for(String fileName : client.listNames()){
					System.out.println(fileName);
				}
			} catch (IOException e) {
			
				e.printStackTrace();
			}
//		 System.out.println(	client.printWorkingDirectory() );
//		 mkDir = ftp.getPathRegular(mkDir);
//		 System.out.println(mkDir);
//		 ftp.changeDirectory(mkDir);
		// System.out.println(	 );
			
			
		
	}

	public FTPClient getFtpClient() {
		return FtpClient;
	}
}