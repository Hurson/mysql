package com.dvnchina.advertDelivery.sysconfig.action;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.service.OperateLogService;
import com.dvnchina.advertDelivery.sysconfig.bean.UserIndustryCategory;
import com.dvnchina.advertDelivery.sysconfig.service.UserTradeService;

public class UserTradeAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	private UserTradeService userTradeService;
	private OperateLogService operateLogService = null;
	private PageBeanDB page = null;
	private UserIndustryCategory industry;
	private OperateLog operLog = null;
	
	/**
	 * 分页查信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public String queryUserTradeList(){
		if(page == null){
			page = new PageBeanDB();
		}
		page =userTradeService.queryUserTradeList(industry,page.getPageNo(), page.getPageSize());
		return SUCCESS;
		
	}
	
	/**
	 * 根据ID获取配置项信息                                                                                                                                                                                                                                                                                                                                
	 * @param id
	 * @return BaseConfig
	 */
	public String getUserTradeById(){
		industry = userTradeService.getUserTradeById(Integer.valueOf(id));
		return SUCCESS;
	}
	
	
	/**
	 * 修改用户行业
	 * 
	 */
	public String updateUserTrade(){
		boolean isExists  = false;
		try{
			isExists = userTradeService.existsIndustry(industry);
			if(isExists){
				message = "save.user.trade.exists";//用户行业已经存在
				return INPUT;
			}
			if(industry.getId() == null){
				operType = "operate.add";
				userTradeService.saveUserTrade(industry);
			} else {
				userTradeService.updateUserTrade(industry);
				operType = "operate.update";
			}
			message = "common.save.success";//保存成功
		}catch(Exception e){
			message = "common.save.failed";//保存失败
			operResult = Constant.OPERATE_FAIL;
			log.error("***** UserTradeAction updateUserTrade occur a exception: "+e);
		}finally{
			if(!isExists){
				operInfo = industry.toString();
				operLog = this.setOperationLog(Constant.OPERATE_MODULE_USER_TRADE);
				operateLogService.saveOperateLog(operLog);
			}
		}
		industry = null;
		return queryUserTradeList();
	}
	
	/**
	 * 新增用户行业信息的时候初始化
	 * @return
	 */
	public String addInitUserTrade(){
		industry = new UserIndustryCategory();
		return SUCCESS;
	}
	/**
	 * 删除用户行业级别信息
	 * @param List<BaseConfig>
	 * 
	 */
	public String deleteUserTrade(){
		StringBuffer delInfo = new StringBuffer();
		delInfo.append("删除用户行业级别：");
		try{
			userTradeService.deleteUserTrade(ids);
			message = "common.delete.success";//删除成功
		}catch(Exception e){
			message = "common.delete.fail";//删除失败
			operResult = Constant.OPERATE_FAIL;
			log.error("***** UserTradeAction deleteUserTrade occur a exception: "+e);
		}finally{
			delInfo.append("共").append(ids.split(",").length).append("条记录");
			operType = "operate.delete";
			operInfo = delInfo.toString();
			operLog = this.setOperationLog(Constant.OPERATE_MODULE_USER_RANK);
			operateLogService.saveOperateLog(operLog);
		}
		ids = null;
		return queryUserTradeList();
		
	}
	
	public PageBeanDB getPage() {
		return page;
	}

	public void setPage(PageBeanDB pageBean) {
		this.page = pageBean;
	}

	public UserIndustryCategory getIndustry() {
		return industry;
	}

	public void setIndustry(UserIndustryCategory industry) {
		this.industry = industry;
	}

	public void setUserTradeService(UserTradeService userTradeService) {
		this.userTradeService = userTradeService;
	}

	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}
}
