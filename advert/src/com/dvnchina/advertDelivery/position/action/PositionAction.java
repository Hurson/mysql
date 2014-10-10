package com.dvnchina.advertDelivery.position.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.ContractADBackup;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.PositionPackage;
import com.dvnchina.advertDelivery.position.bean.QuestionnaireSpecification;
import com.dvnchina.advertDelivery.position.bean.TextSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;
import com.dvnchina.advertDelivery.position.service.PositionService;

public class PositionAction extends BaseAction{
	
	private static final long serialVersionUID = 1772960454993775683L;
	private PositionService positionService = null;
	private PageBeanDB page = null;
	private PositionPackage pPackage = null;
	private AdvertPosition advertPosition = null;
	private String positionPackageName = "";
	private ImageSpecification imageSpe = null;
	private VideoSpecification videoSpe = null;
	private TextSpecification textSpe = null;
	private QuestionnaireSpecification questionnaireSpe = null;
	private ContractAD contractAD = null;
	private ContractADBackup contractADBackup = null;
	
	private String uploadDir;//广告背景图片上传路径
	private File backgroundImage;//广告背景图片文件
	private String backgroundImageFileName;//广告背景图片文件名
	
	/**
	 * 查询广告包信息
	 * @return
	 */
	public String queryPositionPackageList(){
		try{
			if(page == null){
				page = new PageBeanDB();
			}
			page = positionService.queryPositionPackageList(page.getPageNo(), page.getPageSize());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 获取广告位包详情
	 * @return
	 */
	public String getPositionPackage(){
		try{
			pPackage = positionService.getPositionPackageById(id);
			List<AdvertPosition> adList = positionService.findADPositionByPackage(pPackage.getId());
			pPackage.setAdPositionList(adList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 获取子广告位详情
	 * @return
	 */
	public String getAdvertPositionById(){
		try{
			advertPosition = positionService.getAdvertPosition(id);
			if(advertPosition != null){
				pPackage = positionService.getPositionPackageById(advertPosition.getPositionPackageId());
				advertPosition.setPositionPackageName(pPackage.getPositionPackageName());
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 查询广告位包对应的合同占用情况
	 * @return
	 */
	public String viewPositionOccupy(){
		try{
			if(page == null){
				page = new PageBeanDB();
			}
			page = positionService.viewPositionOccupy(contractAD,page.getPageNo(), page.getPageSize());
			pPackage = positionService.getPositionPackageById(contractAD.getPositionId());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
     * 查询广告位包对应的合同占用情况(查询临时表数据)
     * @return
     */
    public String viewPositionOccupyForContractManager(){
        try{
            if(page == null){
                page = new PageBeanDB();
            }
            page = positionService.viewPositionOccupyForContractManager(contractADBackup,page.getPageNo(), page.getPageSize());
            pPackage = positionService.getPositionPackageById(contractADBackup.getPositionId());
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return SUCCESS;
    }
	
	/**
	 * 查询子广告位已存在规格
	 * @return
	 */
	public String querySpecification(){
		try{
			advertPosition = positionService.getAdvertPosition(id);
			if(advertPosition.getImageRuleId() != null){
				imageSpe = positionService.getImageSpeById(advertPosition.getImageRuleId());
			}
			if(advertPosition.getVideoRuleId() != null){
				videoSpe = positionService.getVideoSpeById(advertPosition.getVideoRuleId());
			}
			if(advertPosition.getTextRuleId() != null){
				textSpe = positionService.getTextSpeById(advertPosition.getTextRuleId());
			}
			if(advertPosition.getQuestionRuleId() != null){
				questionnaireSpe = positionService.getQuestionnaireSpeById(advertPosition.getQuestionRuleId());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 获取视频规格信息
	 * @return
	 */
	public String getVideoSpecification(){
		try{
			videoSpe = positionService.getVideoSpeById(videoSpe.getId());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 获取图片规格信息
	 * @return
	 */
	public String getImageSpecification(){
		try{
			imageSpe = positionService.getImageSpeById(imageSpe.getId());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 获取文字规格信息
	 * @return
	 */
	public String getTextSpecification(){
		try{
			textSpe = positionService.getTextSpeById(textSpe.getId());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 获取问卷规格信息
	 * @return
	 */
	public String getQuestionnaireSpecification(){
		try{
			questionnaireSpe = positionService.getQuestionnaireSpeById(questionnaireSpe.getId());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 保存视频规格
	 * @return
	 */
	public String saveVideoSpecification(){
		try{
			positionService.saveVideoSpecification(videoSpe);
		}catch(Exception e){
			e.printStackTrace();
		}
		videoSpe = null;
		return querySpecification();
	}
	
	/**
	 * 保存图片规格
	 * @return
	 */
	public String saveImageSpecification(){
		try{
			positionService.saveImageSpecification(imageSpe);
		}catch(Exception e){
			e.printStackTrace();
		}
		imageSpe = null;
		return querySpecification();
	}
	
	/**
	 * 保存文字规格
	 * @return
	 */
	public String saveTextSpecification(){
		try{
			positionService.saveTextSpecification(textSpe);
		}catch(Exception e){
			e.printStackTrace();
		}
		textSpe = null;
		return querySpecification();
	}
	
	/**
	 * 保存问卷规格
	 * @return
	 */
	public String saveQuestionnaireSpecification(){
		try{
			positionService.saveQuestionnaireSpecification(questionnaireSpe);
		}catch(Exception e){
			e.printStackTrace();
		}
		questionnaireSpe = null;
		return querySpecification();
	}
	
	/**
	 * 上传广告位的背景图片
	 */
	public void uploadBackGroundImage(){
		try {
			String filePath = ServletActionContext.getServletContext().getRealPath(uploadDir);
			//文件重命名,规则为:原名_yyyyMMdd
	        String name1=backgroundImageFileName.substring(0, backgroundImageFileName.lastIndexOf(".")).replace("_", "-");
	        String name2=backgroundImageFileName.substring(backgroundImageFileName.lastIndexOf("."), backgroundImageFileName.length());
	        backgroundImageFileName=name1+"-"+new Date().getTime()+name2;
			String result = positionService.copyLocalFileTargetDirectory(this.backgroundImage.getAbsolutePath(), filePath,backgroundImageFileName,uploadDir);
			renderHtml(result);
		} catch (Exception e) {
			log.error("上传广告位背景图出现异常",e);
		}
	}
	
	/**
	 * 修改子广告位信息（坐标、宽高、描述、背景图片）
	 * @return
	 */
	public String updateAdvertPosition(){
		try{
			positionService.updateAdvertPosition(advertPosition);
		}catch(Exception e){
			e.printStackTrace();
		}
		id = advertPosition.getId();
		return getAdvertPositionById();
	}
	
	public PageBeanDB getPage() {
		return page;
	}
	public void setPage(PageBeanDB page) {
		this.page = page;
	}
	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	public PositionPackage getPPackage() {
		return pPackage;
	}

	public void setPPackage(PositionPackage package1) {
		pPackage = package1;
	}

	public AdvertPosition getAdvertPosition() {
		return advertPosition;
	}

	public void setAdvertPosition(AdvertPosition advertPosition) {
		this.advertPosition = advertPosition;
	}

	public String getPositionPackageName() {
		return positionPackageName;
	}

	public void setPositionPackageName(String positionPackageName) {
		this.positionPackageName = positionPackageName;
	}

	public ImageSpecification getImageSpe() {
		return imageSpe;
	}

	public void setImageSpe(ImageSpecification imageSpe) {
		this.imageSpe = imageSpe;
	}

	public VideoSpecification getVideoSpe() {
		return videoSpe;
	}

	public void setVideoSpe(VideoSpecification videoSpe) {
		this.videoSpe = videoSpe;
	}

	public TextSpecification getTextSpe() {
		return textSpe;
	}

	public void setTextSpe(TextSpecification textSpe) {
		this.textSpe = textSpe;
	}

	public QuestionnaireSpecification getQuestionnaireSpe() {
		return questionnaireSpe;
	}

	public void setQuestionnaireSpe(QuestionnaireSpecification questionnaireSpe) {
		this.questionnaireSpe = questionnaireSpe;
	}

	public ContractAD getContractAD() {
		return contractAD;
	}

	public void setContractAD(ContractAD contractAD) {
		this.contractAD = contractAD;
	}

	public File getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(File backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public String getBackgroundImageFileName() {
		return backgroundImageFileName;
	}

	public void setBackgroundImageFileName(String backgroundImageFileName) {
		this.backgroundImageFileName = backgroundImageFileName;
	}

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

    public ContractADBackup getContractADBackup() {
        return contractADBackup;
    }

    public void setContractADBackup(ContractADBackup contractADBackup) {
        this.contractADBackup = contractADBackup;
    }
	
}
