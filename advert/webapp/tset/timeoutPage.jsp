<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String path = request.getContextPath();
%>
<script type="text/javascript" >
function user_logout(){
	var url  = getContextPath()+'/login.jsp';
	top.location = url;
		
	var userID = getCookie("COOKIE_USER_ID"); 
	if(userID != '' || userID != null){

		//删除Cookie内存放的User信息和sessionId
		DelCookie('COOKIE_USER_ID');
		DelCookie('COOKIE_USER_NAME');
		DelCookie('COOKIE_SESSION_ID');
	}
} 

/**
 * 获取上下文路径
 */ 
function getContextPath() {
	var contextPath = document.location.pathname;
	var index = contextPath.substr(1).indexOf("/");
	contextPath = contextPath.substr(0, index + 1);
	delete index;
	return contextPath;
}
</script>


<head>
</head>
<body onload="user_logout();" class="headBody">
</body>
</html>
