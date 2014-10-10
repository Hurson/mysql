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
var resourcePath=$('#projetPath').val();
var areaChannels =  new Array();
window.onload = function() {
	//  areaChannels=eval(${areasJson});
	//$("#system-dialog").hide();
	/// refreshAreaList();
	showprecisetype()
	
	}
	//显示精准选择
	function showprecisetype()
	{
		if (document.getElementById("precise.precisetype").value==1)
		{
			document.getElementById("precisetype1").style.display="";
		}
		if (document.getElementById("precise.precisetype").value==2)
		{
			document.getElementById("precisetype2").style.display="";
		}
		if (document.getElementById("precise.precisetype").value==3)
		{
			document.getElementById("precisetype31").style.display="";
			//document.getElementById("precisetype31").style.display="";
			document.getElementById("precisetype32").style.display="";
			document.getElementById("precisetype33").style.display="";
			
			document.getElementById("precisetype34").style.display="";
		}
		if (document.getElementById("precise.precisetype").value==4)
		{
			document.getElementById("precisetype4").style.display="";
		}
		if (document.getElementById("precise.precisetype").value==5)
		{
			document.getElementById("precisetype5").style.display="";
		}
		if (document.getElementById("precise.precisetype").value==6)
		{
			document.getElementById("precisetype6").style.display="";
		}
		if (document.getElementById("precise.precisetype").value==7)
		{
			document.getElementById("precisetype7").style.display="";
		}
		if (document.getElementById("precise.precisetype").value==8)
		{
			document.getElementById("precisetype8").style.display="";
		}
		if (document.getElementById("precise.precisetype").value==9)
		{
			document.getElementById("precisetype31").style.display="";
		}
		if (document.getElementById("precise.precisetype").value==10)
		{
			document.getElementById("precisetype10").style.display="";
		}
	}
	//子窗口回调
	function addselectData(tag,dataid,dataname)
	{
		 var optionSelelct = document.getElementById(tag);
		
         optionSelelct.options.add(new Option(dataname,dataid));
        
		
	}
	function changetype()
	{
		var typetemp=document.getElementById("precise.precisetype").value;
		if (typetemp==2)
		{
			document.getElementById("precisetype2").style.display="";
			document.getElementById("precisetype4").style.display="none";
			document.getElementById("precisetype8").style.display="none";
			document.getElementById("precisetype1").style.display="none";
			document.getElementById("precisetype31").style.display="none";
			document.getElementById("precisetype32").style.display="none";
			document.getElementById("precisetype33").style.display="none";
			document.getElementById("precisetype34").style.display="none";
			document.getElementById("precisetype10").style.display="none";
			
		}
		if (typetemp==4)
		{
			document.getElementById("precisetype4").style.display="";
			document.getElementById("precisetype2").style.display="none";
			document.getElementById("precisetype8").style.display="none";
			document.getElementById("precisetype1").style.display="none";
			document.getElementById("precisetype31").style.display="none";
			document.getElementById("precisetype32").style.display="none";
			document.getElementById("precisetype33").style.display="none";
			document.getElementById("precisetype34").style.display="none";
			document.getElementById("precisetype10").style.display="none";
		}
		if (typetemp==8)
		{
			document.getElementById("precisetype8").style.display="";
			document.getElementById("precisetype4").style.display="none";
			document.getElementById("precisetype2").style.display="none";
			document.getElementById("precisetype1").style.display="none";
			document.getElementById("precisetype31").style.display="none";
			document.getElementById("precisetype32").style.display="none";
			document.getElementById("precisetype33").style.display="none";
			document.getElementById("precisetype34").style.display="none";
			document.getElementById("precisetype10").style.display="none";
		}
		if (typetemp==1)
		{
			document.getElementById("precisetype1").style.display="";
			document.getElementById("precisetype4").style.display="none";
			document.getElementById("precisetype2").style.display="none";
			document.getElementById("precisetype8").style.display="none";
			document.getElementById("precisetype31").style.display="none";
			document.getElementById("precisetype32").style.display="none";
			document.getElementById("precisetype33").style.display="none";
			document.getElementById("precisetype34").style.display="none";
			document.getElementById("precisetype10").style.display="none";
		}
		if (typetemp==3)
		{
			document.getElementById("precisetype31").style.display="";
			document.getElementById("precisetype32").style.display="";
			document.getElementById("precisetype33").style.display="";
			
			document.getElementById("precisetype34").style.display="";
			document.getElementById("precisetype4").style.display="none";
			document.getElementById("precisetype2").style.display="none";
			document.getElementById("precisetype8").style.display="none";
			document.getElementById("precisetype1").style.display="none";
			document.getElementById("precisetype10").style.display="none";
		}
		if (typetemp==9)
		{
			document.getElementById("precisetype31").style.display="";
			document.getElementById("precisetype32").style.display="none";
			document.getElementById("precisetype33").style.display="none";
			
			document.getElementById("precisetype34").style.display="none";
			document.getElementById("precisetype4").style.display="none";
			document.getElementById("precisetype2").style.display="none";
			document.getElementById("precisetype8").style.display="none";
			document.getElementById("precisetype1").style.display="none";
			document.getElementById("precisetype10").style.display="none";
		}
		if (typetemp==10)
		{
			document.getElementById("precisetype10").style.display="";
			document.getElementById("precisetype31").style.display="none";
			document.getElementById("precisetype32").style.display="none";
			document.getElementById("precisetype33").style.display="none";
			
			document.getElementById("precisetype34").style.display="none";
			document.getElementById("precisetype4").style.display="none";
			document.getElementById("precisetype2").style.display="none";
			document.getElementById("precisetype8").style.display="none";
			document.getElementById("precisetype1").style.display="none";
			
		}
	}
	/**
	 * 绑定广告位
	 */
	
	  function checkPrecise() {
 			var id = document.getElementById("precise.id").value;
 			var matchName = document.getElementById("precise.matchName").value;
 			//从哪获取Json
 		
          	$.ajax({
                type:"post",
                async : false,
                url:"<%=request.getContextPath()%>/page/precise/checkName.do?",
                data:{"precise.id":id,"precise.matchName":matchName},//Ajax传递的参数
                success:function(mess)
                {
                	if(mess=="0")// 如果获取的数据不为空
                    {
						
						document.getElementById("checkflag").value="0";
			       	//return false;
                    }
                    if(mess=="1")// 如果获取的数据不为空
                    {
						alert("精准名称已存在");
						document.getElementById("checkflag").value="1";
                    	//return false;
                    }
                },
                error:function(mess)
                {
                	alert("未知错误");
                }
            });
			//return true;
	 } 
	 
	//提交
    function submitForm(){ 
        if (validate()==false)
        	{
        	return false;
        	}
        checkPrecise();
        if (document.getElementById("checkflag").value=="1")
        	{
        	return false;	
        	}
        //window.returnValue = "";
       // alert(document.getElementById("saveForm"));
      //  alert(document.getElementById("saveForm").action);
       var optionSelelct = document.getElementById("precise.assetId");
       for (var i=0;i<optionSelelct.length;i++)
       	{
        	optionSelelct.options[i].selected=true;
       	}
       optionSelelct = document.getElementById("precise.productId");
       for (var i=0;i<optionSelelct.length;i++)
       	{
        	optionSelelct.options[i].selected=true;
       	}
       optionSelelct = document.getElementById("precise.assetKey");
       for (var i=0;i<optionSelelct.length;i++)
       	{
        	optionSelelct.options[i].selected=true;
       	}
       optionSelelct = document.getElementById("precise.assetSortId");
       for (var i=0;i<optionSelelct.length;i++)
       	{
        	optionSelelct.options[i].selected=true;
       	}
       optionSelelct = document.getElementById("precise.playbackChannelId");
       for (var i=0;i<optionSelelct.length;i++)
       	{
        	optionSelelct.options[i].selected=true;
       	}
       
       optionSelelct = document.getElementById("precise.lookbackCategoryId");
       for (var i=0;i<optionSelelct.length;i++)
       	{
        	optionSelelct.options[i].selected=true;
       	}
       optionSelelct = document.getElementById("precise.dtvChannelId");
       for (var i=0;i<optionSelelct.length;i++)
       	{
        	optionSelelct.options[i].selected=true;
       	}
       
         optionSelelct = document.getElementById("precise.userArea");
       for (var i=0;i<optionSelelct.length;i++)
       	{
        	optionSelelct.options[i].selected=true;
       	}
         optionSelelct = document.getElementById("precise.userindustrys");
       for (var i=0;i<optionSelelct.length;i++)
       	{
        	optionSelelct.options[i].selected=true;
       	}
         optionSelelct = document.getElementById("precise.userlevels");
       for (var i=0;i<optionSelelct.length;i++)
       	{
        	optionSelelct.options[i].selected=true;
       	}
         optionSelelct = document.getElementById("precise.groupId");
       for (var i=0;i<optionSelelct.length;i++)
       	{
        	optionSelelct.options[i].selected=true;
       	}
       
       
       document.getElementById("precise.precisetype").disabled="";
       
       // optionSelelct.options.add(new Option("文本","111"));
       document.getElementById("saveForm").submit();
        //window.location.href="<%=path %>/page/ploy/save.do?method=saveUpdatePloy";
    }

    function validate(){
        //输入框的边框变为红色
         if (validateSpecialCharacterAfter(document.getElementById("precise.matchName").value))
        {
        	//$("precise.matchName").style.borderColor="red";
        	return false;
        }
         if (isEmpty(document.getElementById("precise.matchName").value))
        {
        	alert("请输入精准名称");				
        	 return false;
        }
         if (document.getElementById("precise.precisetype").value==1)
		{
			
			if (document.getElementById("precise.productId").length==0)
			{
				alert("请选择回看产品");
				return false;
			}
		}
		if (document.getElementById("precise.precisetype").value==2)
		{
			
			if (document.getElementById("precise.assetKey").length==0)
			{
				alert("请添加影片关键字");
				return false;
			}
		}
		
		if (document.getElementById("precise.precisetype").value==4)
		{
			
			if (document.getElementById("precise.assetSortId").length==0)
			{
				alert("请选择影片类型");
				return false;
			}
		}
		if (document.getElementById("precise.precisetype").value==5)
		{
			
			if (document.getElementById("precise.playbackChannelId").length==0)
			{
				alert("请选择回放频道");
				return false;
			}
		}
		if (document.getElementById("precise.precisetype").value==6)
		{
			
			if (document.getElementById("precise.lookbackCategoryId").length==0)
			{
				alert("请选择回看栏目");
				return false;
			}
		}
		if (document.getElementById("precise.precisetype").value==7)
		{
			
			if (document.getElementById("precise.dtvChannelId").length==0)
			{
				alert("请选择频道");
				return false;
			}
		}
		if (document.getElementById("precise.precisetype").value==8)
		{
			if (document.getElementById("precise.assetId").length==0)
			{
				alert("请选择影片");
				return false;
			}
		}
		if (document.getElementById("precise.precisetype").value==10)
		{
			if (document.getElementById("precise.groupId").length==0)
			{
				alert("请选择频道组");
				return false;
			}
		}
      /**
        $("contentType").style.borderColor="red";
        $("select0").style.borderColor="red";
        $("time1").style.borderColor="red";
        $("time2").style.borderColor="red";
        $("remark").style.borderColor="red";
        //输入框后加错误提示信息
        $("name_error").className="error";
        $("name_error").innerHTML = "内容包名不能为空";
        $("contentType_error").className="error";
        $("contentType_error").innerHTML = "请选择内容类型";
        $("select0_error").className="error";
        $("select0_error").innerHTML = "请选择是否为电视剧";
        $("time1_error").className="error";
        $("time1_error").innerHTML = "生效日期不能为空";
        $("time2_error").className="error";
        $("time2_error").innerHTML = "失效日期不能为空";
        $("remark_error").className="error";
        $("remark_error").innerHTML = "备注中含有特殊字符";
        */
        return true;
    } 
	 function selectAsset()
	 {
		var	height = 550;
		var width=750;
		 var ployId = document.getElementById("ployId").value;
		var actinUrl = '<%=request.getContextPath()%>'+'/page/precise/queryAsset.do?ployId='+ployId;
		 var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px");
		if(modelWin){
			
		} 
	 }
	 function selectProduct()
	 {
		var	height = 550;
		var width=750;
		 var ployId = document.getElementById("ployId").value;
		 var productType = document.getElementById("product.type").value;
		 
		var actinUrl = '<%=request.getContextPath()%>'+'/page/precise/queryProduct.do?ployId='+ployId+'&product.type='+productType;
		 var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px");
		if(modelWin){
			
		} 
	 }
	 function addAssetKey()
	 {
		 		var	height = 150;
		var width=350;
		 var ployId = document.getElementById("ployId").value;
		var actinUrl = '<%=request.getContextPath()%>'+'/page/ploy/precise/addAssetKey.jsp?ployId='+ployId;
		 var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px");
		if(modelWin){
			
		} 
	 }
	  function selectAssetCategory()
	 {
		var	height = 550;
		var width=750;
		 var ployId = document.getElementById("ployId").value;
		 var categoryType = document.getElementById("assetCategory.type").value;
		var actinUrl = '<%=request.getContextPath()%>'+'/page/precise/queryAssetCategory.do?ployId='+ployId+'&assetCategory.type'+categoryType;
		 var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px");
		if(modelWin){
			
		} 
	 }
	    
	  function selectNpvn()
	 {
		 var	height = 550;
		var width=750;
		 var ployId = document.getElementById("ployId").value;
		var actinUrl = '<%=request.getContextPath()%>'+'/page/precise/queryNpvrChannel.do?ployId='+ployId;
		 var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px");
		if(modelWin){
			
		} 
	 }
	  function selectColumn()
	 {
		 var	height = 550;
		var width=750;
		 var ployId = document.getElementById("ployId").value;
		 var categoryType = document.getElementById("lookbackCategory.categoryType").value;
		var actinUrl = '<%=request.getContextPath()%>'+'/page/precise/queryLookBackColumn.do?ployId='+ployId+'&lookbackCategory.categoryType='+categoryType;
		 var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px");
		if(modelWin){
			
		} 
	 }
	 function selectChannel()
	 {
		var	height = 550;
		var width=750;
		var ployId = document.getElementById("ployId").value;
		var channelType = document.getElementById("channel.channelType").value;
		var actinUrl = '<%=request.getContextPath()%>'+'/page/precise/queryDTVChannel.do?ployId='+ployId+'&channel.channelType='+channelType;
		actinUrl = encodeURI(actinUrl);
		var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px");
		if(modelWin){
			
		} 
	 }
	 
	 function selectArea()
	 {
		var	height = 550;
		var width=750;
		var ployId = document.getElementById("ployId").value;
		var actinUrl = '<%=request.getContextPath()%>'+'/page/precise/queryReleaseArea.do?ployId='+ployId;
		var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px");
		if(modelWin){
			
		} 
	 }
	  function selectInd()
	 {
		var	height = 550;
		var width=750;
		var ployId = document.getElementById("ployId").value;
		var actinUrl = '<%=request.getContextPath()%>'+'/page/precise/queryUserIndustry.do?ployId='+ployId;
		var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px");
		if(modelWin){
			
		} 
	 }
	   function selectLevel()
	 {
		var	height = 550;
		var width=750;
		var ployId = document.getElementById("ployId").value;
		var actinUrl = '<%=request.getContextPath()%>'+'/page/precise/queryUserRank.do?ployId='+ployId;
		var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px");
		if(modelWin){
			
		} 
	 }
	 function selectChannelGroup()
	 {
		 var	height = 550;
		var width=750;
		var ployId = document.getElementById("ployId").value;
		var actinUrl = '<%=request.getContextPath()%>'+'/page/precise/queryChannelGroup.do?ployId='+ployId;
		var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px");
		if(modelWin){
			
		} 
	 }
	 function delSelected(objectid)
	 {
		 var code;
	    if (document.all) { //判断是否是IE浏览器
	        code = window.event.keyCode;
	    } else {
	        code = arguments.callee.caller.arguments[0].which;
	    }
	   var optionSelelct = document.getElementById(objectid);
	   var indexs="";
	   var index;
	   //空格键删除已选 
      if (code==13)
     {
		   for (var i=0;i<optionSelelct.length;i++)
	       	{
	        	if (optionSelelct.options[i].selected==true)
	        	{
	        		indexs=indexs+i+",";
	        		//optionSelelct.remove(i);
	        	}	        	
	       	}
		   if (indexs!='')
		   {
			   index = indexs.split(",");
			  // alert(index.length-1);
				for (var i=index.length-2;i>-1;i--)
		       	{
				    	optionSelelct.remove(index[i]);
		       	}
		   }
		   
	  }
	  // alert(indexs);
	   // alert(index);
	   
	    //alert("ggg:"+objectid+"lll");
    	//var character = String.fromCharCode(code);
	 }
	 function selectOptionVal(){
	 }
</script>

<style>
	.easyDialog_wrapper{ width:800px;height:580px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
</head>

<body class="mainBody">
<form action="<%=path %>/page/precise/saveOrUpdate.do" method="post" id="saveForm">
<input id="checkflag" name="checkflag" type="hidden" value="0"/>
<input id="projetPath" type="hidden" value="<%=path%>"/>
<div class="detail">
    <table cellspacing="1" class="content" align="left">
        <tr class="title">
            <td colspan="4">精准编辑 </td>
        </tr>
		 <tr >
            <td width="10%" align="right"><span class="required">*</span>精准名称：</td>
            <td width="25%">
                <input id="precise.id" name="precise.id" type="hidden" value="${precise.id}"/>
                <input id="precise.ployId" name="precise.ployId" type="hidden" value="${precise.ployId}"/>
                <input type="hidden" id="product.type" name="product.type" value="${product.type}"/>
	  			<input type="hidden" id="channel.channelType" name="channel.channelType" value="${channel.channelType}"/>
	 			<input type="hidden" id="lookbackCategory.categoryType" name="lookbackCategory.categoryType" value="${lookbackCategory.categoryType}"/>
				<input type="hidden" id="assetCategory.type" name="assetCategory.type" value="${assetCategory.type}"/>
	 
	 
           
                 <input type="hidden" id="positionId" name="positionId" value="${positionId}"/>
	     	<input type="hidden" id="ployId" name="ployId" value="${ployId}"/>
             
                <input onkeypress="return validateSpecialCharacter();" maxlength="30" id="precise.matchName" name="precise.matchName" type="text" style="width: 70%" value="${precise.matchName}"/>
                <span id="name_error" ></span>
            </td>
			<td width="10%" align="right" ><span class="required">*</span>优先级：</td>
            <td width="25%">
             <select id="precise.priority" name="precise.priority" >
             <option value="1" <c:if test="${precise.priority==1 }">
             					selected
             					</c:if>
             >1</option>
               <option value="2" <c:if test="${precise.priority==2 }">
             					selected
             					</c:if>
             					>2</option>
                 <option value="3" <c:if test="${precise.priority==3 }">
             					selected
             					</c:if>>3</option>
                   <option value="4" <c:if test="${precise.priority==4 }">
             					selected
             					</c:if>>4</option>
                     <option value="5" <c:if test="${precise.priority==5 }">
             					selected
             					</c:if>>5</option>
                       <option value="6" <c:if test="${precise.priority==6 }">
             					selected
             					</c:if>>6</option>
                         <option value="7" <c:if test="${precise.priority==7 }">
             					selected
             					</c:if>>7</option>
                           <option value="8" <c:if test="${precise.priority==8 }">
             					selected
             					</c:if>>8</option>
                             <option value="9" <c:if test="${precise.priority==9 }">
             					selected
             					</c:if>>9</option>
             </select>
            </td>
           
        </tr>    
        	 <tr >
            <td width="10%" align="right"><span class="required">*</span>精准类型：
            </td>
            <td width="25%" colspan="3">
               <select id="precise.precisetype"  onchange="changetype();" name="precise.precisetype" >
           		 <c:forEach items="${preciseTypeList}" var="preciseTypeinfo" varStatus="pl">
				
				    <option value="${preciseTypeinfo}" <c:if test="${precise.precisetype==preciseTypeinfo }">
             					selected
             					</c:if>>
				    <c:if test="${preciseTypeinfo==1}">
					按产品
					</c:if>
					<c:if test="${preciseTypeinfo==2}">
					按影片关键字
					</c:if>
					<c:if test="${preciseTypeinfo==3}">
						按受众
					</c:if>
					<c:if test="${preciseTypeinfo==4}">
						按影片分类
					</c:if>
					<c:if test="${preciseTypeinfo==5}">
						按回放频道
					</c:if>
					<c:if test="${preciseTypeinfo==6}">
						按回看栏目
					</c:if>
					<c:if test="${preciseTypeinfo==7}">
						按频道
					</c:if>
					<c:if test="${preciseTypeinfo==8}">
					按影片
					</c:if>
				   <c:if test="${preciseTypeinfo==9}">
					按区域
					</c:if>
					 <c:if test="${preciseTypeinfo==10}">
					按频道分组
					</c:if>
				    </option>
				   <!-- 
				    <c:if test="${preciseTypeinfo==1}">
						<option value="${preciseTypeinfo}" >按回看产品</option>
					</c:if>
					<c:if test="${preciseTypeinfo==2}">
						<option value="${preciseTypeinfo}">按影片关键字</option>
					</c:if>
					<c:if test="${preciseTypeinfo==3}">
						<option value="${preciseTypeinfo}">按受众</option>
					</c:if>
					<c:if test="${preciseTypeinfo==4}">
						<option value="${preciseTypeinfo}">按影片分类</option>
					</c:if>
					<c:if test="${preciseTypeinfo==5}">
						<option value="${preciseTypeinfo}">按回放频道</option>
					</c:if>
					<c:if test="${preciseTypeinfo==6}">
						<option value="${preciseTypeinfo}">按回看栏目</option>
					</c:if>
					<c:if test="${preciseTypeinfo==7}">
						<option value="${preciseTypeinfo}">按频道</option>
					</c:if>
					<c:if test="${preciseTypeinfo==8}">
						<option value="${preciseTypeinfo}" <c:if test="${precise.precisetype==8 }">
             					selected
             					</c:if>
             					>按影片</option>
					</c:if>
				 -->  	
                 </c:forEach>   
           	
             </select>
            </td>
            
			
             
        </tr>    
		<tr id="precisetype1" style="display: none">
		
		    <td align="right">已选产品：</td>
            <td colspan="3" >
          <div  style="FLOAT:left;width:30%;height:100px；">
            <select id="precise.productId" name="precise.productId" multiple="MULTIPLE" style="width:100%;height:100px" ondblclick="this.remove(this.selectedIndex);">
				 <c:forEach items="${productList}" var="selectProduct" varStatus="pl">
				 	<c:forEach items="${pageProduct.dataList}" var="productinfo" varStatus="pl">
                       <c:if test="${selectProduct==productinfo.id}">
						<option value="${selectProduct}">${productinfo.productName}</option>
						</c:if>
                       </c:forEach>
                    </c:forEach>   
					
			</select>
                <span id="remark_error" ></span>
                </div>  
                <div  style="FLOAT:left;">
                <input  type="button" class="btn" value="选择" onclick="selectProduct();"/>&nbsp;&nbsp;&nbsp;&nbsp;
             </div>
             </td>
          
       	</tr>
       	
        <tr id="precisetype2" style="display: none">
		
		    <td align="right">关键字：</td>
            <td colspan="3" style="vertical-align: middle">
            <div  style="FLOAT:left;width:30%;height:100px；">
            <select id="precise.assetKey" name="precise.assetKey" multiple="MULTIPLE" style="width:100%;height:100px" ondblclick="this.remove(this.selectedIndex);">
				 <c:forEach items="${assetKeyList}" var="selectAsset" varStatus="pl">
				 		<option value="${selectAsset}">${selectAsset}</option>
				     </c:forEach>   
					
			</select>
           </div>    
             <div  style="FLOAT:left;">
                 <input type="button" class="btn" value="添加" onclick="addAssetKey();"/>&nbsp;&nbsp;&nbsp;&nbsp;
         </div>
            </td>
             
       	</tr>
       	<tr  id="precisetype31" style="display: none">
		
		    <td align="right">用户区域：</td>
            <td colspan="3">
            <div  style="FLOAT:left;width:30%;height:100px；">
            <select id="precise.userArea" name="precise.userArea" multiple="MULTIPLE" style="width:100%;height:100px" ondblclick="this.remove(this.selectedIndex);">
				 <c:forEach items="${releaseAreaList}" var="selectArea" varStatus="pl">
				 	<c:forEach items="${pagereleaseArea.dataList}" var="areainfo" varStatus="pl">
                       <c:if test="${selectArea==areainfo.id}">
						<option value="${selectArea}">${areainfo.areaName}</option>
						</c:if>
                       </c:forEach>
                    </c:forEach>   
					
			</select>
                <span id="remark_error" ></span>
                </div>  
                <div  style="FLOAT:left;">
                <input  type="button" class="btn" value="选择" onclick="selectArea();"/>&nbsp;&nbsp;&nbsp;&nbsp;
             </div>
            </td>
             
       	</tr>
      
      
      <tr id="precisetype32" style="display:none ">
		
		    <td align="right">用户行业：</td>
            <td colspan="3">
            <div  style="FLOAT:left;width:30%;height:100px；">
            <select id="precise.userindustrys" name="precise.userindustrys" multiple="MULTIPLE" style="width:100%;height:100px" ondblclick="this.remove(this.selectedIndex);">
				 <c:forEach items="${userIndustryList}" var="selectInd" varStatus="pl">
				 	<c:forEach items="${pageuserIndustry.dataList}" var="indinfo" varStatus="pl">
                       <c:if test="${selectInd==indinfo.id}">
						<option value="${selectInd}">${indinfo.userIndustryCategoryValue}</option>
						</c:if>
                       </c:forEach>
                    </c:forEach>   
					
			</select>
                <span id="remark_error" ></span>
                </div>  
                <div  style="FLOAT:left;">
                <input  type="button" class="btn" value="选择" onclick="selectInd();"/>&nbsp;&nbsp;&nbsp;&nbsp;
             </div>
            </td>
             
       	</tr>
      
      
       	<tr id="precisetype33" style="display: none">
		
		    <td align="right">用户级别：</td>
            <td colspan="3">
            <div  style="FLOAT:left;width:30%;height:100px；">
            <select id="precise.userlevels" name="precise.userlevels" multiple="MULTIPLE" style="width:100%;height:100px" ondblclick="this.remove(this.selectedIndex);">
				     <c:forEach items="${userRankList}" var="selectlevel" varStatus="pl">
				 	<c:forEach items="${pageuserRank.dataList}" var="levelinfo" varStatus="pl">
                       <c:if test="${selectlevel==levelinfo.id}">
						<option value="${selectlevel}">${levelinfo.userRankName}</option>
						</c:if>
                       </c:forEach>
                    </c:forEach>   
					
			</select>
                <span id="remark_error" ></span>
                </div>  
                <div  style="FLOAT:left;">
                <input  type="button" class="btn" value="选择" onclick="selectLevel();"/>&nbsp;&nbsp;&nbsp;&nbsp;
             </div>
            </td>
             
       	</tr>
      
      
       	<tr id="precisetype34" style="display: none">
		
		    <td align="right">TVN号段：</td>
            <td colspan="1">
             <input id="precise.tvnNumber" name="precise.tvnNumber" type="text" style="width: 70%" value="${precise.tvnNumber}"/>
                <span id="name_error" ></span>
            </td>
            <td align="right"></td>
            <td colspan="1">
            
            </td> 
       	</tr>      	
       	<tr id="precisetype4" style="display: none">
		
		    <td align="right">已选影片分类：</td>
            <td colspan="3">
           
           <div  style="FLOAT:left;width:30%;height:100px；">
            <select id="precise.assetSortId" name="precise.assetSortId" multiple="MULTIPLE" style="width:100%;height:100px" ondblclick="this.remove(this.selectedIndex);">
				 <c:forEach items="${assetCategoryList}" var="selectAssetCategory" varStatus="pl">
				 	<c:forEach items="${pageAssetCategory.dataList}" var="assetCategoryinfo" varStatus="pl">
                       <c:if test="${selectAssetCategory==assetCategoryinfo.id}">
						<option value="${selectAssetCategory}">${assetCategoryinfo.categoryName}</option>
						</c:if>
                       </c:forEach>
                    </c:forEach>   
					
			</select>
                <span id="remark_error" ></span>
           </div>
           <div  style="FLOAT:left;">
                <input type="button" class="btn" value="选择" onclick="selectAssetCategory();"/>&nbsp;&nbsp;&nbsp;&nbsp;
                </div>
            </td>
             
       	</tr>
       	<tr id="precisetype5" style="display: none">
		
		    <td align="right">已选回放频道：</td>
            <td colspan="3">
            <div  style="FLOAT:left;width:30%;height:100px；">
            <select id="precise.playbackChannelId" name="precise.playbackChannelId" multiple="MULTIPLE" style="width:100%;height:100px" ondblclick="this.remove(this.selectedIndex);">
				 <c:forEach items="${npvrChannelList}" var="selectNpvr" varStatus="pl">
				 	<c:forEach items="${pageNpvrChannel.dataList}" var="Npvrinfo" varStatus="pl">
                       <c:if test="${selectNpvr==Npvrinfo.serviceId}">
						<option value="${selectNpvr}">${Npvrinfo.channelName}</option>
						</c:if>
                       </c:forEach>
                    </c:forEach>   
					
			</select>
                <span id="remark_error" ></span>
            </div>
            <div  style="FLOAT:left;">
                <input type="button" class="btn" value="选择" onclick="selectNpvn();"/>&nbsp;&nbsp;&nbsp;&nbsp;
                </div>
            </td>
             
       	</tr>
       	<tr id="precisetype6" style="display: none">
		
		    <td align="right">已选回看栏目：</td>
            <td colspan="3">
           <div  style="FLOAT:left;width:30%;height:100px；">
            <select id="precise.lookbackCategoryId" name="precise.lookbackCategoryId" multiple="MULTIPLE" style="width:100%;height:100px" ondblclick="this.remove(this.selectedIndex);">
				 <c:forEach items="${lookbackCategoryList}" var="selectLookBack" varStatus="pl">
				 	<c:forEach items="${pageLookBackColumn.dataList}" var="lookBackinfo" varStatus="pl">
                       <c:if test="${selectLookBack==lookBackinfo.id}">
						<option value="${selectLookBack}">${lookBackinfo.categoryName}</option>
						</c:if>
                       </c:forEach>
                    </c:forEach>   
					
			</select>
                <span id="remark_error" ></span>
           </div>
           <div  style="FLOAT:left;">
                <input type="button" class="btn" value="选择" onclick="selectColumn();"/>&nbsp;&nbsp;&nbsp;&nbsp;
                </div>
            </td>
             
       	</tr>
       	<tr id="precisetype7" style="display: none">
		
		    <td align="right">已选频道：</td>
            <td colspan="3">
            <div  style="FLOAT:left;width:30%;height:100px；">
            <select id="precise.dtvChannelId" name="precise.dtvChannelId" multiple="MULTIPLE" style="width:100%;height:100px" ondblclick="this.remove(this.selectedIndex);">
				 <c:forEach items="${channelList}" var="selectChannel" varStatus="pl">
				 	<c:forEach items="${pageChannel.dataList}" var="channelinfo" varStatus="pl">
                       <c:if test="${selectChannel==channelinfo.serviceId}">
						<option value="${selectChannel}">${channelinfo.channelName}</option>
						</c:if>
                       </c:forEach>
                    </c:forEach>   
					
			</select>
                <span id="remark_error" ></span>
            </div>
            <div  style="FLOAT:left;">
                <input type="button" class="btn" value="选择" onclick="selectChannel();"/>&nbsp;&nbsp;&nbsp;&nbsp;
                </div>
            </td>
             
       	</tr>
       	<tr id="precisetype8" style="display: none">
		
		    <td align="right">已选影片：</td>
            <td colspan="3">
           <div  style="FLOAT:left;width:30%;height:100px；">
              <select id="precise.assetId" name="precise.assetId" multiple="MULTIPLE" style="width:100%;height:100px" ondblclick="this.remove(this.selectedIndex);">
				 <c:forEach items="${assetList}" var="selectAsset" varStatus="pl">
				 	<c:forEach items="${pageAsset.dataList}" var="assetinfo" varStatus="pl">
                       <c:if test="${selectAsset==assetinfo.id}">
						<option value="${selectAsset}">${assetinfo.assetName}</option>
						</c:if>
                       </c:forEach>
                    </c:forEach>   
					
			</select>
                <span id="remark_error" ></span>
           </div>
           <div  style="FLOAT:left;">
                <input type="button" class="btn" value="选择" onclick="selectAsset();"/>&nbsp;&nbsp;&nbsp;&nbsp;
                </div>
            </td>
             
       	</tr>
        	<tr id="precisetype10" style="display: none">
		
		    <td align="right">已选频道分组：</td>
            <td colspan="3">
            <div  style="FLOAT:left;width:30%;height:100px；">
            <select id="precise.groupId" name="precise.groupId" multiple="MULTIPLE" style="width:100%;height:100px" ondblclick="this.remove(this.selectedIndex);" onkeypress="delSelected(this.id);">
				 <c:forEach items="${channelGroupList}" var="selectChannelGroup" varStatus="pl">
				 	<c:forEach items="${pageChannelGroup.dataList}" var="groupinfo" varStatus="pl">
                       <c:if test="${selectChannelGroup==groupinfo.id}">
						<option value="${selectChannelGroup}">${groupinfo.name}</option>
						</c:if>
                       </c:forEach>
                    </c:forEach>   
					
			</select>
                <span id="remark_error" ></span>
            </div>
            <div  style="FLOAT:left;">
                <input type="button" class="btn" value="选择" onclick="selectChannelGroup();"/>&nbsp;&nbsp;&nbsp;&nbsp;
                </div>
            </td>
             
       	</tr>
       	
       <!--  <tr >
            <td align="right"><span class="required">*</span>区域：</td>
            <td colspan="3">
             <input id="areas" name="areas" type="hidden" value="${areas}"/>
                <input id="selectAreas"type="button" class="btn" value="选择区域" />
            </td>
        </tr>       
		 -->
		
    </table>
    <div align="center" class="action">
        <input type="button" class="btn" value="保 存" onclick="submitForm();"/>&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" class="btn" value="取 消" onclick="history.back(-1);"/>
    </div>
</div>
</form>
</body>
</html>