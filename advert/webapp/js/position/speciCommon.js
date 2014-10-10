function bindSubmit(formName) {
    	var options = {
            target: '#msgdlg',
            success: showResponse,
			error: showError

            // 其它可选参数: 
            //url:       url         // override for form's 'action' attribute 
            //type:      type        // 'get' or 'post', override for form's 'method' attribute 
            //dataType:  null        // 'xml', 'script', or 'json' (expected server response type) 
            //clearForm: true        // clear all form fields after successful submit 
            //resetForm: true        // reset the form after successful submit 

            // $.ajax 选项,例如超时: 
            //timeout:   3000 
        };

        $(formName).submit(function () {
            $(this).ajaxSubmit(options);
			$("#loading").show();
            return false;
        });
}

// 
function showResponse(responseText, statusText, xhr, $form) {
    $("#loading").hide();
	messagebox(responseText);
    bindSubmit();
}

function showError(xhr, ajaxOptions, thrownError) {
    $("#loading").hide();
	messagebox("出错了!" + thrownError);
    bindSubmit();
}

/**
 * 显示结果信息的对话框
 * @param {} msg 服务器端返回的数据，根据不同的数据类型进行不同的处理
 */
function messagebox(msg) {
	
	var responseMsg = eval('(' + msg + ')');
	if(responseMsg!=null){
		if(responseMsg.method=='save'){
			if(responseMsg.flag=='success'){
				$("#msgdlg").html('保存成功');
				//将添加按钮置为不可用状态
				//$("#addPictureMaterialSpeciButton").attr('disabled',"disabled");
				//直接将添加按钮删除
				removeAddButton(responseMsg.dataType);
				//先将更新内容的主键写入页面中
				fillPrimaryId(responseMsg);
				//fillPositionCollection(responseMsg);
			}else{
				$("#msgdlg").html('保存失败');
			}	
		}else if(responseMsg.method='update'){
			if(responseMsg.flag=='success'){
				$("#msgdlg").html('更新成功');
				//先将更新内容的主键写入页面中
				fillPrimaryId(responseMsg);
				//fillPositionCollection(responseMsg);
			}else{
				$("#msgdlg").html('更新失败');
			}
		}	
	}

    $("#dialog:ui-dialog").dialog("destroy");
    $("#msgdlg").dialog({
            modal: true,
            width: 300,
            height: 120,
            buttons: {
                确认: function () {
                    $(this).dialog("close");
                }
            }
    });
}

/**
 * @param responseMsg 服务器端的后台响应
 * @param dataType 从服务器端返回的数据类型，用于区分是 图片 视频 文字 调查问卷
 */
function fillPositionCollection(responseMsg){
	
	//1、根据进入的参数判断出是当前数据是高清还是标清
	var isHdFlag = getParamValue('isHdFlag');
	//2、将内容填充至对应的集合中
	// 2-1 先获取pictureMaterialSpeciId原始数据 此部分是按照类型 面向机器
	var speciIdObj = $(getObjectId(responseMsg.dataType),window.parent.document);
	var speciIdValue = speciIdObj.val();
	var speciIdValueNew = '';
	//面向 操作人员
	var speciIdObjShow = $(getObjectId(responseMsg.dataType+'Show'),window.parent.document);
	var speciIdValueShow = speciIdObjShow.val();
	var speciIdValueNewShow = '';
	
	// 2-1 将规格ID写入
	if($.isEmptyObject(speciIdValue.trim())){
		//面向服务器
		speciIdValue+=isHdFlag+":"+responseMsg.speciObject.id
		speciIdObj.val(speciIdValue);
		//面向操作人员
		speciIdValueShow+=getOperationDataTypeDesc(isHdFlag)+":"+'记录主键为'+responseMsg.speciObject.id
		speciIdObjShow.val(speciIdValueShow);
	}else if(speciIdValue.indexOf(isHdFlag)!=-1){
		
		//面向机器
		var alreadyValue = speciIdValue.split(";");
		for (var index = 0; index < alreadyValue.length; index++) {
			if((!$.isEmptyObject(alreadyValue[index]))&&(alreadyValue[index].indexOf(isHdFlag)==-1)){
				speciIdValueNew+=alreadyValue[index];
			}
		}
		
		if($.isEmptyObject(speciIdValueNew.trim())){
			speciIdValueNew+=isHdFlag+":"+responseMsg.speciObject.id;
		}else{
			speciIdValueNew+=";"+isHdFlag+":"+responseMsg.speciObject.id;
		}

		speciIdObj.val(speciIdValueNew);
		
		//面向操作人员
		var alreadyValueShow = speciIdValueShow.split(";");
		for (var index = 0; index < alreadyValueShow.length; index++) {
			if((!$.isEmptyObject(alreadyValueShow[index]))&&(alreadyValueShow[index].indexOf(isHdFlag)==-1)){
				speciIdValueNewShow+=alreadyValueShow[index];
			}
		}
		
		if($.isEmptyObject(speciIdValueNewShow.trim())){
			speciIdValueNewShow+=getOperationDataTypeDesc(isHdFlag)+":"+'主键为'+responseMsg.speciObject.id;
		}else{
			speciIdValueNewShow+=";"+getOperationDataTypeDesc(isHdFlag)+":"+'主键为'+responseMsg.speciObject.id;
		}

		speciIdObjShow.val(speciIdValueNewShow);
	}else if(speciIdValue=='0'){
		//面向机器
		speciIdValue='';
		speciIdValue+=isHdFlag+":"+responseMsg.speciObject.id
		speciIdObj.val(speciIdValue);
		//面向操作员
		if(speciIdValueShow.indexOf('还未选择')>=0){
			speciIdValueShow='';
			speciIdValueShow+=getOperationDataTypeDesc(isHdFlag)+":"+'主键为'+responseMsg.speciObject.id
			speciIdObjShow.val(speciIdValueShow);
		}else{
			speciIdValueShow+=";"+getOperationDataTypeDesc(isHdFlag)+":"+'主键为'+responseMsg.speciObject.id
			speciIdObjShow.val(speciIdValueShow);
		}
		
	}else{
		//面向机器
		speciIdValue+=";"+isHdFlag+":"+responseMsg.speciObject.id
		speciIdObj.val(speciIdValue);
		//面向操作员
		speciIdValueShow+=";"+getOperationDataTypeDesc(isHdFlag)+":"+'主键为'+responseMsg.speciObject.id
		speciIdObjShow.val(speciIdValueShow);
	}
	
	// 2-2 将规格ID和内容写入describe 此部分可以共用
	var describeChooseSpeciObj = $("#describeChooseSpeci",window.parent.document);
	var describeChooseSpeciValue = describeChooseSpeciObj.val();
	var describeChooseSpeciValueNew = '';
	// 2-2 将规格ID和内容写入describe
	if($.isEmptyObject(describeChooseSpeciValue.trim())){
		describeChooseSpeciValue+="数据类型:"+getOperationDataTypeDesc(responseMsg.dataType)+":"+getOperationDataTypeDesc(isHdFlag)+":"+responseMsg.speciObject.id
		describeChooseSpeciObj.val(describeChooseSpeciValue);
	}else if(describeChooseSpeciValue.indexOf(isHdFlag)!=-1){
		var alreadyValue = describeChooseSpeciValue.split(";");
		for (var index = 0; index < alreadyValue.length; index++) {
			if((!$.isEmptyObject(alreadyValue[index]))&&(alreadyValue[index].indexOf("数据类型:"+getOperationDataTypeDesc(responseMsg.dataType)+":"+getOperationDataTypeDesc(isHdFlag))==-1)){
				if($.isEmptyObject(describeChooseSpeciValueNew)){
					describeChooseSpeciValueNew+=alreadyValue[index];
				}else{
					describeChooseSpeciValueNew+=";\n"+alreadyValue[index];
				}
				
			}
		}
		if($.isEmptyObject(describeChooseSpeciValueNew)){
			describeChooseSpeciValueNew+="数据类型:"+getOperationDataTypeDesc(responseMsg.dataType)+":"+getOperationDataTypeDesc(isHdFlag)+":"+responseMsg.speciObject.id;
		}else{
			describeChooseSpeciValueNew+=";\n"+"数据类型:"+getOperationDataTypeDesc(responseMsg.dataType)+":"+getOperationDataTypeDesc(isHdFlag)+":"+responseMsg.speciObject.id;
		}
		describeChooseSpeciObj.val(describeChooseSpeciValueNew);
	}else{
		describeChooseSpeciValue+=";\n"+"数据类型:"+getOperationDataTypeDesc(responseMsg.dataType)+":"+getOperationDataTypeDesc(isHdFlag)+":"+responseMsg.speciObject.id;
		describeChooseSpeciObj.val(describeChooseSpeciValue);
	}
}

/**
 * @param responseMsg 服务器端的后台响应
 */
function fillPrimaryId(responseMsg){
	$("#id").val(responseMsg.speciObject.id);
}

/**
 * 当添加成功后删除添加按钮
 * @param responseMsg 服务器端的后台响应
 */
function removeAddButton(dataType){
	/*switch(dataType){
		case 'picture':
		$("#addImageSpecificationButton").remove();
		break;
		case 'content':
		$("#addTextSpecificationButton").remove();
		break;
		case 'video':
		$("#addVideoMaterialSpeciButton").remove();
		break;
		case 'question':
		$("#addQuestionMaterialSpeciButton").remove();
		break;
		default:break;
	}*/
	$("#addButton").remove();
}

/**
 * 根据对象类型获取ObjectId
 * @param {} dataType
 */
function getObjectId(dataType){
	switch(dataType){
		case 'picture':
		return '#imageRuleId';
		case 'pictureShow':
		return '#imageSpecificationIdShow';
		case 'content':
		return '#textRuleId';
		case 'contentShow':
		return '#contentMaterialSpeciIdShow';
		case 'video':
		return '#videoRuleId';
		case 'videoShow':
		return '#videoMaterialSpeciIdShow';
		case 'question':
		return '#questionRuleId';
		case 'questionShow':
		return '#questionMaterialSpeciIdShow';
	}
}
/**
 * 根据录入的数据类型返回中文描述
 * @param {} dataType 数据类型
 */
function getOperationDataTypeDesc(dataType){
	switch(dataType){
		case 'sd':
		return '标清';
		case 'hd':
		return '高清';
		case 'picture':
		return '图片';
		case 'content':
		return '文字'
		case 'video':
		return '视频';
		case 'question':
		return '调查问卷'
	}
}

