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
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/demos.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/position/speciCommon.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/util/util.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/util/tools.js"></script>

<title>广告系统</title>
<script>
var isHd = '${isHd}';
var sdAndhd = '${sdAndhd}';
$(function(){
	$("#system-dialog").hide();   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
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

<body onload="">
	<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
		<tr>
			<td style=" padding:1px;">
				<form action="queryText.do?isHd=${isHd}&sdAndhd=${sdAndhd}"  id="queryForm" name="queryForm" method="post">
					<table cellpadding="0" cellspacing="1" width="100%" class="tablea" >
						<tr>
							<td class="formTitle" colspan="99">
								<span>查询 文字规格</span>
							</td>
						</tr>
						<tr>
							<td class="td_label">文字规格描述：</td>
							<td class="td_input" colspan="3">
								<input id="contentDescQuery" name="contentDescQuery" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="80"/>		
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
		<td style="padding:1px;height: 100%;">
			<div>  
				<table cellpadding="0" cellspacing="1" width="100%"
			class="taba_right_list" id="bm">
					<tr>
						<td colspan="23" style="padding-left: 8px; background: url(<%=path %>/images/menu_righttop.png) repeat-x; text-align: left; height: 26px;"><span>展示·文字规格列表</span></td>
					</tr>
					<tr>
						<td height="26px" align="center">选项</td>
						<td height="26px" align="center">序号</td>
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
						<td colspan="12"></td>
					</tr>
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
				</table>
			</div>
		</td>
	</tr>
	</table>
	<div id="system-dialog" title="友情提示">
	  <p>
	    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>
	    <span id="content"></span>
	  </p>
	</div>
</body>
</html>