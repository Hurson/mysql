package com.dvnchina.advertDelivery.order.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.order.bean.Order;
import com.dvnchina.advertDelivery.order.bean.OrderMaterialRelation;
import com.dvnchina.advertDelivery.order.bean.PlayListGis;
import com.dvnchina.advertDelivery.order.bean.playlist.BootImageInfo;
import com.dvnchina.advertDelivery.order.bean.playlist.BootResourceInfo;
import com.dvnchina.advertDelivery.order.bean.playlist.OrderBean;
import com.dvnchina.advertDelivery.order.bean.playlist.PloyPlayListGisRel;
import com.dvnchina.advertDelivery.order.bean.playlist.PrecisePlayListGisRel;
import com.dvnchina.advertDelivery.order.dao.PlayListGisDao;
import com.dvnchina.advertDelivery.order.dao.PlayListReqDao;
import com.dvnchina.advertDelivery.order.service.PlayListGisService;
import com.dvnchina.advertDelivery.order.service.PlayListReqService;
import com.dvnchina.advertDelivery.sysconfig.service.BaseConfigService;
import com.dvnchina.advertDelivery.utils.DateUtil;
import com.dvnchina.advertDelivery.utils.Transform;
import com.google.gson.Gson;

public class PlayListGisServiceImpl extends PlayListServiceImpl implements
		PlayListGisService {
	private static Logger logger = Logger
			.getLogger(PlayListGisServiceImpl.class);
	private PlayListGisDao playListGisDao;
	private PlayListReqDao playListReqDao;
	private PlayListReqService playListReqService;
	private BaseConfigService baseConfigService;

	public void setPlayListGisDao(PlayListGisDao playListGisDao) {
		this.playListGisDao = playListGisDao;
	}

	public void setPlayListReqDao(PlayListReqDao playListReqDao) {
		this.playListReqDao = playListReqDao;
	}

	public void setPlayListReqService(PlayListReqService playListReqService) {
		this.playListReqService = playListReqService;
	}

	public void setBaseConfigService(BaseConfigService baseConfigService) {
		this.baseConfigService = baseConfigService;
	}

	public int deleteAllPlayList(Integer orderId) {
		try {
			// 删除文件
//			String path = playListGisDao.getContentPath(orderId);
//			if (StringUtils.isNotBlank(path)) {
//				FtpUtils ftpUtil = null;
//				try {
//					ftpUtil = new FtpUtils();
//					ftpUtil.connectionFtp();
//					ftpUtil.deleteFile(path);
//				} catch (Exception e) {
//					e.printStackTrace();
//				} finally {
//					ftpUtil.closedFtp();
//				}
//			}
			playListGisDao.deleteAllPlayList(orderId);
		} catch (Exception e) {
			logger.error("删除投放式播出单出现异常",e);
			return 1;
		}
		return 0;
	}

	public int insertPlayList(Integer orderId) {
		List<PlayListGis> ps = orderToPlayList2(orderId,null);
		if (ps != null && ps.size()>0) {
			playListGisDao.insertPlayList(ps);
			return 0;
		} else {
			return 1;
		}
	}

	public int updatePlayListByDate(Order order) {
		try {
			/** 播出单最大结束时间 */
			Date endTime = playListGisDao.getPlayListEndTime(order.getId());

			/** 播出单结束日期 */
			Date endDate = getYMD(endTime);

			/** 修改后的订单结束时间 */
			Date newEndTime = stringToTime(order.getEndTime(), order.getPloyEndTime());
			if ("00:00:00".equals(order.getPloyStartTime())
					&& ("23:59:59".equals(order.getPloyEndTime()) || "24:00:00".equals(order.getPloyEndTime()) || "00:00:00"
							.equals(order.getPloyEndTime()))) {
				playListGisDao.updateEndTime(order.getId(), newEndTime);
			} else {
				if (endDate.getTime() < order.getEndTime().getTime()) {// 订单延长结束时间
//					List<PlayListGis> ps = orderToPlayListByDate(order.getId(),
//							endDate, order.getEndTime(), order.getPloyStartTime(), order.getPloyEndTime());
					List<PlayListGis> ps = orderToPlayList(order.getId(),endTime);
					playListGisDao.insertPlayList(ps);
				} else if (endDate.getTime() > order.getEndTime().getTime()) {// 订单缩短结束时间
					String newEndTimeStr = Transform
							.CalendartoString(newEndTime);
					playListGisDao.deletePlayListByDate(order.getId(), newEndTimeStr);
				}
			}
		} catch (Exception e) {
			logger.error("更新投放式播出单出现异常", e);
			return 1;
		}
		return 0;

	}

//	/**
//	 * 将策略订单信息填充到播出单对象中
//	 * @param orderId
//	 * @return
//	 */
//	private List<PlayListGis> ployOrderToPlayList(OrderBean order,
//			List<PlayListGis> ps,Map<String,String> serviceIdMap,Date maxEndTime) {
//		
//		List<MaterialBean> ms = playListGisDao.getPloyMaterialByOrder(order.getId());
//		String contentType = "";
//		StringBuffer contentId = new StringBuffer();
//		String contentPath = null;
//		Integer bootPositionId = Integer.valueOf(baseConfigService.getBaseConfigByCode("bootPosition"));
//		if (bootPositionId.intValue() == order.getPositionPackageId().intValue()) {// 开机广告位
//			contentType = Constant.INIT_MATERIAL_TYPE;
//			
//			BootResourceInfo info = new BootResourceInfo();
//			List<BootImageInfo> list = new ArrayList<BootImageInfo>();
//			for (MaterialBean m : ms) {
//				if (StringUtils.isEmpty(m.getPlayLocation()) && m.getType() == Constant.IMAGE) {
//					//开机默认图片
//					info.setPic(m.getPath());
//				}else if (m.getType() == Constant.VIDEO) {
//					//开机视频
//					info.setVideo(m.getPath());
//				}else{
//					//开机时段图片
//					BootImageInfo bootImage = new BootImageInfo();
//					bootImage.setTimeInterval(m.getPlayLocation());
//					bootImage.setImage(m.getPath());
//					list.add(bootImage);
//				}
//			}
//			info.setPics(list);
//			Gson gson = new Gson();
//			contentPath = gson.toJson(info);
//		} else if (order.getAdvertPosition().getIsLoop().intValue() == Constant.ADVERT_POSITION_IS_POLLING) {// 多图
//			contentType = Constant.MULTI_IMAGE_MATERIAL_TYPE;
//			contentPath = "[";
//			for (int i = 0; i < ms.size(); i++) {
//				if (i > 0) {
//					contentPath += ",";
//					contentId.append(",");
//				}
//				if (ms.get(i).getType() == Constant.IMAGE) {
//					contentPath += "{\"pic\":\""+ms.get(i).getPath()+"\"}";
//					String cId = getContentId(ms.get(i).getId(), Constant.IMAGE)+"_"+ms.get(i).getLoopNo();
//					contentId.append(cId);
//				}
//			}
//			contentPath += "]";
//		} else {
//			if (ms.size() > 0) {
//				MaterialBean m = ms.get(0);
//				if (order.getAdvertPosition().getTextRuleId() != null
//						&& m.getType() == Constant.WRITING) {// 字幕
//					contentType = Constant.WRITING_MATERIAL_TYPE;
//					StringBuffer jsonContent = new StringBuffer();
//					String cId = getContentId(m.getId(), Constant.WRITING);
//					contentId.append(cId);
//					TextMate text = m.getText();
//					jsonContent.append("{\"id\":\"").append(text.getId()).append("\",")
//					.append("\"name\":\"").append(text.getName()).append("\",")
//					.append("\"URL\":\"").append(text.getURL()).append("\",")
//					.append("\"action\":\"").append(text.getAction()).append("\",")
//					.append("\"bkgColor\":\"").append(text.getBkgColor()).append("\",")
//					.append("\"content\":\"").append(text.getContent()).append("\",")
//					.append("\"durationTime\":\"").append(text.getDurationTime()).append("\",")
//					.append("\"fontColor\":\"").append(text.getFontColor()).append("\",")
//					.append("\"fontSize\":\"").append(text.getFontSize()).append("\",")
//					.append("\"positionVertexCoordinates\":\"").append(text.getPositionVertexCoordinates()).append("\",")
//					.append("\"positionWidthHeight\":\"").append(text.getPositionWidthHeight()).append("\",")
//					.append("\"rollSpeed\":\"").append(text.getRollSpeed()).append("\"}");
//					
//					contentPath = jsonContent.toString();
//
//				} else {
//					String cId = getContentId(m.getId(), m.getType());
//					contentId.append(cId);
//					contentPath = ms.get(0).getPath();
//				}
//			}
//		}
//		if (contentPath == null) {
//			logger.error("获取资源路径失败！");
//			return null;
//		}
//		
//		/************************/
//		//设置播出单的区域
//		String ployAreaIds = "";
//		//播出单的频道ID
//		String ployServiceIds = "[";
//		//所有频道的serviceIds
//		String allServiceIds = null;
//		//设置播出单的区域信息
//		PloyBean ploy = order.getPloy();
//		List<PloyBean> ployList = new ArrayList<PloyBean>();;
//		if(order.getAdvertPosition().getIsChannel().intValue() == 1 
//				|| order.getAdvertPosition().getIsFreq().intValue() == 1){
//			//广告位支持频道
//			ployList = this.getPloyChannelList(ploy.getPloyId());
//		}else if(order.getAdvertPosition().getIsPlayback().intValue() == 1){
//			ployList = this.getPloyNpvrList(ploy.getPloyId());
//		}
//		
//		int preAreaId = -1;
//		int index = 0;
//		String pServiceIds = "";//每个区域对应的频道serviceIds
//		if(ployList.size()>0){
//		for(PloyBean p : ployList){
//			int areaId = p.getAreaId();
//			if(p.getAreaId() != preAreaId){
//				if(pServiceIds.endsWith(",")){
//					pServiceIds = pServiceIds.substring(0,pServiceIds.length()-1);
//					ployServiceIds += pServiceIds+"\",\"";
//				}
//
//				pServiceIds = "\"";
//				if(index > 0){
//					ployAreaIds += ",";
//				}
//				ployAreaIds += areaId;
//				index++;
//			}
//			if(p.getChannelId() == 0){
//				//单向的频道播出单，serviceId不能写0
//				if((order.getAdvertPosition().getIsChannel().intValue() == 1 || order.getAdvertPosition().getIsFreq().intValue() == 1)
//					&& (Constant.POSITION_TYPE_ONE_REAL_TIME==order.getPositionPackageType().intValue() 
//							|| Constant.POSITION_TYPE_ONE_NOT_REAL_TIME == order.getPositionPackageType().intValue())){
//					if(allServiceIds == null){
//						if("02061".equals(order.getAdvertPosition().getPositionCode()) || "02062".equals(order.getAdvertPosition().getPositionCode())){
//							//广播收听背景广告，只查询音频类频道
//							ChannelInfo channel = new ChannelInfo();
//							channel.setChannelType("音频直播类业务");
//							allServiceIds = this.getDtvServiceById("0",channel);
//						}else{
//							allServiceIds = this.getDtvServiceById("0",null);
//						}
//						
//					}
//					String[] serviceIds = allServiceIds.split(",");
//					for(String id : serviceIds){
//						if(!serviceIdMap.containsKey(id)){//过来精准中存在的频道serviceId
//							pServiceIds += id+",";
//						}
//					}
//				}else{
//					pServiceIds += "0,";
//				}
//			}else{
//				String[] serviceIds = p.getServiceId().split(",");
//				for(String id : serviceIds){
//					if(!serviceIdMap.containsKey(id)){//过来精准中存在的频道serviceId
//						pServiceIds += id+",";
//					}
//				}
//			}
//			
//		}
//		}else{
//			ployAreaIds = order.getPloy().getAreaId().toString();
//		}
//		
//		if(pServiceIds.endsWith(",")){
//			ployServiceIds = ployServiceIds+pServiceIds.substring(0,pServiceIds.length()-1);
//		}
//		
//		if(ployServiceIds.length()<2){
//			ployServiceIds += "\"";
//		}
//		
//		ployServiceIds += "\"]";
//		
//		order.setAreas(ployAreaIds);
//		order.setServiceIds(ployServiceIds);
//		
//		/*************************/
//		String sTime = order.getPloy().getStartTime();
//		String eTime = order.getPloy().getEndTime();
//		if ("00:00:00".equals(sTime)
//				&& ("23:59:59".equals(eTime) || "24:00:00".equals(eTime) || "00:00:00"
//						.equals(eTime))) {
//			PlayListGis playList = new PlayListGis();
//			Date endTime = this.stringToTime(order.getEndDate(), eTime);
//			playList.setStartTime(order.getStartDate());
//			playList.setEndTime(endTime);
//			playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
//			playList.setAreas(order.getAreas());
//			playList.setServiceId(order.getServiceIds());
//			playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
//			playList.setContentId(contentId.toString());
//			playList.setContentPath(contentPath);
//			playList.setContentType(contentType);
//			playList.setContractId(order.getContractId());
//			playList.setOrderId(order.getId());
//			playList.setPloyId(order.getPloyId());
//			playList.setState(Constant.VALID);
//			playList.setTvn(order.getPloy().getTvnNumber());
//			playList.setUserIndustrys(order.getPloy().getUserIndustrys());
//			playList.setUserLevels(order.getPloy().getUserLevels());
//			playList.setAssetId("[\"0\"]");
//			playList.setCategoryId("[\"0\"]");
//			ps.add(playList);
//		} else {
//			Date sDate = order.getStartDate();
//			Date eDate = order.getEndDate();
//			Calendar ca = Calendar.getInstance();
//			while (sDate.compareTo(eDate) <= 0) {
//				Date startTime = new Date(stringToTime(sDate, sTime).getTime());
//				Date endTime = new Date(stringToTime(sDate, eTime).getTime());
//				/**
//				 * 审核新订单时，maxEndTime为null，创建所有时间的播出单
//				 * 审核已播出的订单时，只创建未生产日期的播出单
//				 */
//				if(maxEndTime == null || maxEndTime.getTime()<startTime.getTime()){
//					PlayListGis playList = new PlayListGis();
//					playList.setStartTime(startTime);
//					playList.setEndTime(endTime);
//					playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
//					playList.setAreas(order.getAreas());
//					playList.setServiceId(order.getServiceIds());
//					playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
//					playList.setContentId(contentId.toString());
//					playList.setContentPath(contentPath);
//					playList.setContentType(contentType);
//					playList.setContractId(order.getContractId());
//					playList.setOrderId(order.getId());
//					playList.setPloyId(order.getPloyId());
//					playList.setState(Constant.VALID);
//					playList.setTvn(order.getPloy().getTvnNumber());
//					playList.setUserIndustrys(order.getPloy().getUserIndustrys());
//					playList.setUserLevels(order.getPloy().getUserLevels());
//					playList.setAssetId("[\"0\"]");
//					playList.setCategoryId("[\"0\"]");
//					ps.add(playList);
//				}
//
//				ca.setTime(sDate);
//				ca.add(Calendar.DATE, 1);// 把日期加上1天然后重新赋值给开始时间
//				sDate = ca.getTime();
//			}
//		}
//		return ps;
//	}
	
	/**
	 * 将策略订单信息填充到播出单对象中
	 * @param orderId
	 * @return
	 */
	private List<PlayListGis> ployOrderToPlayList(OrderBean order,
			List<PlayListGis> ps,Map<String,String> serviceIdMap,Date maxEndTime) {
		
		List<PloyPlayListGisRel> pList = playListGisDao.getPloyPlayListGisByOrder(order.getId(),order.getAdvertPosition().getPositionCode());
		Integer prePloyId = 0;//前一个策略主键ID
		String preStartTimeStr = "";//前一个分策略的开始时间
		Map<String,String> ployServiceIdMap = null;
		for(PloyPlayListGisRel pp : pList){
			Integer ployId = pp.getPreciseId();//策略主键ID
			if(prePloyId.intValue()==ployId.intValue()){
				prePloyId = ployId;
				continue;
			}
			
			prePloyId = ployId;
			String contentType = "";//播出单的内容类型
			String contentId = "";//播出单的内容ID
			String contentPath = null;
			if ("01001".equals(order.getAdvertPosition().getPositionCode()) 
					|| "01002".equals(order.getAdvertPosition().getPositionCode())) {
				// 开机广告位
				contentType = Constant.INIT_MATERIAL_TYPE;
				
				BootResourceInfo info = new BootResourceInfo();
				List<BootImageInfo> list = new ArrayList<BootImageInfo>();
				for (PloyPlayListGisRel p : pList) {
					if(p.getPreciseId().intValue() == ployId.intValue()){//只获取某一策略的开机素材
						if (StringUtils.isEmpty(p.getPlayLocation()) && p.getResourceType() == Constant.IMAGE) {
							//开机默认图片
							info.setPic(p.getPath());
						}else if (p.getResourceType() == Constant.VIDEO) {
							//开机视频
							info.setVideo(p.getPath());
						}else{
							//开机时段图片
							BootImageInfo bootImage = new BootImageInfo();
							bootImage.setTimeInterval(p.getPlayLocation());
							bootImage.setImage(p.getPath());
							list.add(bootImage);
						}
						contentId += p.getMateId()+",";
					}
				}
				info.setPics(list);
				Gson gson = new Gson();
				contentPath = gson.toJson(info);
			} else if (order.getAdvertPosition().getIsLoop().intValue() == Constant.ADVERT_POSITION_IS_POLLING) {// 多图
				contentType = Constant.MULTI_IMAGE_MATERIAL_TYPE;
				contentPath = "[";
				for (PloyPlayListGisRel p : pList) {
					if(p.getPreciseId().intValue() == ployId.intValue()){//只获取某一策略的素材
						if (p.getResourceType().intValue() == Constant.IMAGE) {
							contentPath += "{\"pic\":\""+p.getPath()+"\",\"index\":\""+p.getPollIndex()+"\"},";
//							contentId += getContentId(p.getMateId(), Constant.IMAGE)+"_"+p.getPollIndex()+",";
							contentId += p.getMateId()+",";
						}
					}
				}
				if(contentPath.endsWith(",")){
					contentPath = contentPath.substring(0,contentPath.length()-1);
				}
				if(contentId.endsWith(",")){
					contentId = contentId.substring(0,contentId.length()-1);
				}
				contentPath += "]";
			} else if(order.getAdvertPosition().getTextRuleId() != null
					&& pp.getResourceType() == Constant.WRITING){// 字幕
				contentType = Constant.WRITING_MATERIAL_TYPE;
				contentId = getContentId(pp.getMateId(), Constant.WRITING);
				Gson gson = new Gson();
				contentPath = gson.toJson(pp.getText());
			} else {
				contentId = getContentId(pp.getMateId(), pp.getResourceType());
				contentPath = pp.getPath();
			}
			if (contentPath == null) {
				logger.error("获取资源路径失败！");
				return null;
			}
			
			String startTimeStr = pp.getStartTime();//分策略的开始时间
			if(!preStartTimeStr.equals(startTimeStr)){
				//在同一个时间，根据不同频道组可能存在多个分策略，根据优先级过滤频道serviceId
				ployServiceIdMap = new HashMap<String,String>();
			}
			//播出单的频道ID
			String ployServiceIds = "[\"";
			for(String serviceId : pp.getServiceIdList()){
				if(!serviceIdMap.containsKey(serviceId) && !ployServiceIdMap.containsKey(serviceId)){
					//精准中不存在的频道serviceId，并且同时间段内的分策略不存在的serviceId
					ployServiceIds += serviceId+",";
					ployServiceIdMap.put(serviceId, "");
				}
			}
			if(ployServiceIds.endsWith(",")){
				ployServiceIds = ployServiceIds.substring(0,ployServiceIds.length()-1);
			}
			ployServiceIds += "\"]";
			
			String sTime = pp.getStartTime();
			String eTime = pp.getEndTime();
			if ("00:00:00".equals(sTime)
					&& ("23:59:59".equals(eTime) || "24:00:00".equals(eTime) || "00:00:00"
							.equals(eTime))) {
				PlayListGis playList = new PlayListGis();
				Date endTime = this.stringToTime(order.getEndDate(), eTime);
				playList.setStartTime(order.getStartDate());
				if (playList.getStartTime().before(new Date()))
				{
					playList.setStartTime(new Date());
				}
				playList.setEndTime(endTime);
				playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
				playList.setAreas(pp.getAreaCode());
				playList.setServiceId(ployServiceIds);
				playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
				playList.setContentId(contentId);
				playList.setContentPath(contentPath);
				playList.setContentType(contentType);
				playList.setContractId(order.getContractId());
				playList.setOrderId(order.getId());
				playList.setPloyId(order.getPloyId());
				playList.setState(Constant.VALID);
				playList.setTvn(pp.getTvnNumber());
				playList.setUserIndustrys(pp.getUserIndustrys());
				playList.setUserLevels(pp.getUserLevels());
				playList.setAssetId("[\"0\"]");
				playList.setCategoryId("[\"0\"]");
				ps.add(playList);
			} else {
				Date sDate = order.getStartDate();
				Date eDate = order.getEndDate();
				Calendar ca = Calendar.getInstance();
				while (sDate.compareTo(eDate) <= 0) {
					Date startTime = new Date(stringToTime(sDate, sTime).getTime());
					Date endTime = new Date(stringToTime(sDate, eTime).getTime());
					/**
					 * 审核新订单时，maxEndTime为null，创建所有时间的播出单
					 * 审核已播出的订单时，只创建未生产日期的播出单
					 */
					if(maxEndTime == null || maxEndTime.getTime()<startTime.getTime()){
						PlayListGis playList = new PlayListGis();
						playList.setStartTime(startTime);
						if (playList.getStartTime().before(new Date()))
						{
							playList.setStartTime(new Date());
						}
						playList.setEndTime(endTime);
						playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
						playList.setAreas(pp.getAreaCode());
						playList.setServiceId(ployServiceIds);
						playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
						playList.setContentId(contentId);
						playList.setContentPath(contentPath);
						playList.setContentType(contentType);
						playList.setContractId(order.getContractId());
						playList.setOrderId(order.getId());
						playList.setPloyId(order.getPloyId());
						playList.setState(Constant.VALID);
						playList.setTvn(pp.getTvnNumber());
						playList.setUserIndustrys(pp.getUserIndustrys());
						playList.setUserLevels(pp.getUserLevels());
						playList.setAssetId("[\"0\"]");
						playList.setCategoryId("[\"0\"]");
						ps.add(playList);
					}
	
					ca.setTime(sDate);
					ca.add(Calendar.DATE, 1);// 把日期加上1天然后重新赋值给开始时间
					sDate = ca.getTime();
				}
			}
		}
		return ps;
	}
	/**
	 * 将策略订单信息填充到播出单对象中
	 * @param orderId
	 * @return
	 */
	private List<PlayListGis> ployOrderToPlayList(OrderBean order,
			List<PlayListGis> ps,Map<String,String> serviceIdMap,Map<String,String> categoryIdMap,Date maxEndTime) {
		
		List<PloyPlayListGisRel> pList = playListGisDao.getPloyPlayListGisByOrder(order.getId(),order.getAdvertPosition().getPositionCode());
		Integer prePloyId = 0;//前一个策略主键ID
		String preStartTimeStr = "";//前一个分策略的开始时间
		Map<String,String> ployServiceIdMap = null;
		Map<String,String> ployCategoryIdMap = null;
		
		String serviceIds="";
	     String categoryIds="";
		for(PloyPlayListGisRel pp : pList){
			Integer ployId = pp.getPreciseId();//策略主键ID
			if(prePloyId.intValue()==ployId.intValue()){
				prePloyId = ployId;
				continue;
			}
			
			prePloyId = ployId;
			String contentType = "";//播出单的内容类型
			String contentId = "";//播出单的内容ID
			String contentPath = null;
			if ("01001".equals(order.getAdvertPosition().getPositionCode()) 
					|| "01002".equals(order.getAdvertPosition().getPositionCode())) {
				// 开机广告位
				contentType = Constant.INIT_MATERIAL_TYPE;
				
				BootResourceInfo info = new BootResourceInfo();
				List<BootImageInfo> list = new ArrayList<BootImageInfo>();
				for (PloyPlayListGisRel p : pList) {
					if(p.getPreciseId().intValue() == ployId.intValue()){//只获取某一策略的开机素材
						if (StringUtils.isEmpty(p.getPlayLocation()) && p.getResourceType() == Constant.IMAGE) {
							//开机默认图片
							info.setPic(p.getPath());
						}else if (p.getResourceType() == Constant.VIDEO) {
							//开机视频
							info.setVideo(p.getPath());
						}else{
							//开机时段图片
							BootImageInfo bootImage = new BootImageInfo();
							bootImage.setTimeInterval(p.getPlayLocation());
							bootImage.setImage(p.getPath());
							list.add(bootImage);
						}
						contentId += p.getMateId()+",";
					}
				}
				info.setPics(list);
				Gson gson = new Gson();
				contentPath = gson.toJson(info);
			} else if (order.getAdvertPosition().getIsLoop().intValue() == Constant.ADVERT_POSITION_IS_POLLING) {// 多图
				contentType = Constant.MULTI_IMAGE_MATERIAL_TYPE;
				contentPath = "[";
				for (PloyPlayListGisRel p : pList) {
					if(p.getPreciseId().intValue() == ployId.intValue()){//只获取某一策略的素材
						if (p.getResourceType().intValue() == Constant.IMAGE) {
							contentPath += "{\"pic\":\""+p.getPath()+"\",\"index\":\""+p.getPollIndex()+"\"},";
//							contentId += getContentId(p.getMateId(), Constant.IMAGE)+"_"+p.getPollIndex()+",";
							contentId += p.getMateId()+",";
						}
					}
				}
				if(contentPath.endsWith(",")){
					contentPath = contentPath.substring(0,contentPath.length()-1);
				}
				if(contentId.endsWith(",")){
					contentId = contentId.substring(0,contentId.length()-1);
				}
				contentPath += "]";
			} else if(order.getAdvertPosition().getTextRuleId() != null
					&& pp.getResourceType() == Constant.WRITING){// 字幕
				contentType = Constant.WRITING_MATERIAL_TYPE;
				contentId = getContentId(pp.getMateId(), Constant.WRITING);
				Gson gson = new Gson();
				contentPath = gson.toJson(pp.getText());
			} else {
				contentId = getContentId(pp.getMateId(), pp.getResourceType());
				contentPath = pp.getPath();
			}
			if (contentPath == null) {
				logger.error("获取资源路径失败！");
				return null;
			}
			
			String startTimeStr = pp.getStartTime();//分策略的开始时间
			if(!preStartTimeStr.equals(startTimeStr)){
				//在同一个时间，根据不同频道组可能存在多个分策略，根据优先级过滤频道serviceId
				ployServiceIdMap = new HashMap<String,String>();
				ployCategoryIdMap  = new HashMap<String,String>();
			}
			//播出单的频道ID
			String ployServiceIds = "[\"";
			
			
			
			
			if(order.getAdvertPosition().getIsChannel().intValue() == 1
					|| order.getAdvertPosition().getIsFreq().intValue() == 1 || order.getAdvertPosition().getIsPlayback().intValue() == 1){
				ployServiceIds = "[\"";
				for(String serviceId : pp.getServiceIdList()){
					if(!serviceIdMap.containsKey(serviceId) && !ployServiceIdMap.containsKey(serviceId)){
						//精准中不存在的频道serviceId，并且同时间段内的分策略不存在的serviceId
						ployServiceIds += serviceId+",";
						ployServiceIdMap.put(serviceId, "");
					}
				}
				if(ployServiceIds.endsWith(",")){
					ployServiceIds = ployServiceIds.substring(0,ployServiceIds.length()-1);
				}
				ployServiceIds += "\"]";
				categoryIds="[\""+"0\"]";
				
			}else if(order.getAdvertPosition().getIsColumn() == 1){
				//支持回看栏目广告位
				ployServiceIds="[\""+"0\"]";
				categoryIds="[\"";
					
				for(String categoryId : pp.getLookbackCategoryIdList()){
					if(!categoryIdMap.containsKey(categoryId) && !ployCategoryIdMap.containsKey(categoryId)){
						//精准中不存在的频道serviceId，并且同时间段内的分策略不存在的serviceId
						categoryIds += categoryId+",";
						ployCategoryIdMap.put(categoryId, "");
					}
				}
				if(categoryIds.endsWith(",")){
					categoryIds = categoryIds.substring(0,categoryIds.length()-1);
				}
				categoryIds+="\"]";
//				if(pp.getLookbackCategoryIdList() != null){
//					for(String categoryId : pp.getLookbackCategoryIdList()){
//						if(!categoryIdMap.containsKey(categoryId)){
//							categoryIdMap.put(categoryId, "");
//							categoryIds += categoryId+",";
//						}
//					}
//				}
			}else
			{
				ployServiceIds="[\""+"0\"]";
				categoryIds="[\""+"0\"]";
			}
			
			
			
			String sTime = pp.getStartTime();
			String eTime = pp.getEndTime();
			if ("00:00:00".equals(sTime)
					&& ("23:59:59".equals(eTime) || "24:00:00".equals(eTime) || "00:00:00"
							.equals(eTime))) {
				PlayListGis playList = new PlayListGis();
				Date endTime = this.stringToTime(order.getEndDate(), eTime);
				playList.setStartTime(order.getStartDate());
				if (playList.getStartTime().before(new Date()))
				{
					playList.setStartTime(new Date());
				}
				playList.setEndTime(endTime);
				playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
				playList.setAreas(pp.getAreaCode());
				playList.setServiceId(ployServiceIds);
				playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
				playList.setContentId(contentId);
				playList.setContentPath(contentPath);
				playList.setContentType(contentType);
				playList.setContractId(order.getContractId());
				playList.setOrderId(order.getId());
				playList.setPloyId(order.getPloyId());
				playList.setState(Constant.VALID);
				playList.setTvn(pp.getTvnNumber());
				playList.setUserIndustrys(pp.getUserIndustrys());
				playList.setUserLevels(pp.getUserLevels());
				playList.setAssetId("[\"0\"]");
				playList.setCategoryId(categoryIds);
				ps.add(playList);
			} else {
				Date sDate = order.getStartDate();
				Date eDate = order.getEndDate();
				Calendar ca = Calendar.getInstance();
				while (sDate.compareTo(eDate) <= 0) {
					Date startTime = new Date(stringToTime(sDate, sTime).getTime());
					Date endTime = new Date(stringToTime(sDate, eTime).getTime());
					/**
					 * 审核新订单时，maxEndTime为null，创建所有时间的播出单
					 * 审核已播出的订单时，只创建未生产日期的播出单
					 */
					if(maxEndTime == null || maxEndTime.getTime()<startTime.getTime()){
						PlayListGis playList = new PlayListGis();
						playList.setStartTime(startTime);
						if (playList.getStartTime().before(new Date()))
						{
							playList.setStartTime(new Date());
						}
						playList.setEndTime(endTime);
						playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
						playList.setAreas(pp.getAreaCode());
						playList.setServiceId(ployServiceIds);
						playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
						playList.setContentId(contentId);
						playList.setContentPath(contentPath);
						playList.setContentType(contentType);
						playList.setContractId(order.getContractId());
						playList.setOrderId(order.getId());
						playList.setPloyId(order.getPloyId());
						playList.setState(Constant.VALID);
						playList.setTvn(pp.getTvnNumber());
						playList.setUserIndustrys(pp.getUserIndustrys());
						playList.setUserLevels(pp.getUserLevels());
						playList.setAssetId("[\"0\"]");
						playList.setCategoryId(categoryIds);
						ps.add(playList);
					}
	
					ca.setTime(sDate);
					ca.add(Calendar.DATE, 1);// 把日期加上1天然后重新赋值给开始时间
					sDate = ca.getTime();
				}
			}
		}
		return ps;
	}
	
	/**
	 * 将策略订单信息填充到播出单对象中
	 * @param orderId
	 * @return
	 */
	private List<PlayListGis> ployOrderToPlayList2(OrderBean order,
			List<PlayListGis> ps,Map<String,String> serviceIdMap,Map<String,String> categoryIdMap,Date maxEndTime) {
		
		List<PloyPlayListGisRel> pList = playListGisDao.getPloyPlayListGisByOrder(order.getId(),order.getAdvertPosition().getPositionCode());
		Integer prePloyId = 0;//前一个策略主键ID
		String preStartTimeStr = "";//前一个分策略的开始时间
		Map<String,String> ployServiceIdMap = null;
		Map<String,String> ployCategoryIdMap = null;
		//针对轮询菜单图片，同一个地市，只需要生产一个播出单
		String preAreaCode = "";
	    String categoryIds="";
		for(PloyPlayListGisRel pp : pList){
			Integer ployId = pp.getPreciseId();//策略主键ID
			String areaCode = pp.getAreaCode();
			if( prePloyId.intValue()==ployId.intValue() && preAreaCode.equals(areaCode) ){
				continue;
			}
			String startTimeStr = pp.getStartTime();
			//开始时间不一样或者区域不一样，绑定素材的几个频道组可能有重复频道，需去重
			if(!preStartTimeStr.equals(startTimeStr) || !preAreaCode.equals(areaCode)){
				ployServiceIdMap = new HashMap<String,String>();
				ployCategoryIdMap  = new HashMap<String,String>();
			}
			preStartTimeStr = startTimeStr;
			prePloyId = ployId;
			preAreaCode = areaCode;

			String contentType = "";//播出单的内容类型
			String contentId = "";//播出单的内容ID
			String contentPath = null;
			if ("01001".equals(order.getAdvertPosition().getPositionCode()) || "01002".equals(order.getAdvertPosition().getPositionCode())
				|| "01003".equals(order.getAdvertPosition().getPositionCode()) || "01004".equals(order.getAdvertPosition().getPositionCode())	) {
				// 开机广告位
				contentType = Constant.INIT_MATERIAL_TYPE;
				
				BootResourceInfo info = new BootResourceInfo();
				List<BootImageInfo> list = new ArrayList<BootImageInfo>();
				for (PloyPlayListGisRel p : pList) {
					if( p.getPreciseId().intValue() == ployId.intValue() && areaCode.equals(p.getAreaCode()) ){//只获取某一策略的开机素材
						if (StringUtils.isEmpty(p.getPlayLocation()) && p.getResourceType() == Constant.IMAGE) {
							//开机默认图片
							info.setPic(p.getPath());
						}else if (p.getResourceType() == Constant.VIDEO) {
							//开机视频
							info.setVideo(p.getPath());
						}else{
							//开机时段图片
							BootImageInfo bootImage = new BootImageInfo();
							bootImage.setTimeInterval(p.getPlayLocation());
							bootImage.setImage(p.getPath());
							list.add(bootImage);
						}
						contentId += p.getMateId()+",";
					}
				}
				info.setPics(list);
				Gson gson = new Gson();
				contentPath = gson.toJson(info);
			} else if (order.getAdvertPosition().getIsLoop().intValue() == Constant.ADVERT_POSITION_IS_POLLING) {// 多图
				contentType = Constant.MULTI_IMAGE_MATERIAL_TYPE;
				contentPath = "[";
				for (PloyPlayListGisRel p : pList) {
					if(p.getPreciseId().intValue() == ployId.intValue() && areaCode.equals(p.getAreaCode())){//只获取某一策略的素材
						//同一个地市只生产一个播出单
						if (p.getResourceType().intValue() == Constant.IMAGE) {
							contentPath += "{\"pic\":\""+p.getPath()+"\",\"index\":\""+p.getPollIndex()+"\"},";
//							contentId += getContentId(p.getMateId(), Constant.IMAGE)+"_"+p.getPollIndex()+",";
							contentId += p.getMateId()+",";
						}
					}
				}
				if(contentPath.endsWith(",")){
					contentPath = contentPath.substring(0,contentPath.length()-1);
				}
				if(contentId.endsWith(",")){
					contentId = contentId.substring(0,contentId.length()-1);
				}
				contentPath += "]";
			} else if(order.getAdvertPosition().getTextRuleId() != null
					&& pp.getResourceType() == Constant.WRITING){// 字幕
				contentType = Constant.WRITING_MATERIAL_TYPE;
				contentId = getContentId(pp.getMateId(), Constant.WRITING);
				Gson gson = new Gson();
				contentPath = gson.toJson(pp.getText());
			} else {
				contentId = getContentId(pp.getMateId(), pp.getResourceType());
				contentPath = pp.getPath();
			}
			if (contentPath == null) {
				logger.error("获取资源路径失败！");
				return null;
			}

			//播出单的频道ID
			String ployServiceIds = "[\"";
			
			if(order.getAdvertPosition().getIsChannel().intValue() == 1
					|| order.getAdvertPosition().getIsFreq().intValue() == 1 || order.getAdvertPosition().getIsPlayback().intValue() == 1){
				ployServiceIds = "[\"";
				for(String serviceId : pp.getServiceIdList()){
					if(!ployServiceIdMap.containsKey(serviceId)){
						ployServiceIds += serviceId+",";
						ployServiceIdMap.put(serviceId, "");
					}
				}
				if(ployServiceIds.endsWith(",")){
					ployServiceIds = ployServiceIds.substring(0,ployServiceIds.length()-1);
				}
				//有两个频道组包含一样的频道，全部被过滤掉了，不写播出单
				else{
					continue;
				}
				ployServiceIds += "\"]";
				categoryIds="[\""+"0\"]";
				
			}else if(order.getAdvertPosition().getIsColumn() == 1){
				//支持回看栏目广告位
				ployServiceIds="[\""+"0\"]";
				categoryIds="[\"";
					
				for(String categoryId : pp.getLookbackCategoryIdList()){
					if(!categoryIdMap.containsKey(categoryId) && !ployCategoryIdMap.containsKey(categoryId)){
						//精准中不存在的频道serviceId，并且同时间段内的分策略不存在的serviceId
						categoryIds += categoryId+",";
						ployCategoryIdMap.put(categoryId, "");
					}
				}
				if(categoryIds.endsWith(",")){
					categoryIds = categoryIds.substring(0,categoryIds.length()-1);
				}
				categoryIds+="\"]";
			}else
			{
				ployServiceIds="[\""+"0\"]";
				categoryIds="[\""+"0\"]";
			}
			
			
			
			String sTime = pp.getStartTime();
			String eTime = pp.getEndTime();
			if ("00:00:00".equals(sTime)
					&& ("23:59:59".equals(eTime) || "24:00:00".equals(eTime) || "00:00:00"
							.equals(eTime))) {
				PlayListGis playList = new PlayListGis();
				Date endTime = this.stringToTime(order.getEndDate(), eTime);
				playList.setStartTime(order.getStartDate());
				if (playList.getStartTime().before(new Date()))
				{
					playList.setStartTime(new Date());
				}
				playList.setEndTime(endTime);
				playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
				playList.setAreas(pp.getAreaCode());
				playList.setServiceId(ployServiceIds);
				playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
				playList.setContentId(contentId);
				playList.setContentPath(contentPath);
				playList.setContentType(contentType);
				playList.setContractId(order.getContractId());
				playList.setOrderId(order.getId());
				playList.setPloyId(order.getPloyId());
				playList.setState(Constant.VALID);
				playList.setTvn(pp.getTvnNumber());
				playList.setUserIndustrys(pp.getUserIndustrys());
				playList.setUserLevels(pp.getUserLevels());
				playList.setAssetId("[\"0\"]");
				playList.setCategoryId(categoryIds);
				ps.add(playList);
			} else {
				Date sDate = order.getStartDate();
				Date eDate = order.getEndDate();
				Calendar ca = Calendar.getInstance();
				while (sDate.compareTo(eDate) <= 0) {
					Date startTime = new Date(stringToTime(sDate, sTime).getTime());
					Date endTime = new Date(stringToTime(sDate, eTime).getTime());
					/**
					 * 审核新订单时，maxEndTime为null，创建所有时间的播出单
					 * 审核已播出的订单时，只创建未生产日期的播出单
					 */
					if(maxEndTime == null || maxEndTime.getTime()<startTime.getTime()){
						PlayListGis playList = new PlayListGis();
						playList.setStartTime(startTime);
						if (playList.getStartTime().before(new Date()))
						{
							playList.setStartTime(new Date());
						}
						playList.setEndTime(endTime);
						playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
						playList.setAreas(pp.getAreaCode());
						playList.setServiceId(ployServiceIds);
						playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
						playList.setContentId(contentId);
						playList.setContentPath(contentPath);
						playList.setContentType(contentType);
						playList.setContractId(order.getContractId());
						playList.setOrderId(order.getId());
						playList.setPloyId(order.getPloyId());
						playList.setState(Constant.VALID);
						playList.setTvn(pp.getTvnNumber());
						playList.setUserIndustrys(pp.getUserIndustrys());
						playList.setUserLevels(pp.getUserLevels());
						playList.setAssetId("[\"0\"]");
						playList.setCategoryId(categoryIds);
						ps.add(playList);
					}
	
					ca.setTime(sDate);
					ca.add(Calendar.DATE, 1);// 把日期加上1天然后重新赋值给开始时间
					sDate = ca.getTime();
				}
			}
		}
		return ps;
	}
	
	/**
	 * 将策略订单信息填充到播出单对象中
	 * @param orderId
	 * @return
	 */
	private List<PlayListGis> lookPloyOrderToPlayList(OrderBean order,
			List<PlayListGis> ps,Map<String,String> serviceIdMap,Map<String,List<String>> categoryIdMap,Date maxEndTime) {
		
		List<PloyPlayListGisRel> pList = playListGisDao.getPloyPlayListGisByOrder(order.getId(),order.getAdvertPosition().getPositionCode());
//		String preStartTimeStr = "";//前一个分策略的开始时间
		Map<String,String> ployServiceIdMap = null;
		Map<String,String> ployCategoryIdMap = null;
		String categoryIds="";
		for(PloyPlayListGisRel pp : pList){
			String contentType = "";//播出单的内容类型
			String contentId = "";//播出单的内容ID
			String contentPath = null;
			
			contentId = getContentId(pp.getMateId(), pp.getResourceType());
			contentPath = pp.getPath();
			if (contentPath == null) {
				logger.error("获取资源路径失败！");
				return null;
			}
			
//			String startTimeStr = pp.getStartTime();//分策略的开始时间
//			if(!preStartTimeStr.equals(startTimeStr)){
			//在同一个时间，根据不同频道组可能存在多个分策略，根据优先级过滤频道serviceId
			ployServiceIdMap = new HashMap<String,String>();
			ployCategoryIdMap  = new HashMap<String,String>();
			List<String> categoryIdList = categoryIdMap.get(pp.getStartTime());
			if(categoryIdList != null && categoryIdList.size() > 0){
				for(String categoryId : categoryIdList){
					ployCategoryIdMap.put(categoryId, "");
				}
			}  //例：ployCategoryIdMap = {c5657350=, c5661500=}
//			}
			//播出单的频道ID
			String ployServiceIds = "[\"";

			if(order.getAdvertPosition().getIsChannel().intValue() == 1
					|| order.getAdvertPosition().getIsFreq().intValue() == 1 || order.getAdvertPosition().getIsPlayback().intValue() == 1){
				ployServiceIds = "[\"";
				for(String serviceId : pp.getServiceIdList()){
					if(!serviceIdMap.containsKey(serviceId) && !ployServiceIdMap.containsKey(serviceId)){
						//精准中不存在的频道serviceId，并且同时间段内的分策略不存在的serviceId
						ployServiceIds += serviceId+",";
						ployServiceIdMap.put(serviceId, "");
					}
				}
				if(ployServiceIds.endsWith(",")){
					ployServiceIds = ployServiceIds.substring(0,ployServiceIds.length()-1);
				}
				ployServiceIds += "\"]";
				categoryIds="[\""+"0\"]";
				
			}else if(order.getAdvertPosition().getIsColumn() == 1){
				//支持回看栏目广告位
				ployServiceIds="[\""+"0\"]";
				categoryIds="[\"";
					
				for(String categoryId : pp.getLookbackCategoryIdList()){
					if(!ployCategoryIdMap.containsKey(categoryId)){
						//过滤精准中的栏目ID
						categoryIds += categoryId+",";
					}
				}
				if(categoryIds.endsWith(",")){
					categoryIds = categoryIds.substring(0,categoryIds.length()-1);
				}
				categoryIds+="\"]";
			}else
			{
				ployServiceIds="[\""+"0\"]";
				categoryIds="[\""+"0\"]";
			}
			
			
			
			String sTime = pp.getStartTime();
			String eTime = pp.getEndTime();
			if ("00:00:00".equals(sTime)
					&& ("23:59:59".equals(eTime) || "24:00:00".equals(eTime) || "00:00:00"
							.equals(eTime))) {
				PlayListGis playList = new PlayListGis();
				Date endTime = this.stringToTime(order.getEndDate(), eTime);
				playList.setStartTime(order.getStartDate());
				if (playList.getStartTime().before(new Date()))
				{
					playList.setStartTime(new Date());
				}
				playList.setEndTime(endTime);
				playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
				playList.setAreas(pp.getAreaCode());
				playList.setServiceId(ployServiceIds);
				playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
				playList.setContentId(contentId);
				playList.setContentPath(contentPath);
				playList.setContentType(contentType);
				playList.setContractId(order.getContractId());
				playList.setOrderId(order.getId());
				playList.setPloyId(order.getPloyId());
				playList.setState(Constant.VALID);
				playList.setTvn(pp.getTvnNumber());
				playList.setUserIndustrys(pp.getUserIndustrys());
				playList.setUserLevels(pp.getUserLevels());
				playList.setAssetId("[\"0\"]");
				playList.setCategoryId(categoryIds);
				ps.add(playList);
			} else {
				Date sDate = order.getStartDate();
				Date eDate = order.getEndDate();
				Calendar ca = Calendar.getInstance();
				while (sDate.compareTo(eDate) <= 0) {
					Date startTime = new Date(stringToTime(sDate, sTime).getTime());
					Date endTime = new Date(stringToTime(sDate, eTime).getTime());
					/**
					 * 审核新订单时，maxEndTime为null，创建所有时间的播出单
					 * 审核已播出的订单时，只创建未生产日期的播出单
					 */
					if(maxEndTime == null || maxEndTime.getTime()<startTime.getTime()){
						PlayListGis playList = new PlayListGis();
						playList.setStartTime(startTime);
						if (playList.getStartTime().before(new Date()))
						{
							playList.setStartTime(new Date());
						}
						playList.setEndTime(endTime);
						playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
						playList.setAreas(pp.getAreaCode());
						playList.setServiceId(ployServiceIds);
						playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
						playList.setContentId(contentId);
						playList.setContentPath(contentPath);
						playList.setContentType(contentType);
						playList.setContractId(order.getContractId());
						playList.setOrderId(order.getId());
						playList.setPloyId(order.getPloyId());
						playList.setState(Constant.VALID);
						playList.setTvn(pp.getTvnNumber());
						playList.setUserIndustrys(pp.getUserIndustrys());
						playList.setUserLevels(pp.getUserLevels());
						playList.setAssetId("[\"0\"]");
						playList.setCategoryId(categoryIds);
						ps.add(playList);
					}
	
					ca.setTime(sDate);
					ca.add(Calendar.DATE, 1);// 把日期加上1天然后重新赋值给开始时间
					sDate = ca.getTime();
				}
			}
		}
		return ps;
	}
	
	
	
	
	/**
	 * 将策略订单信息填充到播出单对象中  点播随片
	 * @param orderId
	 * @return
	 */
	private List<PlayListGis> followPloyOrderToPlayList(OrderBean order,
			List<PlayListGis> ps,Map<String,String> serviceIdMap,Map<String,List<String>> categoryIdMap,Date maxEndTime) {
		
		List<PloyPlayListGisRel> pList = playListGisDao.getPloyPlayListGisByFollowOrder(order.getId(),order.getAdvertPosition().getPositionCode());

		
		for(PloyPlayListGisRel pp : pList){
			
			String contentType = "";//播出单的内容类型
			String contentId = "";//播出单的内容ID
			String contentPath = null;
			
			String assetId = "[\"0\"]";
			String categoryId = "[\"\"]";

			
			contentId = getContentId(pp.getMateId(), pp.getResourceType());
			contentPath = pp.getPath();
			if (contentPath == null) {
				logger.error("获取资源路径失败！");
				return null;
			}
									
			String sTime = pp.getStartTime();
			String eTime = pp.getEndTime();
			if ("00:00:00".equals(sTime)
					&& ("23:59:59".equals(eTime) || "24:00:00".equals(eTime) || "00:00:00"
							.equals(eTime))) {
				PlayListGis playList = new PlayListGis();
				Date endTime = this.stringToTime(order.getEndDate(), eTime);
				playList.setStartTime(order.getStartDate());
				if (playList.getStartTime().before(new Date()))
				{
					playList.setStartTime(new Date());
				}
				playList.setEndTime(endTime);
				playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
				playList.setAreas(pp.getAreaCode());
				playList.setServiceId("[\"\"]");
				playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
				playList.setContentId(contentId);
				playList.setContentPath(contentPath);
				playList.setContentType(contentType);
				playList.setContractId(order.getContractId());
				playList.setOrderId(order.getId());
				playList.setPloyId(order.getPloyId());
				playList.setState(Constant.VALID);
				playList.setTvn(pp.getTvnNumber());
				playList.setUserIndustrys(pp.getUserIndustrys());
				playList.setUserLevels(pp.getUserLevels());
				playList.setAssetId(assetId);
				playList.setCategoryId(categoryId);
				ps.add(playList);
			} else {
				Date sDate = order.getStartDate();
				Date eDate = order.getEndDate();
				Calendar ca = Calendar.getInstance();
				while (sDate.compareTo(eDate) <= 0) {
					Date startTime = new Date(stringToTime(sDate, sTime).getTime());
					Date endTime = new Date(stringToTime(sDate, eTime).getTime());
					/**
					 * 审核新订单时，maxEndTime为null，创建所有时间的播出单
					 * 审核已播出的订单时，只创建未生产日期的播出单
					 */
					if(maxEndTime == null || maxEndTime.getTime()<startTime.getTime()){
						PlayListGis playList = new PlayListGis();
						playList.setStartTime(startTime);
						if (playList.getStartTime().before(new Date()))
						{
							playList.setStartTime(new Date());
						}
						playList.setEndTime(endTime);
						playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
						playList.setAreas(pp.getAreaCode());
						playList.setServiceId("[\"\"]");
						playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
						playList.setContentId(contentId);
						playList.setContentPath(contentPath);
						playList.setContentType(contentType);
						playList.setContractId(order.getContractId());
						playList.setOrderId(order.getId());
						playList.setPloyId(order.getPloyId());
						playList.setState(Constant.VALID);
						playList.setTvn(pp.getTvnNumber());
						playList.setUserIndustrys(pp.getUserIndustrys());
						playList.setUserLevels(pp.getUserLevels());
						playList.setAssetId(assetId);
						playList.setCategoryId(categoryId);
						ps.add(playList);
					}
	
					ca.setTime(sDate);
					ca.add(Calendar.DATE, 1);// 把日期加上1天然后重新赋值给开始时间
					sDate = ca.getTime();
				}
			}
		}
		return ps;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	/**
//	 * 将精准订单信息填充到播出单对象中
//	 * 单向广告位的有分策略、无精准，双向广告位无分策略，有主策略和精准
//	 * @param orderId
//	 * @param maxEndTime
//	 * @return
//	 */
//	private List<PlayListGis> orderToPlayList(Integer orderId,Date maxEndTime) {
//		
//		OrderBean order = super.getOrder(orderId);
//		List<PlayListGis> ps = new ArrayList<PlayListGis>();
//		Map<String,String> serviceIdMap = new HashMap<String,String>();
//		if(order.getPositionPackageType().intValue()==Constant.POSITION_TYPE_TWO_REAL_TIME
//				|| order.getPositionPackageType().intValue()==Constant.POSITION_TYPE_TWO_REAL_TIME_REQ){
//			//双向广告位需要查询精准信息
//			long prePreciseId = 0;
//			List<MaterialBean> materialList = playListGisDao.getPreciseMaterialByOrder(order.getId());
//			Map<String,String> categoryIdMap = new HashMap<String,String>();
//			Map<String,String> assetIdMap = new HashMap<String,String>();
//			for(MaterialBean material : materialList){
//				long preciseId = material.getPrecise().getId();
//				//根据精准生成播出单（同一个精准可能存在多个素材等）
//				if(prePreciseId != preciseId){
//					String contentType = "";
//					StringBuffer contentId = new StringBuffer();
//					String contentPath = "";
//					if (order.getAdvertPosition().getIsLoop().intValue() == Constant.ADVERT_POSITION_IS_POLLING) {// 多图
//						contentType = Constant.MULTI_IMAGE_MATERIAL_TYPE;
//						contentPath = "[";
//						int index = 0;
//						for (int i = 0; i < materialList.size(); i++) {
//							long pId = materialList.get(i).getPrecise().getId();
//							if(pId == preciseId){
//								if (index > 0) {
//									contentPath += ",";
//									contentId.append(",");
//								}
//								if (materialList.get(i).getType() == Constant.IMAGE) {
//									contentPath += "{\"pic\":\""+materialList.get(i).getPath()+"\"}";
//									String cId = getContentId(materialList.get(i).getId(), Constant.IMAGE)+"_"+materialList.get(i).getLoopNo();
//									contentId.append(cId);
//								}
//								index++;
//							}
//						}
//						contentPath += "]";
//					} else {
//						if (order.getAdvertPosition().getTextRuleId() != null
//								&& material.getType() == Constant.WRITING) {// 字幕
//							contentType = Constant.WRITING_MATERIAL_TYPE;
//							StringBuffer jsonContent = new StringBuffer();
//							String cId = getContentId(material.getId(), Constant.WRITING);
//							contentId.append(cId);
////							TextMate text = material.getText();
////							jsonContent.append("{\"id\":\"").append(text.getId()).append("\",")
////							.append("\"name\":").append(text.getName()).append("\",")
////							.append("\"URL\":").append(text.getURL()).append("\",")
////							.append("\"action\":").append(text.getAction()).append("\",")
////							.append("\"bkgColor\":").append(text.getBkgColor()).append("\",")
////							.append("\"content\":").append(text.getContent()).append(",")
////							.append("\"durationTime\":").append(text.getDurationTime()).append("\",")
////							.append("\"fontColor\":").append(text.getFontColor()).append("\",")
////							.append("\"fontSize\":").append(text.getFontSize()).append("\",")
////							.append("\"positionVertexCoordinates\":").append(text.getPositionVertexCoordinates()).append("\",")
////							.append("\"positionWidthHeight\":").append(text.getPositionWidthHeight()).append("\",")
////							.append("\"rollSpeed\":\"").append(text.getRollSpeed()).append("\"}");
//							
////							contentPath = jsonContent.toString();
//							Gson gson = new Gson();
//							contentPath = gson.toJson(material.getText());
//	
//						} else {
//							String cId = getContentId(material.getId(), material.getType());
//							contentId.append(cId);
//							contentPath = material.getPath();
//						}
//					}
//					if (contentPath == null) {
//						logger.error("精准ID="+preciseId+"获取资源路径失败！");
//						break;
//					}
//					
//					//精准播出单的区域
//					String preciseAreaIds = "";
//					//精准播出单的频道ID
//					String preciseServiceIds = "[\"";
//					String channelIds  = "";
//					String categoryIds = "[\"";
//					String assetIds = "[\"";
//					if(order.getAdvertPosition().getIsChannel().intValue() == 1
//							|| order.getAdvertPosition().getIsFreq().intValue() == 1){
//						if(material.getPrecise().getPrecisetype() != null && material.getPrecise().getPrecisetype().intValue()==10){
//							//按频道分组
//							channelIds = this.getServiceIdByGroupIds(material.getPrecise().getGroupId());
//						}else{
//							//支持频道广告位
//							channelIds = material.getPrecise().getDtvChannelId();
//						}
//						String[] serviceIds = channelIds.split(",");
//						for(String id : serviceIds){
//							if(!serviceIdMap.containsKey(id)){
//								serviceIdMap.put(id, "");
//								preciseServiceIds += id+",";
//							}
//						}
//					}else if(order.getAdvertPosition().getIsPlayback().intValue() == 1){
//						if(material.getPrecise().getPrecisetype() != null && material.getPrecise().getPrecisetype().intValue()==10){
//							//按频道分组
//							channelIds = this.getServiceIdByGroupIds(material.getPrecise().getGroupId());
//						}else{
//							//支持回放广告位
//							channelIds = material.getPrecise().getPlaybackChannelId();
//						}
//						String[] serviceIds = channelIds.split(",");
//						for(String id : serviceIds){
//							if(!serviceIdMap.containsKey(id)){
//								serviceIdMap.put(id, "");
//								preciseServiceIds += id+",";
//							}
//						}
//					}else if(order.getAdvertPosition().getIsColumn() == 1){
//						//支持回看栏目广告位
//						String cateIds = material.getPrecise().getLookbackCategoryId();
//						if(!StringUtils.isEmpty(cateIds)){
//							String cIds = this.getLookbackCategortId(cateIds);
//							String[] ids = cIds.split(",");
//							for(String id : ids){//过滤优先级
//								if(!categoryIdMap.containsKey(id)){
//									categoryIdMap.put(id, "");
//									categoryIds += id+",";
//								}
//							}
//						}
//					}else if(order.getAdvertPosition().getIsAsset() == 1 
//							|| order.getAdvertPosition().getIsFollowAsset() == 1){
//						//支持影片精准广告位 或者 支持点播随片精准广告位
//						String assIds = material.getPrecise().getAssetId();
//						if(!StringUtils.isEmpty(assIds)){
//							String aIds = this.getAssetId(assIds);
//							String[] ids = aIds.split(",");
//							for(String id : ids){//过滤优先级
//								if(!assetIdMap.containsKey(id)){
//									assetIdMap.put(id, "");
//									assetIds += id+",";
//								}
//							}
//						}
//					}
//					if(preciseServiceIds.endsWith(",")){
//						preciseServiceIds = preciseServiceIds.substring(0,preciseServiceIds.length()-1);
//					}
//					if(categoryIds.endsWith(",")){
//						categoryIds = categoryIds.substring(0,categoryIds.length()-1);
//					}
//					if(assetIds.endsWith(",")){
//						assetIds = assetIds.substring(0,assetIds.length()-1);
//					}
//					preciseServiceIds += "\"]";
//					categoryIds += "\"]";
//					assetIds += "\"]";
//					if(material.getPrecise().getUserArea() == null){
//						//精准对应的区域为空，则从策略中取区域信息
//						if(order.getAreas() == null){//订单策略中区域信息为空，设置订单区域信息
//							//播出单的策略区域
//							String ployAreaIds = "";
//							//设置播出单的区域信息
//							List<PloyBean> ployList = new ArrayList<PloyBean>();;
//							if(order.getAdvertPosition().getIsChannel().intValue() == 1
//									|| order.getAdvertPosition().getIsFreq().intValue() == 1){
//								//广告位支持频道
//								ployList = this.getPloyChannelList(order.getPloy().getPloyId());
//							}else if(order.getAdvertPosition().getIsPlayback().intValue() == 1){
//								ployList = this.getPloyNpvrList(order.getPloy().getPloyId());
//							}else if(order.getAdvertPosition().getIsColumn() == 1 
//									|| order.getAdvertPosition().getIsAsset() == 1
//									|| order.getAdvertPosition().getIsFollowAsset() == 1){
//								ployAreaIds = order.getPloy().getAreaId()+"";
//							}
//							
//							int preAreaId = -1;
//							int index = 0;
//							for(PloyBean p : ployList){
//								int areaId = p.getAreaId();
//								if(p.getAreaId() != preAreaId){
//									if(index > 0){
//										ployAreaIds += ",";
//									}
//									ployAreaIds += areaId;
//									index++;
//								}
//							}
//							order.setAreas(ployAreaIds);
//						}
//						preciseAreaIds = order.getAreas();
//					}else{
//						preciseAreaIds = material.getPrecise().getUserArea();
//					}
//					
//					String sTime = order.getPloy().getStartTime();
//					String eTime = order.getPloy().getEndTime();
//					if ("00:00:00".equals(sTime)
//							&& ("23:59:59".equals(eTime) || "24:00:00".equals(eTime) || "00:00:00"
//									.equals(eTime))) {
//						PlayListGis playList = new PlayListGis();
//						Date endTime = this.stringToTime(order.getEndDate(), eTime);
//						playList.setStartTime(order.getStartDate());
//						playList.setEndTime(endTime);
//						playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
//						playList.setAreas(preciseAreaIds);
//						playList.setServiceId(preciseServiceIds);
//						playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
//						playList.setContentId(contentId.toString());
//						playList.setContentPath(contentPath);
//						playList.setContentType(contentType);
//						playList.setContractId(order.getContractId());
//						playList.setOrderId(order.getId());
//						playList.setPloyId(order.getPloyId());
//						playList.setState(Constant.VALID);
//						playList.setTvn(order.getPloy().getTvnNumber());
//						playList.setUserIndustrys(order.getPloy().getUserIndustrys());
//						playList.setUserLevels(order.getPloy().getUserLevels());
//						playList.setCategoryId(categoryIds);
//						playList.setAssetId(assetIds);
//						ps.add(playList);
//					} else {
//						Date sDate = order.getStartDate();
//						Date eDate = order.getEndDate();
//						Calendar ca = Calendar.getInstance();
//						while (sDate.compareTo(eDate) <= 0) {
//							Date startTime = new Date(stringToTime(sDate, sTime).getTime());
//							Date endTime = new Date(stringToTime(sDate, eTime).getTime());
//							/**
//							 * 审核新订单时，maxEndTime为null，创建所有时间的播出单
//							 * 审核已播出的订单时，只创建未生产日期的播出单
//							 */
//							if(maxEndTime == null || maxEndTime.getTime()<startTime.getTime()){
//								PlayListGis playList = new PlayListGis();
//								playList.setStartTime(startTime);
//								playList.setEndTime(endTime);
//								playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
//								playList.setAreas(preciseAreaIds);
//								playList.setServiceId(preciseServiceIds);
//								playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
//								playList.setContentId(contentId.toString());
//								playList.setContentPath(contentPath);
//								playList.setContentType(contentType);
//								playList.setContractId(order.getContractId());
//								playList.setOrderId(order.getId());
//								playList.setPloyId(order.getPloyId());
//								playList.setState(Constant.VALID);
//								playList.setTvn(order.getPloy().getTvnNumber());
//								playList.setUserIndustrys(order.getPloy().getUserIndustrys());
//								playList.setUserLevels(order.getPloy().getUserLevels());
//								playList.setCategoryId(categoryIds);
//								playList.setAssetId(assetIds);
//								ps.add(playList);
//							}
//							ca.setTime(sDate);
//							ca.add(Calendar.DATE, 1);// 把日期加上1天然后重新赋值给开始时间
//							sDate = ca.getTime();
//						}
//					}
//				}
//			}
//		}
//		//设置投放策略对应的播出单
//		ployOrderToPlayList(order,ps,serviceIdMap,maxEndTime);
//		return ps;
//	}
	
	/**
	 * 将精准订单信息填充到播出单对象中
	 * 单向广告位的有分策略、无精准，双向广告位无分策略，有主策略和精准
	 * @param orderId
	 * @param maxEndTime
	 * @return
	 */
	private List<PlayListGis> orderToPlayList(Integer orderId,Date maxEndTime) {
		
		OrderBean order = super.getOrder(orderId);
		List<PlayListGis> ps = new ArrayList<PlayListGis>();
		Map<String,String> serviceIdMap = new HashMap<String,String>();
		Map<String,String> categoryIdMap = new HashMap<String,String>();
		if(order.getPositionPackageType().intValue()==Constant.POSITION_TYPE_TWO_REAL_TIME
				|| order.getPositionPackageType().intValue()==Constant.POSITION_TYPE_TWO_REAL_TIME_REQ){
			//双向广告位需要查询精准信息
			Integer prePreciseId = 0;//前一个精准ID
//			List<MaterialBean> materialList = playListGisDao.getPreciseMaterialByOrder(order.getId());
			List<PrecisePlayListGisRel> pList = playListGisDao.getPrecisePlayListByOrder(order.getId());
			
			Map<String,String> assetIdMap = new HashMap<String,String>();
			for(PrecisePlayListGisRel pp : pList){
				Integer preciseId = pp.getPreciseId();
				//根据精准生成播出单（同一个精准可能存在多个素材等）
				if(prePreciseId.intValue() == preciseId.intValue()){
					prePreciseId = preciseId;
					continue;
				}
				prePreciseId = preciseId;
				String contentType = "";//播出单的内容类型
				String contentId = "";//播出单的内容ID
				String contentPath = "";//播出单的素材路径
				
				if (order.getAdvertPosition().getIsLoop().intValue() == Constant.ADVERT_POSITION_IS_POLLING) {// 多图
					contentType = Constant.MULTI_IMAGE_MATERIAL_TYPE;
					contentPath = "[";
					for (PrecisePlayListGisRel p : pList) {
						if(p.getPreciseId().intValue() == preciseId.intValue()){//只获取某一精准的素材
							if (p.getResourceType().intValue() == Constant.IMAGE) {
								contentPath += "{\"pic\":\""+p.getPath()+"\",\"index\":\""+p.getPollIndex()+"\"},";
//								contentId += getContentId(p.getMateId(), Constant.IMAGE)+"_"+p.getPollIndex()+",";
								contentId += p.getMateId()+",";
							}
						}
					}
					if(contentPath.endsWith(",")){
						contentPath = contentPath.substring(0,contentPath.length()-1);
					}
					if(contentId.endsWith(",")){
						contentId = contentId.substring(0,contentId.length()-1);
					}
					contentPath += "]";
				} else if(order.getAdvertPosition().getTextRuleId() != null
						&& pp.getResourceType() == Constant.WRITING){// 字幕
					contentType = Constant.WRITING_MATERIAL_TYPE;
					contentId = getContentId(pp.getMateId(), Constant.WRITING);
					Gson gson = new Gson();
					contentPath = gson.toJson(pp.getText());
				} else {
					contentId = getContentId(pp.getMateId(), pp.getResourceType());
					contentPath = pp.getPath();
				}
				
				if (contentPath == null) {
					logger.error("精准ID="+preciseId+"获取资源路径失败！");
					break;
				}
				
				//精准播出单的频道ID
				String preciseServiceIds = "[\"";
				String channelIds  = "";
				String categoryIds = "[\"";
				String assetIds = "[\"";
				if(order.getAdvertPosition().getIsChannel().intValue() == 1
						|| order.getAdvertPosition().getIsFreq().intValue() == 1){
					if(pp.getPreciseType() != null && pp.getPreciseType().intValue()==10){
						//按频道分组
						for(String serviceId : pp.getServiceIdList()){
							channelIds += serviceId+",";
						}
					}else{
						//支持频道广告位
						channelIds = pp.getDtvChannelId();
					}
					if(channelIds.endsWith(",")){
						channelIds = channelIds.substring(0,channelIds.length()-1);
					}
					String[] serviceIds = channelIds.split(",");
					for(String id : serviceIds){
						if(!serviceIdMap.containsKey(id)){
							serviceIdMap.put(id, "");
							preciseServiceIds += id+",";
						}
					}
				}else if(order.getAdvertPosition().getIsPlayback().intValue() == 1){
					if(pp.getPreciseType() != null && pp.getPreciseType().intValue()==10){
						//按频道分组
						for(String serviceId : pp.getServiceIdList()){
							channelIds += serviceId+",";
						}
					}else{
						//支持回放广告位
						channelIds = pp.getPlaybackChannelId();
					}
					if(channelIds.endsWith(",")){
						channelIds = channelIds.substring(0,channelIds.length()-1);
					}
					String[] serviceIds = channelIds.split(",");
					for(String id : serviceIds){
						if(!serviceIdMap.containsKey(id)){
							serviceIdMap.put(id, "");
							preciseServiceIds += id+",";
						}
					}
				}else if(order.getAdvertPosition().getIsColumn() == 1){
					//支持回看栏目广告位
					if(pp.getLookbackCategoryIdList() != null){
						for(String categoryId : pp.getLookbackCategoryIdList()){
							if(!categoryIdMap.containsKey(categoryId)){
								categoryIdMap.put(categoryId, "");
								categoryIds += categoryId+",";
							}
						}
					}
				}else if(order.getAdvertPosition().getIsAsset() == 1 
						|| order.getAdvertPosition().getIsFollowAsset() == 1){
					//支持影片精准广告位 或者 支持点播随片精准广告位
					if(pp.getAssetIdList() != null ){
						for(String assetId : pp.getAssetIdList()){
							if(!assetIdMap.containsKey(assetId)){
								assetIdMap.put(assetId, "");
								assetIds += assetId+",";
							}
						}
					}
				}
				if(preciseServiceIds.endsWith(",")){
					preciseServiceIds = preciseServiceIds.substring(0,preciseServiceIds.length()-1);
				}
				if(categoryIds.endsWith(",")){
					categoryIds = categoryIds.substring(0,categoryIds.length()-1);
				}
				if(assetIds.endsWith(",")){
					assetIds = assetIds.substring(0,assetIds.length()-1);
				}
				preciseServiceIds += "\"]";
				categoryIds += "\"]";
				assetIds += "\"]";
				
				PlayListGis playList = new PlayListGis();
				Date endTime = this.stringToTime(order.getEndDate(), "23:59:59");
				playList.setStartTime(order.getStartDate());
				if (playList.getStartTime().before(new Date()))
				{
					playList.setStartTime(new Date());
				}
				playList.setEndTime(endTime);
				playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
				playList.setAreas(pp.getUserArea());
				playList.setServiceId(preciseServiceIds);
				playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
				playList.setContentId(contentId);
				playList.setContentPath(contentPath);
				playList.setContentType(contentType);
				playList.setContractId(order.getContractId());
				playList.setOrderId(order.getId());
				playList.setPloyId(order.getPloyId());
				playList.setState(Constant.VALID);
				if(pp.getIndustryList() != null){
					String industrys = "";
					for(String industry : pp.getIndustryList()){
						industrys += industry+",";
					}
					if(industrys.endsWith(",")){
						industrys = industrys.substring(0,industrys.length()-1);
					}
					playList.setUserIndustrys(industrys);
				}
				if(pp.getLevelList() != null){
					String levels = "";
					for(String level : pp.getLevelList()){
						levels += level+",";
					}
					if(levels.endsWith(",")){
						levels = levels.substring(0,levels.length()-1);
					}
					playList.setUserLevels(levels);
				}
				playList.setCategoryId(categoryIds);
				playList.setAssetId(assetIds);
				ps.add(playList);
			}
		}
		//设置投放策略对应的播出单 频道、回放频道、回看栏目、影片
		//ployOrderToPlayList(order,ps,serviceIdMap,maxEndTime);
		ployOrderToPlayList(order,ps,serviceIdMap,categoryIdMap,maxEndTime);
		return ps;
	}
	
	/**
	 * 将精准订单信息填充到播出单对象中
	 * 单向广告位的有分策略、无精准，双向广告位无分策略，有主策略和精准
	 * @param orderId
	 * @param maxEndTime
	 * @return
	 */
	private List<PlayListGis> orderToPlayList2(Integer orderId,Date maxEndTime) {
		
		OrderBean order = super.getOrder(orderId);
		//回看菜单广告
		if(order.getPositionId().intValue() == 15 || order.getPositionId().intValue() == 16 ){
			return lookOrderToPlayList(order,maxEndTime);
		}
		//回放菜单广告、回放暂停、回放插播
		if(order.getPositionId().intValue() == 17 || order.getPositionId().intValue() == 39 || order.getPositionId().intValue() == 40){
			return lookOrderToNPVRPlayList(order,maxEndTime);
		}
		//点播随片
		if(order.getPositionId().intValue() == 25 || order.getPositionId().intValue() == 26){
			return followPlayList(order,maxEndTime);
		}
		List<PlayListGis> ps = new ArrayList<PlayListGis>();
		//NVOD主界面广告
		if(order.getPositionId()==51&& order.getPositionPackageType().intValue()==Constant.POSITION_TYPE_ONE_REAL_TIME){
			List<PrecisePlayListGisRel> pList = playListGisDao.getNVODMenuPrecisePlayListByOrder(order.getId());
			for(PrecisePlayListGisRel pp : pList){
			String contentId = getContentId(pp.getMateId(), pp.getResourceType());//播出单的内容ID
			String contentPath = pp.getPath();//播出单的素材路径
			String contentType = "";//播出单的内容类型
			
			PlayListGis playList = new PlayListGis();
			playList.setStartTime(DateUtil.StringToDateYYYMMMDD24MM(DateUtil.formatDate(order.getStartDate())+" "+pp.getStartTime()));
			playList.setEndTime(DateUtil.StringToDateYYYMMMDD24MM(DateUtil.formatDate(order.getEndDate())+" "+pp.getEndTime()));
			playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
			playList.setAreas(pp.getAreaCode());
			playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
			playList.setContentId(contentId);
			playList.setContentPath(contentPath);
			playList.setContentType(contentType);
			playList.setContractId(order.getContractId());
			playList.setOrderId(order.getId());
			playList.setPloyId(pp.getPloyId());
			playList.setState(Constant.VALID);
			playList.setMenuTypeCode(pp.getMenuTypeCode());
			ps.add(playList);
			
			}
			return ps;
		}
		//NVOD挂角广告
		if(order.getPositionId()==54&& order.getPositionPackageType().intValue()==Constant.POSITION_TYPE_ONE_REAL_TIME){
			//查询挂角的区域
			List<OrderMaterialRelation> areaCodeList = playListGisDao.getNVODAngleRelMateAreaCodeByOrder(order.getId());
			//查询订单与素材的关系
			List<PrecisePlayListGisRel> pList = playListGisDao.getNVODAnglePrecisePlayListByOrder(order.getId());
			//按区域存放播出单
			for(OrderMaterialRelation mate:areaCodeList){
				PlayListGis playList = new PlayListGis();
				int pollIndex = 1;
				StringBuffer path=new StringBuffer();//每个区域四个素材路径
				for(PrecisePlayListGisRel pp : pList ){
					if(mate.getAreaCode().equals(pp.getAreaCode())&& mate.getStartTime().equals(pp.getStartTime())
							&& mate.getEndTime().equals(pp.getEndTime())){
						if(1==pollIndex){
							String contentId = getContentId(pp.getMateId(), pp.getResourceType());//播出单的内容ID
							//String contentPath = pp.getPath();//播出单的素材路径
							String contentType = "";//播出单的内容类型
							
							playList.setStartTime(DateUtil.StringToDateYYYMMMDD24MM(DateUtil.formatDate(order.getStartDate())+" "+pp.getStartTime()));
							playList.setEndTime(DateUtil.StringToDateYYYMMMDD24MM(DateUtil.formatDate(order.getEndDate())+" "+pp.getEndTime()));
							playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
							playList.setAreas(pp.getAreaCode());
							playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
							playList.setContentId(contentId);
							//playList.setContentPath(contentPath);
							playList.setContentType(contentType);
							playList.setContractId(order.getContractId());
							playList.setOrderId(order.getId());
							playList.setPloyId(pp.getPloyId());
							playList.setState(Constant.VALID);
							playList.setServiceId("[\"0\"]");//和频道无关
							//playList.setMenuTypeCode(pp.getMenuTypeCode());
							//ps.add(playList);
						}
							String contentPath = pp.getPath();//播出单的素材路径
							path.append(contentPath).append(",");
							if(4==pollIndex){
								contentPath = path.toString().substring(0, path.toString().lastIndexOf(","));
								playList.setContentPath(contentPath);
								ps.add(playList);
							}
						pollIndex++;
					}
					
				}
			}
			return ps;
		}
		Map<String,String> serviceIdMap = new HashMap<String,String>();
		Map<String,String> categoryIdMap = new HashMap<String,String>();
		if(order.getPositionPackageType().intValue()==Constant.POSITION_TYPE_TWO_REAL_TIME
				|| order.getPositionPackageType().intValue()==Constant.POSITION_TYPE_TWO_REAL_TIME_REQ){ 
			//双向广告位需要查询精准信息
			Integer prePreciseId = 0;//前一个精准ID
			List<PrecisePlayListGisRel> pList = playListGisDao.getPrecisePlayListByOrder(order.getId());
			
			Map<String,String> assetIdMap = new HashMap<String,String>();
			for(PrecisePlayListGisRel pp : pList){
				Integer preciseId = pp.getPreciseId();
				//根据精准生成播出单（同一个精准可能存在多个素材等）
				if(prePreciseId.intValue() == preciseId.intValue()){
					prePreciseId = preciseId;
					continue;
				}
				prePreciseId = preciseId;
				String contentType = "";//播出单的内容类型
				String contentId = "";//播出单的内容ID
				String contentPath = "";//播出单的素材路径
				
				if (order.getAdvertPosition().getIsLoop().intValue() == Constant.ADVERT_POSITION_IS_POLLING) {// 多图
					contentType = Constant.MULTI_IMAGE_MATERIAL_TYPE;
					contentPath = "[";
					for (PrecisePlayListGisRel p : pList) {
						if(p.getPreciseId().intValue() == preciseId.intValue()){//只获取某一精准的素材
							if (p.getResourceType().intValue() == Constant.IMAGE) {
								contentPath += "{\"pic\":\""+p.getPath()+"\",\"index\":\""+p.getPollIndex()+"\"},";
//								contentId += getContentId(p.getMateId(), Constant.IMAGE)+"_"+p.getPollIndex()+",";
								contentId += p.getMateId()+",";
							}
						}
					}
					if(contentPath.endsWith(",")){
						contentPath = contentPath.substring(0,contentPath.length()-1);
					}
					if(contentId.endsWith(",")){
						contentId = contentId.substring(0,contentId.length()-1);
					}
					contentPath += "]";
				} else if(order.getAdvertPosition().getTextRuleId() != null
						&& pp.getResourceType() == Constant.WRITING){// 字幕
					contentType = Constant.WRITING_MATERIAL_TYPE;
					contentId = getContentId(pp.getMateId(), Constant.WRITING);
					Gson gson = new Gson();
					contentPath = gson.toJson(pp.getText());
				} else {
					contentId = getContentId(pp.getMateId(), pp.getResourceType());
					contentPath = pp.getPath();
				}
				
				if (contentPath == null) {
					logger.error("精准ID="+preciseId+"获取资源路径失败！");
					break;
				}
				
				//精准播出单的频道ID
				String preciseServiceIds = "[\"";
				String channelIds  = "";
				String categoryIds = "[\"";
				String assetIds = "[\"";
				if(order.getAdvertPosition().getIsChannel().intValue() == 1
						|| order.getAdvertPosition().getIsFreq().intValue() == 1){
					if(pp.getPreciseType() != null && pp.getPreciseType().intValue()==10){
						//按频道分组
						for(String serviceId : pp.getServiceIdList()){
							channelIds += serviceId+",";
						}
					}else{
						//支持频道广告位
						channelIds = pp.getDtvChannelId();
					}
					if(channelIds.endsWith(",")){
						channelIds = channelIds.substring(0,channelIds.length()-1);
					}
					String[] serviceIds = channelIds.split(",");
					for(String id : serviceIds){
						if(!serviceIdMap.containsKey(id)){
							serviceIdMap.put(id, "");
							preciseServiceIds += id+",";
						}
					}
				}else if(order.getAdvertPosition().getIsPlayback().intValue() == 1){
					if(pp.getPreciseType() != null && pp.getPreciseType().intValue()==10){
						//按频道分组
						for(String serviceId : pp.getServiceIdList()){
							channelIds += serviceId+",";
						}
					}else{
						//支持回放广告位
						channelIds = pp.getPlaybackChannelId();
					}
					if(channelIds.endsWith(",")){
						channelIds = channelIds.substring(0,channelIds.length()-1);
					}
					String[] serviceIds = channelIds.split(",");
					for(String id : serviceIds){
						if(!serviceIdMap.containsKey(id)){
							serviceIdMap.put(id, "");
							preciseServiceIds += id+",";
						}
					}
				}else if(order.getAdvertPosition().getIsColumn() == 1){
					//支持回看栏目广告位
					if(pp.getLookbackCategoryIdList() != null){
						for(String categoryId : pp.getLookbackCategoryIdList()){
							if(!categoryIdMap.containsKey(categoryId)){
								categoryIdMap.put(categoryId, "");
								categoryIds += categoryId+",";
							}
						}
					}
				}else if(order.getAdvertPosition().getIsAsset() == 1 
						|| order.getAdvertPosition().getIsFollowAsset() == 1){
					//支持影片精准广告位 或者 支持点播随片精准广告位
					if(pp.getAssetIdList() != null ){
						for(String assetId : pp.getAssetIdList()){
							if(!assetIdMap.containsKey(assetId)){
								assetIdMap.put(assetId, "");
								assetIds += assetId+",";
							}
						}
					}
				}
				if(preciseServiceIds.endsWith(",")){
					preciseServiceIds = preciseServiceIds.substring(0,preciseServiceIds.length()-1);
				}
				if(categoryIds.endsWith(",")){
					categoryIds = categoryIds.substring(0,categoryIds.length()-1);
				}
				if(assetIds.endsWith(",")){
					assetIds = assetIds.substring(0,assetIds.length()-1);
				}
				preciseServiceIds += "\"]";
				categoryIds += "\"]";
				assetIds += "\"]";
				
				PlayListGis playList = new PlayListGis();
				Date endTime = this.stringToTime(order.getEndDate(), "23:59:59");
				playList.setStartTime(order.getStartDate());
				if (playList.getStartTime().before(new Date()))
				{
					playList.setStartTime(new Date());
				}
				playList.setEndTime(endTime);
				playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
				playList.setAreas(pp.getUserArea());
				playList.setServiceId(preciseServiceIds);
				playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
				playList.setContentId(contentId);
				playList.setContentPath(contentPath);
				playList.setContentType(contentType);
				playList.setContractId(order.getContractId());
				playList.setOrderId(order.getId());
				playList.setPloyId(order.getPloyId());
				playList.setState(Constant.VALID);
				if(pp.getIndustryList() != null){
					String industrys = "";
					for(String industry : pp.getIndustryList()){
						industrys += industry+",";
					}
					if(industrys.endsWith(",")){
						industrys = industrys.substring(0,industrys.length()-1);
					}
					playList.setUserIndustrys(industrys);
				}
				if(pp.getLevelList() != null){
					String levels = "";
					for(String level : pp.getLevelList()){
						levels += level+",";
					}
					if(levels.endsWith(",")){
						levels = levels.substring(0,levels.length()-1);
					}
					playList.setUserLevels(levels);
				}
				playList.setCategoryId(categoryIds);
				playList.setAssetId(assetIds);
				ps.add(playList);
			}
		}
		//设置投放策略对应的播出单 频道、回放频道、回看栏目、影片
		ployOrderToPlayList2(order,ps,serviceIdMap,categoryIdMap,maxEndTime);
		return ps;
	}
private List<PlayListGis> lookOrderToNPVRPlayList(OrderBean order,Date maxEndTime) {
		
		List<PlayListGis> ps = new ArrayList<PlayListGis>();
		Map<String,String> serviceIdMap = new HashMap<String,String>();
		Map<String,List<String>> categoryIdMap = new HashMap<String,List<String>>();
		//双向广告位需要查询精准信息
		List<PrecisePlayListGisRel> pList = playListGisDao.getPrecisePlayListByNPVROrder(order.getId());
		//保存开始时段与对应的栏目ID到MAP中，以便在策略中生成播出单时过滤栏目ID
		for(PrecisePlayListGisRel pp : pList){
			if(!categoryIdMap.containsKey(pp.getStartTime())){
				categoryIdMap.put(pp.getStartTime(), null);
			}
			List<String> categoryIdList = categoryIdMap.get(pp.getStartTime());
			if(categoryIdList==null){
				categoryIdList = new ArrayList<String>(); 
			}
			//将回看栏目ID添加到对应的时间段中
			if(pp.getLookbackCategoryIdList() != null){
				for(String categoryId : pp.getLookbackCategoryIdList()){
					categoryIdList.add(categoryId);
				}
			}
			//替换时间段中的栏目ID列表
			categoryIdMap.put(pp.getStartTime(), categoryIdList);
		}
		
		for(PrecisePlayListGisRel pp : pList){
			String contentType = "";//播出单的内容类型
			String contentId = "";//播出单的内容ID
			String contentPath = "";//播出单的素材路径
			
			contentId = getContentId(pp.getMateId(), pp.getResourceType());
			contentPath = pp.getPath();
			
			//精准播出单的频道ID
			String preciseServiceIds = "[\"";
			String channelIds  = "";
			String categoryIds = "[\"";
			String assetIds = "[\"";
			if(order.getAdvertPosition().getIsPlayback().intValue() == 1){
				if(pp.getPreciseType() != null && pp.getPreciseType().intValue()==10){
					//按频道分组
					for(String serviceId : pp.getServiceIdList()){
						channelIds += serviceId+",";
					}
				}else{
					//支持回放广告位
					channelIds = pp.getPlaybackChannelId();
				}
				if(channelIds.endsWith(",")){
					channelIds = channelIds.substring(0,channelIds.length()-1);
				}
				String[] serviceIds = channelIds.split(",");
				for(String id : serviceIds){
					if(!serviceIdMap.containsKey(id)){
						serviceIdMap.put(id, "");
						preciseServiceIds += id+",";
					}
				}
			}else if(order.getAdvertPosition().getIsColumn() == 1){
				//支持回看栏目广告位
				if(pp.getLookbackCategoryIdList() != null){
					for(String categoryId : pp.getLookbackCategoryIdList()){
						categoryIds += categoryId+",";
					}
				}
			}
			if(preciseServiceIds.endsWith(",")){
				preciseServiceIds = preciseServiceIds.substring(0,preciseServiceIds.length()-1);
			}
			if(categoryIds.endsWith(",")){
				categoryIds = categoryIds.substring(0,categoryIds.length()-1);
			}
			if(assetIds.endsWith(",")){
				assetIds = assetIds.substring(0,assetIds.length()-1);
			}
			preciseServiceIds += "\"]";
			categoryIds += "\"]";
			assetIds += "\"]";
			
			String sTime = pp.getStartTime();
			String eTime = pp.getEndTime();
			if ("00:00:00".equals(sTime)
					&& ("23:59:59".equals(eTime) || "24:00:00".equals(eTime) || "00:00:00"
							.equals(eTime))) {
				PlayListGis playList = new PlayListGis();
				Date endTime = this.stringToTime(order.getEndDate(), eTime);
				playList.setStartTime(order.getStartDate());
				if (playList.getStartTime().before(new Date()))
				{
					playList.setStartTime(new Date());
				}
				playList.setEndTime(endTime);
				playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
				playList.setAreas(pp.getUserArea());
				playList.setServiceId(preciseServiceIds);
				playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
				playList.setContentId(contentId);
				playList.setContentPath(contentPath);
				playList.setContentType(contentType);
				playList.setContractId(order.getContractId());
				playList.setOrderId(order.getId());
				playList.setPloyId(order.getPloyId());
				playList.setState(Constant.VALID);
				playList.setCategoryId(categoryIds);
				playList.setAssetId(assetIds);
				ps.add(playList);
			}else {
				Date sDate = order.getStartDate();
				Date eDate = order.getEndDate();
				Calendar ca = Calendar.getInstance();
				while (sDate.compareTo(eDate) <= 0) {
					Date startTime = new Date(stringToTime(sDate, sTime).getTime());
					Date endTime = new Date(stringToTime(sDate, eTime).getTime());
					/**
					 * 审核新订单时，maxEndTime为null，创建所有时间的播出单
					 * 审核已播出的订单时，只创建未生产日期的播出单
					 */
					if(maxEndTime == null || maxEndTime.getTime()<startTime.getTime()){
						PlayListGis playList = new PlayListGis();
						playList.setStartTime(startTime);
						if (playList.getStartTime().before(new Date()))
						{
							playList.setStartTime(new Date());
						}
						playList.setEndTime(endTime);
						playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
						playList.setAreas(pp.getUserArea());
						playList.setServiceId(preciseServiceIds);
						playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
						playList.setContentId(contentId);
						playList.setContentPath(contentPath);
						playList.setContentType(contentType);
						playList.setContractId(order.getContractId());
						playList.setOrderId(order.getId());
						playList.setPloyId(order.getPloyId());
						playList.setState(Constant.VALID);
						playList.setCategoryId(categoryIds);
						playList.setAssetId(assetIds);
						ps.add(playList);
					}
	
					ca.setTime(sDate);
					ca.add(Calendar.DATE, 1);// 把日期加上1天然后重新赋值给开始时间
					sDate = ca.getTime();
				}
			}
		}
		//设置投放策略对应的播出单 回看栏目
		lookPloyOrderToPlayList(order,ps,serviceIdMap,categoryIdMap,maxEndTime);
		return ps;
	}
	
	/**
	 * 将精准订单信息填充到播出单对象中
	 * 回看菜单，分时间段和回看栏目
	 * @param orderId
	 * @param maxEndTime
	 * @return
	 */
	private List<PlayListGis> lookOrderToPlayList(OrderBean order,Date maxEndTime) {
		
		List<PlayListGis> ps = new ArrayList<PlayListGis>();
		Map<String,String> serviceIdMap = new HashMap<String,String>();
		Map<String,List<String>> categoryIdMap = new HashMap<String,List<String>>();
		//双向广告位需要查询精准信息      素材订单关联表中 "所有/全部" 这样的记录type为1，对应策略表 
		List<PrecisePlayListGisRel> pList = playListGisDao.getPrecisePlayListByOrder(order.getId());
		//保存开始时段与对应的栏目ID到MAP中，以便在策略中生成播出单时过滤栏目ID
		for(PrecisePlayListGisRel pp : pList){
			if(!categoryIdMap.containsKey(pp.getStartTime())){
				categoryIdMap.put(pp.getStartTime(), null);
			}
			List<String> categoryIdList = categoryIdMap.get(pp.getStartTime());
			if(categoryIdList==null){
				categoryIdList = new ArrayList<String>(); 
			}
			//将回看栏目ID添加到对应的时间段中
			if(pp.getLookbackCategoryIdList() != null){
				for(String categoryId : pp.getLookbackCategoryIdList()){
					categoryIdList.add(categoryId);
				}
			}
			//替换时间段中的栏目ID列表
			categoryIdMap.put(pp.getStartTime(), categoryIdList);
		}  // 例：需要过滤的栏目  categoryIdMap = {00:00:00=[c5661500, c5657350], 12:00:00=[c5661500, c5657350]}
		
		for(PrecisePlayListGisRel pp : pList){
			String contentType = "";//播出单的内容类型
			String contentId = "";//播出单的内容ID
			String contentPath = "";//播出单的素材路径
			
			contentId = getContentId(pp.getMateId(), pp.getResourceType());
			contentPath = pp.getPath();
			
			//精准播出单的频道ID
			String preciseServiceIds = "[\"";
			String channelIds  = "";
			String categoryIds = "[\"";
			String assetIds = "[\"";
			if(order.getAdvertPosition().getIsPlayback().intValue() == 1){
				if(pp.getPreciseType() != null && pp.getPreciseType().intValue()==10){
					//按频道分组
					for(String serviceId : pp.getServiceIdList()){
						channelIds += serviceId+",";
					}
				}else{
					//支持回放广告位
					channelIds = pp.getPlaybackChannelId();
				}
				if(channelIds.endsWith(",")){
					channelIds = channelIds.substring(0,channelIds.length()-1);
				}
				String[] serviceIds = channelIds.split(",");
				for(String id : serviceIds){
					if(!serviceIdMap.containsKey(id)){
						serviceIdMap.put(id, "");
						preciseServiceIds += id+",";
					}
				}
			}else if(order.getAdvertPosition().getIsColumn() == 1){
				//支持回看栏目广告位
				if(pp.getLookbackCategoryIdList() != null){
					for(String categoryId : pp.getLookbackCategoryIdList()){
						categoryIds += categoryId+",";
					}
				}
			}
			if(preciseServiceIds.endsWith(",")){
				preciseServiceIds = preciseServiceIds.substring(0,preciseServiceIds.length()-1);
			}
			if(categoryIds.endsWith(",")){
				categoryIds = categoryIds.substring(0,categoryIds.length()-1);
			}
			if(assetIds.endsWith(",")){
				assetIds = assetIds.substring(0,assetIds.length()-1);
			}
			preciseServiceIds += "\"]";
			categoryIds += "\"]";
			assetIds += "\"]";
			
			String sTime = pp.getStartTime();
			String eTime = pp.getEndTime();
			//时间是连续的，订单跨多天也只生成一条播出单
			if ("00:00:00".equals(sTime)
					&& ("23:59:59".equals(eTime) || "24:00:00".equals(eTime) || "00:00:00"
							.equals(eTime))) {
				PlayListGis playList = new PlayListGis();
				Date endTime = this.stringToTime(order.getEndDate(), eTime);
				playList.setStartTime(order.getStartDate());
				if (playList.getStartTime().before(new Date()))
				{
					playList.setStartTime(new Date());
				}
				playList.setEndTime(endTime);
				playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
				playList.setAreas(pp.getUserArea());
				playList.setServiceId(preciseServiceIds);
				playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
				playList.setContentId(contentId);
				playList.setContentPath(contentPath);
				playList.setContentType(contentType);
				playList.setContractId(order.getContractId());
				playList.setOrderId(order.getId());
				playList.setPloyId(order.getPloyId());
				playList.setState(Constant.VALID);
				playList.setCategoryId(categoryIds);
				playList.setAssetId(assetIds);
				ps.add(playList);
			}else {
				//时间是不连续的，订单开始结束期间内，每天生成一条播出单
				Date sDate = order.getStartDate();
				Date eDate = order.getEndDate();
				Calendar ca = Calendar.getInstance();
				while (sDate.compareTo(eDate) <= 0) {
					Date startTime = new Date(stringToTime(sDate, sTime).getTime());
					Date endTime = new Date(stringToTime(sDate, eTime).getTime());
					/**
					 * 审核新订单时，maxEndTime为null，创建所有时间的播出单
					 * 审核已播出的订单时，只创建未生产日期的播出单
					 */
					if(maxEndTime == null || maxEndTime.getTime()<startTime.getTime()){
						PlayListGis playList = new PlayListGis();
						playList.setStartTime(startTime);
						if (playList.getStartTime().before(new Date()))
						{
							playList.setStartTime(new Date());
						}
						playList.setEndTime(endTime);
						playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
						playList.setAreas(pp.getUserArea());
						playList.setServiceId(preciseServiceIds);
						playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
						playList.setContentId(contentId);
						playList.setContentPath(contentPath);
						playList.setContentType(contentType);
						playList.setContractId(order.getContractId());
						playList.setOrderId(order.getId());
						playList.setPloyId(order.getPloyId());
						playList.setState(Constant.VALID);
						playList.setCategoryId(categoryIds);
						playList.setAssetId(assetIds);
						ps.add(playList);
					}
	
					ca.setTime(sDate);
					ca.add(Calendar.DATE, 1);// 把日期加上1天然后重新赋值给开始时间
					sDate = ca.getTime();
				}
			}
		}
		//设置投放策略对应的播出单 回看栏目
		lookPloyOrderToPlayList(order,ps,serviceIdMap,categoryIdMap,maxEndTime);
		return ps;
	}
	
	/**
	 * 将精准订单信息填充到播出单对象中
	 * 点播随片，分时间段、影片名称、影片分类栏目
	 * @param orderId
	 * @param maxEndTime
	 * @return
	 */
	private List<PlayListGis> followPlayList(OrderBean order,Date maxEndTime) {
		
		List<PlayListGis> ps = new ArrayList<PlayListGis>();
		Map<String,String> serviceIdMap = new HashMap<String,String>();
		Map<String,List<String>> categoryIdMap = new HashMap<String,List<String>>();
		//双向广告位需要查询精准信息      素材订单关联表中 "所有/全部" 这样的记录type为1，对应策略表 
		List<PrecisePlayListGisRel> pList = playListGisDao.getPrecisePlayListByFollowOrder(order.getId());
				
		for(PrecisePlayListGisRel pp : pList){
			String contentType = "";//播出单的内容类型
			String contentId = "";//播出单的内容ID
			String contentPath = "";//播出单的素材路径
			
			contentId = getContentId(pp.getMateId(), pp.getResourceType());
			contentPath = pp.getPath();
			
			//精准播出单的频道ID
			String preciseServiceIds = "[\"";
			
			String categoryIds = "[\"";
			String assetIds = "[\"";
			
			//影片分类栏目
			if(pp.getLookbackCategoryIdList() != null){
				for(String categoryId : pp.getLookbackCategoryIdList()){
					categoryIds += categoryId+",";
				}
			}
			//影片
			if(pp.getAssetIdList() != null){
				for(String assetId : pp.getAssetIdList()){
					assetIds += assetId + ",";
				}
			}
			
			if(categoryIds.endsWith(",")){
				categoryIds = categoryIds.substring(0,categoryIds.length()-1);
			}
			if(assetIds.endsWith(",")){
				assetIds = assetIds.substring(0,assetIds.length()-1);
			}
			preciseServiceIds += "\"]";
			categoryIds += "\"]";
			assetIds += "\"]";
			
			Integer priority = pp.getPriority();
			
			String sTime = pp.getStartTime();
			String eTime = pp.getEndTime();
			//时间是连续的，订单跨多天也只生成一条播出单
			if ("00:00:00".equals(sTime)
					&& ("23:59:59".equals(eTime) || "24:00:00".equals(eTime) || "00:00:00"
							.equals(eTime))) {
				PlayListGis playList = new PlayListGis();
				Date endTime = this.stringToTime(order.getEndDate(), eTime);
				playList.setStartTime(order.getStartDate());
				if (playList.getStartTime().before(new Date()))
				{
					playList.setStartTime(new Date());
				}
				playList.setEndTime(endTime);
				playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
				playList.setAreas(pp.getUserArea());
				playList.setServiceId(preciseServiceIds);
				playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
				playList.setContentId(contentId);
				playList.setContentPath(contentPath);
				playList.setContentType(contentType);
				playList.setContractId(order.getContractId());
				playList.setOrderId(order.getId());
				playList.setPloyId(order.getPloyId());
				playList.setState(Constant.VALID);
				playList.setCategoryId(categoryIds);
				playList.setAssetId(assetIds);
				playList.setPriority(priority);
				ps.add(playList);
			}else {
				//时间是不连续的，订单开始结束期间内，每天生成一条播出单
				Date sDate = order.getStartDate();
				Date eDate = order.getEndDate();
				Calendar ca = Calendar.getInstance();
				while (sDate.compareTo(eDate) <= 0) {
					Date startTime = new Date(stringToTime(sDate, sTime).getTime());
					Date endTime = new Date(stringToTime(sDate, eTime).getTime());
					/**
					 * 审核新订单时，maxEndTime为null，创建所有时间的播出单
					 * 审核已播出的订单时，只创建未生产日期的播出单
					 */
					if(maxEndTime == null || maxEndTime.getTime()<startTime.getTime()){
						PlayListGis playList = new PlayListGis();
						playList.setStartTime(startTime);
						if (playList.getStartTime().before(new Date()))
						{
							playList.setStartTime(new Date());
						}
						playList.setEndTime(endTime);
						playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
						playList.setAreas(pp.getUserArea());
						playList.setServiceId(preciseServiceIds);
						playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
						playList.setContentId(contentId);
						playList.setContentPath(contentPath);
						playList.setContentType(contentType);
						playList.setContractId(order.getContractId());
						playList.setOrderId(order.getId());
						playList.setPloyId(order.getPloyId());
						playList.setState(Constant.VALID);
						playList.setCategoryId(categoryIds);
						playList.setAssetId(assetIds);
						playList.setPriority(priority);
						ps.add(playList);
					}
	
					ca.setTime(sDate);
					ca.add(Calendar.DATE, 1);// 把日期加上1天然后重新赋值给开始时间
					sDate = ca.getTime();
				}
			}
		}
		//设置投放策略对应的播出单 回看栏目
		followPloyOrderToPlayList(order,ps,serviceIdMap,categoryIdMap,maxEndTime);
		return ps;
	}
	
	
	
	
	/**
	 * 根据精准ids获取精准对应的素材列表
	 * @param preciseIds
	 * @return
	 */
//	public List<MaterialBean> getPreciseMaterialByPreciseIds(String preciseIds){
//		return playListGisDao.getPreciseMaterialByPreciseIds(preciseIds);
//	}

	/**
	 * 将订单信息填充到播出单对象中
	 * 
	 * @param orderId
	 *            订单编号
	 * @param playListEndDate
	 *            播出单结束日期
	 * @param orderEndDate
	 *            订单结束日期
	 * @param sTime
	 *            策略开始时间
	 * @param eTime
	 *            策略结束时间
	 * @return 播出单记录集合
	 */
	private List<PlayListGis> orderToPlayListByDate(Integer orderId,
			Date playListEndDate, Date orderEndDate, String sTime, String eTime) {
		PlayListGis pl = playListGisDao.getPlayList(orderId);
		List<PlayListGis> ps = new ArrayList<PlayListGis>();
		Calendar ca = Calendar.getInstance();
		ca.setTime(playListEndDate);
		ca.add(Calendar.DATE, 1);// 把日期加上1天然后重新赋值给开始时间
		while (ca.getTime().compareTo(orderEndDate) <= 0) {
			PlayListGis playList = new PlayListGis();
			Date startTime = new Date(stringToTime(ca.getTime(), sTime)
					.getTime());
			Date endTime = new Date(stringToTime(ca.getTime(), eTime).getTime());
			playList.setStartTime(startTime);
			playList.setEndTime(endTime);
			playList.setAdSiteCode(pl.getAdSiteCode());
			playList.setAreas(pl.getAreas());
			playList.setServiceId(pl.getServiceId());
			playList.setCharacteristicIdentification(pl
					.getCharacteristicIdentification());
			playList.setContentId(pl.getContentId());
			playList.setContentPath(pl.getContentPath());
			playList.setContentType(pl.getContentType());
			playList.setContractId(pl.getContractId());
			playList.setOrderId(pl.getOrderId());
			playList.setPloyId(pl.getPloyId());
			playList.setState(Constant.VALID);
			playList.setTvn(pl.getTvn());
			playList.setUserIndustrys(pl.getUserIndustrys());
			playList.setUserLevels(pl.getUserLevels());
			playList.setCategoryId(pl.getCategoryId());
			playList.setAssetId(pl.getAssetId());
			
			ps.add(playList);

			ca.add(Calendar.DATE, 1);// 把日期加上1天然后重新赋值给开始时间

		}

		return ps;
	}

	/**
	 * 将类型为Date的日期和类型为String的时间转成Date型的日期时间
	 * */
	private Date stringToTime(Date date, String time) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = null;
		try {
			String d = sdf.format(date);
			dt = df.parse(d + " " + time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt;
	}

	/**
	 * 取Date类型的日期
	 * */
	private Date getYMD(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String ymd = df.format(date);
		Date ymdDate = null;
		try {
			ymdDate = df.parse(ymd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ymdDate;
	}

	public int updateAllPlayList(Integer orderId) {
		try {
			List<Integer> pIds = playListGisDao.getPlayListIds(orderId);
			if (pIds != null && pIds.size() > 0) {
				this.deleteAllPlayList(orderId);
			} else {
				pIds = playListReqDao.getPlayListIds(orderId);
				playListReqService.deletePlayList(pIds);
			}
			this.insertPlayList(orderId);
		} catch (Exception e) {
			logger.error("根据订单更新播出单出现异常", e);
			return 1;
		}
		return 0;
	}
	
	/**
	 * 根据素材ID获取图片文件大小
	 * @param id
	 * @return
	 */
	public String getImageMateFileSize(Integer id){
		return playListGisDao.getImageMateFileSize(id);
	}
	
	/**
	 * 获取素材和文件大小关系
	 * @param mateIds
	 * @return
	 */
	public Map<Integer,String> getImageMateFileSizeMap(String mateIds){
		return playListGisDao.getImageMateFileSizeMap(mateIds);
	}

	@Override
	public void repush(Integer orderId) {
		playListGisDao.repush(orderId);
		
	}
}
