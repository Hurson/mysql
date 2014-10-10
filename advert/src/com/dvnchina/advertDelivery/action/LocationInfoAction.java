package com.dvnchina.advertDelivery.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.dvnchina.advertDelivery.bean.TreeBaseBean;
import com.dvnchina.advertDelivery.model.Location;
import com.dvnchina.advertDelivery.service.LocationService;
import com.dvnchina.advertDelivery.utils.TreeUtils;

/**
 * 作为公用接口，自行添加
 * 
 * @author Administrator
 * 
 */
public class LocationInfoAction extends BaseActionSupport<Object> {

	/**
	 * 加载地区信息
	 * 
	 * @return
	 */
	public String getLocationInfo() {
		String locationInfo = "{'result':'success','currentPage':'1','totalPage':'1','locationList':[{'locationCode':'01','locationName':'郑州','choose':'true'},{'locationCode':'02','locationName':'开封','choose':'true'},{'locationCode':'03','locationName':'洛阳','choose':'true'},{'locationCode':'03','locationName':'平顶山','choose':'true'},{'locationCode':'03','locationName':'安阳','choose':'true'}]}";
		renderText(locationInfo);
		return null;
	}

	private static final long serialVersionUID = 1L;

	private LocationService locationService;

	/**
	 * 获取所有的地区信息
	 * 
	 * @return
	 */
	public String getAllLocation() {
		
		List<Location> locationList = locationService.getAllLocation();
		
		String json = JSONArray.fromObject(locationList).toString() + "";
		
		json = json.replaceAll("id", "ID");
		json = json.replaceAll("locationCode", "id");
		
		renderJson(json);
		return NONE;
	}
	
	/**
	 * 获取所有的地区信息
	 * 
	 * @return
	 */
	public String getTreeLocation() {
		
		List<Location> locationList = locationService.getAllLocation();
		List<TreeBaseBean> baseBeans = new ArrayList<TreeBaseBean>();
		for(int i = 0; i < locationList.size(); i++){
			TreeBaseBean bean = new TreeBaseBean();
			Location location = locationList.get(i);
			bean.setId(location.getId()+"");
			bean.setText(location.getAreaName()+"");
			bean.setPid(location.getParentCode()+"");
			bean.setChecked("0");
			baseBeans.add(bean);
		}
		
		String json = TreeUtils.getJsonTree(baseBeans, "-1");
		
		renderJson(json);
		
		return NONE;
	}

	public LocationService getLocationService() {
		return locationService;
	}

	public void setLocationService(LocationService locationService) {
		this.locationService = locationService;
	}

}
