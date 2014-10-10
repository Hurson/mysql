package com.dvnchina.advertDelivery.sysconfig.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.constant.PurviewConstant;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.service.OperateLogService;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.position.service.PositionService;
import com.dvnchina.advertDelivery.service.CustomerService;
import com.dvnchina.advertDelivery.service.PurviewService;
import com.dvnchina.advertDelivery.sysconfig.bean.Role;
import com.dvnchina.advertDelivery.sysconfig.bean.User;
import com.dvnchina.advertDelivery.sysconfig.service.RoleService;
import com.dvnchina.advertDelivery.sysconfig.service.UserService;
import com.dvnchina.advertDelivery.utils.StringUtil;

public class UserAction extends BaseAction{
	
	private static final long serialVersionUID = 1856711197701524610L;
	private UserService userService = null;
	private RoleService roleService;
	private CustomerService customerService;
	private PurviewService purviewService;
	private OperateLogService operateLogService = null;
	private PageBeanDB page = null;
	private User user = null;
	private Customer cust = null;
	private List<Role> roleList;
	private List<Customer> customerList;
	private OperateLog operLog = null;
	private PositionService positionService;
	
	private String newPassword;
	private String newPasswordCheck;
	private String oldPassword;
	/**
	 * 分页查询用户信息
	 */
	public String queryUserList(){
		try{
			if(page == null){
				page = new PageBeanDB();
			}
			page = userService.queryUserList(user,page.getPageNo(), page.getPageSize());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 进入新增用户页面
	 * @return
	 */
	public String initAdd(){
		try{
			roleList = roleService.getAllRole();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 获取角色类型
	 */
	public void getRoleType(){
		try{
			String roleId = getRequest().getParameter("roleId");
			if(roleId != null){
				Role role = roleService.getRoleById(Integer.valueOf(roleId));
				renderText(role.getType()+"");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询广告商列表
	 * @return
	 */
	public String queryCustomerList(){
		try{
			String roleType = getRequest().getParameter("roleType");
			String user_customer_ids = getRequest().getParameter("user_customer_ids");
			List<Integer> customerIdList= StringUtil.getIntegerList(user_customer_ids, PurviewConstant.SIGN_COMMA);
			customerList = customerService.getCustomerList(cust);
			// 去掉ID=0的初始化数据
			for (int i = 0;i<customerList.size();i++) {
				Customer cus = customerList.get(i);
				if(cus.getId() == 0){
					customerList.remove(cus);
					break;
				}
			}
			/*
			for (int i = 0, j=customerList.size(); i < j; i++) {
				Customer cus = customerList.get(i);
				if(cus.getId() == 0){
					customerList.remove(i);
					break;
				}
			}
				*/
			getRequest().setAttribute("roleType", roleType);
			getRequest().setAttribute("customerIdList", customerIdList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//检查登录名是否存在
	public void checkLoginName(){
		String userId = getRequest().getParameter("userId");
		String loginName = getRequest().getParameter("loginName");
		User user = new User();
		user.setLoginName(loginName);
		if(StringUtils.isNotBlank(userId)){
			user.setUserId(Integer.valueOf(userId));
		}
		Boolean existsUser = userService.checkLoginName(user);
		this.renderText(existsUser.toString());
	}
	
	/**
	 * 添加用户 
	 * @return
	 */
	public String addUser(){
		try{
			user.setCreateTime(new Date());
			user.setModifyTime(new Date());
			user.setState(PurviewConstant.USER_STATUS_ABLE);
			user.setDelFlag(0);
			userService.insertUser(user);
			
			//插入和用户相关的数据
			purviewService.insertUserBandingData(user);
			
			message = "common.add.success";//保存成功
		}catch(Exception e){
			message = "common.add.failed";//保存失败
			operResult = Constant.OPERATE_FAIL;
			log.error("***** UserAction addUser occur a exception: "+e);
		}finally{
			operType = "operate.add";
			operInfo = user.toString();
			operLog = this.setOperationLog(Constant.OPERATE_MODULE_USER);
			operateLogService.saveOperateLog(operLog);
		}
		user = null;
		return queryUserList();
	}
	
	/**
	 * 进入用户修改页面
	 * @return
	 */
	public String getUserForUpdate(){
		try {
			user = userService.getUserDetailById(user.getUserId());
			roleList = roleService.getAllRole();
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 修改用户信息
	 * @return
	 */
	public String updateUser(){
		try {
			user.setModifyTime(new Date());
			userService.updateUser(user);
			purviewService.deleteUserRoleAllBinding(user.getUserId());
			purviewService.deleteUserCustomerBatchBinding(user.getUserId());
			purviewService.deleteUserAdvertPackageBinding(user.getUserId());
			//插入和用户相关的数据
			purviewService.insertUserBandingData(user);
			message = "common.update.success";//修改成功
		} catch (Exception e) {
			message = "common.update.failed";//保存失败
			operResult = Constant.OPERATE_FAIL;
			log.error("***** UserAction updateUser occur a exception: "+e);
		}finally{
			operType = "operate.update";
			operInfo = user.toString();
			operLog = this.setOperationLog(Constant.OPERATE_MODULE_USER);
			operateLogService.saveOperateLog(operLog);
		}
		user = null;
		return queryUserList();
	}
	
	/**
	 * 批量删除用户
	 * 
	 */
	public String delUser(){
		
		StringBuffer delInfo = new StringBuffer();
		delInfo.append("删除用户：");
		try{
			String[] idsStr = ids.split(",");
			for (String idStr : idsStr) {
				Integer userId = Integer.valueOf(StringUtils.trim(idStr.split("_")[0]));
				userService.deleteUser(userId);
				purviewService.deleteUserRoleAllBinding(userId);
				purviewService.deleteUserCustomerBatchBinding(userId);
				purviewService.deleteUserAdvertPackageBinding(userId);
				delInfo.append(Integer.valueOf(StringUtils.trim(idStr.split("_")[0]))).append(",");
				delInfo.append(idStr.split("_")[1]).append(Constant.OPERATE_SEPARATE);
			}
			message = "common.delete.success";//删除成功
		}catch(Exception e){
			message = "common.delete.failed";//删除失败
			operResult = Constant.OPERATE_FAIL;
			log.error("***** UserAction delUser occur a exception: "+e);
		}finally{
			delInfo.append("共").append(ids.split(",").length).append("条记录");
			operType = "operate.delete";
			operInfo = delInfo.toString();
			operLog = this.setOperationLog(Constant.OPERATE_MODULE_USER);
			operateLogService.saveOperateLog(operLog);
		}
		ids = null;
		return queryUserList();
	}

	public String getUserAdvertPackage(){
		String user_positions_ids = getRequest().getParameter("user_positions");
		List<Integer> positionsIdList= StringUtil.getIntegerList(user_positions_ids, PurviewConstant.SIGN_COMMA);
		page = positionService.queryPositionPackageList(0, 50);
		getRequest().setAttribute("positionsIdList", positionsIdList);
		return SUCCESS;
	}
	
	public PositionService getPositionService() {
		return positionService;
	}

	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	public PageBeanDB getPage() {
		return page;
	}

	public void setPage(PageBeanDB page) {
		this.page = page;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}

	public Customer getCust() {
		return cust;
	}

	public void setCust(Customer cust) {
		this.cust = cust;
	}

	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public void setPurviewService(PurviewService purviewService) {
		this.purviewService = purviewService;
	}
	
	
	
	
	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordCheck() {
		return newPasswordCheck;
	}

	public void setNewPasswordCheck(String newPasswordCheck) {
		this.newPasswordCheck = newPasswordCheck;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String changePassword()
	{
		return SUCCESS;
	}
	public String changePasswordCheck()
	{
		if (user!=null && user.getUserId()!=0 && oldPassword!="" && newPassword!="")
		{
			this.message=userService.changePassword(user.getUserId(),oldPassword, newPassword);
		}
		return SUCCESS;
	}
}
