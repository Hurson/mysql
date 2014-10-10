

  //同步信息
		function syncCategoryInfoFromCps(){
			easyDialog.open({
			 	container : {
			    header : '确认框',
			    content : '确认同步后，请点击【确认】按钮',
			    yesFn : confirmSyncCategoryInfo,
			    noFn : true
			    //这里也可以设置自动关闭的 时间 autoClose:10000
			  	}
			});
			
		}
		
		var confirmSyncCategoryInfo = function(){
		     //调转到 CategoryBeanAction 中的对应方法：stageSyncCategoryInfo2();
			 window.location.href='CPSPotiontionMgr_stageSyncCategoryInfo2.do';
		};
		

//删除
function goDeleteInfo(id){
			var a = window.confirm("您确定要删除该条记录吗？");
    			if(a==1){
		    		$.ajax( {
						type : 'post',
						url : 'CPSPotiontionMgr_deleteInfo.do',
						data : 'cId=' + id +'&date=' + new Date(),
						success : function(msg) {
							if(msg != null){
								window.location.href="CPSPotiontionMgr_queryPage.do";
							}
						}	
					});
				}	
		
		}




function accessUrl(url){
		window.location.href=url;
}



$(function(){
	
	 var options = {   
        contentType: "application/x-www-form-urlencoded;charset=utf-8"
    }; 
	
	 $("#positionTypeId").click(function(){
     	//alert('123');
     });
     $("#searchPositionSubmit").click(function(){
     	var positionTypeId = $("#positionTypeId").val();
     	var positionName = $("#positionName").val();
     	url='CPSPotiontionMgr_queryPage.do?positionTypeId='+ positionTypeId + '&positionName='+positionName;
     	//url='queryPositionPage.do?method=queryPage&positionTypeId='+positionTypeId;
     	accessUrl(url);
     	return;
     });
     $("#addPositionSubmit").click(function(){
     	url='addPositionPage.do?method=addPage';
     	accessUrl(url);
     	return;
     });
});