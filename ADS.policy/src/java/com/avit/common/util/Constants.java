package com.avit.common.util;
/**
 * @author Weimmy

 * @date:2011-8-8
 * @version :1.0
 * 
 */
public class Constants {

	public static final String USERINFO ="userInfo";
	public static final String LOGINDATE ="loginDate";
	public static final String MENULIST ="menuList";
	public static final String APPLICATIONLIST ="applicationList";
	public static final String VERSION ="version";
	
	public static final String LOGOUTACTION="/logout.action";
	public static final String LOGINACTION="/login.action";
	public static final String TIMEOUTACTION="/timeout.action";
	public static final String MENUACTION="/menu.action";
	public static final String HEADACTION="/head.action";
	public static final String FOOTACTION="/foot.action";
	public static final String BODYACTION="/body.action";
	
	//SRM自发现接口。SM接收的数据，不用登入
	public static final String ERMAUTODISCOVER="/erm.action";
	public static final String ODRMAUTODISCOVER="/odrm.action";
	//登入信息 信息ID
	public static final String USER_ONLINEID="onlineId";
	public static final int ORERMAXONLINE = 100001;  //超过最大登陆限制
	public static final int USERNAMEORPASSWORDERROR = 100002;  //用户名或密码不正确
	
	public static final String RESOURCE_SERVICE_CODE_VOD = "VOD";//面向资源的服务code
	
	
	//---------操作日志==操作结果
	
	/**
	 * 操作日志-操作结果-失败
	 */
	public static final String OPERATELOG_RESULT_ERROR="operatelog.actionresult0";
	
	/**
	 * 操作日志-操作结果-成功
	 */
	public static final String OPERATELOG_RESULT_OK="operatelog.actionresult1";
	
	//---------操作日志==操作码
	
	/**
	 * 操作码-导出-导出ADI操作
	 */
	//public static final String OPERATELOG_CODE_EXPORT_ADI="operatelog.action.export.content.adi";
	
	/**
	 * 操作码-导入节目单操作
	 */
	//public static final String OPERATELOG_CODE_IMPORT_PROGRAM="operatelog.action.import.program";
	
	/**
	 * 操作码-预览视频
	 */
	//public static final String OPERATELOG_CODE_PRE_VIEW_MOVIE="operatelog.action.pre.view.movie";
	
	/**
	 * 操作码-查询-查询操作
	 */
	//public static final String OPERATELOG_CODE_QUERY="operatelog.action.query";
	
	/**
	 * 操作码-详情-查看详情操作
	 */
	//public static final String OPERATELOG_CODE_DETAIL="operatelog.action.detail";
	
	/**
	 * 操作码-审核驳回-审核操作
	 */
	//public static final String OPERATELOG_CODE_AUDIT_NOT_PASS="operatelog.action.audit.not.pass";
	/**
	 * 操作码-审核通过-审核操作
	 */
	//public static final String OPERATELOG_CODE_AUDIT_PASS="operatelog.action.audit.pass";
	
	/**
	 * 操作码-添加-审核操作
	 */
	public static final String OPERATELOG_CODE_ADD="operatelog.action.add";
	
	/**
	 * 操作码-修改-审核操作
	 */
	public static final String OPERATELOG_CODE_UPDATE="operatelog.action.update";
	
	/**
	 * 操作码-删除-审核操作
	 */
	public static final String OPERATELOG_CODE_DELETE="operatelog.action.delete";

	/**
	 * 操作码-暂停-审核操作
	 */
	//public static final String OPERATELOG_CODE_PAUSE="operatelog.action.pause";
	
	/**
	 * 操作码-启用-审核操作
	 */
	//public static final String OPERATELOG_CODE_INVOK="operatelog.action.invok";
	
	/**
	 * 操作码-注销-审核操作
	 */
	//public static final String OPERATELOG_CODE_LOGINOUT="operatelog.action.loginout";
	
	/**
	 * 操作码-批量驳回操作
	 */
	//public static final String OPERATELOG_CODE_BATH_NOT_PASS="operatelog.action.bathnotpass";
	
	/**
	 * 操作码-批量通过操作
	 */
	//public static final String OPERATELOG_CODE_BATH_PASS="operatelog.action.bathpass";
	
	/**
	 * 操作码-批量注销操作
	 */
	//public static final String OPERATELOG_CODE_BATH_LOGINOUT="operatelog.action.bathloginout";
	
	/**
	 * 操作码-批量取消采集操作
	 */
	//public static final String OPERATELOG_CODE_BATH_CANCEL_CAPTURET="operatelog.action.bathcancelcapturet";
	
	/**
	 * 操作码-批量开始采集操作
	 */
	//public static final String OPERATELOG_CODE_BATH_START_CAPTURET="operatelog.action.bathstartcapturet";
	
	/**
	 * 操作码-批量停止采集操作
	 */
	//public static final String OPERATELOG_CODE_BATH_STOP_CAPTURET="operatelog.action.bathstopcapturet";
	
	/**
	 * 操作码-批量暂停操作
	 */
	//public static final String OPERATELOG_CODE_BATH_PAUSE="operatelog.action.bathpause";
	
	/**
	 * 操作码-批量启用操作
	 */
	//public static final String OPERATELOG_CODE_BATH_INVOC="operatelog.action.bathinvoc";
	
	/**
	 * 操作码-批量激活操作
	 */
	//public static final String OPERATELOG_CODE_BATH_EFFECTIVE="operatelog.action.batheffective";

	/**
	 * 操作码-批量注入操作
	 */
	//public static final String OPERATELOG_CODE_BATH_TO_DISTRIBUTE_CONTENT="operatelog.action.bathtodistributecontent";
	
	/**
	 * 操作码-批量取消注入操作
	 */
	//public static final String OPERATELOG_CODE_BATH_TO_CANCEL_DISTRIBUTE_CONTENT="operatelog.action.bathtocanceldistributecontent";
	
	/**
	 * 操作码-批量重新发送同步数据操作
	 */
	//public static final String OPERATELOG_CODE_BATH_TO_SEND="operatelog.action.bathsend";
	
	/**
	 * 操作码-重新注入操作
	 */
	//public static final String OPERATELOG_CODE_RE_DISTRIBUTE="operatelog.action.redistribute";
	
	/**
	 * 操作码-取消注入操作
	 */
	//public static final String OPERATELOG_CODE_CANCEL_DISTRIBUTE="operatelog.action.canceldistribute";
	
	
	/**
	 * 操作码-批量删除操作
	 */
	//public static final String OPERATELOG_CODE_BATH_DELETE="operatelog.action.bathdelete";
}
