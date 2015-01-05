package cn.com.avit.ads.synVoddata;

import java.text.DecimalFormat;

/**
 * ASG系统常量设置籄1�7
 * 
 * @author Zhong.Zongming
 * 
 */
public interface Constants {
    /* 全局常量 */
    /**
     * 字符集编码s
     */
    String CHAR_ENCODING = "UTF-8";
    
    /**
     * 逗号
     */
    String COMMA = ",";
    
    /**
     * VOD服务
     */
    String VOD_SERVICE_CODE = "VOD";
    /**
     * OTT服务
     */
    String OTT_SERVICE_CODE = "OTT";
    char UNDER_LINE = '_';
    /**
     * JSON 数组朄1�7大长庄1�7
     */
    int JSON_ARRAY_MAX_LENGTH = 1000;

    /**
     * 值保畄1�7�小敄1�7
     */
    DecimalFormat DECIMAL_FORMAT1 = new DecimalFormat("#.#");
    /**
     * 值保畄1�7�小敄1�7
     */
    DecimalFormat DECIMAL_FORMAT2 = new DecimalFormat("#.##");
    /* 资产 */
    /**
     * 资产-内容类型-视频
     */
    int ASSET_TRANSITION_CONTENT_TYPE_MOVIE = 1;
    /**
     * 资产-内容类型-频道
     */
    int ASSET_TRANSITION_CONTENT_TYPE_CHANNEL = 4;
    /**
     * 资产-内容类型-网页
     */
    int ASSET_TRANSITION_CONTENT_TYPE_WEB = 5;
    /**
     * 资产-内容类型-音乐
     */
    int ASSET_TRANSITION_CONTENT_TYPE_MUSIC = 7;
    /** 
     * 资产-状�1�7�1�7-正常
     */ 
    String ASSET_TRANSITION_STATE = "0";

    /* 节点 */
    /**
     * 获取节点（栏目）、资源列衄1�7 show参数-显示全部
     */
    int GET_TREE_SHOW_ALL = 0;
    /**
     * 获取节点（栏目）、资源列衄1�7 show参数-显示栏目
     */
    int GET_TREE_SHOW_CATEGORY = 1;
    /**
     * 获取节点（栏目）、资源列衄1�7 show参数-显示资源
     */
    int GET_TREE_SHOW_ASSET = 2;

    /**
     * 节点的type属�1�7�：单独的节盄1�7
     * */
    String NODE_INFO_SINGLENESS_PROGRAM = "1";
    
    /**
     * 节点的type属�1�7�：存在子节目的节目匄1�7
     * */
    String NODE_INFO_HAS_SUB_PROGRAM = "2";
    /*
     * 节点的type属�1�7�：暂无 String CATEGORY_RESOURCE_TYPE_3 = "3"; 节点的type属�1�7�：暂无 String CATEGORY_RESOURCE_TYPE_4 = "4";
     */
    /**
     * 节点的type属�1�7�：频道
     * */
    String NODE_INFO_TYPE_CHANNEL = "5";
    /**
     * 节点的type属�1�7�：第三方业劄1�7
     * */
    String NODE_INFO_TYPE_THIRD_PARTY_BUSINESS = "6";
    /**
     * 节点的type属�1�7�：增�1�7�业劄1�7
     * */
    String NODE_INFO_TYPE_ADD_VALUE_BUSINESS = "7";
    /**
     * 节点的type属�1�7�：栏目
     * */
    String NODE_INFO_TYPE_CATEGORY = "8";
    /**
     * 节点的type属�1�7�：链接
     * */
    String NODE_INFO_TYPE_LINK = "9";
    
    long TREE_TYPE_MAIN = 0;

    /* 商品 */
    /**
     * 计费类型：一次�1�7�1�7
     */
    int PO_TYPE_ON_TIME = 0;

    /**
     * 计费类型：周朄1�7
     */
    int PO_TYPE_CYCLE = 1;

    /**
     * 计费类型：免贄1�7
     */
    int PO_TYPE_FREE = 2;

    /* 频道 */
    /**
     * 频道直播未订购标评1�7
     */
    int CHANNEL_ZHI_BO_NOT_ORDER = 0;
    /**
     * 频道直播已订购标评1�7
     */
    int CHANNEL_ZHI_BO_ORDER = 1;

    /**
     * 频道时移未订购标评1�7
     */
    int CHANNEL_SHI_YI_NOT_ORDER = 0;
    /**
     * 频道时移已订购标评1�7
     */
    int CHANNEL_SHI_YI_ORDER = 1;

    /**
     * 频道回看未购买标评1�7
     */
    int CHANNEL_HUI_KAN_NOT_ORDER = 1;
    /**
     * 频道回看已购买标评1�7
     */
    int CHANNEL_HUI_KAN_ORDER = 1;

    /**
     * 会看
     */
    String DEMAND_TYPE_PLAY_BACK = "2";
    /**
     * 时移
     */
    String DEMAND_TYPE_PASSTV = "3";
    /**
     * 直播
     */
    String DEMAND_TYPE_ZHI_BO = "4";

    /**
     * 频道类型 直播
     */
    int CHANNEL_TYPE_ALL = 0;

    /**
     * 频道类型 直播
     */
    int CHANNEL_TYPE_ZHI_BO = 1;
    /**
     * 频道类型 会看
     */
    int CHANNEL_TYPE_HUI_KAN = 2;
    /**
     * 频道类型 时移
     */
    int CHANNEL_TYPE_SHI_YI = 3;

    /**
     * 频道 支持VOD直播回看
     */
    int CHANNEL_ISSTARTOVER_SUPPER = 1;
    /**
     * 频道 不支持VOD直播回看
     */
    int CHANNEL_ISSTARTOVER_NOT_SUPPER = 0;
    /**
     * 频道 支持VOD时移
     */
    int CHANNEL_ISTVANYTIME_SUPPER = 1;
    /**
     * 频道 不支持VOD时移
     */
    int CHANNEL_ISTVANYTIME_NOT_SUPPER = 0;

    /**
     * 频道 支持OTT直播回看
     */
    int CHANNEL_ISOTTSTARTOVER_SUPPER = 1;
    /**
     * 频道 不支持OTT直播回看
     */
    int CHANNEL_ISOTTSTARTOVER_NOT_SUPPER = 0;
    /**
     * 频道 支持OTT时移
     */
    int CHANNEL_ISOTTTVANYTIME_SUPPER = 1;
    /**
     * 频道 不支持OTT时移
     */
    int CHANNEL_ISOTTTVANYTIME_NOT_SUPPER = 0;

    /**
     * JSON 频道 tagName
     */
    String CHANNEL_TAG_NAME = "Channel";
    /**
     * JSON 节目单日朄1�7 tagName
     */
    String LIST_OF_DATE_TAG_NAME = "Date";
    /**
     * JSON 节目单日朄1�7 tagName
     */
    String LIST_OF_EPG_TAG_NAME = "Program";

    /** 接口********返回砄1�7****begin ****/
    /**
     * ok
     */
    String SUCCEED_CODE = "200";

    /**
     * 服务器错评1�7
     */
    String SERVER_ERROR = "500";

    /** 
     * 请求错误：参数为穄1�7
     */ 
    String PARAMETER_IS_EMPTY = "400";
    
    /** 
     * 请求错误：参数为穄1�7
     */ 
    String OBJECT_IS_NULL = "400";
    
    /** 
     * 请求错误：参数类型错评1�7
     */ 
    String PARAMETER_TYPE_ERROR = "400";
    
    /**
     * 节目不可甄1�7
     */
    String PROGRAMME_IS_ERROR = "404";
    
    String AUTH_ERROR = "00020307";
    
    
    /** 
     * 该商品已购买
     */ 
    String PRODOFFER_ALREADY_BUY = "34118301";
    
    /** 
     * 商品不存圄1�7
     */ 
    String PRODOFFER_IS_NOT_EXIST = "00020107";//"34118302";
    
    /** 
     * 产品包不存在
     */ 
    String PROD_PACKAGE_IS_NOT_EXIST = "00020103";//"34118303";
    /**
     * TOKEN效验失败
     */
    String TOKEN_VALIDATE_ERROR = "00000001";//"34118304";
    /**
     * asset信息不存圄1�7
     */
    String ASSET_IS_NOT_EXIST = "34118305";
    
    /* 公共数据* */

    /* 用户操作* */
    /** 
     *  AAA正确/成功返回砄1�7
     */ 
    String AAA_BACK_CODE_OK = "0";
    
    String AAA_BACK_CODE_OK_ALL = "0,200";
    
    /** 手机用户信息不存圄1�7 */
    String AAA_BACK_CODE_MOBILE_INFO_NOT_EXIST = "34117801";
    /** 手机用户状�1�7�非泄1�7 */
    String AAA_BACK_CODE_MOBILE_STATUS_ERROR = "34117802";
    /** 用户名或密码为空 */
    String AAA_BACK_CODE_LOGIN_NO_EXIST = "34117803";
    /** 用户名或密码错误 */ 
    String AAA_BACK_CODE_LOGIN_ERROR = "34117804";
    /** 机顶盒用户信息不存在 */ 
    String AAA_BACK_CODE_STB_NO_EXIST = "34117901";
    /** 机顶盒用户状态非泄1�7 */ 
    String AAA_BACK_CODE_STB_STATUS_ERROR = "34117902";
    /** 智能卡号为空 */ 
    String AAA_BACK_CODE_STB_ID_NO_EXIST = "34117903";
    /** PC用户信息不存圄1�7 */ 
    String AAA_BACK_CODE_PC_NO_EXIST = "34118001";
    /** PC用户状�1�7�非泄1�7 */ 
    String AAA_BACK_CODE_PC_STATUS_ERROR = "34118002";
    
    /* 用户信息* */
    
    String PRODUCT_OFFERING_NOT_EXIST = "404";

    /* 终端* */

    /** 接口********返回砄1�7******end ****/

    String SERVICE_CODE_VOD = "VOD";
    String SERVICE_CODE_OTT = "OTT";
    // 获取评论的类型，1是获取某个资源所有的评论＄1�7�某个用户所有的评论
    int COMMENT_TYPE_ASSETID = 1;
    int COMMENT_TYPE_USER = 2;

    char CHANNEL_SUB_FLAG_HAS_POWER = '1';
    char CHANNEL_SUB_FLAG_NO_POWER = '0';

    int CHANNEL_TYPE_LIVE = 1;
    int CHANNEL_TYPE_PLAYBACK = 2;
    int CHANNEL_TYPE_PAUSETV = 3;

    /**
     * 鉴权通过标识,通过丄1�7不�1�7�过丄1�7
     */
    int AUTHPROD_SUBFLG_SUCCESS = 0;
    int AUTHPROD_SUBFLG_FAIL = 1;

    String AUTHPROD_SUBFLG_SUCCESS_STRING = "0";
    String AUTHPROD_SUBFLG_FAIL_STRING = "1";

    /**
     * 热播排行
     */
    int GET_CONTENT_TYPE_HOT = 1;
    /**
     * 推荐排行
     */
    int GET_CONTENT_TYPE_RECOMMEND = 2;
    /**
     * 朄1�7新上构1�7
     */
    int GET_CONTENT_TYPE_LATEST = 3;
    /**
     * 好评排行
     */
    int GET_CONTENT_TYPE_FAVOURABLE = 4;
    /**
     * 热搜影片
     */
    int GET_CONTENT_TYPE_SEARCH_HOT = 5;

    /**
     * 节目名称导演演员全�1�7�配，输入参数写在prog_name丄1�7
     */
    String SEARCH_TYPE_ALL = "1";
    /**
     * 只对单种搜索，判断prog_name,actors,directors哪个非空，按哪个搜索，如全空，则返回扄1�7朄1�7
     */
    String SEARCH_TYPE_SINGLE = "2";

    String MMSP_CHANNEL_TYPE_LIVE = "4";
    //未包朄1�7
    String ENTITLEMENT_RESULT_FALSE="false";
    //包月
    String ENTITLEMENT_RESULT_TRUE="true";
    //未购乄1�7
    String ISOWNED_RESULT_FALSE="false";
    //购买
    String ISOWNED_RESULT_TRUE="true";
    
    String APPLICATION_ID_VOD="60010001";
    
    String TYPE_ADD="add";
    String TYPE_DELETE="delete";
    
    String AVIT_BOOKMARK="SCTV.JCFavorite";
    String LIST_OF_FAVORITE_ITEM="ListOfFavoriteItem";
    
    /**
     * 门户数据节点的type属�1�7�：1栏目＄1�7�务
     * */
    String HOMEDATA_TYPE_CATEGORY = "1";
    String HOMEDATA_TYPE_BUSINESS = "2";
    //朄1�7斄1�7 1 朄1�7烄1�7 2 好评 3
    String SEARCH_SORT_TYPE1 ="1";
    String SEARCH_SORT_TYPE2 ="2";
    String SEARCH_SORT_TYPE3 ="3";
    
    String SEARCH_CONDITION_All_AREA ="全部";
    String SEARCH_CONDITION_All_YEAR ="全部";
    String SEARCH_CONDITION_All_GENRE ="全部";
    
    String PRODUCT_TYPE_PACKAGE ="1";//产品包类垄1�7
    
    String PRODUCT_TYPE_ATOM ="0";//产品类型
    
    //产品订购错误码
    String ORDER_PROD_SUCCESS = "00000000";//成功
    String VALIDATE_ERROR = "00000001";//安全效验失败
    String INNER_ERROR = "00000002";//内部错误
    String ORDER_PROD_FALSE = "00000003";    //购买失败
    String PROD_ALREADY_BUY = "00020101"; //产品已购买
    String PRODUCT_IS_NOT_EXIST = "00020103";//产品不存在
    String PRODUCT_STATUS_IS_ERROR = "00020104";//产品状态非法
    String USER_IS_NOT_EXIST = "00000010";//用户不存在
    String USER_STATUS_IS_ERROR = "00020106";//用户状态非法
       
    //AAA返回错误码
    String ORDER_PROD_AAA_RETURN_OK = "0";//购买成功(AAA)
    String PROD_ALREADY_BUY_AAA = "34118301";//产品已购买(AAA)
    String PRODUCT_IS_NOT_EXIST_AAA = "34117102";//产品不存在(AAA)
    String PRODUCT_STATUS_IS_ERROR_AAA = "34117110";//产品状态非法(AAA)
    String USER_IS_NOT_EXIST_AAA = "34118705";//用户不存在(AAA)
    String USER_STATUS_IS_ERROR_AAA = "34118705";//用户状态非法(AAA)
    String CONTENT_IS_NOT_EXIST_AAA = "34118201";//内容不存在(AAA)
    String CONTENT_STATUS_IS_ERROR_AAA = "34118202";//内容状态非法(AAA)
    
    //OTT点播错误码
    String USER_IS_NOT_EXIST_OTT_PLAY = "00000010";//用户不存在
    String USER_STATUS_IS_ERROR_OTT_PLAY = "00020306";//用户状态非法
    String CONTENT_IS_NOT_EXIST = "00020303";//内容不存在
    String  CONTENT_IS_NOT_EXIST_MSG= "内容不存在";//内容不存在
    String CONTENT_STATUS_IS_ERROR = "00020304";//内容状态非法
    String PRODUCT_IS_NOT_EXIST_OTT_PLAY = "00020301";//产品不存在
    String PRODUCT_STATUS_IS_ERROR_OTT_PLAY = "00020302";//产品状态非法


	String SYSTEM_VALIDATE_ERROR = "00000002";

	String GET_TVN_ERROR = "00000010";

	String PARAMETER_ERROR = "10000200";

    
    
    
    
}
