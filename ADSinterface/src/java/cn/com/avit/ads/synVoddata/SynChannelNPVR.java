package cn.com.avit.ads.synVoddata;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import cn.com.avit.ads.synVoddata.bean.ChannelInfoNPVR;
import cn.com.avit.ads.synVoddata.bean.NetWorkinfo;
import cn.com.avit.ads.synVoddata.json.ChannelInfo;
import cn.com.avit.ads.synVoddata.json.ChannelInfoJsonObject;
import cn.com.avit.ads.synVoddata.service.ChannelInfoNPVRService;

import com.google.gson.Gson;

public class SynChannelNPVR {
	@Inject
	private ChannelInfoNPVRService channelInfoNPVRService;
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
					logger.debug("json is ::::::::  " + str[1]);
				}
				ChannelInfoJsonObject po = gson.fromJson(str[1],
						ChannelInfoJsonObject.class);
				if (po.getResult().equals("0")) {
					channelInfoNPVRService.deleteChannelInfoNPVRList(netWorkID);
					for (ChannelInfo channelInfo : po.getChannels()) {
						ChannelInfoNPVR cNPVR = new ChannelInfoNPVR();
						cNPVR.setChannelId(channelInfo.getChannelId());
						cNPVR.setChannelName(channelInfo.getChannelName());
						cNPVR.setNetworkId(new Long(netWorkID).longValue());
						channelInfoNPVRService.insertChannelInfoNPVR(cNPVR);
					}
				} else {
					logger.error("获取区域" + netWorkinfo.getAreaName()
							+ ",areaCode=" + netWorkinfo.getAreaCode()
							+ "频道失败,原因：" + po.getDesc());
				}

			} else {
				logger.error("获取区域" + netWorkinfo.getAreaName() + ",areaCode="
						+ netWorkinfo.getAreaCode() + "频道失败，原因：VOD系统返回错误"
						+ str[0]);

			}
		}
		channelInfoNPVRService.execProc();

	}

}
