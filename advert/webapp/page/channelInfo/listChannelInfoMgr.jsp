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
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="css/platform.css" type="text/css" />

<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>

<script language="javascript" type="text/javascript" src="<%=path%>/js/contract/listContract.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js"></script>

<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css">

<title>广告资产管理</title>
<script><!--
	//删除
	function goDeleteInfo(id){
     var a = window.confirm("您确定要删除此条频道记录吗？");
  			if(a==1){
    		$.ajax( {
				type : 'post',
				url : 'channelInfo_deleteChannelInfo.do',
				data : 'cId=' + id +'&date=' + new Date(),
				success : function(msg) {
					if(msg != null){
						window.location.href="channelInfo_list.do";
					}
				}	
			});
		}	
	} 


	function test(){
		 window.location.href="adCustomerMgr_getAllCustomer.do";
	}

	function go(){
	 document.getElementById("form").submit();
	}


$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
});
</script>
<style>
.treven {
	background: #FFFFFF;
}

.trodd {
	background: #f6f6f6;
}
</style>
</head>

<body>
<form  id="form" action="channelInfo_list.do" method="post" id="channelInfo_list" name="channelInfo_list"> 
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td>
		<table cellpadding="0" cellspacing="0" border="0" width="100%"
			class="tab_right">
			<tr>
				<td>工作台</td>
				<td>客户</td>
				<td>日程行动</td>
				<td>销售机会</td>
				<td>订单管理</td>
				<td>客服中心</td>
				<td>财务中心</td>
				<td>营销中心</td>
				<td>人力资源中心</td>
				<td>数据统计</td>
				<td>信息门户管理</td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td style="padding: 4px;">
		<table cellpadding="0" cellspacing="1" width="100%" class="tablea">
			<tr>
				<td class="formTitle" colspan="99"><span>广告频道管理</span></td>
			</tr>
			<tr>
				<td class="td_label">频道名称：</td>
				<td class="td_input"><input type="text" class="e_input" name="channelInfo.channelName" value="${channelName}" onblur="this.className='e_input'" onfocus="this.className='e_inputFocus'"/></td>
				<td class="td_label">频道类型：</td>
				<td class="td_input"><select name="channelInfo.channelType">
					<option value="10">全部</option>
					<option value="1">音频直播类业务</option>
					<option value="2">视频直播类业务</option>
				</select></td>
			</tr>
			<tr>
				<td class="td_label">区域名：</td>
				<td class="td_input"><input type="text" name="channelInfo.locationName" value="${locationName}" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" /></td>
				<td class="td_label">区域编码：</td>
				<td class="td_input"><input type="text" name="channelInfo.locationCode" value="${locationCode}" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" /></td>			
			</tr>
			<tr>
				<td class="formBottom" colspan="99">
				<!--<input name="Submit" type="submit" title="查看" value="" class="b_search" onfocus=blur() />-->
				<input name="Submit" type="button" title="查看" value="" class="b_search" onfocus=blur() onclick="go()" />
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td style="padding: 4px;">
		<table cellpadding="0" cellspacing="1" width="100%" class="taba_right_list" id="bm">

			<tr>
				<td colspan="12" class="formTitle"
					style="padding-left: 8px; background: url(images/menu_righttop.png) repeat-x; text-align: left; height: 26px;"><span>广告频道分类列表</span></td>
			</tr>

			<tr>
				<td  height="26px" align="center">序号</td>
				<td  height="26px" align="center">频道名称</td>
				<td  height="26px" align="center">频道类型</td>
				<td  height="26px" align="center">频道语种</td>
				<td  height="26px" align="center">频道编码</td>
				<td  height="26px" align="center">区域码</td>
				<td  height="26px" align="center">区域名</td>
				<td  height="26px" align="center">状态</td>
				<td  height="26px" align="center">创建时间</td>
				<td  height="26px" align="center">操作</td>
			</tr>
			<s:if test="listChannelInfo.size==0">
			<tr>
				<td bgcolor="#FFFFFF" align="center" colspan="12"
					style="text-align: center">无记录</td>
			</tr>
		</s:if>
		<s:else>
		<c:set var="index" value="0"/>
			<s:iterator value="listChannelInfo" status="rowstatus" var="item">
				<tr>
					<td align="center">${rowstatus.count}</td>
					<td align="center"><s:property value="channelName" /></td>
					<td align="center"><s:property value="channelType" /></td>
					<td align="center"><s:property value="channelLanguage" /></td>
					<td align="center"><s:property value="channelCode" /></td>
					<td align="center"><s:property value="locationCode" /></td>
					<td align="center"><s:property value="locationName" /></td>
					<td align="center">
						<s:if test="state=='1'">可用</s:if>
						<s:if test="state=='0'">不可用</s:if>
					</td>
					<td align="center"><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/></td>
					<td align="center">
						<input type="button" title="删除" class="button_delete"  onclick="goDeleteInfo(<s:property value='channelId'/>)" />
					</td>
				</tr>
				<c:set var="index" value="${index+1}"/>
			</s:iterator>
			<c:if test="${index<page.pageSize}">
                <c:forEach begin="${index}" end="${page.pageSize-1}" step="1">
					<tr>
						<td align="center" height="26">&nbsp;</td>
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
				<td height="34" colspan="12" style="background: url(images/bottom.jpg) repeat-x; text-align: right;">
					<a href="#">共计${page.totalPage }页</a>&nbsp;&nbsp;
					<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp;
						<c:choose>
							<c:when test="${page.pageNo==1&&page.totalPage!=1}">
								【<a href="channelInfo_list.do?pageNo=${page.pageNo+1 }&channelName=${channelInfo.channelName}&channelType=${channelInfo.channelType}&locationName=${channelInfo.locationName}&locationCode=${channelInfo.locationCode}&infoState=${channelInfo.infoState}">下一页</a>】&nbsp;&nbsp;
								【<a href="channelInfo_list.do?pageNo=${page.totalPage}&channelName=${channelInfo.channelName}&channelType=${channelInfo.channelType}&locationName=${channelInfo.locationName}&locationCode=${channelInfo.locationCode}&infoState=${channelInfo.infoState}">末页</a>】&nbsp;&nbsp;
							</c:when>
							<c:when test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
								【<a href="channelInfo_list.do?pageNo=1&channelName=${channelInfo.channelName}&channelType=${channelInfo.channelType}&locationName=${channelInfo.locationName}&locationCode=${channelInfo.locationCode}&infoState=${channelInfo.infoState}">首页</a>】&nbsp;&nbsp;
								【<a href="channelInfo_list.do?pageNo=${page.pageNo-1 }&channelName=${channelInfo.channelName}&channelType=${channelInfo.channelType}&locationName=${channelInfo.locationName}&locationCode=${channelInfo.locationCode}&infoState=${channelInfo.infoState}">上一页</a>】&nbsp;&nbsp;
							</c:when>
							<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
								【<a href="channelInfo_list.do?pageNo=1&channelName=${channelInfo.channelName}&channelType=${channelInfo.channelType}&locationName=${locationName}&locationCode=${locationCode}&infoState=${channelInfo.infoState}">首页</a>&nbsp;&nbsp;
								【<a href="channelInfo_list.do?pageNo=${page.pageNo-1 }&channelName=${channelInfo.channelName}&channelType=${channelInfo.channelType}&locationName=${channelInfo.locationName}&locationCode=${channelInfo.locationCode}&infoState=${channelInfo.infoState}">上一页</a>】&nbsp;&nbsp;
								【<a href="channelInfo_list.do?pageNo=${page.pageNo+1 }&channelName=${channelInfo.channelName}&channelType=${channelInfo.channelType}&locationName=${channelInfo.locationName}&locationCode=${channelInfo.locationCode}&infoState=${channelInfo.infoState}">下一页</a>】&nbsp;&nbsp;
								【<a href="channelInfo_list.do?pageNo=${page.totalPage}&channelName=${channelInfo.channelName}&channelType=${channelInfo.channelType}&locationName=${channelInfo.locationName}&locationCode=${channelInfo.locationCode}&infoState=${channelInfo.infoState}">末页</a>】&nbsp;&nbsp;
							</c:when>
						</c:choose>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
