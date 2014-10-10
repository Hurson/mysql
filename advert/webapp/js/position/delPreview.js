/** 宽度 */
var width = 0;
/** 高度 */
var height = 0;

function preview(backgroundPath,coordinate,widthHeight){
	//alert(111);
	/**为页面预览区域赋值*/
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

function showMaterial(resourceId,adId){
	$("#preview").show();
	$.ajax({   
	       url:'getAdvertPositionJson.do',       
	       type: 'POST',    
	       dataType: 'text',   
	       data: {
				id:adId
		   },                   
	       timeout: 1000000000,                              
	       error: function(){                      
	    		alert("系统错误，请联系管理员！");
	       },    
	       success: function(result){ 
	    	   var json = eval('(' + result + ')');
	    	   if(json.result=='true'){
	    		   preview(json.backgroundPath,json.coordinate,json.widthHeight);
	    		   viewDelMaterial(resourceId);
	    	   }else{
	    		   alert("系统错误，请联系管理员！");
	    	   }	    	   
		   }  
	}); 
}

/**
 * 显示素材
 * @param id
 * @return
 */
function viewDelMaterial(resourceId){
	
	$.ajax({   
	       url:'getMaterialJsonById.do',       
	       type: 'POST',    
	       dataType: 'text',   
	       data: {
	    	   id:resourceId
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
		    			 switch(material.type){
			 				case 0:showImage(material.path);break;
			 				case 1:showVideo(material.path);break;
			 				case 2:showText(material);break;
			 			}
		    		 }
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
/**显示视频 */
function showVideo(path){
	$("#mImage").hide();
	$("#text").hide();
	$("#vlc").css({
		width:width+"px",
		height:height+"px"
	});
	var vlc=document.getElementById("vlc");
	vlc.playlist.clear();
	 // 添加播放地址
	 vlc.playlist.add(path);
	 // 播放
	 vlc.playlist.play();
	 $("#video").show();
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
