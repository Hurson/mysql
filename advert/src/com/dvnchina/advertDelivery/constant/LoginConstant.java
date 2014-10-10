package com.dvnchina.advertDelivery.constant;

/**
 * @author Administrator
 *
 */
public class LoginConstant {
	
	/**
	 * 用户不存在
	 */
	public static final int USER_ERROR = 1;
	
	/**
	 * 密码错误
	 */
	public static final int PASSWD_ERROR = 2;
	
	/**
	 * 用户已禁用
	 */
	public static final int USER_DIS_ABLE = 3;
	
	/**
	 * 用户认证通过
	 */
	public static final int USER_SUCCESS = 4;
	
	/**
	 * 超级管理员
	 */
	public static  final int ROLE_SUPER_ADMIN = 0;
	
	/**
	 * 运营商
	 */
	public static final int ROLE_BUSINESS_ADMIN = 1;
	
	/**
	 * 广告商
	 */
	public static  final int ROLE_ADVERTISER = 2;
	
	/**
	 * 存放cookie  的用户的Id
	 */
	public static  final String COOKIE_USER_ID = "COOKIE_USER_ID";
	
	/**
	 * 存放cookie  的用户的名字
	 */
	public static final String  COOKIE_USER_NAME = "COOKIE_USER_NAME";
	/**
	 * 存放cookie  的sessionId
	 */
	public static  final String COOKIE_SESSION_ID = "COOKIE_SESSION_ID";
	
}
