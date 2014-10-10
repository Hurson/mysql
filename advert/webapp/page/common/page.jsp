<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/new/css/main.css"> -->
<style type="text/css">
       .pagebtn{ background:url(<%=request.getContextPath()%>/images/new/btn_bg.gif) repeat-x center;padding:1px 50px 1px 8px; border:1px solid #8ab5c3; width:auto; height:24px}
</style>
<%
	String ctx = request.getContextPath();
%>

 <div class="page" style="display:inline;">
 <div style="display:inline;float: left;">
 <p>共${page.count}条记录
    </p>
    <b>${page.pageNo}/${page.totalPage}</b>
    
    <p>每页</p>
    <select id="pageSizeselect" onchange="changePagesize(this.value);" >
    	<option value="10"  <c:if test="${page.pageSize==10}">selected="selected"</c:if>>10</option>
    	<option value="20" <c:if test="${page.pageSize==20}">selected="selected"</c:if>>20</option>
    	<option value="50" <c:if test="${page.pageSize==50}">selected="selected"</c:if>>50</option>
    	<option value="100" <c:if test="${page.pageSize==100}">selected="selected"</c:if>>100</option>
    </select>
    <p>条</p>
    &nbsp;&nbsp;
</div>
<div style="display:inline;float: left;">    
    <c:choose>
        <c:when test="${page.pageNo >1}">
            <input type="button" class="pagebtn" onclick="javascript:goPage('${page.pageNo-1}')"
                   value="上一页"/></c:when>
        <c:otherwise>
            <input type="button" class="pagebtn" onclick="javascript:goPage('${page.pageNo-1}')"
                   value="上一页" disabled="disabled"/>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${page.pageNo < page.totalPage}">
            <input type="button" class="pagebtn" onclick="javascript:goPage('${page.pageNo+1}')"
                   value="下一页"/>
        </c:when>
        <c:otherwise>
            <input type="button" class="pagebtn" onclick="javascript:goPage('${page.pageNo-1}')"
                   value="下一页" disabled="disabled"/>
        </c:otherwise>
    </c:choose>
</div>
<div style="display:inline;float: left;">
    <span>至</span>
    <input style="repeat-x center;padding:1px 8px 1px 8px; border:1px solid #8ab5c3;width:40px; height:20px" maxlength="8" onkeyup="checkPageNo(this)" type="text" value="${page.pageNo}"
                                                  id="gotoPageNo"/>
    <input type="button" value="GO" class="btn" onclick="javascript:goPage($$('gotoPageNo').value)"/>
</div>
</div>
<script>
function gotoSelectedPage(value) {
	goPage(value);
}

function goPage(pageNo) {
	$$("pageSize").value = $$("pageSizeselect").value;
	$$("pageNo").value = pageNo;
	$$("queryForm").submit();
}

function changePagesize(pagesize) {
	$$("pageSize").value = pagesize;
	//重置为第一页
	$$("pageNo").value = 1;
	$$("queryForm").submit();
}

function checkPageNo(element) {
	element.value = element.value.replace(/\D/g, '');
}
</script>