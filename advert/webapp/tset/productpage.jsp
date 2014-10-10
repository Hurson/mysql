<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String ctx = request.getContextPath();
%>

    <td align="right">            
          <s:text name="common.totalcount">
           	<s:param>${productpageBean.rowcount}</s:param>
  		  </s:text>
    &nbsp;&nbsp;${productpageBean.pageno}/${productpageBean.pagecount}&nbsp;&nbsp;
    <s:text name="common.perpage"/>
    <select id="pageSizeselect" class="text_style" style="width:40px; font-size:12px;" onchange="changePagesize(this.value);" >
    	<option value="5"  <c:if test="${productpageBean.pagesize==5}">selected</c:if>>5</option>
    	<option value="10" <c:if test="${productpageBean.pagesize==10}">selected</c:if>>10</option>
    	<option value="15" <c:if test="${productpageBean.pagesize==15}">selected</c:if>>15</option>
    	<option value="20" <c:if test="${productpageBean.pagesize==20}">selected</c:if>>20</option>
    </select>
    <s:text name="common.pageitem"/>
    &nbsp;&nbsp;
    </td>
    <td width="50" align="center">
  		<c:choose>
            <c:when test="${productpageBean.pageno >1}">
            	<input type="button" class="btn_small" onclick="javascript:goPage('${productpageBean.pageno-1}')" value="<s:text name='common.pageup'/>"/></c:when>
            <c:otherwise>
            	<input type="button" class="btn_small" onclick="javascript:goPage('${productpageBean.pageno-1}')" value="<s:text name='common.pageup'/>"  disabled="disabled" />
            </c:otherwise>
        </c:choose>
    </td>
    <td width="80" align="center">
        <select id="pageNumber" class="text_style" style="width:55px; font-size:12px;" onchange="gotoSelectedPage(this.value);">
			<c:forEach var="index" begin="1" end="${productpageBean.pagecount}">
				<option <c:if test="${index==productpageBean.pageno}">selected="selected"</c:if> value="${index}">${index}</option>
            </c:forEach>
		</select>
    </td>
     <td width="50" align="center">
          <c:choose>
            <c:when test="${productpageBean.pageno < productpageBean.pagecount}">
            	<input type="button" class="btn_small" onclick="javascript:goPage('${productpageBean.pageno+1}')" value="<s:text name='common.pagedown'/>"  />
            </c:when>
            <c:otherwise>
                <input type="button" class="btn_small" onclick="javascript:goPage('${productpageBean.pageno-1}')" value="<s:text name='common.pagedown'/>"  disabled="disabled"/>
            </c:otherwise>
        </c:choose>
     </td>
<script>
function gotoSelectedPage(value) {
   goPage(value);
}

function goPage(pageNo){
    $("pagesize").value = $("pageSizeselect").value;
	$("pageno").value = pageNo;
	$("queryForm").submit();
}

function changePagesize(pagesize){
	$("pagesize").value = pagesize;
	$("queryForm").submit();
}


function $(id) {
	return document.getElementById(id);
}
</script>