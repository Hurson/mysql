<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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

<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />

<script type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.core.js"></script>

<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery-ui-timepicker-zh-CN.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/swfobject.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="<%=path%>/js/material/uploadMaterial.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=path%>/js/new/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/addOrUpdate.js"></script>


<title>广告系统</title>

<script type="text/javascript">

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
	
	$("#file_id2").uploadify({
		'uploader':'<%=path%>/js/jquery/upload/image/uploadify.swf',
		'script':'uploadMaterialVideo.do?method=uploadMaterialVideo',
		'cancelImg':'<%=path%>/js/jquery/upload/image/cancel.png',
		'folder':'/uploadFiles',
		'queueID':'fileQueue',
		'buttonImg':'<%=path%>/js/jquery/upload/image/uploadify.jpg',
		'fileDataName': 'backgroundImage2',
		'auto':true,
		'multi':false,
		'fileExt':'*.jpg;*.jepg;*.gif;*.png',
		 'displayData':'speed',
		'onComplete':function(event,queueID,fileObj,response,data){
			var json = eval('(' + response + ')');
			if(json!=null){
					if(json.position=='local'){
						if(json.result=='true'){
							$("#materialViewDivImg2").attr("src","<%=path%>/images/material/"+json.viewpath);
							$("#backgroundImage2").val(json.filepath);
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

function changeType2()
{
   var materialType = selectOptionVal("sel_material_type");
		//if(materialType == "-1"){
			//alert("请选择-资产类型！");
			//return;
		//}
		
	if (materialType == 0 )
	{//视频
	    //alert("视频："+materialType);
		
		document.getElementById('tt').style.display = "";
		document.getElementById('div_text').style.display = "none";      
		document.getElementById('div_video').style.display = "";      
		document.getElementById('div_image').style.display = "none";
		document.getElementById('b1').style.display = "none";
		document.getElementById('b2').style.display = "";
	}
	else if (materialType == 1 )
	{//图片
		//alert("图片："+materialType);
		document.getElementById('tt').style.display = "";
		document.getElementById('div_text').style.display = "none";      
		document.getElementById('div_video').style.display = "none";      
		document.getElementById('div_image').style.display = "";
		document.getElementById('b1').style.display = "none";
		document.getElementById('b2').style.display = "";
	}
	else if (materialType == 2 )
	{//文字
	    //alert("文字："+materialType);
	    document.getElementById('tt').style.display = "";
		document.getElementById('div_text').style.display = "";      
		document.getElementById('div_video').style.display = "none";      
		document.getElementById('div_image').style.display = "none";
		//document.getElementById('materialViewDiv').style.display = "none";
		document.getElementById('b1').style.display = "none";
		document.getElementById('b2').style.display = "";
	}
}

function closeSavePane() {
    window.location.href = "<%=path %>/page/AdContent/adContentMgr_list.do";
}

function addData() {
    alert("后台功能未完善");
}

</script>

</head>
<body class="mainBody">
<form action=""  id="form1" name="form1"  >
	<div class="search">
		<div class="path">首页 >> 广告资产管理 >> 新增资产</div>
		<div class="searchContent" >
			<div class="listDetail">
		    <div style="position: relative">	
		    	<table>
		    		<tr>
		    			<td>
		        <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px">
		            <tr class="title">
		                <td colspan="4">资产信息</td>
		            </tr>
		            <tr>
		                <td width="15%" align="right"><span class="required">*</span>选择合同：</td>
		                <td width="35%">	
		                    <input id="businessId" name="businessId" type="hidden"/>	                		                	
							<input id="sel_contract_id" name="businessID" class="new_input_add" type="text" readonly="readonly" onclick="showContract();" />	
						</td>
		                <td width="15%" align="right"><span class="required">*</span>选择广告位：</td>
		                <td width="35%">
		                    <input type="hidden" id="sel_postion_id"  value=""/>
		                    <input type="hidden" id="sel_postion_image"  value=""/>
					       <input type="hidden" id="sel_postion_video"  value=""/>
					       <input type="hidden" id="sel_postion_text"  value=""/>
					       <input type="hidden" id="sel_postion_quest"  value=""/>
					       <input id="sel_postion_id_str" name="businessID" type="text" class="new_input_add" readonly="readonly" onclick="showPosition();"/>
		                </td>
		            </tr>		           
		            <tr >
		                 <td align="right"><span class="required">*</span>资产名称：</td>
		                <td>
		                	<input id="content_name" name="content_name" type="text" maxlength="20" onkeypress="return validateSpecialCharacter();"/>
		                </td>
		                <td width="15%" align="right"><span class="required">*</span>广告商名称：</td>
		                <td width="35%">
		                    <input id="businessName"  type="text" readonly="readonly" />					       
		                </td>
		                
		            </tr>
		            <tr >
		                 <td align="right"><span class="required">*</span>资产分类：</td>
		                 <td>
		                	<select  id="contentSort" onchange="selectOptionVal(this.id)"   class="e_select"  onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'" >
								<option id="ad_id" value="-1">请选择...</option>
									<c:forEach items="${mTypeList}" var="typeBean">
										<option  value="${typeBean.id }">${typeBean.name }</option>
									</c:forEach>
							</select>
		                </td>
		                <td align="right"><span class="required">*</span>资产类型：</td>
		                <td>
			              	<select id="sel_material_type"   onchange="selectOptionVal(this.id)"  class="e_select" onfocus="this.className='e_selectFocus'" onclick="changeType2()" ">
								<option id="ad_id" value="-1">请选择...</option>
								<c:forEach items="${mKindList}" var="materialBean">
									<option  value="${materialBean.id }">${materialBean.name }</option>
								</c:forEach>
							</select>		  
		                </td>
		            </tr>
		            <tr>
		                <td align="right"><span class="required">*</span>开始时间：</td>
		                <td>		               
		                    <input id="startTime" readonly="readonly"  type="text" style="width:125px;"  name="startTime"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		                    <img onclick="showDate('startTime')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"/>
		                </td>
		                <td align="right"><span class="required">*</span>结束时间：</td>
		                <td>
			              	<input id="endTime"  readonly="readonly" class="input_style2" type="text" style="width:125px;"  name="endTime"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                            <img onclick="showDate('endTime')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"/>
		                </td>
		            </tr>
		            <tr >
		                 <td align="right"><span class="required"></span>资产描述：</td>
		                <td colspan="3">
		                	<textarea id="desc" name="desc" cols="40" rows="3" maxlength="100">
		                	</textarea>		              	
		                </td>
		            </tr>
		            <tr id="b1">
		            	<td colspan="4">
		            		<input type="button" value="确定" class="btn" onclick="javascript:addData();"/>
		            		<input type="button" value="取消" class="btn" onclick="javascript:closeSavePane();"/>
		            	</td>
		            </tr>
		          </table>
		          </td>
		        </tr>
		        <tr id="tt" style="display:none">
		        	<td>
		        <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px" id ="div_text" style="display: none">
		            <tr class="title" >
		                <td colspan="4">文字素材</td>
		            </tr>
		            <tr>
		                <td  align="right"><span class="required"></span>文字名称：</td>
		                <td>
			            		 <input id="messageName" class="e_input" onfocus="this.className='e_inputFocusa'" onblur="this.className='e_inputa'" />
		                </td>
		                <td  align="right"><span class="required"></span>文件URL：</td>
		                <td>
			            		 <input id="messageUrl" class="e_input" onfocus="this.className='e_inputFocusa'" onblur="this.className='e_inputa'" maxlength="256"/>
		                </td>
		            </tr>
		            <tr>
		            	<td width="15%" align="right"><span class="required"></span>内容：</td>
		                <td colspan="3">
		                	<textarea id="messageContent"  maxlength="4000" cols="80" rows="5">
		                	</textarea>
		                	
		                </td>
		            </tr>
		              
		             
		          </table>
							<table cellspacing="1" class="content" align="left" style="margin-bottom: 0px" id ="div_video" style="display: none">
		            <tr class="title" >
		                <td colspan="4">视频素材</td>
		            </tr>
		            <tr>
		            	<td width="15%" align="right"><span class="required"></span>时长：</td>
		                <td width="35%" >
		                	<input maxlength="10" id="runtime" name="runtime" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
		                </td>
		                <td width="15%" align="right"><span class="required"></span>选择文件：</td>
		                <td colspan="3">
			            	<input id="file_id2" name="upload" type="file" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/>
							<input id="backgroundImage2" name="" value="" type="hidden" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
		                </td>
		            </tr>
		            <tr>
							<td align="right" >素材预览效果：</td>
							<td colspan="3">
								<div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:345px;height:250px;">
									<img id="materialViewDivImg2" src="" style="background-repeat:no-repeat; width:345px;height:250px;"/>											
								</div>		
							</td>
							
					</tr>

	            </table>
							<table cellspacing="1" class="content" align="left" style="margin-bottom: 0px" id ="div_image" style="display: none">
		            <tr class="title" >
		                <td colspan="4">图片素材</td>
		            </tr>		            
		            <tr>
		            	
		                <td align="right"><span class="required"></span>选择文件：</td>
		                <td colspan="3">
			            	<input id="file_id" name="upload" type="file" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/>
							<input id="backgroundImage" name="" value="" type="hidden" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
		                </td>
		            </tr>
		            <tr>
							<td align="right" >素材预览效果：</td>
							<td colspan="3">
								<div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:345px;height:250px;">
									<img id="materialViewDivImg" src="" style="background-repeat:no-repeat; width:345px;height:250px;"/>											
								</div>		
							</td>
							
					</tr>
		          </table>
		        </td>
		      </tr>
			  
			  <tr id="b2" style="display:none">
		            	<td colspan="4">
		            		<input type="button" value="确定" class="btn" onclick="javascript:addData();"/>
		            		<input type="button" value="取消" class="btn" onclick="javascript:closeSavePane();"/>
		            	</td>
		            </tr>
			  
			  
		      <tr>
		      	<td>
		      		
		      	</td>
		      </tr>
		    </table>
		    </div>
		</div>
	</div>
	
	
	
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

<input id='flagId' type='hidden' value=""/>	

<div id="bg" class="bg" style="display: none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe> 

	
</body>