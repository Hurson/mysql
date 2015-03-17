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
<script type='text/javascript' src='<%=path %>/js/util/jscolor/jscolor.js'></script>
  
<title>广告系统</title>

<script type="text/javascript">
/** 宽度 */
var width = 0;
/** 高度 */
var height = 0;
	// added by liye 如果有订单关联该素材，则不能修改
	//window.onload = function() {
	$(function(){
	var stateStr = $("#material_state").val();
	if(stateStr!= 0){
		$("#saveBtn").attr('disabled',"true");
	}
	})
function init(positionJson){
	
	var materialType =document.getElementById('materialType').value;
	var imageMetaName = document.getElementById('imageMetaName').value;
	var videoMetaName = document.getElementById('videoMetaName').value;
	var zipMetaName = document.getElementById('zipMetaName').value;
	var materialId = document.getElementById('material.id').value;
	var viewPath = document.getElementById('viewPath').value;

	if(materialType!=null && materialType!=""){
		//预览素材
		preview(positionJson);
		document.getElementById('sel_material_type').disabled=true;
		
		if (materialType == 1 ){//视频
			document.getElementById("sel_material_type").options.add(new Option("视频","1"));
			setTimeout("showVideo($$('videoPath').value)", 1000);
			//showVideo($$('videoPath').value);
		}
		else if (materialType == 0 ){//图片
			document.getElementById('div_video').style.display = "none";      
			document.getElementById('div_image').style.display = "";
			document.getElementById("sel_material_type").options.add(new Option("图片","0"));
			$("#mImage").attr("src",'${viewPath}/${imageMeta.name}'); 
		}
		else if (materialType == 2 ){//文字
			document.getElementById('div_text').style.display = "";      
			document.getElementById('div_video').style.display = "none";      
			document.getElementById("sel_material_type").options.add(new Option("文字","2"));
			showText();
		}
		else if (materialType == 3 ){//调查问卷
			document.getElementById('div_video').style.display = "none";      
			document.getElementById('div_question').style.display = "";
			document.getElementById('div_question2').style.display = "";
			document.getElementById('queView').style.display = "";
			if(materialId!=""){
				   document.getElementById('sel_template_type').disabled=true;
				   document.getElementById('sel_question_type').disabled=true;
				   
				   var materialStatus = document.getElementById('material.state').value;
				   if(materialStatus!="2"){
				       document.getElementById('questionViewBtn').disabled=true;
				   }
		    }
			document.getElementById("sel_material_type").options.add(new Option("问卷","3"));
		}else if(materialType == 4 ){//ZIP
			document.getElementById('div_video').style.display = "none";      
			document.getElementById('div_image').style.display = "none";
			document.getElementById('div_zip').style.display = "";
			document.getElementById("sel_material_type").options.add(new Option("ZIP","4"));
			$("#mImage4").attr("src",'${viewPath}/${zipMeta.fileHeigth}');
		}
	}
}

/**
 * 预览
 */
function preview(positionJson){
	var selPosition = eval(positionJson);
	
	document.getElementById("coordinateStr").value = selPosition.coordinate.replace(/(^\s+)|(\s+$)/g,"");
	
	if(selPosition.isLoop == 1){
		var locationCount = parseInt(selPosition.loopCount);
		for(var i=2; i<=locationCount; i++){
			document.getElementById("picLocation").options.add(new Option(i+"",i+""));
		}
		document.getElementById("picLocation").removeAttribute("disabled");
	}

	/**为页面预览区域赋值*/
	var size = selPosition.widthHeight.split('*');
	width = size[0];
	height = size[1];
	var coordinate = selPosition.coordinate.replace(/(^\s+)|(\s+$)/g,"").split(",")[0].split('*');
	$("#pImage1").attr("width",426).attr("height",240);
	$("#pImage2").attr("width",426).attr("height",240);
	$("#pImage3").attr("width",426).attr("height",240);
	$("#pImage4").attr("width",426).attr("height",240);
	$("#pImage").attr("src",getContextPath()+"/"+selPosition.backgroundPath);
	$("#mImage").attr("width",width).attr("height",height);
	$("#mImage4").attr("width",width).attr("height",height);
	
	$("#mImage,#video").css({
		width:width+"px",
		height:height+"px",
		position:'absolute',
		left: coordinate[0]+"px", 
		top: coordinate[1]+"px" 
	});
	
	$("#mImage4").css({
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
	$("#text2").css({
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
		
		$("#mImage,#video").css({
			left: coordinate[0]+"px", 
			top: coordinate[1]+"px" 
		});
		
		$("#mImage4").css({
			left: coordinate[0]+"px", 
			top: coordinate[1]+"px" 
		});
		
		$("#text").css({
			left:coordinate[0]+"px",
			top:coordinate[1]+"px",
			
		});
		$("#text2").css({
			left:coordinate[0]+"px",
			top:coordinate[1]+"px",
			
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
//问卷预览	 
function questionView() {
         var rand = Math.random();
         var questionViewPath =document.getElementById('questionViewPath').value;
         questionViewPath =questionViewPath+"?rand="+rand;
		 var structInfo ="";
			structInfo+="			<div style='margin:0px;padding:0px;width:600px'>";
			structInfo+="						<iframe id='qeustionView' name='qeustionView'  frameBorder='0' width='1000px' height='500px'  scrolling='yes'  align='top' src='"+questionViewPath+"'> ";
			structInfo+="</iframe>";
			structInfo+="</div>";
			easyDialog.open({
			container : {
				header : "问卷预览",
				content : structInfo
			},
			overlay : true
			
		});
	 }
	 

$(function(){  
		var type = ${adPositionQuery.positionPackageType};
		var fileExt;
		var fileDesc;
		if(type == 0 || type == 1){
			fileExt = '*.gif;*.png;*.jpg;*.iframe';
			fileDesc = '*.gif;*.png;*.jpg;*.iframe';
		}else if(type == 2 || type == 3){
			fileExt = '*.png;*.jpg;*.iframe';
			fileDesc = '*.png;*.jpg;*.iframe';
		}else{
			fileExt = '*.zip';
			fileDesc = '*.zip';
		}
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
		'fileExt':fileExt,
		'fileDesc':fileDesc,
		'displayData':'speed',
		'width':'76',
    	'height':'23',
		'onComplete':function(event,queueID,fileObj,response,data){
			var json = eval('(' + response + ')');
			if(json!=null){
					if(json.position=='local'){
						if(json.result=='true'){
						   $("#mImage").attr("src","<%=path%>/images/material/"+json.viewpath);
							               $("#mImage").show();
							               $("#backgroundImage").val(json.filepath);
							               						             
							               //document.getElementById("imageSpecReal").value="大小:"+json.imageFileSize+" 宽度:"+json.imageFileWidth+" 高度:"+json.imageFileHigh;  
							               
							               document.getElementById("imageSpecReal").innerHTML="大小:"+json.imageFileSize+" 宽度:"+json.imageFileWidth+" 高度:"+json.imageFileHigh;
							               if(json.checkTag==0){
		                                          document.getElementById("imageCheckResult").value="不通过";	     
		                                      }else{
		                                          document.getElementById("imageCheckResult").value="通过";
		                                      }						   						   						
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
		'queueID':'fileQueue2',
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
						    document.getElementById("backgroundImage2").value = json.filepath;
							document.getElementById("videoMeta.runTime").value= json.duration;
							var vedio_url= '<%= basePath%>'+json.filepath;
							$("#vlc").css({
								width:width+"px",
								height:height+"px"
							});

							var vlc=document.getElementById("vlc");
							vlc.playlist.stop();
							vlc.playlist.clear();
							 // 添加播放地址
							 vlc.playlist.add(vedio_url);
							 // 播放
							 vlc.playlist.play();
							 $("#video").show();
							 
							 if(json.checkTag==0){
		                               document.getElementById("videoCheckResult").value="不通过";	     
		                     }else{
		                               document.getElementById("videoCheckResult").value="通过";
		                     }
							
						}else{
							alert('视频上传至FTP失败');
						}
					}
				}	
		}
	});
	
	$("#file_id3").uploadify({
		'uploader':'<%=path%>/js/jquery/upload/image/uploadify.swf',
		'script':'uploadMaterialZip.do?advertPositionId='+document.getElementById('material.advertPositionId').value,
		'cancelImg':'<%=path%>/js/jquery/upload/image/cancel.png',
		'folder':'/uploadFiles',
		'queueID':'fileQueue',
		'buttonImg':'<%=path%>/js/jquery/upload/image/uploadify.jpg',
		'fileDataName': 'zipbackgroundImage',
		'auto':true,
		'multi':false,
		'fileExt':fileExt,
		'fileDesc':fileDesc,
		'displayData':'speed',
		'width':'76',
    	'height':'23',
		'onComplete':function(event,queueID,fileObj,response,data){
			var json = eval('(' + response + ')');
			if(json!=null){
					if(json.position=='local'){
						if(json.result=='true'){
							var viewpath = json.viewpath;
							var path = viewpath.substring(0,viewpath.indexOf("."))
						   $("#mImage4").attr("src","<%=path%>/images/material/"+path+".jpg");
							               $("#mImage4").show();
							               $("#zipbackgroundImage").val(json.filepath);
							               						             
							               document.getElementById("zipSpecReal").innerHTML="大小:"+json.imageFileSize;
							               if(json.checkTag==0){
		                                          document.getElementById("zipCheckResult").value="不通过";	     
		                                      }else{
		                                          document.getElementById("zipCheckResult").value="通过";
		                                      }						   						   						
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
});

function selectTemplate(tId){
  if(tId==-1){
     document.getElementById('div_question2').style.display = "none"; 
  }else{
     document.getElementById('div_question2').style.display = ""; 
  }
    
}

function addQuestion(){

    i=i+1;
	var rowCount = document.getElementById("div_question2").rows.length;
	var tbObj = document.getElementById("div_question2");
	var rs = tbObj.rows;
	var count = rs.length;
	var row0 = rs[0];
    var newRow = row0.cloneNode(true);
    //alert("rowCount:"+rowCount+"/tbObj:"+tbObj+"/rs:"+rs+"/count:"+count+"/row0:"+row0.innerHTML+"/newRow:"+newRow);
	newRow.cells[0].innerHTML="问题内容：<input id='questionCount' name='questionCount' type='hidden' value='"+i +"'/>";
	newRow.cells[1].innerHTML="<textarea id='question' name='question' cols=\"30\" rows=\"3\" maxlength=\"100\"></textarea>";
    
    document.getElementById("div_question2").appendChild(newRow);
    
	newRow = row0.cloneNode(true);
	newRow.cells[0].innerHTML="问题答案：";
	newRow.cells[1].innerHTML="1:<input type='text' id='answer1' name='answer1'/></p>2:<input type='text' id='answer2' name='answer2'/></p>3:<input type='text' id='answer3' name='answer3'/></p>4:<input type='text' id='answer4' name='answer4'/></p>5:<input type='text' id='answer5' name='answer5'/></p>6:<input type='text' id='answer6' name='answer6'/>";
	document.getElementById("div_question2").appendChild(newRow);

}

function closeSavePane() {
    window.location.href = "<%=path %>/page/material/queryMeterialList.do";
}

	function submitForm(){
		//检查素材公共属性是否合法
		if(checkMaterial()){
			return ;
		}
	    var materialType = selectOptionVal("sel_material_type");
	    if (materialType == 0 ){
            var localFilePath = $$("backgroundImage").value; 
            $("#localFilePath").val(localFilePath);
            
            if ($$("imageCheckResult").value == "不通过" ){
            alert("图片规格效验不通过");
            return ;
            }
        }else if (materialType == 1 ){
            var localFilePath = $$("backgroundImage2").value; 
            $("#localFilePath").val(localFilePath);
            
            if ($$("videoCheckResult").value == "不通过" ){
            alert("视频规格效验不通过");
            return ;
            }
        }else if (materialType == 2 ){
	    	if(checkText()){
				return;
	        }
	    }else if (materialType == 4 ){
            var localFilePath = $$("zipbackgroundImage").value;
            $("#localFilePath").val(localFilePath);
            if ($$("zipCheckResult").value == "不通过" ){
            alert("ZIP规格效验不通过");
            return ;
            }
        }
        $$("material.resourceTypeTemp").value=$$('materialType').value;
               
        
        //效验素材名称是否重复   
     var resourceName = $$("material.resourceName").value;	
     var resourceId = $$("material.id").value; 
	 $.ajax({
                type:"post",
                async : false,
                url:"<%=request.getContextPath()%>/page/material/checkMaterialExist.do?",
                data:{"resourceName":resourceName,"resourceId":resourceId},//Ajax传递的参数
                success:function(mess)
                {
                	if(mess=="0")// 如果获取的数据不为空
                    {
                		
                		document.getElementById("saveForm").submit();
                		//alert("ok");
                    }
                    else
                    {
						alert("素材名称已存在，请重新输入！");
						return;
                    }
                   
                },
                error:function(mess)
                {
                	alert("未知错误");
                }
            });
	    
	    //document.getElementById("saveForm").submit();
	}
	
	/**
	* 检查素材公共属性是否合法
	*
	*/
	function checkMaterial(){
	    

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
		if($$("material.resourceName").value.length>255){
			alert("素材名称必须小于255个字节！");
			$$("material.resourceName").focus();
    		return true;
		}
		if(!isEmpty($$("material.keyWords").value)){
			if($$("material.keyWords").value.length>255){
			alert("素材关键字必须小于255个字节！");
			$$("material.keyWords").focus();
    		return true;
		}
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
		
		
	    
		return false;
	}
	
	function dateCompare(startdate,enddate){   
        var arr=startdate.split("-");    
        var starttime=new Date(arr[0],arr[1],arr[2]);    
        var starttimes=starttime.getTime();   
  
        var arrs=enddate.split("-");    
        var lktime=new Date(arrs[0],arrs[1],arrs[2]);    
        var lktimes=lktime.getTime();   
  
        if(starttimes>=lktimes){   
           return false;   
        }   
        else  
        return true;    
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
		if($$("textMeta.name").value.length>255){
			alert("文字标题必须小于255个字节！");
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
			alert("文字大小不能为空！");
			$$("textMeta.fontSize").focus();
			return true;
		}
		if(!isNumber($$("textMeta.fontSize").value)){
			alert("文字大小只能是数字！");
			$$("textMeta.fontSize").focus();
			return true;
		}
		if($$("textMeta.fontSize").value.length>10){
			alert("文字大小必须小于10个字节！");
			$$("textMeta.fontSize").focus();
    		return true;
		}
		if(isEmpty($$("textMeta.fontColor").value)){
			alert("文字颜色不能为空！");
			$$("textMeta.fontColor").focus();
			return true;
		}
		if($$("textMeta.fontColor").value.length>10){
			alert("文字颜色必须小于10个字节！");
			$$("textMeta.fontColor").focus();
    		return true;
		}

        var pat = new RegExp("^[A-Fa-f0-9]+$"); 
		var str = $$("textMeta.fontColor").value;

		if(str.length==6&& pat.test(str)){
			
		}else{
		     alert("文字颜色格式不正确！");
			$$("textMeta.fontColor").focus();
			return true;
		}
		
		if(!isEmpty($$("textMeta.bkgColor").value)){
		//长度效验
		if($$("textMeta.bkgColor").value.length>10){
			alert("文字显示背景色必须小于10个字节！");
			$$("textMeta.bkgColor").focus();
    		return true;
		}
		   //效验背景色
		   str = $$("textMeta.bkgColor").value;
		   if(str.length==6&& pat.test(str)){
			
		}else{
		     alert("文字显示背景色格式不正确！");
			$$("textMeta.bkgColor").focus();
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
		if($$("textMeta.positionVertexCoordinates").value.length>20){
			alert("文本显示坐标必须小于20个字节！");
			$$("textMeta.positionVertexCoordinates").focus();
    		return true;
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
		
		
		if(isEmpty($$("textMeta.durationTime").value)){
			alert("文本显示持续时间不能为空！");
			$$("textMeta.durationTime").focus();
			return true;
		}
		
		//if($$("sel_textMeta_action").value=='1'){
		//滚动
			if(!isEmpty($$("textMeta.rollSpeed").value) && !isNumber($$("textMeta.rollSpeed").value)){
			alert("文本显示滚动速度只能是数字！");
			$$("textMeta.rollSpeed").focus();
			return true;
		    }

		    if($$("textMeta.rollSpeed").value<0){
		    alert("文本显示滚动速度必须大于0！");
			$$("textMeta.rollSpeed").focus();
			return true;
		    }
		    
		    if($$("textMeta.rollSpeed").value.length>10){
			alert("文本显示滚动速度必须小于10个字节！");
			$$("textMeta.rollSpeed").focus();
    		return true;
		}
		//}
		
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
			
			if($$("textMeta.positionWidthHeight").value.length>20){
			alert("文本显示区域必须小于20个字节！");
			$$("textMeta.positionWidthHeight").focus();
    		return true;
		    }
		}
		
		if(!isEmpty($$("textMeta.URL").value)){
			if($$("textMeta.URL").value.length>255){
			alert("文本URL必须小于255个字节！");
			$$("textMeta.URL").focus();
    		return true;
		    }
		}
		if(!isEmpty($$("textMeta.durationTime").value)){
			if($$("textMeta.durationTime").value.length>10){
			alert("文本显示持续时间必须小于10个字节！");
			$$("textMeta.durationTime").focus();
    		return true;
		    }
		}
		
		return false;
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

	/**预览文字*/
	function showText(){
		
		if($$("sel_textMeta_action").value!='0'){
		//滚动
		   $("#textContent").css({
			   'color':$$("textMeta.fontColor").value,
			   'font-size':$$("textMeta.fontSize").value+"px",
			   'background':$$("textMeta.bkgColor").value
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
					   var left = coordinates[0]+"px";
					   var bottom = coordinates[1]+"px";					 				  
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
			    
			    	 var left = coordinates[0]+"px";
					   var bottom = coordinates[1]+"px";					 				  
					   var width = size[0]+"px";
					   var height = size[1]+"px";
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
					   var left = coordinates[0]+"px";
					   var bottom = coordinates[1]+"px";					 				  
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
			    	   var width = size[0]+"px";
					   var height = size[1]+"px";
					   $('#text2').css('width',width);
					   $('#text2').css('height',height);
					}
		 }
		  
		   $("#textContent2").html(content);
		   $("#text2").show();
		   $("#text").hide();
		}
		
		
	}

	/**预览视频*/
	function showVideo(path){

		$("#vlc").css({
			width:width+"px",
			height:height+"px"
		});
		var vlc=document.getElementById("vlc");
		vlc.playlist.stop();
		vlc.playlist.clear();
		 // 添加播放地址
		 vlc.playlist.add(path);
		 // 播放
		 vlc.playlist.play();
	}
	
	

</script>
<style>
	.easyDialog_wrapper{ width:1000px;height:400px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
</head>
<body class="mainBody" onload='init(${positionJson});'>
<form action="<%=path %>/page/meterial/saveMaterialBackup.do" method="post" id="saveForm">	
<input id="positionPackIds" name="positionPackIds" type="hidden" value="${positionPackIds}"/>
<div class="path">首页 >> 素材管理 >> 修改素材</div>
<div class="searchContent" >
<div class="listDetail">
<div style="position: relative">	
<table>
   	<tr>
   	   <td>
            <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px">
              <tr class="title"><td colspan="4">素材信息</td></tr>
                <tr>
                    <!-- 
                    <td width="15%" align="right"><span class="required">*</span>选择合同：</td>
                    <td width="35%">	
                                        		                	
				     <input id="material.contractName" name="material.contractName" value="${material.contractName}" type="text" disabled="disabled" />	
			        </td>
                     -->
                    <td align="right"><span class="required">*</span>选择广告位：</td>
                                                            
                    <td>	                
                        <input id="material.advertPositionId" name="material.advertPositionId" value="${material.advertPositionId}" type="hidden" />
	                    <input id="material.advertPositionName" name="material.advertPositionName" value="${material.advertPositionName}" type="text" disabled="disabled" />		                
                    
                        <input id="material.id" name="material.id" type="hidden" value="${material.id}"/>
                        <input id="viewPath" name="viewPath" type="hidden" value="${viewPath}"/>
                        <input id="material.state" name="material.state" type="hidden" value="${material.state}"/>
                        <input id="material.createTime" name="material.createTime" type="hidden" value="${material.createTime}"/>
                        <input id="materialType" name="materialType" type="hidden" value="${material.resourceType}"/>
                        <input id="material.resourceTypeTemp" name="material.resourceTypeTemp" type="hidden" />
                        <input id="material.examinationOpintions" name="material.examinationOpintions" type="hidden" value="${material.examinationOpintions}"/>
                        <input id="material.contractId" name="material.contractId" type="hidden" value="${material.contractId}"/>	
                        <input id="positionJson" name="positionJson" type="hidden" value="${positionJson}"/>
                        <input id="material.customerId" name="material.customerId" type="hidden" value="${material.customerId}"/>
                        <input id="material.operationId" name="material.operationId" type="hidden" value="${material.operationId}"/>
                        <input id="material_state" name="material_state" type="hidden" value="${material.stateStr}"/> 
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
                    <td align="right"><span class="required">*</span>素材名称：${material.stateStr}</td>
                    <td>
               	     <input id="material.resourceName" name="material.resourceName" value="${material.resourceName}" type="text" maxlength="20" />
                    </td>
                    <td width="15%" align="right">素材关键字：</td>
                    <td width="35%">
                        <input id="material.keyWords" name="material.keyWords" type="text" value="${material.keyWords}"/>					       
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
	              	   <select id="sel_material_type" name="material.resourceType" >
					    </select>  
                    </td>
                 </tr>
                 
                 <!-- 
                 <tr>
                    <td align="right"><span class="required">*</span>开始时间：</td>
                    <td>		               
                        <input id="material.startTime" readonly="readonly"  type="text" style="width:125px;"  name="material.startTime" value='<fmt:formatDate value="${material.startTime}" dateStyle="medium"/>'   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                    </td>
                    <td align="right"><span class="required">*</span>结束时间：</td>
                    <td>
              	     <input id="material.endTime"  readonly="readonly" class="input_style2" type="text" style="width:125px;"  name="material.endTime"  value='<fmt:formatDate value="${material.endTime}" dateStyle="medium"/>'  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                    </td>
                 </tr>
                 <tr>
                    <td align="right"><span class="required"></span>素材描述：</td>
                    <td colspan="3">
               	     <textarea id="material.resourceDesc" name="material.resourceDesc" cols="40" rows="3" maxlength="100">${material.resourceDesc}</textarea>		              	
                    </td>
                </tr>
                  -->
                <!-- 
                <tr id="b1">
           	     <td colspan="4">
           		     <input type="button" value="确定" class="btn" onclick="submitForm();"/>
           		     <input type="button" value="取消" class="btn" onclick="javascript:closeSavePane();"/>
           	     </td>
                </tr> -->
            </table>
         </td>
       </tr>
		        
		        
        <tr>
        	<td>
                <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px; display:none;" id ="div_text">
                      <tr class="title" >
                          <td colspan="4">文字素材</td>
                      </tr>
                      <tr>
                          <td  align="right"><span class="required"></span>文字标题：</td>
                          <td>
                              <input id="textMeta.id" name="textMeta.id" type="hidden" value="${textMeta.id}"/>
	            		      <input id="textMeta.name" name="textMeta.name" value="${textMeta.name}"/>
                          </td>
                          <td  align="right"><span class="required"></span>文本URL：</td>
                          <td>
	            		      <input id="textMeta.URL" name="textMeta.URL" value="${textMeta.URL}"/>
                         </td>
                      </tr>
                      <tr>
                          <td  align="right"></td>
                          <td>
                          <!--<input id="textMeta.action" name="textMeta.action" value="${textMeta.action}" />  -->
	            		      
	            		      <select style="display:none" id="sel_textMeta_action"  name="textMeta.action" onchange="changeTextAction()">
								               <!-- <option id="action_id" value="-1">请选择...</option>
										         --> 
										        <option  value="1" <c:if test="${textMeta.action== 1}">selected="selected"</c:if> >
										                        滚动
										        </option>
										        <option  value="0" <c:if test="${textMeta.action==0}">selected="selected"</c:if> >
										                        静止
										        </option>
										      
							  </select>
                          </td>
                          <td  align="right"><span class="required">*</span>显示持续时间（毫秒）：</td>
                          <td>
	            		      <input  id="textMeta.durationTime" name="textMeta.durationTime" value="${textMeta.durationTime}"/><span class="required">0表示一直显示</span>
                         </td>
                      </tr>
                      <tr>
                          <td  align="right"><span class="required"></span>文字大小：</td>
                          <td>
	            		      <input id="textMeta.fontSize" name="textMeta.fontSize" onchange="showText();" value="${textMeta.fontSize}"/><span class="required">px</span>
                          </td>
                          <td  align="right"><span class="required"></span>文字颜色：</td>
                          <td>
	            		      <input id="textMeta.fontColor" class="color" onchange="showText();" name="textMeta.fontColor" value="${textMeta.fontColor}"/><span class="required">格式：235612</span>
                         </td>
                      </tr>
                      <tr>
                          <td  align="right"><span class="required"></span>文本显示背景色：</td>
                          <td>
	            		      <input id="textMeta.bkgColor" name="textMeta.bkgColor" onchange="showText();" value="${textMeta.bkgColor}"/><span class="required">格式：235612</span>
                          </td>
                          <td  align="right"><span class="required"></span>文本显示滚动速度：</td>
                          <td>
	            <!-- 	      <input id="textMeta.rollSpeed" name="textMeta.rollSpeed" value="${textMeta.rollSpeed}"/>
	            		  -->	     
	            		      
	            		        <select  id="textMeta.rollSpeed"  name="textMeta.rollSpeed" onchange="showText();">
	            		        				 <option  value="0" <c:if test="${textMeta.rollSpeed==0}">selected="selected"</c:if> >
										                         静止
										         </option>
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
                          <td  align="right"><span class="required"></span>文本显示坐标：</td>
                          <td>
	            		      <input id="textMeta.positionVertexCoordinates" name="textMeta.positionVertexCoordinates" onchange="showText();" value="${textMeta.positionVertexCoordinates}"/><span class="required">格式：80*80(坐标x*y)</span>
                          </td>
                          <td  align="right"><span class="required"></span>文本显示区域：</td>
                          <td>
	            		      <input id="textMeta.positionWidthHeight" name="textMeta.positionWidthHeight" onchange="showText();" value="${textMeta.positionWidthHeight}"/><span class="required">格式：80*80(宽高w*h)</span>
                         </td>
                      </tr>
                      <tr>
            	          <td width="15%" align="right"><span class="required"></span>内容：</td>
                          <td colspan="3">
                	      <textarea id="textMeta.contentMsg" name="textMeta.contentMsg" onchange="showText();" maxlength="4000" cols="80" rows="5">${textMeta.contentMsg}</textarea>
                          </td>
                      </tr>  
                      <tr>
					      <td align="right" >素材预览效果：</td>
					      <td colspan="3">
							  <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:426px;height:240px;
							     position: relative;">
									<img id="pImage1" src="<%=path%>/${adPositionQuery.backgroundPath}" width="426px" height="240px" /> 
									<div id="text"><marquee scrollamount="10" id="textContent"></marquee></div>
									<div id="text2"><span id="textContent2"></span></div>
								</div>
					      </td>
			          </tr>   
                </table>
                
	    <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px;" id ="div_video" >
             <tr class="title" >
                     <td colspan="4">视频素材</td>
                     <input id="videoMeta.id" name="videoMeta.id" type="hidden" value="${videoMeta.id}"/>
                     <input id="videoMetaName" name="videoMetaName" type="hidden" value="${videoMeta.name}"/>
             </tr>
             <tr>     	             
                     <td width="15%" align="right"><span class="required"></span>时长规格：</td>
		             <td width="35%" >		                	          
		                	  <input maxlength="10" id="videoFileDuration" disabled="disabled" type="text" value="${videoSpecification.duration}秒" />
		             </td>
                     <td width="15%" align="right"><span class="required"></span>选择文件：                   
                     </td>
                     <td>	         
                     <!-- <div id="fileQueue2"></div> -->
                        	          
			          <input id="backgroundImage2" name="" value="" type="hidden"  />
			          <input id="file_id2" name="upload" type="file" />
                     </td>
              </tr>
              <tr>
                     <td width="15%" align="right"><span class="required"></span>实际时长：</td>
                     <td width="35%" >
           	          <input maxlength="10" id="videoMeta.runTime" name="videoMeta.runTime" type="text" value="${videoMeta.runTime}" />
                     </td>
		             <td width="15%" align="right"><span class="required"></span>效验结果：</td>
		             <td width="35%" >
		                	<input maxlength="10" id="videoCheckResult" type="text" disabled="disabled" value="通过" />
		             </td>
              </tr>
                     
	          <tr>
			      <td align="right" >素材预览效果：</td>
			      <td colspan="3">
			      <input id="videoPath" type="hidden" value="${sssspath}"/>
				  <!-- <embed id="materialViewDivImg2" src="" height="250px" width="345px" autostart="false"  loop="true" type="application/x-vlc-plugin" version="VideoLAN.VLCPlugin.2" pluginspage="http://www.videolan.org" />	 -->
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
           
			   <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px; display:none;" id ="div_image">
                      <tr class="title" ><td colspan="4">图片素材</td></tr>		            
                      <tr>		            	
                         <td align="right"><span class="required"></span>选择文件：                        
                         </td>
                         <!-- <div id="fileQueue"></div> -->
                         
                         <input id="imageMetaName" name="imageMetaName" type="hidden" value="${imageMeta.name}"/>
                         <input id="imageMeta.id" name="imageMeta.id" type="hidden" value="${imageMeta.id}"/>
                         <td>
	            	        <input id="file_id" name="upload" type="file" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/>
					        <input id="backgroundImage" name="" value="" type="hidden" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
					        <input id="localFilePath" name="localFilePath"  type="hidden" />
                         </td>
                         
                         <td width="15%" align="right"><span class="required"></span>文件规格：</td>
		                 <td width="35%" >
		                 <!-- 
		                 <input maxlength="10" id="imageSpec" type="text" disabled="disabled" value="大小:${imageSpecification.fileSize} 宽度:${imageSpecification.imageWidth} 高度:${imageSpecification.imageHeight}"/>
		                  -->
		                	          
		                              <span id="imageSpec" >大小:${imageSpecification.fileSize} 宽度:${imageSpecification.imageWidth} 高度:${imageSpecification.imageHeight}</span>
		                 </td>
                     </tr>
                     <tr>		            	
		                         <td width="15%" align="right"><span class="required"></span>效验结果：</td>
		                          <td width="35%" >
		                	          <input maxlength="10" id="imageCheckResult" type="text" disabled="disabled" value="通过" />
		                          </td>
		                         <td width="15%" align="right"><span class="required"></span>实际规格：</td>
		                          <td width="35%" >
		                              <span id="imageSpecReal">
		                              大小:${imageMeta.fileSize} 宽度:${imageMeta.fileWidth} 高度:${imageMeta.fileHeigth}
		                              </span>
		                	          <!-- 
		                	          <input maxlength="20" id="imageSpecReal" type="text" disabled="disabled" value="大小:${imageMeta.fileSize} 宽度:${imageMeta.fileWidth} 高度:${imageMeta.fileHeigth}" />
		                	           -->
		                	          
		                          </td>
		              </tr>
		              <c:if test="${material.advertPositionId == 23 || material.advertPositionId == 24}">
		              		<tr>
			                 	 <td width="15%" align="right">素材Url：</td>
			                 	 <td colspan="3">
			                 		 <input id="imageMeta.imageUrl" name="imageMeta.imageUrl" type="text" value="${imageMeta.imageUrl}"/>
			                 	 </td>	
			               </tr>
		              </c:if>
		              
                     
                     <tr>
					     <td align="right" >素材预览效果：</td>
					     <td colspan="3">
						   <!-- <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:345px;height:250px;">
							<img id="materialViewDivImg" src="" style="background-repeat:no-repeat; width:345px;height:250px;"/>											
						    </div>	 --> 
						    <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:426px;height:240px;
						     position: relative;">
								<img id="pImage3" src="<%=path%>/${adPositionQuery.backgroundPath}" width="426px" height="240px" /> 
								<img id="mImage" src="${viewPath}/${imageMeta.name}" />
							</div>
					     </td>						
			         </tr>
               </table>
          
          
          <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px; display: none;" id ="div_zip">
                      <tr class="title" ><td colspan="4">ZIP素材</td></tr>		            
                      <tr>		            	
                         <td align="right"><span class="required"></span>选择文件：                        
                         </td>
								<input id="zipMetaName" name="zipMetaName" type="hidden" value="${zipMeta.name}"/>
                         		<input id="zipMeta.id" name="zipMeta.id" type="hidden" value="${zipMeta.id}"/>
                         <td>
	            	        <input id="file_id3" name="upload" type="file" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/>
					        <input id="zipbackgroundImage" name="" value="" type="hidden" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
					       <!--   <input id="ziplocalFilePath" name="localFilePath"  type="hidden" />-->
                         </td>
                         
                         <td width="15%" align="right"><span class="required"></span>文件规格：</td>
		                 <td width="35%" >
		                 <!-- 
		                 <input maxlength="10" id="imageSpec" type="text" disabled="disabled" value="大小:${imageSpecification.fileSize} 宽度:${imageSpecification.imageWidth} 高度:${imageSpecification.imageHeight}"/>
		                  -->
		                	          
		                              <span id="imageSpec" >大小:${zipSpecification.fileSize} </span>
		                 </td>
                     </tr>
                     <tr>		            	
		                         <td width="15%" align="right"><span class="required"></span>效验结果：</td>
		                          <td width="35%" >
		                	          <input maxlength="10" id="zipCheckResult" type="text" disabled="disabled" value="通过" />
		                          </td>
		                         <td width="15%" align="right"><span class="required"></span>实际规格：</td>
		                          <td width="35%" >
		                              <span id="zipSpecReal">
		                              	大小:${zipMeta.fileSize}
		                              </span>
		                	          <!-- 
		                	          <input maxlength="20" id="imageSpecReal" type="text" disabled="disabled" value="大小:${imageMeta.fileSize} 宽度:${imageMeta.fileWidth} 高度:${imageMeta.fileHeigth}" />
		                	          --> 
		                	          
		                          </td>
		              </tr>
                     
                     <tr>
					     <td align="right" >素材预览效果：</td>
					     <td colspan="3">
						   <!-- <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:345px;height:250px;">
							<img id="materialViewDivImg" src="" style="background-repeat:no-repeat; width:345px;height:250px;"/>											
						    </div>	 --> 
						    <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:426px;height:240px;
						     position: relative;">
								<img id="pImage4" src="<%=path%>/${adPositionQuery.backgroundPath}" width="426px" height="240px" /> 
								<img id="mImage4" src="${viewPath}/${zipMeta.fileHeigth}" />
							</div>
					     </td>						
			         </tr>
               </table>
          <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px; display: none;" id ="div_question" >
            <tr class="title" >
                <td colspan="4">调查问卷</td>
            </tr>		            
            <tr>		            	
                <td align="right"><span class="required"></span>问卷类型：</td>
                <td>
                    <input id="questionSubject.questionnaireType" name="questionSubject.questionnaireType" type="hidden" value="${questionSubject.questionnaireType}"/>
              	    <select id="sel_question_type"  name="questionSubject.questionnaireType">
					     <option id="ad_id" value="-1">请选择...</option>
			            <c:forEach items="${questionTypeList}" var="queBean">
				           <option  value="${queBean.id }" <c:if test="${questionSubject.questionnaireType== queBean.id}">selected="selected"</c:if> >${queBean.typeName }</option>
			            </c:forEach>
				    </select>		  
                </td>		            	
                <td align="right"><span class="required"></span>模板选择：</td>
                <td>
                <!-- <input id="questionSubject.templateId" type="hidden" value="${questionSubject.templateId}"/> -->
                    
              	    <select id="sel_template_type" name="questionSubject.templateId" onclick="selectTemplate(this.value)" ">
					     <option id="ad_id" value="-1">请选择...</option>
					            <c:forEach items="${templateList}" var="templateBean">
						           <option  value="${templateBean.id }" <c:if test="${questionSubject.templateId== templateBean.id}">selected="selected"</c:if> >${templateBean.templateName }</option>
					            </c:forEach>
				    </select>		  
                </td>
            </tr>
            <tr>		            	
		                <td align="right"><span class="required"></span>问卷积分：</td>
		                <td colspan="3">
			              	 <input id="questionSubject.integral" name="questionSubject.integral" type="text" maxlength="20" value="${questionSubject.integral}" disabled="disabled" />		  
		                </td>		            	
		    </tr>
            
            <tr id="queView" style="display: none">
			     <td align="right" >问卷预览：</td>
			     <td colspan="3">						
					<input id="questionViewBtn" type="button" value="预览" class="btn" onclick="javascript:questionView();"/>	
					<input id="questionViewPath" name="questionViewPath" type="hidden" value="${questionSubject.filePath}"/>									
			     </td>						
			</tr>
            
            <tr>
            	<td colspan="4">
			        <table  cellspacing="1" class="content" align="left" style="margin-bottom: 0px" id ="div_question2" style="display: none">
                          <tr>
            	              <td width="15%" align="right"><span class="required"></span>问卷主题：</td>
                              <td width="35%">
                                  <input id="questionSubject.id" name="questionSubject.id" type="hidden" value="${questionSubject.id}"/>
                	              <textarea id="questionSubject.summary" name="questionSubject.summary" cols="30" rows="3" maxlength="100" disabled="disabled">${questionSubject.summary}</textarea>
                              </td>
                          </tr>
                          <tr>
            	              <td width="15%" align="right"><span class="required"></span>新增问题：</td>
                              <td width="35%">
                	              <input type="button"  value="新增问题" onclick="addQuestion()" disabled="disabled"/>
                              </td>
                          </tr>
                          <c:if test="${questionInfoList != null && fn:length(questionInfoList) > 0}">
                          <c:forEach items="${questionInfoList}" var="questionInfo" varStatus="pl">
                          <tr>
		                      <td width="15%" align="right"><span class="required"></span>问题内容：</td>
		                      <td width="35%">
		                	      <textarea  cols="30" rows="3" maxlength="100" disabled="disabled">${questionInfo.question}</textarea>
		                      </td>
		                  </tr>		                   
		                  <tr>
		                      <td width="15%" align="right"><span class="required"></span>问题答案：</td>
		                      <td width="35%">
		                      <c:if test="${questionInfo.answerList != null && fn:length(questionInfo.answerList) > 0}">
                              <c:forEach items="${questionInfo.answerList}" var="answerInfo" varStatus="al">
		                	              ${al.index+1} :<input disabled="disabled" type='text' id='answer1' name='answer1' value="${answerInfo.options}"/></p>
		                	              
		                	  </c:forEach>
                             </c:if>            
		                      </td>
		                  </tr>
		                     
		                  </c:forEach>
                          </c:if>
			        </table>
			     </td>
			</tr>      
          </table>
          
        </td>
      </tr>
	  
	  <tr>
       	<td colspan="4">
       		<input type="button" id="saveBtn" value="保存" class="btn" onclick="submitForm();"/>
       		<input type="button" value="取消" class="btn" onclick="javascript:closeSavePane();"/>
       	</td>
      </tr>

</table>
</div>
</div>
</div>
</form>
</body>
</html>