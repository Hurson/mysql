/** 提交表单计数器，为0时可提交表单 */
var op = 0;
/** 订单策略绑定的素材 */
var ployMaterials = null;
/** 订单精准绑定的素材 */
var materials = null;
/** 广告位x轴坐标 */
var x = 0;
/** 广告位y轴坐标 */
var y = 0;
/** 预览广告位宽度 */
var width=0;
/** 预览广告位高度 */
var height=0;
/** 广告位是否轮询 */
var isLoop=0;
/** 广告位是否为开机广告位 */
var isBoot=0;
/** 字体大小 */
var fontSize='';
/** 滚动速度 */
var rollSpeed='';
/** 文字颜色 */
var fontColor='';

/** 轮询到第几个素材 */
var loopNumber = 0;
/** 轮询定时器 */
var timer = null;

/**订单绑定策略的开始时间*/
var startTime = '';

var selPloy = null;// 绑定策略对象

var precises = new Array();// 精准数组

var videoFlag=0;//0-页面不包含视频，1-页面包含视频

/**
 * 设置广告位相关参数和订单绑定的素材数据
 * */
function init(coordinate,hd,loop,boot,size,ployJson,ployMaterialJson,materialJson,fSize,rSpeed,fColor,sTime,ahead,state){
	isLoop = loop;
	isBoot=boot;
	var s = size.split('*');
	width = s[0];
	height = s[1];
	
	fontSize=fSize;
	rollSpeed=rSpeed;
	fontColor=fColor;
	startTime = sTime;
	if(ahead!=0){
		aheadTime = parseInt(ahead)*1000;
	}else{
		aheadTime=0;
	}

	var c = coordinate.split('*');
	x = parseInt(preX)+parseInt(c[0]);
	if(state==7){
		y = parseInt(preY2)+parseInt(c[1]);
	}else{
		y = parseInt(preY1)+parseInt(c[1]);
	}
	
	/**为绑定策略赋值*/
	selPloy = eval(ployJson);
	/**为策略管理精准赋值*/
	if(selPloy.precises!=null&&selPloy.precises!=''){
		precises = selPloy.precises;
	}
	
	ployMaterials = eval(ployMaterialJson);
	materials = eval(materialJson);
	if(materials!=null&&materials.length>1){
		$("#preciseli").show();
		var dh = Math.round(220/materials.length);
		$("#selPloy").css("height",dh+"px");
		$("#selPrecise").css("height",dh*(materials.length-1)+"px");
	}else{
		$("#preciseli").hide();
		$("#selPloy").css("height","250px");
		$("#selPrecise").css("height","0px");
	}
	$("#pImage").attr("width",getHDWidth(hd)).attr("height",getHDHeight(hd));
	$("#mImage").attr("width",width).attr("height",height);
	$("#mImage,#video").css({
		position:'absolute',
		left: x+"px", 
		top: y+"px" 
		
	});
	$("#text").css({
		position:'absolute',
		width:width+"px",
		height:height+"px",
		left:x+"px",
		top:y+"px",
		'z-index':1
	});
	$("#textContent").css({
		'color':fontColor,
		'font-size':fontSize+"px"
	});
	if(rollSpeed!=''){
		$("#textContent").attr("scrollamount",rollSpeed);
	}
	var date = new Date();
	if(getRunningState()){
		date.setDate(date.getDate()-1);
	}
	
	showPloyMaterial();
}
/**
 * 检查订单当前时间是否在订单执行开始时间之前
 * 返回true-当天订单还未执行，false-当天订单已执行
 * */
function getRunningState(){
	var date = new Date();
	var nowDate = date.getFullYear()+"/"+(date.getMonth()+1)+"/"+date.getDate();
    var orderDate=Date.parse(nowDate+" "+startTime);
    var now = Date.parse(date)+aheadTime; 
	if(now < orderDate){
		return true;
	}
	return false;
}

/**
 * 预览策略绑定的素材
 * */
function showPloyMaterial(){	
	if(isLoop==1&&isBoot==0){
		loopNumber = 0;
		changeMaterial(0);
		clearInterval(timer);
		timer = setInterval("changeMaterial(null)",1000);
	}else{
		switch(ployMaterials[0].type){
		case 0:showImage(ployMaterials[0].path);break;
		case 1:showVideo(ployMaterials[0].path);break;
		case 2:showText(ployMaterials[0].content);break;
		}
	}
		
}

/**
 * 预览精准绑定的素材
 * */
function showPreciseMaterial(preciseId){
	for(var i = 0;i<materials.length;i++){
		if(materials[i].pId==preciseId){
			if(isLoop==1&&selPosition.isBoot==0){
				var ms = materials[i].material;
				loopNumber = 0;
				changeMaterial(i);
				clearInterval(timer);
				timer = setInterval("changeMaterial("+i+")",1000);
			 
			}else{
				var ms = materials[i].material;
				for(var j=0;j<ms.length;j++){
					switch(ms[j].type){
						case 0:showImage(ms[j].path);break;
						case 1:showVideo(ms[j].path);break;
						case 2:showText(ms[j].content);break;
					}
				}
			
			}
			break;
		}
		
	}
}

/**改变显示的素材*/
function changeMaterial(i){
	if(i==null){
		showMaterialPre(ployMaterials[loopNumber].path,ployMaterials[loopNumber].content,ployMaterials[loopNumber].type);
		if(loopNumber<ployMaterials.length-1){
			loopNumber++;
		}else{
			loopNumber=0;
		}
	}else{
		var ms = materials[i].material;
		showMaterialPre(ms[loopNumber].path,ms[loopNumber].content,ms[loopNumber].type);
		if(loopNumber<ms.length-1){
			loopNumber++;
		}else{
			loopNumber=0;
		}
	}
	
}


/*function reset(end){
	$("#endDate").val(end);
}*/



/**提交保存订单请求*/
function saveOrder(orderId,ployId,oldEndDate,validEnd){
	if(op>0){
		alert('请不要重复提交表单');
		return;
	}
	var end = $("#endDate").val();
	var date = new Date();
	var preDate = Date.parse(date.getFullYear()+"/"+(date.getMonth()+1)+"/"+(date.getDate()-1));
    var orderDate=Date.parse(end.replace(/-/g, "/"));
    var oldOrderDate = Date.parse(oldEndDate.replace(/-/g,"/"));
    var validDate = Date.parse(validEnd.replace(/-/g,"/"));
	if(orderDate<preDate||(orderDate==preDate&&!getRunningState())){
        alert("此订单在"+end+"正在执行或已执行完毕，结束时间需调整到"+end+"之后！");
        return;
	}
	if(orderDate>validDate){
		alert("订单结束日期超出合同范围，合同有效期截止时间为"+validEnd);
		return;
	}
	if(orderDate>oldOrderDate){
		var oEndDate = new Date(oldOrderDate);
		var sDate = oEndDate.getFullYear()+"-"+(oEndDate.getMonth()+1)+"-"+(oEndDate.getDate()+1);
		var eDate = $("#endDate").val();
		$.ajax({   
		       url:'checkOrderDate.do',       
		       type: 'POST',    
		       dataType: 'text',   
		       data: {
				id:0,
				startDate:sDate,
				endDate:eDate,
				ployId:ployId,
				},                   
		       timeout: 1000000000,                              
		       error: function(){                      
		    		alert("系统错误，请联系管理员！");
		       },    
		       success: function(result){ 
		    	   if(result!='-1'){
			    	   if(result=='0'){
			    		  save(orderId);
			    	   }else{
			    		   alert('订单的日期范围与已有订单存在冲突，订单修改失败！');
			    	   }
		    	   }else{
			    		alert("系统错误，请联系管理员！");
		    	   }
			   }  
		   }); 
	}else{
		save(orderId);
	}
}

/**保存修改内容*/
function save(orderId){
	op = 1;
	$.ajax({   
	       url:'updateOrderEndTime.do',       
	       type: 'POST',    
	       dataType: 'text',   
	       data: {
			id:orderId,
			endDate:$("#endDate").val(),
			
			},                   
	       timeout: 1000000000,                              
	       error: function(){                      
	    		alert("系统错误，请联系管理员！");
	    		op=0;
	       },    
	       success: function(result){ 
	    	   if(result=='0'){
	    		   alert('保存成功！');
	    		  window.location.href='listOrder.do';
	    	   }else if(result=="1"){
	    		   alert("订单已执行完毕，不允许修改！");
		    		  window.location.href='listOrder.do';	    		   
	    	   }else{
	    		   alert('系统错误，请联系管理员！');
	    	   }
	    	   op=0;
		   }  
	   }); 
}
