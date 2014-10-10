<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<input id="projetPath" type="hidden" value="<%=path%>"/>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<link href="<%=path %>/css/new/common/dhtmlx/tree/css/dhtmlxtree.css" rel="stylesheet" type="text/css" media="all" />
<script src="<%=path %>/css/new/common/dhtmlx/tree/js/dhtmlxcommon.js" type="text/javascript"></script>
<script src="<%=path %>/css/new/common/dhtmlx/tree/js/dhtmlxtree.js" type="text/javascript"></script>
<script src="<%=path %>/css/new/common/dhtmlx/tree/js/test.js" type="text/javascript"></script>

<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/position/new/listPosition.js"></script>
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/tools.js"></script>
<script type="text/javascript">

	$(function(){
		var positionName = '${positionName}';
		if((!$.isEmptyObject(positionName))){
			$('#positionName').val(positionName);
		}
		
		var positionTypeId = '${positionTypeId}';
		if((!$.isEmptyObject(positionTypeId))){
			$('#positionTypeId').val(positionTypeId);
		}
		
		var positionTypeName = '${positionTypeName}';
		if((!$.isEmptyObject(positionTypeName))){
			$('#positionTypeName').val(positionTypeName);
		}
		var isHd = '${isHd}';
		if((!$.isEmptyObject(isHd))){
			$('#isHd').val(isHd);
		}
		
		var deliveryMode = '${deliveryMode}';
		if((!$.isEmptyObject(deliveryMode))){
			$('#deliveryMode').val(deliveryMode);
		}
		
		$("#cleanButton").click(function(){
    		$('#positionName').val('');
    		$('#positionTypeId').val('');
    		$('#positionTypeName').val('');
    		$('#isHd').val('');
    		$('#deliveryMode').val('');
    		$("#searchForm").submit();
     	});
	});
	
	function disableDeleteDialog(){
		createDialog('该广告位正在被使用，不允许删除或修改');
	}
	
	
	
</script>
<title>广告位展示</title>

<style type="text/css">
    a{text-decoration:underline;}
    .easyDialog_wrapper{ width:800px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
	.e_input_add {
	background:url(<%=path%>/images/add.png) right no-repeat #ffffff;
	}
</style>
</head>

<body class="mainBody" onload="">
	<input id="projetPath" type="hidden" value="<%=path%>"/>	
<div class="search">
	<div class="path"><img src="<%=path %>/images/new/filder.gif" width="15" height="13" />首页 >> 广告位管理 >> 广告位维护</div>
	<div class="searchContent" >
	
		<table cellspacing="1" class="searchList">
		  <tr class="title">
		    	<td colspan="2">查询条件</td>
		  </tr>
		  <tr>
			    <td class="searchCriteria">	
			    	<form action="" id="searchForm" name="searchForm" method="post">
				    	  <span>广告位名称：</span>
				    	  <input  id="positionName" name="advertPosition.positionName" type="text" />
						  <span>广告位类型：</span>
					      <input id="positionTypeId"  name="advertPosition.positionTypeId"  type="hidden"/>
						  <input id="positionTypeName" name="advertPosition.positionTypeName"  type="text"  class="e_input_add"/>
						  <span>是否高清：</span>
						  <select id="isHd"  name="advertPosition.isHd" class="select_style">
							    <option value="-1">请选择</option>
								<option value="0" >标清</option>
								<option value="1" >高清</option>
						  </select>
						 <span>投放方式：</span>	      
						<select id="deliveryMode" name="advertPosition.deliveryMode"  class="select_style">
						    <option value="-1">请选择</option>
							<option value="0" >投放式</option>
							<option value="1" >请求式</option>
						</select>
						<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
						<input id="searchPositionSubmit" name="searchPositionSubmit" type="submit" value="查询" class="btn"/>
						<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
						<input id="cleanButton" name="cleanButton" type="button" value="查全部" class="btn"/>
					</form>
				</td>
		</tr>
		
		</table>
		<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
		<div id="productList0">
				<table cellspacing="1" class="searchList" id="bm">
				    <tr class="title">
				        <td height="28" class="dot">
				        	选项
				        </td>
				        <td height="26px" align="center">序号</td>
				        <td>特征值</td>
						<td>广告位名称</td>
				        <td>广告位类型名称</td>    
				        <td>是否高清</td>
				        <td>是否叠加</td>
				        <td>是否轮询(素材个数)</td>
				        <td>投放方式</td>
				        <td>操作</td>
				    </tr>
				  	<c:choose>
				  		<c:when test="${!empty positionList}">
				  		
				  			<c:forEach var="advertPosition" items="${positionList}" varStatus="status">
								<tr>
									<td>
										<c:choose>
											<c:when test="${advertPosition.state eq '1'}">
												<input type="checkbox" name="checkBoxElement" value="${advertPosition.id}" disabled="disabled" onclick="disableDeleteDialog()"/>
											</c:when>
											<c:otherwise>
												<input type="checkbox" name="checkBoxElement" value="${advertPosition.id}"/>
											</c:otherwise>
										</c:choose>
									</td>
									<td align="center" height="26">${status.count}</td>
									<td>${advertPosition.characteristicIdentification}</td>
									<td>
										<c:choose>
											<c:when test="${advertPosition.isCharacteristic==1}">
												${advertPosition.positionName}
											</c:when>
											<c:otherwise>
											
												<c:choose>
													<c:when test="${advertPosition.state eq '1'}">
														<a name="modifyPosition${status.count}" id="modifyPosition${status.count}" onClick="disableDeleteDialog()">
															${advertPosition.positionName}
														</a>
													</c:when>
													<c:otherwise>
														<form action="" method="get" id="listPositionOperationForm${status.count}" name="listPositionOperationForm${status.count}">
															<a name="modifyPosition${status.count}" id="modifyPosition${status.count}" onClick="operation('modifyPosition','#listPositionOperationForm${status.count}','${advertPosition.id}')">
																<input id="id" name="advertPosition.id" type="hidden" value="${advertPosition.id}"/>
																${advertPosition.positionName}
															</a>
														</form>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
								    </td>
									<td>${advertPosition.positionTypeName}</td>
									<td>
										<c:choose>
											<c:when test="${advertPosition.isHd==0}">
												标清(否)
											</c:when>
											<c:when test="${advertPosition.isHd==1}">
												高清(是)
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
											<c:when test="${advertPosition.isLoop==0}">
												否(0)
											</c:when>
											<c:when test="${advertPosition.isLoop==1}">
												是(${advertPosition.materialNumber})
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
									<td>
										<form action="" method="get" id="listPositionOperationForm${status.count}" name="listPositionOperationForm${status.count}">
											<input id="id" name="advertPosition.id" type="hidden" value="${advertPosition.id}"/>
										</form>
										<a href="#" onclick="operation('viewOccupyStatesPosition','#listPositionOperationForm${status.count}','${advertPosition.id}')">查看占用情况</a>
									</td>
								</tr>
							</c:forEach>
				  		</c:when>
				  		<c:otherwise>
				  			<tr>
				  				<td colspan="10">
				  					暂无记录
				  				</td>
				  			</tr>
				  		</c:otherwise>
				  	</c:choose>
				  	<tr>
					    <td colspan="10">
					        <input id="deletePositionButton" name="deletePositionButton" type="button" value="删除" class="btn"/>&nbsp;&nbsp;
					        <input id="addPositionButton" name="addPositionButton" type="button" value="添加" class="btn"/>
					        <c:if test="${page.totalPage > 0 }">
								<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
												<a href="#">当前第${page.currentPage}/${page.totalPage}页</a>&nbsp;&nbsp;
												<c:choose>
													<c:when test="${page.currentPage==1&&page.totalPage!=1}">
														【<a href="#" onclick="generateAccess('${page.currentPage+1}','${positionName}','${positionTypeId}','${positionTypeName}','${isHd}','${deliveryMode}')">下一页</a>】
														【<a href="#" onclick="generateAccess('${page.totalPage}','${positionName}','${positionTypeId}','${positionTypeName}','${isHd}','${deliveryMode}')">末页</a>】
													</c:when>
													<c:when test="${page.currentPage==page.totalPage&&page.totalPage!=1 }">
														【<a href="#" onclick="generateAccess('1','${positionName}','${positionTypeId}','${positionTypeName}','${isHd}','${deliveryMode}')">首页</a>】 
														【<a href="#" onclick="generateAccess('${page.currentPage-1}',positionName,positionTypeId,positionTypeName,isHd,deliveryMode)">上一页</a>】
													</c:when>
													<c:when test="${page.currentPage>1&&page.currentPage<page.totalPage}">
														【<a href="#" onclick="generateAccess('1','${positionName}','${positionTypeId}','${positionTypeName}','${isHd}','${deliveryMode}')">首页</a>】 
														【<a href="#" onclick="generateAccess('${page.currentPage-1}','${positionName}','${positionTypeId}','${positionTypeName}','${isHd}','${deliveryMode}')">上一页</a>】
														【<a href="#" onclick="generateAccess('${page.currentPage+1}','${positionName}','${positionTypeId}','${positionTypeName}','${isHd}','${deliveryMode}')">下一页</a>】
														【<a href="#" onclick="generateAccess('${page.totalPage}','${positionName}','${positionTypeId}','${positionTypeName}','${isHd}','${deliveryMode}')">末页</a>】
													</c:when>
												</c:choose>
							</c:if> 
					   </td>
				  	</tr>
				</table>
			</div>
		
		</div>
	</div>
</body>
<div id="system-dialog" title="友情提示">
  <p>
    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>
    <span id="content"></span>
  </p>
</div>
<div id="dialog-conform" title="再次确认">
  <p>
    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>
        即将开始删除，是否继续
  </p>
</div>
</html>