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
		case 'deleteContract':
			  deleteContract(methodName,formName);
			  break;
		case 'modifyContract':
			  modifyContract(methodName,formName);
			  break;
		case 'viewContract':
			  viewContract(methodName,formName);
			  break;
		case 'addContractPage':
			  addContractPage(methodName,formName);
			  break;
		case 'saveContract':
			  saveContract(methodName,formName);
			  break;
	    default:
	          break;
	}
}
/**
 * 页面跳转进入增加合同页面中
 * @param {} methodName
 * @param {} formName
 * @param {} contractId
 */
function addContractPage(methodName,formName){
	var addUrlPage  = resourcePath+'/page/contract/addContractPage.do?method=addPage';
	accessUrl(addUrlPage);
}
/**
 * 保存合同
 * @param {} methodName
 * @param {} formName
 * @param {} contractId
 */
function saveContract(methodName,formName){

}
/**
 * 删除合同
 * @param {} methodName
 * @param {} formName
 * @param {} contractId
 */
function deleteContract(methodName,formName){
	createDialogConfirm('是否继续',resourcePath+'/page/contract/removeContract.do?method=remove','form',formName);
}
/**
 * 修改合同
 * @param {} methodName
 * @param {} formName
 * @param {} contractId
 */
function modifyContract(methodName,formName){
	var updateUrlPage  = resourcePath+'/page/contract/updateContractPage.do?method=updatePage';
	//accessUrl(updateUrlPage);
	$(formName).attr('action',updateUrlPage);
	$(formName).submit();
}
/**
 * 查看合同详情
 * @param {} methodName
 * @param {} formName
 * @param {} contractId
 */
function viewContract(methodName,formName){
	var viewUrlPage  = resourcePath+'/page/contract/viewContractPage.do?method=viewPage';
	$(formName).attr('action',viewUrlPage);
	$(formName).submit();
}

$(function(){
	 resourcePath=$('#projetPath').val();
	$( "#effectiveStartDate" ).datepicker({
		defaultDate: "+1w",
		changeMonth: true,
		numberOfMonths: 3,
		onClose: function( selectedDate ) {
			$( "#effectiveEndDate" ).datepicker( "option", "minDate", selectedDate );
		}
	});
	$( "#effectiveEndDate" ).datepicker({
		defaultDate: "+1w",
		changeMonth: true,
		numberOfMonths: 3,
		onClose: function( selectedDate ) {
			$( "#effectiveStartDate" ).datepicker( "option", "maxDate", selectedDate );
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
		var searchUrl  = resourcePath+'/page/contract/page/contract/queryContractPage.do';
		$('#query').attr('action',searchUrl);
		$('#query').submit();
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
