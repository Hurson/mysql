package cn.com.avit.ads.synVoddata.webservice;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import cn.com.avit.ads.synVoddata.HttpUtil;
import cn.com.avit.ads.synVoddata.NetWorkUtil;
import cn.com.avit.ads.synVoddata.bean.NetWorkinfo;

import com.google.gson.Gson;

public class AdInjectServiceImpl implements AdInjectService {
	
	private Logger logger = Logger.getLogger(AdInjectServiceImpl.class);

	@Value("${vod.ip}")
	private String ip;
	@Value("${vod.port}")
    private String port;
	
	public ResultJson sendAdInject(String assetCode, String fileAddress) {
		
		List<NetWorkinfo> allArea =NetWorkUtil.getInstince().getAllArea();
		for(NetWorkinfo netWorkinfo :allArea){
			String netWorkID = netWorkinfo.getAreaCode();
			String[] str =HttpUtil.get(ip, port, "/payUI/AdInjectionTask?assetCode="+assetCode+"&areaCode="+netWorkID+"&fileAddress="+fileAddress);
			if(logger.isDebugEnabled()){ 
				logger.debug("json is ::::::::  "+ str[1]);
			}
			if(str[0].equals("200")){
				
				 Gson gson = new Gson();
				 ResultJson rj = gson.fromJson(str[1], ResultJson.class);
				 return rj;
			}
		}
		return null;
	}

	public ResultJson getAdAssetInfo(String assetCode) {
		
		List<NetWorkinfo> allArea =NetWorkUtil.getInstince().getAllArea();
		for(NetWorkinfo netWorkinfo :allArea){
			String netWorkID = netWorkinfo.getAreaCode();
			String[] str =HttpUtil.get(ip, port, "/payUI/GetAdAssetInfo?assetCode="+assetCode+"&areaCode="+netWorkID);
			if(logger.isDebugEnabled()){ 
				logger.debug("json is ::::::::  "+ str[1]);
			}
			if(str[0].equals("200")){
				
				 Gson gson = new Gson();
				 ResultJson rj = gson.fromJson(str[1], ResultJson.class);
				 return rj;
			}
		}
		return null;
	}
}
