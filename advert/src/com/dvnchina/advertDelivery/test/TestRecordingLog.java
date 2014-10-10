package com.dvnchina.advertDelivery.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.constant.AdvertLogConstant;
import com.dvnchina.advertDelivery.service.AdvertRecordingLogService;
import com.dvnchina.advertDelivery.service.impl.AdvertRecordingLogServiceImpl;

@SuppressWarnings("unchecked")
public class TestRecordingLog extends TestCase{
	
	private final static String className = TestRecordingLog.class.getName();
	
	private static Logger analystLog = Logger.getLogger(AdvertLogConstant.RECORD_LOG_TYPE_AYALYST);

	private static Logger contentLog = Logger.getLogger(AdvertLogConstant.RECORD_LOG_TYPE_CONTENT);
	
	private static Logger logger = Logger.getLogger(className);
	
	/**
	 * 测试增加【正常】
	 */
	public void testAdd(){
		
		AdvertRecordingLogService addOperation4Analyst = null;
		AdvertRecordingLogService addOperation4Content = null;
		User addUser = getUser(123,"张三","123456");
		List addUserList = new ArrayList();
		try {
			addUserList.add(addUser);
			addOperation4Analyst = new AdvertRecordingLogServiceImpl(AdvertLogConstant.RECORD_LOG_OPERATION_ANALYST_FLAG_TRUE,"YHGL-TJYH",
					"operator1",AdvertLogConstant.RECORD_LOG_OPERATION_TYPE_ADD_FOR_ANALYST,AdvertLogConstant.RECORD_LOG_OPERATION_STATUS_SUCCESS_FOR_ANALYST,
					"用户管理-添加用户【" + addUser.getName() + "】","192.168.12.93");
			
			analystLog.info(addOperation4Analyst.generateLog(null, addUserList));
			
			addOperation4Content = new AdvertRecordingLogServiceImpl(AdvertLogConstant.RECORD_LOG_OPERATION_ANALYST_FLAG_FALSE,"用户管理-添加用户",
					"操作员1",AdvertLogConstant.RECORD_LOG_OPERATION_TYPE_ADD_FOR_CONTENT,AdvertLogConstant.RECORD_LOG_OPERATION_STATUS_SUCCESS_FOR_CONTENT,
					"用户管理-添加用户【" + addUser.getName() + "】","192.168.12.93");
			contentLog.info(addOperation4Content.generateLog(null, addUserList));
		} catch (Exception e) {
			//记录系统日志信息
			logger.error("添加用户时出现异常",e);
			//为后续进行系统自身操作日志分析记录相关日志
			addOperation4Analyst = new AdvertRecordingLogServiceImpl(
					AdvertLogConstant.RECORD_LOG_OPERATION_ANALYST_FLAG_TRUE,
					"YHGL-TJYH",
					"operator1",
					AdvertLogConstant.RECORD_LOG_OPERATION_TYPE_ADD_FOR_ANALYST,
					AdvertLogConstant.RECORD_LOG_OPERATION_STATUS_EXCEPTION_FOR_ANALYST,
					"用户管理-添加用户【" + addUser.getName() + "】异常","192.168.12.93");
			analystLog.info(addOperation4Analyst.generateLog(null, addUserList));
			//记录供后台查看日志信息
			addOperation4Content = new AdvertRecordingLogServiceImpl(
					AdvertLogConstant.RECORD_LOG_OPERATION_ANALYST_FLAG_FALSE,
					"用户管理-添加用户",
					"操作员1",
					AdvertLogConstant.RECORD_LOG_OPERATION_TYPE_ADD_FOR_CONTENT,
					AdvertLogConstant.RECORD_LOG_OPERATION_STATUS_EXCEPTION_FOR_CONTENT,
					"用户管理-添加用户【" + addUser.getName() + "】异常","192.168.12.93");
			contentLog.info(addOperation4Content.generateLog(null, addUserList));
		}
	}
	
	/**
	 * 测试删除
	 */
	public void testDelete(){
		
		try {
			User deleteUser = new User();
			deleteUser.setId(456);
			deleteUser.setName("李四");
			deleteUser.setPassword("123456");
			List deleteUserList = new ArrayList();
			deleteUserList.add(deleteUser);
			AdvertRecordingLogService deleteOperation4Analyst = new AdvertRecordingLogServiceImpl(
					AdvertLogConstant.RECORD_LOG_OPERATION_ANALYST_FLAG_TRUE,
					"YHGL-SCYH",
					"operator1",
					AdvertLogConstant.RECORD_LOG_OPERATION_TYPE_DELETE_FOR_ANALYST,
					AdvertLogConstant.RECORD_LOG_OPERATION_STATUS_SUCCESS_FOR_ANALYST,
					"用户管理-删除用户【" + deleteUser.getName() + "】","192.168.12.93");
			analystLog.info(deleteOperation4Analyst.generateLog(
									deleteUserList, null));
			AdvertRecordingLogService deleteOperation4Content = new AdvertRecordingLogServiceImpl(
					AdvertLogConstant.RECORD_LOG_OPERATION_ANALYST_FLAG_FALSE,
					"用户管理-删除用户",
					"操作员1",
					AdvertLogConstant.RECORD_LOG_OPERATION_TYPE_DELETE_FOR_CONTENT,
					AdvertLogConstant.RECORD_LOG_OPERATION_STATUS_SUCCESS_FOR_CONTENT,
					"用户管理-删除用户【" + deleteUser.getName() + "】","192.168.12.93");
			contentLog.info(deleteOperation4Content.generateLog(
									deleteUserList, null));
		} catch (Exception e) {
			logger.error("删除用户时出现异常",e);
		}
	}
	/**
	 * 测试更新
	 */
	public void testUpdate(){
		try {
			User updateUserBefore = getUser(123,"张三","123456");
			List updateUserBeforeList = new ArrayList();
			updateUserBeforeList.add(updateUserBefore);
			User updateUserAfter = getUser(456,"张三","1234567890");
			List updateUserAfterList = new ArrayList();
			updateUserAfterList.add(updateUserAfter);
			AdvertRecordingLogService updateOperation4Analyst = new AdvertRecordingLogServiceImpl(
					AdvertLogConstant.RECORD_LOG_OPERATION_ANALYST_FLAG_TRUE,
					"YHGL-GXYH",
					"operator1",
					AdvertLogConstant.RECORD_LOG_OPERATION_TYPE_UPDATE_FOR_ANALYST,
					AdvertLogConstant.RECORD_LOG_OPERATION_STATUS_SUCCESS_FOR_ANALYST,
					"用户管理-更新用户【" + updateUserBefore.getName() + "】","192.168.12.93");
			analystLog.info(updateOperation4Analyst.generateLog(updateUserBeforeList,
							updateUserAfterList));
			AdvertRecordingLogService updateOperation4Content = new AdvertRecordingLogServiceImpl(
					AdvertLogConstant.RECORD_LOG_OPERATION_ANALYST_FLAG_FALSE,
					"用户管理-更新用户",
					"操作员1",
					AdvertLogConstant.RECORD_LOG_OPERATION_TYPE_UPDATE_FOR_CONTENT,
					AdvertLogConstant.RECORD_LOG_OPERATION_STATUS_SUCCESS_FOR_CONTENT,
					"用户管理-更新用户【" + updateUserBefore.getName() + "】","192.168.12.93");
			contentLog.info(updateOperation4Content.generateLog(updateUserBeforeList,updateUserAfterList));
		} catch (Exception e) {
			logger.error("更新用户时出现异常", e);
		}
	}
	
	public void testQueryUser(){
		try {
			User updateUserBefore = getUser(123,"张三","123456");
			List updateUserBeforeList = new ArrayList();
			updateUserBeforeList.add(updateUserBefore);
			User updateUserAfter = getUser(456,"张三","1234567890");
			List updateUserAfterList = new ArrayList();
			updateUserAfterList.add(updateUserAfter);
			AdvertRecordingLogService updateOperation4Analyst = new AdvertRecordingLogServiceImpl(
					AdvertLogConstant.RECORD_LOG_OPERATION_ANALYST_FLAG_TRUE,
					"YHGL-YHCX",
					"operator1",
					AdvertLogConstant.RECORD_LOG_OPERATION_TYPE_QUERY_FOR_ANALYST,
					AdvertLogConstant.RECORD_LOG_OPERATION_STATUS_SUCCESS_FOR_ANALYST,
					"用户管理-用户查询【" + updateUserBefore.getName() + "】","192.168.12.93");
			analystLog.info(updateOperation4Analyst.generateLog(updateUserBeforeList,
							updateUserAfterList));
			AdvertRecordingLogService updateOperation4Content = new AdvertRecordingLogServiceImpl(
					AdvertLogConstant.RECORD_LOG_OPERATION_ANALYST_FLAG_FALSE,
					"用户管理-用户查询",
					"操作员1",
					AdvertLogConstant.RECORD_LOG_OPERATION_TYPE_QUERY_FOR_CONTENT,
					AdvertLogConstant.RECORD_LOG_OPERATION_STATUS_SUCCESS_FOR_CONTENT,
					"用户管理-用户查询【" + updateUserBefore.getName() + "】","192.168.12.93");
			contentLog.info(updateOperation4Content.generateLog(updateUserBeforeList,updateUserAfterList));
		} catch (Exception e) {
			logger.error("更新用户时出现异常", e);
		}
	}
	
	/**
	 * 获取用户信息
	 * @return
	 */
	public User getUser(Integer id,String username,String password){
		User user = new User();
		user.setId(id);
		user.setName(username);
		user.setPassword(password);
		return user;
	}
}