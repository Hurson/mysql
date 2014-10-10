<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link id="maincss"  rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/new/main.css" media="all"/>
<title></title>
<script type='text/javascript' src='<%=path%>/js/jquery.min.js'></script>
<script type='text/javascript' src='<%=path%>/js/avit.js'></script>

<script type="text/javascript">
var selectData="";

    //var childAreas=[];
    //var $ = function(id){
       // return document.getElementById(id);
   // };

    function deleteData(){
        document.getElementById("messageDiv").innerHTML = "删除成功";
    }
    
    function addData(){
    	var url = "queryContent4Package.htm";
        window.showModalDialog(url, "", "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
    }


	function selectPositin(){
    	if (getCheckCount('adPositionPackPageInfo.locationId')==0)
    	{
    		alert("请选择广告位");
    		return ;
    	}
    	
    	//设置广告位投放日期
    	if (parent.areaChannels!=null && parent.areaChannels.length>0)
    		{  		
	    		for (var jj=0;jj<parent.areaChannels.length;jj++)
	    		{
	    		var positionStartDate = parent.document.getElementById("positionStartDate_"+parent.areaChannels[jj].positionId).value;
	    		var positionEndDate = parent.document.getElementById("positionEndDate_"+parent.areaChannels[jj].positionId).value;
		    	parent.areaChannels[jj].positionStartDate = positionStartDate;
		    	parent.areaChannels[jj].positionEndDate = positionEndDate;
		    		 
	    		}

        	}

    	var selectData=getCheckValue('adPositionPackPageInfo.locationId');

    	var strPotions = selectData.split(",");
    	
    	for (var i=0;i<strPotions.length;i++)
    	{
			var str = strPotions[i] + "," + document.getElementById("name_"+strPotions[i]).value + "," + document.getElementById("deliveryMode_"+strPotions[i]).value + "," + document.getElementById("videoType_"+strPotions[i]).value + ","
			 + document.getElementById("packageType_"+strPotions[i]).value;
    		if (parent.areaChannels!=null && parent.areaChannels.length>0)
    		{  		
    		
	    		var flag=false;
	    		for (var j=0;j<parent.areaChannels.length;j++)
	    		{
		    		
		    		if (parent.areaChannels[j].positionId==strPotions[i])
	    			{
	    				  flag=true;
	    				  alert(document.getElementById("name_"+strPotions[i]).value+"广告位已选择");
	    				  return ;
	    			}	  
	    		}
	    		if (flag==false)
	    		{
	    			parent.getAreaChannels(str);
	    		}
        	}
    		else
    		{
    			 parent.getFirstChannels(str);
    		}
    	}

		   parent.refreshAreaList();

        parent.easyDialog.close();
    }
    

    
    function cancle(){
    	
        parent.easyDialog.close();
    }
    
    function addIds(id){
    	selectData = document.getElementById("selPositionIds").value
    	if(id.checked){
    	//选中
    	if(selectData==""){
           selectData=id.value;
        }else{
           selectData=selectData+","+id.value;
        }
        document.getElementById("selPositionIds").value=selectData;
        
    	}else{
    	//撤销
    	   if(selectData!=""){
    	      var newdate= "";
    	      var ss= new Array();
              ss = selectData.split(",");
              for(var i=0;i<ss.length;i++){
                 if(ss[i]==id.value){
                     
                 }else{
                   if(newdate==""){
                      newdate=ss[i];
                   }else{
                      newdate=newdate+","+ss[i];
                   }                 
                 }
              }
              selectData=newdate;
              document.getElementById("selPositionIds").value=selectData;

           }
    	}
        

    }

</script>
</head>

<body class="mainBody" >
<form action="<%=path %>/page/contract/selectAdPositionPackage.do" method="post" id="queryForm">
<s:set name="page" value="adPositionPackPage" />
 <input type="hidden" id="pageNo" name="adPositionPackPage.pageNo" value="${page.pageNo}"/>
 <input type="hidden" id="pageSize" name="adPositionPackPage.pageSize" value="${page.pageSize}"/>

<div class="search" >
<div class="searchContent" >
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td height="28" class="dot"><input type="checkbox" name="selectAllBox" onclick="selectAll(this, 'adPositionPackPageInfo.locationId');"/></td>
        <td width="20%" align="center">广告位名称</td>
        <td width="10%" align="center">广告位编码</td>
        <td width="20%" align="center">广告位类型</td>
        <td width="20%" align="center">高标清</td>
        <td width="7%" align="center">是否轮训</td>
        <td width="7%" align="center">是否叠加 </td>
        <td width="7%" align="center">子广告位数</td>
        <td width="7%" align="center">投放方式</td>
        <input id="selPositionIds" name="selPositionIds" type="hidden"  value="${selPositionIds}"/>
    </tr>
       
           <c:if test="${adPositionPackPage.dataList != null && fn:length(adPositionPackPage.dataList) > 0}">
                    <c:forEach items="${adPositionPackPage.dataList}" var="adPositionPackPageInfo" varStatus="pc">
               <tr <c:if test="${pc.index%2==1}">class="sec"</c:if>>
                   <td>
                   
                   
                       <input onclick="addIds(this);" <c:forEach items="${selPositionIdList}" var="ws2" >
                   <c:if test="${ws2==adPositionPackPageInfo.id}">
                    checked
                   </c:if>
                   </c:forEach> type="checkbox" value="${adPositionPackPageInfo.id}"  id="adPositionPackPageInfo.locationId" name="adPositionPackPageInfo.locationId"/>
                   </td>
                   <td>              
                   <input id="name_${adPositionPackPageInfo.id}"  type="hidden" value="${adPositionPackPageInfo.name}"/>                      
                       ${adPositionPackPageInfo.name}
                     </td>
                    <td>
                    <input id="code_${adPositionPackPageInfo.id}"  type="hidden" value="${adPositionPackPageInfo.code}"/>                       
                        ${adPositionPackPageInfo.code}
                    </td>
                    <td>                 
                    <input id="packageType_${adPositionPackPageInfo.id}"  type="hidden" value="${adPositionPackPageInfo.packageType}"/>                                  	    
                	    <c:choose>
								<c:when test="${adPositionPackPageInfo.packageType==0}">
												双向实时广告
								</c:when>
								<c:when test="${adPositionPackPageInfo.packageType==1}">
												双向实时请求广告
								</c:when>
								<c:when test="${adPositionPackPageInfo.packageType==2}">
												单向实时广告
								</c:when>
								<c:when test="${adPositionPackPageInfo.packageType==3}">
												单向非实时广告
								</c:when>
								<c:otherwise>
												未知类型
								</c:otherwise>
						</c:choose>
                   </td>
                   <td>                  
                   <input id="videoType_${adPositionPackPageInfo.id}"  type="hidden" value="${adPositionPackPageInfo.videoType}"/>                                   	    
                	    <c:choose>
								<c:when test="${adPositionPackPageInfo.videoType==0}">
												只支持标清
								</c:when>
								<c:when test="${adPositionPackPageInfo.videoType==1}">
												只支持高清
								</c:when>
								<c:when test="${adPositionPackPageInfo.videoType==2}">
												高清标清都支持
								</c:when>
								<c:otherwise>
												未知
								</c:otherwise>
						</c:choose>
                   </td>
                   <td>                   
                   <input id="isLoop_${adPositionPackPageInfo.id}"  type="hidden" value="${adPositionPackPageInfo.isLoop}"/>                              	    
                	    <c:choose>
								<c:when test="${adPositionPackPageInfo.isLoop==0}">
												否
								</c:when>				
								<c:otherwise>
												是
								</c:otherwise>
						</c:choose>
                   </td>
                   <td>                
                   <input id="isAdd_${adPositionPackPageInfo.id}"  type="hidden" value="${adPositionPackPageInfo.isAdd}"/>                               	    
                	    <c:choose>
								<c:when test="${adPositionPackPageInfo.isAdd==0}">
												否
								</c:when>				
								<c:otherwise>
												是
								</c:otherwise>
						</c:choose>
                   </td>
                   <td>                  
                   <input id="positionCount_${adPositionPackPageInfo.id}"  type="hidden" value="${adPositionPackPageInfo.positionCount}"/>                                           
                        ${adPositionPackPageInfo.positionCount}
                    </td>
                    <td>                  
                    <input id="deliveryMode_${adPositionPackPageInfo.id}"  type="hidden" value="${adPositionPackPageInfo.deliveryMode}"/>                                  	    
                	    <c:choose>
								<c:when test="${adPositionPackPageInfo.deliveryMode==0}">
												投放式
								</c:when>				
								<c:otherwise>
												请求式
								</c:otherwise>
						</c:choose>
                   </td>
               </tr>
           </c:forEach>
       </c:if>
        <tr>
   
		 <td colspan="9"><input type="button" value="选择" class="btn" onclick="selectPositin();"/>
          &nbsp;&nbsp;
          <input type="button" class="btn" value="返 回" onclick="cancle();" />
       		&nbsp;&nbsp;
					        <jsp:include page="../../common/page.jsp" flush="true" />
	</td>
	</tr>
   </table>
   
   </div>
</div>
</form>
</body>
</html>