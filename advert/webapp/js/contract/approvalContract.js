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
 * 每页显示广告位数量
 * @type Number
 */
var showPositionCount={'positionButtonList':[{'buttonName':'#position0B','tdName':'#position0T','orderName':'#position0Order','positionType':'#position0TypeId','eigenValue':'#eigen0Value','positionName':'#position0Name','validStart':'#validStart0I','validEnd':'#validEnd0I','removeButtom':'#removeButtom0','alreadyChooseRule':'#alreadyChooseRule0','editorMarketRuleButtom':'#editorMarketRuleButtom0'}
											,{'buttonName':'#position1B','tdName':'#position1T','orderName':'#position1Order','positionType':'#position1TypeId','eigenValue':'#eigen1Value','positionName':'#position1Name','validStart':'#validStart1I','validEnd':'#validEnd1I','removeButtom':'#removeButtom1','alreadyChooseRule':'#alreadyChooseRule1','editorMarketRuleButtom':'#editorMarketRuleButtom1'}
										    ,{'buttonName':'#position2B','tdName':'#position2T','orderName':'#position2Order','positionType':'#position2TypeId','eigenValue':'#eigen2Value','positionName':'#position2Name','validStart':'#validStart2I','validEnd':'#validEnd2I','removeButtom':'#removeButtom2','alreadyChooseRule':'#alreadyChooseRule2','editorMarketRuleButtom':'#editorMarketRuleButtom2'}
										    ,{'buttonName':'#position3B','tdName':'#position3T','orderName':'#position3Order','positionType':'#position3TypeId','eigenValue':'#eigen3Value','positionName':'#position3Name','validStart':'#validStart3I','validEnd':'#validEnd3I','removeButtom':'#removeButtom3','alreadyChooseRule':'#alreadyChooseRule3','editorMarketRuleButtom':'#editorMarketRuleButtom3'}
										    ,{'buttonName':'#position4B','tdName':'#position4T','orderName':'#position4Order','positionType':'#position4TypeId','eigenValue':'#eigen4Value','positionName':'#position4Name','validStart':'#validStart4I','validEnd':'#validEnd4I','removeButtom':'#removeButtom4','alreadyChooseRule':'#alreadyChooseRule4','editorMarketRuleButtom':'#editorMarketRuleButtom4'}
										    ,{'buttonName':'#position5B','tdName':'#position5T','orderName':'#position5Order','positionType':'#position5TypeId','eigenValue':'#eigen5Value','positionName':'#position5Name','validStart':'#validStart5I','validEnd':'#validEnd5I','removeButtom':'#removeButtom5','alreadyChooseRule':'#alreadyChooseRule5','editorMarketRuleButtom':'#editorMarketRuleButtom5'}]
					  };

/**
 * 原始数据
 * @type 
 */
var alreadyFillInForm={'id':'','contractNumber':'','contractCode':'','customerId':'','customerName':'','contractName':'','submitUnits':'','financialInformation':'','approvalCode':'','metarialPath':'','effectiveStartDate':'','effectiveEndDate':'','approvalStartDate':'','approvalEndDate':'','effectiveStartDateShow':'','effectiveEndDateShow':'','approvalStartDateShow':'','approvalEndDateShow':'','otherContent':'','status':'','contractDesc':'','bindingPosition':[]};

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
function showPositionList4Page(pno, psize) {
	getAlreadyChoosePosition();
	//先清空原有数据
   	initPositionData();
   	
	var num = alreadyChoosePositionP.length;

	var totalPage = 0;// 总页数

	pageSize = psize;// 每页显示行数

	if ((num - 1) / pageSize >= parseInt((num - 1) / pageSize)) {
		totalPage = parseInt((num - 1) / pageSize) + 1;
	} else {
		totalPage = parseInt((num - 1) / pageSize);
	}

	currentPage = pno;// 当前页数
	startRow = (currentPage - 1) * pageSize;

	endRow = currentPage * pageSize;
	endRow = (endRow > num) ? num : endRow;
	
	
	$(alreadyChoosePositionP).each(function(index,itemInner){
	  
	 //初始化广告位信息
	  if(index >= startRow && index < endRow){
		 
	  	var buttunIndex = 0;
	
		if(index>=pageSize){
			buttunIndex=index%pageSize;	
		}else{
			buttunIndex=index;
		}
		 
		 initPosition(buttunIndex,itemInner);
		 //将记录置为可见状态
		 modifyElementsDisplay(showPositionCount.positionButtonList[buttunIndex].validStart,buttonShow);
		 //将记录置为可见状态
		 modifyElementsDisplay(showPositionCount.positionButtonList[buttunIndex].validEnd,buttonShow);
		 //初始化开始结束时间
		 addTimePlugIn(showPositionCount.positionButtonList[buttunIndex].validStart,showPositionCount.positionButtonList[buttunIndex].validEnd,itemInner.positionIndex);
		 //初始化策略绑定按钮
		 modifyElementsDisplay(showPositionCount.positionButtonList[buttunIndex].buttonName,buttonShow);
		 //为策略绑定按钮添加事件
		 addEvent4Button(showPositionCount.positionButtonList[buttunIndex].buttonName,itemInner.characteristicIdentification,buttunIndex,'banding');
		 //初始化删除按钮
		 modifyElementsDisplay(showPositionCount.positionButtonList[buttunIndex].removeButtom,buttonShow);
		 //为策略绑定按钮添加事件
		 addEvent4Button(showPositionCount.positionButtonList[buttunIndex].removeButtom,itemInner.characteristicIdentification,buttunIndex,'delete');			
	 	 //初始化策略绑定按钮
		 modifyElementsDisplay(showPositionCount.positionButtonList[buttunIndex].editorMarketRuleButtom,buttonShow);
		 //为编辑已绑定按钮添加事件
		 addEvent4Button(showPositionCount.positionButtonList[buttunIndex].editorMarketRuleButtom,itemInner.characteristicIdentification,buttunIndex,'editorMarketRule');
	 	}
	});

	
	var tempStr = " 共【" + num + "】条记录 分【" + totalPage + "】页 当前第【" + currentPage
			+ "】页 ";

	if (currentPage > 1) {
		tempStr += " <a href=\"#\" onClick=\"showPositionList4Page(" + (currentPage - 1) + ","
				+ psize + ")\">上一页</a> "
	} else {
		tempStr += " 上一页 ";
	}

	if (currentPage < totalPage) {
		tempStr += " <a href=\"#\" onClick=\"showPositionList4Page(" + (currentPage + 1) + ","
				+ psize + ")\">下一页</a> ";
	} else {
		tempStr += " 下一页 ";
	}

	if (currentPage > 1) {
		tempStr += " <a href=\"#\" onClick=\"showPositionList4Page(" + (1) + "," + psize
				+ ")\">首页</a> ";
	} else {
		tempStr += " 首页 ";
	}
	if (currentPage < totalPage) {
		tempStr += " <a href=\"#\" onClick=\"showPositionList4Page(" + (totalPage) + ","
				+ psize + ")\">尾页</a> ";
	} else {
		tempStr += " 尾页 ";
	}
	document.getElementById("pageOperationDiv").innerHTML = tempStr;
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
		$(showPositionCount.positionButtonList).each(function(index, item){
			//清空【序号】原始记录
			$(item.orderName).html('');
			//清空【广告位类型编码】原始记录
			$(item.positionType).html('');
			//清空【广告位特征值】原始记录
			$(item.eigenValue).html('');
			//清空【广告位名称】原始记录
			$(item.positionName).html('');
			
			//隐藏【投放开始日期】按钮
			modifyElementsDisplay(item.validStart,buttonHidden);
			//隐藏【投放结束日期】按钮
			modifyElementsDisplay(item.validEnd,buttonHidden);
			//让绑定策略按钮隐藏
			modifyElementsDisplay(item.buttonName,buttonHidden);
			//让删除按钮隐藏
			modifyElementsDisplay(item.removeButtom,buttonHidden);
			
			//清空投放开始日期
			$(item.validStart).val('');
			//清空投放结束日期
			$(item.validEnd).val('');
			//清空投放策略
			$(item.alreadyChooseRule).html('');
			
			//让编辑一帮策略按钮隐藏
			modifyElementsDisplay(item.editorMarketRuleButtom,buttonHidden);
		});
}

/**
 * 添加时间插件
 * @param startDate 开始日期
 * @param endDate 结束日期
 */
function addTimePlugIn(startDate,endDate,positionIndexParam){
	//开始日期
	var effectiveStartDate = $(startDate);
	//结束日期
	var effectiveEndDate = $(endDate);

	effectiveStartDate.datetimepicker({
		showHour:false,
		showMinute:false,
		showTime:false,
		showButtonPanel:false,
		alwaysSetTime:false,
		onClose: function(dateText, inst) {
			if (effectiveEndDate.val() != '') {
				var testStartDate = effectiveStartDate.datetimepicker('getDate');
				var testEndDate = effectiveEndDate.datetimepicker('getDate');
				if (testStartDate > testEndDate){
					effectiveEndDate.datetimepicker('setDate', testStartDate);
					setDate4PositionList(effectiveEndDate.selector,dateText,'validEnd');
				}
			}
			else {
				//结束时间为空
				effectiveEndDate.val(dateText);
				if(!$.isEmptyObject(dateText)){
					setDate4PositionList(effectiveEndDate.selector,dateText,'validEnd');
				}
				
			}
			
		},
		onSelect: function (selectedDateTime){
			effectiveEndDate.datetimepicker('option', 'minDate', effectiveStartDate.datetimepicker('getDate') );
			setDate4PositionList(effectiveStartDate.selector,selectedDateTime,'validStart');
		}
	});
	
	effectiveEndDate.datetimepicker({ 
		showHour:false,
		showMinute:false,
		showTime:false,
		showButtonPanel:false,
		alwaysSetTime:false,
		onClose: function(dateText, inst) {
			if (effectiveStartDate.val() != '') {
				var testStartDate = effectiveStartDate.datetimepicker('getDate');
				var testEndDate = effectiveEndDate.datetimepicker('getDate');
				if (testStartDate > testEndDate){
					effectiveStartDate.datetimepicker('setDate', testEndDate);
					setDate4PositionList(effectiveStartDate.selector,dateText,'validStart');	
				}
			}
			else {
				effectiveStartDate.val(dateText);
				if(!$.isEmptyObject(dateText)){
					setDate4PositionList(effectiveStartDate.selector,dateText,'validStart');
				}	
			}
		},
		onSelect: function (selectedDateTime){
			effectiveStartDate.datetimepicker('option', 'maxDate', effectiveEndDate.datetimepicker('getDate'));
			setDate4PositionList(effectiveEndDate.selector,selectedDateTime,'validEnd');
		}
	});
}

/**
 * 为position设置开始和结束时间 
 * @param {} elements 元素 根据此元素定位对应广告位
 * @param {} value 当前日期
 */
function setDate4PositionList(elements,value,type){
	//定位到所选时间的索引位置
	//alert('elements='+elements+' value='+value+'currentPage='+currentPage+' startRow='+startRow+' endRow='+endRow);
	var currentDateIndex = 'init';
	getAlreadyChoosePosition();
	
	if(type=='validStart'){
		$(showPositionCount.positionButtonList).each(function(index, item){
			if(elements==item.validStart){
				currentDateIndex=index;
			}	
		});
		
		//根据索引位置找到对应的记录，并给与其开始和结束时间
		
		if(currentDateIndex!='init'){
			for (var choosePositionindex = startRow; choosePositionindex < endRow; choosePositionindex++) {
				if(choosePositionindex==(currentDateIndex+((currentPage - 1) * pageSize))){
					//alert('index1='+choosePositionindex);
					alreadyChoosePositionP[choosePositionindex].validStartDateShow=value;
					//先将此数据转换成Date类型的数据，然后再进行赋值操作
					alreadyChoosePositionP[choosePositionindex].validStartDate=dateStringTranformDateObject(value);
					
					//将开始时间加入，确定索引位置
					if(!$.isEmptyObject(comparedForm.bindingPosition)&&comparedForm.bindingPosition.length>0){
						var comparedFormIndex='init';
						//找到对应记录
						for (var bindPositionindex = 0; bindPositionindex < comparedForm.bindingPosition.length; bindPositionindex++) {
							if((comparedForm.bindingPosition[bindPositionindex].positionIndexFlag==alreadyChoosePositionP[choosePositionindex].positionIndexFlag)){
								comparedFormIndex=bindPositionindex;
							}
						}
									
						if(comparedFormIndex!='init'){
							//如果日期不相等
							if(comparedForm.bindingPosition[comparedFormIndex].validStartDate!=alreadyChoosePositionP[choosePositionindex].validStartDate){
								comparedForm.bindingPosition[comparedFormIndex].validStartDate=alreadyChoosePositionP[choosePositionindex].validStartDate;
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
	}else if(type=='validEnd'){
		$(showPositionCount.positionButtonList).each(function(index, item){
			if(elements==item.validEnd){
				currentDateIndex=index;
			}	
		});
		
		//根据索引位置找到对应的记录，并给与其开始和结束时间
		if(currentDateIndex!='init'){
			for (var choosePositionindex = startRow; choosePositionindex < endRow; choosePositionindex++) {
				if(choosePositionindex==(currentDateIndex+((currentPage - 1) * pageSize))){
					//alert('index1='+choosePositionindex);
					alreadyChoosePositionP[choosePositionindex].validEndDateShow=value;
					//先将此数据转换成Date类型的数据，然后再进行赋值操作
					alreadyChoosePositionP[choosePositionindex].validEndDate=dateStringTranformDateObject(value);
					
					//将结束时间加入，确定索引位置
					if(!$.isEmptyObject(comparedForm.bindingPosition)&&comparedForm.bindingPosition.length>0){
						var comparedFormIndex='init';
						
						//找到对应记录
						for (var bindPositionindex = 0; bindPositionindex < comparedForm.bindingPosition.length; bindPositionindex++) {
							if((comparedForm.bindingPosition[bindPositionindex].positionIndexFlag==alreadyChoosePositionP[choosePositionindex].positionIndexFlag)){
								comparedFormIndex=bindPositionindex;
							}
						}
									
						if(comparedFormIndex!='init'){
							//如果日期不相等
							if(comparedForm.bindingPosition[comparedFormIndex].validEndDate!=alreadyChoosePositionP[choosePositionindex].validEndDate){
								comparedForm.bindingPosition[comparedFormIndex].validEndDate=alreadyChoosePositionP[choosePositionindex].validEndDate;
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
}

/**
 * 初始化广告位中内容
 * @param positionParam 传递参数
 */
function initPosition(index,positionParam){
	var buttunIndex = 0;
	
	if(index>=pageSize){
		buttunIndex=index%pageSize;	
	}else{
		buttunIndex=index;
	}
	//初始化序号显示内容
	$(showPositionCount.positionButtonList[buttunIndex].orderName).html(pageSize*(currentPage-1)+index+1);
	//初始化广告位类型编码
	$(showPositionCount.positionButtonList[buttunIndex].positionType).html(positionParam.positionTypeId);
	//初始化广告位特征值
	$(showPositionCount.positionButtonList[buttunIndex].eigenValue).html(positionParam.characteristicIdentification);
	//初始化广告位名称
	$(showPositionCount.positionButtonList[buttunIndex].positionName).html(positionParam.positionName);
	
	//初始化投放开始日期
	$(showPositionCount.positionButtonList[buttunIndex].validStart).val(positionParam.validStartDateShow);
	//初始化投放结束日期
	$(showPositionCount.positionButtonList[buttunIndex].validEnd).val(positionParam.validEndDateShow);
	//初始化删除按钮上的内容
	
	//初始化绑定按钮上的内容
	
	//初始化已绑定策略显示内容
	$(showPositionCount.positionButtonList[buttunIndex].alreadyChooseRule).html(positionParam.chooseMarketRules);
	//设置记录为默认选中状态
	//defaultChoose('checkbox',positionParam.marketRules);
}

/**
 * 直接访问
 * @param {} url
 */
function accessUrl(url){
		window.location.href=url;
}

/**
 * 修改元素显示还是隐藏
 * @param elements 待修改元素
 * @param isDisplay 0 隐藏 1 显示
 */
function modifyElementsDisplay(elements,isDisplay){
	switch(isDisplay){
		case 0:
			$(elements).hide();
			break;
		case 1:
			$(elements).show();
			break;
		default:
			break;
	}
}
/**
 * 为元素添加事件
 * @param elements 待添加事件元素
 * @param eigenValue 特征值
 * @param index 索引位置
 * @param eventType 事件类型 
 * 					banding 绑定策略 
 * 					delete 删除
 */
function addEvent4Button(elementsName,eigenValue,index,eventType){
	//添加绑定策略事件
	if(eventType=='banding'){
		getAlreadyChoosePosition();
		setTimeout(function () {
                $(elementsName).attr('onclick', '').unbind('click').bind('click', function () {
                	
                	var currentDateIndex = 'init';
                	var positionId = '';
                	
					$(showPositionCount.positionButtonList).each(function(index, item){
						if(elementsName==item.buttonName){
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
					//根据索引位置找到对应的记录，并给与其开始和结束时间
					if(currentDateIndex!='init'){
						for (var choosePositionindex = startRow; choosePositionindex < endRow; choosePositionindex++) {
							if(choosePositionindex==(currentDateIndex+((currentPage - 1) * pageSize))){
								positionId=alreadyChoosePositionP[choosePositionindex].id;
								chooseMarketRulesElement=showPositionCount.positionButtonList[currentDateIndex].alreadyChooseRule
								alreadyChoosePositionP[choosePositionindex].chooseMarketRulesElement=chooseMarketRulesElement;
								currentIndex=choosePositionindex;
								alreadyChoosePositionP[choosePositionindex].currentIndex=currentIndex;
								positionIndexFlagParam=alreadyChoosePositionP[choosePositionindex].positionIndexFlag;
							}
						}
					}
					//弹层
					easyDialog.open({
						container : {
							header : '绑定规则',
							content : generateStruct('bindingMarketingRule',positionId,chooseMarketRulesElement,currentIndex,positionIndexFlagParam)
						},
						overlay : false
					});
					
                });
                
            }, 1);
	//添加删除事件
	}else if(eventType=='delete'){
		getAlreadyChoosePosition();
		setTimeout(function () {
                $(elementsName).attr('onclick', '').unbind('click').bind('click', function () { 
                	//定位到所选时间的索引位置
					var currentDateIndex = 'init';
					
					$(showPositionCount.positionButtonList).each(function(index, item){
						if(elementsName==item.removeButtom){
							currentDateIndex=index;
						}	
					});
						
					
					if(currentDateIndex!='init'){
						for (var choosePositionindex = startRow; choosePositionindex < endRow; choosePositionindex++) {
							if(choosePositionindex==(currentDateIndex+((currentPage - 1) * pageSize))){
								//alert('index1='+choosePositionindex);
								//标记
								var positionIndexFlagParam = alreadyChoosePositionP[choosePositionindex].positionIndexFlag;
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
								alreadyChoosePositionP.splice(choosePositionindex,1);
							}
						}
					}
					showPositionList4Page(1,6);
                });
            }, 1);
	}else if(eventType=='editorMarketRule'){
		getAlreadyChoosePosition();
		setTimeout(function () {
                $(elementsName).attr('onclick', '').unbind('click').bind('click', function () {
					
					var currentDateIndex = 'init';
                	var positionId = '';
                	
					$(showPositionCount.positionButtonList).each(function(index, item){
						if(elementsName==item.editorMarketRuleButtom){
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
						for (var choosePositionindex = startRow; choosePositionindex < endRow; choosePositionindex++) {
							if(choosePositionindex==(currentDateIndex+((currentPage - 1) * pageSize))){
								positionId=alreadyChoosePositionP[choosePositionindex].id;
								chooseMarketRulesElement=showPositionCount.positionButtonList[currentDateIndex].editorMarketRuleButtom;
								alreadyChoosePositionP[choosePositionindex].chooseMarketRulesElement=chooseMarketRulesElement;
								currentIndex=choosePositionindex;
								alreadyChoosePositionP[choosePositionindex].currentIndex=currentIndex;
								positionIndexFlagParam=alreadyChoosePositionP[choosePositionindex].positionIndexFlag;
								
								if((!$.isEmptyObject(alreadyChoosePositionP[choosePositionindex].marketRules))&&(alreadyChoosePositionP[choosePositionindex].marketRules.length>0)){								   
									alreadyChooseMarketRuleList4View=alreadyChoosePositionP[choosePositionindex].marketRules;
								}
							}
						}
					}
					
					//弹层
					easyDialog.open({
						container : {
							header : '查看并修改已绑定规则',
							content : generateStruct('viewAlreadyBindingPosition',positionId,chooseMarketRulesElement,currentIndex,positionIndexFlagParam)
						},
						overlay : false,
						callback : cleanUpBindingMarketingRule
					});
					
                });
                
            }, 1);
	}

}
/**
 * 回调方法，清除全局变量中的数据
 */
function cleanUpBindingMarketingRule(){
	alreadyChooseMarketRuleList4View=[];
	window.frames['viewAlreadyBindingPositionFrame'].alreadyChooseMarketRuleList=[];
}
function generateStruct(methodName,positionId,chooseMarketRulesElement,currentIndex,positionIndexFlagParam){
	
	var structInfo = '';
	
	
	if(methodName=='bindingCustomer'){
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="bindingCustomerFrame" name="bindingCustomerFrame" src="'+resourcePath+'/page/customer/adCustomerMgr_list.do?contractBindingFlag=1'+'" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	}else if(methodName=='bindingPosition'){
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="bingPositionFrame" name="bingPositionFrame" src="'+resourcePath+'/page/position/queryPositionPage.do?method=queryPage&contractBindingFlag=1'+'&positionIndexFlag='+positionIndexFlagParam+'&saveOrUpdateFlag=update" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	}else if(methodName=='bindingMarketingRule'){
		var url=resourcePath+'/page/marketingrule/listMarketingRule.do?method=listMarketingRule&contractBindingFlag=1&positionId='+positionId+'&currentIndex='+currentIndex+'&startRow='+startRow+'&endRow='+endRow+'&chooseMarketRulesElement='+escape(chooseMarketRulesElement)+'&positionIndexFlag='+positionIndexFlagParam+'&saveOrUpdateFlag=update';
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="bindingMarketingRuleFrame" name="bindingMarketingRuleFrame" src="'+url+'" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='					</div>';
	}else if(methodName=='viewAlreadyBindingPosition'){
		structInfo+='					<div style="margin:0px;padding:0px;">';
		structInfo+='							<iframe id="viewAlreadyBindingPositionFrame" name="viewAlreadyBindingPositionFrame" src="'+resourcePath+'/page/marketingRule/viewAlreadyBindingMarketingRule.jsp?chooseMarketRulesElement='+escape(chooseMarketRulesElement)+'&currentIndex='+currentIndex+'&startRow='+startRow+'&endRow='+endRow+'&currentPage='+currentPage+'&positionIndexFlag='+positionIndexFlagParam+'&saveOrUpdateFlag=update" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
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
	initPositionData();
	 //operationPositionDisplay(0);
	resourcePath=$('#projetPath').val();
	$("#system-dialog").hide();
	//添加合同有效期的开始日期和结束日期
	addTimePlugIn('#effectiveStartDate','#effectiveEndDate');
	//添加审批文号有效期的开始日期和结束日期
	addTimePlugIn('#approvalStartDate','#approvalEndDate');
	
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
				content : generateStruct('bindingPosition')
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
				content : generateStruct('bindingCustomer')
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
				content : generateStruct('bindingMarketingRule')
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
	
	//审核合同
	$('#approvalContractButton').click(function(){
			alreadyFillInForm.bindingPosition=alreadyChoosePositionP;
			var flag = beforeApprovalContract();
			//通过ajax方式提交至后台
			if(flag==true){
				$.ajax({
				url:"page/contract/approvalContract.do?method=approvalContract",
				type:"post",
				dataType:"json",
				timeout:900000,
				data:{contractParam:JSON.stringify(alreadyFillInForm)},
					success:function(strValue){
						var resultObject = strValue;
						if(resultObject.handleResult.resultFlag=="true"){
								message = '审核操作成功！';
								$("#content").html(message);
								$( "#system-dialog" ).dialog({
							      	modal: true,
							      	buttons:{
							      		'回到合同审核列表页':function(){
							      			var url='approvalListContract.do?method=approvalListPage';
							      			accessUrl(url);
							      		},
							      		'回到合同管理列表页':function(){
							      			var url='queryContractPage.do?method=queryPage';
							      			accessUrl(url);
							      		}
							      	},
							      	close: function() {
             							var url='approvalListContract.do?method=approvalListPage';
							      		accessUrl(url);
         							}
								});
						//处理失败
						}else if(resultObject.handleResult.resultFlag="error"){
							createDialog('处理过程出现异常！');
						}
						
					},
					error:function(strValue){
						createDialog('网络超时，请联系管理员！');
					}
				});	
			}		
		
	});

});
/**
 * 在提交审核前，先检查审核意见是否为空，审核按钮是否选择
 */
function beforeApprovalContract(){
		var flag = true;
		//初始化已选中的广告位参数
		getAlreadyChoosePosition();
		
		var examinationOpinions = $('#examinationOpinions').val().trim();
			
		if($.isEmptyObject(examinationOpinions)){
			createDialog('请录入审核意见!');
			flag = false;
		}else{
			alreadyFillInForm.examinationOpinions=examinationOpinions;
		}
		
		var approvalContractButtonChoose = checkradio();
		
		if($.isEmptyObject(approvalContractButtonChoose)){
			createDialog('请选择合同状态');
			flag = false;
		}else{
			alreadyFillInForm.status=checkradio();
		}	
		return flag;
}

/**
 * 关闭页面中所有表单控件，以供查看时使用
 */
function enableInputLable(){
	$("#contractNumber").attr('disabled',"disabled");
	$("#contractCode").attr('disabled',"disabled");
	$("#customerId").attr('disabled',"disabled");
	$("#contractName").attr('disabled',"disabled");
	$("#submitUnits").attr('disabled',"disabled");
	$("#financialInformation").attr('disabled',"disabled");
	$("#approvalCode").attr('disabled',"disabled");
	$("#otherContent").attr('disabled',"disabled");
	$("#effectiveStartDate").attr('disabled',"disabled");
	$("#effectiveEndDate").attr('disabled',"disabled");
	$("#approvalStartDate").attr('disabled',"disabled");
	$("#approvalEndDate").attr('disabled',"disabled");
	$("#bindingPosition").attr('disabled',"disabled");
	$("#bindingCustomer").attr('disabled',"disabled");
	//$("#examinationOpinions").attr('disabled',"disabled");
	$("#bindingTd").remove();
	$("#desc").attr('disabled',"disabled");
	
	$("#position0B").attr('disabled',"disabled");
	$("#position1B").attr('disabled',"disabled");
	$("#position2B").attr('disabled',"disabled");
	$("#position3B").attr('disabled',"disabled");
	$("#position4B").attr('disabled',"disabled");
	$("#position5B").attr('disabled',"disabled");
	
	$("#removeButtom0").attr('disabled',"disabled");
	$("#removeButtom1").attr('disabled',"disabled");
	$("#removeButtom2").attr('disabled',"disabled");
	$("#removeButtom3").attr('disabled',"disabled");
	$("#removeButtom4").attr('disabled',"disabled");
	$("#removeButtom5").attr('disabled',"disabled");
	
	$("#validStart0I").attr('disabled',"disabled");
	$("#validStart1I").attr('disabled',"disabled");
	$("#validStart2I").attr('disabled',"disabled");
	$("#validStart3I").attr('disabled',"disabled");
	$("#validStart4I").attr('disabled',"disabled");
	$("#validStart5I").attr('disabled',"disabled");
	
	$("#validEnd0I").attr('disabled',"disabled");
	$("#validEnd1I").attr('disabled',"disabled");
	$("#validEnd2I").attr('disabled',"disabled");
	$("#validEnd3I").attr('disabled',"disabled");
	$("#validEnd4I").attr('disabled',"disabled");
	$("#validEnd5I").attr('disabled',"disabled");
	
	$("#editorMarketRuleButtom0").attr('disabled',"disabled");
	$("#editorMarketRuleButtom1").attr('disabled',"disabled");
	$("#editorMarketRuleButtom2").attr('disabled',"disabled");
	$("#editorMarketRuleButtom3").attr('disabled',"disabled");
	$("#editorMarketRuleButtom4").attr('disabled',"disabled");
	$("#editorMarketRuleButtom5").attr('disabled',"disabled");

	$("#addContractButton").remove();
	$("#modifyContractButton").remove();
}