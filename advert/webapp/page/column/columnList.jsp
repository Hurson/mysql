<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<title>栏目信息列表</title>
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />
<link href="<%=path%>/css/Pager.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right_new1.css" type="text/css" />

<style>
	.easyDialog_wrapper{ width:800px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.4.js"></script>
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

<script type="text/javascript">
function deleteColumnInfo(id){
	var con = window.confirm("确定要删除吗？");
	if(con){
		$.ajax({
			type:"post",
			url: 'deleteColumn.do?method=deleteColumn',
			dataType:'json',
			data:{id:id},
			success:function(responseText){
				alert("删除成功!");
				window.location.href="<%=basePath%>/page/column/columnList.do";
			},
			error:function(){
				alert("服务器异常，请稍后重试！");
			}
		});
	}
}
//添加
function goAddColumn(){
	window.location.href="<%=basePath%>/page/column/toAddColumn.do";
}

function goUpdateColumn(id){

	window.location.href="<%=path%>/page/column/updateColumn.do?id="+id;
}

</script>

</head>

<body>
<form action="" method="post" id="user_list"
	name="adContentMgr_list">
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
	</table>
	</form>
	<table cellpadding="0" cellspacing="1" width="100%" class="taba_right_list" id="bm">
		<tr>
			<td colspan="12" class="formTitle"
				style="padding-left: 8px; background: url(images/menu_righttop.png) repeat-x; text-align: left; height: 26px;"><span>·栏目列表</span>
				<input type="button"  value="添加" onclick="goAddColumn();"/>
				</td>
		</tr>
		<tr align=center>
			<td  height="26px" align="center">序号</td>
			<td  align="center">栏目名称</td>
			<td  align="center">栏目说明</td>
			<td  align="center">栏目编号</td>
			<td  align="center">父级栏目</td>
			<td  align="center">栏目状态</td>
			<td  align="center">操作</td>
		</tr>
		<s:if test="columnList.size==0">
			<tr>
				<td bgcolor="#FFFFFF" align="center" colspan="12"
					style="text-align: center">无记录</td>
			</tr>
		</s:if>
		<s:else>
			<s:iterator value="columnList" status="rowstatus">
				<tr>
					<td align="center">${rowstatus.count}</td>
					<td align="center"><s:property value="name" /></td>
					<td align="center"><s:property  value="description"/></td>
					<td align="center"><s:property value="columnCode" /></td>
					<td align="center">
						<s:property value="parentName" />
					</td>
					<td align="center"><s:property value="state" /></td>
					<td align="center">
					<input type="button" value="修&nbsp;&nbsp;改"
						onclick="goUpdateColumn(<s:property value='id'/>)" />
				<s:if test="type !=0">
					<input type="button" value="删&nbsp;&nbsp;除"  onclick="deleteColumnInfo(<s:property value='id'/>)" />
				</s:if><s:else>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</s:else>
					</td>
				</tr>
			</s:iterator>
		</s:else>
		<tr>
			<td height="34" colspan="12"
				style="background: url(images/bottom.jpg) repeat-x; text-align: right;"><a
				href="#">共计10页</a>&nbsp;&nbsp;<a href="#">当前第1/10页</a>&nbsp;&nbsp;<a
				href="#">上一页</a>&nbsp;&nbsp;<a href="#">下一页&nbsp;&nbsp;</a></td>
		</tr>
	</table>
</body>
</html>


