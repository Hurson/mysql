<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- 
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />
<link href="<%=path%>/css/Pager.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right_new1.css" type="text/css" />
 -->
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css">
  

 <script src="<%=path %>/js/jquery/jquery-1.3.1.min.js" type="text/javascript"></script>
  <script src="<%=path %>/js/jquery/cluetip/jquery.bgiframe.min.js" type="text/javascript"></script>
  <script src="<%=path %>/js/jquery/cluetip/jquery.hoverIntent.js" type="text/javascript"></script>
  <script src="<%=path %>/js/jquery/cluetip/jquery.cluetip.js" type="text/javascript"></script>
 
  <link rel="stylesheet" href="<%=path %>/css/cluetip/jquery.cluetip.css" type="text/css" />
 <!--  <script src="<%=path %>/js/jquery/cluetip/demo.js" type="text/javascript"></script>-->

<title>策略维护</title>
<script>
$(document).ready(function() {
	var temp='#sticky_310';
	var stikys=document.getElementsByName("sticky");
	if (stikys!=null)
	{
		for (var i=0;i<stikys.length;i++)
		{
			temp = '#'+stikys[i].id;
			$(temp).cluetip({sticky: true, closePosition: 'title',closeText: '<img src="<%=path %>/css/cluetip/images/cross.png" alt="close" />', arrows: true });	
		}
	}
   
});

//unrelated to clueTip -- just for the demo page...

  
 function popupDiv(div_id) {
           var div_obj = $("#" + div_id);
           var windowWidth = document.body.clientWidth;
           var windowHeight = document.body.clientHeight;
           var popupHeight = div_obj.height();
           var popupWidth = div_obj.width();
           //添加并显示遮罩层   
           $("<div id='mask'></div>").addClass("mask")
                                  .width(windowWidth + document.body.scrollWidth)
                                  .height(windowHeight + document.body.scrollHeight)
                                  .click(function () { hideDiv(div_id); })
                                  .appendTo("body")
                                  .fadeIn(200);
           div_obj.css({ "position": "absolute" });
           div_obj.show();
           
          //  .animate({ left: windowWidth / 2 - popupWidth / 2,
          //         top: windowHeight / 2 - popupHeight / 2, opacity: "show"
          //     }, "slow")
       }
       function hideDiv(div_id) {
           $("#mask").remove();
           $("#" + div_id).hide();
          // $("#" + div_id).animate({ left: 0, top: 0, opacity: "hide" }, "slow");
       } 


function query() {
        document.forms[0].submit();
      
    // showDiv();
       /// $("queryForm").submit();
    }

 function addData() {
      var ployId = document.getElementById("ployId").value;
      var positionId = document.getElementById("positionId").value;
      var dataid=0;
       window.location.href="<%=path %>/page/precise/getPreciseMatch.do?ployId="+ployId+"&precise.id="+dataid+"&positionId="+positionId;    
    }
 function deleteData() {
	 if (getCheckCount("dataids") <= 0) {
                alert("请勾选需删除的记录！");
                return;
     }
	 var ret = window.confirm("您确定要删除吗？");
	 if (ret==1)
     {
		 document.forms[0].action="<%=path %>/page/precise/deletePrecise.do";
         document.forms[0].submit();
     }
    }
  function modifyData(dataid) {
	  var ployId = document.getElementById("ployId").value;
      var positionId = document.getElementById("positionId").value;
        window.location.href="<%=path %>/page/precise/getPreciseMatch.do?ployId="+ployId+"&precise.id="+dataid+"&positionId="+positionId;    
    
	  
	  //window.location.href="<%=path %>/page/precise/getPreciseMatch.do?ployId="+ployId+"&precise.id="+dataid; 

       
    }
  
function selectContract() {
          	var structInfo = '';
			structInfo+='					<div style="margin:0px;padding:0px;">';
			structInfo+='							<iframe id="selectContraceFrame" name="selectContraceFrame" src="<%=request.getContextPath()%>'+'/page/ploy/queryContractList.do'+'" frameBorder="0" width="800px" height="540px"  scrolling="yes"></iframe>';
			structInfo+='					</div>';
			easyDialog.open({
			container : {
				header : '选择合同',
				content : structInfo
			},			
			overlay : false
		});
	 } 
 
</script>
<style>
	.easyDialog_wrapper{ width:800px;height:580px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
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
 <form action="<%=path %>/page/precise/queryPreciseList.do" method="post" id="queryForm">
 <s:set name="page" value="%{pagePreciseMatch}" />
		 <input type="hidden" id="pageNo" name="pagePreciseMatch.pageNo" value="${page.pageNo}"/>
		 <input type="hidden" id="pageSize" name="pagePreciseMatch.pageSize" value="${page.pageSize}"/>
	 <input type="hidden" id="ployId" name="ployId" value="${ployId}"/>
	 <input type="hidden" id="positionId" name="positionId" value="${positionId}"/>
	 
 
<div class="search">
<div class="path">首页 >> 投放策略管理 >> 精准维护</div>
<div class="searchContent" >
<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
      <span>精准名称：</span><input onkeypress="return validateSpecialCharacter();" type="text" name="precise.matchName" id="precise.matchName" value="${precise.matchName}"/>
	 
        <input type="button" value="查询" onclick="javascript:query();" class="btn"/></td>
  </tr>
</table>
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;">
<c:if test="${message != null}">
			 <span style="color:red;"><s:property value="%{getText(#request.message)}" /></span>
</c:if></div>
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td height="28" class="dot"><input type="checkbox" name="selectAllBox" onclick="selectAll(this, 'dataids');"/></td>
        <td width="30%" align="center" >精准名称</td>
        <!-- onmouseover="showdiv(this,'1')" onmouseout="showdiv(this,'2')" -->
        <td width="30%" align="center">精准类型</td>
        <td width="30%" align="center">优先级</td>
     
    </tr>
   				 <c:if test="${pagePreciseMatch.dataList != null && fn:length(pagePreciseMatch.dataList) > 0}">
                    <c:forEach items="${pagePreciseMatch.dataList}" var="preciseInfo" varStatus="pl">
                        <tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
                            <td>
                                <input type="checkbox" value="<c:out value='${preciseInfo.id}'/>" name="dataids"/>
                             </td>
                            <td width="30%" >
                               
                            
                                <a  onclick="javascript:modifyData('${preciseInfo.id}');">${preciseInfo.matchName}</a>
                                <div id="${preciseInfo.id}" name="${preciseInfo.id}" style="display:none" >
				                  ${preciseInfo.id} 
								</div>
                            </td>
                            <td width="30%" align="center">
                         <a  id="sticky_${preciseInfo.id}" name="sticky" href="#"
                                         rel="<%=path %>/page/precise/getPreciseMatchinfo.do?ployId=${ployId}&positionId=${positionId}&precise.id=${preciseInfo.id}">
                       <!--  <a href="#" onmouseover="popupDiv('pop-div');">-->
                            <c:choose>
						<c:when test="${preciseInfo.precisetype==1}">
							按回看产品
						</c:when>
						<c:when test="${preciseInfo.precisetype==2}">
							按影片关键字
						</c:when>
						<c:when test="${preciseInfo.precisetype==3}">
							按受众
						</c:when>
						<c:when test="${preciseInfo.precisetype==4}">
							按影片分类
						</c:when>
						<c:when test="${preciseInfo.precisetype==5}">
							按回放频道
						</c:when>
						<c:when test="${preciseInfo.precisetype==6}">
							按回看栏目
						</c:when>
						<c:when test="${preciseInfo.precisetype==7}">
							按频道
						</c:when>
						<c:when test="${preciseInfo.precisetype==8}">
							按影片
						</c:when>
						<c:when test="${preciseInfo.precisetype==9}">
							按区域
						</c:when>
						<c:when test="${preciseInfo.precisetype==10}">
							按频道分组
						</c:when>
					</c:choose>
                           </a>
				         
							</td>
					        <td width="20%" align="center">
					        	${preciseInfo.priority}
                            
							</td>
					   
                        </tr>
                    </c:forEach>
                </c:if>


  <tr>
    <td colspan="4">
		
        <input type="button" value="添加" class="btn" onclick="javascript:addData();"/>&nbsp;&nbsp;
        <input type="button" value="删除" class="btn" onclick="javascript:deleteData();"/>
		
								&nbsp;&nbsp;
					        <jsp:include page="../../common/page.jsp" flush="true" />

    </td>
  </tr>
</table>
</div>
</div>

<div id="materialDiv" class="showDiv" style="display:none">
	<table cellpadding="0" cellspacing="0" style="margin-top: 0; width: 760px; height: 336px; border: 1px solid #cccccc; font-size:12px; background-color:#80;">
		<tr class="list_title_x">
			<td style="border: 0; padding-left:5px;" align="center">素材审核</td>
		    <td style="border: 0;" align="right"></td>
		</tr>
		<tr>
			<td colspan='2' valign="top">
				999
			</td>
		</tr>
		<tr>
			<td colspan="2" class="td_bottom">
				<div class="buttons">
					<a href="javascript:submitOpintions()">确认</a>
					<a href="javascript:closeSelectDiv()">返回</a>
				</div>		
			</td>
		</tr>
	</table>
</div> 
<div id="bg" class="bg" style="display:none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>
</form>
</body>

</html>