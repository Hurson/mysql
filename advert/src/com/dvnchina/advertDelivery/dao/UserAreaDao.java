package com.dvnchina.advertDelivery.dao;

import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.bean.UserReleaseArea.UserReleaseAreaBean;
import com.dvnchina.advertDelivery.model.UserArea;

public interface UserAreaDao {
	
	
	/**
	 * 在投放区域查询时，用于页面上展示
	 * 按照查询条件查询
	 * @return 
	 */
	public List<UserReleaseAreaBean> getUserAndReleaseAreaList(Map map,int first,int pageSize);
	
	
	/**
	 * 在投放区域查询时，用于页面上展示
	 * @return
	 */
	public List<UserReleaseAreaBean> getUserAndReleaseAreaList(Map map);
	
	
	/**
	 * 通过userId查询结果
	 * @param userId
	 * @return
	 */
	public UserArea getUserAreaByUserId(Integer userId);
	
	/**
	 * 通过id 获得对应结果集
	 */
	public UserArea getUserAreaById(Integer id);
	
	/**
	 * 删除投放信息管理表单记录
	 * 
	 */
	public int deleteUserAreaInfo(UserArea userArea);
	
	
	
	
	/**
	 * 查询记录总数
	 */
	
	public int getUserAreaCount(UserArea userArea,String state);
	
	/**
	 * 查询总的结果集
	 */
	
	public List<UserArea> listUserAreaMgr(UserArea userArea,int x,int y,String state);


}
