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
<script language="javascript" type="text/javascript" src="<%=path %>/js/column/addColumn.js"></script>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
});

</script>
<title>新增栏目：</title>
</head>
<body>
<li>系统栏目管理 </li>
	<table style="width:720px;height:576px;">
		<tr>
			<td>
				<form id="saveForm" action="" method="post">
					<table>
						<tr>
							<td style="width:200px">			
							</td>
							<td colspan="2" style="text-align:center">
								添加栏目
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
								<input type="text" name="column.name"/>
							</td>
							<td id="usernameText">
								
							</td>
						</tr>
						<tr>
							<td style="width:200px">
								
							</td>
							<td>
								栏目说明：
							</td>
							<td>
								<input id="pwd1" type="text" name="column.description" value="所属广告营销规则栏目"/>
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
								<input id="pwd1" type="text" name="column.columnCode"/>
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
								<s:iterator value="firstColumnList" status="rowstatus">
												<option value='<s:property value="id" />'><s:property value="name" /></option>
								</s:iterator>
							</select>				
							</td>
							<td id="passwdText">
								
							</td>
						</tr>
						<tr>
							<td style="width:200px">
								
							</td>
							<td>
								栏目状态：
							</td>
							<td>
								<input id="pwd2" type="text" name="column.state" value="1"/>
							</td>
							<td id="passwdText">
								
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