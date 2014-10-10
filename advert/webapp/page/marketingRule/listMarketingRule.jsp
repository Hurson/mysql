<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tags/fmt.tld" prefix="fmt"%>
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

<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery-ui-timepicker-addon.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />
<link rel="stylesheet" href="<%=path%>/css/Pager.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right_new1.css" type="text/css" />

<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery-1.4.4.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.core.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.widget.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.tabs.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.accordion.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery-ui-timepicker-addon.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery-ui-timepicker-zh-CN.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/util/tools.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/util/util.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/marketingRule/listMarketingRule.js"></script>

<title>营销规则列表</title>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
    $("#queryMarketingRuleButton").click(function(){
     	$("#queryMarketingRuleForm").submit();
     });
     $("#startTime").timepicker({
    	showSecond: true,
        timeFormat: 'HH:mm:ss',
        onSelect: function (selectedDateTime){
			
		}
    }); 
    $("#endTime").timepicker({
    	showSecond: true,
        timeFormat: 'HH:mm:ss',
        onSelect: function (selectedDateTime){
			
		}
    }); 
     
});

</script>
<style>
	.easyDialog_wrapper{ width:800px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
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
<body>
<div>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td style="padding:2px;">
			<form action="listMarketingRule.do?method=listMarketingRule"   id="queryMarketingRuleForm" name="queryMarketingRuleForm" method="post" >
					<table cellpadding="0" cellspacing="1" width="100%" class="tablea">
					<tr>
						<td class="formTitle" colspan="99">
							 <span>营销规则管理</span></td>
					</tr>
					<tr>
						<td class="td_label">营销规则名称： </td>
						<td class="td_inputz" colspan="3"><input id="ruleName" name="ruleName" type="text" class="e_input"  value="${ruleName}" /></td>
						<td class="td_label">广告位： </td>
						<td class="td_input">
							<select id="positionId" name="positionId" onchange="selectOptionVal(this.id)"  class="e_select" onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'" >
								<option id="ad_id" value="-1">请选择...</option>
								<c:forEach items="${postionList}" var="postion">
									<option  value="${postion.id }">${postion.positionName }</option>
								</c:forEach>
							</select>		
						</td>
						<td class="td_label">开始时间：</td>
						<td class="td_input">
							<input id="startTime" name="startTime" value="${startTime}" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
						</td>
						<td class="td_label">结束时间：</td>
						<td class="td_input">
							<input id="endTime" name="endTime" value="${endTime}" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
						</td>
					</tr>
		
					<tr>
						<td class="formBottom" colspan="99">
							<input id="queryMarketingRuleButton" name="queryMarketingRuleButton" type="button" title="查看" class="b_search" value="" onfocus=blur()/>
							<input name="Submit" type="button" title="添加" class="b_add" value="" onclick="addRule()" onfocus=blur() /> 
						</td>
					</tr>
				</table>
		</form>
		</td>
	</tr>
	<tr>
		<td style="padding:2px;">
			<div>  
				<table cellpadding="0" cellspacing="1" width="100%"
			class="taba_right_list" id="bm">
					<tr>
						<td colspan="23" style="padding-left: 8px; background: url(<%=path %>/images/menu_righttop.png) repeat-x; text-align: left; height: 26px;"><span>·营销规则列表</span></td>
					</tr>
					<tr>
						<td width="5%" height="26px" align="center">序号</td>
						<td width="10%">规则名称</td>
						<td width="11%">开始时间</td>
						<td width="11%">结束时间</td>
						<td width="11%">创建时间</td>
						<td width="5%">状态</td>
						<td width="15%" >操作</td>
					</tr>
					
					
					
					
					<c:set var="index" value="0" />
			<c:forEach items="${rules}" var="rule">
				<tr>
					<c:set var="index" value="${index+1 }" />
					<td align="center" height="26"><c:out value="${index }" /></td>
					<td><c:out value="${rule.ruleName}" /></td>
					<td><c:out value="${rule.startTime}" /></td>
					<td><c:out value="${rule.endTime}" /></td>
					<td><c:out value="${rule.createTime}" /></td>
					<td><c:out value="${rule.state}" /></td>
					<td><input name="Submit" type="button" class="button_halt"
						value="" title="编辑" onfocus=blur() onclick="updateRule('${rule.ruleId}')" /> <input
						name="Submit" type="button" class="button_delete" value=""
						title="删除" onfocus=blur()
						onclick="deleteRule('${rule.ruleId}')" />
						<input type="button" title="上线"  onclick="upLine('${rule.ruleId}')" 
						style="background:url(<%=basePath%>/images/1.gif); border-style:none; background-repeat:no-repeat;  
						width:33px; height:17px;"/>
				  	<input type="button" title="下线" onclick="downLine('${rule.ruleId}')" 
				  	style="background:url(<%=basePath%>/images/2.gif); border-style:none; background-repeat:no-repeat;  
				  	width:33px; height:17px;" />
						</td>
				</tr>
			</c:forEach>
			<c:if test="${index<page.pageSize}">
				<c:forEach begin="${index}" end="${page.pageSize-1}" step="1">
					<tr>
						<td align="center" height="26">&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</c:forEach>
			</c:if>
			<tr>
				<td height="34" colspan="12"
					style="background: url(<%=path %>/images/bottom.jpg) repeat-x; text-align: right;">
					<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp; 
					<c:choose>
						<c:when test="${page.pageNo==1&&page.totalPage!=1}">
							<a href="listMarketingRule.do?pageNo=${page.pageNo+1 }&positionId=${positionId}&ruleName=${ruleName}&viewType=${viewType}">下一页</a>&nbsp;&nbsp;
							<a href="listMarketingRule.do?pageNo=${page.totalPage}&positionId=${positionId}&ruleName=${ruleName}&viewType=${viewType}">末页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
							<a href="listMarketingRule.do?pageNo=1&positionId=${positionId}&ruleName=${ruleName}&viewType=${viewType}">首页</a>&nbsp;&nbsp;
							<a href="listMarketingRule.do?pageNo=${page.pageNo-1 }&positionId=${positionId}&ruleName=${ruleName}&viewType=${viewType}">上一页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
							<a href="listMarketingRule.do?pageNo=1&positionId=${positionId}&ruleName=${ruleName}&viewType=${viewType}">首页</a>&nbsp;&nbsp;
							<a href="listMarketingRule.do?pageNo=${page.pageNo-1 }&positionId=${positionId}&ruleName=${ruleName}&viewType=${viewType}">上一页</a>&nbsp;&nbsp;
							<a href="listMarketingRule.do?pageNo=${page.pageNo+1 }&positionId=${positionId}&ruleName=${ruleName}&viewType=${viewType}">下一页</a>&nbsp;&nbsp;
							<a href="listMarketingRule.do?pageNo=${page.totalPage}&positionId=${positionId}&ruleName=${ruleName}&viewType=${viewType}">末页</a>&nbsp;&nbsp;
						</c:when>
					</c:choose>
				</td>
			</tr>
					
					
					
		
					
				</table>
			</div>
		</td>
	</tr>
</table>
</div>
<div id="columnDiv" class="showDiv" style="display: none;">
<table cellpadding="0" cellspacing="0"
	style="margin-top: 0; width: 777px; height: 336px; border: 1px solid #cccccc; font-size: 12px;">
	<tr class="list_title">
		<td style="border: 0;">绑定的栏目</td>
		<td style="border: 0;" align="right"><a href="#" onclick="closeSelectDiv('columnDiv');">关闭</a></td>
	</tr>
	<tr>
		<td colspan='2'>
		<div id="columnInfo"></div>
		</td>
	</tr>
	<tr>
		<td colspan="2" class="td_bottom">
		<div class="buttons">
		<a href="#" onclick="closeSelectDiv('columnDiv');">返回</a></div>
		</td>
	</tr>
</table>
</div>
<div id="bg" class="bg" style="display: none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>
</body>
</html>