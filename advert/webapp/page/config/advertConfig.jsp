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
<!--
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
  -->
<link rel="stylesheet" href="<%=path%>/css/menu_right_new1.css" type="text/css" />


<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />


<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css"/>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery-1.4.4.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<!-- 
<script language="javascript" type="text/javascript" src="<%=path %>/js/positionType/addPositionType.js"></script>
 -->
<script type="text/javascript" src="<%=path %>/js/jquery/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="<%=path %>/js/jquery/validate/jquery.validate.messages_cn.js"></script>
<title>广告系统</title>
<script>
$(function(){   
    $("#bm,#mDiv").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm,#mDiv").find("tr:odd").addClass("trodd");  //偶数行的样式
});

//公共方法--获得dom对象
	function getObj(id){
		var obj = document.getElementById(id);
		return obj;	
	}
	
	//公共方法-选择下拉框的值
	function selectOptionVal(id){
		var optionVal="-1";
		var selectPobj = getObj(id); 
		var options   = selectPobj.options; 
		for(var i = 0; i < options.length; i++){
			var optionResult = options[i].selected ;
			if(optionResult){
				optionVal = options[i].value;
			}
		}
		return optionVal;
	}

--></script>

<script >

$(document).ready(function(){
	$('#addPlayCountForm').submit(function() {
		var options = { 
				url:'saveOrUpdatePlayCount.do?method=saveOrUpdatePlayCount',
				type:'post',
		        success:function(responseText){
					alert("保存成功！");
				}, 
				error:function(){
					alert("服务器异常，请稍后重试！");
				},
		        dataType:'html' 
		    };
		
	    $(this).ajaxSubmit(options); 
	    return false; 
	});

	
});

$(function(){ 
   $("#addPositionTypeButton").click(function(){
     		if(!validateNull()){
     			$("#addPositionTypeForm").submit();
     		}
    });
     
    $("#addPlayCountButton").click(function(){
        if(!validateInput()){
	   		$("#addPlayCountForm").submit();
        }
    });

    $("#searchPositionSubmit").click(function(){
        
     	var positionTypeId = $("#positionTypeId").val();
     	positionTypeId = encodeURI(positionTypeId);  
     	
     	var positionName = $("#positionNameS").val();
     	positionName = encodeURI(positionName);  
     	 
     	url='getAdvertConfigList.do?method=queryPage&positionTypeId='+positionTypeId+'&positionName='+positionName;
     	window.location.href=url;
     	
     	return;
     });
});

function validateInput(){
	var flag = false ;
	var count_value = $("#count_id_value").val();
	if($.isEmptyObject(count_value)){
		flag = true;
		alert('插播次数不能为空！');
		$("#count_id_value").focus();
		return flag;
	}
	var reg = /^[1-9]d*|0$/;
	if(!reg.test(count_value)){
		flag = true;
		alert('只允许输入数字！');
		$("#count_id_value").focus();
		return flag;
	}
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
function operation(methodName,formName,id){
	switch(methodName){
		case 'fillMaterialInfo':
			  fillMaterialInfo(methodName,formName,id);
			  break;
		case 'modifyPositionType':
			  modifyPositionType(methodName,formName,id,typeCode,typeName);
			  break;
	    default:
	          break;
	}
	
}
function fillMaterialInfo(methodName,formName,id){
	
	addMaterialInfo(id);
}

function modifyPositionType(methodName,formName,id,typeCode,typeName){
	$("#idAdd").val(id);
	$("#typeCodeAdd").val(typeCode);
	$("#typeNameAdd").val(typeName);
}






/**
 * 填充    广告位绑定默认素材    列表标题
 * */
function fillMaterialTitle(){
	var str = "<td height='26px' style='padding-left: 8px;' align='center' width='5%'>编号</td><td width='20%'>素材类型</td><td width='50%'>素材地址</td><td width='20%'>操作</td>";
	
	$("#materialTitle").html(str);
}

/**
 * 显示添加默认素材
 */
 var positionId ;
function addMaterialInfo(id){
	positionId = id;
	fillMaterialTitle();

	showDefaultMaterial(id);
	
	showSelectDiv("materialDiv");
}

/**
 * 显示默认素材的配置
 */
function showDefaultMaterial(id){
	
	$('#materialInfo').html("");
	
	var url = 'queryDefaultMaterial.do?method=queryDefaultMaterial';
	$.ajax({
		type:'post',
		url:url,
		data:{id:positionId},
		dataType:'json',
		success:function(data){
				
			var defaultMaterials  = eval(data);

			var str = "";
			for(var i = 0 ; i < defaultMaterials.length; i++ ){
				str +="<tr";
				if(i%2==0){
					str += " class='treven' "
				}else{
					str += " class='trodd' "
				}
				var count = i+1;
				str +="><td>"+count+"</td><td>";
				str +=defaultMaterials[i].m_type;
				str +="</td><td>";
				str +=defaultMaterials[i].m_path;
				str +="</td><td>"

				str +="<input name='default_m_name' id='default_m_id' type='button' class='button_delete'  title='删除'  onClick='deleteDefaultMaterialById("+defaultMaterials[i].child_id+")'/>"
			//	str +="<input name='default_m_name' id='default_m_id' type='button' class='button_halt'  title='修改'  onClick='updateDefaultMaterialById("+defaultMaterials[i].child_id+")'/>"
					
				str +="</td></tr>";

				
			}

			$('#materialInfo').html(str);
			
		},
		error:function(){
			alert("服务器异常，数据加载失败，请稍后重试！");
		}
	});
}

function addDefaultMaterial(){
	
	//var num = $('option:selected', '#m_type_id').index();
	var m_type = $('#m_type_id').val();
	var m_path = $('#default_m_path').val();
	var url = 'addDefaultMaterial.do?method=addDefaultMaterial';
	$.ajax({
		type:'post',
		url:url,
		data:{id:positionId,m_type:m_type,m_path:m_path},
		dataType:'json',
		success:function(responseText){
			var flag = responseText.flag;
			var result = responseText.result;
			if(flag=='1'){
					if(result){
						
						alert("保存成功！");
						
						showDefaultMaterial(positionId);
					}else{
						alert("素材已配置在当前广告位！");
					}
			}else{
				alert("参数不正确！");
			}
		},
		error:function(){
			alert("服务器异常，数据加载失败，请稍后重试！");
		}
	});

}

/**
 * 删除默认素材
 */
function deleteDefaultMaterialById(m_id){
	
	var url = "deleteDefaultMaterial.do?method=deleteDefaultMaterial";
	
	$.ajax({
		type:'post',
		url:url,
		data:{id:positionId,m_id:m_id},
		dataType:'json',
		success:function(responseText){
			var flag = responseText.flag;
			if(flag){
				alert("删除成功！");
			}else{
				alert("删除失败！");
			}
			showDefaultMaterial(positionId);
		},
		error:function(){
			alert("服务器异常，数据加载失败，请稍后重试！");
		}
	});
}




/**
 * 弹出策略选择层
 */
function showSelectDiv(divId){
	$('#'+divId).show();
	$('#bg').show();
	$('#popIframe').show();
}
/**
 * 关闭策略选择层
 */
function closeSelectDiv(divId){
	$('#'+divId).hide();
	$('#bg').hide();
	$('#popIframe').hide();
	
}

</script>
<style>
.treven{ background:#FFFFFF;height:20px;}
.trodd{ background:#f6f6f6;height:20px;}
</style>
</head>

<body onload="">
<div>
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td>
				<table cellpadding="0" cellspacing="0" border="0" width="100%"
					class="tab_right">
					<tr>
						<td>工作台</td>
						<td>客户</td>
						<td>日程行动</td>
						<td>销售机会</td>
						<td>订单管理</td>
						<td>客服中心</td>
						<td>财务中心</td>
						<td>营销中心</td>
						<td>人力资源中心</td>
						<td>数据统计</td>
						<td>信息门户管理</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td style=" padding:1px;">
				<form action=""  id="addPlayCountForm" name="addPlayCountForm" method="post">
					<table cellpadding="0" cellspacing="1" width="100%" class="tablea" >
						<tr>
							<td class="formTitle" colspan="99">
								<span>添加/编辑 插播次数</span>
							</td>
						</tr>
						<tr>
							<td class="td_label">插播次数：</td>
							<td class="td_input" colspan="3">
								<input id="count_id" name="playConfigBean.id" type="hidden" value="${playConfigBean.id}"/>
								<input id="count_id_name" name="playConfigBean.name" type="hidden" value="true"/>
								<input id="count_id_value" name="playConfigBean.value" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${playConfigBean.value}" maxlength="40"/> <font color="red">*</font>		
							</td>
						</tr>
					
						<tr>
							<td class="formBottom" colspan="99">
								<input id="addPlayCountButton" 
								type="button"  
								class="b_add" 
								onfocus="blur()"/>
							</td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
		<tr>
		<td style="padding:2px;">
			<table cellpadding="0" cellspacing="1" width="100%" class="tablea">
				<tr>
					<td class="formTitle" colspan="99"><span>·广告位查询</span></td>
				</tr>
				<tr>
					<td class="td_label">广告位类型编码：</td>
					<td class="td_input">
						<input id="positionTypeId" name="positionTypeId" class="e_input" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="80"/>
					</td>
					<td class="td_label">广告位名称：</td>
					<td class="td_input">
						<input id="positionNameS" name="positionNameS" class="e_input" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="80"/>
					</td>
				</tr>
				<tr>
					<td class="formBottom" colspan="99">
						<input name="searchPositionSubmit" id="searchPositionSubmit" type="submit" title="查看" class="b_search" value=""/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="padding:2px;">
			<div>  
				<table cellpadding="0" cellspacing="1" width="100%"
			class="taba_right_list" id="bm">
					<tr>
						<td colspan="23" style="padding-left: 8px; background: url(<%=path %>/images/menu_righttop.png) repeat-x; text-align: left; height: 26px;"><span>·广告位列表</span></td>
					</tr>
					<tr>
						<td height="26px" align="center">序号</td>
						<td>广告位类型编码</td>
						<td>广告位特征值</td>
						<td>广告位名称</td>
						<td>是否高清</td>
						<td>是否轮询</td>
						<td>是否叠加</td>
						<td>投放方式</td>
						<td>轮询个数</td>
						<td>投放平台</td>
						<td>操作</td>
					</tr>
					<c:choose>
						<c:when test="${!empty positionList}">
							<c:set var="index" value="0" />
							<c:forEach var="advertPosition" items="${positionList}" varStatus="status">
								<c:set var="index" value="${index+1 }" />
								<tr>
									<td align="center" height="26">${status.count}</td>
									<td>${advertPosition.positionTypeId}</td>
									<td>${advertPosition.characteristicIdentification}</td>
									<td>${advertPosition.positionName}</td>
									<td>
										<c:choose>
											<c:when test="${advertPosition.isHd==0}">
												标清
											</c:when>
											<c:when test="${advertPosition.isHd==1}">
												高清
											</c:when>
											<c:when test="${advertPosition.isHd==2}">
												都支持
											</c:when>
											<c:otherwise>
												未选择
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${advertPosition.isLoop==0}">
												否
											</c:when>
											<c:when test="${advertPosition.isLoop==1}">
												是
											</c:when>
											<c:otherwise>
												未选择
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${advertPosition.isAdd==0}">
												否
											</c:when>
											<c:when test="${advertPosition.isAdd==1}">
												是
											</c:when>
											<c:otherwise>
												未选择
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${advertPosition.deliveryMode==0}">
												投放式
											</c:when>
											<c:when test="${advertPosition.deliveryMode==1}">
												请求式
											</c:when>
											<c:otherwise>
												未选择
											</c:otherwise>
										</c:choose>
										
									</td>
									<td>${advertPosition.materialNumber}</td>
									<td>${advertPosition.deliveryPlatform}</td>
									<td>
									
										<form action="" method="get" id="listPositionOperationForm${status.count}" name="listPositionOperationForm${status.count}">
											<input id="id" name="advertPosition.id" type="hidden" value="${advertPosition.id}"/>
											<input id="characteristicIdentification" name="advertPosition.characteristicIdentification" type="hidden"  value="${advertPosition.characteristicIdentification}"/>	
											<input id="positionName" name="advertPosition.positionName" type="hidden"  value="${advertPosition.positionName}"/>	
											<input id="categoryId" name="advertPosition.categoryId" type="hidden"  value="${advertPosition.categoryId}"/>
											<input id="positionTypeId" name="advertPosition.positionTypeId" type="hidden"  value="${advertPosition.positionTypeId}"/>
											<input id="deliveryPlatform" name="advertPosition.deliveryPlatform" type="hidden"  value="${advertPosition.deliveryPlatform}"/>
											<input id="textRuleId" name="advertPosition.textRuleId" type="hidden"  value="${advertPosition.textRuleId}"/>
											<input id="questionRuleId" name="advertPosition.questionRuleId" type="hidden"  value="${advertPosition.questionRuleId}"/>
											<input id="imageRuleId" name="advertPosition.imageRuleId" type="hidden"  value="${advertPosition.imageRuleId}"/>
											<input id="questionRuleId" name="advertPosition.questionRuleId" type="hidden"  value="${advertPosition.questionRuleId}"/>
											<input id="isLoop" name="advertPosition.isLoop" type="hidden"  value="${advertPosition.isLoop}"/>
											<input id="deliveryMode" name="advertPosition.deliveryMode" type="hidden"  value="${advertPosition.deliveryMode}"/>
											<input id="isHd" name="advertPosition.isHd" type="hidden"  value="${advertPosition.isHd}"/>
											<input id="isAdd" name="advertPosition.isAdd" type="hidden"  value="${advertPosition.isAdd}"/>
											<input id="materialNumber" name="advertPosition.materialNumber" value="${advertPosition.materialNumber}" type="hidden" />		
											<input id="coordinate" name="advertPosition.coordinate" value="${advertPosition.coordinate}" type="hidden" />
											<input id="price" name="advertPosition.price" value="${advertPosition.price}" type="hidden" />	
											<input id="discount" name="advertPosition.discount" value="${advertPosition.discount}" type="hidden"  />		
											<input id="backgroundPath" name="advertPosition.backgroundPath" value="${advertPosition.backgroundPath}" type="hidden"/>				
											<input id="description" name="advertPosition.description" value="${advertPosition.description}" type="hidden"/>
											<input id="startTime" name="advertPosition.startTime" value="${advertPosition.startTime}" type="hidden"/>
											<input id="endTime" name="advertPosition.endTime" value="${advertPosition.endTime}" type="hidden"/>
											<input id="widthHeight" name="advertPosition.widthHeight" value="${advertPosition.widthHeight}" type="hidden"/>
											<input name="viewPosition${status.count}" id="viewPosition${status.count}" type="button" class="button_start" value="" title="绑定默认素材"  onClick="operation('fillMaterialInfo','#listPositionOperationForm${status.count}','${advertPosition.id}')" />
										</form>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${index<page.pageSize}">
								<c:forEach begin="${index}" end="${page.pageSize-1}" step="1">
									<tr>
										<td align="center" height="26">&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
								</c:forEach>
							</c:if>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="12">
									暂无记录
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<td colspan="12">
						
						</td>
					</tr>
					<tr>
						<td height="34" colspan="23"
							style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: right;">
							<c:if test="${page.totalPage > 0 }">
								<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
								<a href="#">当前第${page.currentPage}/${page.totalPage}页</a>&nbsp;&nbsp;
								<c:choose>
									<c:when test="${page.currentPage==1&&page.totalPage!=1}">
										【<a href="queryPositionPage.do?method=queryPage&currentPage=${page.currentPage+1}&positionTypeId=${positionTypeId}">下一页</a>】
										【<a href="queryPositionPage.do?method=queryPage&currentPage=${page.totalPage}&positionTypeId=${positionTypeId}">末页</a>】
									</c:when>
									<c:when test="${page.currentPage==page.totalPage&&page.totalPage!=1 }">
										【<a href="queryPositionPage.do?method=queryPage&currentPage=1&positionTypeId=${positionTypeId}">首页</a>】 
										【<a href="queryPositionPage.do?method=queryPage&currentPage=${page.currentPage-1 }&positionTypeId=${positionTypeId}">上一页</a>】
									</c:when>
									<c:when test="${page.currentPage>1&&page.currentPage<page.totalPage}">
										【<a href="queryPositionPage.do?method=queryPage&currentPage=1&positionTypeId=${positionTypeId}">首页</a>】 
										【<a href="queryPositionPage.do?method=queryPage&currentPage=${page.currentPage-1}&positionTypeId=${positionTypeId}">上一页</a>】
										【<a href="queryPositionPage.do?method=queryPage&currentPage=${page.currentPage+1}&positionTypeId=${positionTypeId}">下一页</a>】
										【<a href="queryPositionPage.do?method=queryPage&currentPage=${page.totalPage}&positionTypeId=${positionTypeId}">末页</a>】
									</c:when>
								</c:choose>
							</c:if>
						</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
	</table>
	</div>
<div id="materialDiv" class="showDiv" style="display: none;">
	<form action="" id="addDefaultMaterial">
		<table cellpadding="0" cellspacing="1" width="100%" class="tableDiv">
			
			<tr>
				<td class="formTitle" colspan="99"><span>·广告位绑定默认素材</span></td>
			</tr>
			<tr>
				<td class="td_label">素材类型：</td>
				<td class="td_input" colspan="5">
					<select id="m_type_id" name="m_type_name" >
						<option id="1" >图片</option>
						<option id="2" >视频</option>
					</select>
				
				</td>
			</tr>
			<tr>
				<td class="td_label">默认素材地址：</td>
				<td class="td_input" colspan="9"><input type="text" id="default_m_path"
					  /></td>
			</tr>
			<tr>
				<td class="formBottom" colspan="99"><input name="Submit"
					type="button"  title="添加" class="b_add"  value=""
					onclick="addDefaultMaterial()" onfocus="blur()" /></td>
			</tr>
		</table>
	</form>
<table cellpadding="0" cellspacing="1" width="100%"
	class="taba_right_list" id="mDiv">

	<tr>
	
	<td colspan="12"  height="10px;"
			style="padding-left: 8px; background: url(<%=path%>/images/menu_righttop.png) repeat-x; text-align: left; height: 2px;"><span>·绑定默认素材列表</span>
	
	</td>
	</tr>
	<tr id="materialTitle">

	</tr>
	<tbody id="materialInfo" >
	</tbody>

	<tr>
		<td height="20" colspan="12"
			style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: center;">
		<div class="buttons">
		<!-- 
		 <a href="#" onclick="selectMaterial();">确认</a>&nbsp;&nbsp;&nbsp;&nbsp;
		 -->
		<a href="#" onclick="closeSelectDiv('materialDiv');">返回</a></div>
		</td>
	</tr>
</table>
</div>
<div id="bg" class="bg" style="display: none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>
</body>
</html>