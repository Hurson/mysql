/**
 * 对象属性拷贝
 *
 */
var resourcePath=$('#projetPath').val();
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
/**
 * 判断数组中的元素是否在另一个数组中已存在
 **/
Array.existsSameValues = function(arr1, arr2) {
    var existsSaveValueReturn=[];
    if(arr1 instanceof Array && arr2 instanceof Array)
    {
        for(var s in arr1){
		    for(var x in arr2){
		        if(arr1[s]!=''&&arr1[s]==arr2[x]){
		            existsSaveValueReturn.push(arr1[s]);
		        }
		    }
		}
    }
    return existsSaveValueReturn;
};

/**
 * 根据参数名称获取参数值
 * @param {} name
 * @return {}
 */
function getParamValue(name)
{
    var paramsArray = getUrlParams();
    if(paramsArray != null)
    {
        for(var i = 0 ; i < paramsArray.length ; i ++ )
        {
            for(var  j in paramsArray[i] )
            {
                if( j == name )
                {
                    return paramsArray[i][j] ; 
                }
            }
        }
    }
    return null ; 
}

/**
 * 获取地址栏的参数数组
 * @return {}
 */
function getUrlParams()
{
    var search = window.location.search ; 
    // 写入数据字典
    var tmparray = search.substr(1,search.length).split("&");
    var paramsArray = new Array; 
    if( tmparray != null)
    {
        for(var i = 0;i<tmparray.length;i++)
        {
            var reg = /[=|^==]/;    // 用=进行拆分，但不包括==
            var set1 = tmparray[i].replace(reg,'&');
            var tmpStr2 = set1.split('&');
            var array = new Array ; 
            array[tmpStr2[0]] = tmpStr2[1] ; 
            paramsArray.push(array);
        }
    }
    // 将参数数组进行返回
    return paramsArray ;     
}

/**
 * 默认选中状态
 */ 
function defaultChoose(type,alreadyChooseArray){
	 $("input[type='"+type+"']").each(function(index,elements){
	        if(!$.isEmptyObject(alreadyChooseArray)){
	        	$(alreadyChooseArray).each(function(indexInner,elementsInner){
	        		if(elementsInner.id==elements.value){
	        			elements.checked=true;
	        		}
	        	});
	        }
	 });
}

/**
 * 特征码汉字转换
 * @param {} dataTypeParam
 * @param {} dataType
 * @return {String}
 */
function showInfoTransform(dataTypeParam,dataType){
	
	if(dataType=='dataType'){
		if(dataTypeParam=='sd'){
			return '标清';
		}else if(dataTypeParam=='hd'){
			return '高清';
		}
	}
	
	if(dataType=='showDataType'){
		if(dataTypeParam=='0'){
			return 'sd';
		}else if(dataTypeParam=='1'){
			return 'hd';
		}
	}
	
	if(dataType=='isLink'){
		if(dataTypeParam=='0'){
			return '否';
		}else if(dataTypeParam=='1'){
			return '是';
		}else{
			return '未选择';
		}
	}
	
	if(dataType=='isHd'){
		if(dataTypeParam=='0'){
			return '标清';
		}else if(dataTypeParam=='1'){
			return '高清';
		}else{
			return '未选择';
		}
	}
	
	if(dataType=='deliveryMode'){
		if(dataTypeParam=='0'){
			return '投放式';
		}else if(dataTypeParam=='1'){
			return '请求式';
		}else{
			return '未选择';
		}
	}
	
	if(dataType=='marketRule'){
		if(dataTypeParam=='0'){
			return '禁用';
		}else if(dataTypeParam=='1'){
			return '启用';
		}else{
			return '未选择';
		}
	}
}

function validateNumber(str){
	var rule = /^\d+$/;
	return rule.test(str);
}

 function validateTime(dt1,dt2)
{
		var flag = true;
		var date = new Date();
        var jdt1=Date.parse(date.getFullYear()+'-'+date.getMonth()+'-'+date.getDate()+' '+dt1);
        var jdt2=Date.parse(date.getFullYear()+'-'+date.getMonth()+'-'+date.getDate()+' '+dt2);
        if (jdt1 > jdt2)
        {
        	flag = false;
            createDialog('开始时间大于结束时间');
        } else if(jdt1 == jdt2) {
        	flag = false;
            createDialog('开始时间等于结束时间');
        }
    	return flag;
}