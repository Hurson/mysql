<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tags/fmt.tld" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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

<link rel="stylesheet" type="text/css"
			href="<%=path%>/css/new/main.css" />
		<link href="<%=path%>/css/new/common/dhtmlx/tree/css/dhtmlxtree.css"
			rel="stylesheet" type="text/css" media="all" />
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/dhtmlxcommon.js"
			type="text/javascript">
</script>
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/dhtmlxtree.js"
			type="text/javascript">
</script>
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/test.js"
			type="text/javascript">
</script>

<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />
<link href="<%=path%>/css/Pager.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right_new1.css" type="text/css" />

<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/role/role.js"></script>

<title>用户列表</title>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
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

<script>

	//批量删除用户
	
	function deleteUsersBatch(){
		var ids = "";
		
		var  resourceArr = document.getElementsByName("ids");
		for(var i=0; i < resourceArr.length;i++ ){
			if(resourceArr[i].checked){
				ids+= resourceArr[i].value +",";
			}
	    }
	    if(ids==""){
	    	alert("您还没有选择要删除的用户，请确认后再操作！");
	    	return;
	    }
	    
	    
	    var a = window.confirm("确定要删除吗？");
	    
	    if(a == 1){
	      deleteUserInfos(ids);
	    }
	    return;	
	}


	function deleteUserInfos(ids){
	    $.ajax({
	            type:"post",
				url: 'deleteUserBatchInfo.do',
				dataType:'json',
				data:{ids:ids},
				success:function(responseText){
				    var ss = eval(responseText);
				    if(ss != null){
				        alert("删除成功!");
				    	window.location.href="userList.do?method=getAllUserList";
				    }
				},
				error:function(){
					alert("服务器异常，请稍后重试！");
				}
	    });
	
	}



	//全选
	/** 复选框全选 */
	function checkAll(form) {
		for ( var i = 0; i < form.elements.length; i++) {
			var e = form.elements[i];
			if (e.Name != "chkAll")
				e.checked = form.chkAll.checked;
		}
		return;
	}

function deleteUserInfo(userId){
	var con = window.confirm("确定要删除吗？");
	if(con){
		
		$.ajax({
			type:"post",
			url: 'deleteUserInfo.do',
			dataType:'json',
			data:{id:userId},
			success:function(responseText){
				alert("删除成功!");
				window.location.href="userList.do";
			},
			error:function(){
				alert("服务器异常，请稍后重试！");
			}
		});
	}
}
//添加
function goAdduser(){
	window.location.href="<%=basePath%>/page/user/addUser.jsp";
}
//修改
function goUpdateUser(id){

	window.location.href="updateU.do?id="+id;
}

function queryUser(){
	var user_name = $('#user_name').val();
	var user_loginname = $('#user_loginname').val();
	var user_mail = $('#user_mail').val();
	window.location.href="queryUser.do?user_name="+user_name+"&login_name="+user_loginname+"&mail="+user_mail;
	
}

function goUserDetail(id){
	window.location.href="userDetail.do?id="+id;
}
</script>
</head>

<body class="mainBody" onload="">
		<input id="projetPath" type="hidden" value="<%=path%>" />
		<form id="form" action="" method="post"
			name="adCustomerMgr_list">
			<div class="search">
				<div class="path">
					<img src="<%=path%>/images/new/filder.gif" width="15" height="13" />
					首页 >> 系统管理 >> 用户管理
				</div>
				<div class="searchContent">
					<table cellspacing="1" class="searchList">
						<tr class="title">
							<td>
								查询条件
							</td>
						</tr>
						<tr>
							<td>
								用户名称：
								<input type="text" id="user_name" name="user_name"
									value="${queryConditin_name }" class="e_input"
									onfocus="this.className='e_inputFocus'"
									onblur="this.className='e_input'" />
								登陆名称：
								<input type="text" id="user_loginname"  name="login_name"
									value="${clientCode}" class="e_input"
									onfocus="this.className='e_inputFocus'"
									onblur="this.className='e_input'" />
								邮箱：
								<input type="text" id="user_mail" name="mail"
									value="${queryConditin_mail}" class="e_input"
									onfocus="this.className='e_inputFocus'"
									onblur="this.className='e_input'" />
								<input type="button" value="查询" onclick="queryUser();" class="btn" />
								<!--<input type="button" value="添加" onclick="goAdduser();" class="btn" /> -->
							</td>
						</tr>
					</table>
					<div id="messageDiv"
						style="margin-top: 15px; color: red; font-size: 14px; font-weight: bold;"></div>
					<table cellspacing="1" class="searchList" id="bm">
						<tr class="title">
							<td height="28" class="dot">
								<input type="checkbox" name="chkAll"  onclick=checkAll(this.form) id="chkAll"/>
							</td>
							<td align="center">
								序号
							</td>
							<td align="center">
								用户名称
							</td>
							<td align="center">
								登陆名称
							</td>
							<td align="center">
								用户状态
							</td>
							<td align="center">
								电子邮箱
							</td>
							<td align="center">
								角色名称
							</td>
							<td align="center">
								创建时间
							</td>
							<td align="center">
								修改时间
							</td>
							
						</tr>
						<s:if test="userList.size==0">
							<tr>
								<td bgcolor="#FFFFFF" align="center" colspan="12"
									style="text-align: center">
									无记录
								</td>
							</tr>
						</s:if>
						<s:else>
							<c:set var="index" value="0" />
							<s:iterator value="userList" status="rowstatus" var="item">
								<tr>
									<td>
									    <input type="checkbox" name="ids" value="${item.userId}" />
									</td>
									<td align="center">
										${rowstatus.count}
									</td>
									<td align="center">
										<a href="javascript:goUpdateUser('${item.userId}');"><s:property value="username" /></a>
										
									</td>
									<td align="center">
										<s:property value="loginname" />
									</td>
									<td align="center">
										<s:property value="status" />
									</td>
									<td align="center">
										<s:property value="email" />
									</td>
									<td align="center">
										<s:if test="mapUser[id] == 2">运营商</s:if>
										<s:if test="mapUser[id] == 1">广告商</s:if>
									</td>
									<td align="center">
										<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/>
									</td>
									<td align="center">
										<fmt:formatDate value="${item.modifyTime}" pattern="yyyy-MM-dd"/>
									</td>
									
								</tr>
								<c:set var="index" value="${index+1}" />
							</s:iterator>
						</s:else>
						<tr>
							<td colspan="10">
								<input type="button" value="删除" class="btn"
									onclick="deleteUsersBatch();" />
								&nbsp;&nbsp;
								<input id="addPositionSubmit" name="addPositionSubmit" onclick="goAdduser();"
									type="button" value="添加" class="btn" />

								
									<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
									<a href="#">当前第${page.currentPage}/${page.totalPage}页</a>&nbsp;&nbsp;
									<c:choose>
										<c:when test="${page.currentPage==1&&page.totalPage!=1}">
								【<a
												href="userList.do?method=getAllUserList&currentPage=${page.currentPage+1}&user_name=${queryConditin_name }">下一页</a>】&nbsp;&nbsp;
								【<a
												href="userList.do?method=getAllUserList&currentPage=${page.totalPage}&user_name=${queryConditin_name }">末页</a>】&nbsp;&nbsp;
							</c:when>
										<c:when
											test="${page.currentPage==page.totalPage&&page.totalPage!=1 }">
								【<a
												href="userList.do?method=getAllUserList&currentPage=1&user_name=${queryConditin_name }">首页</a>】 &nbsp;&nbsp;
								【<a
												href="userList.do?method=getAllUserList&currentPage=${page.currentPage-1 }&user_name=${queryConditin_name }">上一页</a>】&nbsp;&nbsp;
							</c:when>
										<c:when test="${page.currentPage>1&&page.currentPage<page.totalPage}">
								【<a
												href="userList.do?method=getAllUserList&currentPage=1&user_name=${queryConditin_name }">首页</a>】 &nbsp;&nbsp;
								【<a
												href="userList.do?method=getAllUserList&currentPage=${page.currentPage-1}&user_name=${queryConditin_name }">上一页</a>】&nbsp;&nbsp;
								【<a
												href="userList.do?method=getAllUserList&currentPage=${page.currentPage+1}&user_name=${queryConditin_name }">下一页</a>】&nbsp;&nbsp;
								【<a
												href="userList.do?method=getAllUserList&currentPage=${page.totalPage}&user_name=${queryConditin_name }">末页</a>】&nbsp;&nbsp;
							</c:when>
									</c:choose>
								
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>
	</body>
</html>