package com.dvnchina.advertDelivery.utils.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.constant.TemplateConstant;
import com.dvnchina.advertDelivery.utils.ResourceServerConstant;
import com.dvnchina.advertDelivery.utils.ResourceServerConstant.Serverinfo;

/**
 * 资源同步ftp发布
 *
 */
public class ResourceSyncUtil extends FtpBase {
	
	private static Logger logger = Logger.getLogger(ResourceSyncUtil.class);

	public ResourceSyncUtil(String ip, int port, String username,
			String password) throws IOException {
		super(ip, port, username, password);
	}

	/**
	 * 发送文件到资源服务器上
	 * @param fromFile
	 * @param toDir
	 */
	public static void sendAFile2ResourceServer(String fromFile, String toDir) {
		List<Serverinfo> serverInfoList = ResourceServerConstant.getInstance().loadServerinfoS();
		for (Serverinfo server : serverInfoList) {
			String ip = server.getIp();
			int port = server.getPort();
			String username = server.getUsername();
			String password = server.getPassword();
			ResourceSyncUtil util = null;
			try {
				util = new ResourceSyncUtil(ip, port, username, password);
				util.sendAFileToFtp(fromFile, server.getPath() + "/" + toDir);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				util.closeFTP();
			}
		}
	}

	/**
	 * 发送文件流到资源服务器上
	 * @param stream
	 * @param toDir
	 * @param fileName
	 */
	public static void sendAStreamResourceServer(InputStream stream,
			String toDir, String fileName) {
		List<Serverinfo> serverInfoList = ResourceServerConstant.getInstance()
				.loadServerinfoS();
		for (Serverinfo server : serverInfoList) {
			String ip = server.getIp();
			int port = server.getPort();
			String username = server.getUsername();
			String password = server.getPassword();
			ResourceSyncUtil util = null;
			try {
				util = new ResourceSyncUtil(ip, port, username, password);
				util.sendAStreamToFtp(stream, server.getPath() + "/" + toDir,
						fileName);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				util.closeFTP();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void sendATemplateFilePathResourceServer(String stream,
			String toDir, String fileName, List<Map> utils) throws Exception {
		for (Map map : utils) {
			ResourceSyncUtil util = null;
			try {
				util = (ResourceSyncUtil) map.get(TemplateConstant.UTIL);
				Serverinfo server = (Serverinfo) map
						.get(TemplateConstant.SERVER);
				File file = new File(stream);
				util.sendATemplateFileToFtp(file, server.getPath() + "/"
						+ toDir, fileName);
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception();
			} finally {
				// util.closeFTP();
			}
		}
	}

	private boolean sendATemplateFileToFtp(File stream, String romteFileDir,
			String fileName) throws Exception {
		String workingDir = FtpClient.printWorkingDirectory();
		logger.info("资源服务器当前目录是=" + workingDir + " 发送的路径为=" + romteFileDir + "/" + fileName);
		romteFileDir = this.getPathRegular(romteFileDir);
		// 进入远程相应路径(1:进入一级子目录2:进入多级子目录)
		if (!workingDir.equals(romteFileDir)) {
			if (!romteFileDir.contains("/"))
				this.makeDirectory(romteFileDir);
			else
				this.changeDirectory(romteFileDir);
		}
		InputStream is = new FileInputStream(stream);
		String fn = romteFileDir + "/" + fileName;

		boolean rs = FtpClient.deleteFile(fn);
		int status = FtpClient.getReplyCode();
		boolean flag = FtpClient.storeFile(fileName, is);
		logger.info("ip地址是=" + FtpClient.getRemoteAddress() + " 端口号是=" + FtpClient.getRemotePort());
		if (flag) {
			logger.info("发送" + fn + "成功");
		} else {
			logger.info("发送" + fn + "失败");
		}
		if (is != null) {
			is.close();
		}
		return flag;
	}

	private boolean sendAStreamToFtp(InputStream stream, String romteFileDir,
			String fileName) throws Exception {
		String workingDir = FtpClient.printWorkingDirectory();
		romteFileDir = this.getPathRegular(romteFileDir);
		// 进入远程相应路径(1:进入一级子目录2:进入多级子目录)
		if (!workingDir.equals(romteFileDir)) {
			if (!romteFileDir.contains("/"))
				this.makeDirectory(romteFileDir);
			else
				this.changeDirectory(romteFileDir);
		}
		FtpClient.printWorkingDirectory();
		boolean flag = FtpClient.storeFile(fileName, stream);
		if (stream != null) {
			stream.close();
		}
		return flag;
	}

	/**
	 * @param localFilePath
	 * @param romteFileDir
	 * @return true发送成功 false发送失败
	 * @throws Exception
	 */
	public boolean sendAFileToFtp(String localFilePath, String romteFileDir)
			throws Exception {
		String workingDir = FtpClient.printWorkingDirectory();
		romteFileDir = this.getPathRegular(romteFileDir);
		// 进入远程相应路径(1:进入一级子目录2:进入多级子目录)
		if (!workingDir.equals(romteFileDir)) {
			if (!romteFileDir.contains("/"))
				this.makeDirectory(romteFileDir);
			else
				this.changeDirectory(romteFileDir);
		}
		File file = new File(localFilePath);
		InputStream is = new FileInputStream(file);
		logger.debug(file.getName());
		boolean flag = FtpClient.storeFile(file.getName(), is);
		is.close();
		return flag;
	}
	
	public boolean sendFileToFtp(File stream, String romteFileDir,
			String fileName) throws Exception {
		String workingDir = FtpClient.printWorkingDirectory();
		logger.info("资源服务器当前目录是=" + workingDir + " 发送的路径为=" + romteFileDir + "/" + fileName);
		romteFileDir = this.getPathRegular(romteFileDir);
		// 进入远程相应路径(1:进入一级子目录2:进入多级子目录)
		if (!workingDir.equals(romteFileDir)) {
			if (!romteFileDir.contains("/"))
				this.makeDirectory(romteFileDir);
			else
				this.changeDirectory(romteFileDir);
		}
		InputStream is = new FileInputStream(stream);
		String fn = romteFileDir + "/" + fileName;

		boolean rs = FtpClient.deleteFile(fn);
		int status = FtpClient.getReplyCode();
		boolean flag = FtpClient.storeFile(fileName, is);
		logger.info("ip地址是=" + FtpClient.getRemoteAddress() + " 端口号是=" + FtpClient.getRemotePort());
		if (flag) {
			logger.info("发送" + fn + "成功");
		} else {
			logger.info("发送" + fn + "失败");
		}
		if (is != null) {
			is.close();
		}
		return flag;
	}
}
