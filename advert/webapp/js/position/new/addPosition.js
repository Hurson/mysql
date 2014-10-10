var channelAjaxContentResult = '';
var locationList='';
var locationCurrentPage='';
var locationTotalPage='';
var resourcePath='';
var fillInform = {};
var alreadyChoosePtype='';
/**
 * 删除广告位
 */
function deletePosition(positionId){
	accessPath('removePositionPage.do?method=removePage&positionId='+positionId);
}
/**
 * 
 * @param {} isHd hd/sd
 * @param {} type image/video/content/question
 * @param {} sdAndhd true/true
 * @param {} data 原始数据
 */
function fillSpeciInPage(isHd,type,sdAndhd,data){
	
	if(type=='image'){
		
		if(($.isEmptyObject(sdAndhd))||(sdAndhd!='true')){
			speciArray.image=[];
		}

		if(isHd=='hd'){
			var length = speciArray.image.length;
			var indexNow = 'init';
			$(speciArray.image).each(function(index,item){
				if(item.imageDataType==isHd){
					indexNow=index;
				}
				});
				
			if(indexNow!='init'){
				speciArray.image.splice(indexNow,1);
				speciArray.image.push(data);
			}else{
				speciArray.image.push(data);
			}
		}else if(isHd=='sd'){
			var length = speciArray.image.length;
			var indexNow = 'init';
			$(speciArray.image).each(function(index,item){
				if(item.imageDataType==isHd){
					indexNow=index;
				}
				});
				
			if(indexNow!='init'){
				speciArray.image.splice(indexNow,1);
				speciArray.image.push(data);
			}else{
				speciArray.image.push(data);
			}
		}
	}else if(type=='video'){
		if(($.isEmptyObject(sdAndhd))||(sdAndhd!='true')){
			speciArray.video=[];
		}

		if(isHd=='hd'){
			var length = speciArray.video.length;
			var indexNow = 'init';
			$(speciArray.video).each(function(index,item){
				if(item.videoDataType==isHd){
					indexNow=index;
				}
				});
				
			if(indexNow!='init'){
				speciArray.video.splice(indexNow,1);
				speciArray.video.push(data);
			}else{
				speciArray.video.push(data);
			}
		}else if(isHd=='sd'){
			var length = speciArray.video.length;
			var indexNow = 'init';
			$(speciArray.video).each(function(index,item){
				if(item.videoDataType==isHd){
					indexNow=index;
				}
				});
				
			if(indexNow!='init'){
				speciArray.video.splice(indexNow,1);
				speciArray.video.push(data);
			}else{
				speciArray.video.push(data);
			}
		}
	}else if(type=='content'){
		if(($.isEmptyObject(sdAndhd))||(sdAndhd!='true')){
			speciArray.content=[];
		}

		if(isHd=='hd'){
			var length = speciArray.content.length;
			var indexNow = 'init';
			$(speciArray.content).each(function(index,item){
				if(item.textDataType==isHd){
					indexNow=index;
				}
				});
				
			if(indexNow!='init'){
				speciArray.content.splice(indexNow,1);
				speciArray.content.push(data);
			}else{
				speciArray.content.push(data);
			}
		}else if(isHd=='sd'){
			var length = speciArray.content.length;
			var indexNow = 'init';
			$(speciArray.content).each(function(index,item){
				if(item.textDataType==isHd){
					indexNow=index;
				}
				});
				
			if(indexNow!='init'){
				speciArray.content.splice(indexNow,1);
				speciArray.content.push(data);
			}else{
				speciArray.content.push(data);
			}
		}
	}else if(type=='question'){
		if(($.isEmptyObject(sdAndhd))||(sdAndhd!='true')){
			speciArray.question=[];
		}

		if(isHd=='hd'){
			var length = speciArray.question.length;
			var indexNow = 'init';
			$(speciArray.question).each(function(index,item){
				if(item.questionDataType==isHd){
					indexNow=index;
				}
				});
				
			if(indexNow!='init'){
				speciArray.question.splice(indexNow,1);
				speciArray.question.push(data);
			}else{
				speciArray.question.push(data);
			}
		}else if(isHd=='sd'){
			var length = speciArray.question.length;
			var indexNow = 'init';
			$(speciArray.question).each(function(index,item){
				if(item.questionDataType==isHd){
					indexNow=index;
				}
				});
				
			if(indexNow!='init'){
				speciArray.question.splice(indexNow,1);
				speciArray.question.push(data);
			}else{
				speciArray.question.push(data);
			}
		}
	}
}

/**
 * 修改广告位
 */
function modifyPosition(positionId){
	accessPath('updatePositionPage.do?method=updatePage&positionId='+positionId);
}

/**
 * 查看广告位占用状态
 */
function viewOccupyStatesPosition(positionId){
	accessPath('positionUtilizationPage.do?method=utilizationConditionPage&positionId='+positionId);
}
/**
 * 查看广告位占用时间
 * @param {} positionId 
 */
function viewOccupyTimePosition(positionId){
	accessPath('positionOccupyTime.do?method=occupyTimePage&positionId='+positionId);
}

/**
 * 查看广告位
 */
function viewPosition(positionId){
	accessPath('viewPositionPage.do?method=viewPage&positionId='+positionId);
}

function generateStructView(methodName){
	
	var structInfo = '';
	if(methodName=='bindingPositionType'){
		structInfo+='<div style="margin:0px;padding:0px;">';
		structInfo+='	<iframe id="bindingPositionTypeFrame" name="bindingCustomerFrame" src="'+resourcePath+'/page/positionType/queryPtype.do?contractBindingFlag=1'+'" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='</div>';
	}else if(methodName=='viewChooseRule'){
		structInfo+='<div style="margin:0px;padding:0px;">';
		structInfo+='	<iframe id="viewChooseRuleFrame" name="viewChooseRuleFrame" src="'+resourcePath+'/page/position/new/alreadyChooseSpeci.jsp'+'" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		structInfo+='</div>';
	}
	
	return structInfo;
}


$(function(){
	 $("#bm").find("tr:even").addClass("sec");  
	 resourcePath=$('#projetPath').val();
     /**
      * 广告位类型
      */
     $("#positionTypeName").click(function(){
     	easyDialog.open({
			container : {
				header : '绑定广告位类型',
				content : generateStructView('bindingPositionType')
			},
			overlay : false
		});
     });

     /**
      * 是否轮询
      */
     $("#isLoop").change(function(){
     	
     });
     /**
      * 投放方式
      */
     $("#deliveryMode").change(function(){
     	//alert('advertiseWay');
     });
     
     $("#viewChooseRule").click(function(){
     	easyDialog.open({
			container : {
				header : '查看已选规格',
				content : generateStructView('viewChooseRule')
			},
			overlay : false
		});
     });
     
     $("#isHd").change(function(){
     	//先判断是否已选择广告位类型，如果为选择，则提示先选择广告位类型
		if($.isEmptyObject(alreadyChoosePtype)){
			//先设置为默认值
			$("#isHd").val(-1);
			createDialog('请先选择广告位类型');
			return;
		}   	
     	fillInform.isHd=$("#isHd").val();
     	isHdMap($("#isHd").val());
     });
     
     $("#isAdd").change(function(){
     	
     });
         
     $("#addPositionButton").click(function(){
     		
     		var id = $('#id').val();
     		fillInform.id=id;
     		
     		var positionName = $('#positionName').val();
     		if($.isEmptyObject(positionName)){
     			createDialog('广告位名称不能为空');
     			return;
     		}else{
     			fillInform.positionName=positionName;
     		}
     		
     		/*var discount = $('#discount').val();
     		if($.isEmptyObject(discount)){
     			createDialog('折扣不能为空');
     			return;
     		}else{
     			fillInform.discount=discount;
     		}*/
     		
     		var deliveryMode = $('#deliveryMode').val();
     		if($.isEmptyObject(deliveryMode)){
     			createDialog('投放方式不能为空');
     			return;
     		}else{
     			fillInform.deliveryMode=deliveryMode;
     		}
     		
     		var isHd = $('#isHd').val();
     		if(($.isEmptyObject(deliveryMode))||($("#isHd").val()==-1)){
     			createDialog('是否高清不能为空');
     			return;
     		}else{
     			fillInform.isHd=$("#isHd").val();
     		}
     		
     		var isAdd = $('#isAdd').val();
     		if($.isEmptyObject(isAdd)&&(isAdd!=0)){
     			createDialog('请选择是否叠加');
     			return;
     		}else{
     			fillInform.isAdd=isAdd;
     		}
     		
     		var positionTypeId = $('#positionTypeId').val();
     		if($.isEmptyObject(positionTypeId)){
     			createDialog('请选择广告位类型');
     			return;
     		}else{
     			fillInform.positionTypeId=positionTypeId;
     		}
     		
     		var positionTypeCode = $('#positionTypeCode').val();
     		if($.isEmptyObject(positionTypeCode)){
     			createDialog('缺少广告位类型码');
     			return;
     		}else{
     			fillInform.positionTypeCode=positionTypeCode;
     		}
     		
     		var isLoop = $('#isLoop').val();
     		
     		if($.isEmptyObject(isLoop)){
     			createDialog('请选择是否轮询');
     			return;
     		}else{
     			fillInform.isLoop=isLoop;
     		}
     		
     		var coordinate = $('#coordinate').val();
     		if($.isEmptyObject(coordinate)){
     			createDialog('请输入广告位图片坐标');
     			return;
     		}else{
     			if(validateWidthCoordinate('coordinate',coordinate)){
     				createDialog('广告位图片坐标格式不正确!');
     				return;
     			}else{
     				fillInform.coordinate=coordinate;
     			}
     		}
     		
     		var price = $('#price').val();
     		if($.isEmptyObject(price)){
     			createDialog('请输入价格');
     			return;
     		}else{
     			fillInform.price=price;
     		}
     		
     		var materialNumber = $('#materialNumber').val();
     		if($.isEmptyObject(materialNumber)){
     			createDialog('请输入素材个数');
     			return;
     		}else{
     			fillInform.materialNumber=materialNumber;
     		} 		
     		
     		var widthHeight = $('#widthHeight').val();
     		if($.isEmptyObject(widthHeight)){
     			createDialog('请录入宽高');
     			return;
     		}else{
     			if(validateWidthCoordinate('widthHeight',widthHeight)){
     				createDialog('宽高格式不正确！');
     				return;
     			}else{
     				fillInform.widthHeight=widthHeight;
     			}
     		}
     		
     		var backgroundPath = $('#backgroundPath').val();
     		if($.isEmptyObject(backgroundPath)){
     			createDialog('请上传素材');
     			return;
     		}else{
     			fillInform.backgroundPath=backgroundPath;
     		}
     		
     		var text = speciArray.content;
     		if((!$.isEmptyObject(text))){
     			var textRuleId = '';
     			var length = text.length;
     			$(speciArray.content).each(function(contentIndex,contentItem){
     				var textDataType = contentItem.textDataType;
					var id = contentItem.id;
     				if(textDataType=='sd'){
     					textRuleId+='sd:'+id;
     				}else if(textDataType=='hd'){
     					textRuleId+='hd:'+id;
     				}
     				if(contentIndex<length-1){
     					textRuleId+=';';
     				}	
				});
     		}
     		
     		if(!$.isEmptyObject(textRuleId)){
     			fillInform.textRuleIdList=textRuleId;
     		}else {
     			fillInform.textRuleIdList=0;
     		}
     		
     		var image = speciArray.image;
     		
     		if((!$.isEmptyObject(image))){
     			var length = image.length;
     			var imageRuleId = '';
     			$(image).each(function(imageIndex,imageItem){
					var imageId = imageItem.imageId;
					var imageDataType = imageItem.imageDataType;
					if(imageDataType=='sd'){
     					imageRuleId+='sd:'+imageId;
     					imageRuleId+=';';
     				}else if(imageDataType=='hd'){
     					imageRuleId+='hd:'+imageId;	
     					imageRuleId+=';';
     				}
     				if(imageIndex<length-1){
     					imageRuleId+=';';
     				}
					
				});
     		}
     		
     		if(!$.isEmptyObject(imageRuleId)){
     			fillInform.imageRuleIdList=imageRuleId;
     		}else {
     			fillInform.imageRuleIdList=0;
     		}
     		
     		var video = speciArray.video;
     		if((!$.isEmptyObject(video))){
     			var videoRuleId = '';
     			var length = video.length;
     			$(video).each(function(videoIndex,videoItem){
					var videoDataType=videoItem.videoDataType;
					var id=videoItem.id;
					if(videoDataType=='sd'){
     					videoRuleId+='sd:'+id;
     					videoRuleId+=';';
     				}else if(videoDataType=='hd'){
     					videoRuleId+='hd:'+id;
     					videoRuleId+=';';
     				}
     				
     				if(videoIndex<length-1){
     					videoRuleId+=';';
     				}
     				
				});
     		}
     		
     		if(!$.isEmptyObject(videoRuleId)){
     			fillInform.videoRuleIdList=videoRuleId;
     		}else {
     			fillInform.videoRuleIdList=0;
     		}
     		
     		var question = speciArray.question;
     		if((!$.isEmptyObject(question))){
     			var questionRuleId='';
     			var length = question.length;
     			$(question).each(function(questionIndex,questionItem){
					var id = questionItem.id;
					var questionDataType=questionItem.questionDataType;
					if(questionDataType=='sd'){
     					questionRuleId+='sd:'+id;
     					questionRuleId+=';';
     				}else if(questionDataType=='hd'){
     					questionRuleId+='hd:'+id;	
     					questionRuleId+=';';
     				}
     				
     				if(questionIndex<length-1){
     					questionRuleId+=';';
     				}
				});
     		}
     		
     		if(!$.isEmptyObject(questionRuleId)){
     			fillInform.questionRuleIdList=questionRuleId;
     		}else {
     			fillInform.questionRuleIdList=0;
     		}
     		
     		$.ajax({
				url:"page/position/addPosition.do?method=save",
				type:"post",
				dataType:"json",
				timeout:900000,
				data:{positionParam:JSON.stringify(fillInform)},
					success:function(strValue){
						var resultObject = strValue;
						if(resultObject.flag=="true"){
								message = '保存成功！';
								$("#content").html(message);
								$( "#system-dialog" ).dialog({
							      	modal: true,
							      	buttons:{
							      		'回到列表页':function(){
							      			accessPath('queryPositionPage.do?method=queryPage');
							      		}
							      	},
							      	close: function() {
							      		accessPath('queryPositionPage.do?method=queryPage');
         							}
								});
						//处理失败
						}else if(resultObject.flag=="false"){
							
							if(resultObject.cause=="characteristicIdentificationExist"){
								createDialog('特征值已存在！');
							
							}else if(resultObject.cause=="validateFailure"){
								createDialog('数据校验失败！');
							
							}else if(resultObject.cause=="saveError"){
								createDialog('保存失败！');
							
							}else if(resultObject.cause=="updateError"){
								createDialog('更新失败！');
							
							}else if(resultObject.cause=="parseError"){
								createDialog('数据解析失败！');
							}
						
						}else if(resultObject.cause="error"){
							createDialog('处理过程出现异常！');
						}
						
					},
					error:function(strValue){
						createDialog('网络超时，请联系管理员！');
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

function validateNull(){
	return false;
	/*var flag = false

	if($.isEmptyObject($("#eigenValue").val())){
		flag = true;
		alert('特征值不能为空');
		$("#eigenValue").focus();
		return flag;
	}
	
	if($.isEmptyObject($("#positionName").val())){
		flag = true;
		alert('广告位名称不能为空');
		$("#positionName").focus();
		return flag;
	}
	
	
	if($("#isPolling").val()==1){
		if($.isEmptyObject($("#pollingCount").val())){
			flag = true;
			alert('轮询个数不能为空');
			$("#pollingCount").focus();
			return flag;
		}
	}else{
		$("#pollingCount").val(0);
	}
	
	if($.isEmptyObject($("#coordinate").val())){
		flag = true;
		alert('图片坐标不能为空');
		$("#coordinate").focus();
		return flag;
	}
	
	if($.isEmptyObject($("#price").val())){
		flag = true;
		alert('价格不能为空');
		$("#price").focus();
		return flag;
	}
	
	if($.isEmptyObject($("#discount").val())){
		flag = true;
		alert('折扣不能为空');
		$("#discount").focus();
		return flag;
	}
	
	if($.isEmptyObject($("#backgroundImage").val())){
		flag = true;
		alert('背景图不能为空');
		$("#backgroundImage").focus();
		return flag;
	}
	
	if(($.isEmptyObject($("#describe").val()))||$("#describe").val().trim()==''){
		flag = true;
		alert('描述不能为空');
		$("#describe").focus();
		return flag;
	}	*/
}
/**
 * 对录入格式进行校验 数字 包含节点有 pollingCount price discount
 */
function validateNumber(){
	return false;
	/*var flag = false;
	//只能是数字，不能包含小数
	var regInt = new RegExp("^[0-9]*$");
	var pollingCountValue = $("#pollingCount").val();
	if(!regInt.test(pollingCountValue)){
		flag = true;
		alert('轮询个数只能是数字');
		$("#pollingCount").focus();
		return flag;
	}
	//验证有1-3位小数的正实数
	var regFloat = new RegExp("^[0-9]+(.[0-9]{1,3})?$");
	//可包含小数
	var priceValue = $("#price").val();
	if(!regFloat.test(priceValue)){
		flag = true;
		alert('价格只能是包含一到三位小数的正实数');
		$("#price").focus();
		return flag;
	}
	//可包含小数
	var discountValue = $('#discount').val();
	if(!regFloat.test(discountValue)){
		flag = true;
		alert('折扣只能是包含一到三位小数的正实数');
		$("#discount").focus();
		return flag;
	}
	return flag;*/
}

/**
 * 对录入格式进行校验 字母 数字 下划线 广告位特征值
 */		
function validateCharacter(){
	var flag = false;
	/*var regWord = new RegExp("/^[0-9a-zA-Z]{6,20}$");
	var eigenValueValue = $('#eigenValue').val();
	if(!regWord.test(eigenValueValue)){
		alert('广告位特征值只能包含字母、数字！');
		$('#eigenValue').focus();
		flag = true;
	}*/
	return flag;	
}
/**
 * 只能包含字母、数字、下划线
 * @return {}
 */
function validateCharacterHanzi(){
	var flag = false;
	/*var regWord = new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5]+$");
	var positionNameValue = $('#positionName').val();
	if(!regWord.test(positionNameValue)){
		alert('广告位名称只能包含字母、数字、下划线！');
		$('#positionName').focus();
		flag = true;
	}*/
	return flag;	
}
/**
 * 校验宽高
 */
function validateWidthCoordinate(type,param){
	var flag = false;
	var splitKey = "*";
	//此处可改为正则实现
	var regWord = new RegExp("^[0-9]+$")
	param=param.split(splitKey);
	if('widthHeight'==type){
		if(param.length!=2){
			$('#widthHeight').focus();
			flag = true;
			return flag;
		}else{
			if(!regWord.test(param[0])){
				flag = true;
				return flag;
			}
			
			if(!regWord.test(param[1])){
				flag = true;
				return flag;
			}
		}
	}else if('coordinate'==type){
		if(param.length!=2){
			$('#coordinate').focus();
			flag = true;
		}else{
			if(!regWord.test(param[0])){
				flag = true;
				return flag;
			}
			
			if(!regWord.test(param[1])){
				flag = true;
				return flag;
			}
		}
	}
	return flag;	
}
