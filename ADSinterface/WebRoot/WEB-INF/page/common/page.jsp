<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 <c:if test="${pageBean.listpage != null && fn:length(pageBean.listpage) > 0}">
 <%
	String ctx = request.getContextPath();
	String action = ctx + request.getParameter("action");
	String otherPageBeanId = request.getParameter("otherPageBeanId");
	String searchFormId = request.getParameter("searchFormId");
%>
<%--
本jsp实现列表数据的翻页功能。
使用说明：
前提条件：查询条件和分页必须放在一个form中才能进行分页
	1、在需要翻页的table中加入如下代码：
	<tr>
         <td colspan="18" style="text-align:center;">
             <jsp:include page="../common/page.jsp" flush="true">
             	<jsp:param value="pageBeanUdp" name="otherPageBeanId"/>
				<jsp:param value="/vsStorage/listVsStorage.action" name="action"/>
			</jsp:include>
                     
        </td>
   </tr>
   2、修改查询列表的action
   	即，把<jsp:param标签中的value属性换成你自己的action
   	
   	3、如果你的PageBean对象名称不是“pageBean”,或者你的action里面有多个PageBean对象，比如“productPageBean”,需要在你的页面中做如下修改
   	  a)在第一次出现"pageBean“的jsp页面前面加上：<s:set name="pageBean" value="pageBeanUdp" scope="request"></s:set>
   	  b) 添加<jsp:param value="pageBeanUdp" name="otherPageBeanId"/>，把value修改成你的PageBean对象名
   	    
 --%>
<div class="formButton" style="text-align: right;">
    <div style="text-align:right;">
    	
        <s:text name="common.totalcount">
            <s:param>${pageBean.rowcount}</s:param>
        </s:text>
        ${pageBean.pageno}/${pageBean.pagecount}
        &nbsp;&nbsp;
        <s:text name="common.page.pagesize"/>
        <select onchange='reSearchPage(this.value);' style='width:55px' name="pageBean.pagesize" class="text_style">
        		<%--<option <c:if test="${pageBean.pagesize == 1}">
                                        	selected="selected"
                                        </c:if>  value='1'>1</option>
                <option <c:if test="${pageBean.pagesize == 2}">
                                        	selected="selected"
                                        </c:if>  value='2'>2</option>
                <option <c:if test="${pageBean.pagesize == 3}">
                                        	selected="selected"
                                        </c:if>  value='3'>3</option>
                <option <c:if test="${pageBean.pagesize == 4}">
                                        	selected="selected"
                                        </c:if>  value='4'>4</option>
                                        
        		--%>
        		<option <c:if test="${pageBean.pagesize == 10}">
                                        	selected="selected"
                                        </c:if>  value='10'>10</option>
                <option <c:if test="${pageBean.pagesize == 20}">
                                        	selected="selected"
                                        </c:if>  value='20'>20</option>
                <option <c:if test="${pageBean.pagesize == 50}">
                                        	selected="selected"
                                        </c:if>  value='50'>50</option>
                <option <c:if test="${pageBean.pagesize == 100}">
                                        	selected="selected"
                                        </c:if>  value='100'>100</option>
       </select>
        <s:text name="common.page.unit"/>
        
   
   &nbsp;&nbsp;
        <c:choose>
            <%--<c:when test="${pageBean.pageno >1}">
                <img src='<%=request.getContextPath()%>/css/img/page_up.gif'/>
                <a href="javascript:goPage('${pageBean.pageno-1}')"><font
                        style="font-size: 12px"><s:text name="common.pageup"/></font>
                </a>&nbsp;&nbsp;&nbsp;
            </c:when>
            <c:otherwise>
                <img src='<%=request.getContextPath()%>/css/img/page_up_gray.gif'/>
                <font style="font-size: 12px"><s:text name="common.pageup"/></font>&nbsp;&nbsp;&nbsp;
            </c:otherwise>
            --%><c:when test="${pageBean.pageno >1}">
            	<input type="button" class="btn_small" onclick="javascript:goPage('${pageBean.pageno-1}')" value="<s:text name='common.pageup'/>"/></c:when>
            <c:otherwise>
            	<input type="button" class="btn_small" onclick="javascript:goPage('${pageBean.pageno-1}')" value="<s:text name='common.pageup'/>"  disabled="disabled" />
            </c:otherwise>
        </c:choose>
&nbsp;&nbsp;
        <font id="pageselect1s" style="font-size: 12px">
        	<select id='pageNumber2' onchange='gotoSelectedPage(this.value);' style='width:55px'>
        	<c:forEach var="cnt" begin="1" end="${pageBean.pagecount}" step="1">
        		<option <c:if test="${pageBean.pageno == cnt}">
                                        	selected="selected"
                                        </c:if> value='${cnt}'>${cnt}</option>
        	</c:forEach>
        	</select>
        </font>&nbsp;&nbsp;
        <c:choose><%--
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
        --%>
        	<c:when test="${pageBean.pageno < pageBean.pagecount}">
            	<input type="button" class="btn_small" onclick="javascript:goPage('${pageBean.pageno+1}')" value="<s:text name='common.pagedown'/>"  />
            </c:when>
            <c:otherwise>
                <input type="button" class="btn_small" onclick="javascript:goPage('${pageBean.pageno+1}')" value="<s:text name='common.pagedown'/>"  disabled="disabled"/>
            </c:otherwise>
        </c:choose>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="hidden" maxlength="9" name="pageBean.pageno" value="${pageBean.pageno}" id="pageBean_pageno"/>
        <%--<input type="text"  style="width: 20px;" maxlength="9" class="input_style f14 padding_left"  id="t_pageBean_pageno" onkeypress="keySearch()"/>&nbsp;<input onclick="searchThePage()" type="button" class="btn_small" value="<s:text name="common.button.page.go"/>"/>
    --%></div>
	<div id="hidden_field" style="display: none;"></div>
</div>
 <script type="text/javascript" defer="defer">
function reSearchPage(pagesize){
	var pageBeanId = "<%=otherPageBeanId%>";
	if( pageBeanId!= "null" && pageBeanId!= null && pageBeanId != "") {
		$("hidden_field").innerHTML = '<input type="hidden" name="'+pageBeanId+'.pagesize" value="'+pagesize+'"/>';
	} else {
		//$("hidden_field").innerHTML = '<input type="hidden" name="pageBean.pagesize" value="'+pagesize+'"/>';
	}
	submitForm();
}
function gotoSelectedPage(value) {
   goPage(value);
}

function goPage(pageNo){
	$("pageBean_pageno").value = pageNo;
	var pageBeanId = "<%=otherPageBeanId%>";
	if( pageBeanId!= "null" && pageBeanId!= null && pageBeanId != "") {
		$("hidden_field").innerHTML = '<input type="hidden" name="'+pageBeanId+'.pageno" value="'+pageNo+'"/>';
	}
	submitForm();
}

function submitForm(){
	var searchFormId = "<%=searchFormId%>";
	if( !(searchFormId!= "null" && searchFormId!= null && searchFormId != "")) {
		searchFormId = "searchForm";
	}
	$(searchFormId).submit();
}
function searchThePage(){
 if (!isEmpty($("t_pageBean_pageno").value)) {
	if(isIntegerNumber($("t_pageBean_pageno").value)){
		$("pageBean_pageno").value=$("t_pageBean_pageno").value;
		$("searchForm").submit();
	} else {
		 alert("<s:text name="common.integer.only"/>");
         $("t_pageBean_pageno").focus();
		
	}
 }else{
	 $("t_pageBean_pageno").focus();
 }
}
//按回车
function keySearch(){
	if(event.keyCode==13){
		searchThePage();
	}
	
}
</script>

</c:if>