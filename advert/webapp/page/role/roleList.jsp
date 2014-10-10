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

<script type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/role/role.js"></script>


<script type=text/javascript src="<%=path %>/common/tree/common-min.js"></script>
<script type=text/javascript src="<%=path %>/common/tree/TreePanel.js"></script>
<script type=text/javascript src="<%=path %>/common/tree/china_2.js"></script>



<link href="<%=path %>/common/tree/TreePanel.css" rel="stylesheet" type="text/css"/>

<style>
	.easyDialog_wrapper{ width:800px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>


<script>

//批量删除角色
function deleteRolesBatch(){
	var ids = "";
		var  resourceArr = document.getElementsByName("ids");
		
		for(var i=0; i < resourceArr.length;i++ ){
			if(resourceArr[i].checked){
				ids+= resourceArr[i].value +",";
				var key =resourceArr[i].value + "_state";
				var state = $("#"+key).val();
				if(state == 1){
					alert("不能删除有效状态的角色");
					return;
				}
			}
	    }
	    if(ids==""){
	    	alert("您还没有选择要删除的角色，请确认后再操作！");
	    	return;
	    }
	    
	    var a = window.confirm("确定要删除吗？");
	    
	    if(a == 1){
	      deleteRolesInfos(ids);
	    }
	    return;	
}

	function deleteRolesInfos(ids){
		 $.ajax({
	            type:"post",
				url: 'deleteRoleBatchInfo.do',
				dataType:'json',
				data:{ids:ids},
				success:function(responseText){
				    if(responseText != null){
				    	alert("删除成功!");
				        window.location.href="<%=basePath%>/page/role/toRoleList.do";
				    }
				},
				error:function(){
					alert("服务器异常，请稍后重试！");
				}
	    });
	}

function deleteUserInfo(roleId){
	var con = window.confirm("确定要删除吗？");
	if(con){
		$.ajax({
			type:"post",
			url: 'deleterole.do?method=deleteRole',
			dataType:'json',
			data:{id:roleId},
			success:function(responseText){
				alert("删除成功!");
				window.location.href="<%=basePath%>/page/role/toRoleList.do";
			},
			error:function(){
				alert("服务器异常，请稍后重试！");
			}
		});
	}
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



function accessUrl(url){
	window.location.href=url;
}

//添加
function goAddrole(){
	window.location.href="<%=basePath%>/page/role/addRole.jsp";
}

function goUpdateRole(id){
	window.location.href="<%=path%>/page/role/updaterole.do?id="+id;
}

function goRoleDetail(id){
	window.location.href="<%=path%>/page/role/roledetail.do?id="+id;
}
function queryRole(){   
	var role_name = $('#role_name').val();
	var role_type = $('#type_str').val();
	var role_status = $('#status_str').val();
	window.location.href="<%=path%>/page/role/toRoleList.do?method=getAllRoleList&role_name="+role_name+"&role_type="+role_type+"&role_status="+role_status;
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
					首页 >> 系统管理 >> 角色管理
				</div>
				<div class="searchContent">
					<table cellspacing="1" class="searchList">
						<tr class="title">
							<td >
								查询条件
							</td>
						</tr>
						<tr>
							<td>
								角色名称：
								<input id="role_name" type="text" name="role.name"
									value="${queryConditin_name }" class="e_input"
									onfocus="this.className='e_inputFocus'"
									onblur="this.className='e_input'" />
								角色类型：
								<select name="role.typeStr" id="type_str">
									<option value="10">全部</option>
									<option value="2">运营商</option>
									<option value="1">广告商</option>
								</select>
								角色状态：
								<select name="role.statusStr" id="status_str">
									<option value="10">全部</option>
								    <option value="0">无效</option>
								    <option value="1">有效</option>
								 </select>
								<input name="Submit" id="queryRoleSubmit"  type="button" value="查询"  onclick="queryRole()" class="btn" />
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
								类型
							</td>
							<td align="center">
								角色名称
							</td>
							<td align="center">
								角色描述
							</td>
							<td align="center">
								创建时间
							</td>
							<td align="center">
								修改时间
							</td>
							<td align="center">
								状态
							</td>
							
						</tr>
						<c:choose>
						<c:when test="${!empty roleList}">
							<c:set var="index" value="0" />
							<c:forEach var="item" items="${roleList}" varStatus="status">
								<c:set  var="index"  value="${index+1}" />
								<tr>
									<td align="center" height="26"><input type="checkbox" name="ids" value="${item.id}" /></td>
									<td align="center" height="26">${status.count}</td>
									<td ><c:if test="${item.type=='1'}">广告商</c:if><c:if test="${item.type=='2'}">运营商</c:if></td>
									<td><a href="javascript:goUpdateRole('${item.id}');">${item.name}</a></td>
									<td width="400px">${item.description}</td>
									   <td> <fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/></td>
									   <td> <fmt:formatDate value="${item.modifyTime}" pattern="yyyy-MM-dd"/></td>
									<td ><input type="hidden"  id="${item.id}_state"  value="${item.status}" /><c:if test="${item.status=='1' }"> 有效</c:if><c:if test="${item.status=='0' }">无效</c:if></td>
								
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="9">
									暂无记录
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
						<tr>
							<td colspan="8">
								<input type="button" value="删除" class="btn"onclick="deleteRolesBatch();" />
								&nbsp;&nbsp;
								<input id="addPositionSubmit" name="addPositionSubmit"
									type="button" value="添加" class="btn" onclick="goAddrole()"/>
								
									<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
									<a href="#">当前第${page.currentPage}/${page.totalPage}页</a>&nbsp;&nbsp;
									<c:choose>
										<c:when test="${page.currentPage==1&&page.totalPage!=1}">
								【<a
												href="toRoleList.do?method=getAllRoleList&currentPage=${page.currentPage+1}&role_name=${queryConditin_name}">下一页</a>】&nbsp;&nbsp;
								【<a
												href="toRoleList.do?method=getAllRoleList&currentPage=${page.totalPage}&role_name=${queryConditin_name}">末页</a>】&nbsp;&nbsp;
							</c:when>
										<c:when
											test="${page.currentPage==page.totalPage&&page.totalPage!=1}">
								【<a
												href="toRoleList.do?method=getAllRoleList&currentPage=1&role_name=${queryConditin_name}">首页</a>】 &nbsp;&nbsp;
								【<a
												href="toRoleList.do?method=getAllRoleList&currentPage=${page.currentPage-1 }&role_name=${queryConditin_name}">上一页</a>】&nbsp;&nbsp;
							</c:when>
										<c:when test="${page.currentPage>1&&page.currentPage<page.totalPage}">
								【<a
												href="toRoleList.do?method=getAllRoleList&currentPage=1&role_name=${queryConditin_name}">首页</a>】 &nbsp;&nbsp;
								【<a
												href="toRoleList.do?method=getAllRoleList&currentPage=${page.currentPage-1}&role_name=${queryConditin_name}">上一页</a>】&nbsp;&nbsp;
								【<a
												href="toRoleList.do?method=getAllRoleList&currentPage=${page.currentPage+1}&role_name=${queryConditin_name}">下一页</a>】&nbsp;&nbsp;
								【<a
												href="toRoleList.do?method=getAllRoleList&currentPage=${page.totalPage}&role_name=${queryConditin_name}">末页</a>】&nbsp;&nbsp;
							</c:when>
									</c:choose>
								
							</td>
						</tr>
					</table>
				</div>
			</div>
			
<div id="columnDiv" class="showDiv_2"  style="display: none;">
<table cellpadding="0" cellspacing="0"
	style="margin-top: 0; width: 750px; height: 350px; border: 1px solid #cccccc; font-size: 12px; background-color: #e7f2fc;">
	<tr class="list_title">
		<th id="title_id" style="border: 0;" align="left">&nbsp;&nbsp;· 查看绑定栏目详情</th>
		<td style="border: 0;" align="right">
			<a href="#" onclick="closeDiv('columnDiv');">返回</a>&nbsp;&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan='2'  id="treeList">
		</td>
	</tr>
</table>
</div>
<div id="bg" class="bg" style="display: none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>
		</form>
	</body>
</html>