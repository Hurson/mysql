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
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css">
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/marketingRule/addMarketingRule.js"></script>

<title>地区列表</title>
<script>
var alreadyChoosePosition = [];
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
    var channelName = '',channelId = '';
    var areaList="{'list':[";
     <c:choose>
		<c:when test="${!empty areaList}">
			<c:forEach var="advertArea" items="${areaList}" varStatus="status">
				areaList+="{'areaOrder':'${status.count}','id':'${advertArea.id}','areaName':'${advertArea.areaName}','channel':[]}";
				<c:choose>
					<c:when test="${status.last}">
					</c:when>
					<c:otherwise>
						areaList+=',';
					</c:otherwise>
				</c:choose>						
			</c:forEach>
		</c:when>			
	</c:choose>
	areaList+="]}";
	var areaListObject=eval("("+areaList+")");
	
    $("#chooseAreaButton").click(function(){
     
     	var alreadyChoose = "";
	    $("input[name='areaCheckbox']").each(function(){
	        if($(this).is(':checked')){
	            alreadyChoose += $(this).val() + "#";
	        }
	    });
	    
	    if($.isEmptyObject(alreadyChoose)){
	    	var message = '请先选择已有【地区】后再进行添加';
			$("#content").html(message);
			$( "#system-dialog" ).dialog({
		      	modal: true
		    });
	    }else{
	    	var alreadyChooseColumn = alreadyChoose.split('#');
	    	$(alreadyChooseColumn).each(function(index,itemOut){
	    		if(!$.isEmptyObject(itemOut)){
	       			$(areaListObject.list).each(function(index,itemInner){
	       				if((!$.isEmptyObject(itemOut))&&(itemOut==itemInner.id)){
	       					var jsonStr = deepCopy(itemInner);
	       					alreadyChoosePosition.push(jsonStr);
	       					var timeAreaFlag = new Date().getTime()+"_"+jsonStr.id;
	       					jsonStr.areaIndexFlag=timeAreaFlag;
	       				}
					});
	       		}
			});
	 		parent.showPositionList4Page(1,7);
	 		parent.easyDialog.close();
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



<body class="mainBody">

<div class="search">
        <div class="path">
        	首页 >> 营销规则管理 >>营销规则管理&gt;&gt;选择区域</div>
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
                <td width="163">区域名称</td>
            </tr>
            <c:choose>
						<c:when test="${!empty areaList}">
							<c:forEach var="areas" items="${areaList}" varStatus="status">
								<tr>
									<td align="center" height="26">								
									<input type="checkbox" id="areaCheckbox${areas.id}" name="areaCheckbox" value="${areas.id}"/>
									</td>
									<td>${status.count}</td>
									<td>${areas.areaName}</td>
									
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
							
							<input id="chooseAreaButton" name="chooseAreaButton" type="button" title="确定" class="btn" value="确定" onfocus=blur()/>
					<div class="page">
					<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp; 
					<c:choose>
						<c:when test="${page.pageNo==1&&page.totalPage!=1}">
							<a href="queryArea.do?pageNo=${page.pageNo+1 }&positionId=${positionId}">下一页</a>&nbsp;&nbsp;
							<a href="queryArea.do?pageNo=${page.totalPage}&positionId=${positionId}">末页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
							<a href="queryArea.do?pageNo=1&positionId=${positionId}">首页</a>&nbsp;&nbsp;
							<a href="queryArea.do?pageNo=${page.pageNo-1 }&positionId=${positionId}">上一页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
							<a href="queryArea.do?pageNo=1&positionId=${positionId}">首页</a>&nbsp;&nbsp;
							<a href="queryArea.do?pageNo=${page.pageNo-1 }&positionId=${positionId}">上一页</a>&nbsp;&nbsp;
							<a href="queryArea.do?pageNo=${page.pageNo+1 }&positionId=${positionId}">下一页</a>&nbsp;&nbsp;
							<a href="queryArea.do?pageNo=${page.totalPage}&positionId=${positionId}">末页</a>&nbsp;&nbsp;
						</c:when>
					</c:choose>
					</div>
						</td>
					</tr>
           
        </table>
    </div>
</div>
<div id="system-dialog" title="友情提示">
  <p>
    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>
    <span id="content"></span>
  </p>
</div>
</body>


</html>