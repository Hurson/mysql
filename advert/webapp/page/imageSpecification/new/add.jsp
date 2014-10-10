<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tags/fmt.tld" prefix="fmt"%>
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

		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/jquery-1.9.0.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/contract/listContract.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker-zh-CN.js">
</script>

		<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js">
</script>
		<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js">
</script>
		<script src="<%=path%>/js/new/avit.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/jquery-1.9.0.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/jquery.form.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/ui/jquery-ui-1.10.0.custom.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/position/speciCommon.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/util/util.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/util/tools.js">
</script>
		<script type="text/javascript"
			src="<%=path%>/js/easydialog/easydialog.min.js">
</script>
		<script>

$(function() {
	init();
	$("#bm").find("tr:even").addClass("treven"); //奇数行的样式
	$("#bm").find("tr:odd").addClass("trodd"); //偶数行的样式
	bindSubmit('#addForm');

	$("#system-dialog").hide();
	$("#addButton").click(function() {
		if (validate()) {
			$('#addForm').submit();
			window.location.href = "queryImageManager.do";
		}
	});
	$("#updateButton").click(function() {
		if (validate()) {
			$('#addForm').submit();
			window.location.href = "queryImageManager.do";
		}
	});

});

function init(){
	if(!$.isEmptyObject(parent.id)){
		$('#id').val(parent.id+'');
		$('#addButton').remove();
	}
	
	if(!$.isEmptyObject(parent.imageDesc)){
		$('#imageDesc').val(parent.imageDesc+'');
		$('#addButton').remove();
	}
	
	if(!$.isEmptyObject(parent.imageLength)){
		$('#imageLength').val(parent.imageLength+'');
		$('#addButton').remove();
	}
	
	if(!$.isEmptyObject(parent.imageWidth)){
		$('#imageWidth').val(parent.imageWidth+'');
		$('#addButton').remove();
	}
	
	if(!$.isEmptyObject(parent.fileSize)){
		$('#fileSize').val(parent.fileSize+'');
		$('#addButton').remove();
	}
	
	if(!$.isEmptyObject(parent.type)){
		$('#type').val(parent.type+'');
		$('#addButton').remove();
	}
	
	if(!$.isEmptyObject(parent.isLink)){
		$('#isLink').val(parent.isLink+'');
		$('#addButton').remove();
	}
}

function validate() {
	var flag = true;
	if ($.isEmptyObject($('#imageDesc').val())) {
		flag = false;
		alert('描述不能为空');
		return flag;
	}
	if ($.isEmptyObject($('#imageLength').val())) {
		flag = false;
		alert('图片长不能为空');
		return flag;
	} else {
		flag = validateNumber($('#imageLength').val());
		if (!flag) {
			alert('图片长必须为数字');
			return flag;
		}
	}
	if ($.isEmptyObject($('#imageWidth').val())) {
		flag = false;
		alert('图片宽不能为空');
		return flag;
	} else {
		flag = validateNumber($('#imageWidth').val());
		if (!flag) {
			alert('图片宽必须为数字');
			return flag;
		}
	}
	if ($.isEmptyObject($('#fileSize').val())) {
		flag = false;
		alert('文件大小不能为空');
		return flag;
	} else {
		flag = validateNumber($('#fileSize').val());
		if (!flag) {
			alert('文件大小必须为数字');
			return flag;
		}
	}
	if ($.isEmptyObject($('#type').val())) {
		flag = false;
		alert('类型不能为空');
		return flag;
	}
	if ($.isEmptyObject($('#isLink').val()) || ($('#isLink').val() == -1)) {
		flag = false;
		alert('请选择是否有链接');
		return flag;
	}
	return flag;
}

function goBack() {
	window.location.href = "queryImageManager.do";
}

</script>

		<style>
.treven {
	background: #FFFFFF;
}

.trodd {
	background: #f6f6f6;
}

#loading {
	background-image: url(<%=path%>/images/jqueryui/loading.gif);
	background-position: 0px 0px;
	background-repeat: no-repeat;
	position: absolute;
	width: 50px;
	height: 50px;
	top: 60%;
	left: 50%;
	margin-left: -25px;
	text-align: center;
}
</style>
	</head>
	<body class="mainBody">
		<form action="saveImage.do" id="addForm" name="addForm" method="post">
			<div class="path">
				<img src="<%=path%>/images/new/filder.gif" width="15" height="13" />
				首页 >> 广告位管理 >> 增加图片规格
			</div>
			<div class="searchContent">
				<table cellspacing="1" class="searchList">
					<tr class="title">
						<td colspan="4">
							添加图片规格信息
						</td>
					</tr>
						<c:choose>
					<c:when test="${operate == 'edit'}">
					<tr>
						<td class="td_label" style="width: 270px">
							图片规格描述：
						</td>
						<td class="td_input">
							<input id="id" name="object.id" type="hidden" value="${object.id}"/>
							<input id="imageDesc" name="object.imageDesc" type="text"
								class="e_input" onfocus="this.className='e_inputFocus'"
								onblur="this.className='e_input'" maxlength="80" value="${object.imageDesc}"/>
						</td>
						<td class="td_label" style="width: 270px">
							图片长：
						</td>
						<td class="td_input">
							<input id="imageLength" name="object.imageLength" type="text"
								class="e_input" onfocus="this.className='e_inputFocus'"
								onblur="this.className='e_input'" maxlength="80"  value="${object.imageLength}"/>
						</td>
					</tr>
					<tr>
						<td class="td_label" style="width: 270px">
							图片宽：
						</td>
						<td class="td_input">
							<input id="imageWidth" name="object.imageWidth" type="text"
								class="e_input" onfocus="this.className='e_inputFocus'"
								onblur="this.className='e_input'" maxlength="80" value="${object.imageWidth}"/>
						</td>
						<td class="td_label" style="width: 270px">
							文件大小：
						</td>
						<td class="td_input">
							<input id="fileSize" name="object.fileSize" type="text"
								class="e_input" onfocus="this.className='e_inputFocus'"
								onblur="this.className='e_input'" maxlength="80" value="${object.fileSize}"/>
						</td>
					</tr>
					<tr>
						<td class="td_label" style="width: 270px">
							类型：
						</td>
						<td class="td_input">
							<input id="type" name="object.type" type="text" class="e_input"
								onfocus="this.className='e_inputFocus'"
								onblur="this.className='e_input'" maxlength="80" value="${object.type}"/>
						</td>
						<td class="td_label" style="width: 270px">
							是否有链接：
						</td>
						<td class="td_input">
							<select id="isLink" name="object.isLink" class="e_select"
								onfocus="this.className='e_selectFocus'"
								onblur="this.className='e_select'" value="${object.isLink}">		
								<c:choose>
								<c:when test="${object.isLink==1}">															
								<option value="1" selected="selected">
									是
								</option>
								<option value="0">
									否
								</option>
							</c:when>
							<c:otherwise>
								<option value="1" >
									是
								</option>
								<option value="0" selected="selected">
									否
								</option>
							</c:otherwise>
						</c:choose>
							</select>
						</td>
					</tr>
					</c:when>
							<c:otherwise>
								<tr>
						<td class="td_label" style="width: 270px">
							图片规格描述：
						</td>
						<td class="td_input">
							<input id="id" name="object.id" type="hidden" />
							<input id="imageDesc" name="object.imageDesc" type="text"
								class="e_input" onfocus="this.className='e_inputFocus'"
								onblur="this.className='e_input'" maxlength="80" />
						</td>
						<td class="td_label" style="width: 270px">
							图片长：
						</td>
						<td class="td_input">
							<input id="imageLength" name="object.imageLength" type="text"
								class="e_input" onfocus="this.className='e_inputFocus'"
								onblur="this.className='e_input'" maxlength="80" />
						</td>
					</tr>
					<tr>
						<td class="td_label" style="width: 270px">
							图片宽：
						</td>
						<td class="td_input">
							<input id="imageWidth" name="object.imageWidth" type="text"
								class="e_input" onfocus="this.className='e_inputFocus'"
								onblur="this.className='e_input'" maxlength="80" />
						</td>
						<td class="td_label" style="width: 270px">
							文件大小：
						</td>
						<td class="td_input">
							<input id="fileSize" name="object.fileSize" type="text"
								class="e_input" onfocus="this.className='e_inputFocus'"
								onblur="this.className='e_input'" maxlength="80" />
						</td>
					</tr>
					<tr>
						<td class="td_label" style="width: 270px">
							类型：
						</td>
						<td class="td_input">
							<input id="type" name="object.type" type="text" class="e_input"
								onfocus="this.className='e_inputFocus'"
								onblur="this.className='e_input'" maxlength="80" />
						</td>
						<td class="td_label" style="width: 270px">
							是否有链接：
						</td>
						<td class="td_input">
							<select id="isLink" name="object.isLink" class="e_select"
								onfocus="this.className='e_selectFocus'"
								onblur="this.className='e_select'">
								<option value="-1">
									请选择
								</option>
								<option value="1">
									是
								</option>
								<option value="0">
									否
								</option>
							</select>
						</td>
					</tr>
								</c:otherwise>
						</c:choose>
					<tr>
						<td class="formBottom" colspan="99">
							<input id="addButton" type="button" class="btn" value="保存"
								onfocus="blur()" />

							<input type="button" class="btn" value="返回" onclick="goBack();" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>