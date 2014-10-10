package com.dvnchina.advertDelivery.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.bean.config.AdvertPositionBean;
import com.dvnchina.advertDelivery.bean.config.CommonConfigBean;
import com.dvnchina.advertDelivery.bean.config.InterfaceConfigBean;
import com.dvnchina.advertDelivery.bean.config.PlatformConfigBean;
import com.dvnchina.advertDelivery.constant.PageConstant;
import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.service.AdvertPositionService;
import com.dvnchina.advertDelivery.service.SystemConfigService;
import com.dvnchina.advertDelivery.utils.StringUtil;
import com.dvnchina.advertDelivery.utils.page.PageBean;
import com.dvnchina.advertDelivery.utils.page.PageUtils;

/**
 * 系统配置类
 * 
 * @author Administrator
 * 
 */
public class ConfigAction extends BaseActionSupport<Object> {

	private static Logger logger = Logger.getLogger(ConfigAction.class);

	private static final long serialVersionUID = 1L;

	/**
	 * 平台配置
	 */
	private PlatformConfigBean platformConfigBean = new PlatformConfigBean();

	/**
	 * 接口配置
	 */
	private InterfaceConfigBean interfaceConfigBean = new InterfaceConfigBean();

	/**
	 * 广告区域位置配置
	 */
	private CommonConfigBean configBean = new CommonConfigBean();
	
	/**
	 * 播放次数
	 */
	private CommonConfigBean playConfigBean = new CommonConfigBean();

	/**
	 * 广告位
	 */
	private AdvertPosition advertPosition;
	

	private SystemConfigService systemConfigService;

	private AdvertPositionService advertPositionService;
	
	
	
	
	public PlatformConfigBean getPlatformConfigBean() {
		return platformConfigBean;
	}

	public void setPlatformConfigBean(PlatformConfigBean platformConfigBean) {
		this.platformConfigBean = platformConfigBean;
	}

	public InterfaceConfigBean getInterfaceConfigBean() {
		return interfaceConfigBean;
	}

	public void setInterfaceConfigBean(InterfaceConfigBean interfaceConfigBean) {
		this.interfaceConfigBean = interfaceConfigBean;
	}

	public SystemConfigService getSystemConfigService() {
		return systemConfigService;
	}

	public void setSystemConfigService(SystemConfigService systemConfigService) {
		this.systemConfigService = systemConfigService;
	}

	public CommonConfigBean getConfigBean() {
		return configBean;
	}

	public void setConfigBean(CommonConfigBean configBean) {
		this.configBean = configBean;
	}

	public CommonConfigBean getPlayConfigBean() {
		return playConfigBean;
	}

	public void setPlayConfigBean(CommonConfigBean playConfigBean) {
		this.playConfigBean = playConfigBean;
	}

	public AdvertPositionService getAdvertPositionService() {
		return advertPositionService;
	}

	public void setAdvertPositionService(AdvertPositionService advertPositionService) {
		this.advertPositionService = advertPositionService;
	}

	public AdvertPosition getAdvertPosition() {
		return advertPosition;
	}

	public void setAdvertPosition(AdvertPosition advertPosition) {
		this.advertPosition = advertPosition;
	}

	
	
	
	
	
	
	/**
	 * 保存平台配置信息
	 * 
	 * @return
	 */
	public String saveUrl() {
		String message = "";
		boolean flag = true;
		try {
			systemConfigService.addOrUpdatePlatformUrl(platformConfigBean);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		if (flag) {
			message = "保存成功!";
		} else {
			message = "保存失败!";
		}
		this.renderHtml(generateMessage("getAllPlatformConfigList.do?method=getAllPlatformConfigList", message));
		return SUCCESS;
	}

	/**
	 * 删除平台的URl
	 * 
	 * @return
	 */
	public String deletePlatformUrl() {

		String urlId = platformConfigBean.getId();
		boolean flag = false;
		if (StringUtils.isNotBlank(urlId))
			flag = systemConfigService.deletePlatformUrl(urlId);
		return SUCCESS;
	}

	/**
	 * 获取所有平台的配置
	 * 
	 * @return
	 */
	public String getAllPlatformConfigList() {
		List<PlatformConfigBean> platformConfigBeans = systemConfigService.getPlatformUrl();
		HttpServletRequest request = getRequest();
		request.setAttribute("platformConfigBeans", platformConfigBeans);
		request.setAttribute("platformConfigBean", new PlatformConfigBean());
		return SUCCESS;
	}

	/**********************************************************************************/

	/**
	 * 保存和更新接口配置
	 * 
	 * @return
	 */
	public String saveOrUpdateInterface() {
		String message = "";
		boolean flag = true;
		try {
			systemConfigService.addOrUpdateInterface(interfaceConfigBean);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		if (flag) {
			message = "保存成功!";
		} else {
			message = "保存失败!";
		}
		this.renderHtml(generateMessage("getInterfaceConfigList.do?method=getInterfaceConfigList", message));
		return SUCCESS;
	}

	/**
	 * 删除平台的URl
	 * 
	 * @return
	 */
	public String deleteInterface() {

		String urlId = interfaceConfigBean.getId();
		if (StringUtils.isNotBlank(urlId))
			systemConfigService.deleteInterface(urlId);

		return SUCCESS;
	}

	/**
	 * 获取所有数据接口的配置
	 * 
	 * @return
	 */
	public String getInterfaceConfigList() {
		List<InterfaceConfigBean> interfaceConfigBeans = systemConfigService.getInterfaceConfigList();
		HttpServletRequest request = getRequest();
		request.setAttribute("interfaceConfigBeans", interfaceConfigBeans);
		request.setAttribute("interfaceConfigBean", new InterfaceConfigBean());
		return SUCCESS;
	}

	/*************************************************************************************/

	/**
	 * 保存和修改广告显示位置
	 * 
	 * @return
	 */
	public String saveOrupdatePosition() {
		String message = "";
		boolean flag = true;
		try {
			systemConfigService.addOrUpdateShowPosition(configBean);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		if (flag) {
			message = "保存成功!";
		} else {
			message = "保存失败!";
		}
		this.renderHtml(generateMessage("getPositionConfigList.do?method=getPositionConfigList", message));
		return SUCCESS;

	}

	/**
	 * 删除广告字幕的显示位置
	 * 
	 * @return
	 */
	public String deletePostion() {
		String urlId = configBean.getId();
		if (StringUtils.isNotBlank(urlId))
			systemConfigService.deleteShowPosition(urlId);
		return SUCCESS;
	}

	/**
	 * 获取显示区域位置的配置
	 * 
	 * @return
	 */
	public String getPositionConfigList() {
		List<CommonConfigBean> positionConfigBeans = systemConfigService.getShowPositionList();
		getRequest().setAttribute("configBeans", positionConfigBeans);
		getRequest().setAttribute("configBean", new CommonConfigBean());
		return SUCCESS;
	}

	/**********************************************************************************************/

	/**
	 * 保存和更新插播次数
	 * 
	 * @return
	 */
	public String saveOrUpdatePlayCount() {

		systemConfigService.addOrUpdataPlayCount(playConfigBean);
		return NONE;
	}

	/**
	 * 查询广告位信息
	 * 
	 * @return
	 */
	public String queryPage() {
		
		HttpServletRequest request = getRequest();

		List<CommonConfigBean> playCounts = systemConfigService.getShowPlayCountList();
		if (null != playCounts && playCounts.size() > 0) {
			playConfigBean = playCounts.get(0);
		} else {
			playConfigBean = new CommonConfigBean();
		}
		request.setAttribute("playConfigBean", playConfigBean);

		/*************************************************************/
		
		
		// 清空request中原有记录
		request.setAttribute("advertPosition", null);

		String currentPage = request.getParameter("currentPage");

		String queryConditionTypeId = request.getParameter("positionTypeId");
//		String queryConditionPositionName = request.getParameter("positionName");

		int count = 0;
		int pageNB = 1;
		PageBean pageBean = null;
		int pageSize = PageConstant.PAGE_SIZE;

		if (StringUtils.isNotBlank(currentPage)) {
			pageNB = Integer.valueOf(currentPage);
		}

		Map conditionMap = new HashMap();

		try {
			
			
			
			
			if (StringUtils.isNotBlank(queryConditionTypeId)) {
				conditionMap.put("POSITION_TYPE_ID", queryConditionTypeId);
				request.setAttribute("positionTypeId", Integer.valueOf(queryConditionTypeId));
			}
			count = advertPositionService.queryCount(conditionMap);
			List<AdvertPosition> positionList = advertPositionService.page(conditionMap, (pageNB - 1) * PageConstant.PAGE_SIZE,
					pageNB * PageConstant.PAGE_SIZE);
			pageBean = PageUtils.getPageBean2(pageNB, pageSize, count, positionList);
			request.setAttribute("positionList", pageBean.getList());
			request.setAttribute("page", pageBean);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常", e);
		}

		return SUCCESS;
	}

	/**
	 * @return 添加默认素材地址
	 */
	@SuppressWarnings("unchecked")
	public String addDefaultMaterial(){
		
		try {
			String mat_type = getRequest().getParameter("m_type");
			String mat_path = getRequest().getParameter("m_path");
			String id = getRequest().getParameter("id");
			String[] strArray = { id, mat_type, mat_path };
			if (!StringUtil.isBlankArray(strArray)) {

				Map conditionMap = new HashMap();

				if (StringUtils.isNotBlank(id)) {
					conditionMap.put("ID", Integer.valueOf(id));
				}
				List<AdvertPosition> list = advertPositionService.page(conditionMap, 0, PageConstant.PAGE_SIZE);

				// List<AdvertPosition> list = advertPositionService.getAdvertPositionById(Integer.valueOf(id));

				boolean result = systemConfigService.addPositionDefaultMaterial(list.get(0), mat_type, mat_path);

				String meg = "{\"result\":" + result + ",\"flag\":" + 1 + "}";
				renderJson(meg);

			}
			String meg = "{\"flag\":" + 0 + "}";
			renderJson(meg);
			return NONE;
		} catch (Exception e) {
			log.info(e);
		}
		return NONE;
	}
	
	/**
	 * 删除默认素材
	 * @return
	 */
	public String deleteDefaultMaterial(){
		
		String positionId  = getRequest().getParameter("id");
		String m_id  = getRequest().getParameter("m_id");
		
		String strArray[] = {positionId,m_id};
		
			if(!StringUtil.isBlankArray(strArray)){
			boolean flag = systemConfigService.deletePositionDefaultMaterial(positionId, m_id);
			String meg  = "{\"flag\":"+flag+"}";
			renderJson(meg);
		}
		return NONE;
	}
	
	
	/**
	 * 查询默认素材
	 * 
	 * @return
	 */
	public String queryDefaultMaterial(){
		String id = getRequest().getParameter("id");
		List<AdvertPositionBean> beans  = systemConfigService.queryDefaultMaterial(id);
		
		String json  = JSONArray.fromObject(beans).toString();
		
		renderJson(json);
		return NONE;
	}
	
}
