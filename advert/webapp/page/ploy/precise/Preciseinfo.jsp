<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />
<link href="<%=path%>/css/Pager.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right_new1.css" type="text/css" />

<link id="maincss"  rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" media="all"/>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
	
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/util.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/tools.js"></script>
<title>广告系统</title>
<script type="text/javascript">

</script>

<style>
	.easyDialog_wrapper{ width:800px;height:580px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
</head>

<body class="page" style="background-color: #EFF7FB;">
<form action="<%=path %>/page/precise/saveOrUpdate.do" method="post" id="saveForm">
<div style="overflow-Y:scroll ; height: 155px;width: 260px;" style="background-color: #EFF7FB;">
    <table cellspacing="1" class="content" align="left" style="background-color: #EFF7FB;">
      <c:if test="${channelList != null && fn:length(channelList) > 0}">
       	<tr id="precisetype7" style="background-color: #EFF7FB;">
       	<td style="width:20px;height:150px；" style="background-color: #EFF7FB;">
       	   频道
       	</td>
		   <td style="width:100%;height:150px；" style="background-color: #EFF7FB;">
            <div  style="FLOAT:left;width:100%;height:150px；" style="background-color: #EFF7FB;">
            <select id="precise.dtvChannelId" name="precise.dtvChannelId" multiple="MULTIPLE" style="width:100%;height:150px" ondblclick="this.remove(this.selectedIndex);">
				 <c:forEach items="${channelList}" var="selectChannel" varStatus="pl">
				 	<c:forEach items="${pageChannel.dataList}" var="channelinfo" varStatus="pl">
                       <c:if test="${selectChannel==channelinfo.serviceId}">
						<option value="${selectChannel}">${channelinfo.channelName}</option>
						</c:if>
                       </c:forEach>
                    </c:forEach>   
					
			</select>           
            </div>
            </td>
             
       	</tr>
      </c:if>
      
        <c:if test="${productList != null && fn:length(productList) > 0}">
       	<tr id="precisetype7" style="background-color: #EFF7FB;">
		       	<td style="width:20px;height:150px；" style="background-color: #EFF7FB;">
       	 产品
       	</td> <td style="width:100%;height:150px；" style="background-color: #EFF7FB;">
            <div  style="FLOAT:left;width:100%;height:150px；" style="background-color: #EFF7FB;">
            <select id="precise.productId" name="precise.productId" multiple="MULTIPLE" style="width:100%;height:150px" ondblclick="this.remove(this.selectedIndex);">
				 <c:forEach items="${productList}" var="selectProduct" varStatus="pl">
				 	<c:forEach items="${pageProduct.dataList}" var="productinfo" varStatus="pl">
                       <c:if test="${selectProduct==productinfo.id}">
						<option value="${selectProduct}">${productinfo.productName}</option>
						</c:if>
                       </c:forEach>
                    </c:forEach>   
			</select>           
            </div>
            </td>
             
       	</tr>
      </c:if>
      
        <c:if test="${assetKeyList != null && fn:length(assetKeyList) > 0}">
       	<tr id="precisetype7" style="background-color: #EFF7FB;">
		      	<td style="width:20px;height:150px；" style="background-color: #EFF7FB;">
       	  关键字
       	</td>  <td style="width:100%;height:150px；" style="background-color: #EFF7FB;">
            <div  style="FLOAT:left;width:100%;height:150px；" style="background-color: #EFF7FB;">
            <select id="precise.dtvChannelId" name="precise.dtvChannelId" multiple="MULTIPLE" style="width:100%;height:150px" ondblclick="this.remove(this.selectedIndex);">
				 <c:forEach items="${assetKeyList}" var="selectAsset" varStatus="pl">
				 		<option value="${selectAsset}">${selectAsset}</option>
				     </c:forEach>  
					
			</select>           
            </div>
            </td>
             
       	</tr>
      </c:if>
      <!--  -->
      <c:if test="${releaseAreaList != null && fn:length(releaseAreaList) > 0}">
       	<tr id="precisetype7" style="background-color: #EFF7FB;">
		      	<td style="width:20px;height:150px；" style="background-color: #EFF7FB;">
       	 区域
       	</td>  <td style="width:100%;height:150px；" style="background-color: #EFF7FB;">
            <div  style="FLOAT:left;width:100%;height:150px；" style="background-color: #EFF7FB;">
            <select id="precise.dtvChannelId" name="precise.dtvChannelId" multiple="MULTIPLE" style="width:100%;height:150px" ondblclick="this.remove(this.selectedIndex);">
			 <c:forEach items="${releaseAreaList}" var="selectArea" varStatus="pl">
				 	<c:forEach items="${pagereleaseArea.dataList}" var="areainfo" varStatus="pl">
                       <c:if test="${selectArea==areainfo.id}">
						<option value="${selectArea}">${areainfo.areaName}</option>
						</c:if>
                       </c:forEach>
                    </c:forEach>       
					
			</select>           
            </div>
            </td>
             
       	</tr>
      </c:if>
          <c:if test="${userIndustryList != null && fn:length(userIndustryList) > 0}">
       	<tr id="precisetype7" style="background-color: #EFF7FB;">
		      	<td style="width:20px;height:150px；" style="background-color: #EFF7FB;">
       	  行业
       	</td>  <td style="width:100%;height:150px；" style="background-color: #EFF7FB;">
            <div  style="FLOAT:left;width:100%;height:150px；" style="background-color: #EFF7FB;">
            <select id="precise.dtvChannelId" name="precise.dtvChannelId" multiple="MULTIPLE" style="width:100%;height:150px" ondblclick="this.remove(this.selectedIndex);">
			 <c:forEach items="${userIndustryList}" var="selectInd" varStatus="pl">
				 	<c:forEach items="${pageuserIndustry.dataList}" var="indinfo" varStatus="pl">
                       <c:if test="${selectInd==indinfo.id}">
						<option value="${selectInd}">${indinfo.userIndustryCategoryValue}</option>
						</c:if>
                       </c:forEach>
                    </c:forEach>   
					
			</select>           
            </div>
            </td>
             
       	</tr>
      </c:if>
          <c:if test="${userRankList != null && fn:length(userRankList) > 0}">
       	<tr id="precisetype7" style="background-color: #EFF7FB;">
		      	<td style="width:20px;height:150px；" style="background-color: #EFF7FB;">
       	  级别
       	</td>  <td style="width:100%;height:150px；" style="background-color: #EFF7FB;">
            <div  style="FLOAT:left;width:100%;height:150px；" style="background-color: #EFF7FB;">
            <select id="precise.dtvChannelId" name="precise.dtvChannelId" multiple="MULTIPLE" style="width:100%;height:150px" ondblclick="this.remove(this.selectedIndex);">
			    <c:forEach items="${userRankList}" var="selectlevel" varStatus="pl">
				 	<c:forEach items="${pageuserRank.dataList}" var="levelinfo" varStatus="pl">
                       <c:if test="${selectlevel==levelinfo.id}">
						<option value="${selectlevel}">${levelinfo.userRankName}</option>
						</c:if>
                       </c:forEach>
                    </c:forEach>   
			</select>           
            </div>
            </td>
             
       	</tr>
      </c:if>
        <c:if test="${precise.tvnNumber != null && precise.tvnNumber!=''}">
       	<tr id="precisetype7" style="background-color: #EFF7FB;">
		       	<td style="width:20px;height:150px；" style="background-color: #EFF7FB;">
       	  TVN
       	</td> <td style="width:100%;height:150px；" style="background-color: #EFF7FB;">
          ${precise.tvnNumber}
            </td>
             
       	</tr>
      </c:if>
           <!--  -->
        <c:if test="${assetCategoryList != null && fn:length(assetCategoryList) > 0}">
       	<tr id="precisetype7" style="background-color: #EFF7FB;">
		       	<td style="width:20px;height:150px；" style="background-color: #EFF7FB;">
       	  分类
       	</td> <td style="width:100%;height:150px；" style="background-color: #EFF7FB;">
            <div  style="FLOAT:left;width:100%;height:150px；" style="background-color: #EFF7FB;">
            <select id="precise.dtvChannelId" name="precise.dtvChannelId" multiple="MULTIPLE" style="width:100%;height:150px" ondblclick="this.remove(this.selectedIndex);">
				 <c:forEach items="${assetCategoryList}" var="selectAssetCategory" varStatus="pl">
				 	<c:forEach items="${pageAssetCategory.dataList}" var="assetCategoryinfo" varStatus="pl">
                       <c:if test="${selectAssetCategory==assetCategoryinfo.id}">
						<option value="${selectAssetCategory}">${assetCategoryinfo.categoryName}</option>
						</c:if>
                       </c:forEach>
                    </c:forEach>    
					
			</select>           
            </div>
            </td>
             
       	</tr>
      </c:if>
      
        <c:if test="${npvrChannelList != null && fn:length(npvrChannelList) > 0}">
       	<tr id="precisetype7" style="background-color: #EFF7FB;">
		      	<td style="width:20px;height:150px；" style="background-color: #EFF7FB;">
       	  频道
       	</td>  <td style="width:100%;height:150px；" style="background-color: #EFF7FB;">
            <div  style="FLOAT:left;width:100%;height:150px；" style="background-color: #EFF7FB;">
            <select id="precise.dtvChannelId" name="precise.dtvChannelId" multiple="MULTIPLE" style="width:100%;height:150px" ondblclick="this.remove(this.selectedIndex);">
				 <c:forEach items="${npvrChannelList}" var="selectNpvr" varStatus="pl">
				 	<c:forEach items="${pageNpvrChannel.dataList}" var="Npvrinfo" varStatus="pl">
                       <c:if test="${selectNpvr==Npvrinfo.serviceId}">
						<option value="${selectNpvr}">${Npvrinfo.channelName}</option>
						</c:if>
                       </c:forEach>
                    </c:forEach>    
					
			</select>           
            </div>
            </td>
             
       	</tr>
      </c:if>
      
        <c:if test="${lookbackCategoryList != null && fn:length(lookbackCategoryList) > 0}">
       	<tr id="precisetype7" style="background-color: #EFF7FB;">
		     	<td style="width:20px;height:150px；" style="background-color: #EFF7FB;">
       	   栏目
       	</td> <td style="width:100%;height:150px；" style="background-color: #EFF7FB;">
            <div  style="FLOAT:left;width:100%;height:150px；" style="background-color: #EFF7FB;">
            <select id="precise.lookbackCategoryId" name="precise.lookbackCategoryId" multiple="MULTIPLE" style="width:100%;height:150px" ondblclick="this.remove(this.selectedIndex);">
					 <c:forEach items="${lookbackCategoryList}" var="selectLookBack" varStatus="pl">
				 	<c:forEach items="${pageLookBackColumn.dataList}" var="lookBackinfo" varStatus="pl">
                       <c:if test="${selectLookBack==lookBackinfo.id}">
						<option value="${selectLookBack}">${lookBackinfo.categoryName}</option>
						</c:if>
                       </c:forEach>
                    </c:forEach>   
					
			</select>           
            </div>
            </td>
             
       	</tr>
      </c:if>
      
        <c:if test="${assetList != null && fn:length(assetList) > 0}">
       	<tr id="precisetype7" style="background-color: #EFF7FB;">
		   	<td style="width:20px;height:150px；" style="background-color: #EFF7FB;">
       	   影片
       	</td>
		   <td style="width:;height:150px；" style="background-color: #EFF7FB;">
            <div  style="FLOAT:left;width:100%;height:150px；" style="background-color: #EFF7FB;">
            <select id="precise.dtvChannelId" name="precise.dtvChannelId" multiple="MULTIPLE" style="width:100%;height:150px" ondblclick="this.remove(this.selectedIndex);">
				 <c:forEach items="${assetList}" var="selectAsset" varStatus="pl">
				 	<c:forEach items="${pageAsset.dataList}" var="assetinfo" varStatus="pl">
                       <c:if test="${selectAsset==assetinfo.id}">
						<option value="${selectAsset}">${assetinfo.assetName}</option>
						</c:if>
                       </c:forEach>
                    </c:forEach>      
					
			</select>           
            </div>
            </td>
             
       	</tr>
      </c:if>
    
    
     <c:if test="${channelGroupList != null && fn:length(channelGroupList) > 0}">
       	<tr id="precisetype7" style="background-color: #EFF7FB;">
		      	<td style="width:20px;height:150px；" style="background-color: #EFF7FB;">
       	  频道分组
       	</td>  <td style="width:100%;height:150px；" style="background-color: #EFF7FB;">
            <div  style="FLOAT:left;width:100%;height:150px；" style="background-color: #EFF7FB;">
            <select id="precise.groupId" name="precise.groupId" multiple="MULTIPLE" style="width:100%;height:150px" ondblclick="this.remove(this.selectedIndex);">
				 <c:forEach items="${channelGroupList}" var="selectGroup" varStatus="pl">
				 	<c:forEach items="${pageChannelGroup.dataList}" var="groupinfo" varStatus="pl">
                       <c:if test="${selectGroup==groupinfo.id}">
						<option value="${selectGroup}">${groupinfo.name}</option>
						</c:if>
                       </c:forEach>
                    </c:forEach>    
					
			</select>           
            </div>
            </td>
             
       	</tr>
      </c:if>
    </table>   
</div>
</form>
</body>
</html>