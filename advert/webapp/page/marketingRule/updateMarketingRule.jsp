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
<input id="projetPath" type="hidden" value="<%=path%>"/>
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/Pager.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery-ui-timepicker-addon.css" type="text/css" />

<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.core.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.widget.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.tabs.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.accordion.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/util/tools.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/util/util.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/marketingRule/addMarketingRule.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type="text/javascript">
	var editorFlag  = "update";
	$(function(){
		$("#system-dialog").hide();
		var ruleList = "{'id':'${rule.id}','ruleId':'${rule.ruleId}','ruleName':'${rule.ruleName}','startTime':'${rule.startTime}','endTime':'${rule.endTime}','positionId':'${rule.positionId}','bindingArea':[";
		
			<c:choose>
			<c:when test="${!empty ruleMap}">
				<c:forEach var="entry" items="${ruleMap}">
				ruleList+="{"+split('${entry.key}')+"'channel':[";
					<c:forEach var="channel" items="${entry.value}" varStatus="status">
						ruleList+="{'channelId':'${channel.id}','channelName':'${channel.channelName}'}";
						<c:choose>
							<c:when test="${status.last}">
								
							</c:when>
							<c:otherwise>
								ruleList+=',';
							</c:otherwise>
						</c:choose>						
					</c:forEach>
					ruleList+="]";					
				</c:forEach>
			</c:when>			
		</c:choose>
		
	    ruleList+="}],'bindingPosition':[]}";
		
		
		
	    
		if(!$.isEmptyObject(ruleList)){
			ruleList=eval("("+ruleList+")");
			//1、根据从库中查询出合同相关信息初始化至alreadyFillInForm中
			alreadyFillInForm=deepCopy(ruleList);
			if((!$.isEmptyObject(alreadyFillInForm))){
				//1-1、生成标记值，并将标记值初始化入各自的属性值中
				var areaList = alreadyFillInForm.bindingArea;
				var timeAreaFlag = '';
				if(!$.isEmptyObject(areaList)){
					$(areaList).each(function(index, item){
						timeAreaFlag=new Date().getTime()+'_'+item.id;
						item.areaIndexFlag=timeAreaFlag;
					});
				}
				showPositionList4Page(2,7);
			}
		}
	});
	
	function split(param){
		var array = param.split('#');
		return "'id':'"+array[0]+"','areaName':'"+array[1]+"',";	
	}
</script>
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
							<span>修改营销规则</span>
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
						<td class="td_label" style="width:270px"></td>
					</tr> 
					
					<tr>
						<td class="td_label" style="width:270px">选择广告位：</td>
						<td class="td_input">
							<input id="choosePosition" type="button" value="查询广告位" />
							<input id="oldPositionId" type="hidden" value="${rule.positionId}"/>
						</td>
						<td class="td_label" style="width:270px">已选择的广告位：</td>
						<td class="td_input">
							<input id="position" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"  maxlength="80" readonly="readonly" value="${rule.positionName}" />
							<input id="positionId" type="hidden" />
							<input id="isChannel" type="hidden" />
							<input id="isAllTime" type="hidden" />
						</td>
						<td class="formBottom">
							<input id="findRule" type="button" value="查看已有规则" onclick=''/>
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
						<td class="td_label" style="width:270px"></td>
					</tr>
					
					<tr>
						<td class="td_label" style="width:270px">选择地区：</td>
						<td class="td_input">
							<input id="chooseArea" type="button" value="查询地区" />
						</td>
						<td class="td_label"></td>
						<td class="td_input">
						</td>
						<td class="td_label" style="width:270px"></td>
					</tr>	
					
					<tr>
						<td colspan="5">
							<div>
								<table cellpadding="0" cellspacing="0" border="0" width="100%">
									<tr>
										<td colspan="23" style="padding-left: 8px; background: url(<%=path %>/images/menu_righttop.png) repeat-x; text-align: left; height: 26px;"><span>·地区列表</span></td>
									</tr>
									<tr id="bindingPositionArea">
										<td>地区名称</td>
										<td>频道</td>
									</tr>
									
								</table>
							</div>	
						</td>
					</tr>
					
					<tr>
						<td class="formBottom" colspan="99">
							<input id="updateRuleButton" 
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