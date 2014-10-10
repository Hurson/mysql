package com.dvnchina.advertDelivery.action;

import com.opensymphony.xwork2.ActionSupport;

public class DemoAction extends ActionSupport {
	
	
	
//
//	private static final long serialVersionUID = 250514762149207971L;
//
//	private Logger logger = Logger.getLogger(DemoAction.class);
//
//	private UserService userService;
//	
//	//和页面中表单name相对应
//	private Integer id;
//	//和页面中表单name相对应
//	private String username;
//	//和页面中表单name相对应
//	private String passwd;
//	//和注册页面中passwdRep相对应
//	private String passwdRep;
//	//和页面中表单name相对应
//	private String address;
//	//和页面中表单name相对应
//	private String email;
//
//	public UserService getUserService() {
//		return userService;
//	}
//
//	public void setUserService(UserService userService) {
//		this.userService = userService;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public Logger getLogger() {
//		return logger;
//	}
//
//	public void setLogger(Logger logger) {
//		this.logger = logger;
//	}
//
//	public String getPasswd() {
//		return passwd;
//	}
//
//	public void setPasswd(String passwd) {
//		this.passwd = passwd;
//	}
//
//	public String getAddress() {
//		return address;
//	}
//
//	public void setAddress(String address) {
//		this.address = address;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	
//	public String getPasswdRep() {
//		return passwdRep;
//	}
//
//	public void setPasswdRep(String passwdRep) {
//		this.passwdRep = passwdRep;
//	}
//
//	// 添加用户 尽量在客户端进行数据校验
//	public String addUser() throws Exception {
//		logger.debug("addUser 被调用了");
//		User user = null;
//		String flag = null;
//		int count = 0;
//		try {
//			if (StringUtils.isNotBlank(username)&&StringUtils.isNotBlank(passwd)&&StringUtils.isNotBlank(address)&&StringUtils.isNotBlank(email)) {
//				user = new User();
//				/*user.setUsername(username);
//				user.setPasswd(passwd);
//				user.setAddress(address);
//				user.setEmail(email);*/
//				count = userService.insertUser(user);
//				if (count>0) {
//					flag = SUCCESS;
//					logger.debug("添加数据成功：新增用户为：" + user.getUsername());
//				}
//			}else{
//				flag = ERROR;
//				logger.info("添加用户时,缺少参数：username="+username+" passwd="+passwd+" address="+address+" email="+email);
//			}
//		} catch (Exception e) {
//			flag = ERROR;
//			e.printStackTrace();
//			logger.warn("添加用户时，处理用户信息出现异常");
//		}
//		logger.debug("addUser 调用结束了");
//		return flag;
//	}
//
//	// 查询用户 需配合后台分页查询使用，否则会出问题
//	public String queryUser() throws Exception {
//
//		logger.debug("queryUser 被调用了");
//		List<User> userList = null;
//		HttpServletRequest request = null;
//		String flag = null;
//		
//		try {
//			request = ServletActionContext.getRequest();
//			userList = userService.getAllUser();
//			request.setAttribute("userList", userList);
//			flag = SUCCESS;
//		} catch (Exception e) {
//			flag = ERROR;
//			e.printStackTrace();
//			logger.warn("查询用户信息时发生异常");
//		}
//		
//		logger.debug("queryUser 被调用结束了");
//		return flag;
//	}
//
//	// 删除用户
//	public String deleteUser() throws Exception {
//		
//		logger.debug("deleteUser 被调用了");
//		String flag = null;
//		User user = null;
//		int count = 0;
//		
//		try {
//			if (id!=null) {
//				user = new User();
//				//user.setId(id);
//				count = userService.deleteUser(user);
//				if(count>0){
//					flag = SUCCESS;
//					logger.debug("删除用户成功，用户id为："+id);
//				}
//			}
//		} catch (Exception e) {
//			flag = ERROR;
//			e.printStackTrace();
//			logger.warn("删除用户时发生异常，用户id为："+id);
//		}
//		logger.debug("deleteUser 被调用结束了");
//		
//		return flag;
//	}
//
//	// 更新用户
//	public String updateUser() throws Exception {
//		logger.debug("updateUser 被调用了");
//		User user  = null;
//		String flag = null;
//		int count = 0;
//		try {
//			if (id!=null && StringUtils.isNotBlank(username)&&StringUtils.isNotBlank(passwd)&&StringUtils.isNotBlank(address)&&StringUtils.isNotBlank(email)) {
//				user = new User();
//				/*user.setId(id);
//				user.setUsername(username);
//				user.setPasswd(passwd);
//				user.setAddress(address);
//				user.setEmail(email);*/
//				count = userService.updateUser(user);
//				
//				if(count>0){
//					flag = SUCCESS;
//					logger.debug("更新用户成功，用户id为："+id);
//				}
//			}
//		} catch (Exception e) {
//			flag = ERROR;
//			e.printStackTrace();
//			logger.warn("更新用户时发生异常，用户id为："+id);
//		}
//		logger.debug("updateUser 被调用结束了");
//		return flag;
//	}
//	//查询用户详情
//	public String viewUser() throws Exception {
//		logger.debug("viewUser 被调用了");
//		User user = null;
//		String flag = null;
//		HttpServletRequest request  = null;
//		
//		try {
//			if (id!=null) {
//				user = userService.getUserById(id);
//				if(user!=null){
//					flag = SUCCESS;
//					request = ServletActionContext.getRequest();
//					request.setAttribute("user", user);
//				}
//			}
//		} catch (Exception e) {
//			flag = ERROR;
//			e.printStackTrace();
//			logger.warn("查询用户时发生异常，用户id为："+id);
//		}
//		logger.debug("viewUser 被调用结束了");
//		return flag;
//	}
//	
//	/**
//	 * 用户登录所对应方法
//	 * @return
//	 * @throws Exception
//	 */
//	public String userLogin() throws Exception {
//
//		logger.debug("userLogin 被调用了");
//		logger.debug("username:"+username);
//		logger.debug("passwd:"+passwd);
//		User user = null;
//		HttpServletRequest request = null;
//		HttpSession session = null;
//		String flag = null;
//		Map<String,User> returnMap = null;
//		Set returnSet = null;
//		
//		try {
//			if(validateLogin()){
//				
//				request = ServletActionContext.getRequest();
//				session = request.getSession();
//				returnMap = userService.userLogin(username, passwd);
//				
//				
//				if(returnMap!=null&&returnMap.size()>0){
//					returnSet = returnMap.entrySet();
//					for (Iterator iterator = returnSet.iterator(); iterator
//							.hasNext();) {
//						Map.Entry<String, User> result = (Map.Entry<String, User>) iterator.next();
//						String resultKey = result.getKey();
//						
//						if(StringUtils.isNotBlank(resultKey)&&UserOperationConstant.USER_IS_NOT_EXIST.equals(resultKey)){
//							flag = resultKey;
//							addFieldError("username",getText("userIsNotExist"));
//							break;
//						}
//						
//						if(StringUtils.isNotBlank(resultKey)&&UserOperationConstant.PASSWD_IS_ERROR.equals(resultKey)){
//							flag = resultKey;
//							addFieldError("passwd",getText("passwdIsError"));
//							break;
//						}
//						
//						if(StringUtils.isNotBlank(resultKey)&&UserOperationConstant.LOGINING_SUCCESS.equals(resultKey)){
//							flag = resultKey;
//							user = result.getValue();
//							session.setAttribute("user",user);
//							break;
//						}
//					}
//				}else{
//					flag = ERROR;
//				}
//			}else{
//				return UserOperationConstant.PARAM_IS_NOT_ENOUGH;
//			}
//		} catch (Exception e) {
//			flag = ERROR;
//			e.printStackTrace();
//			logger.warn("查询用户信息时发生异常");
//		}
//		
//		logger.debug("userLogin 被调用结束了");
//		return flag;
//	}
//
//	/**
//	 * 验证用户录入表单数据，在表单提交后，Struts2能自动调用，但无法区别是处理Login操作还是Register操作
//	 */
//	/*@Override
//	public void validate() {
//		
//		//如果用户名为空，或者空字符串
//		if(username==null || username.trim().equals("")){
//			//添加校验错误提示，使用getText方法来使提示信息国际化
//			addFieldError("username",getText("userNameRequired"));
//		}else if(username.length()<6 || username.length()>8){
//			addFieldError("username",getText("userNameLength"));
//		}
//		
//		if(passwd==null || passwd.trim().equals("")){
//			addFieldError("passwd",getText("userPasswdRequired"));
//		}else if(passwd.length()<6 || passwd.length()>8){
//			addFieldError("passwd",getText("userPasswdLength"));
//		}
//	}*/
//	
//	//校验用户登录录入的表单数据
//	public boolean validateLogin() {
//		
//		boolean flag = true;
//		
//		//如果用户名为空，或者空字符串
//		if(username==null || username.trim().equals("")){
//			//添加校验错误提示，使用getText方法来使提示信息国际化
//			addFieldError("username",getText("userNameRequired"));
//			
//			flag = false;
//		}else if(username.length()<6 || username.length()>8){
//			addFieldError("username",getText("userNameLength"));
//			flag = false;
//		}
//		
//		if(passwd==null || passwd.trim().equals("")){
//			addFieldError("passwd",getText("userPasswdRequired"));
//			flag = false;
//		}else if(passwd.length()<6 || passwd.length()>8){
//			addFieldError("passwd",getText("userPasswdLength"));
//			flag = false;
//		}
//		return flag;
//	}
//	
//	//校验用户注册录入的表单数据
//	public boolean validateRegisterParamIsEnough() {
//		
//		boolean flag = true;
//		
//		//如果用户名为空，或者空字符串
//		if(username==null || username.trim().equals("")){
//			//添加校验错误提示，使用getText方法来使提示信息国际化
//			addFieldError("username",getText("userNameRequired"));
//			flag = false;
//		}else if(username.length()<6 || username.length()>8){
//			addFieldError("username",getText("userNameLength"));
//			flag = false;
//		}
//		
//		if(passwd==null || passwd.trim().equals("")){
//			addFieldError("passwd",getText("userPasswdRequired"));
//			flag = false;
//		}else if(passwd.length()<6 || passwd.length()>8){
//			addFieldError("passwd",getText("userPasswdLength"));
//			flag = false;
//		}
//		
//		if(passwdRep==null || passwdRep.trim().equals("")){
//			addFieldError("passwdRep",getText("userPasswdRepRequired"));
//			flag = false;
//		}
//		/*else if(passwdRep.length()<6 || passwdRep.length()>8){
//			addFieldError("passwdRep",getText("userPasswdRepLength"));
//			flag = false;
//		}*/
//		
//		return flag;
//	}
//	
//	public boolean validateRegisterParamIsConsistent(){
//		boolean flag = true;
//		//判断两次输入的密码是否相等
//		if((passwd!=null&&!passwd.trim().equals(""))&&(passwdRep!=null&&!passwdRep.trim().equals(""))&&(passwd.length()>=6 &&passwd.length()<=8)&&(username.length()>=6 &&username.length()<=8)){
//			if(!passwd.equals(passwdRep)){
//				addFieldError("passwdRep",getText("PasswordIsNotConsistent"));
//				flag = false;
//			}
//		}
//		return flag;
//	}
//	
//	
//	/**
//	 * 用户注册所调用方法
//	 * @throws Exception
//	 */
//	public String userRegister() throws Exception{
//		logger.debug("userRegister 被调用了");
//		logger.debug("username:"+username);
//		logger.debug("passwd:"+passwd);
//		
//		User user = null;
//		HttpServletRequest request = null;
//		HttpSession session = null;
//		String flag = null;
//		Map<String,User> returnMap = null;
//		Set returnSet = null;
//		
//		try{
//			if(validateRegisterParamIsEnough()){
//				
//				if(validateRegisterParamIsConsistent()){
//					
//					user = new User();
//					user.setUsername(username);
//				//	user.setPasswd(passwd);
//					
//					request = ServletActionContext.getRequest();
//					session = request.getSession();
//					returnMap = userService.userRegister(user);
//					
//					if(returnMap!=null&&returnMap.size()>0){
//						returnSet = returnMap.entrySet();
//						for (Iterator iterator = returnSet.iterator(); iterator
//								.hasNext();) {
//							Map.Entry<String, User> result = (Map.Entry<String, User>) iterator.next();
//							String resultKey = result.getKey();
//							
//							if(StringUtils.isNotBlank(resultKey)&&UserOperationConstant.USER_IS_ALREADY_EXIST.equals(resultKey)){
//								flag = resultKey;
//								addFieldError("username",getText("userIsAlreadyExist"));
//								break;
//							}
//							
//							if(StringUtils.isNotBlank(resultKey)&&UserOperationConstant.REGISTER_SUCCESS.equals(resultKey)){
//								flag = resultKey;
//								user = result.getValue();
//								session.setAttribute("user",user);
//								break;
//							}
//						}
//					}else{
//						flag = ERROR;
//					}
//				}else{
//					flag = UserOperationConstant.PASSWORD_IS_NOT_CONSISTENT;
//				}	
//			}else{
//				flag = UserOperationConstant.PARAM_IS_NOT_ENOUGH;
//			}
//		}catch(Exception e){
//			flag = ERROR;
//			e.printStackTrace();
//			logger.warn("注册用户时发生异常");
//		}
//		return flag;
//	}
}
