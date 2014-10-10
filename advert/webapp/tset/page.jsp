<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link id="maincss" href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet" type="text/css" media="all"/>
<%
    String ctx = request.getContextPath();
%>
<c:if test="${pageBean.listpage != null && fn:length(pageBean.listpage) > 0}">
    <s:text name="common.totalcount">
        <s:param>${pageBean.rowcount}</s:param>
    </s:text>
    &nbsp;&nbsp;${pageBean.pageno}/${pageBean.pagecount}&nbsp;&nbsp;
    <s:text name="common.perpage"/>
    <select id="pageSizeselect" style="width:45px; font-size:12px;" onchange="changePagesize(this.value);">
        <option value="10" <c:if test="${pageBean.pagesize==10}">selected</c:if>>10</option>
        <option value="20" <c:if test="${pageBean.pagesize==20}">selected</c:if>>20</option>
        <option value="50" <c:if test="${pageBean.pagesize==50}">selected</c:if>>50</option>
        <option value="100" <c:if test="${pageBean.pagesize==100}">selected</c:if>>100</option>
    </select>
    <s:text name="common.pageitem"/>
    &nbsp;&nbsp;
    <c:choose>
        <c:when test="${pageBean.pageno >1}">
            <input type="button" class="btn" onclick="javascript:goPage('${pageBean.pageno-1}')"
                   value="<s:text name='common.pageup'/>"/></c:when>
        <c:otherwise>
            <input type="button" class="btn" onclick="javascript:goPage('${pageBean.pageno-1}')"
                   value="<s:text name='common.pageup'/>" disabled="disabled"/>
        </c:otherwise>
    </c:choose>
    <%--<select id="pageNumber" style="width:55px; font-size:12px;" onchange="gotoSelectedPage(this.value);">
        <c:forEach var="index" begin="1" end="${pageBean.pagecount}">
            <option
                    <c:if test="${index==pageBean.pageno}">selected="selected"</c:if> value="${index}">${index}</option>
        </c:forEach>
    </select>--%>
    <c:choose>
        <c:when test="${pageBean.pageno < pageBean.pagecount}">
            <input type="button" class="btn" onclick="javascript:goPage('${pageBean.pageno+1}')"
                   value="<s:text name='common.pagedown'/>"/>
        </c:when>
        <c:otherwise>
            <input type="button" class="btn" onclick="javascript:goPage('${pageBean.pageno-1}')"
                   value="<s:text name='common.pagedown'/>" disabled="disabled"/>
        </c:otherwise>
    </c:choose>
    <span><s:text name="common.go"/></span><input style="width: 40px" maxlength="10" onkeyup="checkPageNo(this)" type="text" value="${pageBean.pageno}"
                                                  id="gotoPageNo"/><input type="button" value="GO" class="btn"
                                                                          onclick="javascript:goPage($('gotoPageNo').value)"/>
</c:if>
<script>
    function gotoSelectedPage(value) {
        goPage(value);
    }

    function goPage(pageNo) {
        $("pagesize").value = $("pageSizeselect").value;
        $("pageno").value = pageNo;
        $("queryForm").submit();
    }

    function changePagesize(pagesize) {
        $("pagesize").value = pagesize;
        //重置为第一页
        $("pageno").value = 1;
        $("queryForm").submit();
    }

    function checkPageNo(element) {
        element.value = element.value.replace(/\D/g, '');
    }

    function $(id) {
        return document.getElementById(id);
    }
</script>