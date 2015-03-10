package com.dvnchina.advertDelivery.meterial.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.bean.contract.ContractQueryBean;
import com.dvnchina.advertDelivery.dao.AdvertPositionDao;
import com.dvnchina.advertDelivery.meterial.bean.MaterialCategory;
import com.dvnchina.advertDelivery.meterial.bean.MaterialType;
import com.dvnchina.advertDelivery.meterial.dao.MeterialManagerDao;
import com.dvnchina.advertDelivery.meterial.service.MeterialManagerService;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.ImageReal;
import com.dvnchina.advertDelivery.model.MessageMeta;
import com.dvnchina.advertDelivery.model.MessageReal;
import com.dvnchina.advertDelivery.model.Question;
import com.dvnchina.advertDelivery.model.QuestionReal;
import com.dvnchina.advertDelivery.model.Questionnaire;
import com.dvnchina.advertDelivery.model.QuestionnaireReal;
import com.dvnchina.advertDelivery.model.QuestionnaireTemplate;
import com.dvnchina.advertDelivery.model.Resource;
import com.dvnchina.advertDelivery.model.ResourceReal;
import com.dvnchina.advertDelivery.model.VideoMeta;
import com.dvnchina.advertDelivery.model.VideoReal;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.PositionPackage;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;
import com.dvnchina.advertDelivery.position.dao.PositionDao;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.ftp.FtpUtils;
/**
 * 合同维护相关操作
 * @author lester
 *
 */
public class MeterialManagerServiceImpl implements MeterialManagerService{
    private MeterialManagerDao meterialManagerDao;
    private AdvertPositionDao advertPositionDao;
    private PositionDao positionDao;
    private static ConfigureProperties config = ConfigureProperties.getInstance();
    
    
	public AdvertPositionDao getAdvertPositionDao() {
		return advertPositionDao;
	}


	public void setAdvertPositionDao(AdvertPositionDao advertPositionDao) {
		this.advertPositionDao = advertPositionDao;
	}


	public MeterialManagerDao getMeterialManagerDao() {
        return meterialManagerDao;
    }


    public void setMeterialManagerDao(MeterialManagerDao meterialManagerDao) {
        this.meterialManagerDao = meterialManagerDao;
    }
    
    public PositionDao getPositionDao() {
		return positionDao;
	}


	public void setPositionDao(PositionDao positionDao) {
		this.positionDao = positionDao;
	}


	/**
     * 
     * @description: 查询素材列表
     * @param meterialQuery
     * @param pageSize
     * @param pageNumber
     * @return 
     * PageBeanDB
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-9 上午09:29:13
     */
	public PageBeanDB queryMeterialList(Resource meterialQuery,Integer pageSize,Integer pageNumber) {
		return meterialManagerDao.queryMeterialList(meterialQuery,pageSize, pageNumber);
	}
	
	
	
	@Override
	public PageBeanDB queryMeterialList(Resource resource, String accessUserIds, Integer pageSize, Integer pageNumber) {
		
		return meterialManagerDao.queryMeterialList(resource, accessUserIds, pageSize, pageNumber);
	}
	
	


	@Override
	public int getAuditMateNum(String accessUserIds) {
		
		return meterialManagerDao.getAuditMateNum(accessUserIds);
	}


	/**
     * 
     * @description: 查询问卷模板列表
     * @param questionnaireTemplate
     * @param pageSize
     * @param pageNumber
     * @return 
     * PageBeanDB
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-20 上午11:16:23
     */
    public PageBeanDB queryQuestionTemplateList(QuestionnaireTemplate questionnaireTemplate,Integer pageSize,Integer pageNumber){
        return meterialManagerDao.queryQuestionTemplateList(questionnaireTemplate, pageSize, pageNumber);
    }
	
	/**
     * 
     * @description: 获取素材分类列表
     * @return 
     * List<MaterialCategory>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-9 下午03:10:51
     */
    public List<MaterialCategory> getMaterialCategoryList(){
        return meterialManagerDao.getMaterialCategoryList();
    }
    
    /**
     * 
     * @description: 获取子广告位列表
     * @param advertPositionQuery
     * @param pageSize
     * @param pageNumber
     * @return 
     * PageBeanDB
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-9 下午04:04:49
     */
    public PageBeanDB queryAdPositionList(AdvertPosition advertPositionQuery, Integer pageSize, Integer pageNumber){
        return meterialManagerDao.queryAdPositionList(advertPositionQuery,pageSize, pageNumber);
    }
	
    /**
     * 
     * @description: 获取子广告位列表
     * @param advertPositionQuery
     * @param pageSize
     * @param pageNumber
     * @return 
     * PageBeanDB
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-9 下午04:00:12
     */
    public PageBeanDB queryAdPositionList(AdvertPosition advertPositionQuery,String positionPackIds,Integer pageSize,Integer pageNumber){
    	PageBeanDB pb = new PageBeanDB();
    	pb= meterialManagerDao.queryAdPositionList(advertPositionQuery,positionPackIds, pageSize, pageNumber);
    	List<AdvertPosition> advertPositions = pb.getDataList();
    	for(AdvertPosition advertPosition : advertPositions){
    		PositionPackage positionPackage = positionDao.getPositionPackageById(advertPosition.getPositionPackageId());
    		advertPosition.setPositionPackageType(positionPackage.getPositionPackageType());
    	}
    	pb.setDataList(advertPositions);
        return pb;
    }
    
    /**
     * 
     * @description: 根据广告商ID获取广告位包信息
     * @param customerId
     * @return 
     * List<ContractAD>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-8-14 下午04:38:25
     */
    public List<ContractAD> getAdvertPositionPackListByCustomer(Integer customerId){
        return meterialManagerDao.getAdvertPositionPackListByCustomer(customerId);
    }
    
    /**
     * 
     * @description: 根据广告位包ID获取广告位列表
     * @param positionPackIds
     * @return 
     * List<AdvertPosition>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-8-14 上午10:43:10
     */
    public List<AdvertPosition> getAdvertPositionList(String positionPackIds){
        return meterialManagerDao.getAdvertPositionList(positionPackIds);
    }
    
	/**
	 * 
	 * @description: 获取合同列表
	 * @param contract
	 * @param pageSize
	 * @param pageNumber
	 * @return 
	 * PageBeanDB
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-9 下午05:47:26
	 */
    public PageBeanDB queryContractList(ContractQueryBean contract,Integer pageSize,Integer pageNumber) {
        return meterialManagerDao.queryContractList(contract,pageSize, pageNumber);
    }
    
    /**
     * 
     * @description: 保存Resource
     * @param resource 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-10 下午04:52:10
     */
    public void saveResource(Resource resource){
        meterialManagerDao.saveResource(resource);
    }
    
    /**
     * 
     * @description: 保存Resource正式表
     * @param resource 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午04:04:56
     */
    public void saveResourceReal(ResourceReal resource){
        meterialManagerDao.saveResourceReal(resource);
    }
    
    /**
     * 
     * @description: 保存问卷主题
     * @param questionnaire 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-10 下午05:20:26
     */
    public void saveQuestionSubject(Questionnaire questionnaire){
        meterialManagerDao.saveQuestionSubject(questionnaire);
    }
    
    /**
     * 
     * @description: 保存问卷主题正式表
     * @param questionnaire 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午05:39:41
     */
    public void saveQuestionSubjectReal(QuestionnaireReal questionnaire){
        meterialManagerDao.saveQuestionSubjectReal(questionnaire);
    }
    
    /**
     * 
     * @description: 保存问题列表
     * @param questionList
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-10 下午05:29:54
     */
    public boolean saveQuestionList(List<Question> questionList){
        return meterialManagerDao.saveQuestionList(questionList);
    }
    
    /**
     * 
     * @description: 保存问卷问题正式表列表
     * @param questionList
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午05:46:56
     */
    public boolean saveQuestionRealList(List<QuestionReal> questionList){
        return meterialManagerDao.saveQuestionRealList(questionList);
    }
    
    
    /**
     * 
     * @description: 保存文字素材子表
     * @param material 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-13 上午11:45:32
     */
    public void saveTextMaterial(MessageMeta material) {
        meterialManagerDao.saveTextMaterial(material);
    }
    
    /**
     * 
     * @description: 保存文字素材正式表
     * @param material 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午05:02:27
     */
    public void saveTextMaterialReal(MessageReal material){
        meterialManagerDao.saveTextMaterialReal(material);
    }
    
    /**
     * 
     * @description: 保存图片素材
     * @param material 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-14 下午03:06:18
     */
    public void saveImageMaterial(ImageMeta material){
        meterialManagerDao.saveImageMaterial(material);
    }
    
    
    @Override
	public void updateImageUrl(ImageMeta material) {
    	ImageMeta entity = meterialManagerDao.getImageMetaByID(material.getId());
		entity.setImageUrl(material.getImageUrl());
	}


	/**
     * 
     * @description: 保存图片素材正式表
     * @param material 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午04:22:38
     */
    public void saveImageMaterialReal(ImageReal material){
        meterialManagerDao.saveImageMaterialReal(material);
    }
    
    
    /**
     * 
     * @description: 保存视频素材
     * @param material 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 上午09:57:01
     */
    public void saveVideoMaterial(VideoMeta material){
        meterialManagerDao.saveVideoMaterial(material);
    }
    
    /**
     * 
     * @description: 保存视频素材正式表
     * @param material 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午04:46:59
     */
    public void saveVideoMaterialReal(VideoReal material){
        meterialManagerDao.saveVideoMaterialReal(material);
    }
    
    
    /**
     * 
     * @description: 获取合同
     * @param id
     * @return 
     * Contract
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-13 下午01:51:42
     */
    public Contract getContractByID(int id) {
        Contract contract = meterialManagerDao.getContractByID(id);
        return contract;
    }
    
    /**
     * 
     * @description: 根据ID获取material
     * @param id
     * @return 
     * Resource
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-14 上午09:51:03
     */
    public Resource getMaterialByID(int id){
        return meterialManagerDao.getMaterialByID(id);
    }
    
    /**
     * 
     * @description: 根据ID获取文字素材
     * @param id
     * @return 
     * MessageMeta
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-14 上午10:45:51
     */
    public MessageMeta getTextMetaByID(int id){
        return meterialManagerDao.getTextMetaByID(id);
    }
    
    /**
     * 
     * @description: 根据ID获取图片素材
     * @param id
     * @return 
     * ImageMeta
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-14 上午10:47:24
     */
    public ImageMeta getImageMetaByID(int id){
        return meterialManagerDao.getImageMetaByID(id);
    }
    
    /**
     * 
     * @description: 根据ID获取视频素材
     * @param id
     * @return 
     * VideoMeta
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-14 上午10:48:48
     */
    public VideoMeta getVideoMetaByID(int id){
        return meterialManagerDao.getVideoMetaByID(id);
    }
    
    
    /**
     * 
     * @description: 根据ID获取问卷主题
     * @param id
     * @return 
     * Questionnaire
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午05:13:06
     */
    public Questionnaire getQueMetaByID(int id){
        return meterialManagerDao.getQueMetaByID(id);
    }
    
    /**
     * 
     * @description: 根据ID获取问卷问题列表
     * @param id
     * @return 
     * List<Question>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午05:17:24
     */
    public List<Question> getQuestionAnswerList(int id){
        return meterialManagerDao.getQuestionAnswerList(id);
    }
    
    /**
     * 获取问题列表
     * @param id
     * @return
     */
    public List<Question> getQuestionList(int id){
    	return meterialManagerDao.getQuestionList(id);
    }
    
    /**
     * 根据问题获取答案列表
     * @param question
     * @return
     */
    public List<Question> getAnswerList(String question,Integer id){
    	return meterialManagerDao.getAnswerList(question,id);
    }
    
    /**
	 * 校验素材是否可删除
	 * return 1不可删除 0 可删除
	 */
	public int checkDelMeterial(Integer id)
	{
		return meterialManagerDao.checkDelMeterial(id);
	}
    
    /**
     * 
     * @description: 删除素材
     * @param id
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 上午10:51:47
     */
    public boolean deleteMaterialByIds(String id) {      
        id = id.replace(" ", "");
        String[] ids = id.split(",");
        String imageMetaIds="";
        String videoMetaIds="";
        String textMetaIds="";
        String questionMetaIds="";
        for(int i=0;i<ids.length;i++){
            Resource material = meterialManagerDao.getMaterialByID(Integer.parseInt(ids[i]));
            if(material.getResourceType()==0){
                //图片
                if(imageMetaIds.equals("")){
                    imageMetaIds = material.getResourceId().toString();
                }else{
                    imageMetaIds=imageMetaIds+","+material.getResourceId();
                }
                //删除对应FTP文件               
                FtpUtils ftp = null;
                try {                    
                    ftp = new FtpUtils();
                    ftp.connectionFtp();
                    ImageMeta imageMeta = meterialManagerDao.getImageMetaByID(material.getResourceId());                  
                    String remoteDirectoryReal=config.getValueByKey("materila.ftp.realPath");
                    String remoteDirectoryTemp=config.getValueByKey("materila.ftp.tempPath");
                    ftp.deleteFile(remoteDirectoryReal+"/"+imageMeta.getName());//删除正式目录下文件
                    ftp.deleteFile(remoteDirectoryTemp+"/"+imageMeta.getName());//删除临时目录下文件
                    

                } catch (Exception e) {
                    e.printStackTrace();
                } finally{
                    if (ftp != null) {
                        ftp.closeFTP();
                    }
                }
                
            }
            if(material.getResourceType()==1){
                //视频
                if(videoMetaIds.equals("")){
                    videoMetaIds = material.getResourceId().toString();
                }else{
                    videoMetaIds=videoMetaIds+","+material.getResourceId();
                }
              //删除对应FTP文件               
                FtpUtils ftp = null;
                try {                    
                    ftp = new FtpUtils();
                    ftp.connectionFtp();
                    VideoMeta videoMeta = meterialManagerDao.getVideoMetaByID(material.getResourceId());                  
                    String remoteDirectoryReal=config.getValueByKey("materila.ftp.realPath");
                    String remoteDirectoryTemp=config.getValueByKey("materila.ftp.tempPath");
                    ftp.deleteFile(remoteDirectoryReal+"/"+videoMeta.getName());//删除正式目录下文件
                    ftp.deleteFile(remoteDirectoryTemp+"/"+videoMeta.getName());//删除临时目录下文件
                    

                } catch (Exception e) {
                    e.printStackTrace();
                } finally{
                    if (ftp != null) {
                        ftp.closeFTP();
                    }
                }
            }
            if(material.getResourceType()==2){
                //文字
                if(textMetaIds.equals("")){
                    textMetaIds = material.getResourceId().toString();
                }else{
                    textMetaIds=textMetaIds+","+material.getResourceId();
                }
            }
            if(material.getResourceType()==3){
               //问卷    
                if(questionMetaIds.equals("")){
                    questionMetaIds = material.getResourceId().toString();
                }else{
                    questionMetaIds=questionMetaIds+","+material.getResourceId();
                }
                
              //删除对应FTP文件               
                FtpUtils ftp = null;
                try {                    
                    ftp = new FtpUtils();
                    ftp.connectionFtp();                
                    String remoteDirectoryReal=config.getValueByKey("materila.ftp.questionRealPath");
                    ftp.removeRemoteDirectory(remoteDirectoryReal+"/"+material.getResourceId());//删除正式目录下文件
                } catch (Exception e) {
                    e.printStackTrace();
                } finally{
                    if (ftp != null) {
                        ftp.closeFTP();
                    }
                }
            }
        }
        

        boolean flag = meterialManagerDao.deleteMaterialByIds("("+id+")","("+imageMetaIds+")","("+videoMetaIds+")","("+textMetaIds+")","("+questionMetaIds+")");
        
        return flag;
    }
    
    
    /**
     * 
     * @description: 删除问卷模板
     * @param id
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-21 上午11:10:44
     */
    public boolean deleteQuestionTemplate(String id) {
        boolean flag = meterialManagerDao.deleteQuestionTemplate("("+id+")");
        return flag;
    }
    
    
    /**
     * 
     * @description: 获取广告位图片规格
     * @param positionId
     * @return 
     * ImageSpecification
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-17 上午11:54:15
     */
    public ImageSpecification getImageMateSpeci(Integer positionId){
        return meterialManagerDao.getImageMateSpeci(positionId);
    }
    
    /**
     * 
     * @description: 获取广告位视频规格
     * @param positionId
     * @return 
     * VideoSpecification
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-27 上午11:21:31
     */
    public VideoSpecification getVideoMateSpeci(Integer positionId){
        return meterialManagerDao.getVideoMateSpeci(positionId);
    }
    
    /**
     * 
     * @description: 保存问卷模板
     * @param questionnaireTemplate 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-20 下午05:03:13
     */
    public void saveQuestionTemplate(QuestionnaireTemplate questionnaireTemplate){
         meterialManagerDao.saveQuestionTemplate(questionnaireTemplate);
    }
    
    
    /**
     * 
     * @description: 根据ID获取问卷模板
     * @param id
     * @return 
     * QuestionnaireTemplate
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-20 下午05:51:54
     */
    public QuestionnaireTemplate getQuestionTemplateByID(int id){
        return meterialManagerDao.getQuestionTemplateByID(id);
    }
    
    /**
     * 
     * @description: 获取模板列表
     * @return 
     * List<QuestionnaireTemplate>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-21 下午02:44:39
     */
    public List<QuestionnaireTemplate> getQuestionTemplateList(){
        return meterialManagerDao.getQuestionTemplateList();
    }
    
    /**
     * 
     * @description: 获取问题列表
     * @param id
     * @return 
     * List<Question>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-23 上午10:33:49
     */
    public List<Question> getQuestionList(Integer id){
        return meterialManagerDao.getQuestionList(id);
    }


	/**
	 * 首页代办获取待审批的资产的总数
	 * @return 待审批的资产的总数
	 */
	public int queryMaterialWaitingAuditCount(String positionIds) {
		return meterialManagerDao.queryMaterialWaitingAuditCount(positionIds);
	}
    
	/**
	 * @description:异步方法：根据广告位获取该广告位能够加载的广告素材类型
	 * @param positionId
	 * @return List<MaterialType> 广告位可加载的广告类型列表
	 */
	public List<MaterialType> getAdTypeByPosition(String positionId){
		 List<MaterialType> list = new ArrayList<MaterialType>(); 
		
		 return list;
	}
	
	/**
	 * 获取子广告位信息
	 * @param id
	 * @return
	 */
	public AdvertPosition getAdvertPosition(Integer id){
		return meterialManagerDao.getAdvertPosition(id);
	}
	
	/**
	 * 获取广告商列表
	 * @return
	 */
	public List<Customer> getCustomerList(){
		return meterialManagerDao.getCustomerList();
	}
	
	/**
	 * 效验素材名称是否重复
	 * @param resourceName
	 * @return
	 */
	public int checkMaterialExist(String resourceName){
		return meterialManagerDao.checkMaterialExist(resourceName);
	}
	
	/**
	 * 效验模板名称是否重复
	 * @param templateName
	 * @return
	 */
	public int checkQuestionTemplateExist(String templateName){
		return meterialManagerDao.checkQuestionTemplateExist(templateName);
	}
	
}
