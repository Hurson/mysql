<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title><s:text name="login.title"/></title>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		
<style type="text/css">
<!--
body,div,form{ color:#303435; font-size:12px; font-family:Arial, Helvetica, sans-serif;margin:0; padding:0;}
body {  background:url(images/land_01.png) repeat-y center #235b7f;overflow:hidden;}
input{ border:none}

.login_box{ background:url(images/land_0_04.png) no-repeat; width:519px; height:149px; margin: 200px auto 0 auto; padding-top:118px;}
.login_box div{ float:left; display:inline;}

.input_list{margin-left:50px;margin-left:150px !important;}

.input_list input{ margin-bottom:21px; background-color:#e6f0f4; height:24px; width:209px; margin-top:4px; font-size:14px; border:0px;padding:0px;}

.submit_btn { background:url(images/land.png) no-repeat; width:82px; height:82px; margin-left:34px; float:left}
.submit_btn:focus{ background:url(images/land_0.png) no-repeat;}
#username{ width:209px; height:20px; margin-top:6px;padding-top:1px;}
#password{ width:209px; height:20px;margin-top:5px;padding-top:1px;}

.erro_msg{margin-left:170px; clear:both; font-size:12px; color:#dddb24;;white-space:nowrap;}
-->
</style>

<script type="text/javascript" defer="defer">
	function login(){
		if(document.getElementById("username").value ==""){
			alert('<s:property value="%{getText('user.username.not.null')}"/>');
			return;
		}
		if(document.getElementById("password").value ==""){
			alert('<s:property value="%{getText('user.password.not.null')}"/>');
			return;
		}
		document.getElementById("btn_submit").style.cursor="wait";
		document.getElementById("loginForm").submit();
	}
</script>

	</head>
<body scroll="no">
<form action="login.action" method="post" id="loginForm">
<div class="login_box">
  <div class="input_list">
    <input type="text"  name="username" id="username" maxlength="20" onKeyDown="KeyDown()" /><br/>
    <input type="password" name="password" id="password" maxlength="20" onKeyDown="KeyDown()"/>
  </div>
  <div><input type="button" id="btn_submit" class="submit_btn" value="" onclick="javascript:login()" onmouseover="javascript:this.style.cursor='pointer'"/></div>
  <div class="erro_msg"><c:if test="${message != null}">${message}</c:if></div>
</div>
</form>
</body>
<script type="text/javascript">
document.forms[0].username.focus();
function KeyDown(){
	//var gk=event.keyCode;
	var gk;
	if(document.all){
	   gk = window.event.keyCode;
	}else{
	   gk = arguments.callee.caller.arguments[0].which;
	}
	   
	if(gk==13){   
		login();
	}   
} 
</script>
</html>
