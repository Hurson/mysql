<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<title>查看广告位绑定的规格</title>
</head>
<body class="mainBody">
	<div class="detail">
		<div class="path">首页 >> 广告位包查询 >> 子广告位绑定的规格</div>
		<div style="position: relative">
		<table cellspacing="1" class="searchList">
				<tr class="title">
  			 		<td colspan="6">${advertPosition.positionName}</td>
				</tr>
		</table>	
		<c:if test="${videoSpe != null}">
		<table cellspacing="1" class="searchList">
			<tr class="title">
 			 	<td colspan="5">视频规格</td>
			</tr>
    		<tr class="title">
    			<td>分辨率</td>
    			<td>时长（秒）</td>
    			<td>视频类型</td>
    			<td>文件最大尺寸</td>
    			<td>描述</td>
    		</tr>
    		<tr>
    			<td><a href="getVideoSpecification.do?videoSpe.id=${videoSpe.id}&advertPosition.id=${advertPosition.id}"><c:out value="${videoSpe.resolution}" /></a></td>
    			<td><c:out value="${videoSpe.duration}" /></td>
    			<td><c:out value="${videoSpe.type}" /></td>
    			<td><c:out value="${videoSpe.fileSize}" /></td>
    			<td><c:out value="${videoSpe.movieDesc}" /></td>
    		</tr>
		</table>
		</c:if>	
		<c:if test="${imageSpe != null}">	
		<c:if test="${imageSpe.type == 'ZIP'}">
		<table cellspacing="1" class="searchList">
			<tr class="title">
  			 	<td colspan="4">zip规格</td>
			</tr>
    		<tr class="title">
    			<td>类型</td>
    			<td>文件大小</td>
    			<td>是否链接</td>
    			<td>描述</td>
    		</tr>
    		<tr>
    			<td><a href="getImageSpecification.do?imageSpe.id=${imageSpe.id}&advertPosition.id=${advertPosition.id}">
    			<c:out value="${imageSpe.type}" /></a></td>
    			<td><c:out value="${imageSpe.fileSize}" /></td>
    			<td>
					<s:if test="imageSpe.isLink==1">是</s:if>
		            <s:else>否</s:else>
				</td>
    			<td><c:out value="${imageSpe.imageDesc}" /></td>
    		</tr>
		</table>
		</c:if>
		<c:if test="${imageSpe.type != 'ZIP'}">
		<table cellspacing="1" class="searchList">
			<tr class="title">
  			 	<td colspan="6">图片规格</td>
			</tr>
    		<tr class="title">
    			<td>宽度</td>
    			<td>高度</td>
    			<td>类型</td>
    			<td>文件大小</td>
    			<td>是否链接</td>
    			<td>描述</td>
    		</tr>
    		<tr>
    			<td><a href="getImageSpecification.do?imageSpe.id=${imageSpe.id}&advertPosition.id=${advertPosition.id}"><c:out value="${imageSpe.imageWidth}" /></a></td>
    			<td><c:out value="${imageSpe.imageHeight}" /></td>
    			<td><c:out value="${imageSpe.type}" /></td>
    			<td><c:out value="${imageSpe.fileSize}" /></td>
    			<td>
					<s:if test="imageSpe.isLink==1">是</s:if>
		            <s:else>否</s:else>
				</td>
    			<td><c:out value="${imageSpe.imageDesc}" /></td>
    		</tr>
		</table>
		</c:if>
		</c:if>	
		<c:if test="${textSpe != null}">	
		<table cellspacing="1" class="searchList">
			<tr class="title">
  			 	<td colspan="3">文字规格</td>
			</tr>
    		<tr class="title">
    			<td>字段长度</td>
    			<td>是否链接</td>
    			<td>描述</td>
    		</tr>
    		<tr>
    			<td><a href="getTextSpecification.do?textSpe.id=${textSpe.id}&advertPosition.id=${advertPosition.id}"><c:out value="${textSpe.textLength}" /></a></td>
    			<td>
					<s:if test="textSpe.isLink==1">是</s:if>
		            <s:else>否</s:else>
				</td>
    			<td><c:out value="${textSpe.textDesc}" /></td>
    		</tr>
		</table>
		</c:if>	
		<c:if test="${questionnaireSpe != null}">	
		<table cellspacing="1" class="searchList">
			<tr class="title">
  			 	<td colspan="5">问卷规格</td>
			</tr>
    		<tr class="title">
    			<td>类型</td>
    			<td>问题个数</td>
    			<td>每个问题选项个数</td>
    			<td>问题最大字数</td>
    			<td>过滤内容</td>
    		</tr>
    		<tr>
    			<td><a href="getQuestionnaireSpecification.do?questionnaireSpe.id=${questionnaireSpe.id}&advertPosition.id=${advertPosition.id}"><c:out value="${questionnaireSpe.type}" /></a></td>
    			<td><c:out value="${questionnaireSpe.fileSize}" /></td>
    			<td><c:out value="${questionnaireSpe.optionNumber}" /></td>
    			<td><c:out value="${questionnaireSpe.maxLength}" /></td>
    			<td><c:out value="${questionnaireSpe.excludeContent}" /></td>
    		</tr>
		</table>
		</c:if>	
		
		</div>
		<div align="center" class="action">
	        <input type="button" class="btn" value="返回" onclick="window.location.href='getAdvertPositionById.do?id=${advertPosition.id}'" />
	    </div>
	</div>
</body>
</html>