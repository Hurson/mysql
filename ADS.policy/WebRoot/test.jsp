<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title></title>
    <link href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet" type="text/css" media="all"/>
	<link  href="<%=request.getContextPath()%>/js/dhtmlx/tree/css/dhtmlxtree.css" rel="stylesheet" type="text/css" media="all"/>
	 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/dhtmlx/dhtmlxmenu_full/skins/dhtmlxmenu_dhx_blue.css">

    <script type='text/javascript'  src='<%=request.getContextPath()%>/js/avit.js'></script>
	<script type='text/javascript'  src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/js/dhtmlx/tree/js/dhtmlxcommon.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/js/dhtmlx/tree/js/dhtmlxtree.js'></script>
<script src="<%=request.getContextPath()%>/js/dhtmlx/dhtmlxmenu_full/dhtmlxcommon.js"></script>
	<script src="<%=request.getContextPath()%>/js/dhtmlx/dhtmlxmenu_full/dhtmlxmenu.js"></script>
	<script src="<%=request.getContextPath()%>/js/dhtmlx/dhtmlxmenu_full/dhtmlxmenu_ext.js"></script>

</head>
<body>
<link rel='STYLESHEET' type='text/css' href='css/style.css'>
<!-- 当前鼠标的位置 -->
<input type="hidden" id=mouseXPosition><input type="hidden" id=mouseYPosition> 
	

	<table>
		<tr>
			<td>
				<div id="treeboxbox_tree" style="width:260; height:260;background-color:#f5f5f5;border :1px solid Silver;"/>
			</td>
			<td style="padding-left:25px" valign="top">
			
				<div id="treeboxbox_tree2" style="width:250px; height:218px;background-color:#f5f5f5;border :1px solid Silver;overflow:hidden"></div>
			

			</td>
		</tr>
		
	</table>
	<div id="ta" style="width: 500px; height: 160px; border: #909090 1px solid; overflow: auto; font-size: 10px; font-family: Tahoma;"></div>

<script type="text/javascript"> 
// 说明:获取鼠标位置
function mousePosition(ev){
	if(ev.pageX || ev.pageY){
		return {x:ev.pageX, y:ev.pageY};
	}
	return {
		x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,
		y:ev.clientY + document.body.scrollTop  - document.body.clientTop
	};
}
document.onmousemove = mouseMove;
function mouseMove(ev){
    ev = ev || window.event;
    var mousePos = mousePosition(ev);
	document.getElementById('mouseXPosition').value = mousePos.x;
	document.getElementById('mouseYPosition').value = mousePos.y;
}
</script> 

	<script>
	
	function tonclick(id){
		//alert("Item "+tree.getItemText(id)+" was selected");
		var url=tree.getURL(id);
		var target=tree.getTarget(id);
		//alert("url:"+url+"   target:"+target);
		//alert("node:"+tree.getItem(id)+"   url:"+tree.getItem(id).itemURL+"   target:"+tree.getItem(id).itemTarget+"	span:"+tree.getItem(id).span+"	"+tree.getItem(id).span.innerHTML);
		//alert("htmlNode:"+tree.getItem(id).htmlNode+"   tr:"+tree.getItem(id).tr+"	"+tree.getItem(id).tr.innerHTML+"	tr.id:"+tree.getItem(id).tr.id+"   userData:"+tree.getItem(id).userData);
		//alert("htmlNode.innerHTML:"+tree.getItem(id).htmlNode.innerHTML+"	htmlNode.outerHTML:"+tree.getItem(id).htmlNode.outerHTML);
		if(typeof(url)=="undefined")
			url="#";
		if(typeof(target)=="undefined")
			target="mainFrame";
	};
	
	var menu = new dhtmlXMenuObject();
	function treeOnRegihtClick(id){
		//alert("右键"+"	span.id:"+tree.getItem(id).span.id);
		
		menu.setImagePath("imgs/");
		menu.setIconsPath("images/");
		menu.renderAsContextMenu();
		menu.loadXML("<%=request.getContextPath()%>/js/dhtmlx/dhtmlxmenu_full/dhxmenu.xml?e=" + new Date().getTime());
	menu.addContextZone(tree.getItem(id).span.id);alert("width:"+tree.getItem(id).span.clientWidth);
		//var X=tree.getItem(id).span.getBoundingClientRect().left;
		//var Y=tree.getItem(id).span.getBoundingClientRect().top;
		var X=document.getElementById('mouseXPosition').value;
		var Y=document.getElementById('mouseYPosition').value;
		menu.showContextMenu(X,Y);
		//menu._showContextMenu(X,Y,tree.getItem(id).span.id);
	}
	menu.attachEvent("onBeforeContextMenu", function(zoneId){
		if (zoneId == "zoneA") {
			document.getElementById("ta").innerHTML += "<b>onBeforeContextMenu</b>: context menu will shown at <b>"+zoneId+"<b>; deny (return false)<br>";
			return false;
		}
		document.getElementById("ta").innerHTML += "<b>onBeforeContextMenu</b>: context menu will shown at <b>"+zoneId+"<b>; allow (return true)<br>";
		return true;
	});
	menu.attachEvent("onAfterContextMenu", function(zoneId){
		alert(tree.getSelectedItemText());
		document.getElementById("ta").innerHTML += "<b>onAfterContextMenu</b>: context menu was shown at <b>"+zoneId+"<b><br>";
	});
	menu.attachEvent("onClick", function(id, zoneId){
		document.getElementById("ta").innerHTML += "<b>onClick</b>: "+id+" was clicked, context menu at zone <b>"+zoneId+"<b><br>";
	});
	menu.attachEvent("onRadioClick", function(gruop, checked, clicked, zoneId){
		document.getElementById("ta").innerHTML += "<b>onRadioClick</b>: "+clicked+" was clicked, context menu at zone <b>"+zoneId+"<b><br>";
		return true;
	});
	menu.attachEvent("onCheckboxClick", function(id, state, zoneId) {
		document.getElementById("ta").innerHTML += "<b>onCheckboxClick</b>: "+id+" was clicked, context menu at zone <b>"+zoneId+"<b><br>";
		return true;
	});

	

			tree=new dhtmlXTreeObject("treeboxbox_tree","100%","100%",0);
			tree.setImagePath("images/");
			tree.enableDragAndDrop(true);
			tree.setOnClickHandler(tonclick);
			tree.setOnRightClickHandler(treeOnRegihtClick);//右键事件
			//tree.loadXML("tree.xml");
			tree.insertNewChild(0,155,"自定义节点","http://www.baidu.com","main");
			tree.insertNewChild(588,600,"自定义节点 呵呵");
			tree.insertNewItem(0,156,"再来一个 自定义节点");

	

	tree2=new dhtmlXTreeObject("treeboxbox_tree2","100%","100%",0);
	tree2.enableCheckBoxes(1);
	tree2.enableThreeStateCheckboxes(true);
	tree2.setImagePath("images/");
	tree2.enableDragAndDrop(true);
	
	tree2.insertNewChild(0,155,"自定义节点","http://www.baidu.com","main");
	tree2.insertNewChild(588,600,"自定义节点 呵呵");
	tree2.insertNewItem(0,156,"再来一个 自定义节点");
            

	tree2.attachEvent("OnDrop",func);

	function func(a,b,c,d,e) {
		alert(a + "====" + b + "====" + c );
	}
			/*被拖拽结点的id 
			目标结点的id 
			前目标结点(如果拖拽的是兄弟结点) 
			源树对象
			目标树对象*/
	</script>

</body>
</html>
