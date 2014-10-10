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
var queryMaterials = new Array();// 按条件查询素材数组

/**
 * 设置广告位相关参数和订单绑定的素材数据
 * */
//function init(coordinate,hd,loop,size,ployJson,ployMaterialJson,materialJson,fSize,rSpeed,fColor){
//	isLoop = loop;
//	var s = size.split('*');
//	width = s[0];
//	height = s[1];
//	
//	fontSize=fSize;
//	rollSpeed=rSpeed;
//	fontColor=fColor;
//
//	var c = coordinate.split('*');
//	x = c[0];
//	y = "-"+Math.round(getHDWidth(hd)-c[1]);
//	
//	/**为绑定策略赋值*/
//	selPloy = eval(ployJson);
//	/**为策略管理精准赋值*/
//	if(selPloy.precises!=null&&selPloy.precises!=''){
//		precises = selPloy.precises;
//	}
//	
//	ployMaterials = eval(ployMaterialJson);
//	materials = eval(materialJson);
//	if(materials!=null&&materials.length>1){
//		$("#preciseli").show();
//		var dh = Math.round(220/materials.length);
//		$("#selPloy").css("height",dh+"px");
//		$("#selPrecise").css("height",dh*(materials.length-1)+"px");
//	}else{
//		$("#preciseli").hide();
//		$("#selPloy").css("height","250px");
//		$("#selPrecise").css("height","0px");
//	}
//	$("#pImage").attr("width",getHDWidth(hd)).attr("height",getHDHeight(hd));
//	$("#mImage").attr("width",width).attr("height",height);
//	$("#mImage,#video").css({
//		position:'relative',
//		left: x+"px", 
//		top: y+"px" 
//		
//	});
//	$("#text").css({
//		position:'relative',
//		width:width+"px",
//		height:height+"px",
//		left:x+"px",
//		top:y+"px",
//		'z-index':1000
//	});
//	$("#textContent").css({
//		'color':fontColor,
//		'font-size':fontSize+"px"
//	});
//	if(rollSpeed!=''){
//		$("#textContent").attr("scrollamount",rollSpeed);
//	}
//	showPloyMaterial();
//}

function init22(orderJson,positionJson,ployJson,plMJson,prMJson){
	/**为订单对象赋值*/
	order = eval(orderJson);	
	/**为绑定合同id赋值*/
	selContractId = order.contractId;
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
	/**初始化查询素材数组*/
	queryMaterials[0] = {"pId":0,"material":new Array()};
	for(var i = 1;i<=temp.length;i++){
		selMaterial[i]=temp[i-1];
		queryMaterials[i] = {"pId":temp[i-1].pId,"material":new Array()};
	}
	
	/**为页面显示数据赋值*/
	$("#orderNo").html(order.orderNo);
	$("#contract").val(order.contractCode);
	$("#position").val(order.positionName);
	$("#ployName").val(order.ployName);
	var start = order.startTime.replace(" 00:00:00","");
	$("#startDate").val(start);
	var end = order.endTime.replace(" 00:00:00","");
	$("#endDate").val(end);
	$("#desc").val(order.description);
	$("#selPloyName").html("<a href='javascript:showPloyInfo();'>"+order.ployName+"</a>");
	
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
		if((selPosition.isAdd==0||selPosition.isLoop==1)&&selPosition.isInstream==0){
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
		if((selPosition.isAdd==0||selPosition.isLoop==1)&&selPosition.isInstream==0&&i==selMaterial[0].material.length-1){
			pm += "</a>";
		}
	}
	$("#mp0").html(pm);
	
	var prm = "";
	for(var j = 1;j<selMaterial.length;j++){
		if(j>1){
			prm += "<br/><br/>";
		}
		prm +="<a href='javascript:showPreciseInfo("+(j-1)+");'>"+selMaterial[j].preciseName+"</a>";
		
		//prm +="<a style='diplay:block;float:right;margin-left:5px;' href='javascript:fillMaterialInfo("+selMaterial[j].pId+",0)'>编辑绑定</a>";
		//prm +="<a style='diplay:block;float:right' href='javascript:fillMaterialInfo("+selMaterial[j].pId+",1)'>绑定素材</a><br>&nbsp;&nbsp;&nbsp;&nbsp;";
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
	
	fillMaterialTitle();
	//aheadTime = time;
}

function showMaterialPre(path,content,type){
	if(type!=null){
		switch(type){
		case 0:showImage(path);break;
		case 1:showVideo(path);break;
		case 2:showText(content);break;
		}
	}
}

/**
 * 填充素材选择列表标题
 */
function fillMaterialTitle(){
	var str = "<td class='dot'></td><td>资产名称</td><td>资产类型</td><td>所属合同号</td>";
	str += "<td>所属广告商</td><td>所属广告位</td><td>开始时间</td><td>结束时间</td>";
	if(selPosition.isInstream!=0){
		str += "<td>插播位置</td>";
	}else if(selPosition.isLoop==1){
		str += "<td>轮询序号</td>";
	}
	$("#materialTitle").html(str);
}

/**预览策略绑定素材*/
function showPloyMaterial(){
	if(selMaterial[0].material!=null&&selMaterial[0].material.length>0){
		if(selPosition.isLoop==1&&selPosition.isInstream==0){
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

//function showPloyMaterial(){	
//	if(isLoop==1){
//		loopNumber = 0;
//		changeMaterial(0);
//		clearInterval(timer);
//		timer = setInterval("changeMaterial(null)",1000);
//	}else{
//		switch(ployMaterials[0].type){
//		case 0:showImage(ployMaterials[0].path);break;
//		case 1:showVideo(ployMaterials[0].path);break;
//		case 2:showText(ployMaterials[0].content);break;
//		}
//	}
//		
//}
function showPreciseMaterial(preciseId){
	for(var i = 0;i<materials.length;i++){
		if(materials[i].pId==preciseId){
			if(isLoop==1){
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
function showMaterial(path,content,type){
	switch(type){
	case 0:showImage(path);break;
	case 1:showVideo(path);break;
	case 2:showText(content);break;
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
function showImage(path){
	$("#mImage").attr("src",getContextPath()+"/"+path);
	$("#mImage").show();
	$("#video").hide();
}

function showVideo(path){
	$("#video").innerHTML='<embed width="'+width+'" height="'+height+'" version="VideoLAN.VLCPlugin.2" pluginspage="http://www.videolan.org" type="application/x-vlc-plugin" src="'+getContextPath()+"/"+path+'"/>';
	$("#video").show();
	$("#mImage").hide();
}

function showText(content){
	$("#text").show();
	$("#textContent").html(content);
}

/**显示审核意见*/
function showOpinion(){
	$("#opinion").show();
	$("#bg").show();
	$("#popIframe").show();
}

/**隐藏审核意见*/
function hideOpinion(){
	$("#opinion").hide();
	$("#bg").hide();
	$("#popIframe").hide();
}