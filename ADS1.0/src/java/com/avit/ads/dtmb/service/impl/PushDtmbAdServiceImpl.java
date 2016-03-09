package com.avit.ads.dtmb.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avit.ads.dtmb.bean.DOcgInfo;
import com.avit.ads.dtmb.bean.PlayList;
import com.avit.ads.dtmb.cache.SendAdsTypesMap;
import com.avit.ads.dtmb.dao.DOcgInfoDao;
import com.avit.ads.dtmb.des.Des;
import com.avit.ads.dtmb.service.DOcgService;
import com.avit.ads.dtmb.service.DUixService;
import com.avit.ads.dtmb.service.PushDtmbAdService;
import com.avit.ads.pushads.task.bean.TReleaseArea;
import com.avit.ads.pushads.task.bean.TextMate;
import com.avit.ads.pushads.task.dao.PushAdsDao;
import com.avit.ads.pushads.uix.dao.AreaDao;
import com.avit.ads.util.ConstantsAdsCode;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.DtmbSendFlagHelper;
import com.avit.ads.util.DtmbUnRealTimeAdsPushHelper;
import com.avit.ads.util.FileDigestUtil;
import com.avit.ads.util.InitAreas;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.PushFalierHelper;
import com.avit.ads.util.bean.AdResource;
import com.avit.ads.util.message.AdsConfigJs;
import com.avit.ads.util.message.AdsImage;
import com.avit.ads.util.message.ChannelSubtitle;
import com.avit.ads.util.message.ChannelSubtitleElement;
import com.avit.ads.util.message.ChannelSubtitleInfo;
import com.avit.ads.util.message.Subtitle;
import com.avit.ads.util.message.SubtitleContent;
import com.avit.ads.util.message.SubtitlePart;
import com.avit.ads.util.message.TvnTarget;
import com.avit.ads.util.type.UIUpdate;
import com.avit.ads.util.warn.WarnHelper;
import com.avit.common.ftp.service.FtpService;
import com.google.gson.Gson;
@Service("pushDtmbAdService")
public class PushDtmbAdServiceImpl implements PushDtmbAdService {

	private Log log = LogFactory.getLog(this.getClass());
	
	@Inject
	private PushAdsDao pushAdsDao;
	@Inject
	private AreaDao areaDao;
	@Inject
	private FtpService ftpService;
	@Inject
	private DOcgInfoDao ocgInfoDao;
	@Inject
	private DOcgService ocgService;
	@Inject
	private DUixService uixService;
	@Autowired
	private WarnHelper warnHelper;
	@Autowired
	private DtmbSendFlagHelper sendFlagHelper;
	@Autowired
	private DtmbUnRealTimeAdsPushHelper unRealTimeAdsPushHelper;
	@Autowired
	private PushFalierHelper pushFalierHelper;
	private Des des = new Des();
	public void sendRealTimeDTMBAd() {
		List<PlayList> playlistList = pushAdsDao.querySendDTMBAds();
		
		Map<String, Map<String, String[]>> channelAdsMap = SendAdsTypesMap.getDtmbChannelAdsMap();
		List<TReleaseArea> areaList = areaDao.getAllArea();
		
		for(TReleaseArea area : areaList){
			if(StringUtils.isBlank(area.getOcsId())){
				continue;
			}
			String areaCode = area.getAreaCode();
			String configPath = InitConfig.getAdsConfig().getDrealTimeAds().getAdsTempConfigPath() + File.separator + areaCode;
			String configFileName = configPath + "/"+ InitConfig.getAdsConfig().getDrealTimeAds().getAdsConfigFile();
			String oldMD5 = FileDigestUtil.getFileNameMD5(configFileName);
			Map<String, Map<String, String[]>> areaAdsMap = new HashMap<String, Map<String, String[]>>();
			areaAdsMap = putAll(channelAdsMap);
			for(PlayList playlist : playlistList){
				
				if(StringUtils.isNotEmpty(playlist.getAreaCode())&& playlist.getAreaCode().equals(areaCode) || playlist.getAreaCode().equals("152000000000")){
					String serviceIds = playlist.getTypeValue();
					
					if(StringUtils.isNotEmpty(serviceIds)){
						String[] serviceIdss = serviceIds.split(",");
						for(String serviceId : serviceIdss){
							if(areaAdsMap.containsKey(serviceId)&& playlist.getIsHd().equals("1")){
								areaAdsMap.get(serviceId).get(playlist.getPosition())[0]= playlist.getResourcePath();
							
							}else if(areaAdsMap.containsKey(serviceId)&& playlist.getIsHd().equals("0")){
								areaAdsMap.get(serviceId).get(playlist.getPosition())[1]= playlist.getResourcePath();
							}
						}
					}
				}
			}
			
			log.info("生成【"+area.getAreaName()+"】配置文件....");
			String temConfigFileName = "tem_" + InitConfig.getAdsConfig().getDrealTimeAds().getAdsConfigFile();
			Set<String> fileSet = generateConfigFile(configPath + "/" + temConfigFileName, areaAdsMap);
			try {
				des.DesCryptFile(configPath + "/" + temConfigFileName, configFileName);
				//删除临时文件
				//this.deleteFile(configPath, temConfigFileName);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			String newMD5 = FileDigestUtil.getFileNameMD5(configFileName);
			if(!newMD5.equals(oldMD5)){
				log.info("【"+area.getAreaName()+"】区域广告配置有更新，执行投放流程......");
				log.info("从ftp资源服务器下载素材至本地...");
				
				AdResource adResource = InitConfig.getAdsConfig().getAdResource();
				String host = adResource.getIp() ;
				String uname = adResource.getUser();
				String pword = adResource.getPwd(); 
				
				String materialPath =  adResource.getAdsResourcePath();
				String localMaterialPath = InitConfig.getAdsConfig().getDrealTimeAds().getAdsTempPath() + "/" + areaCode;
				
				if(!ftpService.connectServer(host, 21, uname, pword)){
					
					log.info("下载素材，连接ftp" + "ip" + "失败！"); 
				}else{
					
					ftpService.deleteDirFile(localMaterialPath);
					
					for(String fileName : fileSet){
						String temFileName = "";	
						if(StringUtils.isNotEmpty(fileName)){
							temFileName = "tem_" + fileName;
							ftpService.downloadFile(fileName, temFileName, materialPath, localMaterialPath);
							
							try {
								//加密临时文件
								des.DesCryptFile(localMaterialPath + "/" + temFileName, localMaterialPath + "/" + fileName);
								//删除临时文件
								this.deleteFile(localMaterialPath, temFileName);
							} catch (Exception e) {
								log.error(e);
								e.printStackTrace();
							}
						}
					}
					ftpService.disConnectFtpServer();
				}
				
				List<DOcgInfo> ocgList = ocgInfoDao.getOcgInfoList();
				boolean exsit = false;
				for(DOcgInfo ocg : ocgList){
					if(area.getAreaCode().equals(ocg.getAreaCode())){
						exsit = true;
						log.info("向OCG发送素材和配置文件");
						uploadFileToOCG(ocg,configFileName,localMaterialPath);
						
					}
					
				}

				log.info("发送UNT更新通知");
				sendUNTMessage(areaCode);
				
				if(!exsit){
					String errMsg = "无线广告未配置区域【" + areaCode + "】的OCG FTP连接信息";
					log.error(errMsg);
					warnHelper.writeWarnMsgToDb(areaCode,errMsg);
					pushFalierHelper.changeDtmbStateToFailed(playlistList);
					continue;
				}
			}
		}
	}
	public void sendStartVideoAds() {
			//查询过期播出单，删除OCG上的广告素材
			processOverduePlayList(new Date(), ConstantsAdsCode.DPUSH_STARTSTB_VIDEO_HD, "高清开机视频", ConstantsAdsCode.DPUSH_STARTSTB_HD_VIDEO_FILE);
				
			//查询新增播出单
			List<PlayList> newAdsList = pushAdsDao.queryDStartAds(ConstantsAdsCode.DPUSH_STARTSTB_VIDEO_HD);
			if (newAdsList!=null && newAdsList.size()>0)
			{
				for (PlayList adGis : newAdsList)
				{
					String areaCode = adGis.getAreaCode(); //单向投放广告，播出单只会有具体某个地市的区域码
					Integer playListId = adGis.getId();
					
					log.info("开始往区域"+areaCode+"发送高清开机视频广告");
					
					//连接素材FTP服务器
					
					boolean conSuccessful = ftpService.connectServer(InitConfig.getAdsConfig().getAdResource().getIp(), Integer.valueOf(InitConfig.getAdsConfig().getAdResource().getPort()), InitConfig.getAdsConfig().getAdResource().getUser(), InitConfig.getAdsConfig().getAdResource().getPwd());
					
					if(!conSuccessful){	//还可以重新投放
						if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
							continue;
						}else{ //投放三次失败
							failedToPush(playListId);
							continue;
						}
					}
					
					if (StringUtils.isNotBlank(adGis.getResourcePath())){
						
						String downLoadPath = InitConfig.getAdsConfig().getDunRealTimeAds().getAdsTempPath() + "/" + areaCode;
						String downLoadFilePath = downLoadPath + "/" + ConstantsAdsCode.DPUSH_STARTSTB_HD_VIDEO_FILE;
						String remotePath = InitConfig.getAdsResourcePath();
						String temFileName = "tem_" + ConstantsAdsCode.DPUSH_STARTSTB_HD_VIDEO_FILE;
						
						log.info("下载开机视频素材到本地..");
						ftpService.downloadFile(adGis.getResourcePath(), ConstantsAdsCode.DPUSH_STARTSTB_HD_VIDEO_FILE, remotePath, downLoadPath);
						/*ftpService.downloadFile(adGis.getResourcePath(), temFileName, remotePath, downLoadPath);
						Des des = new Des();
						try {
							des.DesCryptFile(downLoadPath + "/" + temFileName, downLoadFilePath);
							this.deleteFile(downLoadPath, temFileName);
						} catch (Exception e) {
							log.error(e);
							e.printStackTrace();
						}*/
													
						List<DOcgInfo> ocgList = ocgInfoDao.getOcgInfoList();

						boolean ocgExist = false;
						boolean ocgSuccess = false;
						
						for (DOcgInfo ocg : ocgList) {

							if (ocg.getAreaCode().equals(areaCode)) {
								
								ocgExist = true;
								
								String ocgIp = ocg.getIp();
								int ocgPort = Integer.parseInt(ocg.getPort());
								String ocgUser = ocg.getUser();
								String ocgPwd = ocg.getPwd();
								
								//建立OCG服务器的FTP连接
								if(!ocgService.connectFtpServer(ocgIp, ocgPort, ocgUser, ocgPwd) ){
									continue;
								}
								//向OCG发送素材					
								log.info("向OCG发送素材..");
								String remoteDir = InitConfig.getAdsConfig().getDunRealTimeAds().getAdsTargetPath();
								ocgService.sendFileToFtp(downLoadFilePath, remoteDir);
								
								//断开OCG服务器的FTP连接
								ocgService.disConnectFtpServer();
								
								//通知OCG投放广告
								if(!ocgService.startOcgPlayByIp(ocgIp, remoteDir, ConstantsHelper.MAIN_CHANNEL, ConstantsHelper.UI_TYPE)){
									continue;
								}
								ocgSuccess = true;
							}
						}
						
						if(!ocgExist){
							String errMsg = "无线广告未配置区域【" + areaCode + "】的OCG FTP连接信息";
							log.error(errMsg);
							warnHelper.writeWarnMsgToDb(areaCode,errMsg);
							failedToPush(playListId);
							continue;
						}
						
						if(!ocgSuccess){
							//还可以重试
							if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
								continue;
							}else{  //三次都失败
								failedToPush(playListId);
								continue;
							}
						}
							
							
						if(!uixService.sendUiUpdateMsg(ConstantsHelper.UI_PLAY, areaCode, UIUpdate.VIDEO.getType(), UIUpdate.VIDEO.getFileName())){
							//UI更新通知，若不成功
							if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
								continue;
							}else{
								failedToPush(playListId);
								continue;
							}
						}		
						
						log.info("往区域"+areaCode+"发送高清开机视频广告结束");			
						//}					
						//更新播出单状态为已执行
						unRealTimeAdsPushHelper.cleanPushMap(playListId);
						pushAdsDao.updateDAdsFlag(adGis.getId(), "1");			
					}
				}
			}
		}
	//查询过期播出单，删除OCG上的广告素材
		private void processOverduePlayList(Date startTime, String adsCode, String adsDesc, String remoteFileName){
			List<PlayList> deadAdsList = pushAdsDao.queryDEndAds(adsCode);	
			
			List<TReleaseArea> areaList = areaDao.getAllArea();
			if(deadAdsList == null || deadAdsList.size() == 0){
				return;
			}
			// TODO 循环处理播出单
			for (TReleaseArea area : areaList){
				
				String areaCode = area.getAreaCode();
				List<Integer> playIdList = new ArrayList<Integer>();
				boolean isDefault = false;
				for(PlayList adGis : deadAdsList){
					if(areaCode.equals(adGis.getAreaCode())){ //单向投放广告，播出单只会有具体某个地市的区域码
						playIdList.add(adGis.getId());
						if("1".equals(adGis.getIsDefault())){
							isDefault = true;
						}
					}
				}
				if(playIdList.size() > 0){
					List<DOcgInfo> ocgList = ocgInfoDao.getOcgInfoList();
					for (DOcgInfo ocg : ocgList) {
						
						if (ocg.getAreaCode().equals(areaCode)) {
							String ocgIp = ocg.getIp();
							int ocgPort = Integer.parseInt(ocg.getPort());
							String ocgUser = ocg.getUser();
							String ocgPwd = ocg.getPwd();
							//建立OCG服务器的FTP连接
							if(ocgService.connectFtpServer(ocgIp, ocgPort, ocgUser, ocgPwd)){ //连不上就不删了，UI类广告订单到期删除OCG素材意义不大
								//通过FTP删除OCG上的文件			
								String remoteDir = InitConfig.getAdsConfig().getDunRealTimeAds().getAdsTargetPath();
								
								log.info(adsDesc + "广告播出单到期，删除OCG素材   areaCode: " + areaCode + ", ip: " + ocgIp +  ", file: " + remoteDir+ "/" + remoteFileName);
								
								ocgService.deleteFtpFileIfExist(remoteFileName, remoteDir);
								
								//断开OCG服务器的FTP连接
								ocgService.disConnectFtpServer();
							}
						}
					}
					
					//删除描述符
					if(ConstantsAdsCode.DPUSH_STARTSTB_HD.equals(adsCode)){
						if(isDefault){ //默认开机图片
							uixService.delUiUpdateMsg(areaCode, UIUpdate.PIC.getType(), UIUpdate.PIC.getFileName(),true);
							//默认图片到期更新adctrl=1
							areaDao.updateAdCtrlByAreaCode(areaCode, "1");
						}else{
							uixService.delUiUpdateMsg(areaCode, UIUpdate.PIC.getType(), UIUpdate.PIC.getFileName(),false);
						}
						
						
					}else if(ConstantsAdsCode.DPUSH_STARTSTB_VIDEO_HD.equals(adsCode)){
						uixService.delUiUpdateMsg(areaCode, UIUpdate.VIDEO.getType(), UIUpdate.VIDEO.getFileName(),false);
					}
					for(Integer id : playIdList){
						pushAdsDao.updateDAdsFlag(id, "4");
					}
					
				}
			}
		}
		public void sendStartPicAds() {
			
			//查询过期播出单，删除OCG上的广告素材
			processOverduePlayList(new Date(), ConstantsAdsCode.PUSH_STARTSTB_HD, "高清开机图片", ConstantsAdsCode.PUSH_STARTSTB_HD_IFRAME_FILE);
			
			//查询新增播出单
			List<PlayList> newAdsList = pushAdsDao.queryDStartAds(ConstantsAdsCode.DPUSH_STARTSTB_HD);
			List<TReleaseArea> areaList = areaDao.getAllArea();
			if(newAdsList == null || newAdsList.size() == 0){
				return;
			}
			// TODO 循环处理播出单
			for (TReleaseArea area : areaList){
				String areaCode = area.getAreaCode(); //单向投放广告，播出单只会有具体某个地市的区域码
				String downLoadPath = InitConfig.getAdsConfig().getDunRealTimeAds().getAdsIframeHDTempPath()+"/"+areaCode+"/"+ConstantsAdsCode.START_RESOURCE_PATH;
				String remotePath = InitConfig.getAdsResourcePath();
				boolean isDefault = false;
				List<Integer> playIdList = new ArrayList<Integer>();
				for (PlayList adGis : newAdsList){
					if(areaCode.equals(adGis.getAreaCode())){
						
						Integer playListId = adGis.getId();
						playIdList.add(playListId);
					
						log.info("开始往区域"+areaCode+"发送高清开机图片广告");
										
						//连接素材FTP服务器
						boolean conSuccessful = ftpService.connectServer(InitConfig.getAdsConfig().getAdResource().getIp(), Integer.valueOf(InitConfig.getAdsConfig().getAdResource().getPort()), InitConfig.getAdsConfig().getAdResource().getUser(), InitConfig.getAdsConfig().getAdResource().getPwd());
						
						if(!conSuccessful){
							//还可以重新投放
							if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
								continue;
							}else{ //投放三次失败
								failedToPush(playListId);
								continue;
							}
						}
														
						log.info("下载开机图片素材到本地..");
						
						if ("1".equals(adGis.getIsDefault())){	
							isDefault = true;
							ftpService.downloadFile(adGis.getResourcePath(), "24.iframe", remotePath, downLoadPath);
							
						}else{	
							ftpService.downloadFile(adGis.getResourcePath(), adGis.getIndexNum()+".iframe", remotePath, downLoadPath);
							
						}
					}
				}
				if(playIdList.size() == 0){
					continue;
				}
				log.info("压缩开机图片素材..");
				String zipFilePath = InitConfig.getAdsConfig().getDunRealTimeAds().getAdsTempPath()+"/"+areaCode;
				//String temFileName = "tem_" + ConstantsAdsCode.DPUSH_STARTSTB_HD_IFRAME_FILE;
				linuxRun("zip -r",downLoadPath,zipFilePath+"/"+ConstantsAdsCode.DPUSH_STARTSTB_HD_IFRAME_FILE);
				/*Des des = new Des();
				try {
					des.DesCryptFile(zipFilePath+"/"+temFileName, zipFilePath+"/"+ConstantsAdsCode.DPUSH_STARTSTB_HD_IFRAME_FILE);
					this.deleteFile(zipFilePath, temFileName);
				} catch (Exception e) {
					log.error(e);
					e.printStackTrace();
				}*/
				
				List<DOcgInfo> ocgList = ocgInfoDao.getOcgInfoList();

				boolean ocgExist = false;
				boolean ocgSuccess = false;
				
				for (DOcgInfo ocg : ocgList) {

					if (ocg.getAreaCode().equals(areaCode)) {
						
						ocgExist = true;
						
						String ocgIp = ocg.getIp();
						int ocgPort = Integer.parseInt(ocg.getPort());
						String ocgUser = ocg.getUser();
						String ocgPwd = ocg.getPwd();
						
						//建立OCG服务器的FTP连接
						if(!ocgService.connectFtpServer(ocgIp, ocgPort, ocgUser, ocgPwd) ){
							continue;
						}
						//向OCG发送开机素材
		            	log.info("向OCG发送开机图片素材..");
		            	String remoteDir = InitConfig.getAdsConfig().getDunRealTimeAds().getAdsTargetPath();
		            	ocgService.sendFileToFtp(zipFilePath+"/"+ConstantsAdsCode.DPUSH_STARTSTB_HD_IFRAME_FILE, remoteDir);
		            	
		            	//断开OCG服务器的FTP连接
		            	ocgService.disConnectFtpServer();
		            	
		            	//通知OCG投放广告
						if(!ocgService.startOcgPlayByIp(ocgIp, remoteDir, ConstantsHelper.MAIN_CHANNEL, ConstantsHelper.UI_TYPE)){
							continue;
						}
						ocgSuccess = true;
					}	
				}
				
				if(!ocgExist){
					String errMsg = "未配置区域【" + areaCode + "】的OCG FTP连接信息";
					log.error(errMsg);
					warnHelper.writeWarnMsgToDb(areaCode,errMsg);
					for(Integer playListId : playIdList){
						failedToPush(playListId);
					}
					continue;
				}
				
				if(!ocgSuccess){
					//还可以重试
					for(Integer playListId : playIdList){
						if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
							continue;
						}else{  //三次都失败
							failedToPush(playListId);
							continue;
						}
					}
				}
				
				//往区域发送NIT描述符插入信息
				
				boolean uiUpdateSuccess;
				
				if(isDefault){
					uiUpdateSuccess = uixService.sendUiUpdateMsg(ConstantsHelper.UI_PLAY, areaCode, UIUpdate.PIC.getType(), UIUpdate.PIC.getFileName(),true);
					//默认开机图片更新adctrl值为0
					areaDao.updateAdCtrlByAreaCode(areaCode, "0");
				}else{
					uiUpdateSuccess = uixService.sendUiUpdateMsg(ConstantsHelper.UI_PLAY, areaCode, UIUpdate.PIC.getType(), UIUpdate.PIC.getFileName());
				}
				
				if(!uiUpdateSuccess){
					//UI更新通知，若不成功
					for(Integer playListId : playIdList){
						if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
							continue;
						}else{
							failedToPush(playListId);
							continue;
						}
					}
				}
								
				log.info("往区域"+areaCode+"发送高清开机图片广告结束");
				
				//更新播出单状态为已执行
				for(Integer playListId : playIdList){
					unRealTimeAdsPushHelper.cleanPushMap(playListId);
					pushAdsDao.updateDAdsFlag(playListId, "1");    
				}
            }				
		}
		@SuppressWarnings("unchecked")
		public void sendAdResourceAds() {	
			//分区域处理	
			List<TReleaseArea> areaEntityList = areaDao.getAllArea();
			for(TReleaseArea areaEntity : areaEntityList){
				
				String areaCode = areaEntity.getAreaCode();
				
				boolean changed = false;
				
				String romatePath = InitConfig.getAdsResourcePath();
				String downLoadPath = InitConfig.getAdsConfig().getDunRealTimeAds().getAdsaudioHDTempPath()+"/"+areaCode+"/"+ConstantsAdsCode.DADVRESOURCE_HD_PATH;				
				String zipFilePath = InitConfig.getAdsConfig().getDunRealTimeAds().getAdsTempPath()+"/"+areaCode;
				
				//查询过期列表，删除本地素材（根据播出单删除素材）	 
		        String hql =  "from PlayList ads where ads.status=1 and ads.areaCode = ? and ads.endDate <=curdate() and ads.endTime<= date_format(now(),'%H:%i:%s') and (ads.positionCode =? or ads.positionCode =? )";
		            
		        List<PlayList> resultList = pushAdsDao.getTemplate().find(hql, areaCode, ConstantsAdsCode.DPUSH_FREQUENCE_HD, ConstantsAdsCode.DPUSH_LIVE_UNDER_HD);
		        
		        if(null != resultList && resultList.size() > 0){
		        	changed = true; 
		        	for(PlayList playListEntity : resultList){
		        		if( ConstantsAdsCode.DPUSH_FREQUENCE_HD.equals(playListEntity.getPositionCode()) ){  //音频背景广告
		        			String[] serviceIdList = playListEntity.getTypeValue().split(",");
		        			if(null != serviceIdList && serviceIdList.length > 0){
		        				log.info("广播收听背景广告播出单过期，删除本地素材..");
		        				for(String serviceId : serviceIdList){
		        					deleteFile(downLoadPath, serviceId+".iframe");
								}
		        			}
		        		}else if( ConstantsAdsCode.DPUSH_LIVE_UNDER_HD.equals(playListEntity.getPositionCode()) ){  //直播下排广告
		        			log.info("直播下排广告播出单过期，删除本地素材..");
		        			String f1 = ConstantsAdsCode.LIVE_UNDER_HD_FILE1;
		        			String f2 = ConstantsAdsCode.LIVE_UNDER_HD_FILE2;
		        			String f3 = ConstantsAdsCode.LIVE_UNDER_HD_FILE3;
		        			deleteFile(downLoadPath, f1);
		        			deleteFile(downLoadPath, f2);
		        			deleteFile(downLoadPath, f3);
		        			//再传默认素材进去
		        			log.info("添加默认素材至本地..");
		        			String rootPath = this.getClass().getClassLoader().getResource("").getPath();
		        			try {
		        				File downLoadDir = new File(downLoadPath);
								FileUtils.copyFileToDirectory(new File(rootPath + "/" + f1), downLoadDir);
								FileUtils.copyFileToDirectory(new File(rootPath + "/" + f2), downLoadDir);
								FileUtils.copyFileToDirectory(new File(rootPath + "/" + f3), downLoadDir);
							} catch (IOException e) {
								log.error("复制文件出现异常", e);
							}
		        		}
		        		//播出单状态更新为过期
		        		pushAdsDao.updateDAdsFlag(playListEntity.getId(), "4");
		        	}
		        }
			
				//查询新增列表， 下载素材到本地 
		        String newHql =  "from PlayList ads where ads.status = 0 and ads.areaCode = ? and curdate() between ads.startDate and ads.endDate and curtime() between ads.startTime and ads.endTime and (ads.positionCode =? or ads.positionCode =? )";
		        List<PlayList> newResultList = pushAdsDao.getTemplate().find(newHql, areaCode, ConstantsAdsCode.DPUSH_FREQUENCE_HD, ConstantsAdsCode.DPUSH_LIVE_UNDER_HD);
				if(null != newResultList && newResultList.size() > 0){
					changed = true; 
					for(PlayList playListEntity : newResultList){
						
						Integer playListId = playListEntity.getId();
						
						//连接素材FTP服务器
						ftpService.connectServer(InitConfig.getAdsConfig().getAdResource().getIp(), Integer.valueOf(InitConfig.getAdsConfig().getAdResource().getPort()), InitConfig.getAdsConfig().getAdResource().getUser(), InitConfig.getAdsConfig().getAdResource().getPwd());
						
						if( ConstantsAdsCode.DPUSH_FREQUENCE_HD.equals(playListEntity.getPositionCode()) ){
							//从素材FTP服务器下载素材
							String[] serviceIdList = playListEntity.getTypeValue().split(",");
							if(serviceIdList == null || serviceIdList.length <=0 ){
								log.error("广播收听背景播出单 " + playListEntity.getId() + " 频道列表为空");
								pushAdsDao.updateDAdsFlag(playListId, "3");
								continue;						
							}else{
								log.info("下载广播收听背景素材到本地..");
								for(String serviceId : serviceIdList){
									ftpService.downloadFile(playListEntity.getResourcePath(),serviceId+".iframe", romatePath, downLoadPath);
								}
							}			
						}else if(ConstantsAdsCode.DPUSH_LIVE_UNDER_HD.equals(playListEntity.getPositionCode())){
							 
							
							if(null != playListEntity.getResourcePath()){
								log.info("下载直播下排素材到本地..");
								
								ftpService.downloadFile(playListEntity.getResourcePath(), ConstantsAdsCode.LIVE_UNDER_HD_FILE_PREFIX + playListEntity.getIndexNum() + ConstantsAdsCode.LIVE_UNDER_HD_FILE_POSTFIX, romatePath, downLoadPath);
								
							}
						}					
					}			
					
				}       
		        
				if(changed){
					
					//删除 advResource-c.dat
					deleteFile(zipFilePath, ConstantsAdsCode.ADVRESOURCE_C);
					
					//压缩文件
					log.info("压缩素材文件..");
					String sourcePath = InitConfig.getAdsConfig().getDunRealTimeAds().getAdsaudioHDTempPath()+"/"+areaCode+"/" + "advResource-c";				
					String temFileName = "tem_" + ConstantsAdsCode.ADVRESOURCE_C;
					linuxRun("zip -r",sourcePath,zipFilePath+"/"+temFileName);
					Des des = new Des();
					try {
						des.DesCryptFile(zipFilePath+"/"+temFileName, zipFilePath+"/"+ConstantsAdsCode.ADVRESOURCE_C);
						this.deleteFile(zipFilePath, temFileName);
					} catch (Exception e) {
						log.error(e);
						e.printStackTrace();
					}
					
					List<DOcgInfo> ocgList = ocgInfoDao.getOcgInfoList();

					//boolean ocgExist = false;
					
					for (DOcgInfo ocg : ocgList) {
						if (ocg.getAreaCode().equals(areaCode)) {
							
							//ocgExist = true;
							
							String ocgIp = ocg.getIp();
							int ocgPort = Integer.parseInt(ocg.getPort());
							String ocgUser = ocg.getUser();
							String ocgPwd = ocg.getPwd();
							
							//建立OCG服务器的FTP连接
							ocgService.connectFtpServer(ocgIp, ocgPort, ocgUser, ocgPwd);
							
							//向OCG发送素材文件
							log.info("向OCG发送广告素材..");
							String remoteDir = InitConfig.getAdsConfig().getDunRealTimeAds().getAdsTargetPath();
			            	ocgService.sendFileToFtp(zipFilePath+"/"+ConstantsAdsCode.ADVRESOURCE_C, remoteDir);
			            	
			            	ocgService.disConnectFtpServer();
			            	
			            	//OCG播发广告
			            	ocgService.startOcgPlayByIp(ocgIp, remoteDir, ConstantsHelper.MAIN_CHANNEL, ConstantsHelper.UI_TYPE);
							
						}
					}
	            	
	            	//UI更新
	            	uixService.sendUiUpdateMsg(ConstantsHelper.UI_PLAY, areaCode, UIUpdate.ADV.getType(), UIUpdate.ADV.getFileName());
	            	
	            	//更新播出单状态
	            	if(null != newResultList && newResultList.size() > 0){
	            		for(PlayList playListEntity : newResultList){
	            			pushAdsDao.updateDAdsFlag(playListEntity.getId(), "1");
	            		}         		
	            	}
	                log.info("往区域"+areaCode+"发送广告结束");			
				}
			}
		}
		
		@SuppressWarnings("unchecked")
		public void sendSubtitleAds() {
			
			//查询过期列表（播出单结束时间 < 当前时间），发送不显示字幕，更新播出单状态为4	 
			String overdueHql =  "from PlayList pl where pl.status=1 and pl.endDate <=curdate() and pl.endTime<= date_format(now(),'%H:%i:%s') and pl.positionCode =? ";			            
			List<PlayList> overdueList = pushAdsDao.getTemplate().find(overdueHql, ConstantsAdsCode.DPUSH_SUBTITLE);
			sendSubtitle(overdueList, "0", "4");
			//查询过期列表（播出单结束时间 < 当前时间），发送不显示字幕，更新播出单状态为4	 
			String overWindowHql =  "from PlayList pl where pl.status=1 and curtime() >= str_to_date(pl.endTime,'%H:%i:%s') and pl.positionCode =? ";			            
			List<PlayList> overWindowList = pushAdsDao.getTemplate().find(overWindowHql, ConstantsAdsCode.DPUSH_SUBTITLE);
			sendSubtitle(overWindowList, "0", "2");
			
			if(overdueList != null && overdueList.size() > 0){
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//查询新增列表（播出单结束时间 > 当前时间，且状态为0），发送显示字幕，更新播出单状态为1
			String newHql =  "from PlayList ads where ads.status in(0,2) and curdate() between ads.startDate and ads.endDate and date_format(now(),'%H:%i:%s') between ads.startTime and ads.endTime and ads.positionCode =? ";   
			List<PlayList> newList = pushAdsDao.getTemplate().find(newHql, ConstantsAdsCode.DPUSH_SUBTITLE);
			sendSubtitle(newList, "1", "1");
		}
		
		private void sendSubtitle(List<PlayList> playLists, String actionType, String state){
			if(null != playLists && playLists.size() > 0){
				//Des des = new Des();
				for(PlayList adGis : playLists){
					String areaCode = adGis.getAreaCode(); 
					
					if("1".equals(actionType)){
						log.info("向区域" + areaCode + "投放菜单字幕广告...");
					}else{
						log.info("区域" + areaCode + "菜单字幕广告到期，发停止消息...");
					}
					
					Gson gson = new Gson();
					TextMate text = gson.fromJson(adGis.getResourcePath(), TextMate.class);
					String[] words = text.getContent().split("@_@");
					
					ChannelSubtitleInfo subtitleInfo = new ChannelSubtitleInfo();
					subtitleInfo.setUiId("a");
					subtitleInfo.setActionType(actionType);
					subtitleInfo.setTimeout(text.getDurationTime() + "");
					subtitleInfo.setFontColor(text.getFontColor());
					subtitleInfo.setFontSize(text.getFontSize() + "");
					String[] coordinate = text.getPositionVertexCoordinates().split("\\*");
					subtitleInfo.setBackgroundX(coordinate[0]);
					subtitleInfo.setBackgroundY(coordinate[1]);
					String[] widthHeight = text.getPositionWidthHeight().split("\\*");
					subtitleInfo.setBackgroundWidth(widthHeight[0]);
					subtitleInfo.setBackgroundHeight(widthHeight[1]);
					subtitleInfo.setBackgroundColor(text.getBkgColor());
					subtitleInfo.setShowFrequency(text.getRollSpeed() + "");
					
					List<SubtitlePart> subtitleInfoList = new ArrayList<SubtitlePart>();
					for(int i =0; i < words.length; i ++){
						String word = words[i].trim();
						/*try {
							word = des.DesCryptString(word);
						} catch (Exception e) {
							log.error(e);
							e.printStackTrace();
						}*/
						if(StringUtils.isBlank(word)){
							continue;
						}
						SubtitlePart part = new SubtitlePart();
						part.setId(i+"");
						part.setWord(word);
						subtitleInfoList.add(part);
					}
					SubtitleContent content = new SubtitleContent();
					content.setSubtitleList(subtitleInfoList);
					Subtitle subtitle = new Subtitle();
					subtitle.setEncryption("1");
					subtitle.setKey(InitConfig.getConfigMap().get("des.key"));
					subtitle.setSubtitleInfo(subtitleInfo);
					subtitle.setSubtileContent(content);
			
					//向OCG发送UNT消息
					boolean success = ocgService.sendDtmbUntUpdateByAreaCode(ConstantsHelper.REALTIME_UNT_MESSAGE_MSUBTITLE, subtitle, areaCode, null);
					
					if(success){
						pushAdsDao.updateDAdsFlag(adGis.getId(), state);
					}else{
						pushAdsDao.updateDAdsFlag(adGis.getId(), "3");
						pushAdsDao.updateDOrderState(adGis.getId(), "9");
					}
				}
			}
		}
		@SuppressWarnings("unchecked")
		public void sendChannelSubtitleAds() {
						
			//查询过期列表（播出单结束时间 < 当前时间），发送不显示字幕，更新播出单状态为4	 
			String overdueHql =  "from PlayList ads where ads.status=1 and ads.endDate <=curdate() and ads.endTime<= date_format(now(),'%H:%i:%s') and ads.positionCode =? ";			            
			List<PlayList> overdueList = pushAdsDao.getTemplate().find(overdueHql, ConstantsAdsCode.DPUSH_CHANNEL_SUBTITLE);
			sendChannelSubtitle3(overdueList,"0","4");
			//查询过期列表（播出单结束时间 < 当前时间），发送不显示字幕，更新播出单状态为4	 
			String overwindowHql =  "from PlayList ads where ads.status=1 and curtime()>= str_to_date(ads.endTime,'%H:%i:%s') and ads.positionCode =? ";			            
			List<PlayList> overwindowList = pushAdsDao.getTemplate().find(overwindowHql, ConstantsAdsCode.DPUSH_CHANNEL_SUBTITLE);
			sendChannelSubtitle3(overwindowList,"0","2");
			if(overdueList != null && overdueList.size() > 0){
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			//查询新增列表（播出单结束时间 > 当前时间，且状态为0），发送显示字幕，更新播出单状态为1
			String newHql =  "from PlayList ads where ads.status = 0 and curdate() between ads.startDate and ads.endDate and curtime() between ads.startTime and ads.endTime and ads.positionCode =? ";    
			List<PlayList> newList = pushAdsDao.getTemplate().find(newHql, ConstantsAdsCode.DPUSH_CHANNEL_SUBTITLE);
			sendChannelSubtitle3(newList,"1","1");
			
		}
		private void sendChannelSubtitle3(List<PlayList> playLists, String actionType, String state){
			List<String> areaList = InitAreas.getInstance().getAreas();
			//Des des = new Des();
			for(String areaCode : areaList){
				List<PlayList> pickedList = pickPlayListByAreaCode(playLists, areaCode);
				if(pickedList.size() > 0){
					if("1".equals(actionType)){
						log.info("向区域" + areaCode + "投放频道字幕广告...");
					}else{
						log.info("区域" + areaCode + "频道字幕广告到期，发停止消息...");
					}
					
					Map<String, List<ChannelSubtitleElement>> channelTsMap =  new HashMap<String, List<ChannelSubtitleElement>>();
					//拼装字幕UNT消息
					for(PlayList adGis : pickedList){					
						String tvn = adGis.getTvn();
						String userIndustrys = adGis.getUserindustries();
						String userLevels = adGis.getUserlevels();
						
						TvnTarget tvnTargetModel = new TvnTarget();
						tvnTargetModel.setTvnType("2"); 
						tvnTargetModel.setTvn(tvn);
						tvnTargetModel.setCaIndustryType(userIndustrys);
						tvnTargetModel.setCaUserLevel(userLevels);
						
						String[] serviceIdList = adGis.getTypeValue().split(",");
						for(String serviceId : serviceIdList){
							
							TvnTarget tvnTarget = new TvnTarget();
							BeanUtils.copyProperties(tvnTargetModel, tvnTarget);
							tvnTarget.setServiceID(serviceId);
							
							Gson gson = new Gson();
							TextMate text = gson.fromJson(adGis.getResourcePath(), TextMate.class);
							
							ChannelSubtitleInfo subtitleInfo = new ChannelSubtitleInfo();
							subtitleInfo.setUiId("a");
							subtitleInfo.setActionType(actionType);
							subtitleInfo.setTimeout(text.getDurationTime() + "");
							subtitleInfo.setFontColor(text.getFontColor());
							subtitleInfo.setFontSize(text.getFontSize() + "");
							String[] coordinate = text.getPositionVertexCoordinates().split("\\*");
							subtitleInfo.setBackgroundX(coordinate[0]);
							subtitleInfo.setBackgroundY(coordinate[1]);
							String[] widthHeight = text.getPositionWidthHeight().split("\\*");
							subtitleInfo.setBackgroundWidth(widthHeight[0]);
							subtitleInfo.setBackgroundHeight(widthHeight[1]);
							subtitleInfo.setBackgroundColor(text.getBkgColor());
							subtitleInfo.setShowFrequency(text.getRollSpeed() + "");
							
							String[] words = text.getContent().split("@_@");
							List<SubtitlePart> subtitleList = new ArrayList<SubtitlePart>();
							for(int i = 0; i < words.length; i ++){
								String word = words[i].trim();
								if(StringUtils.isBlank(word)){
									continue;
								}
								/*try {
									word = des.DesCryptString(word);
								} catch (Exception e) {
									log.error(e);
									e.printStackTrace();
								}*/
								SubtitlePart part = new SubtitlePart();
								part.setId(i + "");
								part.setWord(word);
								
								subtitleList.add(part);
							}
							SubtitleContent content = new SubtitleContent();
							content.setSubtitleList(subtitleList);
							ChannelSubtitleElement elem = new ChannelSubtitleElement();
							elem.setTvnTarget(tvnTarget);
							elem.setSubtitleInfo(subtitleInfo);
							elem.setSubtitleContent(content);
							
							String tsId = pushAdsDao.getAreaChannelTsId(areaCode, serviceId);
							if(StringUtils.isBlank(tsId)){
								continue;
							}
							List<ChannelSubtitleElement> elementList = channelTsMap.get(tsId);
							if(elementList == null){
								elementList = new ArrayList<ChannelSubtitleElement>();
								channelTsMap.put(tsId, elementList);
							}
							elementList.add(elem);
						}
					}
					boolean success = false;
					for(String tsId : channelTsMap.keySet()){
						List<ChannelSubtitleElement> channelSubtitleList = channelTsMap.get(tsId);
					
						ChannelSubtitle channelSubtitle = new ChannelSubtitle();
						channelSubtitle.setTsId(tsId);
						channelSubtitle.setEncryption("1");
						channelSubtitle.setKey(InitConfig.getConfigMap().get("des.key"));
						channelSubtitle.setChannelSubtitleElemList(channelSubtitleList);
						//向OCG发送UNT消息
						success = ocgService.sendDtmbUntUpdateByAreaCode(ConstantsHelper.REALTIME_UNT_MESSAGE_CHANNEL_SUBTITLE, channelSubtitle, areaCode, tsId);
						if(!success){
							break;
						}
					}
					if(success){
						for(PlayList adGis : pickedList){					
							pushAdsDao.updateDAdsFlag(adGis.getId(), state);
						}
					}else{
						for(PlayList adGis : pickedList){					
							pushAdsDao.updateDAdsFlag(adGis.getId(), "3");
							pushAdsDao.updateDOrderState(adGis.getId(), "9");
						}
					}
				}
			}
		}
	
	 private void linuxRun(String cmd,String inpath,String outfile){
		try {
			String dir = inpath.substring(0,inpath.lastIndexOf("/"));
			String zippath = inpath.substring(inpath.lastIndexOf("/")+1);
			File workdir =  new File(dir);
			
			Process pro= Runtime.getRuntime().exec(cmd +" " + outfile+ " "+zippath,null,workdir);
			pro.waitFor();
		}catch(Exception e)	{
			log.error("压缩开机图片出现异常 !" + e.getMessage());
		}
	}
	private Set<String> generateConfigFile(String fileName, Map<String, Map<String, String[]>> areaAdsMap){
		Set<String> fileSet = new HashSet<String>();
		
		try	{
			int size = areaAdsMap.size();
			int count = 0;
			BufferedWriter write= new BufferedWriter(new FileWriter(fileName));
			StringBuffer sb = new StringBuffer();
			sb.append("var advConfig = true;\n");
			sb.append("var isHD = true ;\n");
			sb.append("if(System.isHDPlatform == 4 || System.isHDPlatform == 3 || System.isHDPlatform == 1 || System.isHDPlatform == false)isHD = false ;\n");
			sb.append("var advInfo = [\n");
			for(String serviceId : areaAdsMap.keySet()){
				count ++;
				if(count == size){
					sb.append("{serviceId:"+serviceId+",locationVar:\"locationVar" + serviceId +"\"}" + "\n");
				}else{
					sb.append("{serviceId:"+serviceId+",locationVar:\"locationVar" + serviceId +"\"}," + "\n");
				}
				
			}
			sb.append("];\n");
			
			for(String serviceId : areaAdsMap.keySet()){
				
				Map<String, String[]> innerMap = areaAdsMap.get(serviceId);
				count = 0;
				size = innerMap.size();
				sb.append("var locationVar" + serviceId + "= [\n");
				for(String position : innerMap.keySet()){
					count ++;
					String[] fileNames = innerMap.get(position);
					fileSet.add(fileNames[0]);
					fileSet.add(fileNames[1]);
					
					if(count == size){
						sb.append("{position:\""+ position + "\",areaCode:0,imgSrc:isHD?\"" + fileNames[0] +"\":\"" + fileNames[1] +"\"}\n");
					}else{
						sb.append("{position:\""+ position + "\",areaCode:0,imgSrc:isHD?\"" + fileNames[0] +"\":\"" + fileNames[1] +"\"},\n");
					}
				}
				sb.append("];\n");
				
			}
			write.write(sb.toString());
			write.flush();
			write.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return fileSet;
	}
	
	private void uploadFileToOCG(DOcgInfo ocg, String configFileName, String localMaterialPath){
		if(ocg == null){
			log.info("OCG信息为空！");
			return;
		}
		String ocgIp = ocg.getIp();
		int ocgPort = Integer.parseInt(ocg.getPort());
		String ocgUser = ocg.getUser();
		String ocgPwd = ocg.getPwd();
		
		if(!ocgService.connectFtpServer(ocgIp, ocgPort, ocgUser, ocgPwd) ){
			sendFlagHelper.decreaseSendTimes(ocg.getAreaCode());
			log.info("上传素材，连接OCG ftp "+ ocgIp + "失败！");
			return;
		}
		String ocgMaterialPath = InitConfig.getAdsConfig().getDrealTimeAds().getAdsTargetPath();
		String ocgConfigPath = InitConfig.getAdsConfig().getDrealTimeAds().getAdsTargetConfigPath();
		
		log.info("删除OCG目录中的素材..");
		ocgService.deleteFtpDirFiles(ocgMaterialPath);
    	
		log.info("向OCG发送素材..");
    	ocgService.sendDirFilesToFtp(localMaterialPath, ocgMaterialPath);
    	
    	log.info("向OCG发送配置文件..");
    	ocgService.sendFileToFtp(configFileName, ocgConfigPath);
    	
    	ocgService.disConnectFtpServer();
    	
    	String sendPath = getParentPath(ocgMaterialPath);
		if(!ocgService.startOcgPlayByIp(ocgIp, sendPath, ConstantsHelper.ALL_CHANNEL, ConstantsHelper.UNT_TYPE)){
			sendFlagHelper.decreaseSendTimes(ocg.getAreaCode());
			log.error("start ocg play failed! areacode : " + ocg.getAreaCode() + ", ip: " + ocgIp);
		}
	}
	private void sendUNTMessage(String areaCode){
		String ocgMaterialPath = InitConfig.getAdsConfig().getDrealTimeAds().getAdsTargetPath();
		String ocgConfigPath = InitConfig.getAdsConfig().getDrealTimeAds().getAdsTargetConfigPath();
		String configFileName = InitConfig.getAdsConfig().getDrealTimeAds().getAdsConfigFile();
		log.info("向OCG发送UNT素材更新通知..");
		String imageUpdatePath = ConstantsHelper.UNT_UPDATE_PATH_PREFIX + getDeepestDir(ocgMaterialPath) + "/";
		
		AdsImage imageUpdateMsg = new AdsImage();
		
		imageUpdateMsg.setFilepath(imageUpdatePath);
		imageUpdateMsg.setUiId(ConstantsHelper.UNT_UPDATE_TEMPLATE);
		
		boolean picUpdateSuccess = ocgService.sendDtmbUntUpdateByAreaCode(ConstantsHelper.REALTIME_UNT_MESSAGE_ADIMAGE, imageUpdateMsg, areaCode, null);			
		
		log.info("发UNT配置文件更新通知..");
		String configUpdatePath = ConstantsHelper.UNT_UPDATE_PATH_PREFIX + getDeepestDir(ocgConfigPath)  + "/" + configFileName;
		
		AdsConfigJs configUpdateMsg = new AdsConfigJs();
		
		configUpdateMsg.setFilepath(configUpdatePath);
		configUpdateMsg.setUiId(ConstantsHelper.UNT_UPDATE_TEMPLATE);
		
		boolean confUpdateSuccess = ocgService.sendDtmbUntUpdateByAreaCode(ConstantsHelper.REALTIME_UNT_MESSAGE_ADCONFIG, configUpdateMsg, areaCode, null);
		
		if(!picUpdateSuccess || !confUpdateSuccess){
			if(!picUpdateSuccess || !confUpdateSuccess){
				log.error("send unt update message failed! areacode : " + areaCode);
				sendFlagHelper.decreaseSendTimes(areaCode);
				return;
			}
		}
	}
	private Map<String, Map<String, String[]>> putAll(Map<String, Map<String, String[]>> map){
		Map<String, Map<String, String[]>> newMap = new HashMap<String, Map<String, String[]>>();
		
		for(String key : map.keySet()){
			Map<String, String[]> innerMap = new HashMap<String, String[]>();
			innerMap.putAll(map.get(key));
			newMap.put(key, innerMap);
			for(String innerKey : map.get(key).keySet()){
				String [] arrays = map.get(key).get(innerKey).clone();
				innerMap.put(innerKey, arrays);
			}
		}
		return newMap;
	}
	
	private void failedToPush(Integer playListId){
		unRealTimeAdsPushHelper.cleanPushMap(playListId);
		pushAdsDao.updateDAdsFlag(playListId, "3");
		unRealTimeAdsPushHelper.changeOrderStateToFailed(playListId);
	}
	private void deleteFile(String delDir, String fileName){
	   File f = new File(delDir + "/" + fileName);
	   if(f.exists()){
		   log.info("删除文件 " + f.getAbsolutePath());
		   f.delete();
	   }
	}
	private List<PlayList> pickPlayListByAreaCode(List<PlayList> playList, String areaCode){
		List<PlayList> resultList = new ArrayList<PlayList>();
		if(null != playList){
			for(PlayList gis : playList){
				if(gis.getAreaCode().equals(areaCode)){
					resultList.add(gis);
				}
			}
		}
		return resultList;
	}
	private String getParentPath(String path){
		   if(path.endsWith("/")){
			   path = path.substring(0, path.length() - 1);
		   }
		   return path.substring(0, path.lastIndexOf("/"));
   }
	private String getDeepestDir(String path){
		   String[] dirs = path.split("/");
			int length = dirs.length;		
			if(length > 0){
				return dirs[length -1];
			}	   
		   return path;
	   }
}
