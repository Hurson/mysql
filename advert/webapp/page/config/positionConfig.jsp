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
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery-1.4.4.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<!-- 
<script language="javascript" type="text/javascript" src="<%=path %>/js/positionType/addPositionType.js"></script>
 -->
<script type="text/javascript"  src="<%=path %>/js/jquery-1.4.2-vsdoc.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="<%=path %>/js/jquery/validate/jquery.validate.messages_cn.js"></script>
<title>广告系统</title>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
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

$(function(){ 
   $("#addPositionTypeButton").click(function(){
     		if(!validateNull()){
     			$("#addPositionTypeForm").submit();
     		}
    });
     
      $("#queryPositionTypeButton").click(function(){
     		$("#queryPositionTypeForm").submit();
      });
     
});

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
function operation(methodName,formName,id,typeCode,typeName){
	switch(methodName){
		case 'deletePositionType':
			  deletePositionType(methodName,formName,id,typeCode,typeName);
			  break;
		case 'modifyPositionType':
			  modifyPositionType(methodName,formName,id,typeCode,typeName);
			  break;
	    default:
	          break;
	}
	
}

function deletePositionType(methodName,formName,id,typeCode,typeName){
	var url = 'page/config/deletePostion.do?method=deletePostion';
	$(formName).attr("action",url);
	$(formName).submit();
}

function modifyPositionType(methodName,formName,id,typeCode,typeName){
	$("#idAdd").val(id);
	$("#typeCodeAdd").val(typeCode);
	$("#typeNameAdd").val(typeName);
}

</script>
<style>
.treven{ background:#FFFFFF;}
.trodd{ background:#f6f6f6;}
</style>
</head>

<body onload="">

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
				<form action="saveOrupdatePosition.do?method=saveOrupdatePosition"  id="addPositionTypeForm" name="addPositionTypeForm" method="post">
					<table cellpadding="0" cellspacing="1" width="100%" class="tablea" >
						<tr>
							<td class="formTitle" colspan="99">
								<span>添加/编辑  广告显示区域位置配置</span>
							</td>
						</tr>
						<tr>
							<td class="td_label">显示区域位置类型：</td>
							<td class="td_input" colspan="3">
								<input id="idAdd" name="configBean.id" type="hidden" value="${configBean.id}"/>
								<select id="typeCodeAdd" name="configBean.name" >
									<option  id="left_up" >区域-左  * 区域-上</option>
									<option  id="width_high" >区域-宽  * 区域-高</option>
								</select>
								<!-- 
								 <input id="typeCodeAdd" name="configBean.name" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${configBean.name}" maxlength="40"/> <font color="red">*</font>		
								 -->
							</td>
						</tr>
						<tr>
							<td class="td_label">显示区域位置值：</td>
							<td class="td_input" colspan="3">
								<input id="typeNameAdd" name="configBean.value" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${configBean.value}" maxlength="80"/> <font color="red">*</font>		
							</td>
						</tr>
						<tr>
							<td class="formBottom" colspan="99">
								<input id="addPositionTypeButton" 
								type="button"  
								class="b_add" 
								onfocus="blur()"/>
							</td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
		<!-- 
			<tr>
				<td style=" padding:1px;">
					<form action="queryPositionTypePage.do?method=queryPage"  id="queryPositionTypeForm" name="queryPositionTypeForm" method="post">
						<table cellpadding="0" cellspacing="1" width="100%" class="tablea" >
							<tr>
								<td class="formTitle" colspan="99">
									<span>查询 广告位类型</span>
								</td>
							</tr>
							<tr>
								<td class="td_label">类型名称：</td>
								<td class="td_input" colspan="3">
									<input id="typeNameQuery" name="typeNameQuery" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="80"/>		
								</td>	
							</tr>
							<tr>
								<td class="formBottom" colspan="99">
									<input id="queryPositionTypeButton" 
									type="button"  
									class="b_search" 
									onfocus="blur()"/>
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		 -->
		<tr>
			<td style="padding: 1px;">
					<table cellpadding="0" cellspacing="1" width="100%"
				class="taba_right_list" id="bm">
						<tr>
							<td colspan="23" style="padding-left: 8px; background: url(<%=path %>/images/menu_righttop.png) repeat-x; text-align: left; height: 26px;"><span>展示·广告显示区域位置列表</span></td>
						</tr>
						<tr>
							<td height="26px" align="center">序号</td>
							<td>显示区域位置类型</td>
							<td>显示区域位置</td>
							<td>操作</td>
						</tr>
						<c:set var="index" value="0"/>
						<c:choose>
							<c:when test="${!empty configBeans}">
								<c:forEach var="item" items="${configBeans}" varStatus="status">
									<tr>
									<c:set var="index" value="${index+1 }"/>
										<td align="center" height="26">${status.count}</td>
										<td>${item.name}</td>
										<td>${item.value}</td>
										<td>
											<form action="" method="get" id="listPositionTypeOperationForm${status.count}" name="listPositionTypeOperationForm${status.count}">
												<input id="id" name="configBean.id" type="hidden" value="${item.id}"/>
												<input id="typeCode" name="configBean.name" type="hidden"  value="${item.name}"/>	
												<input id="typeName" name="configBean.value" type="hidden"  value="${item.value}"/>	

												<input name="deletePositionType${status.count}" id="deletePositionType${status.count}" type="button" class="button_delete" value="" title="删除" onClick="operation('deletePositionType','#listPositionTypeOperationForm${status.count}','${item.id}')" />
												<input name="modifyPositionType${status.count}" id="modifyPositionType${status.count}"  type="button" class="button_halt" value="" title="修改" onClick="operation('modifyPositionType','#listPositionTypeOperationForm${status.count}','${item.id}','${item.name}','${item.value}')" />
											</form>
										</td>
									</tr>
								</c:forEach>
								<c:if test="${index<10}">
									<c:forEach begin="${index}" end="9" step="1">
										<tr>
											<td align="center" height="26">&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
									</c:forEach>
								</c:if>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="4">
										暂无记录
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
						<!-- 
						<tr>
							<td colspan="12">
								
							</td>
						</tr>
						<tr>
							<td height="34" colspan="23" style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: right;">
								<c:if test="${page.totalPage > 0 }">
									<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
									<a href="#">当前第${page.currentPage}/${page.totalPage}页</a>&nbsp;&nbsp;
									<c:choose>
										<c:when test="${page.currentPage==1&&page.totalPage!=1}">
											【<a href="queryPositionTypePage.do?method=queryPage&currentPage=${page.currentPage+1}&typeNameQuery=${typeNameQuery}">下一页</a>】
											【<a href="queryPositionTypePage.do?method=queryPage&currentPage=${page.totalPage}&typeNameQuery=${typeNameQuery}">末页</a>】
										</c:when>
										<c:when test="${page.currentPage==page.totalPage&&page.totalPage!=1 }">
											【<a href="queryPositionTypePage.do?method=queryPage&currentPage=1&typeNameQuery=${typeNameQuery}">首页</a>】 
											【<a href="queryPositionTypePage.do?method=queryPage&currentPage=${page.currentPage-1}&typeNameQuery=${typeNameQuery}">上一页</a>】
										</c:when>
										<c:when test="${page.currentPage>1&&page.currentPage<page.totalPage}">
											【<a href="queryPositionTypePage.do?method=queryPage&currentPage=1&typeNameQuery=${typeNameQuery}">首页</a>】 
											【<a href="queryPositionTypePage.do?method=queryPage&currentPage=${page.currentPage-1}&typeNameQuery=${typeNameQuery}">上一页</a>】
											【<a href="queryPositionTypePage.do?method=queryPage&currentPage=${page.currentPage+1}&typeNameQuery=${typeNameQuery}">下一页</a>】
											【<a href="queryPositionTypePage.do?method=queryPage&currentPage=${page.totalPage}&typeNameQuery=${typeNameQuery}">末页</a>】
										</c:when>
									</c:choose>
								</c:if>
							</td>
						</tr>
						 -->
					</table>
			</td>
		</tr>	
	</table>
</body>
</html>