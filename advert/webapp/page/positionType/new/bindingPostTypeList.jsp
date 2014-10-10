<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<input id="projetPath" type="hidden" value="<%=path%>" />
<link rel="stylesheet" type="text/css" href="<%=path%>/css/new/main.css">
<title></title>
<script language="javascript" type="text/javascript"
	src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=path %>/js/util/util.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=path %>/js/util/tools.js"></script>

<script type="text/javascript">
	
	
	var ptList="{'list':[";
     <c:choose>
		<c:when test="${!empty objectList}">
			<c:forEach var="object" items="${objectList}" varStatus="status">
				ptList+="{'id':'${object.id}','positionTypeCode':'${object.positionTypeCode}','positionTypeName':'${object.positionTypeName}','isAdd':'${object.isAdd}','isLoop':${object.isLoop},'isAlltime':'${object.isAlltime}','isText':'${object.isText}','isImage':'${object.isImage}','isVideo':'${object.isVideo}','isQuestion':'${object.isQuestion}','isArea':'${object.isArea}','isChannel':'${object.isChannel}','isFreq':'${object.isFreq}','deliveryType':'${object.deliveryType}','isCharacteristic':'${object.isCharacteristic}'}";
				<c:choose>
					<c:when test="${status.last}">
					</c:when>
					<c:otherwise>
						ptList+=',';
					</c:otherwise>
				</c:choose>						
			</c:forEach>
		</c:when>			
	</c:choose>
	ptList+="]}";
	
	var ptListObject=eval("("+ptList+")");
	
	$(function(){   
    	//奇数行的样式
    	//$("#bm").find("tr:even").addClass("sec");  
    	$("#bm").find("tr:odd").addClass("sec");  //偶数行的样式
	    $('#queryButton').click(function(){
	    	submitForm('#queryForm','queryPtype.do?contractBindingFlag=1');
	    });
	});
 
	function choosePositionType(id){
			if((!$.isEmptyObject(ptListObject))&&(ptListObject.list.length>0)){
				$(ptListObject.list).each(function(ptIndex,ptItem){
					if(ptItem.id==id){
						var jsonStr = deepCopy(ptItem);
						parent.alreadyChoosePtype=jsonStr;
						parent.initPtype();
						return;
					}
				});
			}
			parent.easyDialog.close();
	}

	function generateAccess(currentPage,positionTypeName,positionTypeCode){
		
		var path = 'queryPtype.do?contractBindingFlag=1&currentPage='+currentPage;
		if((!$.isEmptyObject(positionTypeName))){
			$('#positionTypeName').val(positionTypeName);
		}
		if((!$.isEmptyObject(positionTypeCode))){
			$('#positionTypeCode').val(positionTypeCode);
		}
		submitForm('#queryForm',path);
	}
</script>
<style type="text/css">
a {
	text-decoration: underline;
}
</style>
</head>

<body class="mainBody">
<input id="projetPath" type="hidden" value="<%=path%>"/>
<div class="search">
<div class="path"><img src="<%=path%>/images/new/filder.gif"
	width="15" height="13" />首页 >> 广告位管理 >> 广告位类型维护</div>
<div class="searchContent">
<table cellspacing="1" class="searchList">
	<tr class="title">
		<td colspan="2">查询条件</td>
	</tr>
	<tr>
		<td class="searchCriteria">
			<form action="queryPtype.do?contractBindingFlag=1"  id="queryForm" name="queryForm" method="post">
				<span>广告位类型编码：</span> 
					<input type="text" name="positionTypeCode" id="positionTypeCode"/> 
				<span>广告位类型名称：</span>
					<input id="positionTypeName" name="positionTypeName"  type="text" /> 
					<input id="queryButton" name="queryButton" align="right" type="button"  value="查询" class="btn" />
			</form>	
		</td>
	</tr>
</table>
<div id="messageDiv"
	style="margin-top: 15px; color: red; font-size: 14px; font-weight: bold;"></div>
<table cellspacing="1" class="searchList" id="bm">
	<tr class="title">
		<td height="28" class="dot">选项</td>
		<td height="26px" align="dot">序号</td>
		<td>广告位类型编码</td>
		<td>广告位类型名称</td>
	</tr>

		<c:choose>
			<c:when test="${!empty objectList}">
				<c:forEach var="object" items="${objectList}" varStatus="status">
					<tr>
						<td>
							<input type="radio" id="radio${object.id}" name="radio" value="${object.id}"
							onclick="choosePositionType('${object.id}')" />
						</td>
						<td align="center" height="26">${status.count}</td>
						<td>${object.positionTypeCode}</td>
						<td>${object.positionTypeName}</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="4">暂无记录</td>
				</tr>
			</c:otherwise>
		</c:choose>
	<tr>
		<td height="34" colspan="23"
			style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: right;">
		<c:if test="${page.totalPage > 0 }">
			<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
			 <a href="#">当前第${page.currentPage}/${page.totalPage}页</a>&nbsp;&nbsp;
			 <c:choose>
				<c:when test="${page.currentPage==1&&page.totalPage!=1}">
							【<a href="#"
						onclick="generateAccess('${page.currentPage+1}','${positionTypeName}','${positionTypeCode}')">下一页</a>】
							【<a href="#"
						onclick="generateAccess('${page.totalPage}','${positionTypeName}','${positionTypeCode}')">末页</a>】
				    </c:when>
				<c:when
					test="${page.currentPage==page.totalPage&&page.totalPage!=1 }">
							【<a href="#"
						onclick="generateAccess('1','${positionTypeName}','${positionTypeCode}')">首页</a>】 
							【<a href="#"
						onclick="generateAccess('${page.currentPage-1}','${positionTypeName}','${positionTypeCode}')">上一页</a>】
					 </c:when>
				<c:when
					test="${page.currentPage>1&&page.currentPage<page.totalPage}">
							【<a href="#"
						onclick="generateAccess('1','${positionTypeName}','${positionTypeCode}')">首页</a>】 
							【<a href="#"
						onclick="generateAccess('${page.currentPage-1}','${positionTypeName}','${positionTypeCode}')">上一页</a>】
							【<a href="#"
						onclick="generateAccess('${page.currentPage+1}','${positionTypeName}','${positionTypeCode}')">下一页</a>】
							【<a href="#"
						onclick="generateAccess('${page.totalPage}','${positionTypeName}','${positionTypeCode}')">末页</a>】
					 </c:when>
			</c:choose>
		</c:if></td>
	</tr>
</table>
</div>
</div>
</body>
</html>