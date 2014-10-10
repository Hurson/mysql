/**
 * 以弹出框方式显示提示信息
 * @param {} showMessage 提示信息
 */
function createDialog(showMessage){
	$("#content").html(showMessage);
	$( "#system-dialog" ).dialog({
		  modal: true
	});
}

/**
 * 判断radio是否选中，并返回所选中的值
 */
function checkradio(){
	var item = $(":radio:checked");
	var len=item.length;
	if(len>0){
		return $(":radio:checked").val();
	}else{
		return "";
	}
}
/**
 * 弹出确认提示框
 * @param successPath
 */
function createDialogConfirm(message,successPath,type,elementName){
	$("#content").html(message);
	$( "#system-dialog" ).dialog({
      resizable: false,
      height:140,
      modal: true,
      buttons: {
        "确认": function() {
          $( this ).dialog( "close" );
          if(type=='redirect'){
          	accessPath(successPath);
          }else if(type=='form'){
          	submitForm(elementName,successPath)
          }
        },
        "取消": function() {
          $( this ).dialog( "close" );
        }
      }
    });
}

/**
 * 访问指定地址
 * @param {} path 待访问的地址
 */
function accessPath(path){
	window.location.href=path;
}

/**
 * 提交表单
 */
function submitForm(elementName,path){
	$(elementName).attr('action',path);
	$(elementName).submit();
}
/**
 * @param type 数据类型
 * 开启或者关闭form中的选项
 */
function enableInputLable(type){
	if(type=='position'){
		$("#characteristicIdentification").attr('disabled',"disabled");
		$("#positionName").attr('disabled',"disabled");
		$("#positionTypeId").attr('disabled',"disabled");
		$("#positionTypeName").attr('disabled',"disabled");
		
		$("#deliveryPlatform").attr('disabled',"disabled");
		$("#textRuleId").attr('disabled',"disabled");
		$("#videoRuleId").attr('disabled',"disabled");
		$("#imageRuleId").attr('disabled',"disabled");
		$("#questionRuleId").attr('disabled',"disabled");
		$("#isLoop").attr('disabled',"disabled");
		$("#deliveryMode").attr('disabled',"disabled");
		$("#isHd").attr('disabled',"disabled");
		$("#isAdd").attr('disabled',"disabled");
		$("#materialNumber").attr('disabled',"disabled");
		$("#coordinate").attr('disabled',"disabled");
		$("#price").attr('disabled',"disabled");
		$("#discount").attr('disabled',"disabled");
		$("#description").attr('disabled',"disabled");
		$("#describeChooseSpeci").attr('disabled',"disabled");
		$("#startTime").attr('disabled',"disabled");
		$("#endTime").attr('disabled',"disabled");
		$("#widthHeight").attr('disabled',"disabled");
		//删除页面中上传背景图按钮
		$("#removeBackGroundImageTd").remove();
		//删除页面中提交按钮
		$("#addPositionButton").remove();
	}else if(type=='contract'){
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
}

/**
 * 字符串数据转换为日期类型
 */
function dateStringTranformDateObject(dateString){
	return Date.parse(dateString);
}
/**
 * 中文字符转换
 * @param {} paramString
 * @return {}
 */
function chiParamTransform(paramString){
	return encodeURI(paramString);
}