<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery-1.4.4.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/questionnaire/questionnaireList.js"></script>
<title>调查问卷管理</title>
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
</head>

<body>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
<td>
<table cellpadding="0" cellspacing="0" border="0" width="100%" class="tab_right">
<tr>
<td><a href="listQuestionnaire.do">查看调查问卷</a></td>
<td><a href="uploadTemplate.jsp">导入模板</a></td>
<td><a href="listTemplate.do">查看模板</a></td>
</tr>
</table>
</td>
</tr>
<tr>
<td style=" padding:4px;">
<table cellpadding="0" cellspacing="1" width="100%" class="taba_right_list" id="bm">

<tr>
<td colspan="12" style="padding-left:8px; background:url(<%=path %>/images/menu_righttop.png) repeat-x; text-align:left; height:26px;"><span>·调查问卷列表</span></td>
</tr>

<tr>
<td height="26px" align="center">序号</td>
<td>文件名称</td>
<td>文件描述</td>
<td>文件索引</td>
<td>问卷类型</td>
<td>状态</td>
<td>创建时间</td>
<td>修改时间</td>
<td>创建者</td>
<td>广告商</td>
<td>合同号</td>
<td>操作</td>
</tr>
<c:set var="index" value="0"/>
<c:forEach items="${questionnaires}" var="questionnaire">
	<tr>
	<c:set var="index" value="${index+1 }"/>
		<td align="center" height="26"><c:out value="${index }"/></td>
		<td><c:out value="${questionnaire.name}" /></td>
		<td><c:out value="${questionnaire.description}" /></td>
		<td><c:out value="${questionnaire.questionnaireId}" /></td>
		<td><c:out value="${questionnaire.questionnaireType}" /></td>
		<td><c:out value="${questionnaire.state}" /></td>
		<td><c:out value="${questionnaire.createTime}" /></td>
		<td><c:out value="${questionnaire.modifyTime}" /></td>
		<td><c:out value="${questionnaire.userId}" /></td>
		<td><c:out value="${questionnaire.businessName}" /></td>
		<td><c:out value="${questionnaire.contractCode}" /></td>
		<td><input name="Submit" 
					type="button" 
					class="button_start"
					value=""  
					title="预览" 
					onfocus=blur() 
					onclick="preview('${ftpPath}','${questionnaire.questionnaireId }')" />
			<input name="Submit" 
					type="button" 
					class="button_delete"
					value=""  
					title="删除" 
					onfocus=blur()
					onclick="deleteQuestionnaire(${questionnaire.id})" />
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
<td height="34" colspan="12" style="background:url(images/bottom.jpg) repeat-x; text-align:right;">
<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp;
<c:choose>
	<c:when test="${page.pageNo==1&&page.totalPage!=1}">
		<a href="listTemplate.do?pageNo=${page.pageNo+1 }">下一页</a>&nbsp;&nbsp;
		<a href="listTemplate.do?pageNo=${page.totalPage}">末页</a>&nbsp;&nbsp;
	</c:when>
	<c:when test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
		<a href="listTemplate.do?pageNo=1">首页</a>&nbsp;&nbsp;
		<a href="listTemplate.do?pageNo=${page.pageNo-1 }">上一页</a>&nbsp;&nbsp;
	</c:when>
	<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
		<a href="listTemplate.do?pageNo=1">首页</a>&nbsp;&nbsp;
		<a href="listTemplate.do?pageNo=${page.pageNo-1 }">上一页</a>&nbsp;&nbsp;
		<a href="listTemplate.do?pageNo=${page.pageNo+1 }">下一页</a>&nbsp;&nbsp;
		<a href="listTemplate.do?pageNo=${page.totalPage}">末页</a>&nbsp;&nbsp;
	</c:when>
</c:choose>
</td>
</tr>
</table>
</td>
</tr>
</table>
<div style="position:absolute; width:100%; left:0px; bottom:0px;">
<table cellpadding="0" cellspacing="0" border="0" class="footer">
<tr><td>22</td></tr>
</table>
</div>
</body>
</html>
