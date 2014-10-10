package com.dvnchina.advertDelivery.constant;

import com.dvnchina.advertDelivery.utils.config.ConfigureProperties;

public class CPSConstant {
	
	private static ConfigureProperties config = ConfigureProperties.getInstance();
	
	/**
	 * 加载同步过来的图片地址前
	 */
	//advert.sync.category.resoucePath=http://192.168.12.93
	public static final String RESOURCE_SERVER_PATH = config.get("advert.sync.category.resoucePath");
	/**
	 * 加载从cps中同步数据的接口地址
	 */
	//advert.sync.category.path=http://localhost:12010/iscp/userValidateServlet
	public static final String SYNC_CPS_CATEGORY_PATH = config.get("advert.sync.category.path");
	
	/**
	 * 有效模板
	 */
	public static final String EFFECTIVE_TEMPLATE = "effectiveTemplate";
	
	/**
	 * 无效模板
	 */
	public static final String INVALIDS_TEMPLATE="invalidsTemplate";
	/**
	 * 无效模板-无广告位
	 */
	public static final String INVALIDS_TEMPLATE_NO_POSITION="invalidsTemplate_no_position";
	/**
	 * 无效模板-无对应模板
	 */
	public static final String INVALIDS_TEMPLATE_NO_TEMPLATE="invalidsTemplate_no_template";
	/**
	 * 新建CategoryTemplateBean
	 */
	public static final Integer CATEGORY_TEMPLATE_STATE_CREATE = 0;
	/**
	 * 修改CategoryTemplateBean
	 */
	public static final Integer CATEGORY_TEMPLATE_STATE_MODIFY = 1;
	/**
	 * 删除CategoryTemplateBean
	 */
	public static final Integer CATEGORY_TEMPLATE_STATE_DELETE = 2;
	
	/**
	 * type:类型 8:分类 7：业务 6：应用 5：频道 4： 3：产品 2：资源包  1：资源 
	 * 从cps中同步数据类型type=8
	 */
	public static final Integer CATEGORY_TYPE_SYC_CPS = 8;
	
	/**
	 * 业务
	 */
	public static final Integer BUSINESS_TYPE_SYC_CPS = 7;
	
	/**
	 * 应用
	 */
	public static final Integer APPLICATION_TYPE_SYC_CPS = 6;
	
	/**
	 * 频道
	 */
	public static final Integer CHANNEL_TYPE_SYC_CPS = 5;
	
	/**
	 * 产品
	 */
	public static final Integer PRODUCT_TYPE_SYC_CPS = 4;
	
	/**
	 * 资源包
	 */
	public static final Integer ASSET_PACKAGE_TYPE_SYC_CPS = 2;
	
	/**
	 * 资源
	 */
	public static final Integer ASSET_TYPE_SYC_CPS = 1;
	
	
	/**
	 * 当前页
	 */
	public static final Integer CURRENT_PAGE = 1;

	/**
	 * 每页显示记录数
	 */
	public static final Integer PAGE_SIZE = 5;
	/**
	 * 每次解析50条数据
	 */
	public static final Integer PARSE_SIZE = 2;
	
	/**
	 * 有效节点
	 */
	public static final String EFFECTIVE_CATEGORY = "effectiveCategory";
	/**
	 * 无效节点
	 */
	public static final String INVALIDS_CATEGORY="invalidsCategory";
	/**
	 * 无效节点-模板无广告位
	 */
	public static final String INVALIDS_CATEGORY_TEMPLATE_NO_POSITION="invalidsCategory_template_no_position";
	/**
	 * 无效节点-模板无对应模板
	 */
	public static final String INVALIDS_CATEGORY_TEMPLATE_NO_TEMPLATE="invalidsCategory_template_no_template";
	
	/**
	 * 待删除状态
	 */
	public static final Integer WAIT_BE_DELETED_CATEGORY = 0;
	/**
	 * 更新状态
	 */
	public static final Integer WAIT_BE_UPDATE_CATEGORY = 1;
	
	/**
	 * 投放平台 PORTAL
	 */
	public static final String ADVERT_POSITION_PLATFORM = "2";
	
	
	
	
}

















