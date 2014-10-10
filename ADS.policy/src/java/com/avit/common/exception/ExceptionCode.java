/**   
 * 
 * @Title: ExceptionCode.java 
 * @Package com.avit.portal.common.exception
 * @Description:  ExceptionCode 异常错误码类
 * @author  zhayong   
 * @email   zhayong@intranet.com  
 * @date 2012-5-16 上午10:56:28 
 * @version V1.0
 * Copyright (c) 2012 AVIT Company,Inc. All Rights Reserved.
 * 
 */
package com.avit.common.exception;

/**
 * @ClassName: ExceptionCode 
 * @Description: ExceptionCode 异常错误码类
 *
 */
public class ExceptionCode {

	/**
	 * 服务器异常
	 */
	public static final int SERVER_ERROR = 13000500;
	
	/**
	 * 数据库异常
	 */
	public static final int DATABASE_ACCESS_ERROR = 13000501;
	
	/**
	 * 业务处理异常
	 */
	public static final int SERVICE_BUSINESS_EXCEPTION = 13000502;
	
	/**
	 * IO异常
	 */
	public static final int IO_EXCEPTION = 13000503;
	
	/**
	 * 解压异常
	 */
	public static final int COMPRESSION_ERROR = 130005001;
}
