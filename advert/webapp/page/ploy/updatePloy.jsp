<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/Pager.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery-ui-timepicker-addon.css" type="text/css" />

<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/validate/jquery.validate.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/validate/jquery.validate.messages_cn.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.core.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery-ui-timepicker-addon.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker-zh-CN.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery-ui-timepicker-zh-CN.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/marketingRule/addMarketingRule.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>

<title>广告系统</title>
<style>
	.easyDialog_wrapper{ width:1000px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
<style>
.treven{ background:#FFFFFF;}
.trodd{ background:#f6f6f6;}
</style>

</head>
<input id="projetPath" type="hidden" value="<%=path%>"/>
<body onload="">
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td style=" padding:4px;">
				<table cellpadding="0" cellspacing="1" width="100%" class="tablea" >
					<tr>
						<td class="formTitle" colspan="99">
							<span>添加营销规则</span>
						</td>
					</tr>
					<input id="id" type="hidden" value="${rule.id}"/>
					<tr>
						<td class="td_label" style="width:270px">规则ID：</td>
						<td class="td_input">
							<input id="ruleId" name="rule.id" value="${rule.ruleId}" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"  maxlength="80"/>	
						</td>
						<td class="td_label" style="width:270px">规则名称：</td>
						<td class="td_input">
							<input id="ruleName" name="rule.name" value="${rule.ruleName}" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"  maxlength="80"/>
						</td>
					</tr> 
					
					<tr>
						<td class="td_label">开始时间：</td>
						<td class="td_input">
							<select id="startTime"   onchange="selectOptionVal(this.id)"  class="e_select" onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'" >
								<option id="ad_id" value="${rule.startTime}">${rule.startTime}</option>
								<c:forEach items="${startTimeList}" var="startTime">
									<option  value="${startTime.areaName }">${startTime.ruleName }</option>
								</c:forEach>
							</select>		
						</td>
						<td class="td_label">结束时间：</td>
						<td class="td_input">
							<select id="endTime"   onchange="selectOptionVal(this.id)"  class="e_select" onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'" >
								<option id="ad_id" value="${rule.endTime}">${rule.endTime}</option>
								<c:forEach items="${endTimeList}" var="endTime">
									<option  value="${endTime.channelName }">${endTime.ruleName }</option>
								</c:forEach>
							</select>		
						</td>
					</tr>
					
					<tr>
						<td class="td_label" style="width:270px">选择广告位：</td>
						<td class="td_input">
							<input id="choosePosition" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${rule.positionName}"/>
							<input id="oldPositionId" type="hidden" value="${rule.positionId}"/>
						</td>
						<td class="td_label"></td>
						<td class="td_input">
						</td>
					</tr>	
					
					<tr id="positionChoosed">
						<td id="position" colspan="4">
						</td>
					</tr>
					
					<tr>
						<td class="td_label" style="width:270px">选择地区：</td>
						<td class="td_input">
							<input id="chooseArea" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${rule.areaName}"/>
							<input id="oldAreaId" type="hidden" value="${rule.areaId}"/>
						</td>
						<td class="td_label"></td>
						<td class="td_input">
						</td>
					</tr>	
					
					<tr id="areaChoosed">
						<td id="area" colspan="4">
						</td>
					</tr>
					
					<tr>
						<td class="td_label" style="width:270px">选择频道：</td>
						<td class="td_input">
							<input id="chooseChannel" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${rule.channelName}"/>
							<input id="oldChannelId" type="hidden" value="${rule.channelId}"/>
						</td>
						<td class="td_label"></td>
						<td class="td_input">
						</td>
					</tr>	
					
					<tr id="channelChoosed">
						<td id="channel" colspan="4">
						</td>
					</tr>				
					
					<tr>
						<td height="26px" colspan="23" style="text-align: right;">
							<div id="pageOperationDiv"></div>						
						</td>
					</tr>
					
					<tr>
						<td class="formBottom" colspan="99">
							<input id="addRuleButton" 
							type="button"
							class="b_edit" 
							onclick="saveUpdateRule()"
							onfocus="blur()"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
<div id="system-dialog" title="友情提示">
  <p>
    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>
    <span id="content"></span>
  </p>
</div>
</body>
</html>