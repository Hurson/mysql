$(function(){ 
   $("#addPositionTypeButton").click(function(){
     		if(!validateNull()){
     			$("#addPositionTypeForm").submit();
     		}
    });
     
      $("#queryPositionTypeButton").click(function(){
     		$("#queryPositionTypeForm").submit();
      });
     
});
/**
 * 绑定广告位类型
 * @param {} customerId
 */
function choosePositionType(positionTypeId){
	$("#qpositionType",window.parent.document).val(positionTypeId);
	parent.easyDialog.close();
}

function validateNull(){
	var flag = false

	if($.isEmptyObject($("#typeCodeAdd").val())){
		flag = true;
		alert('类型编码不能为空');
		$("#typeCodeAdd").focus();
		return flag;
	}
	
	if($.isEmptyObject($("#typeNameAdd").val())){
		flag = true;
		alert('类型名称不能为空');
		$("#typeNameAdd").focus();
		return flag;
	}	
}

/**
 * 选择不同操作对应的不同方法
 * @param {} methodName
 * @param {} formName
 * @param {} positionId
 */
function operation(methodName,formName,id,typeCode,typeName){
	switch(methodName){
		case 'deletePositionType':
			  deletePositionType(methodName,formName,id,typeCode,typeName);
			  break;
		case 'modifyPositionType':
			  modifyPositionType(methodName,formName,id,typeCode,typeName);
			  break;
	    default:
	          break;
	}
	
}

function deletePositionType(methodName,formName,id,typeCode,typeName){
	var url = 'page/positionType/remove.do?method=remove';
	$(formName).attr("action",url);
	$(formName).submit();
}

function modifyPositionType(methodName,formName,id,typeCode,typeName){
	$("#idAdd").val(id);
	$("#typeCodeAdd").val(typeCode);
	$("#typeNameAdd").val(typeName);
}