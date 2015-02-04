package com.dvnchina.advertDelivery.meterial.dao;

import java.util.List;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.bean.contract.ContractQueryBean;
import com.dvnchina.advertDelivery.meterial.bean.MaterialCategory;
import com.dvnchina.advertDelivery.meterial.bean.MaterialType;
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
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;


public interface MeterialManagerDao {
    
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
     * @date: 2013-5-9 上午09:30:40
     */
    public PageBeanDB queryMeterialList(Resource meterialQuery,Integer pageSize,Integer pageNumber);
    
    public PageBeanDB queryMeterialList(Resource resource, String accessUserIds, Integer pageSize, Integer pageNumber);
    
    
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
    public PageBeanDB queryQuestionTemplateList(QuestionnaireTemplate questionnaireTemplate,Integer pageSize,Integer pageNumber);
    
    
    /**
     * 
     * @description: 获取素材分类列表
     * @return 
     * List<MaterialCategory>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-9 下午03:10:51
     */
    public List<MaterialCategory> getMaterialCategoryList();
    
    
    /**
	 * 获取广告商列表
	 * @return
	 */
	public List<Customer> getCustomerList();
    
   
    
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
    public PageBeanDB queryAdPositionList(AdvertPosition advertPositionQuery,Integer pageSize,Integer pageNumber);
    
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
    public List<AdvertPosition> getAdvertPositionList(String positionPackIds);
    
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
    public List<ContractAD> getAdvertPositionPackListByCustomer(Integer customerId);
    
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
    public PageBeanDB queryAdPositionList(AdvertPosition advertPositionQuery,String positionPackIds,Integer pageSize,Integer pageNumber);
    
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
     * @date: 2013-5-9 下午05:48:28
     */
    public PageBeanDB queryContractList(ContractQueryBean contract,Integer pageSize,Integer pageNumber);
    
    /**
     * 
     * @description: 保存Resource
     * @param resource 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-10 下午04:52:10
     */
    public void saveResource(Resource resource);
    
    /**
     * 
     * @description: 保存Resource正式表
     * @param resource 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午04:03:44
     */
    public void saveResourceReal(ResourceReal resource);
    
    /**
     * 
     * @description: 保存问卷主题
     * @param questionnaire 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-10 下午05:20:26
     */
    public void saveQuestionSubject(Questionnaire questionnaire);
    
    /**
     * 
     * @description: 保存问卷主题正式表
     * @param questionnaire 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午05:39:41
     */
    public void saveQuestionSubjectReal(QuestionnaireReal questionnaire);
    
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
    public boolean saveQuestionList(List<Question> questionList);
    
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
    public boolean saveQuestionRealList(List<QuestionReal> questionList);
    
    /**
     * 
     * @description: 保存文字素材子表
     * @param material 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-13 上午11:45:32
     */
    public void saveTextMaterial(MessageMeta material);
    
    /**
     * 
     * @description: 保存文字素材正式表
     * @param material 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午05:02:27
     */
    public void saveTextMaterialReal(MessageReal material);
    
    /**
     * 
     * @description: 保存图片素材
     * @param material 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-14 下午03:06:18
     */
    public void saveImageMaterial(ImageMeta material);
    
    
    /**
     * 
     * @description: 保存图片素材正式表
     * @param material 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午04:22:38
     */
    public void saveImageMaterialReal(ImageReal material);
    
    
    /**
     * 
     * @description: 保存视频素材
     * @param material 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 上午09:57:01
     */
    public void saveVideoMaterial(VideoMeta material);
    
    /**
     * 
     * @description: 保存视频素材正式表
     * @param material 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午04:46:59
     */
    public void saveVideoMaterialReal(VideoReal material);
    
    
    /**
     * 
     * @description: 获取合同
     * @param id
     * @return 
     * Contract
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-13 下午01:54:12
     */
    public Contract getContractByID(int id);
    
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
    public Resource getMaterialByID(int id);
    
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
    public MessageMeta getTextMetaByID(int id);
    
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
    public ImageMeta getImageMetaByID(int id);
    
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
    public VideoMeta getVideoMetaByID(int id);
    
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
    public Questionnaire getQueMetaByID(int id);
    
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
    public List<Question> getQuestionAnswerList(int id);
    
    /**
     * 获取问题列表
     * @param id
     * @return
     */
    public List<Question> getQuestionList(int id);
    
    /**
     * 根据问题获取答案列表
     * @param question
     * @return
     */
    public List<Question> getAnswerList(String question,Integer id);
    
    /**
	 * 校验素材是否可删除
	 * return 1不可删除 0 可删除
	 */
	public int checkDelMeterial(Integer id);
    
    /**
     * 
     * @description: 删除素材 
     * @param ids
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 上午10:53:02
     */
    public boolean deleteMaterialByIds(String ids,String imageIds,String videoIds,String textIds,String questionIds);
    
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
    public ImageSpecification getImageMateSpeci(Integer positionId);
    
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
    public VideoSpecification getVideoMateSpeci(Integer positionId);
    
    /**
     * 
     * @description: 保存问卷模板
     * @param questionnaireTemplate 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-20 下午05:03:13
     */
    public void saveQuestionTemplate(QuestionnaireTemplate questionnaireTemplate);
    
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
    public QuestionnaireTemplate getQuestionTemplateByID(int id);
    
    /**
     * 
     * @description: 删除问卷模板
     * @param ids
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-21 上午11:13:26
     */
    public boolean deleteQuestionTemplate(String ids);
    
    /**
     * 
     * @description: 获取模板列表
     * @return 
     * List<QuestionnaireTemplate>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-21 下午02:44:39
     */
    public List<QuestionnaireTemplate> getQuestionTemplateList();
    
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
    public List<Question> getQuestionList(Integer id);


    /**
	 * @description: 首页代办获取待审批的资产的总数
	 * @return 待审批的资产的总数
	 */
	public int queryMaterialWaitingAuditCount(String positionIds);
	
	
	/**
	 * 获取子广告位信息
	 * @param id
	 * @return
	 */
	public AdvertPosition getAdvertPosition(Integer id);
	
	/**
	 * 效验素材名称是否重复
	 * @param resourceName
	 * @return
	 */
	public int checkMaterialExist(String resourceName);
	
	/**
	 * 效验模板名称是否重复
	 * @param templateName
	 * @return
	 */
	public int checkQuestionTemplateExist(String templateName);
    
}
