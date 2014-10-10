<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<script language="javascript" type="text/javascript" src="js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/js.js"></script>

<title>修改广告商文字素材</title>
<script type="text/javascript">
    		//取消素材		
    		function goBack(){
    			window.location.href="adContentMgr_listReal.do";
    		}
    		
    		function goUpdate(){
    			alert("等待上传文字素材表单字段的最终确定，而确定页面字段，暂不可用，请单击返回按钮，回到主页面,.T_T.");
    			return false;
    		}
    		
    		
    </script>
<script>
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
<form  action="adContentMgr_updateMesageMetaReal.do" method="post" name="adContentMgr_updateMesageMetaReal" onsubmit="return goUpdate();"> 
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
				<td class="formTitle" colspan="99"><span>文字素材修改</span></td>
			</tr>
			<tr>
			
				<input type=hidden value="${resourceReal.id}" name="resourceReal.id"/>
				
				<input type=hidden value="${resourceReal.createTime}" name="resourceReal.createTime"/>
				<input type=hidden value="${resourceReal.operationId}" name="resourceReal.operationId"/>
				<input type=hidden value="${resourceReal.resourceType}" name="resourceReal.resourceType"/>
				<input type=hidden value="${resourceReal.resourceId}" name="resourceReal.resourceId"/>
				<input type=hidden value="${resourceReal.category}" name="resourceReal.category"/>
				<input type=hidden value="${resourceReal.state}" name="resourceReal.state"/>
				<input type=hidden value="${resourceReal.customerId}" name="resourceReal.customerId"/> 
				<input type=hidden value="${resourceReal.contractNumber}" name="resourceReal.contractNumber"/>
				<input type=hidden value="${resourceReal.startTime}" name="resourceReal.startTime" id= "startTimeHidden"/>
				<input type=hidden value="${resourceReal.endTime}" name="resourceReal.endTime" id="endTimeHidden"/>
				
				<input type=hidden value="${messageReal.id}" name="messageReal.id"/>
				<input type=hidden value="${messageReal.content}" name="messageReal.content"/>
			
				<td class="td_label">内容分类：</td> 
			    <td class="td_input">&nbsp;<c:if test="${resourceReal.category == 1}">商用</c:if><c:if test="${resourceReal.category == 0}">公益</c:if></td>
				<td class="td_label">素材状态：</td>
				<td class="td_input">&nbsp;<c:if test="${resourceReal.state == '0'}">待审核</c:if><c:if test="${resourceReal.state == '1'}">审核不通过</c:if><c:if test="${resourceReal.state == '2'}">上线</c:if><c:if test="${resourceReal.state == '3'}">下线</c:if><c:if test="${resource.state == '4'}">审核待删除</c:if><c:if test="${resource.state == '5'}">审核已删除</c:if></td>
			</tr>
			<tr>
			   <td class="td_label">所属广告位</td>
         		<td class="td_input">&nbsp;${positionName}</td>
				<td class="td_label">所属合同号：</td>
				<td class="td_input">&nbsp;${resourceReal.contractNumber}</td>
			</tr>
			<tr>
			   <td class="td_label">资产名称：</td>
				<td class="td_input"><input type="text" class="e_input" value="${resourceReal.resourceName}" name="resourceReal.resourceName" id="resourceName" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/><font style="color:red;"><span id = "resourceNameF"></span>*</font></td>
				<td class="td_label">文字名称：</td>
				<td class="td_input"><input type="text" class="e_input" value="${messageReal.name}" name="messageReal.name" id="name" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/><font style="color:red;"><span id = "nameF"></span>*</font></td>
			</tr>
			<tr>
				<td class="td_label">有效开始时间：</td> 
				<td class="td_input"><input type="text" class="e_input" name="resourceReal.startTime" id="startTime" readOnly="true" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/><font style="color:red;">*</font></td>
				<td class="td_label">有效结束时间：</td>
				<td class="td_input"><input type="text" class="e_input"  name="resourceReal.endTime" id="endTime" class="e_input" readOnly="true" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/><font style="color:red;">*</font></td>
			</tr>
			
			<tr>
				<td class="td_label">文字URL：</td>
				<td class="td_input"><input type="text" class="e_input" value="${messageReal.URL}" name="messageReal.URL" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/></td>
				<td class="td_label" rowspan="3">文字内容：</td>
				<td class="td_input" rowspan="3"> <textarea id="" rows="12" cols="30" readonly="true">${content}</textarea> </td> 
			</tr>
			
			<tr>
				<td class="td_label">资产描述：</td>
				<td class="td_input"> <textarea id="" rows="8" cols="35" value="${resourceReal.resourceDesc}" name="resourceReal.resourceDesc">${resourceReal.resourceDesc}</textarea></td>
			</tr>
			<tr>
				<td class="td_label">审核意见：</td>
				<td class="td_input">
				</td>
			</tr>
			
			 <tr>
				<td class="formBottom" colspan="99" align="center">
					<input   type="submit" value ="确定"/>
					<input type="button" value="返回"   onfocus=blur() id="bt1" onclick="goBack();"/>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
