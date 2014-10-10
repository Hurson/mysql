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

$(function(){
	 
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
