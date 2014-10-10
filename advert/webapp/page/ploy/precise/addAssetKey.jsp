﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<base target="_self" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />
<link href="<%=path%>/css/Pager.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right_new1.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css">
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>


<title>策略维护</title>
<script>

function query() {
        document.forms[0].submit();
        
       /// $("queryForm").submit();
    }
function selectData(){
    	//parent.document.request.getAttribute("areas","10,12");
    
    	var assetKey=document.getElementById("assetKey").value;
    	if (isEmpty(assetKey))
    	{
    		alert("请选择关键字");
    		return ;
    	}
    	　　var optionSelelct =window.dialogArguments.document.getElementById("precise.assetKey");
    	　　var flag=false;
    		
	       for (var j=0;j<optionSelelct.length;j++)
	        {
	        	if (optionSelelct.options[j].value==assetKey)
	        	{
	        		flag =true;
	        	}
	        	//alert(optionSelelct.length);
	        }
	       if (flag==false)
	        {
	        	window.dialogArguments.addselectData("precise.assetKey",assetKey,assetKey);
	        }
	 
    	//window.dialogArguments.document.getElementById("precise.assetId").options.add(new Option("文本","111"));
    
		//parent.easyDialog.close();
    }
</script>
<style>
	.easyDialog_wrapper{ width:800px;height:580px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
<style>
.treven {
	background: #FFFFFF;
}

.trodd {
	background: #f6f6f6;
}
</style>

</head>
<body class="mainBody">
 <form action="#" method="post" id="queryForm">

<div class="search">
<div class="path">首页 >> 投放策略管理 >> 精准维护>>添加关键字</div>
<div class="searchContent" >
<table cellspacing="1" class="searchList">
    <tr>
    <td class="searchCriteria">
      <span>关键字：</span><input onkeypress="return validateSpecialCharacter();" maxlength="30" type="text" name="assetKey" id="assetKey" />
	  <input type="button" value="添加" class="btn" onclick="javascript:selectData();"/>&nbsp;&nbsp;
        <input type="button" value="返回" class="btn" onclick="window.close();"/>
  </tr>
</table>

</div>
</div>
</form>
</body>

</html>