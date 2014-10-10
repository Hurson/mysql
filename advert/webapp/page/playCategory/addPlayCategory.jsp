<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery-1.4.4.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/playCategory/addPlayCategory.js"></script>
<title>广告系统</title>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
});

//公共方法--获得dom对象
	function getObj(id){
		var obj = document.getElementById(id);
		return obj;	
	}
	
	//公共方法-选择下拉框的值
	function selectOptionVal(id){
		var optionVal="-1";
		var selectPobj = getObj(id); 
		var options   = selectPobj.options; 
		for(var i = 0; i < options.length; i++){
			var optionResult = options[i].selected ;
			if(optionResult){
				optionVal = options[i].value;
			}
		}
		return optionVal;
	}

--></script>
<style>
.treven{ background:#FFFFFF;}
.trodd{ background:#f6f6f6;}
</style>
</head>

<body onload="">

<form action="addPlayCategory.do?method=save"  id="addPlayCategoryForm" name="addPlayCategoryForm" method="post">
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td style=" padding:4px;">
				<table cellpadding="0" cellspacing="1" width="100%" class="tablea" >
					<tr>
						<td class="formTitle" colspan="99">
							<span>添加 投放栏目</span>
						</td>
					</tr>
					<tr>
						<td class="td_label" style="width:270px">节点ID：</td>
						<td class="td_input">
							<input id="categoryId" name="playCategory.categoryId" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/>		
						</td>
						<td class="td_label" style="width:270px">节点名称：</td>
						<td class="td_input">
							<input id="categoryName" name="playCategory.categoryName" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/>		
						</td>
					</tr>
					<tr>
						<td class="td_label" style="width:270px">节点类型：</td>
						<td class="td_input">
							<input id="categoryType" name="playCategory.categoryType" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/>		
						</td>
						<td class="td_label" style="width:270px">模板ID：</td>
						<td class="td_input">
							<input id="templateId" name="playCategory.templateId" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/>		
						</td>
					</tr>
					<tr>
						<td class="td_label" style="width:270px">模板名称：</td>
						<td class="td_input" colspan="3">
							<input id="templateName" name="playCategory.templateName" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/>		
						</td>
						
					</tr>
					<tr>
						<td class="formBottom" colspan="99">
							<input id="addPlayCategoryButton" 
							type="button" 
							title="添加" 
							class="b_next" 
							value="添加" 
							onfocus="blur()"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
