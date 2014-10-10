<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <input id="projetPath" type="hidden" value="<%=path%>" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" />
    <title></title>
	<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
	<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
	<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" />

	<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.widget.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.tabs.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.accordion.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker-zh-CN.js"></script>


	<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path%>/js/contract/new/list.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path%>/js/util/tools.js"></script>	
	
    <script type="text/javascript">
       $(function(){   
       		resourcePath=$('#projetPath').val();
       		
       		var customerName = '${object.customerName}';
       		if(!$.isEmptyObject(customerName)){
				$('#customerName').val(customerName);
			}
       		var contractName = '${object.contractName}';
       		if(!$.isEmptyObject(contractName)){
				$('#contractName').val(contractName);
			}
       		var effectiveStartDate = '<fmt:formatDate value="${object.effectiveStartDate}" dateStyle="medium"/>';
       		if(!$.isEmptyObject(effectiveStartDate)){
				$('#effectiveStartDate').val(effectiveStartDate);
			}
       		var effectiveEndDate = '<fmt:formatDate value="${object.effectiveEndDate}" dateStyle="medium"/>';
       		if(!$.isEmptyObject(effectiveEndDate)){
				$('#effectiveEndDate').val(effectiveEndDate);
			}
  			$("#bm").find("tr:even").addClass("sec");  //偶数行的样式
    		$("#system-dialog").hide();	
    		var alreadyChoose = "";
    		$("#removeButton").click(function(){
    			$("input[name='contractDelCheckbox']").each(function(){
			        if($(this).is(':checked')){
			            alreadyChoose += $(this).val() + ",";
			        }
	    		});
	    		
	    		if($.isEmptyObject(alreadyChoose)){
			    	var message = '请先选择合同后再执行删除操作';
					createDialog(message);
	    		}else{
	    			var url = resourcePath+'/page/contract/removeContract.do?method=remove&ids='+alreadyChoose;
	    			createDialogConfirm('是否继续',url,'redirect','');
	    		}
    		});
    		
    		$("#cleanButton").click(function(){
    			$('#customerName').val('');
    			$('#contractName').val('');
    			$('#effectiveStartDate').val('');
    			$('#effectiveEndDate').val('');
    			generateAccess(1,'','','','')
    		});
    		
	   });

function generateAccess(currentPage,customerName,contractName,effectiveStartDate,effectiveEndDate){
	
	var path = 'queryContractPage.do?currentPage='+currentPage;
	if((!$.isEmptyObject(customerName))){
		$('#customerName').val(customerName);
	}
	if((!$.isEmptyObject(contractName))){
		$('#contractName').val(contractName);
	}
	if((!$.isEmptyObject(effectiveStartDate))){
		$('#effectiveStartDate').val(effectiveStartDate);
	}
	if((!$.isEmptyObject(effectiveEndDate))){
		$('#effectiveEndDate').val(effectiveEndDate);
	}
	submitForm('#query',path);
}

    </script>
</head>
<style>
	.easyDialog_wrapper{ width:800px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>

<body class="mainBody">

<div class="search">
        <div class="path">
        	<img src="<%=path %>/images/filder.gif" width="15" height="13"/>首页 >> 合同管理 >>合同维护
        </div>
        <div class="searchContent">
        <table cellspacing="1" class="searchList">
        	<form action="" method="post" id="query" name="query">
            <tr class="title">
                <td>查询条件</td>
            </tr>
            <tr>
                <td class="searchCriteria">
                  <span>广告商名称：</span>
                  <input type="text" name="object.customerName" id="customerName" value="${object.customerName}"/>
                  <span>合同名称：</span>
                  <input type="text" name="object.contractName" id="contractName" value="${object.contractName}"/>
                  <span>开始日期：</span>
                  		<input id="effectiveStartDate" name="object.effectiveStartDate" class="input_style2" type="text" style="width:80px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'effectiveEndDate\')}'})"/>
                 		<img onclick="showDate('effectiveStartDate')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"/>
                  <span>截止日期：</span>
                  		<input id="effectiveEndDate"  name="object.effectiveEndDate" class="input_style2" type="text" style="width:80px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'effectiveStartDate\')}'})"/>
                 		<img onclick="showDate('effectiveEndDate')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"/>
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    <input name="searchContractButton" id="searchContractButton" type="button" value="查询" class="btn"/>
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    <input name="cleanButton" id="cleanButton" type="button" value="查全部" class="btn"/>
                </td>
            </tr>
            </form>
        </table>
        <div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
        <table cellspacing="1" class="searchList" id="bm">
            <tr class="title">
              <td width="38" height="28" class="dot">
              	 选项
              </td>
                <td width="73">合同名称</td>
                <td width="98">合同编号</td>
                <td width="63">合同号</td>
                <td width="52">广告商</td>
				<td width="73">送审单位</td>
				<td width="67">合同金额</td>
				<td width="109">审批文号</td>
                <td width="96">合同开始日期</td>
                <td width="96">合同截止日期</td>
                <td width="52">状态</td>
            </tr>
            <c:choose>
						<c:when test="${!empty contractBackupList}">
							<c:forEach var="contract" items="${contractBackupList}" varStatus="status">
								<tr>
									<td align="center" height="26">
										<c:choose>
											<c:when test="${contract.status==1}">
												<input type="checkbox" name="contractDelCheckbox" id="checkbox${contract.id}" value="${contract.id}" disabled="disabled"/>
											</c:when>
											<c:otherwise>
												<input type="checkbox" name="contractDelCheckbox" id="checkbox${contract.id}" value="${contract.id}"/>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
									<form action="#" method="get" id="listContractOperationForm${status.count}" name="listContractOperationForm${status.count}">
											<input id="id" name="contract.id" type="hidden" value="${contract.id}"/>
											<input id="status" name="contract.status" type="hidden"  value="${contract.status}"/>
											<c:choose>
												<c:when test="${contract.status==0}">
													<a name="modifyContract${status.count}" id="modifyContract${status.count}"  value="" onClick="operation('modifyContract','#listContractOperationForm${status.count}')" href="#">${contract.contractName}</a>
												</c:when>
												<c:when test="${contract.status==1}">
													<a name="modifyContract${status.count}" id="modifyContract${status.count}"  value="" onClick="operation('modifyContract','#listContractOperationForm${status.count}')" href="#">${contract.contractName}</a>
												</c:when>
												<c:when test="${contract.status==2}">
													<a name="modifyContract${status.count}" id="modifyContract${status.count}"  value="" onClick="operation('modifyContract','#listContractOperationForm${status.count}')" href="#">${contract.contractName}</a>
												</c:when>
												<c:when test="${contract.status==3}">
													<a name="modifyContract${status.count}" id="modifyContract${status.count}"  value="" onClick="operation('modifyContract','#listContractOperationForm${status.count}')" href="#">${contract.contractName}</a>
												</c:when>
												<c:otherwise>
													<a name="modifyContract${status.count}" id="modifyContract${status.count}"  value="" onClick="operation('modifyContract','#listContractOperationForm${status.count}')" href="#">${contract.contractName}</a>
												</c:otherwise>
											</c:choose>
										</form>									
									</td>
									<td>${contract.contractNumber}</td>
									<td>${contract.contractCode}</td>
									<td>${contract.customerName}</td>
									<td>${contract.submitUnits}</td>
									<td>${contract.financialInformation}</td>
									<td>${contract.approvalCode}</td>
									<td><fmt:formatDate value="${contract.effectiveStartDate}" dateStyle="medium"/></td>
									<td><fmt:formatDate value="${contract.effectiveEndDate}" dateStyle="medium"/></td>
									<td>
										<c:choose>
											<c:when test="${contract.status==0}">
												待审核
											</c:when>
											<c:when test="${contract.status==1}">
												已审核
											</c:when>
											<c:when test="${contract.status==2}">
												审核未通过
											</c:when>
											<c:otherwise>
												下线状态
											</c:otherwise>
										</c:choose>
									</td>								
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="11">
									暂无记录
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
            <tr>
            	
				<td height="34" colspan="11"
							style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: right;">
							<input id="removeButton" name="removeButton" type="button" value="删除" class="btn"/>&nbsp;&nbsp;
                			<input id="addContractButton" name="addContractButton" type="button" value="添加" class="btn"/>
							<c:if test="${page.totalPage > 0 }">
								<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
								<a href="#">当前第${page.currentPage}/${page.totalPage}页</a>&nbsp;&nbsp;
								<c:choose>
									<c:when test="${page.currentPage==1&&page.totalPage!=1}">
										【<a href="#" onclick="generateAccess('${page.currentPage+1}','${object.customerName}','${object.contractName}','${object.effectiveStartDate}','${object.effectiveEndDate}')">下一页</a>】
										【<a href="#" onclick="generateAccess('${page.currentPage+1}','${object.customerName}','${object.contractName}','${object.effectiveStartDate}','${object.effectiveEndDate}')">末页</a>】
									</c:when>
									<c:when test="${page.currentPage==page.totalPage&&page.totalPage!=1 }">
										【<a href="#" onclick="generateAccess('1','${object.customerName}','${object.contractName}','${object.effectiveStartDate}','${object.effectiveEndDate}')">首页</a>】 
										【<a href="#" onclick="generateAccess('${page.currentPage-1}','${object.customerName}','${object.contractName}','${object.effectiveStartDate}','${object.effectiveEndDate}')">上一页</a>】
									</c:when>
									<c:when test="${page.currentPage>1&&page.currentPage<page.totalPage}">
										【<a href="#" onclick="generateAccess('1','${object.customerName}','${object.contractName}','${object.effectiveStartDate}','${object.effectiveEndDate}')">首页</a>】 
										【<a href="#" onclick="generateAccess('${page.currentPage-1}','${object.customerName}','${object.contractName}','${object.effectiveStartDate}','${object.effectiveEndDate}')">上一页</a>】
										【<a href="#" onclick="generateAccess('${page.currentPage+1}','${object.customerName}','${object.contractName}','${object.effectiveStartDate}','${object.effectiveEndDate}')">下一页</a>】
										【<a href="#" onclick="generateAccess('${page.totalPage}','${object.customerName}','${object.contractName}','${object.effectiveStartDate}','${object.effectiveEndDate}')">末页</a>】
									</c:when>
								</c:choose>
						</c:if>
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