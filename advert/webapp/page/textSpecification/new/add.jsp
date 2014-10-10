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
	$('#system-dialog').hide();
	$("#bm").find("tr:even").addClass("treven"); //奇数行的样式
	$("#bm").find("tr:odd").addClass("trodd"); //偶数行的样式
	bindSubmit('#addForm');

	$("#addButton").click(function() {
		if (validate()) {
			$("#addForm").submit();
			window.location.href = "queryTextManager.do";
		}
	});
	$("#updateButton").click(function() {
		if (validate()) {
			$("#addForm").submit();
			window.location.href = "queryTextManager.do";
		}
	});
});

function init() {

	if (!$.isEmptyObject(parent.id)) {
		$('#id').val(parent.id + '');
		$('#addButton').remove();
	}

	if (!$.isEmptyObject(parent.textDesc)) {
		$('#textDesc').val(parent.textDesc + '');
		$('#addButton').remove();
	}

	if (!$.isEmptyObject(parent.textLength)) {
		$('#textLength').val(parent.textLength + '');
		$('#addButton').remove();
	}

	if (!$.isEmptyObject(parent.isLink)) {
		$('#isLink').val(parent.isLink + '');
		$('#addButton').remove();
	}

}

function bindSubmit(formName) {
	var options = {
		success : showResponse,
		error : showError
	};

	$(formName).submit(function() {
		$(this).ajaxSubmit(options);
		return false;
	});
}

function showResponse(responseText, statusText, xhr, $form) {
	messagebox(responseText);
	bindSubmit();
}

function showError(xhr, ajaxOptions, thrownError) {
	messagebox("出错了!" + thrownError);
	bindSubmit();
}

function messagebox(msg) {

	var responseMsg = eval('(' + msg + ')');
	if (responseMsg != null) {
		if (responseMsg.method == 'save') {
			if (responseMsg.flag == 'success') {
				createDialog('保存成功');
				removeAddButton(responseMsg.dataType);
				fillPrimaryId(responseMsg);
			} else {
				createDialog('保存失败');
			}
		} else if (responseMsg.method = 'update') {
			if (responseMsg.flag == 'success') {
				createDialog('更新成功');
				fillPrimaryId(responseMsg);
			} else {
				createDialog('更新失败');
			}
		}
	}
}

function validate() {
	var flag = true;
	if ($.isEmptyObject($('#textDesc').val())) {
		flag = false;
		createDialog('描述不能为空');
		return flag;
	}
	if ($.isEmptyObject($('#textLength').val())) {
		flag = false;
		createDialog('长度不能为空');
		return flag;
	} else {
		flag = validateNumber($('#textLength').val());
		if (!flag) {
			createDialog('长度必须为数字');
			return flag;
		}
	}
	if ($.isEmptyObject($('#isLink').val()) || ($('#isLink').val() == -1)) {
		flag = false;
		createDialog('请选择是否有链接');
		return flag;
	}
	return flag;
}

function goBack() {
	window.location.href = "queryTextManager.do";
}
</script>
	</head>
	<body class="mainBody">
		<form action="saveText.do" id="addForm" name="addForm" method="post">
			<div class="path">
				<img src="<%=path%>/images/new/filder.gif" width="15" height="13" />
				首页 >> 广告位管理 >> 增加文字规格
			</div>
			<div class="searchContent">
				<table cellspacing="1" class="searchList">
					<tr class="title">
						<td colspan="4">
							添加文字规格信息
						</td>
					</tr>
					<c:choose>
						<c:when test="${operate == 'edit'}">
							<tr>
								<td class="td_label">
									文字规格描述：
								</td>
								<td class="td_input" colspan="3">
									<input id="id" name="object.id" type="hidden"
										value="${object.id}" />
									<input id="textDesc" name="object.textDesc" type="text"
										class="e_input" onfocus="this.className='e_inputFocus'"
										onblur="this.className='e_input'" maxlength="80"
										value="${object.textDesc}" />
								</td>
							</tr>
							<tr>
								<td class="td_label">
									文字长度：
								</td>
								<td class="td_input" colspan="3">
									<input id="textLength" name="object.textLength" type="text"
										class="e_input" onfocus="this.className='e_inputFocus'"
										onblur="this.className='e_input'" maxlength="80"
										value="${object.textLength}" />
								</td>
							</tr>
							<tr>
								<td class="td_label">
									是否有链接:
								</td>
								<td class="td_input" colspan="3">
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
												<option value="1">
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
								<td class="td_label">
									文字规格描述：
								</td>
								<td class="td_input" colspan="3">
									<input id="id" name="object.id" type="hidden" />
									<input id="textDesc" name="object.textDesc" type="text"
										class="e_input" onfocus="this.className='e_inputFocus'"
										onblur="this.className='e_input'" maxlength="80" />
								</td>
							</tr>
							<tr>
								<td class="td_label">
									文字长度：
								</td>
								<td class="td_input" colspan="3">
									<input id="textLength" name="object.textLength" type="text"
										class="e_input" onfocus="this.className='e_inputFocus'"
										onblur="this.className='e_input'" maxlength="80" />
								</td>
							</tr>
							<tr>
								<td class="td_label">
									是否有链接:
								</td>
								<td class="td_input" colspan="3">
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
