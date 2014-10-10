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
<script language="javascript" type="text/javascript" src="<%=path %>/js/positionType/addPositionType.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="<%=path %>/js/jquery/validate/jquery.validate.messages_cn.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/util/util.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/util/tools.js"></script>
<title>广告系统</title>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
    $('#queryButton').click(function(){
    	submitForm('#queryForm','queryPtype.do?contractBindingFlag=1');
    });
});

function choosePositionType(positionTypeId,positionTypeName){
		//绑定广告位类型id
		$("#positionTypeId",window.parent.document).val(positionTypeId);
		//绑定广告位类型名称
		$("#positionTypeName",window.parent.document).val(positionTypeName);
		parent.easyDialog.close();
}

function generateAccess(currentPage,typeNameQuery,isHd,sdAndhd){
	
	var path = 'queryPtype.do?contractBindingFlag=1&currentPage='+currentPage;
	if((!$.isEmptyObject(typeNameQuery))){
		$('#typeNameQuery').val(typeNameQuery);
	}
	submitForm('#queryForm',path);
}
</script>

<style>
.treven{ background:#FFFFFF;}
.trodd{ background:#f6f6f6;}
.easyDialog_wrapper{height:400px;width:1000px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
.easyDialog_text{}
</style>
</head>

<body>
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td style=" padding:1px;">
				<form action="queryPtype.do?contractBindingFlag=1"  id="queryForm" name="queryForm" method="post">
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
								<input id="queryButton" 
								type="button"  
								class="b_search" 
								onfocus="blur()"/>
							</td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
		<tr>
			<td style="padding: 1px;">
					<table cellpadding="0" cellspacing="1" width="100%"
				class="taba_right_list" id="bm">
						<tr>
							<td colspan="23" style="padding-left: 8px; background: url(<%=path %>/images/menu_righttop.png) repeat-x; text-align: left; height: 26px;"><span>展示·广告位类型列表</span></td>
						</tr>
						<tr>
							<td>选项</td>
							<td height="26px" align="center">序号</td>
							<td>类型编码</td>
							<td>类型名称</td>
						</tr>
						<c:choose>
							<c:when test="${!empty advertPositionTypeList}">
								<c:forEach var="advertPositionType" items="${advertPositionTypeList}" varStatus="status">
									<tr>
										<td><input type="radio" id="advertPositionTypeCheckbox${advertPositionType.id}" name="advertPositionTypeCheckbox" value="${advertPositionType.id}" onclick="choosePositionType('${advertPositionType.id}','${advertPositionType.positionTypeName}')"/></td>
										<td align="center" height="26">${status.count}</td>
										<td>${advertPositionType.positionTypeCode}</td>
										<td>${advertPositionType.positionTypeName}</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="4">
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
							<td height="34" colspan="23" style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: right;">
								<c:if test="${page.totalPage > 0 }">
									<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
									<a href="#">当前第${page.currentPage}/${page.totalPage}页</a>&nbsp;&nbsp;
									<c:choose>
										<c:when test="${page.currentPage==1&&page.totalPage!=1}">
											【<a href="#" onclick="generateAccess('${page.currentPage+1}','${typeNameQuery}','${isHd}','${sdAndhd}')">下一页</a>】
											【<a href="#" onclick="generateAccess('${page.totalPage}','${typeNameQuery}','${isHd}','${sdAndhd}')">末页</a>】
										</c:when>
										<c:when test="${page.currentPage==page.totalPage&&page.totalPage!=1 }">
											【<a href="#" onclick="generateAccess('1','${typeNameQuery}','${isHd}','${sdAndhd}')">首页</a>】 
											【<a href="#" onclick="generateAccess('${page.currentPage-1}','${typeNameQuery}','${isHd}','${sdAndhd}')">上一页</a>】
										</c:when>
										<c:when test="${page.currentPage>1&&page.currentPage<page.totalPage}">
											【<a href="#" onclick="generateAccess('1','${typeNameQuery}','${isHd}','${sdAndhd}')">首页</a>】 
											【<a href="#" onclick="generateAccess('${page.currentPage-1}','${typeNameQuery}','${isHd}','${sdAndhd}')">上一页</a>】
											【<a href="#" onclick="generateAccess('${page.currentPage+1}','${typeNameQuery}','${isHd}','${sdAndhd}')">下一页</a>】
											【<a href="#" onclick="generateAccess('${page.totalPage}','${typeNameQuery}','${isHd}','${sdAndhd}')">末页</a>】
										</c:when>
									</c:choose>
								</c:if>
							</td>
						</tr>
					</table>
			</td>
		</tr>	
	</table>
</body>
</html>