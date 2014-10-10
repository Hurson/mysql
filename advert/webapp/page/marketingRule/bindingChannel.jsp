<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tags/fmt.tld" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<input id="projetPath" type="hidden" value="<%=path%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css">
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/util/util.js"></script>
<title>频道列表</title>
<script>
var alreadyChoosePosition = [];
var areaIndexFlag = '${areaIndexFlag}';
$(function(){   
    $("#system-dialog").hide();
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
    
    var channelList="{'list':[";
     <c:choose>
		<c:when test="${!empty channelList}">
			<c:forEach var="advertChannel" items="${channelList}" varStatus="status">
				channelList+="{'channelOrder':'${status.count}','id':'${advertChannel.id}','channelName':'${advertChannel.channelName}'}";
				<c:choose>
					<c:when test="${status.last}">
					</c:when>
					<c:otherwise>
						channelList+=',';
					</c:otherwise>
				</c:choose>						
			</c:forEach>
		</c:when>			
	</c:choose>
	channelList+="]}";
	var channelListObject=eval("("+channelList+")");
	
    $("#chooseChannelButton").click(function(){
    	var areaId = $("#areaId").val();
     	var exist = false;
     	var alreadyChoose = "";
	    $("input[name='channelCheckbox']").each(function(){
	        if($(this).is(':checked')){
	            alreadyChoose += $(this).val() + "#";
	        }
	    });
	    if($.isEmptyObject(alreadyChoose)){
	    	var message = '请先选择已有【频道】后再进行添加';
			$("#content").html(message);
			$( "#system-dialog" ).dialog({
		      	modal: true
		    });
	    }else{
	    	//parent.getAlreadyChoose(8);
			var channelIndex='init';
			$(parent.alreadyFillInForm.bindingArea).each(function(index,item){
	   			if(areaIndexFlag==item.areaIndexFlag){
	   				channelIndex = index;
	   			}
	    	});
	    	var alreadyChooseColumn = alreadyChoose.split('#');
	    	
	    	if(channelIndex!='init'){
	    		var existChooseChannel = parent.alreadyFillInForm.bindingArea[channelIndex].channel;
	    		$(alreadyChooseColumn).each(function(index,itemOut){
	    			if(!$.isEmptyObject(itemOut)){
	    				$(channelListObject.list).each(function(index,itemInner){
	    					if((!$.isEmptyObject(itemOut))&&(itemOut==itemInner.id)){
	    						$(existChooseChannel).each(function(index,item){
		   							if(itemOut == item.id){
		   								exist = true;
		   							}
	   							});
	   							if(!exist){
		       						var jsonStr = deepCopy(itemInner);
		       						alreadyChoosePosition.push(jsonStr);
	       						}
	    					}
	    				});
	   				}
	    		});	
	    	}
	    	
	    	if(!exist){
	 			parent.showPositionList4Page(1,8);
	 			parent.easyDialog.close();
	 		}else{
	       		alert("包括已经添加的频道！");
	       		return;
	       	}
	    }
     });
});

	/**
	 * 对象属性拷贝
	 *
	 */
	function deepCopy(obj){
           if(obj instanceof Array){
               var arr = [],i = obj.length;
               while(i--){
                  arr[i] = arguments.callee.call(null,obj[i]);
               }
               return arr;
           }else if(obj instanceof Date || obj instanceof RegExp || obj instanceof Function){
               return obj;
           }else if(obj instanceof Object){
               var a = {};
               for(var i in obj){
                  a[i] = arguments.callee.call(null,obj[i]);
               }
               return a;
           }else{
               return obj;
           }
       }
</script>
<style>
	.easyDialog_wrapper{ width:700px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
<style>
.treven {
	background: #FFFFFF;
}

.trodd {
	background: #f6f6f6;
}
</style>

</head>



<body class="mainBody">

<div class="search">
        <div class="path">
        	首页 >> 营销规则管理 >>营销规则管理&gt;&gt;选择频道</div>
        <div class="searchContent">
        <table cellspacing="1" class="searchList">
            <tr class="title">
                <td>查询条件</td>
            </tr>

        </table>
        <div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
        <table width="100%" cellspacing="1" class="searchList" id="bm">
            <tr class="title">
              <td width="38" height="28" class="dot">选项</td>
              	<td width="150">序号</td>
                <td width="163">频道名称</td>
            </tr>
            <c:choose>
						<c:when test="${!empty channelList}">
							<c:forEach var="channels" items="${channelList}" varStatus="status">
								<tr>
									<td align="center" height="26"><input type="checkbox" id="channelCheckbox${channels.id}" name="channelCheckbox" value="${channels.id}"/></td>
									<td>${status.count}</td>
									<td>${channels.channelName}</td>
									
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="3">
									暂无记录
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
           <tr>
						<td height="34" colspan="3" style="text-align: left;">
						    <input id="areaId" type="hidden" value="${areaId}"/>
							<input id="chooseChannelButton" name="chooseChannelButton" type="button" title="确定" class="btn" value="确定" onfocus=blur()/>
					<div class="page">
					<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp; 
					<c:choose>
						<c:when test="${page.pageNo==1&&page.totalPage!=1}">
							<a href="queryChannel.do?pageNo=${page.pageNo+1 }&positionId=${positionId}&areaId=${areaId}&areaIndexFlag=${areaIndexFlag}">下一页</a>&nbsp;&nbsp;
							<a href="queryChannel.do?pageNo=${page.totalPage}&positionId=${positionId}&areaId=${areaId}&areaIndexFlag=${areaIndexFlag}">末页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
							<a href="queryChannel.do?pageNo=1&positionId=${positionId}&areaId=${areaId}&areaIndexFlag=${areaIndexFlag}">首页</a>&nbsp;&nbsp;
							<a href="queryChannel.do?pageNo=${page.pageNo-1 }&positionId=${positionId}&areaId=${areaId}&areaIndexFlag=${areaIndexFlag}">上一页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
							<a href="queryChannel.do?pageNo=1&positionId=${positionId}&areaId=${areaId}&areaIndexFlag=${areaIndexFlag}">首页</a>&nbsp;&nbsp;
							<a href="queryChannel.do?pageNo=${page.pageNo-1 }&positionId=${positionId}&areaId=${areaId}&areaIndexFlag=${areaIndexFlag}">上一页</a>&nbsp;&nbsp;
							<a href="queryChannel.do?pageNo=${page.pageNo+1 }&positionId=${positionId}&areaId=${areaId}&areaIndexFlag=${areaIndexFlag}">下一页</a>&nbsp;&nbsp;
							<a href="queryChannel.do?pageNo=${page.totalPage}&positionId=${positionId}&areaId=${areaId}&areaIndexFlag=${areaIndexFlag}">末页</a>&nbsp;&nbsp;
						</c:when>
					</c:choose>
					</div>
						</td>
					</tr>
           
        </table>
    </div>
</div>
<div id="system-dialog" title="友情提示" >
  <p>
    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>
    <span id="content"></span>
  </p>
</div>
</body>
</html>