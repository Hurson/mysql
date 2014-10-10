<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/questionnaire/addQuestionnaire.js"></script>
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
<title>新建调查问卷</title>
</head>
<body>
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
<form id="saveForm" enctype="multipart/form-data">
<input type="hidden" name="templateId" value="${templateId}"/>
<table cellpadding="0" cellspacing="1" width="100%" class="tablea" >
	<tr>
		<td class="formTitle" colspan="99">
			<span>·创建调查问卷</span>
			
		</td>
	</tr>
	<tr>
		<td class="td_label">问卷名称<span style="color:red;">(50字以内)</span>：</td>
	 	<td class="td_input">
			<input id="name" type="text" name="name" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
		</td>
		<td class="td_label">合同号：</td>
		<td class="td_input">
			<select id="contractCode" name="contractCode" class="e_select"  onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'">
				<option value="-1">请选择</option>
				<option value="1">合同号1</option>
				<option value="2">合同号2</option>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="td_label">问卷描述<span style="color:red;">(120字以内)</span>：</td>
	 	<td class="td_input"  colspan="3">
	 		<textarea id="description" name="description" class="e_texteare" onfocus="this.className='e_texteareFocus'" onblur="this.className='e_texteare'" rows="2" cols="100"></textarea>
		</td> 
	</tr>
	<c:if test="${templateJsBean.summaryId!=null}">
		<tr>
			<td class="td_label">问卷摘要<span style="color:red;">(300字以内)</span>：</td>
			<td class="td_input"  colspan="3">
				<textarea id="summary" name="summary" class="e_texteare" onfocus="this.className='e_texteareFocus'" onblur="this.className='e_texteare'"  rows="5" cols="100"></textarea>
			</td>
		</tr>
	</c:if>
	
	
	<c:choose>
		<c:when test="${templateJsBean.pictureId!=null&&templateJsBean.videoId!=null}">
			<tr>
				<td class="td_label">上传图片：</td>
			 	<td class="td_input">
					<input id="picturePath" name="picture" type="file" class="e_inputfile" onfocus="this.className='e_inputFocusfile'" onblur="this.className='e_inputfile'" />
				</td>
				<td class="td_label">上传视频：</td>
				<td class="td_input">
					<input id="videoPath" name="video" type="file" class="e_inputfile" onfocus="this.className='e_inputFocusfile'" onblur="this.className='e_inputfile'" />
				</td>
			</tr>
		</c:when>
		<c:when test="${templateJsBean.pictureId!=null&&templateJsBean.videoId==null}">
			<tr>
				<td class="td_label">上传图片：</td>
			 	<td class="td_input" colspan="3">
					<input id="picturePath" name="picturePath" type="file" class="e_inputfile" onfocus="this.className='e_inputFocusfile'" onblur="this.className='e_inputfile'" />
				</td>
			</tr>
		</c:when>
		<c:when test="${templateJsBean.pictureId==null&&templateJsBean.videoId!=null}">
			<tr>
				<td class="td_label">上传视频：</td>
				<td class="td_input" colspan="3">
					<input id="videoPath" name="videoPath" type="file" class="e_inputfile" onfocus="this.className='e_inputFocusfile'" onblur="this.className='e_inputfile'" />
				</td>
			</tr>
		</c:when>
	</c:choose>
		
	
	<c:set var="index" value="0"/>
	<c:forEach items="${templateJsBean.question}" var="question">
		<input type="hidden" id="optionNum${index }" value="${question.optionNum }"/>
		<tr>
			<td class="td_label">问题${index+1 }<span style="color:red;">(100字以内)</span>：</td>
			<td class="td_input" colspan="3">
				<input type="hidden" name="questions[${index }].questionnaireIndex" value="${index }"/>
				<textarea id="question${index }" name="questions[${index }].question" class="e_texteare" onfocus="this.className='e_texteareFocus'" onblur="this.className='e_texteare'"  rows="2" cols="100"></textarea>
			</td>
		</tr>
		<tr>
			<td class="td_label">问题${index+1 }选项<span style="color:red;">(共80字以内)</span>：</td>
			<td class="td_input" colspan="3">
				<c:set var="oIndex" value="0"/>
				<c:forEach items="${question.option}" var="option">	
					<input id="option${index }${oIndex }" type="input" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />	
					<c:set var="oIndex" value="${oIndex+1 }"/>
				</c:forEach>
				<input id="option${index }" type="hidden" name="questions[${index }].options" value=""/>
			</td>
		</tr>
		<c:set var="index" value="${index+1 }"/>
	</c:forEach>
	<tr>
		<td class="formBottom" colspan="99">
			<input name="Submit" 
			type="button" 
			title="添加" 
			class="b_add" 
			value="" 
			onfocus=blur() 
			onclick="firstSubmit(${templateJsBean.questionNum});"/>
			<input name="Submit" 
			type="reset" 
			title="重置"
			class="b_edit" 
			value="" 
			onfocus=blur() />
		</td>
	</tr>
</table>
</form>
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