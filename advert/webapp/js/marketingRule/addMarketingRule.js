var resourcePath='';
var alreadyChoosePositionP = [];

/**
 * 原始数据
 * @type 
 */
var alreadyFillInForm={'id':'','ruleId':'','ruleName':'','startTime':'','endTime':'','positionId':'','bindingArea':[],'bindingPosition':[]};
var hints='';
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


$(function(){
	$("#system-dialog").hide();
  	resourcePath=$('#projetPath').val();
    
/**
 * 选择广告位
 */
	$("#choosePosition").click(function(){
		easyDialog.open({
			container : {
				header : '选择广告位',
				content : generateStruct('choosePosition')
			},
			overlay : false
		});
	});
	
/**
 * 选择地区
 */
	$("#chooseArea").click(function(){
		easyDialog.open({
			container : {
				header : '选择地区',
				content : generateStruct('chooseArea')
			},
			overlay : false
		});
	});
	
/**
 * 选择频道
 */
	$("#chooseChannel").click(function(){
		easyDialog.open({
			container : {
				header : '选择频道',
				content : generateStruct('chooseChannel')
			},
			overlay : false
		});
	});
	
/**
 * 查看广告位已有规则
 */
	$("#findRule").click(function(){
		easyDialog.open({
			container : {
				header : '查看已有规则',
				content : generateStruct('findRule')
			},
			overlay : false
		});
	});
	
	//规则ID
	$('#ruleId').change(function(){
		var ruleId = $("#ruleId").val();
		alreadyFillInForm.ruleId=ruleId;
	});
	
	//规则名称
	$('#ruleName').change(function(){
		var ruleName = $("#ruleName").val();
		alreadyFillInForm.ruleName=ruleName;
	});
	
	//开始时间
	$('#startTime').change(function(){
		var startTime = $("#startTime").val();
		alreadyFillInForm.startTime=startTime;
	});
	
	//结束时间
	$('#endTime').change(function(){
		var endTime = $("#endTime").val();
		alreadyFillInForm.endTime=endTime;
	});
	
});

function findChannel(editorFlag,positionId,areaId,areaIndexFlag){
	var channelIndex = 'init';
	$(alreadyFillInForm.bindingArea).each(function(index,item){
		if(item.areaIndexFlag==areaIndexFlag){
			channelIndex = index;
		}
	});
	if((channelIndex!='init')&&(channelIndex<alreadyFillInForm.bindingArea.length)){
		if(editorFlag=='update'){
			alreadyFillInForm.bindingArea[channelIndex].channel=[];
		}
	}
	if(positionId == "" || positionId == null){
		alert("广告位为空，请选择广告位!");
		return;
	}
	easyDialog.open({
			container : {
				header : '选择频道',
				content : generateChannelStruct(positionId,areaId,areaIndexFlag)
			},
			overlay : false
		});
	}

function generateChannelStruct(positionId,areaId,areaIndexFlag){
	var structInfo = '';
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="chooseChannelFrame" name="chooseChannelFrame" src="'+resourcePath+'/page/marketingRule/queryChannel.do?method=queryChannel&positionId='+positionId+'&areaId='+areaId+'&areaIndexFlag='+areaIndexFlag+'" frameBorder="0" width="1000px" height="300px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	return structInfo;
}

function generateStruct(methodName){
	var positionId = '';
	var areaId = '';
	if(document.getElementById("positionId") != null&&!$.isEmptyObject(document.getElementById("positionId"))){
		positionId = document.getElementById("positionId").value;
	}
	if(document.getElementById("areaId") != null&&!$.isEmptyObject(document.getElementById("areaId"))){
		areaId = document.getElementById("areaId").value;
	}
	var structInfo = '';
	if(methodName=='choosePosition'){
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="choosePositionFrame" name="choosePositionFrame" src="'+resourcePath+'/page/position/queryPositionPage.do?method=queryPage&contractBindingFlag=1&saveOrUpdateFlag=save'+'" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	}else if(methodName=='chooseArea'){
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="chooseAreaFrame" name="chooseAreaFrame" src="'+resourcePath+'/page/marketingRule/queryArea.do?method=queryArea&positionId='+positionId+'" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	}else if(methodName=='chooseChannel'){
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="chooseChannelFrame" name="chooseChannelFrame" src="'+resourcePath+'/page/marketingRule/queryChannel.do?method=queryChannel&positionId='+positionId+'&areaId='+areaId+'" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	}else if(methodName=='findRule'){
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="findRuleFrame" name="findRuleFrame" src="'+resourcePath+'/page/marketingRule/queryRule.do?method=queryRule&positionId='+positionId+'" frameBorder="0" width="1000px" height="350px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	}
	return structInfo;
}

/**
 * 获取已选广告位
 */
function getAlreadyChoose(psize){
	var alreadyChoosePositionInner='';
	var areaIndexFlag='';
	if(psize == 6){
		if(window.frames['choosePositionFrame']!=null&&(!$.isEmptyObject(window.frames['choosePositionFrame'].alreadyChoosePosition))){
			alreadyChoosePositionInner = window.frames['choosePositionFrame'].alreadyChoosePosition;
			if(!$.isEmptyObject(alreadyChoosePositionInner)){
				alreadyChoosePositionP = alreadyChoosePositionInner;
			}
		}
	}else if(psize == 7){
		if(window.frames['chooseAreaFrame']!=null&&(!$.isEmptyObject(window.frames['chooseAreaFrame'].alreadyChoosePosition))){
			alreadyChoosePositionInner = window.frames['chooseAreaFrame'].alreadyChoosePosition;
			if(!$.isEmptyObject(alreadyChoosePositionInner)){
				alreadyChoosePositionP = alreadyChoosePositionInner;
				alreadyFillInForm.bindingArea=alreadyChoosePositionP;
			}
		}
	}else{
		if(window.frames['chooseChannelFrame']!=null&&(!$.isEmptyObject(window.frames['chooseChannelFrame'].alreadyChoosePosition))){
			var indexChoose = 'init';
			alreadyChoosePositionInner = window.frames['chooseChannelFrame'].alreadyChoosePosition;
			areaIndexFlag = window.frames['chooseChannelFrame'].areaIndexFlag;
			if(!$.isEmptyObject(alreadyChoosePositionInner)){
				$(alreadyFillInForm.bindingArea).each(function(index,areaParam){
					if(areaIndexFlag==areaParam.areaIndexFlag){
						indexChoose = index;
					}
				});
				if(indexChoose!='init'){
					//拷贝已有数据至当前记录中
	 				alreadyFillInForm.bindingArea[indexChoose].channel=alreadyFillInForm.bindingArea[indexChoose].channel.concat(alreadyChoosePositionInner);
				}
				
			}
		}else{
			if((!$.isEmptyObject(alreadyFillInForm.bindingArea))&&(alreadyFillInForm.bindingArea.length>0)){
				alreadyChoosePositionP = alreadyFillInForm.bindingArea;
			}
		}
	}
}

function showPositionList4Page(pno, psize) {
	getAlreadyChoose(psize);
	if(psize == 6){
		var num = alreadyChoosePositionP.length;
		if(num > 1){
			alert("不能选择多个广告位！");
			initPositionData();
			return;
		}
		hints=document.getElementById("position");
		var hintsId =document.getElementById("positionId");
		var hintsIsChannel =document.getElementById("isChannel");
		var hintsIsAllTime =document.getElementById("isAllTime");
		$(alreadyChoosePositionP).each(function(index,positionParam){
			hintsId.value=positionParam.id;     
			hints.value=positionParam.positionName;
			hintsIsChannel.value=positionParam.isChannel;
			hintsIsAllTime.value=positionParam.isAllTime; 
		});
	}else{
		var num = alreadyFillInForm.bindingArea.length;
		//先清空原有数据
		if((!$.isEmptyObject(alreadyFillInForm.bindingArea))&&(num>0)){
			$("#bindingPositionArea").siblings().remove();
			$("#bindingPositionArea").after(initBindingAreaContent(1,alreadyFillInForm));
		}else{
			$("#bindingPositionArea").siblings().remove();
			$("#bindingPositionArea").after(initBindingAreaContent(0,''));
		}
	}
}

function show() {
	var psize = 6;
	getAlreadyChoose(psize);
	if(psize == 6){
		var num = alreadyChoosePositionP.length;
		if(num > 1){
			alert("不能选择多个广告位！");
			initPositionData();
			return;
		}
		hints=document.getElementById("position");
		var hintsId =document.getElementById("positionId");
		var hintsIsChannel =document.getElementById("isChannel");
		var hintsIsAllTime =document.getElementById("isAllTime");
		$(alreadyChoosePositionP).each(function(index,positionParam){
			hintsId.value=positionParam.id;     
			hints.value=positionParam.positionName;
			hintsIsChannel.value=positionParam.isChannel;
			hintsIsAllTime.value=positionParam.isAllTime; 
		});
	}else{
		var num = alreadyFillInForm.bindingArea.length;
		//先清空原有数据
		if((!$.isEmptyObject(alreadyFillInForm.bindingArea))&&(num>0)){
			$("#bindingPositionArea").siblings().remove();
			$("#bindingPositionArea").after(initBindingAreaContent(1,alreadyFillInForm));
		}else{
			$("#bindingPositionArea").siblings().remove();
			$("#bindingPositionArea").after(initBindingAreaContent(0,''));
		}
	}
}

/**
	 * 初始化内容
	 */
function initBindingAreaContent(flag,areaList){
	var positionId = '';
	if(document.getElementById("positionId").value==""){
		positionId = areaList.positionId;
	}else{
		positionId = document.getElementById("positionId").value;
	}
    var initAreaContent ='';
    //默认状态
   	if(flag==0){
   		initAreaContent ='<tr>';
   		initAreaContent+='	  <td colspan="8">';
	    initAreaContent+='	  	  暂无数据';
	    initAreaContent+='	  </td>';
	    initAreaContent+='</tr>';
   	}
   	//绑定地区后进行页面初始化操作
   	else{
   	   $(areaList.bindingArea).each(function(index,item){
            initAreaContent+="<tr>";
		    initAreaContent+="	  <td id='areaName"+index+"' name='areaName"+index+"'><a href='#' onclick='findChannel(\""+editorFlag+"\",\""+positionId+"\",\""+item.id+"\",\""+item.areaIndexFlag+"\")'><u>"+item.areaName+"</u></a></td>";
		   
		   if(item.channel!=null&&item.channel.length>0){
		    var channelNameP = '';
		   	$(item.channel).each(function(index,channelParam){
				channelNameP+=','+channelParam.channelName;
			});
		   	
		   	 initAreaContent+="	  <td id='channeName"+index+"' name='channeName"+index+"'>"+channelNameP.substring(1)+"</td>";
		    }else{
		    	initAreaContent+='	  <td id="channeName'+index+'" name="channeName'+index+'"></td>';
		    }
			initAreaContent+='</tr>';
   	   	});
	}
   return initAreaContent;
}

/**
 * 页面广告位部分内容初始化
 */
function initPositionData(){
	//清理广告位信息
	clearPosition();
}


/**
 * 清理广告位列表中内容
 */
function clearPosition(){
	if(window.frames['choosePositionFrame']!=null&&(!$.isEmptyObject(window.frames['choosePositionFrame'].alreadyChoosePosition))){
		window.frames['choosePositionFrame'].alreadyChoosePosition=[];
	}
	hints=document.getElementById("position");
	if(hints != null&&!$.isEmptyObject(hints)){
		hints.innerHTML = '';
	}
}

function firstSubmit(){
	var positionId = $("#positionId").val();
	alreadyFillInForm.positionId=positionId;
	getAlreadyChoose(7);
	if(validateDate()){
		alreadyFillInForm.bindingArea = alreadyChoosePositionP;
	    $.ajax({
				url:"page/marketingRule/saveMarketingRule.do?method=insertMarketingRule",
				type:"post",
				dataType:"json",
				timeout:900000,
				data:{ruleParam:JSON.stringify(alreadyFillInForm)},
					success:function(strValue){
						var resultObject = strValue;
						//处理成功
						if(resultObject.handleResult.flag=="true"){
							message = '保存成功！';
								$("#content").html(message);
								window.location.href='listMarketingRule.do';
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
}

function saveUpdateRule(){
	if(validateDate()){
	    $.ajax({
				url:"page/marketingRule/saveUpdateMarketingRule.do?method=saveUpdateMarketingRule",
				type:"post",
				dataType:"json",
				timeout:900000,
				data:{ruleParam:JSON.stringify(alreadyFillInForm)},
					success:function(strValue){
						var resultObject = strValue;
						//处理成功
						if(resultObject.handleResult.flag=="true"){
							message = '保存成功！';
								$("#content").html(message);
								window.location.href='listMarketingRule.do';
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
}

/**
 * 1、数据校验
 * 2、时间校验
 */
function validateDate(){
	var flag= true;
	//基本属性校验
	var ruleId = $("#ruleId").val();
		if($.isEmptyObject(ruleId.trim())){
				flag = false;
				createDialog('规则ID不能为空');
				return flag;
		}
		
		var ruleName = $("#ruleName").val();
		if($.isEmptyObject(ruleName.trim())){
				flag = false;
				createDialog('规则名称不能为空');
				return flag;
		}
	
		var positionId = $("#positionId").val();
		var oldPositionId = $("#oldPositionId").val();
		if($.isEmptyObject(positionId.trim())){
			if($.isEmptyObject(oldPositionId.trim())){
				flag = false;
				createDialog('请选择广告位');
				return flag;
			}
		}
	
		var startTime = $("#startTime").val();
		if($.isEmptyObject(startTime.trim())){
				flag = false;
				createDialog('请选择开始时间');
				return flag;
		}
		
		var endTime = $("#endTime").val();
		if($.isEmptyObject(endTime.trim())){
				flag = false;
				createDialog('请选择结束时间');
				return flag;
		}
		
		if(startTime>endTime){
				flag = false;
				createDialog('开始时间不能大于结束时间');
				return flag;
		}
		
		var isAllTime = $("#isAllTime").val();
		if(isAllTime != "" && isAllTime != null && isAllTime != undefined){
			if(isAllTime == "1"){
				if(startTime != "00:00:00"||endTime != "23:59:59"){
					flag = false;
					createDialog('广告位为全时段，请选择0点到24点');
					return flag;
				}
			}
		}
		
		var isChannel = $("#isChannel").val();
		if("" != isChannel&&isChannel == 0){
			$(alreadyFillInForm.bindingArea).each(function(index,item){
			if(alreadyFillInForm.bindingArea[index].channel != ""){
				flag = false;
				createDialog('广告位不包含频道，不能绑定频道');
				return flag;
			}
	});
		}
	
		if(alreadyFillInForm.bindingArea.length>0){
			return flag;
		}else {
			flag = false;
			createDialog('请选择地区');
			return flag;
		}
}