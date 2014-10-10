var resourcePath='';
var alreadyChoosePtype='';
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
	}else if(methodName=='bindingPositionType'){
		positionInfo+='<div style="margin:0px;padding:0px;">';
		positionInfo+='	<iframe id="bindingPositionTypeFrame" name="bindingCustomerFrame" src="'+resourcePath+'/page/positionType/queryPtype.do?contractBindingFlag=1'+'" frameBorder="0" width="800px" height="400px"  scrolling="yes"></iframe>';
		positionInfo+='</div>';
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
          	var url = 'page/position/removePosition.do?method=remove';
			$(formName).attr("action",url);
			$(formName).submit();
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
	 $("#system-dialog").hide();
	 resourcePath=$('#projetPath').val();
	 $("#bm").find("tr:even").addClass("sec"); 
	 
	 $("#positionTypeName").click(function(){
     	easyDialog.open({
			container : {
				header : '绑定广告位类型',
				content : generatePositionStruct('bindingPositionType')
			},
			overlay : false
		});
     });
     $("#searchPositionSubmit").click(function(){
     	submitForm('searchForm','queryPositionPage.do?method=queryPage');
     	
     });
     $("#addPositionButton").click(function(){
     	url='addPositionPage.do?method=addPage';
     	accessUrl(url);
     	return;
     });
     
     $("#deletePositionButton").click(function(){
     	$( "#dialog-conform" ).dialog({
		      modal: true,
		      buttons: {
		         '确认': function() {
		          $(this).dialog( "close" );
		          	  var alreadyChoose = "";
					  $("input[name='checkBoxElement']").each(function(){
					        if($(this).is(':checked')){
					            alreadyChoose += $(this).val() + ",";
					        }
					  });
					  var url = resourcePath+'/page/position/batchRemovePosition.do?method=batchRemove&ids='+alreadyChoose;
					  accessUrl(url);
		          }
		      }
    	});
     });
    
});

/**
 * 获取访问地址
 */
function generateUrl(moduleName,position){
	var urlMap = "";
	return url+"&positionId="+position;
}

/**
 * 获取已选广告位类型
 */
function initPtype(){
	if((!$.isEmptyObject(alreadyChoosePtype))){
		$('#positionTypeId').val(alreadyChoosePtype.id);
		$('#positionTypeName').val(alreadyChoosePtype.positionTypeName);
	}
}

function generateAccess(currentPage,positionName,positionTypeId,positionTypeName,isHd,deliveryMode){
	var path = 'queryVideoManager.do?currentPage='+currentPage;
	if((!$.isEmptyObject(positionName))){
		$('#positionName').val(positionName);
	}
	if((!$.isEmptyObject(positionTypeId))){
		$('#positionTypeId').val(positionTypeId);
	}
	if((!$.isEmptyObject(positionTypeName))){
		$('#positionTypeName').val(positionTypeName);
	}
	if((!$.isEmptyObject(isHd))){
		$('#isHd').val(isHd);
	}
	if((!$.isEmptyObject(deliveryMode))){
		$('#deliveryMode').val(deliveryMode);
	}
	submitForm('#searchForm',path);
}