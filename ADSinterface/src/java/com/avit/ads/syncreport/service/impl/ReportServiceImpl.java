package com.avit.ads.syncreport.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Thread.State;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.avit.ads.syncreport.bean.AdPlaylistGis;
import com.avit.ads.syncreport.bean.ContentPush;
import com.avit.ads.syncreport.bean.LuceneBean;
import com.avit.ads.syncreport.bean.TReportData;
import com.avit.ads.syncreport.dao.ReportDao;
import com.avit.ads.syncreport.service.MyCreateIndexThread;
import com.avit.ads.syncreport.service.ReportService;
import com.avit.ads.util.ConstantsAdsCode;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.DateUtil;
import com.avit.ads.util.InitConfig;
import com.avit.common.ftp.service.FtpService;
import com.avit.common.util.StringUtil;
import com.avit.lucene.MyIndex;
import com.avit.lucene.PageBean;
import com.avit.lucene.Search;

@Repository(value="reportServiceImpl")
public class ReportServiceImpl implements ReportService {
	@Inject
	private ReportDao reportDao;
	
	@Inject
	private FtpService ftpService;
	
	private Logger logger = Logger.getLogger(ReportServiceImpl.class);
	
	MyCreateIndexThread[] createIndexThreads = new MyCreateIndexThread[20];
	
	
	public static List<AdPlaylistGis> adPlayListGisList;

	public static Map <String,List<AdPlaylistGis>> hourAdPlayListGisList; //无效
	public static Map <String,ContentPush> contentPush;	                  //无效
	

	
	public boolean generateReportData(Date day) throws Exception{
		try{

			logger.info("generateReportData  enter");
			
			int createIndexThreadCount = 0;
			
			SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
				
			Date today= null;
			if(null != day){
				today = day;
			}else{
				today= new Date();
			}
			Date yesterday = DateUtil.addDay(today, -1);
			
			//下载CMA压缩文件并解压
			downCMAFile(dayFormat.format(yesterday));
			//extractZipFile(dayFormat.format(yesterday));
	    	
			//清理数据库
			deleteDbData(yesterday);
			    	
			adPlayListGisList = reportDao.queryAdPlaylistGisList(null,DateUtil.getHeadTimeOfDate(yesterday), DateUtil.getHeadTimeOfDate(today));
			
			//创建索引
		    logger.info("create index begin... ");
		    
		   //建索引前把原来的索引删除（适用磁盘存放的索引）
		    String indexFolderPath = InitConfig.getConfigMap().get("cma_lucene");
			File dir = new File(indexFolderPath);
			if(!dir.exists()){
				dir.mkdirs();
			}else{
				for(File f : dir.listFiles()){
					f.delete();
				}
			}
		    MyIndex myIndex = new MyIndex(indexFolderPath);
		   
		    String cmaFilePath = InitConfig.getConfigMap().get(ConstantsHelper.CMA_DATA_PATH);
			File cmaFile = new File(cmaFilePath);
			if(cmaFile.exists() && cmaFile.isDirectory()){
				File[] areaFolders = cmaFile.listFiles();
				for(int i = 0; i < areaFolders.length; i++){
					if(areaFolders[i].isDirectory()){ 
						int index = createIndexThreadCount++;
						createIndexThreads[index] = new MyCreateIndexThread(dayFormat.format(yesterday),cmaFilePath, areaFolders[i].getName(), myIndex);
						createIndexThreads[index].start();
					}
				}	
			}
			
			//等待线程结束	
			boolean breaks = false;
			while (!breaks) {
				breaks = true;
				for(int i = 0; i < createIndexThreadCount; i++){
					State state = createIndexThreads[i].getState();
					if (state != State.TERMINATED) {
						breaks = false;
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						logger.error(e.getMessage());
					}
				}
			}
			//关闭IndexWriter
			try {
				myIndex.getIndexWriter().close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			
			logger.info("search begin ");

			Search search = null;
			try {
				search = new Search(myIndex.getIndexDir());
			} catch (IOException e1) {
				logger.error(e1.getMessage());
			}
			List<String> contentPushList = getContentPushList(adPlayListGisList);
			TReportData reportEntity = new TReportData();
			
			
			//查询条件的key是LuceneBean中的属性
			for(String entity : contentPushList){
				for(int i = 0; i < 24; i++){
					String[] datas = entity.split("_");
					String adSiteCode = datas[0];
					String contentId = datas[1];
					String[] keys = new String[]{"position_code", "contentid", "hourStr"};
					String[] values = new String[]{adSiteCode, contentId, i+""};
					
					reportEntity.setPositionCode(adSiteCode);
					reportEntity.setResourceId(Integer.parseInt(contentId));
					Calendar cal = Calendar.getInstance();
					cal.setTime(yesterday);
					cal.set(Calendar.HOUR_OF_DAY, i);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					reportEntity.setPushDate(cal.getTime());
					
					int receiveCount = search.MultiQuery4Count(keys, values);
					reportEntity.setReceiveCount(receiveCount);
					try {
						search.groupQuery4Count("tvn", keys, values, reportEntity);
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					if(ConstantsAdsCode.CMA_MAINMENU_POSITION_CODE.equals(adSiteCode)){ //主菜单广告需除以6
						reportEntity.setReceiveCount(receiveCount/6);
						reportEntity.setReacheCount(reportEntity.getReacheCount()/6);
						reportEntity.setEffCount(reportEntity.getEffCount()/6);
					}
					if(ConstantsAdsCode.CMA_START_POSITION_CODE.equals(adSiteCode) && receiveCount == 0){
						//do nothing  开机广告与时间段相关，不在这个时段投放，接收次数为0，就不写数据库了
					}else{
						reportDao.insertReport(reportEntity);
					}
				}	
			}
			try {
				search.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			
			cleanCma(cmaFilePath, dayFormat.format(yesterday));
			
			logger.info("generateReportData end");
		}catch(Exception ex){
			throw ex;
		}
		return true;
		
	}
	
	private void cleanCma(String dirPath, String dateStr){
		logger.info("clean cma dir");
		File cmaDir = new File(dirPath);
		for(File areaDir : cmaDir.listFiles()){
			for(File dataFile : areaDir.listFiles()){
				//String fileName = dataFile.getName();
				//if(fileName.startsWith(ConstantsHelper.ACTION_FLAG + dateStr) || fileName.startsWith(ConstantsHelper.BOOT_FLAG + dateStr) ){
				//	dataFile.delete();
				//}
				dataFile.delete();
			}
		}
	}	  
	
	//从播出单提取 adSiteCode_contentId（广告位和素材）
	private List<String> getContentPushList(List<AdPlaylistGis> adPlayListGisList){
		if(null == adPlayListGisList){
			return null;
		}
		List<String> resultList = new ArrayList<String>();
		for(AdPlaylistGis gis : adPlayListGisList){
			String adSiteCode = gis.getAdSiteCode();
			String[] contentIds = gis.getContentId().trim().split(ConstantsHelper.COMMA);
			for(String contentId : contentIds){
				String entity = adSiteCode + "_" + contentId;
				if(!resultList.contains(entity)){
					resultList.add(entity);
				}
			}
		}
		return resultList;
	}
	
	public boolean downCMAFile(String downday){
		logger.info("down file start");
		String serverIp=InitConfig.getConfigMap().get("cma_server_ip");
		String serverPort=InitConfig.getConfigMap().get("cma_server_port");
		String serverUser=InitConfig.getConfigMap().get("cma_user");
		String serverPwd=InitConfig.getConfigMap().get("cma_password");
		String remoteDirectory=InitConfig.getConfigMap().get("cma_server_path");
		String backUpDirectory=InitConfig.getConfigMap().get(ConstantsHelper.CMA_DATA_BACKUPPATH)+File.separator;
		String localDirectory=InitConfig.getConfigMap().get(ConstantsHelper.CMA_DATA_PATH)+File.separator;
		String filename = ConstantsHelper.ZIP_FLAG+downday+".tgz";
		boolean  flag=false;
		try{
			ftpService.setServer(serverIp, StringUtil.toInt(serverPort), serverUser, serverPwd);
			//下载压缩包到备份目录中（原来是下载到工作目录），解压到工作目录，就不用另作备份操作 
			flag=ftpService.download(filename, filename, remoteDirectory, backUpDirectory);  
			//解压
			linuxRun("tar zxf",backUpDirectory+File.separator+filename,localDirectory);
			logger.info("down file end");
		}catch(Exception e){
			logger.info("down file failed");
			logger.error(e.getMessage());
		}
		return flag;
	}
	
	private void extractZipFile(String downday){
		logger.info("extract zip file");
		String backUpDirectory=InitConfig.getConfigMap().get(ConstantsHelper.CMA_DATA_BACKUPPATH)+File.separator;
		String localDirectory=InitConfig.getConfigMap().get(ConstantsHelper.CMA_DATA_PATH)+File.separator;
		String filename = ConstantsHelper.ZIP_FLAG+downday+".tgz";
		try{
			//解压
			linuxRun("tar zxf",backUpDirectory+File.separator+filename,localDirectory);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
	
	private void deleteDbData(Date reportDate)throws Exception{
		try{
			logger.info("delete db data");
			Date startTime = DateUtil.getHeadTimeOfDate(reportDate);
			Date endTime = DateUtil.addDay(startTime, 1);
			reportDao.deleteReportByDate(startTime,endTime);
			logger.info("delete db data end ");
		}catch(Exception ex){
			throw ex;
		}

	}
	
		
	public static void printPageBean(PageBean page){
		for(int i = 0;i<page.getData().size();i++){
			System.out.println("tvn:"+((LuceneBean)page.getData().get(i)).getTvn());
			System.out.println("position_code:"+((LuceneBean)page.getData().get(i)).getPosition_code());
			System.out.println("service_id:"+((LuceneBean)page.getData().get(i)).getService_id());
			System.out.println("time:"+((LuceneBean)page.getData().get(i)).getOperator_time());
		}
	}
	public boolean backupCMA(String backupday,String sourcePath,String desPath) {
		logger.info("backupCMA enter "+backupday+" sourcePath=" +sourcePath +";desPath="+desPath);
		try 
		  {
			  (new File(desPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
			  
			  File a=new File(sourcePath);
			 
			  String[] file=a.list();
			  File temp=null;
			  for (int i = 0; i < file.length; i++) 
			  {
				  
				  if(sourcePath.endsWith(File.separator))
				  {
					  temp=new File(sourcePath+file[i]);
				  }
				  else
				  { 
					  temp=new File(sourcePath+File.separator+file[i]);
				  }
				  if(temp.isDirectory())
				  {
					  deleteCMAFile(backupday,sourcePath+File.separator+temp.getName());
					  continue;
					 // backupCMA(backupday,sourcePath+File.separator+temp.getName(),desPath);					 
				  }
				  if(temp.isFile())
				  {   
					  //复制至备份目录
					  if (temp.getName().toString().startsWith(ConstantsHelper.ZIP_FLAG+backupday) )
					  {					  
						  try {
								String stemp = sourcePath + File.separator;
								String dir = stemp.substring(0,stemp.lastIndexOf(File.separator));
								File workdir =  new File(dir);
								
								String cmd  = "mv " + sourcePath+File.separator+temp.getName().toString() + " " + desPath;
								logger.info("workpath "+workdir +" "+cmd);
								Process pro= Runtime.getRuntime().exec(cmd,null,workdir);
								pro.waitFor();
							} 
							catch(Exception cmde)
							{
								logger.error(cmde.getMessage());								
							}
					  }
				  }				 
			  }
		  }
		  catch (Exception e)
		  {   
			logger.error(e.getMessage());		
			return false;
		  }
		  logger.info("backupCMA end "+backupday+" sourcePath=" +sourcePath +";desPath="+desPath);			
		  return true;
	}
	
	public boolean deleteCMAFile(String backupday,String sourcePath) {
		try 
		  {		  
			  File a=new File(sourcePath);
			  logger.info("deleteCMAFile="+sourcePath+";date="+backupday);
			  String[] file=a.list();
			  File temp=null;
			  for (int i = 0; i < file.length; i++) 
			  {
				  if(sourcePath.endsWith(File.separator))
				  {
					  temp=new File(sourcePath+file[i]);
				  }
				  else
				  { 
					  temp=new File(sourcePath+File.separator+file[i]);
				  }
				  if(temp.isFile())
				  {   
					  //如果是广告配置文件，不复制
					  if (!temp.getName().toString().startsWith(ConstantsHelper.ACTION_FLAG+backupday) && !temp.getName().toString().startsWith(ConstantsHelper.BOOT_FLAG+backupday))
					  {
						  continue;
					  }
					  //删除源文件
					  temp.delete(); 
				  }				 
			  }
			  logger.info("end deleteCMAFile");
		  }
		  catch (Exception e)
		  {   
			logger.error(e.getMessage());		
			return false;
		  }
		  return true;
	}
	
	 public boolean copyFolder(String oldPath, String newPath)
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
					  copyFolder(oldPath+File.separator+file[i],newPath+File.separator+file[i]);
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
	 /** 
	    * 删除某个文件夹下的所有文件夹和文件 
	    * 
	    * @param delpath 
	    *            String 
	    * @throws FileNotFoundException 
	    * @throws IOException 
	    * @return boolean 
	    */  
	   public boolean deleteAllfile(String delpath)  {  
	    try {  
	    	logger.info("deletpath" +delpath);
	     File file = new File(delpath);  
	     // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true  
	     if (!file.isDirectory()) {  
	        file.delete();  
	     } else if (file.isDirectory()) {  
	      String[] filelist = file.list();  
	      for (int i = 0; i < filelist.length; i++) {  
	       File delfile = new File(delpath + File.separator+ filelist[i]);  
	       if (!delfile.isDirectory()) {  
	    	   if (filelist[i].indexOf(".js")==filelist[i].length()-3)
		          {
		        	  continue;//
		          }
	    	   delfile.delete();  
	        
	       } else if (delfile.isDirectory()) {  
	        deleteAllfile(delpath + "\\" + filelist[i]);  
	       }  
	      }  
	     }  
	    
	    } 	    
	    catch (Exception e)
	    {
	    	logger.error("deletpath" +delpath);
	    }
	    return true;  
	   } 

	   public  void linuxRun(String cmd,String infile,String outpath)
		{
			try {
				String dir = outpath.substring(0,outpath.lastIndexOf(File.separator));
				File workdir =  new File(dir);
				Process pro= Runtime.getRuntime().exec(cmd +" " + infile,null,workdir);
			    pro.waitFor();  
			} 
			catch(Exception e){
				logger.error(e.getMessage());
			}
			
		}
}
