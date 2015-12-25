package com.avit.ads.dtmb.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avit.ads.dtmb.bean.DAdPosition;
import com.avit.ads.dtmb.bean.DChannelinfo;

public class SendAdsTypesMap {

	private static Map<String, Map<String, String[]>> dtmbChannelAdsMap = new HashMap<String, Map<String, String[]>>();
	
	public static void initRealTimeAdsMap(){
		List<DChannelinfo> channelList = DtmbChannelMap.getDtmbChannelMap();
		List<DAdPosition> realTimeAdList = DtmbAdPositionMap.getAdvertPositionMapByType("1");
		for(DChannelinfo channel : channelList){
			for(DAdPosition adPosition : realTimeAdList){
				Map<String, String[]> map = dtmbChannelAdsMap.get(channel.getServiceId());
				if(map == null){
					map = new HashMap<String, String[]>();
					dtmbChannelAdsMap.put(channel.getServiceId(),map);
				}
				String[] fileNames = map.get(adPosition.getPosition());
				if(fileNames == null){
					fileNames = new String[]{"",""};
					map.put(adPosition.getPosition(), fileNames);
				}
				if(adPosition.getIsHd().equals("1")){
					fileNames[0] = adPosition.getFilePath();
				}else{
					fileNames[1] = adPosition.getFilePath();
				}
				
			}
		}
		
	}
	
	public static Map<String, Map<String, String[]>> getDtmbChannelAdsMap(){
		
		return dtmbChannelAdsMap;
	}
}
