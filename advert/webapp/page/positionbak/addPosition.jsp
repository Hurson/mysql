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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />

<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery-ui-timepicker-addon.css" type="text/css" />

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

<script type="text/javascript" src="<%=path%>/js/position/addPosition.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/tools.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/util.js"></script>
<title>广告系统</title>
<style>
	.easyDialog_wrapper{ width:1000px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
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
	$('#positionTypeId').val(positionTypeId);
	$('#positionTypeName').val(positionTypeName);
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

$(function(){
		
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
	 $("#system-dialog").hide();	
		var advertPosition = "${advertPosition}";
			
		//初始化描述信息
		var requestDesc = '${fn:trim(advertPosition.description)}';
		if(!$.isEmptyObject(requestDesc)){
			$('#description').val(requestDesc);
		}
		
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
											alert('图片上传至FTP失败');
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
});
</script>
<style>
.treven{ background:#FFFFFF;}
.trodd{ background:#f6f6f6;}
</style>
</head>
	<input id="projetPath" type="hidden" value="<%=path%>"/>
<body onload="">
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td>
				<table cellpadding="0" cellspacing="0" border="0" width="100%"
					class="tab_right">
					<tr>
						<td>工作台</td>
						<td>客户</td>
						<td>日程行动</td>
						<td>销售机会</td>
						<td>订单管理</td>
						<td>客服中心</td>
						<td>财务中心</td>
						<td>营销中心</td>
						<td>人力资源中心</td>
						<td>数据统计</td>
						<td>信息门户管理</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
					<form action="#"  id="addPositionform" name="addPositionform" method="get">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							
								<tr>
									<td style=" padding:4px;">
										<table cellpadding="0" cellspacing="1" width="100%" class="tablea" >
											<tr>
												<td class="formTitle" colspan="99">
													<span>广告位</span>
												</td>
											</tr>
											<tr>
												<td class="td_label">广告位特征值：</td>
												<td class="td_input">
													<input id="characteristicIdentification" name="advertPosition.characteristicIdentification" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${advertPosition.characteristicIdentification}" maxlength="80" />
													<input id="id" name="advertPosition.id" type="hidden" value="${advertPosition.id}"/>
												</td>
												<td class="td_label">广告位名称：</td>
												<td class="td_input">
													<input id="positionName" name="advertPosition.positionName" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${advertPosition.positionName}" maxlength="80"/>	
												</td>
												<td class="td_label">折扣：</td>
												<td class="td_input">
													<input id="discount" name="advertPosition.discount" value="${advertPosition.discount}" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="80"/>	
												</td>
											</tr>
												<tr>
													<td class="td_label">投放方式：</td>
													<td class="td_input">
														<select id="deliveryMode" name="advertPosition.deliveryMode" class="e_select"  onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'">
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
													<td class="td_label">是否高清：</td>
													<td class="td_input">
														<select id="isHd" name="advertPosition.isHd" class="e_select"  onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'">
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
																<c:choose>
																		<c:when test="${advertPosition.isHd==2}">
																			<option value="2" selected="selected">都支持</option>
																		</c:when>
																		<c:otherwise>
																			<option value="2">都支持</option>
																		</c:otherwise>
																</c:choose>
														</select>		
													</td>
												
													<td class="td_label" >是否叠加：</td>
													<td class="td_input">
														<select id="isAdd" name="advertPosition.isAdd" class="e_select"  onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'">
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
											</tr>	
											<tr>
												<td class="td_label">广告位类型:</td>
												<td class="td_input">
													<input id="positionTypeId" name="advertPosition.positionTypeId" type="hidden" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${advertPosition.positionTypeId}"/>
													<input id="positionTypeName" name="advertPosition.positionTypeName" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${advertPositionType.positionTypeName}"/>
												</td>
												<td class="td_label">投放平台:</td>
												<td class="td_input">
													<select id="deliveryPlatform" name="advertPosition.deliveryPlatform" class="e_select"  onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'" >
															<option value="-1">请选择</option>
															<c:choose>
																	<c:when test="${advertPosition.deliveryPlatform==0}">
																		<option value="0" selected="selected">OCG</option>
																	</c:when>
																	<c:otherwise>
																		<option value="0">OCG</option>
																	</c:otherwise>
															</c:choose>
															<c:choose>
																	<c:when test="${advertPosition.deliveryPlatform==1}">
																		<option value="1" selected="selected">UNT</option>
																	</c:when>
																	<c:otherwise>
																		<option value="1">UNT</option>
																	</c:otherwise>
															</c:choose>
													</select>	
												</td>
												<td class="td_label">是否轮询：</td>
													<td class="td_input">
														<select id="isLoop" name="advertPosition.isLoop" class="e_select"  onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'">
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
											</tr>			
											<tr>
												<td class="td_label">广告位图片坐标：</td>
												<td class="td_input">
													<input id="coordinate" name="advertPosition.coordinate" value="${advertPosition.coordinate}" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="80"/>
												</td>
												<td class="td_label">价格：</td>
												<td class="td_input">
													<input id="price" name="advertPosition.price" value="${advertPosition.price}" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="80"/>
												</td>
												<td class="td_label">轮询个数：</td>
												<td class="td_input">
													<input id="materialNumber" name="advertPosition.materialNumber" value="${advertPosition.materialNumber}" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="2"/>	
												</td>
											</tr>
											<tr>
												<td class="td_label">开始时间：</td>
												<td class="td_input">
													<input id="startTime" name="advertPosition.startTime" value="${advertPosition.startTime}" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
												</td>
												<td class="td_label">结束时间：</td>
												<td class="td_input">
													<input id="endTime" name="advertPosition.endTime" value="${advertPosition.endTime}" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
												</td>
												<td class="td_label">宽,高</td>
												<td class="td_input">
													<input id="widthHeight" name="advertPosition.widthHeight" value="${advertPosition.widthHeight}" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />	
												</td>
											</tr>
											
										</table>
											<table cellpadding="0" cellspacing="1" width="100%" class="tableayl" >
												<tr>
														<td class="td_label" rowspan="2">背景图预览效果：</td>
														<td class="td_input" rowspan="2">
															<div id = "positionViewDiv" style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:345px;height:250px;">
																<img id="positionViewDivImg" src="<%=path%>/images/position/position.jpg" style="background-repeat:no-repeat; width:345px;height:250px;"/>											
															</div>		
														</td>
														<td class="td_label" rowspan="2">描述：</td>
														<td class="td_input" rowspan="2">
															<textarea id="description" name="advertPosition.description" value="${advertPosition.description}" style="overflow:hidden;padding:0;width:345px;height:125px;border:1px solid gray;" >		
															</textarea>
														</td>
												</tr>
													
										</table>
										<table cellpadding="0" cellspacing="1" width="100%" class="tableayl" >
												<tr id="removeBackGroundImageTd">
													<input id="textRuleId" name="advertPosition.textRuleIdList" type="hidden" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${advertPosition.textRuleIdList}" readonly="readonly" />
												    <input id="videoRuleId" name="advertPosition.videoRuleIdList" type="hidden" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${advertPosition.videoRuleIdList}" readonly="readonly"/>													
													<input id="imageRuleId" name="advertPosition.imageRuleIdList" type="hidden" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${advertPosition.imageRuleIdList}" readonly="readonly"/>
													<input id="questionRuleId" name="advertPosition.questionRuleIdList" type="hidden" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${advertPosition.questionRuleIdList}" readonly="readonly"/>
												
												<td class="td_labelp">广告位背景图：</td>
												<td class="td_inputp">
													<input id="backupPictureImage" name="backupPictureImage" type="file" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/>
													<input id="backgroundPath" name="advertPosition.backgroundPath" value="${advertPosition.backgroundPath}" type="hidden" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />			
												</td>
												<td class="td_labelp">已选规格查询：</td>
												<td class="td_inputp">
													<input id="viewChooseRule" name="viewChooseRule" type="button"  value="点击查看已选规格" />
												</td>
												<td class="td_labelp"></td>
												<td class="td_inputp" colspan="2">
													<input id="addPositionButton" 
															type="button"
															class="b_add"
															onfocus="blur()"/>
												</td>
												
											</tr>
													
										</table>
									</td>
								</tr>
						</table>
					</form>
				</td>
		</tr>
</table>
<div id="system-dialog" title="友情提示">
  <p>
    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>
    <span id="content"></span>
  </p>
</div>
</body>
</html>