package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.ReleaseArea;

public interface ReleaseAreaDao {
	
	
	/**
	 * 通过投放区域编码得到结果 
	 * @param releaseAreaCode
	 * @return
	 */
	public ReleaseArea getReleaseAreaByreleaseAreaCode(String releaseAreaCode);
	
	
	/**
	 * 通过id 获得对应结果集
	 */
	
	public ReleaseArea getReleaseAreaById(Integer id);
	
	/**
	 * 删除投放信息管理表单记录
	 * 
	 */
	public int deleteReleaseArea(ReleaseArea releaseArea);
	
	
	/**
	 * 查询投放信息管理表单结果集的记录数
	 */
	public int getReleaseAreaCount(ReleaseArea releaseArea,String state);

	/**
	 * 查询投放信息管理表单结果集
	 */
	public List<ReleaseArea> listReleaseAreaMgr(ReleaseArea releaseArea,int x,int y,String state);
	
}
