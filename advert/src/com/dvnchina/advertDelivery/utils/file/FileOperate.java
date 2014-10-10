package com.dvnchina.advertDelivery.utils.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * 文件操作类
 *
 */
public class FileOperate {
	private static Logger logger = Logger.getLogger(FileOperate.class);
	private static byte[] _BUFFER = new byte[2048];
	private static final int BUFFER_SIZE = 16 * 1024;  
	private static String _SEPARATOR = File.separator;
    private static String message;
    
    public FileOperate() {
    } 
    
    /**
     * 读取文本文件内容
     * @param filePathAndName 带有完整绝对路径的文件名
     * @param encoding 文本文件打开的编码方式
     * @return 返回文本文件的内容
     */
    public String readTxt(String filePathAndName,String encoding) throws IOException{
     encoding = encoding.trim();
     StringBuffer str = new StringBuffer("");
     String st = "";
     try{
      FileInputStream fs = new FileInputStream(filePathAndName);
      InputStreamReader isr;
      if(encoding.equals("")){
       isr = new InputStreamReader(fs);
      }else{
       isr = new InputStreamReader(fs,encoding);
      }
      BufferedReader br = new BufferedReader(isr);
      try{
       String data = "";
       while((data = br.readLine())!=null){
         str.append(data+" "); 
       }
      }catch(Exception e){
       str.append(e.toString());
      }
      st = str.toString();
     }catch(IOException es){
      st = "";
     }
     return st;     
    }

    /**
     * 新建目录
     * @param folderPath 目录
     * @return 返回目录创建后的路径
     */
    public String createFolder(String folderPath) {
        try {
           File myFilePath = new File(folderPath);
            if (!myFilePath.exists()) {
                myFilePath.mkdir();
            }
        }
        catch (Exception e) {
            message = "创建目录操作出错";
        }
        return folderPath;
    }
    
    /**
     * 多级目录创建
     * @param folderPath 准备要在本级目录下创建新目录的目录路径 例如 c:myf
     * @param paths 无限级目录参数，各级目录以单数线区分 例如 a|b|c
     * @return 返回创建文件后的路径 例如 c:myfac
     */
    public String createFolders(String folderPath, String paths){
        String txts = folderPath;
        try{
            String txt;
            txts = folderPath;
            StringTokenizer st = new StringTokenizer(paths,"|");
            for(int i=0; st.hasMoreTokens(); i++){
                    txt = st.nextToken().trim();
                    if(txts.lastIndexOf("/")!=-1){ 
                        txts = createFolder(txts+txt);
                    }else{
                        txts = createFolder(txts+txt+"/");    
                    }
            }
       }catch(Exception e){
           message = "创建目录操作出错！";
       }
        return txts;
    }

    /**
     * 新建文件
     * @param filePathAndName 文本文件完整绝对路径及文件名
     * @param fileContent 文本文件内容
     * @return
     */
    public static void createFile(String filePathAndName, String fileContent) {
     
        try {
        	String fileDirPath = filePathAndName.substring(0,filePathAndName.lastIndexOf(File.separator));
        	File fileDir = new File(fileDirPath);
            String mayfilePath = filePathAndName.toString();
            File myFilePath = new File(mayfilePath);
            if (!fileDir.exists()) {
            	fileDir.mkdirs();
                myFilePath.createNewFile();
            }
            FileWriter resultFile = new FileWriter(myFilePath);
            PrintWriter myFile = new PrintWriter(resultFile);
            String strContent = fileContent;
            myFile.println(strContent);
            myFile.close();
            resultFile.close();
        }
        catch (Exception e) {
            message = "创建文件操作出错";
        }
    }
    
    

    /**
     * 有编码方式的文件创建
     * @param filePathAndName 文本文件完整绝对路径及文件名
     * @param fileContent 文本文件内容
     * @param encoding 编码方式 例如 GBK 或者 UTF-8
     * @return
     */
    public void createFile(String filePathAndName, String fileContent, String encoding) {
    	PrintWriter myFile = null;
        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.createNewFile();
            }
            myFile = new PrintWriter(myFilePath,encoding);
            myFile.println(fileContent);
           
        }
        catch (Exception e) {
            logger.info( "创建文件操作出错,文件名"+filePathAndName);
        }finally{
        	 myFile.close();
        }
    } 


    /**
     * 删除文件
     * @param filePathAndName 文本文件完整绝对路径及文件名
     * @return Boolean 成功删除返回true遭遇异常返回false
     */
    public static boolean delFile(String filePathAndName) {
     boolean bea = false;
        try {
            String filePath = filePathAndName;
            File myDelFile = new File(filePath);
            if(myDelFile.exists()){
             myDelFile.delete();
             bea = true;
            }else{
             bea = false;
             message = (filePathAndName+"删除文件操作出错");
            }
        }
        catch (Exception e) {
            message = e.toString();
        }
        return bea;
    }
    


    /**
     * 删除指定文件夹下所有文件
     * @param path 文件夹完整绝对路径
     * @return
     * @return
     */
    public static boolean delAllFile(String path) {
     boolean bea = false;
        File file = new File(path);
        if (!file.exists()) {
            return bea;
        }
        if (!file.isDirectory()) {
            return bea;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(_SEPARATOR)) {
                temp = new File(path + tempList[i]);
            }else{
                temp = new File(path + _SEPARATOR + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path+"/"+ tempList[i]);//先删除文件夹里面的文件
                delFolder(path+"/"+ tempList[i]);//再删除空文件夹
                bea = true;
            }
        }
        return bea;
    }


    /**
	 * 复制单个文件
	 * 
	 * @param oldPathFile
	 *            准备复制的文件源 如 E:\\advert\\mini\\li.jpg
	 * @param newPathFile
	 *            拷贝到新绝对路径带文件名 如"F:\\ss\\a\\b\\c\\d\\ss.jpg")
	 * @return
	 */
	public static boolean copyFile(String oldPathFile, String newPathFile) {
		InputStream inStream = null;
		FileOutputStream fos = null;
		int bytesum = 0;
		int byteread = 0;
		try {
			File oldfile = new File(oldPathFile);
			if (oldfile.exists()) { // 文件存在时
				inStream = new FileInputStream(oldPathFile); // 读入原文件
				fos = new FileOutputStream(newPathFile);
				
				while ((byteread = inStream.read(_BUFFER)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					//System.out.println(bytesum);
					fos.write(_BUFFER, 0, byteread);
				}
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("复制单个文件操作出错");
			return false;
		} finally {
			try {
				inStream.close();
				fos.close();
			} catch (IOException e) {
				logger.info("复制单个文件操作出错");
				e.printStackTrace();
				return false;
			}
		}

	}
	
	/**
	 * 复制单个文件
	 * 
	 * @param oldPathFile
	 *            准备复制的文件源 如 E:\\advert\\mini\\li.jpg
	 * @param newPathFile
	 *            拷贝到新绝对路径带文件名 如"F:\\ss\\a\\b\\c\\d\\ss.jpg")
	 * @return
	 */
	public static int copyFile2FileSize(String oldPathFile, String newPathFile) {
		InputStream inStream = null;
		FileOutputStream fos = null;
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			if (oldfile.exists()) { // 文件存在时
				inStream = new FileInputStream(oldPathFile); // 读入原文件
				fos = new FileOutputStream(newPathFile);
				
				while ((byteread = inStream.read(_BUFFER)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					//System.out.println(bytesum);
					fos.write(_BUFFER, 0, byteread);
				}
			}
			
			return bytesum;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("复制单个文件操作出错");
			return 0;
		} finally {
			try {
				inStream.close();
				fos.close();
			} catch (IOException e) {
				logger.info("复制单个文件操作出错");
				e.printStackTrace();
				return 0;
			}
		}
		
	}

	/**
	 * 拷贝单个文件
	 * 
	 * @param src
	 * @param dst
	 */
	public static boolean copyFile(File src, File dst) {
		InputStream in = null;
		OutputStream out = null;
		int len = 0;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 拷贝单个文件
	 * 
	 * @param src
	 * @param dst
	 * 
	 * return 文件的大小
	 */
	public static int copyFile2FileSize(File src, File dst) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			int bytesum = 0;
			while ((len = in.read(buffer)) > 0) {
				 bytesum += len;
				out.write(buffer, 0, len);
			}
			return bytesum;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
			
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
					return 0;
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
					return 0;
				}
			}
		}
	}

	/**
	 * 复制单个文件，替换文件名
	 * 
	 * @param oldPathFile
	 *            准备复制的文件源 如 E:\\advert\\mini\\li.jpg
	 * @param newFileName
	 *            文件名temp.jpg
	 * @param newPath
	 *            拷贝到新绝对路径 如"F:\\ss\\a\\b\\c\\d
	 * @return
	 */
	public static boolean copyFile2(String oldPathFile, String newFileName, String newPath) {
		String newPathFile = newPath + (_SEPARATOR) + newFileName;
		return copyFile(oldPathFile, newPathFile);
	}

	
	 /**
	 * 复制多个文件，到一个文件夹内
	 * 
	 * @param listFile
	 *            等待复制的文件集合，其中每条记录携带这该文件的新名字：以分号分割“;”
	 *            例如：F:\\ss\\a\\b\\c\\d\\oldFile.jpg;newFile.png
	 * @param newPath
	 *            拷贝到新绝对路径    如"E:\\temp
	 * @return
	 */
	public static int copyFiles(List<String> listFileWithNewName,String newPath)throws Exception {
		InputStream inStream = null;
		FileOutputStream fos = null;
		int bytesum = 0;
		int byteread = 0;
		String oldPathFile = "";
		String newFileName = "";
		String newPathFile = "";
		File newFileDir = new File(newPath);
		if(!newFileDir.exists()){
			newFileDir.mkdirs();
		}
		try {
			for(String srcFileWithNewName : listFileWithNewName){
				String[] file_name = srcFileWithNewName.split(";");
				 oldPathFile = file_name[0];
				 newFileName = file_name[1];
				 newPathFile = newPath+(_SEPARATOR)+newFileName;
				 
				 File oldfile = new File(oldPathFile);
				 if (oldfile.exists()) { // 文件存在时
					 inStream = new FileInputStream(oldPathFile); // 读入原文件
					 fos = new FileOutputStream(newPathFile);
					 
					 while ((byteread = inStream.read(_BUFFER)) != -1) {
						 bytesum += byteread; // 字节数 文件大小
						 fos.write(_BUFFER, 0, byteread);
					 }
				 }else{
					 logger.info("##"+oldPathFile+"文件不存在,无法将此文件进行归档。");
				 }
			}
			
			return bytesum;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("复制文件操作出错");
			//删除归档文件夹
			delFolder(newPath);
			return 0;
		} finally {
			try {
				inStream.close();
				fos.close();
			} catch (IOException e) {
				logger.info("复制单个文件操作出错");
				e.printStackTrace();
				//删除归档文件夹
				delFolder(newPath);
				return 0;
			}
		}

	
	}


    /**
     * 删除文件夹
     * @param folderPath 文件夹完整绝对路径
     * @return
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹
        }
        catch (Exception e) {
            message = ("删除文件夹操作出错");
        }
    }
	
    /**
     * 复制整个文件夹的内容
     * @param oldPath 准备拷贝的目录
     * @param newPath 指定绝对路径的新目录
     * @return
     */
    public void copyFolder(String oldPath, String newPath) {
        try {
            new File(newPath).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a=new File(oldPath);
            String[] file=a.list();
            File temp=null;
            for (int i = 0; i < file.length; i++) {
                if(oldPath.endsWith(_SEPARATOR)){
                    temp=new File(oldPath+file[i]);
                }else{
                    temp=new File(oldPath+_SEPARATOR+file[i]);
                }
                if(temp.isFile()){
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                    (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if(temp.isDirectory()){//如果是子文件夹
                    copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);
                }
            }
        }catch (Exception e) {
            message = "复制整个文件夹内容操作出错";
        }
    }


    /**
     * 移动文件
     * @param oldPath
     * @param newPath
     * @return
     */
    public static void moveFile(String oldPath, String newPath) {
        copyFile(oldPath, newPath);
        delFile(oldPath);
    }
    

    /**
     * 移动目录
     * @param oldPath
     * @param newPath
     * @return
     */
    public void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }
  
    
    /**
     * @param f  java.io.File
     * 				文件路径，可以是文件夹路径或者文件的绝对路径
     * @return  long
     * 				得到文件的大小
     * @throws Exception
     */
    public static long getFileSize(File f)throws Exception {//取得文件夹大小
		long size = 0;
		File[] flist = f.listFiles();
		if(flist != null){
			for (int i = 0; i < flist.length; i++) {
				if (flist[i].isDirectory()) {
					size = size + getFileSize(flist[i]);
				} else {
					size = size + flist[i].length();
				}
			}
			return size;
		}
		return size;
    }
    /**
     * @param f  java.io.File
     * 				文件路径，可以是文件夹路径或者文件的绝对路径
     * @return  long
     * 				得到文件的大小
     * @throws Exception
     */
    public static long getFileSize(String filePath)throws Exception  {//取得文件夹大小
    	File file = new File(filePath);
    	return getFileSize(file);
    }
    
    /**
     * @return  操作信息
     */
    public static String getMessage(){
        return message;
    }
    
    public static File[] getFileList(String folderPath){
    	File[] fileList = null;
    	File folder = new File(folderPath) ;
    	if(folder.isDirectory()){
    		fileList = folder.listFiles();
    	}
    	return fileList;
    }
}
