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

<link rel="stylesheet" type="text/css"
			href="<%=path%>/css/new/main.css" />
		<link href="<%=path%>/css/new/common/dhtmlx/tree/css/dhtmlxtree.css"
			rel="stylesheet" type="text/css" media="all" />
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/dhtmlxcommon.js"
			type="text/javascript">
</script>
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/dhtmlxtree.js"
			type="text/javascript">
</script>
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/test.js"
			type="text/javascript">
</script>


<link rel="stylesheet" href="css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="css/platform.css" type="text/css" />

<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js"></script>

<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css">

<title>广告资产管理</title>
<script language="javascript" type="text/javascript">

	function deleteData(){
	    var ids = "";
		var  resourceArr = document.getElementsByName("ids");
		for(var i=0; i < resourceArr.length-1;i++ ){
			if(resourceArr[i].checked){
				ids+= resourceArr[i].value +",";
			}
	    }
	    
	    if(ids==""){
	    	alert("您还没有选择要上线的素材，请确认后再操作！");
	    	return;
	    }
		deleteReleaseArea(ids);		
	}
	
	function deleteReleaseArea(ids){
		alert("ids="+ids);
		return;
	}
	
	
	

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

    //删除
	function goDeleteInfo(id){
	
			var a = window.confirm("您确定要删除该条记录吗？");
			if(a == 1){
				$.ajax( {
					type : 'post',
					url : 'ReleaseArea_deleteReleaseArea.do',
					data : 'cId=' + id + '&date='+new Date(),
					success : function(msg) {
						if(msg != null){
							window.location.href="ReleaseArea_list.do";
						}
					}	
				});
			}
	}
  
  
  function go() {
		document.getElementById("form").submit();
}

$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
});
</script>


</head>

<body class="mainBody" onload="">
		<input id="projetPath" type="hidden" value="<%=path%>" />
		<form id="form" action="ReleaseArea_list.do" method="post" name="">
			<div class="search">
				<div class="path">
					<img src="<%=path%>/images/new/filder.gif" width="15" height="13" />
					首页 >> 系统查询 >> 投放区域信息管理
				</div>
				<div class="searchContent">
					<table cellspacing="1" class="searchList">
						<tr class="title">
							<td colspan="9">
								查询条件
							</td>
						</tr>
						<tr>
							<td>
								投放区域名称
								<input type="text" name="releaseArea.areaName"
									 class="e_input"
									onfocus="this.className='e_inputFocus'"
									onblur="this.className='e_input'" />
								投放区域编码：
								<input type="text" name="releaseArea.areaCode"
									value="${clientCode}" class="e_input"
									onfocus="this.className='e_inputFocus'"
									onblur="this.className='e_input'" />
								<input type="button" value="查询" onclick="go();" class="btn" />
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
								投放区域编码
							</td>
							<td>
								投放区域名称
							</td>
							<td>
								父级区域编码
							</td>
							<td>
								父级区域名称
							</td>
							<td  height="26px" align="center">操作</td>
						</tr>
						<s:if test="listReleaseArea.size==0">
							<tr>
								<td bgcolor="#FFFFFF" align="center" colspan="12"  style="text-align: center">
									无记录
								</td>
							</tr>
						</s:if>
						<s:else>
							<c:set var="index" value="0" />
							<s:iterator value="listReleaseArea" status="rowstatus" var="item">
								<tr>
									<td>
										<s:if test='parentCode == "-1"'>
										  <input type="checkbox" name="ids" value="${item.id}" disabled="disabled" />
										</s:if>
										<s:else>
										   <input type="checkbox" name="ids" value="${item.id}" />
										</s:else>
									</td>
									<td align="center">${rowstatus.count}</td>
									<td align="center"><s:property value="areaCode"/></td>
									<td align="center"><s:property value="areaName"/></td>
									<td align="center"><s:property value="parentCode"/></td>
									<td align="center">
									    <s:if test='parentCode == "-1"'>根节点</s:if>
									    <s:if test='parentCode == "0"'>河南</s:if>
									    <s:if test='parentCode == "1"'>郑州</s:if>
									    <s:if test='parentCode == "2"'>开封</s:if>
									    <s:if test='parentCode == "3"'>济源</s:if>
									    <s:if test='parentCode == "4"'>平顶山</s:if>
									    <s:if test='parentCode == "5"'>安阳</s:if>
									    <s:if test='parentCode == "6"'>焦作</s:if>
									    <s:if test='parentCode == "7"'>鹤壁</s:if>
									    <s:if test='parentCode == "8"'>新乡</s:if>
									    <s:if test='parentCode == "9"'>濮阳</s:if>
									    <s:if test='parentCode == "10"'>许昌</s:if>
									    <s:if test='parentCode == "11"'>漯河</s:if>
									    <s:if test='parentCode == "12"'>三门峡</s:if>
									    <s:if test='parentCode == "13"'>南阳</s:if>
									    <s:if test='parentCode == "14"'>商丘</s:if>
									    <s:if test='parentCode == "15"'>信阳</s:if>
									    <s:if test='parentCode == "16"'>周口</s:if>
									    <s:if test='parentCode == "17"'>驻马店</s:if>
									    <s:if test='parentCode == "18"'>济源</s:if>
									</td>	
							<td align="center">
								<s:if test='parentCode == "-1"'></s:if>
								<s:else><input type="button" title="删除" class="button_delete"  onclick="goDeleteInfo(<s:property value='id'/>)" /></s:else>
							</td>
						</s:iterator>
						</s:else>
						<tr>
							<td colspan="10">
								<input type="button" value="删除" class="btn"
									onclick="javascript:deleteData();" />
									<a href="#">共计${page.totalPage }页</a>&nbsp;&nbsp;
									<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp;
									<c:choose>
										<c:when test="${page.pageNo==1&&page.totalPage!=1}">
											【<a href="ReleaseArea_list.do?pageNo=${page.pageNo+1 }">下一页</a>】&nbsp;&nbsp;
											【<a href="ReleaseArea_list.do?pageNo=${page.totalPage}">末页</a>】&nbsp;&nbsp;
										</c:when>
										<c:when test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
											【<a href="ReleaseArea_list.do?pageNo=1">首页</a>】&nbsp;&nbsp;
											【<a href="ReleaseArea_list.do?pageNo=${page.pageNo-1 }">上一页</a>】&nbsp;&nbsp;
										</c:when>
										<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
											【<a href="ReleaseArea_list.do?pageNo=1">首页</a>】&nbsp;&nbsp;
											【<a href="ReleaseArea_list.do?pageNo=${page.pageNo-1 }">上一页</a>】&nbsp;&nbsp;
											【<a href="ReleaseArea_list.do?pageNo=${page.pageNo+1 }">下一页</a>】&nbsp;&nbsp;
											【<a href="ReleaseArea_list.do?pageNo=${page.totalPage}">末页</a>】&nbsp;&nbsp;
										</c:when>
									</c:choose>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>
	</body>
</html>
