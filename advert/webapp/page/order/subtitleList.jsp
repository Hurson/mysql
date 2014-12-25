<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<title>字幕广告订单</title>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>

<script type="text/javascript">
    function query(){
		$("#queryForm").submit();
    }
    
    function delSubtitle() {
		if (getCheckCount('ids') <= 0) {
	         alert("请选择需要删除的订单记录！");
	         return;
	    }

	    if (confirm("您确定需要删除所选的订单记录吗？")) {
	         document.getElementById("queryForm").action = "delSubtitle.do";
	         document.getElementById("queryForm").submit();
	    }
	}

</script>
</head>

<body class="mainBody">
<form action="findSubtitleList.do" method="post" id="queryForm">
<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
<div class="search">
<div class="path">首页 >> 订单管理 >> 字幕广告订单</div>
<div class="searchContent" >

<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
    	<span style="width: 60px;text-align:right">字幕内容：</span><input type="text" name="subtitle.word"/>
    	<span style="width: 60px;text-align:right;">创建日期：</span>
	   	<input id="createDate" name="subtitle.createDateStr" type="text" readonly="readonly" style="width: 100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
	   	<img onclick="showDate('createDate')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="left"/>
	 	
	 	<span style="width: 60px;text-align:right">投放日期：</span>
	    <input id="pushDate" name="subtitle.pushDateStr"  type="text" readonly="readonly" style="width: 100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
	   	<img onclick="showDate('pushDate')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"/>
      	
      	<span style="width: 50px;text-align:right">状态：</span>
      	<select name="subtitle.state" style="width: 115px">
          	<option value="">请选择</option>
          	<option value="1">创建</option>
		  	<option value="2">已投放</option>
      	</select>
      	&nbsp;&nbsp;
     	<input type="button" value="查询" onclick="javascript:query();" class="btn"/>
     </td>
  </tr>
  
</table>
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;">
	 <c:if test="${message != null}">
            <span style="color:red;"><s:property value="%{getText(#request.message)}" /></span>
    </c:if>
</div> 
<table cellspacing="1" class="searchList" >
    <tr class="title" align="center">
    	<td width="5%" height="28" class="dot"><input type="checkbox" name="chkAll" onclick="selectAll(this, 'ids');" id="chkAll"/></td>
        <td width="5%">序列号</td>
        <td  width="25%">字幕内容</td>
        <td  width="25%">投放区域</td>
        <td width="15%">最后更新时间</td>
        <td width="15%">投放时间</td>
        <td width="10%">状态</td>
    </tr>
	<s:iterator value="page.dataList" status="status" var="subtitle">
		<tr <s:if test="#status.index%2==1">class="sec"</s:if>
			onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'" align="center">
			<td>
				<s:if test="#subtitle.state==1">
					<input type="checkbox" name="ids" value="<s:property value='#subtitle.id' />"  />
				</s:if>
				<s:else>
					<input type="checkbox" name="ids" value="<s:property value='#subtitle.id' />" disabled="disabled" />
				</s:else>
			</td>
			<td><a href="getSubtitleDetail.do?subtitle.id=<s:property value='#subtitle.id' />"><s:property value="#status.count" /></a></td>
			<td><s:property value="#subtitle.word" /></td>
			<td><s:property value="#subtitle.areaNames" /></td>
			<td><s:date name="#subtitle.createTime" format="yyyy-MM-dd hh:mm:ss" /></td>
			<td><s:date name="#subtitle.pushTime" format="yyyy-MM-dd hh:mm:ss" /></td>
			
			<td>
				<s:if test="#subtitle.state==1">创建</s:if>
				<s:elseif test="#subtitle.state==2">已投放</s:elseif>
			</td>
			
		</tr>
	</s:iterator>
  <tr>
    <td colspan="7">
    	<input type="button" value="删除" class="btn" onclick="javascript:delSubtitle();"/>&nbsp;&nbsp;
        <input type="button" value="添加" class="btn" onclick="window.location.href='addSubtitle.do'"/>
        <jsp:include page="../common/page.jsp" flush="true" />
    </td>
  </tr>
</table>
</div>
</div>
</form>
</body>
</html>