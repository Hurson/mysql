<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />

<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/demos.css" type="text/css" />

<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/position/speciCommon.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/util/util.js"></script>

<title>广告系统</title>

<script>
$(function(){
	   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
    
    bindSubmit('#addQuestionMaterialSpeciform');
    $("#loading").hide();
    $("#msgdlg").hide();
    
    $("#addQuestionMaterialSpeciButton").click(function(){
     		$("#addQuestionMaterialSpeciform").submit();
    });
    
    $("#updateQuestionMaterialSpeciButton").click(function(){
     		$("#addQuestionMaterialSpeciform").submit();
    });
    
});
</script>
<style>
.treven{ background:#FFFFFF;}
.trodd{ background:#f6f6f6;}
#loading{background-image:url(<%=path %>/images/jqueryui/loading.gif);background-position:0px 0px;background-repeat:no-repeat; position:absolute;width:50px;height:50px;top:60%;left:50%;margin-left:-25px;text-align:center;}

</style>
</head>

<body onload="">

	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		
		<tr>
			<td style="padding:1px;">
				<form action="addQuestionnaireSpecification.do?method=save"  id="addQuestionMaterialSpeciform" name="addQuestionMaterialSpeciform" method="post">
					<table cellpadding="0" cellspacing="1" width="100%" class="tablea" >
						<tr>
							<td class="td_label" style="width:270px">调查问卷类型：</td>
							<td class="td_input">
								<input id="id" name="questionnaireSpecification.id" type="hidden"/>		
								<input id="typeAdd" name="questionnaireSpecification.type" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="80"/>		
							</td>
							<td class="td_label" style="width:270px">问题个数：</td>
							<td class="td_input">
								<input id="fileSizeAdd" name="questionnaireSpecification.fileSize" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="80"/>		
							</td>
						</tr>
						<tr>
							<td class="td_label" style="width:270px">每个问题选项个数：</td>
							<td class="td_input">
								<input id="optionNumberAdd" name="questionnaireSpecification.optionNumber" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="5"/>		
							</td>
							<td class="td_label" style="width:270px">问题最大字数：</td>
							<td class="td_input">
								<input id="maxLengthAdd" name="questionnaireSpecification.maxLength" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="5"/>	
							</td>
						</tr>
						<tr>
							<td class="td_label" style="width:270px">过滤内容：</td>
							<td class="td_input" colspan="3">
								<input id="excludeContentAdd" name="questionnaireSpecification.excludeContent" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="80"/>	
							</td>
						</tr>
						<tr>
							<td class="formBottom" colspan="99">
								<input id="addQuestionMaterialSpeciButton" 
								type="button" 
								class="b_add" 
								onfocus="blur()"/>
								<input id="updateQuestionMaterialSpeciButton" 
								type="button" 
								class="b_edit" 
								onfocus="blur()"/>
							</td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
	</table>
	<div id="msgdlg" title="消息"></div>
	<div id="loading" style="display:none" ondblclick="this.style.display='none'"></div>
</body>
</html>
