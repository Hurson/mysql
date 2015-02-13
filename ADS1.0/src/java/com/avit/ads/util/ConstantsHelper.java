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
    public static final int PRECISION_TYPE_PASSED_CHANNEL = 5;
    
    /** 区域编码的拆分符号. */
    public static final String AREA_SPLIT__SIGN = ",";
    public static final String CHANNEL_ID_SPLIT__SIGN = ",";
    public static final String CACHE_DIVIDE_BEGIN = "0";
    public static final String CACHE_DIVIDE_END = "235959";
	
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
    public static final int A_HOUR_CONTAINS_MILLISECONDS = 60000;//86400000;
    
    public static final String UNT_UPDATE_ADPIC="/adimage?update&templateid=a";
    public static final String UNT_UPDATE_ADCONFIG="/adconfigurationfile?update&templateid=a";
    public static final String UNT_UPDATE_ADLINGK="";
    
    /** UNT更新使用模板*/
    public static final String UNT_UPDATE_TEMPLATE="a";
    /** UNT更新配置文件路径前缀*/
    public static final String UNT_UPDATE_PATH_PREFIX="adv://";
    
    public static final int OCG_UDP_PORT = 30005;
    public static final int OCG_UDP_PORT_RECEIVE = 30006;
    
    public static final int OCG_UDP_RET_MSG_MAX_LENGTH = 212; 
    
    
    public static final int REALTIME_UNT_MESSAGE_WEATHER= 1; 
    public static final int REALTIME_UNT_MESSAGE_MSUBTITLE= 2; 
    public static final int REALTIME_UNT_MESSAGE_ADCONFIG= 3; 
    public static final int REALTIME_UNT_MESSAGE_ADIMAGE= 4; 
    public static final int REALTIME_UNT_MESSAGE_STB= 5; 
    public static final int REALTIME_UNT_MESSAGE_CHANNEL_SUBTITLE= 6; 
    public static final int REALTIME_UNT_MESSAGE_CHANNE= 7; 
    
    /** 主频点，对应原UI路径*/
    public static final String MAIN_CHANNEL = "1";
    /** 所有频点，对应原UNT路径*/
    public static final String ALL_CHANNEL = "2";
    
    /**  OCG使用参数 */
    public static final String UNT_TYPE = "1";
    public static final String UI_TYPE = "2";
    public static final String RECOMMEND_TYPE = "3";
    
    /**
     * UI更新操作类型
     */
    public static final String UI_PLAY = "play";
    public static final String UI_DEL = "del";
    public static final String UI_GET = "get";
    
    public static final String TS_FILE_SUFFIX = ".ts";
    public static final String DEST_FILE_PATH = "ads_generated_ts_store_path";
    public static final String LOG_FILE_PATH = "ads_logger_path";
    
    /** UI更新类型 
	    1：开机图片initPic.iframe更新
	    2：栏目配置文件，dataDefine.dat更新 
	    3：非实时广告资源advResource.dat更新
	    4：HTML文件组htmlData.dat更新 
	    5：开机视频initVideo.ts更新
     
    public static final int UI_UPDATE_TYPE_PIC = 1;
    public static final int UI_UPDATE_TYPE_ADV = 3;
    public static final int UI_UPDATE_TYPE_VIDEO = 5;
    
    
    public static final String UI_UPDATE_PATH_PIC = "initPic-c.iframe";
    public static final String UI_UPDATE_PATH_PIC_SD = "initPic-a.iframe";
    public static final String UI_UPDATE_PATH_ADV = "advResource-c.dat";
    public static final String UI_UPDATE_PATH_VIDEO = "initVideo-c.ts";
    
    */
    
}
