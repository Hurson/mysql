var resourcePath='';
var alreadyChooseObjectP = [];
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
function checkType(id){
	var optionVal="-1";
	var selectPobj = getObj(id); 
	var options   = selectPobj.options; 
	for(var i = 0; i < options.length; i++){
		var optionResult = options[i].selected ;
		if(optionResult){
			optionVal = options[i].value;
		}
	}
	
	var type = optionVal;
	if(type == "-1"){
		alert("请选择-精准类型！");
		return;
	}
	if(type == 1){
		$('#product_div_id').show();
		$('#channel_div_id').hide();
		$('#key_div_id').hide();
		$('#sort_div_id').hide();
		$('#userarea_div_id').hide();
		$('#userIndustrys_div_id').hide();
		$('#userLevels_div_id').hide();
		$('#tvnNumber_div_id').hide();
		$('#category_div_id').hide();
	}else if(type == 2){
		$('#product_div_id').hide();
		$('#channel_div_id').hide();
		$('#key_div_id').show();
		$('#sort_div_id').hide();
		$('#userarea_div_id').hide();
		$('#userIndustrys_div_id').hide();
		$('#userLevels_div_id').hide();
		$('#tvnNumber_div_id').hide();
		$('#category_div_id').hide();
	}else if(type == 3){
		$('#product_div_id').hide();
		$('#channel_div_id').hide();
		$('#key_div_id').hide();
		$('#sort_div_id').hide();
		$('#userarea_div_id').show();
		$('#userIndustrys_div_id').show();
		$('#userLevels_div_id').show();
		$('#tvnNumber_div_id').show();
		$('#category_div_id').hide();
	}else if(type == 4){
		$('#product_div_id').hide();
		$('#channel_div_id').show();
		$('#key_div_id').hide();
		$('#sort_div_id').hide();
		$('#userarea_div_id').hide();
		$('#userIndustrys_div_id').hide();
		$('#userLevels_div_id').hide();
		$('#tvnNumber_div_id').hide();
		$('#category_div_id').hide();
	}else if(type == 5){
		$('#product_div_id').hide();
		$('#channel_div_id').hide();
		$('#key_div_id').hide();
		$('#sort_div_id').hide();
		$('#userarea_div_id').hide();
		$('#userIndustrys_div_id').hide();
		$('#userLevels_div_id').hide();
		$('#tvnNumber_div_id').show();
		$('#category_div_id').hide();
	}else{
		$('#product_div_id').hide();
		$('#channel_div_id').hide();
		$('#key_div_id').hide();
		$('#sort_div_id').hide();
		$('#userarea_div_id').hide();
		$('#userIndustrys_div_id').hide();
		$('#userLevels_div_id').hide();
		$('#tvnNumber_div_id').hide();
		$('#category_div_id').show();
	}
}

$(function(){
  	resourcePath=$('#projetPath').val();
  	$("#system-dialog").hide();
/**
 * 选择策略
 */
	$("#choosePloy").click(function(){
		easyDialog.open({
			container : {
				header : '选择策略',
				content : generateStruct('choosePloy')
			},
			overlay : false
		});
	});
    
/**
 * 选择产品编码
 */
	$("#chooseProduct").click(function(){
		easyDialog.open({
			container : {
				header : '选择产品',
				content : generateStruct('chooseProduct')
			},
			overlay : false
		});
	});
	
/**
 * 选择回看频道
 */
	$("#chooseChannel").click(function(){
		easyDialog.open({
			container : {
				header : '选择回看频道',
				content : generateStruct('chooseChannel')
			},
			overlay : false
		});
	});
	
/**
 * 选择影片分类
 */
	$("#chooseAssetSort").click(function(){
		easyDialog.open({
			container : {
				header : '选择影片分类',
				content : generateStruct('chooseAssetSort')
			},
			overlay : false
		});
	});
	
/**
 * 选择关键字
 */
	$("#chooseKeyword").click(function(){
		easyDialog.open({
			container : {
				header : '选择关键字',
				content : generateStruct('chooseKeyword')
			},
			overlay : false
		});
	});
	
/**
 * 选择用户区域
 */
	$("#chooseUserArea").click(function(){
		easyDialog.open({
			container : {
				header : '选择用户区域',
				content : generateStruct('chooseUserArea')
			},
			overlay : false
		});
	});
	
/**
 * 选择用户区域
 */
	$("#chooseUserIndustrys").click(function(){
		easyDialog.open({
			container : {
				header : '选择用户行业',
				content : generateStruct('chooseUserIndustrys')
			},
			overlay : false
		});
	});
	
/**
 * 选择用户级别
 */
	$("#chooseUserLevels").click(function(){
		easyDialog.open({
			container : {
				header : '选择用户级别',
				content : generateStruct('chooseUserRank')
			},
			overlay : false
		});
	});
	
/**
 * 选择节点信息
 */
	$("#choosePlatCategory").click(function(){
		easyDialog.open({
			container : {
				header : '选择节点信息',
				content : generateStruct('choosePlatCategory')
			},
			overlay : false
		});
	});
	
});


function generateStruct(methodName){
	var structInfo = '';
	if(methodName=='chooseProduct'){
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="chooseProductFrame" name="chooseProductFrame" src="'+resourcePath+'/page/precise/queryProduct.do?method=queryProduct" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	}else if(methodName=='chooseAssetSort'){
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="chooseAssetSortFrame" name="chooseAssetSortFrame" src="'+resourcePath+'/page/precise/queryAssetSort.do?method=queryAssetSort" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	}else if(methodName=='chooseChannel'){
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="chooseChannelFrame" name="chooseChannelFrame" src="'+resourcePath+'/page/precise/queryBTVChannel.do?method=queryChannel" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	}else if(methodName=='chooseKeyword'){
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="chooseKeywordFrame" name="chooseKeywordFrame" src="'+resourcePath+'/page/precise/queryKeyword.do?method=queryKeyword" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	}else if(methodName=='chooseUserArea'){
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="chooseUserAreaFrame" name="chooseUserAreaFrame" src="'+resourcePath+'/page/precise/queryUserArea.do?method=queryUserArea" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	}else if(methodName=='chooseUserIndustrys'){
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="chooseUserIndustrysFrame" name="chooseUserIndustrysFrame" src="'+resourcePath+'/page/precise/queryUserIndustrys.do?method=queryUserIndustrys" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	}else if(methodName=='chooseUserRank'){
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="chooseUserRankFrame" name="chooseUserRankFrame" src="'+resourcePath+'/page/precise/queryUserRank.do?method=queryUserRank" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	}else if(methodName=='choosePlatCategory'){
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="choosePlatCategoryFrame" name="choosePlatCategoryFrame" src="'+resourcePath+'/page/precise/queryPlatCategory.do?method=queryPlatCategory" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	}else if(methodName=='choosePloy'){
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="choosePloyFrame" name="choosePloyFrame" src="'+resourcePath+'/page/precise/queryPloy.do?method=queryPloy" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	}
	return structInfo;
}

/**
 * 获取已选广告位
 */
 //6：产品，7：影片分类，8：关键字，9：用户区域，10：用户行业，11：用户级别，12：节点信息
function getAlreadyChoose(psize){
	var alreadyChooseObjectInner=''
	if(psize == 6){
		if(window.frames['chooseProductFrame']!=null&&(!$.isEmptyObject(window.frames['chooseProductFrame'].alreadyChooseObject))){
			alreadyChooseObjectInner = window.frames['chooseProductFrame'].alreadyChooseObject;
			if(!$.isEmptyObject(alreadyChooseObjectInner)){
				alreadyChooseObjectP = alreadyChooseObjectInner;
			}
		}
	}else if(psize == 7){
		if(window.frames['chooseAssetSortFrame']!=null&&(!$.isEmptyObject(window.frames['chooseAssetSortFrame'].alreadyChooseObject))){
			alreadyChooseObjectInner = window.frames['chooseAssetSortFrame'].alreadyChooseObject;
			if(!$.isEmptyObject(alreadyChooseObjectInner)){
				alreadyChooseObjectP = alreadyChooseObjectInner;
			}
		}
	}else if(psize == 8){
		if(window.frames['chooseKeywordFrame']!=null&&(!$.isEmptyObject(window.frames['chooseKeywordFrame'].alreadyChooseObject))){
			alreadyChooseObjectInner = window.frames['chooseKeywordFrame'].alreadyChooseObject;
			if(!$.isEmptyObject(alreadyChooseObjectInner)){
				alreadyChooseObjectP = alreadyChooseObjectInner;
			}
		}
	}else if(psize == 9){
		if(window.frames['chooseUserAreaFrame']!=null&&(!$.isEmptyObject(window.frames['chooseUserAreaFrame'].alreadyChooseObject))){
			alreadyChooseObjectInner = window.frames['chooseUserAreaFrame'].alreadyChooseObject;
			if(!$.isEmptyObject(alreadyChooseObjectInner)){
				alreadyChooseObjectP = alreadyChooseObjectInner;
			}
		}
	}else if(psize == 10){
		if(window.frames['chooseUserIndustrysFrame']!=null&&(!$.isEmptyObject(window.frames['chooseUserIndustrysFrame'].alreadyChooseObject))){
			alreadyChooseObjectInner = window.frames['chooseUserIndustrysFrame'].alreadyChooseObject;
			if(!$.isEmptyObject(alreadyChooseObjectInner)){
				alreadyChooseObjectP = alreadyChooseObjectInner;
			}
		}
	}else if(psize == 11){
		if(window.frames['chooseUserRankFrame']!=null&&(!$.isEmptyObject(window.frames['chooseUserRankFrame'].alreadyChooseObject))){
			alreadyChooseObjectInner = window.frames['chooseUserRankFrame'].alreadyChooseObject;
			if(!$.isEmptyObject(alreadyChooseObjectInner)){
				alreadyChooseObjectP = alreadyChooseObjectInner;
			}
		}
	}else if(psize == 12){
		if(window.frames['choosePlatCategoryFrame']!=null&&(!$.isEmptyObject(window.frames['choosePlatCategoryFrame'].alreadyChooseObject))){
			alreadyChooseObjectInner = window.frames['choosePlatCategoryFrame'].alreadyChooseObject;
			if(!$.isEmptyObject(alreadyChooseObjectInner)){
				alreadyChooseObjectP = alreadyChooseObjectInner;
			}
		}
	}else if(psize == 13){
		if(window.frames['choosePloyFrame']!=null&&(!$.isEmptyObject(window.frames['choosePloyFrame'].alreadyChooseObject))){
			alreadyChooseObjectInner = window.frames['choosePloyFrame'].alreadyChooseObject;
			if(!$.isEmptyObject(alreadyChooseObjectInner)){
				alreadyChooseObjectP = alreadyChooseObjectInner;
			}
		}
	}else{
		if(window.frames['chooseChannelFrame']!=null&&(!$.isEmptyObject(window.frames['chooseChannelFrame'].alreadyChooseObject))){
			alreadyChooseObjectInner = window.frames['chooseChannelFrame'].alreadyChooseObject;
			if(!$.isEmptyObject(alreadyChooseObjectInner)){
				alreadyChooseObjectP = alreadyChooseObjectInner;
			}
		}
	}
}

function showChooseList4Page(pno, psize) {
	getAlreadyChoose(psize);
	var num = alreadyChooseObjectP.length;
	//psize值说明   6：产品，7：影片分类，8：关键字，9：用户区域，10：用户行业，11：用户级别，12：节点信息，13：策略
	if(psize == 6){
		var productCodeT = '',productNameT='';
		var productCodeHints=document.getElementById("product");
		var productNameHints=document.getElementById("productName");
		$(alreadyChooseObjectP).each(function(index,productParam){
			productCodeT += ','+productParam.productCode;
			productNameT += ','+productParam.productName;
		});
		productCodeHints.value = productCodeT.substring(1);
		productNameHints.value = productNameT.substring(1);
	}else if(psize == 7){
		var assetSort = '',assetSortN='';
		var assetSortHints=document.getElementById("assetSort");
		var assetSortNHints=document.getElementById("assetSortName");
		$(alreadyChooseObjectP).each(function(index,assetSortParam){
			assetSort += ','+assetSortParam.assetSortId;
			assetSortN += ','+assetSortParam.assetSortName;
		});
		assetSortHints.value = assetSort.substring(1);
		assetSortNHints.value = assetSortN.substring(1);
	}else if(psize == 8){
		var keyT = '';
		var keyHints=document.getElementById("keyword");
		$(alreadyChooseObjectP).each(function(index,keywordParam){
			keyT += ','+keywordParam.keyword;
		});
		keyHints.value = keyT.substring(1);
	}else if(psize == 9){
		var userAreaT = '',userAreaN = '';
		var userAreaHints=document.getElementById("userArea");
		var userAreaNHints=document.getElementById("userAreaName");
		$(alreadyChooseObjectP).each(function(index,userAreaParam){
			userAreaT += ','+userAreaParam.userAreaCode;
			userAreaN += ','+userAreaParam.userAreaName;
		});
		userAreaHints.value = userAreaT.substring(1);
		userAreaNHints.value = userAreaN.substring(1);
	}else if(psize == 10){
		var industrys = '',industrysN='';
		var industrysHints=document.getElementById("userIndustrys");
		var industrysNHints=document.getElementById("userIndustrysName");
		$(alreadyChooseObjectP).each(function(index,userIndustrysParam){
			industrys += ','+userIndustrysParam.userIndustrysCode;
			industrysN += ','+userIndustrysParam.userIndustrysName;
		});
		industrysHints.value = industrys.substring(1);
		industrysNHints.value = industrysN.substring(1);
	}else if(psize == 11){
		var levels = '',levelsN='';
		var levelsHints=document.getElementById("userLevels");
		var levelsNHints=document.getElementById("userLevelsName");
		$(alreadyChooseObjectP).each(function(index,userRankParam){
			levels += ','+userRankParam.userRankCode;
			levelsN += ','+userRankParam.userRankName;
		});
		levelsHints.value = levels.substring(1);
		levelsNHints.value = levelsN.substring(1);
	}else if(psize == 12){
		var categoryT = '';
		var hints=document.getElementById("category");
		$(alreadyChooseObjectP).each(function(index,categoryParam){
			categoryT += ','+categoryParam.platCategory;
		});
		hints.value = categoryT.substring(1);
	}else if(psize == 13){
		var ployT = '',ployN='';
		var ployId=document.getElementById("ployId");
		var ployName=document.getElementById("ployName");
		$(alreadyChooseObjectP).each(function(index,ployParam){
			ployT += ','+ployParam.ployCode;
			ployN += ','+ployParam.ployName;
		});
		ployId.value = ployT.substring(1);
		ployName.value = ployN.substring(1);
	}else{
		var channelC = '',channelN='';
		var channelHints=document.getElementById("channel");
		var channelNHints=document.getElementById("channelName");
		$(alreadyChooseObjectP).each(function(index,channelParam){
			channelC += ','+channelParam.btvChannelId;
			channelN += ','+channelParam.btvChannelName;
		});
		channelHints.value = channelC.substring(1);
		channelNHints.value = channelN.substring(1);
	}
}


/**
 * 清理添加精准列表中内容
 */
function clearOption(){
	$("#product").val('');
	$("#channel").val('');
	$("#keyword").val('');
	$("#assetSort").val('');
	$("#userArea").val('');
	$("#userIndustrys").val('');
	$("#userLevels").val('');
	$("#tvnNumber").val('');
	$("#category").val('');
	$("#productName").val('');
	$("#channelName").val('');
	$("#assetSortName").val('');
	$("#userAreaName").val('');
	$("#userIndustrysName").val('');
	$("#userLevelsName").val('');
	$("#categoryName").val('');
}

function firstSubmit(){
	var preciseName = $("#preciseName").val();
	if(preciseName == "" || preciseName == null || preciseName == undefined){
		alert("精准名称不能为空!");
		return;
	}
	var priority = $("#priority").val();
	var type = selectOptionVal("sel_type_id");
		if(type == "-1"){
			alert("请精准匹配类型！");
			return;
		}
	var product = $("#product").val();
	var channel = $("#channel").val();
	var keyword = $("#keyword").val();
	var assetSort = $("#assetSort").val();
	var userArea = $("#userArea").val();
	var userIndustrys = $("#userIndustrys").val();
	var userLevels = $("#userLevels").val();
	var tvnNumber = $("#tvnNumber").val();
	var category = $("#category").val();
	var productName = $("#productName").val();
	var channelName = $("#channelName").val();
	var assetSortName = $("#assetSortName").val();
	var userAreaName = $("#userAreaName").val();
	var userIndustrysName = $("#userIndustrysName").val();
	var userLevelsName = $("#userLevelsName").val();
	var categoryName = $("#categoryName").val();
	var ployId = $("#ployId").val();
	if(ployId == "" || ployId == null || ployId == undefined){
		alert("策略不能为空!");
		return;
	}
	    $.ajax({
				url:"page/precise/savePrecise.do?method=insertPrecise",
				type:"post",
				dataType:"json",
				timeout:900000,
				data:{preciseName:preciseName,priority:priority,type:type,product:product,channel:channel,keyword:keyword,assetSort:assetSort,
					userArea:userArea,userIndustrys:userIndustrys,userLevels:userLevels,tvnNumber:tvnNumber,category:category,ployId:ployId,
					productName:productName,channelName:channelName,assetSortName:assetSortName,userAreaName:userAreaName,
					userIndustrysName:userIndustrysName,userLevelsName:userLevelsName,categoryName:categoryName},
					success:function(strValue){
						var resultObject = strValue;
						//处理成功
						if(resultObject.handleResult.flag=="true"){
							clearOption();
							message = '保存成功！';
							$("#content").html(message);
							window.location.href='listPrecise.do?method=getAllPreciseList&ployId='+resultObject.handleResult.ployId;
						//处理失败
						}else if(resultObject.handleResult.flag=="false"){
						//处理过程出现异常
							message = '处理过程出现异常！';
							$("#content").html(message);
							$( "#system-dialog" ).dialog({
						      	modal: true
						    });
						}
						
					},
					error:function(strValue){
						message = '网络超时，请联系管理员！';
						$("#content").html(message);
						$( "#system-dialog" ).dialog({
					      	modal: true
					    });
					}
				
			});
}

function saveUpdatePrecise(){
	var id = $("#preciseId").val();
	var preciseName = $("#preciseName").val();
	if(preciseName == "" || preciseName == null || preciseName == undefined){
		alert("精准名称不能为空!");
		return;
	}
	var priority = $("#priority").val();
	var type = selectOptionVal("sel_type_id");
		if(type == "-1"){
			alert("请精准匹配类型！");
			return;
		}
	var product = '',productName='',channel = '',channelName ='',userArea ='',userAreaName ='',
		userIndustrys ='',userIndustrysName ='',userLevels = '',userLevelsName = '',
		tvnNumber = '',keyword ='';
	var assetSort = $("#assetSort").val();
	var assetSortName = $("#assetSortName").val();
	var category = $("#category").val();
	var categoryName = $("#categoryName").val();
	if(type == 1){
		product = $("#product").val();
		productName = $("#productName").val();
	}else if(type == 2){
		keyword = $("#keyword").val();
	}else if(type == 3){
		userArea = $("#userArea").val();
		userIndustrys = $("#userIndustrys").val();
		userLevels = $("#userLevels").val();
		userAreaName = $("#userAreaName").val();
		userIndustrysName = $("#userIndustrysName").val();
		userLevelsName = $("#userLevelsName").val();
		tvnNumber = $("#tvnNumber").val();
	}else if(type == 4){
		channel = $("#channel").val();
		channelName = $("#channelName").val();
	}
	
	var ployId = $("#ployId").val();
	if(ployId == "" || ployId == null || ployId == undefined){
		alert("策略不能为空!");
		return;
	}
	    $.ajax({
				url:"page/marketingRule/saveUpdatePrecise.do?method=saveUpdatePrecise",
				type:"post",
				dataType:"json",
				timeout:900000,
				data:{id:id,preciseName:preciseName,priority:priority,type:type,product:product,channel:channel,keyword:keyword,assetSort:assetSort,
					userArea:userArea,userIndustrys:userIndustrys,userLevels:userLevels,tvnNumber:tvnNumber,category:category,ployId:ployId,
					productName:productName,channelName:channelName,assetSortName:assetSortName,userAreaName:userAreaName,
					userIndustrysName:userIndustrysName,userLevelsName:userLevelsName,categoryName:categoryName},
					success:function(strValue){
						var resultObject = strValue;
						//处理成功
						if(resultObject.handleResult.flag=="true"){
							message = '保存成功！';
								$("#content").html(message);
								
							window.location.href='listPrecise.do?method=getAllPreciseList&ployId='+resultObject.handleResult.ployId;
						//处理失败
						}else if(resultObject.handleResult.flag=="false"){
						//处理过程出现异常
							message = '处理过程出现异常！';
							$("#content").html(message);
							$( "#system-dialog" ).dialog({
						      	modal: true
						    });
						}
						
					},
					error:function(strValue){
						message = '网络超时，请联系管理员！';
						$("#content").html(message);
						$( "#system-dialog" ).dialog({
					      	modal: true
					    });
					}
				
			});
}