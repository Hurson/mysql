<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link id="maincss"  rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" media="all"/>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
	
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/util.js"></script>
<title>无标题文档</title>
</head>
<script type="text/javascript">

var j =${fn:length(ploy.ployDetailList)};

	window.onload = function() {
		var status = $("#ploy_state").val();
		if(status == 4){
			$("#saveBtn").attr('disabled',"true");
		}
		changePosition('${ploy.dposition.positionCode}');
		initItem(); 
	}
		
	/**
	 * 绑定广告位
	 */
    function submitForm(status){
      
       document.getElementById("saveForm").action="<%=path %>/dploy/auditDPloy.action?ploy.status="+status;
       document.getElementById("saveForm").submit();
     
    }

    function validate(){
        
        if(document.getElementById("ploy.ployName").value==null || ''==document.getElementById("ploy.ployName").value ){
			alert('请输入策略名称');
			document.getElementById("ploy.ployName").focus();
			return false ;
		}
        if(document.getElementById("ploy.ployName").value.indexOf("_") != -1){
        	alert('策略名称不能含有下划线  ');
			document.getElementById("ploy.ployName").focus();
			return false ;
        }
        if(validateSpecialCharacterAfter(document.getElementById("ploy.ployName").value)){
			alert('不能输入特殊字符');
			document.getElementById("ploy.ployName").value="";
		    document.getElementById("ploy.ployName").focus();
			return false ;
     	}
        
        if(document.getElementById("ploy.positionCode").value==null || ''==document.getElementById("ploy.positionCode").value){
			alert('请选择广告位');
			return false ;
		}
		var result = true;
        var times = $("#table2 input[type=text]");
		var start='0';
        times.each(function(m, out){
        	if(out.value==''){
        		alert("开始时间或结束时间不能为空！");
        		out.focus();
        		result = false;
        		return result;
        	}
        	
        	if(m%2==0){
        		start = out.value;
        	}else{
        		if(start >= out.value){
        			alert("结束时间要大于开始时间！");
        			out.focus();
        			result = false;
        			return result;
        		}
        		
        		times.each(function(n, int){
        			if(n== m){
        				return true;
        			}
        			if(int.value >start && int.value < out.value){
        				alert((n%2==0?"开始":"结束")+"时间选择有冲突,请重新选择！");
        				int.focus();
        				result = false;
        				return result;
        			}
        		});
        		return result;
        	}
        
        });
        if(!result){
        	return result;
        }
        
       var areas = $("#table1 select");
       var hash = {}; 
       areas.each(function(x,area){
           if(hash[area.value]){
       	   	  alert("不能选择重复的区域！");
       		  area.focus();
        	  result = false;
        	  return result;
       	   }
       	   hash[area.value] = true; 
	 	   if(areas.size() > 1){
	 	       if(area.value=='' || area.value=='0' || area.value=='152000000000'){
		 		   alert("选择多个区域时不能选择河南！");
		 	       area.focus();
	        	   result = false;
	        	   return result;
 	    	   }
	 	   }
       
       });
       return result;	 	
       		 
    }
   
 	
	 //更改广告位
	 function changePosition(positionCode){
		 var ployType ;
		  $.ajax({
                type:"get",
                async:false,
                url:"<%=path%>/dploy/getPositionPloy.action",
                data:{"position.positionCode":positionCode},//Ajax传递的参数
                success:function(mess){
                    ployType = eval(mess);
                },
                error:function(mess){
                	alert("未知错误");
                }
           });
           $("#positionCode").val(positionCode);
           if(ployType != ''){
	           	$("#typeList span").remove();
	           	$(".boxList table").remove();
	           	$.each(ployType, function(key,value) {     
	   				 $("#typeList").append('<span><input type="checkbox" id="type'+key+'" value="'+key+'" checked disabled/>'+value+'</span>');
	   				 createTable(key,value);        
				});
       		}
	 }

	 function createTable(index, title){
	 	var tab = '<table cellspacing="1" class="searchList">'
	     +' <tr class="title">'
	     +'   <td height="28" class="dot"><input type="checkbox" onclick="selectAll(this,\'checkbox'+index+'\');"/></td>'
		 +'      <td><b>'+title+'</b>'
	     +'     </td>'
		 +'	</td>'
	     +' </tr>'
	     +' <tr>'
	     +'   <td colspan="2" class="conditionList">'
	     +'   <div>'
	     +'       <table width="100%" border="0" cellspacing="0" cellpadding="0" id="table'+index+'">'
	     +'         <tr >'
	     +'           <td class="dot"></td>'
	     +'           <td >'+(index==2?'开始时间':title)+'</td>'
	     +'           <td >'+(index==2?'结束时间':'优先级')+'</td>'
	     +'         </tr>  '
	     +'       </table>'
	     +'   </div>'
	     +'   </td>'
	     +' </tr>'
		 +'</table>';
	 	$(".boxList").append(tab);
	 
	 }
	 function initItem(){
	    var detailJson = eval(${ploy.ployDetailJson});
	   	$.each(detailJson, function(i, detail){
	   		var type= detail.ployType;
	   		var item = '<tr><td class="dot"><input type="checkbox" name="checkbox'+type+'" value="checkbox" />'
	   				+'</td>';
	    	switch(type){
	    		case '1':
	    			item+='<td><select name="ploy.ployDetailList['+i+'].typeValue" style="width:80px" disabled>'
	          			+'<c:forEach items="${areaList}" var="areaVar" >'
				        +'<option value="${areaVar.areaCode}" '+(${areaVar.areaCode}==detail.typeValue?'selected':'')+'>${areaVar.areaName}</option>'   
				        +'</c:forEach>' 				         					
				        +'</select></td>';
				   
				    break;
				case '2':
					item+='<td ><input name="ploy.ployDetailList['+i+'].startTime" type="text" readonly="readonly" onclick="WdatePicker({dateFmt:\'HH:mm:ss\'})" value="'+detail.startTime+'" disabled/></td>'
              			+'<td ><input name="ploy.ployDetailList['+i+'].endTime" type="text" readonly="readonly" onclick="WdatePicker({dateFmt:\'HH:mm:ss\'})" value="'+detail.endTime+'" disabled/></td>';
	    			break;
	    		default:
	    			item+='<td ><input name="ploy.ployDetailList['+i+'].typeValue" type="text"  value="'+detail.typeValue+'" disabled/></td>'
              			+'<td ><input name="ploy.ployDetailList['+i+'].priority" type="text"  value="'+detail.priority+'" disabled/></td>';
              		break;
	    	
	    	}
	    	item+='</tr>';
	    	$("#table"+type+" tbody").append(item);
	   
	   	});
			 	
	 }

</script>
<body class="mainBody">
<form action="<%=path %>/dploy/saveDPloy.action" method="post" id="saveForm">
<div class="path">首页 &gt;&gt; 投放策略管理 &gt;&gt; 策略审核</div>
<div class="searchContent">
  <table cellspacing="1" class="searchList">
    <tr class="title">
      <td>策略审核</td>
    </tr>
  <tr>
    <td class="searchCriteria">
      <span>*投放策略名称：</span>
      			             
               <input onkeypress="return validateSpecialCharacter();" maxlength="30" id="ploy.ployName" name="ploy.ployName" type="text" value="${ploy.ployName}" disabled/>
               <span>&nbsp;&nbsp;&nbsp;&nbsp;广告位名称：</span> 
               
               <select id="ploy.positionCode" name="ploy.dposition.positionCode" onchange="changePosition(this.value);"  <c:if test="${ploy.id > 0}"> disabled="disabled" </c:if> >
	               <c:forEach items="${listPosition}" var="positionVar" >
	                     <option value="${positionVar.positionCode}" <c:if test="${positionVar.positionCode==ploy.dposition.positionCode}"> selected </c:if>  >
	                            ${positionVar.positionName}
	                	</option>
	               </c:forEach>
               </select>
               <input type="hidden" name="ploy.id" id="ploy.id" type="text" value="${ploy.id}"/>
              
               <input  disabled="disabled" maxlength="30" id="positionCode" name="positionCode" type="text" style="width: 40px" value="${ploy.dposition.positionCode}"/>
               
       </td>
      </tr>
      <tr>
      <td><span id="typeList"></span></td>
      </tr>
  	
  </table>
  <div class="boxList">
    
  </div>
  <table cellspacing="1" class="searchList">
<tr>
    	<td style="width:10%">
    		<span>审核意见：</span>
    	</td>
    	<td>
         <textarea  name="ploy.auditAdvice" style="width:40%" rows="5"></textarea>
  	 	</td>
     </tr>
  <tr>
    <td class="searchSec" colspan="2" align="center">
    	
         <input id="saveBtn" type="button" class="btn" value="通过" onclick="submitForm(2);"/>&nbsp;&nbsp;&nbsp;&nbsp;
         <input id="saveBtn" type="button" class="btn" value="驳回" onclick="submitForm(3);"/>&nbsp;&nbsp;&nbsp;&nbsp;
         <input type="button" class="btn" value="取消" onclick="history.back(-1);"/>
    </td>
   </tr>
</table>
</div>

</form>
</body>
</html>
