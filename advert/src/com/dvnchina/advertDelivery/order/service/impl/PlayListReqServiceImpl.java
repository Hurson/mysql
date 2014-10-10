package com.dvnchina.advertDelivery.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.order.bean.Order;
import com.dvnchina.advertDelivery.order.bean.PlayListReq;
import com.dvnchina.advertDelivery.order.bean.PlayListReqContent;
import com.dvnchina.advertDelivery.order.bean.PlayListReqPContent;
import com.dvnchina.advertDelivery.order.bean.PlayListReqPrecise;
import com.dvnchina.advertDelivery.order.bean.playlist.MaterialBean;
import com.dvnchina.advertDelivery.order.bean.playlist.OrderBean;
import com.dvnchina.advertDelivery.order.bean.playlist.PloyBean;
import com.dvnchina.advertDelivery.order.dao.PlayListGisDao;
import com.dvnchina.advertDelivery.order.dao.PlayListReqDao;
import com.dvnchina.advertDelivery.order.service.PlayListGisService;
import com.dvnchina.advertDelivery.order.service.PlayListReqService;
import com.dvnchina.advertDelivery.sysconfig.service.BaseConfigService;

public class PlayListReqServiceImpl extends PlayListServiceImpl implements
		PlayListReqService {
	private static Logger logger = Logger.getLogger(PlayListReqServiceImpl.class);

	private PlayListReqDao playListReqDao;
	private BaseConfigService baseConfigService;
	private PlayListGisDao playListGisDao;
	private PlayListGisService playListGisService;

	public void setPlayListReqDao(PlayListReqDao playListReqDao) {
		this.playListReqDao = playListReqDao;
	}

	public void setBaseConfigService(BaseConfigService baseConfigService) {
		this.baseConfigService = baseConfigService;
	}

	public void setPlayListGisDao(PlayListGisDao playListGisDao) {
		this.playListGisDao = playListGisDao;
	}

	public void setPlayListGisService(PlayListGisService playListGisService) {
		this.playListGisService = playListGisService;
	}

//	public int insertPlayList(Integer orderId) {
//		try {
//			OrderBean order = getOrder(orderId);
//			
//			/************************/
//			List<PloyBean> ployList = new ArrayList<PloyBean>();;
//			if(order.getAdvertPosition().getIsChannel().intValue() == 1){
//				//广告位支持频道
//				ployList = this.getPloyChannelList(order.getPloy().getPloyId());
//			}else if(order.getAdvertPosition().getIsPlayback().intValue() == 1){
//				ployList = this.getPloyNpvrList(order.getPloy().getPloyId());
//			}
//			if(ployList.size()>0){
//				//设置播出单的区域
//				String ployAreaIds = "";
//				//播出单的频道ID
//				String ployServiceIds = "[";
//				int preAreaId = -1;
//				int index = 0;
//				for(PloyBean p : ployList){
//					int areaId = p.getAreaId();
//					if(p.getAreaId() != preAreaId){
//						if(index > 0){
//							ployAreaIds += ",";
//							if(ployServiceIds.endsWith(",")){
//								ployServiceIds = ployServiceIds.substring(0,ployServiceIds.length()-1);
//							}
//							ployServiceIds += "\",\"";
//						}else{
//							ployServiceIds += "\"";
//						}
//						ployAreaIds += areaId;
//						index++;
//					}
//					if(p.getChannelId() == 0){
//						ployServiceIds += "0,";
//					}else{
//						ployServiceIds += p.getServiceId()+",";
//					}
//					
//				}
//				if(ployServiceIds.endsWith(",")){
//					ployServiceIds = ployServiceIds.substring(0,ployServiceIds.length()-1)+"\"]";
//				}
//				order.setAreas(ployAreaIds);
//				order.setServiceIds(ployServiceIds);
//			}else{
//				//填充策略对应的区域
//				order.setAreas(order.getPloy().getAreaId().toString());
//			}
//			
//			/*************************/
//
//			Integer iNumber = 0;
//			
//			boolean isInstream = false;//是否是插播广告位
//			String iPosition = baseConfigService.getBaseConfigByCode("instreamPosition");
//			String[] array = iPosition.split(",");
//			for(String adCode : array){
//				if(order.getAdvertPosition().getPositionCode().equals(adCode)){
//					iNumber = new Integer(baseConfigService.getBaseConfigByCode("instreamNumber"));
//					isInstream = true;//插播广告位
//					break;
//				}
//			}
//			/** 订单内容记录 */
//			List<MaterialBean> ms = playListReqDao.getMaterialByOrder(orderId);
//			if (isInstream) {// 视频插播广告位，产生多条播出单记录
//
//				List<PlayListReq> ps = this.orderToPlayLists(order, iNumber);
//				playListReqDao.insertPlayList(ps);
//				Map<Integer, String> idMap = playListReqDao.getPlayListMap(orderId);
//				Set<Integer> idSet = idMap.keySet();
//				Iterator<Integer> idIter = idSet.iterator();
//				while (idIter.hasNext()) {
//					Integer id = idIter.next();
//					String values = idMap.get(id);
//					String playLocation = values.split(",")[1];
//					Map<String, Object> map = this.getPlayListContent(ms,id, playLocation);
//					this.insertContents(map);
//				}
//
//			} else {// 非视频插播,非字幕广告位，产生一条播出单记录
//				/** 处理播出单记录 */
//				PlayListReq p = this.orderToPlayList(order);
//				playListReqDao.insertPlayList(p);
//				Integer id = playListReqDao.getPlayListId(orderId);
//
//				Map<String, Object> map = this.getPlayListContent(ms, id);
//				this.insertContents(map);
//			}
//			return 0;
//		} catch (Exception e) {
//			e.printStackTrace();
//			this.deletePlayList(orderId);
//		}
//		return 1;
//	}
	
	public int insertPlayList(Integer orderId) {
		try {
			OrderBean order = getOrder(orderId);
//			Integer iNumber = 0;
			
			boolean isInstream = false;//是否是插播广告位
			String iPosition = baseConfigService.getBaseConfigByCode("instreamPosition");
			String[] array = iPosition.split(",");
			for(String adCode : array){
				if(order.getAdvertPosition().getPositionCode().equals(adCode)){
//					iNumber = new Integer(baseConfigService.getBaseConfigByCode("instreamNumber"));
					isInstream = true;//插播广告位
					break;
				}
			}
			/** 订单内容记录 */
			List<MaterialBean> ms = playListReqDao.getMaterialByOrder(orderId);
			if (isInstream) {// 视频插播广告位，产生多条播出单记录

				List<PlayListReq> ps = this.orderToPlayLists(order,ms);
				playListReqDao.insertPlayList(ps);
				Map<Integer, String> idMap = playListReqDao.getPlayListMap(orderId);
				Set<Integer> idSet = idMap.keySet();
				Iterator<Integer> idIter = idSet.iterator();
				while (idIter.hasNext()) {
					Integer id = idIter.next();
					String values = idMap.get(id);
					String playLocation = values.split(",")[1];
					String begin = values.split(",")[2];
					Map<String, Object> map = this.getPlayListContent(ms,id, playLocation,begin);
					this.insertContents(map);
				}

			} 
			//夏双奇加的，还原成任光明的，2014-8-26，请求式订单有且只有一条非精准播出单
//			else {// 非视频插播
//				/** 处理播出单记录 */
//				List<PlayListReq> ps = this.orderToPlayLists(order,ms);
//				playListReqDao.insertPlayList(ps);
////				Integer id = playListReqDao.getPlayListId(orderId);
//				List<PlayListReq> list = playListReqDao.getPlayList(orderId);
//
//				Map<String, Object> map = this.getPlayListContent(ms, list);
//				this.insertContents(map);
//			}
			else {// 非视频插播,非字幕广告位，产生一条播出单记录
				/** 处理播出单记录 */
				PlayListReq p = this.orderToPlayList(order);
				playListReqDao.insertPlayList(p);
				Integer id = playListReqDao.getPlayListId(orderId);

				Map<String, Object> map = this.getPlayListContent(ms, id);
				this.insertContents(map);
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			this.deletePlayList(orderId);
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	private void insertContents(Map<String, Object> map) {
		List<PlayListReqContent> contents = (List<PlayListReqContent>) map
				.get("content");
		playListReqDao.insertContents(contents);

		/** 处理精准及精准内容记录 */
		if (map.get("precise") != null) {
			List<PlayListReqPrecise> precises = (List<PlayListReqPrecise>) map
					.get("precise");
			for (PlayListReqPrecise precise : precises) {
				playListReqDao.insertPrecise(precise);
				List<PlayListReqPContent> pContents = precise.getPContents();
				for (PlayListReqPContent pContent : pContents) {
					pContent.setPreciseId(precise.getId());
				}
				playListReqDao.insertPContent(pContents);
			}
		}

	}

//	public void deletePlayList(List<Integer> playListIds) {
//		List<Integer> preciseIds = playListReqDao.getPreciseIds(playListIds);
//		List<String> contentPaths = playListReqDao
//				.getWritingContentPath(playListIds);
//		List<String> pContentPaths = null;
//		if (preciseIds != null && preciseIds.size() > 0) {
//			pContentPaths = playListReqDao.getPWritingContentPath(preciseIds);
//			playListReqDao.deletePlayLists(playListIds, preciseIds);
//		} else {
//			playListReqDao.deletePlayLists(playListIds);
//		}
//		if ((contentPaths != null && contentPaths.size() > 0)
//				|| (pContentPaths != null && pContentPaths.size() > 0)) {
//			FtpUtils ftpUtil = null;
//			try {
//				ftpUtil = new FtpUtils();
//				ftpUtil.connectionFtp();
//				if (contentPaths != null && contentPaths.size() > 0) {
//					for (int i = 0; i < contentPaths.size(); i++) {
//						ftpUtil.deleteFile(contentPaths.get(i));
//					}
//				}
//				if (pContentPaths != null && pContentPaths.size() > 0) {
//					for (int i = 0; i < pContentPaths.size(); i++) {
//						ftpUtil.deleteFile(pContentPaths.get(i));
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				ftpUtil.closedFtp();
//			}
//		}
//
//	}
	
	public void deletePlayList(List<Integer> playListIds) {
		List<Integer> preciseIds = playListReqDao.getPreciseIds(playListIds);
		if (preciseIds != null && preciseIds.size() > 0) {
			playListReqDao.deletePlayLists(playListIds, preciseIds);
		} else {
			playListReqDao.deletePlayLists(playListIds);
		}

	}

	public int deletePlayList(Integer orderId) {
		try {
			List<Integer> pIds = playListReqDao.getPlayListIds(orderId);
			if(pIds != null && pIds.size()>0){
				this.deletePlayList(pIds);
			}
		} catch (Exception e) {
			logger.error("删除请求式播出单出现异常",e);
			return 1;
		}
		return 0;
		
	}

	/**
	 * 更新请求式播出单的结束时间
	 */
	public int updatePlayListEndDate(Order order) {
		try {
//			Date endDate = playListReqDao.getOrderEndDate(orderId);
			playListReqDao.updateEndTime(order.getId(), order.getEndTime());
		} catch (Exception e) {
			logger.error("更新请求式播出单出现异常",e);
			return 1;
		}
		return 0;

	}

	/**
	 * 根据订单ID更新对应的播出单
	 */
	public int updateAllPlayList(Integer orderId) {
		try {
			//根据订单ID查询订单对应的播出单ID列表
			List<Integer> pIds = playListReqDao.getPlayListIds(orderId);
			if(pIds!=null&&pIds.size()>0){
				this.deletePlayList(pIds);
			}
			//add by zxf 2014-8-26理论上不会被执行，因为外层已经区分请求式
			else{
				pIds = playListGisDao.getPlayListIds(orderId);
				playListGisService.deleteAllPlayList(orderId);
			}
			this.insertPlayList(orderId);
		} catch (Exception e) {
			logger.error("根据订单更新播出单出现异常",e);
			return 1;
		}
		return 0;
	}
	
	/**
	 * 添加策略对应插播请求播出单
	 * @param order
	 * @param instreamNumber
	 * @return
	 */
	private List<PlayListReq> orderToPlayLists(OrderBean order,
			int instreamNumber) {
		List<PlayListReq> ps = new ArrayList<PlayListReq>();
		for(PloyBean ploy : order.getPloyList()){
//			PloyBean ploy = new PloyBean();
			if(order.getPloyList() != null){
				ploy = order.getPloyList().get(0);
			}
			String startTime = ploy.getStartTime();
			String endTime = ploy.getEndTime();
			if("0".equals(startTime)){
				startTime = "00:00:00";
				endTime = "23:59:59";
			}
			String serviceIds = "";
			if(ploy.getServiceIdList() != null){
				for(String serviceId : ploy.getServiceIdList()){
					serviceIds += serviceId+",";
				}
				if(serviceIds.endsWith(",")){
					serviceIds = serviceIds.substring(0,serviceIds.length()-1);
				}
			}
			for (int i = 0; i < instreamNumber; i++) {
				PlayListReq playList = new PlayListReq();
				playList.setBegin(startTime);
				playList.setEnd(endTime);
				playList.setPlayTime(ploy.getPloyNumber());
				playList.setBeginDate(order.getStartDate());
				playList.setEndDate(order.getEndDate());
				playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
				playList.setAreas(ploy.getAreaId()+"");
				playList.setChannels(serviceIds);
				playList.setContractId(order.getContractId());
				playList.setOrderId(order.getId());
				playList.setPloyId(order.getPloyId());
				playList.setState(Constant.VALID);
				playList.setTvn(ploy.getTvnNumber());
				playList.setUserIndustrys(ploy.getUserIndustrys());
				playList.setUserLevels(ploy.getUserLevels());
				playList.setCharacteristicIdentification(getIdentificationStr(
						order.getAdvertPosition().getIsHD(), i, instreamNumber));
				ps.add(playList);
			}
		}

		return ps;
	}
	
	/**
	 * 添加策略对应请求播出单
	 * @param ms
	 * @return
	 */
	private List<PlayListReq> orderToPlayLists(OrderBean order,List<MaterialBean> ms) {
		List<PlayListReq> ps = new ArrayList<PlayListReq>();
		PloyBean ploy = new PloyBean();
		if(order.getPloyList() != null){
			ploy = order.getPloyList().get(0);
		}
		//对于有插播位置的广告位，一个插播位应向ad_playlist_req表插入一条数据
		Map mappposi=new HashMap();
		for(MaterialBean m : ms){
			//if(m.getPreciseType() == 1){//基本策略对应素材
				if(mappposi.containsKey(m.getPlayLocation())){
					continue;
				}
			    mappposi.put(m.getPlayLocation(), m.getPlayLocation());
				String startTime = m.getStartTime();
				String endTime = m.getEndTime();
				if("0".equals(startTime)){
					startTime = "00:00:00";
					endTime = "23:59:59";
				}
				String serviceIds = "";
				if(ploy.getServiceIdList() != null){
					for(String serviceId : ploy.getServiceIdList()){
						serviceIds += serviceId+",";
					}
					if(serviceIds.endsWith(",")){
						serviceIds = serviceIds.substring(0,serviceIds.length()-1);
					}
				}
				PlayListReq playList = new PlayListReq();
				playList.setBegin(startTime);
				playList.setEnd(endTime);
				playList.setPlayTime(ploy.getPloyNumber());
				playList.setBeginDate(order.getStartDate());
				playList.setEndDate(order.getEndDate());
				playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
				playList.setAreas(ploy.getAreaId()+"");
				playList.setChannels(serviceIds);
				playList.setContractId(order.getContractId());
				playList.setOrderId(order.getId());
				playList.setPloyId(order.getPloyId());
				playList.setState(Constant.VALID);
				playList.setTvn(ploy.getTvnNumber());
				playList.setUserIndustrys(ploy.getUserIndustrys());
				playList.setUserLevels(ploy.getUserLevels());
				playList.setCharacteristicIdentification(getIdentificationStr2(
						order.getAdvertPosition().getIsHD(), m.getPlayLocation()));
				ps.add(playList);
			//}
		}

		return ps;
	}

	private PlayListReq orderToPlayList(OrderBean order) {
		PloyBean ploy = new PloyBean();
		if(order.getPloyList() != null){
			ploy = order.getPloyList().get(0);
		}
		String startTime = ploy.getStartTime();
		String endTime = ploy.getEndTime();
		if("0".equals(startTime)){
			startTime = "00:00:00";
			endTime = "23:59:59";
		}
		String serviceIds = "";
		if(ploy.getServiceIdList() != null){
			for(String serviceId : ploy.getServiceIdList()){
				serviceIds += serviceId+",";
			}
			if(serviceIds.endsWith(",")){
				serviceIds = serviceIds.substring(0,serviceIds.length()-1);
			}
		}
		
		PlayListReq playList = new PlayListReq();
		playList.setBegin(startTime);
		playList.setEnd(endTime);
		playList.setPlayTime(ploy.getPloyNumber());
		playList.setBeginDate(order.getStartDate());
		playList.setEndDate(order.getEndDate());
		playList.setAdSiteCode(order.getAdvertPosition().getPositionCode());
		playList.setAreas(ploy.getAreaId()+"");
		playList.setChannels(serviceIds);
		playList.setCharacteristicIdentification(getHDStr(order.getAdvertPosition().getIsHD()));
		playList.setContractId(order.getContractId());
		playList.setOrderId(order.getId());
		playList.setPloyId(order.getPloyId());
		playList.setState(Constant.VALID);
		playList.setTvn(ploy.getTvnNumber());
		playList.setUserIndustrys(ploy.getUserIndustrys());
		playList.setUserLevels(ploy.getUserLevels());
		playList.setUserNumber(order.getUserNumber());
		playList.setQuestionnaireNumber(order.getQuestionnaireNumber());
		playList.setIntegralRatio(order.getIntegralRatio());

		return playList;
	}

	private Map<String, Object> getPlayListContent(List<MaterialBean> ms, Integer playListId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PlayListReqContent> contents = new ArrayList<PlayListReqContent>();
		List<PlayListReqPrecise> precises = null;
		for (MaterialBean m : ms) {
			if (m.getPrecise() == null) {// 策略绑定素材
				PlayListReqContent c = new PlayListReqContent();
				c.setContentId(getContentId(m.getId(), m.getType()));
				c.setPlayListId(playListId);
				if (m.getType() == Constant.WRITING) {
					c.setContentType(Constant.WRITING_MATERIAL_TYPE);
					String fileContent = getWritingJsonContent(m);
					c.setContentPath(fileContent);
				} else {
					c.setContentPath(m.getPath());
				}
				contents.add(c);
			} else {// 精准绑定素材
				PlayListReqPContent pContent = new PlayListReqPContent();
				pContent.setContentId(getContentId(m.getId(), m.getType()));
				if (m.getType() == Constant.WRITING) {
					pContent.setContentType(Constant.WRITING_MATERIAL_TYPE);
					String fileContent = getWritingJsonContent(m);
					pContent.setContentPath(fileContent);
				} else {
					pContent.setContentPath(m.getPath());
				}
				if (precises == null) {
					precises = new ArrayList<PlayListReqPrecise>();
				}
				Integer pIndex = this.preciseIsExist(precises, m.getPrecise().getId().intValue());
				if (pIndex == null) {
					//将精准信息转成播出单对应的精准信息
					PlayListReqPrecise p = setPlayListReqPrecise(playListId, m);
					precises.add(p);
					pIndex = precises.size() - 1;
				}
				List<PlayListReqPContent> pContents = precises.get(pIndex).getPContents();
				if (pContents == null) {
					pContents = new ArrayList<PlayListReqPContent>();
				}
				pContents.add(pContent);
				precises.get(pIndex).setPContents(pContents);

			}

		}
		map.put("content", contents);
		if (precises != null) {
			map.put("precise", precises);

		}
		return map;
	}
	
	private Map<String, Object> getPlayListContent(List<MaterialBean> ms, List<PlayListReq> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PlayListReqContent> contents = new ArrayList<PlayListReqContent>();
		List<PlayListReqPrecise> precises = null;
		for (MaterialBean m : ms) {
			for(PlayListReq req : list){
				if(req.getBegin().equals(m.getStartTime())){
					if (m.getPrecise() == null) {// 策略绑定素材
						PlayListReqContent c = new PlayListReqContent();
						c.setContentId(getContentId(m.getId(), m.getType()));
						c.setPlayListId(req.getId());
						if (m.getType() == Constant.WRITING) {
							c.setContentType(Constant.WRITING_MATERIAL_TYPE);
							String fileContent = getWritingJsonContent(m);
							c.setContentPath(fileContent);
						} else {
							c.setContentPath(m.getPath());
						}
						contents.add(c);
					} else {// 精准绑定素材
						PlayListReqPContent pContent = new PlayListReqPContent();
						pContent.setContentId(getContentId(m.getId(), m.getType()));
						if (m.getType() == Constant.WRITING) {
							pContent.setContentType(Constant.WRITING_MATERIAL_TYPE);
							String fileContent = getWritingJsonContent(m);
							pContent.setContentPath(fileContent);
						} else {
							pContent.setContentPath(m.getPath());
						}
						if (precises == null) {
							precises = new ArrayList<PlayListReqPrecise>();
						}
						Integer pIndex = this.preciseIsExist(precises, m.getPrecise().getId().intValue(),req.getId());
						if (pIndex == null) {
							//将精准信息转成播出单对应的精准信息
							PlayListReqPrecise p = setPlayListReqPrecise(req.getId(), m);
							precises.add(p);
							pIndex = precises.size() - 1;
						}
						List<PlayListReqPContent> pContents = precises.get(pIndex).getPContents();
						if (pContents == null) {
							pContents = new ArrayList<PlayListReqPContent>();
						}
						pContents.add(pContent);
						precises.get(pIndex).setPContents(pContents);
		
					}
				}
			}
		}
		map.put("content", contents);
		if (precises != null) {
			map.put("precise", precises);

		}
		return map;
	}


	/**
	 * 将精准信息转成播出单对应的精准信息
	 * @param playListId
	 * @param m
	 * @return
	 */
	private PlayListReqPrecise setPlayListReqPrecise(Integer playListId,
			MaterialBean m) {
		PlayListReqPrecise p = new PlayListReqPrecise();
		p.setPrecisionId(m.getPrecise().getId().intValue());
		p.setAssetSortId(m.getPrecise().getAssetSortId());
		p.setDtvServiceId(m.getPrecise().getDtvChannelId());
		p.setAssetKey(m.getPrecise().getAssetKey());
		p.setPlayListId(playListId);
		p.setProductId(m.getPrecise().getProductId());
		p.setTvnExpression(m.getPrecise().getTvnExpression());
		p.setTvnNumber(m.getPrecise().getTvnNumber());
		p.setType(m.getPrecise().getPrecisetype().intValue());
		p.setUseLevel(m.getPrecise().getPriority().intValue());
		p.setUserArea(m.getPrecise().getUserArea());
		
		if (m.getPrecise().getUserArea2()!=null && !m.getPrecise().getUserArea2().equals("0"))
		{
			p.setUserArea(m.getPrecise().getUserArea2());
		}
		if (m.getPrecise().getUserArea3()!=null && !m.getPrecise().getUserArea3().equals("0"))
		{
			p.setUserArea(m.getPrecise().getUserArea3());
		}
		p.setUserIndustrys(m.getPrecise().getUserindustrys());
		p.setUserLevels(m.getPrecise().getUserlevels());
		p.setAssetId(m.getPrecise().getAssetIds());
		p.setPlaybackServiceId(m.getPrecise().getPlaybackChannelId());
		p.setLookbackCategoryId(m.getPrecise().getLookbackCategoryId());
		return p;
	}

	private Map<String, Object> getPlayListContent(List<MaterialBean> ms,Integer playListId, String playLocation,String beginTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PlayListReqContent> contents = new ArrayList<PlayListReqContent>();
		List<PlayListReqPrecise> precises = null;
		for (MaterialBean m : ms) {
			/** 处理视频插播各位置的素材 */
			String[] pls = m.getPlayLocation().split(",");
			for (String pl : pls) {
				if(beginTime.equals(m.getStartTime())){
					if (pl.equals(playLocation)) {
						if (m.getPrecise() == null) {// 策略绑定素材
	
							PlayListReqContent c = new PlayListReqContent();
							c.setContentId(getContentId(m.getId(), m.getType()));
							c.setPlayListId(playListId);
							if (m.getType() == Constant.WRITING) {
								c.setContentType(Constant.WRITING_MATERIAL_TYPE);
								String jsonContent = getWritingJsonContent(m);
								c.setContentPath(jsonContent);
							} else {
								c.setContentPath(m.getPath());
							}
							contents.add(c);
						} else {// 精准绑定素材
							PlayListReqPContent pContent = new PlayListReqPContent();
							pContent.setContentId(getContentId(m.getId(), m
									.getType()));
							if (m.getType() == Constant.WRITING) {
								pContent.setContentType(Constant.WRITING_MATERIAL_TYPE);
								String jsonContent = getWritingJsonContent(m);
								pContent.setContentPath(jsonContent);
							} else {
								pContent.setContentPath(m.getPath());
							}
							if (precises == null) {
								precises = new ArrayList<PlayListReqPrecise>();
							}
							Integer pIndex = this.preciseIsExist(precises, m.getPrecise().getId().intValue(),playListId);
							if (pIndex == null) {
								//将精准信息转成播出单对应的精准信息
								PlayListReqPrecise p = setPlayListReqPrecise(playListId, m);
								precises.add(p);
								pIndex = precises.size() - 1;
							}
							List<PlayListReqPContent> pContents = precises.get(pIndex).getPContents();
							if (pContents == null) {
								pContents = new ArrayList<PlayListReqPContent>();
							}
							pContents.add(pContent);
							precises.get(pIndex).setPContents(pContents);
	
						}
					}
				}
			}
		}
		map.put("content", contents);
		if (precises != null) {
			map.put("precise", precises);

		}
		return map;
	}

	/** null-不存在，非空-集合索引 */
	private Integer preciseIsExist(List<PlayListReqPrecise> ps, Integer pId) {
		for (int i = 0; i < ps.size(); i++) {
			if (pId.equals(ps.get(i).getPrecisionId())) {
				return i;
			}
		}
		return null;
	}
	
	/** null-不存在，非空-集合索引 */
	private Integer preciseIsExist(List<PlayListReqPrecise> ps, Integer pId, Integer playListId) {
		for (int i = 0; i < ps.size(); i++) {
			if (pId.equals(ps.get(i).getPrecisionId()) && playListId.equals(ps.get(i).getPlayListId())) {
				return i;
			}
		}
		return null;
	}

	

}
