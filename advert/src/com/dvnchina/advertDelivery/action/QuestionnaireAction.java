package com.dvnchina.advertDelivery.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.bean.question.TemplateJsBean;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.constant.QuestionnaireConstant;
import com.dvnchina.advertDelivery.constant.TemplateConstant;
import com.dvnchina.advertDelivery.model.Question;
import com.dvnchina.advertDelivery.model.Questionnaire;
import com.dvnchina.advertDelivery.model.QuestionnaireTemplate;
import com.dvnchina.advertDelivery.service.QuestionnaireService;
import com.dvnchina.advertDelivery.service.TemplateService;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.ftp.FtpUtils;
import com.dvnchina.advertDelivery.utils.ftp.ResourceSyncUtil;

public class QuestionnaireAction extends BaseActionSupport<Object> {
	private TemplateService templateService;
	private QuestionnaireService questionnaireService;
	private static ConfigureProperties config = ConfigureProperties
			.getInstance();
	public static final String QUESTIONNAIRE_TEMP_DIR = config
			.getValueByKey("upload.file.temp.dir");

	private TemplateJsBean templateJsBean;
	private Character state;
	private String summary;

	private File picture;
	private String pictureFileName; // 文件名称
	private String pictureContentType; // 文件类型
	private File video;
	private String videoFileName; // 文件名称
	private String videoContentType; // 文件类型
	private Integer templateId;

	private String name;

	private String description;

	private Integer contractCode;

	private List<Question> questions;

	public String addQuestionnaire() {
		QuestionnaireTemplate template = templateService
				.getTemplateById(templateId);
		// String htmlPath = template.getHtmlPath();
		String templateName = template.getTemplatePackageName();
		int endIndex = templateName.lastIndexOf(".zip");
		String jsName = templateName.substring(0, endIndex);
		String jsPath = new StringBuffer().append(
				getRealPath())
				.append(TemplateConstant.SAVEDESCJSROOT).append("/").append(
						jsName).append(".js").toString();
		templateJsBean = questionnaireService.getQuesitonnaireContainer(jsPath);
		return "success";
	}

	public String saveQuestionnaire() {
		QuestionnaireTemplate template = templateService
				.getTemplateById(templateId);
		String questionnaireId = "q" + System.currentTimeMillis();
		Questionnaire questionnaire = new Questionnaire();
		questionnaire.setQuestionnaireId(questionnaireId);
		String rootPath = QuestionnaireConstant.SENDROOTPATH + questionnaireId;
		if (StringUtils.isNotBlank(templateJsBean.getPictureId())) {
			String suffix = pictureFileName.substring(pictureFileName
					.lastIndexOf("."));
			String fileName = Constant.IMAGE_PREFIX + System.currentTimeMillis()
					+ suffix;
			String picturePath = QuestionnaireConstant.SENDIMAGEPATH;
			questionnaire.setPicturePath(picturePath + fileName);
			this.sendFile(picture, rootPath + "/" + picturePath, fileName);
		}
		if (StringUtils.isNotBlank(templateJsBean.getVideoId())) {
			String suffix = videoFileName.substring(videoFileName
					.lastIndexOf("."));
			String fileName = Constant.VIDEO_PREFIX + System.currentTimeMillis()
					+ suffix;
			String videoPath = QuestionnaireConstant.SENDIMAGEPATH;
			questionnaire.setVideoPath(videoPath + fileName);
			this.sendFile(video, rootPath + "/" + videoPath, fileName);
		}

		questionnaire.setSummary(summary);

		String tHtmlPath = new StringBuffer(getRequest().getSession()
				.getServletContext().getRealPath("/")).append(
				template.getHtmlPath()).toString();
		questionnaireService.generateQuestionnaire(tHtmlPath, templateJsBean,
				questionnaire, questions);
		this.sendFile(QUESTIONNAIRE_TEMP_DIR + "/" + questionnaireId + ".html",
				rootPath);

		String templateName = template.getTemplatePackageName().substring(0,
				template.getTemplatePackageName().lastIndexOf("."));
		this.sendTemplateFile(templateName, rootPath);

		questionnaire.setContractCode(contractCode);
		questionnaire.setCreateTime(new Date());
		questionnaire.setDescription(description);
		questionnaire.setName(name);
		questionnaire.setQuestionnaireType(Constant.WEB_QUESTIONNAIRE);
		questionnaire.setState(Constant.AVAILABLE);
		questionnaire.setTemplateId(templateId);
		questionnaire.setType(Constant.QUESTIONNAIRE);
		questionnaire.setBusinessId(0);
		// questionnaire.setBusinessName(businessName);
		questionnaire.setUserId(0);

		Integer qId = questionnaireService.insertQuestionnaire(questionnaire);

		questionnaireService.insertQuestions(questions, qId);

		this.renderHtml("保存成功");
		return null;
	}

	public String list() {
		int count = questionnaireService.getQuestionnairesCount();
		PageBeanDB page = new PageBeanDB(count, pageNo);
		List<Questionnaire> questionnaires = questionnaireService
				.listQuestionnaireByPage(page.getBegin(), page.getPageSize());
		String path = new StringBuffer("http://").append(config.get("ftpIp")).append(":").append(config.get("ftpWebPort")).append("/")
		.append(QuestionnaireConstant.SENDROOTPATH).toString();
		getRequest().setAttribute("ftpPath", path);
		getRequest().setAttribute("questionnaires", questionnaires);
		getRequest().setAttribute("page", page);
		return "success";
	}

	public String delete() {
		Questionnaire q = questionnaireService.getQuestionnaireById(id);
		String questionnaireId = q.getQuestionnaireId();
		String path = new StringBuffer(config.get("ftpRootPath")).append("/")
				.append(QuestionnaireConstant.SENDROOTPATH).append(
						questionnaireId).toString();
		boolean b = this.removeFile(path);
		
		questionnaireService.deleteQuestionnaire(q);
		renderHtml("调查问卷删除成功");
		
		return null;
	}

	public String changeState() {
		questionnaireService.updateQuestionnaireState(id, state);
		return "success";
	}

	private void sendTemplateFile(String templatePackageName, String rootPath) {
		String realPath = getRequest().getSession().getServletContext().getRealPath(
				"/");
		String cssPath = realPath + TemplateConstant.SAVESTYLEROOT + "/"
				+ templatePackageName + TemplateConstant.MAIN;
		String jsPath = realPath + TemplateConstant.SAVESCRIPTROOT + "/"
				+ templatePackageName + TemplateConstant.MAIN;
		String imagePath = realPath + TemplateConstant.SAVEIMAGESROOT + "/"
				+ templatePackageName + TemplateConstant.MAIN;
		String backgroundPath = realPath + TemplateConstant.SAVEBACKGROUNDROOT
				+ "/" + templatePackageName + TemplateConstant.MAIN;
		String[] paths = { cssPath, jsPath, imagePath, backgroundPath };
		String[] targetPaths = {
				rootPath + "/" + QuestionnaireConstant.SENDSTYLEPATH,
				rootPath + "/" + QuestionnaireConstant.SENDJSPATH,
				rootPath + "/" + QuestionnaireConstant.SENDIMAGEPATH,
				rootPath + "/" + QuestionnaireConstant.SENDBACKGROUDPATH };
		this.sendFiles(paths, targetPaths);

	}

	/**
	 * 发送文件数据
	 */
	private void sendFiles(String[] localPaths, String[] targetPaths) {
		for (int i = 0; i < localPaths.length; i++) {
			File file = new File(localPaths[i]);
			if (file.exists()) {
				File files[] = file.listFiles();
				for (int j = 0; j < files.length; j++) {
					this.sendFile(files[j], targetPaths[i], files[j].getName());
				}
			}
		}
	}

	/**
	 * 发送文件数据
	 */
	private void sendFile(File localFile, String targetPath, String fileName) {
		ResourceSyncUtil ftp = null;
		// logger.info("开始发送--"+localPath+"--文件！");
		try {
			String ip = config.get("ftpIp");
			int port = Integer.valueOf(config.get("ftpPort"));
			String username = config.get("ftpUserName");
			String password = config.get("ftpPassWord");
			String path = config.get("ftpRootPath");
			ftp = new ResourceSyncUtil(ip, port, username, password);
			boolean b = ftp.sendFileToFtp(localFile, path + "/" + targetPath,
					fileName);
		} catch (Exception e) {
			e.printStackTrace();
			// logger.info("文件发送失败！！！！");
		} finally {
			if (ftp != null) {
				ftp.closeFTP();
			}
		}
		// logger.info("文件==="+localPath+"-- 发送成功 --");
	}

	/**
	 * 发送文件数据
	 */
	private void sendFile(String localFilePath, String targetPath) {
		ResourceSyncUtil ftp = null;
		// logger.info("开始发送--"+localPath+"--文件！");
		try {
			String ip = config.get("ftpIp");
			int port = Integer.valueOf(config.get("ftpPort"));
			String username = config.get("ftpUserName");
			String password = config.get("ftpPassWord");
			String path = config.get("ftpRootPath");
			ftp = new ResourceSyncUtil(ip, port, username, password);
			ftp.sendAFileToFtp(localFilePath, path + "/" + targetPath);
		} catch (Exception e) {
			e.printStackTrace();
			// logger.info("文件发送失败！！！！");
		} finally {
			if (ftp != null) {
				ftp.closeFTP();
			}
		}
		// logger.info("文件==="+localPath+"-- 发送成功 --");
	}

	/**
	 * 删除调查问卷包
	 */
	private boolean removeFile(String directory) {
		FtpUtils ftp = null;
		boolean b= false;
		// logger.info("开始发送--"+localPath+"--文件！");
		try {
			String ip = config.get("ftpIp");
			int port = Integer.valueOf(config.get("ftpPort"));
			String username = config.get("ftpUserName");
			String password = config.get("ftpPassWord");
			ftp = new FtpUtils(ip, port, username, password);
			ftp.connectionFtp();
			b = ftp.removeDirectory(directory, true);
		} catch (Exception e) {
			e.printStackTrace();
			// logger.info("文件发送失败！！！！");
		} finally {
			if (ftp != null) {
				ftp.closeFTP();
			}
		}
		// logger.info("文件==="+localPath+"-- 发送成功 --");
		return b;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public void setQuestionnaireService(
			QuestionnaireService questionnaireService) {
		this.questionnaireService = questionnaireService;
	}

	public TemplateJsBean getTemplateJsBean() {
		return templateJsBean;
	}

	public void setTemplateJsBean(TemplateJsBean templateJsBean) {
		this.templateJsBean = templateJsBean;
	}

	public Character getState() {
		return state;
	}

	public void setState(Character state) {
		this.state = state;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public File getPicture() {
		return picture;
	}

	public void setPicture(File picture) {
		this.picture = picture;
	}

	public String getPictureFileName() {
		return pictureFileName;
	}

	public void setPictureFileName(String pictureFileName) {
		this.pictureFileName = pictureFileName;
	}

	public String getPictureContentType() {
		return pictureContentType;
	}

	public void setPictureContentType(String pictureContentType) {
		this.pictureContentType = pictureContentType;
	}

	public File getVideo() {
		return video;
	}

	public void setVideo(File video) {
		this.video = video;
	}

	public String getVideoFileName() {
		return videoFileName;
	}

	public void setVideoFileName(String videoFileName) {
		this.videoFileName = videoFileName;
	}

	public String getVideoContentType() {
		return videoContentType;
	}

	public void setVideoContentType(String videoContentType) {
		this.videoContentType = videoContentType;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getContractCode() {
		return contractCode;
	}

	public void setContractCode(Integer contractCode) {
		this.contractCode = contractCode;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

}
