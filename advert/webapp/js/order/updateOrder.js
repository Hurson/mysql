var order = null;
var contracts = new Array();// 合同数组
var queryContracts = new Array();// 按条件查询合同数组
var selContractId = -1;// 绑定合同id
var selContractCode = '';//绑定合同合同号

var positions = new Array();// 广告位数组
var queryPositions = new Array();// 按条件查询广告位数组
var selPosition = null;// 绑定广告位对象

var ploys = new Array();// 策略数组
var queryPloys = new Array();// 按条件查询策略数组
var selPloy = null;// 绑定策略对象

var precises = new Array();// 精准数组

var materials = new Array();// 素材数组
var queryMaterials = new Array();// 按条件查询素材数组
var selMaterial = new Array();// 绑定素材数组


var selMFlag = 0;// 0-订单绑定素材，精准id-精准绑定素材
var selMOpFlag = 0;// 0:编辑绑定，对已绑定的素材进行编辑，绑定后清空原有的绑定内容；1-绑定素材，每次绑定后会与原有绑定的内容进行叠加

var op = 0;// 提交表单计数器，防止重复提交表单

var x = 0;
var y = 0;
var width=0;
var height=0;
var loopNumber = 0;
var timer = null;
var videoFlag=0;//0-页面不包含视频，1-页面包含视频

function init(contractId,contractJson,positionJson,ployJson,plMJson,prMJson,time){	
	/**为绑定合同id赋值*/
	selContractId = contractId;
	/**为合同数组赋值*/
	contracts = eval(contractJson);
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
		prm +="<a href='javascript:showPreciseInfo("+(j-1)+");'>"+selMaterial[j].preciseName+"</a>";
		
		prm +="<a style='diplay:block;float:right;margin-left:5px;' href='javascript:fillMaterialInfo("+selMaterial[j].pId+",0)'>编辑绑定</a>";
		prm +="<a style='diplay:block;float:right' href='javascript:fillMaterialInfo("+selMaterial[j].pId+",1)'>绑定素材</a><br>&nbsp;&nbsp;&nbsp;&nbsp;";
		prm +="<span id='mp"+selMaterial[j].pId+"'>";
		for(var k = 0;k<selMaterial[j].material.length;k++){
			if(k>0){
				prm += ",";
			}
			if((selPosition.isAdd==0||selPosition.isLoop==1)&&selPosition.isBoot==0&&selPosition.isInstream==0){
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
			if((selPosition.isAdd==0||selPosition.isLoop==1)&&selPosition.isBoot==0&&selPosition.isInstream==0&&i==selMaterial[j].material.length-1){
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
		'z-index':1
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
	aheadTime = time;
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

/**预览精准绑定素材*/
function showPreciseMaterial(i){
	if(selMaterial[i].material!=null&&selMaterial[i].material.length>0){
		if(selPosition.isLoop==1&&selPosition.isBoot==0&&selPosition.isInstream==0){
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
/**
 * 选择合同
 */
function selectContract(){
	var contractIds = $("input[name='contractId']");
	var contractCodes = $("input[name='contractCode']");
	var contractNames = $("input[name='contractName']");
	for(var i = 0;i<contractIds.length;i++){
		if(contractIds[i].checked){
			if(selContractId!=contractIds[i].value){
				selContractId = contractIds[i].value;
				selContractCode = contractCodes[i].value;
				setPosition(0);
			}else{
				setPosition(1);
			}
			$("#contract").val(contractNames[i].value);
			$("#contractId").val(contractIds[i].value);
			break;
		}
	}
	closeContract();
}

/**
 * 显示广告位选择层
 */
function showPosition(){
	if(queryPositions==null||queryPositions.length==0){
		setPosition(1);
	}
	if(selContractId==-1){
		alert("请选择合同");
		return;
	}
	queryPositions = positions;
	fillPositionInfo();
	var position = $("input[name='positionId']");
	if(selPosition!=null){
		for(var i = 0;i<position.length;i++){
			if(position[i].value==selPosition.id){
				position[i].checked="checked";
			}else{
				position[i].checked="";
			}
		}
	}else{
		selPosition = queryPositions[0];
		position[0].checked="checked";
	}
	showSelectDiv('positionDiv');
}

/**
 * 设置广告位
 */
function setPosition(pFlag){
	$.ajax({
		type:"post",
		url: 'getPosition.do',
		success:function(responseText){
			if(responseText!='-1'){
				positions = eval(responseText);
				queryPositions = eval(responseText);
				fillPositionInfo();
				if(pFlag==0){
					emptyInfo();
				}
			}else{
				alert("服务器异常，广告位加载失败，请稍后重试！");				
			}
		},
		dataType:'text',
		data:{contractId:selContractId},
		error:function(){
			alert("服务器异常，广告位加载失败，请稍后重试！");
		}
	});
}
/**
 * 按条件筛选广告位
 */
function queryPosition(){
	queryPositions = new Array();
	var pName = $("#pName").val();
	var pType = $("#pType").val();
	var pMode = $("#pMode").val();

	for(var i = 0;i<positions.length;i++){
		if($.trim(pName).length>=1){
			var poName = positions[i].name;
			if(poName.indexOf(pName)==-1){
				continue;
			}
		}
		if(pType!=-1){
			var poType = positions[i].typeId
			if(poType!=pType){
				continue;
			}
		}
		if(pMode!=-1){
			var poMode = positions[i].mode;
			if(poMode!=pMode){
				continue;
			}
		}
		
		queryPositions[queryPositions.length] = positions[i];
	}
	fillPositionInfo();
}
/**
 * 填充广告位记录内容
 */
function fillPositionInfo(){
	var str = "";
	for(var i =0;i<queryPositions.length;i++){
		str +="<tr><td><input type='radio' name='positionId' value='";
		str +=queryPositions[i].id;
		if(selPosition!=null){
			if(queryPositions[i].id==selPosition.id){
				str+="' checked='checked";
			}
		}
		str +="'/></td><td>";
		str +=queryPositions[i].name;
		str +="</td><td>";
		str += queryPositions[i].typeName;
		str +="</td><td>";
		str += getIsHDDesc(queryPositions[i].isHD);
		str +="</td><td>";
		str += getAddDesc(queryPositions[i].isAdd);
		str +="</td><td>";
		str += getLoopDesc(queryPositions[i].isLoop);
		str +="</td><td>";
		if(queryPositions[i].materialNumber==0){
			str +="-";
		}else{
			str += queryPositions[i].materialNumber;
		}
		str +="</td><td>";
		str += getModeDesc(queryPositions[i].mode);
		str +="</td>";
		str +="<td>"+queryPositions[i].validStart.replace(" 00:00:00","")+"</td>";
		str +="<td>"+queryPositions[i].validEnd.replace(" 00:00:00","")+"</td>";
		str +="</tr>";
	}
	$("#positionInfo").html(str);
}
/**
 * 选择广告位
 */
function selectPosition(){
	var positionIds = $("input[name='positionId']");
	for(var i = 0;i<positionIds.length;i++){
		if(positionIds[i].checked){
			if(selPosition==null||selPosition.id!=queryPositions[i].id){
				selPosition = queryPositions[i];
				$("#position").val(selPosition.name);
				$("#positionId").val(positionIds[i].value);
				validMinDate = selPosition.validStart;
				validMaxDate = selPosition.validEnd;
				emptyExceptPosition();
				fillMaterialTitle();
			}
			break;
		}
	}
	closePosition();
	setPloy();
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
		top: y+"px" ,
		'z-index':1
		
	});
}

/**
 * 显示策略选择内容
 */
function showPloy(){
	if(queryPloys==null||queryPloys.length==0){
		setPloy();
	}
	if(selContractId==-1){
		alert('请选择合同');
		return;
	}else if(selPosition==null){
		alert('请选择广告位');
		return;
	}
	queryPloys = ploys;
	fillPloyInfo();
	var ploy = $("input[name='ployId']");
	if(selPloy!=null){
		for(var i = 0;i<ploy.length;i++){
			if(ploy[i].value==selPloy.id){
				ploy[i].checked="checked";
			}else{
				ploy[i].checked="";
			}
		}
	}
	showSelectDiv("ployDiv");
}
/**
 * 设置策略
 */
function setPloy(){
	$.ajax({
		type:"post",
		url: 'getPloy.do',
		success:function(responseText){
			if(responseText!='-1'){
				ploys = eval(responseText);
				queryPloys = eval(responseText);
				fillPloyInfo();
			}else{
				alert("服务器异常，策略加载失败，请稍后重试！");	
			}
		},
		dataType:'text',
		data:{positionId:selPosition.id,contractId:selContractId},
		error:function(){
			alert("服务器异常，策略加载失败，请稍后重试！");
		}
	});
}
/**
 * 填充策略内容
 */
function fillPloyInfo(){
	var str = "";
	for(var i =0;i<queryPloys.length;i++){
		str +="<tr><td><input type='radio' name='ployId' value='";
		str +=queryPloys[i].id;
		if(selPloy!=null){
			if(queryPloys[i].id==selPloy.id){
				str +="' checked='checked";
			}
		}else if(i==0){
			str +="' checked='checked";
		}
		str +="'/></td><td>";
		str +=queryPloys[i].name;
		str +="</td><td>";
		str += queryPloys[i].startTime;
		str +="</td><td>";
		str += queryPloys[i].endTime;
		str +="</td>";
		str +=fillAreaChannelInfo(queryPloys[i].areas);
		str +="</tr>";			
	}
	$("#ployInfo").html(str);
}

/**
 * 按条件筛选策略
 */
function queryPloy(){
	queryPloys = new Array();
	var pName = $("#plName").val();

	for(var i = 0;i<ploys.length;i++){
		if($.trim(pName).length>=1){
			var plName = ploys[i].name;
			if(plName.indexOf(pName)==-1){
				continue;
			}
		}		
		queryPloys[queryPloys.length] = ploys[i];
	}
	fillPloyInfo();
}

/**
 * 选择绑定的策略
 */
function selectPloy(){
	var ployIds = $("input[name='ployId']");
	var str='';
	for(var i = 0;i<ployIds.length;i++){
		if(ployIds[i].checked){
			if(selPloy!=null){
				if(selPloy.id!=queryPloys[i].id){
					$("#mp0").html('');
					fillPreciseInfo();
					setSelMaterialArray();
					setMaterial(null);
					selPloy = queryPloys[i];
				}
			}else{
				selPloy = queryPloys[i];
				fillPreciseInfo();
				setSelMaterialArray();
			}
			
			break;
		}
	}
	var ployName = selPloy.name;
	var sPloyName = ployName;
	if(ployName.length>10){
		ployName =ployName.substring(0,10)+"...";
	}
	
	$("#ployName").val(ployName);
	ployName = "<a href='javascript:showPloyInfo();'>"+ployName+"</a>"
	$("#selPloyName").html(ployName);

	$("#ploybd").show();
	closePloy();
	
}

/**
 * 填充精准内容
 */
function fillPreciseInfo(){
	if(selPloy.precises!=null&&selPloy.precises!=''){
		$("#preciseli").show();
		precises = selPloy.precises;
		var str = "";
		for(var i = 0;i<precises.length;i++){
			str +="<a href='javascript:showPreciseInfo("+i+")'>"+precises[i].name+"</a>";
			
			str += "<a style='diplay:block;float:right;margin-left:5px;' href='javascript:fillMaterialInfo("+precises[i].id+",0)'>编辑绑定</a>";
			str += "<a style='diplay:block;float:right' href='javascript:fillMaterialInfo("+precises[i].id+",1)'>绑定素材</a>";
			str += "<br>&nbsp;&nbsp;&nbsp;&nbsp;<span id='mp"+precises[i].id+"'></span><br><br>";
		}
		var dh = Math.round(220/(1+precises.length));
		$("#selPloy").css("height",dh+"px");
		$("#selPrecise").css("height",dh*precises.length+"px");
		$("#selPrecise").html(str);
	}else{
		precises = new Array();
		$("#preciseli").hide();
		$("#selPloy").css("height","250px");
		$("#selPrecise").css("height","0px");
	}	
}



/**
 * 填充设置素材数据
 */
function setMaterial(pId){
	$.ajax({
		type:"post",
		url: 'getMaterial.do',
		success:function(responseText){
			if(responseText!='-1'){
				materials = eval(responseText);
				queryMaterials[0].material = eval(responseText);
				for(var i = 1;i<=precises.length;i++){
					queryMaterials[i] = {"pId":precises[i-1].id,"material":eval(responseText)};
				}
				if(pId!=null){
					selMFlag = pId;
					var num = getMaterialArrayIndex();
					var str = "";
					var qMaterials = queryMaterials[num].material;
					for(var i =0;i<qMaterials.length;i++){
						var isSelected = 0;
						var k = 0;
						if(selMOpFlag==1&&(selPosition.isLoop==0||selPosition.isInstream!=0)){
							for(;k<selMaterial[num].material.length;k++){
								if(qMaterials[i].id==selMaterial[num].material[k].id){
									isSelected=1;
									break;
								}
							}
						}
						str +="<tr><td><input ";
						if(selPosition.isLoop==1||selPosition.isAdd==1){
							str += "type='checkbox'";
						}else{
							str += "type='radio'";
						}
						if(isSelected==1){
							str += " checked='checked' disabled='disabled'";
						}
						str += " name='materialId' value='";
						if(isSelected==1){
							str += 0;
						}else{
							str +=qMaterials[i].id;
						}
						str +="'/></td><td>";
						str +=qMaterials[i].name;
						str +="</td><td>";
						str += getMaterialType(qMaterials[i].type);
		                                str +="</td><td>";
					
						str += selContractCode;			

						str +="</td><td>";
						str +=qMaterials[i].businessName;
						str +="</td><td>";
						str +=selPosition.name;
						str +="</td><td>";
						str +=qMaterials[i].startDate;
						str +="</td><td>";
						str +=qMaterials[i].endDate;
						str +="</td>";
						
						if(selPosition.isInstream!=0){
							str += "<td>";
							for(var n=0;n<selPosition.isInstream;n++){
								var inValue = 0;
								if(n>0){
									inValue = n+"/"+selPosition.isInstream;
								}
								str += inValue;
								str +=":<input type='checkbox' name='mInStreamNo'";
								if(isSelected==1){
									var ins = selMaterial[num].material[k].instream.split(',');
									for(var m=0;m<ins.length;m++){
										if(ins[m]==inValue){
											str += "' checked='checked ";
											break;
										}
									}
									str += "' disabled='disabled";
								}
								str +="value='"+inValue+"'/>&nbsp;&nbsp;";
							}
							str += "</td>";
						}else if(selPosition.isLoop==1){
							str += "<td><input type='text' name='mLoopNo' style='width:30px' size='1'/></td>";
						}
						
						str +="</tr>";	
							
					}
					$("#materialInfo").html(str);
				}
			}else{
				alert("服务器异常，素材加载失败，请稍后重试！");
			}
		},
		dataType:'text',
		data:{positionId:selPosition.id,contractId:selContractId},
		error:function(){
			alert("服务器异常，素材加载失败，请稍后重试！");
		}
	});
}
/**
 * 按条件筛选素材
 */
function queryMaterial(){
	var num = getMaterialArrayIndex();
	queryMaterials[num].material = new Array();
	var mName = $("#mName").val();

	for(var i = 0;i<materials.length;i++){
		if($.trim(mName).length>=1){
			var maName = materials[i].name;
			if(maName.indexOf(mName)==-1){
				continue;
			}
		}		
		queryMaterials[num].material[queryMaterials[num].material.length] = materials[i];
	}
	fillMaterialInfo(selMFlag,selMOpFlag);
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
/**
 * 填充素材选择列表内容 pId-精准id
 */
function fillMaterialInfo(pId,flag){
	selMOpFlag = flag;
	if(selMOpFlag==1&&(queryMaterials[0].material==null||queryMaterials[0].material.length==0)){
		setMaterial(pId);
	}else{
		if(selContractId=='-1'){
			alert('请选择合同');
		}else if(selPosition==null){
			alert('请选择广告位');
		}else{
			selMFlag = pId;
			var num = getMaterialArrayIndex();
			var str = "";
			var qMaterials = queryMaterials[num].material;
			if(selMOpFlag==0){
				qMaterials = selMaterial[num].material;
			}
			for(var i =0;i<qMaterials.length;i++){
				var isSelected = 0;
				var k=0;
				if(selMOpFlag==1&&(selPosition.isLoop==0||selPosition.isInstream!=0)){
					for(;k<selMaterial[num].material.length;k++){
						if(qMaterials[i].id==selMaterial[num].material[k].id){
							isSelected=1;
							break;
						}
					}
				}
				str +="<tr><td><input ";
				if(selPosition.isLoop==1||selPosition.isAdd==1){
					str += "type='checkbox'";
				}else{
					str += "type='radio'";
				}
				if(selMOpFlag==0){
					str += " checked='checked' "
				}
				if(isSelected==1){
					str += " checked='checked' disabled='disabled'";
				}
				str += " name='materialId' value='";
				if(isSelected==1){
					str += 0;
				}else{
					str +=qMaterials[i].id;
				}
				str +="'/></td><td>";
				str +=qMaterials[i].name;
				str +="</td><td>";
				str += getMaterialType(qMaterials[i].type);
                                str +="</td><td>";
			
				str += selContractCode;			

				str +="</td><td>";
				str +=qMaterials[i].businessName;
				str +="</td><td>";
				str +=selPosition.name;
				str +="</td><td>";
				str +=qMaterials[i].startDate;
				str +="</td><td>";
				str +=qMaterials[i].endDate;
				str +="</td>";
				if(selPosition.isInstream!=0){
					str += "<td>";
					for(var n=0;n<selPosition.isInstream;n++){
						var inValue = 0;
						if(n>0){
							inValue = n+"/"+selPosition.isInstream;
						}
						str += inValue;
						str +=":<input type='checkbox' name='mInStreamNo' value='"+inValue;
						if(selMOpFlag==0){
							var ins = qMaterials[i].instream.split(',');
							for(var m=0;m<ins.length;m++){
								if(ins[m]==inValue){
									str += "' checked='checked ";
									break;
								}
							}
							
						}
						if(isSelected==1){
							var ins = selMaterial[num].material[k].instream.split(',');
							for(var m=0;m<ins.length;m++){
								if(ins[m]==inValue){
									str += "' checked='checked ";
									break;
								}
							}
							str += "' disabled='disabled";
						}
						str +="'/>&nbsp;&nbsp;";
					}
					str += "</td>";
				}else if(selPosition.isLoop==1){
					str += "<td><input type='text' name='mLoopNo' style='width:30px' size='1";
					if(selMOpFlag==0){
						str += "' value='"+qMaterials[i].loopNo;
					}
					str +="'/></td>";
				}
			
				str +="</tr>";	
					
			}
			$("#materialInfo").html(str);
		}
	}
	if(selMOpFlag==0){
		$("#queryMaterial").hide();
	}else{
		$("#queryMaterial").show();
	}
	showSelectDiv("materialDiv");

}

/**
 * 选择绑定的素材
 */
function selectMaterial(){
	var materialIds = $("input[name='materialId']");
	var mLoops = $("input[name='mLoopNo']");
	var mInstreams = $("input[name='mInStreamNo']");
	var materialName='';
	var num = getMaterialArrayIndex();	
	var temp = new Array();	
	for(var m=0;m<selMaterial[num].material.length;m++){
		temp[temp.length]=selMaterial[num].material[m];
	}
	if(num!=null){
		var ms = queryMaterials[num].material;
		if(selMOpFlag==0){
			ms = selMaterial[num].material;
			selMaterial[num].material = new Array();
		}
		if(selPosition.isAdd==0&&selPosition.isLoop==0){
			selMaterial[num].material = new Array();
		}
		for(var i = 0;i<materialIds.length;i++){
			if(materialIds[i].checked&&materialIds[i].value!='0'){					
				var selMLength = selMaterial[num].material.length;
				selMaterial[num].material[selMLength]={
					"id":ms[i].id,"content":ms[i].content,
					"path":ms[i].path,"type":ms[i].type,
					"name":ms[i].name,"businessName":ms[i].businessName,
					"description":ms[i].description,"loopNo":ms[i].loopNo,
					"instream":ms[i].instream,"startDate":ms[i].startDate,				
					"endDate":ms[i].endDate
					};
				if(selPosition.isInstream!=0){
					var j=i*parseInt(selPosition.isInstream);
					var maxJ = j+parseInt(selPosition.isInstream);
					var instreams = '';
					for(;j<maxJ;j++){
						if(mInstreams[j].checked==true){
							var mInstreamsValue = mInstreams[j].value;
							if(instreams!=''){
								instreams += ",";
							}
							instreams += mInstreamsValue;
							
						}
					}
					if(instreams==''){
						alert('请为选中的素材选择插播位置！');
						selMaterial[num].material = temp;
						return;
					}
					selMaterial[num].material[selMLength].instream = instreams;

				}else if(selPosition.isLoop==1){
					var mLoopsValue = mLoops[i].value;
					if($.trim(mLoopsValue).length==0){
						alert('请为选中的素材输入对应的轮询序号！');
						selMaterial[num].material = temp;
						return;
					}
					var reg=/^\d+$/;    
					if(!reg.test($.trim(mLoopsValue))||mLoopsValue==0){
						alert('输入的轮询序号应为从1开始的数字！');
						selMaterial[num].material = temp;
						return;
					}
					selMaterial[num].material[selMLength].loopNo = mLoopsValue;
				}
				
			}
		}
		var materialName=getSelMaterialName(num);
		$("#mp"+selMFlag).html(materialName);
		
		closeMaterial();
		if(num==0){
			showPloyMaterial();
		}
	}
}

/**
 * 获取选中素材要显示的名称及轮询序号或插播位置
 */


/**
 * 保存订单
 */
function saveOrder(){
	if(op>0){
		alert('请不要重复提交表单');
		return;
	}
	if(checkFormNotNull()&&checkOrderDate()&&checkMaterialNum()&&checkMaterialType()){	
		var orderId = $("#id").val();
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		var ployId=selPloy.id;
		$.ajax({   
		       url:'checkOrderDate.do',       
		       type: 'POST',    
		       dataType: 'text',   
		       data: {
				id:orderId,
				startDate:startDate,
				endDate:endDate,
				ployId:ployId,
				},                   
		       timeout: 1000000000,                              
		       error: function(){                      
		    		alert("系统错误，请联系管理员！");
		       },    
		       success: function(result){ 
		    	   if(result!='-1'){
			    	   if(result=='0'){
			    		  save();
			    	   }else{
			    		   alert('订单的日期范围与已有订单存在冲突，订单修改失败！');
			    	   }
		    	   }else{
			    		alert("系统错误，请联系管理员！");
		    	   }
			   }  
		   }); 
	}
}

/**
 * 提交表单，保存订单
 */
function save(){
	$("#contractId").val(selContractId);
	$("#positionId").val(selPosition.id);
	$("#ployId").val(selPloy.id);
	$("#orderType").val(selPosition.mode);
	$("#isHD").val(selPosition.isHD);
	var materialJson="[";
	for(var i = 0;i<selMaterial.length;i++){
		if(i>0){
			materialJson += ",";
		}
		materialJson += "{pId:";
		materialJson +=selMaterial[i].pId;
		materialJson +=",material:[";

		for(var j=0;j<selMaterial[i].material.length;j++){
			if(j>0){
				materialJson += ",";
			}
			materialJson +="{id:";
			materialJson +=selMaterial[i].material[j].id;
			if(selPosition.isInstream!=0){
				materialJson +=",instream:'";
				materialJson +=selMaterial[i].material[j].instream+"'";
			}else if(selPosition.isLoop==1){
				materialJson +=",loopNo:";
				materialJson +=selMaterial[i].material[j].loopNo;
			}

			materialJson +="}";
		}
		materialJson +="]}";
	}
	materialJson +="]";
	$("#materialJson").val(materialJson);
	op = 1;
	$("#updateForm").submit();
}