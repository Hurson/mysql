package com.dvnchina.advertDelivery.action;

import java.io.File;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.constant.LoginConstant;
import com.dvnchina.advertDelivery.constant.PageConstant;
import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.AdvertPositionType;
import com.dvnchina.advertDelivery.model.ImageSpecification;
import com.dvnchina.advertDelivery.model.PlayCategory;
import com.dvnchina.advertDelivery.model.PositionOccupyStatesInfo;
import com.dvnchina.advertDelivery.model.QuestionnaireSpecification;
import com.dvnchina.advertDelivery.model.TextSpecification;
import com.dvnchina.advertDelivery.model.VideoSpecification;
import com.dvnchina.advertDelivery.service.AdvertPositionService;
import com.dvnchina.advertDelivery.service.AdvertPositionTypeService;
import com.dvnchina.advertDelivery.service.ImageSpecificationService;
import com.dvnchina.advertDelivery.service.PlayCategoryService;
import com.dvnchina.advertDelivery.service.QuestionnaireSpecificationService;
import com.dvnchina.advertDelivery.service.TextSpecificationService;
import com.dvnchina.advertDelivery.service.VideoSpecificationService;
import com.dvnchina.advertDelivery.service.common.tools.support.OperatorFtpFileService;
import com.dvnchina.advertDelivery.service.common.tools.support.OperatorLocalFileService;
import com.dvnchina.advertDelivery.service.common.tools.support.PictureMaterialSpeciValidateService;
import com.dvnchina.advertDelivery.utils.CookieUtils;
import com.dvnchina.advertDelivery.utils.page.PageBean;
import com.dvnchina.advertDelivery.utils.page.PageUtils;

/**
 * 广告位相关操作
 * 
 * @author lester
 * 
 */
public class AdvertPositionAction extends BaseActionSupport<Object> {

	private static final long serialVersionUID = 5528985853425795719L;

	private static Logger logger = Logger.getLogger(AdvertPositionAction.class);
	/**
	 * 操作本地文件
	 */
	private OperatorLocalFileService operatorLocalFileService;
	/**
	 * 操作远程FTP文件
	 */
	private OperatorFtpFileService operatorFtpFileService;
	/**
	 * 图片素材校验
	 */
	private PictureMaterialSpeciValidateService pictureMaterialSpeciValidateService;

	private AdvertPositionService advertPositionService;
	/**
	 * 文字规格
	 */
	private TextSpecificationService textSpecificationService;
	/**
	 * 视频规格
	 */
	private VideoSpecificationService videoSpecificationService;
	/**
	 * 图片规格
	 */
	private ImageSpecificationService pictureMaterialSpeciService;
	/**
	 * 调查问卷规格
	 */
	private QuestionnaireSpecificationService questionnaireSpecificationService;
	/**
	 * 投放节点信息
	 */
	private PlayCategoryService playCategoryService;
	/**
	 * 广告位类型
	 */
	private AdvertPositionTypeService advertPositionTypeService;

	private AdvertPosition advertPosition;
	/**
	 * 普通广告位查询结果集显示页
	 */
	private static final String POSITION_FORWARD_PAGE_RESULT = "page";
	/**
	 * 合同绑定广告位时查询结果集显示页
	 */
	private static final String CONTRACT_BINDING_POSITION_FORWARD_PAGE_RESULT = "contract";
	/**
	 * 所上传广告位背景图片
	 */
	private File backgroundImage;

	private String backgroundImageFileName;
	
	private String backgroundImageContentType;
	
	private String backgroundImageSavePath;
	/**
	 * 保存上传文件的目录，相对于Web应用程序的根路径，在struts.xml中配置
	 */
	private String uploadDir;
	/**
	 * 当前页
	 */
	private Integer currentPage;
	/**
	 * 广告位占用状态信息封装类
	 */
	private PositionOccupyStatesInfo positionOccupyStatesInfo;
	
	private String positionParam;
	

	/**
	 * 获取用户名
	 * */
	private String getUserName() {
		String userName = CookieUtils.getCookieValueByKey(getRequest(),
				LoginConstant.COOKIE_USER_NAME);
		return userName;
	}
	
	/**
	 * 增加广告位
	 * 
	 * @return
	 */
	public String addPage() {
		HttpServletRequest request = null;
		String flag = "";
		try {
			request = ServletActionContext.getRequest();
			initMaterialSpeciInfo(request);
			flag = request.getParameter("flag");
			if (!StringUtils.isNotBlank(flag) && !"saveAgain".equals(flag)) {
				request.setAttribute("advertPosition", new AdvertPosition());
			}
		} catch (Exception e) {
			logger.error("页面跳转时出现异常", e);
		}
		return SUCCESS;
	}

	/**
	 * 查看单个广告位
	 * 
	 * @return
	 */
	public String viewPage() {

		HttpServletRequest request = null;
		List<AdvertPosition> advertPositionQueryList = null;
		try {
			request = this.getRequest();
			advertPositionQueryList = advertPositionService.getAdvertPositionById(advertPosition.getId());
			// 现有参数传递
			//deliverAdvertPosition(request);
			request.setAttribute("formEnable", false);
			if (advertPositionQueryList != null
					&& advertPositionQueryList.size() > 0) {
				
				request.setAttribute("advertPosition", advertPositionQueryList
						.get(0));
				
				ImageSpecification imageSpecification = pictureMaterialSpeciService.get(advertPositionQueryList.get(0).getImageRuleId());
				if(imageSpecification!=null){
					request.setAttribute("imageSpecification",imageSpecification);
				}
				TextSpecification textSpecification=textSpecificationService.get(advertPositionQueryList.get(0).getTextRuleId());
				if(textSpecification!=null){
					request.setAttribute("textSpecification",textSpecification);
				}
				QuestionnaireSpecification questionnaireSpecification=questionnaireSpecificationService.get(advertPositionQueryList.get(0).getQuestionRuleId());
				if(questionnaireSpecification!=null){
					request.setAttribute("questionnaireSpecification",questionnaireSpecification);
				}
				VideoSpecification videoSpecification=videoSpecificationService.get(advertPositionQueryList.get(0).getVideoRuleId());
				if(videoSpecification!=null){
					request.setAttribute("videoSpecification",videoSpecification);
				}
				AdvertPositionType advertPositionType = advertPositionTypeService.get(advertPositionQueryList.get(0).getPositionTypeId());
				if(advertPositionType!=null){
					request.setAttribute("advertPositionType",advertPositionType);
				}
				
			}
		} catch (Exception e) {
			logger.error("查看详情时出现异常", e);
		}
		return SUCCESS;
	}

	/**
	 * 删除广告位
	 * 
	 * @return
	 */
	public String remove() {
		try {
			advertPositionService.remove(advertPosition.getId());
		} catch (Exception e) {
			logger.error("删除数据时出现异常", e);
		}
		return SUCCESS;
	}
	
	public String batchRemove(){
		String ids = this.getRequest().getParameter("ids");
		String[] idArray = null;
		int[] removeResult = null;
		try {
			if(StringUtils.isNotBlank(ids)){
				idArray = ids.split(",");
				removeResult = advertPositionService.removeBatchAdvertPosition(idArray);
				if(removeResult!=null){
					logger.info("删除"+removeResult.length+"条记录");
				}
			}
		} catch (Exception e) {
			logger.error("删除数据时出现异常", e);
		}
		return SUCCESS;
	}

	/**
	 * 更新广告位
	 * 
	 * @return
	 */
	public String updatePage() {
		HttpServletRequest request = null;
		List<AdvertPosition> advertPositionQueryList = null;
		
		try {
			request = this.getRequest();

			advertPositionQueryList = advertPositionService.getAdvertPositionById(advertPosition.getId());
			// 现有参数传递
			//deliverAdvertPosition(request);
			if (advertPositionQueryList != null
					&& advertPositionQueryList.size() > 0) {
				
				ImageSpecification imageSpecification = pictureMaterialSpeciService.get(advertPositionQueryList.get(0).getImageRuleId());
				if(imageSpecification!=null){
					request.setAttribute("imageSpecification",imageSpecification);
				}
				TextSpecification textSpecification=textSpecificationService.get(advertPositionQueryList.get(0).getTextRuleId());
				if(textSpecification!=null){
					request.setAttribute("textSpecification",textSpecification);
				}
				QuestionnaireSpecification questionnaireSpecification=questionnaireSpecificationService.get(advertPositionQueryList.get(0).getQuestionRuleId());
				if(questionnaireSpecification!=null){
					request.setAttribute("questionnaireSpecification",questionnaireSpecification);
				}
				VideoSpecification videoSpecification=videoSpecificationService.get(advertPositionQueryList.get(0).getVideoRuleId());
				if(videoSpecification!=null){
					request.setAttribute("videoSpecification",videoSpecification);
				}
				
				AdvertPositionType advertPositionType = advertPositionTypeService.get(advertPositionQueryList.get(0).getPositionTypeId());
				AdvertPosition advertPosition1 = advertPositionQueryList.get(0);
				if(advertPositionType!=null){
					advertPosition1.setPositionTypeId(advertPositionType.getId());
					advertPosition1.setPositionTypeCode(advertPositionType.getPositionTypeCode());
					advertPosition1.setPositionTypeName(advertPositionType.getPositionTypeName());
					request.setAttribute("advertPositionType",advertPositionType);
				}
				request.setAttribute("advertPosition", advertPosition1);
			}	
		} catch (Exception e) {
			logger.error("更新广告位获取数据时出现异常", e);
		}
		return SUCCESS;
	}

	/**
	 * 查询广告位
	 * 
	 * @return
	 */
	public String queryPage() {

		HttpServletRequest request = this.getRequest();

		String currentPage = request.getParameter("currentPage");

		String contractBindingFlag = request.getParameter("contractBindingFlag");

		String returnPath = "";
		
		String positionIndexFlag = request.getParameter("positionIndexFlag");
		
		String saveOrUpdateFlag = request.getParameter("saveOrUpdateFlag");

		if (StringUtils.isNotBlank(contractBindingFlag)&& "1".equals(contractBindingFlag)) {
			List<AdvertPositionType> apt = this.advertPositionTypeService.getAll();
			request.setAttribute("list",apt);
			returnPath = CONTRACT_BINDING_POSITION_FORWARD_PAGE_RESULT;
		} else {
			returnPath = POSITION_FORWARD_PAGE_RESULT;
		}
		
		if(StringUtils.isNotBlank(saveOrUpdateFlag)){
			request.setAttribute("saveOrUpdateFlag",saveOrUpdateFlag);
		}
		
		if(StringUtils.isNotBlank(positionIndexFlag)){
			request.setAttribute("positionIndexFlag",positionIndexFlag);
		}

		int count = 0;
		int pageNB = 1;
		PageBean pageBean = null;
		int pageSize = PageConstant.PAGE_SIZE;

		if (StringUtils.isNotBlank(currentPage)) {
			pageNB = Integer.valueOf(currentPage);
		}

		Map conditionMap = new HashMap();

		try {
			if (advertPosition!=null) {
				
				if (StringUtils.isNotBlank(advertPosition.getPositionName())) {
					conditionMap.put("POSITION_NAME",advertPosition.getPositionName());
					request.setAttribute("positionName",advertPosition.getPositionName());
				}
				
				if (advertPosition.getPositionTypeId()!=null&&advertPosition.getPositionTypeId().intValue()!=-1) {
					conditionMap.put("POSITION_TYPE_ID", advertPosition.getPositionTypeId());
					request.setAttribute("positionTypeId",advertPosition.getPositionTypeId());
					request.setAttribute("positionTypeName",advertPosition.getPositionTypeName());
				}
				
				if (advertPosition.getIsHd()!=null&&advertPosition.getIsHd().intValue()!=-1) {
					conditionMap.put("IS_HD",advertPosition.getIsHd());
					request.setAttribute("isHd", advertPosition.getIsHd());
				}
				
				if (advertPosition.getDeliveryMode()!=null&&advertPosition.getDeliveryMode().intValue()!=-1) {
					conditionMap.put("DELIVERY_MODE",advertPosition.getDeliveryMode());
					request.setAttribute("deliveryMode", advertPosition
							.getDeliveryMode());
				}
			}
			
			count = advertPositionService.queryCount(conditionMap);
			
			List<AdvertPosition> positionList = advertPositionService.page(
					conditionMap, (pageNB - 1) * PageConstant.PAGE_SIZE, pageNB
							* PageConstant.PAGE_SIZE);
			List<Integer> positionIdList = null;
			if(positionList!=null&&positionList.size()>0){
				positionIdList = advertPositionService.getAdvertPositionOccupyStatus(positionList);
				
				for (AdvertPosition advertPosition : positionList) {
					for (Integer positionId : positionIdList) {
						if(advertPosition.getId().intValue()==positionId.intValue()){
							advertPosition.setState(Constant.POSITION_OCCUPY_STATUS);
						}
					}
				}
			}
			
			
			pageBean = PageUtils.getPageBean2(pageNB, pageSize, count,positionList);
			request.setAttribute("positionList", pageBean.getList());
			request.setAttribute("page", pageBean);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常", e);
		}
		return returnPath;
	}

	/**
	 * 上传广告位背景图
	 */
	public void uploadImage() {
		try {
			String filePath = ServletActionContext.getServletContext()
					.getRealPath(uploadDir);
			Map param = new HashMap();
			param.put("targetDirectory", uploadDir);
			String result = operatorLocalFileService
					.copyLocalFile2TargetDirectory(this.backgroundImage
							.getAbsolutePath(), filePath,
							backgroundImageFileName, param);
			renderHtml(result);
		} catch (Exception e) {
			logger.error("上传背景图出现异常", e);
		}
	}

	/**
	 * 保存或更新广告位
	 */
	public String save() {
		boolean flag = true;
		String message = "";
		List<String> eignValueList = null;
		HttpServletRequest request = getRequest();
		Map<String,String> resultMap = new HashMap<String,String>();
		List<AdvertPosition> advertPositionListFromDBAfter = null;
		List<AdvertPosition> advertPositionListFromDBBefore = null;
		AdvertPosition advertPosition = null;
		Timestamp timestamp = new Timestamp(new Date().getTime());
		String handleFlag = "";
		String cause = "";
		try {
			positionParam = URLDecoder.decode(positionParam, "UTF-8");
			if (StringUtils.isNotBlank(positionParam)) {
				advertPosition = JSON.toJavaObject(JSON.parseObject(positionParam),AdvertPosition.class);
			}
		} catch (Exception e) {
			handleFlag = "false";
			cause = "parseError";
			resultMap.put("flag", handleFlag);
			resultMap.put("cause", cause);
			String json = JSON.toJSONString(resultMap);
			this.renderJson(json);
			return null;
		}
		
		if (advertPosition != null && advertPosition.getId() != null) {
			advertPosition.setModifyTime(timestamp);
			advertPosition.setOperationId(getUserId()+"");
			//advertPosition.setState("SUCC");
			// 更新
			try {
				List<String> eigenValueListSource = new ArrayList<String>();
				eigenValueListSource=advertPositionService.generateCharacteristicIdentification(advertPosition.getIsHd(),advertPosition.getPositionTypeCode());
				advertPositionListFromDBBefore = advertPositionService.getAdvertPositionById(advertPosition.getId());
				
				String eignValueSd = advertPosition.getCharacteristicIdentification();
				String eignValueHd = advertPosition.getCharacteristicIdentification();

				if (flag) {
					advertPositionListFromDBAfter = advertPositionService.findPositionByEign(eigenValueListSource);
					if (advertPositionListFromDBAfter != null&& advertPositionListFromDBAfter.size() > 0) {
						for (AdvertPosition advertPositionInner : advertPositionListFromDBAfter) {
							if (advertPosition.getId().intValue() != advertPositionInner.getId().intValue()) {
								handleFlag = "false";
								cause = "characteristicIdentificationExist";
								resultMap.put("flag", handleFlag);
								resultMap.put("cause", cause);
								String json = JSON.toJSONString(resultMap);
								this.renderJson(json);
								return null;
							}
						}
					}

				} else {
					handleFlag = "false";
					cause = "validateFailure";
					resultMap.put("flag", handleFlag);
					resultMap.put("cause", cause);
					String json = JSON.toJSONString(resultMap);
					this.renderJson(json);
					return null;
				}
				
				// 组合成为两条记录
				List<AdvertPosition> advertPositionList = mixData(advertPosition);
				// 更新记录
				List<AdvertPosition> updateAdvertPositionList = new ArrayList<AdvertPosition>();
				
				if (advertPositionList != null
						&& advertPositionList.size() == 2) {
					if (advertPositionListFromDBBefore != null
							&& advertPositionListFromDBBefore.size() > 0) {
						for (Iterator iterator = advertPositionListFromDBBefore
								.iterator(); iterator.hasNext();) {
							AdvertPosition advertPositionDB = (AdvertPosition) iterator
									.next();
							for (Iterator iterator2 = advertPositionList
									.iterator(); iterator2.hasNext();) {
								AdvertPosition advertPositionMix = (AdvertPosition) iterator2
										.next();
								if (advertPositionMix.getCharacteristicIdentification().equals(advertPositionDB.getCharacteristicIdentification())) {
									updateAdvertPositionList.add(advertPositionMix);
								}
							}

						}
						// 将已放入更新集合中的记录从原有集合中删除
						advertPositionList.removeAll(updateAdvertPositionList);
						if (updateAdvertPositionList != null
								&& updateAdvertPositionList.size() > 0) {
							// 一条记录更新
							advertPositionService.updateBatch(updateAdvertPositionList);
						}

						if (advertPositionList != null
								&& advertPositionList.size() > 0) {
							// 一条记录新增
							advertPositionService.saveBatchPosition(advertPositionList);
						}
					}
				} else if (advertPositionList != null
						&& advertPositionList.size() == 1) {
					advertPositionService.updateBatch(advertPositionList);
				}

			} catch (Exception e) {
				logger.error("更新失败",e);
				handleFlag = "false";
				cause = "updateError";
				resultMap.put("flag", handleFlag);
				resultMap.put("cause", cause);
				String json = JSON.toJSONString(resultMap);
				this.renderJson(json);
				return null;
			}
		} else {
			// 新增
			try {
				advertPosition.setModifyTime(timestamp);
				advertPosition.setCreateTime(timestamp);
				advertPosition.setOperationId(getUserId()+"");
				
				if (flag) {
					eignValueList = advertPositionService.generateCharacteristicIdentification(advertPosition.getIsHd(), advertPosition.getPositionTypeCode());
					List<AdvertPosition> advertPositionList = advertPositionService.findPositionByEign(eignValueList);

					if (advertPositionList != null&& advertPositionList.size() > 0) {
						handleFlag = "false";
						cause = "characteristicIdentificationExist";
						resultMap.put("flag", handleFlag);
						resultMap.put("cause", cause);
						String json = JSON.toJSONString(resultMap);
						this.renderJson(json);
						return null;
					}

				} else {
					handleFlag = "false";
					cause = "validateFailure";
					resultMap.put("flag", handleFlag);
					resultMap.put("cause", cause);
					String json = JSON.toJSONString(resultMap);
					this.renderJson(json);
					return null;
				}
				List<AdvertPosition> advertPositionList = mixData(advertPosition);
				advertPositionService.saveBatchPosition(advertPositionList);
				handleFlag = "true";
				resultMap.put("flag", handleFlag);
				String json = JSON.toJSONString(resultMap);
				this.renderJson(json);
				return null;
			} catch (Exception e) {
				handleFlag = "false";
				cause = "saveError";
				resultMap.put("flag", handleFlag);
				resultMap.put("cause", cause);
				String json = JSON.toJSONString(resultMap);
				this.renderJson(json);
				return null;
			}

		}
		handleFlag = "true";
		resultMap.put("flag", handleFlag);
		String json = JSON.toJSONString(resultMap);
		this.renderJson(json);
		return null;
	}

	public void initMaterialSpeciInfo(HttpServletRequest request) {
		List<TextSpecification> contentMaterialSpecilList = textSpecificationService
				.page(null, 0, 10);
		List<VideoSpecification> videoMaterialSpeciList = videoSpecificationService
				.page(null, 0, 10);
		List<ImageSpecification> pictureMaterialSpeciList = pictureMaterialSpeciService
				.page(null, 0, 10);
		List<QuestionnaireSpecification> questionMaterialSpeciList = questionnaireSpecificationService
				.page(null, 0, 10);
		List<PlayCategory> playCategoryList = playCategoryService.page(null, 0,
				10);
		List<AdvertPositionType> advertPositionTypeList = advertPositionTypeService
				.page(null, 0, 10);

		request.setAttribute("contentMaterialSpecilList",
				contentMaterialSpecilList);
		request.setAttribute("videoMaterialSpeciList", videoMaterialSpeciList);
		request.setAttribute("pictureMaterialSpeciList",
				pictureMaterialSpeciList);
		request.setAttribute("questionMaterialSpeciList",
				questionMaterialSpeciList);
		request.setAttribute("playCategoryList", playCategoryList);
		request.setAttribute("advertPositionTypeList", advertPositionTypeList);
	}

	/**
	 * 查看和更新广告位信息前所需要加载的原始广告位信息
	 */
	private void deliverAdvertPosition(HttpServletRequest request) {
		// 初始化参数
		initMaterialSpeciInfo(request);
		AdvertPosition advertPosition = new AdvertPosition();
		try {
			Enumeration e = request.getParameterNames();
			while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				String value = null;
				String targetString = "advertPosition.";
				System.out.println("name=" + name);
				if (StringUtils.isNotBlank(name)
						&& name.startsWith(targetString)) {
					value = new String(request.getParameter(name).getBytes(
							"ISO-8859-1"), "UTF-8").trim();
					value = value.replace("\\", "/");
					logger.debug("name=" + name);
					logger.debug("name.indexOf(targetString)="
							+ name.indexOf(targetString));
					logger.debug("targetString.length()="
							+ targetString.length());
					BeanUtils.setProperty(advertPosition, name.substring(name
							.indexOf(targetString)
							+ targetString.length()), value);
				}
			}
			request.setAttribute("advertPosition", advertPosition);
		} catch (Exception e) {
			logger.error("获取信息失败", e);
		}
	}

	/**
	 * 将页面中传递过来的参数进行转码操作，避免出现中文乱码现象，此方法可利用泛型简化
	 */
	private AdvertPosition transcoder(HttpServletRequest request) {

		AdvertPosition advertPosition = new AdvertPosition();
		try {
			Enumeration e = request.getParameterNames();
			while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				String value = "";
				String targetString = "advertPosition.";
				System.out.println("name=" + name);
				if (StringUtils.isNotBlank(name)
						&& name.startsWith(targetString)) {
					// 进行编码转换
					value = new String(request.getParameter(name).getBytes(
							"ISO-8859-1"), "UTF-8").trim();
					value = value.replace("\\", "/");
					BeanUtils.setProperty(advertPosition, name.substring(name
							.indexOf(targetString)
							+ targetString.length()), value);
				}
			}
		} catch (Exception e) {
			logger.error("编码转换出现异常", e);
		}
		return advertPosition;
	}

	private List<AdvertPosition> mixData(AdvertPosition advertPosition)
			throws Exception {

		List<AdvertPosition> advertPositionList = new ArrayList<AdvertPosition>();

		Map<String, String> mapSd = new HashMap<String, String>();

		Map<String, String> mapHd = new HashMap<String, String>();

		String contentMaterialSpeciIdList = advertPosition.getTextRuleIdList();
		if (StringUtils.isNotBlank(contentMaterialSpeciIdList)) {
			String[] contentMaterialSpeciIdArray = contentMaterialSpeciIdList
					.split(";");
			if (contentMaterialSpeciIdArray != null
					&& contentMaterialSpeciIdArray.length >= 1) {
				for (String contentMaterialSpeciId : contentMaterialSpeciIdArray) {
					if (StringUtils.isNotBlank(contentMaterialSpeciId)
							&& contentMaterialSpeciId.contains("sd")) {
						mapSd.put("content", contentMaterialSpeciId);
					} else if (StringUtils.isNotBlank(contentMaterialSpeciId)
							&& contentMaterialSpeciId.contains("hd")) {
						mapHd.put("content", contentMaterialSpeciId);
					}
				}
			}
		}

		String videoMaterialSpeciIdList = advertPosition.getVideoRuleIdList();
		if (StringUtils.isNotBlank(videoMaterialSpeciIdList)) {
			String[] videoMaterialSpeciIdArray = videoMaterialSpeciIdList
					.split(";");
			if (videoMaterialSpeciIdArray != null
					&& videoMaterialSpeciIdArray.length >= 1) {
				for (String videoMaterialSpeciId : videoMaterialSpeciIdArray) {
					if (StringUtils.isNotBlank(videoMaterialSpeciId)
							&& videoMaterialSpeciId.contains("sd")) {
						mapSd.put("video", videoMaterialSpeciId);
					} else if (StringUtils.isNotBlank(videoMaterialSpeciId)
							&& videoMaterialSpeciId.contains("hd")) {
						mapHd.put("video", videoMaterialSpeciId);
					}
				}
			}
		}

		String pictureMaterialSpeciIdList = advertPosition.getImageRuleIdList();
		if (StringUtils.isNotBlank(pictureMaterialSpeciIdList)) {
			String[] pictureMaterialSpeciIdArray = pictureMaterialSpeciIdList
					.split(";");
			if (pictureMaterialSpeciIdArray != null
					&& pictureMaterialSpeciIdArray.length >= 1) {
				for (String pictureMaterialSpeciId : pictureMaterialSpeciIdArray) {
					if (StringUtils.isNotBlank(pictureMaterialSpeciId)
							&& pictureMaterialSpeciId.contains("sd")) {
						mapSd.put("picture", pictureMaterialSpeciId);
					} else if (StringUtils.isNotBlank(pictureMaterialSpeciId)
							&& pictureMaterialSpeciId.contains("hd")) {
						mapHd.put("picture", pictureMaterialSpeciId);
					}
				}
			}
		}

		String questionMaterialSpeciIdList = advertPosition
				.getQuestionRuleIdList();
		if (StringUtils.isNotBlank(questionMaterialSpeciIdList)) {
			String[] questionMaterialSpeciIdArray = questionMaterialSpeciIdList
					.split(";");
			if (questionMaterialSpeciIdArray != null
					&& questionMaterialSpeciIdArray.length >= 1) {
				for (String questionMaterialSpeciId : questionMaterialSpeciIdArray) {
					if (StringUtils.isNotBlank(questionMaterialSpeciId)
							&& questionMaterialSpeciId.contains("sd")) {
						mapSd.put("question", questionMaterialSpeciId);
					} else if (StringUtils.isNotBlank(questionMaterialSpeciId)
							&& questionMaterialSpeciId.contains("hd")) {
						mapHd.put("question", questionMaterialSpeciId);
					}
				}
			}
		}
		advertPosition.setCreateTime(new Timestamp(new Date().getTime()));
		// 标清
		AdvertPosition sdPosition = null;
		// 高清
		AdvertPosition hdPosition = null;
		// Bean 拷贝
		// 都支持
		if (2 == advertPosition.getIsHd()) {
			/*// 标清
			sdPosition = new AdvertPosition();
			// 标清属性拷贝
			BeanUtils.copyProperties(sdPosition, advertPosition);

			String sdPositionEigenValue = sdPosition
					.getCharacteristicIdentification();

			if (!sdPositionEigenValue.startsWith("sd_")) {
				sdPositionEigenValue = sdPositionEigenValue.replace("hd_", "");
				sdPositionEigenValue = "sd_" + sdPositionEigenValue;
				sdPosition.setCharacteristicIdentification(sdPositionEigenValue);
				sdPosition.setIsHd(0);
			} else {
				sdPosition.setIsHd(0);
			}
			// 高清
			hdPosition = new AdvertPosition();
			// 高清属性拷贝
			BeanUtils.copyProperties(hdPosition, advertPosition);

			String hdPositionEigenValue = sdPosition
					.getCharacteristicIdentification();

			if (!hdPositionEigenValue.startsWith("hd_")) {
				hdPositionEigenValue = hdPositionEigenValue.replace("sd_", "");
				hdPositionEigenValue = "hd_" + hdPositionEigenValue;
				hdPosition
						.setCharacteristicIdentification(hdPositionEigenValue);
				hdPosition.setIsHd(1);
			} else {
				hdPosition.setIsHd(1);
			}
			// 标清
			advertPositionList.add(sdPosition);
			advertPositionList.add(hdPosition);*/
		} else if (0 == advertPosition.getIsHd()) {
			// 标清
			sdPosition = new AdvertPosition();
			
			// 标清属性拷贝
			advertPosition.setCharacteristicIdentification("sd_"+advertPosition.getPositionTypeCode());
			BeanUtils.copyProperties(sdPosition, advertPosition);
			// 高清
			advertPositionList.add(sdPosition);
		} else if (1 == advertPosition.getIsHd()) {
			hdPosition = new AdvertPosition();
			// 高清属性拷贝
			advertPosition.setCharacteristicIdentification("hd_"+advertPosition.getPositionTypeCode());
			BeanUtils.copyProperties(hdPosition, advertPosition);
			advertPositionList.add(hdPosition);
		}

		// 标清
		if (mapSd.size() > 0) {
			for (Iterator iterator = mapSd.entrySet().iterator(); iterator
					.hasNext();) {
				Map.Entry<String, String> map = (Map.Entry<String, String>) iterator
						.next();
				String key = map.getKey();
				String value = map.getValue();
				if (StringUtils.isNotBlank(key)) {
					if ("content".equals(key)) {
						sdPosition.setTextRuleId(Integer.valueOf(value
								.split(":")[1]));
					} else if ("video".equals(key)) {
						sdPosition.setVideoRuleId(Integer.valueOf(value
								.split(":")[1]));
					} else if ("picture".equals(key)) {
						sdPosition.setImageRuleId(Integer.valueOf(value
								.split(":")[1]));
					} else if ("question".equals(key)) {
						sdPosition.setQuestionRuleId(Integer.valueOf(value
								.split(":")[1]));
					}
				}
			}
		}
		// 高清
		if (mapHd.size() > 0) {
			for (Iterator iterator = mapHd.entrySet().iterator(); iterator
					.hasNext();) {
				Map.Entry<String, String> map = (Map.Entry<String, String>) iterator
						.next();
				String key = map.getKey();
				String value = map.getValue();
				if (StringUtils.isNotBlank(key)) {
					if ("content".equals(key)) {
						hdPosition.setTextRuleId(Integer.valueOf(value
								.split(":")[1]));
					} else if ("video".equals(key)) {
						hdPosition.setVideoRuleId(Integer.valueOf(value
								.split(":")[1]));
					} else if ("picture".equals(key)) {
						hdPosition.setImageRuleId(Integer.valueOf(value
								.split(":")[1]));
					} else if ("question".equals(key)) {
						hdPosition.setQuestionRuleId(Integer.valueOf(value
								.split(":")[1]));
					}
				}
			}
		}
		return advertPositionList;
	}

	/**
	 * 校验数据是否为空
	 * 
	 * @param advertPosition
	 * @return
	 */
	public boolean validateData(String type, AdvertPosition advertPosition) {
		boolean flag = true;
		if ("add".equals(type)) {

			if (StringUtils.isNotBlank(advertPosition.getBackgroundPath())) {
				flag = validataTool(advertPosition);
			} else {
				flag = false;
			}

		} else if ("update".equals(type)) {
			flag = validataTool(advertPosition);
		}
		return flag;
	}

	/**
	 * 
	 * @return
	 */
	public boolean validataTool(AdvertPosition advertPosition) {
		boolean flag = true;
		/*
		 * if(advertPosition==null){ flag = false; return flag; }
		 * 
		 * if(advertPosition.getPositionTypeId()==null){ flag = false; return
		 * flag; }
		 * 
		 * if(StringUtils.isBlank(advertPosition)){ flag = false; return flag; }
		 * 
		 * if(StringUtils.isBlank(advertPosition.getPositionName())){ flag =
		 * false; return flag; }
		 * 
		 * if(StringUtils.isBlank(advertPosition.getDescribe())){ flag = false;
		 * return flag; }else{
		 * advertPosition.setDescribe(advertPosition.getDescribe().trim()); }
		 * 
		 * if(advertPosition.getPictureMaterialSpeciId()==null){ flag = false;
		 * return flag; }
		 * 
		 * if(advertPosition.getVideoMaterialSpeciId()==null){ flag = false;
		 * return flag; }
		 * 
		 * if(advertPosition.getContentMaterialSpeciId()==null){ flag = false;
		 * return flag; }
		 * 
		 * if(advertPosition.getQuestionMaterialSpeciId()==null){ flag = false;
		 * return flag; }
		 * 
		 * if((advertPosition.getIsHD()==null)||(advertPosition.getIsHD().intValue()==-1)){
		 * flag = false; return flag; }
		 * 
		 * if(advertPosition.getIsOverlying()==null){ flag = false; return flag; }
		 * 
		 * if(advertPosition.getIsPolling()==null){ flag = false; return flag; }
		 * 
		 * if(advertPosition.getPollingCount()==null){ flag = false; return
		 * flag; }
		 * 
		 * if(advertPosition.getAdvertiseWay()==null){ flag = false; return
		 * flag; }
		 * 
		 * if(StringUtils.isBlank(advertPosition.getPrice())){ flag = false;
		 * return flag; }
		 * 
		 * if(StringUtils.isBlank(advertPosition.getDiscount())){ flag = false;
		 * return flag; }
		 * 
		 * if(StringUtils.isBlank(advertPosition.getCoordinate())){ flag =
		 * false; return flag; }
		 * 
		 * if(StringUtils.isBlank(advertPosition.getPositionPlatform())){ flag =
		 * false; return flag; }
		 */

		return flag;
	}

	/**
	 * 更新广告位属性
	 * 
	 * @return
	 */
	public String propertyManagePage() {
		return POSITION_FORWARD_PAGE_RESULT;
	}

	/**
	 * 广告位状态设置
	 * 
	 * @return
	 */
	public String stateManagePage() {
		return POSITION_FORWARD_PAGE_RESULT;
	}

	/**
	 * 占用情况
	 * 
	 * @return
	 */
	public String queryPositionOccupyStates() {

		int count = 0;
		int pageNB = 1;
		PageBean pageBean = null;
		int pageSize = PageConstant.PAGE_SIZE;

		HttpServletRequest request = this.getRequest();

		String currentPage = request.getParameter("currentPage");

		String queryConditionPositionId = request.getParameter("positionId");
		
		Integer queryConditionPositionIdInt = null;

		
		Integer statusInt = null;
		String startDate = null;
		String endDate = null;

		if (StringUtils.isNotBlank(currentPage)) {
			pageNB = Integer.valueOf(currentPage);
		}

		try {
			// 从广告位列表中进入时传递过来此参数，分页时也会使用此参数
			if (StringUtils.isNotBlank(queryConditionPositionId)) {
				queryConditionPositionIdInt = Integer.valueOf(queryConditionPositionId);
				request.setAttribute("positionId", queryConditionPositionId);
			}
			
			if (positionOccupyStatesInfo!=null) {
				// 分页时会传递此参数
				if (positionOccupyStatesInfo.getPositionOccupyStatesType() != null) {
					statusInt = positionOccupyStatesInfo.getPositionOccupyStatesType();
				} else {
					statusInt = 1;
				}
				startDate = positionOccupyStatesInfo.getEffectiveStartDate();
				endDate = positionOccupyStatesInfo.getEffectiveEndDate();
			}else{
				statusInt = 1;
			}
			
			count = advertPositionService.queryCount4PositionOccupyStatus(statusInt, queryConditionPositionIdInt,startDate,endDate);
			List<PositionOccupyStatesInfo> positionOccupyStatesInfoList = advertPositionService.page4PositionOccupyStatus(statusInt,queryConditionPositionIdInt,(pageNB - 1)
									* PageConstant.PAGE_SIZE, pageNB
									* PageConstant.PAGE_SIZE,startDate,endDate);
			
			request.setAttribute("positionOccupyStatesInfo", positionOccupyStatesInfo);
			
			pageBean = PageUtils.getPageBean2(pageNB, pageSize, count,positionOccupyStatesInfoList);
			request.setAttribute("objectList", pageBean.getList());
			request.setAttribute("page", pageBean);
		} catch (Exception e) {
			logger.error("获取广告位占用信息时出现异常", e);
		}
		return SUCCESS;
	}

	public File getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(File backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public OperatorLocalFileService getOperatorLocalFileService() {
		return operatorLocalFileService;
	}

	public void setOperatorLocalFileService(
			OperatorLocalFileService operatorLocalFileService) {
		this.operatorLocalFileService = operatorLocalFileService;
	}

	public OperatorFtpFileService getOperatorFtpFileService() {
		return operatorFtpFileService;
	}

	public void setOperatorFtpFileService(
			OperatorFtpFileService operatorFtpFileService) {
		this.operatorFtpFileService = operatorFtpFileService;
	}

	public PictureMaterialSpeciValidateService getPictureMaterialSpeciValidateService() {
		return pictureMaterialSpeciValidateService;
	}

	public void setPictureMaterialSpeciValidateService(
			PictureMaterialSpeciValidateService pictureMaterialSpeciValidateService) {
		this.pictureMaterialSpeciValidateService = pictureMaterialSpeciValidateService;
	}

	public String getBackgroundImageFileName() {
		return backgroundImageFileName;
	}

	public void setBackgroundImageFileName(String backgroundImageFileName) {
		this.backgroundImageFileName = backgroundImageFileName;
	}

	public String getBackgroundImageContentType() {
		return backgroundImageContentType;
	}

	public void setBackgroundImageContentType(String backgroundImageContentType) {
		this.backgroundImageContentType = backgroundImageContentType;
	}

	public String getBackgroundImageSavePath() {
		return backgroundImageSavePath;
	}

	public void setBackgroundImageSavePath(String backgroundImageSavePath) {
		this.backgroundImageSavePath = backgroundImageSavePath;
	}

	public AdvertPosition getAdvertPosition() {
		return advertPosition;
	}

	public void setAdvertPosition(AdvertPosition advertPosition) {
		this.advertPosition = advertPosition;
	}

	public AdvertPositionService getAdvertPositionService() {
		return advertPositionService;
	}

	public void setAdvertPositionService(
			AdvertPositionService advertPositionService) {
		this.advertPositionService = advertPositionService;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public ImageSpecificationService getPictureMaterialSpeciService() {
		return pictureMaterialSpeciService;
	}

	public void setPictureMaterialSpeciService(
			ImageSpecificationService pictureMaterialSpeciService) {
		this.pictureMaterialSpeciService = pictureMaterialSpeciService;
	}

	public PlayCategoryService getPlayCategoryService() {
		return playCategoryService;
	}

	public void setPlayCategoryService(PlayCategoryService playCategoryService) {
		this.playCategoryService = playCategoryService;
	}

	public AdvertPositionTypeService getAdvertPositionTypeService() {
		return advertPositionTypeService;
	}

	public void setAdvertPositionTypeService(
			AdvertPositionTypeService advertPositionTypeService) {
		this.advertPositionTypeService = advertPositionTypeService;
	}

	public PositionOccupyStatesInfo getPositionOccupyStatesInfo() {
		return positionOccupyStatesInfo;
	}

	public void setPositionOccupyStatesInfo(
			PositionOccupyStatesInfo positionOccupyStatesInfo) {
		this.positionOccupyStatesInfo = positionOccupyStatesInfo;
	}

	public TextSpecificationService getTextSpecificationService() {
		return textSpecificationService;
	}

	public void setTextSpecificationService(
			TextSpecificationService textSpecificationService) {
		this.textSpecificationService = textSpecificationService;
	}

	public VideoSpecificationService getVideoSpecificationService() {
		return videoSpecificationService;
	}

	public void setVideoSpecificationService(
			VideoSpecificationService videoSpecificationService) {
		this.videoSpecificationService = videoSpecificationService;
	}

	public QuestionnaireSpecificationService getQuestionnaireSpecificationService() {
		return questionnaireSpecificationService;
	}

	public void setQuestionnaireSpecificationService(
			QuestionnaireSpecificationService questionnaireSpecificationService) {
		this.questionnaireSpecificationService = questionnaireSpecificationService;
	}

	public String getPositionParam() {
		return positionParam;
	}

	public void setPositionParam(String positionParam) {
		this.positionParam = positionParam;
	}
	
	public static void main(String[] args) throws Exception{
		String abc = "{\"characteristicIdentification\":\"00001\",\"id\":\"\",\"positionName\":\"00001名称\",\"discount\":\"00001折扣\",\"deliveryMode\":\"0\",\"isAdd\":\"1\",\"positionTypeId\":\"91\",\"deliveryPlatform\":\"0\",\"isLoop\":\"1\",\"coordinate\":\"00001坐标\",\"price\":\"00001价格\",\"materialNumber\":\"0\",\"startTime\":\"03/13/2013\",\"endTime\":\"03/19/2013\",\"widthHeight\":\"宽高\",\"backgroundPath\":\"images/position/1364327023919.jpg\",\"description\":\"00001描述\",\"textRuleIdList\":0,\"imageRuleIdList\":\"sd:;\",\"videoRuleIdList\":0,\"questionRuleIdList\":0}";   
		abc = URLDecoder.decode(abc, "UTF-8");
		AdvertPosition positionParam = null;
		if (StringUtils.isNotBlank(abc)) {
			positionParam = JSON.toJavaObject(JSON.parseObject(abc),AdvertPosition.class);
		}
	}

}