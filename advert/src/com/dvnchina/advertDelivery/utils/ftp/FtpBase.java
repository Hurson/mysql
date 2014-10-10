package com.dvnchina.advertDelivery.utils.ftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.constant.TemplateConstant;

/**
 * ftp基类
 * 
 */
public class FtpBase {
	static Logger logger = Logger.getLogger(FtpBase.class);

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
		logger.debug("去掉头部的特殊字符");
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
		logger.debug("去掉尾部的特殊字符");
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
				FtpClient.setControlEncoding(TemplateConstant.ENCODING);
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
		this.changeDirectory(romteFileDir);
		boolean flag = FtpClient.deleteFile(name);
		return flag;
	}

	
	protected FTPClient connectServerWithException() throws Exception {
		// boolean flag = true;
		if (FtpClient == null) {
			int reply;
			FtpClient = new FTPClient();
			FtpClient.setControlEncoding(TemplateConstant.ENCODING);
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
	protected boolean changeDirectory(String dir) throws Exception {
		FtpClient.changeWorkingDirectory("/");
		int start = 0;
		int end = -1;
		String tempdir = null;
		try {
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
			logger.error(e);
			return false;
		}
		return true;
	}

	/**
	 * 关闭FTP连接
	 */
	public void closeFTP() {
		try {
			if (FtpClient != null && FtpClient.isConnected()) {
				FtpClient.disconnect();
				FtpClient = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}