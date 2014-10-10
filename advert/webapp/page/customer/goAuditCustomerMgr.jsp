<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tags/fmt.tld" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="css/platform.css" type="text/css" />

<script type="text/javascript" src="<%=basePath%>/js/customerJS.js"></script>

<link rel="stylesheet" href="<%=basePath%>/css/popUpDiv.css" type="text/css" />

<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/contract/listContract.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js"></script>
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css">


<title>客户管理</title>
<script>

	function go(){
		var startTime = document.getElementById("startTime").value;
		var endTime =  document.getElementById("endTime").value;
		
		var sTime = new Date(startTime);
		var eTime = new Date(endTime);
		
		if(startTime !="" && endTime == ""){
			alert("创建结束时间不能为空！");
			document.getElementById("startTime").value="";
			return false;
		}else if(startTime == "" && endTime != ""){
		    alert("创建开始时间不能为空！");
		    document.getElementById("endTime").value="";
		    return false;
		}else if(startTime > endTime){
			alert("您输入的创建开始时间大于结束时间，请确认后再输入！");
			document.getElementById("startTime").value="";
			document.getElementById("endTime").value="";
			return false;
		}else{
		  document.getElementById("form").submit();
		}
	}



	$(function(){   
	    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
	    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
	});
</script>
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

	<div id="materialDiv" class="showDiv" style="display:none">
	<table cellpadding="0" cellspacing="0" style="margin-top: 0; width: 760px; height: 336px; border: 1px solid #cccccc; font-size:12px; background-color:#cccccc;">
		<tr class="list_title_x">
			<td style="border: 0; padding-left:5px;" align="center">广告商审核</td>
		    <td style="border: 0;" align="right"></td>
		</tr>
		<tr>
			<td colspan='2' valign="top">
				<div id="materialInfo">
				
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="td_bottom">
				<div class="buttons">
					<a href="javascript:submitOpintions()">确认</a>
					<a href="javascript:closeSelectDiv()">返回</a>
				</div>		
			</td>
		</tr>
	</table>
</div> 
  
  <div id="noMaterialDiv" class="showDiv" style="display:none">
	<table cellpadding="0" cellspacing="0" style="margin-top: 0; width: 760px; height: 336px; border: 1px solid #cccccc; font-size:12px;background-color:#cccccc; ">
		<tr class="list_title">
			<td style="border: 0; padding-left:5px;" align="center">广告商审核</td>
		    <td style="border: 0;" align="right"></td>
		</tr>
		<tr>
			<td colspan='2' valign="top">
				<div id="noMaterialInfo">
				
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="td_bottom">
				<div class="buttons">
					<a href="javascript:noSubmitOpintions()">确认</a>
					<a href="javascript:noCloseSelectDiv()">返回</a>
				</div>		
			</td>
		</tr>
	</table>
</div> 
  
<div id="bg" class="bg" style="display:none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>


<form id="form" action="adCustomerMgr_listAudit.do"  method="post" name="adCustomerMgr_listAudit"> 
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
		<td style="padding: 4px;">
		<table cellpadding="0" cellspacing="1" width="100%" class="tablea">
			<tr>
				<td class="formTitle" colspan="99"><span>客户详细信息</span></td>
			</tr>
			<tr>
				<td class="td_label">广告商名称：</td>
				<td class="td_input"><input type="text" name="customerBackUp.advertisersName" value="${advertisersName}" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" /></td>
				<td class="td_label">客户代码</td>
				<td class="td_input"><input type="text" name="customerBackUp.clientCode" value="${clientCode}" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" /></td>
			</tr>
			<tr>
				<td class="td_label">联系人：</td>
				<td class="td_input"><input type="text" name="customerBackUp.communicator" value="${communicator}" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/></td>
				<td class="td_label">创建时间：</td>
				<td class="td_input"><input type="text" id="startTime" name="customerBackUp.createTimeA"   readOnly="true" class="e_inputTime" onfocus="this.className='e_inputFocusTime'" onblur="this.className='e_inputTime'" />~<input type="text" id="endTime" name="customerBackUp.createTimeB"  readOnly="true" class="e_inputTime" onfocus="this.className='e_inputFocusTime'" onblur="this.className='e_inputTime'"/></td>
				
			</tr>

			<tr>
				<td class="formBottom" colspan="99">
				   <input name="Submit" type="button" title="查看" class="b_search" value="" onfocus=blur() onclick="go()" />
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td style="padding: 4px;">
		<table cellpadding="0" cellspacing="1" width="100%" class="taba_right_list" id="bm">

			<tr>
				<td colspan="12" class="formTitle"
					style="padding-left: 8px; background: url(images/menu_righttop.png) repeat-x; text-align: left; height: 26px;"><span>客户管理列表</span></td>
			</tr>

			<tr>
				<td height="26px" align="center">序号</td>
				<td align="center">广告商名称</td>
				<td align="center">客户代码</td>
				<td align="center">公司地址</td>
				<td align="center">联系人</td>
				<td align="center">联系方式</td>
				<td align="center">合作期限</td>
				<td align="center">状态</td>
				<td align="center">创建时间</td>
				<td align="center">操作</td>
			</tr>
			<s:if test="listCustomers.size==0">
			<tr>
				<td bgcolor="#FFFFFF" align="center" colspan="12"
					style="text-align: center">无记录</td>
			</tr>
		</s:if>
		<s:else>
		   <c:set var="index" value="0"/>
			<s:iterator value="listCustomers" status="rowstatus" var="item">
				<tr>
				  <td align="center">${rowstatus.count}</td>
				  <td align="center"><s:property value="advertisersName" /></td>
				  <td align="center"><s:property value="clientCode" /></td>
				  <td align="center"><s:property value="conpanyAddress" /></td>
				  <td align="center"><s:property value="communicator" /></td>
				  <td align="center"><s:property value="contacts" /></td>
				  <td align="center"><s:property value="cooperationTime" /></td>
				  <td align="center">
				  	<s:if test='status == "0"'>待审核</s:if>
				  	<s:if test='status == "1"'>审核通过</s:if>
				  	<s:if test='status == "2"'>审核未通过</s:if>
				  </td>
				  <td align="center"><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/></td>
				  <td align="center">
				  	<input type="button" title="通过"  onclick="goAuditMetas('<s:property value ='id'/>')" style="background:url(<%=basePath%>/images/1.gif); border-style:none; background-repeat:no-repeat;  width:33px; height:17px;"/>
				  	<input type="button" title="不通过" onclick="noAuditMetasPass(<s:property value='id'/>)" style="background:url(<%=basePath%>/images/2.gif); border-style:none; background-repeat:no-repeat;  width:33px; height:17px;" />
				  	<input type="button" title="查看详情" class="button_start" onclick="goInfo('<s:property value ='id'/>')" />
				  </td>
				</tr>
				<c:set var="index" value="${index+1}"/>
			</s:iterator>
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
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
			</c:forEach>
		</c:if>
		</s:else>
			<tr>
				<td height="34" colspan="12" style="background: url(images/bottom.jpg) repeat-x; text-align: right;">
					<a href="#">共计${page.totalPage }页</a>&nbsp;&nbsp;
					<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp;
						<c:choose>
							<c:when test="${page.pageNo==1&&page.totalPage!=1}">
								【<a href="adCustomerMgr_listAudit.do?pageNo=${page.pageNo+1 }">下一页</a>】&nbsp;&nbsp;
								【<a href="adCustomerMgr_listAudit.do?pageNo=${page.totalPage}">末页</a>】&nbsp;&nbsp;
							</c:when>
							<c:when test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
								【<a href="adCustomerMgr_listAudit.do?pageNo=1">首页</a>】&nbsp;&nbsp;
								【<a href="adCustomerMgr_listAudit.do?pageNo=${page.pageNo-1 }">上一页</a>】&nbsp;&nbsp;
							</c:when>
							<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
								【<a href="adCustomerMgr_listAudit.do?pageNo=1">首页</a>&nbsp;&nbsp;
								【<a href="adCustomerMgr_listAudit.do?pageNo=${page.pageNo-1 }">上一页</a>】&nbsp;&nbsp;
								【<a href="adCustomerMgr_listAudit.do?pageNo=${page.pageNo+1 }">下一页</a>】&nbsp;&nbsp;
								【<a href="adCustomerMgr_listAudit.do?pageNo=${page.totalPage}">末页</a>】&nbsp;&nbsp;
							</c:when>
						</c:choose>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
