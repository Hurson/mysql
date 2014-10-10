<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tags/fmt.tld" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css">

<link rel="stylesheet" href="css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>/css/popUpDiv.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css">
<script type="text/javascript" src="../../js/new/My97DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/marketingRule/listMarketingRule.js"></script>

<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery-ui-timepicker-addon.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />
<link href="<%=path%>/css/Pager.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right_new1.css" type="text/css" />

<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.core.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.widget.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.tabs.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.accordion.js"></script>
<title></title>

<style type="text/css">
        a{text-decoration:underline;}
</style>

<script language="Javascript1.1" src="../../js/avit.js"></script>
<script type="text/javascript">
   /** 复选框全选 */
	function checkAll(form) {
		for ( var i = 0; i < form.elements.length; i++) {
			var e = form.elements[i];
			if (e.Name != "chkAll")
				e.checked = form.chkAll.checked;
		}
		
		return;
	}
	
	//批量删除
	function goDeleteAbatch(){
		var ids = "";
		var  resourceArr = document.getElementsByName("ids");
		
		for(var i=0; i <= resourceArr.length-1;i++ ){
			if(resourceArr[i].checked){
				ids+= resourceArr[i].value +",";
			}
	    }
	    if(ids==""){
	    	alert("您没有选择要删除的规则，请确认后再操作！");
	    	return;
	    }
	    
	    if(ids != ""){
	       deleteRuleBatch(ids);
	    }else{
	      alert("你还没有选中！");
	    }
	}
	
	function deleteRuleBatch(ids){
	var con = window.confirm("确定要删除吗？");
	if(con){
	    $.ajax( {
			type : 'post',
			url : 'deleteMarketingRuleBatch.do',
			data : 'ids=' + ids,
			success : function(responseText) {
				    alert("删除成功!");
				    window.location.href="listMarketingRule.do";
			},
			
			error:function(){
				alert("服务器异常，请稍后重试！");
			}
		});
	}
		
	}
   
</script>



</head>

<body class="mainBody">

<form action="listMarketingRule.do?method=listMarketingRule"   id="queryMarketingRuleForm" name="queryMarketingRuleForm" method="post" >
	<div class="search">
		<div class="path">首页 >> 营销规则管理 >> 营销规则管理</div>
		<div class="searchContent" >
			<table cellspacing="1" class="searchList">
				<tr class="title">
  			        <td>查询条件</td>
				</tr>
				<tr>
			       <td class="searchCriteria">
			          <span>营销规则名称：</span>
			          <input id="ruleName" name="ruleName" type="text" class="e_input"  value="${ruleName}" />
			          <span>广告位：</span>
			          <select id="positionId" name="positionId" onchange="selectOptionVal(this.id)"  class="e_select" onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'" >
								<option id="ad_id" value="-1">请选择...</option>
								<c:forEach items="${postionList}" var="postion">
									<option  value="${postion.id }">${postion.positionName }</option>
								</c:forEach>
							</select>
			          <span>开始时间：</span>
			          <input id="startTime" name="startTime" value="${startTime}" type="text"  />
			          <span>结束时间：</span>
			          <input id="endTime" name="endTime" value="${endTime}" type="text" />
			          <input value="查询" class="btn" id="queryMarketingRuleButton" name="queryMarketingRuleButton" type="submit" title="查询" />
			       </td>
			    </tr>		    		  	
			</table>
			<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
			<table cellspacing="1" class="searchList" id="bm">
    		<tr class="title">    		
    			<td height="28" class="dot">
    			<input type="checkbox" name="chkAll"  onclick=checkAll(this.form) id="chkAll"/>
    			</td>
    			<td>序号</td>
    			<td>规则名称</td>
    			<td>广告位</td>
    			<td>区域</td>
    			<td>频道</td>			
    			<td>开始时间</td>
    			<td>结束时间</td>
    			<td>状态</td>
    			<td>创建时间</td>
    		</tr>
    		<s:if test="rules.size==0">
			<tr>
				<td bgcolor="#FFFFFF" align="center" colspan="11"
					style="text-align: center">无记录</td>
			</tr>
			</s:if>
			<s:else>
			    <c:set var="index" value="0"/>
				<c:forEach items="${rules}" var="rule">
				<tr>
				    <td>
				    <input type="checkbox" name="ids" value="${rule.id}" />
				    </td>
				    <c:set var="index" value="${index+1 }" />
					<td align="center" height="26"><c:out value="${index }" /></td>
					<td align="center">			
					<a onClick="updateRule('${rule.ruleId}')" href="#">
			              ${rule.ruleName}
			        </a>
					</td>			
					<td align="center">${rule.positionName}</td>					
					<td align="center">${rule.areaName}</td>
					<td align="center">${rule.channelName}</td>
					<td align="center">${rule.startTime}</td>
					<td align="center">${rule.endTime}</td>
					<td align="center">${rule.state}</td>
					<td align="center">${rule.createTime}</td>
				</tr>
			</c:forEach>

			
			

			<c:if test="${index<page.pageSize}">
                <c:forEach begin="${index}" end="${page.pageSize-1}" step="1">
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
			    </c:forEach>
			</c:if>

			
			
		</s:else>
    		

			
			<tr>
				<td height="34" colspan="11" style="background: url(images/bottom.jpg) repeat-x; text-align: left;">

					<input type="button" value="删除" class="btn" onclick="goDeleteAbatch()"/>
					<input type="button" value="新增" class="btn" onclick="addRule()"/>

					
					
					<div class="page">
					<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp; 
					<c:choose>
						<c:when test="${page.pageNo==1&&page.totalPage!=1}">
							<a href="listMarketingRule.do?pageNo=${page.pageNo+1 }&positionId=${positionId}&ruleName=${ruleName}&viewType=${viewType}">下一页</a>&nbsp;&nbsp;
							<a href="listMarketingRule.do?pageNo=${page.totalPage}&positionId=${positionId}&ruleName=${ruleName}&viewType=${viewType}">末页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
							<a href="listMarketingRule.do?pageNo=1&positionId=${positionId}&ruleName=${ruleName}&viewType=${viewType}">首页</a>&nbsp;&nbsp;
							<a href="listMarketingRule.do?pageNo=${page.pageNo-1 }&positionId=${positionId}&ruleName=${ruleName}&viewType=${viewType}">上一页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
							<a href="listMarketingRule.do?pageNo=1&positionId=${positionId}&ruleName=${ruleName}&viewType=${viewType}">首页</a>&nbsp;&nbsp;
							<a href="listMarketingRule.do?pageNo=${page.pageNo-1 }&positionId=${positionId}&ruleName=${ruleName}&viewType=${viewType}">上一页</a>&nbsp;&nbsp;
							<a href="listMarketingRule.do?pageNo=${page.pageNo+1 }&positionId=${positionId}&ruleName=${ruleName}&viewType=${viewType}">下一页</a>&nbsp;&nbsp;
							<a href="listMarketingRule.do?pageNo=${page.totalPage}&positionId=${positionId}&ruleName=${ruleName}&viewType=${viewType}">末页</a>&nbsp;&nbsp;
						</c:when>
					</c:choose>
					</div>
				</td>
			</tr>
    		
			</table>
		</div>
	</div>
	</form>
	
		
</body>