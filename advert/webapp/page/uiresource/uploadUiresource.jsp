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
var i=0;
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
		
		var imageList= "<table cellspacing='1' class='searchList'>";
	                                           imageList=imageList+  "<tr class=\"title\" ><td colspan='5'>已上传UI文件</td></tr>";
	                                           imageList=imageList+"<tr >";
	                                           imageList=imageList+"    <td >序号</td>";
	                                           imageList=imageList+"    <td >文件名</td>";
	                                           imageList=imageList+"    <td>文件全名</td>";
	                                           imageList=imageList+"    <td>文件大小</td>";
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
		                                      imageList=imageList+"    <td>"+ww[0]+"</td>";
		                                      imageList=imageList+" <input id='imageFileNames' name='imageFileNames' type='hidden' value='"+ww[0] +"'/>";
		                                      imageList=imageList+"    <td>"+ww[1]+"</td>";		                                      
		                                      imageList=imageList+"    <td>&nbsp;&nbsp;<a href='#' onclick='deleteImageFile(\""+ww[0]+"\");'>删除</a></td>";                      
		                                      imageList=imageList+"  </tr>";
                                          }
	                                      
		                                    }     
	                                      
                                          imageList=imageList+"  </table>";
                                          document.getElementById("image_file").innerHTML=imageList;
                                          document.getElementById("div_image").style.display="";
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
		'script':'uploadMaterial.do',
		'cancelImg':'<%=path%>/js/jquery/upload/image/cancel.png',
		'folder':'/uploadFiles',
		'queueID':'fileQueue',
		'buttonImg':'<%=path%>/js/jquery/upload/image/uploadify.jpg',
		'fileDataName': 'backgroundImage',
		'auto':true,
		'multi':false,
		'fileExt':'*.zip;',
		'fileDesc':'*.zip;',
		'displayData':'speed',
		'width':'76',
    	'height':'23',
		'onSelect': function (event, queueID, fileObj){ 
			$("#file_id").uploadifySettings('script','uploadUiresource.do'); 
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
							               /*
							               if(uploadImage==""){
							                  uploadImage=json.viewpath+"/"+json.imageFileSize+"/"+json.imageFileWidth+"/"+json.imageFileHigh+"/"+json.oldFileName+"/ /"+json.checkTag;
							               }else{
							                  uploadImage = uploadImage+","+json.viewpath+"/"+json.imageFileSize+"/"+json.imageFileWidth+"/"+json.imageFileHigh+"/"+json.oldFileName+"/ /"+json.checkTag;
							               }*/
							                uploadImage=json.viewpath+"/"+json.imageFileSize+"/"+json.imageFileWidth+"/"+json.imageFileHigh+"/"+json.oldFileName+"/ /"+json.checkTag;
							             
							               var imageList= "<table cellspacing='1' class='searchList'>";
	                                           imageList=imageList+  "<tr class=\"title\" ><td colspan='5'>已上传UI文件</td></tr>";
	                                           imageList=imageList+"<tr >";
	                                           imageList=imageList+"    <td >序号</td>";
	                                           imageList=imageList+"    <td >文件名</td>";
	                                           imageList=imageList+"    <td>文件全名</td>";
	                                           imageList=imageList+"    <td>文件大小</td>";
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
		                                      imageList=imageList+"    <td>"+ww[0]+"</td>";
		                                      imageList=imageList+" <input id='imageFileNames' name='imageFileNames' type='hidden' value='"+ww[0] +"'/>";
		                                      imageList=imageList+"    <td>"+ww[1]+"</td>";	
		                                      imageList=imageList+"    <td>&nbsp;&nbsp;<a href='#' onclick='deleteImageFile(\""+ww[0]+"\");'>删除</a></td>";                      
		                                      imageList=imageList+"  </tr>";
                                          }
                                          imageList=imageList+"  </table>";
                                          document.getElementById("image_file").innerHTML=imageList;
                                          
                                          document.getElementById("file_id").style.readonly="true";
                                         // document.getElementById("div_image").style.display="none";
						      					   						   						
						}else{
							alert('图片上传失败');
						}
					}
				}	
		}
	});
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

function changeType2()
{
   var materialType = selectOptionVal("sel_material_type");
		
	if (materialType == 1 )
	{//视频
		//document.getElementById('tt').style.display = "";
		document.getElementById('div_text').style.display = "none";      
		document.getElementById('div_video').style.display = "";      
		document.getElementById('div_image').style.display = "none";
		document.getElementById('div_question').style.display = "none";
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
		//document.getElementById('b1').style.display = "none";
		//document.getElementById('b2').style.display = "";
		document.getElementById('div_materialName').style.display = "none";
		document.getElementById('video_file').style.display = "none";
		document.getElementById('image_file').style.display = "";
	}
	else if (materialType == 2 )
	{//文字
	    //alert("文字："+materialType);
	    //document.getElementById('tt').style.display = "";
		document.getElementById('div_text').style.display = "";      
		document.getElementById('div_video').style.display = "none";      
		document.getElementById('div_image').style.display = "none";
		document.getElementById('div_question').style.display = "none";
		//document.getElementById('b1').style.display = "none";
		//document.getElementById('b2').style.display = "";
		document.getElementById('div_materialName').style.display = "";
		document.getElementById('image_file').style.display = "none";
		document.getElementById('video_file').style.display = "none";
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
	   //效验素材名称是否重复   
	   if (checkImage()==true)
		{
		   return false;
		}
        var ss= new Array();
       var ww= new Array();
       ss = uploadImage.split(",");
	   var resourceName="";
	   var tag="1";
	   if(tag==1){
	           document.getElementById("saveForm").submit();
	           document.getElementById("addMaterialButton").disabled="true";
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
		   if($$("textMeta.bkgColor").value.substring(0,1)=='#'
		&& $$("textMeta.bkgColor").value.substring(1).length==6
		&& isNumber($$("textMeta.bkgColor").value.substring(1))){
			
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
<form action="<%=path %>/page/meterial/saveUiMaterial.do" method="post" id="saveForm">	
<div class="path">首页 >> 素材管理 >> UI素材维护</div>
<div class="searchContent" >
<div class="listDetail">
<div style="position: relative">	
<table>
		    	
		        
		        
		        <tr>
		        	<td>
					   <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px; display: ;" id ="div_image" >
		                      <tr class="title" ><td colspan="2">UI素材</td></tr>		            
		                      <tr>		            	
		                         <td width="60px" align="left"><span class="required"></span> 选择文件：</td>		                         		                         
		                         <input id="imageMetaName" name="imageMetaName" type="hidden" />
		                         <td>
			            	        <input id="file_id" name="upload" type="file" class="e_input" />
							        <input id="backgroundImage" name="" value="" type="hidden"  />
							        <input id="localFilePath" name="localFilePath"  type="hidden" />
		                         </td>
		                        
		                     </tr>
		               </table>
		               <div id="image_file">
					         
					   </div>
		          
		        
		          
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