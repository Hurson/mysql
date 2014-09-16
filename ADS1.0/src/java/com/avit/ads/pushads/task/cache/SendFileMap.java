package com.avit.ads.pushads.task.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;


import com.avit.ads.util.ConstantsAdsCode;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.InitConfig;

// TODO: Auto-generated Javadoc
/**
 * 发送文件缓存.
 * Key=filename
 * Data=List(播出单单号)
 * 启动播出单时，调用addFile,添加文件列表
 * 播出单结束，调用deleteFile，删除播出单对应记录，如无其他播出单时候用，直接删除资源文件
 */
public class SendFileMap {
	
	/**  
	 * 发送文件缓存.
	 * Key=filename
	 * Data=List(播出单单号). 
	 * */
	private static Map<String,List<String>> fileMap = new HashMap<String,List<String>>();
	
	/** 
	 * 读写锁
	 */
	private static final ReentrantLock lock = new ReentrantLock();

	public SendFileMap()
	{
		//添加默认素材　记录
		//**.JPG:0 
	}
	public static void initSendFileMap()
	{
		//遍历配置广告位列表
		for(int i =0;i<InitConfig.getAdsConfig().getRealTimeAds().getAdsList().size();i++)
		{
			//添加发送文件记录
			//key=filename value=0 
			String [] restemp =InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i).getDefaultRes().split(ConstantsHelper.CHANNEL_SPLIT__SIGN);
			for (int j=0;j<restemp.length;j++)
			{
				//addFile(restemp[j],"0",InitConfig.getAdsResourcePath()+File.separator+restemp[j],InitConfig.getAdsConfig().getRealTimeAds().getAdsTempPath()+File.separator+restemp[j]);
				addFile(restemp[j],"0",InitConfig.getAdsResourcePath()+File.separator+restemp[j],InitConfig.getAdsConfig().getRealTimeAds().getAdsTempPath()+File.separator+restemp[j]);
			}
		}
	}
	/**
	 * 启动播出单时调用,添加文件列表
	 * 
	 * @param filename 资源文件名
	 * @param playid 播出单单号
	 * @return 返回1 成功 0 失败
	 */
	public static int addFile(String filename,String playid)
	{
		lock.lock();
		String key = filename;
		List<String> gisList = fileMap.get(key);
		if (gisList==null)
		{
			gisList = new ArrayList<String>();
			gisList.add(playid);
			fileMap.put(key, gisList);			
		}
		else
		{
			if (!gisList.contains(playid))
			{
				gisList.add(playid);
			}
		}
		lock.unlock();
		return 1;
	}
	/**
	 * 启动播出单时调用,添加文件列表
	 * 
	 * @param filename 资源文件名
	 * @param playid 播出单单号
	 * @return 返回1 成功 0 失败
	 */
	public static int addFile(String filename,String playid,String oldPath, String newPath)
	{
		lock.lock();
		copyFile(oldPath, newPath);
		String key = filename;
		List<String> gisList = fileMap.get(key);
		if (gisList==null)
		{
			gisList = new ArrayList<String>();
			gisList.add(playid);
			fileMap.put(key, gisList);			
		}
		else
		{
			if (!gisList.contains(playid))
			{
				gisList.add(playid);
			}
		}
		lock.unlock();
		return 1;
	}
	
	/**
	 * 播出单结束，调用deleteFile，删除播出单对应记录，如无其他播出单时候用，直接删除资源文件.
	 * 同时记录播出单状态为已完成
	 * @param filename 资源名称
	 * @param playid 播出单单号
	 * @return 返回1 成功 0 失败
	 */
	public static int deleteFile(String filename,String playid)
	{
		lock.lock();		
		String key = filename;
		List<String> gisList = fileMap.get(key);
		if (gisList==null)
		{
	
		}
		else
		{
			gisList.remove(playid);			
			if (gisList.size()<=0)
			{
				fileMap.remove(gisList);
			}		
		}
		lock.unlock();
		return 1;
	}
	/**
	 * 播出单结束，调用deleteFile，删除播出单对应记录，如无其他播出单时候用，直接删除资源文件.
	 * 同时记录播出单状态为已完成
	 * @param filename 资源名称
	 * @param playid 播出单单号
	 * @return 返回1 成功 0 失败
	 */
	public static int deleteFile(String filename,String playid,String filePathAndName)
	{
		lock.lock();		
		String key = filename;
		List<String> gisList = fileMap.get(key);
		if (gisList==null)
		{
			delFile(filePathAndName);
		}
		else
		{
			gisList.remove(playid);			
			if (gisList.size()<=0)
			{
				delFile(filePathAndName);
				fileMap.remove(gisList);
			}		
		}
		lock.unlock();
		return 1;
	}
	/**
	 * 投放数据时调用，读取现有投放文件列表.
	 * @return the file list
	 */
	public static List<String> getFileList()
	{
		//投放时直接复制临时文件夹所有内容，不调用此函数
		List retList=null;
		lock.lock();		
		lock.unlock();
		return retList;
	}
	 public static boolean copyFile(String oldPath, String newPath)
	 {  
		 try {
			 int bytesum = 0;
             int byteread = 0; 
             File oldfile = new File(oldPath);
             if (oldfile.exists()) { 
        	   //文件存在时                
        	   InputStream inStream = new FileInputStream(oldPath);
        	   //读入原文件                
        	   FileOutputStream fs = new FileOutputStream(newPath);
        	   byte[] buffer = new byte[1444];                
        	   int length;                
        	   while ( (byteread = inStream.read(buffer)) != -1) 
        	   { 
        		   bytesum += byteread; //字节数 文件大小 
        		   fs.write(buffer, 0, byteread); 
        		}
        	   inStream.close();
        	   } 
         }        
		 catch (Exception e)
		 {           
			 e.printStackTrace(); 
			 return false;
		 }
	     return true;
	 }
	  public static boolean copyFolder(String oldPath, String newPath)
	  {        
		  try 
		  {
			  (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
			  File a=new File(oldPath);
			  String[] file=a.list();
			  File temp=null;
			  for (int i = 0; i < file.length; i++) 
			  {
				  
				  if(oldPath.endsWith(File.separator))
				  {
					  temp=new File(oldPath+file[i]);
				  }
				  else
				  { 
					  temp=new File(oldPath+File.separator+file[i]);
				  }
				  if(temp.isFile())
				  {   
					  //如果是广告配置文件，不复制
					  if (temp.getName().toString().endsWith(".js"))
					  {
						  continue;
					  }
					  FileInputStream input = new FileInputStream(temp);
					  FileOutputStream output = new FileOutputStream(newPath + "/" +(temp.getName()).toString());
					  byte[] b = new byte[1024 * 5];
					  int len;
					  while ( (len = input.read(b)) != -1) 
					  { 
						  output.write(b, 0, len);
					  }
					  output.flush();
					  output.close();
					  input.close();
				  }
				  if(temp.isDirectory())
				  {
					  //如果是子文件夹,不复制 
					  //copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);
				  } 
			  }
		  }
		  catch (Exception e)
		  {   
			e.printStackTrace();
			return false;
		  }
		  return true;
	  }
	  public static void delFile(String filePathAndName)
	  {
		  try
		  {
		  String filePath = filePathAndName;
		  filePath = filePath.toString();
		  File myDelFile = new File(filePath);
		  myDelFile.delete();
		  }
		  catch (Exception e)
		  {
		  e.printStackTrace();
		  }
	  }
}
