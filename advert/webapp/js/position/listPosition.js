var resourcePath='';
function accessUrl(url){
		window.location.href=url;
}
/**
 * 选择不同操作对应的不同方法
 * @param {} methodName
 * @param {} formName
 * @param {} positionId
 */
function operation(methodName,formName,positionId){
	switch(methodName){
		case 'deletePosition':
			  deletePosition(methodName,formName,positionId);
			  break;
		case 'modifyPosition':
			  modifyPosition(methodName,formName,positionId);
			  break;
		case 'viewOccupyStatesPosition':
			  viewOccupyStatesPosition(methodName,formName,positionId);
			  break;
		case 'setPositionStates':
			  setPositionStates(methodName,formName,positionId);
			  break;
		case 'viewPosition':
			  viewPosition(methodName,formName,positionId);
			  break;
	    default:
	          break;
	}
}
/**
 * 设置广告位状态
 * @param {} methodName 方法名称
 * @param {} formName 表单名称
 * @param {} positionId 广告位id
 */
function setPositionStates(methodName,formName,positionId){
	easyDialog.open({
			container : {
				header : '设置广告位状态',
				content : generatePositionStruct(methodName,positionId)
			},
			overlay : false
	});
}

function generatePositionStruct(methodName,positionId){
	
	var positionInfo = '';
	
	if(methodName=='viewOccupyStatesPosition'){
		positionInfo+='					<div style="margin:0px;padding:0px;">';
		positionInfo+='							<iframe src="'+resourcePath+'/page/position/queryPositionOccupyStates.do?method=queryPositionOccupyStates&positionId='+positionId+'" frameBorder="0" width="800px" height="400px"  scrolling="yes"></iframe>';
		positionInfo+='					</div>';
	}else if(methodName=='setPositionStates'){
		positionInfo+='					<div style="margin:0px;padding:0px;">';
		positionInfo+='							<iframe src="'+resourcePath+'/page/position/queryPositionOccupyStates.do?method=queryPositionOccupyStates&positionId='+positionId+'" frameBorder="0" width="800px" height="400px"  scrolling="yes"></iframe>';
		positionInfo+='					</div>';
	}
	return positionInfo;
}


/**
 * 删除广告位
 */
function deletePosition(methodName,formName,positionId){
	$( "#dialog-conform" ).dialog({
      modal: true,
      buttons: {
         '确认': function() {
          $( this ).dialog( "close" );
          	var url = 'page/position/batchRemove.do?method=batchRemove';
			
          }
      }
    });
}

/**
 * 修改广告位
 */
function modifyPosition(methodName,formName,positionId){
	var url='updatePositionPage.do?method=updatePage&positionId='+positionId;
	$(formName).attr("action",url);
	$(formName).submit();
}

/**
 * 查看广告位占用状态 弹层
 */
function viewOccupyStatesPosition(methodName,formName,positionId){
	/*var url='listPositionOccupyStatus.do?method=listPositionOccupyStatus';
	$(formName).attr("action",url);
	$(formName).submit();*/
	easyDialog.open({
			container : {
				header : '查看广告位占用状态',
				content : generatePositionStruct(methodName,positionId)
			},
			overlay : false
		});
}

/**
 * 查看广告位
 */
function viewPosition(methodName,formName,positionId){
	var url = 'page/position/viewPositionPage.do?method=viewPage';
	$(formName).attr("action",url);
	$(formName).submit();
}

$(function(){
	 $("#dialog-conform").hide();
	 resourcePath=$('#projetPath').val();
	 var options = {   
        contentType: "application/x-www-form-urlencoded;charset=utf-8"
    }; 
	
	 $("#positionTypeId").click(function(){
     	//alert('123');
     });
     $("#searchPositionSubmit").click(function(){
     	var positionTypeId = $("#positionTypeId").val();
     	positionTypeId = encodeURI(positionTypeId);   
		positionTypeId = encodeURI(positionTypeId);
     	var positionName = $("#positionNameS").val();
     	positionName = encodeURI(positionName);   
		positionName = encodeURI(positionName);
     	url='queryPositionPage.do?method=queryPage&positionTypeId='+positionTypeId+'&positionName='+positionName;
     	accessUrl(url);
     	return;
     });
     $("#addPositionSubmit").click(function(){
     	url='addPositionPage.do?method=addPage';
     	accessUrl(url);
     	return;
     });
});

/**
 * 获取访问地址
 */
function generateUrl(moduleName,position){
	var urlMap = "";
	return url+"&positionId="+position;
}
