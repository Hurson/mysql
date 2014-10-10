/** 订单策略绑定的素材 */
var ployMaterials = null;
/** 订单精准绑定的素材 */
var materials = null;
/**预览图x坐标*/
var preX=670;
/**预览图Y坐标*/
var preY=267;
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

var selPloy = null;// 绑定策略对象

var precises = new Array();// 精准数组



var selMaterial = new Array();// 绑定素材数组

/**
 * 初始化记录
 * @param positionJson  广告位对象
 * @param ployJson  策略对象
 * @param plMJson   订单策略对应的素材对象
 * @param prMJson   订单精准对应的素材对象
 * @return
 */
function init(positionJson,ployJson,plMJson,prMJson){
	/**为绑定广告位赋值*/
	selPosition = eval(positionJson);
	/**为绑定策略赋值*/
	selPloy = eval(ployJson);
	/**为策略管理精准赋值*/
	if(selPloy.precises!=null&&selPloy.precises!=''){
		precises = selPloy.precises;
	}
	/**为绑定的素材赋值*/
	selMaterial[0] = eval(plMJson);
	var temp = eval(prMJson);
	for(var i = 1;i<=temp.length;i++){
		selMaterial[i]=temp[i-1];
	}
	
	
	/**为页面订单参数配置区域显示数据赋值*/
	if(selMaterial.length>1){
		$("#preciseli").show();
		var dh = Math.round(220/selMaterial.length);
		$("#selPloy").css("height",dh+"px");
		$("#selPrecise").css("height",dh*(selMaterial.length-1)+"px");
	}else{
		$("#preciseli").hide();
		$("#selPloy").css("height","250px");
		$("#selPrecise").css("height","0px");
	}
	var pm = "";
	for(var i = 0;i<selMaterial[0].material.length;i++){
		if(i>0){
			pm += ",";
		}
		if((selPosition.isAdd==0||selPosition.isLoop==1)&&selPosition.isBoot==0&&selPosition.isInstream==0){
			if(i==0){
				pm += "<a href='javascript:showPloyMaterial();'>";
			}
			pm += selMaterial[0].material[i].name;
		}else{
			pm +="<a href='javascript:showMaterialPre(\""
				+selMaterial[0].material[i].path+"\",\""
				+selMaterial[0].material[i].content+"\","
				+selMaterial[0].material[i].type+")'>"
				+selMaterial[0].material[i].name+"</a>";

		}
		if(selPosition.isInstream!=0){
			pm +="[插播位置："+selMaterial[0].material[i].instream+"]";
		}else if(selPosition.isLoop==1){
			pm += "[轮询序号："+selMaterial[0].material[i].loopNo+"]"
		}
		if((selPosition.isAdd==0||selPosition.isLoop==1)&&selPosition.isBoot==0&&selPosition.isInstream==0&&i==selMaterial[0].material.length-1){
			pm += "</a>";
		}
	}
	$("#mp0").html(pm);
	
	var prm = "";
	for(var j = 1;j<selMaterial.length;j++){
		if(j>1){
			prm += "<br/><br/>";
		}
		prm +="<a href='javascript:showPreciseInfo("+(j-1)+");'>"+selMaterial[j].preciseName+"</a><br>&nbsp;&nbsp;&nbsp;&nbsp;";
		
		prm +="<span id='mp"+selMaterial[j].pId+"'>";
		for(var k = 0;k<selMaterial[j].material.length;k++){
			if(k>0){
				prm += ",";
			}
			if((selPosition.isAdd==0||selPosition.isLoop==1)&&selPosition.isInstream==0){
				if(k==0){
					prm += "<a href='javascript:showPreciseMaterial("+j+");'>";
				}
				prm += selMaterial[j].material[k].name;
			}else{
				prm += "<a href='javascript:showMaterialPre(\""
					+selMaterial[j].material[k].path+"\",\""
					+selMaterial[j].material[k].content+"\","
					+selMaterial[j].material[k].type+")'>"
					+selMaterial[j].material[k].name+"</a>";
			}
			if(selPosition.isInstream!=0){
				prm +="[插播位置："+selMaterial[j].material[k].instream+"]";
			}else if(selPosition.isLoop==1){
				prm += "[轮询序号："+selMaterial[j].material[k].loopNo+"]"
			}
			if((selPosition.isAdd==0||selPosition.isLoop==1)&&selPosition.isInstream==0&&i==selMaterial[j].material.length-1){
				prm += "</a>";
			}
		}
		prm +="</span>";
	}
	
	$("#selPrecise").html(prm);
	
	/**为页面预览区域赋值*/
	var size = selPosition.positionSize.split('*');
	width = size[0];
	height = size[1];
	var coordinate = selPosition.coordinate.split('*');
	x = parseInt(preX)+parseInt(coordinate[0]);
	y = parseInt(preY)+parseInt(coordinate[1]);
	$("#pImage").attr("width",getHDWidth(selPosition.isHD)).attr("height",getHDHeight(selPosition.isHD));
	$("#pImage").attr("src",getContextPath()+"/"+selPosition.backgroundPath);
	$("#mImage").attr("width",width).attr("height",height);
	$("#mImage,#video").css({
		width:width+"px",
		height:height+"px",
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
		'z-index':1000
	});
	$("#textContent").css({
		'color':selPloy.fontColor,
		'font-size':selPloy.fontSize+"px"
	});
	if(selPloy.rollSpeed!=''){
		$("#textContent").attr("scrollamount",selPloy.rollSpeed);
	}
	showPloyMaterial();
}



/**预览策略绑定素材*/
function showPloyMaterial(){
	if(selMaterial[0].material!=null&&selMaterial[0].material.length>0){
		if(selPosition.isLoop==1&&selPosition.isBoot==0&&selPosition.isInstream==0){
			loopNumber = 0;
			changeMaterialPre(0);
			clearInterval(timer);
			if(selMaterial[0].material.length>1){
				timer = setInterval("changeMaterialPre(0)",1000);
			}
		}else{
			switch(selMaterial[0].material[0].type){
				case 0:showImage(selMaterial[0].material[0].path);break;
				case 1:showVideo(selMaterial[0].material[0].path);break;
				case 2:showText(selMaterial[0].material[0].content);break;
			}
		}	
	}else{
		clearInterval(timer);
		$("#mImage,#video,#text").hide();
	}
}

function changeMaterialPre(i){
	var ms = selMaterial[i].material;
	if(ms!=null&&ms.length>0){
		showMaterialPre(ms[loopNumber].path,ms[loopNumber].content,ms[loopNumber].type);
		if(loopNumber<ms.length-1){
			loopNumber++;
		}else{
			loopNumber=0;
		}	
	}
}


/**预览精准绑定素材*/
function showPreciseMaterial(i){
	if(selMaterial[i].material!=null&&selMaterial[i].material.length>0){
		if(selPosition.isLoop==1&&selPosition.isInstream==0){
			var ms = selMaterial[i].material;
			loopNumber = 0;
			changeMaterialPre(i);
			clearInterval(timer);
			if(selMaterial[i].material.length>1){
				timer = setInterval("changeMaterialPre("+i+")",1000);
			}
		 
		}else{
			var ms = selMaterial[i].material;
			for(var j=0;j<ms.length;j++){
				switch(ms[j].type){
					case 0:showImage(ms[j].path);break;
					case 1:showVideo(ms[j].path);break;
					case 2:showText(ms[j].content);break;
				}
			}
		
		}
	}
}


function changeMaterial(i){
	if(i==null){
		showMaterial(ployMaterials[loopNumber].path,ployMaterials[loopNumber].content,ployMaterials[loopNumber].type);
		if(loopNumber<ployMaterials.length-1){
			loopNumber++;
		}else{
			loopNumber=0;
		}
	}else{
		var ms = materials[i].material;
		showMaterial(ms[loopNumber].path,ms[loopNumber].content,ms[loopNumber].type);
		if(loopNumber<ms.length-1){
			loopNumber++;
		}else{
			loopNumber=0;
		}
	}
	
}

/**显示审核意见
function showOpinion(){
	$("#opinion").show();
	$("#bg").show();
	$("#popIframe").show();
}*/

/**隐藏审核意见
function hideOpinion(){
	$("#opinion").hide();
	$("#bg").hide();
	$("#popIframe").hide();
}*/