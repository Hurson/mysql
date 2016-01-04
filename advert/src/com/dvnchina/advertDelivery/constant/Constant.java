package com.dvnchina.advertDelivery.constant;

public class Constant {
	/** 可用状态 **/
	public static final char AVAILABLE = '0';

	/** 删除状态 **/
	public static final char DELETE = '1';

	/** 有效 */
	public static final int VALID = 0;

	/** 失效 */
	public static final int INVALID = 1;

	/** 图片类型 **/
	// public static final char IMAGE_TYPE = '0';

	/** 视频类型 **/
	// public static final char VIDEO_TYPE = '1';

	/** 素材图片类型 **/
	public static final int IMAGE = 0;

	/** 素材视频类型 **/
	public static final int VIDEO = 1;

	/** 素材文字类型 **/
	public static final int WRITING = 2;

	/** 素材调查问卷类型 **/
	public static final int QUESTIONNAIRE = 3;
	
	/** 素材ZIP类型 **/
	public static final int ZIP = 4;

	/** 图片名称前缀 **/
	public static final String IMAGE_PREFIX = "pic";

	/** 视频名称前缀 **/
	public static final String VIDEO_PREFIX = "video";

	/** 文字名称前缀 **/
	public static final String WRITING_PREFIX = "txt";

	/** 调查问卷名称前缀 */
	public static final String QUESTIONNAIRE_PREFIX = "que";

	/** 开机图片名称前缀 */
	public static final String INIT_IMAGE_PREFIX = "initPic";

	/** 开机视频名称前缀 */
	public static final String INIT_VIDEO_PREFIX = "initVideo";

	/** WEB页面类型调查问卷 **/
	public static final char WEB_QUESTIONNAIRE = '0';

	/** 文字类型调查问卷 **/
	public static final char WRITING_QUESTIONNAIRE = '1';

	/** 播出单相关，开机素材类型 */
	public static final String INIT_MATERIAL_TYPE = "1";

	/** 播出单相关，多图素材类型 */
	public static final String MULTI_IMAGE_MATERIAL_TYPE = "2";

	/** 播出单相关，字幕素材类型 */
	public static final String WRITING_MATERIAL_TYPE = "3";

	/** 订单待审核状态 **/
	public static final String ORDER_PENDING_CHECK = "0";

	/** 订单修改待审核状态 **/
	public static final String ORDER_PENDING_CHECK_UPDATE = "1";

	/** 订单删除待审核状态 **/
	public static final String ORDER_PENDING_CHECK_DELETE = "2";

	/** 订单审核未通过状态 **/
	public static final String ORDER_CHECK_NOT_PASS = "3";

	/** 订单修改审核不通过状态 **/
	public static final String ORDER_CHECK_NOT_PASS_UPDATE = "4";

	/** 订单删除审核不通过状态 **/
	public static final String ORDER_CHECK_NOT_PASS_DELETE = "5";

	/** 订单已发布状态 **/
	public static final String ORDER_PUBLISHED = "6";

	/** 订单执行完毕状态 **/
	public static final String ORDER_IS_FINISHED = "7";
	
	/** 订单投放失败状态 **/
	public static final String ORDER_IS_FAIL = "9";

	/** 投放式订单 */
	public static final int PUT_IN_ORDER = 0;

	/** 请求式订单 */
	public static final int REQUEST_ORDER = 1;

	/**
	 * 广告位相关 投放平台 OCG
	 */
	public static final int ADVERT_POSITION_PLATFORM_OCG = 0;
	/**
	 * 广告位相关 投放平台 UNT
	 */
	public static final int ADVERT_POSITION_PLATFORM_UNT = 1;
	/**
	 * 广告位相关 不轮询
	 */
	public static final int ADVERT_POSITION_NOT_POLLING = 0;
	/**
	 * 广告位相关 轮询
	 */
	public static final int ADVERT_POSITION_IS_POLLING = 1;

	/**
	 * 广告位相关 投放方式 投放式
	 */
	public static final int ADVERT_POSITION_WAY_ADVERT = 0;
	/**
	 * 广告位相关 投放方式 请求式
	 */
	public static final int ADVERT_POSITION_WAY_REQUEST = 1;

	/**
	 * 广告位相关 标清
	 */
	public static final int ADVERT_POSITION_NOT_HD = 0;
	/**
	 * 广告位相关 高清
	 */
	public static final int ADVERT_POSITION_HD = 1;
	/**
	 * 广告位相关 既有高清又有标清
	 */
	public static final int ADVERT_POSITION_HD_COMPATI = 2;
	/**
	 * 广告位相关 不叠加
	 */
	public static final int ADVERT_POSITION_NOT_OVERLYING = 0;
	/**
	 * 广告位相关 叠加
	 */
	public static final int ADVERT_POSITION_IS_OVERLYING = 1;
	/**
	 * 合同相关 待审核
	 */
	public static final int CONTRACT_AUDIT_STATUS_WAIT = 0;
	/**
	 * 合同相关 审核通过
	 */
	public static final int CONTRACT_AUDIT_STATUS_PASS = 1;
	/**
	 * 合同相关 审核不通过
	 */
	public static final int CONTRACT_AUDIT_STATUS_NO_PASS = 2;
	/**
	 * 合同相关 下线状态
	 */
	public static final int CONTRACT_AUDIT_STATUS_OFFLINE = 3;
	/**
	 * 合同相关存储过程执行成功
	 */
	public static final int CALLABLE_EXECUTE_SUCCESS = 0;
	/**
	 * 合同相关存储过程执行失败
	 */
	public static final int CALLABLE_EXECURE_ERROR=1;
	/**
	 * 广告位被占用
	 */
	public static final String POSITION_OCCUPY_STATUS = "1";
	
	/** 操作成功 */
	public static final int OPERATE_SECCESS = 0;
	/** 操作失败 */
	public static final int OPERATE_FAIL = 1;
	/** 对象分割符 */
	public static final String OPERATE_SEPARATE="|";
	//模块名称
	public static final String OPERATE_MODULE_ROLE="角色管理";
	public static final String OPERATE_MODULE_USER="用户管理";
	public static final String OPERATE_MODULE_USER_RANK= "用户级别维护";
	public static final String OPERATE_MODULE_USER_TRADE= "用户行业维护";
	public static final String OPERATE_MODULE_DEF_RESOURCE="默认素材配置";
	public static final String OPERATE_MODULE_CUSTOMER="广告商管理";
	public static final String OPERATE_MODULE_CONTRACT="合同管理";
	public static final String OPERATE_MODULE_RESOURCE="素材管理";
	public static final String OPERATE_MODULE_DRESOURCE="无线素材管理";
	public static final String OPERATE_MODULE_PLOY="投放策略管理";
	public static final String OPERATE_MODULE_DPLOY="无线策略管理";
	public static final String OPERATE_MODULE_ORDER="订单管理";
	public static final String OPERATE_MODULE_DORDER="无线订单管理";
	public static final String OPERATE_MODULE_CHANNEL_GROUP="频道组管理";
	public static final String OPERATE_MODULE_DCHANNEL_GROUP="无线频道组管理";
	
	/** 审核订单关联类型 */
	public static final int AUDIT_RELATION_TYPE_ORDER = 1;
	/** 审核无线订单关联类型 */
	public static final int AUDIT_RELATION_TYPE_DORDER = 2;
	/** 审核通过 */
	public static final int AUDIT_LOG_STATUS_PASS = 0;
	/** 审核不通过 */
	public static final int AUDIT_LOG_STATUS_NOT_PASS = 1;
	/** 修改审核通过 */
	public static final int AUDIT_LOG_STATUS_UPDATE_PASS = 2;
	/** 修改审核不通过 */
	public static final int AUDIT_LOG_STATUS_UPDATE_NOT_PASS = 3;
	/** 删除审核通过 */
	public static final int AUDIT_LOG_STATUS_DELETE_PASS = 4;
	/** 删除审核不通过 */
	public static final int AUDIT_LOG_STATUS_DELETE_NOT_PASS = 5;
	
	/** 双向实时广告 */
	public static final int POSITION_TYPE_TWO_REAL_TIME = 0;
	/** 双向实时请求广告 */ 
	public static final int POSITION_TYPE_TWO_REAL_TIME_REQ = 1;
	/** 单向实时广告 */ 
	public static final int POSITION_TYPE_ONE_REAL_TIME = 2;
	/** 单向非实时广告 */ 
	public static final int POSITION_TYPE_ONE_NOT_REAL_TIME = 3;
	/**单向数据广播*/
	public static final int POSITION_TYPE_ONE_DATA_BROADCASTING = 4;

	
}
