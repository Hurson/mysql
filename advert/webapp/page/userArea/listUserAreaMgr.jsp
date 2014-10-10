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

<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>


<title>用户区域管理</title>
<script language="javascript" type="text/javascript">


	function go(){
	
		var userName = document.getElementById("userName").value;
		var releaseAreaName = document.getElementById("releaseAreaName").value;
		
		if( userName == "" && releaseAreaName != ""){
			alert("用户名不能为空！");
			document.getElementById("userName").value="";
			document.getElementById("releaseAreaName").value="";
			return;
		}else{
				//alert("nihao");
			document.getElementById("form").submit();
		}
	}
	
    //删除
	function goDeleteInfo(userId){
			var a = window.confirm("您确定要删除该条记录吗？");
			if(a == 1){
				$.ajax( {
					type : 'post',
					url : 'userArea_deleteUserArea.do',
					data : 'cId=' + userId + '&date='+new Date(),
					success : function(msg) {
						if(msg != null){
							window.location.href="userArea_list.do";
						}
					}	
				});
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
<form  id="form" action="userArea_list.do" method="post" id="userArea_list" name="userArea_list"> 
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
				<td class="formTitle" colspan="99"><span>用户区域管理</span></td>
			</tr>
			<tr> 
			<td class="td_label">用户名:</td> 
				<td class="td_input"><input type="text" class="e_input" name="userArea.userNamePage" id="userName" value="${userName}" onblur="this.className='e_input'" onfocus="this.className='e_inputFocus'"/></td>
				<td class="td_label">投放区域:</td>
				<td class="td_input"><input type="text" class="e_input" name="userArea.releaseAreaNamePage"  id="releaseAreaName" value="${releaseAreaName}"  onblur="this.className='e_input'" onfocus="this.className='e_inputFocus'"/></td>
			</tr>	
			<tr>
				<td class="formBottom" colspan="99">
			<!-- <input name="Submit" type="submit" title="查看" value="" class="b_search" onfocus="blur()" />-->
			<input name="Submit" type="button" title="查看" value="" class="b_search" onclick="go()"  onfocus="blur()" />
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td style="padding: 4px;">
		<table cellpadding="0" cellspacing="1" width="100%" class="taba_right_list" id="bm">

			<tr>
				<td colspan="12" class="formTitle"
					style="padding-left: 8px; background: url(images/menu_righttop.png) repeat-x; text-align: left; height: 26px;"><span>用户区域信息列表</span></td>
			</tr>

			<tr>
				<td  height="26px" align="center">序号</td>
				<td  height="26px" align="center">用户</td>
				<td  height="26px" align="center">投放区域</td>
				<td  height="26px" align="center">操作</td>
			</tr>
			
			<s:if test="listUserReleaseAreaBean == null">
			<tr>
				<td bgcolor="#FFFFFF" align="center" colspan="12"
					style="text-align: center">暂无记录</td>
			</tr>
		</s:if>
		<s:else>
		<c:set var="index" value="0"/>
			<s:iterator value="listUserReleaseAreaBean" status="rowstatus" var="item">
				<tr>
					<td align="center">${rowstatus.count}</td>
					<td align="center"><s:property value="loginname" /></td>
					<td align="center"><s:property value="areaName" /></td>
					<td align="center">
					<!--     <input type="button" title="修改" class="button_halt" onclick="" />--> 
						<input type="button" title="删除" class="button_delete"  onclick="goDeleteInfo(<s:property value='userId'/>)" />
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
					</tr>
			</c:forEach>
		</c:if>
		</s:else>
		<tr>
			<td height="34" colspan="23"
				style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: right;">
				<c:if test="${page.totalPage > 0 }">  
					<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
					<a href="#">当前第${page.currentPage}/${page.totalPage}页</a>&nbsp;&nbsp;
					<c:choose>
						<c:when test="${page.currentPage==1&&page.totalPage!=1}">
							【<a href="userArea_list.do?currentPage=${page.currentPage+1}&userNamePage=${userName}&releaseAreaNamePage=${releaseAreaName}">下一页</a>】
							【<a href="userArea_list.do?currentPage=${page.totalPage}&userNamePage=${userName}&releaseAreaNamePage=${releaseAreaName}">末页</a>】
						</c:when>
						<c:when test="${page.currentPage==page.totalPage&&page.totalPage!=1 }">
							【<a href="userArea_list.do?currentPage=1&userNamePage=${userName}&releaseAreaNamePage=${releaseAreaName}">首页</a>】 
							【<a href="userArea_list.do?currentPage=${page.currentPage-1 }&userNamePage=${userName}&releaseAreaNamePage=${releaseAreaName}">上一页</a>】
						</c:when>
						<c:when test="${page.currentPage>1&&page.currentPage<page.totalPage}">
							【<a href="userArea_list.do?currentPage=1&userNamePage=${userName}&releaseAreaNamePage=${releaseAreaName}">首页</a>】 
							【<a href="userArea_list.do?currentPage=${page.currentPage-1}&userNamePage=${userName}&releaseAreaNamePage=${releaseAreaName}">上一页</a>】
							【<a href="userArea_list.do?currentPage=${page.currentPage+1}&userNamePage=${userName}&releaseAreaNamePage=${releaseAreaName}">下一页</a>】
							【<a href="userArea_list.do?currentPage=${page.totalPage}&userNamePage=${userName}&releaseAreaNamePage=${releaseAreaName}">末页</a>】
						</c:when>
					</c:choose>
				</c:if>
			</td>
		</tr>
	</table>
</table>
</form>
</body>
</html>
