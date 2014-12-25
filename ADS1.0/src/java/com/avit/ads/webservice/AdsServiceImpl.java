package com.avit.ads.webservice;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jws.WebService;

import org.springframework.stereotype.Service;

import com.avit.ads.pushads.ocg.dao.OcgInfoDao;
import com.avit.ads.pushads.ocg.service.OcgService;
import com.avit.ads.pushads.task.bean.OcgInfo;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.message.MsubtitleInfo;

@Service
@WebService(endpointInterface = "com.avit.ads.webservice.AdsService", serviceName = "AdsService")
public class AdsServiceImpl implements AdsService {
	
	@Resource(name="OcgService") 
	private OcgService ocgService;
	@Resource(name="OcgInfoDao") 
	private OcgInfoDao ocgInfoDao;
	
	public String sendUI(String areaCode, String dataDefine, String htmlData) {
		//TODO 暂不实现
		return null;
	}

	public boolean pushSubtitle(SubtitleBean subtitle) {
		
		MsubtitleInfo subtitleInfo = new MsubtitleInfo();
		subtitleInfo.setActionType(subtitle.getActionType()+ "");
		subtitleInfo.setBackgroundColor(subtitle.getBgColor());
		subtitleInfo.setBackgroundHeight(subtitle.getBgHeight() + "");
		subtitleInfo.setBackgroundWidth(subtitle.getBgWidth() + "");
		subtitleInfo.setBackgroundX(subtitle.getBgX() + "");
		subtitleInfo.setBackgroundY(subtitle.getBgY() + "");
		subtitleInfo.setFontColor(subtitle.getFontColor());
		subtitleInfo.setFontSize(subtitle.getFontSize() + "");
		subtitleInfo.setServiceID("-1");
		subtitleInfo.setShowFrequency(subtitle.getShowSpeed() + "");
		subtitleInfo.setTimeout(subtitle.getTimeout() + "");
		subtitleInfo.setUiId("a");
		subtitleInfo.setWord(subtitle.getWord());
		
		List<OcgInfo> ocgList = ocgInfoDao.getOcgInfoList();
		
		String areaCodes = subtitle.getAreaCodes().replace(" ", "");

		for(OcgInfo ocg : ocgList){   //不考虑区域未配置OCG信息的情况，系统部署好了之后，数据库这些信息都会有
			String areaCode = ocg.getAreaCode();
			String ocgIp = ocg.getIp();
			if("0".equals(areaCodes) || areaCodes.contains(areaCode)){
				ocgService.sendUNTMessageUpdateByIp(ocgIp, ConstantsHelper.REALTIME_UNT_MESSAGE_MSUBTITLE, subtitleInfo);
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		String areaCodes = "0";
		String[] areaCodeArray = areaCodes.replace(" ", "").split(",");
		System.out.println(areaCodeArray[0]);
	}

	public OcgService getOcgService() {
		return ocgService;
	}

	public void setOcgService(OcgService ocgService) {
		this.ocgService = ocgService;
	}

	public OcgInfoDao getOcgInfoDao() {
		return ocgInfoDao;
	}

	public void setOcgInfoDao(OcgInfoDao ocgInfoDao) {
		this.ocgInfoDao = ocgInfoDao;
	}

	public void test(EasyBean easyBean) {
		System.out.println("fuck you");
	}
	
	

}
