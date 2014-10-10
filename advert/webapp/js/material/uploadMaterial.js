var sel_postion_id_str = -1;
var selContractId = -1;//绑定合同id
var queryContracts = new Array();//按条件查询合同数组
var selContractCode = '';//绑定合同合同号
var selPosition = null;//绑定广告位对象
var positions = new Array();//广告位数组
var queryPositions = new Array();//按条件查询广告位数组

/**合同数组*/
var contracts = new Array();


/**
 * 显示广告位选择层
 */
function showPosition(){
	if(selContractId==-1){
		alert("请选择合同");
		return;
	}
	var position = $("input[name='sel_postion_id']");
	if(selPosition!=null){
		for(var i = 0;i<position.length;i++){
			if(position[i].value==selPosition.id){
				position[i].checked="checked";
			}else{
				position[i].checked="";
			}
		}
	}	
	showSelectDiv('positionDiv');
}

/**
 * 选择广告位
 * */
function selectPosition(){
	var positionIds = $("input[name='positionId']");
	for(var i = 0;i<positionIds.length;i++){
		if(positionIds[i].checked){
			if(selPosition==null||selPosition.id!=queryPositions[i].id){
				selPosition = queryPositions[i];
				$("#sel_postion_id_str").val(selPosition.name);
				$("#sel_postion_id").val(positionIds[i].value);
				//emptyExceptPosition();
			}
			break;
		}
	}
	closeSelectDiv('positionDiv');
}



/**
 * 选择合同
 */
function selectContract(){
	var contractIds = $("input[name='contractId']");
	var contractCodes = $("input[name='contractCode']");
	var contractNames = $("input[name='contractName']");
	var customerNames = $("input[name='customerName']");
	var customerIds = $("input[name='customerId']");
	for(var i = 0;i<contractIds.length;i++){
		if(contractIds[i].checked){
			if(selContractId!=contractIds[i].value){
				selContractId = contractIds[i].value;
				selContractCode = contractCodes[i].value;
				setPosition();
			}
			$("#sel_contract_id").val(contractCodes[i].value);
			$("#businessName").val(customerNames[i].value);
			$("#contractName").val(contractNames[i].value);
			$("#businessId").val(customerIds[i].value);
			break;
		}
	}
	closeSelectDiv('contractDiv');
}


/**
 * 设置广告位
 */
function setPosition(){
	$.ajax({
		type:"post",
		url: 'getPosition.do',
		success:function(responseText){
			if(responseText!='-1'){
				positions = eval(responseText);
				queryPositions = eval(responseText);
				fillPositionInfo();
				//emptyInfo();
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
 * 填充广告位记录内容
 * */
function fillPositionInfo(){
	var str = "";
	for(var i =0;i<queryPositions.length;i++){
		str +="<tr><td><input type='radio' name='positionId' value='";
		str +=queryPositions[i].id;
		if(i==0){
			str +="' checked='checked";
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
		str +="</td></tr>";	
	}
	$("#positionInfo").html(str);
}


/**
 * 根据条件检索合同
 */
function queryContract(){
	var cName = $("#cName").val();
	var cCode = $("#cCode").val();
	var cNumber = $("#cNumber").val();
}

function init(contractJson,time){
	contracts = eval(contractJson);
	aheadTime = time;
}


/**
 * 显示合同选择层
 */
function showContract(){
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
 * 弹出策略选择层
 */
function showSelectDiv(divId){
	$('#'+divId).show();
	$('#bg').show();
	$('#popIframe').show();
}

/**
 * 关闭策略选择层
 */
function closeSelectDiv(divId){
	$('#'+divId).hide();
	$('#bg').hide();
	$('#popIframe').hide();
}

//公共方法--获得dom对象
	function getObj(id){
		var obj = document.getElementById(id);
		return obj;	
	}
	
	//公共方法-选择下拉框的值
	function selectOptionVal(id){
		var optionVal="-1";
		var selectPobj = getObj(id); 
		var options   = selectPobj.options; 
		for(var i = 0; i < options.length; i++){
			var optionResult = options[i].selected ;
			if(optionResult){
				optionVal = options[i].value;
			}
		}
		return optionVal;
	}

	//根据类型控制标签显示
	function checkType(){
		var materialType = selectOptionVal("sel_material_type");
		if(materialType == "-1"){
			alert("请选择-素材类型！");
			return;
		}
		if(materialType == 2){
			$('#message_div_id').show();
			$('#messageurl_div_id').show();
			$('#file_div_id').hide();
			$('#runtime_div_id').hide();
			$('#materialViewDiv').hide();
		}else if(materialType == 1){
			$('#file_div_id').show();
			$('#runtime_div_id').hide();
			$('#message_div_id').hide();
			$('#messageurl_div_id').hide();
		}else{
			$('#file_div_id').show();
			$('#runtime_div_id').show();
			$('#message_div_id').hide();
			$('#messageurl_div_id').hide();
		}
	}
	
	//上传文件写入素材
	function writeMessage(){

		var materialType = selectOptionVal("sel_material_type");
		if(materialType == "-1"){
			alert("请选择-素材分类！");
			return;
		}
		
		//确定广告位
	   var positionVal = getObj("sel_postion_id").value;
		if(positionVal == "-1"){
			alert("请选择-广告位！");
			return;
		}
		
		var contractCode = getObj("sel_contract_id").value;
		if(contractCode == "-1"){
			alert("请选择-合同编号！");
			return;
		}
		if(materialType == 2){
			var messagename = getObj("messageName").value;
			if(messagename == "" || messagename == null || messagename == undefined){
				alert("文字名称不能为空!");
				return;
			}
			
			var messagecontent = getObj("messageContent").value;
			if(messagecontent == "" || messagecontent == null || messagecontent == undefined){
				alert("文字内容不能为空!");
				return;
			}
			
			var messageurl = getObj("messageUrl").value;
		}else{
			var localFilePath = getObj("backgroundImage").value; 
			if(localFilePath == "" || localFilePath == null || localFilePath == undefined){
				alert("上传的文件不能为空!");
				return;
			}
			
			var desc = getObj("desc").value;
		
			var runtime = getObj("runtime").value;
			if(materialType == 0){
				if(runtime == "" || runtime == null || runtime == undefined){
					alert("视频文件时长不能为空!");
					return;
				}
			}
		}
		
		var form = $("form[name=form1]"); //其中的form1是我form的名称   
        var options = {   
		 	url:'writeMaterial.do', 
			type:'post', 
			dataType:'json',   
			data: {materialType:materialType, positionId:positionVal,contractId:selContractId,localFilePath:localFilePath,
					desc:desc,runtime:runtime,messageName:messagename,content:messagecontent,url:messageurl},    
			success:function(data){ 
				var flag = data.result;  
				if(flag){
					alert("素材上传保存成功");
					getObj("flagId").value = "1";
				}else{
					alert("上传保存失败,原因:"+data.msg);
				}
		  		     
		 	}   
	 	};   
        form.ajaxSubmit(options);    
	}
	
	//写入资产
	function writeResource(){
	
	//	var flag = getObj("flagId").value;
	//	if(flag == "" || flag == null || flag == undefined){
	//		alert("请先点击【上传文件】或【添加】按钮！");
	//		return;
	//	}

		//确定素材分类
		var materialType = selectOptionVal("sel_material_type");
		if(materialType == "-1"){
			alert("请选择-素材分类！");
			return;
		}
		
		//确定广告位
		var positionVal = getObj("sel_postion_id").value;
		if(positionVal == "-1"){
			alert("请选择-广告位！");
			return;
		}
		
		if(materialType != 2){
			var localFilePath = getObj("backgroundImage").value; 
			if(localFilePath == "" || localFilePath == null || localFilePath == undefined){
				alert("上传的文件不能为空!");
				return;
			}
			//所属内容分类
			var contentSort = selectOptionVal("contentSort");
			if(contentSort == "-1"){
				alert("请选择-内容分类！");
				return;
			}
			//确定资产描述
			var desc = getObj("desc").value;
		}else{
			var messageName = getObj("messageName").value;
			if(messageName == "" || messageName == null || messageName == undefined){
				alert("文字名称不能为空!");
				return;
			}
		}
		
		//确定所属广告商
		var businessId = getObj("businessId").value;
		if(businessId == null || businessId == undefined){
			alert("所属合同名称不能为空！");
			return;
		}
		
		var contractCode = getObj("sel_contract_id").value;
		if(contractCode == "-1"){
			alert("请选择-合同编号！");
			return;
		}
		
		var startTime = getObj("startTime").value;
		if(startTime == "" || startTime == null || startTime == undefined){
			alert("开始日期不能为空!");
			return;
		}
		
		var endTime = getObj("endTime").value; 
		if(endTime == "" || endTime == null || endTime == undefined){
			alert("结束日期不能为空!");
			return;
		}
		
		var form = $("form[name=form1]"); //其中的form1是我form的名称   
        var options = {   
		 	url:'writeResource.do', 
			type:'post', 
			dataType:'json',   
			data: {materialType:materialType,positionId:positionVal,businessId:businessId,contractCode:contractCode,desc:desc,
			contentSort:contentSort,startTime:startTime,endTime:endTime,localFilePath:localFilePath,messageName:messageName},    
			success:function(data){
				var flag = data.result;  
				if(flag){
					alert("上传成功");
				}else{
					alert("上传失败,原因:"+data.msg);
				}
		  		     
		 	}   
	 	};   
        form.ajaxSubmit(options);    
	}