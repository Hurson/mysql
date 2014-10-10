<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="fn" uri="/WEB-INF/tags/fn.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>广告系统</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<input id="projetPath" type="hidden" value="<%=path%>"/>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css">
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />

<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.core.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.widget.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.tabs.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.accordion.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/validate/jquery.validate.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/validate/jquery.validate.messages_cn.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery-ui-timepicker-addon.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery-ui-timepicker-zh-CN.js"></script>

<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/swfobject.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>

<script type="text/javascript" src="<%=path%>/js/position/new/addPosition.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/tools.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/util.js"></script>
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
<script>
var speciArray = {'content':[],'image':[],'video':[],'question':[]};
var advertPositionType='${advertPositionType}';
var videoSpecification='${videoSpecification}';
var questionnaireSpecification='${questionnaireSpecification}';
var textSpecification='${textSpecification}';
var imageSpecification='${imageSpecification}';

if(!$.isEmptyObject(advertPositionType)){
	var positionTypeId='${advertPosition.positionTypeId}';
	var positionTypeName='${advertPositionType.positionTypeName}';
	var positionTypeCode='${advertPositionType.positionTypeCode}';
	$('#positionTypeId').val(positionTypeId);
	$('#positionTypeName').val(positionTypeName);
	$('#positionTypeCode').val(positionTypeCode);
}

if(!$.isEmptyObject(videoSpecification)){
	var videoSpeci = "{'videoDataType':'"+showInfoTransform('${advertPosition.isHd}','showDataType')+"','id':'"+"${videoSpecification.id}"+"','movieDesc':'"+"${videoSpecification.movieDesc}"+"','resolution':'"+"${videoSpecification.resolution}"+"','duration':'"+"${videoSpecification.duration}"+"','fileSize':'"+"${videoSpecification.fileSize}"+"','type':'"+"${videoSpecification.type}"+"'}";
	videoSpeci=eval('('+videoSpeci+')');
	speciArray.video.push(videoSpeci);
}

if(!$.isEmptyObject(questionnaireSpecification)){
	var questSpeci = "{'id':'"+"${questionnaireSpecification.id}"+"','questiontype':'"+"${questionnaireSpecification.type}"+"','questionDataType':'"+showInfoTransform('${advertPosition.isHd}','showDataType')+"','questionFileSize':'"+"${questionnaireSpecification.fileSize}"+"','optionNumber':'"+"${questionnaireSpecification.optionNumber}"+"','questionMaxLength':'"+"${questionnaireSpecification.maxLength}"+"','questionExcludeContent':'"+"${questionnaireSpecification.excludeContent}"+"'}";
	questSpeci=eval('('+questSpeci+')');
	speciArray.question.push(questSpeci);
}

if(!$.isEmptyObject(textSpecification)){
	var textSpeci = "{'textDataType':'"+showInfoTransform('${advertPosition.isHd}','showDataType')+"','textDesc':'"+"${textSpecification.textDesc}"+"','textLength':'"+"${textSpecification.textLength}"+"','isLink':'"+'${textSpecification.isLink}'+"','id':'"+"${textSpecification.id}"+"'}";
	textSpeci=eval('('+textSpeci+')');
	speciArray.content.push(textSpeci);
}

if(!$.isEmptyObject(imageSpecification)){
	var imageSpeci = "{'imageDataType':'"+showInfoTransform('${advertPosition.isHd}','showDataType')+"','imageId':'"+"${imageSpecification.id}"+"','imageDesc':'"+"${imageSpecification.imageDesc}"+"','imageLength':'"+"${imageSpecification.imageLength}"+"','imageWidth':'"+"${imageSpecification.imageWidth}"+"','imageFileSize':'"+"${imageSpecification.fileSize}"+"','imageType':'"+"${imageSpecification.type}"+"','imageIsLink':"+"'${imageSpecification.isLink}'"+"}";
	imageSpeci=eval('('+imageSpeci+')');
	speciArray.image.push(imageSpeci);
}

function generateIsHdTabStruct(isHdVal){
	
		var isHDInfo = '';
		isHDInfo+= '<div id="tabs">';
		isHDInfo+='		<ul>';
		if(isHdVal=='0'){
			isHDInfo+='			<li><a href="#sd" style="font-size:15px;font-weight:bold">标清</a></li>';
		}else if(isHdVal=='1'){
			isHDInfo+='			<li><a href="#hd" style="font-size:15px;font-weight:bold">高清</a></li>';
		}
		isHDInfo+='		</ul>';
	
	if(isHdVal=='0'){

		isHDInfo+='			<div id="sd">';	
		isHDInfo+='		    <div id="accordion">';
		
		if((!$.isEmptyObject(alreadyChoosePtype))&&(alreadyChoosePtype.isText==1)){
			isHDInfo+='					<h3 style="font-weight:bold">标清类型 选择 文字规格</h3>';
			isHDInfo+='					<div style="margin:0px;padding:0px;">';
			isHDInfo+='							<iframe src="'+resourcePath+'/page/textSpecification/queryText.do?isHd=sd" frameBorder="0" width="945px" height="250px"  scrolling="yes"></iframe>';
			isHDInfo+='					</div>';
		}
		
		if((!$.isEmptyObject(alreadyChoosePtype))&&(alreadyChoosePtype.isVideo==1)){
			isHDInfo+='					<h3 style="font-weight:bold">标清类型 选择 视频规格</h3>';
			isHDInfo+='					<div style="margin:0px;padding:0px;">';
			isHDInfo+='							<iframe src="'+resourcePath+'/page/videoMaterialSpeci/queryVideo.do?isHd=sd" frameBorder="0" width="945px" height="250px"  scrolling="yes"></iframe>';
			isHDInfo+='					</div>';
		}
		
		if((!$.isEmptyObject(alreadyChoosePtype))&&(alreadyChoosePtype.isImage==1)){
			isHDInfo+='					<h3 style="font-weight:bold">标清类型 选择 图片规格</h3>';
			isHDInfo+='					<div style="margin:0px;padding:0px;">';
			isHDInfo+='							<iframe src="'+resourcePath+'/page/imageSpecification/queryImage.do?isHd=sd" frameBorder="0" width="945px" height="250px" scrolling="yes"></iframe>';
			isHDInfo+='					</div>';
		}
		
		isHDInfo+='			</div>';
		isHDInfo+='		</div>';
	}else if(isHdVal=='1'){
		isHDInfo+='			<div id="hd">';
		isHDInfo+='				<div id="accordion">';
		
		if((!$.isEmptyObject(alreadyChoosePtype))&&(alreadyChoosePtype.isText==1)){
			isHDInfo+='					<h3 style="font-weight:bold">高清类型 选择 文字规格</h3>';
			isHDInfo+='					<div style="margin:0px;padding:0px;">';
			isHDInfo+='							<iframe src="'+resourcePath+'/page/textSpecification/queryText.do?isHd=hd" frameBorder="0" width="945px" height="250px"  scrolling="yes"></iframe>';
			isHDInfo+='					</div>';
		}
		
		if((!$.isEmptyObject(alreadyChoosePtype))&&(alreadyChoosePtype.isVideo==1)){
			isHDInfo+='					<h3 style="font-weight:bold">高清类型 选择 视频规格</h3>';
			isHDInfo+='					<div style="margin:0px;padding:0px;">';
			isHDInfo+='							<iframe src="'+resourcePath+'/page/videoMaterialSpeci/queryVideo.do?isHd=hd" frameBorder="0" width="945px" height="250px"  scrolling="yes"></iframe>';
			isHDInfo+='					</div style="margin:0px;padding:0px;">';
		}
		
		if((!$.isEmptyObject(alreadyChoosePtype))&&(alreadyChoosePtype.isImage==1)){
			isHDInfo+='					<h3 style="font-weight:bold">高清类型 选择 图片规格</h3>';
			isHDInfo+='					<div style="margin:0px;padding:0px;">';
			isHDInfo+='							<iframe src="'+resourcePath+'/page/imageSpecification/queryImage.do?isHd=hd" frameBorder="0" width="945px" height="250px"  scrolling="yes"></iframe>';
			isHDInfo+='					</div>';
		}
		isHDInfo+='			   </div>';
		isHDInfo+='		</div>';
	}
	
	isHDInfo+= '</div>';
	return isHDInfo;
}

/**
 * 添加频道--高清
 */
function isHdReduce(isHdVal){
	easyDialog.open({
			container : {
				header : '规格展示',
				content : generateIsHdTabStruct(isHdVal)
			},
			overlay : false
	});
	$( "#tabs" ).tabs();
	$( "#accordion" ).accordion({
		heightStyle: "content"
	});
}

/**
 * 添加频道--标清
 */
function isSdReduce(isHdVal){
		easyDialog.open({
			container : {
				header : '规格展示',
				content : generateIsHdTabStruct(isHdVal)
			},
			overlay : false
		});
		$( "#tabs" ).tabs();
		$( "#accordion" ).accordion();
}

/**
 * isHd接收器，根据用户选择的值，执行不同的处理逻辑 
 */
function isHdMap(isHdVal){
	switch(isHdVal){
		case '0':
		isSdReduce(isHdVal);
		break;
		case '1':
		isHdReduce(isHdVal);
		break;
		default:
		break;
	}
}

/**
 * 获取已选广告位类型
 */
function initPtype(){
	if((!$.isEmptyObject(alreadyChoosePtype))){
		
		// 切换广告位类型时，先清空原有选择的素材规格
		speciArray.content=[];
		speciArray.image=[];
		speciArray.video=[];
		speciArray.question=[];
		
		// 初始化 isHd 为待选状态
		$('#isHd').val(-1);
		
		//根据已选广告位类型初始化相关信息
		$('#positionTypeId').val(alreadyChoosePtype.id);
		$('#positionTypeName').val(alreadyChoosePtype.positionTypeName);
		$('#positionTypeCode').val(alreadyChoosePtype.positionTypeCode);
		
		//是否叠加 1：是 0：否
		var isAdd = alreadyChoosePtype.isAdd;
		$('#isAdd').val(isAdd);
		//是否轮询 1：是 0：否
		var isLoop = alreadyChoosePtype.isLoop;
		$('#isLoop').val(isLoop);
		
		//如果不是轮询或者处于未选择状态
     	if((isLoop==-1)||(isLoop==0)){
     		$("#materialNumber").removeAttr("disabled");
     		$("#materialNumber").val(0);
     		$("#materialNumber").attr('disabled',"disabled");
     	}else{
     		$("#materialNumber").removeAttr("disabled");
     		$("#materialNumber").val(0);
     	}
		
		//投放方式 0 投放式  1 请求式
		var deliveryType = alreadyChoosePtype.deliveryType;
		var deliveryType = $('#deliveryMode').val(deliveryType);
		
		
		
	}
}

$(function(){
		
		//设置轮询次数为只读状态
		var isLoop = '${advertPosition.isLoop}';
		if(!$.isEmptyObject(isLoop)){
			if(isLoop!=1){
				$("#materialNumber").val(0);
     			$("#materialNumber").attr('disabled',"disabled");
			}
		}
		
		
		
		$("#system-dialog").hide();	
		var advertPosition = "${advertPosition}";
		
		//初始化背景图
		var requestBackgroundImage = '${advertPosition.backgroundPath}';
		if(!$.isEmptyObject(requestBackgroundImage)){
			requestBackgroundImage='<%=path%>/'+requestBackgroundImage;
			$('#positionViewDivImg').attr("src",requestBackgroundImage);
		}
		
		//根据传入的参数判断表单中的值是否可以进行修改
		var formEnable = '${formEnable}';
		if($.isEmptyObject(formEnable)){
		$("#backupPictureImage").uploadify({
							'uploader':'<%=path%>/js/jquery/upload/image/uploadify.swf',
							'script':'uploadPositionBI.do?method=uploadImage',
							'cancelImg':'<%=path%>/js/jquery/upload/image/cancel.png',
							'folder':'/uploadFiles',
							'queueID':'fileQueue',
							'buttonImg':'<%=path%>/js/jquery/upload/image/uploadify.jpg',
							'fileDataName': 'backgroundImage',
							'auto':true,
							'multi':false,
							'fileExt':'*.jpg;*.jepg;*.gif;*.png',
							 'displayData':'speed',
							'onComplete':function(event,queueID,fileObj,response,data){
								var json = eval('(' + response + ')');
								if(json!=null){
									if(json.position=='local'){
										if(json.result=='true'){
											$("#positionViewDivImg").attr("src","");
											$("#positionViewDivImg").attr("src","<%=path%>/images/position/"+json.viewpath);
											$("#backgroundPath").val(json.filepath);
											//alert($("#backgroundImage").val());
										}else{
											alert('图片上传至FTP失败');
										}
									}else if(json.position='ftp'){
										if(json.result=='true'){
											$("#positionViewDivImg").attr("src","");
											$("#positionViewDivImg").attr("src","<%=path %>/images/position/"+json.viewpath);
											$("#backgroundPath").val(json.filepath);
											//alert($("#backgroundImage").val());
										}else{
											//alert('图片上传至FTP失败');
										}
									}	
								}
							}
						});
						
		}else{
			if('false'==formEnable){
				enableInputLable('position');
			}
		}
		
		$("#returnButton").click(function(){
			history.go(-1);
		});
		
		
});
</script>
<style>
	.easyDialog_wrapper{width:1000px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
	.e_input_add {
		background:url(<%=path%>/images/add.png) right no-repeat #ffffff;
	}
</style>
</head>

<body class="mainBody">
	<input id="projetPath" type="hidden" value="<%=path%>"/>
<div class="path">
	<img src="<%=path %>/images/new/filder.gif" width="15" height="13" />
		首页 >> 广告位管理 >> 广告位维护
</div>
<div class="searchContent" >
<form action="#"  id="addPositionform" name="addPositionform" method="get">
	<table  id="bm" cellspacing="1" class="searchList" cellpadding="3">
	    <tr class="title">
	        <td colspan="6">广告位信息</td>
	    </tr>
	    <tr>
	        <td align="right" width="10%">
	        	<span>广告位名称：</span>
	        </td>
	        <td> 
	        	<input id="positionName" name="advertPosition.positionName" type="text" value="${advertPosition.positionName}" maxlength="80"/>
	        	<input id="id" name="advertPosition.id" type="hidden" value="${advertPosition.id}"/>
	        </td>
			<td align="right" width="10%">
				<span> 价    格：</span>
			</td>
			<td> 
				<input id="price" name="advertPosition.price" value="${advertPosition.price}" type="text" maxlength="80"/>
			</td>
			<td colspan="2">&nbsp;</td>
	     </tr>
		 <tr>
	        <td align="right" >
				<span> 广告位类型：</span> 
			</td>
			<td>
				 <input id="positionTypeId" name="advertPosition.positionTypeId" type="hidden" value="${advertPosition.positionTypeId}"/>
				 <input id="positionTypeCode" name="advertPosition.positionTypeCode" type="hidden" value="${advertPosition.positionTypeCode}"/>
				 <input id="positionTypeName" name="advertPosition.positionTypeName" type="text" value="${advertPositionType.positionTypeName}" class="e_input_add"/>
			</td>
			<td align="right" >
				<span>
					广告位坐标：
				</span>
			</td>
			<td>
				<input id="coordinate" name="advertPosition.coordinate" value="${advertPosition.coordinate}" type="text" border="0" maxlength="80"/><span style="color:red">*分隔</span>
			</td>
			<td align="right" width="10%">
				<span> 宽*高：</span>
			</td>
			<td> 
				<input id="widthHeight" name="advertPosition.widthHeight" value="${advertPosition.widthHeight}"  type="text" value="56*56" border="0"/><span style="color:red">*分隔</span>
			</td>
	    </tr>
		<tr>
	        <td align="right" >
				<span> 高/标清：</span>
			</td>
			<td>
				<select id="isHd" name="advertPosition.isHd" readonly="readonly">
					<option value="-1">请选择</option>
																<c:choose>
																		<c:when test="${advertPosition.isHd==0}">
																			<option value="0" selected="selected">标清</option>
																		</c:when>
																		<c:otherwise>
																			<option value="0">标清</option>
																		</c:otherwise>
																</c:choose>
																<c:choose>
																		<c:when test="${advertPosition.isHd==1}">
																			<option value="1" selected="selected">高清</option>
																		</c:when>
																		<c:otherwise>
																			<option value="1">高清</option>
																		</c:otherwise>
																</c:choose>
																
				</select>
			</td>
			<td align="right" width="10%">
				<input id="viewChooseRule" name="viewChooseRule" type="button" value="查看已绑规格" class="btn" onclick=''/>
			</td>
			<td colspan="3">&nbsp;</td>
	    </tr>
		
		<tr>
	        <td align="right" >
				<span> 投放方式：</span> 
			</td>
			<td>
				 <select id="deliveryMode" name="advertPosition.deliveryMode" class="select_style" disabled="disabled">
		            <option value="-1">请选择</option>
																<c:choose>
																		<c:when test="${advertPosition.deliveryMode==0}">
																			<option value="0" selected="selected">投放式</option>
																		</c:when>
																		<c:otherwise>
																			<option value="0">投放式</option>
																		</c:otherwise>
																</c:choose>
																<c:choose>
																		<c:when test="${advertPosition.deliveryMode==1}">
																			<option value="1" selected="selected">请求式</option>
																		</c:when>
																		<c:otherwise>
																			<option value="1">请求式</option>
																		</c:otherwise>
																</c:choose>
		        </select>
			</td>
			<td colspan="4">&nbsp;</td>
	    </tr>
		<tr>
	        <td align="right" >
				<span /> 是否叠加：</span> 
			</td>
			<td>
				 <select id="isAdd" name="advertPosition.isAdd" class="select_style" disabled="disabled">
		           <option value="-1">请选择</option>
																<c:choose>
																		<c:when test="${advertPosition.isAdd==0}">
																			<option value="0" selected="selected">否</option>
																		</c:when>
																		<c:otherwise>
																			<option value="0">否</option>
																		</c:otherwise>
																</c:choose>
																<c:choose>
																		<c:when test="${advertPosition.isAdd==1}">
																			<option value="1" selected="selected">是</option>
																		</c:when>
																		<c:otherwise>
																			<option value="1">是</option>
																		</c:otherwise>
																</c:choose>
		        </select>
			</td>
			<td align="right" >
				<span> 是否轮询：</span> </td><td>
				 <select id="isLoop" name="advertPosition.isLoop" class="select_style" disabled="disabled">
		           <option value="-1">请选择</option>
																<c:choose>
																		<c:when test="${advertPosition.isLoop==0}">
																			<option value="0" selected="selected">否</option>
																		</c:when>
																		<c:otherwise>
																			<option value="0">否</option>
																		</c:otherwise>
																</c:choose>
																<c:choose>
																		<c:when test="${advertPosition.isLoop==1}">
																			<option value="1" selected="selected">是</option>
																		</c:when>
																		<c:otherwise>
																			<option value="1">是</option>
																		</c:otherwise>
																</c:choose>
		        </select>
			</td>		
			 <td align="right" >
				<span> 轮询次数：</span>
			 </td>
			 <td>
				<input id="materialNumber" name="advertPosition.materialNumber" value="${advertPosition.materialNumber}"/>
			</td>
	    </tr>
		<tr>
	        <td align="right" >
			<span>广告位背景图：</span> 
			</td>
			<td>
				<input id="backupPictureImage" name="backupPictureImage" type="file" />
				<input id="backgroundPath" name="advertPosition.backgroundPath" value="${advertPosition.backgroundPath}" type="hidden"/>	
			</td>		
			<td align="right"  colspan="4"></td>
	    </tr>
		<tr>
	        <td align="right" >
				<span>效果预览：</span> 
			</td>
			<td colspan="3">
				<img id="positionViewDivImg" src="<%=path %>/images/new/bg_06.jpg" border="2" width="100%"/>
			</td>		
			 <td colspan="2">&nbsp;</td>
	    </tr>
		<tr>
			<td colspan="6" align="center">
				<input id="addPositionButton" name="addPositionButton" type="button" class="btn" value="确认"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="returnButton" name="returnButton" type="button" class="btn" value="返回"/>
			</td>
		</tr>
	  </table>
  </form>
</div>
<div id="system-dialog" title="友情提示">
  <p>
    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>
    <span id="content"></span>
  </p>
</div>
</body>
</html>