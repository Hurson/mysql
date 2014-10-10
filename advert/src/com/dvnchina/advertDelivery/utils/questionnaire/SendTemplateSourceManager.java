package com.dvnchina.advertDelivery.utils.questionnaire;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dvnchina.advertDelivery.constant.TemplateConstant;
import com.dvnchina.advertDelivery.utils.ResourceServerConstant;
import com.dvnchina.advertDelivery.utils.ResourceServerConstant.Serverinfo;
import com.dvnchina.advertDelivery.utils.ftp.ResourceSyncUtil;


public class SendTemplateSourceManager {
	private static Log log = LogFactory.getLog(SendTemplateSourceManager.class);
	
	/**
	 * 获得上传的ftp服务器连接
	 * 
	 * @param stream
	 * @param toDir
	 * @param fileName
	 * @return
	 */
	public static List<Map> getResourceSyncUtils() {
		List<Serverinfo> serverInfoList = ResourceServerConstant.getInstance()
				.loadServerinfoS();
		List<Map> list = new ArrayList();
		for (Serverinfo server : serverInfoList) {
			String ip = server.getIp();
			int port = server.getPort();
			String username = server.getUsername();
			String password = server.getPassword();
			try {
				ResourceSyncUtil util = new ResourceSyncUtil(ip, port,
						username, password);
				Map map = new HashMap();
				map.put(TemplateConstant.SERVER, server);
				map.put(TemplateConstant.UTIL, util);
				list.add(map);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 关闭连接
	 * @param list
	 */
	public static void closeUtils(List<Map> list){
		for(Map map:list){
			ResourceSyncUtil util = (ResourceSyncUtil) map.get(TemplateConstant.UTIL);
			util.closeFTP();
		}
	}
	
	/**
	 * 
	 * @param stream
	 *            上传文件的流
	 * @param mainTemplateName
	 *            主模板文件名 带.html后缀 所以要截
	 * @param mainTemplateType
	 *            //主模板的类型
	 * @param romotedir
	 *            //存放ftp路径
	 * @throws Exception 
	 */
	public static void sendTemplateSourceToFtp(String stream,
			String mainTemplateName, String romotedir,
			List<Map> utils,String fileName) throws Exception {
		String templateName = mainTemplateName.substring(1, mainTemplateName
				.indexOf("."));
		String savePath = "/"+romotedir + "/" + templateName
				+ TemplateConstant.MAIN;
		
		ResourceSyncUtil.sendATemplateFilePathResourceServer(stream, savePath,
				fileName, utils);
	}
	/**
	 * 
	 * @param stream
	 *            上传文件的流
	 * @param mainTemplateType
	 *            //主模板的类型
	 * @param romotedir
	 *            //存放ftp路径
	 * @throws Exception 
	 */
	public static void sendTemplateSourceToFtp(String stream,
			String romotedir,
			List<Map> utils,String fileName) throws Exception {
		String savePath = "/"+romotedir;
		
		ResourceSyncUtil.sendATemplateFilePathResourceServer(stream, savePath,
				fileName, utils);
	}
}
