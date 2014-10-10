package com.dvnchina.advertDelivery.order.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.avit.ads.webservice.UploadClient;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.channelGroup.bean.TChannelGroup;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.ImageReal;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.model.ResourceReal;
import com.dvnchina.advertDelivery.model.VideoReal;
import com.dvnchina.advertDelivery.order.bean.AreaResource;
import com.dvnchina.advertDelivery.order.bean.DtvFtpInfo;
import com.dvnchina.advertDelivery.order.bean.Order;
import com.dvnchina.advertDelivery.order.bean.OrderMaterialRelation;
import com.dvnchina.advertDelivery.order.bean.OrderMaterialRelationTmp;
import com.dvnchina.advertDelivery.order.bean.PlayListResource;
import com.dvnchina.advertDelivery.order.bean.PutInPlayListBean;
import com.dvnchina.advertDelivery.order.bean.RequestPlayListBean;
import com.dvnchina.advertDelivery.order.bean.playlist.MaterialBean;
import com.dvnchina.advertDelivery.order.dao.OrderDao;
import com.dvnchina.advertDelivery.order.dao.PlayList4OrderDao;
import com.dvnchina.advertDelivery.order.service.OrderService;
import com.dvnchina.advertDelivery.ploy.bean.Ploy;
import com.dvnchina.advertDelivery.ploy.bean.TPreciseMatch;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo;
import com.dvnchina.advertDelivery.sysconfig.service.BaseConfigService;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.Transform;
import com.google.gson.Gson;

public class OrderServiceImpl implements OrderService{
	
	private OrderDao orderDao = null;
	private PlayList4OrderDao playList4OrderDao;
	private BaseConfigService baseConfigService;
	/**
	 * 分页查询订单信息
	 * @param order
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryOrderList(Order order,int pageNo, int pageSize){
		return orderDao.queryOrderList(order, pageNo, pageSize);
	}
	
	/**
	 * 分页查询审核订单信息
	 * @param order
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryOrderAuditList(Order order,int pageNo, int pageSize){
		return orderDao.queryOrderAuditList(order, pageNo, pageSize);
	}
	
	/**
	 * 根据广告商ID分页查询合同信息
	 * @param customId
	 * @param contract
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryContractList(Integer customId,Contract contract,int pageNo, int pageSize){
		return orderDao.queryContractList(customId, contract, pageNo, pageSize);
	}
	
	/**
	 * 根据合同分页查询广告位信息
	 * @param position
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryPositionList(AdvertPosition position,int pageNo, int pageSize){
		return orderDao.queryPositionList(position, pageNo, pageSize);
	}
	
	/**
	 * 分页查询策略信息
	 * @param ploy
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryPloyList(Ploy ploy,Integer adPackageType,int pageNo, int pageSize){
		return orderDao.queryPloyList(ploy,adPackageType, pageNo, pageSize);
	}
	
	/**
	 * 根据策略ID  ployId获取子策略JSON信息
	 * @param ployId
	 * @return
	 */
	public String getSubPloyJson(Integer ployId){
		List<Ploy> subPloyList = orderDao.getSubPloyListByPloy(ployId);
		Gson gson = new Gson();
		return gson.toJson(subPloyList);
	}
	
	/**
	 * 根据策略ID获取精准json字符串
	 * @param ployId
	 * @return
	 */
	public String queryPrecises4Json(Integer ployId){
		List<TPreciseMatch> preciseList = orderDao.queryPreciseListByPloy(ployId);
		Gson gson = new Gson();
		return gson.toJson(preciseList);
	}
	
	public boolean checkOrderStartTime(String startTime, Date now) {
		Date start;
		try {
			start = Transform.StringtoCalendar(startTime).getTime();
			long time = now.getTime() - start.getTime();
			String aheadTime = baseConfigService.getBaseConfigByCode("orderOpAheadTime");
			int aTime = new Integer(aheadTime);
			if (time > aTime * 1000) {// 订单已执行
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	/**
	 * 查询素材信息
	 * @param resource
	 * @return
	 */
	public List<ResourceReal> queryResourceList(ResourceReal resource){
		return orderDao.queryResourceList(resource);
	}
	
	/**
	 * 查询素材信息
	 * @param ids
	 * @return
	 */
	public List<ResourceReal> getResourceListByIds(String ids){
		return orderDao.getResourceListByIds(ids);
	}
	
	/**
	 * 根据ID获取视频规格
	 * @param id
	 * @return
	 */
	public VideoReal getVideoRealById(Integer id){
		return orderDao.getVideoRealById(id);
	}
	
	/**
	 * 根据ID获取图片规格
	 * @param id
	 * @return
	 */
	public ImageReal getImageRealById(Integer id){
		return orderDao.getImageRealById(id);
	}
	
	/**
	 * 根据日期范围，广告位ID和要排除的订单主键查询订单记录数
	 * 
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @param positionId
	 *            广告位ID
	 * @param orderId
	 *            订单主键
	 * @return 订单记录数
	 */
	public int getOrderNumByDate(String start, String end, Integer positionId,Integer orderId){
		return orderDao.getOrderNumByDate(start, end, positionId, orderId);
	}
	
	
	@Override
	public int getOrderNumByDateAndArea(String start, String end,Integer positionId, Integer orderId) {
		return orderDao.getOrderNumByDateAndArea(start, end, positionId, orderId);
	}

	/**
	 * 判断是否存在响应区域的订单
	 * @param start
	 * @param end
	 * @param positionId
	 * @param orderId
	 * @param ployId
	 * @return
	 */
	public boolean exsiteAreaOrder(String start, String end, Integer positionId,Integer orderId, Integer ployId){
		return orderDao.exsiteAreaOrder(start, end, positionId, orderId, ployId);
	}
	
	/**
	 * 根据区域、时段、频道组判断是否有冲突的订单
	 * @param positionId
	 * @param orderCode
	 * @return
	 */
	public boolean exsiteOrder(String start, String end,Integer positionId,String orderCode){
		return orderDao.exsiteOrder(start, end,positionId, orderCode);
	}
	
	/**
	 * 保存订单
	 * @param order
	 */
	public void saveOrder(Order order,Integer isHD){
		orderDao.save(order);
		saveOrderMaterialRelation(order);
		//this.saveOrderMaterialRelation(order,isHD);
	}
	
	/**
	 * 将临时表中的订单和素材关系添加到正式表
	 * @param order
	 */
	private void saveOrderMaterialRelation(Order order){ 
		//删除原有正式表中的订单与素材关系记录
		orderDao.delOrderMaterialRelation(order.getId());
		//将临时表记录添加到正式表 
		orderDao.saveOrderMaterialRelationFromTmp(order.getId(), order.getOrderCode());
		//删除临时表记录
		orderDao.delOrderMateRelTmp(order.getOrderCode());
	}
	
	/**
	 * 保存订单素材关联关系
	 * @param rel
	 */
	public void saveOrderMaterialRelation(List<OrderMaterialRelation> rel){
		orderDao.saveAll(rel);
	}
	
	/**
	 * 保存订单素材关联关系
	 * 多个精准与素材关系以~分割，
	 * 每个精准与素材关系格式：精准/分策略类型_精准ID#素材列表，素材列表以@分割，
	 * 每个素材中的格式为：素材ID,轮询序号,插播位置
	 * 样例：0_0#10,1,0/3@10,1,1/3@10,1,2/3@12,2,-1@~
	 * @param order
	 * @param isHD
	 */
	public void saveOrderMaterialRelation(Order order,Integer isHD){
		String selResource = order.getSelResource();
//		List<OrderMaterialRelation> materialList = new ArrayList<OrderMaterialRelation>();
//		if(selResource.endsWith("~")){//去掉最后一个~
//			selResource = selResource.substring(0,selResource.length()-1);
//		}
//		String[] matResArray = selResource.split("~");
//		for(String resInfo : matResArray){
//			String[] relsArray = resInfo.split("#");
//			String preciseId = relsArray[0];//精准ID
//			String resStr = relsArray[1];//素材列表
//			String[] relArray = resStr.split("@");
//			for(String rel : relArray){
//				String[] resArray = rel.split(",");
//				
//				OrderMaterialRelation mr = new OrderMaterialRelation();
//				mr.setOrderId(order.getId());
//				mr.setMateId(Integer.valueOf(resArray[0]));
//				if(StringUtils.isNotBlank(resArray[1]) && !resArray[1].equals("-1")){
//					mr.setPollIndex(Integer.valueOf(resArray[1]));
//				}
//				if(StringUtils.isNotBlank(resArray[2]) && !resArray[2].equals("-1")){
//					if(resArray[2].startsWith("0")){
//						//插播位置以0开头，表示在片头
//						mr.setPlayLocation("0");
//					}else{
//						mr.setPlayLocation(resArray[2]);
//					}
//				}
//				mr.setIsHD(isHD);
//				mr.setPreciseId(Integer.valueOf(preciseId));
//				materialList.add(mr);
//			}
//		}
		List<OrderMaterialRelation> materialList = this.getOrderMaterialRelation(selResource);
		for(OrderMaterialRelation rel : materialList){
			rel.setOrderId(order.getId());
			rel.setIsHD(isHD);
		}
		orderDao.saveAll(materialList);
	}
	
	/**
	 * 将订单素材关系转成列表信息
	 * selResource格式：多个精准与素材关系以~分割，
	 * 每个精准与素材关系格式：精准/分策略类型_精准ID#素材列表，素材列表以@分割，
	 * 每个素材中的格式为：素材ID,轮询序号,插播位置
	 * 样例：0_0#10,1,0/3@10,1,1/3@10,1,2/3@12,2,-1@~
	 * @param selResource
	 */
	public List<OrderMaterialRelation> getOrderMaterialRelation(String selResource){
		List<OrderMaterialRelation> materialList = new ArrayList<OrderMaterialRelation>();
		if(selResource.endsWith("~")){//去掉最后一个~
			selResource = selResource.substring(0,selResource.length()-1);
		}
		String[] matResArray = selResource.split("~");
		for(String resInfo : matResArray){
			String[] relsArray = resInfo.split("#");
			String type = relsArray[0].split("_")[0];//精准/分策略类型
			String preciseId = relsArray[0].split("_")[1];//精准ID
			String resStr = relsArray[1];//素材列表
			String[] relArray = resStr.split("@");
			for(String rel : relArray){
				String[] resArray = rel.split(",");
				
				OrderMaterialRelation mr = new OrderMaterialRelation();
//				mr.setOrderId(order.getId());
				mr.setMateId(Integer.valueOf(resArray[0]));
				if(StringUtils.isNotBlank(resArray[1]) && !resArray[1].equals("-1")){
					mr.setPollIndex(Integer.valueOf(resArray[1]));
				}
				if(StringUtils.isNotBlank(resArray[2]) && !resArray[2].equals("-1")){
					if(resArray[2].startsWith("0")){
						//插播位置以0开头，表示在片头
						mr.setPlayLocation("0");
					}else{
						mr.setPlayLocation(resArray[2]);
					}
				}
//				mr.setIsHD(isHD);
				mr.setPreciseId(Integer.valueOf(preciseId));
				mr.setType(Integer.valueOf(type));
				materialList.add(mr);
			}
		}
		return materialList;
	}
	
	/**
	 * 根据订单号删除订单素材关系
	 * @param orderId
	 */
	public void delOrderMaterialRelation(Integer orderId){
		orderDao.delOrderMaterialRelation(orderId);
	}
	
	/**
	 * 根据订单ID获取订单信息
	 * @param id
	 * @return
	 */
	public Order getOrderById(Integer id){
		return orderDao.getOrderById(id);
	}
	
	/**
	 * 修改订单
	 * @param order
	 */
	public void updateOrder(Order order){
		orderDao.update(order);
		saveOrderMaterialRelation(order);
	}
	
	/**
	 * 获取订单与素材关系列表
	 * @param rel
	 * @param positionCode
	 * @return
	 */
	public List<OrderMaterialRelation> getOrderMaterialRelList(OrderMaterialRelation rel,String positionCode){
		
		if("01001".equals(positionCode) || "01002".equals(positionCode)){
			//开机广告位ID
			return orderDao.getBootOrderMaterialRelList(rel);
		}else{
			return orderDao.getOrderMaterialRelList(rel);
		}
	}
	
	/**
	 * 根据播出单恢复订单,同时更新订单状态和操作员
	 * */
	public void restoreOrder(PutInPlayListBean playList, String state,Integer userId){
		this.restorePutInOrderMaterial(playList);
		Order o = (Order) orderDao.get(Order.class, playList.getOrderId());
		o.setPloyId(playList.getPloyId());
		o.setContractId(playList.getContractId());
		o.setPositionId(playList.getPositionId());
		o.setStartTime(this.getYMD(playList.getStartTime()));
		o.setEndTime(this.getYMD(playList.getEndTime()));
		o.setOrderType(Constant.PUT_IN_ORDER);
		if (state != null) {
			o.setState(state);
			o.setOperatorId(userId);
		}
		orderDao.update(o);
	}
	
	/**
	 * 恢复投放式订单素材关系记录
	 * */
	private void restorePutInOrderMaterial(PutInPlayListBean playList) {	
		
		String isHD = "0";
		Map<String, String> map = this.getIdentificationContent(playList.getIdentification());
		if (map.get("isHD") != null) {
			isHD = map.get("isHD");
		}
		String contentType = playList.getContentType();
		Integer orderId = playList.getOrderId();
		Integer[] cIds = this.getResourceId(playList.getContentId(), contentType);
		List<OrderMaterialRelation> relList = new ArrayList<OrderMaterialRelation>();
		for (int i = 0; i < cIds.length; i++) {
			OrderMaterialRelation rel = new OrderMaterialRelation();
			rel.setIsHD(Integer.valueOf(isHD));
			rel.setOrderId(orderId);
			rel.setPreciseId(0);
			// 投放式广告位没有叠加，只有轮询
			if ("2".equals(contentType)) {
				rel.setPollIndex(i);
			}
			rel.setMateId(cIds[i]);
			relList.add(rel);
		}
		orderDao.delOrderMaterialRelation(orderId);
		orderDao.saveAll(relList);
	}
	
	/**
	 * 根据特征值获取广告位高标清属性和插播位置
	 * */
	private Map<String, String> getIdentificationContent(String identification) {
		Map<String, String> map = new HashMap<String, String>();
		String[] strs = identification.split(",");
		if (strs.length >= 1) {
			String isHD = "0";
			if ("HD".equals(strs[0])) {
				isHD = "1";
			}
			map.put("isHD", isHD);
		}
		if (strs.length == 3) {
			map.put("instream", strs[1]);
		}
		return map;
	}
	
	/**
	 * 根据播出单内容类型和内容id获取资源id数组
	 * */
	private Integer[] getResourceId(String contentId, String contentType) {
		String[] cIds = contentId.split(",");
		Integer[] ids = new Integer[cIds.length];
		if (StringUtils.isNotBlank(contentType)) {
			if (Constant.INIT_MATERIAL_TYPE.equals(contentType)) {// 开机素材
				for (int i = 0; i < cIds.length; i++) {
					if (cIds[i].startsWith(Constant.INIT_IMAGE_PREFIX)) {
						cIds[i] = cIds[i].replace(Constant.INIT_IMAGE_PREFIX,
								"");
						ids[i] = new Integer(cIds[i]);
					} else if (cIds[i].startsWith(Constant.INIT_VIDEO_PREFIX)) {
						cIds[i] = cIds[i].replace(Constant.INIT_VIDEO_PREFIX,
								"");
						ids[i] = new Integer(cIds[i]);
					}
				}
			} else if (Constant.MULTI_IMAGE_MATERIAL_TYPE.equals(contentType)) {// 多图素材
				for (int i = 0; i < cIds.length; i++) {
					if (cIds[i].startsWith(Constant.IMAGE_PREFIX)) {
						cIds[i] = cIds[i].substring(0,cIds[i].indexOf("_")).replace(Constant.IMAGE_PREFIX, "");
						ids[i] = new Integer(cIds[i]);
					} else if (cIds[i].startsWith(Constant.VIDEO_PREFIX)) {
						cIds[i] = cIds[i].substring(0,cIds[i].indexOf("_")).replace(Constant.VIDEO_PREFIX, "");
						ids[i] = new Integer(cIds[i]);
					}
				}
			} else if (Constant.WRITING_MATERIAL_TYPE.equals(contentType)) {// 字幕素材
				for (int i = 0; i < cIds.length; i++) {
					if (cIds[i].startsWith(Constant.WRITING_PREFIX)) {
						cIds[i] = cIds[i].replace(Constant.WRITING_PREFIX, "");
						ids[i] = new Integer(cIds[i]);
					}
				}
			}
		} else {// 单个或叠加素材
			for (int i = 0; i < cIds.length; i++) {
				if (cIds[i].startsWith(Constant.IMAGE_PREFIX)) {
					cIds[i] = cIds[i].replace(Constant.IMAGE_PREFIX, "");
					ids[i] = new Integer(cIds[i]);
				} else if (cIds[i].startsWith(Constant.VIDEO_PREFIX)) {
					cIds[i] = cIds[i].replace(Constant.VIDEO_PREFIX, "");
					ids[i] = new Integer(cIds[i]);
				} else if (cIds[i].startsWith(Constant.QUESTIONNAIRE_PREFIX)) {
					cIds[i] = cIds[i]
							.replace(Constant.QUESTIONNAIRE_PREFIX, "");
					ids[i] = new Integer(cIds[i]);
				}
			}
		}
		return ids;
	}
	
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
	
	/**
	 * 根据播出单恢复订单,同时更新订单状态和造作员
	 * */
	public void restoreOrder(List<RequestPlayListBean> playList, String state, Integer userId){
		Integer orderId = playList.get(0).getOrderId();
		List<OrderMaterialRelation> relList = new ArrayList<OrderMaterialRelation>();
		for (int i = 0; i < playList.size(); i++) {
			String identification = playList.get(i).getIdentification();
			List<PlayListResource> r = playList.get(i).getResources();
			List<OrderMaterialRelation> rs = this.getRequestOrderMaterial(orderId, identification, r);
			if (rs != null && rs.size() > 0) {
				relList.addAll(rs);
			}
		}
		
		orderDao.delOrderMaterialRelation(orderId);
		orderDao.saveAll(relList);

		Order o = (Order) orderDao.get(Order.class, orderId);
		o.setPloyId(playList.get(0).getPloyId());
		o.setContractId(playList.get(0).getContractId());
		o.setPositionId(playList.get(0).getPositionId());
		o.setStartTime(playList.get(0).getStartDate());
		o.setEndTime(playList.get(0).getEndDate());
		o.setOrderType(Constant.REQUEST_ORDER);
		if (state != null) {
			o.setState(state);
			o.setOperatorId(userId);
		}
		orderDao.update(o);
	}
	
	/**
	 * 通过播出单记录获取订单素材关系集合
	 * */
	private List<OrderMaterialRelation> getRequestOrderMaterial(Integer orderId, String identification, List<PlayListResource> rs) {
		List<OrderMaterialRelation> relList = new ArrayList<OrderMaterialRelation>();
		String isHD = "0";
		String instream = null;
		Map<String, String> map = this.getIdentificationContent(identification);
		if (map.get("isHD") != null) {
			isHD = map.get("isHD");
		}
		if (map.get("instream") != null) {
			instream = map.get("instream");
		}
		for (PlayListResource r : rs) {
			Integer[] rIds = this.getResourceId(r.getContentId(), r
					.getContentType());
			for (int i = 0; i < rIds.length; i++) {
				OrderMaterialRelation rel = new OrderMaterialRelation();
				rel.setIsHD(Integer.valueOf(isHD));
				if (StringUtils.isNotBlank(instream)) {
					rel.setPlayLocation(instream);
				}
				rel.setPreciseId(r.getPreciseId());
				rel.setOrderId(orderId);
				rel.setMateId(rIds[i]);
				if ("2".equals(r.getContentType())) {
					rel.setPollIndex(i);
				}
				relList.add(rel);
			}
		}
		return relList;
	}
	
	/**
	 * 根据订单ID查询订单列表信息
	 * @param ids
	 * @return
	 */
	public List<Order> findOrderListByIds(String ids){
		String[] idsArray = ids.split(",");
		String idsStr = "";
		for(String id : idsArray){
			idsStr = idsStr+id.trim()+",";
		}
		if(idsStr.length()>0){
			idsStr = idsStr.substring(0,idsStr.length()-1);
		}
		return orderDao.findOrderListByIds(idsStr);
	}
	
	/**
	 * 根据订单号删除订单及关系记录
	 * @param ids
	 */
	public void deleteOrderByIds(String ids){
		orderDao.deleteRelationByIds(ids);
		orderDao.deleteOrderByIds(ids);
	}
	
	/**
	 * 根据订单id修改订单状态
	 * @param orderId
	 * @param orderState
	 * @param userId
	 */
	public void updateOrderState(Integer orderId, String orderState,Integer userId){
		orderDao.updateOrderState(orderId, orderState, userId);
	}
	
	/**
	 * 根据广告位类型获取图片和视频素材文件总大小
	 * @param positionType 广告位类型
	 * @param order  订单对象
	 * @param mateIds  素材IDS
	 * @return
	 */
	public long getFileSumSize(int positionType,Order order,String mateIds){
		
		long imageSize = orderDao.getImageFileSumSize(positionType,order,mateIds);
//		long videoSize = orderDao.getVideoFileSumSize(positionType,order);
//		return imageSize+videoSize;
		return imageSize;
	}
	
	/**
	 * 针对DTV订单，需要将素材FTP到DTV服务器:
	 * 策略对应的素材FTP到所有地市，
	 * 精准素材发送到精准对应的地市。
	 */
	public void ftp2DTVServer(Integer orderId){
		List<DtvFtpInfo> ftpList = orderDao.getDtvFtpInfoList(orderId);
		UploadClient client  = new UploadClient();
		String dtvTargetPath = baseConfigService.getBaseConfigByCode("dtvTargetPath");;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		for(DtvFtpInfo ftp : ftpList){
			if(ftp.getType().intValue() == 1){
				//策略对应的素材FTP到所有地市
				//List<ReleaseArea> areaList = orderDao.getAreaList();
				//for(ReleaseArea area : areaList){
					/**
					 * 发送策略视频文件FTP到DTV服务器
					 */
				    
				    String outdtvpath = dtvTargetPath+File.separator+ftp.getStartdate()+"-"+ftp.getEnddate();
				    outdtvpath =outdtvpath+" "+ftp.getStarttime()+"-"+ftp.getEndtime();
				    outdtvpath=outdtvpath.replace(":", "");
					client.sendFileDTV("0", ftp.getFilePath(), outdtvpath);
				//}
			}else{
				String[] ids = ftp.getAreaIds().split(",");
				for(String id : ids){
					/**
					 * 发送精准视频文件FTP到DTV服务器
					 */
					client.sendFileDTV(id, ftp.getFilePath(), dtvTargetPath);
				}
			}
		}
	}
	
	/**
	 * 根据播出单恢复状态为[修改待审核]或[修改审核不通过]）的订单
	 * @param ids  订单ID集合
	 * */
	public void restoreOrder(List<Integer> ids){
		String idsStr = "";
		for (Integer id : ids) {
			idsStr = idsStr+id.intValue()+",";
		}
		if(idsStr.length()>1){
			idsStr = idsStr.substring(0,idsStr.length()-1);
			List<Order> orderList = orderDao.findRestoreOrderByIds(idsStr);
			if(orderList != null && orderList.size()>0){
				PutInPlayListBean pBean = null;
				List<RequestPlayListBean> rBeans = null;
				List<OrderMaterialRelation> or = new ArrayList<OrderMaterialRelation>();
				List<Order> updateOrderList = new ArrayList<Order>();
				for(Order o : orderList){
					pBean = playList4OrderDao.getPutInPlayListByOrderId(o.getId());
					if (pBean != null) {
						this.setUpdateOrderList(pBean, o,updateOrderList, or);
					} else {
						rBeans = playList4OrderDao.getRequestPlayListByOrderId(o.getId());
						this.setUpdateOrderList(rBeans, o,updateOrderList, or);
					}
				}
				orderDao.saveAll(updateOrderList);
				orderDao.saveAll(or);
			}
		}
	}
	
	/**
	 * 设置投放式播出单对应的订单修改对象
	 * @param playList
	 * @param o
	 * @param updateOrderList
	 * @param or
	 */
	private void setUpdateOrderList(PutInPlayListBean playList,Order o, List<Order> updateOrderList,List<OrderMaterialRelation> or) {
		this.setRestorePutInOrderMaterial(playList,or);
		o.setPloyId(playList.getPloyId());
		o.setContractId(playList.getContractId());
		o.setPositionId(playList.getPositionId());
		o.setStartTime(this.getYMD(playList.getStartTime()));
		o.setEndTime(this.getYMD(playList.getEndTime()));
		o.setOrderType(Constant.PUT_IN_ORDER);
		updateOrderList.add(o);

	}
	
	/**
	 * 设置恢复投放式订单素材关系记录
	 * */
	private void setRestorePutInOrderMaterial(PutInPlayListBean playList,List<OrderMaterialRelation> or) {	
		
		String isHD = "0";
		Map<String, String> map = this.getIdentificationContent(playList.getIdentification());
		if (map.get("isHD") != null) {
			isHD = map.get("isHD");
		}
		String contentType = playList.getContentType();
		Integer orderId = playList.getOrderId();
		Integer[] cIds = this.getResourceId(playList.getContentId(), contentType);
		for (int i = 0; i < cIds.length; i++) {
			OrderMaterialRelation rel = new OrderMaterialRelation();
			rel.setIsHD(Integer.valueOf(isHD));
			rel.setOrderId(orderId);
			rel.setPreciseId(0);
			// 投放式广告位没有叠加，只有轮询
			if ("2".equals(contentType)) {
				rel.setPollIndex(i);
			}
			rel.setMateId(cIds[i]);
			or.add(rel);
		}
		
		orderDao.delOrderMaterialRelation(orderId);
	}
	
	/**
	 * 设置请求式播出单对应的订单修改对象
	 * @param playList
	 * @param o
	 * @param updateOrderList
	 * @param or
	 */
	private void setUpdateOrderList(List<RequestPlayListBean> playList,Order o,List<Order> updateOrderList,List<OrderMaterialRelation> or) {
		Integer orderId = playList.get(0).getOrderId();
		for (int i = 0; i < playList.size(); i++) {
			String identification = playList.get(i).getIdentification();
			List<PlayListResource> r = playList.get(i).getResources();
			List<OrderMaterialRelation> rs = this.getRequestOrderMaterial(
					orderId, identification, r);
			if (rs != null && rs.size() > 0) {
				or.addAll(rs);
			}
		}
		orderDao.delOrderMaterialRelation(orderId);

		o.setPloyId(playList.get(0).getPloyId());
		o.setContractId(playList.get(0).getContractId());
		o.setPositionId(playList.get(0).getPositionId());
		o.setStartTime(playList.get(0).getStartDate());
		o.setEndTime(playList.get(0).getEndDate());
		o.setOrderType(Constant.REQUEST_ORDER);
		updateOrderList.add(o);
	}
	
	/**
	 * 将执行完毕的订单状态设置为”执行完毕“
	 * 
	 * @param ids
	 *            订单ID集合
	 * */
	public void setOrderFinished(List<Integer> ids){
		orderDao.updateOrdersState(ids, Constant.ORDER_IS_FINISHED);
	}
	
	
	/**
	 * @description: 首页代办获取待审批的订单的总数
	 * @return 待审批的订单的总数
	 */
	public int getWaitingAuditOrderCount(String ids){
		return orderDao.getWaitingAuditOrderCount(ids);
	}
	
	/**
	 * 根据素材ID获取素材信息
	 * @param id
	 * @return
	 */
	public String getMaterialJsonById(Integer id){
		MaterialBean mate = orderDao.getMaterialById(id);
		Gson gson = new Gson();
		return gson.toJson(mate);
	}
	/**
	 * 根据订单ID获取已选素材信息
	 * @param id
	 * @return
	 */
	public String getSelectMaterialJsonByOrderId(String id){
		List<AreaResource> areaResource = orderDao.getSelectMaterialJsonByOrderId(id);
		Gson gson = new Gson();
		return gson.toJson(areaResource);
	}
	/**
	 * 根据策略ID查询策略信息
	 * @param ployId
	 * @return
	 */
	public List<Ploy> getPloyByPloyId(Integer ployId){
		return orderDao.getPloyByPloyId(ployId);
	}
	
	/**
	 * 根据频道组ID获取频道serviceId列表
	 * @param groupId
	 * @param channel
	 * @return
	 */
	public List<String> getServiceIdList(Integer groupId,ChannelInfo channel){
		return orderDao.getServiceIdList(groupId,channel);
	}
	
	/**
	 * 根据策略IDS  策略列表
	 * @param ids
	 * @return
	 */
	public List<Ploy> getPloyListByIds(String ids){
		return orderDao.getPloyListByIds(ids);
	}
	
	/**
	 * 根据策略ID获取策略
	 * @param id
	 * @return
	 */
	public Ploy getPloyById(Integer id){
		return (Ploy)orderDao.get(Ploy.class, id);
	}
	
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void setPlayList4OrderDao(PlayList4OrderDao playList4OrderDao) {
		this.playList4OrderDao = playList4OrderDao;
	}

	public void setBaseConfigService(BaseConfigService baseConfigService) {
		this.baseConfigService = baseConfigService;
	}
	
	/**
	 * 根据广告位和位移天数查找空档订单日期
	 * @param positionIds
	 * @param shiftDate
	 * @return
	 */
	public List<String> getFreePositionRemindOrders(String positionIds, List<AdvertPosition> listAP, Date today, Date  shiftDate){
		List<String> lstFreePosition = new ArrayList<String>();
		List<Order> lstOrder = orderDao.getFreePositionRemindOrders(positionIds,today, shiftDate);
		int tempPosition = 0;
		Map<String, List<Order>> mapGroupOrder = new HashMap<String, List<Order>>();
		
		List<Order> list = null;
		for (int i = 0, j = lstOrder.size(); i < j; i++) {
			Order order = lstOrder.get(i);
			if(tempPosition != order.getPositionId()){
				if(list != null){
					mapGroupOrder.put(tempPosition+"", list);
				}
				list = new ArrayList<Order>();
			}
			list.add(order);
			if(i == j -1){
				mapGroupOrder.put(order.getPositionId()+"", list);
			}
			tempPosition = order.getPositionId();
		}
		for (AdvertPosition ap : listAP) {
			Integer id = ap.getId();
			List<Order> lstGroup = mapGroupOrder.get(id.toString());
			if(lstGroup == null || lstGroup.size() <= 0){
				lstFreePosition.add(ap.getPositionName());
				continue;
			}
			boolean judgeShift = getWhetherHaveFreePosition(lstGroup, shiftDate, today);
			if(judgeShift){
				lstFreePosition.add(ap.getPositionName());
			}
		}
		return lstFreePosition;
	}
	
	/**
	 * 根据广告位和位移天数查找空档订单日期（广告商）
	 * @param positionIds
	 * @param shiftDate
	 * @param today
	 * @return List<Object[]>
	 */
	public List<Object[]> getCustomerPositions(Integer customerId, Date today, Date shiftDate){
		List<Object[]> lstPosition = orderDao.getCustomerPositions(customerId, today, shiftDate);
		return lstPosition;
	}
	
	/**
	 * 获取广告商空档广告位
	 * @param positionIds
	 * @param contractIds
	 * @param listAP
	 * @param mapPostion
	 * @param today
	 * @param shiftDate
	 * @return List<String>
	 */
	public List<String> getCustomerFreePositions(String positionIds, String contractIds,
			List<AdvertPosition> listAP, Map<String, Object[]> mapPostion , Date today, Date shiftDate){
		List<String> lstFreePosition = new ArrayList<String>();
		List<Order> lstOrder = orderDao.getCustomerFreePositionRemindOrders(positionIds,today, shiftDate, contractIds);
		int tempPosition = 0;
		Map<String, List<Order>> mapGroupOrder = new HashMap<String, List<Order>>();
		
		List<Order> list = null;
		for (int i = 0, j = lstOrder.size(); i < j; i++) {
			Order order = lstOrder.get(i);
			if(tempPosition != order.getPositionId()){
				if(list != null){
					mapGroupOrder.put(tempPosition+"", list);
				}
				list = new ArrayList<Order>();
			}
			list.add(order);
			if(i == j -1){
				mapGroupOrder.put(order.getPositionId()+"", list);
			}
			tempPosition = order.getPositionId();
		}
		for (AdvertPosition p : listAP) {
			Integer id = p.getId();
			List<Order> lstGroup = mapGroupOrder.get(id.toString());
			if(lstGroup == null || lstGroup.size() <= 0){
				lstFreePosition.add(p.getPositionName());
				continue;
			}
			Object[] obj = mapPostion.get(String.valueOf(id));
			boolean judgeShift = this.whetherHavaCustomerFreePosition(lstGroup, shiftDate, today, obj);
			if(judgeShift){
				lstFreePosition.add(p.getPositionName());
			}
		}
		return lstFreePosition;
	}
	
	/**
	 * 查看广告商是否有空档广告位
	 * @param lstGroup
	 * @param shiftDate
	 * @param today
	 * @param mapPostion
	 * @return boolean
	 */
	private boolean whetherHavaCustomerFreePosition(List<Order> lstGroup,
			Date shiftDate, Date today, Object[] mapPostion) {
		boolean flag = false;
		Date tempStart = null;
		for (int j = lstGroup.size() -1, i = j; i >= 0; i--) {
			
			Order o = lstGroup.get(i);
			Date[] d = this.getStandardRangeDate(today, shiftDate, mapPostion);
			if(!judgeTwoDayMarginOne(d[1], o.getEndTime())){
				flag = true;
				break;
			}
			
			if(!judgeTwoDayMarginOne(o.getStartTime(), d[0])){
				flag = true;
				break;
			}
			if(i < j){
				boolean ma = judgeTwoDayMarginOne(tempStart, o.getEndTime());
				if(!ma){
					flag = true;
					break;
				}
			}
			tempStart = o.getStartTime();
		}
		return flag;
	}

	/**
	 * 根据合同有效期获取标准的时间范围
	 * @param today
	 * @param shiftDate
	 * @param mapPostion
	 * @return Date[2]
	 */
	private Date[] getStandardRangeDate(Date today, Date shiftDate,
			Object[] mapPostion) {
		Date[] date = new Date[2];
		Date date1 = (Date)mapPostion[1];
		Date date2 = (Date)mapPostion[2];
		if(today.before(date1) && shiftDate.after(date2)){
			date[0] = setBeginDateTime(date1);
			date[1] = setShiftDateTime(date2);
		}else if(today.after(date1) && today.before(date2) && shiftDate.after(date2)){
			date[0] = today;
			date[1] = setShiftDateTime(date2);
		}else if(today.before(date1) && shiftDate.before(date2) && shiftDate.after(date1)){
			date[0] = setBeginDateTime(date1);
			date[1] = shiftDate;
		}else{
			date[0] = today;
			date[1] = shiftDate;
		}
		
		return date;
	}

	/**
	 * 查看运营商是否有空档广告位
	 * @param list
	 * @param shiftDate
	 * @param today
	 * @return
	 */
	private boolean getWhetherHaveFreePosition(List<Order> list, Date shiftDate, Date today){
		boolean flag = false;
		Date tempStart = null;
		for (int j = list.size() -1, i = j; i >= 0; i--) {
			if(!judgeTwoDayMarginOne(shiftDate, list.get(i).getEndTime())){
				flag = true;
				break;
			}
			if(!judgeTwoDayMarginOne(list.get(j).getStartTime(), today)){
				flag = true;
				break;
			}
			if(i < j){
				boolean ma = judgeTwoDayMarginOne(tempStart, list.get(i).getEndTime());
				if(!ma){
					flag = true;
					break;
				}
			}
			tempStart = list.get(i).getStartTime();
		}
		
		return flag;
	}
	
	/**
	 * 判断两个时间之间是否小于24小时，一个时间必须为23:59:59或者00:00:00
	 * @param date1
	 * @param date2
	 * @return
	 */
	private boolean judgeTwoDayMarginOne(Date date1, Date date2){
		long nd = 1000*24*60*60;//一天的毫秒数
		double margin = (double)date1.getTime() - (double)date2.getTime();
		double deffer = margin/nd;
		if(deffer <= 1){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 设置判断的结束时间的Time 为 23:59:59
	 * @param date
	 * @return
	 */
	private Date setShiftDateTime(Date date){
		Calendar calendar = GregorianCalendar.getInstance();
	    calendar.setTimeInMillis(date.getTime());
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
	}
	
	/**
	 * 设置判断的开始时间Time为00:00:00
	 * @param date
	 * @return
	 */
	private Date setBeginDateTime(Date date){
		Calendar calendar = GregorianCalendar.getInstance();
    	calendar.setTimeInMillis(date.getTime());
    	calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND,59);
        return calendar.getTime();
	}

	/**
	 * 获取问卷订单已请求的记录数
	 * @param orderId
	 * @return
	 */
	public int getQuestionnaireCount(Integer orderId){
		return orderDao.getQuestionnaireCount(orderId);
	}
	
	/**
	 * 保存问卷订单在代办中已阅记录
	 * @param orderId
	 */
	public void saveRealQuestionnaire(Integer orderId){
		orderDao.saveRealQuestionnaire(orderId);
	}
	/**
	 * 根据策略ID获取NPVR频道组列表
	 * @param ployId
	 * @return
	 */
	public List<TChannelGroup> getNPVRChannelGroupListByPloyId(Integer ployId){
		
		return orderDao.getNPVRChannelGroupListByPloyId(ployId);
	}
	/**
	 * 根据策略ID获取频道组列表
	 * @param ployId
	 * @return
	 */
	public List<TChannelGroup> getChannelGroupListByPloyId(Integer ployId){
		
		return orderDao.getChannelGroupListByPloyId(ployId);
	}
	
	/**
	 * 查询订单可选素材列表信息
	 * @param areaResource
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryNPVRAreaResourceList(OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize){
		return orderDao.queryNPVRAreaResourceList(omRelTmp, pageNo, pageSize);
	}
	
	/**
	 * 查询订单可选素材列表信息
	 * @param areaResource
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryAreaResourceList(OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize){
		return orderDao.queryAreaResourceList(omRelTmp, pageNo, pageSize);
	}
	
	
	
	@Override
	public PageBeanDB queryRadioResourceList(OrderMaterialRelationTmp omRelTmp,int pageNo, int pageSize) {
		return orderDao.queryRadioResourceList(omRelTmp, pageNo, pageSize);
	}

	@Override
	public PageBeanDB queryBootResourceDetailList(OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize) {
		return orderDao.queryBootResourceDetailList(omRelTmp, pageNo, pageSize);
	}

	/**
	 * 查询订单可选素材列表信息 区域在策略中
	 * @param areaResource
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryTheAreaResourceList(OrderMaterialRelationTmp omRelTmp,
			Integer ployId, int pageNo, int pageSize){
		return orderDao.queryTheAreaResourceList(omRelTmp,ployId, pageNo, pageSize);
	}
	
	
	
	@Override
	public PageBeanDB queryBootPicResourceList(OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize) {
		return orderDao.queryBootPicResourceList(omRelTmp, pageNo, pageSize);
	}

	/**
	 * 查询回看菜单广告订单可选素材列表信息
	 * @param omRelTmp
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryLookResourceList(OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize){
		return orderDao.queryLookResourceList(omRelTmp, pageNo, pageSize);
	}
	/**
	 * 查询点播随片广告订单可选素材列表信息
	 * @param omRelTmp
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryLookResourceListbyPre(OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize){
		return orderDao.queryLookResourceListbyPre(omRelTmp, pageNo, pageSize);
	}
	
	/**
	 * 查询插播广告订单可选素材列表信息
	 * @param omRelTmp
	 * @param positionId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryInstreamResourceList(OrderMaterialRelationTmp omRelTmp,int positionId, int pageNo, int pageSize){
		return orderDao.queryInstreamResourceList(omRelTmp,positionId, pageNo, pageSize);
	}
	
	/**
	 * 查询暂停广告订单可选素材列表信息
	 * @param omRelTmp
	 * @param positionId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryPauseResourceList(OrderMaterialRelationTmp omRelTmp,int positionId, int pageNo, int pageSize){
		return orderDao.queryPauseResourceList(omRelTmp,positionId, pageNo, pageSize);
	}
	
	/**
	 * 查询请求式广告订单可选素材列表信息
	 * @param omRelTmp
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryReqResourceList(OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize){
		return orderDao.queryReqResourceList(omRelTmp, pageNo, pageSize);
	}
	
	/**
	 * 查询订单可选素材列表信息
	 * @param areaResource
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB getSelectedResource(AreaResource areaResource, int pageNo, int pageSize){
		return orderDao.getSelectedResource(areaResource, pageNo, pageSize);
	}
	
	/**
	 * 添加订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 */
	public void insertOrderMateRelTmp(String orderCode,int ployId,int positionId){
		orderDao.insertOrderMateRelTmp(orderCode, ployId,positionId);
	}
	public void insertOrderMateRelTmp2(String orderCode,int ployId,int positionId){
		orderDao.insertOrderMateRelTmp2(orderCode, ployId,positionId);
	}
	
	/**
	 * 修改全时段的开始、结束时段
	 */
	public void updateAllTimeOrderMateRelTmp(){
		orderDao.updateAllTimeOrderMateRelTmp();
	}
	
	/**
	 * 添加回看回放菜单广告订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 */
	public void insertLookBackOrderMateRelTmp(String orderCode,int ployId,int positionId){
		orderDao.insertLookBackOrderMateRelTmp(orderCode, ployId,positionId);
	}
	public void insertLookBackOrderMateRelTmp2(String orderCode,int ployId,int positionId){
		orderDao.insertLookBackOrderMateRelTmp2(orderCode, ployId,positionId);
	}
	
	/**
	 * 添加回看回放菜单广告订单和素材临时关系数据 点播随片
	 * @param orderCode
	 * @param ployId
	 */
	public void insertFollowOrderMateRelTmp(String orderCode,int ployId,int positionId){
		orderDao.insertFollowOrderMateRelTmp(orderCode, ployId,positionId);
	}
	public void insertFollowOrderMateRelTmp2(String orderCode,int ployId,int positionId){
		orderDao.insertFollowOrderMateRelTmp2(orderCode, ployId,positionId);
	}
	
	
	/**
	 * 添加回放菜单广告订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 */
	public void insertLookRepalyOrderMateRelTmp(String orderCode,int ployId,int positionId){
		orderDao.insertLookRepalyOrderMateRelTmp(orderCode, ployId,positionId);
	}
	public void insertLookRepalyOrderMateRelTmp2(String orderCode,int ployId,int positionId){
		orderDao.insertLookRepalyOrderMateRelTmp2(orderCode, ployId,positionId);
	}
	/**
	 * 添加插播广告订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 * @param instreamNumber
	 */
	public void insertInstreamOrderMateRelTmp(String orderCode,int ployId,int instreamNumber){
		orderDao.insertInstreamOrderMateRelTmp(orderCode, ployId,instreamNumber);
	}
	public void insertInstreamOrderMateRelTmp2(String orderCode,int ployId,int instreamNumber){
		orderDao.insertInstreamOrderMateRelTmp2(orderCode, ployId,instreamNumber);
	}
	/**
	 * 添加回看回放暂停广告订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 */
	public void insertPauseOrderMateRelTmp(String orderCode,int ployId){
		orderDao.insertPauseOrderMateRelTmp(orderCode, ployId);
	}
	public void insertPauseOrderMateRelTmp2(String orderCode,int ployId){
		orderDao.insertPauseOrderMateRelTmp2(orderCode, ployId);
	}
	
	/**
	 * 添加请求式广告订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 */
	public void insertReqOrderMateRelTmp(String orderCode,int ployId){
		orderDao.insertReqOrderMateRelTmp(orderCode, ployId);
	}
	public void insertReqOrderMateRelTmp2(String orderCode,int ployId){
		orderDao.insertReqOrderMateRelTmp2(orderCode, ployId);
	}
	
	
	
	
	@Override
	public void insertBootOrderMateRelTmp(String orderCode, int ployId) {
		orderDao.insertBootOrderMateRelTmp(orderCode, ployId);
		
	}
	public void insertBootOrderMateRelTmp2(String orderCode, int ployId) {
		orderDao.insertBootOrderMateRelTmp2(orderCode, ployId);
		
	}

	/**
	 * 轮询菜单图片广告位中的订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 * @param loops
	 */
	public void loopMenuPosition(String orderCode,int ployId,int loops){
		orderDao.loopMenuPosition(orderCode, ployId, loops);
	}
	public void loopMenuPosition2(String orderCode,int ployId,int loops){
		orderDao.loopMenuPosition2(orderCode, ployId, loops);
	}
	
	/**
	 * 保存订单和素材临时数据
	 * @param ids
	 * @param mateId
	 */
	public void saveOrderMateRelTmp(String ids, Integer mateId){
		orderDao.saveOrderMateRelTmp(ids, mateId);
	}
	
	
	@Override
	public void saveBootOrderMateRelTmp(String orderCode, String selectedAreas, String materLocation) {
		if(StringUtils.isNotBlank(materLocation)){
			//默认素材
			if(materLocation.endsWith(":")){
				Integer mateId = Integer.parseInt(materLocation.substring(0, materLocation.length()-1 ));
				orderDao.saveDefaultBootOrderMateRelTmp(mateId, orderCode, selectedAreas);
			}
			//24张素材
			else{
				String[] materRefs = materLocation.split("-");
				for(String materRef : materRefs){
					String[] twoProp = materRef.split(":");
					Integer mateId = Integer.parseInt(twoProp[0]);
					String locations = twoProp[1];
					orderDao.saveBootOrderMateRelTmp(mateId, orderCode, locations, selectedAreas);
				}
			}			
		}
		
	}
	public void saveBootOrderMateRelTmp2(String orderCode, String selectedAreas, String materLocation) {

			//默认素材
		
				orderDao.saveDefaultBootOrderMateRelTmp(null, orderCode, selectedAreas);
			
		
		
	}

	/**
	 * 查询订单和素材临时关系数据
	 * @param orderCode
	 * @return
	 */
	public List<OrderMaterialRelationTmp> getOrderMaterialRelationTmpList(String orderCode){
		return orderDao.getOrderMaterialRelationTmpList(orderCode);
	}

	public String queryResourcePath(String metaId) {
		String mageMeta = orderDao.queryResourcePath2(metaId);
		ConfigureProperties config = ConfigureProperties.getInstance();
//		String ip=config.getValueByKey("ftp.ip");
//		String port=config.getValueByKey("ftp.port");
//		String userName=config.getValueByKey("ftp.username");
//		String psw=config.getValueByKey("ftp.password");		
//		String path="ftp://"+userName+":"+psw+"@"+ip+":"+port+mageMeta;
	   String ip=config.getValueByKey("ftp.ip");
       String vpath=config.getValueByKey("materila.ftp.realPath");
       vpath=vpath.substring(5, vpath.length());
       String path="http://"+ip+vpath+"/"+mageMeta;
		Gson gson = new Gson();
		return gson.toJson(path);
	}

	/*
	 *校验菜单图片是否6张轮询素材都已经绑定好了
	 */
	@Override
	public boolean validateLoopData(String orderCode) {
		List resultList = orderDao.getSelectedLoopPicNumList(orderCode);
		if(null != resultList){
			for(Object obj : resultList){
				int num = ((BigInteger)obj).intValue();
				if(6 != num){
					return false;
				}
			}
		}
		return true;
	}
	
	
	/*
	 *验证订单是否绑定素材,false有素材
	 */
	@Override
	public boolean valiIsHasSuCai(String orderCode) {
		List resultList = orderDao.valiIsHasSuCai(orderCode);
		if(null != resultList){
			for(Object obj : resultList){
				int num = ((BigInteger)obj).intValue();
				if(num > 0){
					return false;
				}
			}
		}
		return true;
	}
	
	
	
	
	
	
	
	
}
