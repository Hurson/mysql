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


<title>新增广告素材</title>

<script type="text/javascript">
var uploadImage="";
var uploadVideo="";
var uploadZip="";
var i=0;
var  maTerialType=-1;
//选择子广告位
function selectAdPosition() {
	     //var contractId= document.getElementById("material.contractId").value;
	     //if (contractId==null || contractId==""){
			// alert("请选择合同");
			 //return ;
		 //} 
		 var positionPackIds = document.getElementById("positionPackIds").value;
		 var structInfo ="<div style='margin:0px;padding:0px;width:600px'>";
			//structInfo+="<iframe id='selectAdPositionFrame' name='selectAdPositionFrame'  frameBorder='0' width='1000px' height='500px'  scrolling='yes'  align='top' src='<%=request.getContextPath()%>/page/meterial/selectAdPosition.do?adPositionQuery.contractId="+contractId+"'> ";
			structInfo+="<iframe id='selectAdPositionFrame' name='selectAdPositionFrame'  frameBorder='0' width='1000px' height='500px'  scrolling='yes'  align='top' src='<%=request.getContextPath()%>/page/meterial/selectAdPosition.do?positionPackIds="+positionPackIds+"'> ";
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
	 
//选择合同
function selectContract() {
		 var structInfo ="<div style='margin:0px;padding:0px;width:600px'>";
			structInfo+="<iframe id='selectContractFrame' name='selectContractFrame'  frameBorder='0' width='1000px' height='500px'  scrolling='yes'  align='top' src='<%=request.getContextPath()%>/page/meterial/selectContract.do'></iframe>";
			structInfo+="</div>";
			easyDialog.open({
			container : {
				header : "选择合同",
				content : structInfo
			},
			overlay : true
		});
	 }
	 
	 
//问卷预览	 
function questionView() {
         var questionViewPath =document.getElementById('questionViewPath').value;
		 var structInfo ="";
			structInfo+="			<div style='margin:0px;padding:0px;width:600px'>";
			structInfo+="						<iframe id='qeustionView' name='qeustionView'  frameBorder='0' width='1000px' height='500px'  scrolling='yes'  align='top' src='"+questionViewPath+"' ";
			structInfo+="'></iframe>";
			structInfo+="</div>";
			easyDialog.open({
			container : {
				header : "问卷预览",
				content : structInfo
			},
			overlay : true
			
		});
	 }
	 
//图片批量预览	 
function imagePreview2(imagePreviewName) {
         var advertPositionId =document.getElementById('material.advertPositionId').value;
         //alert(advertPositionId);
         //alert(imagePreviewName);
		 var structInfo ="";
			structInfo+="			<div style='margin:0px;padding:0px;width:426px;height:240px' >";
			structInfo+="						<iframe id='PreviewImage' name='PreviewImage'  frameBorder='0' width='426px' height='240px'  scrolling='yes'  align='top' src='<%=request.getContextPath()%>/page/meterial/getAdvertPosition.do?advertPositionId="+advertPositionId+"&imagePreviewName="+imagePreviewName+"'> ";
			structInfo+="</iframe>";
			structInfo+="</div>";
			easyDialog.open({
			container : {
				header : "预览",
				content : structInfo
			},
			overlay : true
			
		});
	 }
	 
	 //图片批量预览另一种方式
	function imagePreview(imagePreviewName){
	    var advertPositionId =document.getElementById('material.advertPositionId').value;
		var url = "getAdvertPosition.do?advertPositionId="+advertPositionId+"&imagePreviewName="+imagePreviewName;
		window.showModalDialog(url, window, "dialogHeight=240px;dialogWidth=426px;center=1;resizable=0;status=0;");

	}
	 
	 	 //zip文件图片预览
	function zipImagePreview(zipImagePreviewName){
	    var advertPositionId =document.getElementById('material.advertPositionId').value;
		var url = "getAdvertPosition.do?advertPositionId="+advertPositionId+"&zipImagePreviewName="+zipImagePreviewName;
		window.showModalDialog(url, window, "dialogHeight=240px;dialogWidth=426px;center=1;resizable=0;status=0;");

	}
	 
//视频批量预览	 
function videoPreview2(videoPreviewName) {
         var advertPositionId =document.getElementById('material.advertPositionId').value;
         //alert(advertPositionId);
         //alert(videoPreviewName);
		 var structInfo ="";
			structInfo+="			<div style='margin:0px;padding:0px;width:426px;height:240px' >";
			structInfo+="						<iframe id='PreviewVideo' name='PreviewVideo'  frameBorder='0' width='426px' height='240px'  scrolling='yes'  align='top' src='<%=request.getContextPath()%>/page/meterial/getAdvertPosition.do?advertPositionId="+advertPositionId+"&videoPreviewName="+videoPreviewName+"'> ";
			structInfo+="</iframe>";
			structInfo+="</div>";
			easyDialog.open({
			container : {
				header : "预览",
				content : structInfo
			},
			overlay : true
			
		});
	 }	
	 
//视频批量预览另一种方式
function videoPreview(videoPreviewName){

	    var advertPositionId =document.getElementById('material.advertPositionId').value;
		var url = "getAdvertPosition.do?advertPositionId="+advertPositionId+"&videoPreviewName="+videoPreviewName;
		window.showModalDialog(url, window, "dialogHeight=240px;dialogWidth=426px;center=1;resizable=0;status=0;");

	} 	 
	 
	 
function deleteImageFile(delUploadImage)
	{
	    var kk= new Array();
        kk = uploadImage.split(",");
        var newdate= "";

        for(var j=0;j<kk.length;j++)
	    {	
	        var ww= new Array();
	        ww = kk[j].split("/");
		    if (delUploadImage==ww[0])
		    {
		    }else{
		        if(newdate==""){
                      newdate=kk[j];
                   }else{
                      newdate=newdate+","+kk[j];
                   }
		    }
		    
	     }
	     uploadImage=newdate;
	     
	     var adsPositionCode = document.getElementById('material.advertPositionId').value;
		 var showUrl = false;
		 if(adsPositionCode == 24 || adsPositionCode == 23){
		   	  showUrl = true;
		 }
		
		var imageList= "<table cellspacing='1' class='searchList'>";
                                         imageList=imageList+  "<tr class=\"title\" ><td colspan='9'>已上传图片</td></tr>";
                                         imageList=imageList+"<tr >";
                                         imageList=imageList+"    <td >序号</td>";
                                         imageList=imageList+"    <td >素材名</td>";
                                         imageList=imageList+"    <td >素材关键字</td>";
                                         if(showUrl){
			                                  imageList=imageList+"    <td >素材Url</td>";
			                             }
                                         imageList=imageList+"    <td>文件名</td>";
                                         imageList=imageList+"    <td>文件大小</td>";
                                         imageList=imageList+"    <td>文件宽度</td>";
                                         imageList=imageList+"    <td>文件高度</td>";
                                         imageList=imageList+"    <td>规格效验</td>";
                                      	 imageList=imageList+"	<td>操作</td>";
                                         imageList=imageList+"  </tr>";
	                                      if(uploadImage!=""){
	                                      
	                                      var ss= new Array();
                                          ss = uploadImage.split(",");
                                          for(var i=0;i<ss.length;i++){
                                              var ww= new Array();
                                              ww = ss[i].split("/");
                                               
                                              imageList=imageList+"  <tr>";
		                                      imageList=imageList+"    <td>"+(i+1)+"</td>";
		                                      imageList=imageList+"    <td><input  type=\"text\" id='imageFileName-"+ww[0]+"' name='imageFileName-"+ww[0]+"' value='"+ww[4]+"' onchange='imageNameChange(\""+ww[0]+"\");' /></td>";
		                                      imageList=imageList+"    <td><input  type=\"text\" id='imageKeyword-"+ww[0]+"' name='imageKeyword-"+ww[0]+"' value='"+ww[5]+"' onchange='imageKeywordChange(\""+ww[0]+"\");' /></td>";
		                                      if(showUrl){
		                                          imageList=imageList+"    <td><input  type=\"text\" id='imageUrl-"+ww[0]+"' name='imageUrl-"+ww[0]+"' value='"+ww[7]+"' onchange='imageUrlwordChange(\""+ww[0]+"\");' /></td>";
		                                      }   
		                                      imageList=imageList+"    <td>"+ww[0]+"</td>";
		                                      imageList=imageList+" <input id='imageFileNames' name='imageFileNames' type='hidden' value='"+ww[0] +"'/>";
		                                      imageList=imageList+"    <td>"+ww[1]+"</td>";
		                                      imageList=imageList+"    <td>"+ww[2]+"</td>";
		                                      imageList=imageList+"    <td>"+ww[3]+"</td>";
		                                      if(ww[6]==0){
		                                          imageList=imageList+" <td>不通过</td>";	     
		                                      }else{
		                                          imageList=imageList+" <td>通过</td>";
		                                      }
		                                      
		                                      imageList=imageList+"    <td><a href='#' onclick='imagePreview(\""+ww[0]+"\");'>预览</a>&nbsp;&nbsp;<a href='#' onclick='deleteImageFile(\""+ww[0]+"\");'>删除</a></td>";                      
		                                      imageList=imageList+"  </tr>";
                                          }
	                                      
		                                    }     
	                                      
                                          imageList=imageList+"  </table>";
                                          document.getElementById("image_file").innerHTML=imageList;
	}	 
	 



function deleteZipFile(delUploadZip)
	{
	    var kk= new Array();
        kk = uploadZip.split(",");
        var newdate= "";
        for(var j=0;j<kk.length;j++)
	    {	
	        var ww= new Array();
	        ww = kk[j].split("/");
		    if (delUploadZip==ww[0])
		    {
		    }else{
		        if(newdate==""){
                      newdate=kk[j];
                   }else{
                      newdate=newdate+","+kk[j];
                   }
		    }
		    
	     }

	     uploadZip=newdate;
		
		var zipList= "<table cellspacing='1' class='searchList'>";
	                                           zipList=zipList+  "<tr class=\"title\" ><td colspan='9'>已上传图片</td></tr>";
	                                           zipList=zipList+"<tr >";
	                                           zipList=zipList+"    <td >序号</td>";
	                                           zipList=zipList+"    <td >素材名</td>";
	                                           zipList=zipList+"    <td >素材关键字</td>";
	                                           zipList=zipList+"    <td>文件名</td>";
	                                           zipList=zipList+"    <td>文件大小</td>";
	                                           zipList=zipList+"    <td>规格效验</td>";
		                                       zipList=zipList+"	<td>操作</td>";
	                                           zipList=zipList+"  </tr>";
	                                      if(uploadZip!=""){
	                                      
	                                      var ss= new Array();
                                          ss = uploadZip.split(",");
                                          for(var i=0;i<ss.length;i++){
                                              var ww= new Array();
                                              ww = ss[i].split("/");
                                               
                                              zipList=zipList+"  <tr>";
		                                      zipList=zipList+"    <td>"+(i+1)+"</td>";
		                                      zipList=zipList+"    <td><input  type=\"text\" id='zipFileName-"+ww[0]+"' name='zipFileName-"+ww[0]+"' value='"+ww[4]+"' onchange='zipNameChange(\""+ww[0]+"\");' /></td>";
		                                      zipList=zipList+"    <td><input  type=\"text\" id='zipKeyword-"+ww[0]+"' name='zipKeyword-"+ww[0]+"' value='"+ww[5]+"' onchange='zipKeywordChange(\""+ww[0]+"\");' /></td>";
		                                      zipList=zipList+"    <td>"+ww[0]+"</td>";
		                                      zipList=zipList+" <input id='zipFileNames' name='zipFileNames' type='hidden' value='"+ww[0] +"'/>";
		                                      zipList=zipList+"    <td>"+ww[1]+"</td>";

		                                      if(ww[6]==0){
		                                          zipList=zipList+" <td>不通过</td>";	     
		                                      }else{
		                                          zipList=zipList+" <td>通过</td>";
		                                      }
		                                      var previewZipImage = ww[0].substring(0,ww[0].lastIndexOf("."))+".jpg";
		                                      zipList=zipList+"    <td><a href='#' onclick='zipImagePreview(\""+previewZipImage+"\");'>预览</a>&nbsp;&nbsp;<a href='#' onclick='deleteZipFile(\""+ww[0]+"\");'>删除</a></td>";                      
		                                      zipList=zipList+"  </tr>";
                                          }
	                                      
		                                    }     
	                                      
                                          zipList=zipList+"  </table>";
                                          document.getElementById("zip_file").innerHTML=zipList;
	}	 
	 



function deleteVideoFile(delUploadVideo)
	{
	
	    var kk= new Array();
        kk = uploadVideo.split(",");
        var newdate= "";
        for(var j=0;j<kk.length;j++)
	    {	
	        var ww= new Array();
	        ww = kk[j].split("/");
	        
		    if (delUploadVideo==ww[0])
		    {
		    }else{
		        if(newdate==""){
                      newdate=kk[j];
                   }else{
                      newdate=newdate+","+kk[j];
                   }
		    }
		    
	     }
	     uploadVideo=newdate;
		
		var videoList= "<table cellspacing='1' class='searchList'>";
	                                           videoList=videoList+  "<tr class=\"title\" ><td colspan='9'>已上传视频</td></tr>";
	                                           videoList=videoList+"<tr >";
	                                           videoList=videoList+"    <td >序号</td>";
	                                           videoList=videoList+"    <td >素材名</td>";
	                                           videoList=videoList+"    <td >素材关键字</td>";
	                                           videoList=videoList+"    <td>文件名</td>";
	                                           videoList=videoList+"    <td>时长</td>";
	                                           videoList=videoList+"    <td>规格效验</td>";
		                                       videoList=videoList+"	<td>操作</td>";
	                                           videoList=videoList+"  </tr>";
	                                           
	                                      if(uploadVideo!=""){
	                                      
	                                      var ss= new Array();
                                          ss = uploadVideo.split(",");
                                          for(var i=0;i<ss.length;i++){
                                              var ww= new Array();
                                              ww = ss[i].split("/");
                                               
                                              videoList=videoList+"  <tr>";
		                                      videoList=videoList+"    <td>"+(i+1)+"</td>";
		                                      videoList=videoList+"    <td><input  type=\"text\" id='videoFileName-"+ww[0]+"' name='videoFileName-"+ww[0]+"' value='"+ww[2]+"' onchange='videoNameChange(\""+ww[0]+"\");' /></td>";
		                                      videoList=videoList+"    <td><input  type=\"text\" id='videoKeyword-"+ww[0]+"' name='videoKeyword-"+ww[0]+"' value='"+ww[3]+"' onchange='videoKeywordChange(\""+ww[0]+"\");' /></td>";
		                                      videoList=videoList+"    <td>"+ww[0]+"</td>";
		                                      videoList=videoList+" <input id='videoFileNames' name='videoFileNames' type='hidden' value='"+ww[0] +"&"+ww[1]+"'/>";
		                                      videoList=videoList+"    <td>"+ww[1]+"</td>";
		                                      if(ww[4]==0){
		                                          videoList=videoList+" <td>不通过</td>";	     
		                                      }else{
		                                          videoList=videoList+" <td>通过</td>";
		                                      }
		                                      
		                                      videoList=videoList+"    <td><a href='#' onclick='videoPreview(\""+ww[0]+"\");'>预览</a>&nbsp;&nbsp;<a href='#' onclick='deleteVideoFile(\""+ww[0]+"\");'>删除</a></td>";                      
		                                      videoList=videoList+"  </tr>";
                                          }
	                                      
	                                      }     
	                                           
	                                      
                                          videoList=videoList+"  </table>";
                                          document.getElementById("video_file").innerHTML=videoList;
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
		'multi':true,
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

						   //if(json.image_width_fail!=null){
						     // alert(json.image_width_fail);
						      //}else{
						        // if(json.image_high_fail!=null){
						          //  alert(json.image_high_fail);
						           //}else{
						             // if(json.image_fileSize_fail!=null){
						               //  alert(json.image_fileSize_fail);
						                 //}else{
						                   //效验通过
						                 //}
						           //}
						      //}

						                   //$("#mImage").attr("src","<%=path%>/images/material/"+json.viewpath);
							               //$("#mImage").show();
							               $("#backgroundImage").val(json.filepath);
							               
							               if(uploadImage==""){
							                  uploadImage=json.viewpath+"/"+json.imageFileSize+"/"+json.imageFileWidth+"/"+json.imageFileHigh+"/"+json.oldFileName+"/ /"+json.checkTag;
							               }else{
							                  uploadImage = uploadImage+","+json.viewpath+"/"+json.imageFileSize+"/"+json.imageFileWidth+"/"+json.imageFileHigh+"/"+json.oldFileName+"/ /"+json.checkTag;
							               }
							               
							               var imageList= "<table cellspacing='1' class='searchList'>";
	                                           imageList=imageList+  "<tr class=\"title\" ><td colspan='10'>已上传图片</td></tr>";
	                                           imageList=imageList+"<tr >";
	                                           imageList=imageList+"    <td >序号</td>";
	                                           imageList=imageList+"    <td >素材名</td>";
	                                           imageList=imageList+"    <td >素材关键字</td>";
	                                           // imageList=imageList+"    <td >素材Url</td>";
	                                           imageList=imageList+"    <td>文件名</td>";
	                                           imageList=imageList+"    <td>文件大小</td>";
	                                           imageList=imageList+"    <td>文件宽度</td>";
	                                           imageList=imageList+"    <td>文件高度</td>";
	                                           imageList=imageList+"    <td>规格效验</td>";
		                                       imageList=imageList+"	<td>操作</td>";
	                                           imageList=imageList+"  </tr>";
	                                           
	                                      var ss= new Array();
                                          ss = uploadImage.split(",");
                                          for(var i=0;i<ss.length;i++){
                                              var ww= new Array();
                                              ww = ss[i].split("/");
                                               
                                              imageList=imageList+"  <tr>";
		                                      imageList=imageList+"    <td>"+(i+1)+"</td>";
		                                      imageList=imageList+"    <td><input  type=\"text\" id='imageFileName-"+ww[0]+"' name='imageFileName-"+ww[0]+"' value='"+ww[4]+"' onchange='imageNameChange(\""+ww[0]+"\");' /></td>";
		                                      imageList=imageList+"    <td><input  type=\"text\" id='imageKeyword-"+ww[0]+"' name='imageKeyword-"+ww[0]+"' value='"+ww[5]+"' onchange='imageKeywordChange(\""+ww[0]+"\");' /></td>";
		                                      //imageList=imageList+"    <td><input  type=\"text\" id='imageUrl-"+ww[0]+"' name='imageUrl-"+ww[0]+"' value='"+ww[5]+"' onchange='imageUrlwordChange(\""+ww[0]+"\");' /></td>";
		                                      imageList=imageList+"    <td>"+ww[0]+"</td>";
		                                      imageList=imageList+" <input id='imageFileNames' name='imageFileNames' type='hidden' value='"+ww[0] +"'/>";
		                                      imageList=imageList+"    <td>"+ww[1]+"</td>";
		                                      imageList=imageList+"    <td>"+ww[2]+"</td>";
		                                      imageList=imageList+"    <td>"+ww[3]+"</td>";
		                                      if(ww[6]==0){
		                                          imageList=imageList+" <td>不通过</td>";	     
		                                      }else{
		                                          imageList=imageList+" <td>通过</td>";
		                                      }
		                                      
		                                      imageList=imageList+"    <td><a href='#' onclick='imagePreview(\""+ww[0]+"\");'>预览</a>&nbsp;&nbsp;<a href='#' onclick='deleteImageFile(\""+ww[0]+"\");'>删除</a></td>";                      
		                                      imageList=imageList+"  </tr>";
                                          }
                                          imageList=imageList+"  </table>";
                                          document.getElementById("image_file").innerHTML=imageList;
						      					   						   						
						}else{
							alert('图片上传失败');
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
		'multi':true,
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

						   //if(json.image_width_fail!=null){
						     // alert(json.image_width_fail);
						      //}else{
						        // if(json.image_high_fail!=null){
						          //  alert(json.image_high_fail);
						           //}else{
						             // if(json.image_fileSize_fail!=null){
						               //  alert(json.image_fileSize_fail);
						                 //}else{
						                   //效验通过
						                 //}
						           //}
						      //}

						                   //$("#mImage").attr("src","<%=path%>/images/material/"+json.viewpath);
							               //$("#mImage").show();
							               $("#backgroundImage").val(json.filepath);
				
										   var adsPositionCode = document.getElementById('material.advertPositionId').value;
										   var showUrl = false;
										   if(adsPositionCode == 24 || adsPositionCode == 23){
										   	    showUrl = true;
										   }
				
							               if(uploadImage==""){
							                  uploadImage=json.viewpath+"/"+json.imageFileSize+"/"+json.imageFileWidth+"/"+json.imageFileHigh+"/"+json.oldFileName+"/ /"+json.checkTag+"/ ";
							               }else{
							                  uploadImage = uploadImage+","+json.viewpath+"/"+json.imageFileSize+"/"+json.imageFileWidth+"/"+json.imageFileHigh+"/"+json.oldFileName+"/ /"+json.checkTag+"/ ";
							               }
							               
							               var imageList= "<table cellspacing='1' class='searchList'>";
	                                           imageList=imageList+  "<tr class=\"title\" ><td colspan='10'>已上传图片</td></tr>";
	                                           imageList=imageList+"<tr >";
	                                           imageList=imageList+"    <td >序号</td>";
	                                           imageList=imageList+"    <td >素材名</td>";
	                                           imageList=imageList+"    <td >素材关键字</td>";
				                               if(showUrl){
				                                  imageList=imageList+"    <td >素材Url</td>";
				                               }
	                                           imageList=imageList+"    <td>文件名</td>";
	                                           imageList=imageList+"    <td>文件大小</td>";
	                                           imageList=imageList+"    <td>文件宽度</td>";
	                                           imageList=imageList+"    <td>文件高度</td>";
	                                           imageList=imageList+"    <td>规格效验</td>";
		                                       imageList=imageList+"	<td>操作</td>";
	                                           imageList=imageList+"  </tr>";
	                                           
	                                      var ss= new Array();
                                          ss = uploadImage.split(",");
                                          for(var i=0;i<ss.length;i++){
                                              var ww= new Array();
                                              ww = ss[i].split("/");
                                               
                                              imageList=imageList+"  <tr>";
		                                      imageList=imageList+"    <td>"+(i+1)+"</td>";
		                                      imageList=imageList+"    <td><input  type=\"text\" id='imageFileName-"+ww[0]+"' name='imageFileName-"+ww[0]+"' value='"+ww[4]+"' onchange='imageNameChange(\""+ww[0]+"\");' /></td>";
		                                      imageList=imageList+"    <td><input  type=\"text\" id='imageKeyword-"+ww[0]+"' name='imageKeyword-"+ww[0]+"' value='"+ww[5]+"' onchange='imageKeywordChange(\""+ww[0]+"\");' /></td>";
		                                      if(showUrl){
		                                          imageList=imageList+"    <td><input  type=\"text\" id='imageUrl-"+ww[0]+"' name='imageUrl-"+ww[0]+"' value='"+ww[7]+"' onchange='imageUrlwordChange(\""+ww[0]+"\");' /></td>";
		                                      }       
		                                      imageList=imageList+"    <td>"+ww[0]+"</td>";
		                                      imageList=imageList+" <input id='imageFileNames' name='imageFileNames' type='hidden' value='"+ww[0] +"'/>";
		                                      imageList=imageList+"    <td>"+ww[1]+"</td>";
		                                      imageList=imageList+"    <td>"+ww[2]+"</td>";
		                                      imageList=imageList+"    <td>"+ww[3]+"</td>";
		                                      if(ww[6]==0){
		                                          imageList=imageList+" <td>不通过</td>";	     
		                                      }else{
		                                          imageList=imageList+" <td>通过</td>";
		                                      }
		                                      
		                                      imageList=imageList+"    <td><a href='#' onclick='imagePreview(\""+ww[0]+"\");'>预览</a>&nbsp;&nbsp;<a href='#' onclick='deleteImageFile(\""+ww[0]+"\");'>删除</a></td>";                      
		                                      imageList=imageList+"  </tr>";
                                          }
                                          imageList=imageList+"  </table>";
                                          document.getElementById("image_file").innerHTML=imageList;
						      					   						   						
						}else{
							alert('图片上传失败');
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
		'multi':true,
		'fileExt':'*.ts;*.mp4',
		'fileDesc':'*.ts;*.mp4',
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
							//document.getElementById("videoMeta.runTime").value= json.duration;
							//var vedio_url= '<%= basePath%>'+json.filepath;
							
							//$("#vlc").css({
								//width:$$("videoWidth").value+"px",
								//height:$$("videoHeight").value+"px"
							//});
							//var vlc=document.getElementById("vlc");
							//vlc.playlist.clear();
							 // 添加播放地址
							 //vlc.playlist.add(vedio_url);
							 // 播放
							 //vlc.playlist.play();
							 //$("#video").show();
							 
							               if(uploadVideo==""){
							                  uploadVideo=json.viewpath+"/"+json.videoDuration+"/"+json.oldFileName+"/ /"+json.checkTag;
							               }else{
							                  uploadVideo = uploadVideo+","+json.viewpath+"/"+json.videoDuration+"/"+json.oldFileName+"/ /"+json.checkTag;
							               }
							               
							               var videoList= "<table cellspacing='1' class='searchList'>";
	                                           videoList=videoList+  "<tr class=\"title\" ><td colspan='7'>已上传视频</td></tr>";
	                                           videoList=videoList+"<tr >";
	                                           videoList=videoList+"    <td >序号</td>";
	                                           videoList=videoList+"    <td >素材名</td>";
	                                           videoList=videoList+"    <td >素材关键字</td>";
	                                           videoList=videoList+"    <td>文件名</td>";
	                                           videoList=videoList+"    <td>时长</td>";
	                                           videoList=videoList+"    <td>规格效验</td>";
		                                       videoList=videoList+"	<td>操作</td>";
	                                           videoList=videoList+"  </tr>";
	                                           
	                                      var ss= new Array();
                                          ss = uploadVideo.split(",");
                                          for(var i=0;i<ss.length;i++){
                                              var ww= new Array();
                                              ww = ss[i].split("/");
                                               
                                              videoList=videoList+"  <tr>";
		                                      videoList=videoList+"    <td>"+(i+1)+"</td>";
		                                      videoList=videoList+"    <td><input  type=\"text\" id='videoFileName-"+ww[0]+"' name='videoFileName-"+ww[0]+"' value='"+ww[2]+"' onchange='videoNameChange(\""+ww[0]+"\");' /></td>";
		                                      videoList=videoList+"    <td><input  type=\"text\" id='videoKeyword-"+ww[0]+"' name='videoKeyword-"+ww[0]+"' value='"+ww[3]+"' onchange='videoKeywordChange(\""+ww[0]+"\");' /></td>";
		                                      videoList=videoList+"    <td>"+ww[0]+"</td>";
		                                      videoList=videoList+" <input id='videoFileNames' name='videoFileNames' type='hidden' value='"+ww[0] +"&"+ww[1]+"'/>";
		                                      videoList=videoList+"    <td>"+ww[1]+"</td>";
		                                      if(ww[4]==0){
		                                          videoList=videoList+" <td>不通过</td>";	     
		                                      }else{
		                                          videoList=videoList+" <td>通过</td>";
		                                      }
		                                      
		                                      videoList=videoList+"    <td><a href='#' onclick='videoPreview(\""+ww[0]+"\");'>预览</a>&nbsp;&nbsp;<a href='#' onclick='deleteVideoFile(\""+ww[0]+"\");'>删除</a></td>";                      
		                                      videoList=videoList+"  </tr>";
                                          }
                                          videoList=videoList+"  </table>";
                                          document.getElementById("video_file").innerHTML=videoList;
							 
							 

							
						}else{
							alert('视频上传失败');
						}
					}
				}	
		}
	});
	
	
	//<!-- zip start -->
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
							               $("#zipbackgroundImage").val(json.filepath);;
							               if(uploadZip==""){
							                  uploadZip=json.viewpath+"/"+json.imageFileSize+"/"+json.imageFileWidth+"/"+json.imageFileHigh+"/"+json.oldFileName+"/ /"+json.checkTag;
							               }else{
							                  uploadZip = uploadZip+","+json.viewpath+"/"+json.imageFileSize+"/"+json.imageFileWidth+"/"+json.imageFileHigh+"/"+json.oldFileName+"/ /"+json.checkTag;
							               }
							               var zipList= "<table cellspacing='1' class='searchList'>";
	                                           zipList=zipList+  "<tr class=\"title\" ><td colspan='9'>已上传ZIP文件</td></tr>";
	                                           zipList=zipList+"<tr >";
	                                           zipList=zipList+"    <td >序号</td>";
	                                           zipList=zipList+"    <td >素材名</td>";
	                                           zipList=zipList+"    <td >素材关键字</td>";
	                                           zipList=zipList+"    <td>文件名</td>";
	                                           zipList=zipList+"    <td>文件大小</td>";

	                                           zipList=zipList+"    <td>规格效验</td>";
		                                       zipList=zipList+"	<td>操作</td>";
	                                           zipList=zipList+"  </tr>";
	                                           
	                                      var ss= new Array();

                                          ss = uploadZip.split(",");

                                          for(var i=0;i<ss.length;i++){
                                              var ww= new Array();
                                              ww = ss[i].split("/");
                                               
                                              zipList=zipList+"  <tr>";
		                                      zipList=zipList+"    <td>"+(i+1)+"</td>";
		                                      zipList=zipList+"    <td><input  type=\"text\" id='zipFileName-"+ww[0]+"' name='zipFileName-"+ww[0]+"' value='"+ww[4]+"' onchange='zipNameChange(\""+ww[0]+"\");' /></td>";
		                                      zipList=zipList+"    <td><input  type=\"text\" id='zipKeyword-"+ww[0]+"' name='zipKeyword-"+ww[0]+"' value='"+ww[5]+"' onchange='zipKeywordChange(\""+ww[0]+"\");' /></td>";
		                                      zipList=zipList+"    <td>"+ww[0]+"</td>";
		                                      zipList=zipList+" <input id='zipFileNames' name='zipFileNames' type='hidden' value='"+ww[0] +"'/>";
		                                      zipList=zipList+"    <td>"+ww[1]+"</td>";

		                                      if(ww[6]==0){
		                                          zipList=zipList+" <td>不通过</td>";	     
		                                      }else{
		                                          zipList=zipList+" <td>通过</td>";
		                                      }
		                                      var previewZipImage = ww[0].substring(0,ww[0].lastIndexOf("."))+".jpg";
		                                      zipList=zipList+"    <td><a href='#' onclick='zipImagePreview(\""+previewZipImage+"\");'>预览</a>&nbsp;&nbsp;<a href='#' onclick='deleteZipFile(\""+ww[0]+"\");'>删除</a></td>";                      
		                                      zipList=zipList+"  </tr>";
                                          }
                                          zipList=zipList+"  </table>";
                                          document.getElementById("zip_file").innerHTML=zipList;
						      					   						   						
						}else{
							if(json.zip_fileSize_fail != null){
								alert(json.zip_fileSize_fail);
							}else if(json.dirName != "recommend"){
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
							               $("#zipbackgroundImage").val(json.filepath);
							               
							               if(uploadZip==""){
							                  uploadZip=json.viewpath+"/"+json.imageFileSize+"/"+json.imageFileWidth+"/"+json.imageFileHigh+"/"+json.oldFileName+"/ /"+json.checkTag;
							               }else{
							                  uploadZip = uploadZip+","+json.viewpath+"/"+json.imageFileSize+"/"+json.imageFileWidth+"/"+json.imageFileHigh+"/"+json.oldFileName+"/ /"+json.checkTag;
							               }
							               
	                                       var zipList= "<table cellspacing='1' class='searchList'>";
	                                           zipList=zipList+  "<tr class=\"title\" ><td colspan='9'>已上传ZIP文件</td></tr>";
	                                           zipList=zipList+"<tr >";
	                                           zipList=zipList+"    <td >序号</td>";
	                                           zipList=zipList+"    <td >素材名</td>";
	                                           zipList=zipList+"    <td >素材关键字</td>";
	                                           zipList=zipList+"    <td>文件名</td>";
	                                           zipList=zipList+"    <td>文件大小</td>";

	                                           zipList=zipList+"    <td>规格效验</td>";
		                                       zipList=zipList+"	<td>操作</td>";
	                                           zipList=zipList+"  </tr>";
	                                      var ss= new Array();
                                          ss = uploadZip.split(",");
                                          for(var i=0;i<ss.length;i++){
                                              var ww= new Array();
                                              ww = ss[i].split("/");

		                                      
		                                      zipList=zipList+"  <tr>";
		                                      zipList=zipList+"    <td>"+(i+1)+"</td>";
		                                      zipList=zipList+"    <td><input  type=\"text\" id='zipFileName-"+ww[0]+"' name='zipFileName-"+ww[0]+"' value='"+ww[4]+"' onchange='zipNameChange(\""+ww[0]+"\");' /></td>";
		                                      zipList=zipList+"    <td><input  type=\"text\" id='zipKeyword-"+ww[0]+"' name='zipKeyword-"+ww[0]+"' value='"+ww[5]+"' onchange='zipKeywordChange(\""+ww[0]+"\");' /></td>";
		                                      zipList=zipList+"    <td>"+ww[0]+"</td>";
		                                      zipList=zipList+" <input id='zipFileNames' name='zipFileNames' type='hidden' value='"+ww[0] +"'/>";
		                                      zipList=zipList+"    <td>"+ww[1]+"</td>";

		                                      if(ww[6]==0){
		                                          zipList=zipList+" <td>不通过</td>";	     
		                                      }else{
		                                          zipList=zipList+" <td>通过</td>";
		                                      }
		                                      var previewZipImage = ww[0].substring(0,ww[0].lastIndexOf("."))+".jpg";
		                                      zipList=zipList+"    <td><a href='#' onclick='zipImagePreview(\""+previewZipImage+"\");'>预览</a>&nbsp;&nbsp;<a href='#' onclick='deleteZipFile(\""+ww[0]+"\");'>删除</a></td>";                      
		                                      zipList=zipList+"  </tr>";
                                          }
                                          zipList=zipList+"  </table>";
                                          document.getElementById("zip_file").innerHTML=zipList;
						      					   						   						
						}else{
							if(json.zip_fileSize_fail != null){
								alert(json.zip_fileSize_fail);
							}else if(json.dirName != "recommend"){
								alert("ZIP包内文件夹名称必须为：recommend");
							}else{
								alert('ZIP文件上传失败');
							}
						}
					}
				}	
		}
	});
	
	//<!-- zip end-->
	
	
	
	
});

//图片素材名改变时,页面数据实时更新
function imageNameChange(fileName){
    var objId="imageFileName-"+fileName;
    var newName = document.getElementById(objId).value;
    if(uploadImage!=""){
		var ss= new Array();
        ss = uploadImage.split(",");
        for(var i=0;i<ss.length;i++){
            var ww= new Array();
            ww = ss[i].split("/");
            if(ww[0]==fileName){
                 ss[i]=ww[0]+"/"+ww[1]+"/"+ww[2]+"/"+ww[3]+"/"+newName+"/"+ww[5]+"/"+ww[6];
            }
            if(i==0){
               uploadImage=ss[i];
            }else{
               uploadImage = uploadImage+","+ss[i];
            }
        }
	}
}

//图片素材关键字改变时,页面数据实时更新
function imageKeywordChange(fileName){
    var objId="imageKeyword-"+fileName;
    var newName = document.getElementById(objId).value;
    if(uploadImage!=""){
		var ss= new Array();
        ss = uploadImage.split(",");
        for(var i=0;i<ss.length;i++){
            var ww= new Array();
            ww = ss[i].split("/");
            if(ww[0]==fileName){
                 ss[i]=ww[0]+"/"+ww[1]+"/"+ww[2]+"/"+ww[3]+"/"+ww[4]+"/"+newName+"/"+ww[6];
            }
            if(i==0){
               uploadImage=ss[i];
            }else{
               uploadImage = uploadImage+","+ss[i];
            }
        }
	}
}

//图片素材URL改变时,页面数据实时更新

function imageUrlwordChange(fileName){
	
    var objId="imageUrl-"+fileName;
    var newUrl = document.getElementById(objId).value;
    if(uploadImage!=""){
		var ss= new Array();
        ss = uploadImage.split(",");
        for(var i=0;i<ss.length;i++){
            var ww= new Array();
            ww = ss[i].split("/");
            if(ww[0]==fileName){
                 ss[i]=ww[0]+"/"+ww[1]+"/"+ww[2]+"/"+ww[3]+"/"+ww[4]+"/"+ww[5]+"/"+ww[6]+"/"+newUrl;
            }
            if(i==0){
               uploadImage=ss[i];
            }else{
               uploadImage = uploadImage+","+ss[i];
            }
        }
	}
	
}


//视频素材名改变时,页面数据实时更新
function videoNameChange(fileName){
    var objId="videoFileName-"+fileName;
    var newName = document.getElementById(objId).value;
    if(uploadVideo!=""){
		var ss= new Array();
        ss = uploadVideo.split(",");
        for(var i=0;i<ss.length;i++){
            var ww= new Array();
            ww = ss[i].split("/");
            if(ww[0]==fileName){
                 ss[i]=ww[0]+"/"+ww[1]+"/"+newName+"/"+ww[3]+"/"+ww[4];
            }
            if(i==0){
               uploadVideo=ss[i];
            }else{
               uploadVideo = uploadVideo+","+ss[i];
            }
        }
	}
}

//视频素材关键字改变时,页面数据实时更新
function videoKeywordChange(fileName){
    var objId="videoKeyword-"+fileName;
    var newName = document.getElementById(objId).value;
    if(uploadVideo!=""){
		var ss= new Array();
        ss = uploadVideo.split(",");
        for(var i=0;i<ss.length;i++){
            var ww= new Array();
            ww = ss[i].split("/");
            if(ww[0]==fileName){
                 ss[i]=ww[0]+"/"+ww[1]+"/"+ww[2]+"/"+newName+"/"+ww[4];
            }
            if(i==0){
               uploadVideo=ss[i];
            }else{
               uploadVideo = uploadVideo+","+ss[i];
            }
        }
	}
}


//zip素材名改变时,页面数据实时更新
function zipNameChange(fileName){
    var objId="zipFileName-"+fileName;
    var newName = document.getElementById(objId).value;
    if(uploadZip!=""){
		var ss= new Array();
        ss = uploadZip.split(",");
        for(var i=0;i<ss.length;i++){
            var ww= new Array();
            ww = ss[i].split("/");
            if(ww[0]==fileName){
                 ss[i]=ww[0]+"/"+ww[1]+"/"+ww[2]+"/"+ww[3]+"/"+newName+"/"+ww[5]+"/"+ww[6];
            }
            if(i==0){
               uploadZip=ss[i];
            }else{
               uploadZip = uploadZip+","+ss[i];
            }
        }
	}
}

//ZIP素材关键字改变时,页面数据实时更新
function zipKeywordChange(fileName){
    var objId="zipKeyword-"+fileName;
    var newName = document.getElementById(objId).value;
    if(uploadZip!=""){
		var ss= new Array();
        ss = uploadZip.split(",");
        for(var i=0;i<ss.length;i++){
            var ww= new Array();
            ww = ss[i].split("/");
            if(ww[0]==fileName){
                 ss[i]=ww[0]+"/"+ww[1]+"/"+ww[2]+"/"+ww[3]+"/"+ww[4]+"/"+newName+"/"+ww[6];
            }
            if(i==0){
               uploadZip=ss[i];
            }else{
               uploadZip = uploadZip+","+ss[i];
            }
        }
	}
}

function changeType2()
{
   var materialType = selectOptionVal("sel_material_type");
	maTerialType=materialType;	
	if (materialType == 1 )
	{//视频
		//document.getElementById('tt').style.display = "";
		document.getElementById('div_text').style.display = "none";      
		document.getElementById('div_video').style.display = "";      
		document.getElementById('div_image').style.display = "none";
		document.getElementById('div_question').style.display = "none";
		document.getElementById('div_zip').style.display = "none";
		//document.getElementById('b1').style.display = "none";
		//document.getElementById('b2').style.display = "";
		document.getElementById('div_materialName').style.display = "none";
		document.getElementById('image_file').style.display = "none";
		document.getElementById('video_file').style.display = "";
		
	}
	else if (materialType == 0 )
	{//图片
		//alert("图片："+materialType);
		//document.getElementById('tt').style.display = "";
		document.getElementById('div_text').style.display = "none";      
		document.getElementById('div_video').style.display = "none";      
		document.getElementById('div_image').style.display = "";
		document.getElementById('div_question').style.display = "none";
		document.getElementById('div_zip').style.display = "none";
		//document.getElementById('b1').style.display = "none";
		//document.getElementById('b2').style.display = "";
		document.getElementById('div_materialName').style.display = "none";
		document.getElementById('video_file').style.display = "none";
		document.getElementById('image_file').style.display = "";
		document.getElementById('zip_file').style.display = "none";
	}
	else if (materialType == 2 )
	{//文字
	    //alert("文字："+materialType);
	    //document.getElementById('tt').style.display = "";
		document.getElementById('div_text').style.display = "";      
		document.getElementById('div_video').style.display = "none";      
		document.getElementById('div_image').style.display = "none";
		document.getElementById('div_question').style.display = "none";
		document.getElementById('div_zip').style.display = "none";
		//document.getElementById('b1').style.display = "none";
		//document.getElementById('b2').style.display = "";
		document.getElementById('div_materialName').style.display = "";
		document.getElementById('image_file').style.display = "none";
		document.getElementById('video_file').style.display = "none";
		document.getElementById('zip_file').style.display = "none";
	}
	else if (materialType == 3 )
	{//调查问卷
	    //alert("调查问卷："+materialType);
	    //document.getElementById('tt').style.display = "";
		document.getElementById('div_text').style.display = "none";      
		document.getElementById('div_video').style.display = "none";      
		document.getElementById('div_image').style.display = "none";
		document.getElementById('div_question').style.display = "";
		document.getElementById('div_question2').style.display = "none";
		//document.getElementById('b1').style.display = "none";
		//document.getElementById('b2').style.display = "";
		document.getElementById('div_materialName').style.display = "";
		document.getElementById('image_file').style.display = "none";
		document.getElementById('video_file').style.display = "none";
		document.getElementById('zip_file').style.display = "none";
	}
	else if (materialType == 4 )
	{//zip


		document.getElementById('div_text').style.display = "none";      
		document.getElementById('div_video').style.display = "none";      
		document.getElementById('div_image').style.display = "none";
		document.getElementById('div_question').style.display = "none";
		document.getElementById('div_question2').style.display = "none";
		document.getElementById('div_zip').style.display = "";

		document.getElementById('div_materialName').style.display = "none";
		document.getElementById('image_file').style.display = "none";
		document.getElementById('video_file').style.display = "none";
		document.getElementById('zip_file').style.display = "";
	}
}

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
        }else if (materialType == 3 ){
        	if(checkQuestion()){
				return;
            }
        }else if(materialType == 4){
        	if(checkZip()){
        		return;
        	}
        }
        
     //效验素材名称是否重复   
     if(materialType == 0){

       //图片
       var ss= new Array();
       var ww= new Array();
       ss = uploadImage.split(",");
	   var resourceName="";
	   var tag="1";

	   for (var i=0;i<ss.length;i++){            
                ww = ss[i].split("/");                                                          
	            resourceName=$$("imageFileName-"+ww[0]).value;
	            //alert("素材名:"+resourceName);
	            if(ww[6]==0){
                    //图片效验不通过
                    alert("图片规格效验不通过");
                    tag="0"
                    break;
                }            
	            $.ajax({
                type:"post",
                async : false,
                url:"<%=request.getContextPath()%>/page/material/checkMaterialExist.do?",
                data:{"resourceName":resourceName},//Ajax传递的参数
                success:function(mess)
                {
                	if(mess=="0")// 如果获取的数据不为空
                    {
                		
                		//alert("ok");
                    }
                    else
                    {
						alert("素材名称已存在，请重新输入！");
						tag="0";
						$$("zipFileName-"+ww[0]).focus();
						return;
                    }
                   
                },
                error:function(mess)
                {
                	alert("未知错误");
                }
            });
	        }
	        if(tag==1){
	           document.getElementById("saveForm").submit();
	           document.getElementById("addMaterialButton").disabled="true";
	        }
	        
       
     }else{
        if(materialType == 1){
        //视频
       var ss= new Array();
       var ww= new Array();
       ss = uploadVideo.split(",");
	   var resourceName="";
	   var tag="1";

	   for (var i=0;i<ss.length;i++){            
                ww = ss[i].split("/");                                            
	            resourceName=$$("videoFileName-"+ww[0]).value;
	            //alert("素材名:"+resourceName);
	            if(ww[4]==0){
                    //视频效验不通过
                    alert("视频规格效验不通过");
                    tag="0";
                    break;
                } 
	                         
	            $.ajax({
                type:"post",
                async : false,
                url:"<%=request.getContextPath()%>/page/material/checkMaterialExist.do?",
                data:{"resourceName":resourceName},//Ajax传递的参数
                success:function(mess)
                {
                	if(mess=="0")// 如果获取的数据不为空
                    {
                		
                		//alert("ok");
                    }
                    else
                    {
						alert("素材名称已存在，请重新输入！");
						tag="0";
						$$("videoFileName-"+ww[0]).focus();
						return;
                    }
                   
                },
                error:function(mess)
                {
                	alert("未知错误");
                }
            });
	        }
	        if(tag==1){
	           document.getElementById("saveForm").submit();
	           document.getElementById("addMaterialButton").disabled="true";
	        }
        
        
        }else if(materialType == 4){
        	 //zip
       var ss= new Array();
       var ww= new Array();
       ss = uploadZip.split(",");
	   var resourceName="";
	   var tag="1";

	   for (var i=0;i<ss.length;i++){            
                ww = ss[i].split("/");                                                          
	            resourceName=$$("zipFileName-"+ww[0]).value;
	            //alert("素材名:"+resourceName);
	            if(ww[6]==0){
                    alert("ZIP规格效验不通过");
                    tag="0"
                    break;
                }            
	            $.ajax({
                type:"post",
                async : false,
                url:"<%=request.getContextPath()%>/page/material/checkMaterialExist.do?",
                data:{"resourceName":resourceName},//Ajax传递的参数
                success:function(mess)
                {
                	if(mess=="0")// 如果获取的数据不为空
                    {
                		
                		//alert("ok");
                    }
                    else
                    {
						alert("素材名称已存在，请重新输入！");
						tag="0";
						$$("imageFileName-"+ww[0]).focus();
						return;
                    }
                   
                },
                error:function(mess)
                {
                	alert("未知错误");
                }
            });
	        }
	        if(tag==1){
	           document.getElementById("saveForm").submit();
	           document.getElementById("addMaterialButton").disabled="true";
	        }
       
        }else{
        //其他
           var resourceName = $$("material.resourceName").value;	  
	       $.ajax({
                type:"post",
                async : false,
                url:"<%=request.getContextPath()%>/page/material/checkMaterialExist.do?",
                data:{"resourceName":resourceName},//Ajax传递的参数
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
						$$("material.resourceName").focus();
						return;
                    }
                   
                },
                error:function(mess)
                {
                	alert("未知错误");
                }
            });
        }
     }
        
     
     
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
		if(selectOptionVal("sel_material_type")==2||selectOptionVal("sel_material_type")==3){
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
	* 检查图片
	*/
	function checkImage(){
		var localFilePath = $$("backgroundImage").value; 
        $("#localFilePath").val(localFilePath);
		if(isEmpty($("#localFilePath").val())){
			alert("上传的文件不能为空!");
			return true;
		}
		return false;
    }
    
    /**
	*
	* 检查问卷
	*/
	function checkQuestion(){
		if(isEmpty($$("questionSubject.integral").value)){
			alert("问卷积分不能为空！");
			$$("questionSubject.integral").focus();
    		return true;
		}
		return false;
    }

	/**
	*
	* 检查图片
	*/
	function checkVideo(){
		var localFilePath = $$("backgroundImage2").value; 
        $("#localFilePath").val(localFilePath);
		if(isEmpty($("#localFilePath").val())){
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
        $("#ziplocalFilePath").val(localFilePath);
		if(isEmpty($("#ziplocalFilePath").val())){
			alert("上传的文件不能为空!");
			return true;
		}
		return false;
    }
	

function IsDigit(cCheck) { 
   return (('0'<=cCheck) && (cCheck<='9')); 

}

function IsAlpha(cCheck) {
 return ((('a'<=cCheck) && (cCheck<='f')) || (('A'<=cCheck) && (cCheck<='F'))) 
 
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
		var str = $$("textMeta.fontColor").value.substring(1);

		if($$("textMeta.fontColor").value.substring(0,1)=='#'
		&& $$("textMeta.fontColor").value.substring(1).length==6
		&& pat.test(str)){
			
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
		   str = $$("textMeta.bkgColor").value.substring(1);
		   if($$("textMeta.bkgColor").value.substring(0,1)=='#'
		&& $$("textMeta.bkgColor").value.substring(1).length==6
		&& pat.test(str)){
			
		}else{
		     alert("文字显示背景色格式不正确！");
			$$("textMeta.bkgColor").focus();
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
		    
		    if($$("textMeta.rollSpeed").value.length>10){
			alert("文本显示滚动速度必须小于10个字节！");
			$$("textMeta.rollSpeed").focus();
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
			    	 var left = coordinates[0]/1280*426+"px";
					   var bottom = coordinates[1]/720*240+"px";					 				  
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
		
		


</script>
<style>
	.easyDialog_wrapper{ width:1000px;height:400px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
<style>
	.easyDialog2_wrapper{ width:426px;height:240px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog2_text{}
</style>
</head>
<body class="mainBody">
<form action="<%=path %>/page/meterial/saveMaterialBackup.do" method="post" id="saveForm">	
<input id="videoWidth" name="videoWidth" type="hidden" value=""/>
<input id="videoHeight" name="videoHeight" type="hidden" value=""/>
<input id="positionPackIds" name="positionPackIds" type="hidden" value="${positionPackIds}"/>
<div class="path">首页 >> 素材管理 >> 新增素材</div>
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
		                         <input id="material.contractId" name="material.contractId" type="hidden" />	              		                	
							     <input id="material.contractName" name="material.contractName" class="new_input_add" 
							     type="text" readonly="readonly" onclick="selectContract();" />	
						     </td>
		                  -->
		                     
		                     <td width="15%" align="right"><span class="required">*</span>选择广告位：</td>
		                     <td width="35%" colspan="3">	                
		                         <input id="material.advertPositionId" name="material.advertPositionId" type="hidden" />
				                 <input id="material.advertPositionName" name="material.advertPositionName" value="" type="text" class="new_input_add" readonly="readonly" onclick="selectAdPosition();"/>
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
		                        
			              	    <select id="sel_material_type" name="material.resourceType" onclick="changeType2();">
							    </select>		  
		                     </td>
		                  </tr>
		                  <tr id="div_materialName">
		                     <td align="right"><span class="required">*</span>素材名称：</td>
		                     <td>
		                	     <input id="material.resourceName" name="material.resourceName" type="text" maxlength="20" />
		                     </td>
		                     <td width="15%" align="right">素材关键字：</td>
		                     <td width="35%">
		                         <input id="material.keyWords" name="material.keyWords" type="text" />					       
		                     </td>		                
		                 </tr>
		                  <!-- 
		                  <tr>
		                     <td align="right"><span class="required">*</span>开始时间：</td>
		                     <td>		               
		                         <input id="material.startTime" readonly="readonly"  type="text" style="width:125px;"  name="material.startTime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		                     </td>
		                     <td align="right"><span class="required">*</span>结束时间：</td>
		                     <td>
			              	     <input id="material.endTime"  readonly="readonly" class="input_style2" type="text" style="width:125px;"  name="material.endTime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		                     </td>
		                  </tr>
		                  <tr>
		                     <td align="right"><span class="required"></span>素材描述：</td>
		                     <td colspan="3">
		                	     <textarea id="material.resourceDesc" name="material.resourceDesc" cols="40" rows="3" maxlength="100"></textarea>		              	
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
		                <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px; display: none;" id ="div_text" >
		                      <tr class="title" >
		                          <td colspan="4">文字素材</td>
		                      </tr>
		                      <tr>
		                          <td  align="right"><span class="required">*</span>文字标题：</td>
		                          <td>
			            		      <input id="textMeta.name" name="textMeta.name" />
		                          </td>
		                          <td  align="right">文本URL：</td>
		                          <td>
			            		      <input id="textMeta.URL" name="textMeta.URL" />
		                         </td>
		                      </tr>
		                      <tr>
		                          <td  align="right">文本显示动作：</td>
		                          <td>
			            		      <select  id="sel_textMeta_action"  name="textMeta.action" onchange="changeTextAction()">
								               <!-- <option id="action_id" value="-1">请选择...</option> --> 
										          <option  value="1" <c:if test="${textMeta.action== 1}">selected="selected"</c:if> >
										                        滚动
										        </option>
										        <option  value="0" <c:if test="${textMeta.action==0}">selected="selected"</c:if> >
										                        静止
										        </option>
										      
							          </select>
		                          </td>
		                          <td  align="right"></td>
		                          <td>
			            		      <input style="display:none" id="textMeta.durationTime" name="textMeta.durationTime" />
		                         </td>
		                      </tr>
		                      <tr>
		                          <td  align="right"><span class="required">*</span>文字大小：</td>
		                          <td>
			            		      <input id="textMeta.fontSize" name="textMeta.fontSize" /><span class="required">px</span>
		                          </td>
		                          <td  align="right"><span class="required">*</span>文字颜色：</td>
		                          <td>
			            		      <input id="textMeta.fontColor" name="textMeta.fontColor" /><span class="required">格式：#235612</span>
		                         </td>
		                      </tr>
		                      <tr>
		                          <td  align="right">文本显示背景色：</td>
		                          <td>
			            		      <input id="textMeta.bkgColor" name="textMeta.bkgColor" /><span class="required">格式：#235612</span>
		                          </td>
		                          <td  align="right">文本显示滚动速度：</td>
		                          <td>
			            		     <!--  <input id="textMeta.rollSpeed" name="textMeta.rollSpeed" /> -->
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
			            		      <input id="textMeta.positionVertexCoordinates" name="textMeta.positionVertexCoordinates" /><span class="required">格式：80*80(坐标x*y)</span>
		                          </td>
		                          <td  align="right">文本显示区域：</td>
		                          <td>
			            		      <input id="textMeta.positionWidthHeight" name="textMeta.positionWidthHeight" /><span class="required">格式：80*80(宽高w*h)</span>
		                         </td>
		                      </tr>
		                      <tr>
		            	          <td width="15%" align="right"><span class="required">*</span>内容：</td>
		                          <td colspan="3">
		                	      <textarea id="textMeta.contentMsg" name="textMeta.contentMsg" onchange="javascript:showText();" maxlength="4000" cols="80" rows="5"></textarea>
		                          </td>
		                      </tr>  
		                      <tr>
							      <td align="right" >素材预览效果：</td>
							      <td colspan="3">
									  <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:426px;height:240px;
									     position: relative;">
											<img id="pImage" src="<%=path%>/images/position/position.jpg" width="426px" height="240px" /> 
											<div id="text"><marquee scrollamount="10" id="textContent"></marquee></div>
											<div id="text2"><span id="textContent2"></span></div> 
											
										</div>
							      </td>
							
					          </tr>   
		                </table>
					
					    <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px; display: none;" id ="div_video">
		                      <tr class="title" >
		                          <td colspan="4">视频素材</td>
		                          <input id="videoMetaName" name="videoMetaName" type="hidden" />
		                      </tr>
		                      <tr>
		            	          <td width="15%" align="right"><span class="required"></span>时长规格：</td>
		                          <td width="35%" >
		                          <!-- <input maxlength="10" id="videoMeta.runTime" name="videoMeta.runTime" type="text" /> -->
		                	          
		                	          <input maxlength="10" id="videoFileDuration" disabled="disabled" type="text" />
		                          </td>
		                          <td width="15%" align="right"><span class="required"></span>选择文件：</td>
		                          <td>	           
		                              <div id="fileQueue2"></div> 	          
							          <input id="backgroundImage2" name="" value="" type="hidden"  />
							          <input id="file_id2" name="upload" type="file" />
		                          </td>
		                          <div id="fileQueue2"></div>
		                      </tr>
		                      <!-- 
		                      <tr>
							      <td align="right" >素材预览效果：</td>
							      <td colspan="3">					 
								  <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:426px;height:240px;
								     position: relative;">
										<img id="pImage" src="<%=path%>/images/position/position.jpg" width="426px" height="240px" />
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
		                       -->			          
                       </table>
                       <div id="video_file"> 
					   </div>
	            
					   <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px; display: none;" id ="div_image" >
		                      <tr class="title" ><td colspan="4">图片素材</td></tr>		            
		                      <tr>		            	
		                         <td align="right"><span class="required"></span> 选择文件：</td>		                         		                         
		                         <input id="imageMetaName" name="imageMetaName" type="hidden" />
		                         <td>
			            	        <div id="upload11"><input id="file_id" name="upload" type="file" class="e_input" /></div>
			            	        <div id="upload13"><input id="file_id3" name="upload" type="file" class="e_input" /></div>
							        <input id="backgroundImage" name="" value="" type="hidden"  />
							        <input id="localFilePath" name="localFilePath"  type="hidden" />
		                         </td>
		                         <td width="15%" align="right"><span class="required"></span>文件大小规格：</td>
		                          <td width="35%" >
		                	          <input maxlength="10" id="imageFileSize" type="text" disabled="disabled"/>
		                          </td>
		                     </tr>
		                     <tr>		            	
		                         <td width="15%" align="right"><span class="required"></span>文件宽度规格：</td>
		                          <td width="35%" >
		                	          <input maxlength="10" id="imageFileWidth" type="text" disabled="disabled"/>
		                          </td>
		                         <td width="15%" align="right"><span class="required"></span>文件高度规格：</td>
		                          <td width="35%" >
		                	          <input maxlength="10" id="imageFileHigh" type="text" disabled="disabled"/>
		                          </td>
		                     </tr>
		                     <!--<tr>		            	
		                         <td width="15%" align="right"><span class="required"></span>图片URL：</td>
		                          <td width="35%" >
		                	          <input maxlength="20" id="imageUrl" name="imageUrl" value="" type="text"/>
		                          </td>
		                          <td width="15%" >
		                          </td>
		                          <td width="35%" >
		                          </td>
		                     </tr>
		                     --><!-- 
		                     <tr>
							     <td align="right" >素材预览效果：</td>
							     <td colspan="3">
								    <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:426px;height:240px;
								     position: relative;">
										<img id="pImage" src="<%=path%>/images/position/position.jpg" width="426px" height="240px" /> 
										<img id="mImage" src="" style="display: none" />
									</div>
							     </td>						
					         </tr>
		                      -->
		                     
					         
		               </table>
		               <div id="image_file">
					         
					   </div>
					    <!----------------------------------------------->
					    <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px; display: none;" id ="div_zip" >
		                      <tr class="title" ><td colspan="4">ZIP素材</td></tr>		            
		                      <tr>		            	
		                         <td align="right"><span class="required"></span> 选择文件：</td>		                         		                         
		                         <input id="zipMetaName" name="zipMetaName" type="hidden" />
		                         <td>
			            	        <div id="zipupload11"><input id="zipfile_id" name="upload" type="file" class="e_input" /></div>
			            	        <div id="zipupload13"><input id="zipfile_id3" name="upload" type="file" class="e_input" /></div>
							        <input id="zipbackgroundImage" name="" value="" type="hidden"  />
							        <input id="ziplocalFilePath" name="localFilePath"  type="hidden" />
		                         </td>
		                         <td width="15%" align="right"><span class="required"></span>文件大小规格：</td>
		                          <td width="35%" >
		                	          <input maxlength="10" id="zipFileSize" type="text" disabled="disabled"/>
		                          </td>
		                     </tr>
		                     
		                     <!-- 
		                     <tr>
							     <td align="right" >素材预览效果：</td>
							     <td colspan="3">
								    <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:426px;height:240px;
								     position: relative;">
										<img id="pImage" src="<%=path%>/images/position/position.jpg" width="426px" height="240px" /> 
										<img id="mImage" src="" style="display: none" />
									</div>
							     </td>						
					         </tr>
		                      -->
		                     
					         
		               </table>
		               <div id="zip_file">
					         
					   </div>
					   
					   <!----------------------------------------------->
					   
					   
					   
					   
					   
		          
		          <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px; display: none;" id ="div_question" >
		            <tr class="title" >
		                <td colspan="4">调查问卷</td>
		            </tr>		            
		            <tr>		            	
		                <td align="right"><span class="required"></span>问卷类型：</td>
		                <td>
			              	    <select id="sel_question_type"  name="questionSubject.questionnaireType">
								     <option id="ad_id" value="-1">请选择...</option>
								            <c:forEach items="${questionTypeList}" var="queBean">
									           <option  value="${queBean.id }" <c:if test="${questionSubject.questionnaireType== queBean.id}">selected="selected"</c:if> >${queBean.typeName }</option>
								            </c:forEach>
							    </select>		  
		                </td>		            	
		                <td align="right"><span class="required"></span>模板选择：</td>
		                <td>
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
			              	 <input id="questionSubject.integral" name="questionSubject.integral" type="text" maxlength="20" />		  
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
		                	              <textarea id="questionSubject.summary" name="questionSubject.summary" cols="30" rows="3" maxlength="100"></textarea>
		                              </td>
		                          </tr>
		                          <tr>
		            	              <td width="15%" align="right"><span class="required"></span>新增问题：</td>
		                              <td width="35%">
		                	              <input type="button"  value="新增问题" onclick="addQuestion()"/>
		                              </td>
		                          </tr>
					        </table>
					     </td>
					</tr>
		            
		          </table>
		          
		        </td>
		      </tr>
			  
			  <tr>
	            	<td colspan="4">
	            		<input type="button" id="addMaterialButton" value="保存" class="btn" onclick="submitForm();"/>
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