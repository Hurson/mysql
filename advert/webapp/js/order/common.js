
/**
 * 显示双向策略信息
 * @param ployJson
 * @param flag 0 非审核  1 审核
 * @param ployId 策略主键ID
 * @param ployName 策略名称
 * @return
 */
function viewTwoPloy(ployJson,flag){
	var ployNumber = 0;
	var str = "";
	var precises = eval(ployJson);
	 if(precises!=null && precises!=''){
		ployNumber = precises.length;
		for(var i = 0;i<precises.length;i++){
			str +=precises[i].matchName;
			if(flag == 0){
				str += "<a style='diplay:block;float:right' href=javascript:showContent("+"'resource'"+",'"+precises[i].assetIds+"')>绑定素材</a>";
			}
		
			str += "<br>&nbsp;&nbsp;&nbsp;&nbsp;<span id='mp"+precises[i].assetIds+"'></span><br><br>";
		}
		var ggwHeight = precises.length*57;
		if(ggwHeight>240){
			$("#ggw").css("height","350px");
			$("#ggw").css("overflow","auto");
		}else{
			$("#ggw").css("height","240px");
		}
		/*
		if(precises.length>2){
			$("#ggw").css("height",(210+((precises.length-2)*60))+"px");
		}else{
			$("#ggw").css("height","240px");
		}
		*/
		//$("#selPloy").css("height",60+"px");
		var selPreciseHeight = precises.length*57;
		if(selPreciseHeight>240){
			$("#selPrecise").css("height",selPreciseHeight+"px");
		}else{
			$("#selPrecise").css("height","240px");
		}
//		$("#selPrecise").css("height",60*precises.length+"px");
		$("#selPrecise").html(str);
	}else{
		//$("#preciseli").hide();
		//$("#selPloy").css("height","250px");
		//$("#selPrecise").css("height","0px");
		$("#selPrecise").html(str);
	}
	 return ployNumber;
}

/**
 * 显示单向策略信息
 * @param ployJson
 * @param flag 0 非审核  1 审核
 * @return
 */
function viewOnePloy(ployJson,flag){
	var ployNumber = 0;
	var subPloy = eval(ployJson);
	 if(subPloy!=null && subPloy!=''){
	 		var str = "";
	 		ployNumber = subPloy.length;
	 		for(var i = 0;i<subPloy.length;i++){
	 			str +=subPloy[i].startTime+"-"+subPloy[i].endTime+"-"+subPloy[i].groupName+"-"+subPloy[i].priority;
	 			if(flag == 0){
	 				str += "<a style='diplay:block;float:right' href=javascript:showContent("+"'resource'"+","+"'1_"+subPloy[i].id+"')>绑定素材</a>";
	 			}
	 			//alert(subPloy[i].id);
	 			str += "<br>&nbsp;&nbsp;&nbsp;&nbsp;<span id='mp1_"+subPloy[i].id+"'></span><br><br>";
	 		}
	 		var ggwHeight = subPloy.length*57;
			if(ggwHeight>240){
				$("#ggw").css("height","350px");
				$("#ggw").css("overflow","auto");
			}else{
				$("#ggw").css("height","240px");
			}
	 		/*
	 		if(subPloy.length>2){
				$("#ggw").css("height",(90+((subPloy.length-2)*60))+"px");
			}else{
				$("#ggw").css("height","240px");
			}
			*/
			var selPreciseHeight = subPloy.length*57;
			if(selPreciseHeight>240){
				$("#selPrecise").css("height",selPreciseHeight+"px");
			}else{
				$("#selPrecise").css("height","240px");
			}
//			$("#selPrecise").css("height",60*subPloy.length+"px");
	 		$("#selPrecise").html(str);
	 }
	 return ployNumber;
}
function viewOneAreaPloy(result,orderCode,ployId,positionId){
	 var json=eval("("+result+")");
	    var str="<tr><td>策略详情</td><td>素材</td><td>操作</td></tr>";
	    if(result==null||json==null){
			   return;
		   }
	   for(var i = 0;i<json.length;i++){
	        var obj = json[i];
	        var resourcevalue=obj.resourceId;
		    str+="<tr><td>"+"<a style='diplay:block;float:left' href=javascript:showAreaResourceDetail("+orderCode+","+obj.resourceId+","+ployId+","+positionId+")>详情</a>"+"</td>"+
			 	 "<td>"+obj.resourceName+"</td>"+
		   		 "<td>"+"<a style='diplay:block;float:left' href=javascript:showSource("+"'resource'"+","+obj.resourceId+")>预览</a>"+"</td></tr>";
		   		 //"<td>"+"<a style='diplay:block;float:left' href=javascript:showSource("+"'resourcevalue'"+","+obj.resourceId+")>预览</a>"+" "+
		   		 
	  }
	$("#sucai").html(str);
	//$("#sucai").show();
}
function hidDetails(){
	$("#sucai").html('');
}
function showAreaResourceDetail(orderCode,resourceId,ployId,positionId){
	var url = "getAreaResourceDetail.do?order.ployId="+ployId+"&omRelTmp.orderCode="+orderCode+"&omRelTmp.mateId="+resourceId+"&order.positionId="+positionId;
	var value = window.showModalDialog(url, window, "dialogHeight=580px;dialogWidth=820px;center=1;resizable=0;status=0;");
 }
function showAreaResource(ployId,orderCode,positionId){
			 $.ajax({   
		 		       url:'getAreaResource.do',       
		 		       type: 'POST',    
		 		       dataType: 'text',   
		 		       data: {
		 		    	   orderId:orderCode
		 				},                   
		 		       timeout: 1000000000,                              
		 		       error: function(){                      
		 		    		alert("系统错误，请联系管理员！");
		 		       },    
		 		       success: function(result){ 
		 		    	   if(result == '-1'){
		 		    		  alert("系统错误，请联系管理员！");
		 		    	   }else{
		 		    	      viewOneAreaPloy(result,orderCode,ployId,positionId);
		 		    	   }
		 			   }  
		 		   });
			}


function showSource(url,metaId){
	$.ajax({   
	       url:'getAreaResourcePath.do',       
	       type: 'POST',    
	       dataType: 'text',   
	       data: {
	    	   metaId:metaId
			},                   
	       timeout: 1000000000,                              
	       error: function(){                      
	    		alert("系统错误，请联系管理员！");
	       },    
	       success: function(result){ 
	    	   if(result == '-1'){
	    		  alert("系统错误，请联系管理员！");
	    	   }else{
	    		   if(!isEmpty(result)){
	    			   //var ss=eval(result);
	    			   var resourceValue=metaId;	    			   
	    			   //alert(previewValue);
	    			   //alert(ss);	    		    	   
	    		    	//window.open('preview.do?perviewvalue='+perviewvalue,'','location=no,menubar=no,resizable=no,status=no,scrollbars=no,height=250px,width=430px');
	    		    	//window.open('preview.do?previewValue='+previewValue+'&resourceValue='+resourceValue,'','location=no,menubar=no,resizable=no,status=no,scrollbars=no,height=270px,width=446px');
	    		    	window.open('preview.do?previewValue='+previewValue+'&resourceValue='+resourceValue,'','location=no,menubar=no,resizable=no,status=no,scrollbars=no,height=240px,width=426px');
	    		    	//window.open(ss,'','location=no,menubar=no,resizable=no,status=no,scrollbars=no,height=400px,width=500px');
	    			}else{
	    				alert("素材不存在！");
	    			}
	    	   }
		   }  
	   });
	
}

/**
 * 显示已经选择的素材信息
 * @param materialJson
 * @return
 */
function viewMaterials(materialJson){
	var materials = eval(materialJson);
	if(materials!=null && materials!=''){
		var materialName = "";//素材名称
		var prePloyInfo = materials[0].type+"_"+materials[0].preciseId;//前一个精准/分策略类型_精准ID
		var ployInfo = "";//精准/分策略类型_精准ID
		for(var i = 0;i<materials.length;i++){
			ployInfo = materials[i].type+"_"+materials[i].preciseId;
			if(ployInfo == prePloyInfo){
				materialName += "<a href='javascript:viewMaterial("+materials[i].mateId+")'>"+materials[i].mateName+"</a>";
			}else{
				//显示前一个策略或精准对应的素材
				$("#mp"+prePloyInfo).html(materialName.substring(0,materialName.length-1));
				materialName = "<a href='javascript:viewMaterial("+materials[i].mateId+")'>"+materials[i].mateName+"</a>";
				prePloyInfo = ployInfo;
			}
			//存在插播位置
			if(materials[i].playLocation != null && materials[i].playLocation != ''){
				materialName +="[播放位置："+materials[i].playLocation+"]";
			}
			//存在轮询序号
			if(materials[i].pollIndex != null && materials[i].pollIndex != 0){
				materialName += "[轮询序号："+materials[i].pollIndex+"]"
			}
			//多个素材之间用逗号,分割
			materialName += ",";
		}
		$("#mp"+prePloyInfo).html(materialName.substring(0,materialName.length-1));
	}
}

/**
 * 显示已经选择的素材信息
 * @param materialJson
 * @return
 */
/*
function viewMaterials(materialJson){
	var materials = eval(materialJson);
	if(materials!=null && materials!=''){
		var materialName = "";//素材名称
		var pId = 0;
		for(var i = 0;i<materials.length;i++){
			if(materials[i].preciseId == pId){
				materialName += "<a href='javascript:viewMaterial("+materials[i].mateId+")'>"+materials[i].mateName+"</a>";
			}else{
				//显示前一个策略或精准对应的素材
				$("#mp"+pId).html(materialName.substring(0,materialName.length-1));
				materialName = "<a href='javascript:viewMaterial("+materials[i].mateId+")'>"+materials[i].mateName+"</a>";
				pId = materials[i].preciseId;
			}
			//存在插播位置
			if(materials[i].playLocation != null && materials[i].playLocation != ''){
				materialName +="[播放位置："+materials[i].playLocation+"]";
			}
			//存在轮询序号
			if(materials[i].pollIndex != null && materials[i].pollIndex != 0){
				materialName += "[轮询序号："+materials[i].pollIndex+"]"
			}
			//多个素材之间用逗号,分割
			materialName += ",";
		}
		$("#mp"+pId).html(materialName.substring(0,materialName.length-1));
	}
}
*/