package com.dvnchina.advertDelivery.sysconfig.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.service.OperateLogService;
import com.dvnchina.advertDelivery.sysconfig.bean.UserRank;
import com.dvnchina.advertDelivery.sysconfig.service.UserRankService;

public class UserRankAction extends BaseAction{

	private static final long serialVersionUID = 1278623255540264405L;
	private UserRankService userRankService = null;
	private OperateLogService operateLogService = null;
	private PageBeanDB page = null;
	private UserRank userRank = null;
	private OperateLog operLog = null;
	
	/**
	 * 分页查询用户级别信息
	 */
	public String queryUserRankList(){
		try{
			if(page == null){
				page = new PageBeanDB();
			}
			page = userRankService.queryUserRankList(userRank,page.getPageNo(), page.getPageSize());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 根据ID获取用户级别信息
	 * @param id
	 */
	public String getUserRankById(){
		try{
			userRank = userRankService.getUserRankById(userRank.getId());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 进入新增用户级别页面
	 * @return
	 */
	public String addUserRank(){
		return SUCCESS;
	}
	
	/**
	 * 修改用户级别信息
	 * @param userRank
	 */
	public String saveUserRank(){
		boolean isExists  = false;
		try{
			isExists  = userRankService.existsUserRank(userRank);
			if(isExists){
				message = "save.user.rank.exists";//用户级别已经存在
				return INPUT;
			}
			if(userRank.getId() == null){
				operType = "operate.add";
			}else{
				operType = "operate.update";
			}
			
			userRankService.saveUserRank(userRank);
			message = "common.save.success";//保存成功
		}catch(Exception e){
			message = "common.save.failed";//保存失败
			operResult = Constant.OPERATE_FAIL;
			log.error("***** UserRankAction saveUserRank occur a exception: "+e);
		}finally{
			if(!isExists){
				operInfo = userRank.toString();
				operLog = this.setOperationLog(Constant.OPERATE_MODULE_USER_RANK);
				operateLogService.saveOperateLog(operLog);
			}
		}
		userRank = null;
		return queryUserRankList();
	}
	
	/**
	 * 删除用户级别
	 * @return
	 */
	public String delUserRank(){
		StringBuffer delInfo = new StringBuffer();
		delInfo.append("删除用户级别：");
		List<UserRank> list = new ArrayList<UserRank>();
		try{
			
			String[] idsStr = ids.split(",");
			for (String idStr : idsStr) {
				UserRank ur = new UserRank();
				ur.setId(Integer.valueOf(StringUtils.trim(idStr.split("_")[0])));
				delInfo.append(Integer.valueOf(StringUtils.trim(idStr.split("_")[0]))).append(",");
				delInfo.append(idStr.split("_")[1]).append(Constant.OPERATE_SEPARATE);
				list.add(ur);
			}
			
			userRankService.delUserRank(list);
			message = "common.delete.success";//删除成功
		}catch(Exception e){
			message = "common.delete.fail";//删除失败
			operResult = Constant.OPERATE_FAIL;
			log.error("***** UserRankAction delUserRank occur a exception: "+e);
		}finally{
			delInfo.append("共").append(ids.split(",").length).append("条记录");
			operType = "operate.delete";
			operInfo = delInfo.toString();
			operLog = this.setOperationLog(Constant.OPERATE_MODULE_USER_RANK);
			operateLogService.saveOperateLog(operLog);
		}
		ids = null;
		return queryUserRankList();
	}
	
	public PageBeanDB getPage() {
		return page;
	}
	public void setPage(PageBeanDB page) {
		this.page = page;
	}
	public UserRank getUserRank() {
		return userRank;
	}
	public void setUserRank(UserRank userRank) {
		this.userRank = userRank;
	}
	public void setUserRankService(UserRankService userRankService) {
		this.userRankService = userRankService;
	}

	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}
	
	
	
	
}
