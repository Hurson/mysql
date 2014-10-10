package com.dvnchina.advertDelivery.constant;
/**
 * 广告系统常量类，记录和记录日志相关的常量信息
 * @author lester
 */
public class AdvertLogConstant {
	/**
	 * 是否为日志解析系统所需数据
	 */
	public static final String RECORD_LOG_OPERATION_ANALYST_FLAG="operationAnalyst";
	/**
	 * 需要被日志解析系统解析的标识
	 */
	public static final String RECORD_LOG_OPERATION_ANALYST_FLAG_TRUE="true";
	/**
	 * 记录今后可能需要系统解析自有日志类型
	 */
	public static final String RECORD_LOG_TYPE_AYALYST="analyst";
	/**
	 * 记录控制台输出类型
	 */
	public static final String RECORD_LOG_TYPE_CONTENT="content";
	/**
	 * 不需要被日志解析系统解析的标识
	 */
	public static final String RECORD_LOG_OPERATION_ANALYST_FLAG_FALSE="false";
	/**
	 * 模块名称
	 */
	public static final String RECORD_LOG_OPERATION_MODULE="operationModule";
	/**
	 * 操作员名称
	 */
	public static final String RECORD_LOG_OPERATION_USER="operationUser";
	/**
	 * 操作类型
	 */
	public static final String RECORD_LOG_OPERATION_TYPE="operationType";
	/**
	 * 操作前
	 */
	public static final String RECORD_LOG_OPERATION_BEFORE="operationBefore";
	/**
	 * 操作后
	 */
	public static final String RECORD_LOG_OPERATION_AFTER="operationAfter";
	/**
	 * 操作状态
	 */
	public static final String RECORD_LOG_OPERATION_STATUS="operationStatus";
	/**
	 * 操作的备注信息
	 */
	public static final String RECORD_LOG_OPERATION_COMMENT="operationComment";
	/**
	 * 操作时间
	 */
	public static final String RECORD_LOG_OPERATION_DATE="operationDate";
	/**
	 * 访问用户的IP地址
	 */
	public static final String RECORD_LOG_OPERATION_IP="operationIp";
	/**
	 * 目录的绝对路径首字母，不同层级目录用中横线"-"隔开[用户管理-添加用户]
	 */
	public static final String RECORD_LOG_OPERATION_MODULE_NAME_YHGL_TJYH = "YHGL-TJYH";
	/**
	 * 添加（为分析日志信息时使用）
	 */
	public static final String RECORD_LOG_OPERATION_TYPE_ADD_FOR_ANALYST = "0";
	/**
	 * 删除（为分析日志信息时使用）
	 */
	public static final String RECORD_LOG_OPERATION_TYPE_DELETE_FOR_ANALYST = "1";
	/**
	 * 更新（为分析日志信息时使用）
	 */
	public static final String RECORD_LOG_OPERATION_TYPE_UPDATE_FOR_ANALYST = "2";
	/**
	 * 查询（为分析日志信息时使用）
	 */
	public static final String RECORD_LOG_OPERATION_TYPE_QUERY_FOR_ANALYST = "3";
	/**
	 * 添加(从后台查看日志信息时使用)
	 */
	public static final String RECORD_LOG_OPERATION_TYPE_ADD_FOR_CONTENT = "添加";
	/**
	 * 删除(从后台查看日志信息时使用)
	 */
	public static final String RECORD_LOG_OPERATION_TYPE_DELETE_FOR_CONTENT = "删除";
	/**
	 * 更新(从后台查看日志信息时使用)
	 */
	public static final String RECORD_LOG_OPERATION_TYPE_UPDATE_FOR_CONTENT = "更新";
	/**
	 * 查询(从后台查看日志信息时使用)
	 */
	public static final String RECORD_LOG_OPERATION_TYPE_QUERY_FOR_CONTENT = "查询";
	/**
	 * 处理成功（为分析日志信息时使用）
	 */
	public static final String RECORD_LOG_OPERATION_STATUS_SUCCESS_FOR_ANALYST = "SUCCESS";
	/**
	 * 处理失败（为分析日志信息时使用）
	 */
	public static final String RECORD_LOG_OPERATION_STATUS_FAILURE_FOR_ANALYST = "FAILURE";
	/**
	 * 处理异常（为分析日志信息时使用）
	 */
	public static final String RECORD_LOG_OPERATION_STATUS_EXCEPTION_FOR_ANALYST = "EXCEPTION";
	/**
	 * 处理成功(从后台查看日志信息时使用)
	 */
	public static final String RECORD_LOG_OPERATION_STATUS_SUCCESS_FOR_CONTENT = "处理成功";
	/**
	 * 处理失败(从后台查看日志信息时使用)
	 */
	public static final String RECORD_LOG_OPERATION_STATUS_FAILURE_FOR_CONTENT = "处理失败";
	/**
	 * 处理异常(从后台查看日志信息时使用)
	 */
	public static final String RECORD_LOG_OPERATION_STATUS_EXCEPTION_FOR_CONTENT = "处理异常";
	
}