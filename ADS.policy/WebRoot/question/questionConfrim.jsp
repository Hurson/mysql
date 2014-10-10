<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="../WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String backurl = (String)request.getParameter("backurl");
String retvalue = (String)request.getParameter("retvalue");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'questionConfrim.jsp' starting <%=backurl%> page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
<!--
.STYLE3 {font-size: 28px}
.STYLE4 {font-size: 24px}
.STYLE5 {font-size: 18px}
-->
</style>
  </head>
  <script>
 function redirectBackurl(){
	 
	// alert("path:"+document.getElementById("backurl").value);
	window.location.href= document.getElementById("backurl").value;
	
 }
  function showMsg(){
	 
	// alert("path:"+document.getElementById("backurl").value);
	// window.location.href= document.getElementById("backurl").value;
	if (2==<%=retvalue%>)
	{
		
	     document.getElementById("msg2").style.display="";
	}if (3==<%=retvalue%>)
	{
		
	     document.getElementById("msg3").style.display="";
	}
	if (4==<%=retvalue%>)
	{
		
	     document.getElementById("msg4").style.display="";
	}
 }
   </script>
  <body onload="showMsg();"  style="background:url(../images/bg.jpg)">
  
   <div id="messageDiv" style="margin:20px auto;  margin-top: 300px;color: red;font-size: 14px;font-weight: bold;text-align:center; ">
        <span id="msg2" class="STYLE5" style="display:none;color:red;">您回答此问卷已超过限制次数</span>
        <span id="msg3" class="STYLE5" style="display:none;color:red;">此问卷回答已超过总次数</span>
        <span id="msg4" class="STYLE5" style="display:none;color:red;">此问卷当前无定单</span>
        <p>
        <input type="button" class="STYLE5" onclick="redirectBackurl();" value=" 确  认 "/>
	    <input type="hidden" id="backurl" name="backurl" value='<%=backurl%>'>
</div> 
   
  </body>
</html>
