<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" />
    <title>广告位详情信息</title>
	<script type='text/javascript' src="<%=path %>/js/new/avit.js"></script>
</head>

<body class="mainBody">

<div class="detail">
    <div style="position: relative">
        <table cellspacing="1" class="content" align="left">
            <tr class="title">
                <td colspan="4">广告位详情信息</td>
            </tr>
            <tr>
                <td width="12%" align="right">广告位编码：</td>
                <td width="33%">${pPackage.positionPackageCode}</td>
                <td width="12%" align="right">广告位名称：</td>
                <td width="33%">${pPackage.positionPackageName}</td>
            </tr>
            <tr class="sec">
                <td width="12%" align="right">子广告位个数：</td>
                <td width="33%">${pPackage.positionCount}</td>
                <td width="12%" align="right">投放方式：</td>
                <td width="33%">
	                <s:if test="pPackage.deliveryMode==0">投放式</s:if>
	                <s:elseif test="pPackage.deliveryMode==1">请求式</s:elseif>
                </td>
            </tr>
            <tr>
                <td width="12%" align="right">广告位类型：</td>
                <td width="33%">
                	<s:if test="pPackage.positionPackageType==0">双向实时广告</s:if>
                	<s:elseif test="pPackage.positionPackageType==1">双向实时请求广告</s:elseif>
                	<s:elseif test="pPackage.positionPackageType==2">单向实时广告</s:elseif>
                	<s:elseif test="pPackage.positionPackageType==3">单向非实时广告</s:elseif>
                </td>
                <td width="12%" align="right">高标清标识：</td>
                <td width="33%">
                	<s:if test="pPackage.videoType==0">只支持标清</s:if>
                	<s:elseif test="pPackage.videoType==1">只支持高清</s:elseif>
                	<s:elseif test="pPackage.videoType==2">高清标清都支持</s:elseif>
				</td>
            </tr>
            <tr class="sec">
                <td width="12%" align="right">是否轮询：</td>
                <td width="33%">
	                <s:if test="pPackage.isLoop==1">是</s:if>
	                <s:else>否</s:else>
                </td>
                <td width="12%" align="right">是否叠加：</td>
                <td width="33%">
	                <s:if test="pPackage.isAdd==1">是</s:if>
	                <s:else>否</s:else>
                </td>
            </tr>
            <tr>
                <td width="12%" align="right">是否可以投放视频：</td>
                <td width="33%">
	                <s:if test="pPackage.isVideo==1">是</s:if>
	                <s:else>否</s:else>
                </td>
                <td width="12%" align="right">是否可以投放图片：</td>
                <td width="33%">
	                <s:if test="pPackage.isImage==1">是</s:if>
	                <s:else>否</s:else>
                </td>
            </tr>
            <tr class="sec">
                <td width="12%" align="right">否可以投放文字：</td>
                <td width="33%">
	                <s:if test="pPackage.isText==1">是</s:if>
	                <s:else>否</s:else>
                </td>
                <td width="12%" align="right">是否可以投放问卷：</td>
                <td width="33%">
	                <s:if test="pPackage.isQuestion==1">是</s:if>
	                <s:else>否</s:else>
                </td>
            </tr>
            <tr>
	            <td width="12%" align="right">投放策略描述：</td>
	            <td colspan="3">
	                <textarea disabled="disabled" cols="50" rows="3">${pPackage.ployDescription }</textarea>
	            </td> 
	        </tr>
	        <tr class="sec">
	            <td width="12%" align="right">广告位描述：</td>
	            <td colspan="3">
	                <textarea disabled="disabled" cols="50" rows="3">${pPackage.description }</textarea>
	            </td> 
	        </tr>
        </table>
		<table cellspacing="1" class="searchList">
			<tr class="title">
                <td colspan="3">子广告位列表</td>
            </tr>
		      <tr class="title">
		        <td>广告位编码</td>
		        <td>广告位名称</td>
		        <td>是否高清</td>
		      </tr>
		      <c:forEach items="${pPackage.adPositionList}" var="subAd" varStatus="pl">
				<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
					<td><a href="getAdvertPositionById.do?id=${subAd.id}"><c:out value="${subAd.positionCode}" /></a></td>
					<td><c:out value="${subAd.positionName}" /></td>
					<td>
						<c:choose>
							<c:when test="${subAd.isHD==1}">是</c:when>
							<c:when test="${subAd.isHD==0}">否</c:when>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
    </table>
    </div>
    <div align="center" class="action">
        <input type="button" class="btn" value="返回" onclick="window.location.href='queryPositionPackageList.do'" />
    </div>
</div>
</body>
</html>