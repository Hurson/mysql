package com.dvnchina.advertDelivery.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.dvnchina.advertDelivery.constant.LoginConstant;
import com.dvnchina.advertDelivery.constant.PurviewConstant;
import com.dvnchina.advertDelivery.model.Column;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.Location;
import com.dvnchina.advertDelivery.model.UserLogin;
import com.dvnchina.advertDelivery.service.SecurityService;
import com.dvnchina.advertDelivery.sysconfig.bean.Role;
import com.dvnchina.advertDelivery.sysconfig.bean.User;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.CookieUtils;

/**
 * <h1>1. 用户检查</h1>
 * 
 * 	<li>1.1、验证用户是否为超级管理员：配置信息写在配置文件中（configuration.properties）</li>
 * 	<li>1.2、验证用户是否存在：账户不存在时，给出提示</li>
 * 	<li>1.3、验证用户密码是否正确:密码错误时，给出提示</li>
 * 	<li>1.4、将用户的名字写入cookie</li>
 * 
 * <h1>2.用户登陆</h1>
 * 
 * <li>2.1 、将用户信息存入cookie ，userId  、userName 、sessionId</li>
 * <li>2.2、提供用户相关信息的接口</li>
 * 
 * <h1>3.根据用户的ID获取角色</h1>
 * 
 * <h1>4.根据角色的ID获取拥有的栏目</h1>
 * 
 * <h1>5.展示用户拥有的栏目</h1>
 * @return
 */
public class LoginAction extends BaseActionSupport<Object>  {

	private static final long serialVersionUID = 1L;
	
	private User user;
	private SecurityService securityService;
	
	private String message;
	
	private  ConfigureProperties config = ConfigureProperties.getInstance();
	
	/**
	 * 用户检查
	 * 	<li>1.1、验证用户是否为超级管理员：配置信息写在配置文件中（configuration.properties）</li>
	 * 	<li>1.2、验证用户是否存在：账户不存在时，给出提示</li>
	 * 	<li>1.3、验证用户密码是否正确:密码错误时，给出提示</li>
	 * 	<li>1.4、将用户的名字写入cookie</li>
	 * 
	 * @return
	 */
	public String userCheck(){
	
		HttpServletRequest  request = getRequest();
		
		try {
			
			int result = 0;
			Integer userId = null;
			
			String loginName = request.getParameter("loginname");
			String password = request.getParameter("password");
			
			//用户登陆检查
			User user = securityService.queryUser(loginName, password);
			if(null != user){
				String userPwd = user.getPassword();
				if(password.equals(userPwd)){
					if(PurviewConstant.USER_STATUS_DIS_ABLE.equals(user.getState())){
						result = LoginConstant.USER_DIS_ABLE;
					}else{
						userId = user.getUserId();
						request.setAttribute("userId", userId);
						
						//将用户信息写入cookies
						
						result = LoginConstant.USER_SUCCESS;
					}
				}else{
					result = LoginConstant.PASSWD_ERROR;	
				}
			//	CookieUtils.setCookie(request, getResponse(), LoginConstant.COOKIE_USER_NAME, user.getUserName());
				CookieUtils.setCookie(request, getResponse(), LoginConstant.COOKIE_USER_NAME, "user.getLoginName()");
				
				UserLogin userLogin = new UserLogin();
				userLogin.setUserId(user.getUserId());
				userLogin.setUserName(user.getUserName());
				this.getRequest().getSession().setAttribute("USER_LOGIN_INFO", userLogin);
			}else{
				result = LoginConstant.USER_ERROR;
			}
			
			
			
			String json  = "{\"result\":"+result+",\"userId\":"+userId+"}";
			renderJson(json);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	
		return NONE;
	} 
	
	
	/**
	 * 判断是否是超级管理员
	 * @param loginname
	 * @param password
	 * @return
	 */
	private boolean isNotAdmin(String loginname,String password){
		String loginname_config = config.getValueByKey("system.administrator.loginname");
		String password_config = config.getValueByKey("system.administrator.password");
		if(loginname.equals(loginname_config) && password.equals(password_config)){
			return  true;
		}
		return false;
	}
	
	
	/**
	 * <li>1.将用户信息存入cookie ，userId  、userName 、sessionId</li>
	 * <li>提供用户相关信息的接口</li>
	 * @return  用戶登陆
	 */
	public String userLogin(){
		
		HttpServletRequest  request = getRequest();
		HttpServletResponse response =  getResponse();
		
		String sessionId = request.getSession().getId();
		
		try {
			String userName = CookieUtils.getCookieValueByKey(request, LoginConstant.COOKIE_USER_NAME);
			String userId = request.getParameter("userId");
			if(userId == null || "".equals(userId) || userName.isEmpty()){
				response.sendRedirect("login.jsp");
			}
			List<Column> userOwnCoulumn = securityService.getColumnByUserId(userId);
			Integer id = Integer.valueOf(userId);
			
			//用户角色类型
			int roleType = securityService.getRoleTypeByUserId(id);
			
			//广告商Id
			Integer customerId = 0;
			if(roleType == 1){
				customerId = securityService.getCustomerIds(id);
			}
			
			//绑定的广告位包Id
			List<Integer> lstPositionPackageIds = securityService.getPositionPackageIds(id, roleType);
			
			//绑定的区域码列表
			String locationCodes = securityService.getUserOwnLocationCodes(id, roleType);
			
			//登陆用户具有访问权限的用户id
			List<Integer> accessUserId = securityService.getAccessUserIdList(lstPositionPackageIds,locationCodes,id,roleType, customerId);
			
			String dtmbPositionIds = securityService.getAccessDtmbPositionIds(id,roleType);
			
			UserLogin userLogin = (UserLogin) this.getRequest().getSession().getAttribute("USER_LOGIN_INFO");
			if(userLogin != null){
				userLogin.setRoleType(roleType);
				userLogin.setCustomerId(customerId);
				userLogin.setPositionIds(lstPositionPackageIds);
				userLogin.setAreaCodes(locationCodes);
				userLogin.setAccessUserIds(accessUserId);
				userLogin.setDtmbPositionIds(dtmbPositionIds);
			}
			
			request.setAttribute("flag", "test");
			String columns = JSONArray.fromObject(userOwnCoulumn).toString();
			
			request.setAttribute("coulumns",columns);
			request.setAttribute("userId",userId);
			
		    this.getRequest().getSession().setAttribute("coulumns", columns);
			this.getRequest().getSession().setAttribute("userId",userId);
				
			CookieUtils.setCookie(request, response, LoginConstant.COOKIE_USER_ID, userId);
			CookieUtils.setCookie(request, response, LoginConstant.COOKIE_SESSION_ID, sessionId);
			
			return "index";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ERROR;
	} 
	
	/**
	 *  接口  根据用户的ID，获取用户拥有的角色
	 *  
	 * @return JSON Role List<Role>
	 */
	public String getUserOwnRole(){
		
		HttpServletRequest request = getRequest();
		String userId = request.getParameter("userId");
		String content = "";
		
		if(StringUtils.isBlank(userId)){
			userId = CookieUtils.getCookieValueByKey(request, LoginConstant.COOKIE_USER_ID);
		}
		
		if(StringUtils.isNotBlank(userId)){
			List<Role> user_roleL = securityService.getUserOwnRoleList(Integer.valueOf(userId));
			
			content = JSONArray.fromObject(user_roleL).toString()+"";
		}
		
		renderJson(content);
		return NONE;
		
	}
	
	/**
	 *  接口  根据用户的ID，获取用户拥有的地区信息
	 *  
	 * @return JSON Location List<Location>
	 */
	public String getUserOwnLocation(){
		
		HttpServletRequest request = getRequest();
		String userId = request.getParameter("userId");
		String content = "";
		
		if(StringUtils.isBlank(userId)){
			userId = CookieUtils.getCookieValueByKey(request, LoginConstant.COOKIE_USER_ID);
		}
		
		if(StringUtils.isNotBlank(userId)){
			List<Location> user_locationL = securityService.getUserOwnLocation(Integer.valueOf(userId));
			content = JSONArray.fromObject(user_locationL).toString()+"";
		}
		
		renderJson(content);
		return NONE;
		
	}
	
	/**
	 * 
	 *  接口  根据用户的ID，获取用户拥有的客户的信息
	 * @return JSON  Customer List<Customer>
	 */
	public String getUserOwnCustomer(){
		
		HttpServletRequest request = getRequest();
		String userId = request.getParameter("userId");
		String content = "";
		
		if(StringUtils.isBlank(userId)){
			userId = CookieUtils.getCookieValueByKey(request, LoginConstant.COOKIE_USER_ID);
		}
		
		if(StringUtils.isNotBlank(userId)){
			List<Customer> user_customerL = securityService.getUserOwnCustomer(Integer.valueOf(userId));
			content = JSONArray.fromObject(user_customerL).toString()+"";
		}
		
		renderJson(content);
		return NONE;
		
	}
	
	
	/**
	 * 用户登出:
	 * <li>1、清楚Cookie 里面的COOKIE_USER_ID、COOKIE_USER_NAME </li>
	 * <li>2、跳出操作页面、进入登录页面 或者logo页面</li>
	 * 
	 * @return
	 */
	public String userLogout(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		CookieUtils.setCookie(request, response, LoginConstant.COOKIE_USER_NAME, "");
		CookieUtils.setCookie(request, response, LoginConstant.COOKIE_SESSION_ID, "");
		CookieUtils.setCookie(request, response, LoginConstant.COOKIE_SESSION_ID, "");
		Map<String, Object> session = ServletActionContext.getContext().getSession();
		session.remove("USER_LOGIN_INFO");
		session.remove("coulumns");
		session.remove("userId");
		session.remove("customer");
		request.removeAttribute("userId");
		return "login";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
