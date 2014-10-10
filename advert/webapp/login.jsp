<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*,com.opensymphony.xwork2.util.*" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>互动平台系统登陆页面</title>
<link href="<%=path%>/css/logn.css" type="text/css" rel="stylesheet" />
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script>

function firstSubmit(){
	var loginname = $('#loginname').val();
	var password = $('#pwd').val();
	if($.trim(loginname)==''){
		alert("请输入登录名！");
		return ;
	}
	if($.trim(password)==''){
		alert("请输入密码！");
		return ;
	}

	$.ajax({
		url:'userCheck.do?method=userCheck',
		type:'post',
		data:{loginname:loginname,password:password},
        success:function(responseText){
			var megs = eval(responseText);
			if(megs.result == 1){
				alert("用户名不存在！");
			}else if(megs.result == 2){
				alert("密码错误！");
			}else if(megs.result == 3){
				alert("用户被禁用！");
			}else if(megs.result == 4){
				var userId = megs.userId;
				window.location.href="login.do?method=userLogin&userId="+userId;
			}else if(megs.result == 0){
				var userId = megs.userId;
				window.location.href="login.do?method=userLogin&userId="+userId;
			}
			
		}, 
		error:function(e){
			alert("服务器异常，请稍后重试！");
		},
        dataType:'json' 
	});
}
/**
 * 回车
 */
function keyCodeShow(e){
	var evt = e || window.event;
	//alert(evt.keyCode);
	if(evt.keyCode == 13){
		firstSubmit();
	};
}
document.onkeydown=keyCodeShow;


</script>
</head>
<body>
<div class="divMain">
<div class="main"><img src="<%=path%>/images/imessge.png" /></div>
</div>
<div class="inputAdmin"><br />
登录：<input id="loginname" name="" type="text" />
&nbsp;&nbsp;密码：<input id="pwd" name="" type="password" />&nbsp;&nbsp;
<input type="button" class="btn_logn" onclick="firstSubmit()"/></div>
</body>
</html>