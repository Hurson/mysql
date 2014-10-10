<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<input id="projetPath" type="hidden" value="<%=path%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css"
	type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css"
	type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css"
	type="text/css" />
<script language="javascript" type="text/javascript"
	src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript"
	src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css"
	type="text/css" />
<script language="javascript" type="text/javascript"
	src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=path %>/js/util/util.js"></script>
<title>无标题文档</title>
<script type="">
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
	//加载显示内容
	showInfo();
});


function showInfo(){
	
	var showArray = parent.speciArray;
	
	if(!$.isEmptyObject(showArray)){
				
				if(!$.isEmptyObject(showArray.image)){
					$(showArray.image).each(function(imageIndex,imageItem){
						
						if(imageIndex<2){
							$('#imageType'+imageIndex).html(imageItem.imageType);
							$('#imageId'+imageIndex).html(imageItem.imageId);
							$('#imageLength'+imageIndex).html(imageItem.imageLength);
							$('#imageWidth'+imageIndex).html(imageItem.imageWidth);
							$('#imageFileSize'+imageIndex).html(imageItem.imageFileSize);
							$('#imageDataType'+imageIndex).html(showInfoTransform(imageItem.imageDataType,'dataType'));
							$('#imageIsLink'+imageIndex).html(showInfoTransform(imageItem.imageIsLink,'isLink'));		
						}
					});
				}

				if(!$.isEmptyObject(showArray.video)){
					$(showArray.video).each(function(videoIndex,videoItem){
						if(videoIndex<2){
							$('#videoDataType'+videoIndex).html(showInfoTransform(videoItem.videoDataType,'dataType'));
							//$('#movieDesc'+videoIndex).html(videoItem.movieDesc);
							$('#videoId'+videoIndex).html(videoItem.id);
							$('#resolution'+videoIndex).html(videoItem.resolution);
							$('#duration'+videoIndex).html(videoItem.duration);
							$('#fileSize'+videoIndex).html(videoItem.fileSize);
							$('#type'+videoIndex).html(videoItem.type);
						}
					});
				}

				if(!$.isEmptyObject(showArray.question)){
					$(showArray.question).each(function(questionIndex,questionItem){
						if(questionIndex<2){
							$('#questionType'+questionIndex).html(questionItem.questiontype);
							$('#questionId'+questionIndex).html(questionItem.id);
							$('#questionDataType'+questionIndex).html(showInfoTransform(questionItem.questionDataType,'dataType'));
							$('#questionFileSize'+questionIndex).html(questionItem.questionFileSize);
							$('#optionNumber'+questionIndex).html(questionItem.optionNumber);						
							$('#questionMaxLength'+questionIndex).html(questionItem.questionMaxLength);
							$('#questionExcludeContent'+questionIndex).html(questionItem.questionExcludeContent);	
						}
					});
				}
					
				if(!$.isEmptyObject(showArray.content)){
					$(showArray.content).each(function(contentIndex,contentItem){
						if(contentIndex<2){
							$('#textDataType'+contentIndex).html(showInfoTransform(contentItem.textDataType,'dataType'));
							$('#textId'+contentIndex).html(contentItem.id);
							$('#textLength'+contentIndex).html(contentItem.textLength);
							$('#textIsLink'+contentIndex).html(showInfoTransform(contentItem.isLink,'isLink'));
						}
					});
				}
				
			
		
	}
	
}

</script>
<style>
.treven {
	background: #FFFFFF;
}

.trodd {
	background: #f6f6f6;
}
</style>
</head>

<body>
<table cellpadding="0" cellspacing="0" border="0" width="100%"
	height="100%">
	<tr>
		<td style="padding: 1px;">
		<table cellpadding="0" cellspacing="1" width="100%" height="25%" class="tablea">
			<tr>
				<td class="formTitle" colspan="99"><span>查询 文字规格</span></td>
			</tr>
			<tr>
				<td class="td_label1">数据类型</td>
				<td class="td_label1">ID</td>
				<td class="td_label1">文字长度</td>
				<td class="td_label1">是否有链接</td>
				<td class="td_label1"></td>
				<td class="td_label1"></td>
				<td class="td_label1"></td>
				<td class="td_label1"></td>
			</tr>
			<tr>
				<td class="td_input1" align="center" id="textDataType0"></td>
				<td class="td_input1" align="center" id="textId0"></td>
				<td class="td_input1" align="center" id="textLength0"></td>
				<td class="td_input1" align="center" id="textIsLink0"></td>
				<td class="td_input1" align="center" id=""></td>
				<td class="td_input1" align="center" id=""></td>
				<td class="td_input1" align="center" id=""></td>
				<td class="td_input1" align="center" id=""></td>
			</tr>
			<tr>
				<td class="td_input1" align="center" id="textDataType1"></td>
				<td class="td_input1" align="center" id="textId1"></td>
				<td class="td_input1" align="center" id="textLength1"></td>
				<td class="td_input1" align="center" id="textIsLink1"></td>
				<td class="td_input1" align="center" id=""></td>
				<td class="td_input1" align="center" id=""></td>
				<td class="td_input1" align="center" id=""></td>
				<td class="td_input1" align="center" id=""></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td style="padding: 1px;">
			<table cellpadding="0" cellspacing="1" width="100%" height="25%" class="tablea">
				<tr>
					<td class="formTitle" colspan="99"><span>查询 视频规格</span></td>
				</tr>
				<tr>
					<td class="td_label1">数据类型</td>
					<td class="td_label1">ID</td>
					<td class="td_label1">分辨率</td>
					<td class="td_label1">时长</td>
					<td class="td_label1">文件大小</td>
					<td class="td_label1">类型</td>
					<td class="td_label1"></td>
				</tr>
				<tr>
					<td class="td_input1" align="center" id="videoDataType0"></td>
					<td class="td_input1" align="center" id="videoId0"></td>
					<td class="td_input1" align="center" id="resolution0"></td>
					<td class="td_input1" align="center" id="duration0"></td>
					<td class="td_input1" align="center" id="fileSize0"></td>
					<td class="td_input1" align="center" id="type0"></td>
					<td class="td_input1" align="center" id=""></td>
				</tr>
				<tr>
					<td class="td_input1" align="center" id="videoDataType1"></td>
					<td class="td_input1" align="center" id="videoId1"></td>
					<td class="td_input1" align="center" id="resolution1"></td>
					<td class="td_input1" align="center" id="duration1"></td>
					<td class="td_input1" align="center" id="fileSize1"></td>
					<td class="td_input1" align="center" id="type1"></td>
					<td class="td_input1" align="center" id=""></td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="padding: 1px;">
		<table cellpadding="0" cellspacing="1" width="100%" height="25%" class="tablea">
			<tr>
				<td class="formTitle" colspan="99"><span>查询 调查问卷规格</span></td>
			</tr>
			<tr>
				<td class="td_label1">数据类型</td>
				<td class="td_label1">ID</td>
				<td class="td_label1">问题个数</td>
				<td class="td_label1">每个问题选项个数</td>
				<td class="td_label1">问题最大字数</td>
				<td class="td_label1">过滤内容</td>
				<td class="td_label1">类型</td>
			</tr>
			<tr>
				<td class="td_input1" align="center" id="questionDataType0"></td>
				<td class="td_input1" align="center" id="questionId0"></td>
				<td class="td_input1" align="center" id="questionFileSize0"></td>
				<td class="td_input1" align="center" id="optionNumber0"></td>
				<td class="td_input1" align="center" id="questionMaxLength0"></td>
				<td class="td_input1" align="center" id="questionExcludeContent0"></td>
				<td class="td_input1" align="center" id="questionType0"></td>
			</tr>
			<tr>
				<td class="td_input1" align="center" id="questionDataType1"></td>
				<td class="td_input1" align="center" id="questionId1"></td>
				<td class="td_input1" align="center" id="questionFileSize1"></td>
				<td class="td_input1" align="center" id="questionOptionNumber1"></td>
				<td class="td_input1" align="center" id="questionMaxLength1"></td>
				<td class="td_input1" align="center" id="questionExcludeContent1"></td>
				<td class="td_input1" align="center" id="questionType1"></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td style="padding: 1px;">
			<table cellpadding="0" cellspacing="1" width="100%" class="tablea">
			<tr>
				<td class="formTitle" colspan="99"><span>查询 图片规格</span></td>
			</tr>
			<tr>
				<td class="td_label1">数据类型</td>
				<td class="td_label1">ID</td>
				<td class="td_label1">图片长</td>
				<td class="td_label1">图片宽</td>
				<td class="td_label1">文件大小</td>
				<td class="td_label1">图片类型</td>
				<td class="td_label1">是否有链接</td>
			</tr>
			<tr>
				<td class="td_input1" align="center" id="imageDataType0"></td>
				<td class="td_input1" align="center" id="imageId0"></td>
				<td class="td_input1" align="center" id="imageLength0"></td>
				<td class="td_input1" align="center" id="imageWidth0"></td>
				<td class="td_input1" align="center" id="imageFileSize0"></td>
				<td class="td_input1" align="center" id="imageType0"></td>
				<td class="td_input1" align="center" id="imageIsLink0"></td>
			</tr>
			<tr>
				<td class="td_input1" align="center" id="imageDataType1"></td>
				<td class="td_input1" align="center" id="imageId1"></td>
				<td class="td_input1" align="center" id="imageLength1"></td>
				<td class="td_input1" align="center" id="imageWidth1"></td>
				<td class="td_input1" align="center" id="imageFileSize1"></td>
				<td class="td_input1" align="center" id="imageType1"></td>
				<td class="td_input1" align="center" id="imageIsLink1"></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td style="padding: 1px;" colspan="99">
	<tr>
		<td class="formBottom" colspan="99">
		</td>
	</tr>
	</td>
	</tr>
</table>
</body>
</html>