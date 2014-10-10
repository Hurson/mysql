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
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>

<title>频道列表</title>
<script>
var alreadyChoosePosition = [];
$(function(){   
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
	    	var alreadyChooseColumn = alreadyChoose.split('#');
	    	$(alreadyChooseColumn).each(function(index,itemOut){
	    		if(!$.isEmptyObject(itemOut)){
	       			$(channelListObject.list).each(function(index,itemInner){
	       				if((!$.isEmptyObject(itemOut))&&(itemOut==itemInner.id)){
	       					$(alreadyChoosePosition).each(function(index,itemAlready){
	       						if(itemAlready.id == itemOut){
	       							alreadyChoosePosition.splice(index,1);
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
	 		if(!exist){
	 			parent.showPositionList4Page(1,8);
	 		}else{
	       		alert("包括已经添加的频道！");
	       		return;
	       	}
	 		//parent.easyDialog.close();
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
	.easyDialog_wrapper{ width:800px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
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
<body>
<div>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td style="padding:2px;">
			<div>  
				<table cellpadding="0" cellspacing="1" width="100%" class="taba_right_list" id="bm">
					<tr>
						<td colspan="23" style="padding-left: 8px; background: url(<%=path %>/images/menu_righttop.png) repeat-x; text-align: left; height: 26px;"><span>·地区列表</span></td>
					</tr>
					<tr>
						<td height="26px" align="center">选项</td>
						<td height="26px" align="center">序号</td>
						<td>频道名称</td>
					</tr>
					
					<c:choose>
						<c:when test="${!empty channelList}">
							<c:forEach var="channels" items="${channelList}" varStatus="status">
								<tr>
									<td align="center" height="26"><input type="checkbox" id="channelCheckbox${channels.id}" name="channelCheckbox" value="${channels.id}"/></td>
									<td align="center" height="26">${status.count}</td>
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
						<td colspan="3">
						
						</td>
					</tr>
					
			<c:if test="${index<page.pageSize}">
				<c:forEach begin="${index}" end="${page.pageSize-1}" step="1">
					<tr>
						<td align="center" height="26">&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</c:forEach>
			</c:if>
			<tr>
				<td height="34" colspan="12"
					style="background: url(<%=path %>/images/bottom.jpg) repeat-x; text-align: right;">
					<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp; 
					<c:choose>
						<c:when test="${page.pageNo==1&&page.totalPage!=1}">
							<a href="queryChannel.do?pageNo=${page.pageNo+1 }&positionId=${positionId}">下一页</a>&nbsp;&nbsp;
							<a href="queryChannel.do?pageNo=${page.totalPage}&positionId=${positionId}">末页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
							<a href="queryChannel.do?pageNo=1&positionId=${positionId}">首页</a>&nbsp;&nbsp;
							<a href="queryChannel.do?pageNo=${page.pageNo-1 }&positionId=${positionId}">上一页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
							<a href="queryChannel.do?pageNo=1&positionId=${positionId}">首页</a>&nbsp;&nbsp;
							<a href="queryChannel.do?pageNo=${page.pageNo-1 }&positionId=${positionId}">上一页</a>&nbsp;&nbsp;
							<a href="queryChannel.do?pageNo=${page.pageNo+1 }&positionId=${positionId}">下一页</a>&nbsp;&nbsp;
							<a href="queryChannel.do?pageNo=${page.totalPage}&positionId=${positionId}">末页</a>&nbsp;&nbsp;
						</c:when>
					</c:choose>
				</td>
			</tr>
					
			<tr>
						<td class="formBottom" colspan="99" style="text-align: right;">
							 <input id="chooseChannelButton" name="chooseChannelButton" type="button" title="确定" class="b_add" value="" onfocus=blur()/>
						</td>
					</tr>		
					
		
					
				</table>
			</div>
		</td>
	</tr>
</table>
</div>
</body>
</html>