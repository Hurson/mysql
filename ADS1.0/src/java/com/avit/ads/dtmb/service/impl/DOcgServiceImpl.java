package com.avit.ads.dtmb.service.impl;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avit.ads.dtmb.bean.DOcgInfo;
import com.avit.ads.dtmb.dao.DOcgInfoDao;
import com.avit.ads.dtmb.dao.DUntDao;
import com.avit.ads.dtmb.service.DOcgService;
import com.avit.ads.pushads.ocg.GenerateFileJni;
import com.avit.ads.pushads.ocg.bean.HeaderInfo;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.message.AdsConfigJs;
import com.avit.ads.util.message.AdsImage;
import com.avit.ads.util.message.ChannelRecomend;
import com.avit.ads.util.message.ChannelRecomendElement;
import com.avit.ads.util.message.ChannelSubtitle;
import com.avit.ads.util.message.ChannelSubtitleElement;
import com.avit.ads.util.message.MsubtitleInfo;
import com.avit.ads.util.message.OcgPlayMsg;
import com.avit.ads.util.message.RetMsg;
import com.avit.ads.util.message.SsuLocation;
import com.avit.ads.util.message.Subtitle;
import com.avit.ads.util.message.SystemMaintain;
import com.avit.ads.util.message.TvnTarget;
import com.avit.ads.util.message.UNTMessage;
import com.avit.ads.util.message.Weatherforecast;
import com.avit.ads.util.warn.WarnHelper;
import com.avit.ads.xml.JaxbXmlObjectConvertor;
import com.avit.common.ftp.service.FtpService;

@Service("DOcgService")
public class DOcgServiceImpl implements DOcgService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Inject
	private FtpService ftpService;

	@Autowired
	private WarnHelper warnHelper;
	
	@Inject
	private DOcgInfoDao ocgInfoDao;
	
	@Autowired
	private DUntDao untDao;	

	JaxbXmlObjectConvertor helper = JaxbXmlObjectConvertor.getInstance();
	
	
	public boolean connectFtpServer(String ip, int port, String username,String password) {
		return ftpService.connectServer(ip, port, username, password);
	}



	public void deleteFtpDirFiles(String dirPath) {
		ftpService.deleteDirFile(dirPath);
	}
	
	public void deleteFtpFileIfExist(String fileName, String remoteDir) {
		ftpService.deleteFileIfExist(fileName, remoteDir);
	}

	public void sendDirFilesToFtp(String localDirPath, String remotDirPath) {
		ftpService.sendAFilePath2ResourceServer(localDirPath, remotDirPath);
	}
	
	public void sendFileToFtp(String localFilePath, String remotDirPath) {
		try {
			ftpService.sendAFileToFtp(localFilePath, remotDirPath);
		} catch (Exception e) {
			logger.error("通过FTP发送文件到OCG失败", e);
		}
	}

	public void disConnectFtpServer() {
		ftpService.disConnectFtpServer();
	}

	public boolean startOcgPlayByIp(String ip, String sendPath, String sendType, String adsType) {
		
		OcgPlayMsg sendMsgEntity = new OcgPlayMsg();
		sendMsgEntity.setSendPath(sendPath);
		sendMsgEntity.setSendType(sendType);
		sendMsgEntity.setAdsType(adsType);

		String sendMsg = helper.toXML(sendMsgEntity);
		logger.info("SendMsg: " + sendMsg);
		int port = ConstantsHelper.OCG_UDP_PORT;
		byte[] retBuf = sendUdpMsg(ip, port, sendMsg);
		if (null == retBuf || retBuf.length == 0) {
			return false;
		}
		String retMsg = new String(retBuf).trim();
		RetMsg retMsgEntity = null;
		try {
			retMsgEntity = (RetMsg) helper.fromXML(retMsg);
			if ("200".equals(retMsgEntity.getCode())) {
				logger.info("OCG投放广告成功");
				return true;
			} else if ("400".equals(retMsgEntity.getCode())) {
				logger.error("OCG投放广告失败: 请求格式不对 ");
			} else if ("401".equals(retMsgEntity.getCode())) {
				logger.error("OCG投放广告失败： 为获取广告文件");
			}
		} catch (Exception e) {
			logger.error("OCG投放广告，返回XML格式消息解析异常", e);
		}
		return false;
	}


	private HttpResponse sendHttpMsg(String areaCode, HeaderInfo header,String url, String fileName){
		
		HttpClient httpclient = new DefaultHttpClient();
		
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000); 
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);

		try { 
			HttpPost httpost = new HttpPost(url); 
			
			httpost.setHeader("message_type", header.getMessageType());
			httpost.setHeader("stream_id", header.getStreamId());
			httpost.setHeader("control_command", header.getControlCommand());
			httpost.setHeader("bitrate", header.getBitrate());
			httpost.setHeader("send_address", header.getSendAddress());
			httpost.setHeader("send_port", header.getSendPort());
			httpost.setHeader("reserve_time", header.getReserveTime());
			httpost.setHeader("effect_time", header.getEffectTime());
			httpost.setHeader("outdate_time", header.getOutdateTime());
			
			File file = new File(fileName);
			if(file.exists()){
				FileEntity bodyEntity = new FileEntity(new File(fileName),"binary/octet-stream");
				httpost.setEntity(bodyEntity); 
			}else{
				
				String errorMsg = "区域【"+ areaCode +"】频点【"+fileName.substring(fileName.lastIndexOf(File.separator) + 1)+"】文件生成异常";
				logger.error(errorMsg);
				warnHelper.writeWarnMsgToDb(areaCode, errorMsg);
			}
			
			HttpResponse response = httpclient.execute(httpost); 
			return response;
		}catch(Exception e){
			String errorMsg = "向OCG发送HTTP请求异常, url: " + url;
			logger.error(errorMsg, e);
			warnHelper.writeWarnMsgToDb(areaCode, errorMsg);
			return null;
		}finally {
			try{
			httpclient.getConnectionManager().shutdown();
			}catch(Exception e){
				logger.error("关闭连接异常！", e);
			}
		}

	}
	private byte[] sendUdpMsg(String ip, int port, String xmlMsg) {

		byte[] retBuf = new byte[ConstantsHelper.OCG_UDP_RET_MSG_MAX_LENGTH]; // 接口文档定义的最大长度
		DatagramSocket ds = null;
		try {
			int receivePort = ConstantsHelper.OCG_UDP_PORT_RECEIVE;
			ds = new DatagramSocket(receivePort);
			ds.setSoTimeout(10000);   // 10s超时等待
			byte[] data = xmlMsg.getBytes("utf8");
			DatagramPacket sendPacket = new DatagramPacket(data, 0, data.length, InetAddress.getByName(ip), port);
			ds.send(sendPacket);
			DatagramPacket retPacket = new DatagramPacket(retBuf, ConstantsHelper.OCG_UDP_RET_MSG_MAX_LENGTH);
			ds.receive(retPacket);
		} catch (Exception e) {
			logger.error("向OCG发送UDP请求异常", e);
			return null;
		} finally {
			ds.close();
		}
		return retBuf;
	}
	public boolean sendDtmbUntUpdateByAreaCode(int sendType, Object message, String areaCode, String tsId) {
		
		UNTMessage uNTMessage = new UNTMessage();
		uNTMessage.setSendType(sendType + "");

		switch (sendType) {
			case ConstantsHelper.REALTIME_UNT_MESSAGE_WEATHER:
				uNTMessage.setWeatherforecast((Weatherforecast) message);
				break;
			case ConstantsHelper.REALTIME_UNT_MESSAGE_MSUBTITLE:
				uNTMessage.setSubtitle((Subtitle) message);
				break;
			case ConstantsHelper.REALTIME_UNT_MESSAGE_ADCONFIG:
				uNTMessage.setAdsConfig((AdsConfigJs) message);
				break;
			case ConstantsHelper.REALTIME_UNT_MESSAGE_ADIMAGE:
				uNTMessage.setAdsImage((AdsImage) message);
				break;
			case ConstantsHelper.REALTIME_UNT_MESSAGE_STB:
				uNTMessage.setSystemMaintain((SystemMaintain) message);
				break;
			case ConstantsHelper.REALTIME_UNT_MESSAGE_CHANNEL_SUBTITLE:
				uNTMessage.setChannelSubtitle((ChannelSubtitle)message);
				break;
			case ConstantsHelper.REALTIME_UNT_MESSAGE_CHANNE_RECOMMEND:
				uNTMessage.setChannelRecomend((ChannelRecomend) message);
				break;
			default:
				break;
		}

		String sendMsg = helper.toXML(uNTMessage);
		logger.info("SendMsg=【"+sendMsg+"】");
		String destPath =InitConfig.getConfigMap().get(ConstantsHelper.D_DEST_FILE_PATH) + File.separator + areaCode;
		
		String logPath = InitConfig.getConfigMap().get(ConstantsHelper.D_LOG_FILE_PATH)+File.separator+ConstantsHelper.LOG_FILE_NAME;
		int version = untDao.getUntVersion(areaCode, sendType);
		version = (version + 1) % 32;
		boolean result = GenerateFileJni.getInstance().geneTSFile(sendMsg.getBytes(), version, version, destPath, logPath);
		boolean success = false;
		
		if(result){
			List<DOcgInfo> ocgList = ocgInfoDao.getOcgMulticastInfoList(areaCode, tsId);
			boolean ocgExist = false;
			for (DOcgInfo ocg : ocgList) {
				if (ocg.getAreaCode().equals(areaCode)) {
					ocgExist = true;	
					HeaderInfo headerInfo = new HeaderInfo(ocg.getStreamId(), ocg.getMulticastBitrate(),ocg.getMulticastIp(), ocg.getMulticastPort());
					String url = "http://" + ocg.getIp() + ":" + ConstantsHelper.OCG_HTTP_PORT;
					HttpResponse res = sendHttpMsg(areaCode, headerInfo, url, destPath+File.separator + ConstantsHelper.SEND_FILE + File.separator + ocg.getTsId() + ConstantsHelper.TS_FILE_SUFFIX);
					if(null == res){
						continue;
					}
					int code = res.getStatusLine().getStatusCode();
					if(code == 200){
						success = true;
						logger.info("UNT更新成功！");
					}else if(code == 400){
						logger.info("参数格式不正确！");
						success = false;
						break;
					}else if(code == 401){
						logger.info("发送OCG消息失败！");
						success = false;
					}
				}
			}
			if(success){
				untDao.updateVersion(areaCode, sendType);
			}
			if(!ocgExist){
				String errMsg = "未配置区域【" + areaCode + "】的频点tsId【"+tsId+"】信息";
				logger.error(errMsg);
				warnHelper.writeWarnMsgToDb(areaCode, errMsg);
			}
			
		}else{
			logger.error("构造unt表失败.");
		}
		return success;

	}


	/**
	 * 以下全部为调试接口专用
	 * 
	 * @param ip
	 * @param port
	 * @param xmlMsg
	 * @return
	 */
	

	public static void main(String[] args) throws Exception {	
		
	}
	
	public static String initIntefaceDebug(){
		
		// sendFile
//		OcgPlayMsg ocgPalyMsg = new OcgPlayMsg();
//		ocgPalyMsg.setSendPath("ftp://abc:abc@192.168.128.128/ui");
//		ocgPalyMsg.setSendType("1");
		
		// sendUIMessage
//		UiUpdateMsg uiMsg = new UiUpdateMsg();
//		uiMsg.setUpdateType("1:initPic-c.iframe,5:0");
//		uiMsg.setNetworkID("6280");
//		uiMsg.setServicesID("0");
//		uiMsg.setTsID("10086");

		//UNTMessage uNTMsg = initUNTMsg(1);
		//UNTMessage uNTMsg = initUNTMsg(2);
		//UNTMessage uNTMsg = initUNTMsg(3);
		//UNTMessage uNTMsg = initUNTMsg(4);
		//UNTMessage uNTMsg = initUNTMsg(5);
		//UNTMessage uNTMsg = initUNTMsg(6);
		
		//JaxbXmlObjectConvertor jaxbhelper = JaxbXmlObjectConvertor.getInstance();
        //String ocgXml = jaxbhelper.toXML(ocgPalyMsg);
        //String ocgXml = jaxbhelper.toXML(uiMsg);
        //String ocgXml = jaxbhelper.toXML(uNTMsg);
        //return ocgXml;
		
		TvnTarget tvnTarget1 = new TvnTarget();
		tvnTarget1.setServiceID("100");
		tvnTarget1.setTvnType("2");
		tvnTarget1.setTvn("1");
		tvnTarget1.setCaIndustryType("0");
		tvnTarget1.setCaUserLevel("0");
		
		MsubtitleInfo subtitle1 = new MsubtitleInfo();
		subtitle1.setUiId("a");
		subtitle1.setActionType("1");
		subtitle1.setTimeout("5");
		subtitle1.setFontColor("1");
		subtitle1.setFontSize("1");
		subtitle1.setBackgroundX("1");
		subtitle1.setBackgroundY("1");
		subtitle1.setBackgroundWidth("1");
		subtitle1.setBackgroundHeight("1");
		subtitle1.setBackgroundColor("2");
		subtitle1.setShowFrequency("2");
		subtitle1.setWord("hello");
		
		ChannelSubtitleElement elem1 = new ChannelSubtitleElement();
		elem1.setTvnTarget(tvnTarget1);
	//	elem1.setSubtitleInfo(subtitle1);
		
		
		TvnTarget tvnTarget2 = new TvnTarget();
		tvnTarget2.setServiceID("101");
		tvnTarget2.setTvnType("2");
		tvnTarget2.setTvn("1");
		tvnTarget2.setCaIndustryType("0");
		tvnTarget2.setCaUserLevel("0");
		
		MsubtitleInfo subtitle2 = new MsubtitleInfo();
		subtitle2.setUiId("a");
		subtitle2.setActionType("1");
		subtitle2.setTimeout("5");
		subtitle2.setFontColor("1");
		subtitle2.setFontSize("1");
		subtitle2.setBackgroundX("1");
		subtitle2.setBackgroundY("1");
		subtitle2.setBackgroundWidth("1");
		subtitle2.setBackgroundHeight("1");
		subtitle2.setBackgroundColor("2");
		subtitle2.setShowFrequency("2");
		subtitle2.setWord("world");
		
		ChannelSubtitleElement elem2 = new ChannelSubtitleElement();
		elem2.setTvnTarget(tvnTarget2);
	//	elem2.setSubtitleInfo(subtitle2);
		
		TvnTarget tvnTarget3 = new TvnTarget();
		tvnTarget3.setServiceID("102");
		tvnTarget3.setTvnType("2");
		tvnTarget3.setTvn("1");
		tvnTarget3.setCaIndustryType("0");
		tvnTarget3.setCaUserLevel("0");
		
		MsubtitleInfo subtitle3 = new MsubtitleInfo();
		subtitle3.setUiId("a");
		subtitle3.setActionType("1");
		subtitle3.setTimeout("5");
		subtitle3.setFontColor("1");
		subtitle3.setFontSize("1");
		subtitle3.setBackgroundX("1");
		subtitle3.setBackgroundY("1");
		subtitle3.setBackgroundWidth("1");
		subtitle3.setBackgroundHeight("1");
		subtitle3.setBackgroundColor("2");
		subtitle3.setShowFrequency("2");
		subtitle3.setWord("world");
		
		ChannelSubtitleElement elem3 = new ChannelSubtitleElement();
		elem3.setTvnTarget(tvnTarget3);
	//	elem3.setSubtitleInfo(subtitle3);
		
		ChannelSubtitle channelSubtitle = new ChannelSubtitle();
		List<ChannelSubtitleElement> list = new ArrayList<ChannelSubtitleElement>();
		list.add(elem1);
		list.add(elem2);
		list.add(elem3);
		channelSubtitle.setChannelSubtitleElemList(list);
		
		UNTMessage untMsg = new UNTMessage();
		untMsg.setSendType("6");
		untMsg.setChannelSubtitle(channelSubtitle);
		
		JaxbXmlObjectConvertor jaxbhelper = JaxbXmlObjectConvertor.getInstance();
       
        String ocgXml = jaxbhelper.toXML(untMsg);
        return ocgXml;
	}

	public static void dealIntefaceRs(String retMsg){
		JaxbXmlObjectConvertor jaxbhelper = JaxbXmlObjectConvertor.getInstance();
		RetMsg retMsgEntity = null;
		try {
			retMsgEntity = (RetMsg) jaxbhelper.fromXML(retMsg);
			if ("200".equals(retMsgEntity.getCode())) {
				System.out.println("200-更新信息成功");
			} else if ("400".equals(retMsgEntity.getCode())) {
				System.out.println("400-更新信息失败：请求参数格式不正确 ");
			} else if ("401".equals(retMsgEntity.getCode())) {
				System.out.println("401-更新信息失败");
			}
		} catch (Exception e) {
			System.out.println("OCG返回消息解析异常");
			e.printStackTrace();
		}
	}
	
	public static Object initUNTMsg(int type){
		//1.天气预报
		Weatherforecast weather = new Weatherforecast();
	    weather.setWord(new String("郑州明日天气：晴。气温：23-27度"));
		weather.setNetworkId("100");
		weather.setUiId("a");
		//2.全频道字幕
		MsubtitleInfo subtitle = new MsubtitleInfo();
		subtitle.setActionType("1");
		subtitle.setServiceID("101");
		subtitle.setTimeout("0");
		subtitle.setFontSize("16");
		subtitle.setFontColor("345");
		subtitle.setBackgroundX("50");
		subtitle.setBackgroundY("50");
		subtitle.setBackgroundWidth("80");
		subtitle.setBackgroundHeight("80");
		subtitle.setBackgroundColor("346");
		subtitle.setShowFrequency("8");
		subtitle.setWord("测试滚动字幕广告测试滚动字幕广告测试滚动字幕广告测试滚动字幕广告");
		subtitle.setUiId("b");
		//3.配置文件
		AdsConfigJs adsConfig = new AdsConfigJs();
		adsConfig.setFilepath("ads/cofig/asdConfig.js");
		adsConfig.setUiId("b");
		//4.图片文件
		AdsImage adsImage = new AdsImage();
		adsImage.setFilepath("ads/cofig/asdConfig.png");
		adsImage.setUiId("c");
		//5.待机
		SystemMaintain stb = new SystemMaintain();
		stb.setActionCode(0);
		stb.setActiveHour(5);
		//6.频道字幕
		TvnTarget tvnTarget1 = new TvnTarget();
		tvnTarget1.setServiceID("100");
		tvnTarget1.setTvnType("2");
		tvnTarget1.setTvn("1");
		tvnTarget1.setCaIndustryType("0");
		tvnTarget1.setCaUserLevel("0");
		
		MsubtitleInfo subtitle1 = new MsubtitleInfo();
		subtitle1.setUiId("a");
		subtitle1.setActionType("1");
		subtitle1.setTimeout("5");
		subtitle1.setFontColor("1");
		subtitle1.setFontSize("1");
		subtitle1.setBackgroundX("1");
		subtitle1.setBackgroundY("1");
		subtitle1.setBackgroundWidth("1");
		subtitle1.setBackgroundHeight("1");
		subtitle1.setBackgroundColor("2");
		subtitle1.setShowFrequency("2");
		subtitle1.setWord("hello");
		
		ChannelSubtitleElement elem1 = new ChannelSubtitleElement();
		elem1.setTvnTarget(tvnTarget1);
//		elem1.setSubtitleInfo(subtitle1);
		
		
		TvnTarget tvnTarget2 = new TvnTarget();
		tvnTarget2.setServiceID("101");
		tvnTarget2.setTvnType("2");
		tvnTarget2.setTvn("1");
		tvnTarget2.setCaIndustryType("0");
		tvnTarget2.setCaUserLevel("0");
		
		MsubtitleInfo subtitle2 = new MsubtitleInfo();
		subtitle2.setUiId("a");
		subtitle2.setActionType("1");
		subtitle2.setTimeout("5");
		subtitle2.setFontColor("1");
		subtitle2.setFontSize("1");
		subtitle2.setBackgroundX("1");
		subtitle2.setBackgroundY("1");
		subtitle2.setBackgroundWidth("1");
		subtitle2.setBackgroundHeight("1");
		subtitle2.setBackgroundColor("2");
		subtitle2.setShowFrequency("2");
		subtitle2.setWord("world");
		
		ChannelSubtitleElement elem2 = new ChannelSubtitleElement();
		elem2.setTvnTarget(tvnTarget2);
//		elem2.setSubtitleInfo(subtitle2);
		
		TvnTarget tvnTarget3 = new TvnTarget();
		tvnTarget3.setServiceID("102");
		tvnTarget3.setTvnType("2");
		tvnTarget3.setTvn("1");
		tvnTarget3.setCaIndustryType("0");
		tvnTarget3.setCaUserLevel("0");
		
		MsubtitleInfo subtitle3 = new MsubtitleInfo();
		subtitle3.setUiId("a");
		subtitle3.setActionType("1");
		subtitle3.setTimeout("5");
		subtitle3.setFontColor("1");
		subtitle3.setFontSize("1");
		subtitle3.setBackgroundX("1");
		subtitle3.setBackgroundY("1");
		subtitle3.setBackgroundWidth("1");
		subtitle3.setBackgroundHeight("1");
		subtitle3.setBackgroundColor("2");
		subtitle3.setShowFrequency("2");
		subtitle3.setWord("world");
		
		ChannelSubtitleElement elem3 = new ChannelSubtitleElement();
		elem3.setTvnTarget(tvnTarget3);
	//	elem3.setSubtitleInfo(subtitle3);
		
		ChannelSubtitle channelSubtitle = new ChannelSubtitle();
		List<ChannelSubtitleElement> list = new ArrayList<ChannelSubtitleElement>();
		list.add(elem1);
		list.add(elem2);
		list.add(elem3);
		channelSubtitle.setChannelSubtitleElemList(list);
		//7.频道推荐链接
		TvnTarget tvn = new TvnTarget();
		tvn.setServiceID("101");
		tvn.setTvn("333");
		tvn.setTvnType("1");
		tvn.setCaIndustryType("322,4355");
		tvn.setCaUserLevel("98,23");
		
		SsuLocation location = new SsuLocation();
		location.setUrl("http:baidu.com");
		
		ChannelRecomendElement recomendElem = new ChannelRecomendElement();
		recomendElem.setTvnTarget(tvn);
		recomendElem.setSsuLocation(location);
		
		List<ChannelRecomendElement> elemList = new ArrayList<ChannelRecomendElement>();
		elemList.add(recomendElem);
		
		ChannelRecomend channelRecomend = new ChannelRecomend();
		channelRecomend.setChannelRecomendElemList(elemList);
		
		switch (type) {
		case 1:
			return weather;
		case 2:
			return subtitle;
		case 3:
			return adsConfig;
		case 4:
			return adsImage;
		case 5:
			return stb;
		case 6:
			return channelSubtitle;
		case 7:
			return channelRecomend;
		default:
			return null;
		}
	}
	
	
}
