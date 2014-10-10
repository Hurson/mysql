<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"	href="<%=path %>/css/new/main.css" />
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>

<title>编辑图片广告位规格</title>
</head>
<script type="text/javascript">
	function save() {
		if (!validateBeforeSubmit()) {
	        return;
	    }
		$("#editForm").submit();
	}
	
	//表单验证
	function validateBeforeSubmit(){
		var imageWidth = $.trim($("#imageWidth").val());
		if(imageWidth!=''){
			if(!isIntegerNumber(imageWidth)){
				alert("图片规格宽度只能是数字！");
				$("#imageWidth").focus();
				return false;
			}
			if(imageWidth.length>10){
				alert("图片规格宽度长度最大只能是10位！");
				$("#imageWidth").focus();
				return false;
			}
		}
		var imageHeight = $.trim($("#imageHeight").val());
		if(imageHeight!=''){
			if(!isIntegerNumber(imageHeight)){
				alert("图片规格高度只能是数字！");
				$("#imageHeight").focus();
				return false;
			}
			if(imageHeight.length>10){
				alert("图片规格高度长度最大只能是10位！");
				$("#imageHeight").focus();
				return false;
			}
		}
		var imageDesc = $.trim($("#imageDesc").val());
		if(imageDesc!=''){
			if(imageDesc.length>120){
				alert("图片规格描述长度必须在0-120字之间！");
				$("#imageDesc").focus();
				return false;
			}
		}
		return true;
	}

</script>
<body class="mainBody">
<form action="saveImageSpecification.do" method="post" id="editForm">
<input type="hidden" name="id" value="${advertPosition.id}"  />
<input type="hidden" id="id" name="imageSpe.id" value="${imageSpe.id}"  />
<div class="detail">
<c:if test="${imageSpe.type == 'ZIP'}">
<table cellspacing="1" class="searchList" align="left">
	<tr class="title">
		<td colspan="4">ZIP规格信息</td>
	</tr>
	
	<tr class="sec">
		<td width="12%" align="right">类型：</td>
		<td><input id="type" name="imageSpe.type" value="${imageSpe.type}" type="text" /></td>
		<td width="12%" align="right">最大文件大小：</td>
		<td><input id="fileSize" name="imageSpe.fileSize" value="${imageSpe.fileSize}" type="text" />字节</td>
	</tr>
	<tr>
		<td width="12%" align="right">是否链接：</td>
		<td colspan="3">
			<select id="isLink" name="imageSpe.isLink">
				<option value="0"  <c:if test="${imageSpe.isLink==0}">selected='selected'  </c:if>>否</option>
				<option value="1"  <c:if test="${imageSpe.isLink==1}">selected='selected'  </c:if>>是</option>
			</select>
		</td>
	</tr>
	<tr class="sec">
		<td width="12%" align="right">描述：</td>
		<td colspan="3"><textarea id="imageDesc" name="imageSpe.imageDesc" cols="50" rows="3">${imageSpe.imageDesc}</textarea>
		<span class="required">(长度在0-120字之间)</span></td>
	</tr>
	<tr>
		<td align="center" colspan="4">
			<input type="button" class="btn" value="确定"  onclick="javascript:save();" />
			<input type="button" class="btn" value="返回" onclick="window.location.href='querySpecification.do?id=${advertPosition.id}'" />
		</td>
	</tr>
</table>
</c:if>
<c:if test="${imageSpe.type != 'ZIP'}">
<table cellspacing="1" class="searchList" align="left">
	<tr class="title">
		<td colspan="4">图片规格信息</td>
	</tr>
	<tr>
		<td width="12%" align="right">宽度：</td>
		<td><input id="imageWidth" name="imageSpe.imageWidth" value="${imageSpe.imageWidth}" type="text" /></td>
		<td width="12%" align="right">高度：</td>
		<td><input id="imageHeight" name="imageSpe.imageHeight" value="${imageSpe.imageHeight}" type="text" /></td>
	</tr>
	<tr class="sec">
		<td width="12%" align="right">类型：</td>
		<td><input id="type" name="imageSpe.type" value="${imageSpe.type}" type="text" /></td>
		<td width="12%" align="right">最大文件大小：</td>
		<td><input id="fileSize" name="imageSpe.fileSize" value="${imageSpe.fileSize}" type="text" />字节</td>
	</tr>
	<tr>
		<td width="12%" align="right">是否链接：</td>
		<td colspan="3">
			<select id="isLink" name="imageSpe.isLink">
				<option value="0"  <c:if test="${imageSpe.isLink==0}">selected='selected'  </c:if>>否</option>
				<option value="1"  <c:if test="${imageSpe.isLink==1}">selected='selected'  </c:if>>是</option>
			</select>
		</td>
	</tr>
	<tr class="sec">
		<td width="12%" align="right">描述：</td>
		<td colspan="3"><textarea id="imageDesc" name="imageSpe.imageDesc" cols="50" rows="3">${imageSpe.imageDesc}</textarea>
		<span class="required">(长度在0-120字之间)</span></td>
	</tr>
	<tr>
		<td align="center" colspan="4">
			<input type="button" class="btn" value="确定"  onclick="javascript:save();" />
			<input type="button" class="btn" value="返回" onclick="window.location.href='querySpecification.do?id=${advertPosition.id}'" />
		</td>
	</tr>
</table>
</c:if>
</div>
</form>
</body>
</html>