<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	response.setHeader("Pragma","No-cache");     
    response.setHeader("Cache-Control","no-cache");      
    response.setDateHeader("Expires",   -10);     
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type='text/javascript' src='<%=path%>/js/avit.js'></script>
<script type="text/javascript">
	window.onload = function() {
		//refreshPloyList();
		document.getElementById("yysscsh").style.display=window.parent.parent.frames["leftFrame"].getDisplay('yysscsh');	
		document.getElementById("tfclsh").style.display=window.parent.parent.frames["leftFrame"].getDisplay('tfclsh');
		document.getElementById("ddsh").style.display=window.parent.parent.frames["leftFrame"].getDisplay('ddsh');
		document.getElementById("htwh").style.display=window.parent.parent.frames["leftFrame"].getDisplay('htwh');
	}
	
	function deleteInfo(elem){
		//数据库记录修改
		var info = $(elem).attr("id").split("-");
		var warnId = info[0];
		var trIndex = info[1];
		$.ajax({
            type:"post",
            url:"<%=request.getContextPath()%>/test/warn/deleteInfoAction.do?",
            data:{"id": warnId}
        });	
		
		$("#my_tr_" + trIndex).remove();
	}
	
	function showWarn(){
	window.showModalDialog("showWarn.do", window, "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
	}
</script>
</head>

<body class="mainBody" >
	<div class="searchContent" >
	<form action="getAwaitDoing.do" method="post" id="queryForm">
		<table cellspacing="1"  width="100%" class="searchList">
			<tr >
				<td width="70%" style="padding-top:30px;" valign="top">
					<table cellspacing="1" class="searchList">
						<tr class="title">
							<td>
								待办事项
							</td>
						</tr>
						<tr id="yysscsh">
							<td>
								您有<a href="#" onclick="javascript:window.parent.parent.frames['leftFrame'].openPage('/page/meterial/auditMaterialList.do', '84', '素材审核', '素材审核');">【${materialAuditAwaiting}】</a>条素材需要审核
							</td>
						</tr>
						<tr id="tfclsh">
							<td>
								您有<a href="#" onclick="javascript:window.parent.parent.frames['leftFrame'].openPage('/page/ploy/queryCheckPloyList.do', '73', '策略审核', '策略审核');">【${ployAuditAwaiting} 】</a>条投放策略需要审核
							</td>
						</tr>
						<tr id="ddsh" >
							<td>
								您有<a href="#" onclick="javascript:window.parent.parent.frames['leftFrame'].openPage('/page/order/queryOrderAuditList.do', '92', '订单审核', '订单审核');">【${orderAuditAwaiting} 】</a>条订单需要审核
							</td>
						</tr>					
						<tr >
							<td id="htwh">
								您有<a href="#" onclick="javascript:window.parent.parent.frames['leftFrame'].openPage('/page/contract/queryContractList8.do?method=queryContractList', '61', '合同维护', '合同维护');">【${expireingContractCount} 】</a>条合同快要过期了
							</td>
						</tr>
						<tr >
							<td>
								未来&nbsp;<b>${value}</b>&nbsp;天的空档广告位为：<br>
								<c:forEach items="${lstMarginFreePosition}" var="position" varStatus="pl">
									<a href="#" onclick="javascript:window.parent.parent.frames['leftFrame'].openPage('/page/order/queryOrderList.do', '91', '订单维护', '订单维护');">【${position} 】</a>&nbsp;&nbsp;&nbsp;
									<c:if test="${pl.index%4==3}"><br></c:if>
								</c:forEach>
							</td>
						</tr>
						<s:if test="questionnaireOrderList.size > 0">
							<tr >
								<td>
									到达通知门限值的问卷订单：<br>
									<c:forEach items="${questionnaireOrderList}" var="order" varStatus="pl">
										<a href="#" onclick="window.location.href='<%=path %>/tset/page/order/getQuestionnaireDetail.do?order.id=${order.id}'">【${order.orderCode} 】</a>&nbsp;&nbsp;&nbsp;
										<c:if test="${pl.index%4==3}"><br></c:if>
									</c:forEach>
								</td>
							</tr>
						</s:if>
					</table>
					
				</td>
				<td width="30%" style="padding-top:30px;" valign="top">
				
					<div style="overflow-y:auto; height:600px;">
						<table cellspacing="1" class="searchList" >
							<tr class="title">
								<td>
									告警信息  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<a href="#" onclick="javascript:showWarn();">查看更多</a>
								</td>

							</tr>
															
							<s:iterator value="warnInfoList" status="status" var="warnInfo" >
								<tr id='my_tr_<s:property value="#status.index"/>'><td>
									<table>
										<tr>
											<td>
												<s:date name="#warnInfo.time" format="yyyy-MM-dd HH:mm:ss" />
											</td>
											<td>
												<a id='<s:property value="#warnInfo.id"/>-<s:property value="#status.index"/>' href="#" onclick="deleteInfo(this)">标记处理</a>
											</td>
										</tr>
										<tr >
											<td colspan=2>
												<div style="border:1px solid #bfd8e0; padding-top:10px;padding-left:10px;">
													<s:property value="#warnInfo.content" />
												</div>
											</td>
										</tr>
									</table>
							    </td></tr>
							</s:iterator>
						</table>
					</div>

				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>

