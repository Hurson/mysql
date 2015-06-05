package com.avit.ads.pushads.ocg.service.impl;

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

import com.avit.ads.pushads.ocg.GenerateFileJni;
import com.avit.ads.pushads.ocg.bean.HeaderInfo;
import com.avit.ads.pushads.ocg.dao.OcgInfoDao;
import com.avit.ads.pushads.ocg.dao.UntDao;
import com.avit.ads.pushads.ocg.service.OcgService;
import com.avit.ads.pushads.task.bean.OcgInfo;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.bean.Ocg;
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
	
	@Inject
	private OcgInfoDao ocgInfoDao;
	
	@Autowired
	private UntDao untDao;	

	JaxbXmlObjectConvertor helper = JaxbXmlObjectConvertor.getInstance();
	
	
	

	public boolean connectFtpServer(String areaCode) {

		List<OcgInfo> ocgList = ocgInfoDao.getOcgInfoList();
		for (OcgInfo ocg : ocgList) {
			if (ocg.getAreaCode().equals(areaCode)) {
				String ip = ocg.getIp();
				int port = Integer.parseInt(ocg.getPort());
				String user = ocg.getUser();
				String pwd = ocg.getPwd();
				return ftpService.connectServer(ip, port, user, pwd);
			}
		}

		String errMsg = "未配置区域【" + areaCode + "】的OCG FTP连接信息";
		logger.error(errMsg);
		warnHelper.writeWarnMsgToDb(areaCode, errMsg);
		return false;
	}
	
	
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

	public boolean startOcgPlay(String areaCode, String sendPath, String sendType, String adsType) {

		OcgPlayMsg sendMsgEntity = new OcgPlayMsg();
		sendMsgEntity.setSendPath(sendPath);
		sendMsgEntity.setSendType(sendType);
		sendMsgEntity.setAdsType(adsType);

		String sendMsg = helper.toXML(sendMsgEntity);

		//List<Ocg> ocgList = InitConfig.getAdsConfig().getOcgList();

		List<OcgInfo> ocgList = ocgInfoDao.getOcgInfoList();
		for (OcgInfo ocg : ocgList) {
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

	public boolean startOcgPlayByIp(String ip, String sendPath, String sendType, String adsType) {
		
		OcgPlayMsg sendMsgEntity = new OcgPlayMsg();
		sendMsgEntity.setSendPath(sendPath);
		sendMsgEntity.setSendType(sendType);
		sendMsgEntity.setAdsType(adsType);

		String sendMsg = helper.toXML(sendMsgEntity);
		
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


	public boolean sendUiDesc(String areaCode, String description, String filePath) {
		UiUpdateMsg sendMsgEntity = new UiUpdateMsg();
		sendMsgEntity.setUpdateType(description);
		String nid = InitConfig.getAreaMap().get(areaCode);
		sendMsgEntity.setNetworkID(nid);
		sendMsgEntity.setFilePath(filePath);
		
		String sendMsg = helper.toXML(sendMsgEntity);

		//List<Ocg> ocgList = InitConfig.getAdsConfig().getOcgList();
		//modify  xml-config  to DATABASE  DAO
		List<OcgInfo> ocgList = ocgInfoDao.getOcgInfoList();
		
		for (OcgInfo ocg : ocgList) {
			if (ocg.getAreaCode().equals("0")) {
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

	private HttpResponse sendHttpMsg(String areaCode, HeaderInfo header,String url, String fileName){
		
		HttpClient httpclient = new DefaultHttpClient();
		
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000); 
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);

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
				
				String errorMsg = "区域【"+ areaCode +"】频点【"+fileName.substring(fileName.lastIndexOf(File.separator))+"】文件生成异常";
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
			warnHelper.writeWarnMsgToDb(areaCode, errorMsg);
			return true; // 不发送到OCG，流程正常往下走
		}

		// 如果区域编码不为空，则只投放对应区域
		//List<Ocg> ocgList = InitConfig.getAdsConfig().getOcgList();
		//modify  xml-config  to DATABASE  DAO
		List<OcgInfo> ocgList = ocgInfoDao.getOcgInfoList();
		for (int i = 0; i < ocgList.size(); i++) {
			OcgInfo ocg = ocgList.get(i);
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
							.writeWarnMsgToDb(areaCode, "【上传文件到OCG异常，请检查OCG连接】 areaCode: "
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
		//List<Ocg> ocgList = InitConfig.getAdsConfig().getOcgList();
		//modify  xml-config  to DATABASE  DAO
		List<OcgInfo> ocgList = ocgInfoDao.getOcgInfoList();
		for (int i = 0; i < ocgList.size(); i++) {
			OcgInfo ocg = ocgList.get(i);
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
					warnHelper.writeWarnMsgToDb(areaCode, "【删除失败，请检查OCG连接】 areaCode: "
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
			warnHelper.writeWarnMsgToDb(areaCode, errorMsg);
		} else {
			for (File file : foder.listFiles()) {
				if (file.length() == 0) {
					errorMsg = "【向OCG发送的文件大小为0】" + file.getAbsolutePath(); // 文件大小为0
					file.delete();
					warnHelper.writeWarnMsgToDb(areaCode, errorMsg);
				}
			}
		}

		// 如果区域编码不为空，则只投放对应区域
		//List<Ocg> ocgList = InitConfig.getAdsConfig().getOcgList();
		//modify  xml-config  to DATABASE  DAO
		List<OcgInfo> ocgList = ocgInfoDao.getOcgInfoList();
		for (int i = 0; i < ocgList.size(); i++) {
			OcgInfo ocg = ocgList.get(i);
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
							.writeWarnMsgToDb(areaCode, "【上传素材到OCG异常，请检查OCG连接】 areaCode: "
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
		//List<Ocg> ocgList = InitConfig.getAdsConfig().getOcgList();
		//modify  xml-config  to DATABASE  DAO
		List<OcgInfo> ocgList = ocgInfoDao.getOcgInfoList();
		for (OcgInfo ocg : ocgList) {
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
		//List<Ocg> ocgList = InitConfig.getAdsConfig().getOcgList();
		//modify  xml-config  to DATABASE  DAO
		List<OcgInfo> ocgList = ocgInfoDao.getOcgInfoList();
		for (OcgInfo ocg : ocgList) {
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

	//http发送ts文件
	/*
	public boolean sendUNTMessageToOCG(int version, String url, int sendType, Object message, HeaderInfo headerInfo){
//		InitConfig init = new InitConfig();
//		init.initConfig();
		String destPath =InitConfig.getConfigMap().get(ConstantsHelper.DEST_FILE_PATH);
		String logPath = InitConfig.getConfigMap().get(ConstantsHelper.LOG_FILE_PATH)+File.separator+ConstantsHelper.LOG_FILE_NAME;
		
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
			uNTMessage.setAdsConfig((AdsConfigJs) message);
			break;
		case ConstantsHelper.REALTIME_UNT_MESSAGE_ADIMAGE:
			uNTMessage.setAdsImage((AdsImage) message);
			break;
		case ConstantsHelper.REALTIME_UNT_MESSAGE_STB:
			uNTMessage.setSystemMaintain((SystemMaintain) message);
			break;
		case ConstantsHelper.REALTIME_UNT_MESSAGE_CHANNE_RECOMMEND:
			//uNTMessage.setChannelrecomendurl((Channelrecomendurl) message);
			break;
		default:
			break;
		}

		String sendMsg = helper.toXML(uNTMessage);
		
		try{
			boolean result = GenerateFileJni.getInstance().geneTSFile(sendMsg, version, version, destPath, logPath);
			
			if(result){
			
					HttpResponse res = sendHttpMsg(headerInfo,url, destPath+File.separator+sendType+ConstantsHelper.TS_FILE_SUFFIX);
					int code = res.getStatusLine().getStatusCode();
					if(code == 200){
						logger.info("发送OCG成功！");
						return true;
					}else if(code == 400){
						logger.info("参数格式不正确！");
					}else if(code == 401){
						logger.info("发送OCG消息失败！");
					}
			
			}
		}catch(Exception e){
			logger.error("OCG发送异常:"+e.getMessage());
		}
		
		return false;
	}*/
	/**
	 * OCG集成UNT信息发送
	 */

	public boolean sendUNTMessageUpdate(String areaCode, int sendType, Object message) {

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
		case ConstantsHelper.REALTIME_UNT_MESSAGE_CHANNE_RECOMMEND:
			//uNTMessage.setChannelrecomendurl((Channelrecomendurl) message);
			break;
		default:
			break;
		}

		String sendMsg = helper.toXML(uNTMessage);

		//List<Ocg> ocgList = InitConfig.getAdsConfig().getOcgList();

		List<OcgInfo> ocgList = ocgInfoDao.getOcgInfoList();
		for (OcgInfo ocg : ocgList) {
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
	
	

	public boolean sendUNTMessageUpdateByIp(String ip, int sendType, Object message) {
		
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
				//uNTMessage.setChannelrecomendurl((Channelrecomendurl) message);
				break;
			default:
				break;
		}

		String sendMsg = helper.toXML(uNTMessage);
				
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
		return false;	
	}
	

	public boolean sendUNTMessageUpdateByIp(String ip, int sendType, Object message, String areaCode) {
		
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
		String destPath =InitConfig.getConfigMap().get(ConstantsHelper.DEST_FILE_PATH);
		String logPath = InitConfig.getConfigMap().get(ConstantsHelper.LOG_FILE_PATH)+File.separator+ConstantsHelper.LOG_FILE_NAME;
		int version = untDao.getUntVersion(areaCode, sendType);
		version = (version + 1) % 32;
		
		boolean result = GenerateFileJni.getInstance().geneTSFile(sendMsg.getBytes(), version, version, destPath, logPath);
						
		if(result){
			Ocg ocg = InitConfig.getOcgConfig("" + sendType);
			if(null == ocg){
				logger.error("未配置unt类型：" + sendType + " 的OCG播发信息");
				return false;
			}
			
			HeaderInfo headerInfo = new HeaderInfo(ocg.getStreamId(), ocg.getBitrate(),ocg.getSendAddress(), ocg.getSendPort());
			String url = "http://" + ip + ":" + ConstantsHelper.OCG_HTTP_PORT;
			
			HttpResponse res = sendHttpMsg(areaCode, headerInfo, url, destPath+File.separator+sendType+ConstantsHelper.TS_FILE_SUFFIX);
			if(null == res){
				return false;
			}
			int code = res.getStatusLine().getStatusCode();
			if(code == 200){
				untDao.updateVersion(areaCode, sendType);
				logger.info("UNT更新成功！");
				return true;
			}else if(code == 400){
				logger.info("参数格式不正确！");
			}else if(code == 401){
				logger.info("发送OCG消息失败！");
			}
		}else{
			logger.error("构造unt表失败.");
		}
		return false;	
	}
	
	public boolean sendUntUpdateByAreaCode(int sendType, Object message, String areaCode, String tsId) {
		
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
		String destPath =InitConfig.getConfigMap().get(ConstantsHelper.DEST_FILE_PATH) + File.separator + areaCode;
		
		String logPath = InitConfig.getConfigMap().get(ConstantsHelper.LOG_FILE_PATH)+File.separator+ConstantsHelper.LOG_FILE_NAME;
		int version = untDao.getUntVersion(areaCode, sendType);
		version = (version + 1) % 32;
		boolean result = GenerateFileJni.getInstance().geneTSFile(sendMsg.getBytes(), version, version, destPath, logPath);
		boolean success = false;
		
		if(result){
			List<OcgInfo> ocgList = ocgInfoDao.getOcgMulticastInfoList(areaCode, tsId);
			boolean ocgExist = false;
			for (OcgInfo ocg : ocgList) {
				if (ocg.getAreaCode().equals(areaCode)) {
					ocgExist = true;	
					HeaderInfo headerInfo = new HeaderInfo(ocg.getStreamId(), ocg.getMulticastBitrate(),ocg.getMulticastIp(), ocg.getMulticastPort());
					String url = "http://" + ocg.getIp() + ":" + ConstantsHelper.OCG_HTTP_PORT;
					HttpResponse res = sendHttpMsg(areaCode, headerInfo, url, destPath+File.separator + ConstantsHelper.SEND_FILE + File.separator + ocg.getTsId() + ConstantsHelper.TS_FILE_SUFFIX);
					if(null == res){
						break;
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
						break;
					}
				}
			}
			if(success){
				untDao.updateVersion(areaCode, sendType);
			}
			if(!ocgExist){
				String errMsg = "t_ocginfo表未配置区域【" + areaCode + "】的OCG信息和频点信息";
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
		stb.setActionCode("0");
		stb.setActiveHour("5");
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
