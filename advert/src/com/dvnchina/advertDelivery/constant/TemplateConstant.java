package com.dvnchina.advertDelivery.constant;

import com.dvnchina.advertDelivery.utils.ConfigureProperties;

/**
 * 模板常量类
 */
public class TemplateConstant {
	
	public static String ENCODING=ConfigureProperties.getInstance().get("templateEncoding");

	/** 模板存放文件夹的后缀 */
	public static String MAIN = "_main";
	
	/** 导入模板时候存放zip文件的路径 */
	public static String SAVEZIPROOT = "loadTemplateManage/temp";
	
	public static String SAVEEFFROOT = "loadTemplateManage/effectDrawing";
	
	public static String SAVETEMPLATEROOT = "loadTemplateManage";
	
	public static String SAVEIMAGESROOT = "loadTemplateManage/images";
	
	public static String SAVEIHTMLROOT = "loadTemplateManage/html";
	
	public static String SAVEBACKGROUNDROOT = "loadTemplateManage/backGround";
	
	public static String SAVESCRIPTROOT = "loadTemplateManage/scripts";
	
	public static String SAVESTYLEROOT = "loadTemplateManage/styles";
	
	public static String SAVEDESCJSROOT = "loadTemplateManage/descJs";
	
	public static String EFFECTDRAWING = "effectDrawing";
	
	public static String DESCJS = "descJs";
	
	public static String HTML = "html";
	
	public static String SCRIPTS = "scripts";
	
	public static String STYLES = "styles";
	
	public static String IMAGES = "images";
	
	public static String BACKGROUND = "backGround";
	
	public static String TEMP = "temp";
	
	public static String TEMPLATE = "template";
	
	public static String SVN = ".svn";
	
	public static String DB = ".db";
	
	public static String HTMLEND = ".html";
	
	public static String HTMEND = ".htm";
	
	/** 资源服务器连接用 */
	public static String SERVER = "server";
	
	public static String UTIL = "util";
	
	/** 模板图片存放目录 */
	public static String SENDIMAGESPATH = "images/template";
	
	/** 模板js存放目录 */
	public static String SENDJSPATH = "js/template";
	
	/** 模板描述文件js存放目录 */
	public static String SENDDESCJSPATH = "descJs/template";
	
	/** 模板css存放目录 */
	public static String SENDCSSPATH = "css/template";
	
	/** 模板html存放目录 */
	public static String SENDHTMLPATH = "html/template";
	
	public static String IMAGE = "images";
	
	public static String ROOTBACKGROUND = "/backGround/";
	
	public static String ROOTIMAGES = "/images/";
	
	/** 模板脚本目录 */
	public static String ROOTSCRIPTS = "/scripts/";
	
	/** 模板样式目录 */
	public static String ROOTSTYLES = "/styles/";
	
	public static String ROOTEFFECTDRAWING = "/effectDrawing/";
	
	/** 模板html文件目录 */
	public static String ROOTHTML = "/html/";
	
	
}
