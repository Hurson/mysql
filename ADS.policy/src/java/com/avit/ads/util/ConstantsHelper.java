package com.avit.ads.util;

/**
 * Created by IntelliJ IDEA.
 * User: Hemeijin
 * Date: 2011-7-19
 * Time: 17:37:56
 * To change this template use File | Settings | File Templates.
 */
public class ConstantsHelper {
	/**
	 * 私有构造函数，防止被实例化
	 */
	private ConstantsHelper(){
		
	}
    public static final String CRLF = "\r\n";
    public static final String COMMA = ",";
    public static final String SEMICOLON = ";";
    public static final String EQUATE = "=";
    public static final String COLON = ":";
    public static final String FIRST_ROW_CONTENT = "firstRowContent";  //报文的第一行数据，一般接口方法名称和协议包含在其中
    public static final String CSEQ = "CSeq";  //CSep: 896
    public static final String REQUIRE = "Require";  //Require: com.comcast.ngod.s3
    public static final String ON_DEMAND_SESSION_ID = "OnDemandSessionId";  //OnDemandSessionId: be074250cc5a11d98cd50800200c9a66
    public static final String SOP_GROUP = "SopGroup";  //SopGroup: boston.spg1 ; SopGroup: boston.spg2
    public static final String TRANSPORT = "Transport";  //Transport:MP2T/DVBC/UDP/;unicast;client=00AF123456DE;bandwidth=2920263;destination=1.1.1.1;client_port=23,
    public static final String SESSION_GROUP = "SessionGroup";  //SessionGroup: SM1
    public static final String START_POINT = "StartPoint";  //StartPoint: 1 3.0
    public static final String POLICY = "Policy";  //Policy: priority=1
    public static final String INBANDMARKER = "InbandMarker";  //InbandMarker:type=4;pidType=A;pidValue=01EE;dataType=T;insertDuration=10000;data=400200303030
    public static final String CONTENT_TYPE = "Content-Type";  //Content-type: application/sdp
    public static final String CONTENT_LENGTH = "Content-Length";  //Content-length: 168
    public static final String SDP = "sdp";  //0=- be074250cc5a11d98cd50800200c9a662890842807 IN IP4 10.47.16.5 s=  t=0 0 a=X-playlist-item: comcast.com abcd1234567890123456 1.0-6.0 c=IN IP4 0.0.0.0 m=video 0 udp MP2T
    public static final String UDP = "UDP";  //Transport: MP2T/DVBC/UDP/;
    public static final String QAM = "QAM";  //Transport: MP2T/DVBC/QAM/;
    public static final String PURCHASE_TOKEN = "purchaseToken";  //purchaseToken=c0c2d8b0cc8211d98211d98cd508002009a66;
    public static final String SERVER_ID = "serverID";  //serverID=1.1.1.1 RTSP/1.0
    public static final String VOLUME = "Volume";
    public static final String APPLICATION_SDP = "application/sdp";
    public static final String TEXT_PARAMETERS = "text/parameters";
    
    public static final String CLIENTSESSIONID = "ClientSessionId";
    
    public static final String SPRIT = "/";//斜杠
    public static final String SPACE = " ";//一个空格
    public static final String  PLUS = "+";//加号
    public static final String PROTOCOL = "rtsp://";
    public static final String PROTOCOL_VERSION = "RTSP/1.0";
    public static final String MP2T_DVBC_UDP = "MP2T/DVBC/UDP";
    public static final String UNICAST = "unicast";
    public static final String CLIENT = "client";
    public static final String BANDWIDTH = "bandwidth";
    public static final String DESTINATION = "destination";
    public static final String CLIENT_PORT = "client_port";
    public static final String SOP_NAME = "sop_name";
    public static final String SOPGROUP_NAME = "sop_group";
    public static final String SESSION = "Session";
    public static final String SOURCE="source";
    public static final String SERVER_PORT="server_port";
    
    /** 精准类型*/
    public static final int PRECISION_TYPE_PRODUCTION = 1;
    public static final int PRECISION_TYPE_MOVES_KEYWORDS = 2;
    public static final int PRECISION_TYPE_USER_SORT = 3; 
    public static final int PRECISION_TYPE_MOVES_SORT = 4;
    public static final int PRECISION_TYPE_LOOKBACK_CHANNEL = 6;
    public static final int PRECISION_TYPE_PLAYBACK_CHANNEL = 5;
    public static final int PRECISION_TYPE_PASSED_ASSET = 8;
    public static final int PRECISION_TYPE_USER_TRADE = 12;
    public static final int PRECISION_TYPE_USER_LEVELS = 13;
    public static final int PRECISION_TYPE_USER_LOCATION = 11;
    
    public static final String PRECISION_TVN_EXPRESSION_EQUAL = "1";
    public static final String PRECISION_TVN_EXPRESSION_UNEQUAL = "0";
    
    /** 区域编码的拆分符号. */
    public static final String AREA_SPLIT__SIGN = ",";
    public static final String CHANNEL_ID_SPLIT__SIGN = ",";
    public static final String CACHE_DIVIDE_BEGIN = "00:00:00";
    public static final String CACHE_DIVIDE_END = "23:59:59";
	
    /** 广告投放状态*/
    /** 未投放*/
    public static final int UN_PLAY_AD = 0;
    /** 已投放*/
    public static final int PLAYED_SUCCESSFULLY = 1;
    /** 投放失败*/
    public static final int PLAYED_FAILED = 2;
    /** 但对象的资源类型 为图片（静态/动态）或者是视频*/
    public static final String SIGN_CONTENT_TYPE = "0";
    
    //外部系统名
    public static final String SYSTEM_OCG = "OCG";
    public static final String SYSTEM_CPS = "CPS";
    /** 区域编码的拆分符号. */
    public static final String SPLIT__SIGN = ",";
	
    public static final String CHANNEL_SPLIT__SIGN = ",";
    public static final String CONTENT_TYPE_START="1";
    public static final String CONTENT_TYPE_LOOP="2";
    public static final String CONTENT_TYPE_MESSAGE="3";
    public static final String START_CONTENT_VIDEO_TYPE="initVideo";
    public static final String START_CONTENT_PIC_TYPE="initPic";
    public static final String UNT_UPDATE_FLAG_MESSAGE ="1";
    public static final String UNT_UPDATE_FLAG_LINK ="2";
    public static final String UNT_UPDATE_FLAG_CONFIG ="3";
    
    public static final String FILE_UPLOAD_TYPE_LOCAL ="1";
    public static final String FILE_UPLOAD_TYPE_HTTP ="2";
    public static final String FILE_UPLOAD_TYPE_VIDEO ="3";
    
    public static final String MP4_FILE_POSTFIX = ".mp4";
    public static final String RESPONSE_CONTENT_TYPE_VIDEO = "5";
    public static final String RESPONSE_CONTENT_TYPE_PICTURE = "4";
    
    public static final String HIGH_DEFINITION_VIDEO = "HD";
    public static final String STANDARD_DEFINITION_VIDEO = "SD";
    public static final String OBLIQUE_LINE = "/";
    
    public static final int SUCCESSFUL_MESSAGE_CODE = 0;
    /** 24小时含的毫秒数*/
    public static final int A_HOUR_CONTAINS_MILLISECONDS = 1200000;
    public static final int TF_HOUR_CONTAINS_MILLISECONDS = 86400000;
    /** 问卷类型 0:喜好*/
    public static final String SURVEY_TYPE_FAVORITE = "0";
    /** 问卷类型 1:商用*/
    public static final String SURVEY_TYPE_BUSINESS = "1";
    
    /** 产品类型为：回看*/
    public static final String LOOKBACK_TYPE = "lookback";
    public static final String PLAYBACK_TYPE_FALG = "playback";
    
    public static final String ASSET_LOOKBACK_NAME = "节目回看";
    public static final String CHANNEL_LOOKBACK_NAME = "频道回看";
    /** 频道类型：回放*/
    public static final int PLAYBACK_TYPE = 1;
    /** 默认素材类型*/
    public static final int DEFAULT_RESOURCE_TYPE = 1;
    /** 素材分类 0：图片、1：视频、2：文字*/
    public static final int DEFAULT_RESOURCE_TYPE_IMAGE = 0;
    public static final int DEFAULT_RESOURCE_TYPE_VIDEO = 1;
    public static final int DEFAULT_RESOURCE_TYPE_TEXT = 2;
    
    public static final String PRECISONAREAFLAG = "AREA";
}
