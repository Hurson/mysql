package com.avit.ads.pushads.ocg.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avit.ads.pushads.ocg.service.OcgService;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.bean.Ocg;
import com.avit.ads.util.message.AdsConfigJs;
import com.avit.ads.util.message.AdsImage;
import com.avit.ads.util.message.Channelrecomendurl;
import com.avit.ads.util.message.MsubtitleInfo;
import com.avit.ads.util.message.OcgPlayMsg;
import com.avit.ads.util.message.RetMsg;
import com.avit.ads.util.message.SystemMaintain;
import com.avit.ads.util.message.UNTMessage;
import com.avit.ads.util.message.UiUpdateMsg;
import com.avit.ads.util.message.Weatherforecast;
import com.avit.ads.util.warn.WarnHelper;
import com.avit.ads.xml.JaxbXmlObjectConvertor;
import com.avit.common.ftp.service.FtpService;
import com.ipanel.http.util.HttpCommon;

@Service("OcgService")
public class OcgServiceImpl implements OcgService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Inject
	private FtpService ftpService;

	@Autowired
	private WarnHelper warnHelper;

	JaxbXmlObjectConvertor helper = JaxbXmlObjectConvertor.getInstance();

	public boolean connectFtpServer(String areaCode) {

		List<Ocg> ocgList = InitConfig.getAdsConfig().getOcgList();
		for (Ocg ocg : ocgList) {
			if (ocg.getAreaCode().equals(areaCode)) {
				String ip = ocg.getIp();
				int port = Integer.parseInt(ocg.getPort());
				String user = ocg.getUser();
				String pwd = ocg.getPwd();
				try {
					ftpService.setServer(ip, port, user, pwd);
				} catch (IOException e) {
					String errMsg = "OCG系统的FTP无法连接   areaCode:" + areaCode
							+ ", ip: " + ip + ", port: " + port + ", user: "
							+ user + ", pwd: " + pwd;
					logger.error(errMsg, e);
					warnHelper.writeWarnMsgToDb(errMsg);
					return false;
				}
				return true;
			}
		}

		String errMsg = "未配置区域【" + areaCode + "】的OCG FTP连接信息";
		logger.error(errMsg);
		warnHelper.writeWarnMsgToDb(errMsg);
		return false;
	}

	public void deleteFtpDirFiles(String dirPath) {
		ftpService.deleteDirFile(dirPath);
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

	public boolean startOcgPlay(String areaCode, String sendPath,
			String sendType) {

		OcgPlayMsg sendMsgEntity = new OcgPlayMsg();
		sendMsgEntity.setSendPath(sendPath);
		sendMsgEntity.setSendType(sendType);

		String sendMsg = helper.toXML(sendMsgEntity);

		List<Ocg> ocgList = InitConfig.getAdsConfig().getOcgList();
		for (Ocg ocg : ocgList) {
			if (ocg.getAreaCode().equals(areaCode)) {
				String ip = ocg.getIp();
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
				break;
			}
		}
		return false;
	}

	public boolean sendUiDesc(String areaCode, String description) {
		UiUpdateMsg sendMsgEntity = new UiUpdateMsg();
		sendMsgEntity.setUpdateType(description);

		String sendMsg = helper.toXML(sendMsgEntity);

		List<Ocg> ocgList = InitConfig.getAdsConfig().getOcgList();
		for (Ocg ocg : ocgList) {
			if (ocg.getAreaCode().equals(areaCode)) {
				String ip = ocg.getIp();
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
						logger.info("UI更新成功");
						return true;
					} else if ("400".equals(retMsgEntity.getCode())) {
						logger.error("UI更新失败: 请求格式不对 ");
					} else if ("401".equals(retMsgEntity.getCode())) {
						logger.error("UI更新失败： 发送失败");
					}
				} catch (Exception e) {
					logger.error("UI更新，返回XML格式消息解析异常", e);
				}
				break;
			}
		}
		return false;
	}

	private byte[] sendUdpMsg(String ip, int port, String xmlMsg) {

		byte[] retBuf = new byte[ConstantsHelper.OCG_UDP_RET_MSG_MAX_LENGTH]; // 接口文档定义的最大长度
		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket(30005);
			byte[] data = xmlMsg.getBytes(); 
			DatagramPacket sendPacket = new DatagramPacket(data,
					0, data.length, InetAddress.getByName(ip), port);

			ds.send(sendPacket);

			DatagramPacket retPacket = new DatagramPacket(retBuf,
					ConstantsHelper.OCG_UDP_RET_MSG_MAX_LENGTH);
			ds.receive(retPacket);
		} catch (Exception e) {
			logger.error("向OCG发送UDP请求异常", e);
			return null;
		} finally {
			ds.close();
		}
		return retBuf;
	}

	/**
	 * 发送文件到OCG系统
	 * 
	 * @param areaCode
	 *            区域ID
	 * @param 源目文件
	 * @param 目标文件
	 */
	public boolean sendFile(String areaCode, String sourceFile,
			String targetPath) {

		File file = new File(sourceFile);
		String errorMsg = "";
		if (!file.exists()) {
			errorMsg = "【向OCG发送文件不存在】"; // 文件不存在
		} else if (file.length() == 0) {
			errorMsg = "【向OCG发送文件大小为0】"; // 文件大小为0
		}
		if (!"".equals(errorMsg)) {
			errorMsg += sourceFile;
			warnHelper.writeWarnMsgToDb(errorMsg);
			return true; // 不发送到OCG，流程正常往下走
		}

		// 如果区域编码不为空，则只投放对应区域
		List<Ocg> ocgList = InitConfig.getAdsConfig().getOcgList();
		for (int i = 0; i < ocgList.size(); i++) {
			Ocg ocg = ocgList.get(i);
			if (ocg.getAreaCode().equals(areaCode)) {
				HttpCommon.getInstance().initHttp(ocg.getIp());
				boolean isuploadSuccess = false;
				try {
					isuploadSuccess = HttpCommon.getInstance().uploadFile(
							sourceFile, targetPath);
				} catch (Exception e) {
					logger.error("send file to ocg --error:" + areaCode
							+ " sourceFile:" + sourceFile + " targetPath:"
							+ targetPath, e);
					warnHelper
							.writeWarnMsgToDb("【上传文件到OCG异常，请检查OCG连接】 areaCode: "
									+ areaCode + " ip: " + ocg.getIp());
					return false;
				}
				if (isuploadSuccess == false) {
					logger.error("send file to ocg --error:" + areaCode
							+ " sourceFile:" + sourceFile + " targetPath:"
							+ targetPath);
					return false;
				}
			}
		}
		return true;

	}

	/**
	 * 发送文件到OCG系统
	 * 
	 * @param areaCode
	 *            区域ID
	 * @param 源目文件
	 * @param 目标文件
	 */
	public boolean deleteFile(String areaCode, String sourceFile,
			String targetPath) {

		// 如果区域编码不为空，则只投放对应区域
		List<Ocg> ocgList = InitConfig.getAdsConfig().getOcgList();
		for (int i = 0; i < ocgList.size(); i++) {
			Ocg ocg = ocgList.get(i);
			// 调用ＯＣＧ接口
			// uploadDir(targetPath,sourcePath);
			if (areaCode.equals(ocg.getAreaCode())) {
				HttpCommon.getInstance().initHttp(ocg.getIp());
				String filePath = "";
				if (targetPath.endsWith("/")) {
					filePath = targetPath + sourceFile;
				} else {
					filePath = targetPath + "/" + sourceFile;
				}
				boolean isuploadSuccess = false;
				try {
					logger.info("删除OCG素材 " + filePath);
					isuploadSuccess = HttpCommon.getInstance().deleteFile(
							filePath);
				} catch (Exception e) {
					logger.error("delete file on ocg --erro:" + areaCode
							+ " sourceFile:" + sourceFile + " targetPath:"
							+ targetPath, e);
					warnHelper.writeWarnMsgToDb("【删除失败，请检查OCG连接】 areaCode: "
							+ areaCode + " ip: " + ocg.getIp());
					return false;
				}
				if (isuploadSuccess == false) {
					logger.error("delete file on ocg --erro:" + areaCode
							+ "sourceFile:" + sourceFile + "gargetPath:"
							+ targetPath);
				}
			}
		}
		return true;
	}

	/**
	 * 发送文件到OCG系统
	 * 
	 * @param areaCode
	 *            区域ID
	 * @param 源目录
	 * @param 目标目录
	 */
	public boolean sendPath(String areaCode, String sourcePath,
			String targetPath) {
		File foder = new File(sourcePath);
		String errorMsg = "";
		if (!foder.exists() || !foder.isDirectory()) { // 不存在或非目录
			errorMsg = "【向OCG发送文件夹不存在】" + sourcePath;
			warnHelper.writeWarnMsgToDb(errorMsg);
		} else {
			for (File file : foder.listFiles()) {
				if (file.length() == 0) {
					errorMsg = "【向OCG发送的文件大小为0】" + file.getAbsolutePath(); // 文件大小为0
					file.delete();
					warnHelper.writeWarnMsgToDb(errorMsg);
				}
			}
		}

		// 如果区域编码不为空，则只投放对应区域
		List<Ocg> ocgList = InitConfig.getAdsConfig().getOcgList();
		for (int i = 0; i < ocgList.size(); i++) {
			Ocg ocg = ocgList.get(i);
			if (ocg.getAreaCode().equals(areaCode)) {
				HttpCommon.getInstance().initHttp(ocg.getIp());
				boolean isuploadSuccess = false;
				try {
					isuploadSuccess = HttpCommon.getInstance()
							.uploadDirContent(sourcePath, targetPath);
				} catch (Exception e) {
					logger.error("send path  to ocg --erro:" + areaCode
							+ "sourcePath:" + sourcePath + "gargetPath:"
							+ targetPath, e);
					warnHelper
							.writeWarnMsgToDb("【上传素材到OCG异常，请检查OCG连接】 areaCode: "
									+ areaCode + " ip: " + ocg.getIp());
					return false;
				}
				if (isuploadSuccess == false) {
					logger.error("send path  to ocg --erro:" + areaCode
							+ "sourcePath:" + sourcePath + "gargetPath:"
							+ targetPath);
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 发送文件到OCG系统
	 * 
	 * @param fileName
	 *            文件名
	 * @param adsTypeCode
	 *            广告位编码
	 * @param areaCode
	 *            区域编码
	 */
	public void sendFile(String fileName, String adsTypeCode,
			String adsIdentification, String areaCode) {

	}

	/**
	 * 发送文件到OCG系统
	 * 
	 * @param fileName
	 *            文件名
	 * @param adsTypeCode
	 *            广告位编码
	 * @param areaCode
	 *            区域编码 标清initPic-a.iframe initVideo-a.ts 高清initPic-b.iframe
	 *            initVideo-b.ts
	 */
	public void sendFile(String fileName, String adsTypeCode,
			String adsIdentification, String areaCode, String contentType) {

	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 *            文件名
	 * @param adsTypeCode
	 *            广告位编码
	 * @param areaCode
	 *            区域编码
	 */
	public void deleteFile(String fileName, String adsTypeCode,
			String adsIdentification, String areaCode) {

		// 根据文件名fileName获取待发送文件全路径
		/*
		 * String fromFile = null; //根据areaCode从配置文件中获取OCG目标目录
		 * //开机广告通过areaCode获取OCG目标目录 String toDir = null;
		 * //开机广告以外的其他广告位目标目录为统一的目标目录 toDir =InitConfig.getAdsTargetPath();
		 * 
		 * 
		 * //针对开机广告位，可能需要根据区域编码areaCode获取不同的目标目录toDir if
		 * (ConstantsAdsCode.PUSH_STARTSTB.equals(adsTypeCode)){ //开机广告位
		 * List<AdsArea> areaList =
		 * InitConfig.getAdsConfigByCode(adsTypeCode).getAdsAreaList();
		 * if(areaList != null && areaList.size()>0){ for(AdsArea area :
		 * areaList){ if(area.getAreaCode().equals(areaCode)){
		 * 
		 * toDir = area.getTargetPath(); Ocg ocg =
		 * InitConfig.getOcgConfig(areaCode); try {
		 * ftpService.setServer(ocg.getIp(),Integer.parseInt(ocg.getPort()),
		 * ocg.getUser(),ocg.getPwd()); } catch (Exception e) {
		 * 
		 * } //删除OCG服务上的文件 if (fileName==null || fileName.equals("")) {
		 * ftpService.deleteDirFile(toDir); } else {
		 * ftpService.deleteFile(fileName, toDir); } break; } } } } else {
		 * //如果区域编码不为空，则只投放对应区域 if (areaCode!=null && !areaCode.equals("")) {
		 * Ocg ocg = InitConfig.getOcgConfig(areaCode); try {
		 * ftpService.setServer(ocg.getIp(),Integer.parseInt(ocg.getPort()),
		 * ocg.getUser(),ocg.getPwd()); } catch (Exception e) {
		 * 
		 * } //删除OCG服务上的文件 toDir =ocg.getTargetPath();
		 * 
		 * if (fileName==null || fileName.equals("")) {
		 * ftpService.deleteDirFile(toDir); } else {
		 * ftpService.deleteFile(fileName, toDir); } } else { List<Ocg> ocgList
		 * = InitConfig.getAdsConfig().getOcgList(); for (int
		 * i=0;i<ocgList.size();i++) { Ocg ocg = ocgList.get(i); try {
		 * ftpService.setServer(ocg.getIp(),Integer.parseInt(ocg.getPort()),
		 * ocg.getUser(),ocg.getPwd()); } catch (Exception e) {
		 * 
		 * } //删除OCG服务上的文件 toDir =ocg.getTargetPath();
		 * 
		 * if (fileName==null || fileName.equals("")) {
		 * ftpService.deleteDirFile(toDir); } else {
		 * ftpService.deleteFile(fileName, toDir); } } }
		 * 
		 * 
		 * }
		 */
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 *            文件名
	 * @param adsTypeCode
	 *            广告位编码
	 * @param areaCode
	 *            区域编码
	 */
	public void deleteFile(String fileName, String adsTypeCode,
			String adsIdentification, String areaCode, String contentType) {

	}

	/**
	 * 设置OCG更新标识.
	 * 
	 * @param updateType
	 *            1:开机画面 (initPic.iframe)更新 5:开机视频或动画（initVideo.ts)更新
	 */
	public void startOcg(String updateType) {
	}

	public void startPlay(String areaCode, String name) {
	}

	public void startPlayPgm(String areaCode, String pgmname, String outputname) {
	}

	public void downloadDir(String areaCode, String savePath, String serverPath) {
		List<Ocg> ocgList = InitConfig.getAdsConfig().getOcgList();
		for (Ocg ocg : ocgList) {
			if (ocg.getAreaCode().equals(areaCode)) {
				try {
					HttpCommon.getInstance().initHttp(ocg.getIp());
					boolean isDownloadSuccess = HttpCommon.getInstance()
							.downloadDir(savePath, serverPath);
					if (isDownloadSuccess == false) {
						logger.error("download dir from ocg --erro:" + areaCode
								+ "serverPath:" + serverPath);
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("download dir from ocg --erro:" + areaCode
							+ "serverPath:" + serverPath);
				}
			}
		}
	}

	public void downloadFile(String areaCode, String savePath, String serverPath) {
		List<Ocg> ocgList = InitConfig.getAdsConfig().getOcgList();
		for (Ocg ocg : ocgList) {
			if (ocg.getAreaCode().equals(areaCode)) {
				try {
					HttpCommon.getInstance().initHttp(ocg.getIp());
					boolean isDownloadSuccess = HttpCommon.getInstance()
							.downloadFile(savePath, serverPath);
					if (isDownloadSuccess == false) {
						logger.error("error:" + areaCode + "serverPath:"
								+ serverPath);
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("download file from ocg --erro:" + areaCode
							+ "serverPath:" + serverPath);
				}
			}
		}
	}

	/**
	 * OCG集成UNT信息发送
	 */

	public boolean sendUNTMessageUpdate(String areaCode, int sendType,
			Object message) {

		UNTMessage uNTMessage = new UNTMessage();
		uNTMessage.setSendType(sendType + "");

		switch (sendType) {
		case ConstantsHelper.REALTIME_UNT_MESSAGE_WEATHER:
			uNTMessage.setWeatherforecast((Weatherforecast) message);
			break;
		case ConstantsHelper.REALTIME_UNT_MESSAGE_MSUBTITLE:
			uNTMessage.setMsubtitleInfo((MsubtitleInfo) message);
			break;
		case ConstantsHelper.REALTIME_UNT_MESSAGE_ADCONFIG:
			uNTMessage.setAdsConfigJs((AdsConfigJs) message);
			break;
		case ConstantsHelper.REALTIME_UNT_MESSAGE_ADIMAGE:
			uNTMessage.setAdsImage((AdsImage) message);
			break;
		case ConstantsHelper.REALTIME_UNT_MESSAGE_STB:
			uNTMessage.setSystemMaintain((SystemMaintain) message);
			break;
		case ConstantsHelper.REALTIME_UNT_MESSAGE_CHANNE:
			uNTMessage.setChannelrecomendurl((Channelrecomendurl) message);
			break;
		default:
			break;
		}

		String sendMsg = helper.toXML(uNTMessage);

		List<Ocg> ocgList = InitConfig.getAdsConfig().getOcgList();
		for (Ocg ocg : ocgList) {
			if (ocg.getAreaCode().equals(areaCode)) {
				String ip = ocg.getIp();
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
						logger.info("UNT更新信息成功");
						return true;
					} else if ("400".equals(retMsgEntity.getCode())) {
						logger.error("UNT更新信息失败：请求参数格式不正确 ");
					} else if ("401".equals(retMsgEntity.getCode())) {
						logger.error("UNT更新信息失败");
					}
				} catch (Exception e) {
					logger.error("OCG返回消息解析异常", e);
				}
				break;
			}
		}

		return false;
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
		OcgServiceImpl ocgService = new OcgServiceImpl();
		String ocgXml = initIntefaceDebug();
		//String ocgXmlU = new String(ocgXml.getBytes("GBK"), "GB2312");  
		
		//String ip = "192.168.100.65";
		String ip = "192.168.112.65";
		int port = 30005;
		System.out.println("Start Send---------------->");
		byte[] retBuf = ocgService.sendUdpMsg(ip, port,ocgXml);
		if (null == retBuf || retBuf.length == 0) {
			System.out.println("没有返回数据");
			return;
		}
		String retMsg = new String(retBuf).trim();
		/*for(byte b :retMsg.getBytes()){
			System.out.println(b);
		}*/
		//String msg = "<?xml version=\"1.0\" encoding=\"utf-8\"?><serverResponse code=\"200\" />";
		//System.out.println(retMsg.equals(msg));	
		//for(byte b :msg.getBytes()){
		//	System.out.println(b);
		//}
		//dealIntefaceRs(msg);
		dealIntefaceRs(retMsg);
		

	}
	
	public static String initIntefaceDebug(){
		
		// sendFile
		OcgPlayMsg ocgPalyMsg = new OcgPlayMsg();
		ocgPalyMsg.setSendPath("ftp://abc:abc@192.168.128.128/ui");
		ocgPalyMsg.setSendType("1");
		
		// sendUIMessage
		UiUpdateMsg uiMsg = new UiUpdateMsg();
		uiMsg.setUpdateType("1:initPic-c.iframe,5:0");
		uiMsg.setNetworkID("6280");
		uiMsg.setServicesID("0");
		uiMsg.setTsID("10086");

		UNTMessage uNTMsg = initUNTMsg(1);
		//UNTMessage uNTMsg = initUNTMsg(2);
		//UNTMessage uNTMsg = initUNTMsg(3);
		//UNTMessage uNTMsg = initUNTMsg(4);
		//UNTMessage uNTMsg = initUNTMsg(5);
		//UNTMessage uNTMsg = initUNTMsg(6);
		
		JaxbXmlObjectConvertor jaxbhelper = JaxbXmlObjectConvertor
		.getInstance();
        //String ocgXml = jaxbhelper.toXML(ocgPalyMsg);
        //String ocgXml = jaxbhelper.toXML(uiMsg);
        String ocgXml = jaxbhelper.toXML(uNTMsg);
        return ocgXml;
	}

	public static void dealIntefaceRs(String retMsg){
		JaxbXmlObjectConvertor jaxbhelper = JaxbXmlObjectConvertor
		.getInstance();
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
	
	public static UNTMessage initUNTMsg(int type){
		//
		Weatherforecast weather = new Weatherforecast();
	    weather.setWord(new String("郑州明日天气：晴。气温：23-27度"));
		
		//weather.setWord(new String("ZhenZhou tommorrow weather sun,tem 23-270C"));
		weather.setNetworkId("100");
		weather.setUiId("a");
		//
		MsubtitleInfo subtitle = new MsubtitleInfo();
		subtitle.setActionType("1");
		subtitle.setServiceID("-1");
		subtitle.setTimeout("10");
		subtitle.setFontSize("16");
		subtitle.setFontColor("345");
		subtitle.setBackgroundX("50");
		subtitle.setBackgroundY("50");
		subtitle.setBackgroundWidth("80");
		subtitle.setBackgroundHeight("80");
		subtitle.setBackgroundColor("346");
		subtitle.setShowFrequency("8");
		subtitle.setWord("滚动字幕");
		//subtitle.setWord("Move Words");
		subtitle.setUiId("b");
		//
		AdsConfigJs adsConfig = new AdsConfigJs();
		adsConfig.setFilepath("ads/cofig/asdConfig.js");
		adsConfig.setUiId("b");
		//
		AdsImage adsImage = new AdsImage();
		adsImage.setFilepath("ads/cofig/asdConfig.png");
		adsImage.setUiId("c");
		//
		SystemMaintain stb = new SystemMaintain();
		stb.setActionCode("0");
		stb.setActiveHour("5");
		//
		Channelrecomendurl channel = new Channelrecomendurl();
		channel.setServiceID("-1");
		channel.settVNType("2");
		channel.settVN("1380");
		channel.setcAIndustryType("0");
		channel.setcAUserLevel("0");
		channel.setuRL("http://www.avit.com");
		
		// sendUntMessage
		UNTMessage uNTMsg = new UNTMessage();
		switch (type) {
		case 1:
			uNTMsg.setSendType("1");
			uNTMsg.setWeatherforecast(weather);
			break;

		case 2:
			uNTMsg.setSendType("2");
			uNTMsg.setMsubtitleInfo(subtitle);
			break;
		case 3:
			uNTMsg.setSendType("3");
			uNTMsg.setAdsConfigJs(adsConfig);
			break;
			
		case 4:
			uNTMsg.setSendType("4");
			uNTMsg.setAdsImage(adsImage);
			break;
		case 5:
			uNTMsg.setSendType("5");
			uNTMsg.setSystemMaintain(stb);
			break;
		case 6:
			uNTMsg.setSendType("6");
			uNTMsg.setChannelrecomendurl(channel);
			uNTMsg.setMsubtitleInfo(subtitle);
			break;
		default:
			break;
		}
		return uNTMsg;
		

		
	}
	

}
