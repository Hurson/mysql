<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<input id="projetPath" type="hidden" value="<%=path%>" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css">
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/demos.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/position/speciCommon.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/util/util.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/util/tools.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>

<title>广告系统</title>

<style type="text/css">
a{text-decoration:underline;}
.treven{ background:#FFFFFF;}
.trodd{ background:#f6f6f6;}
.easyDialog_wrapper{ width:1000px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
.easyDialog_text{}
#loading{background-image:url(<%=path %>/images/jqueryui/loading.gif);background-position:0px 0px;background-repeat:no-repeat; position:absolute;width:50px;height:50px;top:60%;left:50%;margin-left:-25px;text-align:center;}
</style>

<script type="text/javascript">
    var isHd = '${isHd}';
	var sdAndhd = '${sdAndhd}';

$(function(){
	$("#system-dialog").hide();
    $("#bm").find("tr:odd").addClass("sec");  //偶数行的样式
    
    $('#queryButton').click(function(){
    	submitForm('#queryForm','queryImage.do?isHd=${isHd}&sdAndhd=${sdAndhd}');
    });
});

function chooseImageSpeci(id,imageDesc,imageLength,imageWidth,fileSize,type,isLink){
	var imageSpeci = "{'imageDataType':'"+isHd+"','imageId':'"+id+"','imageDesc':'"+imageDesc+"','imageLength':'"+imageLength+"','imageWidth':'"+imageWidth+"','imageFileSize':'"+fileSize+"','imageType':'"+type+"','imageIsLink':'"+isLink+"'}";
	imageSpeci=eval('('+imageSpeci+')');
	parent.fillSpeciInPage(isHd,'image',sdAndhd,imageSpeci);
	createDialog('绑定图片规格成功');
}

function generateAccess(currentPage,imageDescQuery,isHd,sdAndhd){
	
	var path = 'queryImage.do?currentPage='+currentPage+'&isHd='+isHd+'&sdAndhd='+sdAndhd;
	if((!$.isEmptyObject(imageDescQuery))){
		$('#imageDescQuery').val(imageDescQuery);
	}
	submitForm('#queryForm',path);
}
</script>
</head>

<body class="mainBody">

<div class="search">
<div class="path">
	<img src="<%=path %>/images/new/filder.gif" width="15" height="13" />首页 >> 广告位管理 >> 广告位维护>> 绑定图片规格
</div>
<div class="searchContent" >
<form action=""  id="queryForm" name="queryForm" method="post">
	<table cellspacing="1" class="searchList">
		  <tr class="title">
		    	<td>查询 图片规格</td>
		  </tr>
		  <tr>
		      <td class="searchCriteria">
			      <span>图片规格描述：</span>
			      <input id="imageDescQuery"  name="imageDescQuery"  type="text" maxlength="80"/>
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
        <td height="26px" align="center">序号</td>
		<td>图片规格描述</td>
		<td>图片长</td>
		<td>图片宽</td>
		<td>文件大小</td>
		<td>类型</td>
		<td>是否有链接</td>
    </tr>
    <c:choose>
						<c:when test="${!empty objectList}">
							<c:forEach var="object" items="${objectList}" varStatus="status">
								<tr>
									<td align="center">
										<input type="radio" id="imageSpeci${object.id}" name="imageSpeci" value="${object.id}" onclick="chooseImageSpeci('${object.id}','${object.imageDesc}','${object.imageLength}','${object.imageWidth}','${object.fileSize}','${object.type}','${object.isLink}')"/>
									</td>
									<td align="center" height="26">${status.count}</td>
									<td>${object.imageDesc}</td>
									<td>${object.imageLength}</td>
									<td>${object.imageWidth}</td>
									<td>${object.fileSize}</td>
									<td>${object.type}</td>
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
								<td colspan="8">
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
									【<a href="#" onclick="generateAccess('${page.currentPage+1}','${imageDescQuery}','${isHd}','${sdAndhd}')">下一页</a>】
									【<a href="#" onclick="generateAccess('${page.totalPage}','${imageDescQuery}','${isHd}','${sdAndhd}')">末页</a>】
								</c:when>
								<c:when test="${page.currentPage==page.totalPage&&page.totalPage!=1 }">
									【<a href="#" onclick="generateAccess('1','${imageDescQuery}','${isHd}','${sdAndhd}')">首页</a>】 
									【<a href="#" onclick="generateAccess('${page.currentPage-1}','${imageDescQuery}','${isHd}','${sdAndhd}')">上一页</a>】
								</c:when>
								<c:when test="${page.currentPage>1&&page.currentPage<page.totalPage}">
									【<a href="#" onclick="generateAccess('1','${imageDescQuery}','${isHd}','${sdAndhd}')">首页</a>】 
									【<a href="#" onclick="generateAccess('${page.currentPage-1}','${imageDescQuery}','${isHd}','${sdAndhd}')">上一页</a>】
									【<a href="#" onclick="generateAccess('${page.currentPage+1}','${imageDescQuery}','${isHd}','${sdAndhd}')">下一页</a>】
									【<a href="#" onclick="generateAccess('${page.totalPage}','${imageDescQuery}','${isHd}','${sdAndhd}')">末页</a>】
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