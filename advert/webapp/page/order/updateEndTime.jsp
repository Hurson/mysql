<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type='text/javascript'	src="<%=path%>/js/new/My97DatePicker/WdatePicker.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" />
<script type="text/javascript" src="<%=path%>/js/order/preview.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/common.js"></script>

<title>订单管理</title>
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

#selPloy {
	width: 100%;
	overflow: auto
}

#selPrecise {
	width: 100%;
	overflow: auto
}

img {
	border: 0px;
}

.e_input_time{
	background:url(<%=path%>/js/new/My97DatePicker/skin/datePicker.gif) right no-repeat #ffffff;
}
</style>
</head>
<script type="text/javascript">

	var validMinDate = '';//广告位投放开始日期
	var validMaxDate = '';//广告位投放结束日期
	var deliveryMode = 1;//投放方式
	var aheadTime=0;
	var previewValue='${previewValue}';
	//$(function(){
	//var state =${order.state};
	//if(state== 9){
	//	$("#endDate").attr('disabled',"true");
	//	$("#save_btn").attr('disabled',"true");
		
	//}
	//})
	
	function init(time,positionId){
		aheadTime = time;
		validMinDate = $("#validStart").val();
		validMaxDate = $("#validEnd").val();
		if(isEmpty(validMinDate)){
			validMinDate="2010-01-01";
		}
		if(isEmpty(validMaxDate)){
			validMaxDate="2100-01-01";
		}
		deliveryMode = $("#orderType").val();

		var playNumber = $("#playNumber").val();
		if(isEmpty(playNumber) || playNumber==0){
			$("#periodDate").show();
		}else{
			$("#onceDate").show();
		}

		//问卷广告位，订单添加用户次数、问卷次数、通知门限值、积分兑换人民币比率
		if(positionId=='27'||positionId=='28'){
			$("#questionnaire1").show();
			$("#questionnaire2").show();
		}
	    /*
		if(adPackageType == 0 || adPackageType == 1){
			var ployId = $("#ployId").val();
			var ployName = $("#ployName").val();
			//显示双向策略信息
			viewTwoPloy(ployJson,1,ployId,ployName);
		}else{
			//显示单向策略信息
			viewOnePloy(ployJson,1);
		}*/
		
		//显示已经选择的素材信息
		 var ployId = document.getElementById("ployId").value;
		showAreaResource(ployId,'${order.orderCode}',positionId);
		
		/*viewMaterials(materialJson);

		//预览素材
		var selPosition = eval(positionJson)
		preview(selPosition.backgroundPath,selPosition.coordinate,selPosition.widthHeight);*/
		
	}
				
		 //添加素材
    function addResouce(){
    	positionId = document.getElementById("positionId").value;
    	ployId = document.getElementById("ployId").value;
    	var orderCode = document.getElementById("orderCode").value;
    	if(!ployId || ployId==-1){
        	document.getElementById("ployName").style.borderColor="red";
        	document.getElementById("ploy_error").innerHTML = "策略不能为空";
    		return;
    	}
    	var url = "initAreaResource.do?order.ployId="+ployId+"&order.positionId="+positionId+"&areaResource.orderCode="+orderCode;
    	var value = window.showModalDialog(url, window, "dialogHeight=580px;dialogWidth=820px;center=1;resizable=0;status=0;");
    	showAreaResource(ployId,'${order.orderCode}',positionId);  
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

  //显示订单审核日志
	function showAuditLog(relationId){
    	var url = "queryOrderAuditLog.do?auditLog.relationType=1&auditLog.relationId="+relationId;
    	window.showModalDialog(url, window, "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
	}

	/**保存修改内容*/
	function save(orderId){
		if(isEmpty($("#endDate").val())){
    		alert("请选择结束日期！");
    		return false;
    	}
		$.ajax({   
		       url:'updateOrderEndTime.do',       
		       type: 'POST',    
		       dataType: 'text',   
		       data: {
				id:orderId,
				endDate:$("#endDate").val()
				
				},                   
		       timeout: 1000000000,                              
		       error: function(){                      
		    		alert("系统错误，请联系管理员！");
		    		op=0;
		       },    
		       success: function(result){ 
		    	   if(result=='0'){
		    		   alert('保存成功！');
		    		  window.location.href='queryOrderList.do';
		    	   }else if(result=="-1"){
		    		   alert("系统错误，请联系管理员！");   		   
		    	   }else{
		    		   alert(result);
		    	   }
			   }  
		   }); 
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
<body class="mainBody" onload='init(${aheadTime},${order.positionId});' >
<div class="detail">
<table cellspacing="1" class="searchList" align="left">
	<tr class="title">
		<td colspan="4">订单编辑</td>
	</tr>
	<input type="hidden" id="orderType" name="order.orderType" value="${order.orderType}"  />
	<input type="hidden" id="validStart" name="order.validStart" value="${order.validStart}"  />
	<input type="hidden" id="validEnd" name="order.validEnd" value="${order.validEnd}"  />
	<input type="hidden" id="playNumber" name="order.playNumber" value="${order.playNumber}"  />
	<input type="hidden" id="ployId" name="order.ployId" value="${order.ployId}"  />
	<tr>
		<td width="12%" align="right">订单号：</td>
		<td width="33%">${order.orderCode}</td>
		<td width="12%" align="right">合同名称：</td>
		<td width="33%">${order.contractName }</td>
	</tr>
	<tr>
		<td align="right">广告位名称：</td>
		<td>${order.positionName }</td>
		<td align="right">策略名称：</td>
		<td>${order.ployName }</td>
	</tr>
	<tr id="questionnaire1" style="display: none">
		<td width="12%" align="right">用户总次数：</td>
           <td width="33%">${order.userNumber}</td>
           <td width="12%" align="right">问卷总次数：</td>
           <td width="33%">${order.questionnaireNumber}</td>
	</tr>
	<tr id="questionnaire2" style="display: none">
		<td width="12%" align="right">通知门限值：</td>
           <td width="33%">${order.thresholdNumber}</td>
           <td width="12%" align="right">积分兑换人民币比率：</td>
           <td width="33%">${order.integralRatio}</td>
	</tr>
	<tr id="periodDate" style="display: none">
		<td align="right">生效日期：</td>
		<td><fmt:formatDate value="${order.startTime}" dateStyle="medium"/></td>
		<td align="right">
			<c:if test="${order.state!='7'}">
				<span class="required">*</span>
			</c:if>
			失效日期：</td>
		<td>
			<c:if test="${order.state!='7'&&order.state!='9'}">
				<input type="text" readonly="readonly" class="e_input_time"	id="endDate" onclick="selectDate();"
				value="<fmt:formatDate value="${order.endTime}" dateStyle="medium"/>" />
			</c:if>
			<c:if test="${order.state=='7'||order.state=='9'}">
				<fmt:formatDate value="${order.endTime}" dateStyle="medium"/>
			</c:if>
		</td>
	</tr>
	<tr id="onceDate" style="display: none">
		<td align="right">生效日期：</td>
		<td><fmt:formatDate value="${order.startTime}" dateStyle="medium"/></td>
		<td align="right">播放次数：</td>
		<td>${order.playNumber}  &nbsp;&nbsp;&nbsp;&nbsp;（已播放次数${order.playedNumber}）</td>
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
		<td colspan="3"><textarea id="desc" cols="50" rows="3"
			disabled="disabled">${order.description }</textarea></td>
	</tr>
	<tr>
		    <td colspan="3" class="yulan"><span
				style="display: block; width: 500px;">策略绑定素材信息:</span>
			</td>
			<td>
				<!--<input type="button" onclick="addResouce();" class="btn" value="新增绑定" />
			--></td>
	   </tr>
	        <table  name="sucai" id="sucai"  width="100%" cellspacing="1" class="searchList">
	        </table>
	<!--<tr>
		<td colspan="4" class="yulan"><span
			style="display: block; width: 500px; padding: 5px;">·策略绑定素材信息</span>
		<div id="ggw" class="ggw">
		<ul>
			 <li>已选择策略</li>
			<div id="selPloy"><span id="selPloyName">${order.ployName}</span> <br />
				&nbsp;&nbsp;&nbsp;&nbsp; <span id="mp0"></span></div>
				<li id="preciseli" style="display: none">已选择精准</li> 
			<div id="selPrecise"></div>
		</ul>
		</div>

		<div style="width: 6%; height: 65px; margin-left: 15px; margin-top: 70px; float: left; border: 1px dashed #CCCCCC; padding-left: 5px; padding-top: 35px">
		<img src="<%=path%>/images/jiantou.png" /></div>
		<div style="width: 426px; height: 240px; float: left; border: 1px dashed #CCCCCC; margin-left: 15px; color: #CCCCCC; margin-top: -20px; position: relative;">
		<img id="pImage" src="<%=path%>/images/position/position.jpg" width="426px" height="240px" />
		<img id="mImage" src=""	style="display: none" />
		<div id="video">
			<object type='application/x-vlc-plugin' id='vlc'  classid='clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921' width="150" height="150">
		           <param name='mrl'  value=''/>
					<param name='volume' value='50' />
					<param name='autoplay' value='false' />
					<param name='loop' value='false' />
					<param name='fullscreen' value='false' />
		    </object>
		</div>
		<div id="text" style="display: none;">
			<marquee scrollamount="10" id="textContent"></marquee>
		</div>
		</div>
		</td>
	</tr>
--></table>
<div style="margin-left:50px;">
	<c:if test="${order.state!='7' &&order.state!='9' &&(order.playNumber==null || order.playNumber==0)}">
		<input type="button" onclick="save(${order.id});"	id="save_btn" class="btn" value="确认" />&nbsp;&nbsp;
		<input
		type="button" onclick="javascript :history.back(-1);" class="btn" value="取消" />
	</c:if> 
	<c:if test="${order.state=='7'||order.state=='9' || order.playNumber>0}">
		<input
		type="button" onclick="javascript :history.back(-1);" class="btn" value="返回" />
	</c:if>  
	<a href="javascript:showAuditLog('${order.id}');" >审核日志</a>
</div>
</div>
</body>
</html>
