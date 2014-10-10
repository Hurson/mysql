<%--<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="app" uri="/WEB-INF/app.tld"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@   page   isELIgnored="false"%> 
--%><style type="text/css" media="all">
  @import url("${pageContext.request.contextPath}/css/screen.css");
</style>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/IcType.js'></script>
<center>
<table heigth="100%" width="98%" cellpadding="2" cellspacing="2">
	<tr>
		<td height="27" background="<%=request.getContextPath()%>/images/1-004.gif">
			<div align="center"><strong><spring:message code="ic_type.mgr"/></strong></div></td>
	</tr>
	<c:if test="${not empty message}">
	<tr>
		<td align="center"><b><font class="msg"><spring:message code="common.msg.info"/> : <c:out value="${message}"/></font></td>
	</tr>
	</c:if>
	<tr>
		<td><b><spring:message code="ic_type.list"/></b><hr size="1" color="#000000"/></td>
	</tr>
	<tr>
		<td align="center">
			<fmt:bundle basename="resource_base">
			<display:table name="itList"  id="row" class="its"> 
					 <display:column title="" style="width:20"><input type="checkbox" name="t_id" value="${row.id}"/></display:column>
					 <display:column  property="vendor"  titleKey="property.vendor"/> 
					 <display:column  property="modelNo"  titleKey="property.model_no"/>
					 <display:column  property="price"  titleKey="property.price"/>
					 <display:column  property="stock"  titleKey="property.stock"/>
					 <display:column  property="useNum"  titleKey="property.useNum"/>
					 <display:column  property="delNum"  titleKey="property.delNum"/>
					 <display:column  property="version"  titleKey="property.version"/>
					 <display:column titleKey="common.oper" style="width:90">
						<input type="button" class="btn" onclick="javascript:changeOperInfo('MODI','<c:out value="${row.id}"/>')" value="<spring:message code="common.modi"/>"/>&nbsp;
						<input type="button" class="btn" value="<spring:message code="common.del"/>" onclick="javascript:changeOperInfo('DEL','<c:out value="${row.id}"/>')"/>
					 </display:column>
			</display:table>
			</fmt:bundle> 
		</td>
	</tr>
	<tr>
		<td>
			<table width="100%">
				<tr>
					<td width="50%" align="left">
						<input type="button" class="btn" value="<spring:message code="common.del.select"/>" onclick="javascript:changeOperInfo('DELS');"/>
					</td>
					<td width="50%" align="right">
						<input type="button" class="btn" value="<spring:message code="common.add"/>" onclick="javascript:changeOperInfo('ADD','')"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr id="viewOper" style="display:none">
	    <td width="100%">
	    	<table width="100%">
	    		<tr>
	    			<td width="100%"><b><div id="operInfo"></div></b><hr size="1" color="#000000"/></td>
	    		</tr>
				<tr>
					<td><div id="errorDiv" align="center" style="color:red;font-weight:bold"></div></td>
				</tr>
	    		<tr>
		    		<form:form action="icType.do">
		    			<form:hidden path="operFlag"/>
		    			<form:hidden path="id"/>
		    			<form:hidden path="ids"/>
					<td width="100%" align="center" valign="middle">
						<table width="100%" class="query" cellpadding="1" cellspacing="1">
							<tr bgcolor="#FFFFFF">
						      	<td width="20%" class="table_title" align="right">
								  <spring:message code="property.vendor"/>: 
								</td>
						        <td class="table_cell">
								 <form:input path="vendor"/>
							</td>
							</tr>
							<tr bgcolor="#FFFFFF">
								<td width="20%" class="table_title">
								  <spring:message code="property.model_no"/>: 
								</td>
						        <td class="table_cell">
								  	<form:input path="modelNo"/>
								</td>
							</tr>
							<tr bgcolor="#FFFFFF">
								<td width="20%" class="table_title">
								  <spring:message code="property.price"/>: 
								</td>
						        <td class="table_cell">
								  	<form:input path="price"/>
								</td>
							</tr>
							<tr bgcolor="#FFFFFF">
								<td width="20%" class="table_title">
								  <spring:message code="property.stock"/>: 
								  <form:hidden path="stock"/>
								</td>
						        <td class="table_cell">
									<div id="stockDiv"></div>
								</td>
						     </tr>
							 <tr bgcolor="#FFFFFF">
								<td width="20%" class="table_title">
								  <spring:message code="property.useNum"/>: 
								</td>
						        <td class="table_cell">
								  <div id="useNumDiv">0</div>
								</td>
						     </tr>
							 <tr bgcolor="#FFFFFF">
								<td width="20%" class="table_title">
								  <spring:message code="property.delNum"/>: 
								</td>
						        <td class="table_cell">
								  <div id="delNumDiv">0</div>
								</td>
						     </tr>
						     <tr bgcolor="#FFFFFF">
								<td width="20%" class="table_title">
								  <spring:message code="property.version"/>: 
								</td>
						        <td class="table_cell">
								  <form:input path="version"/>
								</td>
						     </tr>
							 <tr bgcolor="#FFFFFF">
						     	<td align="center" colspan="2">
						     		<input type="submit" class="btn" value="<spring:message code="common.confirm"/>" onclick="return validate();"/>
						     		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						     		<input type="button" class="btn" onclick="javascript:closeOperInfo();" value="<spring:message code="common.cancel"/>"/>
						     	</td>
						     </tr>
						</table>
					</td>
		    		</form:form>
				</tr>
	    	</table>
	    </td>
	</tr>
</table>
</center>
<script language="javascript">
	function changeOperInfo(type, id){
		$("errorDiv").innerText = "";
		if(type == "MODI"){
			$("operInfo").innerText="<spring:message code="ic_type.edit"/>";
			IcType.getIcTypeById(id, fillForm);
			$("viewOper").style.display="";
		}else if(type == "ADD"){
			$("operInfo").innerText="<spring:message code="ic_type.insert"/>";
			DWRUtil.setValue("operFlag", "ADD");
			DWRUtil.setValue("id", "");
			DWRUtil.setValue("vendor", "");
			DWRUtil.setValue("modelNo", "");
			DWRUtil.setValue("price", "");
			DWRUtil.setValue("stock", "");
			$("stockDiv").innerText = "0";
			DWRUtil.setValue("version", "");
			$("viewOper").style.display="";
		}else if(type == "DEL"){
			if(confirm("<spring:message code="msg.confirm.del.warning"/>")){
				DWRUtil.setValue("operFlag", "DEL");
				DWRUtil.setValue("id", id);
				document.forms[0].submit();
			}
		}else if(type == "DELS"){
			if(confirm("<spring:message code="msg.confirm.del.warning"/>")){
				DWRUtil.setValue("operFlag", "DELS");
				var ids = (""+DWRUtil.getValue("t_id")).replaceAll(",",";");
				DWRUtil.setValue("ids", ids);
				document.forms[0].submit();
			}
		}
	}
	
	function validate(){
		var f = document.forms[0];
		var msg = "";
		if(f.vendor.value.trim() == ""){
			msg = "<spring:message code="msg.input.vendor.empty"/>";
			f.vendor.focus();
		}else if(f.modelNo.value.trim() == ""){
			msg = "<spring:message code="msg.input.model_no.empty"/>";
			f.modelNo.focus();
		}else if(f.price.value.trim() == ""){
			msg = "<spring:message code="msg.input.price.empty"/>";
			f.price.focus();
		}else if(!f.price.value.isNumber()){
			msg = "<spring:message code="msg.input.price.err"/>";
			f.price.focus();
		}else if(f.stock.value.trim() != "" && !f.stock.value.isInteger()){
			msg = "<spring:message code="msg.input.stock.err"/>";
			f.stock.focus();
		}
		if(msg != ""){
			$("errorDiv").innerText = "<spring:message code="common.error.info"/>" + ": " + msg;
			return false;
		}
		return true;
	}
	
	function fillForm(item){
		DWRUtil.setValue("operFlag", "MODI");
		DWRUtil.setValue("id", item.id);
		DWRUtil.setValue("vendor", item.vendor);
		DWRUtil.setValue("modelNo", item.modelNo);
		DWRUtil.setValue("price", item.price);
		DWRUtil.setValue("stock", item.stock);
		$("stockDiv").innerText = item.stock;
		$("delNumDiv").innerText = item.delNum;
		$("useNumDiv").innerText = item.useNum;
		DWRUtil.setValue("version", item.version);
	}
	
	function closeOperInfo(){
		$("viewOper").style.display="none";
	}

	function selectAll(){
		var ids = document.getElementsByName("t_id");
		for(var i = 0; i < ids.length; i++){
			ids[i].checked = true;
		}
	}
</script>