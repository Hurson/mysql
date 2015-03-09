package com.dvnchina.advertDelivery.sysconfig.action;

import java.io.File;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.sysconfig.bean.AreaOCG;
import com.dvnchina.advertDelivery.sysconfig.service.OCGUpgradeService;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.config.OperatePropertyFile;
import com.dvnchina.advertDelivery.utils.file.FileUtil;
import com.dvnchina.advertDelivery.utils.ftp.FtpUtils;

public class OnlineUpgradeAction extends BaseAction{
	
	private static final long serialVersionUID = -3235343638046726865L;
	private static ConfigureProperties config = ConfigureProperties.getInstance();
	private Logger logger = Logger.getLogger(this.getClass());
	private String ips;
	
	private File upgrade;
	private String upgradeFileName;
	private String upgradePath;
	
	
	private File description;
	private final String descriptionFileName="description.properties";
	private String descriptionPath;
	
	private String newVersion;
	
	private OCGUpgradeService ocgUpgradeService;
	private AreaOCG areaOCG;
	private PageBeanDB page = new PageBeanDB();
	
	/**
	 * 初始化页面
	 * @return
	 */
	public String initPage(){
		return SUCCESS;
	}
	
	/**
	 * 上传升级包和描述文件
	 * @return
	 */
	public String uploadResource(){
		
		if(upgrade != null){
			upgradePath = sendFtpFile(upgrade,upgradeFileName);
		}
		if(description != null){
			descriptionPath = setFilePath(descriptionFileName);
			FileUtil.uploadResource(description, descriptionPath, true);
		}
		
		if(upgradePath != null && descriptionPath != null){
			OperatePropertyFile oper = new OperatePropertyFile(descriptionPath);
			oper.setValue("upgrade_package_file_path", upgradePath);
			oper.saveFile(descriptionPath);
			logger.debug("保存文件成功");
			message = "common.save.success";//保存成功
			return startUpgrade();
		}
		logger.debug("上传文件失败");
		message = "common.save.failed";//保存失败
		return ERROR;
	}
	public String startUpgrade(){
		File descFile = new File(setFilePath(descriptionFileName));
		logger.info("DescriptionPath:" + descFile.getAbsolutePath());
		if(descFile.exists()){
			OperatePropertyFile oper = new OperatePropertyFile(descFile.getAbsolutePath());
			
			newVersion = oper.getValue("version");
			upgradePath = oper.getValue("upgrade_package_file_path");
		}else{
			newVersion = "1.0";
			upgradePath = "/root/advertres/temp/upgrade/ocg";
		}
		
		/*if(areaOCG != null)
			System.out.println("AreaName : "+areaOCG.getAreaName());*/
		page = ocgUpgradeService.queryAreaOCGList(areaOCG, page.getPageNo(), page.getPageSize());
		return SUCCESS;
	}

	public String upgrade()throws Exception{
		
		message = "common.upgrade.success";//升级成功
		String[] ipss = ips.split(",");
		for(String ip : ipss){
			int result = sendUpgradeMessage(upgradePath, newVersion, ip);
			if(result != 200){
				result = sendUpgradeMessage(upgradePath, newVersion, ip);
			}
			if(result == 200){
				ocgUpgradeService.updateAreaOCG(newVersion, ip);
			}else{
				logger.debug("升级失败");
				message="common.upgrade.failed";
			}
		}
		
		return startUpgrade();
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
		    String remoteDirectoryReal=config.getValueByKey("upgrade.ocg.ftp.realPath");
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
	private int sendUpgradeMessage(String path, String version, String ip){
		HttpClient httpclient = new HttpClient();

	   
		httpclient.getParams().setConnectionManagerTimeout(5000);

		try { 
			PostMethod method = new PostMethod(ip); 
			
			method.addRequestHeader("update_url", path);
			method.addRequestHeader("version", version);
			
			int code = httpclient.executeMethod(method); 
			return code;
		}catch(Exception e){
			return 0;
		}
	    
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
		filePathSB.append("upgrade");
		filePathSB.append(File.separator);
		
		/*//资源文件名
		filePathSB.append(fileName.substring(0,fileName.lastIndexOf(".")));
		filePathSB.append("_");
		filePathSB.append(new SimpleDateFormat("yyyyMMddHHmmss").format(
				new Date()).toString());
		filePathSB.append(fileName.substring(fileName.lastIndexOf(".")));	
		//资源文件名全路径*/
		filePathSB.append(fileName);
		return filePathSB.toString();
		
	}

	public String getUpgradePath() {
		return upgradePath;
	}

	public void setUpgradePath(String upgradePath) {
		this.upgradePath = upgradePath;
	}

	public String getDescriptionPath() {
		return descriptionPath;
	}

	public void setDescriptionPath(String descriptionPath) {
		this.descriptionPath = descriptionPath;
	}

	public String getIps() {
		return ips;
	}

	public void setIps(String ips) {
		this.ips = ips;
	}

	public File getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(File upgrade) {
		this.upgrade = upgrade;
	}

	public String getUpgradeFileName() {
		return upgradeFileName;
	}

	public void setUpgradeFileName(String upgradeFileName) {
		this.upgradeFileName = upgradeFileName;
	}

	public File getDescription() {
		return description;
	}

	public void setDescription(File description) {
		this.description = description;
	}

	public String getDescriptionFileName() {
		return descriptionFileName;
	}

	public OCGUpgradeService getOcgUpgradeService() {
		return ocgUpgradeService;
	}

	public void setOcgUpgradeService(OCGUpgradeService ocgUpgradeService) {
		this.ocgUpgradeService = ocgUpgradeService;
	}

	public PageBeanDB getPage() {
		return page;
	}

	public void setPage(PageBeanDB page) {
		this.page = page;
	}

	public AreaOCG getAreaOCG() {
		return areaOCG;
	}

	public void setAreaOCG(AreaOCG areaOCG) {
		this.areaOCG = areaOCG;
	}

	public String getNewVersion() {
		return newVersion;
	}

	public void setNewVersion(String newVersion) {
		this.newVersion = newVersion;
	}

}
