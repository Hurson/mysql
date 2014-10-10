<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ page import="com.dvnchina.advertDelivery.utils.ConfigureProperties"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery-ui-timepicker-addon.css" type="text/css" />
<script type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker-zh-CN.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery-ui-timepicker-zh-CN.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/swfobject.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="<%=path%>/js/material/uploadMaterial.js"></script>

<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" />
<link rel="stylesheet" href="<%=path%>/css/popUpDiv.css" type="text/css" />
<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js"></script>

<script type="text/javascript" src="<%=path%>/js/order/addOrUpdate.js"></script>

<link rel="stylesheet" href="<%=path%>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path%>/css/platform.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" />


<title>广告系统</title>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
    $("#file_id").uploadify({
		'uploader':'<%=path%>/js/jquery/upload/image/uploadify.swf',
		'script':'uploadMaterial.do?method=uploadMaterial',
		'cancelImg':'<%=path%>/js/jquery/upload/image/cancel.png',
		'folder':'/uploadFiles',
		'queueID':'fileQueue',
		'buttonImg':'<%=path%>/js/jquery/upload/image/uploadify.jpg',
		'fileDataName': 'backgroundImage',
		'auto':true,
		'multi':false,
		'fileExt':'*.jpg;*.jepg;*.gif;*.png',
		 'displayData':'speed',
		'onComplete':function(event,queueID,fileObj,response,data){
			var json = eval('(' + response + ')');
			if(json!=null){
					if(json.position=='local'){
						if(json.result=='true'){
							$("#materialViewDivImg").attr("src","<%=path%>/images/material/"+json.viewpath);
							$("#backgroundImage").val(json.filepath);
							writeMessage();
						}else{
							alert('图片上传至FTP失败');
						}
					}
				}	
		}
	});
	
	$("#startTime").datepicker({
        onSelect: function (selectedDateTime){
		}
    }); 
    $("#endTime").datepicker({
        onSelect: function (selectedDateTime){
		}
    }); 
});

</script>
<style>
.treven{ background:#FFFFFF;}
.trodd{ background:#f6f6f6;}
</style>
</head>

<body class="mainBody" onload='init(${contractJson },"<%=ConfigureProperties.getInstance().get("orderOpAheadTime")%>");'>
<form action=""  id="form1" name="form1"  >
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td style=" padding:4px;">
				<table cellpadding="0" cellspacing="1" width="100%" class="tablea" >
					<tr>
						<td class="formTitle" colspan="99">
							<span>上传素材</span>
						</td>
					</tr>
					<tr>
						<td class="td_label">选择素材分类：</td>
						<td class="td_input">
							<select id="sel_material_type"   onchange="selectOptionVal(this.id)"  class="e_select" onfocus="this.className='e_selectFocus'" onblur="this.className='e_select';checkType()" >
								<option id="ad_id" value="-1">请选择...</option>
								<c:forEach items="${mKindList}" var="materialBean">
									<option  value="${materialBean.id }">${materialBean.name }</option>
								</c:forEach>
							</select>		
						</td>
						
						<td class="td_label">所属合同编号：</td>
						<td class="td_input"> 
						   <input id="sel_contract_id" name="businessID" type="text" readonly="readonly" onclick="showContract();" class="e_input_add"/>
							<!--<select id="sel_contract_id" onchange="selectOptionVal(this.id)"   class="e_select"  onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'" >
								<option id="ad_id" value="-1">请选择...</option>
									<c:forEach items="${cList}" var="contractBean">
										<option value="${contractBean.id }">${contractBean.contractNumber}</option>
									</c:forEach>
							</select> -->
						</td>
						
					</tr>
					
					<tr>
						<td class="td_label">选择广告位：</td>
					    <td class="td_input">
					       <input type="hidden" id="sel_postion_id"  value=""/>
					       <input id="sel_postion_id_str" name="businessID" type="text" class="e_input_add" readonly="readonly" onclick="showPosition();"/>
						   <!--<select  id="sel_postion_id" onchange="selectOptionVal(this.id)"   class="e_select"  onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'" >
								<option id="ad_id" value="-1">请选择...</option>
									<c:forEach items="${pList}" var="positionBean">
										<option  value="${positionBean.id }">
										${positionBean.positionName}
										<c:choose>
											<c:when test="${positionBean.isHd==0}">
											(标清)
											</c:when>
											<c:otherwise>
											(高清)
											</c:otherwise>
										</c:choose>
										</option>
									</c:forEach>
							</select>-->
						</td>
						<td class="td_label">所属内容分类：</td>
						<td class="td_input">
							<select  id="contentSort" onchange="selectOptionVal(this.id)"   class="e_select"  onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'" >
								<option id="ad_id" value="-1">请选择...</option>
									<c:forEach items="${mTypeList}" var="typeBean">
										<option  value="${typeBean.id }">${typeBean.name }</option>
									</c:forEach>
							</select>	
						</td>
					</tr>
					
					<tr>
						<td class="td_label">所属广告商名称：</td>
						<td class="td_input">
							<input id="businessName"  type="text" class="e_input" readonly="readonly" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />		
						</td>
						<td class="td_label">所属合同名称：</td>
						<td class="td_input">
							<input id="businessId" name="businessId" type="hidden"/>
							<input id="contractName" name="businessName" value="" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" readonly="readonly" />
						</td>
					</tr>
					
					<tr>
						<td class="td_label">开始时间：</td>
						<td class="td_input">
							<input id="startTime" name="startTime" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
						</td>
						<td class="td_label">结束时间：</td>
						<td class="td_input">
							<input id="endTime" name="endTime" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
						</td>
					</tr>
					
					<tr>
						<td  colspan="4">
							<div id="message_div_id" style="display:block;" >
								<table cellpadding="0" cellspacing="0" width="100%" >
									<tr>
										<td class="td_label" style="border-right:1px solid #cccccc; ">文字名称：</td>
										<td class="td_input" style="border-right:1px solid #cccccc; ">
											<input id="messageName" class="e_input" onfocus="this.className='e_inputFocusa'" onblur="this.className='e_inputa'" />
										</td>
										<td class="td_label" style="border-right:1px solid #cccccc; ">文字内容：</td>
										<td class="td_input">
											<input id="messageContent" class="e_input" onfocus="this.className='e_inputFocusa'" onblur="this.className='e_inputa'" />
										</td>
									</tr>	
								</table>
							</div>	
						</td>
					</tr>
					
					<tr>
						<td  colspan="4">
							<div id="runtime_div_id" style="display:block;" >
								<table cellpadding="0" cellspacing="0" width="100%" class="tablea" >
									<tr>
										<td class="td_label" style="border-right:1px solid #cccccc; ">时长：</td>
										<td class="td_input" style="border-right:1px solid #cccccc; ">
											<input id="runtime" name="runtime" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
										</td>
										<td class="td_label" style="border-right:1px solid #cccccc; "></td>
										<td class="td_input"></td>
									</tr>	
								</table>
							</div>	
						</td>
					</tr>
					
					<tr>
						<td colspan="4">
							<div id="file_div_id" style="display:block;" >
								<table cellpadding="0" cellspacing="0" width="100%" class="tablea" >
									<tr>
										<td class="td_label" style="border-right:1px solid #cccccc; ">描述：</td>
										<td class="td_input" style="border-right:1px solid #cccccc; ">
											<input id="desc" class="e_input" onfocus="this.className='e_inputFocusa'" onblur="this.className='e_inputa'" />
										</td>
										<td class="td_label" style="border-right:1px solid #cccccc; ">选择文件：</td>
										<td class="td_input">
											<input id="file_id" name="upload" type="file" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/>
											<input id="backgroundImage" name="" value="" type="hidden" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" /> 
											<!-- <input title="浏览..." value=""  id="file_id" name="upload" class="e_inputfile" onfocus="this.className='e_inputFocusfile'" onblur="this.className='e_inputfile'" type="file" onclick="writeMessage()" />-->
										</td>
									</tr>	
								</table>
							</div>	
						</td>
					</tr>
					
					<tr>
						<td  colspan="4">
							<div id="messageurl_div_id" style="display:block;" >
								<table cellpadding="0" cellspacing="0" width="100%" class="tablea" >
									<tr>
										<td class="td_label" style="border-right:1px solid #cccccc; ">文字URL：</td>
										<td class="td_input" style="border-right:1px solid #cccccc; ">
											<input id="messageUrl" class="e_input" onfocus="this.className='e_inputFocusa'" onblur="this.className='e_inputa'" />
										</td>
										<td class="td_label" style="border-right:1px solid #cccccc; "></td>
										<td class="td_input">
											<input id="messageButton" type="button" class="b_add" onfocus="blur()" title="保存文字素材" onclick="writeMessage()"/>
										</td>
									</tr>	
								</table>
							</div>	
						</td>
					</tr>
					
					<tr>
						<td class="formBottom" colspan="99">
							<input name="Submit" 
							type="button" 
							title="上传" 
							value="确定" 
							onfocus=blur() 
							onclick="writeResource()"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="1" width="100%" class="tableayl" >
		<tr>
			<td>
				<div id="materialViewDiv">
					<table cellpadding="0" cellspacing="0" width="100%" class="tablea" >
						<tr>
							<td class="td_label" rowspan="2">素材预览效果：</td>
							<td class="td_input" rowspan="2">
								<div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:345px;height:250px;">
									<img id="materialViewDivImg" src="" style="background-repeat:no-repeat; width:345px;height:250px;"/>											
								</div>		
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
</form>

<div id="contractDiv" class="showDiv" style="display:none">
    <div class="searchContent">
        <table cellspacing="1" class="searchList">
            <tr class="title">
                <td>查询条件</td>
            </tr>
            <tr>
                <td class="searchCriteria">
                	<span>合同名称：</span><input type="text" name="textfield" id="cName" style="width: 14%"/>
                    <span>合&nbsp;同&nbsp;号：</span><input type="text" name="textfield" id="cCode" style="width: 14%"/>
                    <span>合同代码：</span><input type="text" name="textfield" id="cNumber" style="width: 14%"/>
                    <input type="button" value="查询" onclick="queryContract();" class="btn"/>
   				</td>
            </tr>
        </table>
        <div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
        <table width="100%" cellspacing="1" class="searchList">
            <tr class="title">
              <td class="dot"></td>
                <td>合同名称</td>
                <td>合同号</td>
                <td>合同代码</td>
                <td>广告商名称</td>
				<td>开始时间</td>
				<td>结束时间</td>
            </tr>
            <c:set var="cIndex" value="0" />
			<c:forEach items="${contracts}" var="contract">
				<tr>
					<td align="center" height="26"><input type="radio"
						name="contractId" value="${contract.id}"
						<c:if test="${cIndex==0 }"> checked="checked"</c:if> /> <input
						type="hidden" name="contractCode" value="${contract.contractCode }" />
						<input
						type="hidden" name="contractName" value="${contract.contractName }" />
						<input
						type="hidden" name="customerName" value="${contract.customer.advertisersName }" />
						<input
						type="hidden" name="customerId" value="${contract.customer.id }" />
						</td>
					<td>${contract.contractName }</td>
					<td>${contract.contractCode }</td>
					<td>${contract.contractNumber }</td>
					<td>${contract.customer.advertisersName }</td>
					<td>${contract.effectiveStartDate }</td>
					<td>${contract.effectiveEndDate }</td>
				</tr>
				<c:set var="cIndex" value="${cIndex+1 }" />
			</c:forEach>
            <tr>
                <td colspan="7">
                    <input type="button" value="确定" class="btn" onclick="selectContract();"/>&nbsp;&nbsp;
					<input type="button" value="取消" class="btn" onclick="closeSelectDiv('contractDiv');"/>&nbsp;&nbsp;
                </td>
            </tr>
        </table>
    </div>
</div>

<div id="positionDiv" class="showDiv" style="display: none;">
    <div class="searchContent">
		 <table cellspacing="1" class="searchList">
            <tr class="title">
                <td>查询条件</td>
            </tr>
            <tr>
                <td class="searchCriteria">
                <span>广告位名称：</span><input type="text" name="textfield" id="pName"/>
                    <span>广告位类型：</span>
                    <select id="pType">
			            <option value="-1">--选择广告位类型--</option>
						<c:forEach items="${positionTypes}" var="type">
							<option value="${type.id }">${type.positionTypeName }</option>
						</c:forEach>
			        </select>
                    <span>投放方式：</span>
                    <select id="pMode">
			           <option value="-1">--选择投放方式--</option>
						<c:forEach items="${positionModes}" var="mode">
							<option value="${mode.id }">${mode.name}</option>
						</c:forEach>
			        </select>
                    <input type="button" value="查询" onclick="queryPosition();" class="btn"/>
   				</td>
   			</tr>
		</table>
		<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
        <table width="100%" cellspacing="1" class="searchList">
            <tr class="title">
              <td class="dot"></td>
				<td>广告位名称</td>
				<td>广告位类型</td>
				<td>高清/标清</td>
				<td>是否叠加</td>
				<td>是否轮询</td>
				<td>轮询个数</td>
				<td>投放方式</td>
			</tr>
			<tbody id="positionInfo"></tbody>
			<tr>
				<td colspan="8">
                    <input type="button" value="确定" class="btn" onclick="selectPosition();"/>&nbsp;&nbsp;
					<input type="button" value="取消" class="btn" onclick="closeSelectDiv('positionDiv');"/>&nbsp;&nbsp;
                </td>
			</tr>
		</table>
	</div>
</div>



<div id="bg" class="bg" style="display: none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe> 

<input id='flagId' type='hidden' value=""/>
</body>
</html>
