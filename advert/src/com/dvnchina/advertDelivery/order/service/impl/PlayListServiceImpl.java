package com.dvnchina.advertDelivery.order.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;

import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.order.bean.playlist.MaterialBean;
import com.dvnchina.advertDelivery.order.bean.playlist.OrderBean;
import com.dvnchina.advertDelivery.order.bean.playlist.TextMate;
import com.dvnchina.advertDelivery.order.dao.PlayListDao;
import com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo;
import com.dvnchina.advertDelivery.utils.config.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.ftp.FtpUtils;

public class PlayListServiceImpl {
	private PlayListDao playListDao;

	public void setPlayListDao(PlayListDao playListDao) {
		this.playListDao = playListDao;
	}

	/**
	 * 根据订单编号查询订单记录，填充绑定的素材记录
	 * 
	 * @param orderId
	 *            订单编号
	 * @return
	 */
	protected OrderBean getOrder(Integer orderId) {
		return playListDao.getOrderById(orderId);
	}

	/**
	 * 获取区域编号字符串
	 * 
	 * @param areaIds
	 *            区域编号集合
	 * @return 区域编号字符串
	 */
	protected String getAreaIdStr(Set<String> areaIds) {
		StringBuffer areaId = new StringBuffer();
		Iterator<String> iter = areaIds.iterator();
		int i = 0;
		while (iter.hasNext()) {
			if (i > 0) {
				areaId.append(",");
			}
			areaId.append(iter.next());
			i++;
		}
		return areaId.toString();
	}

	/**
	 * 获取频道字符串
	 * 
	 * @param areaIds
	 *            区域编号集合
	 * @param ployId
	 *            策略编号
	 * @return 频道编号字符串
	 */
	protected String getChannelIdStr(Set<String> areaIds, Integer ployId) {
		StringBuffer channelId = new StringBuffer("[");
		Iterator<String> iter = areaIds.iterator();
		int i = 0;
		while (iter.hasNext()) {
			if (i > 0) {
				channelId.append(",");
			}
			channelId.append("\"");
			List<String> cIds = playListDao.getChannelByArea(ployId, iter
					.next());
			for (int j = 0; j < cIds.size(); j++) {
				if (j > 0) {
					channelId.append(",");
				}
				channelId.append(cIds.get(j));
			}
			channelId.append("\"");
			i++;
		}
		channelId.append("]");
		return channelId.toString();
	}
	
	/**
	 * 根据频道ID获取serviceId字符信息
	 * @param channelId
	 * @return
	 */
//	protected String getDtvServiceById(String channelIds) {
//		StringBuffer serviceId = new StringBuffer("[\"");
//		List<String> cIds = playListDao.getDtvServiceById(channelIds);
//		for (int j = 0; j < cIds.size(); j++) {
//			if (j > 0) {
//				serviceId.append(",");
//			}
//			serviceId.append(cIds.get(j));
//		}
//		serviceId.append("\"]");
//		return serviceId.toString();
//	}
	
	/**
	 * 根据频道ID获取serviceId字符信息
	 * @param channelId
	 * channel
	 * @return
	 */
	public String getDtvServiceById(String channelIds, ChannelInfo channel) {
		StringBuffer serviceId = new StringBuffer();
		List<String> cIds = playListDao.getDtvServiceById(channelIds,channel);
		for (int j = 0; j < cIds.size(); j++) {
			if (j > 0) {
				serviceId.append(",");
			}
			serviceId.append(cIds.get(j));
		}
		return serviceId.toString();
	}
	
	/**
	 * 获取频道表中的所有serviceIds
	 */
//	protected String getAllServiceIds() {
//		StringBuffer serviceIds = new StringBuffer();
//		List<String> serviceIdList = playListDao.getAllServiceIdList();
//		for(String id : serviceIdList){
//			serviceIds.append(id).append(",");
//		}
//		return serviceIds.toString();
//	}
	
	/**
	 * 根据频道ID获取回放serviceId字符信息
	 * @param channelIds
	 * @return
	 */
	protected String getPlaybackServiceId(String channelIds) {
		StringBuffer serviceId = new StringBuffer();
		List<String> cIds = playListDao.getPlaybackServiceId(channelIds);
		for (int j = 0; j < cIds.size(); j++) {
			if (j > 0) {
				serviceId.append(",");
			}
			serviceId.append(cIds.get(j));
		}
		return serviceId.toString();
	}
	
	/**
	 * 回看根据ID获取栏目ID
	 * @param ids
	 * @return
	 */
	protected String getLookbackCategortId(String ids) {
		StringBuffer categoryId = new StringBuffer();
		List<String> cIds = playListDao.getLookbackCategortId(ids);
		for (int j = 0; j < cIds.size(); j++) {
			if (j > 0) {
				categoryId.append(",");
			}
			categoryId.append(cIds.get(j));
		}
		return categoryId.toString();
	}
	
	/**
	 * 节目ID获取assetId
	 */
	protected String getAssetId(String ids) {
		StringBuffer assetId = new StringBuffer();
		List<String> aIds = playListDao.getAssetId(ids);
		for (int j = 0; j < aIds.size(); j++) {
			if (j > 0) {
				assetId.append(",");
			}
			assetId.append(aIds.get(j));
		}
		return assetId.toString();
	}
	
	/**
	 * 根据频道组IDS获取serviceIds
	 * @param groupIds
	 * @return
	 */
	protected String getServiceIdByGroupIds(String groupIds) {
		StringBuffer serviceId = new StringBuffer();
		List<String> sIds = playListDao.getServiceIdByGroupIds(groupIds);
		for (int j = 0; j < sIds.size(); j++) {
			if (j > 0) {
				serviceId.append(",");
			}
			serviceId.append(sIds.get(j));
		}
		return serviceId.toString();
	}

	/**
	 * 生成并发送播出单文件
	 * 
	 * @param fileName
	 *            文件名称
	 * @param fileContent
	 *            文件内容
	 * @return 上传路径
	 */
	protected String generateFile(String fileName, String fileContent) {
		String tempPath = fileName + ".txt";
		String ftpDir = this.getFtpDir();
		String ftpPath = this.getFtpDir() + fileName + ".txt";
		FileWriter fw = null;
		PrintWriter pw = null;
		FtpUtils ftpUtil = null;
		File file = null;
		try {
			file = new File(tempPath);
			if (!file.exists()) {
				file.createNewFile();
			}
			fw = new FileWriter(file);
			// 把该对象包装进PrinterWriter对象
			pw = new PrintWriter(fw);
			// 再通过PrinterWriter对象的println()方法把字符串数据写入新建文件
			pw.println(fileContent);
			pw.close();
			fw.close();
			ftpUtil = new FtpUtils();
			ftpUtil.connectionFtp();
			ftpUtil.sendFileToFtp(tempPath, ftpDir);
		} catch (Exception e) {
			e.printStackTrace();
			ftpPath = null;
		} finally {
			ftpUtil.closedFtp();
			if (file.exists()) {
				file.delete();
			}
		}
		return ftpPath;
	}

	/**
	 * 获取配置文件播出单文件上传地址
	 * 
	 * @return
	 */
	protected String getFtpDir() {
		StringBuffer dir = new StringBuffer();
		ConfigureProperties config = ConfigureProperties.getInstance();
		String ftpDir = config.get("ftp.advertDirectory");
		String plDir = config.get("ftp.playlistDirectory");
		dir.append(ftpDir);
		if(!ftpDir.endsWith("/")){
			dir.append("/");
		}
		dir.append(plDir);
		if(!plDir.endsWith("/")){
			dir.append("/");
		}
		return dir.toString();
	}

	/**
	 * 根据高标清状态码返回特征值高标清描述字符串
	 * */
	protected String getHDStr(int isHD) {
		String str = "HD";
		if (isHD == Constant.ADVERT_POSITION_NOT_HD) {
			str = "SD";
		}
		return str;
	}

	/**
	 * 根据高标清状态码和广告位特征值返回字符串
	 * */
//	protected String getIdentificationStr(int isHD, String indentification) {
//		String str = this.getHDStr(isHD);
//		StringBuffer buf = new StringBuffer(str);
//		buf.append(",");
//		buf.append(indentification);
//		return buf.toString();
//	}
	
//	/**
//	 * 根据高标清状态码，插播位置和广告位特征值返回字符串
//	 * */
//	protected String getIdentificationStr(int isHD, int playLocation, int instreamNumber,String indentification) {
//		String str = this.getHDStr(isHD);
//		StringBuffer buf = new StringBuffer(str);
//		buf.append(",");
//		if (playLocation == 0) {
//			buf.append("0");
//		} else {
//			buf.append(playLocation).append("/").append(instreamNumber);
//		}
//		buf.append(",");
//		buf.append(indentification);
//		return buf.toString();
//	}
	
	/**
	 * 根据高标清状态码，插播位置和广告位特征值返回字符串
	 * */
	protected String getIdentificationStr(int isHD, int playLocation, int instreamNumber) {
		String str = this.getHDStr(isHD);
		StringBuffer buf = new StringBuffer(str);
		buf.append(",");
		if (playLocation == 0) {
			buf.append("0");
		} else {
			buf.append(playLocation).append("/").append(instreamNumber);
		}
		return buf.toString();
	}
	
	/**
	 * 根据高标清状态码，插播位置和广告位特征值返回字符串
	 * */
	protected String getIdentificationStr2(int isHD, String playLocation) {
		String str = this.getHDStr(isHD);
		StringBuffer buf = new StringBuffer(str);
		if(StringUtils.isNotBlank(playLocation)){
			buf.append(",");
			buf.append(playLocation);
		}
		return buf.toString();
	}

	/**
	 * 根据内容类型，将内容id拼成资源类型前缀加资源id的形式
	 * @param contentId
	 * @param contentType
	 * @return
	 */
	protected String getContentId(int contentId, int contentType) {
//		String cId = null;
//		switch (contentType) {
//		case Constant.IMAGE:
//			cId = Constant.IMAGE_PREFIX + contentId;
//			break;
//		case Constant.VIDEO:
//			cId = Constant.VIDEO_PREFIX + contentId;
//			break;
//		case Constant.WRITING:
//			cId = Constant.WRITING_PREFIX + contentId;
//			break;
//		case Constant.QUESTIONNAIRE:
//			cId = Constant.QUESTIONNAIRE_PREFIX + contentId;
//			break;
//		}
		return contentId+"";
	}
	
	/**
	 * 获取文字的JSON内容
	 * @param m
	 * @return
	 */
	protected String getWritingJsonContent(MaterialBean m){
		StringBuffer jsonContent = new StringBuffer();
		TextMate text = m.getText();
		jsonContent.append("{\"id\":\"").append(text.getId()).append("\",")
		.append("\"name\":\"").append(text.getName()).append("\",")
		.append("\"URL\":\"").append(text.getURL()).append("\",")
		.append("\"action\":\"").append(text.getAction()).append("\",")
		.append("\"bkgColor\":\"").append(text.getBkgColor()).append("\",")
		.append("\"content\":\"").append(text.getContent()).append("\",")
		.append("\"durationTime\":\"").append(text.getDurationTime()).append("\",")
		.append("\"fontColor\":\"").append(text.getFontColor()).append("\",")
		.append("\"fontSize\":\"").append(text.getFontSize()).append("\",")
		.append("\"positionVertexCoordinates\":\"").append(text.getPositionVertexCoordinates()).append("\",")
		.append("\"positionWidthHeight\":\"").append(text.getPositionWidthHeight()).append("\",")
		.append("\"rollSpeed\":\"").append(text.getRollSpeed()).append("\"}");
		return jsonContent.toString();
	}
	
	/**
	 * 生成字幕文件内容
	 * @param ploy 策略对象
	 * @param m 素材对象
	 * @return 文件内容
	 */
//	protected String getWritingFileContent(PloyBean ploy,MaterialBean m){
//		StringBuffer fileContent = new StringBuffer();
//		fileContent.append("action=").append(ploy.getAction())
//				.append("\n");
//		fileContent.append("content=").append(m.getContent())
//				.append("\n");
//		fileContent.append("durationTime=").append(
//				ploy.getDurationTime()).append("\n");
//		fileContent.append("fontColor=")
//				.append(ploy.getFontColor()).append("\n");
//		fileContent.append("fontSize=").append(ploy.getFontSize())
//				.append("\n");
//		String size = ploy.getWidthHeight();
//		String[] sizes = size.split("\\*");
//		String co = ploy.getCoordinates();
//		String[] cos = co.split("\\*");
//		fileContent.append("regionAbove=").append(cos[1]).append(
//				"\n");
//		fileContent.append("regionHeight=").append(sizes[1])
//				.append("\n");
//		fileContent.append("regionLeft=").append(cos[0]).append(
//				"\n");
//		fileContent.append("regionWidth=").append(sizes[0]).append(
//				"\n");
//		fileContent.append("regionColor=").append(
//				ploy.getBackgroundColor()).append("\n");
//		fileContent.append("speed=").append(ploy.getRollSpeed())
//				.append("\n");
//		fileContent.append("tvn=").append(ploy.getTvnNumber())
//				.append("\n");
//		fileContent.append("linkurl=").append(m.getUrl()).append(
//				"\n");
//		return fileContent.toString();
//	}
	
	 
	/**
	 * 根据策略ID获取区域、serviceId列表
	 * @param ployId
	 * @return
	 */
//	protected List<PloyBean> getPloyChannelList(Integer ployId){
//		return playListDao.getPloyChannelList(ployId);
//	}
	
	/**
	 * 根据策略ID获取区域、serviceId列表
	 * @param ployId
	 * @return
	 */
//	protected List<PloyBean> getPloyNpvrList(Integer ployId){
//		return playListDao.getPloyNpvrList(ployId);
//	}
}
