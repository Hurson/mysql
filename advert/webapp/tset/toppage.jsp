<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String ctx = request.getContextPath();
	String action = ctx + request.getParameter("action");
%>
<%--
本jsp实现列表数据的翻页功能。
使用说明：
	1、在需要翻页的table中加入如下代码：
	<tr>
         <td colspan="18" style="text-align:center;">
             <jsp:include page="../common/page.jsp" flush="true">
				<jsp:param value="/vsStorage/listVsStorage.action" name="action"/>
			</jsp:include>
                     
        </td>
   </tr>
   2、修改查询列表的action
   	即，把<jsp:param标签中的value属性换成你自己的action
 --%>
<div class="formButton" style="text-align:center;">

    <div style="text-align:center;">
        <c:choose>
            <c:when test="${pageBean.pageno >1}">
                <img src='<%=request.getContextPath()%>/css/img/page_up.gif'/>
                <a href="javascript:goPage('${pageBean.pageno-1}')"><font
                        style="font-size: 12px"><s:text name="common.pageup"/></font>
                </a>&nbsp;&nbsp;&nbsp;
            </c:when>
            <c:otherwise>
                <img src='<%=request.getContextPath()%>/css/img/page_up_gray.gif'/>
                <font style="font-size: 12px"><s:text name="common.pageup"/></font>&nbsp;&nbsp;&nbsp;
            </c:otherwise>
        </c:choose>

        <font id="toppageselect" style="font-size: 12px"></font>&nbsp;&nbsp;
        <c:choose>
            <c:when test="${pageBean.pageno < pageBean.pagecount}">
                <a href="javascript:goPage('${pageBean.pageno+1}')">

                    <font
                            style="font-size: 12px"><s:text name="common.pagedown"/></font>
                </a>
                <img src='<%=request.getContextPath()%>/css/img/page_down.gif'/>
            </c:when>
            <c:otherwise>
                <font
                        style="font-size: 12px"><s:text name="common.pagedown"/></font>
                <img src='<%=request.getContextPath()%>/css/img/page_down_gray.gif'/>
            </c:otherwise>
        </c:choose>
        <input type="text" size="5" maxlength="5" name="toppageBean.pageno" id="toppageBean_pageno"/><input onclick="searchTheTopPage()" type="button" value="<s:text name="common.button.page.go"/>"/>
        <font style="font-size: 12px">
						   		<s:text name="user.customerbloc.totalcount">
						            <s:param>${pageBean.rowcount}</s:param>
						        </s:text>
		</font>
    </div>

</div>
<script>
function gotoSelectedPage(value) {
   goPage(value);
}

function goPage(pageNo){
	$("pageBean_pageno").value = pageNo;
	$("searchForm").submit();
}
function searchThePage(){

	if(isIntegerNumber($("pageBean_pageno").value)){
		$("searchForm").submit();
	} else {
		 alert("<s:text name="common.integer.only"/>");
         $("pageBean_pageno").focus();
       // $("vsStorageBean_vs").style.borderColor = "red";
		
	}
	 
}
function searchTheTopPage(){

	if(isIntegerNumber($("toppageBean_pageno").value)){
		$("pageBean_pageno").value =$("toppageBean_pageno").value 
		$("searchForm").submit();
	} else {
		 alert("<s:text name="common.integer.only"/>");
         $("toppageBean_pageno").focus();
       // $("vsStorageBean_vs").style.borderColor = "red";
		
	}
	 
}
</script>