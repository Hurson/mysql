<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tags/fmt.tld" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/css/new/main.css" />
		<link href="<%=path%>/css/new/common/dhtmlx/tree/css/dhtmlxtree.css"
			rel="stylesheet" type="text/css" media="all" />
			<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css">
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/dhtmlxcommon.js"
			type="text/javascript">
</script>
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/dhtmlxtree.js"
			type="text/javascript">
</script>
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/test.js"
			type="text/javascript">
</script>

<script type="text/javascript" src="<%=path%>/js/customerJS.js"></script>

		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/jquery-1.9.0.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/contract/listContract.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker-zh-CN.js">
</script>

		<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js">
</script>
		<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js">
</script>
<script src="<%=path%>/js/new/avit.js">
</script>

<script>
	//全选
	/** 复选框全选 */
	function checkAll(form) {
		for ( var i = 0; i < form.elements.length; i++) {
			var e = form.elements[i];
			if (e.Name != "chkAll")
				e.checked = form.chkAll.checked;
		}
		return;
	}
	
	//执行删除操作	
	function deleteData(){
		var ids = "";
		/*var  resourceArr = document.getElementsByName("ids");
		for(var i=0; i < resourceArr.length;i++ ){
			if(resourceArr[i].checked){
				ids+= resourceArr[i].value ;
			}
			if(i < resourceArr.length-1){
				ids+=",";
			}
	    }*/
	    ids=getCheckValue("ids");
	    if(ids==""){
	    	alert("您没有选择要删除的广告商，请确认后再操作！");
	    	return;
	    }
	    deleteCustomer(ids);
	}
	function deleteCustomer(ids){
		$.ajax( {
			type : 'post',
			url : 'adCustomerMgr_deleteCustomerBatch.do',
			data : 'ids=' + ids +'&date=' + new Date(),
			success : function(msg) {
				var ss = eval(msg);
				 if(ss.result == 3){
				 	alert("您要删除的广告商还有合同未执行完毕，请确认后，再操作！");
				 	return;
				 }else{
				 	var a = window.confirm("您确定要删除吗？");
				 	if(a==1){
				 		
				 		submitDeleteCustomer(ids);
				 	}
				 }
			}	
		});
	}
	
	
	function submitDeleteCustomer(ids){
		
		$.ajax( {
			type : 'post',
			url : 'adCustomerMgr_submitDeleteCustomerInfo.do',
			data : 'ids=' + ids +'&date=' + new Date(),
			success : function(msg) {
				if(msg != null){
				   window.location.href="adCustomerMgr_list.do";
				}
			}	
		});
	}
	
     function go() {
		document.getElementById("queryForm").submit();
}

</script>
	</head>
	<body class="mainBody" onload="">
		<input id="projetPath" type="hidden" value="<%=path%>" />
		<form id="queryForm" action="adCustomerMgr_list.do" method="post" name="">
		 <s:set name="page" value="%{pageCustomer}" />
		 <input type="hidden" id="pageNo" name="pageCustomer.pageNo" value="${page.pageNo}"/>
		 <input type="hidden" id="pageSize" name="pageCustomer.pageSize" value="${page.pageSize}"/>
			<div class="search">
				<div class="path">
					<img src="<%=path%>/images/new/filder.gif" width="15" height="13" />
					首页 >> 广告商管理 >> 广告商维护
				</div>
				<div class="searchContent">
					<table cellspacing="1" class="searchList">
						<tr class="title">
							<td colspan="1">
								查询条件
							</td>
						</tr>
						<tr>
							<td>
								广告商名称
								<input onkeypress="return validateSpecialCharacter();" type="text" name="customerBackUp.advertisersName"
									value="${advertisersName}" class="e_input"
									onfocus="this.className='e_inputFocus'"
									onblur="this.className='e_input'" />
								客户代码：
								<input onkeypress="return validateSpecialCharacter();" type="text" name="customerBackUp.clientCode"
									value="${clientCode}" class="e_input"
									onfocus="this.className='e_inputFocus'"
									onblur="this.className='e_input'" />
								联系人：
								<input onkeypress="return validateSpecialCharacter();" type="text"  name="customerBackUp.communicator"
									value="${communicator}" class="e_input"
									onfocus="this.className='e_inputFocus'"
									onblur="this.className='e_input'" />
								状态：
								<select id="selectId" name="customerBackUp.status">
									<option value="">
										请选择
									</option>
									<option value="0">
										待审核
									</option>
									<option value="1">
										审核通过
									</option>
									<option value="2">
										审核未通过
									</option>
								</select>
								<input type="button" value="查询" onclick="go();" class="btn" />
								</td>
						</tr>
					</table>
					<div id="messageDiv"
						style="margin-top: 15px; color: red; font-size: 14px; font-weight: bold;"></div>
					<table cellspacing="1" class="searchList" id="bm">
						<tr class="title">
							<td height="28" class="dot">
							    <input type="checkbox" name="chkAll"  onclick=checkAll(this.form) id="chkAll"/>
								选项
							</td>
							<td>
								序号
							</td>
							<td>
								广告商名称
							</td>
							<td>
								客户代码
							</td>
							<td>
								公司地址
							</td>
							<td>
								联系人
							</td>
							<td>
								联系方式
							</td>
							<td>
								级别
							</td>
							<td>
								状态
							</td>
							<td>
								创建时间
							</td>
						</tr>
						<s:if test="listCustomers.size==0">
							<tr>
								<td bgcolor="#FFFFFF" align="center" colspan="12"  style="text-align: center">
									无记录
								</td>
							</tr>
						</s:if>
						<s:else>
							<c:set var="index" value="0" />
							<s:iterator value="listCustomers" status="rowstatus" var="item">
								<tr <c:if test="${index%2==1}">class="sec"</c:if>>
									<td>
										<input type="checkbox" name="ids" value="${item.id}" />
									</td>
									<td align="center">
										${rowstatus.count}
									</td>
									<td align="center">
										<a href="#" onclick="goUpdateReditrect(<s:property value ='id'/>,<s:property value='status'/>)"><s:property value="advertisersName" /></a>										
									</td>
									<td align="center">
										<s:property value="clientCode" />
									</td>
									<td align="center">
										<s:property value="conpanyAddress" />
									</td>
									<td align="center">
										<s:property value="communicator" />
									</td>
									<td align="center">
										<s:property value="contacts" />
									</td>
									<td align="center">
										<s:if test='customerLevel == "1"'>国级</s:if>
										<s:if test='customerLevel == "2"'>省级</s:if>
										<s:if test='customerLevel == "3"'>市级</s:if>
										<s:if test='customerLevel == "4"'>其它</s:if>
									</td>
									<td align="center">
										<s:if test='status == "0"'>待审核</s:if>
										<s:if test='status == "1"'>审核通过</s:if>
										<s:if test='status == "2"'>审核未通过</s:if>
									</td>
									<td align="center">
										<fmt:formatDate value="${item.createTime}"
											pattern="yyyy-MM-dd" />
									</td>
								</tr>
								<c:set var="index" value="${index+1}" />
							</s:iterator>

						</s:else>
						
						
						 <tr>
					    <td colspan="10">
					        <input type="button" value="删除" class="btn"
									onclick="javascript:deleteData();" />
								&nbsp;&nbsp;
					        <jsp:include page="../common/page.jsp" flush="true" />
					    </td>
					  </tr>
						
							<!--
						<tr>
							<td colspan="10">
								<input type="button" value="删除" class="btn"
									onclick="javascript:deleteData();" />
								&nbsp;&nbsp;
							
								<input id="addPositionSubmit" name="addPositionSubmit"
									type="button" value="添加" class="btn" onclick="goSaveCustomerRedirect()" />

								
									<a href="#">共计${page.totalPage }页</a>&nbsp;&nbsp;
									<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp;
									<c:choose>
										<c:when test="${page.pageNo==1&&page.totalPage!=1}">
								【<a
												href="adCustomerMgr_list.do?pageNo=${page.pageNo+1 }&advertisersName=${customerBackUp.advertisersName}&clientCode=${customerBackUp.clientCode }&conpanyAddress=${customerBackUp.conpanyAddress }&cooperationTime=${customerBackUp.cooperationTime}&createTimeA=${customerBackUp.createTimeA}&createTimeB=${customerBackUp.createTimeB}">下一页</a>】&nbsp;&nbsp;
								【<a
												href="adCustomerMgr_list.do?pageNo=${page.totalPage}&advertisersName=${customerBackUp.advertisersName}&clientCode=${customerBackUp.clientCode }&conpanyAddress=${customerBackUp.conpanyAddress }&cooperationTime=${customerBackUp.cooperationTime}&createTimeA=${customerBackUp.createTimeA}&createTimeB=${customerBackUp.createTimeB}">末页</a>】&nbsp;&nbsp;
							</c:when>
										<c:when
											test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
								【<a
												href="adCustomerMgr_list.do?pageNo=1&advertisersName=${customerBackUp.advertisersName}&clientCode=${customerBackUp.clientCode }&conpanyAddress=${customerBackUp.conpanyAddress }&cooperationTime=${customerBackUp.cooperationTime}&createTimeA=${customerBackUp.createTimeA}&createTimeB=${customerBackUp.createTimeB}">首页</a>】&nbsp;&nbsp;
								【<a
												href="adCustomerMgr_list.do?pageNo=${page.pageNo-1 }&advertisersName=${customerBackUp.advertisersName}&clientCode=${customerBackUp.clientCode }&conpanyAddress=${customerBackUp.conpanyAddress }&cooperationTime=${customerBackUp.cooperationTime}&createTimeA=${customerBackUp.createTimeA}&createTimeB=${customerBackUp.createTimeB}">上一页</a>】&nbsp;&nbsp;
							</c:when>
										<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
								【<a
												href="adCustomerMgr_list.do?pageNo=1&advertisersName=${customerBackUp.advertisersName}&clientCode=${customerBackUp.clientCode }&conpanyAddress=${customerBackUp.conpanyAddress }&cooperationTime=${customerBackUp.cooperationTime}&createTimeA=${customerBackUp.createTimeA}&createTimeB=${customerBackUp.createTimeB}">首页</a>】&nbsp;&nbsp;
								【<a
												href="adCustomerMgr_list.do?pageNo=${page.pageNo-1 }&advertisersName=${customerBackUp.advertisersName}&clientCode=${customerBackUp.clientCode }&conpanyAddress=${customerBackUp.conpanyAddress }&cooperationTime=${customerBackUp.cooperationTime}&createTimeA=${customerBackUp.createTimeA}&createTimeB=${customerBackUp.createTimeB}">上一页</a>】&nbsp;&nbsp;
								【<a
												href="adCustomerMgr_list.do?pageNo=${page.pageNo+1 }&advertisersName=${customerBackUp.advertisersName}&clientCode=${customerBackUp.clientCode }&conpanyAddress=${customerBackUp.conpanyAddress }&cooperationTime=${customerBackUp.cooperationTime}&createTimeA=${customerBackUp.createTimeA}&createTimeB=${customerBackUp.createTimeB}">下一页</a>】&nbsp;&nbsp;
								【<a
												href="adCustomerMgr_list.do?pageNo=${page.totalPage}&advertisersName=${customerBackUp.advertisersName}&clientCode=${customerBackUp.clientCode }&conpanyAddress=${customerBackUp.conpanyAddress }&cooperationTime=${customerBackUp.cooperationTime}&createTimeA=${customerBackUp.createTimeA}&createTimeB=${customerBackUp.createTimeB}">末页</a>】&nbsp;&nbsp;
							</c:when>
									</c:choose>
								
							</td>
						</tr>
						-->
					</table>
				</div>
			</div>
		</form>
	</body>
</html>
