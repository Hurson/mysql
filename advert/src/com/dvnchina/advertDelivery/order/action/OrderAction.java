package com.dvnchina.advertDelivery.order.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.channelGroup.bean.TChannelGroup;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.log.bean.AuditLog;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.service.AuditLogService;
import com.dvnchina.advertDelivery.log.service.OperateLogService;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.ImageReal;
import com.dvnchina.advertDelivery.model.ResourceReal;
import com.dvnchina.advertDelivery.model.VideoReal;
import com.dvnchina.advertDelivery.order.bean.Order;
import com.dvnchina.advertDelivery.order.bean.OrderMaterialRelation;
import com.dvnchina.advertDelivery.order.bean.OrderMaterialRelationTmp;
import com.dvnchina.advertDelivery.order.bean.PutInPlayListBean;
import com.dvnchina.advertDelivery.order.bean.RequestPlayListBean;
import com.dvnchina.advertDelivery.order.service.OrderService;
import com.dvnchina.advertDelivery.order.service.PlayList4OrderService;
import com.dvnchina.advertDelivery.order.service.PlayListGisService;
import com.dvnchina.advertDelivery.order.service.PlayListReqService;
import com.dvnchina.advertDelivery.ploy.bean.Ploy;
import com.dvnchina.advertDelivery.ploy.service.PloyService;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.position.bean.PositionPackage;
import com.dvnchina.advertDelivery.position.service.PositionService;
import com.dvnchina.advertDelivery.service.CustomerService;
import com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo;
import com.dvnchina.advertDelivery.sysconfig.service.BaseConfigService;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.ConstantsAdsCode;
import com.dvnchina.advertDelivery.utils.Transform;
import com.google.gson.Gson;

public class OrderAction extends BaseAction{
	
	private static final long serialVersionUID = 2573264368644140289L;
	private OrderService orderService = null;
	private OperateLogService operateLogService = null;
	private AuditLogService auditLogService = null;
	private PositionService positionService = null;
	private PlayList4OrderService playList4OrderService;
	private CustomerService customerService;
	private PlayListGisService playListGisService;
	private PlayListReqService playListReqService;
	private BaseConfigService baseConfigService;
	private PloyService ployService;
	private PageBeanDB page = null;//订单、合同分页列表
	private Order order = null;
	private OperateLog operLog = null;
	private AuditLog auditLog = null;
	private Contract contract = null;
	private Ploy ploy = null;
	private ResourceReal resource = null;
	private List<ResourceReal> resourceList = null;//可绑定的素材列表
	private AdvertPosition advertPosition = null;
	private List<Customer> customerList ;
	private int roleType = 0;//角色类型  1广告商  2运营商
	private PageBeanDB pageReleaseLocation = new PageBeanDB();
	List<TChannelGroup> channelGroupList = null;
	OrderMaterialRelationTmp omRelTmp = null;
	
	private String previewValue;
	private String resourceValue;
	private String mateId;
	/**
	 * 查询订单列表
	 * */
	public String queryOrderList() {
		
		try{
			if(page == null){
				page = new PageBeanDB();
			}
			page = orderService.queryOrderList(order,page.getPageNo(), page.getPageSize());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 进入新增订单页面
	 * */
	public String initSave() {
		if(order == null){
			order = new Order();
		}
		order.setOrderCode(System.currentTimeMillis() + "");
		Integer aheadTime = new Integer(baseConfigService.getBaseConfigByCode("orderOpAheadTime"));
		getRequest().setAttribute("orderOpAheadTime", aheadTime);
		roleType = getLoginUser().getRoleType();
		return SUCCESS;
	}
	
	/**
	 * 根据广告商ID获取合同列表
	 * @return
	 */
	public String queryContractList() {
		
		try{
			if(page == null){
				page = new PageBeanDB();
			}
			Integer customId = getLoginUser().getCustomerId();
			page = orderService.queryContractList(customId,contract,page.getPageNo(), page.getPageSize());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 查询广告位信息列表
	 * @return
	 */
	public String queryPositionList() {
		
		try{
			if(page == null){
				page = new PageBeanDB();
			}
			page = orderService.queryPositionList(advertPosition,page.getPageNo(), page.getPageSize());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 查询策略信息列表
	 * @return
	 */
	public String queryPloyList() {
		
		try{
			if(page == null){
				page = new PageBeanDB();
			}
			//获取广告位包类型
			Integer adPackageType = positionService.getPackageTypeByAdId(ploy.getPositionId());
			page = orderService.queryPloyList(ploy,adPackageType, page.getPageNo(), page.getPageSize());
			getRequest().setAttribute("adPackageType", adPackageType);		
			// TODO 暂时以广告位ID来判断跳转页面，后期修改成根据广告位属性来判断
			//回放（频道）广告 ，  IS_PLAYBACK = 1
			if(ploy.getPositionId().intValue() == 17 
					|| ploy.getPositionId().intValue() == 39 
					|| ploy.getPositionId().intValue() == 40){
				return "PLAYBACK";
			}			//回看产品、回看栏目（菜单）广告，  IS_LOOKBACK_PRODUCT=1、IS_COLUMN=1
			else if(ploy.getPositionId().intValue()==15 || ploy.getPositionId().intValue()==16
					|| ploy.getPositionId().intValue() == 25 || ploy.getPositionId().intValue() == 26  // 25、26 点播随片
					|| ploy.getPositionId().intValue() == 43
					|| ploy.getPositionId().intValue() == 19 || ploy.getPositionId().intValue() == 20){
				//回看回放菜单广告、回看回放插播广告、回看回放暂停广告
				return "lookBack";
			}else if(ploy.getPositionId().intValue()==1 || ploy.getPositionId().intValue()==2||
					ploy.getPositionId().intValue()==3 || ploy.getPositionId().intValue()==4||
					ploy.getPositionId().intValue()==45 || ploy.getPositionId().intValue()==46
					//||ploy.getPositionId().intValue()==13 || ploy.getPositionId().intValue()==14
					){
				return "start";
			}else if(ploy.getPositionId().intValue()==23 || ploy.getPositionId().intValue()==24){
				return "twoVod";
			}
			//广播背景
			else if(ploy.getPositionId().intValue()==13 || ploy.getPositionId().intValue()==14){
				return "radio";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 根据策略ID  ployId获取子策略JSON信息
	 */
	public void getSubPloyJson() {
		
		try{
			String json = orderService.getSubPloyJson(id);
			renderJson(json);
		}catch(Exception e){
			renderText("-1");
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据策略ID查询精准信息
	 */
	public void queryPrecises4Json() {
		
		try{
			String json = orderService.queryPrecises4Json(id);
			renderJson(json);
		}catch(Exception e){
			renderText("-1");
			e.printStackTrace();
		}
	}
	
	public void getAreaResourcePath(){
		try{
			//System.out.println("come");
			String metaId = getRequest().getParameter("metaId");
			String json = orderService.queryResourcePath(metaId);
			renderJson(json);
		}catch(Exception e){
			renderText("-1");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 查询素材信息
	 * @return
	 */
	public String queryResourceList() {
		
		try{
			advertPosition = positionService.getAdvertPosition(resource.getAdvertPositionId());
			resourceList = orderService.queryResourceList(resource);
			String selresource=order.getSelResource();
			getRequest().setAttribute("selectResource", selresource);
			if("01001".equals(advertPosition.getPositionCode()) || "01002".equals(advertPosition.getPositionCode())){
				//开机广告位
				for(ResourceReal resource : resourceList){
					if(resource.getResourceType().intValue()==0){
						ImageReal image = orderService.getImageRealById(resource.getResourceId());
						resource.setFileSize(image.getFileSize());
					}else if(resource.getResourceType().intValue()==1){
						VideoReal video = orderService.getVideoRealById(resource.getResourceId());
						resource.setFileSize(video.getFileSize());
					}
				}
				String ployId = getRequest().getParameter("ployId");
				List<Ploy> polyList = orderService.getPloyByPloyId(Integer.valueOf(ployId));
				getRequest().setAttribute("defaultStart", polyList.get(0).getDefaultstart().trim());
				return "boot";
			}else if("02061".equals(advertPosition.getPositionCode()) || "02062".equals(advertPosition.getPositionCode())){
				//音频背景广告位
				for(ResourceReal resource : resourceList){
					if(resource.getResourceType().intValue()==0){
						ImageReal image = orderService.getImageRealById(resource.getResourceId());
						resource.setFileSize(image.getFileSize());
					}else if(resource.getResourceType().intValue()==1){
						VideoReal video = orderService.getVideoRealById(resource.getResourceId());
						resource.setFileSize(video.getFileSize());
					}
				}
				return "audio";
			}else{
				//设置视频广告位编码插播次数
				String instreamPosition = baseConfigService.getBaseConfigByCode("instreamPosition");
				if(instreamPosition != null){
					String[] array = instreamPosition.split(",");
					for(String adCode : array){
						if(advertPosition.getPositionCode().equals(adCode)){
							String instreamNumber = baseConfigService.getBaseConfigByCode("instreamNumber");
							if(instreamNumber != null){
								advertPosition.setIsInstream(Integer.valueOf(instreamNumber));
							}
							break;
						}
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 查询素材信息
	 * @return
	 */
	public String queryAreaResourceList() {
		
		try{
			advertPosition = positionService.getAdvertPosition(resource.getAdvertPositionId());
			resourceList = orderService.queryResourceList(resource);
//			getRequest().setAttribute("selectResource", order.getSelResource());
			if("01001".equals(advertPosition.getPositionCode()) || "01002".equals(advertPosition.getPositionCode())){
				//开机画面
				
				List<ResourceReal> resourceTempList=new ArrayList<ResourceReal>(resourceList);
				for(ResourceReal resource : resourceTempList){
					if(resource.getResourceType().intValue()==0){
						ImageReal image = orderService.getImageRealById(resource.getResourceId());
						resource.setFileSize(image.getFileSize());
					}else if(resource.getResourceType().intValue()==1){
						//VideoReal video = orderService.getVideoRealById(resource.getResourceId());
						//resource.setFileSize(video.getFileSize());
						resourceList.remove(resource);
					}
				}
				String ployId = getRequest().getParameter("ployId");
				List<Ploy> polyList = orderService.getPloyByPloyId(Integer.valueOf(ployId));
				getRequest().setAttribute("defaultStart", polyList.get(0).getDefaultstart().trim());
				getRequest().setAttribute("ployId", ployId);
				return "boot";
			}else if("01004".equals(advertPosition.getPositionCode()) || "01003".equals(advertPosition.getPositionCode())){
				//开机视频
				List<ResourceReal> resourceTempList=new ArrayList<ResourceReal>(resourceList);
				for(ResourceReal resource : resourceTempList){
					if(resource.getResourceType().intValue()==0){
						//ImageReal image = orderService.getImageRealById(resource.getResourceId());
						//resource.setFileSize(image.getFileSize());
						resourceList.remove(resource);
					}else if(resource.getResourceType().intValue()==1){
						//System.out.println(resource.getResourceId());
						VideoReal video = orderService.getVideoRealById(resource.getResourceId());
						//System.out.println(video);
						//if(video!=null){
							resource.setFileSize(video.getFileSize());
						//}
						
					}
				}
				String ployId = getRequest().getParameter("ployId");
				List<Ploy> polyList = orderService.getPloyByPloyId(Integer.valueOf(ployId));
				getRequest().setAttribute("defaultStart", polyList.get(0).getDefaultstart().trim());
				getRequest().setAttribute("ployId", ployId);
				return "boot";
			}else if("02061".equals(advertPosition.getPositionCode()) || "02062".equals(advertPosition.getPositionCode())){
				//广播收听背景广告位
				for(ResourceReal resource : resourceList){
					if(resource.getResourceType().intValue()==0){
						ImageReal image = orderService.getImageRealById(resource.getResourceId());
						resource.setFileSize(image.getFileSize());
					}else if(resource.getResourceType().intValue()==1){
						VideoReal video = orderService.getVideoRealById(resource.getResourceId());
						resource.setFileSize(video.getFileSize());
					}
				}
				return "audio";
			}else{
				//设置视频广告位编码插播次数
				String instreamPosition = baseConfigService.getBaseConfigByCode("instreamPosition");
				if(instreamPosition != null){
					String[] array = instreamPosition.split(",");
					for(String adCode : array){
						if(advertPosition.getPositionCode().equals(adCode)){
							String instreamNumber = baseConfigService.getBaseConfigByCode("instreamNumber");
							if(instreamNumber != null){
								advertPosition.setIsInstream(Integer.valueOf(instreamNumber));
							}
							break;
						}
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 检查开机播放时段和素材大小
	 * */
	public void checkBootResoueces() {
		try {
			
			//开机图片播放时段，以逗号分隔
			String timeIntervals = getRequest().getParameter("timeIntervals");
			if(StringUtils.isNotBlank(timeIntervals)){
				if(timeIntervals.endsWith(",")){
					timeIntervals = timeIntervals.substring(0,timeIntervals.length()-1);
				}
				List<String> list = new ArrayList<String>();
				for(int i=0;i<24;i++){
					list.add(i+"timeInterval");
				}
				String[] timeIntervalArray = timeIntervals.split(",");
				for(String timeInterval : timeIntervalArray){
					try{
						boolean flag = list.remove(Integer.parseInt(timeInterval)+"timeInterval");
						if(!flag){
							renderText("播放时段必须是0——23之间，并用逗号,分隔！");
							return;
						}
					}catch(NumberFormatException e){
						renderText("播放时段必须是0——23之间，并用逗号,分隔！");
						return;
					}
				}
				if(list.size() !=0){
					renderText("播放时段必须是0——23之间，并用逗号,分隔！");
					return;
				}
			}
			/**
			 * 选择的开机素材  
			 * 格式：选择素材与播放时段关系数组，每个关系用@分隔；
			 * 素材与播放时段关系用#分隔；
			 * 样例：10#0,1,3@11#4,5,6@12#7,8,9
			 */
			String selectResource = getRequest().getParameter("selectResource");
			ids = "";
			String[] resourceArray = selectResource.split("@");
			//map  key素材id，value播放时段列表
			Map<String,List<String>> map = new HashMap<String,List<String>>();
			for(String resource : resourceArray){
				String[] idArray = resource.split("#");
				String[] timeArray = idArray[1].split(",");
				List<String> list = new ArrayList<String>();
				for(String time : timeArray){
					list.add(time);
				}
				map.put(idArray[0], list);
				ids +=idArray[0]+",";
			}
			resourceList = orderService.getResourceListByIds(ids.substring(0,ids.length()-1));
			long imageFileSize = 0;
			for(ResourceReal resource : resourceList){
				if(resource.getResourceType()==0){
					ImageReal image = orderService.getImageRealById(resource.getResourceId());
					imageFileSize += Long.parseLong(image.getFileSize())*map.get(resource.getId().toString()).size();
				}else if(resource.getResourceType()==1){
					VideoReal video = orderService.getVideoRealById(resource.getResourceId());
					String bootVideoFileSize = baseConfigService.getBaseConfigByCode("bootVideoFileSize");
					if(Long.parseLong(video.getFileSize()) > Long.parseLong(bootVideoFileSize)){
						renderText("开机视频文件大小不能大于"+bootVideoFileSize+"！");
						return;
					}
				}
			}
			String bootImageFileSize = baseConfigService.getBaseConfigByCode("bootImageFileSize");
			if(imageFileSize > Long.parseLong(bootImageFileSize)){
				renderText("开机图片文件总大小不能大于"+bootImageFileSize+"！");
				return;
			}
			
			renderText("0");
			
		} catch (Exception e) {
			log.error("检查开机播放时段时出现异常", e);
			renderText("-1");
		}
	}
	
	/**
	 * 检查订单规则是否满足条件
	 * */
	public void checkOrderRule() {
		try {
			String startDate = getRequest().getParameter("startDate");
			String endDate = getRequest().getParameter("endDate");
			String ployId = getRequest().getParameter("ployId");
			String positionId = getRequest().getParameter("positionId");
			advertPosition = positionService.getAdvertPosition(Integer.parseInt(positionId));
			//检查订单绑定的策略在排期内是否有冲突
			int orderNum = orderService.getOrderNumByDate(startDate,
					endDate,Integer.valueOf(positionId), id);
			if(orderNum !=0){
				String areaPosition = ConfigureProperties.getInstance().getValueByKey("area.position");
				if(areaPosition.indexOf(advertPosition.getPositionCode()) >= 0){
					boolean exsiteAreaOrder = orderService.exsiteAreaOrder(startDate,
							endDate,Integer.valueOf(positionId), id, Integer.valueOf(ployId));
					if(exsiteAreaOrder){
						renderText("订单对应区域在日期范围与已有订单存在冲突，订单创建失败！");
						return;
					}
				}else{
					renderText("订单的日期范围与已有订单存在冲突，订单创建失败！");
					return;
				}
			}
			
			if("02061".equals(advertPosition.getPositionCode()) || "02062".equals(advertPosition.getPositionCode())){
				
				//音频类广告位，检查素材文件总大小
				String selResource = getRequest().getParameter("selResource");
				List<OrderMaterialRelation> relList = orderService.getOrderMaterialRelation(selResource);
				
				String pIds = "";
				String mateIds = "";
				//策略ID与素材关系
				Map<Integer,List<Integer>> ployMateMap = new HashMap<Integer,List<Integer>>();
				Integer prePloyId = relList.get(0).getPreciseId();
				List<Integer> mateIdList = new ArrayList<Integer>();
				for(OrderMaterialRelation rel : relList){
					pIds += rel.getPreciseId()+",";
					mateIds += rel.getMateId()+",";
					if(prePloyId.intValue() != rel.getPreciseId().intValue()){
						ployMateMap.put(prePloyId, mateIdList);
						mateIdList = new ArrayList<Integer>();
					}
					mateIdList.add(rel.getMateId());
					prePloyId =  rel.getPreciseId();
				}
				//设置最后一个策略ID与素材关系
				ployMateMap.put(prePloyId, mateIdList);
				if(pIds.endsWith(",")){
					pIds = pIds.substring(0,pIds.length()-1);
				}
				if(mateIds.endsWith(",")){
					mateIds = mateIds.substring(0,mateIds.length()-1);
				}
				//获取素材ID与文件大小关系
				Map<Integer,String> mateMap = playListGisService.getImageMateFileSizeMap(mateIds);
				//获取添加素材的策略列表，并根据开始时间升序，优先级倒序进行排序
				List<Ploy> ployList = orderService.getPloyListByIds(pIds);
				String preStartTime = ployList.get(0).getStartTime();
				String preEndTime = ployList.get(0).getEndTime();
				//频道组ID与频道serviceId列表关系
				Map<String,List<String>> serviceIdListMap = new HashMap<String,List<String>>();
				String audioImageFileSize = baseConfigService.getBaseConfigByCode("audioImageFileSize");
				//每个时间段内的serviceId列表
				Map<String,String> serviceIdMap = new HashMap<String,String>();
				long fileSize = 0;//音频类素材文件总大小，每个音频频道都要计算文件大小
				List<String> serviceIdList = null;
				for(Ploy p : ployList){
					if(!serviceIdListMap.containsKey(p.getGroupId()+"")){
						ChannelInfo channel = new ChannelInfo();
						channel.setChannelType("音频直播类业务");
						serviceIdList = orderService.getServiceIdList(p.getGroupId(), channel);
						serviceIdListMap.put(p.getGroupId()+"", serviceIdList);
					}
					if(preStartTime.equals(p.getStartTime())){
						serviceIdList = serviceIdListMap.get(p.getGroupId()+"");
						int serviceIdSize = 0;
						for(String serviceId : serviceIdList){
							if(!serviceIdMap.containsKey(serviceId)){
								serviceIdMap.put(serviceId, "");
								++serviceIdSize;
							}
						}
						mateIdList = ployMateMap.get(p.getId());
						for(Integer mateId : mateIdList){
							String mateFileSize = mateMap.get(mateId);
							fileSize += Long.parseLong(mateFileSize)*serviceIdSize;
						}
						
					}else{
						if(fileSize > Long.parseLong(audioImageFileSize)){
							if("0".equals(preStartTime.trim())){
								preStartTime = "00:00:00";
								preEndTime = "23:59:59";
							}
							renderText("时间段"+preStartTime+"-"+preEndTime+"之间的音频类素材文件总大小不能大于"+audioImageFileSize+"字节！");
							return;
						}
						fileSize = 0;
						serviceIdMap = new HashMap<String,String>();
						serviceIdList = serviceIdListMap.get(p.getGroupId()+"");
						int serviceIdSize = 0;
						for(String serviceId : serviceIdList){
							if(!serviceIdMap.containsKey(serviceId)){
								serviceIdMap.put(serviceId, "");
								++serviceIdSize;
							}
						}
						mateIdList = ployMateMap.get(p.getId());
						for(Integer mateId : mateIdList){
							String mateFileSize = mateMap.get(mateId);
							fileSize += Long.parseLong(mateFileSize)*serviceIdSize;
						}
					}
					preStartTime = p.getStartTime();
					preEndTime = p.getEndTime();
				}
				if(fileSize > Long.parseLong(audioImageFileSize)){
					if("0".equals(preStartTime.trim())){
						preStartTime = "00:00:00";
						preEndTime = "23:59:59";
					}
					renderText("时间段"+preStartTime+"-"+preEndTime+"之间的音频类素材文件总大小不能大于"+audioImageFileSize+"字节！");
					return;
				}
			}else{
				PositionPackage adPackage = positionService.getPositionPackageById(advertPosition.getPositionPackageId());
				if(adPackage.getPositionPackageType().intValue() == Constant.POSITION_TYPE_ONE_REAL_TIME){
					//单向实时广告位
					long oneRealTimeFileSize = Long.parseLong(baseConfigService.getBaseConfigByCode("oneRealTimeFileSize"));
					
					long fileSize = 0;
					String selResource = getRequest().getParameter("selResource");
					String mateIds = "";
					String pIds = "";
					if(StringUtils.isNotBlank(selResource)){
						Date startD = null;
						Date endD = null;
						try{
							startD = Transform.string4SqlDateYYYYMMDD(startDate);
							endD = Transform.string4SqlDateYYYYMMDD(endDate);
						}catch(Exception e){
							e.printStackTrace();
						}
						List<OrderMaterialRelation> relList = orderService.getOrderMaterialRelation(selResource);
						Integer prePloyId = relList.get(0).getPreciseId();
						Order o = null;
						Ploy ploy = null;
						//根据分策略检查每个时间段内的素材大小是否合法
						for(OrderMaterialRelation rel : relList){
							if(prePloyId.intValue() != rel.getPreciseId().intValue()){
								ploy = orderService.getPloyById(rel.getPreciseId());
								o = new Order();
								o.setStartTime(startD);
								o.setEndTime(endD);
								if("0".equals(ploy.getStartTime())){
									o.setPloyStartTime("00:00:00");
									o.setPloyEndTime("23:59:59");
								}else{
									o.setPloyStartTime(ploy.getStartTime());
									o.setPloyEndTime(ploy.getEndTime());
								}
								
								if(mateIds.endsWith(",")){
									mateIds = mateIds.substring(0,mateIds.length()-1);
								}
								//获取素材ID与文件大小关系
								Map<Integer,String> mateMap = playListGisService.getImageMateFileSizeMap(mateIds);
								Collection<String> list = mateMap.values();
								for(String str : list){
									fileSize += Long.parseLong(str);
								}
								fileSize += orderService.getFileSumSize(Constant.POSITION_TYPE_ONE_REAL_TIME,o,mateIds);
								if(fileSize >= oneRealTimeFileSize){
									renderText("单向实时订单在时间段"+o.getPloyStartTime()
											+"——"+o.getPloyEndTime()+"内的素材总大小大于"+oneRealTimeFileSize+"字节，创建失败！");
									return ;
								}
								mateIds = "";
								fileSize = 0;
							}
							mateIds += rel.getMateId()+",";
							prePloyId =  rel.getPreciseId();
						}
						if(o == null){
							ploy = orderService.getPloyById(prePloyId);
							o = new Order();
							o.setStartTime(startD);
							o.setEndTime(endD);
							if("0".equals(ploy.getStartTime())){
								o.setPloyStartTime("00:00:00");
								o.setPloyEndTime("23:59:59");
							}else{
								o.setPloyStartTime(ploy.getStartTime());
								o.setPloyEndTime(ploy.getEndTime());
							}
						}
						if(mateIds.endsWith(",")){
							mateIds = mateIds.substring(0,mateIds.length()-1);
						}
						//获取素材ID与文件大小关系
						Map<Integer,String> mateMap = playListGisService.getImageMateFileSizeMap(mateIds);
						Collection<String> list = mateMap.values();
						for(String str : list){
							fileSize += Long.parseLong(str);
						}
						fileSize += orderService.getFileSumSize(Constant.POSITION_TYPE_ONE_REAL_TIME,o,mateIds);
						if(fileSize >= oneRealTimeFileSize){
							renderText("单向实时订单在时间段"+o.getPloyStartTime()
									+"——"+o.getPloyEndTime()+"内的素材总大小大于"+oneRealTimeFileSize+"字节，创建失败！");
							return ;
						}
					}
				}
			}
			renderText("0");
		} catch (Exception e) {
			log.error("检查订单规则是否满足条件时出现异常", e);
			renderText("-1");
		}
	}
	
	/**
	 * 检查订单规则是否满足条件
	 * */
	public void checkOrderRule2() {
		try {
			String startDate = getRequest().getParameter("startDate");
			String endDate = getRequest().getParameter("endDate");
			String ployId = getRequest().getParameter("ployId");
			String positionId = getRequest().getParameter("positionId");
			String orderCode = getRequest().getParameter("orderCode");
			advertPosition = positionService.getAdvertPosition(Integer.parseInt(positionId));
			//检查订单绑定的策略在排期内是否有冲突
			int orderNum = orderService.getOrderNumByDate(startDate,
					endDate,Integer.valueOf(positionId), id);
			if(orderNum !=0){
				String areaPosition = ConfigureProperties.getInstance().getValueByKey("area.position");
				//17回放菜单，不分地市，但分频道组和时间段
				if(areaPosition.indexOf(advertPosition.getPositionCode()) >= 0 || "17".equals(positionId)){
//					boolean exsiteAreaOrder = orderService.exsiteAreaOrder(startDate,
//							endDate,Integer.valueOf(positionId), id, Integer.valueOf(ployId));
//					if(exsiteAreaOrder){
//						renderText("订单对应区域在日期范围与已有订单存在冲突，订单创建失败！");
//						return;
//					}
					boolean exsiteOrder = orderService.exsiteOrder(startDate,
							endDate,Integer.valueOf(positionId), orderCode);
					if(exsiteOrder){
						renderText("订单对应区域在日期范围与已有订单存在冲突，订单创建失败！");
						return;
					}
				}else{
					renderText("订单的日期范围与已有订单存在冲突，订单创建失败！");
					return;
				}
			}
			
			if(orderService.valiIsHasSuCai(orderCode)){
				renderText("请为策略绑定素材！");
				return;
			}
			
			if("02061".equals(advertPosition.getPositionCode()) || "02062".equals(advertPosition.getPositionCode())){
				
				//音频类广告位，检查素材文件总大小
				String mateIds = "";
				//获取选择的素材
				Map<String,String> matesMap = new HashMap<String,String>();
				List<OrderMaterialRelationTmp> orList = orderService.getOrderMaterialRelationTmpList(orderCode);
				if(orList != null && orList.size()>0){
					for(OrderMaterialRelationTmp tmp : orList){
						if(!matesMap.containsKey(tmp.getMateId()+"")){
							matesMap.put(tmp.getMateId()+"", "");
							mateIds += tmp.getMateId()+",";
						}
					}
				}
				if(mateIds.endsWith(",")){
					mateIds = mateIds.substring(0,mateIds.length()-1);
				}
				
				//获取素材ID与文件大小关系
				Map<Integer,String> mateMap = playListGisService.getImageMateFileSizeMap(mateIds);
				
				//频道组ID与频道serviceId列表关系
				Map<String,List<String>> serviceIdListMap = new HashMap<String,List<String>>();
				List<String> serviceIdList = null;
				//每个时间段内的serviceId列表
				Map<String,String> serviceIdMap = new HashMap<String,String>();
				String audioImageFileSize = baseConfigService.getBaseConfigByCode("audioImageFileSize");
				long fileSize = 0;//音频类素材文件总大小，每个音频频道都要计算文件大小
				if(orList != null && orList.size()>0){
					String preStartTime = orList.get(0).getStartTime();
					String preEndTime = orList.get(0).getEndTime();
					for(OrderMaterialRelationTmp tmp : orList){
						if(!serviceIdListMap.containsKey(tmp.getChannelGroupId()+"")){
							ChannelInfo channel = new ChannelInfo();
							channel.setChannelType("音频直播类业务");
							//根据频道组ID获取频道serviceId列表
							serviceIdList = orderService.getServiceIdList(tmp.getChannelGroupId(), channel);
							serviceIdListMap.put(tmp.getChannelGroupId()+"", serviceIdList);
						}
						if(preStartTime.equals(tmp.getStartTime())){
							serviceIdList = serviceIdListMap.get(tmp.getChannelGroupId()+"");
							int serviceIdSize = 0;
							for(String serviceId : serviceIdList){
								if(!serviceIdMap.containsKey(serviceId)){
									serviceIdMap.put(serviceId, "");
									++serviceIdSize;
								}
							}
							String mateFileSize = mateMap.get(tmp.getMateId());
							fileSize += Long.parseLong(mateFileSize)*serviceIdSize;
							
						}else{
							if(fileSize > Long.parseLong(audioImageFileSize)){
								if("0".equals(preStartTime.trim())){
									preStartTime = "00:00:00";
									preEndTime = "23:59:59";
								}
								renderText("时间段"+preStartTime+"-"+preEndTime+"之间的音频类素材文件总大小不能大于"+audioImageFileSize+"字节！");
								return;
							}
							fileSize = 0;
							serviceIdMap = new HashMap<String,String>();
							serviceIdList = serviceIdListMap.get(tmp.getChannelGroupId()+"");
							int serviceIdSize = 0;
							for(String serviceId : serviceIdList){
								if(!serviceIdMap.containsKey(serviceId)){
									serviceIdMap.put(serviceId, "");
									++serviceIdSize;
								}
							}
							String mateFileSize = mateMap.get(tmp.getMateId());
							fileSize += Long.parseLong(mateFileSize)*serviceIdSize;
						}
						preStartTime = tmp.getStartTime();
						preEndTime = tmp.getEndTime();
					}
					if(fileSize > Long.parseLong(audioImageFileSize)){
						if("0".equals(preStartTime.trim())){
							preStartTime = "00:00:00";
							preEndTime = "23:59:59";
						}
						renderText("时间段"+preStartTime+"-"+preEndTime+"之间的音频类素材文件总大小不能大于"+audioImageFileSize+"字节！");
						return;
					}
				}
				
			}
			else{
				//菜单图片，绑定了素材的地市必须选齐6张素材
				if("02011".equals(advertPosition.getPositionCode()) || "02012".equals(advertPosition.getPositionCode())){
					if(!orderService.validateLoopData(orderCode)){
						renderText("绑定了素材的地区必须绑定6张素材！");
						return;
					}
					
				}
				
				PositionPackage adPackage = positionService.getPositionPackageById(advertPosition.getPositionPackageId());
				if(adPackage.getPositionPackageType().intValue() == Constant.POSITION_TYPE_ONE_REAL_TIME){
					//单向实时广告位
					long oneRealTimeFileSize = Long.parseLong(baseConfigService.getBaseConfigByCode("oneRealTimeFileSize"));
					
					long fileSize = 0;
					String selResource = getRequest().getParameter("selResource");
					String mateIds = "";
					String pIds = "";
					if(StringUtils.isNotBlank(selResource)){
						Date startD = null;
						Date endD = null;
						try{
							startD = Transform.string4SqlDateYYYYMMDD(startDate);
							endD = Transform.string4SqlDateYYYYMMDD(endDate);
						}catch(Exception e){
							e.printStackTrace();
						}
						List<OrderMaterialRelation> relList = orderService.getOrderMaterialRelation(selResource);
						Integer prePloyId = relList.get(0).getPreciseId();
						Order o = null;
						Ploy ploy = null;
						//根据分策略检查每个时间段内的素材大小是否合法
						for(OrderMaterialRelation rel : relList){
							if(prePloyId.intValue() != rel.getPreciseId().intValue()){
								ploy = orderService.getPloyById(rel.getPreciseId());
								o = new Order();
								o.setStartTime(startD);
								o.setEndTime(endD);
								if("0".equals(ploy.getStartTime())){
									o.setPloyStartTime("00:00:00");
									o.setPloyEndTime("23:59:59");
								}else{
									o.setPloyStartTime(ploy.getStartTime());
									o.setPloyEndTime(ploy.getEndTime());
								}
								
								if(mateIds.endsWith(",")){
									mateIds = mateIds.substring(0,mateIds.length()-1);
								}
								//获取素材ID与文件大小关系
								Map<Integer,String> mateMap = playListGisService.getImageMateFileSizeMap(mateIds);
								Collection<String> list = mateMap.values();
								for(String str : list){
									fileSize += Long.parseLong(str);
								}
								fileSize += orderService.getFileSumSize(Constant.POSITION_TYPE_ONE_REAL_TIME,o,mateIds);
								if(fileSize >= oneRealTimeFileSize){
									renderText("单向实时订单在时间段"+o.getPloyStartTime()
											+"——"+o.getPloyEndTime()+"内的素材总大小大于"+oneRealTimeFileSize+"字节，创建失败！");
									return ;
								}
								mateIds = "";
								fileSize = 0;
							}
							mateIds += rel.getMateId()+",";
							prePloyId =  rel.getPreciseId();
						}
						if(o == null){
							ploy = orderService.getPloyById(prePloyId);
							o = new Order();
							o.setStartTime(startD);
							o.setEndTime(endD);
							if("0".equals(ploy.getStartTime())){
								o.setPloyStartTime("00:00:00");
								o.setPloyEndTime("23:59:59");
							}else{
								o.setPloyStartTime(ploy.getStartTime());
								o.setPloyEndTime(ploy.getEndTime());
							}
						}
						if(mateIds.endsWith(",")){
							mateIds = mateIds.substring(0,mateIds.length()-1);
						}
						//获取素材ID与文件大小关系
						Map<Integer,String> mateMap = playListGisService.getImageMateFileSizeMap(mateIds);
						Collection<String> list = mateMap.values();
						for(String str : list){
							fileSize += Long.parseLong(str);
						}
						fileSize += orderService.getFileSumSize(Constant.POSITION_TYPE_ONE_REAL_TIME,o,mateIds);
						if(fileSize >= oneRealTimeFileSize){
							renderText("单向实时订单在时间段"+o.getPloyStartTime()
									+"——"+o.getPloyEndTime()+"内的素材总大小大于"+oneRealTimeFileSize+"字节，创建失败！");
							return ;
						}
					}
				}
			}
			renderText("0");
		} catch (Exception e) {
			log.error("检查订单规则是否满足条件时出现异常", e);
			renderText("-1");
		}
	}
	
	/**
	 * 保存订单
	 * @return
	 */
	public String saveOrder(){
		
		try{
			advertPosition = positionService.getAdvertPosition(order.getPositionId());
			order.setStartTime(Transform.StringtoCalendarYMD(order.getStartDateStr()).getTime());
			if(StringUtils.isNotBlank(order.getEndDateStr())){
				order.setEndTime(Transform.StringtoCalendarYMD(order.getEndDateStr()).getTime());
			}else{
				//按次订单，结束时间无限长
				order.setEndTime(Transform.StringtoCalendarYMD("2100-01-01").getTime());
			}
			Date now = new Date();
			order.setCreateTime(now);
			order.setModifyTime(now);
			order.setState(Constant.ORDER_PENDING_CHECK);
			order.setOrderType(advertPosition.getDeliveryMode());
			order.setOperatorId(this.getUserId());
			if(order.getPlayNumber()==null){
				order.setPlayNumber(0);
			}
			order.setPlayedNumber(0);
			orderService.saveOrder(order, advertPosition.getIsHD());
			
			//orderService.saveOrderMaterialRelation(order, advertPosition.getIsHD());
			message = "common.save.success";//保存成功
		}catch(Exception e){
			message = "common.save.failed";//保存失败
			operResult = Constant.OPERATE_FAIL;
			e.printStackTrace();
			log.error("***** OrderAction saveOrder occur a exception: "+e);
		}finally{
			operInfo = order.toString();
			operType = "operate.add";
			operLog = this.setOperationLog(Constant.OPERATE_MODULE_ORDER);
			operateLogService.saveOperateLog(operLog);
		}
		order = null;
		return this.queryOrderList();
	}
	
	/**
	 * 查询更新订单需要的订单信息
	 * */
	public String getOrderForUpdate() {
		Gson gson = new Gson();
		try {
			/** 
			 * 设置订单素材关系信息
			 * 多个精准与素材关系以~分割，
			 * 每个精准与素材关系格式：精准/分策略类型_精准ID#素材列表，素材列表以@分割，
			 * 每个素材中的格式为：素材ID,轮询序号,插播位置
			 * 样例：0_0#10,1,0/3@10,1,1/3@10,1,2/3@12,2,-1@~
			 */
			order = orderService.getOrderById(order.getId());
			advertPosition = positionService.getAdvertPosition(order.getPositionId());
			OrderMaterialRelation omr = new OrderMaterialRelation();
			omr.setOrderId(order.getId());
			List<OrderMaterialRelation> relList = orderService.getOrderMaterialRelList(omr,advertPosition.getPositionCode());
			getRequest().setAttribute("materialJson", gson.toJson(relList));
//			Integer prePreciseId = -1;
			String prePloyInfo = "";//前一个精准/分策略类型_精准ID
			String ployInfo = "";//精准/分策略类型_精准ID
			String selResource = "";
			for(OrderMaterialRelation rel : relList){
				Integer pollIndex = -1;
				String playLocation = "-1";
				if(rel.getPollIndex() != null){
					pollIndex = rel.getPollIndex();
				}
				if(rel.getPlayLocation() != null){
					playLocation = rel.getPlayLocation();
				}
				ployInfo = rel.getType()+"_"+rel.getPreciseId();
				if(!prePloyInfo.equals(ployInfo)){
					selResource += "~"+ployInfo+"#";
					prePloyInfo = ployInfo;
				}
//				if(rel.getPreciseId().intValue() != prePreciseId.intValue()){
//					selResource += "~"+rel.getPreciseId()+"#";
//					prePreciseId = rel.getPreciseId();
//				}
				selResource += rel.getMateId()+","+pollIndex+","+playLocation+"@";
			}
			if(selResource.startsWith("~")){
				selResource = selResource.substring(1);
			}
			if(selResource.length()>0 && !selResource.endsWith("~")){
				selResource += "~";
			}
			
			order.setSelResource(selResource);
			
			String ployJson = "";
			//获取广告位包类型
			Integer adPackageType = positionService.getPackageTypeByAdId(order.getPositionId());
			if(Constant.POSITION_TYPE_ONE_NOT_REAL_TIME==adPackageType.intValue()
					|| Constant.POSITION_TYPE_ONE_REAL_TIME == adPackageType.intValue()
					|| Constant.POSITION_TYPE_ONE_DATA_BROADCASTING == adPackageType.intValue()){
				//单向广告位
				//根据策略ID获取分策略的json信息
				ployJson = orderService.getSubPloyJson(order.getPloyId());
			}else{
				//双向广告位
				//根据策略ID获取精准的json信息
				ployJson = orderService.queryPrecises4Json(order.getPloyId());
				
			}
			getRequest().setAttribute("adPackageType", adPackageType);
			getRequest().setAttribute("ployJson", ployJson);
			
			Integer aheadTime = 0;//提前量
			if (order.getOrderType().intValue() == Constant.ADVERT_POSITION_WAY_ADVERT) {
				aheadTime = new Integer(baseConfigService.getBaseConfigByCode("orderOpAheadTime"));
			}
			getRequest().setAttribute("aheadTime", aheadTime);
			
			//返回广告位的JSON信息，用于预览
			getRequest().setAttribute("positionJson", gson.toJson(advertPosition));
			String state = order.getState();//orderService.getOrder(id).getState();
			
			if (state.equals(Constant.ORDER_PENDING_CHECK_UPDATE)
					|| state.equals(Constant.ORDER_PENDING_CHECK_DELETE)
					|| state.equals(Constant.ORDER_CHECK_NOT_PASS_UPDATE)
					|| state.equals(Constant.ORDER_CHECK_NOT_PASS_DELETE)
					|| state.equals(Constant.ORDER_PUBLISHED)
					|| state.equals(Constant.ORDER_IS_FINISHED)) {
				String startTime = Transform.date2String(order.getStartTime(), "yyyy-MM-dd") + " " + order.getPloyStartTime();
				if (state.equals(Constant.ORDER_IS_FINISHED)||!orderService.checkOrderStartTime(startTime, new Date())) {
					//执行完毕 或者 订单起始时间<（当前时间-提前量）
					/**订单已执行，订单状态为[修改待审核]或[修改审核不通过]，根据播出单还原订单的内容*/
					if (state == Constant.ORDER_PENDING_CHECK_UPDATE
							|| state == Constant.ORDER_CHECK_NOT_PASS_UPDATE) {
						
						PutInPlayListBean pBean = playList4OrderService.getPutInPlayListByOrderId(order.getId());
						if (pBean != null) {
							orderService.restoreOrder(pBean,null,null);
						} else {
							List<RequestPlayListBean> rBeans = playList4OrderService
									.getRequestPlayListByOrderId(order.getId());
							orderService.restoreOrder(rBeans,null,null);
						}
					}
					return "updateEndTime";
				}

			}
			
		} catch (Exception e) {
			log.error("查询更新订单需要的订单信息出现异常", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 查询更新订单需要的订单信息
	 * */
	public String getOrderForUpdate2() {
		Gson gson = new Gson();
		try {
			/** 
			 * 设置订单素材关系信息
			 * 多个精准与素材关系以~分割，
			 * 每个精准与素材关系格式：精准/分策略类型_精准ID#素材列表，素材列表以@分割，
			 * 每个素材中的格式为：素材ID,轮询序号,插播位置
			 * 样例：0_0#10,1,0/3@10,1,1/3@10,1,2/3@12,2,-1@~
			 */
			order = orderService.getOrderById(order.getId());
			/*
			advertPosition = positionService.getAdvertPosition(order.getPositionId());
			OrderMaterialRelation omr = new OrderMaterialRelation();
			omr.setOrderId(order.getId());
			List<OrderMaterialRelation> relList = orderService.getOrderMaterialRelList(omr,advertPosition.getPositionCode());
			getRequest().setAttribute("materialJson", gson.toJson(relList));
			String prePloyInfo = "";//前一个精准/分策略类型_精准ID
			String ployInfo = "";//精准/分策略类型_精准ID
			String selResource = "";
			for(OrderMaterialRelation rel : relList){
				Integer pollIndex = -1;
				String playLocation = "-1";
				if(rel.getPollIndex() != null){
					pollIndex = rel.getPollIndex();
				}
				if(rel.getPlayLocation() != null){
					playLocation = rel.getPlayLocation();
				}
				ployInfo = rel.getType()+"_"+rel.getPreciseId();
				if(!prePloyInfo.equals(ployInfo)){
					selResource += "~"+ployInfo+"#";
					prePloyInfo = ployInfo;
				}
				selResource += rel.getMateId()+","+pollIndex+","+playLocation+"@";
			}
			if(selResource.startsWith("~")){
				selResource = selResource.substring(1);
			}
			if(selResource.length()>0 && !selResource.endsWith("~")){
				selResource += "~";
			}
			
			order.setSelResource(selResource);
			*/
			//添加订单和素材临时关系数据
			insertOrderMateRelTmp(order.getOrderCode(), order.getPloyId(),order.getPositionId());
//			String ployJson = "";
//			//获取广告位包类型
//			Integer adPackageType = positionService.getPackageTypeByAdId(order.getPositionId());
//			if(Constant.POSITION_TYPE_ONE_NOT_REAL_TIME==adPackageType.intValue()
//					|| Constant.POSITION_TYPE_ONE_REAL_TIME == adPackageType.intValue()
//					|| Constant.POSITION_TYPE_ONE_DATA_BROADCASTING == adPackageType.intValue()){
//				//单向广告位
//				//根据策略ID获取分策略的json信息
//				ployJson = orderService.getSubPloyJson(order.getPloyId());
//			}else{
//				//双向广告位
//				//根据策略ID获取精准的json信息
//				ployJson = orderService.queryPrecises4Json(order.getPloyId());
//				
//			}
//			getRequest().setAttribute("adPackageType", adPackageType);
//			getRequest().setAttribute("ployJson", ployJson);
			
			Integer aheadTime = 0;//提前量
			if (order.getOrderType().intValue() == Constant.ADVERT_POSITION_WAY_ADVERT) {
				aheadTime = new Integer(baseConfigService.getBaseConfigByCode("orderOpAheadTime"));
			}
			getRequest().setAttribute("aheadTime", aheadTime);
			
			//返回广告位的JSON信息，用于预览
			
			
			//add by ly 用于预览   因为预览时只有后面三个参数有用    故省略部分信息 用1_2_3_4_5代替
			advertPosition = positionService.getAdvertPosition(order.getPositionId());
			//String pid=advertPosition.getId().toString();
			//String pname=advertPosition.getPositionName();
			String pbackgroundPath=advertPosition.getBackgroundPath();
			String pcoordinate=advertPosition.getCoordinate();
			String pwidthHeight=advertPosition.getWidthHeight();
			previewValue="1_2_3_4_5_"+pbackgroundPath+"_"+pcoordinate+"_"+pwidthHeight;
			getRequest().setAttribute("previewValue", previewValue);
			
			
			String state = order.getState();//orderService.getOrder(id).getState();
			if (state.equals(Constant.ORDER_PENDING_CHECK_UPDATE)
					|| state.equals(Constant.ORDER_PENDING_CHECK_DELETE)
					|| state.equals(Constant.ORDER_CHECK_NOT_PASS_UPDATE)
					|| state.equals(Constant.ORDER_CHECK_NOT_PASS_DELETE)
					|| state.equals(Constant.ORDER_PUBLISHED)
					|| state.equals(Constant.ORDER_IS_FINISHED)
					||state.equals(Constant.ORDER_IS_FAIL)) {
				String startTime = Transform.date2String(order.getStartTime(), "yyyy-MM-dd") + " " + order.getPloyStartTime();
				if (state.equals(Constant.ORDER_IS_FINISHED)||!orderService.checkOrderStartTime(startTime, new Date())) {
					//执行完毕 或者 订单起始时间<（当前时间-提前量）
					/**订单已执行，订单状态为[修改待审核]或[修改审核不通过]，根据播出单还原订单的内容*/
					if (state == Constant.ORDER_PENDING_CHECK_UPDATE
							|| state == Constant.ORDER_CHECK_NOT_PASS_UPDATE) {
						
						PutInPlayListBean pBean = playList4OrderService.getPutInPlayListByOrderId(order.getId());
						if (pBean != null) {
							orderService.restoreOrder(pBean,null,null);
						} else {
							List<RequestPlayListBean> rBeans = playList4OrderService
									.getRequestPlayListByOrderId(order.getId());
							orderService.restoreOrder(rBeans,null,null);
						}
					}
					return "updateEndTime";
				}

			}
			
		} catch (Exception e) {
			log.error("查询更新订单需要的订单信息出现异常", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 获取问卷订单详情
	 * @return
	 */
	public String getQuestionnaireDetail(){
		order = orderService.getOrderById(order.getId());
		order.setQuestionnaireCount(orderService.getQuestionnaireCount(order.getId()));
		return SUCCESS;
	}
	
	/**
	 * 修改订单
	 * @return
	 */
	public String updateOrder(){
		
		try{
			advertPosition = positionService.getAdvertPosition(order.getPositionId());
			order.setStartTime(Transform.StringtoCalendarYMD(order.getStartDateStr()).getTime());
			if(StringUtils.isNotBlank(order.getEndDateStr())){
				order.setEndTime(Transform.StringtoCalendarYMD(order.getEndDateStr()).getTime());
			}
			Date now = new Date();
			order.setModifyTime(now);
			order.setOrderType(advertPosition.getDeliveryMode());
			order.setOperatorId(this.getUserId());
			
			String state = order.getState();
			if("0".equals(state) || "3".equals(state)){
				order.setState(Constant.ORDER_PENDING_CHECK);
			}else if("1".equals(state) || "2".equals(state) || "4".equals(state) || "5".equals(state) || "6".equals(state)){
				order.setState(Constant.ORDER_PENDING_CHECK_UPDATE);
			}
			//更新订单
			orderService.updateOrder(order);
			//删除订单与素材关系记录
			//orderService.delOrderMaterialRelation(order.getId());
			//新增订单与素材关系记录
			//orderService.saveOrderMaterialRelation(order, advertPosition.getIsHD());
			message = "common.update.success";//更新成功
		}catch(Exception e){
			message = "common.update.failed";//更新失败
			operResult = Constant.OPERATE_FAIL;
			e.printStackTrace();
			log.error("***** OrderAction updateOrder occur a exception: "+e);
		}finally{
			operInfo = order.toString();
			operType = "operate.update";
			operLog = this.setOperationLog(Constant.OPERATE_MODULE_ORDER);
			operateLogService.saveOperateLog(operLog);
		}
		order=null;
		return queryOrderList();
	}
	
	/**
	 * 更新订单结束时间
	 * */
	public void updateOrderEndTime() {
		Date now = new Date();
		try {
			order = orderService.getOrderById(id);
			String endDate = getRequest().getParameter("endDate");
			int positionId = order.getPositionId();
			advertPosition = positionService.getAdvertPosition(positionId);
//			Integer orderNum = 0;
//			//分区域的订单
//			if(positionId == 1 || positionId ==2){
//				orderNum = orderService.getOrderNumByDateAndArea(order.getStartTime().toString(),endDate, order.getPositionId(), id);
//			}else{
//				orderNum = orderService.getOrderNumByDate(order.getStartTime().toString(),endDate, order.getPositionId(), id);
//			}
//			
//			if(orderNum.intValue() != 0){
//				renderText("订单的日期范围与已有订单存在冲突，修改订单失败！");
//				operResult = Constant.OPERATE_FAIL;
//				return;
//			}
			
			//检查订单绑定的策略在排期内是否有冲突
			int orderNum = orderService.getOrderNumByDate(order.getStartTime().toString(),
					endDate,Integer.valueOf(positionId), null);
			if(orderNum !=0){
				String areaPosition = ConfigureProperties.getInstance().getValueByKey("area.position");
				if(areaPosition.indexOf(advertPosition.getPositionCode()) >= 0 || "17".equals(positionId)){
					boolean exsiteOrder = orderService.exsiteOrder(order.getStartTime().toString(),
							endDate,Integer.valueOf(positionId), order.getOrderCode());
					if(exsiteOrder){
						renderText("订单对应区域在日期范围与已有订单存在冲突，订单创建失败！");
						operResult = Constant.OPERATE_FAIL;
						return;
					}
				}else{
					renderText("订单的日期范围与已有订单存在冲突，订单创建失败！");
					operResult = Constant.OPERATE_FAIL;
					return;
				}
			}
			
			if (this.checkOrderTime(order, now) == 1) {
				Date endTime = Transform.StringtoCalendarYMD(endDate).getTime();
				order.setEndTime(endTime);
				order.setState(Constant.ORDER_PENDING_CHECK_UPDATE);
				order.setOperatorId(this.getUserId());
				order.setModifyTime(now);
				orderService.updateOrder(order);
				renderText("0");
			} else {
				renderText("订单已执行完毕，不允许修改");
				operResult = Constant.OPERATE_FAIL;
			}
			message = "common.update.success";//更新成功
		} catch(Exception e){
			message = "common.update.failed";//更新失败
			operResult = Constant.OPERATE_FAIL;
			log.error("***** OrderAction updateOrderEndTime occur a exception: "+e);
			renderText("-1");
		}finally{
			operInfo = order.toString();
			operType = "operate.update";
			operLog = this.setOperationLog(Constant.OPERATE_MODULE_ORDER);
			operateLogService.saveOperateLog(operLog);
		}
	}
	
	/**
	 * 检查订单是否正在执行或已执行完毕 0-未执行，1-正在执行，2-已执行完毕
	 * */
	private Integer checkOrderTime(Order order, Date now) {
		Integer isRunning = 0;
		try {
			String startTime = Transform.date2String(order.getStartTime(), "yyyy-MM-dd") + " " + order.getPloyStartTime();
			if (!orderService.checkOrderStartTime(startTime, now)) {
				if (Constant.ORDER_IS_FINISHED.equals(order.getState())) {
					isRunning = 2;
				} else {
					isRunning = 1;
				}
			}
		} catch (Exception e) {
			log.error("检查订单是否正在执行出现异常", e);
		}
		return isRunning;
	}
	
	/**
	 * 删除订单
	 * @return
	 */
	public String delOrder(){
		StringBuffer delInfo = new StringBuffer();
		delInfo.append("删除订单：");
		
		String delIds = "";//需要直接删除的订单（待审核、审核不通过）
		String updateStateIds = "";//修改状态的订单（修改待审核状态、修改审核不通过状态）
		String publishStateIds = "";//发布状态的订单
		
		//针对修改待审核、修改审核不通过状态的订单，需要校验是否存在正在执行的播出单
		List<Order> checkOrderStartTimeList = new ArrayList<Order>();
		try{
			//查询需要删除的订单信息
			List<Order> orderList = orderService.findOrderListByIds(ids);
			String state = "";
			int delId  = 0;
			for(Order order : orderList){
				state = order.getState();
				delId = order.getId();
				if(Constant.ORDER_PENDING_CHECK.equals(state)){
					delIds = delIds+delId+",";
				}else if(Constant.ORDER_PENDING_CHECK_UPDATE.equals(state)){
					updateStateIds = updateStateIds+delId+",";
					checkOrderStartTimeList.add(order);
				}else if(Constant.ORDER_CHECK_NOT_PASS.equals(state)){
					delIds = delIds+delId+",";
				}else if(Constant.ORDER_CHECK_NOT_PASS_UPDATE.equals(state)){
					updateStateIds = updateStateIds+delId+",";
					checkOrderStartTimeList.add(order);
				}else if(Constant.ORDER_PUBLISHED.equals(state)){
					publishStateIds = publishStateIds+delId+",";
				}
			}
			
			int aheadTime = Integer.parseInt(baseConfigService.getBaseConfigByCode("orderOpAheadTime"));
			String startTime = "";
			Date start;
			long longTime = new Date().getTime();
			for(Order order : checkOrderStartTimeList){
				String ployStartTime = order.getPloyStartTime();
				if("0".equals(ployStartTime)){
					ployStartTime = "00:00:00";
				}
				startTime = order.getStartDateStr() + " " + ployStartTime;
				start = Transform.StringtoCalendar(startTime).getTime();
				if ((longTime - start.getTime()) > aheadTime * 1000) {// 订单已执行
					message = "delete.failed.order.is.run";//1选择的订单包括正在执行的订单，不能进行删除操作！
					return SUCCESS;
				}
			}
			
			if(delIds.length()>0){
				delIds = delIds.substring(0,delIds.length()-1);
				orderService.deleteOrderByIds(delIds);
			}
			
			Integer userId = this.getUserId();
			//修改待审核状态、修改审核不通过状态订单处理
			if(updateStateIds.length()>0){
				updateStateIds = updateStateIds.substring(0,updateStateIds.length()-1);

				String[] updateIds = updateStateIds.split(",");
				
				for (String pId : updateIds) {
					// 根据播出单恢复订单信息
					PutInPlayListBean pBean = playList4OrderService
							.getPutInPlayListByOrderId(Integer.parseInt(pId));
					if (pBean != null) {
						orderService.restoreOrder(pBean,
								Constant.ORDER_PENDING_CHECK_DELETE,userId);
					} else {
						List<RequestPlayListBean> rBeans = playList4OrderService
								.getRequestPlayListByOrderId(Integer.parseInt(pId));
						orderService.restoreOrder(rBeans,
								Constant.ORDER_PENDING_CHECK_DELETE,userId);
					}
				}
			}
			
			//发布状态的订单处理
			if(publishStateIds.length()>0){
				publishStateIds = publishStateIds.substring(0,publishStateIds.length()-1);
				
				String[] pubIds = publishStateIds.split(",");
				for (String pId : pubIds) {
					orderService.updateOrderState(Integer.parseInt(pId),Constant.ORDER_PENDING_CHECK_DELETE, userId);

				}
			}
			message = "common.delete.success";//0删除成功！
		}catch(ParseException pe){
			message = "common.delete.failed";//-1删除失败，检查订单是否正在执行出现异常！
			operResult = Constant.OPERATE_FAIL;
			pe.printStackTrace();
			log.error("***** OrderAction delOrder occur a exception: "+pe);
		}catch(Exception e){
			message = "common.delete.failed";//删除失败，检查订单是否正在执行出现异常！
			operResult = Constant.OPERATE_FAIL;
			log.error("***** OrderAction delOrder occur a exception: "+e);
			e.printStackTrace();
		}finally{
			delInfo.append("共").append(ids.split(",").length).append("条记录");
			operType = "operate.delete";
			operInfo = delInfo.toString();
			operLog = this.setOperationLog(Constant.OPERATE_MODULE_ORDER);
			operateLogService.saveOperateLog(operLog);
		}
		order = null;
		return this.queryOrderList();
	}
	
	/**
	 * 查询待审核的订单列表
	 * */
	public String queryOrderAuditList() {
		try {
			getLoginUser();
			if(page == null){
				page = new PageBeanDB();
			}
			page = orderService.queryOrderAuditList(order,page.getPageNo(), page.getPageSize());
			
			customerList = customerService.getCustomerList(null);
		} catch (Exception e) {
			log.error("查询待审核的订单列表出现异常", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 查看审核订单详情
	 * */
	public String getOrderAuditDetail() {
		try {
			order = orderService.getOrderById(order.getId());
			advertPosition = positionService.getAdvertPosition(order.getPositionId());
			insertOrderMateRelTmp(order.getOrderCode(), order.getPloyId(),order.getPositionId());
//			Gson gson = new Gson();
//			//获取订单对应的素材信息
//			OrderMaterialRelation omr = new OrderMaterialRelation();
//			omr.setOrderId(order.getId());
//			List<OrderMaterialRelation> relList = orderService.getOrderMaterialRelList(omr,advertPosition.getPositionCode());
//			getRequest().setAttribute("materialJson", gson.toJson(relList));
//			
//			String ployJson = "";
//			//获取广告位包类型
//			Integer adPackageType = positionService.getPackageTypeByAdId(order.getPositionId());
//			if(Constant.POSITION_TYPE_ONE_NOT_REAL_TIME==adPackageType.intValue()
//					|| Constant.POSITION_TYPE_ONE_REAL_TIME == adPackageType.intValue()
//					|| Constant.POSITION_TYPE_ONE_DATA_BROADCASTING == adPackageType.intValue()){
//				//单向广告位
//				//根据策略ID获取分策略的json信息
//				ployJson = orderService.getSubPloyJson(order.getPloyId());
//			}else{
//				//双向广告位
//				//根据策略ID获取精准的json信息
//				ployJson = orderService.queryPrecises4Json(order.getPloyId());
//				
//			}
//			getRequest().setAttribute("adPackageType", adPackageType);
//			getRequest().setAttribute("ployJson", ployJson);
//			
//			//返回广告位的JSON信息，用于预览
//			getRequest().setAttribute("positionJson", gson.toJson(advertPosition));
			
			//add by ly 用于预览   因为预览时只有后面三个参数有用    故省略部分信息 用1_2_3_4_5代替
			advertPosition = positionService.getAdvertPosition(order.getPositionId());
			//String pid=advertPosition.getId().toString();
			//String pname=advertPosition.getPositionName();
			String pbackgroundPath=advertPosition.getBackgroundPath();
			String pcoordinate=advertPosition.getCoordinate();
			String pwidthHeight=advertPosition.getWidthHeight();
			previewValue="1_2_3_4_5_"+pbackgroundPath+"_"+pcoordinate+"_"+pwidthHeight;
			getRequest().setAttribute("previewValue", previewValue);

			if(!"03001".equals(advertPosition.getPositionCode())){//非DTV广告位，检查播出单信息
				String areaPosition = ConfigureProperties.getInstance().getValueByKey("area.position");
				if(areaPosition.indexOf(advertPosition.getPositionCode()) < 0){//不按区域区分的广告位
					//检查待审核订单是否符合规则，不符合规则，则设置默认审核意见
					checkPlayList(order);
				}
			}
			
		} catch (Exception e) {
			log.error("查询订单详情出现异常", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 检查待审核订单是否符合规则，不符合规则，则设置默认审核意见
	 */
	private void checkPlayList(Order order) throws Exception{
//		String sTime = Transform.date2String(order.getStartTime(), "yyyy-MM-dd") + " " + order.getPloyStartTime();
//		Date date = Transform.string2Date(sTime, "yyyy-MM-dd HH:mm:ss");
//		
//		//投放式订单的提前量
//		Integer aheadTime = 0;
//		if (order.getOrderType() == Constant.ADVERT_POSITION_WAY_ADVERT) {
//			aheadTime = new Integer(baseConfigService.getBaseConfigByCode("orderOpAheadTime"));
//		}
		//开始时间小于当前时间，审核不通过
		/*if((date.getTime()+aheadTime*1000) < new Date().getTime()){
			if(Constant.ORDER_PENDING_CHECK_UPDATE.equals(order.getState())){
				//修改待审核状态订单
				String result = checkPlayList4Upd1(order);
				if("0".equals(result)){
					order.setUpdateFlag(1);
				}else if("-1".equals(result)){
					order.setOpinion("服务器异常，检查失败，请稍后重试！");
				}else if("1".equals(result)){
					order.setOpinion("未执行的订单，修改后的开始时间小于当前时间，审核失败！");
				}else if("2".equals(result)){
					order.setOpinion("已执行的订单，结束时间小于当前时间，审核失败！");
				}else if("3".equals(result)){
					order.setOpinion("订单正在执行，不能修改除结束日期以外的内容，审核失败！");
				}else{
					order.setOpinion("与订单"+result+"冲突，审核失败！");
				}
			}else{
				//订单为待审核状态和删除待审核状态
				order.setOpinion("订单的开始时间小于当前时间，审核失败！");
			}
		}else{*/
			if(Constant.ORDER_PENDING_CHECK.equals(order.getState())){
				//待审核状态订单
				String result = checkPlayList4Add(order);
				if("0".equals(result)){

				}else if("-1".equals(result)){
					order.setOpinion("服务器异常，检查失败，请稍后重试！");
				}else{
					order.setOpinion("与订单"+result+"冲突，审核失败！");
				}
				
			}else if(Constant.ORDER_PENDING_CHECK_UPDATE.equals(order.getState())){
				//修改待审核状态订单
				String result = checkPlayList4Upd(order);
				
				if("0".equals(result)){
					order.setUpdateFlag(0);
				}else if("-1".equals(result)){
					order.setOpinion("服务器异常，检查失败，请稍后重试！");
				}else if("1".equals(result)){
					order.setUpdateFlag(1);
					//order.setOpinion("订单正在执行，不能修改除结束日期以外的内容，审核失败！");
				}else{
					order.setOpinion("与订单"+result+"冲突，审核失败！");
				}
			}
		//}
	}
	
	/**
	 * 当订单开始时间<当前时间时 验证订单时间是否符合规则，策略在排期范围内是否与播出单冲突
	 * 时间符合规则情况：订单修改时间>播出单开始时间&&订单结束时间>当前时间
	 * 时间不符合规则情况:(1)订单修改时间>播出单开始时间&&订单结束时间<当前时间 (2)订单修改时间<播出单开始时间
	 * 参数：id,orderNo,startDate, endDate, endTime,modifyTime, ployId, orderType
	 * 0-通过 1-未执行的订单，修改后的开始时间小于当前时间，审核失败 2-已执行的订单，结束时间小于当前时间，审核失败，3-已执行的订单，修改了除结束日期以外的内容，审核失败  orderNo-策略时间冲突
	 * */
	public String checkPlayList4Upd1(Order order) {
		Integer re = 0;
		String oNo = "";
		try {
			
			int con = playList4OrderService.checkPlayListStartTime(order.getId(),new Date(), order.getOrderType());
			if(con == 0){
				re = 1;
			}else{
				con = playList4OrderService.checkPlayListStartTime(order.getId(),
						order.getModifyTime(), order.getOrderType());
				if (con == 0) {
					re = 3;
				} else {
					SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
					Integer c = this.compareDate(sf.format(order.getEndTime()), order.getPloyEndTime());
					if (c != null) {
						if (c == 1) {
							re = 2;
						} else {
							oNo = playList4OrderService.getPlayListOrderNo(order.getPloyId(),
									order.getStartTime(), order.getEndTime(), order.getOrderType(), order.getId());
						}
					} else {
						return "-1";
					}
				}
			}
			if (StringUtils.isBlank(oNo)) {
				return re.toString();
			} else {
				return oNo;
			}
		} catch (Exception e) {
			log.error("验证更新订单更新内容是否符合要求时出现异常", e);
		}

		return "-1";
	}
	
	/**
	 * 为新增订单检查绑定的策略在排期内是否与播出单存在冲突
	 * */
	public String checkPlayList4Add(Order order) {
		String oNo = "";
		try {
			oNo = playList4OrderService.getPlayListOrderNo(order.getPloyId(),
					order.getStartTime(), order.getEndTime(), order.getOrderType());
			if (StringUtils.isBlank(oNo)) {
				return "0";
			} else {
				return oNo;
			}
		} catch (Exception e) {
			log.error("为新增订单绑定策略在排期范围内是否与播出单存在冲突时出现异常", e);
		}
		return "-1";
	}
	
	/**
	 * 当订单开始时间>当前时间时 验证订单时间是否符合规则，策略在排期范围内是否与播出单冲突 时间符合规则情况：播出单开始时间>当前时间
	 * 时间不符合规则情况:播出单开始时间<当前时间 参数：id,startDate,endDate,ployId,orderType
	 * */
	public String checkPlayList4Upd(Order order) {
		Integer re = 0;
		String oNo = "";

		try {
			Integer oType = playList4OrderService.getTypeByOrderId(order.getId());
			if (oType != null) {
				re = playList4OrderService.checkPlayListStartTime(order.getId(),
						new Date(), oType);
				if (re == 0) {
					oNo = playList4OrderService.getPlayListOrderNo(order.getPloyId(),
							order.getStartTime(), order.getEndTime(), oType, order.getId());
				}

				if (StringUtils.isBlank(oNo)) {
					return re.toString();
				} else {
					return oNo;
				}
			} else {
				log.info("根据订单编号在播出单内未查到记录，数据异常");
				return "-1";
			}
		} catch (Exception e) {
			log.error("验证更新订单更新内容是否符合要求时出现异常", e);
		}
		return "-1";
	}
	
	/**
	 * 给定的日期和时间与当前日期比较 0-大于 1-小于
	 * */
	private Integer compareDate(String endDate, String endTime) {
		Date end;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			end = df.parse(endDate + " " + endTime);
			if (end.compareTo(new Date()) > 0) {
				return 0;
			} else {
				return 1;
			}
		} catch (ParseException e) {
			log.error("比较日期出现异常", e);
		}
		return null;
	}
	 public   static  String getDoGetURL(String url, String charset) throws  Exception {   
		   try
		   {
		        HttpClient client =  new  HttpClient();   
		        GetMethod method1 =  new  GetMethod(url);  
		  
		         if  ( null  == url || !url.startsWith( "http" )) {   
		             throw   new  Exception( "请求地址格式不对" );   
		        }   
		  
		         // 设置请求的编码方式   
		         if  ( null  != charset) {   
		            method1.addRequestHeader( "Content-Type" ,   
		                     "application/x-www-form-urlencoded; charset="  + charset);   
		        }  else  {   
		            method1.addRequestHeader( "Content-Type" ,   
		                     "application/x-www-form-urlencoded; charset="  +  "utf-8" );   
		        }   
		         client.setConnectionTimeout(1000);
		         int  statusCode = client.executeMethod(method1);   
		  
		         if  (statusCode != HttpStatus.SC_OK) { // 打印服务器返回的状态   
		            //System.out.println( "Method failed: "  + method1.getStatusLine());   
		         }   
		        method1.releaseConnection();   
		   }
		   catch(Exception e){
			   
		   }
	        return  "";   
	    }
	/**
	 * 提交订单审核结果 id,orderNo,orderType,pass
	 * */
	public void checkOrder() {
		int isAdd = 0;
		try {
			/** 通过标识，0-审核通过，1-审核驳回 */
			Integer pass = new Integer(getRequest().getParameter("pass"));
			/** 修改标识，0-全部修改，1-修改结束时间 */
			Integer updateFlag = new Integer(getRequest().getParameter("updateFlag"));
			order = orderService.getOrderById(id);
			String state = order.getState();
			int orderType = order.getOrderType();
			String opinion = getRequest().getParameter("opinion");
			String oState = Constant.ORDER_PUBLISHED;
			
			
			
			int auditState = -1;//审核日志状态
			if (state.equals(Constant.ORDER_PENDING_CHECK)) {// 未发布待审核的订单
				if (pass == 1) {// 不通过
					oState = Constant.ORDER_CHECK_NOT_PASS;
					auditState = Constant.AUDIT_LOG_STATUS_NOT_PASS;
				} else {// 通过
					isAdd = 1;
					auditState = Constant.AUDIT_LOG_STATUS_PASS;
				}
			} else if (state.equals(Constant.ORDER_PENDING_CHECK_UPDATE)) {// 修改待审核的订单
				if (pass == 1) {// 不通过
					oState = Constant.ORDER_CHECK_NOT_PASS_UPDATE;
					auditState = Constant.AUDIT_LOG_STATUS_UPDATE_NOT_PASS;
				}else{
					auditState = Constant.AUDIT_LOG_STATUS_UPDATE_PASS;
				}
			} else if (state.equals(Constant.ORDER_PENDING_CHECK_DELETE)) {// 删除待审核的订单
				if (pass == 1) {// 不通过
					oState = Constant.ORDER_CHECK_NOT_PASS_DELETE;
					auditState = Constant.AUDIT_LOG_STATUS_DELETE_NOT_PASS;
				} else {// 通过
					oState = null;
					auditState = Constant.AUDIT_LOG_STATUS_DELETE_PASS;
				}
			}
			if (oState != null) {//非删除审核通过
				int flag = 0;
				if (oState == Constant.ORDER_PUBLISHED) {// 待审核、修改待审核通过，同时更新播出单
					
					advertPosition = positionService.getAdvertPosition(order.getPositionId());
					if(!this.ftp2DTVServer(id, advertPosition)){//非DTV广告位
//						if(checkFileSize(advertPosition)){//检查素材文件大小
							if (isAdd == 1) {// 新增
								if (orderType==Constant.PUT_IN_ORDER) {// 投放式
									/** 新增播出单 */
									flag = playListGisService.insertPlayList(id);
								} else {// 请求式
									flag = playListReqService.insertPlayList(id);
								}
							} else {// 更新
								if (orderType==Constant.PUT_IN_ORDER) {// 投放式
									/** 删除存在播出单，新增播出单 */
									if (updateFlag == 0) {
										flag = playListGisService.updateAllPlayList(id);
									} else {
										flag = playListGisService.updatePlayListByDate(order);
									}
								} else {// 请求式
									if (updateFlag == 0) {
										flag = playListReqService.updateAllPlayList(id);
									} else {
										flag = playListReqService.updatePlayListEndDate(order);
									}
								}
							}
//						}else{//单向实时订单有效期内的素材总大小大于相应的配置值
//							return;
//						}
					}
				}
				if (flag == 0) {
					orderService.updateOrderState(id, oState, null);
				} else {
					//renderText("-1");
					renderText("请绑定素材");
					return ;
				}
			} else {// 删除通过
				int flag = 0;
				/** 删除播出单 */
				if (orderType==Constant.PUT_IN_ORDER) {//投放式
					flag = playListGisService.deleteAllPlayList(id);
				} else {//请求式
					flag = playListReqService.deletePlayList(id);
				}
				if (flag == 0) {
					orderService.deleteOrderByIds(id.toString());
				} else {
					renderText("-1");
					return ;
				}
			}
			
			try
			{
				advertPosition = positionService.getAdvertPosition(order.getPositionId());
				if (ConstantsAdsCode.REQ_ADVERT_POSITION.indexOf(advertPosition.getPositionCode())>0)
				{
					ConfigureProperties config = ConfigureProperties.getInstance();
					String serverurl=config.getValueByKey("policyserver1");
					getDoGetURL(config.getValueByKey("policyserver1"),null);
					getDoGetURL(config.getValueByKey("policyserver2"),null);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			//添加审核日志
			saveAuditLog(id, opinion, auditState);
			renderText("0");
		} catch (Exception e) {
			log.error("提交订单审核结果出现异常", e);
			renderText("-1");
		}
	}
	
	/**
	 * 检查文件大小
	 * @param order
	 * @return
	 */
	private boolean checkFileSize(AdvertPosition advertPosition){
		PositionPackage adPackage = positionService.getPositionPackageById(advertPosition.getPositionPackageId());
		if(adPackage.getPositionPackageType().intValue() == Constant.POSITION_TYPE_ONE_REAL_TIME){
			long oneRealTimeFileSize = Long.parseLong(baseConfigService.getBaseConfigByCode("oneRealTimeFileSize"));
			
			long fileSize = 0;
			//音频类广告位，检查素材文件总大小
			String selResource = getRequest().getParameter("selResource");
			String mateIds = "";
			if(StringUtils.isNotBlank(selResource)){
				List<OrderMaterialRelation> relList = orderService.getOrderMaterialRelation(selResource);
				
				for(OrderMaterialRelation rel : relList){
					mateIds += rel.getMateId()+",";
				}
				if(mateIds.endsWith(",")){
					mateIds = mateIds.substring(0,mateIds.length()-1);
				}
				//获取素材ID与文件大小关系
				Map<Integer,String> mateMap = playListGisService.getImageMateFileSizeMap(mateIds);
				Collection<String> list = mateMap.values();
				for(String str : list){
					fileSize += Long.parseLong(str);
				}
			}
			fileSize += orderService.getFileSumSize(Constant.POSITION_TYPE_ONE_REAL_TIME,order,mateIds);
			if(fileSize >= oneRealTimeFileSize){
				renderText("单向实时订单有效期内的素材总大小大于"+oneRealTimeFileSize+"字节，审核失败！");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 针对DTV订单，需要将素材FTP到DTV服务器:
	 * 策略对应的素材FTP到所有地市，
	 * 精准素材发送到精准对应的地市。
	 * @return true DTV广告位  false 非DTV广告位
	 */
	private boolean ftp2DTVServer(Integer orderId,AdvertPosition advertPosition){
		if("03001".equals(advertPosition.getPositionCode())){
			orderService.ftp2DTVServer(orderId);
			return true;
		}
		return false;
	}

	/**
	 * 添加审核日志
	 * @param orderId
	 * @param opinion
	 * @param auditState
	 */
	private void saveAuditLog(Integer orderId,String opinion, int auditState) {
		AuditLog auditLog = new AuditLog();
		auditLog.setRelationType(Constant.AUDIT_RELATION_TYPE_ORDER);
		auditLog.setRelationId(orderId);
		auditLog.setState(auditState);
		auditLog.setOperatorId(getUserId());
		auditLog.setAuditOpinion(opinion);
		auditLogService.saveAuditLog(auditLog);
	}
	
	/**
	 * 查询订单审核日志
	 * @return
	 */
	public String queryOrderAuditLog(){
		try{
			if(page == null){
				page = new PageBeanDB();
			}
			page = auditLogService.queryAuditLogList(auditLog, page.getPageNo(), page.getPageSize());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 根据素材ID获取素材信息
	 */
	public void getMaterialJsonById(){
		try{
			String mateJson = orderService.getMaterialJsonById(id);
			renderJson(mateJson);
		}catch(Exception e){
			e.printStackTrace();
			renderJson("-1");
		}
	}
	
	/**
	 * 根据订单ID获取素材信息
	 */
	public void getSelectMaterialJsonByOrderId(){
		try{
			String orderCode= getRequest().getParameter("orderId");
			String mateJson = orderService.getSelectMaterialJsonByOrderId(orderCode);
			renderJson(mateJson);
		}catch(Exception e){
			e.printStackTrace();
			renderJson("-1");
		}
	}
	
	/**
	 * 保存问卷订单代办已阅记录
	 * @return
	 */
	public String saveRealQuestionnaire(){
		orderService.saveRealQuestionnaire(order.getId());
		return SUCCESS;
	}
	
	/**
	 * 进入选择订单素材页面
	 * @return
	 */
	public String initAreaResource() {
		String returnStr = "";
		if(page == null){
			page = new PageBeanDB();
		}
		try{
			int positionId = order.getPositionId().intValue();
			if( positionId == 21 || positionId == 22|| positionId == 44 ){
				//按区域选择素材（菜单视频外框广告位、热点推荐页面广告位）
				returnStr = "area";
				pageReleaseLocation = ployService.queryCityAreaList(null, 1, 100);
				page = orderService.queryAreaResourceList(omRelTmp,page.getPageNo(), page.getPageSize());
			}
			//开机广告位
			else if(positionId == 1 || positionId == 2){
				pageReleaseLocation = ployService.queryCityAreaList(null, 1, 100);
				returnStr = "boot";
				page = orderService.queryBootPicResourceList(omRelTmp, page.getPageNo(), page.getPageSize());
				
			}
			else if( positionId == 45 || positionId == 46){
				returnStr = "area";
				pageReleaseLocation = ployService.queryCityAreaList(null, 1, 100);
				page = orderService.queryTheAreaResourceList(omRelTmp, order.getPloyId(),page.getPageNo(), page.getPageSize());
			}else if(positionId == 3 || positionId == 4 ){
				//轮询菜单图片广告位（菜单图片广告位）
				pageReleaseLocation = ployService.queryCityAreaList(null, 1, 100);
				returnStr = "loopMenu";
				page = orderService.queryAreaResourceList(omRelTmp,page.getPageNo(), page.getPageSize());
			}else if(positionId == 5 || positionId == 6 || positionId == 7 || positionId == 8
					|| positionId == 9 || positionId == 10 || positionId == 11 || positionId == 12 
					 || positionId == 41 || positionId == 42 ){
				//按时间段、区域、频道组选择素材（导航条广告、快捷切换列表广告、音量条广告、预告提示广告、广播收听背景广告）
				pageReleaseLocation = ployService.queryCityAreaList(null, 1, 100);
				channelGroupList = orderService.getChannelGroupListByPloyId(order.getPloyId());
				returnStr = "timeAreaGroup";
				page = orderService.queryAreaResourceList(omRelTmp,page.getPageNo(), page.getPageSize());
			}
			//广播背景
			else if(positionId == 13 || positionId == 14){
				pageReleaseLocation = ployService.queryCityAreaList(null, 1, 100);
				channelGroupList = orderService.getChannelGroupListByPloyId(order.getPloyId());
				returnStr = "timeAreaGroup";
				page = orderService.queryRadioResourceList(omRelTmp,page.getPageNo(), page.getPageSize());
			}
			else if(positionId == 15 || positionId == 16){
				//回看回放菜单广告
				returnStr = "look";
				page = orderService.queryLookResourceList(omRelTmp,page.getPageNo(), page.getPageSize());
			//}else if(positionId == 29 || positionId == 40 || positionId == 43){
			}else if(positionId == 29 || positionId == 40 || positionId == 43){
				//高清点播片头插播广告、高清回放插播广告、高清回看插播广告
				returnStr = "instream";
				page = orderService.queryInstreamResourceList(omRelTmp,positionId,page.getPageNo(), page.getPageSize());
			}else if(positionId == 19 || positionId == 20 || positionId == 39){
				//回看回放暂停广告
				returnStr = "pause";
				page = orderService.queryPauseResourceList(omRelTmp,positionId,page.getPageNo(), page.getPageSize());
			}else if(positionId == 31 || positionId == 32 
					|| positionId == 33 || positionId == 34
					|| positionId == 35 || positionId == 36||positionId==27||positionId==28){
				//点播暂停广告、点播挂角广告、点播游动字幕广告
				returnStr = "request";
				page = orderService.queryReqResourceList(omRelTmp,page.getPageNo(), page.getPageSize());
			}else if(positionId == 17){
				channelGroupList = orderService.getNPVRChannelGroupListByPloyId(order.getPloyId());
				returnStr = "timeGroup";
				page = orderService.queryNPVRAreaResourceList(omRelTmp,page.getPageNo(), page.getPageSize());			
			}else if(positionId == 25 || positionId == 26 ){
				//点播随片图片广告
				returnStr = "lookvod";
				page = orderService.queryLookResourceListbyPre(omRelTmp,page.getPageNo(), page.getPageSize());
			}else if(positionId == 23 || positionId == 24){
				//点播菜单广告
				returnStr = "vod";
				page = orderService.queryLookResourceList(omRelTmp,page.getPageNo(), page.getPageSize());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
 		return returnStr;
	}
	public String getSelectedResource() {
		//initAreaResource();
		//omRelTmp.setContain("1");
		omRelTmp.setNotNull(true);
		String returnStr = "";
		try{
			if(page == null){
				page = new PageBeanDB();
			}
			int positionId = order.getPositionId().intValue();
			mateId=getRequest().getParameter("omRelTmp.mateId");
			//System.out.println("mateId="+mateId);
			//开机图片广告
			if(positionId == 1 || positionId == 2){
				page = orderService.queryBootResourceDetailList(omRelTmp,page.getPageNo(), page.getPageSize());
				pageReleaseLocation = ployService.queryAreaList(null, 1, 100);
				returnStr = "boot";
			}
			else if(positionId == 21 || positionId == 22
					|| positionId == 44 || positionId == 45 || positionId == 46 ){
				//按区域选择素材（开机广告位）
				returnStr = "areaDetail";
				pageReleaseLocation = ployService.queryAreaList(null, 1, 100);
				page = orderService.queryAreaResourceList(omRelTmp,page.getPageNo(), page.getPageSize());
//				List list=page.getDataList();
//				for (int i = list.size()-1; i >=0; i--) {
//					OrderMaterialRelationTmp orderMaterialRelationTmp = (OrderMaterialRelationTmp) list.get(i);
//					if(orderMaterialRelationTmp.getMateId()!=Integer.parseInt(mateId) ){
//						list.remove(orderMaterialRelationTmp);
//					}
				//}
			}else if(positionId == 3 || positionId == 4 ){
				//轮询菜单图片广告位（菜单图片广告位）
				pageReleaseLocation = ployService.queryAreaList(null, 1, 100);
				returnStr = "loopMenuDetail";
				page = orderService.queryAreaResourceList(omRelTmp,page.getPageNo(), page.getPageSize());
			}else if(positionId == 5 || positionId == 6 || positionId == 7 || positionId == 8
					|| positionId == 9 || positionId == 10 || positionId == 11 || positionId == 12 
					|| positionId == 13 || positionId == 14 || positionId == 41 || positionId == 42 ){
				//按时间段、区域、频道组选择素材（导航条广告、快捷切换列表广告、音量条广告、预告提示广告、广播收听背景广告）
				pageReleaseLocation = ployService.queryAreaList(null, 1, 100);
				channelGroupList = orderService.getChannelGroupListByPloyId(order.getPloyId());
				returnStr = "timeAreaGroupDetail";
				page = orderService.queryAreaResourceList(omRelTmp,page.getPageNo(), page.getPageSize());
			}else if(positionId == 15 || positionId == 16){
				//回看回放菜单广告
				returnStr = "lookDetail";
				page = orderService.queryLookResourceList(omRelTmp,page.getPageNo(), page.getPageSize());

			}else if(positionId == 29 || positionId == 40 || positionId == 43){
				//高清点播片头插播广告、高清回放插播广告、高清回看插播广告
				returnStr = "instreamDetail";
				page = orderService.queryInstreamResourceList(omRelTmp,positionId,page.getPageNo(), page.getPageSize());

			}else if(positionId == 19 || positionId == 20 || positionId == 39){
				//回看回放暂停广告
				returnStr = "pauseDetail";
				page = orderService.queryPauseResourceList(omRelTmp,positionId,page.getPageNo(), page.getPageSize());

			}else if(positionId == 31 || positionId == 32 
					|| positionId == 33 || positionId == 34
					|| positionId == 35 || positionId == 36){
				//点播暂停广告、点播挂角广告、点播游动字幕广告
				returnStr = "requestDetail";
				page = orderService.queryReqResourceList(omRelTmp,page.getPageNo(), page.getPageSize());

			}else if(positionId == 17){
				channelGroupList = orderService.getNPVRChannelGroupListByPloyId(order.getPloyId());
				returnStr = "timeGroupDetail";
				page = orderService.queryNPVRAreaResourceList(omRelTmp,page.getPageNo(), page.getPageSize());

			}else if(positionId == 23 || positionId == 24){
				//、点播菜单广告、
				//returnStr = "lookvodDetail";
				returnStr = "vodDetail";
				page = orderService.queryLookResourceList(omRelTmp,page.getPageNo(), page.getPageSize());
			}else if(positionId == 25 || positionId == 26){
				//点播随片图片广告
				//returnStr = "vodDetail";
				returnStr = "lookvodDetail";
				page = orderService.queryLookResourceList(omRelTmp,page.getPageNo(), page.getPageSize());
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return returnStr;
	}
	/**
	 * 添加订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 */
	public void insertOrderMateRelTmp(){
		try{
			String orderCode = getRequest().getParameter("orderCode");
			String ployId = getRequest().getParameter("ployId");
			String positionId = getRequest().getParameter("positionId");
			insertOrderMateRelTmp(orderCode, Integer.parseInt(ployId), Integer.parseInt(positionId));
			renderText("0");
		}catch(Exception e){
			renderText("-1");
			e.printStackTrace();
		}
	}
	public void insertOrderMateRelTmp2(){
		try{
			String orderCode = getRequest().getParameter("orderCode");
			String ployId = getRequest().getParameter("ployId");
			String positionId = getRequest().getParameter("positionId");
			insertOrderMateRelTmp2(orderCode, Integer.parseInt(ployId), Integer.parseInt(positionId));
			renderText("0");
		}catch(Exception e){
			renderText("-1");
			e.printStackTrace();
		}
	}

	/**
	 * 添加订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 * @param positionId
	 */
	private void insertOrderMateRelTmp(String orderCode, int ployId, int positionId) {
		
		//开机图片广告
		if(positionId==1 || positionId == 2){
			orderService.insertBootOrderMateRelTmp(orderCode, ployId);
		}
		//轮询菜单图片广告位
		else if(positionId==3 || positionId == 4){
			advertPosition = positionService.getAdvertPosition(positionId);
			orderService.loopMenuPosition(orderCode, ployId, advertPosition.getLoopCount());
		}
		//点播随片图片广告
		else if(positionId == 25 || positionId == 26){
			orderService.insertFollowOrderMateRelTmp(orderCode, ployId,positionId);
		}
		else if(positionId == 15 || positionId == 16 ||positionId == 23 ||positionId == 24){
			//点播菜单广告
			orderService.insertLookBackOrderMateRelTmp(orderCode, ployId,positionId);
		}else if( positionId == 17){
			//回放菜单广告
			orderService.insertLookRepalyOrderMateRelTmp(orderCode, ployId,positionId);
		}else if(positionId == 29 || positionId == 40 || positionId == 43){
			//回看回放插播广告
			String instreamNumber = baseConfigService.getBaseConfigByCode("instreamNumber");
			orderService.insertInstreamOrderMateRelTmp(orderCode, ployId,Integer.parseInt(instreamNumber));
		}else if(positionId == 19 || positionId == 20 || positionId == 39){
			//回看回放暂停广告
			orderService.insertPauseOrderMateRelTmp(orderCode, ployId);
		}else if(positionId == 31 || positionId == 32 
				|| positionId == 33 || positionId == 34
				|| positionId == 35 || positionId == 36
				||positionId == 27||positionId == 28){
			//点播暂停广告、点播挂角广告、点播游动字幕广告
			orderService.insertReqOrderMateRelTmp(orderCode, ployId);
		}else{
			orderService.insertOrderMateRelTmp(orderCode, ployId,positionId);
		}
		//修改全时段的开始、结束时段
		orderService.updateAllTimeOrderMateRelTmp();
	}
private void insertOrderMateRelTmp2(String orderCode, int ployId, int positionId) {
		
		//开机图片广告
		if(positionId==1 || positionId == 2){
			orderService.insertBootOrderMateRelTmp2(orderCode, ployId);
		}
		//轮询菜单图片广告位
		else if(positionId==3 || positionId == 4){
			advertPosition = positionService.getAdvertPosition(positionId);
			orderService.loopMenuPosition2(orderCode, ployId, advertPosition.getLoopCount());
		}
		//点播随片图片广告
		else if(positionId == 25 || positionId == 26){
			orderService.insertFollowOrderMateRelTmp2(orderCode, ployId,positionId);
		}
		else if(positionId == 15 || positionId == 16 ||positionId == 23 ||positionId == 24){
			//点播菜单广告
			orderService.insertLookBackOrderMateRelTmp2(orderCode, ployId,positionId);
		}else if( positionId == 17){
			//回放菜单广告
			orderService.insertLookRepalyOrderMateRelTmp2(orderCode, ployId,positionId);
		}else if(positionId == 29 || positionId == 40 || positionId == 43){
			//回看回放插播广告
			String instreamNumber = baseConfigService.getBaseConfigByCode("instreamNumber");
			orderService.insertInstreamOrderMateRelTmp2(orderCode, ployId,Integer.parseInt(instreamNumber));
		}else if(positionId == 19 || positionId == 20 || positionId == 39){
			//回看回放暂停广告
			orderService.insertPauseOrderMateRelTmp2(orderCode, ployId);
		}else if(positionId == 31 || positionId == 32 
				|| positionId == 33 || positionId == 34
				|| positionId == 35 || positionId == 36){
			//点播暂停广告、点播挂角广告、点播游动字幕广告
			orderService.insertReqOrderMateRelTmp2(orderCode, ployId);
		}else{
			orderService.insertOrderMateRelTmp2(orderCode, ployId,positionId);
		}
		//修改全时段的开始、结束时段
		orderService.updateAllTimeOrderMateRelTmp();
	}
	
	/**
	 * 保存订单和素材临时关系数据
	 */
	public void saveOrderMateRelTmp(){
		try{
			//ids = getRequest().getParameter("ids");
			String resourceId = getRequest().getParameter("resourceId");
			orderService.saveOrderMateRelTmp(ids, Integer.parseInt(resourceId));
			renderText("0");
		}catch(Exception e){
			renderText("-1");
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存   开机图片  订单和素材临时关系数据
	 */
	public void saveBootOrderMateRelTmp(){
		try{
			String orderCode = getRequest().getParameter("orderCode");
			String selectedAreas = getRequest().getParameter("selectedAreas");
			String materLocation = getRequest().getParameter("materLocation");
			orderService.saveBootOrderMateRelTmp(orderCode, selectedAreas, materLocation);
			renderText("0");
		}catch(Exception e){
			renderText("-1");
			e.printStackTrace();
		}
	}
	//预览广告位 
	public String preview(){
		previewValue=getRequest().getParameter("previewValue");
		resourceValue=getRequest().getParameter("resourceValue");
		//System.out.println(perviewValue);
		//System.out.println(resourcevalue);
		getRequest().setAttribute("previewValue", previewValue);
		getRequest().setAttribute("resourceValue", resourceValue);
		return "preview";		
	}
	
	
	
	
	/**
	 * 删除订单和素材临时关系数据
	 * @return
	 */
	public String delOrderMateRelTmp(){
		orderService.saveOrderMateRelTmp(ids, null);
		return this.initAreaResource();
	}
	public void delBootOrderMateRelTmp(){
		try{
			String orderCode = getRequest().getParameter("orderCode");
			String selectedAreas = getRequest().getParameter("selectedAreas");
			orderService.saveBootOrderMateRelTmp2(orderCode, selectedAreas, null);
			renderText("0");
		}catch(Exception e){
			renderText("-1");
			e.printStackTrace();
		}
	}
	/**
	 * 重新投放
	 */
	public void repush(){
		try{
			Integer orderId = Integer.parseInt(getRequest().getParameter("orderId"));
			playListGisService.repush(orderId);
			renderText("0");
		}catch(Exception e){
			renderText("-1");
			e.printStackTrace();
		}
	}
	
	
	
	public PageBeanDB getPage() {
		return page;
	}
	public void setPage(PageBeanDB page) {
		this.page = page;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public Ploy getPloy() {
		return ploy;
	}

	public void setPloy(Ploy ploy) {
		this.ploy = ploy;
	}

	public ResourceReal getResource() {
		return resource;
	}

	public void setResource(ResourceReal resource) {
		this.resource = resource;
	}

	public List<ResourceReal> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<ResourceReal> resourceList) {
		this.resourceList = resourceList;
	}

	public AdvertPosition getAdvertPosition() {
		return advertPosition;
	}

	public void setAdvertPosition(AdvertPosition advertPosition) {
		this.advertPosition = advertPosition;
	}

	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	public void setPlayList4OrderService(PlayList4OrderService playList4OrderService) {
		this.playList4OrderService = playList4OrderService;
	}

	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public void setPlayListGisService(PlayListGisService playListGisService) {
		this.playListGisService = playListGisService;
	}

	public void setPlayListReqService(PlayListReqService playListReqService) {
		this.playListReqService = playListReqService;
	}

	public void setAuditLogService(AuditLogService auditLogService) {
		this.auditLogService = auditLogService;
	}

	public AuditLog getAuditLog() {
		return auditLog;
	}

	public void setAuditLog(AuditLog auditLog) {
		this.auditLog = auditLog;
	}

	public void setBaseConfigService(BaseConfigService baseConfigService) {
		this.baseConfigService = baseConfigService;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	public int getRoleType() {
		return roleType;
	}

	public PageBeanDB getPageReleaseLocation() {
		return pageReleaseLocation;
	}

	public void setPageReleaseLocation(PageBeanDB pageReleaseLocation) {
		this.pageReleaseLocation = pageReleaseLocation;
	}

	public List<TChannelGroup> getChannelGroupList() {
		return channelGroupList;
	}

	public void setChannelGroupList(List<TChannelGroup> channelGroupList) {
		this.channelGroupList = channelGroupList;
	}

	public OrderMaterialRelationTmp getOmRelTmp() {
		return omRelTmp;
	}

	public void setOmRelTmp(OrderMaterialRelationTmp omRelTmp) {
		this.omRelTmp = omRelTmp;
	}

	public void setPloyService(PloyService ployService) {
		this.ployService = ployService;
	}
	

	

	

	public String getPreviewValue() {
		return previewValue;
	}

	public void setPreviewValue(String previewValue) {
		this.previewValue = previewValue;
	}

	public String getResourceValue() {
		return resourceValue;
	}

	public void setResourceValue(String resourceValue) {
		this.resourceValue = resourceValue;
	}

	public String getMateId() {
		return mateId;
	}

	public void setMateId(String mateId) {
		this.mateId = mateId;
	}

	
	

	
	
}
