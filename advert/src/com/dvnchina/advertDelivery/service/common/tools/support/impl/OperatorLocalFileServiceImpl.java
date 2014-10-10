package com.dvnchina.advertDelivery.service.common.tools.support.impl;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.InputFormatException;
import it.sauronsoftware.jave.MultimediaInfo;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.constant.FileOperationConstant;
import com.dvnchina.advertDelivery.meterial.dao.MeterialManagerDao;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;
import com.dvnchina.advertDelivery.service.common.tools.support.OperatorLocalFileService;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.json.Obj2JsonUtil;

public class OperatorLocalFileServiceImpl implements OperatorLocalFileService {
	
	private static Logger logger = Logger.getLogger(OperatorLocalFileServiceImpl.class);
    private static ConfigureProperties config = ConfigureProperties.getInstance();
	private MeterialManagerDao meterialManagerDao;
	private static Integer DTVID=37;
	
	public MeterialManagerDao getMeterialManagerDao() {
        return meterialManagerDao;
    }

    public void setMeterialManagerDao(MeterialManagerDao meterialManagerDao) {
        this.meterialManagerDao = meterialManagerDao;
    }

    @Override
	public String copyLocalFile2TargetDirectory(File file,
			String targetDirectory, String targetFileName) {
		return null;
	}

	@Override
	public String copyLocalFile2TargetDirectory(String localFilePath,
			String targetDirectory, String targetFileName,Map param) {
		
		//得到当前时间自1970年1月1日0时0分0秒开始流逝的毫秒数，将这个毫秒数作为上传文件新的文件名
		long now = new Date().getTime();
		File dir = new File(targetDirectory);
		
		//如果这个目录不存在，则创建他
		if(!dir.exists()){
			dir.mkdir();
		}
		
		int index = targetFileName.lastIndexOf(".");
		//判断上传文件名是否有扩展名，以时间戳作为新的文件名
		
		if(index!=-1){
			targetFileName = now + targetFileName.substring(index);
		}else{
			targetFileName = Long.toString(now);
		}
		
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		FileInputStream fis  = null;
		FileOutputStream fos = null;
		
		Map<String,String> resultMap = new HashMap<String,String>();
		try {
			fis = new FileInputStream(localFilePath);
			bis = new BufferedInputStream(fis);
			fos = new FileOutputStream(new File(dir,targetFileName));
			bos = new BufferedOutputStream(fos);
			
			byte[] buf = new byte[4096];
			
			int len = -1;
			
			while((len = bis.read(buf))!=-1){
				bos.write(buf,0,len);
			}
			resultMap.put("result","true");
		} catch (Exception e) {
			resultMap.put("result","false");
			logger.error("读取本地文件失败",e);
		}finally{
			try {
				if(bis!=null){
					bis.close();
				}
				
				if(bos!=null)
				{
					bos.close();
				}
				
				if(fis!=null){
					fis.close();
				}
				
				if(fos!=null){
					fos.close();
				}
			} catch (Exception e) {
				resultMap.put("result","false");
				logger.error("关闭IO时出现异常",e);
			}
		}
		String filePath = param.get("targetDirectory")+FileOperationConstant.FILE_SEPARATOR+targetFileName;
		filePath = filePath.replace("\\","/");
		resultMap.put("position","local");
		resultMap.put("filepath",filePath);
		resultMap.put("viewpath",targetFileName);
		return Obj2JsonUtil.map2json(resultMap);
	}
	
	
	@Override
	public String copyLocalFile2TargetDirectory(String localFilePath,String targetDirectory, String targetFileName){
		File dir = new File(targetDirectory);
		
		//如果这个目录不存在，则创建他
		if(!dir.exists()){
			dir.mkdirs();
		}
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		FileInputStream fis  = null;
		FileOutputStream fos = null;

		try {
			fis = new FileInputStream(localFilePath);
			bis = new BufferedInputStream(fis);
			fos = new FileOutputStream(new File(dir,targetFileName));
			bos = new BufferedOutputStream(fos);
			
			byte[] buf = new byte[4096];
			
			int len = -1;
			
			while((len = bis.read(buf))!=-1){
				bos.write(buf,0,len);
			}
			
		} catch (Exception e) {

			logger.error("读取本地文件失败",e);
		}finally{
			try {
				if(bis!=null){
					bis.close();
				}
				
				if(bos!=null)
				{
					bos.close();
				}
				
				if(fis!=null){
					fis.close();
				}
				
				if(fos!=null){
					fos.close();
				}
			} catch (Exception e) {
				logger.error("关闭IO时出现异常",e);
			}
		}
		return "";
	}
	
	@Override
	public String copyLocalFileTargetDirectory(String localFilePath,
			String targetDirectory, String targetFileName,Map param) {
	    String fileWidth="";
        String fileHigh="";
        String fileSize="";
        String zipDirName="";
		//得到当前时间自1970年1月1日0时0分0秒开始流逝的毫秒数，将这个毫秒数作为上传文件新的文件名
		File dir = new File(targetDirectory);
		
		//如果这个目录不存在，则创建他
		if(!dir.exists()){
			dir.mkdirs();
		}
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		FileInputStream fis  = null;
		FileOutputStream fos = null;
		
		Map<String,String> resultMap = new HashMap<String,String>();
		try {
		    
	        
			fis = new FileInputStream(localFilePath);
			bis = new BufferedInputStream(fis);
			fos = new FileOutputStream(new File(dir,targetFileName));
			bos = new BufferedOutputStream(fos);
			
			byte[] buf = new byte[4096];
			
			int len = -1;
			
			while((len = bis.read(buf))!=-1){
				bos.write(buf,0,len);
			}
			resultMap.put("result","true");
			// 获取视频的时长
			Encoder encoder = new Encoder();
	        MultimediaInfo m = null;
	        long duration=0l;
			try {
				m = encoder.getInfo(new File(localFilePath));
				duration = m.getDuration()/1000;
				resultMap.put("duration",duration+"秒");
			} catch (InputFormatException e1) {
				resultMap.put("duration","");
			} catch (EncoderException e1) {
				resultMap.put("duration","");
			}
			
			
			if(param.get("advertPositionId")!=null ){
			  //效验素材规格
			    String imageFormat = targetFileName.substring(targetFileName.indexOf(".")+1, targetFileName.length());
			    //开始图片效验
			    if(imageFormat.equals("png")||imageFormat.equals("jpg")){
			        InputStream inputStream = new FileInputStream(localFilePath);
	                BufferedImage bi = ImageIO.read(inputStream);   
	                 fileWidth = String.valueOf(bi.getWidth());
	                 fileHigh = String.valueOf(bi.getHeight());
	                                      
	                //计算文件大小
	                InputStream in = new BufferedInputStream(new FileInputStream(localFilePath), 16 * 1024);
	                byte[] buffer = new byte[16 * 1024];
	                int len2 = 0;
	                int bytesum = 0;
	                while ((len2 = in.read(buffer)) > 0) {
	                     bytesum += len2;
	                }
	                fileSize=Integer.valueOf(bytesum).toString();
	                //开始图片效验
	              ImageSpecification imageSpecification =new ImageSpecification();
	              imageSpecification = meterialManagerDao.getImageMateSpeci((Integer)param.get("advertPositionId"));
	              if(imageSpecification!=null){
	                  if(!fileWidth.equals(imageSpecification.getImageWidth())){
	                      resultMap.put("image_width_fail","上传的文件宽度不等于广告位的设置大小### Material_image_width=" + imageSpecification.getImageWidth());
	                      logger.info("上传的文件宽度不等于广告位的设置大小### Material_image_width=" + imageSpecification.getImageWidth());
	                      resultMap.put("checkTag","0");
	                  }else{
	                      if(!fileHigh.equals(imageSpecification.getImageHeight())){
	                          resultMap.put("image_high_fail","上传的文件高度不等于广告位的设置大小### Material_image_high=" + imageSpecification.getImageHeight());
	                          logger.info("上传的文件高度不等于广告位的设置大小### Material_image_high=" + imageSpecification.getImageHeight());
	                          resultMap.put("checkTag","0");
	                      }else{
	                          String maxVal = imageSpecification.getFileSize();                            
	                          if(!maxVal.equals("")&&maxVal!=null){
	                              int maxSize = Integer.valueOf(maxVal);//图片规格中文件大小单位为字节
	                              if(bytesum>maxSize){
	                                  resultMap.put("image_fileSize_fail","上传的文件大小超过广告位的设置大小### Material_image_fileSize=" + imageSpecification.getFileSize()+"K");
	                                  logger.info("上传的文件大小超过广告位的设置大小### Material_image_fileSize=" + imageSpecification.getFileSize()+"K");
	                                  resultMap.put("checkTag","0");
	                              }else{
	                                  resultMap.put("checkTag","1");
	                              }
	                          }
	                      }
	                  }
	              }
			    }
	            
	            //开始视频效验
			    if(imageFormat.equals("ts")){
			        resultMap.put("videoDuration",Long.toString(duration));
			        VideoSpecification videoSpecification = meterialManagerDao.getVideoMateSpeci((Integer)param.get("advertPositionId"));
		            //DTV 视频不做校验
			         if(videoSpecification!=null && (Integer)param.get("advertPositionId")!=DTVID){
			                if(duration!=videoSpecification.getDuration()){
			                    resultMap.put("video_duration_fail","上传的视频时长不等于广告位的设置大小### Material_video_duration=" + videoSpecification.getDuration()+"秒");
			                    logger.info("上传的视频时长不等于广告位的设置大小### Material_video_duration=" + videoSpecification.getDuration());
			                    resultMap.put("checkTag","0");
			                }else{
			                    resultMap.put("checkTag","1");
			                }
			         }
		          
			    }
			   
			
			}
			 //计算UI压缩文件文件大小
		    if (targetFileName.endsWith(".zip"))
			{
				 InputStream in = new BufferedInputStream(new FileInputStream(localFilePath), 16 * 1024);
	             byte[] buffer = new byte[16 * 1024];
	             int len2 = 0;
	             int bytesum = 0;
	             while ((len2 = in.read(buffer)) > 0) {
	                  bytesum += len2;
	             }
	             fileSize=Integer.valueOf(bytesum).toString();
			}
		    try {
				if(bis!=null){
					bis.close();
				}
				
				if(bos!=null)
				{
					bos.close();
				}
				
				if(fis!=null){
					fis.close();
				}
				
				if(fos!=null){
					fos.close();
				}
			} catch (Exception e) {
				resultMap.put("result","false");
				resultMap.put("duration", "0");
				logger.error("关闭IO时出现异常",e);
			}
		    
		    
		    if(param.get("zipName")!= null && targetFileName.endsWith(".zip")){
		    	
		    	  //开始ZIP文件效验
	              ImageSpecification zipSpecification =new ImageSpecification();
	              zipSpecification = meterialManagerDao.getImageMateSpeci((Integer)param.get("advertPositionId"));
	              if(zipSpecification!=null){
	            	  String maxVal = zipSpecification.getFileSize();  
                      if(!maxVal.equals("")&&maxVal!=null){
                          int maxSize = Integer.valueOf(maxVal);//图片规格中文件大小单位为字节
                          if(Integer.valueOf(fileSize)>maxSize){
                              resultMap.put("zip_fileSize_fail","上传的文件大小超过广告位的设置大小" + Integer.valueOf(maxVal)/1000+"K");
                              logger.info("上传的文件大小超过广告位的设置大小### Material_zip_fileSize=" + Integer.valueOf(maxVal)/1000+"K");
                              resultMap.put("checkTag","0");
                              resultMap.put("result","false");
                          }else{
                              resultMap.put("checkTag","1");
                          }
                      }
	              }
	            	  
		    	
		    	
		    	String filePathString = targetDirectory+"/"+targetFileName;
		    	ZipFile zipFile = new ZipFile(filePathString);
				Enumeration emu = zipFile.entries();
				while(emu.hasMoreElements()){
			           ZipEntry entry = (ZipEntry)emu.nextElement();
			           //会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
			           if (entry.isDirectory()){
			        	   zipDirName = entry.getName();
			             //new File(targetDirectory + "/" + entry.getName()).mkdirs();
			             continue;
			           }
			           }
			           zipFile.close();
			           
			           zipDirName = zipDirName.split("/")[0];
			           
			           if(!zipDirName.equals(config.getValueByKey("recommend.zip.name"))){
			        	   resultMap.put("dirName",zipDirName);
				           resultMap.put("result","false");
			           }
			          }
		    
		    
		} catch (Exception e) {
			resultMap.put("result","false");
			
			logger.error("读取本地文件失败",e);
		}finally{
			try {
				if(bis!=null){
					bis.close();
				}
				
				if(bos!=null)
				{
					bos.close();
				}
				
				if(fis!=null){
					fis.close();
				}
				
				if(fos!=null){
					fos.close();
				}
			} catch (Exception e) {
				resultMap.put("result","false");
				resultMap.put("duration", "0");
				logger.error("关闭IO时出现异常",e);
			}
		}
		
		String filePath = param.get("targetDirectory")+FileOperationConstant.FILE_SEPARATOR+targetFileName;
		filePath = filePath.replace("\\","/");
		resultMap.put("position","local");
		resultMap.put("filepath",filePath);
		resultMap.put("viewpath",targetFileName);
		resultMap.put("oldFileName",(String)param.get("oldFileName"));
		resultMap.put("imageFileWidth",fileWidth);
		resultMap.put("imageFileHigh",fileHigh);
		//resultMap.put("imageFileSize",String.valueOf(Float.parseFloat(fileSize)/1024));
		resultMap.put("imageFileSize",fileSize);
		
		
		return Obj2JsonUtil.map2json(resultMap);
	}

	@Override
	public String mkdirLocalDirectory(String localDirectory) {
		return null;
	}
}
