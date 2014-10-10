package com.dvnchina.advertDelivery.action;

import com.opensymphony.xwork2.ActionSupport;

public class PositionAction extends ActionSupport{
	
	private static final long serialVersionUID = 5528985853425795719L;
	
	private static final String POSITION_FORWARD_PAGE_RESULT="page";
	
	/**
	 * 增加广告位
	 * @return
	 */
	public String addPage(){	
		return POSITION_FORWARD_PAGE_RESULT;
	}
	/**
	 * 删除广告位
	 * @return
	 */
	public String removePage(){	
		return POSITION_FORWARD_PAGE_RESULT;
	}
	/**
	 * 更新广告位
	 * @return
	 */
	public String updatePage(){	
		return POSITION_FORWARD_PAGE_RESULT;
	}
	/**
	 * 查询广告位
	 * @return
	 */
	public String queryPage(){	
		return POSITION_FORWARD_PAGE_RESULT;
	}
	
	/**
	 * 更新广告位属性
	 * @return
	 */
	public String propertyManagePage(){
		return POSITION_FORWARD_PAGE_RESULT;
	}
	
	/**
	 * 广告位状态设置
	 * @return
	 */
	public String stateManagePage(){
		return POSITION_FORWARD_PAGE_RESULT;
	}
	
	/**
	 * 占用情况
	 * @return
	 */
	public String utilizationConditionPage(){
		return POSITION_FORWARD_PAGE_RESULT;
	}
	
	/**
	 * 占用时间
	 * @return
	 */
	public String occupyTimePage(){
		return POSITION_FORWARD_PAGE_RESULT;
	}
}
