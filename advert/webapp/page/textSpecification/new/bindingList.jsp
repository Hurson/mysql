<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<input id="projetPath" type="hidden" value="<%=path%>" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<title>广告系统</title>

<style type="text/css">
        a{text-decoration:underline;}
</style>

<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/demos.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/position/speciCommon.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/util/util.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/util/tools.js"></script>
<script>
var isHd = '${isHd}';
var sdAndhd = '${sdAndhd}';
$(function(){
	$("#system-dialog").hide();   
    $("#bm").find("tr:odd").addClass("sec"); //偶数行的样式
    $('#queryButton').click(function(){
    	submitForm('#queryForm','queryText.do?isHd=${isHd}&sdAndhd=${sdAndhd}');
    });
});

function chooseTextSpecification(id,textDesc,textLength,isLink){
	var textSpeci = "{'textDataType':'"+isHd+"','textDesc':'"+textDesc+"','textLength':'"+textLength+"','isLink':'"+isLink+"','id':'"+id+"'}";
	textSpeci=eval('('+textSpeci+')');
	parent.fillSpeciInPage(isHd,'content',sdAndhd,textSpeci);
	createDialog('绑定文字规格成功');
}

function generateAccess(currentPage,contentDescQuery,isHd,sdAndhd){
	
	var path = 'queryText.do?currentPage='+currentPage+'&isHd='+isHd+'&sdAndhd='+sdAndhd;
	if((!$.isEmptyObject(contentDescQuery))){
		$('#contentDescQuery').val(contentDescQuery);
	}
	submitForm('#queryForm',path);
}
</script>
<style>
.treven{ background:#FFFFFF;}
.trodd{ background:#f6f6f6; }
#loading{background-image:url(<%=path %>/images/jqueryui/loading.gif);background-position:0px 0px;background-repeat:no-repeat; position:absolute;width:50px;height:50px;top:60%;left:50%;margin-left:-25px;text-align:center;}

</style>
</head>

<body class="mainBody">

<div class="search">
<div class="path">
	<img src="<%=path %>/images/new/filder.gif" width="15" height="13" />首页 >> 广告位管理 >> 广告位维护 >> 绑定文字规格
</div>
<div class="searchContent" >
	<form action="queryText.do?isHd=${isHd}&sdAndhd=${sdAndhd}"  id="queryForm" name="queryForm" method="post">
		<table cellspacing="1" class="searchList">
		  <tr class="title">
		    <td>查询条件</td>
		  </tr>
		  <tr>
		    <td class="searchCriteria">
			      <span>文字规格描述：</span>
			      <input id="contentDescQuery"  name="contentDescQuery"  type="text" maxlength="80"/>
			      <input id="queryButton" name="queryButton" type="button" value="查询" class="btn"/>
		    </td>
		  </tr>
		</table>
	</form>
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td height="28" class="dot">
        	选项
        </td>
        <td>序号</td>
		<td>文字规格描述</td>
		<td>文字长度</td>
		<td>是否有链接</td>
    </tr>
    <c:choose>
						<c:when test="${!empty objectList}">
							<c:forEach var="object" items="${objectList}" varStatus="status">
								<tr>
									<td align="center"><input type="radio" id="textSpeci${object.id}" name="textSpeci" value="${object.id}" onclick="chooseTextSpecification('${object.id}','${object.textDesc}','${object.textLength}','${object.isLink}')"/></td>
									<td align="center" height="26">${status.count}</td>
									<td>${object.textDesc}</td>
									<td>${object.textLength}</td>
									<td>
										<c:choose>
											<c:when test="${object.isLink==1}">是</c:when>
											<c:when test="${object.isLink==0}">否</c:when>
											<c:otherwise>
												未选择
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
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
    <td colspan="8">
       	<div class="page">
            <tr>
						<td height="34" colspan="23"
							style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: right;">
							<c:if test="${page.totalPage > 0 }">
								<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
								<a href="#">当前第${page.currentPage}/${page.totalPage}页</a>&nbsp;&nbsp;
								<c:choose>
									<c:when test="${page.currentPage==1&&page.totalPage!=1}">
										【<a href="#" onclick="generateAccess('${page.currentPage+1}','${contentDescQuery}','${isHd}','${sdAndhd}')">下一页</a>】
										【<a href="#" onclick="generateAccess('${page.totalPage}','${contentDescQuery}','${isHd}','${sdAndhd}')">末页</a>】
									</c:when>
									<c:when test="${page.currentPage==page.totalPage&&page.totalPage!=1 }">
										【<a href="#" onclick="generateAccess('1','${contentDescQuery}','${isHd}','${sdAndhd}')">首页</a>】 
										【<a href="#" onclick="generateAccess('${page.currentPage-1}','${contentDescQuery}','${isHd}','${sdAndhd}')">上一页</a>】
									</c:when>
									<c:when test="${page.currentPage>1&&page.currentPage<page.totalPage}">
										【<a href="#" onclick="generateAccess('1','${contentDescQuery}','${isHd}','${sdAndhd}')">首页</a>】 
										【<a href="#" onclick="generateAccess('${page.currentPage-1}','${contentDescQuery}','${isHd}','${sdAndhd}')">上一页</a>】
										【<a href="#" onclick="generateAccess('${page.currentPage+1}','${contentDescQuery}','${isHd}','${sdAndhd}')">下一页</a>】
										【<a href="#" onclick="generateAccess('${page.totalPage}','${contentDescQuery}','${isHd}','${sdAndhd}')">末页</a>】
									</c:when>
								</c:choose>
							</c:if>
						</td>
					</tr>
        </div>
    </td>
  </tr>
</table>
</div>
</div>
<div id="system-dialog" title="友情提示">
	  <p>
	    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>
	    <span id="content"></span>
	  </p>
</div>
</body>
</html>