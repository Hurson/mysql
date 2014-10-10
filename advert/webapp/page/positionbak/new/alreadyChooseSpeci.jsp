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

<link rel="stylesheet" type="text/css" href="<%=path%>/css/new/main.css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<input id="projetPath" type="hidden" value="<%=path%>"/>
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<script language="javascript" type="text/javascript"  src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/util/util.js"></script>

<title>查看已选规格</title>
<script type="text/javascript">

$(function(){
		$('#returnButton').click(function(){
			parent.easyDialog.close();
		});
		//加载显示内容
		showInfo();		
});

function showInfo(){
		//页面对应按钮隐藏
		displayButton('text',0);
		displayButton('video',0);
		displayButton('image',0);
	
		var showArray = parent.speciArray;
	
		if(!$.isEmptyObject(showArray)){
					
					if(!$.isEmptyObject(showArray.image)&&(showArray.image.length>0)){
						$(showArray.image).each(function(imageIndex,imageItem){
							if(imageIndex<1){
								$('#imageType'+imageIndex).html(imageItem.imageType);
								$('#imageId'+imageIndex).html(imageItem.imageId);
								$('#imageLength'+imageIndex).html(imageItem.imageLength);
								$('#imageWidth'+imageIndex).html(imageItem.imageWidth);
								$('#imageFileSize'+imageIndex).html(imageItem.imageFileSize);
								$('#imageDataType'+imageIndex).html(showInfoTransform(imageItem.imageDataType,'dataType'));
								$('#imageIsLink'+imageIndex).html(showInfoTransform(imageItem.imageIsLink,'isLink'));
								displayButton('image',1);
							}
						});
					}else{
						//数据为空，清空原有数据
						$('#imageType0').html('');
						$('#imageId0').html('');
						$('#imageLength0').html('');
						$('#imageWidth0').html('');
						$('#imageFileSize0').html('');
						$('#imageDataType0').html('');
						$('#imageIsLink0').html('');
					}
	
					if(!$.isEmptyObject(showArray.video)&&(showArray.video.length>0)){
						$(showArray.video).each(function(videoIndex,videoItem){
							if(videoIndex<1){
								$('#videoDataType'+videoIndex).html(showInfoTransform(videoItem.videoDataType,'dataType'));
								//$('#movieDesc'+videoIndex).html(videoItem.movieDesc);
								$('#videoId'+videoIndex).html(videoItem.id);
								$('#resolution'+videoIndex).html(videoItem.resolution);
								$('#duration'+videoIndex).html(videoItem.duration);
								$('#fileSize'+videoIndex).html(videoItem.fileSize);
								$('#type'+videoIndex).html(videoItem.type);
								displayButton('video',1);	
							}
						});
					}else{
						//数据为空，清空原有数据
						$('#videoDataType0').html('');
						$('#videoId0').html('');
						$('#resolution0').html('');
						$('#duration0').html('');
						$('#fileSize0').html('');
						$('#type0').html('');
					}
						
					if(!$.isEmptyObject(showArray.content)&&(showArray.content.length>0)){
						$(showArray.content).each(function(contentIndex,contentItem){
							if(contentIndex<1){
								$('#textDataType'+contentIndex).html(showInfoTransform(contentItem.textDataType,'dataType'));
								$('#textId'+contentIndex).html(contentItem.id);
								$('#textLength'+contentIndex).html(contentItem.textLength);
								$('#textIsLink'+contentIndex).html(showInfoTransform(contentItem.isLink,'isLink'));
								displayButton('text',1);	
							}
						});
					}else{
						//数据为空，清空原有数据
						$('#textDataType0').html('');
						$('#textId0').html('');
						$('#textLength0').html('');
						$('#textIsLink0').html('');
					}
		}
}

/**
 * 控制元素显示还是隐藏
 */
function displayButton(element,flag){
	if(flag==0){
		$('#'+element+'DeleteButton0').html('');
	}else{
		$('#'+element+'DeleteButton0').html('');
		$('#'+element+'DeleteButton0').html('<input id="'+element+'DeleteButton0" name="'+element+'DeleteButton0" type=button value="删除" class="btn" onclick="deleteButton(\''+element+'\',0)"/>');
	}
}
  /**
 * 删除按钮触发事件
 * @param element 元素类型 
 * @param index 索引位置
 */
function deleteButton(element,index){
	var showArray = parent.speciArray;
	if('text'==element&&(!$.isEmptyObject(showArray))){
		showArray.text.splice(index,1);
	}else if('video'==element&&(!$.isEmptyObject(showArray))){
		showArray.video.splice(index,1);
	}else if('image'==element&&(!$.isEmptyObject(showArray))){
		showArray.image.splice(index,1);
	}else if('question'==element&&(!$.isEmptyObject(showArray))){
		showArray.question.splice(index,1);
	}
	//初始化数据显示
	showInfo();
} 


</script>
<style type="text/css">
        a{text-decoration:underline;}
</style>
</head>

<body class="mainBody">

<div class="search">
  <div class="searchContent" >
<table cellspacing="1" class="searchList" id="bm">
  <tr class="title" >
    	<td colspan="8">查看 文字规格</td>
  </tr>
  <tr class="sec">
  		<td class="view_speci_td"><span style="font-weight:bold;">数据类型</span></td>
    	<td class="view_speci_td"><span style="font-weight:bold;">ID</span></td>
    	<td class="view_speci_td"><span style="font-weight:bold;">文字长度</span></td>
    	<td class="view_speci_td"><span style="font-weight:bold;">是否有连接</span></td>
		<td class="view_speci_td"><span style="font-weight:bold;">操作</span></td>
		<td colspan="4" class="view_speci_td"></td>
  </tr>
  <tr>
  		<td class="view_speci_td" id="textDataType0"></td>
    	<td class="view_speci_td" id="textId0"></td>
    	<td class="view_speci_td" id="textLength0"></td>
    	<td class="view_speci_td" id="textIsLink0">
			
		</td>
		<td class="view_speci_td" id="textDeleteButton0">
			
		</td>
		<td colspan="4">
		</td>
  </tr>
  <tr class="title">
    <td colspan="8">查看 视频规格</td>
  </tr>
  <tr class="sec">
  		<td class="view_speci_td"><span style="font-weight:bold;">数据类型</span></td>
  		<td class="view_speci_td"><span style="font-weight:bold;">ID</span></td>
  		<td class="view_speci_td"><span style="font-weight:bold;">分辨率</span></td>
  		<td class="view_speci_td"><span style="font-weight:bold;">时长</span></td>
    	<td class="view_speci_td"><span style="font-weight:bold;">文件大小</span></td>
		<td class="view_speci_td"><span style="font-weight:bold;">类型</span></td>
    	<td class="view_speci_td"><span style="font-weight:bold;">操作</span></td>
    	<td class="view_speci_td"></td>
  </tr>
   <tr>
  		<td class="view_speci_td" id="videoDataType0"></td>
  		<td class="view_speci_td" id="videoId0"></td>
  		<td class="view_speci_td" id="resolution0"></td>
    	<td class="view_speci_td" id="duration0"></td>
		<td class="view_speci_td" id="fileSize0"></td>
    	<td class="view_speci_td" id="type0"></td>
    	<td class="view_speci_td" id="videoDeleteButton0">
    		
    	</td>
    	<td></td>
  </tr>
  <tr class="title">
    <td colspan="8">查询 图片规格</td>
  </tr>
  <tr class="sec">
  		<td class="view_speci_td"><span style="font-weight:bold;">数据类型</span></td>
  		<td class="view_speci_td"><span style="font-weight:bold;">ID</span></td>
  		<td class="view_speci_td"><span style="font-weight:bold;">图片长</span></td>
    	<td class="view_speci_td"><span style="font-weight:bold;">图片宽</span></td>
    	<td class="view_speci_td"><span style="font-weight:bold;">文件大小</span></td>
    	<td class="view_speci_td"><span style="font-weight:bold;">图片类型</span></td>
    	<td class="view_speci_td"><span style="font-weight:bold;">是否有连接</span></td>
    	<td class="view_speci_td"><span style="font-weight:bold;">操作</span></td>    						
  </tr>
  <tr>
  		<td class="view_speci_td"  id="imageDataType0"></td>
  		<td class="view_speci_td"  id="imageId0"></td>
    	<td class="view_speci_td"  id="imageLength0"></td>
    	<td class="view_speci_td"  id="imageWidth0"></td>
    	<td class="view_speci_td"  id="imageFileSize0"></td>
		<td class="view_speci_td"  id="imageType0"></td>
    	<td class="view_speci_td"  id="imageIsLink0"></td>
    	<td class="view_speci_td"  id="imageDeleteButton0">
    		
    	</td>					
  </tr>
</table>
<br />
<br />
<div align="center">
 	<input id="returnButton" name="returnButton" type="button" class="btn" value="返回"/>
</div>
</div>
</div>
</body>
</html>