package com.dvnchina.advertDelivery.position.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.service.OperateLogService;
import com.dvnchina.advertDelivery.model.ResourceReal;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.position.bean.DefaulResourceAD;
import com.dvnchina.advertDelivery.position.bean.PositionPackage;
import com.dvnchina.advertDelivery.position.service.DefaulResourceService;
import com.dvnchina.advertDelivery.position.service.PositionService;
import com.dvnchina.advertDelivery.sysconfig.service.BaseConfigService;
import com.google.gson.Gson;

public class DefaulResourceAction extends BaseAction{
	
	private static final long serialVersionUID = 4223103594985058266L;
	private DefaulResourceService defaulResourceService = null;
	private PositionService positionService = null;
	private BaseConfigService baseConfigService;
	private PageBeanDB page = null;
	private List<AdvertPosition> adList = null;
	private Integer positionPackageId;
	private String adResources;
	private List<ResourceReal> defResourceList = null;//可设置的默认素材列表
	private List<DefaulResourceAD> defResourceADList = null;//已经设置的默认素材列表
	private AdvertPosition advertPosition = null;
	private PositionPackage pPackage = null;
	private OperateLogService operateLogService = null;
	private OperateLog operLog = null;
	
	/**
	 * 查询默认素材
	 * @return
	 */
	public String queryResourceList(){
		
		try{
			if(page == null){
				page = new PageBeanDB();
			}
			page = defaulResourceService.queryResourceList(page.getPageNo(), page.getPageSize());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 根据广告位包查询子广告位列表
	 * @return
	 */
	public String queryADList(){
		
		try{
			adList = defaulResourceService.queryADList(id);
			for(AdvertPosition ad : adList){
				List<ResourceReal> resourceList = defaulResourceService.getDefResourceByAdId(ad.getId());
				String resourceNames  = "";
				if(resourceList != null && resourceList.size()>0){
					for(ResourceReal resource : resourceList){
						resourceNames += "<a href='javascript:showMaterial("+resource.getId()+","+ad.getId()+")'>"+resource.getResourceName()+"</a>,";
					}
					if(resourceNames.length()>0){
						resourceNames = resourceNames.substring(0,resourceNames.length()-1);
					}
				}
				ad.setResourceNames(resourceNames);
			}
			pPackage = positionService.getPositionPackageById(id);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	/**
	 * 查询默认素材列表
	 * @return
	 */
	public String queryDefResourceList(){
		
		try{
			advertPosition = positionService.getAdvertPosition(id);
			defResourceList = defaulResourceService.queryDefResourceListByAdId(id);
			defResourceADList = defaulResourceService.queryDefResourceADListByAdId(id);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 检查选择的素材是否合法
	 */
	public void checkResource() {
		String resourceIds = "";
		try {
			int bootPosition = Integer.parseInt(baseConfigService.getBaseConfigByCode("bootPosition"));
			
			//开机广告位
			if(positionPackageId.intValue() == bootPosition){
				String[] idArray = ids.split(",");
				for (String idStr : idArray) {
					resourceIds = resourceIds+Integer.valueOf(StringUtils.trim(idStr.split("_")[0]))+",";
				}
				if(resourceIds.length()>0){
					resourceIds = resourceIds.substring(0,resourceIds.length()-1);
				}
				List<ResourceReal> resourceList = defaulResourceService.findResourceListByIds(resourceIds);
				if(resourceList == null || resourceList.size() != 2){
					renderText("开机广告位只能选择一个视频和一个图片素材！");
				}else{
					boolean imageFlag = false;
					boolean videoFlag = false;
					for(ResourceReal resource : resourceList){
						int resourceType = resource.getResourceType().intValue();
						if(Constant.IMAGE == resourceType){
							imageFlag = true;
						}else if(Constant.VIDEO == resourceType){
							videoFlag = true;
						}
					}
					if(imageFlag && videoFlag){
						renderText("0");
					}else{
						renderText("开机广告位只能选择一个视频和一个图片素材！");
					}
				}
			}
			renderText("0");
		} catch (Exception e) {
			e.printStackTrace();
			renderText("-1");
		}
	}
	
	/**
	 * 保存默认素材配置
	 * @return
	 */
	public String saveDefResource(){
		StringBuffer saveInfo = new StringBuffer();
		saveInfo.append("子广告位：");
		Integer resourceId = null;
		try{
			Integer adId = id;
			advertPosition = positionService.getAdvertPosition(adId);
			saveInfo.append(adId).append(",").append(advertPosition.getPositionName()).append("：设置对应默认素材：");
			String[] idArray = ids.split(",");
			List<DefaulResourceAD> raList = new ArrayList<DefaulResourceAD>();
			for (String idStr : idArray) {
				DefaulResourceAD ra = new DefaulResourceAD();
				ra.setPositionPackageId(positionPackageId);
				ra.setAdId(adId);
				resourceId = Integer.valueOf(StringUtils.trim(idStr.split("_")[0]));
				ra.setResourceId(resourceId);
				raList.add(ra);
				saveInfo.append(resourceId).append(",").append(idStr.split("_")[1]).append(Constant.OPERATE_SEPARATE);
			}
			
//			String[] idArray = ids.split(",");
//			List<DefaulResourceAD> raList = new ArrayList<DefaulResourceAD>();
//			
//			for(String id : idArray){
//				DefaulResourceAD ra = new DefaulResourceAD();
//				ra.setPositionPackageId(positionPackageId);
//				ra.setAdId(adId);
//				ra.setResourceId(Integer.valueOf(id.trim()));
//				raList.add(ra);
//			}
			defaulResourceService.delResourceADByAdId(adId);
			defaulResourceService.saveDefResource(raList);
			message = "common.update.success";//修改成功
			
		}catch(Exception e){
			message = "common.update.failed";//修改失败
			operResult = Constant.OPERATE_FAIL;
			log.error("***** DefaulResourceAction saveDefResource occur a exception: "+e.getMessage());
		}finally{
			operType = "operate.update";
			operInfo = saveInfo.toString();
			operLog = this.setOperationLog(Constant.OPERATE_MODULE_DEF_RESOURCE);
			operateLogService.saveOperateLog(operLog);
		}
		return SUCCESS;
	}
	
	public void getAdvertPositionJson(){
		try{
			advertPosition = positionService.getAdvertPosition(id);
			if(advertPosition != null){
				Map<String,String> resultMap = new HashMap<String,String>();
				resultMap.put("result","true");
				resultMap.put("backgroundPath",advertPosition.getBackgroundPath());
				resultMap.put("coordinate",advertPosition.getCoordinate());
				resultMap.put("widthHeight",advertPosition.getWidthHeight());
				Gson gson = new Gson();
				renderHtml(gson.toJson(resultMap));
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	

	public PageBeanDB getPage() {
		return page;
	}

	public void setPage(PageBeanDB page) {
		this.page = page;
	}

	public void setDefaulResourceService(DefaulResourceService defaulResourceService) {
		this.defaulResourceService = defaulResourceService;
	}

	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	public List<AdvertPosition> getAdList() {
		return adList;
	}

	public void setAdList(List<AdvertPosition> adList) {
		this.adList = adList;
	}

	public Integer getPositionPackageId() {
		return positionPackageId;
	}

	public void setPositionPackageId(Integer positionPackageId) {
		this.positionPackageId = positionPackageId;
	}

	public String getAdResources() {
		return adResources;
	}

	public void setAdResources(String adResources) {
		this.adResources = adResources;
	}

	public List<ResourceReal> getDefResourceList() {
		return defResourceList;
	}

	public void setDefResourceList(List<ResourceReal> defResourceList) {
		this.defResourceList = defResourceList;
	}

	public List<DefaulResourceAD> getDefResourceADList() {
		return defResourceADList;
	}

	public void setDefResourceADList(List<DefaulResourceAD> defResourceADList) {
		this.defResourceADList = defResourceADList;
	}

	public AdvertPosition getAdvertPosition() {
		return advertPosition;
	}

	public void setAdvertPosition(AdvertPosition advertPosition) {
		this.advertPosition = advertPosition;
	}

	public PositionPackage getPPackage() {
		return pPackage;
	}

	public void setPPackage(PositionPackage package1) {
		pPackage = package1;
	}

	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}

	public void setBaseConfigService(BaseConfigService baseConfigService) {
		this.baseConfigService = baseConfigService;
	}
}
