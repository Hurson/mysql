/** 宽度 */
var width = 0;
/** 高度 */
var height = 0;

function preview(backgroundPath,coordinate,widthHeight){

	/**为页面预览区域赋值*/
	//alert("come");
	var size = widthHeight.split('*');
	width = size[0];
	height = size[1];
	var coor = coordinate.split('*');
	$("#pImage").attr("width",426).attr("height",240);
	$("#pImage").attr("src",getContextPath()+"/"+backgroundPath);
	$("#mImage").attr("width",width).attr("height",height);
	$("#mImage,#video").css({
		width:width+"px",
		height:height+"px",
		position:'absolute',
		left: coor[0]+"px", 
		top: coor[1]+"px" 
	});
	$("#video").hide();
	
	$("#text").css({
		position:'absolute',
		width:width+"px",
		height:height+"px",
		left:coor[0]+"px",
		top:coor[1]+"px",
		'z-index':1
	});
}

function previewMaterial(previewValue,pollIndex){
	
	var coordinateStr = previewValue.split("_")[6];	
	var coordinates = coordinateStr.replace(/(^\s+)|(\s+$)/g,"").split(",");
	var coordinate;
    if(pollIndex <= coordinates.length){
    	coordinate = coordinates[pollIndex-1].split('*');
    }else{
    	coordinate = coordinates[0].split('*');
    }
	
	var backgroundPath = previewValue.split("_")[5];
	
	var widthHeight = previewValue.split("_")[7];
	
	/**为页面预览区域赋值*/
	//alert("come");
	var size = widthHeight.split('*');
	width = size[0];
	height = size[1];
	//var coor = coordinateStr.split('*');
	$("#pImage").attr("width",426).attr("height",240);
	$("#pImage").attr("src",getContextPath()+"/"+backgroundPath);
	$("#mImage").attr("width",width).attr("height",height);
	$("#mImage,#video").css({
		width:width+"px",
		height:height+"px",
		position:'absolute',
		left: coordinate[0]+"px", 
		top: coordinate[1]+"px" 
	});
	$("#video").hide();
	
	$("#text").css({
		position:'absolute',
		width:width+"px",
		height:height+"px",
		left:coordinate[0]+"px",
		top:coordinate[1]+"px",
		'z-index':1
	});
}

/**
 * 显示素材
 * @param id
 * @return
 */
function viewMaterial(id){
	var path;
	var metaId=id;
	//alert(metaId);
	$.ajax({   
	       url:'getAreaResourcePath.do',       
	       type: 'POST',    
	       dataType: 'text',   
	       data: {
	    	   metaId:metaId
			},                   
	       timeout: 1000000000,                              
	       error: function(){                      
	    		alert("系统错误，请联系管理员！");
	       },    
	       success: function(result){ 
	    	  /// alert(result);
	    	   if(result == '-1'){
	    		  alert("系统错误，请联系管理员！");
	    	   }else{

	    			 
	    			  path=eval(result);
	    			  //alert(path);
	    			   //showImage(path);
	    			   //var resourceValue=metaId;
	    			   //alert(resourcevalue);
	    			       		    	   
	    		    	//window.open('preview.do?perviewvalue='+perviewvalue,'','location=no,menubar=no,resizable=no,status=no,scrollbars=no,height=250px,width=430px');
	    		    	//window.open('preview.do?previewValue='+previewValue+'&resourceValue='+resourceValue,'','location=no,menubar=no,resizable=no,status=no,scrollbars=no,height=250px,width=430px');
	    		    	//window.open(ss,'','location=no,menubar=no,resizable=no,status=no,scrollbars=no,height=400px,width=500px');
	    			  $.ajax({   
	    			       url:'getMaterialJsonById.do',       
	    			       type: 'POST',    
	    			       dataType: 'text',   
	    			       data: {
	    			    	   id:id
	    					},                   
	    			       timeout: 1000000000,                              
	    			       error: function(){                      
	    			    		alert("系统错误，请联系管理员！");
	    			       },    
	    			       success: function(responseText){ 
	    			    	   if(responseText == '-1'){
	    			    		  alert("系统错误，请联系管理员！");
	    			    	   }else{
	    			    		     var material = eval("("+responseText+")");

	    				    		 if(material!=null && material!=''){
	    				    			 //alert(material.type);
	    				    			
	    				    			 switch(material.type){
	    					 				case 0:showImage(path);break;
	    					 				case 1:showVideo(path);break;
	    					 				case 2:showText(material);break;
	    					 				case 4:showZip(path);break;
	    					 			}
	    				    		 }
	    			    	   }
	    				   }  
	    			   });
	    	   }
		   }  
	   });
	
	
}

/**显示图片*/
function showImage(path){
	$("#video").hide();
	$("#text").hide();
	$("#mImage").attr("src",path);
	$("#mImage").show();
}

/**显示zip内预览图*/
function showZip(path){

	$("#video").hide();
	$("#text").hide();
	$("#mImage").attr("src",path);
	$("#mImage").show();
}




/**显示视频 */
function showVideo(path){
	//var videopath="http://192.168.2.224/advertres/temp/material/video_1408606454984.ts";
	//var videopath="ftp://root:111111@192.168.2.224:21/root/advertres/temp/materialReal/video_1408584436125.ts";
	//var videopath="ftp://root:111111@192.168.2.224:21/advertres/temp/materialReal/video_1408584436125.ts";
	var videopath=path;
	//alert(videopath);
	$("#mImage").hide();
	$("#text").hide();
	//$("#video").show();
	$("#vlc").css({
		width:width+"px",
		height:height+"px"
	});
	var vlc=document.getElementById("vlc");
	//var n=vlc.playlist.itemCount;
	//alert(n);
	vlc.playlist.stop();
	vlc.playlist.clear();
	 // 添加播放地址
	 vlc.playlist.add(videopath);
	 //var m=vlc.playlist.itemCount;okm
	 // 播放
	vlc.playlist.play();
	 //vlc.clear();
	 // 添加播放地址
	 //vlc.add(path);
	 // 播放
	 //vlc.play();
	$("#video").show();
	//$("#video").show();
}

/**显示文字*/
function showText(material){
	$("#video").hide();
	$("#mImage").hide();
	$("#textContent").css({
		'color':material.text.fontColor,
		'font-size':material.text.fontSize+"px"
	});
	if(material.text.rollSpeed!=''){
		$("#textContent").attr("scrollamount",material.text.rollSpeed);
	}
	//material"positionVertexCoordinates":"1*1","positionWidthHeight"
	var str1="12:120";
	var coordinates = material.text.positionVertexCoordinates.split("*");//positionVertexCoordinates
	var size = material.text.positionWidthHeight.split("*");//material.positionWidthHeight.split(":");
	//alert(material.text.positionVertexCoordinates);
	//alert(material.text.positionWidthHeight);
	//alert(coordinates[0]);
	var left = coordinates[0]/1280*426+"px";
	var bottom = coordinates[1]/720*240+"px";					 				  
	var width = size[0]/1280*426+"px";
	var height = size[1]/720*240+"px";
	
	$('#text').css('width',width);
	$('#text').css('height',height);
	 $('#text').css('left',left);
	$('#text').css('top',bottom);
	
	$("#text").show();
	$("#textContent").html(material.text.content);
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
