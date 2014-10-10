package com.dvnchina.advertDelivery.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.constant.TemplateConstant;
import com.dvnchina.advertDelivery.model.QuestionnaireTemplate;
import com.dvnchina.advertDelivery.service.TemplateService;
import com.dvnchina.advertDelivery.sysconfig.bean.User;

public class TemplateAction extends BaseActionSupport<Object> {
	private String templateName;
	private File upload;
	private TemplateService templateService;
	/**
	 * 上传文件类型属性
	 */
	protected String uploadContentType;
	
	/**
	 * 上传的文件名
	 */
	protected String uploadFileName;

	public String uploadTemplate() throws Exception {
		String msg = this.zipFormat();
		if (StringUtils.isNotBlank(msg)) {
			/** 如果返回的信息不为空，说明验证没有通过 */
			this.returnMessage(msg, 1);
			return null;
		}
		msg = this.isRepeatTest();
		if (StringUtils.isNotBlank(msg)) {
			this.returnMessage(msg, 2);
			return null;
		}
		this.save();

		return null;

	}

	
	public String save() {
		String saveFlag = getRequest().getParameter("saveFlag");
		/** 模板包存放路径 */
		String realpath = ServletActionContext.getServletContext().getRealPath(
				TemplateConstant.SAVEZIPROOT);
		String tempPath = ServletActionContext.getServletContext().getRealPath(
				TemplateConstant.SAVETEMPLATEROOT);
		File savefile = new File(new File(realpath), uploadFileName);
		if (!savefile.getParentFile().exists())
			savefile.getParentFile().mkdirs();
		try {
			if (upload == null){
				upload = new File(tempPath+SPE+uploadFileName);
			}
			FileUtils.copyFile(upload, savefile);
			String targetPath = new StringBuffer().append(
					getRealPath())
					.append(TemplateConstant.SAVETEMPLATEROOT).toString();
			// log.info("模板存放路径为：" + targetPath);
			Map<String, String> savePath = templateService.saveTemplateFile(
					upload, targetPath);
			if ("0".equals(saveFlag)) {
				QuestionnaireTemplate t = new QuestionnaireTemplate();
				t.setTemplateName(templateName);
				t.setTemplatePackageName(uploadFileName);
				t.setHtmlPath(savePath.get("html"));
				t.setShowImagePath(savePath.get("showImage"));
				t.setState(Constant.AVAILABLE);
				User user = new User();
				user.setUserId(1);
				t.setUser(user);// userid
			
				t.setCreateTime(new Date());
				templateService.insertTemplate(t);
				this.returnMessage("文件上传成功", 0);
			} else {
				QuestionnaireTemplate t = templateService
						.getTemplateByName(uploadFileName);
				t.setTemplateName(templateName);
				t.setTemplatePackageName(uploadFileName);
				t.setHtmlPath(savePath.get("html"));
				t.setShowImagePath(savePath.get("showImage"));
				t.setState(Constant.AVAILABLE);
//				t.setUser(new User());// userid
				t.setCreateTime(new Date());
				templateService.updateTemplate(t);
				renderHtml("文件覆盖成功");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if ("0".equals(saveFlag)) {
				this.returnMessage("文件上传失败", 3);
			} else {
				renderHtml("文件上传失败");
			}
			e.printStackTrace();
		}finally{
			if (upload != null) {
				upload.delete();
			}
		}

		return null;

	}
	public String deleteZip(){
		String tempPath = ServletActionContext.getServletContext().getRealPath(
				TemplateConstant.SAVETEMPLATEROOT);
		upload = new File(tempPath+SPE+uploadFileName);
		upload.delete();
		return null;
	}
	public String list(){
		int count = templateService.getTemplatesCount();
		PageBeanDB page = new PageBeanDB(count,pageNo);
		List<QuestionnaireTemplate> templates = templateService.listTemplatesByPage(page.getBegin(), page.getPageSize());
		getRequest().setAttribute("templates", templates);
		getRequest().setAttribute("page", page);
		return "success";
	}

	public String delete() throws Exception {
		Integer templateId = new Integer(getRequest().getParameter("templateId"));
		templateService.deleteTemplate(templateId);
		renderHtml("删除成功！");
		return null;

	}
	private String zipFormat() {
		String msg = null;
		boolean flag = false;
		try {
			Map map = templateService.checkZip(upload);
			int htmlCount = (Integer) map.get("htmlCount");
			if (htmlCount == 0) {
				msg = "缺少html文件";
			} else {
				flag = Boolean.valueOf((String) map.get("flag"));
				msg = (String) map.get("msg");
			}
		} catch (Exception e) {
			msg = "上传的不是zip文件或者文件结构不符合规范";
			if (upload != null) {
				upload.delete();
			}
			// log.warn("上传模板文件格式或者结构不正确");
		}
		if (flag) {
			return null;
		} else {
			return msg;
		}
	}

	private String isRepeatTest() throws Exception {
		String msg = null;
		QuestionnaireTemplate t = templateService
				.getTemplateByName(uploadFileName);
		if (t != null) {
			msg = "模板已存在,是否覆盖";
			String tempPath = ServletActionContext.getServletContext()
					.getRealPath(TemplateConstant.SAVETEMPLATEROOT);
			File savefile = new File(new File(tempPath), uploadFileName);
			if (!savefile.getParentFile().exists())
				savefile.getParentFile().mkdirs();
			try {
				FileUtils.copyFile(upload, savefile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (upload != null) {
				upload.deleteOnExit();
				upload=null;
			}
		}
		return msg;
	}

	private void returnMessage(String content, int flag) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			String msg = "{\"flag\":" + flag + ",\"msg\":\"" + content + "\"}";
			out.print(msg);
			out.flush();
			out.close();
		} catch (IOException e) {
		}
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}


	public String getUploadContentType() {
		return uploadContentType;
	}


	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}


	public String getUploadFileName() {
		return uploadFileName;
	}


	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}


}
