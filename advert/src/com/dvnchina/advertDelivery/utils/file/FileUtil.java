package com.dvnchina.advertDelivery.utils.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.log4j.Logger;
import org.apache.tools.ant.util.FileUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 * FileUtil.java 文件操作工具类
 * 
 */
public class FileUtil {

	private static final Logger logger = Logger.getLogger(FileUtil.class);

	public static char separator = '\\';
	public static char linux_separator = '/';

	/**
	 * 保存文件输入流到指定目录和文件名的文件实体中
	 * 
	 * @param destFileName
	 *            目标文件名
	 * @param in
	 *            源文件输入流
	 * @param destFilePath
	 *            目标文件路径
	 * @throws Exception
	 */
	public static void saveFile(String destFileName, InputStream in,
			String destFilePath) throws IOException {
	    OutputStream bos = null;// 建立一个保存文件的输出流
		
		try{
		    destFilePath = trimFilePath(destFilePath) + "/" + destFileName;
		    bos = new FileOutputStream(destFilePath, false);
    		int bytesRead = 0;
    		byte[] buffer = new byte[8192];
    		while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
    			bos.write(buffer, 0, bytesRead);
    		}
		}catch(Exception e){
		    e.printStackTrace();
		}finally{
    		bos.close();
    		in.close();
		}
	}

	/**
	 * 保存文件
	 * 
	 * @param destFile
	 *            目标文件完整路径
	 * @param in
	 *            源文件输入流
	 * @throws Exception
	 */
	public static void saveFile(File destFile, InputStream in)
			throws IOException {
	    OutputStream bos = null;
	    try{
	        bos = new FileOutputStream(destFile, false);
    		int bytesRead = 0;
    		byte[] buffer = new byte[8192];
    		while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
    			bos.write(buffer, 0, bytesRead);
    		}
		}catch(Exception e){
		    e.printStackTrace();
		}finally{
    		bos.close();
    		in.close();
		}
	}

	/**
	 * zip文件解压缩
	 * 
	 * @param zipFileName
	 *            文件完整路径
	 * @param outputDirectory
	 *            解压保存路径
	 * @param isCover
	 *            是否重名覆盖
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void unzip(String zipFileName, String outputDirectory,
			boolean isCover) throws IOException {
		logger.debug("开始解压文件：" + zipFileName + ":" + outputDirectory);
		ZipFile zipFile = new ZipFile(zipFileName);
		try {
			Enumeration<ZipEntry> e = zipFile.getEntries();
			org.apache.tools.zip.ZipEntry zipEntry = null;
			createDirectory(outputDirectory, "");
			while (e.hasMoreElements()) {
				zipEntry = e.nextElement();
				if (zipEntry.isDirectory()) {
					String name = zipEntry.getName();
					name = name.substring(0, name.length() - 1);
					File f = new File(outputDirectory + File.separator + name);
					if (!f.exists()) {
						if (!f.mkdirs()) {
							zipFile.close();
							throw new IOException("创建目录失败！");
						}
					}

				} else {
					String fileName = zipEntry.getName();
					fileName = fileName.replace(separator, File.separatorChar);
					fileName = fileName.replace(linux_separator, File.separatorChar);
					if (fileName.indexOf(File.separatorChar) != -1) {
						createDirectory(outputDirectory, getPath(fileName));
						fileName = fileName.substring(fileName
								.lastIndexOf(separator) + 1, fileName.length());
					}

					File f = new File(outputDirectory + File.separator
							+ zipEntry.getName());
					if (f.exists()) {
						if (isCover) {
							// 如果要覆盖，先删除原有文件
							FileUtils.delete(f);
						} else {
							// 如果不覆盖，不做处理
							continue;
						}

						// continue;
					}

					if (!f.createNewFile()) {
						zipFile.close();
						throw new IOException("创建文件失败！");
					}
					InputStream in = zipFile.getInputStream(zipEntry);
					FileOutputStream out = new FileOutputStream(f);

					byte[] by = new byte[1024];
					int c;
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
					out.close();
					in.close();
				}
			}
		} catch (IOException e) {
			throw e;
		} finally {
			zipFile.close();
		}

	}

	public static String trimFilePath(String filePath) {
		if (filePath == null || filePath.trim().length() == 0) {
			return "";
		}
		if (filePath.endsWith(String.valueOf(separator))) {
			filePath = filePath.substring(0, filePath.length() - 1);
		}
		return filePath;
	}

	private static void createDirectory(String directory, String subDirectory)
			throws IOException {
		String dir[];
		File fl = new File(directory);

		if (subDirectory.equals("") && fl.exists() != true) {
			if (fl.mkdirs()) {
				throw new IOException("创建文件失败！" + directory);
			}
		} else if (!subDirectory.equals("")) {
			dir = subDirectory.replace(separator, File.separatorChar).split(
					"\\\\");
			StringBuffer sBuffer = new StringBuffer(directory);
			for (int i = 0; i < dir.length; i++) {
				File subFile = new File(sBuffer.toString() + separator + dir[i]);
				if (!subFile.exists()) {
					if (!subFile.mkdirs()) {
						throw new IOException("创建文件失败！" + subDirectory);
					}
				}
				sBuffer.append(separator + dir[i]);
			}

		}

	}

	/**
	 * 当文件路径包含文件名时，截取该文件所在文件夹路径
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getPath(String fileName) {
		if (fileName.lastIndexOf(File.separator) >= 0) {
			return fileName.substring(0, fileName.lastIndexOf(File.separator));
		}
		return fileName;

	}

	/**
	 * 把字符串写入指定文件
	 * 
	 * @param fileName
	 * @param content
	 */
	public static void writeStringToFile(String fileName, String content)
			throws IOException {
		File file = new File(fileName);

		if (file.exists()) {
			FileUtils.delete(file);
		}
//		if (file.createNewFile()) {
//			throw new IOException("创建文件：" + fileName + "失败！");
//		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(content);
		writer.close();
	}

	/**
	 * 获取某文件路径的跟路径
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getRootPath(String fileName) {
		if (fileName.lastIndexOf("/") >= 0) {
			return "/" + fileName.split("/")[1];
		}

		return fileName;

	}
	
	/**
	 * 删除指定的文件
	 * @param file
	 */
	public static void deleteFile(File file) throws IOException
	{
		if(file == null || !file.exists())
		{
			return ;
		}
		
		if(file.isDirectory())
		{
			deleteDirectory(file);
		}
		else {
			file.delete();
		}
		
 	}
	
	/**
	 * 删除指定的目录
	 * @param directory
	 * @throws IOException
	 */
	public static void deleteDirectory(File directory) throws IOException
	{
		String[] subFiles = directory.list();
		for (int i = 0; i < subFiles.length; i++) {
			File subFile = new File(directory.getAbsolutePath() + File.separator + subFiles[i]);
			deleteFile(subFile);
		}
		directory.delete();
	}
	
	/**
	 * 获取旧图片全路径
	 * @param filePath 图片的路径
	 * @param imageUrl 数据库中保存的图片路径
	 * @return
	 */
	public static String getOldResourcePath(String filePath,String imageUrl){
		int imageIndex = 0;
		int imageIndex2 = 0;
		int pathIndex = 0;
		int pathIndex2 = 0;
		if(imageUrl != null && !imageUrl.equals("")){
			imageIndex = imageUrl.lastIndexOf("\\");
			imageIndex2 = imageUrl.lastIndexOf("/");
			imageIndex = (imageIndex>imageIndex2?imageIndex:imageIndex2);
		}
		if(filePath != null && !filePath.equals("")){
			pathIndex = filePath.lastIndexOf("\\");
			pathIndex2 = filePath.lastIndexOf("/");
			pathIndex = (pathIndex>pathIndex2?pathIndex:pathIndex2);
		}
		StringBuffer oldImagePath = new StringBuffer();
		if(pathIndex>0){
			oldImagePath.append(filePath.substring(0, pathIndex));
		}else{
			oldImagePath.append(filePath);
		}
		oldImagePath.append(File.separator);
		if(imageIndex>0){
			oldImagePath.append(imageUrl.substring(imageIndex+1));
		}else{
			oldImagePath.append(imageUrl);
		}

		return oldImagePath.toString();
	}
	
	/**
	 * 删除旧图片
	 * @param fullFilePath 删除图片的全路径
	 */
	public static void delOldResource(String fullFilePath){
		if(fullFilePath != null && !fullFilePath.equals("")){
			File outFile= new File(fullFilePath);
			if(outFile.exists()){
				outFile.delete();
			}
		}
	}
	
	/**
     * 资源上传的处理方法
     * @param file 上传的文件实体
     * @param cover 是否重名覆盖 true -- 是，false -- 否
     * @throws PortalMSException
     */
    public static void uploadResource(File file, String path, Boolean cover) 
    {
        if (file == null || path == null || path.equals(""))
        {
            return;
        }
        File outFile=null;
        try
        {       	
            outFile = new File(path);
            String pathName = FileUtil.getPath(outFile.getPath());
            createFolder(pathName);
            // 如果有同名文件
            if (outFile.exists())
            {
                // 如果选择同名覆盖
                if (cover)
                {
                	org.apache.commons.io.FileUtils.forceDelete(outFile);
                }
                else
                {// 否则退出操作
                    return;
                }
            }else{
            	if(!outFile.createNewFile()){
            		logger.error("创建文件失败！");
            	}
            }
            // 创建源文件输入流
            FileInputStream in = new FileInputStream(file);
            // 保存输入流到指定文件路径
            FileUtil.saveFile(outFile, in);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 创建一个文件夹
     * @param path
     * @return
     * @throws Exception
     */
    public static boolean createFolder(String path) 
    {
        File dirFile = null;
        boolean isCreate = true;
        try
        {
            logger.debug("创建文件夹...");
            dirFile = new File(path);
            //判断文件夹是否已存在
            boolean isExist = dirFile.exists();
            if (!isExist)
            {
                //文件夹不存在，则创建一个
                isCreate = dirFile.mkdirs();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return isCreate;
    }
    
    /**
	 * 返回servlet context路径，即WebRoot路径
	 * @return
	 */
	public static String getSerlvetContextPath()
	{
		// 获得classes目录路径
		String classPath = Thread.currentThread().getContextClassLoader()
			.getResource("").getPath();
		File file = new File(classPath);
		// 返回上两级目录，此时为WebRoot目录
		String path = file.getParentFile().getParentFile().getPath();
		
		return path;
	}
}
