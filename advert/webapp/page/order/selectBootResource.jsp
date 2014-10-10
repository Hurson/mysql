<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<title>素材查询</title>
</head>
<script type="text/javascript">
	function query(){
		if(validateSpecialCharacterAfter($("#resourceName").val())){
			alert("素材名称不能包括特殊字符！");
			$("#resourceName").focus();
			return ;
		}
		$("#queryForm").submit();
	}
	
	function save(defaultStart) {
		if (getCheckCount('ids') <= 0) {
	         alert("请选择需要绑定的素材！");
	         return;
	    }
		var ids  = getCheckValue('ids');
   	   var returnArray = new Array();
	   var idsArray = ids.split(",");
	   var timeIntervals = "";//所有播放时段
	   var selectVideo = false; //选择开机视频
	   var selectDefaulImage = false; //选择默认开机图片
	   var selectResource = "";//选择的素材
	   for(var i = 0; i<idsArray.length; i++){  
		   var resourceId = idsArray[i].split("_")[0];
		   var resourceName = idsArray[i].split("_")[1];
		   var resourceType = idsArray[i].split("_")[2];
		   if(resourceType==1){
		   		 selectResource += resourceId+"#-1@";
		   		//选择开机视频
		   		if(selectVideo){
		   			alert("开机广告位只能选择一个开机视频！");
		   			return;
		   		}else{
		   			selectVideo = true;
		   			returnArray[i] = {"resourceId":resourceId,"resourceName":resourceName,"inStreamArray":new Array(),"mLoopsValue":"-1"};
		   		}
		   }else{
			   //开机图片播放时段
			   if(defaultStart == '0'){
					//时段图片
				   var timeInterval = $.trim(document.getElementById(resourceId+"_timeInterval").value);
				   if(isEmpty(timeInterval)){
					   alert("请为选中的素材输入对应的播放时段！");
					   $("#"+resourceId+"_timeInterval").focus();
			   		   return;
				   }else if(timeInterval.charAt(timeInterval.length-1)==","){
					   timeInterval = timeInterval.substring(0,timeInterval.length-1);
				   }
				   selectResource += resourceId+"#"+timeInterval+"@";
				   timeIntervals +=timeInterval+",";
				   returnArray[i] = {"resourceId":resourceId,"resourceName":resourceName,"inStreamArray":timeInterval.split(","),"mLoopsValue":"-1"};
			   }else{
				   selectResource += resourceId+"#-1@";
			   		if(selectDefaulImage){
			   			alert("开机广告位只能选择一个默认图片！");
			   			return;
			   		}else{
			   			selectDefaulImage = true;
			   			//timeIntervals="0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23".split(",");
		   				returnArray[i] = {"resourceId":resourceId,"resourceName":resourceName,"inStreamArray":new Array(),"mLoopsValue":"-1"};
			   		}
			   }
		   }
	   }
	   /*
	   if(!selectVideo){
	   		alert("开机广告位必须选择一个开机视频！");
	   		return;
	   }*/
	   if(resourceType==0){
	   	if(defaultStart == '0' && timeIntervals.length<=0){
		   	alert("根据策略该开机广告位必须选择24张时段图片！");
	   	   	return;
	   	}else if(defaultStart == '1' && !selectDefaulImage){
		  	 alert("根据策略该开机广告位必须选择一张默认图片！");
	   	   	return;
	   	}
	   }
	   
	   $.ajax({   
	       url:'checkBootResoueces.do',       
	       type: 'POST',    
	       dataType: 'text',   
	       data: {
	       	   selectResource:selectResource.substring(0,selectResource.length-1),
	    	   timeIntervals:timeIntervals
			},                   
	       timeout: 1000000000,
	       error: function(){
	    		alert("系统错误，请联系管理员！");
	       },    
	       success: function(result){ 
	    	   if(result!='-1'){
		    	   if(result=='0'){
		    		   window.returnValue=returnArray;
		   			   window.close();
		    	   }else{
		    		   alert(result);
		    	   }
	    	   }else{
		    		alert("系统错误，请联系管理员！");
	    	   }
		   }  
	   });
	}

	/**
	 * 订单绑定的素材
	 * @param selectResource
	 * 格式：订单素材关系数组，以@分隔
	 * 每个订单素材关系格式：素材ID,轮询序号,播放时段
	 * 样例：10,-1,0@10,-1,1@10,-1,2@12,-1,3@
	 */
	function init(selectResource){
		if(selectResource){
			//去掉最后一个@
			if(selectResource.charAt(selectResource.length-1)=="@"){
				selectResource = selectResource.substring(0,selectResource.length-1);
			}
			
			var relArray = selectResource.split("@");
			if(relArray && relArray.length>0){
				var resourceIdArray = new Array();
				var index = 0;
				for(var i=0;i<relArray.length;i++){
					var resArray = relArray[i].split(",");
					if(resArray && resArray.length>2){
						var resourceId = resArray[0];//资源ID
						var mLoopsValue = resArray[1];//轮训序号
						var insPosition = resArray[2];//插播位置
						var setResource = false;//设置资源标示
						if(resourceIdArray.length>0){
							for(var j=0;j<resourceIdArray.length;j++){
								if(resourceIdArray[j] == resourceId){
									setResource = true;
									continue;
								}
							}
							if(setResource){
								continue;
							}else{
								resourceIdArray[index] = resourceId;
								index = index+1;
								$("#"+resourceId+"_resource").attr("checked","checked") ;
								var insPositions = "";
								for(var k=0;k<relArray.length;k++){
									var setArray = relArray[k].split(",");
									if(setArray[0]==resourceId){
										if(setArray[2] && setArray[2] != '-1'){
											insPositions += setArray[2]+",";
										}
									}
								}
								if(!isEmpty(insPositions)){
									$("#"+resourceId+"_timeInterval").val(insPositions.substring(0,insPositions.length-1));
								}
							}
						}else{
							//第一次relArray数组循环进入
							resourceIdArray[index] = resourceId;
							index = index+1;
							$("#"+resourceId+"_resource").attr("checked","checked") ;
							var insPositions = "";
							for(var k=0;k<relArray.length;k++){
								var setArray = relArray[k].split(",");
								if(setArray[0]==resourceId){
									if(setArray[2] && setArray[2] != '-1'){
										insPositions += setArray[2]+",";
									}
								}
							}
							if(!isEmpty(insPositions)){
								$("#"+resourceId+"_timeInterval").val(insPositions.substring(0,insPositions.length-1));
							}
						}
					}
				}
			}
		}
	}
	
</script>
<body class="mainBody" onload="init('${selectResource}');">
<form action="queryAreaResourceList.do" method="post" id="queryForm">
	<input type="hidden" name="resource.contractId" value="${resource.contractId}"/>
	<input type="hidden" name="resource.advertPositionId" value="${resource.advertPositionId}"/>
	<input type="hidden" name="ployId" value="${ployId}"/>
	<div class="searchContent">
		<table cellspacing="1" class="searchList">
            <tr class="title">
                <td>查询条件</td>
            </tr>
            <tr>
                <td class="searchCriteria">
                	<span>素材名称：</span><input type="text" id="resourceName" name="resource.resourceName" value="${resource.resourceName}" />
                	<input type="button" value="查询" onclick="javascript:query();" class="btn"/>
   				</td>
            </tr>
        </table>
		<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
		<table cellspacing="1" class="searchList">
    		<tr class="title">
    			
    			<td height="28" class="dot">
    				<input type="checkbox" name="chkAll" onclick="selectAll(this, 'ids');" id="chkAll"/>
    			</td>
    			<td>素材名称</td>
    			<td>素材类型</td>
    			<td>文件大小（字节）</td>
    			<c:if test="${defaultStart=='0'&&resourceList[0].resourceType=='0'}">
    			<td>播放时段 </td>
    			</c:if>
    		
    		</tr>
    		<c:forEach items="${resourceList}" var="resource" varStatus="pl">
    		<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>
    			onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'">
    			<td><input id="${resource.id}_resource" type="checkbox"	name="ids" value="${resource.id}_${resource.resourceName}_${resource.resourceType}" /></td>
    			<td><c:out value="${resource.resourceName}" /></td>
    			<td>
    				<c:choose>
						<c:when test="${resource.resourceType==0}">图片</c:when>
						<c:when test="${resource.resourceType==1}">视频</c:when>
					</c:choose>
    			</td>
    			<td><c:out value="${resource.fileSize}" /></td>
    			<c:if test="${defaultStart=='0'&&resource.resourceType=='0'}">
    			<td>
    				<!--<c:if test="${resource.resourceType==0}">
    					
    				</c:if>
    			-->
    			<input id="${resource.id}_timeInterval" type="text" name="timeInterval" size="100" />
    			</td>
    			</c:if>
    			<c:if test="${defaultStart!='0'}">
    				<c:if test="${resource.resourceType==0}">
    					<input id="${resource.id}_timeInterval" type="hidden" name="timeInterval" />
    				</c:if>
    			</c:if>
    		</tr>
    		</c:forEach>
			<tr>
				<td 
				<c:if test="${defaultStart=='0'}">colspan="5"</c:if>
				<c:if test="${defaultStart!='0'}">colspan="4"</c:if>
				>
			    	<input type="button" value="确定" class="btn" onclick="javascript:save('${defaultStart}');"/>&nbsp;&nbsp;
        			<input type="button" value="取消" class="btn" onclick="javascript :window.close();"/>
			    </td>
			</tr>
		</table>
		<div>
		备注：开机图片广告位可以选择一个默认图片素材或24张时段图片素材（播放时段为0——23，一张图片素材包括多个时段的，多个时段以英文逗号,分隔）
		</div>	
	</div>
</form>
</body>
</html>