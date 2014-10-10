var resourcePath='';
var message = '';
//按钮隐藏
var buttonHidden = 0;
//按钮显示
var buttonShow = 1;
//每页显示总记录数
var pageSize = 6;
// 当前页
var currentPage = 0;
// 开始显示的行
var startRow = 0;
// 结束显示的行
var endRow = 0;

var alreadyChoosePositionP = [];
/**
 * 以供查看已绑定规则页面使用viewAlreadyBindingMarketingRule.jsp
 * @type String
 */
var alreadyChooseMarketRuleList4View = '';

/**
 * 原始数据
 * @type 
 */
var alreadyFillInForm={'id':'','contractNumber':'','contractCode':'','customerId':'','customerName':'','contractName':'','submitUnits':'','financialInformation':'','approvalCode':'','metarialPath':'','effectiveStartDate':'','effectiveEndDate':'','approvalStartDate':'','approvalEndDate':'','effectiveStartDateShow':'','effectiveEndDateShow':'','approvalStartDateShow':'','approvalEndDateShow':'','otherContent':'','status':'','contractDesc':'','bindingPosition':[]};
/**
 * 拷贝数据,对alreadyFillInForm数据的拷贝，以供修改时使用，以此来判断数据的修改状态
 */
var alreadyFillInFormCopy='';

function accessPath(path){
	window.location.href=path;
}
/**
 * 数据最终的修改参考
 */
var comparedForm={'id':'false','contractNumber':'false','contractCode':'false','customerId':'false','customerName':'false','contractName':'false','submitUnits':'false','financialInformation':'false','approvalCode':'false','metarialPath':'false','effectiveStartDate':'false','effectiveEndDate':'false','approvalStartDate':'false','approvalEndDate':'false','effectiveStartDateShow':'false','effectiveEndDateShow':'false','approvalStartDateShow':'false','approvalEndDateShow':'false','otherContent':'false','status':'false','contractDesc':'false'};

/**
 * 获取已选广告位
 */
function getAlreadyChoosePosition(){
	var alreadyChoosePositionInner=''
	//从子页面获取数据
	if(window.frames['bingPositionFrame']!=null&&(!$.isEmptyObject(window.frames['bingPositionFrame'].alreadyChoosePosition))){
		alreadyChoosePositionInner = window.frames['bingPositionFrame'].alreadyChoosePosition;
		if(!$.isEmptyObject(alreadyChoosePositionInner)){
			alreadyChoosePositionP = alreadyChoosePositionInner;
			alreadyFillInForm.bindingPosition=alreadyChoosePositionP;
		}
	//从数据库中初始化数据
	}else{
		if((!$.isEmptyObject(alreadyFillInForm.bindingPosition))&&(alreadyFillInForm.bindingPosition.length>0)){
			alreadyChoosePositionP = alreadyFillInForm.bindingPosition;
			//alreadyFillInForm.bindingPosition=alreadyChoosePositionP;
		}
	}
}

var noticeInfo = {'true':'处理成功','false':'处理失败','ParamNotEnough':'参数不足','contractNumberExist':'合同号已存在','saveContractFailure':'保存合同失败','noBindingMarketingRule':'广告位未绑定策略','noBindingAdvertPosition':'未绑定广告位','saveSuccess':'保存成功','saveFailure':'保存失败'};


/**
 * 分页函数
 * pno--页数
 * psize--每页显示记录数
 */				  
function show() {
	getAlreadyChoosePosition();
	//先清空原有数据
	if((!$.isEmptyObject(alreadyChoosePositionP))&&(alreadyChoosePositionP.length>0)){
		$("#bindingPositionArea").siblings().remove();
		$("#bindingPositionArea").after(initBindingPositionContent(1,alreadyChoosePositionP));
	}else{
		$("#bindingPositionArea").siblings().remove();
		$("#bindingPositionArea").after(initBindingPositionContent(0,''));
	}
   	
}

/**
 * 为position设置开始和结束时间 
 * @param {} elements 元素 根据此元素定位对应广告位
 * @param {} value 当前日期
 */
function setDate4PositionList(positionIndexFlag,value,type){
	//定位到所选时间的索引位置
	//alert('elements='+elements+' value='+value+'currentPage='+currentPage+' startRow='+startRow+' endRow='+endRow);
	var currentDateIndex = 'init';
	
	getAlreadyChoosePosition();
	
	if(type=='validStart'){
		
		$(alreadyFillInForm.bindingPosition).each(function(index, item){
			if(positionIndexFlag==item.positionIndexFlag){
				currentDateIndex=index;
			}	
		});
		
		//根据索引位置找到对应的记录，并给与其开始和结束时间
		
		if(currentDateIndex!='init'){
			
				if(currentDateIndex<alreadyFillInForm.bindingPosition.length){
					//alert('index1='+choosePositionindex);
					alreadyChoosePositionP[currentDateIndex].validStartDateShow=value;
					//先将此数据转换成Date类型的数据，然后再进行赋值操作
					alreadyChoosePositionP[currentDateIndex].validStartDate=dateStringTranformDateObject(value);
					
					if(!$.isEmptyObject(comparedForm.bindingPosition)&&comparedForm.bindingPosition.length>0){
						var comparedFormIndex='init';
						//找到对应记录
						for (var bindPositionindex = 0; bindPositionindex < comparedForm.bindingPosition.length; bindPositionindex++) {
							if((comparedForm.bindingPosition[bindPositionindex].positionIndexFlag==alreadyChoosePositionP[currentDateIndex].positionIndexFlag)){
								comparedFormIndex=bindPositionindex;
							}
						}
									
						if(comparedFormIndex!='init'){
							//如果日期不相等
							if(comparedForm.bindingPosition[comparedFormIndex].validStartDate!=alreadyChoosePositionP[currentDateIndex].validStartDate){
								comparedForm.bindingPosition[comparedFormIndex].validStartDate=alreadyChoosePositionP[currentDateIndex].validStartDate;
								//因属性值发生了变化，故此处置为3[分情况，1、数据库中存在 2、数据库中不存在]
								if(comparedForm.bindingPosition[comparedFormIndex].dbFlag==0){
									comparedForm.bindingPosition[comparedFormIndex].flag=3;
								}
							}
						}
					}
				}
			
		}
	}else if(type=='validEnd'){
		
		$(alreadyFillInForm.bindingPosition).each(function(index, item){
			if(positionIndexFlag==item.positionIndexFlag){
				currentDateIndex=index;
			}	
		});
		
		//根据索引位置找到对应的记录，并给与其开始和结束时间
		if(currentDateIndex!='init'){
				if(currentDateIndex<alreadyFillInForm.bindingPosition.length){
					//alert('index1='+choosePositionindex);
					alreadyChoosePositionP[currentDateIndex].validEndDateShow=value;
					//先将此数据转换成Date类型的数据，然后再进行赋值操作
					alreadyChoosePositionP[currentDateIndex].validEndDate=dateStringTranformDateObject(value);
					
					if(!$.isEmptyObject(comparedForm.bindingPosition)&&comparedForm.bindingPosition.length>0){
						var comparedFormIndex='init';
						
						//找到对应记录
						for (var bindPositionindex = 0; bindPositionindex < comparedForm.bindingPosition.length; bindPositionindex++) {
							if((comparedForm.bindingPosition[bindPositionindex].positionIndexFlag==alreadyChoosePositionP[currentDateIndex].positionIndexFlag)){
								comparedFormIndex=bindPositionindex;
							}
						}
									
						if(comparedFormIndex!='init'){
							//如果日期不相等
							if(comparedForm.bindingPosition[comparedFormIndex].validEndDate!=alreadyChoosePositionP[currentDateIndex].validEndDate){
								comparedForm.bindingPosition[comparedFormIndex].validEndDate=alreadyChoosePositionP[currentDateIndex].validEndDate;
								//因属性值发生了变化，故此处置为3[分情况，1、数据库中存在 2、数据库中不存在]
								if(comparedForm.bindingPosition[comparedFormIndex].dbFlag==0){
									comparedForm.bindingPosition[comparedFormIndex].flag=3;
								}
							}
						}
					}
				}
		}
	}	
}

/**
 * 回调方法，清除全局变量中的数据
 */
function cleanUpBindingMarketingRule(){
	alreadyChooseMarketRuleList4View=[];
	window.frames['viewAlreadyBindingPositionFrame'].alreadyChooseMarketRuleList=[];
}

function generateStructContract(methodName,positionId,chooseMarketRulesElement,currentIndex,positionIndexFlagParam){
	
	var structInfo = '';
	if(methodName=='bindingCustomer'){
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="bindingCustomerFrame" name="bindingCustomerFrame" src="'+resourcePath+'/page/customer/adCustomerMgr_list.do?contractBindingFlag=1'+'" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	}else if(methodName=='bindingPosition'){
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="bingPositionFrame" name="bingPositionFrame" src="'+resourcePath+'/page/position/queryPositionPage.do?method=queryPage&contractBindingFlag=1'+'&positionIndexFlag='+positionIndexFlagParam+'&saveOrUpdateFlag='+saveOrUpdateFlag+'" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	}else if(methodName=='bindingMarketingRule'){
		positionIndexFlag=positionId;
		var positionIdParam = chooseMarketRulesElement;
		var url=resourcePath+'/page/marketingrule/listMarketingRule.do?method=listMarketingRule&contractBindingFlag=1'+'&positionIndexFlag='+positionIndexFlag+'&saveOrUpdateFlag='+saveOrUpdateFlag+'&positionId='+positionIdParam;
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="bindingMarketingRuleFrame" name="bindingMarketingRuleFrame" src="'+url+'" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	}else if(methodName=='viewAlreadyBindingPosition'){
		//currentDateIndex=positionId;
		positionIndexFlag=positionId;
		structInfo+='					<div style="margin:0px;padding:0px;">';																																	
		structInfo+='							<iframe id="viewAlreadyBindingPositionFrame" name="viewAlreadyBindingPositionFrame" src="'+resourcePath+'/page/marketingRule/new/view.jsp?positionIndexFlag='+positionIndexFlag+'&saveOrUpdateFlag='+saveOrUpdateFlag+'" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	}
	
	return structInfo;
}
/**
 * 字符串数据转换为日期类型
 */
function dateStringTranformDateObject(dateString){
	return Date.parse(dateString);
}

/**
 * 比较修改前后记录，确定哪些记录为修改字段
 * @param {} beforeRecord 修改前记录
 * @param {} afterRecord 修改后记录
 */
function compareModifyRecord(beforeRecord,afterRecord){
	
	if((!$.isEmptyObject(beforeRecord))&&(!$.isEmptyObject(afterRecord)))
	{
		if(beforeRecord.id!=afterRecord.id){
			comparedForm.id='true';
		}
		
		if(beforeRecord.contractNumber!=afterRecord.contractNumber){
			comparedForm.contractNumber='true';
		}
		
		if(beforeRecord.contractCode!=afterRecord.contractCode){
			comparedForm.contractCode='true';
		}
		
		if(beforeRecord.customerId!=afterRecord.customerId){
			comparedForm.customerId='true';
		}
		
		if(beforeRecord.customerName!=afterRecord.customerName){
			comparedForm.customerName='true';
		}
		
		if(beforeRecord.contractName!=afterRecord.contractName){
			comparedForm.contractName='true';
		}
		
		if(beforeRecord.financialInformation!=afterRecord.financialInformation){
			comparedForm.financialInformation='true';
		}
		
		if(beforeRecord.approvalCode!=afterRecord.approvalCode){
			comparedForm.approvalCode='true';
		}
		
		if(beforeRecord.metarialPath!=afterRecord.metarialPath){
			comparedForm.metarialPath='true';
		}
		
		if(beforeRecord.effectiveStartDate!=afterRecord.effectiveStartDate){
			comparedForm.effectiveStartDate='true';
		}
		
		if(beforeRecord.effectiveEndDate!=afterRecord.effectiveEndDate){
			comparedForm.effectiveEndDate='true';
		}
		
		if(beforeRecord.approvalStartDate!=afterRecord.approvalStartDate){
			comparedForm.approvalStartDate='true';
		}
		
		if(beforeRecord.approvalEndDate!=afterRecord.approvalEndDate){
			comparedForm.approvalEndDate='true';
		}
		
		if(beforeRecord.effectiveStartDateShow!=afterRecord.effectiveStartDateShow){
			comparedForm.effectiveStartDateShow='true';
		}
		
		if(beforeRecord.effectiveEndDateShow!=afterRecord.effectiveEndDateShow){
			comparedForm.effectiveEndDateShow='true';
		}
		
		if(beforeRecord.approvalStartDateShow!=afterRecord.approvalStartDateShow){
			comparedForm.approvalStartDateShow='true';
		}
		
		if(beforeRecord.approvalEndDateShow!=afterRecord.approvalEndDateShow){
			comparedForm.approvalEndDateShow='true';
		}
		
		if(beforeRecord.otherContent!=afterRecord.otherContent){
			comparedForm.otherContent='true';
		}
		
		if(beforeRecord.status!=afterRecord.status){
			comparedForm.status='true';
		}
		
		if(beforeRecord.contractDesc!=afterRecord.contractDesc){
			comparedForm.contractDesc='true';
		}
		
		if(beforeRecord.submitUnits!=afterRecord.submitUnits){
			comparedForm.submitUnits='true';
		}
	}
}


$(function(){
	show();
	resourcePath=$('#projetPath').val();
	$("#system-dialog").hide();
	
	$("#customerId").click(function(){
		var customerId = $("#customerId").val();
		if($.isEmptyObject(customerId)){
			message = '请单击【绑定广告商】文本框进行广告商绑定';
			$("#content").html(message);
			$( "#system-dialog" ).dialog({
		      	modal: true
		    });
		}
	
	});
	/**
	 * 绑定广告位
	 */
	$("#bindingPosition").click(function(){
		easyDialog.open({
			container : {
				header : '绑定广告位',
				content : generateStructContract('bindingPosition')
			},
			overlay : false
		});
	});
	/**
	 * 绑定广告商
	 */
	$("#bindingCustomer").click(function(){
		easyDialog.open({
			container : {
				header : '绑定广告商',
				content : generateStructContract('bindingCustomer')
			},
			overlay : false
		});
	});
	/**
	 * 绑定营销规则
	 */
	$("#bindingMarketingRule").click(function(){
		easyDialog.open({
			container : {
				header : '绑定营销规则',
				content : generateStructContract('bindingMarketingRule')
			},
			overlay : false
		});
	});
	
	//合同编号
	$('#contractNumber').change(function(){
		var contractNumberVal=$('#contractNumber').val();
		alreadyFillInForm.contractNumber=contractNumberVal;
	});
	
	//合同代码
	$('#contractCode').change(function(){
		var contractCodeVal=$('#contractCode').val();
		alreadyFillInForm.contractCode=contractCodeVal;
	});
	
	//合同名称
	$('#contractName').change(function(){
		var contractNameVal=$('#contractName').val();
		alreadyFillInForm.contractName=contractNameVal;
	});
	
	//送审单位
	$('#submitUnits').change(function(){
		var submitUnitsVal=$('#submitUnits').val();
		alreadyFillInForm.submitUnits=submitUnitsVal;
	});
	
	//合同金额
	$('#financialInformation').change(function(){
		var financialInformationVal=$('#financialInformation').val();
		alreadyFillInForm.financialInformation=financialInformationVal;
	});
	
	//广告审批文号
	$('#approvalCode').change(function(){
		var approvalCodeVal=$('#approvalCode').val();
		alreadyFillInForm.approvalCode=approvalCodeVal;
	});
	
	//合同有效期的开始时间
	$('#effectiveStartDate').change(function(){
		var effectiveStartDateVal=$('#effectiveStartDate').val();
		alreadyFillInForm.effectiveStartDateShow=effectiveStartDateVal;
		alreadyFillInForm.effectiveEndDateShow=effectiveStartDateVal;
		//进行格式转换[此处暂时缺少判断条件]
		alreadyFillInForm.effectiveStartDate=dateStringTranformDateObject(effectiveStartDateVal);
		//进行格式转换[此处暂时缺少判断条件]
		alreadyFillInForm.effectiveEndDate=dateStringTranformDateObject(effectiveStartDateVal);
	});
	
	//合同有效期的结束时间
	$('#effectiveEndDate').change(function(){
		var effectiveEndDateVal=$('#effectiveEndDate').val();
		alreadyFillInForm.effectiveEndDateShow=effectiveEndDateVal;
		alreadyFillInForm.effectiveEndDate=dateStringTranformDateObject(effectiveEndDateVal);
	});
	
	//审批文号有效期的开始日期
	$('#approvalStartDate').change(function(){
		var approvalStartDateVal=$('#approvalStartDate').val();
		alreadyFillInForm.approvalStartDateShow=approvalStartDateVal;
		alreadyFillInForm.approvalEndDateShow=approvalStartDateVal;
		//进行格式转换[此处暂时缺少判断条件]
		alreadyFillInForm.approvalStartDate=dateStringTranformDateObject(approvalStartDateVal);
		//进行格式转换[此处暂时缺少判断条件]
		alreadyFillInForm.approvalEndDate=dateStringTranformDateObject(approvalStartDateVal);
	});
	
	//审批文号有效期的截止日期
	$('#approvalEndDate').change(function(){
		var approvalEndDateVal=$('#approvalEndDate').val();
		alreadyFillInForm.approvalEndDateShow=approvalEndDateVal;
		//进行格式转换[此处暂时缺少判断条件]
		alreadyFillInForm.approvalEndDate=dateStringTranformDateObject(approvalEndDateVal);
	});
	
	//其他内容
	$('#otherContent').change(function(){
		var otherContentVal=$('#otherContent').val();
		alreadyFillInForm.otherContent=otherContentVal;
	});
	
	//备注描述
	$('#desc').change(function(){
		var descVal=$('#desc').val();
		alreadyFillInForm.contractDesc=descVal.trim();
	});
	
	
	//添加合同
	$('#addContractButton').click(function(){
		
		//初始化已选中的广告位参数
		getAlreadyChoosePosition();
		
		if(validateDate()){
			alreadyFillInForm.bindingPosition=alreadyChoosePositionP;
			//通过ajax方式提交至后台
			$.ajax({
				url:"page/contract/addContract.do?method=save",
				type:"post",
				dataType:"json",
				timeout:900000,
				data:{contractParam:JSON.stringify(alreadyFillInForm)},
					success:function(strValue){
						
						var resultObject = strValue;
						
						//resultObject=eval("("+resultObject+")");
						//处理成功
						if(resultObject.handleResult.flag=="true"){
								message = '保存成功！';
								$("#content").html(message);
								$( "#system-dialog" ).dialog({
							      	modal: true,
							      	buttons:{
							      		'回到列表页':function(){
							      			var url='queryContractPage.do?method=queryPage';
							      			accessPath(url);
							      		}
							      	},
							      	close: function() {
             							var url='queryContractPage.do?method=queryPage';
							      		accessPath(url);
         							}
								});
						//处理失败
						}else if(resultObject.handleResult.flag=="false"){
							//参数不足':'','':'','':'','':''
							if(resultObject.handleResult.cause=="ParamNotEnough"){
								message = '参数不足！';
								$("#content").html(message);
								$( "#system-dialog" ).dialog({
							      	modal: true
							    });
							//合同号已存在
							}else if(resultObject.handleResult.cause=="contractNumberExist"){
								message = '合同号已存在！';
								$("#content").html(message);
								$( "#system-dialog" ).dialog({
							      	modal: true
							    });
							//保存合同失败
							}else if(resultObject.handleResult.cause=="saveContractFailure"){
								message = '保存合同失败！';
								$("#content").html(message);
								$( "#system-dialog" ).dialog({
							      	modal: true
							    });
							//广告位未绑定策略
							}else if(resultObject.handleResult.cause=="noBindingMarketingRule"){
								message = '广告位未绑定策略！';
								$("#content").html(message);
								$( "#system-dialog" ).dialog({
							      	modal: true
							    });
							//未绑定广告位
							}else if(resultObject.handleResult.cause=="noBindingAdvertPosition"){
								message = '未绑定广告位！';
								$("#content").html(message);
								$( "#system-dialog" ).dialog({
							      	modal: true
							    });
							}
						//处理过程出现异常
						}else if(resultObject.handleResult.cause="error"){
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
	});
	
	//修改合同
	$('#modifyContractButton').click(function(){
		//初始化已选中的广告位参数
		getAlreadyChoosePosition();
		
		//对比修改前后记录，找出已修改记录
		if(validateDate()){
			//alreadyFillInForm.bindingPosition=alreadyChoosePositionP;
			//page/contract/updateContract.do?method=update
			alreadyFillInFormCopy.id=$('#id').val();
			alreadyFillInForm.id=$('#id').val();
			compareModifyRecord(alreadyFillInForm,alreadyFillInFormCopy);
			alreadyFillInForm.bindingPosition=alreadyChoosePositionP;
			//通过ajax方式提交至后台
			$.ajax({
				url:"page/contract/updateContract.do?method=update",
				type:"post",
				dataType:"json",
				timeout:900000,
				data:{contractParam:JSON.stringify(alreadyFillInForm),
					  contractParamBefore:JSON.stringify(alreadyFillInFormCopy),
					  comparedFormResult:JSON.stringify(comparedForm),
					  contractAd:contractAdObject
					 },
					success:function(strValue){
						var resultObject = strValue;
						if(resultObject.handleResult.flag=="true"){
								message = '修改成功！';
								$("#content").html(message);
								$( "#system-dialog" ).dialog({
							      	modal: true,
							      	buttons:{
							      		'回到列表页':function(){
							      			var url='queryContractPage.do?method=queryPage';
							      			accessPath(url);
							      		}
							      	},
							      	close: function() {
             							var url='queryContractPage.do?method=queryPage';
							      		accessPath(url);
         							}
								});
						//处理失败
						}else if(resultObject.handleResult.resultFlag="error"){
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
	});
});

/**
	 * 初始化内容
	 */
function initBindingPositionContent(flag,positionList){
   var initPositionContent ='';
    //默认状态
   	if(flag==0){
   		initPositionContent ='<tr>';
   		initPositionContent+='	  <td colspan="8">';
	    initPositionContent+='	  	  暂无数据';
	    initPositionContent+='	  </td>';
	    initPositionContent+='</tr>';
   	}
   	//绑定广告位后进行页面初始化操作
   	else{
   	   $(positionList).each(function(index,item){
   	   		
   	   		initPositionContent+='<tr>';
   	   		initPositionContent+='	  <td id="positionOrder'+index+'" name="positionOrder'+'" >'+(index+1)+'</td>';
		    initPositionContent+='	  <td id="positionName'+index+'" name="positionName'+'">'+item.positionName+'</td>';
		    initPositionContent+='	  <td id="positionTypeName'+index+'" name="positionTypeName'+'">'+item.positionTypeName+'</td>';
		    initPositionContent+='	  <td id="deliveryMode'+index+'" name="deliveryMode'+'">'+showInfoTransform(item.isHd,'deliveryMode')+'</td>';
		    initPositionContent+='	  <td id="isHd'+index+'" name="isHd'+'">'+showInfoTransform(item.isHd,'isHd')+'</td>';
		    initPositionContent+='	  <td id="validStartTd'+index+'" name="validStartTd'+'">';
		    //initPositionContent+='	  <input id="validStart'+index+'" name="validStart'+index+'" class="input_style2" type="text" style="width:70px;" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\',maxDate:\'#F{$dp.$D('+'\\'+'\''+'validEnd'+index+'\\'+'\''+')}\',onpicked:function(dp){';
		    initPositionContent+='	  <input id="validStart'+index+'" name="validStart'+index+'" class="input_style2" type="text" style="width:70px;" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\',maxDate:\'#F{$dp.$D('+'\\'+'\''+'effectiveEndDate'+'\\'+'\''+')}\',onpicked:function(dp){';
		    initPositionContent+='		setDate4PositionList(\''+item.positionIndexFlag+'\',dp.cal.getDateStr(),\'validStart\')';
			if(typeof(item.validStartDateShow)!='undefined'){
				initPositionContent+='	  }})" value="'+item.validStartDateShow+'"/>';
			}else{
				initPositionContent+='	  }})" value="'+'"/>';
			}
		    initPositionContent+='	  </td>';
		    initPositionContent+='	  <td id="validEnd'+index+'" name="validEnd'+'">';
		    //initPositionContent+='		  <input id="validEnd'+index+'" name="validEnd'+index+'" class="input_style2" type="text" style="width:70px;" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\',minDate:\'#F{$dp.$D('+'\\'+'\''+'validStart'+index+'\\'+'\''+')}\',onpicked:function(dp){';
		    initPositionContent+='		  <input id="validEnd'+index+'" name="validEnd'+index+'" class="input_style2" type="text" style="width:70px;" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\',minDate:\'#F{$dp.$D('+'\\'+'\''+'effectiveStartDate'+'\\'+'\''+')}\',onpicked:function(dp){';
		    initPositionContent+='		setDate4PositionList(\''+item.positionIndexFlag+'\',dp.cal.getDateStr(),\'validEnd\')';
			if(typeof(item.validEndDateShow)!='undefined'){
				initPositionContent+='	  }})" value="'+item.validEndDateShow+'"/>';
			}else{
				initPositionContent+='	  }})" value="'+'"/>';
			}
		    initPositionContent+='	  </td>';
			initPositionContent+='	  <td >';
			initPositionContent+='		  <a id="remove'+index+'" name="remove'+'" href="#" onclick="deleteAlreadyChoosePosition(\''+item.positionIndexFlag+'\')">删除</a>';
			initPositionContent+='		  &nbsp;&nbsp;';
			initPositionContent+='		  <a id="binding'+index+'" name="binding'+'" href="#" onclick="bindingMarketRule(\''+item.positionIndexFlag+'\',\''+item.id+'\')">绑定新规则</a>';
			initPositionContent+='		  &nbsp;&nbsp;';
			initPositionContent+='		  <a id="editBinding'+index+'" name="editBinding'+'" href="#" onclick="editBindMarketingRule(\''+item.positionIndexFlag+'\')">编辑已绑定</a>';
			initPositionContent+='	  </td>';
			initPositionContent+='</tr>';
   	   	});
	}
   return initPositionContent;
}

/*
 * 删除已选广告位
 * @param positionIndexFlag
 */
function deleteAlreadyChoosePosition(positionIndexFlag){
	getAlreadyChoosePosition();
	//定位到所选时间的索引位置
	var currentDateIndex = 'init';
					
	$(alreadyChoosePositionP).each(function(index, item){
		if(positionIndexFlag==item.positionIndexFlag){
			currentDateIndex=index;
		}	
	});
						
	if(currentDateIndex!='init'){
			if(currentDateIndex<alreadyChoosePositionP.length){
					var positionIndexFlagParam = alreadyChoosePositionP[currentDateIndex].positionIndexFlag;
					if(!$.isEmptyObject(comparedForm.bindingPosition)&&comparedForm.bindingPosition.length>0){
					   var comparedFormIndex='init';
					   //找到对应记录
					   for (var bindPositionindex = 0; bindPositionindex < comparedForm.bindingPosition.length; bindPositionindex++) {
						    if(comparedForm.bindingPosition[bindPositionindex].positionIndexFlag==positionIndexFlagParam){
							    comparedFormIndex=bindPositionindex;
							}
					   }
					   if(comparedFormIndex!='init'){
						   //分两种情况，一种数据库存在，页面中要进行删除操作(修改状态为2)
					       if(comparedForm.bindingPosition[comparedFormIndex].dbFlag==0){
						       comparedForm.bindingPosition[comparedFormIndex].flag=2;
					   	   }else{
						   //一种数据库中已存在，页面中进行删除操作(可直接删除)
						   comparedForm.bindingPosition.splice(comparedFormIndex,1);
					       }
					}
			    }
				//从下标为choosePositionindex的元素开始,删除一个元素
				alreadyChoosePositionP.splice(currentDateIndex,1);
			}
			show();
	}
	
}

/*
 * 绑定规则
 * @param positionIndexFlag
 */
function bindingMarketRule(positionIndexFlag,positionId){
		getAlreadyChoosePosition();
		var currentDateIndex = 'init';
					
					//弹层
		easyDialog.open({
			container : {
				header : '绑定规则',
				content : generateStructContract('bindingMarketingRule',positionIndexFlag,positionId,'save')
			},
			overlay : false
		});
}

/*
 * 绑定开始时间
 * @param positionIndexFlag
 */
function bindingStartDate(positionIndexFlag){

}

/*
 * 绑定结束时间
 * @param positionIndexFlag
 */
function bindingEndDate(positionIndexFlag){
	
}

function editBindMarketingRule(positionIndexFlag){
	getAlreadyChoosePosition();
	var currentDateIndex = 'init';
    var positionId = '';
                	
	$(alreadyChoosePositionP).each(function(index, item){
						if(positionIndexFlag==item.positionIndexFlag){
							currentDateIndex=index;
						}	
	});
					
	//将待修改的元素传入已选广告位中
	//触发绑定事件的元素
	var chooseMarketRulesElement = '';
	//当前索引位置
	var currentIndex = '';
	//标记
    var positionIndexFlagParam = '';
					
					
	if(currentDateIndex!='init'){
		if(currentDateIndex<alreadyChoosePositionP.length){
				positionId=alreadyChoosePositionP[currentDateIndex].id;
				if((!$.isEmptyObject(alreadyChoosePositionP[currentDateIndex].marketRules))&&(alreadyChoosePositionP[currentDateIndex].marketRules.length>0)){								   
						alreadyChooseMarketRuleList4View=alreadyChoosePositionP[currentDateIndex].marketRules;
				}
		}
	}
					
					//弹层
	easyDialog.open({
			container : {
							header : '查看并修改已绑定规则',
							content : generateStructContract('viewAlreadyBindingPosition',positionIndexFlag)
			},
						overlay : false,
						callback : cleanUpBindingMarketingRule
	});
}

/**
 * 1、数据校验
 * 2、时间校验
 */
function validateDate(){
	
	var flag= true;
	//数据校验 1、广告位是否为空 2、广告位所绑时段是否为空 3、开始时段是否大于结束时段 4、广告位所绑策略是否为空 
	if(alreadyFillInForm.bindingPosition.length<=0){
		flag = false;
		createDialog('请先绑定广告位');
		return flag;
	}else {
		//、基本属性校验
		var customerIdVal=$('#customerId').val();
		if((''==customerIdVal)||$.isEmptyObject(customerIdVal.trim())){
				flag = false;
				createDialog('请绑定广告商');
				return flag;
		}
		
		var contractNumberVal=$('#contractNumber').val();
		if((''==contractNumberVal)||$.isEmptyObject(contractNumberVal.trim())){
				flag = false;
				createDialog('合同编号不能为空');
				return flag;
		}
	
		var contractCodeVal=$('#contractCode').val();
		if((''==contractCodeVal)||$.isEmptyObject(contractCodeVal.trim())){
				flag = false;
				createDialog('合同代码不能为空');
				return flag;
		}
	
		var contractNameVal=$('#contractName').val();
		if((''==contractNameVal)||$.isEmptyObject(contractNameVal.trim())){
				flag = false;
				createDialog('合同名称不能为空');
				return flag;
		}
	
		var submitUnitsVal=$('#submitUnits').val();
		if((''==submitUnitsVal)||$.isEmptyObject(submitUnitsVal.trim())){
				flag = false;
				createDialog('送审单位不能为空');
				return flag;
		}
	
		var financialInformationVal=$('#financialInformation').val();
		if((''==financialInformationVal)||$.isEmptyObject(financialInformationVal.trim())){
				flag = false;
				createDialog('合同金额不能为空');
				return flag;
		}
	
		var approvalCodeVal=$('#approvalCode').val();
		if((''==approvalCodeVal)||$.isEmptyObject(approvalCodeVal.trim())){
				flag = false;
				createDialog('广告审批文号不能为空');
				return flag;
		}
		
		var effectiveStartDateVal=$('#effectiveStartDate').val();
		if((''==effectiveStartDateVal)||$.isEmptyObject(effectiveStartDateVal.trim())){
				flag = false;
				createDialog('合同有效期的开始时间不能为空');
				return flag;
		}
		
		
		var effectiveEndDateVal=$('#effectiveEndDate').val();
		if((''==effectiveEndDateVal)||$.isEmptyObject(effectiveEndDateVal.trim())){
				flag = false;
				createDialog('合同有效期的结束时间不能为空');
				return flag;
		}
	
		var approvalStartDateVal=$('#approvalStartDate').val();
		if((''==approvalStartDateVal)||$.isEmptyObject(approvalStartDateVal.trim())){
				flag = false;
				createDialog('审批文号有效期的开始日期不能为空');
				return flag;
		}
	
		var approvalEndDateVal=$('#approvalEndDate').val();
		if((''==approvalEndDateVal)||$.isEmptyObject(approvalEndDateVal.trim())){
				flag = false;
				createDialog('审批文号有效期的截止日期不能为空');
				return flag;
		}
	
		var descVal=$('#desc').val();
		if((''==descVal)||($.isEmptyObject(descVal.trim()))){
				flag = false;
				createDialog('备注描述不能为空');
				return flag;
		}
		
		var positionList = [];
		//比较时间时使用
		var startDate = '';
		//比较时间时使用
		var endDate='';
		//规则Id
		var marketRule= [];
		var marketRuleId= '';
		$(alreadyFillInForm.bindingPosition).each(function(indexOut,itemOut){
			//、绑定时段是否为空
			
			if(itemOut.validStartDate==''){
				flag = false;
				createDialog('请先为广告位绑定开始时间');
				return flag;
			}
			
			if(itemOut.validEndDate==''){
				flag = false;
				createDialog('请先为广告位绑定结束时间');
				return flag;
			}
			
			//2、开始时间是否大于结束时间
			if(itemOut.validStartDate>itemOut.validEndDate){
				flag = false;
				createDialog('开始时间不能大于结束时间');
				return flag;
			}
			
			//4 绑定策略是否为空
			if(itemOut.marketRules<=0){
				flag = false;
				createDialog('请绑定规则');
				return flag;
			}
			
			//冲突校验 1、
			//同个日期同个规则只有一个广告位
			$(alreadyFillInForm.bindingPosition).each(function(indexInner,itemInner){
				if(itemOut.id==itemInner.id){
					positionList.push(itemInner);
				}
			});
			
			//代表同一个广告位绑定了多次，
			if(positionList.length>1){
				
				$(positionList).each(function(positionIndexInner,positionInnerItem){
					startDate = positionInnerItem.validStartDate;
					endDate= positionInnerItem.validEndDate;
					marketRuleInner = positionInnerItem.marketRules;
					
					$(positionList).each(function(positionOuterIndex,positionOuterItem){
						// 1、日期不能交叠
						if((positionInnerItem.positionIndexFlag!=positionOuterItem.positionIndexFlag)&&(positionOuterItem.validStartDate>=startDate&&positionOuterItem.validStartDate<=endDate)){
							flag = false;
							createDialog('所绑定开始时间有重叠');
							return flag;
						}
						
						if((positionInnerItem.positionIndexFlag!=positionOuterItem.positionIndexFlag)&&(positionOuterItem.validEndDate>=startDate&&positionOuterItem.validEndDate<=endDate)){
							flag = false;
							createDialog('所绑定结束时间有重叠');
							return flag;
						}
						
						if((positionInnerItem.positionIndexFlag!=positionOuterItem.positionIndexFlag)&&(positionOuterItem.validStartDate<=startDate&&(positionOuterItem.validEndDate>=startDate||positionOuterItem.validEndDate>=endDate))){
							flag = false;
							createDialog('所绑定结束时间有重叠');
							return flag;
						}
						
						// 2、同一个规则同一个广告位只能有一个[如果日期不重叠，此部分校验将不予执行]
						/*marketRuleOuter = positionOuterItem.marketRules;
						
						$(marketRuleInner).each(function(marketRuleIndexInner,marketRuleInnerItem){
							marketRuleId =  marketRuleInnerItem.id;
							$(marketRuleOuter).each(function(marketRuleIndexOuter,marketRuleOuterItem){
								if(marketRuleId==marketRuleOuterItem.id){
									marketRule.push(marketRuleOuterItem);
								}
							});
							
							if(marketRule.length>1){
								flag = false;
								createDialog('规则绑定校验失败');
								return flag;
							}
							marketRule=[];
						});*/
					});
				}); 
			}
			positionList=[];
		});	
		return flag;
	}
}