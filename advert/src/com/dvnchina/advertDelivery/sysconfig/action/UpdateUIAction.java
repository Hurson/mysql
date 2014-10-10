package com.dvnchina.advertDelivery.sysconfig.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.avit.ads.webservice.AdsClient;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.ploy.service.PloyService;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.file.FileUtil;
import com.dvnchina.advertDelivery.utils.ftp.FtpUtils;

public class UpdateUIAction extends BaseAction{
	
	private static final long serialVersionUID = -3235343638046726865L;
	private static ConfigureProperties config = ConfigureProperties.getInstance();
	private String areaCode;
	
	private File dataDefine;
	private String dataDefineFileName;
	private String dataDefineContentType;
	
	private File htmlData;
	private String htmlDataFileName;
	private String htmlDataContentType;
	
	private PloyService ployService;
	private PageBeanDB pageReleaseLocation = new PageBeanDB();
	
	/**
	 * 进入更新UI描述符页面
	 * @return
	 */
	public String intoUpdateUI(){
		pageReleaseLocation = ployService.queryAreaList(null, 1, 100);
		return SUCCESS;
	}
	
	/**
	 * 更新UI描述符
	 * @return
	 */
	public String updateUI(){
		String dataDefinePath = "";
		String htmlDataPath = "";
		if(dataDefine != null){
			dataDefinePath = sendFtpFile(dataDefine,dataDefineFileName);
		}
		if(htmlData != null){
			htmlDataPath = sendFtpFile(htmlData,htmlDataFileName);
		}
		
		AdsClient client  = new AdsClient();
    	String ret = client.sendUI(areaCode, dataDefinePath, htmlDataPath);
    	if("0".equals(ret)){
    		message = "common.add.success";//保存成功
    		log.info("发送更新UI描述符成功！");
    	}else{
    		message = "common.add.failed";//保存失败
    		log.info("发送更新UI描述符失败！");
    	}
		
		return intoUpdateUI();
	}

	/**
	 * 将文件保存到本地，并远程到FTP上，返回FTP远程全路径
	 * @param file
	 * @param fileName
	 * @return
	 */
	private String sendFtpFile(File file, String fileName) {
		String path = "";
		//设置资源文件名全路径
		String filePath = setFilePath(fileName);
		//将资源文件保存到本地
		FileUtil.uploadResource(file, filePath, true);
		FtpUtils ftp = null;
		try {
			int index = filePath.lastIndexOf(File.separator);
			String localFileName = filePath.substring(index+1,filePath.length());
		    ftp = new FtpUtils();
		    ftp.connectionFtp();
		    String remoteDirectoryReal=config.getValueByKey("materila.ftp.realPath");
		    //将本地文件保存到FTP上
		    ftp.uploadFileToRemote(filePath, remoteDirectoryReal, localFileName);   
		    path = remoteDirectoryReal+"/"+localFileName;
		} catch (Exception e) {
		    e.printStackTrace();
		} finally{
		    if (ftp != null) {
		        ftp.closeFTP();
		    }
		}
		return path;
	}
	
	/**
	 * 设置资源文件名全路径
	 * @param fileName  页面请求的文件名
	 * @return
	 */
	private String setFilePath(String fileName){
		
		StringBuffer filePathSB = new StringBuffer();
		filePathSB.append(FileUtil.getSerlvetContextPath());
		filePathSB.append(File.separator);
		filePathSB.append("updateUI");
		filePathSB.append(File.separator);
		
		//资源文件名
		filePathSB.append(fileName.substring(0,fileName.lastIndexOf(".")));
		filePathSB.append("_");
		filePathSB.append(new SimpleDateFormat("yyyyMMddHHmmss").format(
				new Date()).toString());
		filePathSB.append(fileName.substring(fileName.lastIndexOf(".")));	
		//资源文件名全路径
		return filePathSB.toString();
		
	}

	public File getDataDefine() {
		return dataDefine;
	}

	public void setDataDefine(File dataDefine) {
		this.dataDefine = dataDefine;
	}

	public String getDataDefineFileName() {
		return dataDefineFileName;
	}

	public void setDataDefineFileName(String dataDefineFileName) {
		this.dataDefineFileName = dataDefineFileName;
	}

	public String getDataDefineContentType() {
		return dataDefineContentType;
	}

	public void setDataDefineContentType(String dataDefineContentType) {
		this.dataDefineContentType = dataDefineContentType;
	}

	public File getHtmlData() {
		return htmlData;
	}

	public void setHtmlData(File htmlData) {
		this.htmlData = htmlData;
	}

	public String getHtmlDataFileName() {
		return htmlDataFileName;
	}

	public void setHtmlDataFileName(String htmlDataFileName) {
		this.htmlDataFileName = htmlDataFileName;
	}

	public String getHtmlDataContentType() {
		return htmlDataContentType;
	}

	public void setHtmlDataContentType(String htmlDataContentType) {
		this.htmlDataContentType = htmlDataContentType;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public PageBeanDB getPageReleaseLocation() {
		return pageReleaseLocation;
	}

	public void setPageReleaseLocation(PageBeanDB pageReleaseLocation) {
		this.pageReleaseLocation = pageReleaseLocation;
	}

	public void setPloyService(PloyService ployService) {
		this.ployService = ployService;
	}

}
