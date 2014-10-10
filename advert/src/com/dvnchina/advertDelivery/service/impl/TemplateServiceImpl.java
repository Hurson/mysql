package com.dvnchina.advertDelivery.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipException;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.htmlparser.util.ParserException;

import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.constant.TemplateConstant;
import com.dvnchina.advertDelivery.dao.TemplateDao;
import com.dvnchina.advertDelivery.model.QuestionnaireTemplate;
import com.dvnchina.advertDelivery.service.TemplateService;
import com.dvnchina.advertDelivery.utils.questionnaire.SendTemplateSourceManager;

public class TemplateServiceImpl implements TemplateService {
	private TemplateDao templateDao;
	private TemplateFormater templateFormater;
	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}
	public void setTemplateFormater(TemplateFormater templateFormater) {
		this.templateFormater = templateFormater;
	}
	@Override
	public Map checkZip(File file) throws ZipException, ParserException,
			IOException {
		Map rsMap = new HashMap();
		rsMap = templateFormater.formatZip(file);
		return rsMap;
	}
	@Override
	public QuestionnaireTemplate getTemplateByName(String fileName){
		QuestionnaireTemplate template = templateDao.getTemplateByName(fileName,Constant.AVAILABLE);
		return template;
	}
	@Override
	public QuestionnaireTemplate getTemplateById(Integer templateId){
		QuestionnaireTemplate template = (QuestionnaireTemplate) templateDao.get(QuestionnaireTemplate.class, templateId);
		return template;
	}
	@Override
	public void insertTemplate(QuestionnaireTemplate template) {
		QuestionnaireTemplate t = templateDao.getTemplateByName(template.getTemplatePackageName(), Constant.DELETE);
		if(t!=null){
			t.setCreateTime(template.getCreateTime());
			t.setHtmlPath(template.getHtmlPath());
			t.setShowImagePath(template.getShowImagePath());
			t.setState(template.getState());
			t.setTemplateName(template.getTemplateName());
			t.setTemplatePackageName(template.getTemplatePackageName());
			t.setUser(template.getUser());
			this.updateTemplate(t);
		}else{
			templateDao.save(template);
		}
	}
	@Override
	public void updateTemplate(QuestionnaireTemplate template){
		templateDao.update(template);
	}
	@Override
	public void deleteTemplate(Integer templateId){
		QuestionnaireTemplate template = (QuestionnaireTemplate) templateDao.get(QuestionnaireTemplate.class, templateId);
		template.setState(Constant.DELETE);
		templateDao.update(template);
	}
	
	@Override
	public int getTemplatesCount() {
		return templateDao.getTemplatesCount();
	}
	@Override
	public List<QuestionnaireTemplate> listTemplatesByPage(int begin,int pageSize){
		return templateDao.listTemplatesByPage(begin, pageSize);
	}
	@Override
	public Map<String,String> saveTemplateFile(File file,String targetPath) throws Exception{
		ZipFile zipFile = new ZipFile(file);
		String mainTemplateName = this.getMainTemplateFileName(zipFile);		
		Enumeration em = zipFile.getEntries();
		ZipEntry zipEntry = null;
		List<Map> utils = SendTemplateSourceManager.getResourceSyncUtils();
		Map<String,String> savePath = new HashMap<String,String>();
		while (em.hasMoreElements()) {
			zipEntry = (ZipEntry) em.nextElement();			
			// 将该文件写入工程
			Map<String,String> sPath = wirteToTarget(zipEntry, targetPath, zipFile, mainTemplateName, utils);
			if(sPath!=null){
				savePath.putAll(sPath);
			}
		}
		SendTemplateSourceManager.closeUtils(utils);
		zipFile.close();
		return savePath;
	}
	
	/**
	 * 获得模板文件名(
	 * 
	 * @param zipFile
	 * @return
	 * @throws IOException
	 * @throws ZipException
	 * @throws ParserException
	 */
	public  String getMainTemplateFileName(ZipFile zipFile) throws ZipException, IOException, ParserException {
		Enumeration em = zipFile.getEntries();
		ZipEntry zipEntry = null;
		String zipName;
		String templateName = "";
		while (em.hasMoreElements()) {
			zipEntry = (ZipEntry) em.nextElement();
			if (zipEntry.isDirectory()) {
			} else {
				zipName = zipEntry.getName();
				if (zipName.toLowerCase().endsWith(".htm") || zipName.toLowerCase().endsWith(".html")) {
						templateName = zipName.substring(zipName.lastIndexOf("/"));
					
				}
			}
		}
		return templateName;
	}
	public Map<String,String> wirteToTarget(ZipEntry zipEntry, String targetPath, ZipFile zipFile, String templateRealName, List<Map> utils) throws Exception {
		Map<String,String> path = null;
		String zipName = zipEntry.getName();
		// 去掉包文件名后的路径名
		String templateName = templateRealName.substring(1, templateRealName.indexOf("."));
		String targettempname2 = zipName.substring(zipEntry.getName().indexOf("/") + 1);
		String fileName = targettempname2.substring(targettempname2.lastIndexOf("/") + 1);
		if (zipName.toLowerCase().endsWith(".html") || zipName.toLowerCase().endsWith(".htm")) {
			String localPath = targetPath + File.separator + TemplateConstant.HTML + "/"  + templateName
					+ TemplateConstant.MAIN + "/" + fileName;

			writeToLocal(localPath, zipEntry, zipFile);
			SendTemplateSourceManager.sendTemplateSourceToFtp(localPath, templateRealName, 
					TemplateConstant.SENDHTMLPATH, utils, fileName);
			path = new HashMap<String,String>();
			String htmlPath = "/"  +TemplateConstant.SAVEIHTMLROOT+ "/"  + templateName
			+ TemplateConstant.MAIN + "/" + fileName;
			path.put("html", htmlPath);
		} else {
			if (zipName.toLowerCase().endsWith(".css")) {

				String localPath = targetPath + File.separator + TemplateConstant.STYLES + "/"  + templateName
						+ TemplateConstant.MAIN + "/" + fileName;
				writeToLocal(localPath, zipEntry, zipFile);
				SendTemplateSourceManager.sendTemplateSourceToFtp(localPath, templateRealName, 
						TemplateConstant.SENDCSSPATH, utils, fileName);
			}
			if (zipName.toLowerCase().endsWith(".js")) {
				if(fileName.equals(templateName+".js")){
					String localPath = targetPath + File.separator + TemplateConstant.DESCJS+ "/" + fileName;
					writeToLocal(localPath, zipEntry, zipFile); 
					SendTemplateSourceManager.sendTemplateSourceToFtp(localPath,
							TemplateConstant.SENDDESCJSPATH, utils, fileName);
				}else{
					String localPath = targetPath + File.separator + TemplateConstant.SCRIPTS + "/" 
							+ templateName + TemplateConstant.MAIN + "/" + fileName;
					writeToLocal(localPath, zipEntry, zipFile);
					SendTemplateSourceManager.sendTemplateSourceToFtp(localPath, templateRealName, 
							TemplateConstant.SENDJSPATH, utils, fileName);
				}
			}
			if (zipName.toLowerCase().endsWith(".jpg") || zipName.toLowerCase().endsWith(".gif")
					|| zipName.toLowerCase().endsWith(".png")) {
				if (zipName.contains(TemplateConstant.EFFECTDRAWING)) {
					String localPath = targetPath + File.separator + TemplateConstant.EFFECTDRAWING + "/" 
							+ templateName + TemplateConstant.MAIN + "/" + fileName;
					writeToLocal(localPath, zipEntry, zipFile);
					path = new HashMap<String,String>();
					String showImage = "/"+TemplateConstant.SAVEEFFROOT + "/" + templateName + TemplateConstant.MAIN + "/" + fileName;
					path.put("showImage", showImage);
					SendTemplateSourceManager.sendTemplateSourceToFtp(localPath, templateRealName,
							TemplateConstant.SENDIMAGESPATH+TemplateConstant.EFFECTDRAWING, utils, fileName);
				} else if (zipName.contains(TemplateConstant.ROOTBACKGROUND)) {

					String localPath = targetPath + File.separator + TemplateConstant.BACKGROUND + "/" 
							+ templateName + TemplateConstant.MAIN + "/" + fileName;
					writeToLocal(localPath, zipEntry, zipFile);
					SendTemplateSourceManager.sendTemplateSourceToFtp(localPath, templateRealName, 
							TemplateConstant.SENDIMAGESPATH+TemplateConstant.BACKGROUND, utils, fileName);

				} else {
					String localPath = targetPath + File.separator + TemplateConstant.IMAGE + "/"  
							+ templateName + TemplateConstant.MAIN + "/" + fileName;
					writeToLocal(localPath, zipEntry, zipFile);
					SendTemplateSourceManager.sendTemplateSourceToFtp(localPath,  templateRealName, 
							TemplateConstant.SENDIMAGESPATH, utils, fileName);
				}
			}
		}
		return path;
	}
	public void writeToLocal(String locationName, ZipEntry zipEntry, ZipFile zipFile) throws Exception {
		File file2 = null;
		//log.info("往广告系统写入文件:" + locationName);
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		InputStream in = null;
		OutputStream op = null;
		File pathFile = null;
		try {
			String path = locationName.substring(0, locationName.lastIndexOf("/") + 1);
			pathFile = new File(path);
			file2 = new File(locationName);
			// 目录不存在先创建目录
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			if (!file2.exists()) {
				file2.createNewFile();
			}
			in = zipFile.getInputStream(zipEntry);
			op = new FileOutputStream(file2);
			inBuff = new BufferedInputStream(in);
			outBuff = new BufferedOutputStream(op);
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			if (pathFile != null) {
				pathFile = null;
			}
			if (file2 != null) {
				file2 = null;
			}
			outBuff.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			if (inBuff != null) {
				try {
					inBuff.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outBuff != null) {
				try {
					inBuff.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (op != null) {
				try {
					op.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (file2 != null) {
				file2 = null;
			}
		}
	}
	
}
