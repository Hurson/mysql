<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="css/platform.css" type="text/css" />
<script language="javascript" type="text/javascript" src="js/jquery-1.4.4.js"></script>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
});
</script>
<style>
.treven{ background:#FFFFFF;}
.trodd{ background:#f6f6f6;}
</style>

<title>资产管理 </title>
</head>

<body>
  <form action="" namespace="/" method="post" ID = "pageForm">

<table cellpadding="0" cellspacing="0" border="0" width="100%">

	<tr>
		<td>
		<table cellpadding="0" cellspacing="0" border="0" width="100%"
			class="tab_right">
			<tr>
				<td>工作台</td>
				<td>客户</td>
				<td>日程行动</td>
				<td>销售机会</td>
				<td>订单管理</td>
				<td>客服中心</td>
				<td>财务中心</td>
				<td>营销中心</td>
				<td>人力资源中心</td>
				<td>数据统计</td>
				<td>信息门户管理</td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td style=" padding:4px;">
		<table cellpadding="0" cellspacing="1" width="100%" class="tablea"  >
	<tr>
		<td class="padding-left:8px; background:url(images/menu_righttop.png) repeat-x; text-align:left; height:26px;" colspan="99">
			<span>广告资产管理</span>	
		</td>
	  </tr>
		<tr>
			<td class="td_label">资产名称：</td>
			<td class="td_input"><s:textfield  name="" id="" class="e_input"  theme="simple" /></td>
			<td class="td_label">资产类别：</td>
			<td class="td_input">
			<select id="selectId" onchange="" name="pbTaxBokuan.renderDate" >
				<option value="">请选择</option>
				<option value="01">图片</option>
				<option value="02">视频</option>
				<option value="03">文字</option>
				</select>
			</td>
			<td class="td_label">资产描述：</td>
			<td class="td_input">
			    <s:textfield theme="simple" name="" id="" class="e_input"  />
			</td>
		</tr>
		<tr>
			<td class="td_label">所属广告商：</td>
			<td class="td_input">
			    <s:textfield  theme="simple" name="" id="" class="e_input"  />
			</td>
			<td class="td_label">所属合同号：</td>
			<td class="td_input">
			    <s:textfield theme="simple" name="" id="" class="e_input"  />
			</td>
			<td class="td_label">创建时间：</td>
			<td class="td_input">
			    <s:textfield theme="simple" name="" id="" class="e_input" onclick="WdatePicker()" readOnly="true" /> 
			</td>
		</tr>
	   </table>
	</td>
	</tr>
    </from>
 </body>
</html>














