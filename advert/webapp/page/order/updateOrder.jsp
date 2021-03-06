<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.dvnchina.advertDelivery.utils.ConfigureProperties"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type='text/javascript' src="<%=path%>/js/new/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" />
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/preview.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/common.js"></script>
<title>修改订单</title>
<style>
	.ggw {
		width: 48%;
		/*height: 268px;*/
		color: #000000;
		float: left;
		border: 1px dashed #CCCCCC;
	}
	
	
	.ggw li {
		background: #efefef;
		font-weight: bold;
		width: 100%;
		height: 25px;
	}
	.e_input_add {
		background:url(<%=path%>/images/add.png) right no-repeat #ffffff;
	}
	
	.e_input_time{
		background:url(<%=path%>/js/new/My97DatePicker/skin/datePicker.gif) right no-repeat #ffffff;
	}
</style>
<script type="text/javascript">
	//var contractId = -1;
	var positionId = -1;
	var ployId = 0;
	var validMinDate = '';//广告位投放开始日期
	var validMaxDate = '';//广告位投放结束日期
	var ployStartTime = '';//策略开始时间
	var ployEndTime = '';//策略结束时间
	var deliveryMode = 1;//投放方式
	var selResource = '';//选择的素材
	var aheadTime=0;//提前量
	var playNumber = 0;//按次播放次数
	var ployNumber = 1;//分策略或基本策略加精准总数
	//var previewValue='${position.id}_${position.positionName}_${position.validStart}_${position.validEnd}_${position.deliveryMode}_${position.backgroundPath}_${position.coordinate}_${position.widthHeight}';
	var previewValue='${previewValue}';
	//window.onload = function(){	
	 //   alert("aaa");
 	//	previewValue=$("#positionId").val();
 	//	alert(previewValue);
	//} 
	function init(time){
	   //alert($("#positionName").val());
	  //previewValue=document.getElementByName("preV").value;
		aheadTime = time;
		positionId = $("#positionId").val();
		ployId = $("#ployId").val();
		validMinDate = $("#validStart").val();
		validMaxDate = $("#validEnd").val();
		if(isEmpty(validMinDate)){
			validMinDate="2010-01-01";
		}
		if(isEmpty(validMaxDate)){
			validMaxDate="2100-01-01";
		}
		ployStartTime = $("#ployStartTime").val();
		ployEndTime = $("#ployEndTime").val();
		deliveryMode = $("#orderType").val();
		selResource = $("#selResource").val();
		playNumber = $("#playNumber").val();
		if(playNumber<=0){
			$("#periodDate").show();
			$("#onceDate").hide();
		}else{
			$("#onceDate").show();
			$("#periodDate").hide();
		}
		//问卷广告位，订单添加用户次数、问卷次数、通知门限值、积分兑换人民币比率
		if(positionId=='27'||positionId=='28'){
			$("#questionnaire1").show();
			$("#questionnaire2").show();
		}
		
		showAreaResource(ployId,'${order.orderCode}',positionId);
/*
		if(adPackageType == 0 || adPackageType == 1){
			var ployName = $("#ployName").val();
			//显示双向策略信息
			ployNumber = viewTwoPloy(ployJson,0,ployId,ployName);
		}else{
			//显示单向策略信息
			ployNumber = viewOnePloy(ployJson,0);
		}
		*/
		//显示已经选择的素材信息
		//viewMaterials(materialJson);

		//预览素材
		//var selPosition = eval(positionJson);
		//preview(selPosition.backgroundPath,selPosition.coordinate,selPosition.widthHeight);
	}

	/**
	*  进入弹出页面前，先检查会话是否过期
	*/
	function showContent(content,pId){
    	$.ajax({   
 	       url:'checkSession.do',       
 	       type: 'POST',    
 	       dataType: 'text',   
 	       data: {
 		   },                   
 	       timeout: 1000000000,                             
 	       error: function(){                      
 	    		alert("系统错误，请联系管理员！");
 	       },    
 	       success: function(result){
 	    	   if(result=='0'){
 	 	 	      if(content == 'ploy'){ 	 	 	      
 	 	    		showPloy();
 	 	    	  }else if(content == 'resource'){
 	 	    		showResource(pId);
 	 	    	  }
 	    	   }else{
 	 	    	   //会话已经过期
 	    		  window.location.href=getContextPath()+'/tset/timeoutPage.jsp'
 	 	    	}	    	   
 		   }  
 		}); 
    }
    

  	//选择策略
    function showPloy(){
    	var ployId = document.getElementById("ployId").value;
    	var url = "showPloyList.do?ploy.positionId="+positionId+"&order.ployId="+ployId;
    	var value = window.showModalDialog(url, window, "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
        var orderId=${order.orderCode};
        if(value){
            if(ployId != value.split("_")[0]){
            	ployId = value.split("_")[0];
            	ployName = value.split("_")[1];
	        	$("#ployId").val(ployId);
				$("#ployName").val(ployName);
				playNumber = value.split("_")[4];//播放次数
				if(playNumber && playNumber>0){
					$("#playNumber").val(playNumber);
					$("#periodDate").hide();
					$("#onceDate").show();
				}else{
					$("#periodDate").show();
					$("#onceDate").hide();
				}
				/*
				var adPackageType = value.split("_")[2];
				if(adPackageType == 0 || adPackageType == 1){				
					//双向广告位策略
					$.ajax({   
			 		       url:'showPrecises4Json.do',       
			 		       type: 'POST',    
			 		       dataType: 'text',   
			 		       data: {
			 		    	   id:ployId
			 				},                   
			 		       timeout: 1000000000,                              
			 		       error: function(){                      
			 		    		alert("系统错误，请联系管理员！");
			 		       },    
			 		       success: function(result){ 
			 		    	   if(result == '-1'){
			 		    		  alert("系统错误，请联系管理员！");
			 		    	   }else{
			 		    		  ployNumber = viewTwoPloy(result,0);
			 		    	   }
			 			   }  
			 		   });
				}else{
					//单向广告位策略
					$.ajax({   
			 		       url:'getSubPloyJson.do',       
			 		       type: 'POST',    
			 		       dataType: 'text',   
			 		       data: {
			 		    	   id:ployId
			 				},                   
			 		       timeout: 1000000000,                              
			 		       error: function(){                      
			 		    		alert("系统错误，请联系管理员！");
			 		       },    
			 		       success: function(result){ 
			 		    	   if(result == '-1'){
			 		    		  alert("系统错误，请联系管理员！");
			 		    	   }else{
			 		    		  ployNumber = viewOnePloy(result,0);
			 		    	   }
			 			   }  
			 		   });
				}
				$("#selResource").val("");*/
				//选择策略，保存订单和素材临时关系数据
				
				var orderCode = document.getElementById("orderCode").value;
				$.ajax({   
		 		       url:'insertOrderMateRelTmp2.do',       
		 		       type: 'POST',    
		 		       dataType: 'text',   
		 		       data: {
		 		    	   orderCode:orderCode,
		 		    	   ployId:ployId,
		 		    	   positionId:positionId
		 				},                   
		 		       timeout: 1000000000,                              
		 		       error: function(){                      
		 		    		alert("系统错误，请联系管理员！");
		 		       },    
		 		       success: function(result){ 
		 		    	   if(result == '-1'){
		 		    		  alert("系统错误，请联系管理员！");
		 		    	   }
		 			   }  
		 		   });
            }
        }
        hidDetails();
    }

    function showResource(pId){
    	var index = selResource.indexOf(pId+"#");
    	var selectResource = '';
    	var resStr = '';
        if(index >= 0){
        	var index2 = selResource.indexOf("~",index);
        	var selectResource = selResource.substring(index,index2);
        	var relsArray = selectResource.split("#");
        	resStr = relsArray[1];
				
        }
		var url = "showResourceList.do?resource.advertPositionId="+positionId+"&order.selResource="+resStr+"&ployId="+$("#ployId").val();
		var resourceArray = window.showModalDialog(url, window, "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
		var materialJson= "";
		if(resourceArray && resourceArray != null) {
            if(resourceArray.length>0){
	        	var materialName = "";
				for(var i=0;i<resourceArray.length;i++){
					var inStreamArray = resourceArray[i].inStreamArray;
					if(inStreamArray && inStreamArray.length>0){
						for(var j=0;j<inStreamArray.length;j++){
							materialJson += resourceArray[i].resourceId+","+resourceArray[i].mLoopsValue+","+inStreamArray[j]+"@";
							materialName += "<a href='javascript:viewMaterial("+resourceArray[i].resourceId+")'>"+resourceArray[i].resourceName+"</a>";
							if(inStreamArray[j] != '-1'){
								materialName +="[播放位置："+inStreamArray[j]+"]";
							}
							if(resourceArray[i].mLoopsValue != '-1'){
								materialName += "[轮询序号："+resourceArray[i].mLoopsValue+"]"
							}
							materialName += ",";
						}
					}else{
						materialJson += resourceArray[i].resourceId+","+resourceArray[i].mLoopsValue+",-1@";
						materialName += "<a href='javascript:viewMaterial("+resourceArray[i].resourceId+")'>"+resourceArray[i].resourceName+"</a>";
						if(resourceArray[i].mLoopsValue != '-1'){
							materialName += "[轮询序号："+resourceArray[i].mLoopsValue+"]"
						}
						materialName += ",";
					}
				}
				index = selResource.indexOf(pId+"#");
	            if(index >= 0){
	            	index2 = selResource.indexOf("~",index);
	            	selResource = selResource.substring(0,index)+selResource.substring(index2+1);
	            }
	            selResource = selResource+pId+"#"+materialJson+"~";
	            //$("#selResource").val(selResource);
	            $("#mp"+pId).html(materialName.substring(0,materialName.length-1));
            }else{//取消绑定素材
            	index = selResource.indexOf(pId+"#");
                if(index >= 0){
                	index2 = selResource.indexOf("~",index);
                	selResource = selResource.substring(0,index)+selResource.substring(index2+1);
                }
        		$("#mp"+pId).html('');
            }
    	}
        $("#selResource").val(selResource);
	}

    /**
    * 选择订单日期
    */
    function selectDate(){
    	var ahead = 0;
    	if(deliveryMode==0){
    		ahead =  parseInt(aheadTime)*1000;
    	}
    	var now = Date.parse(new Date())+ahead;
    	var vStartDate = Date.parse(validMinDate.replace(/-/g,"/"));
    	//当前时间大于投放开始时间，则订单开始时间只能选择大于当前时间，否则订单开始时间从投放开始时间选择
    	var tmp = '#';
    	if(now > vStartDate){
    		WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-'+tmp+'{%d}',maxDate:validMaxDate});
    	}else{
    		WdatePicker({dateFmt:'yyyy-MM-dd',minDate:validMinDate,maxDate:validMaxDate});
    	}
    }

    /**
     * 保存订单
     */
    function updateOrder(){
    	if(checkFormNotNull()&&checkOrderDate()){
    		if(!playNumber || playNumber<=0){
    			$("#startTime").val($("#startDate").val())
        	}else{
        		$("#startTime").val($("#startDate2").val())
            }
    		var startDate = $("#startTime").val();
    		var endDate = $("#endDate").val();
    		var ployId = $("#ployId").val();
    		var id = $("#id").val();
    		var positionId = $("#positionId").val();
    		//var selResource = $("#selResource").val();
    		var orderCode = $("#orderCode").val();
    		$.ajax({   
   		       url:'checkOrderRule2.do',       
   		       type: 'POST',    
   		       dataType: 'text',   
   		       data: {
	   				startDate:startDate,
	   				endDate:endDate,
	   				ployId:ployId,
	   				id:id,
	   				positionId:positionId,
	   				orderCode:orderCode
   			   },                   
   		       timeout: 1000000000,                              
   		       error: function(){                      
   		    		alert("系统错误，请联系管理员！");
   		       },    
   		       success: function(result){ 
   		    	   if(result!='-1'){
   			    	   if(result=='0'){
   			    			$("#updateForm").submit();
   			    			/*
   			    		   var resourceArray = selResource.split("~");
	   			    	   if(resourceArray.length != (ployNumber+1)){
	   	   			    		if (confirm("有部分分策略未选择素材，是否提交订单？")) {
	   	   			    			$("#updateForm").submit();
	   	   	   			    	}
	   	   			       }else{
	   	   			  			$("#updateForm").submit();
	   	   	   			   }*/
   			    	   }else{
   			    		   alert(result);
   			    	   }
   		    	   }else{
   			    		alert("系统错误，请联系管理员！");
   		    	   }
   		    	   
   			   }  
   		   }); 
    		
    	}
	}

    /**
     * 检查输入项是否符合要求
     * */
    function checkFormNotNull(){
    	if(isEmpty($("#ployId").val())){
    		alert("请选择策略！");
    		return false;
    	}

    	if($("#positionId").val() == '27' || $("#positionId").val()=='28'){
        	//问卷广告位，用户次数、问卷次数、积分兑换人民币比率必填
        	var userNumber = $("#userNumber").val();
    		if(isEmpty(userNumber)){
        		alert("请输入用户总次数！");
        		$("#userNumber").focus();
        		return false;
        	}
    		if(!isIntegerNumber(userNumber)){
        		alert("用户总次数只能是整数数字！");
        		$("#userNumber").focus();
        		return false;
        	}
    		if(userNumber.length>10){
        		alert("用户总次数长度不能大于10位！");
        		$("#userNumber").focus();
        		return false;
        	}
        	var questionnaireNumber = $("#questionnaireNumber").val();
    		if(isEmpty(questionnaireNumber)){
        		alert("请输入问卷次数！");
        		$("#questionnaireNumber").focus();
        		return false;
        	}
    		if(!isIntegerNumber(questionnaireNumber)){
        		alert("问卷次数只能是整数数字！");
        		$("#questionnaireNumber").focus();
        		return false;
        	}
    		if(questionnaireNumber.length>10){
        		alert("问卷次数长度不能大于10位！");
        		$("#questionnaireNumber").focus();
        		return false;
        	}
        	var thresholdNumber = $("#thresholdNumber").val();
        	if(!isEmpty(thresholdNumber)){
        		if(!isIntegerNumber(thresholdNumber)){
            		alert("通知门限值只能是整数数字！");
            		$("#thresholdNumber").focus();
            		return false;
            	}
        		if(thresholdNumber.length>10){
            		alert("通知门限值长度不能大于10位！");
            		$("#thresholdNumber").focus();
            		return false;
            	}
        		if(Number(thresholdNumber) > Number(questionnaireNumber)){
            		alert("通知门限值不能大于问卷次数！");
            		$("#thresholdNumber").focus();
            		return false;
            	}
        	}
        	var integralRatio = $("#integralRatio").val();
    		if(isEmpty(integralRatio)){
        		alert("请输入积分兑换人民币比率！");
        		$("#integralRatio").focus();
        		return false;
        	}else{
        		if(integralRatio.length>20){
            		alert("积分兑换人民币比率长度不能大于20位！");
            		$("#integralRatio").focus();
            		return false;
            	}
    			var integralRatios = integralRatio.split(":");
    			if(integralRatios.length != 2 || !isFloat2(integralRatios[0]) || !isFloat2(integralRatios[1])){
    				alert("积分兑换人民币比率格式不正确！");
    				$("#integralRatio").focus();
    	    		return false;
    			}
    		}
    	}
    	
    	if(!playNumber || playNumber<=0){
    		if(isEmpty($("#startDate").val())){
        		alert("请选择开始日期！");
        		$("#startDate").focus();
        		return false;
        	}
	    	if(isEmpty($("#endDate").val())){
	    		alert("请选择结束日期！");
	    		$("#endDate").focus();
	    		return false;
	    	}
    	}else{
    		if(isEmpty($("#startDate2").val())){
        		alert("请选择开始日期！");
        		$("#startDate2").focus();
        		return false;
        	}
        }
    	if(!isEmpty($("#desc").val()) && $("#desc").val().length>120){
    		alert("订单描述字数在0-120字之间！");
    		return false;
    	}
    	if(validateSpecialCharacterAfter($("#desc").val())){
			alert("订单描述不能包括特殊字符！");
			$("#desc").focus();
			return false;
		}
    	if($("#sucai").find("tr").length==0){
    		alert("请为策略绑定素材！");
    		return false;
    	}
    	
    	return true;
    }

    /**
     * 检查开始时间和结束时间是否符合要求
     * */
    function checkOrderDate(){
    	var start = $("#startDate").val();
    	var end = $("#endDate").val();
    	var ahead = 0;
    	if(deliveryMode==0){
    		ahead =  parseInt(aheadTime)*1000;
    	}
    	
    	var startTime=Date.parse(start.replace(/-/g, "/")+" "+ployStartTime);
        var endTime=Date.parse(end.replace(/-/g, "/")+" "+ployEndTime);
        var now = Date.parse(new Date())+ahead; 
        
        var startDate = Date.parse(start.replace(/-/g,"/"));
        var endDate = Date.parse(end.replace(/-/g,"/"));
        var vStartDate = Date.parse(validMinDate.replace(/-/g,"/"));
        var vEndDate = Date.parse(validMaxDate.replace(/-/g,"/"));
        
       if(endTime<startTime)
       {
           alert("订单结束时间不能小于开始时间！");
           return false;
       }
       /*
       if(startTime<now)
       {
          alert("订单开始时间与当前时间间隔小于"+aheadTime+"秒！");
          return false;
       } */
       if(startDate<vStartDate||endDate>vEndDate){
    	   alert("订单日期超出广告位投放日期范围，广告位投放日期范围是"+vStartDate.replace(" 00:00:00","")+"~"+vEndDate.replace(" 00:00:00",""));
    	   return false;
       }
       return true;
    }

  //显示订单审核日志
	function showAuditLog(relationId){
    	var url = "queryOrderAuditLog.do?auditLog.relationType=1&auditLog.relationId="+relationId;
    	window.showModalDialog(url, window, "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
	}
	 function addResouce(){
    	positionId = document.getElementById("positionId").value;
    	ployId = document.getElementById("ployId").value;
    	var orderCode = document.getElementById("orderCode").value;
    	if(!ployId || ployId==-1){
        	document.getElementById("ployName").style.borderColor="red";
        	document.getElementById("ploy_error").innerHTML = "策略不能为空";
    		return;
    	}
    	var url = "initAreaResource.do?order.ployId="+ployId+"&order.positionId="+positionId+"&omRelTmp.orderCode="+orderCode;
    	var value = window.showModalDialog(url, window, "dialogHeight=580px;dialogWidth=820px;center=1;resizable=0;status=0;");
    	showAreaResource(ployId,'${order.orderCode}',positionId);  
    }	
    function repush(orderId){
	$.ajax({   
		       url:'repush.do',       
		       type: 'POST',    
		       dataType: 'text',   
		       data: {
				orderId:orderId,				
				},                   
		       timeout: 1000000000,                              
		       error: function(){                      
		    		alert("系统错误，请联系管理员！");
		       },    
		       success: function(result){ 
		    	   if(result=='0'){
		    		   alert('重新投放成功！');
		    		  window.location.href='queryOrderList.do';
		    	   }else if(result=="-1"){
		    		   alert("系统错误，请联系管理员！");   		   
		    	   }else{
		    		   alert(result);
		    	   }
			   }  
		   }); 
	}		
				
</script>
</head>

<body class="mainBody" onload='init(${aheadTime});'>
<form action="updateOrder.do" method="post" id="updateForm">
<input type="hidden" id="id" name="order.id" value="${order.id}"  />
<input type="hidden" id="state" name="order.state" value="${order.state}"  />
<input type="hidden" id="orderType" name="order.orderType" value="${order.orderType}"  />
<input type="hidden" id="validStart" name="order.validStart" value="${order.validStart}"  />
<input type="hidden" id="validEnd" name="order.validEnd" value="${order.validEnd}"  />
<input type="hidden" id="ployStartTime" name="order.ployStartTime" value="${order.ployStartTime}"  />
<input type="hidden" id="ployEndTime" name="order.ployEndTime" value="${order.ployEndTime}"  />
<input type="hidden" id="selResource" name="order.selResource" value="${order.selResource}" />
<input type="hidden" id="createTime" name="order.createTime" value="${order.createTime}" />
<input type="hidden" name="order.playedNumber" value="${order.playedNumber}" />
<input type="hidden" id="playNumber" name="order.playNumber" value="${order.playNumber}" />
<input type="hidden" id="startTime" name="order.startDateStr"  />
<div class="detail">
<table cellspacing="1" class="searchList" align="left">
	<tr class="title">
		<td colspan="4">订单编辑</td>
	</tr>
	<tr>
		<td width="12%" align="right">订单号：</td>
		<td width="33%"><input type="hidden" id="orderCode" name="order.orderCode" value="${order.orderCode}" />${order.orderCode}</td>
		<td width="12%" align="right">合同名称：</td>
		<td width="33%">
			<input type="hidden" id="contractId" name="order.contractId" value="${order.contractId}"  />${order.contractName}
		</td>
	</tr>
	<tr>
		<td align="right">广告位名称：</td>
		<td>
			<input type="hidden" id="positionId" name="order.positionId" value="${order.positionId}"  />${order.positionName}
		</td>
		<td align="right"><span class="required">*</span>策略名称：</td>
		<td>
			<input id="ployName" name="order.ployName" value="${order.ployName}" class="e_input_add" readonly="readonly" type="text" onclick="showContent('ploy',null);" />
			<input type="hidden"  id="ployId" name="order.ployId" value="${order.ployId}"  />
		</td>
	</tr>
	<tr id="questionnaire1" style="display: none">
		<td align="right"><span class="required">*</span>用户总次数：</td>
		<td>
			<input id="userNumber" name="order.userNumber" type="text" value="${order.userNumber}" />
		</td>
		<td align="right"><span class="required">*</span>问卷总次数：</td>
		<td>
			<input id="questionnaireNumber" name="order.questionnaireNumber" type="text" value="${order.questionnaireNumber}" />
		</td>
	</tr>
	
	<tr id="questionnaire2" style="display: none">
		<td align="right">通知门限值：</td>
		<td>
			<input id="thresholdNumber" name="order.thresholdNumber" type="text" value="${order.thresholdNumber}" />
		</td>
		<td align="right"><span class="required">*</span>积分兑换人民币比率：</td>
		<td>
			<input id="integralRatio" name="order.integralRatio" type="text"  value="${order.integralRatio}"/><span class="required">格式：1:1</span>
		</td>
	</tr>
	<tr id="periodDate" style="display: none">
		<td align="right"><span class="required">*</span>开始日期：</td>
		<td><input type="text" value="<fmt:formatDate value="${order.startTime}" dateStyle="medium"/>" readonly="readonly"  class="e_input_time" id="startDate"
			 onclick="selectDate();"/></td>
		<td align="right"><span class="required">*</span>结束日期：</td>
		<td><input type="text" name="order.endDateStr" value="<fmt:formatDate value="${order.endTime}" dateStyle="medium"/>" readonly="readonly" class="e_input_time" id="endDate" 
			onclick="selectDate();"/></td>
	</tr>
	<tr id="onceDate" style="display: none">
		<td align="right"><span class="required">*</span>开始日期：</td>
		<td><input type="text" value="<fmt:formatDate value="${order.startTime}" dateStyle="medium"/>" readonly="readonly"  class="e_input_time" id="startDate2"
			 onclick="selectDate();"/></td>
		<td align="right">播放次数：</td>
		<td><input type="text" id="viewPlayNumber" value="${order.playNumber}" disabled="disabled" /></td>
	</tr>
	<tr>
         <td width="12%" align="right">订单类型：</td>
         <td width="33%">
         	<c:choose>
				<c:when test="${order.orderType==0}">投放式</c:when>
				<c:when test="${order.orderType==1}">请求式</c:when>
			</c:choose>
	    </td>
	         <td width="12%" align="right">订单状态：</td>
	         <td width="33%">
	         	<c:choose>
					<c:when test="${order.state=='0'}">【未发布订单】待审核</c:when>
					<c:when test="${order.state=='1'}">【修改订单】待审核</c:when>
					<c:when test="${order.state=='2'}">【删除订单】待审核</c:when>
					<c:when test="${order.state=='3'}">【未发布订单】审核不通过</c:when>
					<c:when test="${order.state=='4'}">【修改订单】审核不通过</c:when>
					<c:when test="${order.state=='5'}">【删除订单】审核不通过</c:when>
					<c:when test="${order.state=='6'}">已发布</c:when>
					<c:when test="${order.state=='7'}">执行完毕</c:when>
					<c:when test="${order.state=='9'}">投放失败 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="重新投放" class="btn" onclick="repush(${order.id})"></c:when>
				</c:choose>
         </td>
     </tr>
	<tr>
		<td align="right">订单描述：</td>
		<td colspan="3"><textarea id="desc" name="order.description" cols="50" rows="3">${order.description}</textarea> <span class="required">(长度在0-120字之间)</span></td>
	</tr>
	<!--<tr>
		--><!--<td colspan="4" class="yulan"><span
			style="display: block; width: 500px; padding: 5px;">·策略绑定素材信息</span>
		<div id="ggw" class="ggw">
		<ul>
			<div id="selPrecise"></div>
		</ul>
		</div>

		<div style="width: 6%; height: 65px; margin-left: 15px; margin-top: 70px; float: left; border: 1px dashed #CCCCCC; padding-left: 5px; padding-top: 35px; " >
		<img src="<%=path%>/images/jiantou.png" /></div>
		<div
			style="width: 426px; height: 240px; float: left; border: 1px dashed #CCCCCC; margin-left: 15px; color: #CCCCCC; margin-top: -20px; position: relative;">
		<img id="pImage" src="<%=path%>/images/position/position.jpg" width="426px" height="240px" /> 
		<img id="mImage" src="" style="display: none;" />
		<div id="video">
			<object type='application/x-vlc-plugin' id='vlc'  classid='clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921' width="150" height="150">
		           <param name='mrl'  value=''/>
					<param name='volume' value='50' />
					<param name='autoplay' value='false' />
					<param name='loop' value='false' />
					<param name='fullscreen' value='false' />
		    </object>
		</div>
		<div id="text" style="display: none;"><marquee scrollamount="10"
			id="textContent"></marquee></div>
		</div>
		</td>
	--><!--</tr>
-->
  <tr>
		    <td colspan="3" class="yulan"><span
				style="display: block; width: 500px;">策略绑定素材信息:</span>
			</td>
			<td>
				<input type="button" onclick="addResouce();" class="btn" value="新增绑定" />
			</td>
	   </tr>
	        <table  name="sucai" id="sucai"  width="100%" cellspacing="1" class="searchList">
	        </table>
  </table>
<div style="margin-left:50px;">
	<input type="button" onclick="updateOrder();" class="btn" value="确认" /> &nbsp;&nbsp; 
	<input type="button" onclick="javascript :history.back(-1);" class="btn" value="取消" /> &nbsp;&nbsp; 
	<a href="javascript:showAuditLog('${order.id}');" >审核日志</a>
</div>

</div>

</form>
</body>
</html>