
	function goInfo(id){
	   window.location.href="adCustomerMgr_getCustomerInfo.do?cId= "+id+" ";
	
	}

		//客户通过审核
		function goAuditMetas(id){
			//alert("id="+id);
			$.ajax({
				type:"post",
				url: 'adCustomerMgr_auditCustomerBackUp.do',
				data : 'cId=' + id +'&date=' + new Date(),
				success:function(responseText){
				 var ss = eval(responseText);
					var str = "<table cellpadding='0' cellspacing='1' width='100%' class='tablea' height='272px'>";
					for(var i =0;i<ss.length;i++){
					    str += "<tr><td class='td_labelXdiv'>广告商名称：</td>";
						str +="<td class='td_inputXdiv'>";
						str += ss[i].advertisersName;
						str +="</td><input type='hidden' value='";
						str +=ss[i].id;
						str +="' id='id'/>";
						str += "<td class='td_labelXdiv' rowspan='5'>客户描述：</td>"
						str +="<td class='td_inputXdiv' rowspan='5'><textarea rows='12' cols='30' readonly='true'>";
						str +=ss[i].remark;
						str +="</textarea></td></tr>"; 
						str +="<tr><td class='td_labelXdiv'>客户代码：</td>"
						str +="<td class='td_inputXdiv'>";
						str +=ss[i].clientCode;
						str +="</td></tr>";
						str +="<tr><td class='td_labelXdiv'>联系方式：</td>"
						str +="<td class='td_inputXdiv'>";
						str +=ss[i].communicator;
						str +="</td></tr>"
						str +="<tr><td class='td_labelXdiv'>状态：</td><td class='td_inputXdiv'><font style='color:red;'>审核通过</font></td></tr>"
						str += "<tr><td class='td_labelXdiv'>审核意见：</td>";
						str +="<td class='td_inputXdiv'><textarea id='examinationOpintions'  rows='6' cols='25' value='${customerBackUp.examinationOpinions}' name='customerBackUp.examinationOpintions'></textarea>";
						str +="</td></tr>";
					}
					str +="</table>"
					$("#materialInfo").html(str);
					showDiv();
				},
				dataType:'text',
				error:function(){
				alert("服务器异常，策略加载失败，请稍后重试！");
				}
			});
			
		}
		
		
		
		//客户没有通过审核
		function noAuditMetasPass(id){
			$.ajax({
				type:"post",
				url: 'adCustomerMgr_auditCustomerBackUp.do',
				data : 'cId=' + id +'&date=' + new Date(),
				success:function(responseText){
				 var ss = eval(responseText);
					var str = "<table cellpadding='0' cellspacing='1' width='100%' class='tablea' height='272px'>";
					for(var i =0;i<ss.length;i++){
					    str += "<tr><td class='td_labelXdiv'>广告商名称：</td>";
						str +="<td class='td_inputXdiv'>";
						str += ss[i].advertisersName;
						str +="</td><input type='hidden' value='";
						str +=ss[i].id;
						str +="' id='id'/>";
						str += "<td class='td_labelXdiv' rowspan='5'>客户描述：</td>"
						str +="<td class='td_inputXdiv' rowspan='5'><textarea rows='12' cols='30' readonly='true'>";
						str +=ss[i].remark;
						str +="</textarea></td></tr>"; 
						str +="<tr><td class='td_labelXdiv'>客户代码：</td>"
						str +="<td class='td_inputXdiv'>";
						str +=ss[i].clientCode;
						str +="</td></tr>";
						str +="<tr><td class='td_labelXdiv'>联系方式：</td>"
						str +="<td class='td_inputXdiv'>";
						str +=ss[i].communicator;
						str +="</td></tr>"
						str +="<tr><td class='td_labelXdiv'>状态：</td><td class='td_inputXdiv'><font style='color:red;'>审核不通过</font></td></tr>"
						str += "<tr><td class='td_labelXdiv'>审核意见：</td>";
						str +="<td class='td_inputXdiv'><textarea id='examinationOpintions'  rows='6' cols='25' value='${customerBackUp.examinationOpinions}' name='customerBackUp.examinationOpintions'></textarea>";
						str +="</td></tr>";
					}
					str +="</table>"
					$("#noMaterialInfo").html(str);
					showDivNo();
				},
				dataType:'text',
				error:function(){
				alert("服务器异常，策略加载失败，请稍后重试！");
				}
			});
		}		
		
	   //提交审核不通过意见
	   function noSubmitOpintions(){
	   		var examinationOpintions = document.getElementById("examinationOpintions").value;
	        var id = document.getElementById("id").value;
	        
	        alert("广告商信息没有通过审核，确认请按确定按钮！");
			window.location.href="adCustomerMgr_insertNoAuditCustomer.do?cId= "+id+" "+"&examinationOpintions= "+examinationOpintions+" ";
	   		return false;
	   }
		
	   //提交审核通过意见
	   function submitOpintions(){
	   		var examinationOpintions = document.getElementById("examinationOpintions").value;
	        var id = document.getElementById("id").value;
	        
	        alert("广告商信息通过审核，确认请按确定按钮！");
			window.location.href="adCustomerMgr_insertGoAuditCustomer.do?cId= "+id+" "+"&examinationOpintions= "+examinationOpintions+" ";
	   		return false;
	   }
		
		
		
		//显示审核不通过
	   function showDivNo(){
			$('#noMaterialDiv').show("slow");
			$('#bg').show();
			$('#popIframe').show();
		}


	    //显示审核通过
		function showDiv(){
			$('#materialDiv').show("slow");
			$('#bg').show();
			$('#popIframe').show();
		}
		
		//关闭审核通过弹出层
		function closeSelectDiv(){
		    $('#materialDiv').hide();
		  	$('#bg').hide();
		  	$('#popIframe').hide();
		  	return;
		}

		//关闭审核未通过弹出层
		function noCloseSelectDiv(){
		    $('#noMaterialDiv').hide();
		  	$('#bg').hide();
		  	$('#popIframe').hide();
		  	return;
		}
		
		//删除
	//	function goDelete(id,state){
	//		if(state == "1"){
	//			alert("审核通过的广告商，不能被删除！");
	//			return;
	//		}else{
	//			var a = window.confirm("您确定要删除该条客户记录吗？");
	//			if(a==1){
	//				  $.ajax( {
	//					  type : 'post',
	//					  url : 'adCustomerMgr_deleteAdvalidate.do',
	//					  data : 'cId=' + id + '&date=' + new Date(),
	//					  success : function(msg) {
	//					 	 	if(msg == "1"){
	//					 	 		alert("此客户有合同在执行，不能删除，请确认再操作！");
	//					 	 		return false;
	//					   			//window.location.href="adCustomerMgr_list.do";
	//							 }else{
	//							    window.location.href="adCustomerMgr_list.do";
	//							 }
	//						}	
	//				});
	//		     }
	//		}
	//	}
		
		//原来没有验证的判断删除
		function goDelete(id,state){
		
			if(state == "1"){
				alert("审核通过的广告商，不能被删除！");
				return;
			}else{
				var a = window.confirm("您确定要删除该条客户记录吗？");
				if(a==1){
					  $.ajax( {
						  type : 'post',
						  url : 'adCustomerMgr_deleteCustomerBackUp.do',
						  data : 'cId=' + id + '&date=' + new Date(),
						  success : function(msg) {
						 	 	if(msg != null){
						   			window.location.href="adCustomerMgr_list.do";
								 }
							}	
					});
				}
		
		   }
		}
		
	//跳转到 添加 页面
     function goSaveCustomerRedirect(){
		window.location.href="adCustomerMgr_saveCustomerBackUpRedirect.do";     
     }
     
	//调转到 修改 页面
	function goUpdateReditrect(id,state){
	
		if(state == "1"){
			window.location.href="adCustomerMgr_updateCustomerBackUpRedirect.do?cId= "+id+" ";
		 // alert("审核通过的广告商，不能被修改！");
		 // return ;
		}else{
			window.location.href="adCustomerMgr_updateCustomerBackUpRedirect.do?cId= "+id+" ";
			
     //   var url = "adCustomerMgr_updateCustomerBackUpRedirect.do?cId= "+id+" ";
     //   <!--window.location.href = url;-->
	//	window.showModalDialog(url, "", "dialogHeight=500px;dialogWidth=820px;center=1;resizable=0;status=0;");
    
		}
	}
	
	//跳转到详细审核页面
	function goAuditReditrect(id){
	
	
			window.location.href="http://localhost:8080/Advert/adCustomerMgr_getCustomerAuditInfo.do?cId= "+id+" ";
			
     //   var url = "adCustomerMgr_updateCustomerBackUpRedirect.do?cId= "+id+" ";
     //   <!--window.location.href = url;-->
	//	window.showModalDialog(url, "", "dialogHeight=500px;dialogWidth=820px;center=1;resizable=0;status=0;");
   
		
	}
	
