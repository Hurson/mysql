package com.avit.ads.syncreport.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.avit.ads.syncreport.bean.AdPlaylistGis;
import com.avit.ads.syncreport.bean.LuceneBean;
import com.avit.ads.syncreport.service.impl.ReportServiceImpl;
import com.avit.ads.util.ConstantsAdsCode;
import com.avit.ads.util.ConstantsHelper;
import com.avit.common.type.AreaType;
import com.avit.common.util.DateUtil;
import com.avit.lucene.MyIndex;


public class MyCreateIndexThread extends Thread { 
	
	private String convertday;
    private String cmaFilePath;
    private String area;
	private MyIndex myIndex;
	
	
	public MyCreateIndexThread(String convertday,String cmaFilePath,String area, MyIndex myIndex){
		this.convertday = convertday;
		this.cmaFilePath = cmaFilePath;
		this.area = area;
		this.myIndex = myIndex;		
	}
	
	public void run(){
		File areaFolder = new File(cmaFilePath + File.separator + area);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmsssss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		//SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf4 = new SimpleDateFormat("yyyyMMdd_HH");
		SimpleDateFormat sdf5 = new SimpleDateFormat("yyyyMMdd");
		for(File dataFile : areaFolder.listFiles()){
			String fileName = dataFile.getName();
			int timeStartIndex = fileName.indexOf("_") + 1;
			String fileTimeStr = fileName.substring(timeStartIndex, timeStartIndex+11);
			
			BufferedReader br = null;
			String line = "";
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(dataFile)));
				if(fileName.startsWith(ConstantsHelper.ACTION_FLAG + convertday)){
					String tvn = "";
					String timeStr = "";
					String positionFlag = "";
					String serviceId = "";
					
					while((line = br.readLine()) != null){   //单向实时广告
						String[] datas = line.split(ConstantsHelper.COMMA);
						if(datas.length != 3){
							continue;
						}
						tvn = datas[0];
						String[] leftDatas = datas[1].trim().split(ConstantsHelper.COLON);
						if(leftDatas.length == 3){  //含频道
							timeStr = leftDatas[0];
							positionFlag = leftDatas[1];
							serviceId = leftDatas[2];
						}else if(leftDatas.length == 2){  //无频道
							timeStr = leftDatas[0];
							positionFlag = leftDatas[1];
						}else{
							continue;
						}
						LuceneBean entity = new LuceneBean(tvn, timeStr, positionFlag, serviceId, area);
						Date operatorTime = null;
						try {
							operatorTime = sdf1.parse(entity.getOperator_time());   //从记录中解析时间
						} catch (Exception e) {
							try {
								operatorTime = sdf4.parse(fileTimeStr);   //从文件名获取，只精确到小时
							} catch (Exception e1) {
								continue;
							}
						}
						
						if(!isSameDay(operatorTime, convertday, sdf5)){
							continue;
						}
						
						//判断该条记录是否有效（有对应播出单）,若有效，补全bean,加入索引
						validateAndAddBean(entity, operatorTime, ReportServiceImpl.adPlayListGisList);  
						
					}				
					
					
				}else if(fileName.startsWith(ConstantsHelper.BOOT_FLAG + convertday)){ //开机广告
					String tvn = "";
					String timeStr = "";
					
					while( (line = br.readLine()) != null ){  //开机广告
						String[] datas = line.split(ConstantsHelper.COMMA);
						if(datas.length < 2){
							continue;
						}
						tvn = datas[0];
						timeStr = datas[1];
						LuceneBean entity = new LuceneBean(tvn, timeStr, ConstantsAdsCode.CMA_START, "", area);
						
						Date operatorTime = null;
						try {
							operatorTime = sdf2.parse(entity.getOperator_time()); //从记录解析
						} catch (Exception e) {
							try {
								operatorTime = sdf4.parse(fileTimeStr);   //从文件名获取，只精确到小时
							} catch (Exception e1) {
								continue;
							}
						}
						
						if(!isSameDay(operatorTime, convertday, sdf5)){
							continue;
						}
						
						validateAndAddBean(entity, operatorTime, ReportServiceImpl.adPlayListGisList);
						
					}
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally{  //关闭 BufferedReader
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	private  boolean isSameDay(Date operatorTime, String dateStr, SimpleDateFormat sdf) {
	    String opDateStr = sdf.format(operatorTime);
	    if (opDateStr.equals(dateStr)) {
	        return true;
	    } else {
	        return false;
	    }
	}
	
	
	
	//判断方法：【CMA文件的一条记录】 那个时间点，那个区域，那个广告位，那个频道（如果有），【数据库】是否有相应的播出单（有广告投放）
	private void validateAndAddBean(LuceneBean entity, Date operatorTime, List<AdPlaylistGis> adPlayListGisList){
		for(AdPlaylistGis gis : adPlayListGisList){
			if( compareAdSite(entity.getPosition_flag(), gis.getAdSiteCode())   //广告位
					&& operatorTime.after(gis.getStartTime()) && operatorTime.before(gis.getEndTime())  //时间
					&& compareArea(AreaType.getAreaMap().get(entity.getArea_name()), gis.getAreas()) //区域，注： getAreas只会返回一个区域
					&& validateServiceId(entity.getService_id(), gis.getChannelId()) ){  //频道
				//补全bean，填充contentID、positionCode、播出单id、小时数
				entity.setPosition_code(gis.getAdSiteCode());
				entity.setPlayListId(gis.getId() + "");
				Calendar cal = Calendar.getInstance();
				cal.setTime(operatorTime);
				int hour = cal.get(Calendar.HOUR_OF_DAY);
				entity.setHourStr(hour + "");
				if(entity.getPosition_flag().equals(ConstantsAdsCode.CMA_START)){  //开机广告，通过时段判断content_id
					String[] contentids = gis.getContentId().trim().split(ConstantsHelper.COMMA);
					int contentCount = contentids.length;
					switch (contentCount) {
						case 1:   //1张默认图片
							entity.setContentid(contentids[0]);
							myIndex.addLuceneBeanToIndex(entity);  
							break;
						case 2:   //1个视频 + 1张默认图片
							entity.setContentid(contentids[0]);
							myIndex.addLuceneBeanToIndex(entity);  
							entity.setContentid(contentids[1]);
							myIndex.addLuceneBeanToIndex(entity);  
							break;
						case 24:  //24张图片
							String contentId = getContentIdWithHour(contentids, operatorTime);
							if(null != contentId){
								entity.setContentid(contentId);
								myIndex.addLuceneBeanToIndex(entity); 
							}
							break;
						case 25:  //1个视频 + 24张图片
							entity.setContentid(contentids[0]);
							myIndex.addLuceneBeanToIndex(entity);  
							String[] hourContentIds = Arrays.copyOfRange(contentids, 1, 25);
							String contentID = getContentIdWithHour(hourContentIds, operatorTime);
							if(null != contentID){
								entity.setContentid(contentID);
								myIndex.addLuceneBeanToIndex(entity); 
							}
							break;
	
						default:
							break;
					}

				}else if(entity.getPosition_flag().equals(ConstantsAdsCode.CMA_MAINMENU)){ //主菜单，6张素材轮播，先全部加进索引，查询出来后除以6
					String [] contentids = gis.getContentId().trim().split(ConstantsHelper.COMMA);
					for(String contentId : contentids){
						entity.setContentid(contentId);
						myIndex.addLuceneBeanToIndex(entity);  
					}
					continue;   //可能两种广告都有，都加入索引
					
				}else{  //其他广告，DB的播出单记录中content_id只有一个
					entity.setContentid(gis.getContentId());
					myIndex.addLuceneBeanToIndex(entity);  
				}
				break; 
			}
		}
		
	}
	
	private boolean compareAdSite(String cmaAdFlag, String dbAdCode){
		if(cmaAdFlag.equals(ConstantsAdsCode.CMA_START) && dbAdCode.equals(ConstantsAdsCode.CMA_START_POSITION_CODE)){
			return true;
		}
		if(cmaAdFlag.equals(ConstantsAdsCode.CMA_MAINMENU) && ( dbAdCode.equals(ConstantsAdsCode.CMA_MAINMENU_POSITION_CODE) 
				|| dbAdCode.equals(ConstantsAdsCode.CMA_MAINMENU_FRAME_POSITION_CODE))){
			return true;
		}
		if(cmaAdFlag.equals(ConstantsAdsCode.CMA_GUIDE) && dbAdCode.equals(ConstantsAdsCode.CMA_GUIDE_POSITION_CODE)){
			return true;
		}
		if(cmaAdFlag.equals(ConstantsAdsCode.CMA_VOLUME) && dbAdCode.equals(ConstantsAdsCode.CMA_VOLUME_POSITION_CODE)){
			return true;
		}
		if( ( cmaAdFlag.equals(ConstantsAdsCode.CMA_MINIEPG) || cmaAdFlag.equals(ConstantsAdsCode.CMA_DVB) ) 
				&& dbAdCode.equals(ConstantsAdsCode.CMA_MINIEPG_POSITION_CODE) ){
			return true;
		}
		if(cmaAdFlag.equals(ConstantsAdsCode.CMA_LIST) && dbAdCode.equals(ConstantsAdsCode.CMA_LIST_POSITION_CODE)){
			return true;
		}
		if(cmaAdFlag.equals(ConstantsAdsCode.CMA_FAVORITEB) && dbAdCode.equals(ConstantsAdsCode.CMA_FAVORITEB_POSITION_CODE)){
			return true;
		}
		if(cmaAdFlag.equals(ConstantsAdsCode.CMA_RADIO) && dbAdCode.equals(ConstantsAdsCode.CMA_RADIO_POSITION_CODE)){
			return true;
		}
		return false;
	}
	
	
	private boolean compareArea(String cmaArea, String dbArea){
		
		if("".equals(dbArea) || "0".equals(dbArea) || "15200000".equals(dbArea)){ //投放所有区域
			return true;
		}else{
			return cmaArea.equals(dbArea);  //投放某个地市
		}
	}
	
	private boolean validateServiceId(String cmaServiceId, String dbServiceIds){
		if("".equals(cmaServiceId)){  //CMA中的记录不含serviceId
			return true;
		}else{
			return dbServiceIds.contains(cmaServiceId);  //DB中的service_id是多个service_id的集合
		}
	}
	
	//DB中的开机播出单content_id排序用的是string排序，不是数字排序
	private String getContentIdWithHour(String[] contents, Date time){
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		String[] hourStr = new String[24]; 
		for(int i = 0; i < 24; i++){
			hourStr[i] = i + "";
		}
		Arrays.sort(hourStr);
		if(contents.length == 24){
			for(int i = 0; i < 24; i++){
				if(hourStr[i].equals(hour + "")){
					return contents[i];
				}
			}
		}
		return null;
	}
	
}
