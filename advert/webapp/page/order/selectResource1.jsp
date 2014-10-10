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
	
	function save(loopCount,positionPackageId,isInstream) {
		if (getCheckCount('ids') <= 0) {
	         alert("请选择需要绑定的素材！");
	         return;
	    }
		var ids  = getCheckValue('ids');

		//插播位置检查
		if(isInstream && isInstream != 0){
			var idsArray = ids.split(",");
			
			if (getCheckCount('mInStreamNo') != isInstream) {
				alert("必须选择"+isInstream+"个视频插播位置！");
				return;
			}
			var selectNo = 0;
			var mInStreamNo  = getCheckValue('mInStreamNo');
			var instreamArray = mInStreamNo.split(",");
			//校验每个插播位置是否填充完成
			for(var n=0;n<=isInstream-1;n++){
				var inValue = n+"/"+isInstream;
				for(var ins=0;ins<instreamArray.length;ins++){
					var flag = false;
					if(instreamArray[ins].split("_")[0] == inValue){
						for(var i = 0; i<idsArray.length; i++){
							var resourceId = idsArray[i].split("_")[0];
							if(resourceId == instreamArray[ins].split("_")[1]){
								selectNo = selectNo+1;
								flag = true;
								break;
							}
						}
						if(flag){
							break;
						}
					}
				}
			}
			if (selectNo != isInstream) {
				alert("请正确选择插播位置！");
				return;
			}

			var instreamFlag = true;
			//校验每个视频文件是否有插播位置
			for(var i = 0; i<idsArray.length; i++){
				if(instreamFlag){
					instreamFlag = false;
					var resourceId = idsArray[i].split("_")[0];
					for(var ins=0;ins<instreamArray.length;ins++){
						if(resourceId == instreamArray[ins].split("_")[1]){
							instreamFlag = true;
							break;
						}
					}
				}else{
					break;
				}
			}
			if(!instreamFlag){
				alert("请为选中的素材选择插播位置！");
				return;
			}
		}
	    //轮询检查
	    if(loopCount && loopCount != 0){
	    	if (getCheckCount('ids') != loopCount) {
		         alert("只能选择"+loopCount+"个素材！");
		         return;
		    }
	    	var idsArray = ids.split(",");
	    	for(var i = 0; i<idsArray.length; i++){
				var resourceId = idsArray[i].split("_")[0];
				var mLoopsValue = document.getElementById(resourceId+"_mLoopNo").value;
				
				if(isEmpty(mLoopsValue)){
					alert('请为选中的素材输入对应的轮询序号！');
					$("#"+resourceId+"_mLoopNo").focus();
					return;
				}
			}
			var reg=/^\d+$/;    
			if(!reg.test($.trim(mLoopsValue))||mLoopsValue==0){
				alert('输入的轮询序号应为数字！');
				return;
			}
	    }

	    $.ajax({   
	       url:'checkResource.do',       
	       type: 'POST',    
	       dataType: 'text',   
	       data: {
	    	   ids:ids,
	    	   positionPackageId:positionPackageId
			},                   
	       timeout: 1000000000,                              
	       error: function(){                      
	    		alert("系统错误，请联系管理员！");
	       },    
	       success: function(result){ 
	    	   if(result!='-1'){
		    	   if(result=='0'){
			    	   var returnArray = new Array();
		    		   var idsArray = ids.split(",");

		    		   //获取插播位置值
		    		   var mInStreamValue = getCheckValue('mInStreamNo');
		    		   var mInStreamNoArray = mInStreamValue.split(",");
		   			   
		    		   for(var i = 0; i<idsArray.length; i++){  
		    			   var resourceId = idsArray[i].split("_")[0];
		    			   var resourceName = idsArray[i].split("_")[1];
						   //获取插播位置信息
		    			   var inStreamArray = new Array(); 
		    			   var k = 0;
		    			   if(mInStreamNoArray && mInStreamNoArray.length>0){
		    				   for(var ins=0;ins<mInStreamNoArray.length;ins++){
		    					   if(resourceId == mInStreamNoArray[ins].split("_")[1]){
		    						   inStreamArray[k] = mInStreamNoArray[ins].split("_")[0];
		    						   k = k+1;
		    					   }
		    				   }
		    			   }
		    			   //获取轮询值
		    			   var mLoopsValue = '-1';
		    			   var mLoops = document.getElementById(resourceId+"_mLoopNo");
		    			   if(mLoops){
		    				   mLoopsValue = mLoops.value;
			    		   }

		    			   returnArray[i] = {"resourceId":resourceId,"resourceName":resourceName,"inStreamArray":inStreamArray,"mLoopsValue":mLoopsValue};
		    		   }
			    	   
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
	 *  获取checkbox 选中的value值,以","分隔
	 * @param id
	 */
	function getCheckValueById(id) {
	    var cbg = document.getElementsById(id);
	    var value = '';
	    var len = cbg.length;
	    for (var k = 0; k < len; k++) {
	        if (cbg[k] && (cbg[k].type) && ((cbg[k].type == 'checkbox') || cbg[k].type == 'radio')
	                && cbg[k].checked) {
	            value += cbg[k].value + ',';
	        }
	    }
	    if (value.length > 0) {
	    	value = value.substring(0,value.length-1);
	    }
	    
	    return value;
	}

	/**
	* 取消绑定的素材
	*/
	function cancelBinding(){
		var returnArray = new Array();
		window.returnValue=returnArray;
		window.close();
	}

	function init(selectResource){
		if(selectResource){
			selectResource = selectResource.substring(0,selectResource.length-1);
			
			var relArray = selectResource.split("@");
			if(relArray && relArray.length>0){
				for(var i=0;i<relArray.length;i++){
					var resArray = relArray[i].split(",");
					if(resArray && resArray.length>2){
						var resourceId = resArray[0];
						var mLoopsValue = resArray[1];
						var insPosition = resArray[2];
						$("#"+resourceId+"_resource").attr("checked","checked") ;
						$("#"+resourceId+"_mLoopNo").val(mLoopsValue);
						var index = insPosition.indexOf("/");
						if(index>=0){
							if(insPosition.substring(0,index) == 0){
								insPosition = insPosition.substring(0,index);
							}else{
								insPosition = insPosition.substring(0,index)+insPosition.substring(index+1);
							}
						}
						$("#"+resourceId+"_"+insPosition+"_mInStreamNo").attr("checked","checked");
					}
				}
			}
		}
	}
	
</script>
<body class="mainBody" onload="init('${selectResource}');">
<form action="showResourceList.do" method="post" id="queryForm">
	<input type="hidden" name="resource.contractId" value="${resource.contractId}"/>
	<input type="hidden" name="resource.advertPositionId" value="${resource.advertPositionId}"/>
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
    				<c:if test="${advertPosition.isLoop==1||advertPosition.isAdd==1}">
    					<input type="checkbox" name="chkAll" onclick="selectAll(this, 'ids');" id="chkAll"/>
    				</c:if>
    			</td>
    			<td width="30%">素材名称</td>
    			<td width="15%">素材类型</td>
    			<td width="50%">描述</td>
    			<c:if test="${advertPosition.isInstream != null && advertPosition.isInstream != 0}">
    			<td>
    			插播位置
    			</td>
    			</c:if>
    			<c:if test="${advertPosition.isLoop==1}">
    			<td>
    			轮询序号
    			</td>
    			</c:if>
    		</tr>
    		<c:forEach items="${resourceList}" var="resource" varStatus="pl">
    		<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>
    			onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'">
    			<td><input id="${resource.id}_resource"
    				<c:choose>
						<c:when test="${advertPosition.isLoop==1||advertPosition.isAdd==1}">type="checkbox" </c:when>
						<c:when test="${advertPosition.isLoop!=1&&advertPosition.isAdd!=1}">type="radio" </c:when>
					</c:choose>
    				name="ids" value="${resource.id}_${resource.resourceName}" 
    			 /></td>
    			<td><c:out value="${resource.resourceName}" /></td>
    			
    			<td>
    				<c:choose>
						<c:when test="${resource.resourceType==0}">图片</c:when>
						<c:when test="${resource.resourceType==1}">视频</c:when>
						<c:when test="${resource.resourceType==2}">文字</c:when>
						<c:when test="${resource.resourceType==3}">问卷</c:when>
						<c:when test="${resource.resourceType==4}">ZIP</c:when>
					</c:choose>
    			</td>
    			<td><c:out value="${resource.resourceDesc}" /></td>
    			<s:if test="advertPosition.isInstream != null && advertPosition.isInstream != 0 ">
	    			<c:choose>
		    			<c:when test="${resource.resourceType==1}">
		    			<td>
		    				<c:forEach var="i" begin="1" end="${advertPosition.isInstream}" step="1">
		    				<c:choose>
		    					<c:when test="${i==1}">
		    					0:<input id="${resource.id}_0_mInStreamNo" type="checkbox" name="mInStreamNo" value="${i-1}/${advertPosition.isInstream}_${resource.id}" />
		    					</c:when>
		    					<c:when test="${i !=1}">
		    					${i-1}/${advertPosition.isInstream}:<input id="${resource.id}_${i-1}${advertPosition.isInstream}_mInStreamNo" type="checkbox" name="mInStreamNo" value="${i-1}/${advertPosition.isInstream}_${resource.id}" />
		    					</c:when>
		    				</c:choose>
		    				</c:forEach>
		    				 
						</td>
						</c:when>
						<c:when test="${resource.resourceType!=1}"><td>-</td></c:when>
	    			</c:choose>
    			</s:if>
    			<s:if test="advertPosition.isLoop==1">
    			<td><input id="${resource.id}_mLoopNo" type="text" name="mLoopNo" style="width:30px" size="1" /></td>
    			</s:if>
    		</tr>
    		</c:forEach>
			<tr>
				<td 
				<s:if test="advertPosition.isInstream != null && advertPosition.isInstream != 0 && advertPosition.isLoop==1">
					colspan="6"
				</s:if>
				<s:elseif test="(advertPosition.isInstream != null && advertPosition.isInstream != 0) || advertPosition.isLoop==1">
					colspan="5"
				</s:elseif>
				<s:else>
					colspan="4"
				</s:else>
				>
			    	<input type="button" value="确定" class="btn" onclick="javascript:save('${advertPosition.loopCount}','${advertPosition.positionPackageId}','${advertPosition.isInstream}');"/>&nbsp;&nbsp;
        			<input type="button" value="关闭" class="btn" onclick="javascript :window.close();"/>
        			<input type="button" value="取消绑定" class="btn" onclick="javascript :cancelBinding();"/>
			    </td>
			</tr>
		</table>		
	</div>
</form>
</body>
</html>