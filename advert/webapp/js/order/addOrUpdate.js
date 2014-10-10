var validMinDate = '2010-01-01';//订单开始日期
var validMaxDate = '2099-01-01';//订单结束日期
/**
 * 根据高标清编码返回相应描述
 * */
function getIsHDDesc(isHD) {
	switch (isHD) {
	case 0:
		return "标清";
	case 1:
		return "高清";
	}
}

/**
 * 根据投放模式编码返回相应描述
 * */
function getModeDesc(mode) {
	switch (mode) {
	case 0:
		return "投放式";
	case 1:
		return "请求式";
	}
}
/**
 * 根据是否叠加编码返回相应描述
 * */
function getAddDesc(add) {
	switch (add) {
	case 0:
		return "否";
	case 1:
		return "是";
	}
}

/**
 * 根据是否轮询编码返回相应描述
 * */
function getLoopDesc(loop) {
	switch (loop) {
	case 0:
		return "否";
	case 1:
		return "是";
	}
}
function getMaterialType(type){
	switch (type) {
	case 0:
		return "[图片]";
	case 1:
		return "[视频]";
	case 2:
		return "[文字]";
	case 3:
		return "[问卷]";
	case 4:
		return "[zip]";
	}
}
/**
 * 检查输入项是否符合要求
 * */
function checkFormNotNull(){
	if(selContractId==-1){
		alert("请选择合同！");
		return false;
	}
	if(selPosition==null){
		alert("请选择广告位！");
		return false;
	}
	if(selPloy==null){
		alert("请选择策略！");
		return false;
	}
	if($("#startDate").val()==''){
		alert("请选择开始日期！");
		return false;
	}
	if($("#endDate").val()==''){
		alert("请选择结束日期！");
		return false;
	}
	if($("#desc").val()!=''&&$("#desc").val().length>120){
		alert("订单描述字数在0-120字之间！");
		return false;
	}
	return true;
}

/**
 * 检查开始时间和结束时间是否符合要求
 * */
function checkOrderDate(){
	var start = $("#startDate").val();
	var end = $("#endDate").val();
	var sTime = selPloy.startTime;
	var eTime = selPloy.endTime;
	
	var vStart = selPosition.validStart;
	var vEnd = selPosition.validEnd;
	var ahead = 0;
	if(selPosition.mode==0){
		ahead =  parseInt(aheadTime)*1000;
	}
	
	var startTime=Date.parse(start.replace(/-/g, "/")+" "+sTime);
    var endTime=Date.parse(end.replace(/-/g, "/")+" "+eTime);
    var now = Date.parse(new Date())+ahead; 
    
    var startDate = Date.parse(start.replace(/-/g,"/"));
    var endDate = Date.parse(end.replace(/-/g,"/"));
    var vStartDate = Date.parse(vStart.replace(/-/g,"/"));
    var vEndDate = Date.parse(vEnd.replace(/-/g,"/"));
    
   if(endTime<startTime)
   {
       alert("订单结束时间不能小于开始时间！");
       return false;
   }
   if(startTime<now)
   {
      alert("订单开始时间与当前时间间隔小于"+aheadTime+"秒！");
      return false;
   } 
   if(startDate<vStartDate||endDate>vEndDate){
	   alert("订单日期超出合同范围，合同日期范围是"+vStart.replace(" 00:00:00","")+"~"+vEnd.replace(" 00:00:00",""));
	   return false;
   }
   return true;
}

/**
 * 检查绑定的素材个数是否符合广告位的要求
 * */
function checkMaterialNum(){
	for(var i = 0 ; i<selMaterial.length;i++){
		if(selMaterial[i].material==null||selMaterial[i].material.length==0){
			if(i==0){
				alert("请为策略绑定素材！");
				return false;
			}else{
				alert("请为精准绑定素材！")
				return false;
			}
		}
		if(selPosition.isAdd==0&&selPosition.isLoop==0&&selPosition.isInstream==0){
			if(selMaterial[i].material.length>1){
				alert("此广告位只支持绑定一个素材！");
				return false;
			}
		}
		if(selPosition.isLoop==1&&selPosition.isInstream==0){
			if(selMaterial[i].material.length!=selPosition.materialNumber){
				alert("此广告位需要绑定"+selPosition.materialNumber+"个素材！");
				return false;
			}
			if(!checkLoopMaterial(i)){
				return false;
			}
			
		}
		if(selPosition.isInstream!=0){
			var instreamStr = selMaterial[i].material[0].instream;
			for(var j=1;j<selMaterial[i].material.length;j++){
				instreamStr +=","+selMaterial[i].material[j].instream; 
			}
			var instreamArray = instreamStr.split(",");
			if(!checkInstreamMaterial(instreamArray)){
				return false;
			}
		}
	}
	
	return true;
}

/**
 * 检查绑定的素材类型是否符合广告位的要求
 * */
function checkMaterialType(){
	for(var i=0;i<selMaterial.length;i++){
		var image = selPosition.image;
		var video = selPosition.video;
		var text = selPosition.text;
		var question = selPosition.question;
		for(var j = 0;j<selMaterial[i].material.length;j++){
			switch(selMaterial[i].material[j].type){
			case 0 : image=0;break;
			case 1 : video=0;break;
			case 2 : text=0;break;
			case 3 : question=0;break;
			}
		}
		if(image!=0){
			if(i==0){
				alert('请为策略绑定图片素材');
			}else{
				alert('请为精准绑定图片素材');
			}
			return false;
		}
		if(video!=0){
			if(i==0){
				alert('请为策略绑定视频素材');
			}else{
				alert('请为精准绑定视频素材');
			}
			return false;
		}
		if(text!=0){
			if(i==0){
				alert('请为策略绑定文字素材');
			}else{
				alert('请为精准绑定文字素材');
			}
			return false;
		}
		if(question!=0){
			if(i==0){
				alert('请为策略绑定调查问卷素材');
			}else{
				alert('请为精准绑定调查问卷素材');
			}
			return false;
		}
	}
	return true;
}

/**
 * 检查轮询广告位素材是否符合要求
 * */
function checkLoopMaterial(num){
	var loopArray = [];
    var len = selMaterial[num].material.length;
    var maxLoopNo = 0;
	for(var i=0; i<len; i++){
		var temp = selMaterial[num].material[i].loopNo;
		loopArray.push(temp);
		if(temp>maxLoopNo){
			maxLoopNo = temp;
		}
	}
	var result = loopArray.distinct();
    if(len>result.length){
    	alert("绑定素材的轮询序号不能重复，请点击【编辑绑定】链接编辑绑定的素材！");
    	return false;
    }
    if(len==result.length&&maxLoopNo!=selPosition.materialNumber){
    	alert("轮询序号应从1开始连续排列，请点击【编辑绑定】链接编辑绑定的素材！");
    	return false;
    }
    return true;
}
/**
 * 检查插播广告位素材是否符合要求
 * */
function checkInstreamMaterial(instreamArray){
    var result=instreamArray.distinct();
    var len = instreamArray.length;
    if(len>result.length){
    	alert("广告位每个插播位置只允许绑定一个素材，请点击【编辑绑定】链接编辑绑定的素材！");
    	return false;
    }
    if(len==result.length&&len<selPosition.isInstream){
    	alert("广告位每个插播位置需要绑定一个素材，请点击【编辑绑定】链接编辑绑定的素材！");
    	return false;
    }
    return true;
}

Array.prototype.distinct = function(){ 
	var newArr=[],obj={}; 
	for(var i=0,len=this.length;i<len;i++){ 
		if(!obj[typeof(this[i]) + this[i]]){ 
		newArr.push(this[i]); 
		obj[typeof(this[i])+this[i]]='new'; 
		} 
	} 
	return newArr; 
};

/**
 * 根据精准匹配id获取素材在素材数组中的索引
 */
function getMaterialArrayIndex(){
	var i = 0;
	for(;i<selMaterial.length;i++){
		if(selMaterial[i].pId==selMFlag){
			return i;
		}
	}
	return null;
}

/**
 * 初始化设置绑定素材数组内容
 */
function setSelMaterialArray(){
	selMaterial = new Array();
	queryMaterials = new Array();
	selMaterial[0] = {"pId":0,"material":new Array()};
	queryMaterials[0] = {"pId":0,"material":new Array()};
	for(var i = 1;i<=precises.length;i++){
		selMaterial[i]={"pId":precises[i-1].id,"material":new Array()};
		queryMaterials[i] = {"pId":precises[i-1].id,"material":new Array()};
	}
}

/**
 * 为选择的轮询素材排序
 * */
function sortMaterialByLoopNo(num){
	var ms = selMaterial[num].material;
	for(var i = 0;i<ms.length-1;i++){
		for(var j = i+1;j<ms.length;j++){
			if(parseInt(ms[i].loopNo)>parseInt(ms[j].loopNo)){
				var temp = ms[i];
				ms[i] = ms[j];
				ms[j] = temp;
			}
		}
	}
}

/**
 * 获取选中素材要显示的名称及轮询序号或插播位置
 * */
function getSelMaterialName(num){
	var materialName='';
	if(selPosition.isLoop==1){
		sortMaterialByLoopNo(num);
	}
	for(var i = 0;i<selMaterial[num].material.length;i++){
		if(materialName!=''){
			materialName +=',';
		}
		if((selPosition.isAdd==0||selPosition.isLoop==1)&&selPosition.isBoot==0&&selPosition.isInstream==0){
			if(i==0&&selMFlag==0){
				materialName += "<a href='javascript:showPloyMaterial();'>";
			}else if(i==0){
				materialName += "<a href='javascript:showPreciseMaterial("+num+");'>"
			}
			materialName +=selMaterial[num].material[i].name;
		}else{
			materialName +=" <a href='javascript:showMaterialPre(\""
				+selMaterial[num].material[i].path+"\",\""
				+selMaterial[num].material[i].content+"\","
				+selMaterial[num].material[i].type+")'>"
				+selMaterial[num].material[i].name+"</a>";
		}
		if(selPosition.isInstream!=0){
			materialName += "[插播位置："+selMaterial[num].material[i].instream+"]";	
		}else if(selPosition.isLoop==1){
			materialName += "[轮询序号："+selMaterial[num].material[i].loopNo+"]";
		}
		if((selPosition.isAdd==0||selPosition.isLoop==1)&&selPosition.isBoot==0&&selPosition.isInstream==0&&i==selMaterial[num].material.length-1){
			materialName += "</a>";
		}
	}
	return materialName;
}

/**
 * 选择广告位时，清空除广告位外的数据
 */
function emptyExceptPosition(){
	selPloy = null;
	$("#selPloyName").html('');// 显示已选策略名称清空
	$("#ploybd").hide();// 显示已选策略绑定素材链接清空
	$("#mp0").html('');// 显示策略选择素材名称清空
	$("#ployName").val('');// 策略输入框内容清空
	clearInterval(timer);
	selMaterial = new Array();
	$("#mImage,#video,#text").hide();
	$("#temp").hide();
	$("#temp").remove();
	videoFlag=0;
	$("#selPrecise").html('');
	$("#preciseli").hide();
}
/**
 * 清空数据
 */
function emptyInfo(){
	emptyExceptPosition();
	$("#position").val('');
	$("#pImage").attr("src",getContextPath()+"/images/position/position.jpg");
	selPosition = null;
}

/**
 * 显示合同选择层
 */
function showContract(){
	queryContracts = contracts;
	fillContractInfo();
	var contract = $("input[name='contractId']");
	if(selContractId!=-1){
		for(var i = 0;i<contract.length;i++){
			if(contract[i].value==selContractId){
				contract[i].checked="checked";
			}else{
				contract[i].checked="";
			}
		}
	}else{
		if(contract.length>0){
			contract[0].checked="checked";
		}
	}
	showSelectDiv('contractDiv');
}
/**
 * 根据条件检索合同
 */
function queryContract(){
	queryContracts = new Array();
	var cName = $("#cName").val();
	var cCode = $("#cCode").val();
	var cNumber = $("#cNumber").val();
//	var cStart = $("#cStart").val();
//	var cEnd = $("#cEnd").val();
	for(var i = 0;i<contracts.length;i++){
		if($.trim(cName).length>=1){
			var conName = contracts[i].contractName;
			if(conName.indexOf(cName)==-1){
				continue;
			}
		}
		if($.trim(cCode).length>=1){
			var conCode = contracts[i].contractCode
			if(conCode.indexOf(cCode)==-1){
				continue;
			}
		}
		if($.trim(cNumber).length>=1){
			var conNumber = contracts[i].contractNumber;
			if(conNumber.indexOf(cNumber)==-1){
				continue;
			}
		}
/*		if($.trim(cStart).length>=1){
			var conStart = contracts[i].effectiveStartDate;
			start = conStart.replace(" 00:00:00","");
			if(start!=cStart){
				continue;
			}
		}
		if($.trim(cEnd).length>=1){
			var conEnd = contracts[i].effectiveEndDate;
			end = conEnd.replace(" 00:00:00","");
			if(end!=cEnd){
				continue;
			}
		}*/
		queryContracts[queryContracts.length] = contracts[i];
	}
	fillContractInfo();
}
/**
 * 填充待选择合同记录内容
 */
function fillContractInfo(){
	var str = "";
	for(var i = 0;i<queryContracts.length;i++){
		str +="<tr><td><input type='radio' name='contractId' value='";
		str +=queryContracts[i].id;
		if(i==0){
			str +="' checked='checked";
		}
		str +="'/><input type='hidden' name='contractCode' value='";
		str +=queryContracts[i].contractCode;
		str +="'/><input type='hidden' name='contractName' value='";
		str +=queryContracts[i].contractName;
		str +="'/></td><td>";
		str +=queryContracts[i].contractName;
		str +="</td><td>";
		str +=queryContracts[i].contractCode;
		str +="</td><td>";
		str +=queryContracts[i].contractNumber;
		str +="</td><td>";
		str +=queryContracts[i].customer.advertisersName;
		str +="</td><td>";
		var conStart = queryContracts[i].effectiveStartDate;
		start = conStart.replace(" 00:00:00","");
		str +=start;
		str +="</td><td>";
		var conEnd = queryContracts[i].effectiveEndDate;
		end = conEnd.replace(" 00:00:00","");
		str +=end;
		str +="</td></tr>";	
	}

	$("#contractInfo").html(str);
}
function closeContract(){
	closeSelectDiv('contractDiv');
	queryContracts = contracts;
	$("#cName").val('');
	$("#cCode").val('');
	$("#cNumber").val('');
}
function closePosition(){
	closeSelectDiv('positionDiv');
	queryPositions = positions;
	$("#pName").val('');
	$("#pType").val('-1');
	$("#pMode").val('-1');
}
function closePloy(){
	closeSelectDiv("ployDiv");
	queryPloys = ploys;
	$("#plName").val('');
}
function closeMaterial(){
	closeSelectDiv("materialDiv");
	var num = getMaterialArrayIndex();
	queryMaterials[num].material = materials;
	$("#mName").val('');
}

function selectDate(){
	var ahead = 0;
	if(selPosition.mode==0){
		ahead =  parseInt(aheadTime)*1000;
	}
	var now = Date.parse(new Date())+ahead;
	var vStartDate = Date.parse(validMinDate.replace(/-/g,"/"));
	//当前时间大于投放开始时间，则订单开始时间只能选择大于当前时间，否则订单开始时间从投放开始时间选择
	if(now > vStartDate){
		WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-#{%d}',maxDate:validMaxDate});
	}else{
		WdatePicker({dateFmt:'yyyy-MM-dd',minDate:validMinDate,maxDate:validMaxDate});
	}
}