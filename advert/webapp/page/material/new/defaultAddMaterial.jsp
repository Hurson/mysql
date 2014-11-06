<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />


<script type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="<%=path%>/js/material/uploadMaterial.js"></script>
<script type="text/javascript" src="<%=path%>/js/new/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>


<script type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/addOrUpdate.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/swfobject.js"></script>
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>

<title>广告系统</title>

<script type="text/javascript">
/** 宽度 */
var width = 0;
/** 高度 */
var height = 0;
var check = "";
//window.onload = function() {
function init(positionJson){
	var id = document.getElementById("material.id").value;
	if(id == null || id == ""){
		return;
	}
	var adsPositionCode = document.getElementById('material.advertPositionId').value;
	
	if(adsPositionCode == 24 || adsPositionCode == 23){
		document.getElementById("urlTr").style.display = "";
	}
	var materialType =document.getElementById('materialType').value;
	var imageMetaName = document.getElementById('imageMetaName').value;
	var zipMetaName = document.getElementById('zipMetaName').value;
	var videoMetaName = document.getElementById('videoMetaName').value;
	var materialId = document.getElementById('material.id').value;
	var viewPath = document.getElementById('viewPath').value;
	
	if(materialType!=null && materialType!=""){

		//预览素材
		preview(positionJson);
		
		document.getElementById('sel_material_type').disabled=true;
		document.getElementById('material.advertPositionName').disabled=true;
		
		if (materialType == 1 ){//视频
			document.getElementById('tt').style.display = "";    
			document.getElementById('div_video').style.display = "";     
			document.getElementById('div_text').style.display = "none";      
			document.getElementById('div_image').style.display = "none"; 
			document.getElementById('div_zip').style.display = "none";
			document.getElementById("sel_material_type").options.add(new Option("视频","1"));
			showVideo($("#videoPath").val());
		}
		else if (materialType == 0 ){//图片
			document.getElementById('tt').style.display = "";    
			document.getElementById('div_video').style.display = "none";      
			document.getElementById('div_image').style.display = "";
			document.getElementById('div_text').style.display = "none";
			document.getElementById('div_zip').style.display = "none";
			document.getElementById("sel_material_type").options.add(new Option("图片","0"));
		}
		else if (materialType == 2 ){//文字
			document.getElementById('tt').style.display = "";    
			document.getElementById('div_video').style.display = "none";      
			document.getElementById('div_image').style.display = "none";
			document.getElementById('div_text').style.display = "";  
			document.getElementById('div_zip').style.display = "none";
			document.getElementById("sel_material_type").options.add(new Option("文字","2"));
			showText();
		}
		else if (materialType == 3 ){//调查问卷
			document.getElementById('div_video').style.display = "none";      
			document.getElementById('div_question').style.display = "";
			document.getElementById('queView').style.display = "";
			document.getElementById('div_zip').style.display = "none";
			if(materialId!=""){
				   document.getElementById('sel_template_type').disabled=true;
				   document.getElementById('sel_question_type').disabled=true;
				   
				   var materialStatus = document.getElementById('material.state').value;
				   if(materialStatus!="2"){
				       document.getElementById('questionViewBtn').disabled=true;
				   }
		    }
			document.getElementById("sel_material_type").options.add(new Option("问卷","3"));
		}
		else if (materialType == 4 ){//ZIP
			document.getElementById('tt').style.display = "";    
			document.getElementById('div_video').style.display = "none";      
			document.getElementById('div_image').style.display = "none";
			document.getElementById('div_text').style.display = "none";
			document.getElementById('div_zip').style.display = "";
			document.getElementById("sel_material_type").options.add(new Option("ZIP","4"));
		}
	}
	
	//加载上传组件

	var positionType = $$("material.advertPositionType").value;
	if(positionType == 0 || positionType == 1){
    	document.getElementById("upload11").style.display = "none";
    	document.getElementById("upload13").style.display = "";
  }else if(positionType == 4){
	  	document.getElementById("zipupload11").style.display = "none";
    	document.getElementById("zipupload13").style.display = "";
  }else{
    	document.getElementById("upload11").style.display = "";
    	document.getElementById("upload13").style.display = "none";
  }
}

/**
 * 预览
 */
function preview(positionJson){

	var selPosition = eval(positionJson);
	/**为页面预览区域赋值*/
	var size = selPosition.widthHeight.split('*');
	width = size[0];
	height = size[1];

	if(selPosition.isLoop == 1){
		var locationCount = parseInt(selPosition.loopCount);
		for(var i=2; i<=locationCount; i++){
			document.getElementById("picLocation").options.add(new Option(i+"",i+""));
		}
		document.getElementById("picLocation").removeAttribute("disabled");
	}
	document.getElementById("coordinateStr").value = selPosition.coordinate.replace(/(^\s+)|(\s+$)/g,"");
	
	var coordinate = selPosition.coordinate.replace(/(^\s+)|(\s+$)/g,"").split(",")[0].split('*');
	$("#pImage1").attr("width",426).attr("height",240);
	$("#pImage2").attr("width",426).attr("height",240);
	$("#pImage3").attr("width",426).attr("height",240);
	$("#pImage4").attr("width",426).attr("height",240);
	$("#pImage").attr("src",getContextPath()+"/"+selPosition.backgroundPath);
	$("#mImage").attr("width",width).attr("height",height);

	$("#mImage,#video,#mImage4").css({
		width:width+"px",
		height:height+"px",
		position:'absolute',
		left: coordinate[0]+"px", 
		top: coordinate[1]+"px" 
	});
	
	$("#text").css({
		position:'absolute',
		width:width+"px",
		height:height+"px",
		left:coordinate[0]+"px",
		top:coordinate[1]+"px",
		'z-index':1
	});
}

function priviewLocationChange(){

	var imagePreviewLocation = document.getElementById('picLocation').value;
	var location = parseInt(imagePreviewLocation); 
    var coordinates = document.getElementById('coordinateStr').value.split(",");
    
    var coordinate;
    if(location <= coordinates.length){
    	coordinate = coordinates[location-1].split('*');
    }else{
    	coordinate = coordinates[0].split('*');
    }
	
	$("#mImage,#video,#mImage4,#text").css({
		left: coordinate[0]+"px", 
		top: coordinate[1]+"px" 
	});
	
}

/**
 * 获取上下文路径
 */ 
function getContextPath() {
	var contextPath = document.location.pathname;
	var index = contextPath.substr(1).indexOf("/");
	contextPath = contextPath.substr(0, index + 1);
	delete index;
	return contextPath;
}
var i=0;
//选择子广告位
function selectAdPosition() {
		var positionPackIds = document.getElementById("positionPackIds").value;
		 var structInfo ="";
			structInfo+="			<div style='margin:0px;padding:0px;width:600px'>";
			structInfo+="						<iframe id='selectAdPositionFrame' name='defaultSelectAdPositionFrame'  frameBorder='0' width='1000px' height='500px'  scrolling='yes'  align='top' src='<%=request.getContextPath()%>/page/meterial/selectAdPosition.do?positionPackIds=";
			structInfo+=positionPackIds;
			structInfo+="'> ";
			structInfo+="</iframe>";
			structInfo+="</div>";
			easyDialog.open({
			container : {
				header : "选择广告位",
				content : structInfo
			},
			overlay : true
			
		});
	 }
	 


$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
    $("#file_id").uploadify({
		'uploader':'<%=path%>/js/jquery/upload/image/uploadify.swf',
		'script':'uploadMaterial.do?advertPositionId='+document.getElementById('material.advertPositionId').value,
		'cancelImg':'<%=path%>/js/jquery/upload/image/cancel.png',
		'folder':'/uploadFiles',
		'queueID':'fileQueue',
		'buttonImg':'<%=path%>/js/jquery/upload/image/uploadify.jpg',
		'fileDataName': 'backgroundImage',
		'auto':true,
		'multi':false,
		'fileExt':'*.png;*.jpg;*.iframe',
		'fileDesc':'*.png;*.jpg;*.iframe',
		 'displayData':'speed',
		'width':'76',
    	'height':'23',
		 'onSelect': function (event, queueID, fileObj){ 
			$("#file_id").uploadifySettings('script','uploadMaterial.do?advertPositionId='+document.getElementById('material.advertPositionId').value); 
		 } ,
		'onComplete':function(event,queueID,fileObj,response,data){
			var json = eval('(' + response + ')');
			if(json!=null){
					if(json.position=='local'){
						if(json.result=='true'){
							check = json.checkTag;
							var ss = "";
							var objjudge = document.getElementById("judge");
							if(check == "0"){
								var msg =  "";
								if(json.image_fileSize_fail != null){
									msg += json.image_fileSize_fail +"\n";
								}
								if(json.image_width_fail != null){
									msg += json.image_width_fail +"\n";
								}
								if(json.image_high_fail != null){
									msg += json.image_high_fail ;
								}
								objjudge = document.getElementById("judge");
								objjudge.innerHTML = "不通过";
								
							}else{
							    objjudge = document.getElementById("judge");
								objjudge.innerHTML = "通过";
							
							}
							var imageO = document.getElementById("inputImageFileSize");
							
							ss += "大小："+(json.imageFileSize)+"，宽度："+json.imageFileWidth+" px， 高度："+ json.imageFileHigh+" px";
							imageO.innerHTML = ss;
							$("#mImage").attr("src","<%=path%>/images/material/"+json.viewpath);
							$("#mImage").show();
							$("#backgroundImage").val(json.filepath);
						}else{
							alert('图片上传至FTP失败');
						}
					}
				}	
		}
	});
	
	$("#file_id3").uploadify({
		'uploader':'<%=path%>/js/jquery/upload/image/uploadify.swf',
		'script':'uploadMaterial.do?advertPositionId='+document.getElementById('material.advertPositionId').value,
		'cancelImg':'<%=path%>/js/jquery/upload/image/cancel.png',
		'folder':'/uploadFiles',
		'queueID':'fileQueue',
		'buttonImg':'<%=path%>/js/jquery/upload/image/uploadify.jpg',
		'fileDataName': 'backgroundImage',
		'auto':true,
		'multi':false,
		'fileExt':'*.gif;*.png;*.jpg;*.iframe',
		'fileDesc':'*.gif;*.png;*.jpg;*.iframe',
		 'displayData':'speed',
		'width':'76',
    	'height':'23',
		 'onSelect': function (event, queueID, fileObj){ 
			$("#file_id3").uploadifySettings('script','uploadMaterial.do?advertPositionId='+document.getElementById('material.advertPositionId').value); 
		 } ,
		'onComplete':function(event,queueID,fileObj,response,data){
			var json = eval('(' + response + ')');
			if(json!=null){
					if(json.position=='local'){
						if(json.result=='true'){
							check = json.checkTag;
							var ss = "";
							var objjudge = document.getElementById("judge");
							if(check == "0"){
								var msg =  "";
								if(json.image_fileSize_fail != null){
									msg += json.image_fileSize_fail +"\n";
								}
								if(json.image_width_fail != null){
									msg += json.image_width_fail +"\n";
								}
								if(json.image_high_fail != null){
									msg += json.image_high_fail ;
								}
								objjudge = document.getElementById("judge");
								objjudge.innerHTML = "不通过";
								
							}else{
							    objjudge = document.getElementById("judge");
								objjudge.innerHTML = "通过";
							
							}
							
							 var adsPositionCode = document.getElementById('material.advertPositionId').value;
							
							 if(adsPositionCode == 24 || adsPositionCode == 23){
								 document.getElementById("urlTr").style.display = "";
							 } 
							
							var imageO = document.getElementById("inputImageFileSize");
							
							ss += "大小："+(json.imageFileSize)+"，宽度："+json.imageFileWidth+" px， 高度："+ json.imageFileHigh+" px";
							imageO.innerHTML = ss;
							$("#mImage").attr("src","<%=path%>/images/material/"+json.viewpath);
							$("#mImage").show();
							$("#backgroundImage").val(json.filepath);
						}else{
							alert('图片上传至FTP失败');
						}
					}
				}	
		}
	});
	
	$("#file_id2").uploadify({
		'uploader':'<%=path%>/js/jquery/upload/image/uploadify.swf',
		'script':'uploadMaterialVideo.do?advertPositionId='+document.getElementById('material.advertPositionId').value,
		'cancelImg':'<%=path%>/js/jquery/upload/image/cancel.png',
		'folder':'/uploadFiles',
		'queueID':'fileQueue',
		'buttonImg':'<%=path%>/js/jquery/upload/image/uploadify.jpg',
		'fileDataName': 'backgroundImage2',
		'auto':true,
		'multi':false,
		'fileExt':'*.ts',
		'fileDesc':'*.ts',
		 'displayData':'speed',
		'width':'76',
    	'height':'23',
		  'onSelect': function (event, queueID, fileObj){ 
			$("#file_id2").uploadifySettings('script','uploadMaterialVideo.do?advertPositionId='+document.getElementById('material.advertPositionId').value); 
		 } ,
		'onComplete':function(event,queueID,fileObj,response,data){
			var json = eval('(' + response + ')');
			if(json!=null){
					if(json.position=='local'){
						if(json.result=='true'){
							check = json.checkTag;
							var aa = "";
							var objjudge = document.getElementById("judgevideo");
							if(check == "0"){
								var msg = json.video_duration_fail;
								objjudge = document.getElementById("judgevideo");
								objjudge.innerHTML = "不通过";
							}else{
								objjudge = document.getElementById("judgevideo");
								objjudge.innerHTML = "通过";
							}
							
								
								var imageO = document.getElementById("inputVideoFileSize");
								aa = json.videoDuration+"秒";
								imageO.innerHTML = aa;
								
								document.getElementById("backgroundImage2").value = json.filepath;
								document.getElementById("videoMeta.runTime").value= json.duration;
								$("#vlc").css({
									width:$$("videoWidth").value+"px",
									height:$$("videoHeight").value+"px"
								});
								var vlc=document.getElementById("vlc");
								var vedio_url= '<%= basePath%>'+json.filepath;
								vlc.playlist.clear();
								// 添加播放地址
								vlc.playlist.add(vedio_url);
								// 播放
								vlc.playlist.play();
								$("#video").show();
							
							
						}else{
							alert('图片上传至FTP失败');
						}
					}
				}	
		}
	});
	
	
	<!-- zip start -->
	$("#zipfile_id").uploadify({
		'uploader':'<%=path%>/js/jquery/upload/image/uploadify.swf',
		'script':'uploadMaterialZip.do?advertPositionId='+document.getElementById('material.advertPositionId').value,
		'cancelImg':'<%=path%>/js/jquery/upload/image/cancel.png',
		'folder':'/uploadFiles',
		'queueID':'fileQueue',
		'buttonImg':'<%=path%>/js/jquery/upload/image/uploadify.jpg',
		'fileDataName': 'zipbackgroundImage',
		'auto':true,
		'multi':true,
		'fileExt':'*.zip',
		'fileDesc':'*.zip',
		'displayData':'speed',
		'width':'76',
    	'height':'23',
		'onSelect': function (event, queueID, fileObj){ 
			$("#zipfile_id").uploadifySettings('script','uploadMaterialZip.do?advertPositionId='+document.getElementById('material.advertPositionId').value); 
		 } ,
		'onComplete':function(event,queueID,fileObj,response,data){
			var json = eval('(' + response + ')');
			if(json!=null){
					if(json.position=='local'){
						if(json.result=='true'){
							
							check = json.checkTag;
							var ss = "";
							var objjudge = document.getElementById("zipjudge");
							if(check == "0"){
								var msg =  "";
								if(json.image_fileSize_fail != null){
									msg += json.image_fileSize_fail +"\n";
								}
								if(json.image_width_fail != null){
									msg += json.image_width_fail +"\n";
								}
								if(json.image_high_fail != null){
									msg += json.image_high_fail ;
								}
								objjudge = document.getElementById("zipjudge");
								objjudge.innerHTML = "不通过";
								
							}else{
							    objjudge = document.getElementById("zipjudge");
								objjudge.innerHTML = "通过";
							
							}
							var imageO = document.getElementById("zipFileSize");
							
							ss += "大小："+(json.imageFileSize);
							imageO.innerHTML = ss;
							var viewpath = json.viewpath;
							var previewZipImage = viewpath.substring(0,viewpath.lastIndexOf("."))+".jpg";
							$("#mImage4").attr("src","<%=path%>/images/material/"+previewZipImage);
							$("#mImage4").show();
							$("#zipbackgroundImage").val(json.filepath);
						}else{
							if(json.dirName != "recommend"){
								alert("ZIP包内文件夹名称必须为：recommend");
							}else{
								alert('ZIP文件上传失败');
							}
						}
					}
				}	
		}
	});
	
	
	$("#zipfile_id3").uploadify({
		'uploader':'<%=path%>/js/jquery/upload/image/uploadify.swf',
		'script':'uploadMaterialZip.do?advertPositionId='+document.getElementById('material.advertPositionId').value,
		'cancelImg':'<%=path%>/js/jquery/upload/image/cancel.png',
		'folder':'/uploadFiles',
		'queueID':'fileQueue',
		'buttonImg':'<%=path%>/js/jquery/upload/image/uploadify.jpg',
		'fileDataName': 'zipbackgroundImage',
		'auto':true,
		'multi':true,
		'fileExt':'*.zip',
		'fileDesc':'*.zip',
		'displayData':'speed',
		'width':'76',
    	'height':'23',
		'onSelect': function (event, queueID, fileObj){ 
			$("#zipfile_id3").uploadifySettings('script','uploadMaterialZip.do?advertPositionId='+document.getElementById('material.advertPositionId').value); 
		 } ,
		'onComplete':function(event,queueID,fileObj,response,data){
			var json = eval('(' + response + ')');
			if(json!=null){
					if(json.position=='local'){
						if(json.result=='true'){
							check = json.checkTag;
							var ss = "";
							var objjudge = document.getElementById("zipjudge");
							if(check == "0"){
								var msg =  "";
								if(json.image_fileSize_fail != null){
									msg += json.image_fileSize_fail +"\n";
								}
								if(json.image_width_fail != null){
									msg += json.image_width_fail +"\n";
								}
								if(json.image_high_fail != null){
									msg += json.image_high_fail ;
								}
								objjudge = document.getElementById("zipjudge");
								objjudge.innerHTML = "不通过";
								
							}else{
							    objjudge = document.getElementById("zipjudge");
								objjudge.innerHTML = "通过";
							
							}
							var imageO = document.getElementById("zipFileSize");
							
							ss += "大小："+(json.imageFileSize);
							imageO.innerHTML = ss;
							var viewpath = json.viewpath;
							var previewZipImage = viewpath.substring(0,viewpath.lastIndexOf("."))+".jpg";
							$("#mImage4").attr("src","<%=path%>/images/material/"+previewZipImage);
							$("#mImage4").show();
							$("#zipbackgroundImage").val(json.filepath);
						      					   						   						
						}else{
							if(json.dirName != "recommend"){
								alert("ZIP包内文件夹名称必须为：recommend");
							}else{
								alert('ZIP文件上传失败');
							}
						}
					}
				}	
		}
	});
	
	<!-- zip end-->
	
	
});

function changeType2()
{
   var materialType = selectOptionVal("sel_material_type");
   document.getElementById("sel_material_type").value = materialType;
		//if(materialType == "-1"){
			//alert("请选择-资产类型！");
			//return;
		//}
		
	if (materialType == 1 )
	{//视频
	    //alert("视频："+materialType);
		
		document.getElementById('tt').style.display = "block";
		document.getElementById('div_text').style.display = "none";      
		document.getElementById('div_video').style.display = "block";      
		document.getElementById('div_image').style.display = "none";
		document.getElementById('div_zip').style.display = "none";
	}
	else if (materialType == 0 )
	{//图片
		//alert("图片："+materialType);
		document.getElementById('tt').style.display = "";
		document.getElementById('div_text').style.display = "none";      
		document.getElementById('div_video').style.display = "none";      
		document.getElementById('div_image').style.display = "";
		document.getElementById('div_zip').style.display = "none";
		
	}
	else if (materialType == 2 )
	{//文字
	    //alert("文字："+materialType);
	    document.getElementById('tt').style.display = "";
		document.getElementById('div_text').style.display = "";      
		document.getElementById('div_video').style.display = "none";      
		document.getElementById('div_image').style.display = "none";
		document.getElementById('div_zip').style.display = "none";

	}else if (materialType == 4 )
	{//图片
		//alert("图片："+materialType);
		document.getElementById('tt').style.display = "";
		document.getElementById('div_text').style.display = "none";      
		document.getElementById('div_video').style.display = "none";      
		document.getElementById('div_image').style.display = "none";
		document.getElementById('div_zip').style.display = "";
		
		
	}

}



function closeSavePane() {
    window.location.href = "<%=path %>/page/material/queryDefaultMeterialList.do";
}

function submitForm(){
		//检查素材公共属性是否合法
		if(checkMaterial()){
    		return ;
		}
        var materialType = selectOptionVal("sel_material_type");
        if (materialType == 0 ){
            if(checkImage()){
				return;
            }
        }else if (materialType == 1 ){
        	if(checkVideo()){
				return;
            }
        }else if (materialType == 2 ){
        	if(checkText()){
				return;
            }
        }else if(materialType == 4){
        	if(checkZip()){
        		return;
        	}
        }
        
        document.getElementById("saveForm").submit();
    }
    
    /**
	* 检查素材公共属性是否合法
	*
	*/
    function checkMaterial(){
    	if(check == "0"){
    		alert("素材效验不通过！");
    		return true;
    	}	
        
    	if(isEmpty($$("material.advertPositionId").value)){
			alert("请选择广告位！");
			$$("material.advertPositionName").focus();
    		return true;
		}
    	if(isEmpty($$("material.resourceName").value)){
			alert("素材名称不能为空！");
			$$("material.resourceName").focus();
    		return true;
		}
    	if(isEmpty($$("material.keyWords").value)){
			alert("素材关键字不能为空！");
			$$("material.keyWords").focus();
    		return true;
		}
    	if(isEmpty(selectOptionVal("contentSort")) || selectOptionVal("contentSort")==-1){
			alert("请选择素材分类！");
			$$("contentSort").focus();
    		return true;
		}
    	if(isEmpty(selectOptionVal("sel_material_type")) || selectOptionVal("sel_material_type")==-1){
			alert("请选择素材类型！");
			$$("sel_material_type").focus();
    		return true;
		}
		if($$("material.resourceDesc").value.length > 100){
			alert("素材描述不能超过100个字");
			$$("material.resourceDesc").focus();
    		return true;
		}
		return false;
    }

	/**
	*
	* 检查图片
	*/
	function checkImage(){
		var localFilePath = $$("backgroundImage").value; 
        $("#localFilePath").val(localFilePath);
        var aa = $$("material.id").value;
		if(isEmpty(localFilePath) && isEmpty(aa)){
		
			alert("上传的文件不能为空!");
			return true;
		}
		return false;
    }
	
		/**
	*
	* 检查ZIP
	*/
	function checkZip(){
		var localFilePath = $$("zipbackgroundImage").value; 
        $("#localFilePath").val(localFilePath);
        var aa = $$("material.id").value;
		if(isEmpty($("#localFilePath").val())&& isEmpty(aa)){
			alert("上传的文件不能为空!");
			return true;
		}
		return false;
    }
	

	/**
	*
	* 检查视频
	*/
	function checkVideo(){
		var localFilePath = $$("backgroundImage2").value; 
        $("#localFilePath").val(localFilePath);
		if(isEmpty(localFilePath)){
			alert("上传的文件不能为空!");
			return true;
		}
		return false;
    }

	/**
	*
	* 检查文字
	*/
	function checkText(){
		if(isEmpty($$("textMeta.name").value)){
			alert("文字标题不能为空！");
			$$("textMeta.name").focus();
    		return true;
		}
		if (validateSpecialCharacterAfter($$("textMeta.name").value))
		{
			alert("文字标题不能有特殊字符！");
			$$("textMeta.name").focus();
    		return true;
		}
		
		
		if($$("sel_textMeta_action").value=='-1'){
			alert("文字显示动作不能为空！");
			$$("sel_textMeta_action").focus();
			return true;
		}
		if(isEmpty($$("textMeta.fontSize").value)){
			alert("文本字体大小不能为空！");
			$$("textMeta.fontSize").focus();
    		return true;
		}
		if(!isNumber($$("textMeta.fontSize").value)){
			alert("文本字体大小只能是数字！");
			$$("textMeta.fontSize").focus();
    		return true;
		}
		if(isEmpty($$("textMeta.fontColor").value)){
			alert("文本字体颜色不能为空！");
			$$("textMeta.fontColor").focus();
    		return true;
		}else{
			var type = "\#[0-9a-fA-F]{6}" ;
		    var re = new RegExp(type);
		    if (($$("textMeta.fontColor").value).match(re) == null) {
		        alert("文本字体颜色格式不正确！");
				$$("textMeta.fontColor").focus();
				return true;
		    }
		}
		if(isEmpty($$("textMeta.positionVertexCoordinates").value)){
			alert("文本显示坐标不能为空！");
			$$("textMeta.positionVertexCoordinates").focus();
    		return true;
		}else{
			var coordinates = $$("textMeta.positionVertexCoordinates").value.split("*");
			if(coordinates.length != 2 || !isNumber(coordinates[0]) || !isNumber(coordinates[1])){
				alert("文本显示坐标格式不正确！");
				$$("textMeta.positionVertexCoordinates").focus();
	    		return true;
			}
		}
		if(!isEmpty($$("textMeta.bkgColor").value)){
		   //效验背景色
		    var type = "\#[0-9a-fA-F]{6}" ;
		    var re = new RegExp(type);
		    if (($$("textMeta.bkgColor").value).match(re) == null) {
		        alert("文字显示背景色格式不正确！");
				$$("textMeta.bkgColor").focus();
				return true;
		    }
		   
		}
		if(isEmpty($$("textMeta.contentMsg").value)){
			alert("文字内容不能为空！");
			$$("textMeta.contentMsg").focus();
    		return true;
		}		
				if (validateSpecialCharacterAfter($$("textMeta.contentMsg").value))
		{
			alert("文字内容不能有特殊字符！");
			$$("textMeta.contentMsg").focus();
    		return true;
		}
		
		if(!isEmpty($$("textMeta.durationTime").value) && !isNumber($$("textMeta.durationTime").value)){
			alert("文本显示持续时间只能是数字！");
			$$("textMeta.durationTime").focus();
    		return true;
		}
		if(!isEmpty($$("textMeta.rollSpeed").value) && !isNumber($$("textMeta.rollSpeed").value)){
			alert("文本显示滚动速度只能是数字！");
			$$("textMeta.rollSpeed").focus();
    		return true;
		}
		if(!isEmpty($$("textMeta.durationTime").value) && !isNumber($$("textMeta.durationTime").value)){
			alert("文本显示持续时间只能是数字！");
			$$("textMeta.durationTime").focus();
    		return true;
		}
		if(!isEmpty($$("textMeta.positionWidthHeight").value)){
			var size = $$("textMeta.positionWidthHeight").value.split("*");
			if(size.length != 2 || !isNumber(size[0]) || !isNumber(size[1])){
				alert("文本显示区域格式不正确！");
				$$("textMeta.positionWidthHeight").focus();
	    		return true;
			}
		}
		if($$("sel_textMeta_action").value=='1'){
		//滚动
			if(!isEmpty($$("textMeta.rollSpeed").value) && !isNumber($$("textMeta.rollSpeed").value)){
			alert("文本显示滚动速度只能是数字！");
			$$("textMeta.rollSpeed").focus();
			return true;
		    }

		    if($$("textMeta.rollSpeed").value<=0){
		    alert("文本显示滚动速度必须大于0！");
			$$("textMeta.rollSpeed").focus();
			return true;
		    }
		}
		return false;
    }

	/**预览文字*/
	function showText(){
		if($$("sel_textMeta_action").value!='0'){
		//滚动
		   $("#textContent").css({
			   'color':$$("textMeta.fontColor").value,
			   'font-size':$$("textMeta.fontSize").value+"px"
		   });
		   if($$("textMeta.rollSpeed").value!=''){
			$("#textContent").attr("scrollamount",$$("textMeta.rollSpeed").value);
		   }
		   
		   var content = $$("textMeta.contentMsg").value;
		   if($$("textMeta.URL").value!=''){
			 content = "<a href='"+$$("textMeta.URL").value+"'>"+content+"</a>";
		   }
		    if(isEmpty($$("textMeta.positionVertexCoordinates").value)){
		   
		   }else{
					var coordinates = $$("textMeta.positionVertexCoordinates").value.split("*");
					if(coordinates.length != 2 || !isNumber(coordinates[0]) || !isNumber(coordinates[1]) || coordinates[0]>1280 || coordinates[1]>720){
						
					}
					else
					{
					   var left = coordinates[0]/1280*426+"px";
					   var bottom = coordinates[1]/720*240+"px";					 				  
					   $('#text').css('left',left);
					   $('#text').css('top',bottom);
					}				
	  	}
    	if(!isEmpty($$("textMeta.positionWidthHeight").value)){
					var size = $$("textMeta.positionWidthHeight").value.split("*");
					if(size.length != 2 || !isNumber(size[0]) || !isNumber(size[1])){
					
					}
			    else
			    {
			    	   var width = size[0]/1280*426+"px";
					   var height = size[1]/720*240+"px";
					   $('#text').css('width',width);
					   $('#text').css('height',height);
					}
		 }
		   $("#textContent").html(content);
		   $("#text").show();
		   $("#text2").hide();
		}else{
		//静止
		   $("#textContent2").css({
			   'color':$$("textMeta.fontColor").value,
			   'font-size':$$("textMeta.fontSize").value+"px"
		   });
		   document.getElementById("textMeta.rollSpeed").value="0";
		   document.getElementById("textMeta.rollSpeed").disabled="true";
		   
		   var content = $$("textMeta.contentMsg").value;
		   if($$("textMeta.URL").value!=''){
			content = "<a href='"+$$("textMeta.URL").value+"'>"+content+"</a>";
		   }
		     if(isEmpty($$("textMeta.positionVertexCoordinates").value)){
		   
		   }else{
					var coordinates = $$("textMeta.positionVertexCoordinates").value.split("*");
					if(coordinates.length != 2 || !isNumber(coordinates[0]) || !isNumber(coordinates[1]) || coordinates[0]>1280 || coordinates[1]>720){
						
					}
					else
					{
					   var left = coordinates[0]/1280*426+"px";
					   var bottom = coordinates[1]/720*240+"px";					 				  
					   $('#text2').css('left',left);
					   $('#text2').css('top',bottom);
					}				
	  	}
    	if(!isEmpty($$("textMeta.positionWidthHeight").value)){
					var size = $$("textMeta.positionWidthHeight").value.split("*");
					if(size.length != 2 || !isNumber(size[0]) || !isNumber(size[1])){
					
					}
			    else
			    {
			    	   var width = size[0]/1280*426+"px";
					   var height = size[1]/720*240+"px";
					   $('#text2').css('width',width);
					   $('#text2').css('height',height);
					}
		 }
		   $("#textContent2").html(content);
		   $("#text2").show();
		   $("#text").hide();
		}
	}
	
	function showVideo(path){
		
		var vlc=document.getElementById("vlc");
		vlc.playlist.stop();
		vlc.playlist.clear();
		 // 添加播放地址
		 vlc.playlist.add(path);
		 $("#vlc").css({
		
			width:width+"px",
			height:height+"px"
		});
		 // 播放
		 vlc.playlist.play();
		 
	}
	
	function changeTextAction(){
      var textMetaAction = selectOptionVal("sel_textMeta_action");
        if(textMetaAction==1){
		   document.getElementById("textMeta.rollSpeed").disabled=false;
		}
		if(textMetaAction==0){
		   document.getElementById("textMeta.rollSpeed").value="0";
		   document.getElementById("textMeta.rollSpeed").disabled="true";
		}
    }

</script>
<style>
	.easyDialog_wrapper{ width:1000px;height:400px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
</head>
<body class="mainBody" onload='init(${positionJson});'>
<form action="<%=path %>/page/meterial/saveDefaultMaterialBackup.do" method="post" id="saveForm">	
<div class="path">首页 >> 素材管理 >> 新增默认素材</div>
<div class="searchContent" >
<div class="listDetail">
<div style="position: relative">	
<input id="videoWidth" name="videoWidth" type="hidden" value=""/>
<input id="videoHeight" name="videoHeight" type="hidden" value=""/>
<input id="material.id" name="material.id" type="hidden" value="${material.id}"/>
<input id="viewPath" name="viewPath" type="hidden" value="${viewPath}"/>
<input id="material.state" name="material.state" type="hidden" value="${material.state}"/>
<c:if test="${material.id!= null }">
<input id="materialType" name="material.resourceType" type="hidden" value="${material.resourceType}"/>
<input id="imageMeta.id" name="imageMeta.id" type="hidden" value="${imageMeta.id}"/>
<input id="zipMeta.id" name="zipMeta.id" type="hidden" value="${zipMeta.id}"/>
</c:if>
<input id="material.createTime" name="material.createTime" type="hidden" value="${material.createTime}"/>
<input id="isDefault" name="material.isDefault" type="hidden" value="${material.isDefault}"/>
<input id="positionJson" name="positionJson" type="hidden" value="${positionJson}"/> 
<input id="positionPackIds" name="positionPackIds" type="hidden" value="${positionPackIds}"/>

<input id="imageFileWidth"  type="hidden" value="${imageFileWidth}"/>
<input id="imageFileHigh"  type="hidden" value="${imageFileHigh}"/>
<input id="imageFileSize"  type="hidden" value="${imageFileSize}"/>

<input id="material.resourceTypeTemp" name="material.resourceTypeTemp" type="hidden" />
<table>
		    	<tr>
		    	   <td>
		               <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px">
		                 <tr class="title"><td colspan="4">默认素材信息</td></tr>
		                 <tr>
		                    
		                     <td align="right"><span class="required">*</span>选择广告位：</td>
		                     <td >	                
		                         <input id="material.advertPositionId" name="material.advertPositionId" value="${material.advertPositionId}" type="hidden"  readonly="readonly"/>
				                 <input id="material.advertPositionName" name="material.advertPositionName" value="${material.advertPositionName}" type="text" class="new_input_add" readonly="readonly" onclick="selectAdPosition();"/>
				                 <input id="material.advertPositionType" value="${adPositionQuery.positionPackageType}" type="hidden"  readonly="readonly"/>               
		                    	 <input id="coordinateStr" type="hidden"/> 
		                     </td>
		                     <td align="right">素材位置：</td>
		                     <td >
		                     	 <select disabled="disabled" id="picLocation"  name="picLocation" onchange="javascript:priviewLocationChange();">
								      <option value="1">1</option>
							    </select>
		                     </td>
		                 </tr>		           
		                 <tr>
		                     <td align="right"><span class="required">*</span>素材名称：</td>
		                     <td>
		                	     <input id="material.resourceName" name="material.resourceName" value="${material.resourceName}" type="text" maxlength="40" />
		                     </td>
		                     <td width="15%" align="right"><span class="required">*</span>素材关键字：</td>
		                     <td width="35%">
		                         <input id="material.keyWords" name="material.keyWords" type="text" value="${material.keyWords}" maxlength="50"/>					       
		                     </td>		                
		                 </tr>
		                 <tr>
		                     <td align="right"><span class="required">*</span>素材分类：</td>
		                     <td>
		                	    <select  id="contentSort"  name="material.categoryId">
								      <option id="ad_id" value="-1">请选择...</option>
									         <c:forEach items="${materialCategoryList}" var="typeBean">
										        <option  value="${typeBean.id }" <c:if test="${material.categoryId== typeBean.id}">selected="selected"</c:if> >
										        ${typeBean.categoryName }
										        </option>
									         </c:forEach>
							    </select>
		                     </td>
		                     <td align="right"><span class="required">*</span>素材类型：</td>
		                     <td>
		                        
			              	    <select id="sel_material_type" name="material.resourceType"  onclick="changeType2();">
								     
								            
							    </select>		  
		                     </td>
		                  </tr>
		                 
		                  <tr>
		                     <td align="right"><span class="required"></span>素材描述：</td>
		                     <td colspan="3">
		                	     <textarea id="material.resourceDesc" name="material.resourceDesc" cols="40" rows="3" maxlength="100">${material.resourceDesc}</textarea>		              	
		                     </td>
		                 </tr>
		             </table>
		          </td>
		        </tr>
		        
		        
		        <tr id="tt" style="display:none">
		        	<td>
		                
						<table cellspacing="1" class="content" align="left" style="margin-bottom: 0px" id ="div_text" style="display: none">
		                      <tr class="title" >
		                          <td colspan="4">文字素材</td>
		                      </tr>
		                      <tr>
		                          <td  align="right"><span class="required">*</span>文字标题：</td>
		                          <td>
		                              <input id="textMeta.id" name="textMeta.id" type="hidden" value="${textMeta.id}"/>
			            		      <input id="textMeta.name" maxlength="40" name="textMeta.name" value="${textMeta.name}"/>
		                          </td>
		                          <td  align="right">文件URL：</td>
		                          <td>
			            		      <input id="textMeta.URL" maxlength="80" name="textMeta.URL" value="${textMeta.URL}"/>
		                         </td>
		                      </tr>
		                      <tr>
		                          <td  align="right">文本显示动作：</td>
		                          <td>
		                          	<select  id="sel_textMeta_action"  name="textMeta.action" onchange="changeTextAction()">
								               <!-- <option id="action_id" value="-1">请选择...</option>
										        -->   <option  value="1" <c:if test="${textMeta.action== 1}">selected="selected"</c:if> >
										                        滚动
										        </option>
										        <option  value="0" <c:if test="${textMeta.action==0}">selected="selected"</c:if> >
										                        静止
										        </option>
										       
							          </select>
		                          </td>
		                          <td  align="right"></td>
		                          <td>
			            		      <input style="display:none" id="textMeta.durationTime" maxlength="5" name="textMeta.durationTime" value="${textMeta.durationTime}"/>
		                         </td>
		                      </tr>
		                      <tr>
		                          <td  align="right"><span class="required">*</span>文本字体大小：</td>
		                          <td>
			            		      <input id="textMeta.fontSize" maxlength="3" name="textMeta.fontSize"  value="${textMeta.fontSize}"/>
		                          </td>
		                          <td  align="right"><span class="required">*</span>文本字体颜色：</td>
		                          <td>
			            		      <input id="textMeta.fontColor" maxlength="7" name="textMeta.fontColor" value="${textMeta.fontColor}" /><span class="required">格式：#235612</span>
		                         </td>
		                      </tr>
		                      <tr>
		                          <td  align="right">文本显示背景色：</td>
		                          <td>
			            		      <input id="textMeta.bkgColor" maxlength="7" name="textMeta.bkgColor" value="${textMeta.bkgColor}"/><span class="required">格式：#235612</span>
		                          </td>
		                          <td  align="right">文本显示滚动速度：</td>
		                          <td>
			            		    <!-- <input id="textMeta.rollSpeed" maxlength="2" name="textMeta.rollSpeed" value="${textMeta.rollSpeed}"/>
		                           -->  
		                            <select  id="textMeta.rollSpeed"  name="textMeta.rollSpeed" >
								                 <option  value="2" <c:if test="${textMeta.rollSpeed==2}">selected="selected"</c:if> >
										                        低速
										        </option>
										        <option  value="6" <c:if test="${textMeta.rollSpeed== 6}">selected="selected"</c:if> >
										                        中速
										        </option>
										         <option  value="12" <c:if test="${textMeta.rollSpeed== 12}">selected="selected"</c:if> >
										                        高速
										        </option>
		                         </select>
		                         </td>
		                      </tr>
		                      <tr>
		                          <td  align="right"><span class="required">*</span>文本显示坐标：</td>
		                          <td>
			            		      <input id="textMeta.positionVertexCoordinates" maxlength="12" name="textMeta.positionVertexCoordinates" value="${textMeta.positionVertexCoordinates}"/><span class="required">格式：80*80(坐标x*y)</span>
		                          </td>
		                          <td  align="right">文本显示区域：</td>
		                          <td>
			            		      <input id="textMeta.positionWidthHeight" maxlength="12" name="textMeta.positionWidthHeight" value="${textMeta.positionWidthHeight}"/><span class="required">格式：80*80(宽高w*h)</span>
		                         </td>
		                      </tr>
		                      <tr>
		            	          <td width="15%" align="right"><span class="required"></span>内容：</td>
		                          <td colspan="3">
		                	      <textarea id="textMeta.contentMsg" name="textMeta.contentMsg" onchange="javascript:showText();"  maxlength="4000" cols="80" rows="5">${textMeta.contentMsg}</textarea>
		                          </td>
		                      </tr> 
		                      <tr>
							      <td align="right" >素材预览效果：</td>
							      <td colspan="3">
									  <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:426px;height:240px;
									     position: relative;">
											<img id="pImage1" src="<%=path%>/${adPositionQuery.backgroundPath}"  width="426px" height="240px" /> 
											<div id="text"><marquee scrollamount="10" id="textContent"></marquee></div>
										</div>
							      </td>
							
					          </tr>    
		                </table>
					    <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px" id ="div_video" style="display: none">
		                      <tr class="title" >
		                          <td colspan="4">视频素材</td>
		                          <input id="videoMeta.id" name="videoMeta.id" type="hidden" value="${videoMeta.id}"/>
                     			<input id="videoMetaName" name="videoMetaName" type="hidden" value="${videoMeta.name}"/>
		                      </tr>
		                      <tr>
		            	          <td width="15%" align="right">广告位时长规格：</td>
		                          <td width="35%"  >
		                	          <input id="videoFileDuration"  type="text"  value="${videoFileDuration}"  disabled="disabled"/>
		                          </td>
		                          <td width="15%" align="right">素材时长：</td>
		            	          <input id="videoMeta.runTime" name="videoMeta.runTime" type="hidden" readonly="readonly" value="${videoMeta.runTime}" />
		                          <td width="35%"  id="inputVideoFileSize">
		                	          
		                          	${inputVideoFileSize}
		                          </td>
		                      </tr>
		                      <tr>
		            	          <td width="15%" align="right">验证信息：</td>
							          <td width="35%"  id="judgevideo">
		                	          ${judge}
		                          </td>
		                          <td width="15%" align="right">选择文件：</td>
		                          <td>	            	          
							          <input id="backgroundImage2" name="" value="" type="hidden"  />
							          <input id="file_id2" name="upload" type="file" />
		                          </td>
		                      </tr>
		                      	
		                       
		                      <tr>
							      <td align="right" >素材预览效果：</td>
							      <td colspan="3">
							      <input id="videoPath" type="hidden" value="${sssspath}"/>
							      <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:426px;height:240px;
								     position: relative;">
										<img id="pImage2" src="<%=path%>/${adPositionQuery.backgroundPath}" width="426px" height="240px" />
										<div id="video">
											<object type='application/x-vlc-plugin' id='vlc'  classid='clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921' width="150" height="150">
										           <param name='mrl'  value=''/>
													<param name='volume' value='50' />
													<param name='autoplay' value='false' />
													<param name='loop' value='false' />
													<param name='fullscreen' value='false' />
										    </object>
										</div>
									</div>
							      </td>
							
					          </tr>
                       </table>
	            
					   <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px" id ="div_image" style="display: none">
		                      <tr class="title" ><td colspan="4">图片素材</td></tr>		
		                     
		                         
		                      <tr>		         
		                          <td align="right"><span class="required"></span>选择文件：</td>
		                         		<input id="imageMetaName" name="imageMetaName" type="hidden" />
		                         <td width="35%"  >
			            	        <div id="upload11"><input id="file_id" name="upload" type="file" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/></div>
			            	        <div id="upload13"><input id="file_id3" name="upload" type="file" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/></div>
							        <input id="backgroundImage" name="" value="" type="hidden" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
							        <input id="localFilePath" name="localFilePath"  type="hidden" />
		                         </td>
		                         <td align="right" >广告位素材规格：</td>
		                         <td width="35%"  id="mars">
		                         		大小：${imageFileSize}， 宽度：${imageFileWidth}px，高度：${imageFileHigh}px
		                          </td>   
		                     </tr>
		                      <tr>
		                      	  <td width="15%" align="right">验证信息：</td>
		                          <td width="35%"  id="judge">
		                	          ${judge}
		                          </td>
		            	          <td width="15%" align="right">图片文件信息：</td>
		                          <td width="35%"  id="inputImageFileSize">
		                	          ${inputImageFileSize}
		                          </td>
		                      </tr>
		                      
		                      <tr id="urlTr" style="display: none">
		                      	 <td width="15%" align="right">素材Url：</td>
			                 	 <td colspan="3">
			                 		 <input id="imageMeta.imageUrl" name="imageMeta.imageUrl" type="text" value="${imageMeta.imageUrl}"/>
			                 	 </td>	
		                      </tr>
		                      
		                     
		                      	                          
		                     <tr>
							     <td align="right" >素材预览效果：</td>
							     <td colspan="3">
								    <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:426px;height:240px;
								     position: relative;">
										<img id="pImage3" src="<%=path%>/${adPositionQuery.backgroundPath}" width="426px" height="240px" /> 
										<img id="mImage" src="${viewPath}/${imageMeta.name}" />
									</div>	
							     </td>						
					         </tr>
		               </table>
		               
		               
		                 <!----------------------------------------------->
					    <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px; display: none;" id ="div_zip" >
		                      <tr class="title" ><td colspan="4">ZIP素材</td></tr>		            
		                      <tr>		            	
		                         <td align="right"><span class="required"></span> 选择文件：</td>		                         		                         
		                         <input id="zipMetaName" name="zipMetaName" type="hidden" />
		                         <td>
			            	        <div id="zipupload11"><input id="zipfile_id" name="upload" type="file" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/></div>
			            	        <div id="zipupload13"><input id="zipfile_id3" name="upload" type="file" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/></div>
							        <input id="zipbackgroundImage" name="" value="" type="hidden"  onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/>
							        <!--  <input id="ziplocalFilePath" name="localFilePath"  type="hidden" />-->
		                         </td>
		                         <td align="right" >广告位素材规格：</td>
		                         <td width="35%"  id="zipmars">
		                         		大小：${zipFileSize}
		                          </td>   
		                     </tr>
		                      <tr>
		                      	  <td width="15%" align="right">验证信息：</td>
		                          <td width="35%"  id="zipjudge">
		                	          ${judge}
		                          </td>
		            	          <td width="15%" align="right">ZIP文件信息：</td>
		                          <td width="35%"  id="zipFileSize">
		                	          ${inputZipFileSize}
		                          </td>
		                      </tr>    
		                     <tr>
							     <td align="right" >素材预览效果：</td>
							     <td colspan="3">
								    <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:426px;height:240px;
								     position: relative;">
										<img id="pImage4" src="<%=path%>/${adPositionQuery.backgroundPath}" width="426px" height="240px" /> 
										<img id="mImage4" src="${viewPath}/${zipMeta.fileHeigth}" />
									</div>	
							     </td>						
					         </tr>  
		               </table>
					   <!----------------------------------------------->
					   
		          
		        </td>
		      </tr>
			  
			  <tr>
		            	<td colspan="4">
		            		<input type="button" value="保存" class="btn" onclick="submitForm();"/>
		            		<input type="button" value="取消" class="btn" onclick="closeSavePane();"/>
		            	</td>
		      </tr>

</table>
</div>
</div>
</div>
</form>
</body>