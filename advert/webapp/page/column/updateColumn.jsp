<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<script type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/column/updateColumn.js"></script>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
});

</script>
<title>修改栏目：</title>
</head>
<body>
<li>系统角色管理 </li>
	<table style="width:720px;height:576px;">
		<tr>
			<td>
				<form id="updateForm" action="" method="post">
					<table>
						<tr>
							<td style="width:200px">			
							</td>
							<td colspan="2" style="text-align:center">
								修改栏目
							</td>
							<td>	
							</td>
						</tr>
						<tr>
							<td style="width:200px">
								
							</td>
							<td>
								栏目名称：
							</td>
							<td>
							
								<input type="hidden" name="column.id" value="${column.id }"/>
								<input type="text" name="column.name" value="${column.name }"/>
							</td>
							<td id="usernameText">
								
							</td>
						</tr>
						<tr>
							<td style="width:200px">
								
							</td>
							<td>
								栏目描述：
							</td>
							<td>
								<input id="pwd1" type="text" name="column.description"  value="${column.description }"/>
							</td>
							<td id="passwdText">
								
							</td>
						</tr>
						<tr>
							<td style="width:200px">
								
							</td>
							<td>
								栏目编号：
							</td>
							<td>
								<input id="pwd2" type="text" name="column.columnCode" value="${column.columnCode }"/>
							</td>
							<td id="passwdText">
								
							</td>
						</tr>
						<tr>
							<td style="width:200px">
								
							</td>
							<td>
								父级栏目：
							</td>
							<td>
							<select id="selectId" onchange="" name="column.parentId">
								<c:forEach var="item" items="${firstColumnList}" varStatus="status">				
												<option <c:if test="${column.parentId==item.id }"> selected='selected'  </c:if>  value='${item.id }'>
												${item.name }
												</option>
								</c:forEach>
							</select>	
							</td>
							<td id="addressText">
								
							</td>
						</tr>
						<tr>
							<td style="width:200px">
								
							</td>
							<td>
								栏目状态：
							</td>
							<td>
								<input type="text" name="column.state" value="${column.state }"/>
							</td>
							<td id="addressText">
								
							</td>
						</tr>
						
						<tr>
							<td style="width:200px">
								
							</td>
							<td>
								<input type="button" value="提交"  onclick="firstSubmit()"/>
							</td>
							<td>
								<input type="reset" value="重置"/>
							</td>
							<td>
								
							</td>
						</tr>
					</table>
				</form>	
			</td>
		</tr>
	</table>
</body>
</html>