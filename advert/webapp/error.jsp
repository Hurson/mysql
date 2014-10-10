<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" />
<title>系统出错提示</title>
<style>
	#tip_window {
		width: 388px;
		height: 178px;
		overflow: hidden;
		position: absolute;
		left: 0;
		top: 0;
		z-index: 51;
		background: #FFF;
		border: 1px solid #86B4E5;
	}
	
	#tip_failth {
		font-size: 14px;
		font-family: "微软雅黑";
		color: #C40000;
		padding: 5px 20px 5px 10px;
		text-align: center;
		overflow: auto;
		overflow-x: hidden;
	}
	
	#btn_window {
		text-align: center;
		margin: 0 10px;
		padding: 10px 0 0 0;
		border-top: 1px dashed #9E9E9E;
	}
</style>
</head>
<body>
<div id="tip_window" style="left:30%; margin-top:20%;">
<h2>系统出错提示</h2>
<div id="tip_failth">
<!-- <s:property value="%{exception.message}"/>  -->

</p>
<h3>详细信息</h3>
<p>
 <!-- <s:property value="%{exceptionStack}"/> --> 
	系统出错：请联系管理员！
</div>
<div id="btn_window"><button onclick="history.go(-1);">返回</button></div>
</div>
</body>
</html>