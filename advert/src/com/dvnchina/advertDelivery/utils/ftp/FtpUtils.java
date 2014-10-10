/*
 * Copyright 2008 Digital Video Networks (Beijing) Co., Ltd. All rights reserved.
 */
package com.dvnchina.advertDelivery.utils.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.utils.ConfigureProperties;


/**
 * 
 * <p>
 * Title:FTP 工具类
 * </p>
 * <p>
 * Description:用于上传或下载文件
 * </p>
 * 
 * @author lester
 * @version 1.0
 * @since 2012-03-05
 */

public class FtpUtils{

	private static Logger logger = Logger.getLogger(FtpUtils.class);

	private static ConfigureProperties configureProperties = ConfigureProperties
			.getInstance();

	// 二进制模式 此种类型在传输过程中大小不会发生变化
	public static final int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE;
	// ASCII模式 此种类型在传输过程中大小会发生变化
	public static final int ASCII_FILE_TYPE = FTP.ASCII_FILE_TYPE;

	// 盘符分隔符
	private static String SEPARATOR = System.getProperty("file.separator");

	// 从配置文件中获取FTP的IP
	private String ftpIp = null;
	// 从配置文件中读取连接FTP超时信息
	private Integer timeout = 120000;
	// ftp用户名
	private String ftpUserName = null;
	// ftp密码
	private String ftpPassWord = null;
	// ftp端口
	private Integer ftpPort = null;

	// 显示执行过程开关
	private String showOperationCommandKey = null;

	private FTPClient ftpClient = null;

	/**
	 * 初始化一些连接参数
	 * 
	 * @author lester
	 */
	public FtpUtils() {
		ftpClient = new FTPClient();
		// 重新获取配置文件中的参数值
		ftpIp = configureProperties.get("ftp.ip");
		ftpUserName = configureProperties.get("ftp.username");
		ftpPassWord = configureProperties.get("ftp.password");
		try {
			ftpPort = Integer.parseInt(configureProperties.get("ftp.port"));
		} catch (Exception e) {
			logger.error("从配置文件中获取端口时发生异常，请检查配置", e);
		}

		try {
			if(StringUtils.isNotBlank(configureProperties.get("ftpTimeout"))){
				timeout = Integer.parseInt(configureProperties.get("ftpTimeout")) * 1000;
			}
		} catch (Exception e) {
			logger.error("从配置文件中获取超时时间时时发生异常，请检查配置", e);
		}

		//showOperationCommandKey = getPropertiesFromConfiguration("showOperationCommand");

		if (StringUtils.isNotBlank(showOperationCommandKey)
				&& showOperationCommandKey.equalsIgnoreCase("yes")) {
			// 设置将过程中使用到的命令输出到控制台
			this.ftpClient.addProtocolCommandListener(new PrintCommandListener(
					new PrintWriter(System.out)));
		}

	}
	
	/**
	 * 构造方法2
	 * @author lester
	 * @param ip	
	 * @param port
	 * @param userName
	 * @param passWord
	 * @throws IOException 
	 */
	public FtpUtils(String ip,int port, String userName,String passWord) throws IOException {
		
	}

	/**
	 * 替换路径头尾特殊字符以及分隔符处理
	 * 
	 * @param path
	 * @author lester
	 */
	public String getRegularPath(String path) {
		int length;
		if (StringUtils.isNotBlank(path)) {
			length = path.length();
			if (length <= 0)
				return path;
			char ch = path.charAt(0);

			while (ch == '/' || ch == '\\') {
				try {
					path = path.substring(1, length);
				} catch (Exception e) {
					logger.error("字符串截取时发生异常", e);
					return path;
				}
				length = path.length();
				if (length <= 0)
					return path;
				try {
					ch = path.charAt(0);
				} catch (Exception e) {
					logger.error("字符串截取时发生异常", e);
					return path;
				}
			}

			try {
				ch = path.charAt(length - 1);
			} catch (Exception e) {
				logger.error("字符串截取时发生异常", e);
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
					logger.error("字符串截取时发生异常", e);
					return path;
				}
			}
			if (path.contains("\\")) {
				path = path.replace("\\", "/");
			}
			path = path.replaceAll("//", "/");
			path = path.replaceAll("///", "/");
			path = path.replaceAll("////", "/");
			path = path.replaceAll("/////", "/");
		}
		return path;
	}

	/**
	 * 创建FTP连接，创建连接相关参数从配置文件中获取
	 * 
	 * @author lester
	 */
	public boolean connectionFtp() {
		boolean flag = true;

		int reply;
		try {
			ftpClient.setControlEncoding("GBK");
			ftpClient.connect(ftpIp);
			ftpClient.login(ftpUserName, ftpPassWord);
			ftpClient.setDefaultPort(ftpPort);
			ftpClient.setBufferSize(1024);
			ftpClient.setFileType(FtpUtils.BINARY_FILE_TYPE);
			ftpClient.setDataTimeout(timeout);
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				flag = false;
				logger.warn("FTP 服务拒绝连接");
			}
			// 设置被动模式
			ftpClient.enterLocalPassiveMode();
		} catch (Exception e) {
			flag = false;
//			logger.error("连接FTP时出现异常", e);
			logger.info("连接FTP时出现异常,请检查ftp的连接配置");
		}
		return flag;
	}

	/**
	 * 创建FTP连接
	 * 
	 * @param ftpIp
	 *            FTP ip地址
	 * @param port
	 *            FTP 端口号
	 * @param username
	 *            FTP 用户名
	 * @param password
	 *            FTP 密码
	 * @param timeout
	 *            FTP 超时时间 单位秒
	 * @return true- 成功 false-失败
	 */
	public boolean connectionFtp(String ftpIp, int port, String username,
			String password, Integer timeout) {
		boolean flag = false;
		this.ftpIp = ftpIp;
		this.ftpPort = port;
		this.ftpUserName = username;
		this.ftpPassWord = password;
		this.timeout = timeout;

		flag = connectionFtp();

		if (flag) {
			logger.info("FTP连接成功");
		} else {
			logger.info("FTP连接失败");
		}
		return flag;
	}

	/**
	 * 关闭FTP连接
	 * @author lester
	 */
	public void closedFtp() {
		if (ftpClient != null && ftpClient.isConnected()) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
				logger.error("关闭FTP连接时出现异常", e);
			}
		}
	}

	/**
	 * 进入到服务器的某个目录下
	 * @param directory
	 * @author lester
	 */
	public boolean changeWorkingDirectory(String directory) {
		boolean flag = false;
		try {
			flag = ftpClient.changeWorkingDirectory(directory);
		} catch (IOException e) {
			logger.error("切换目录失败", e);
		}
		return flag;
	}

	/**
	 * 发送文件到ftp上
	 * @param localFilePath
	 * @param romteFileDir
	 * @return
	 * @throws Exception
	 */
	public boolean sendFileToFtp(String localFilePath, String romteFileDir) throws Exception {
		romteFileDir = getRegularPath(romteFileDir);
		String workingDir = ftpClient.printWorkingDirectory();
		
		if (!workingDir.equals(romteFileDir)) {
			if (!romteFileDir.contains("/")) {
				makeDirectory(romteFileDir);
			} else {
				changeDirectory(romteFileDir);
			}
		}
		File file = new File(localFilePath);
		InputStream is = new FileInputStream(file);
		boolean flag = ftpClient.storeFile(file.getName(), is);
		String resultDesc;
		if (flag) {
			resultDesc = file.getAbsolutePath() + " send to " + this.ftpIp + " success";
		} else {
			resultDesc = file.getAbsolutePath() + " send to " + this.ftpIp + " error";
		}
		logger.info(resultDesc);
		is.close();
		return flag;
	}
	
	protected void makeDirectory(String dir) throws Exception {
		ftpClient.changeWorkingDirectory("/");
		ftpClient.makeDirectory(dir);
		ftpClient.changeWorkingDirectory(dir);

	}
	
	protected boolean changeDirectory(String dir) throws Exception {
		ftpClient.changeWorkingDirectory("/");
		int start = 0;
		int end = -1;
		String tempdir = null;
		try {
			while ((end = dir.indexOf("/", start)) > 0) {
				tempdir = dir.substring(start, end);
				// FtpClient.changeWorkingDirectory(tempdir);
				start = end + 1;
				ftpClient.makeDirectory(tempdir);
				ftpClient.changeWorkingDirectory(tempdir);
			}
			if (start > 0) {
				tempdir = dir.substring(start);
				// FtpClient.changeWorkingDirectory(tempdir);
				ftpClient.makeDirectory(tempdir);
				ftpClient.changeWorkingDirectory(tempdir);
			}
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	
	public boolean createDirecoty(String path)throws Exception{
		boolean flag = true;
		path=getRegularPath(path);
		String workingDir = ftpClient.printWorkingDirectory();
		try {
			if (!workingDir.equals(path)) {
				if (!path.contains("/")) {
					makeDirectory(path);
				} else {
					changeDirectory(path);
				}
			}
		} catch (Exception e) {
			logger.error("创建目录时出现异常",e);
		}
		return flag;
	}

	/**
	 * 删除一个FTP服务器上的目录（如果为空）
	 * 
	 * @param path
	 *            目录路径
	 * @author lester
	 */
	public boolean removeDirectory(String path) {
		boolean flag = false;
		if (ftpClient == null) {
			connectionFtp();
		}
		try {
			flag = ftpClient.removeDirectory(path);
		} catch (Exception e) {
			logger.error("删除目录时出现异常，具体目录路径为：" + path, e);
		}
		return flag;
	}

	/**
	 * 删除一个FTP服务器上的目录
	 * @param path
	 *            目录路径
	 * @param isAll
	 *            是否递归删除所有子目录和文件 true 递归 false 不递归
	 * @author lester
	 */
	public boolean removeDirectory(String path, boolean isAll) {
		// 遍历子目录和文件
		FTPFile[] ftpFileArr = null;
		if (StringUtils.isNotBlank(path)) {
			logger.info("递归参数为true，将递归删除文件夹中内容");
			path = getRegularPath(path);
			if (!isAll) {
				return removeDirectory(path);
			}

			try {
				ftpFileArr = ftpClient.listFiles(path);
			} catch (Exception e) {
				logger.error("获取路径下文件列表时出现异常，具体路径为:" + path, e);
			}

			if (ftpFileArr == null || ftpFileArr.length == 0) {
				return removeDirectory(path);
			}

			for (int i = 0; i < ftpFileArr.length; i++) {
				FTPFile ftpFile = ftpFileArr[i];
				if (ftpFile != null) {
					String name = ftpFile.getName();
					if (ftpFile.isDirectory() && !".".equals(name)
							&& !"..".equals(name)) {
						removeDirectory(path + "/" + name, true);
					} else if (ftpFile.isFile() && !".".equals(name)
							&& !"..".equals(name)) {
						deleteFile(path + "/" + name);
					} else if (ftpFile.isSymbolicLink()) {

					} else if (ftpFile.isUnknown()) {

					}
				}
			}
		} else {
			logger.info("传入的待删除路径为空！");
		}
		return removeDirectory(path);
	}
	
	public boolean removeRemoteDirectory(String path) {
	 // 遍历子目录和文件
        FTPFile[] ftpFileArr = null;
        if (StringUtils.isNotBlank(path)) {

            
            try {
                ftpClient.changeWorkingDirectory(path);
                ftpFileArr = ftpClient.listFiles(path);
            } catch (Exception e) {
                logger.error("获取路径下文件列表时出现异常，具体路径为:" + path, e);
            }

            if (ftpFileArr == null || ftpFileArr.length == 0) {
                return removeDirectory(path);
            }

            for (int i = 0; i < ftpFileArr.length; i++) {
                FTPFile ftpFile = ftpFileArr[i];
                if (ftpFile != null) {
                    String name = ftpFile.getName();
                    if (ftpFile.isDirectory() && !".".equals(name)
                            && !"..".equals(name)) {
                        removeRemoteDirectory(path + "/" + name);
                    } else if (ftpFile.isFile() && !".".equals(name)
                            && !"..".equals(name)) {
                        deleteFile(path + "/" + name);
                    } else if (ftpFile.isSymbolicLink()) {

                    } else if (ftpFile.isUnknown()) {

                    }
                }
            }
        } else {
            logger.info("传入的待删除路径为空！");
        }
        return removeDirectory(path);
	}

	/**
	 * 删除一个文件
	 * @param filename 待删除的文件名称
	 * @author lester
	 */
	public boolean deleteFile(String filename) {
		boolean flag = true;

		try {
			flag = ftpClient.deleteFile(filename);
			if (flag) {
				logger.info("删除文件成功！");
			} else {
				logger.info("删除文件失败！");
			}
		} catch (IOException ioe) {
			logger.error("", ioe);
		}
		return flag;
	}

	/**
	 * 返回远程FTP给定目录下的文件列表--所有的
	 * @param 		path				FTP 文件目录
	 * @param 		resultList			文件夹树下的所有叶子（文件）
	 * @author 		lester
	 * @return
	 */
	public List<String> getFileList(String path, List<String> resultList) { 
		FTPFile[] ftpFiles = null;
		List<String> list = null;

		try {
			if (StringUtils.isNotBlank(path)) {
				path = getRegularPath(path);
				logger.info("当前目录：###"+path);
				if (changeWorkingDirectory(path)) {
					ftpFiles = ftpClient.listFiles();
					if (resultList == null) {
						list = new LinkedList<String>();
					}
					if (ftpFiles == null || ftpFiles.length == 0) {
						return resultList;
					}
					for (int i = 0; i < ftpFiles.length; i++) {
						FTPFile ftpFile = ftpFiles[i];
						if (ftpFile != null && ftpFile.isFile()
								&& !".".equals(ftpFile.getName())
								&& !"..".equals(ftpFile.getName())) {
							if (resultList!=null) {
								resultList.add(ftpFile.getName());
							}else{
								list.add(ftpFile.getName());
							}
						} else if (ftpFile != null && ftpFile.isDirectory()
								&& !".".equals(ftpFile.getName())
								&& !"..".equals(ftpFile.getName())) {
							changeWorkingDirectory("~/");
							if (resultList!=null) {
								getFileList(path + SEPARATOR
										+ ftpFile.getName(), resultList);
							}else{
								getFileList(path + SEPARATOR
										+ ftpFile.getName(), list);
							}
						}
					}

				} else {
					logger.info("切换目录失败，请检查路径");
				}
			} else {
				logger.info("所查询的条件为空，将不予查询");
			}
		} catch (Exception e) {
			logger.error("获取路径" + path + "时出现异常！", e);
		}
		return list;
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
					remoteDirectory = getRegularPath(remoteDirectory);
					if (!changeWorkingDirectory(remoteDirectory)) {
						logger.info("切换远程目录失败，请检查所传路径是否正确，具体路径为"
								+ remoteDirectory);
						return flag;
					}
				} else {
					logger.info("传入的远程路径为空，将在FTP宿主目录中寻找文件");
				}
				
				if (StringUtils.isNotBlank(localFileName)) {
					outfile = new File(localDirectory+SEPARATOR+localFileName);
				} else {
					outfile = new File(localDirectory+SEPARATOR+remoteFileName);
				}
				
				try {
					outputStream = new FileOutputStream(outfile);
					flag = ftpClient.retrieveFile(remoteFileName, outputStream);
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
	
	/**
	 * 
	 * @description: 远程FTP文件下载到本地
	 * @param remoteFileName 远程文件名
	 * @param localFileName  本地文件名
	 * @param remoteDirectory 远程文件路径
	 * @param localDirectory  本地文件路径
	 * @return 
	 * boolean
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-16 下午03:14:34
	 */
	public boolean downloadToLocal(String remoteFileName, String localFileName,String remoteDirectory,String localDirectory) {
        boolean flag = false;
        File outfile = null;
        OutputStream outputStream = null;
                    
        //outfile = new File(localDirectory+SEPARATOR+localFileName);
        outfile = new File(".././"+localFileName);
        try {
            
            outputStream = new FileOutputStream(outfile);
            ftpClient.changeWorkingDirectory(remoteDirectory);
            flag = ftpClient.retrieveFile(remoteFileName, outputStream);
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
	
	/**
	 * 
	 * @description: 本地文件上传到远程FTP
	 * @param localFilePath 本地文件路径
	 * @param remoteDirectoryPath 远程目录
	 * @param remoteFileName 远程文件名
	 * @return 
	 * boolean
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-16 下午03:35:50
	 */
	public boolean uploadFileToRemote(String localFilePath,
            String remoteDirectoryPath, String remoteFileName) {

        File localFile = null;
        FileInputStream fis = null;
        boolean uploadFlag = false;

            localFile = new File(localFilePath);  
            if (localFile.isFile()) {

                try {

                    fis = new FileInputStream(localFile);
                    ftpClient.changeWorkingDirectory(remoteDirectoryPath);

                    uploadFlag = ftpClient.storeFile(remoteFileName, fis);
                        
                        if (uploadFlag) {
                            logger.info(remoteFileName + "文件上传成功");
                        }

                    ftpClient.changeWorkingDirectory("~/");
                } catch (Exception e) {
                    logger.error("读取本地文件失败！", e);
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            logger.error("读取本地文件后，关闭IO时发生异常", e);
                        }
                    }
                }
            } else {
                logger.info("传入不合法的本地文件地址");
            }

        return uploadFlag;
    }
	
	
	/**
	 * 上传单个文件
	 * @param localFilePath
	 *            待上传本地文件
	 * @param remoteDirectoryPath
	 *            远程目录
	 * @param remoteFileName
	 *            上传后的文件名称 若没有则取原有文件名称
	 * @author lester
	 */ 
	public boolean uploadSimpleFile(String localFilePath,
			String remoteDirectoryPath, String remoteFileName) {
		Integer flag = null;
		File localFile = null;
		FileInputStream fis = null;
		boolean uploadFlag = false;
		boolean renameFlag = false;
		String localFileName = null;
		// 1、检查传入参数是否为空
		if (StringUtils.isNotBlank(localFilePath)) {
			localFile = new File(localFilePath);  //localFilePath= F:\2.jpg
			if (localFile.isFile()) {

				try {
					localFileName = localFile.getName();  //localFileName = 2.jpg
					fis = new FileInputStream(localFile); //localFile = F:\2.jpg
					if (StringUtils.isNotBlank(remoteDirectoryPath)) {  //remoteDirectoryPath = new
						//方法getRegularPath(),替换路径头尾特殊字符以及分隔符处理
						remoteDirectoryPath = getRegularPath(remoteDirectoryPath); // remoteDirectoryPath = new
						if (!changeAndCreateWorkingDirectory(remoteDirectoryPath)) {
							logger.warn("创建或者切换目录失败，请检查ftp用户访问权限");
						}
					} else {
						logger.info("远程目录为空，文件将被传到用户宿主目中");
					}

					if (StringUtils.isNotBlank(remoteFileName)) { //remoteFileName = 3.jpg
						
					//	FtpClient.enterLocalPassiveMode();
						
						uploadFlag = ftpClient.storeFile(remoteFileName, fis); //fis 获得 F:\2.jpg 的流
						
					//	uploadFlag = FtpClient.storeFile(new String(remoteFileName.getBytes("UTF-8"),"iso-8859-1"),fis);
						
						if (uploadFlag) {
							logger.info(remoteFileName + "文件上传成功");
							renameFlag = ftpClient.rename(remoteFileName, remoteFileName);
							if (renameFlag) {
								logger.info(remoteFileName + "文件重命名成功");
							}

						}
					} else {
						uploadFlag = ftpClient.storeFile(
								localFileName + ".bak", fis);

						if (uploadFlag) {
							renameFlag = ftpClient.rename(localFileName, localFileName);

							if (renameFlag) {
								logger.info(localFileName + "文件重命名成功");
							}
						}
					}
					ftpClient.changeWorkingDirectory("~/");
				} catch (Exception e) {
					logger.error("读取本地文件失败！", e);
				} finally {
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							logger.error("读取本地文件后，关闭IO时发生异常", e);
						}
					}
				}
			} else {
				logger.info("传入不合法的本地文件地址");
			}
		} else {
			logger.info("传入的本地文件地址为空");
		}
		return renameFlag;
	}
	
	public boolean copyRemoteFile(String srcFilePath,String srcFileName,
            String remoteDirectoryPath, String remoteFileName) throws IOException {

        FileInputStream fis = null;
        boolean uploadFlag = false;

        // 1、检查传入参数是否为空
        if (StringUtils.isNotBlank(srcFilePath)) {

            srcFilePath=srcFilePath+"/"+srcFileName; 
            InputStream s =  ftpClient.retrieveFileStream(srcFilePath);
            OutputStream o=null;

                try {


                    if (StringUtils.isNotBlank(remoteFileName)) { 
                        
                        remoteDirectoryPath=remoteDirectoryPath+"/"+remoteFileName;

                        while(!ftpClient.completePendingCommand()){
                            System.out.println("sssssssssssssssssssssssssssssssssssssssssssssssss");
                        }

                        o =  ftpClient.storeFileStream(remoteDirectoryPath);
                        
                        byte[] buffer = new byte[16 * 1024];
                        int len = 0;
                        int bytesum = 0;
                        while ((len = s.read(buffer)) > 0) {
                            bytesum += len;
                           o.write(buffer, 0, len);
                       }

                       
                        uploadFlag=true;

                    }
                    ftpClient.changeWorkingDirectory("~/");
                } catch (Exception e) {
                    logger.error("读取本地文件失败！", e);
                } finally {
                    if (s != null) {
                        try {
                            s.close();
                            o.close();
                        } catch (IOException e) {
                            logger.error("读取本地文件后，关闭IO时发生异常", e);
                        }
                    }
                }
           
        } else {
            logger.info("传入的本地文件地址为空");
        }
        return uploadFlag;
    }
	
	   /**
	    * 
	    * @description: FTP文件重命名 
	    * @param srcFilePath:原文件路径
	    * @param srcFileName:原文件名
	    * @param remoteDirectoryPath:目标文件路径
	    * @param remoteFileName:目标文件名
	    * @throws IOException 
	    * void
	    *
	    * @author: wangfei@avit.com.cn
	    * @date: 2013-5-23 下午03:55:42
	    */
	   public void renameRemoteFile(String srcFilePath,String srcFileName,
	            String remoteDirectoryPath, String remoteFileName) throws IOException {
	       boolean tag =  ftpClient.changeWorkingDirectory(srcFilePath);
	       srcFilePath=srcFilePath+"/"+srcFileName;
	       remoteDirectoryPath=remoteDirectoryPath+"/"+remoteFileName;
	       
	       try {
	           if(tag){
	               boolean ss =  ftpClient.rename(srcFilePath, remoteDirectoryPath);
	               System.out.println("重命名结果:"+ss);
	           }
            
           
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

 
	   }
	
	/**
	 * 创建或者切换目录
	 * @param ftpDirectory
	 *            远程ftp路径
	 * @author lester
	 */
	public boolean changeAndCreateWorkingDirectory(String ftpDirectory) {
		String[] remoteDirectory = null;
		boolean createFlag = false;
		boolean channelFlag = false;
		if (StringUtils.isNotBlank(ftpDirectory)) {

			ftpDirectory = getRegularPath(ftpDirectory);
			if (ftpDirectory.contains("/")) {
				remoteDirectory = ftpDirectory.split("/");
			}
			try {
				if (!ftpClient.changeWorkingDirectory(ftpDirectory)) {

					if (remoteDirectory != null) {
						for (int i = 0; i < remoteDirectory.length; i++) {
							if (!ftpClient
									.changeWorkingDirectory(remoteDirectory[i])) {
								createFlag = ftpClient
										.makeDirectory(remoteDirectory[i]);
								channelFlag = ftpClient
										.changeWorkingDirectory(remoteDirectory[i]);
							}
						}
					} else {
						createFlag = ftpClient.makeDirectory(ftpDirectory);
						channelFlag = ftpClient
								.changeWorkingDirectory(ftpDirectory);
					}

					if (createFlag) {
						logger.warn("创建目录" + ftpDirectory + "成功");
					} else {
						logger.warn("创建目录" + ftpDirectory + "失败");
					}
				} else {
					channelFlag = true;
				}
			} catch (Exception e) {
				logger.error("创建或者切换目录" + ftpDirectory + "失败", e);
			}
		} else {
			logger.info("所传入的ftp路径为空");
		}

		return channelFlag;
	}
	
	public void closeFTP() {
		try {
			if (ftpClient != null && ftpClient.isConnected()) {
				ftpClient.disconnect();
				ftpClient = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception{
		FtpUtils ftpUtils = new FtpUtils();
		ftpUtils.connectionFtp();
		ftpUtils.createDirecoty("/advert/temp/clientcode/contractnumber");
		ftpUtils.closedFtp();
	}
	
	public void upload(File file,String ss) throws Exception{      
        if(file.isDirectory()){           
            ss=ss+"/"+file.getName();
            ftpClient.makeDirectory(ss);                
            ftpClient.changeWorkingDirectory(ss);      
            String[] files = file.list();             
            for (int i = 0; i < files.length; i++) {      
                File file1 = new File(file.getPath()+File.separator+files[i] );      
                if(file1.isDirectory()){      
                    upload(file1,ss);      
                    ftpClient.changeToParentDirectory();      
                }else{                    
                  //  File file2 = new File(file.getPath()+"\\"+files[i]);  
                    File file2 = new File(file.getPath()+File.separator+files[i]);  
                    
                    FileInputStream input = new FileInputStream(file2);      
                    ftpClient.storeFile(file2.getName(), input);      
                    input.close();                            
                }                 
            }      
        }else{      
            File file2 = new File(file.getPath());      
            FileInputStream input = new FileInputStream(file2);      
            ftpClient.storeFile(file2.getName(), input);      
            input.close();        
        }      
    }   
	
}