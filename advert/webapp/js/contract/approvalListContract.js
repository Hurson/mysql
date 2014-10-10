var resourcePath='';
/**
 * 直接访问
 * @param {} url
 */
function accessUrl(url){
		window.location.href=url;
}

/**
 * 选择不同操作对应的不同方法
 * @param {} methodName
 * @param {} formName
 * @param {} positionId
 */
function operation(methodName,formName){
	switch(methodName){
		case 'approvalContract':
			  approvalContract(methodName,formName);
			  break;
	    default:
	          break;
	}
}

/**
 * 审核合同进入页面
 * @param {} methodName
 * @param {} formName
 * @param {} contractId
 */
function approvalContract(methodName,formName){
	var viewUrlPage  = resourcePath+'/page/contract/approvalContractPage.do?method=approvalContractPage';
	$(formName).attr('action',viewUrlPage);
	$(formName).submit();
}

$(function(){
	 resourcePath=$('#projetPath').val();
	$( "#qeffectiveStartDate" ).datepicker({
		defaultDate: "+1w",
		changeMonth: true,
		numberOfMonths: 3,
		onClose: function( selectedDate ) {
			$( "#qeffectiveEndDate" ).datepicker( "option", "minDate", selectedDate );
		}
	});
	$( "#qeffectiveEndDate" ).datepicker({
		defaultDate: "+1w",
		changeMonth: true,
		numberOfMonths: 3,
		onClose: function( selectedDate ) {
			$( "#qeffectiveStartDate" ).datepicker( "option", "maxDate", selectedDate );
		}
	});
	$("#positionTypeName").click(function(){
		easyDialog.open({
			container : {
				header : '选择广告位类型',
				content : generateStructContract('bindingPositionType')
			},
			overlay : false
		});
	});
	
	$( "#startTime" ).datepicker({
		defaultDate: "+1w",
		changeMonth: true,
		numberOfMonths: 3,
		onClose: function( selectedDate ) {
			$( "#startTime" ).datepicker( "option", "minDate", selectedDate );
		}
	});
	$( "#endTime" ).datepicker({
		defaultDate: "+1w",
		changeMonth: true,
		numberOfMonths: 3,
		onClose: function( selectedDate ) {
			$( "#endTime" ).datepicker( "option", "maxDate", selectedDate );
		}
	});
	
	/**
	 * 为搜索按钮添加onclick事件
	 */
	$("#searchContractButton").click(function(){
		var qcustomerName = $('#qcustomerName');
		var qpositionName = $('#qpositionName');
		var qeffectiveStartDate = $('#qeffectiveStartDate');
		var qeffectiveEndDate = $('#qeffectiveEndDate');
		var qpositionType = $('#qpositionType');
		var status = $('#status');
	});
	/**
	 * 为添加按钮添加onclick事件
	 */
	$("#addContractButton").click(function(){
		operation('addContractPage','','');
	});
	
	
});

function generateStructContract(methodName,positionId,chooseMarketRulesElement,currentIndex){
	
	var structInfo = '';
	if(methodName=='bindingPositionType'){
		structInfo+='<div style="margin:0px;padding:0px;">';
		structInfo+='		<iframe id="bindingPositionType" name="bindingPositionType" src="'+resourcePath+'/page/positionType/queryPtype.do?contractBindingFlag=1'+'" frameBorder="0" width="800px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='</div>';
	}
	return structInfo;
}
