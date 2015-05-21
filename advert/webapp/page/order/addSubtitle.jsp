<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type='text/javascript' src="<%=path%>/js/new/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" />
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/preview.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/common.js"></script>
<script type='text/javascript' src='<%=path %>/js/util/jscolor/jscolor.js'></script>
<title>新增字幕广告订单</title>
<style>
	.ggw {
		width: 100%;
		height: 68px;
	}
	.ggw li {
		background: #efefef;
		font-weight: bold;
		width: 100%;
		height: 25px;
	}
	.e_input_add {
		background:url(<%=path%>/images/add.png) right no-repeat #ffffff;
	}
	
	.e_input_time{
		background:url(<%=path%>/js/new/My97DatePicker/skin/datePicker.gif) right no-repeat #ffffff;
	}
</style>
<script type="text/javascript">

	var areaCodes =  "${subtitle.areaCodes}";
	
	$(function(){
		var areaCodeArray = areaCodes.replace(/\s/ig,'').split(",");
		for(var i = 0; i < areaCodeArray.length; i++){
			$($("#area" + areaCodeArray[i])[0]).attr("checked",true);
		}
	})

	function checkInt(input){
		var reg1 = /^\d+$/;
		return reg1.test(input);
	}
	
	function checkSubtitle(){
		
		var timeout = $("#timeout").val();
		if(!checkInt(timeout)){
			alert("请输入整数");
			$("#timeout").focus();
			return false;
		}
		var fontSize = $("#fontSize").val();
		if(!checkInt(fontSize)){
			alert("请输入整数");
			$("#fontSize").focus();
			return false;
		}
		var bgX = $("#bgX").val();
		if(!checkInt(bgX)){
			alert("请输入整数");
			$("#bgX").focus();
			return false;
		}
		var bgY = $("#bgY").val();
		if(!checkInt(bgY)){
			alert("请输入整数");
			$("#bgY").focus();
			return false;
		}
		var bgWidth = $("#bgWidth").val();
		if(!checkInt(bgWidth)){
			alert("请输入整数");
			$("#bgWidth").focus();
			return false;
		}
		var bgHeight = $("#bgHeight").val();
		if(!checkInt(bgHeight)){
			alert("请输入整数");
			$("#bgHeight").focus();
			return false;
		}
		var showSpeed = $("#showSpeed").val();
		if(!checkInt(showSpeed)){
			alert("请输入整数");
			$("#showSpeed").focus();
			return false;
		}
		var word = $("#word").val().replace(/(^\s*)|(\s*$)/g, "");
		$("#word").val(word);
		if(''==word){
			alert("请输入字幕内容");
			$("#word").focus();
			return false;
		}
		var wordCount = word.length;
		if(wordCount > 80){
			alert("字幕内容不能超过80");
			$("#word").focus();
			return false;
		}
		return true;
	}
	
	function saveSubtitle(){
		if(checkSubtitle()){
			$("#saveForm").submit();
		}
	}
	
	function pushSubtitle(){
		if(checkSubtitle()){
			$("#saveForm").attr("action", "saveAndPushSubtitle.do");
			$("#saveForm").submit();
		}
		
	}

</script>
</head>

<body class="mainBody">
<form action="saveSubtitle.do" method="post" id="saveForm">

<div class="detail">
<table cellspacing="1" class="searchList" align="left">
	<tr class="title">
		<td colspan="4">字幕广告订单添加</td>
	</tr>
	<tr>
		<td width="15%" align="right">控制显示：</td>
		<td width="35%">
			<input type="hidden" name="subtitle.id" value="${subtitle.id}" />
			<select name="subtitle.actionType" style="width: 115px">
	          	<option value="1" <c:if test="${subtitle.actionType=='1'}">selected</c:if> >显示</option>
			  	<option value="0" <c:if test="${subtitle.actionType=='0'}">selected</c:if> >关闭</option>
      		</select>
		</td>
		<td width="15%" align="right"><span class="required">*</span>字幕持续时间：</td>
		<td width="35%"><input type="text" id="timeout" name="subtitle.timeout" value="${subtitle.timeout}" />(单位： 毫秒  0表示一直显示)</td>
	</tr>
	
	<tr>
		<td align="right"><span class="required">*</span>字体大小：</td>
		<td><input type="text" id="fontSize" name="subtitle.fontSize" value="${subtitle.fontSize}" /></td>
		
		<td align="right"><span class="required">*</span>字体颜色</td>
		<td><input class="color" name="subtitle.fontColor" value="${subtitle.fontColor}" />(单击选择颜色)</td>
	</tr>
	
	<tr>
		<td align="right"><span class="required">*</span>区域-左：</td>
		<td><input type="text" id="bgX" name="subtitle.bgX" value="${subtitle.bgX}" /></td>
		<td align="right"><span class="required">*</span>区域-上：</td>
		<td><input type="text" id="bgY" name="subtitle.bgY" value="${subtitle.bgY}" /></td>
	</tr>
	
	<tr>
		<td align="right"><span class="required">*</span>区域-宽：</td>
		<td><input type="text" id="bgWidth" name="subtitle.bgWidth" value="${subtitle.bgWidth}" /></td>
		<td align="right"><span class="required">*</span>区域-高：</td>
		<td><input type="text" id="bgHeight" name="subtitle.bgHeight" value="${subtitle.bgHeight}" /></td>
	</tr>
	
	<tr>
		<td align="right"><span class="required">*</span>区域-颜色：</td>
		<td><input class="color"  name="subtitle.bgColor" value="${subtitle.bgColor}" />(单击选择颜色)</td>
		<td align="right"><span class="required">*</span>滚动速度：</td>
		<td><input type="text" id="showSpeed" name="subtitle.showSpeed" value="${subtitle.showSpeed}" />(0表示静止)</td>
	</tr>
	
	<tr>
		<td align="right">字幕内容：</td>
		<td colspan="3"><textarea id="word" name="subtitle.word" cols="50" rows="3">${subtitle.word}</textarea> <span class="required">(长度在1-80字之间)</span></td>
	</tr>
	
	<tr>
		<td align="right">选择区域：</br> <span class="required">(若不选则全部投放)</span></td>
		<td id="area_checkbox" colspan="3">
			  <c:forEach items="${areaList}" var="areaEntity" varStatus="status">
			       &nbsp;&nbsp;<input type="checkbox" id="area${areaEntity.areaCode}" name="subtitle.areaCodes" value="${areaEntity.areaCode}"/> ${areaEntity.areaName}
		       	   <c:if test="${status.count%10==0}"></br></c:if>
		       </c:forEach>
		</td>
		
	</tr>
		
</table>
<div style="margin-left:50px;">
	<input type="button" onclick="saveSubtitle();" class="btn" value="保存" /> &nbsp;&nbsp; 
	<input type="button" onclick="pushSubtitle();" class="btn" value="投放" /> &nbsp;&nbsp; 
	<input type="button" onclick="javascript:history.back(-1);" class="btn" value="取消" />
</div>
</div>


</form>
</body>
</html>