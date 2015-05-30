<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"	href="<%=path %>/css/new/main.css" />
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/swfobject.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/jquery.uploadify.v2.1.4.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js"></script>
<script type="text/javascript">
	var result1 =false;
	var result2 =false;
	function checkExt(obj){
		var name=obj.name;
		var val= obj.value
		var ext=val.substring(val.lastIndexOf("."),val.length);
		if(name == "upgrade" && (ext==".tar" || ext==".zip" || ext==".gz")){
			result1 = true;
		}
		
		else if(name=="description" && val.substring(val.lastIndexOf("."))==".properties"){
			result2 = true;
		}else{
			alert("不支持的文件类型"+ext+",请重新选择！");
			obj.focus();
			obj.value="";
		}
	}
	function save() {
		if(result1 && result2){
			$("#editForm").submit();
		}else{
			alert("包文件或描述文件没有选择或选择错误,请重新选择！");
		}
	}
	
</script>

<title>上传OCG升级包</title>
</head>
<body class="mainBody">
<form action="uploadResource.do" id="editForm" theme="simple"  validate="false" enctype ="multipart/form-data" method ="POST">
	<div class="detail">
	<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;">
		 <c:if test="${message != null}">
	            <span style="color:red;"><s:property value="%{getText(#request.message)}" /></span>
	    </c:if>
	</div> 
	<table cellspacing="1" class="searchList" align="left">
		<tr class="title">
			<td colspan="2">上传OCG升级包</td>
		</tr>
		
		<tr>
			<td width="20%" align="right">升级包压缩文件：</td>
			<td><input type="file" name="upgrade" onchange="checkExt(this);"/></td>
		</tr>
		<tr class="sec">
			<td width="20%" align="right">描述.properties文件：</td>
			<td><input type="file"  name="description" onchange="checkExt(this);"/></td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<input type="button" class="btn" value="保存"  onclick="javascript:save();" />
			</td>
		</tr>
	</table>
	</div>
</form>
</body>
</html>