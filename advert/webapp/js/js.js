	
	
	//下线
	function goDownLine(id,type,resourceId,state){
		if(state == '3'){
		    alert("已经下线的素材不能再进行下线操作，请确认！");
		    return;
		}else{
			//图片
    		if(type == 0){
    			var a = window.confirm("您确定要将该条图片记录下线吗？");
    			if(a==1){
		    		$.ajax( {
						type : 'post',
						url : 'adContentMgr_downLineImageMeta.do',
						data : 'id=' + id + '&resourceId='+resourceId+'&date=' + new Date(),
						success : function(msg) {
							if(msg != null){
								window.location.href="adContentMgr_listReal.do";
							}
						}	
					});
				}	
    		//视频
    		}else if(type == 1){
    		  var a = window.confirm("您确定要将该条视频记录下线吗？");
    		  	if(a==1){
		    		 $.ajax( {
						type : 'post',
						url : 'adContentMgr_downLineVideoMeta.do',
						data : 'id=' + id +'&resourceId='+resourceId+'&date=' + new Date(),
						success : function(msg) {
							if(msg != null){
								window.location.href="adContentMgr_listReal.do"
							}
						}	
					});
				}
    		 //消息
    		}else if(type == 2){
    		  var a = window.confirm("您确定要将该条消息记录下线吗？");
    		  if(a==1){
		    	  $.ajax( {
						type : 'post',
						url : 'adContentMgr_downLineMessageMeta.do',
						data : 'id='+id +'&resourceId='+resourceId+'&date=' + new Date(),
						success : function(msg) {
							if(msg != null){
								window.location.href="adContentMgr_listReal.do"
							}
						}	
					});
				}
			}
		}
	}
	
	
	//删除素材
	function goDeleteInfo(id,type,resourceId,state){
	    //上线状态
		if(state == '2'){
			alert("已经上线的素材不能被删除，请确认后，再操作！");
			return;
		//删除待审核状态
		}else if(state == '4'){
			alert("已删除状态的素材不能再删除，请确认后，再操作！");
			return;
		// 下线状态	
		}else if(state == '5'){
			alert("删除已审核状态的素材不能被删除，请确认后，再操作！");
			return;
		}else if(state == '3'){
		    //图片
			if(type == 0){
    			var a = window.confirm("您执行确定按钮后，图片将变为删除状态！，确定吗？");
    			if(a==1){
		    		$.ajax( {
						type : 'post',
						url : 'adContentMgr_deleteImageMeta.do',
						data : 'id=' + id + '&resourceId='+resourceId+'&state='+state+'&date=' + new Date(),
						success : function(msg) {
							if(msg != null){
								window.location.href="adContentMgr_list.do";
							}
						}	
					});
				}	
    		//视频
    		}else if(type == 1){
    		  var a = window.confirm("您执行确定按钮后，视频将变为删除状态！，确定吗？");
    		  	if(a==1){
		    		 $.ajax( {
						type : 'post',
						url : 'adContentMgr_deleteVideoMeta.do',
						data : 'id=' + id +'&resourceId='+resourceId+'&state='+state+'&date=' + new Date(),
						success : function(msg) {
							if(msg != null){
								window.location.href="adContentMgr_list.do"
							}
						}	
					});
				}
    		 //消息
    		}else if(type == 2){
    		  var a = window.confirm("您执行确定按钮后，将变为已删除状态！，确定吗？");
    		  if(a==1){
		    	  $.ajax( {
						type : 'post',
						url : 'adContentMgr_deleteMessageMeta.do',
						data : 'id='+id +'&resourceId='+resourceId+'&state='+state+'&date=' + new Date(),
						success : function(msg) {
							if(msg != null){
								window.location.href="adContentMgr_list.do"
							}
						}	
					});
				}
			}
		}else{
    		//图片
    		if(type == 0){
    			var a = window.confirm("您确定要删除该条图片记录吗？");
    			if(a==1){
		    		$.ajax( {
						type : 'post',
						url : 'adContentMgr_deleteImageMeta.do',
						data : 'id=' + id + '&resourceId='+resourceId+'&state='+state+'&date=' + new Date(),
						success : function(msg) {
							if(msg != null){
								window.location.href="adContentMgr_list.do";
							}
						}	
					});
				}	
    		//视频
    		}else if(type == 1){
    		  var a = window.confirm("您确定要删除该条视频记录吗？");
    		  	if(a==1){
		    		 $.ajax( {
						type : 'post',
						url : 'adContentMgr_deleteVideoMeta.do',
						data : 'id=' + id +'&resourceId='+resourceId+'&state='+state+'&date=' + new Date(),
						success : function(msg) {
							if(msg != null){
								window.location.href="adContentMgr_list.do"
							}
						}	
					});
				}
    		 //消息
    		}else if(type == 2){
    		  var a = window.confirm("您确定要删除该条消息记录吗？");
    		  if(a==1){
		    	  $.ajax( {
						type : 'post',
						url : 'adContentMgr_deleteMessageMeta.do',
						data : 'id='+id +'&resourceId='+resourceId+'&state='+state+'&date=' + new Date(),
						success : function(msg) {
							if(msg != null){
								window.location.href="adContentMgr_list.do"
							}
						}	
					});
				}
	    			
    		}else if(type == 3){
    			//window.location.href="adContentMgr_deleteImageMeta?imageMeta.id="+id+"&type="+type;
    		}else {
    			alert("请确认，没有您要删除的类型");
    		}
    	}
    }
    	
    	
    	//运营商删除Real表单素材
    	function goDeleteInfoReal(id,type,resourceId,state){
    	
    	 if(state == '2'){
			alert("此素材属于正在运行或将要投放的订单，不能删除，请确认后，再操作！");
			return;
		}else{
    		//图片
    		if(type == 0){
    			var a = window.confirm("您确定要删除该条图片记录吗？");
    			if(a==1){
		    		$.ajax( {
						type : 'post',
						url : 'adContentMgr_deleteImageMeta.do',
						data : 'id=' + id + '&resourceId='+resourceId+'&state=3'+'&date=' + new Date(),
						success : function(msg) {
							if(msg != null){
								window.location.href="adContentMgr_listReal.do";
							}
						}	
					});
				}	
    		//视频
    		}else if(type == 1){
    		  var a = window.confirm("您确定要删除该条视频记录吗？");
    		  	if(a==1){
		    		 $.ajax( {
						type : 'post',
						url : 'adContentMgr_deleteVideoMeta.do',
						data : 'id=' + id +'&resourceId='+resourceId+'&state=3'+'&date=' + new Date(),
						success : function(msg) {
							if(msg != null){
								window.location.href="adContentMgr_listReal.do"
							}
						}	
					});
				}
    		 //消息
    		}else if(type == 2){
    		  var a = window.confirm("您确定要删除该条消息记录吗？");
    		  if(a==1){
		    	  $.ajax( {
						type : 'post',
						url : 'adContentMgr_deleteMessageMeta.do',
						data : 'id='+id +'&resourceId='+resourceId+'&state=3'+'&date=' + new Date(),
						success : function(msg) {
							if(msg != null){
								window.location.href="adContentMgr_listReal.do"
							}
						}	
					});
				}
	    			
    		}else if(type == 3){
    			//window.location.href="adContentMgr_deleteImageMeta?imageMeta.id="+id+"&type="+type;
    		}else {
    			alert("请确认，没有您要删除的类型");
    		}
    	}
    }
    	
    	
	//修改素材 
	function goUpdateMetaRedirect(id,type,resourceId,state){
	   if(state == '2'){
			alert("已经上线的素材，不能被修改，请确认后，再操作！");
			return;
		}else{
			  //图片
			  //这里需要在id 后面加上一个空格，否则值就会传不到后台
			  if(type ==0){
			 		 window.location.href="adContentMgr_updateImageMetaRedirect.do?id= "+id+" "+"&resourceId= "+resourceId+" ";
			 		 return;
			  //视频
			  }else if(type ==1){
			  		 //这里需要在id 后面加上一个空格，否则值就会传不到后台
			   		 window.location.href="adContentMgr_updateVideoMetaRedirect.do?id= "+id+" "+"&resourceId= "+resourceId+" ";
			   		 return;
			  //文字
			  }else if(type ==2){
			   		 window.location.href="adContentMgr_updateMessageMetaRedirect.do?id= "+id+" "+"&resourceId= "+resourceId+" ";
			   		  return;
			  //问卷
			  }else if(type ==3){
			   		 window.location.href="adContentMgr_updateImageMetaRedirect.do?id= "+id+" "+"&resourceId= "+resourceId+" ";
			   		  return;
			  }else{
			  		 alert("没有您要修改的素材类型");
			  }
	     }
		
	}
	
	//运行商修改运行期Real素材 
	function goUpdateMetaRedirectReal(id,type,resourceId,state){
	
	  if(state == '2'){
			alert("此素材属于正在运行的订单，不能修改，请确认后再操作！");
			return;
		}else{
	
		  //图片
		  //这里需要在id 后面加上一个空格，否则值就会传不到后台
		  if(type ==0){
		 		 window.location.href="adContentMgr_updateImageMetaRealRedirect.do?id= "+id+" "+"&resourceId= "+resourceId+" ";
		 		 return;
		  //视频
		  }else if(type ==1){
		  		 //这里需要在id 后面加上一个空格，否则值就会传不到后台
		   		 window.location.href="adContentMgr_updateVideoMetaRealRedirect.do?id= "+id+" "+"&resourceId= "+resourceId+" ";
		   		 return;
		  //文字
		  }else if(type ==2){
		   		 window.location.href="adContentMgr_updateMessageMetaRealRedirect.do?id= "+id+" "+"&resourceId= "+resourceId+" ";
		   		  return;
		  //问卷
		  }else if(type ==3){
		   		 window.location.href="adContentMgr_updateImageMetaRealRedirect.do?id= "+id+" "+"&resourceId= "+resourceId+" ";
		   		  return;
		  }else{
		  		 alert("没有您要修改的素材类型");
		  }
	   }
		
	}
	
	//提交修改完成的素材
	function UpdateMetas(type){
		
		if(type == 1){
		    alert("等待上传图片素材表单字段的最终确定，而确定页面字段，暂不可用……");
		    return false;
		   // alert("更新图片素材 UpdateMetas()--type是"+type );
		    //图片
			//window.location.href="adContentMgr_updateImageMeta.do";
		}else if(type == 2){
		alert("等待上传视频素材表单字段的最终确定，而确定页面字段，暂不可用……");
		    //视频
		 //	window.location.href="adContentMgr_updateVideoMeta.do";
		 return false;
		}else if(type == 3){
		alert("等待上传文字素材表单字段的最终确定，而确定页面字段，暂不可用……");
		    //文字
		//	window.location.href="adContentMgr_updateMessageMeta.do";
		return false;
		}else{
			alert("没有您要提交的修改类型.....");
			return false;
		}
	
	}

	//跳转添加素材选择框
	function goMetaRedirect(){
			window.location.href="adContentMgr_goAddMetas.do";
			//window.showModalDialog("adMetaOpenShow.jsp",window,"status:no;dialogHeight:210px;dialogWidth:360px;help:no");
	}
			
	//调转到审核素材
	function goAuditRedirect(){
		var id = 2;
		window.location.href="adContentMgr_list.do?id= " +id+" ";
	}	
	
	//审核素材通过
	function goAuditMetasPass(id,type,resourceId){
	    //alert("审核素材通过的方法"+"id="+id+"type="+type);
		if(type == 0){
			alert("图片素材通过审核");
			//图片
			window.location.href="adContentMgr_insertGoAuditImageMetas.do?id= "+id+" "+"&resourceId= "+resourceId+" ";
		}else if(type == 1){
			alert("视频素材通过审核");
			//视频
			window.location.href="adContentMgr_insertGoAuditVideoMetas.do?id= "+id+" "+"&resourceId= "+resourceId+" ";
		}else if(type == 2){
			alert("文字素材通过审核");
			//文字		
			window.location.href="adContentMgr_insertGoAuditMessageMetas.do?id= "+id+" "+"&resourceId= "+resourceId+" ";
		}else{
			alert("没有找到要审核的素材类型....");
		}
	}	
	
	//审核素材不通过
	function noAuditMetasPass(id,type,resourceId){
	//	alert("审核素材不通过的方法"+"id="+id+"type="+type);
		if(type == 0){
		    //图片
			alert("该图片素材没有通过审核");
		 	window.location.href="adContentMgr_noAuditImageMetasPass.do?id= "+id+" "+"&resourceId= "+resourceId+" ";;
		}else if(type == 1){
		    //视频
		    alert("该视频素材没有通过审核");
		 	window.location.href="adContentMgr_noAuditVideoMetasPass.do?id= "+id+" "+"&resourceId= "+resourceId+" ";;
		}else if(type == 2){
		    //文字
		    alert("该文字素材没有通过审核");
			window.location.href="adContentMgr_noAuditMessageMetasPass.do?id= "+id+" "+"&resourceId= "+resourceId+" ";;
		}else {
			alert("没有找到要审核的素材类型.....");
		}
		
	}
	
			
 