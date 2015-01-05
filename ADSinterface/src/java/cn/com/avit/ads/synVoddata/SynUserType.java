package cn.com.avit.ads.synVoddata;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import cn.com.avit.ads.synVoddata.bean.LocationCodeBean;
import cn.com.avit.ads.synVoddata.bean.LocationCodeBeanId;
import cn.com.avit.ads.synVoddata.bean.NetWorkinfo;
import cn.com.avit.ads.synVoddata.bean.UserIndustryCategoryBean;
import cn.com.avit.ads.synVoddata.bean.UserRankBean;
import cn.com.avit.ads.synVoddata.json.IndustryCategoryObject;
import cn.com.avit.ads.synVoddata.json.LocationObject;
import cn.com.avit.ads.synVoddata.json.UserLevelObject;
import cn.com.avit.ads.synVoddata.json.UserTypeInfoJsonObject;
import cn.com.avit.ads.synVoddata.service.SynLocationCodeService;
import cn.com.avit.ads.synVoddata.service.SynUserIndustryCategoryService;
import cn.com.avit.ads.synVoddata.service.UserRankService;

import com.google.gson.Gson;

public class SynUserType {
	@Inject
	private UserRankService userRankService;
	@Inject
	private SynLocationCodeService synLocationCodeService;
	@Inject
	private SynUserIndustryCategoryService synUserIndustryCategoryService;
	
	@Value("${vod.ip}")
	private String ip;
	@Value("${vod.port}")
	private String port;

	private Logger logger = Logger.getLogger(SynProduct.class);

	public void generateProductData() {
		List<NetWorkinfo> allArea = NetWorkUtil.getInstince().getAllArea();
		for (NetWorkinfo netWorkinfo : allArea) {
			String netWorkID = netWorkinfo.getAreaCode();
			String[] str = HttpUtil.get(ip, port,
					"/payUI/GetChannels?areaCode=" + netWorkID);
			if (str[0].equals("200")) {
				String jsonStr = str[1];
				Gson gson = new Gson();
				if (logger.isDebugEnabled()) {
					logger.debug("json is ::::::::  " + jsonStr);
				}
				UserTypeInfoJsonObject po = gson.fromJson(jsonStr,
						UserTypeInfoJsonObject.class);
				if (po.getResult().equals("0")) {
					userRankService.deleteUserRankList(netWorkID);
					synLocationCodeService.deleteLocationCodeList(netWorkID);
					synUserIndustryCategoryService.deleteUserIndustryCategoryList(netWorkID);
				
					for (UserLevelObject userLevelObject : po.getUserLevels()) {
						UserRankBean urb = new UserRankBean();
						urb.setAreacode(netWorkID);
						urb.setUserRankCode(userLevelObject.getUserLevel());
						urb.setUserRankName(userLevelObject.getUserLevelDesc());
						userRankService.insertUserRank(urb);
					}
					for (IndustryCategoryObject industryCategoryObject : po.getIndustryCategorys()) {
						UserIndustryCategoryBean uicb = new UserIndustryCategoryBean();
						uicb.setAreacode(netWorkID);
						uicb.setUserIndustryCategoryCode(industryCategoryObject.getIndustryCategory());
						uicb.setUserIndustryCategoryValue(industryCategoryObject.getIndustryCategoryDesc());
						synUserIndustryCategoryService.insertUserIndustryCategory(uicb);
					}
					for (LocationObject locationObject : po.getLocations()) {
						LocationCodeBean lcb = new LocationCodeBean();
						LocationCodeBeanId tempId = new LocationCodeBeanId();
						
						tempId.setAreacode(netWorkID);
						tempId.setLocationcode(new Long(locationObject.getLocationcode()).longValue());
						tempId.setLocationname( locationObject.getLocationName());
						tempId.setLocationtype(locationObject.getLoctionType());
						tempId.setParentlocation(new Long(locationObject.getParentLoction()).longValue());
						lcb.setId(tempId);
						synLocationCodeService.insertLocationCode(lcb);
					}
					
				} else {
					logger.error("获取区域" + netWorkinfo.getAreaName()
							+ ",areaCode=" + netWorkinfo.getAreaCode()
							+ "用户信息失败,原因：" + po.getDesc());
				}

			} else {
				logger.error("获取区域" + netWorkinfo.getAreaName() + ",areaCode="
						+ netWorkinfo.getAreaCode() + "用户信息失败，原因：VOD系统返回错误"
						+ str[0]);

			}
		}
		userRankService.execProc();
		synUserIndustryCategoryService.execProc();
		synLocationCodeService.execProc();
	}

}
