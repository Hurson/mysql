package com.avit.ads.webservice;

import java.util.Date;

import com.dvnchina.advertDelivery.subtitle.bean.SubtitleBean;

public class Test {

	public void test(){
		UploadClient upClient = new UploadClient();
		//upClient.setServerUrl("http://192.168.102.104:8080/ads/");
		
		//upClient.sendFileVideoPump("uploadFiles/contractScanFile/movie05.png", "/video/");
		//upClient.sendFileDTV("0", "uploadFiles/contractScanFile/movie05.png", "/dtv/");
		
		//upClient.sendFile("fuck.exe", "adsTypeCode");
		
		AdsClient client = new AdsClient();
		
		SubtitleBean subtitle = new SubtitleBean();
		
		subtitle.setActionType(1);
		subtitle.setBgColor("1");
		subtitle.setBgHeight(1);
		subtitle.setBgWidth(1);
		subtitle.setBgX(1);
		subtitle.setBgY(1);
		subtitle.setCreateDateStr("00-00-00");
		subtitle.setCreateTime(new Date());
		subtitle.setFontColor("1");
		subtitle.setFontSize(1);
		subtitle.setId(1);
		subtitle.setPushDateStr("00-00-00");
		subtitle.setPushTime(new Date());
		subtitle.setShowSpeed(1);
		subtitle.setState(1);
		subtitle.setTimeout(1);
		subtitle.setWord("1");
		
		client.pushSubtitle(subtitle);
		
		EasyBean easyBean = new EasyBean();
		easyBean.setActionType(1);
		easyBean.setBgColor("1");
		easyBean.setBgHeight(1);
		easyBean.setBgWidth(1);
		easyBean.setBgX(1);
		easyBean.setBgY(1);
		easyBean.setCreateDateStr("00-00-00");
		easyBean.setCreateTime(new Date());
		easyBean.setFontColor("1");
		easyBean.setFontSize(1);
		easyBean.setId(1);
		easyBean.setPushDateStr("00-00-00");
		easyBean.setPushTime(new Date());
		easyBean.setShowSpeed(1);
		easyBean.setState(1);
		easyBean.setTimeout(1);
		easyBean.setWord("1");
		
		
		//client.testEasyBean(easyBean);

	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test test = new Test();
		test.test();
	}

}
