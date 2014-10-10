<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link id="maincss" href="<%=path%>/css/new/main.css"
          rel="stylesheet" type="text/css"
          media="all"/>
    <title></title>
    <script type='text/javascript'
            src='<%=path%>/js/jquery.min.js'></script>
</head>

<body style="background: url(<%=path%>/images/bg_06.jpg) no-repeat #2393cf;">
<div id="tabbar"></div>
<!--
<div id="tabbar"></div>
-->
</body>
</html>

<script type="text/javascript">
    var path;
  //选中或打开已存在的标签页时，是否重新刷新页面内容，默认刷新
    var ifRefreshWhenTabActive = true;
var v = {len:'7',index:'0',tab:[],l:""}
$(document).ready(function () {
	var tabbar = $("#tabbar");
	var subMenu = $("<div class='subMenu'></div>");
	var subMenuList = $("<div class='subMenuList' id='subMenuList'></div>").appendTo(subMenu);
	var mainContent = $("<div class='mainContent' id='mainContent'></div>").appendTo(tabbar);
	$("<div class='subMenuDir'><a href='javascript:choiceTab(0)'><img src='<%=path%>/images/new/menu_dir_01.png' width='23' height='20' /></a><a href='javascript:choiceTab(1)'><img src='<%=path%>/images/new/menu_dir_02.png' width='24' height='20' /></a></div>").appendTo(tabbar);
	subMenu.appendTo(tabbar);
	openTab("getAwaitDoing.do",0,'首页');
	$(".subMenuDir a").click(function(e){

		var marginLeft = parseInt(v.l) + 110;;
		if(marginLeft)
			$(".subMenuList").css("margin-left",v.l+"px");
		}
	)
})
function openTab(url, tabId, tabName){
	var menuA = $(".subMenuList a");
	if(v.tab[tabId]){
		if(ifRefreshWhenTabActive){ //刷新
            $("#fram_"+tabId).attr("src", url);
        }
		showFrame(tabId);
		return;
	}
	v.index = tabId;
	v.tab[tabId] = tabName;
	if($(".subMenuList a"))$(".subMenuList a").removeAttr("class");
	$(".mainContent iframe").attr('class','searchFrame');
	var subMenuList = $('.subMenuList');
	var mainContent = $('.mainContent');
	var tab = $("<a href='#' class='focus' id='tab_"+tabId+"' title='"+tabName+"'><input type = 'button' />"+tabName+"</a>").appendTo(subMenuList);
	if(menuA.length>=v.len){
		v.l = "-"+(menuA.length-v.len)*115;
		$(".subMenuList").css("margin-left",v.l+"px");
	}
	$("<iframe class='searchFrame focus' frameborder='0' id = 'fram_"+tabId+"' src='"+url+"'></iframe>").appendTo(mainContent);
	$(".subMenuList a#tab_"+tabId).click(function(e){showFrame(tabId);});
    $(".subMenuList a#tab_"+tabId).dblclick(function(e){removeChild(tabId)});
	$(".subMenuList a#tab_"+tabId+" input").click(function(e){removeChild(tabId)});
}
function showFrame(id){
	v.index = id;
	if($("#tab_"+id).attr('class')=="focus")return;
	$(".subMenuList a").removeAttr("class");
	$(".mainContent iframe").attr('class','searchFrame');
	var left = parseInt(document.getElementById("tab_"+id).offsetLeft);
	if(left<0){
		var listLeft = parseInt(v.l) - left;
		v.l = listLeft;
		$("#subMenuList").css("margin-left",listLeft+"px");
	}else if(left>=920){  // 920 = v.len*115 + 15
		if(parseInt(v.l)>=0) v.l = 0;
		var t = parseInt(v.l) - parseInt(left) + 920 - 115;
		v.l = t;
		$("#subMenuList").css("margin-left",t+"px");
	}
	$("#tab_"+id).attr('class','focus');
	$("#fram_"+id).attr('class','searchFrame focus');
}
function removeChild(id){
	var AId,tabId = "tab_"+id,frameId = "fram_"+id,lastId=$('.subMenuList>a:last-child').attr("id");
	if($(".subMenuList a").length==1) return;
	if($("a[id="+tabId+"]").attr("class")=="focus"){
		AId = lastId == tabId?$("a[id="+tabId+"]").prev("a").attr("id"):$("a[id="+tabId+"]").next("a").attr("id");
		showFrame(AId.substring(4));
	}
	v.tab[id] = "";
	$(".subMenuList a").remove("a[id=tab_"+id+"]");
	$(".mainContent iframe").remove("iframe[id=fram_"+id+"]");
}
function choiceTab(dir){
	var AId,lastId=$('.subMenuList>a:last-child').attr("id"),firstId=$('.subMenuList>a:first-child').attr("id");
	if(dir == 0){
		AId = firstId.substring(4)==v.index ? lastId : $("a[id=tab_"+v.index+"]").prev("a").attr("id");
	}else{
		AId = lastId.substring(4)==v.index ? firstId : $("a[id=tab_"+v.index+"]").next("a").attr("id");
	}
	showFrame(AId.substring(4));
}
</script>

