//获取上下文路径
function getContextPath() {
	var contextPath = document.location.pathname;
	var index =contextPath.substr(1).indexOf("/"); 
	contextPath = contextPath.substr(0,index+1);
	delete index;
	return contextPath; 
} 

/*
获取指定的URL参数值
URL:http://www.blogjava.net/blog?name=bainian
参数：paramName URL参数
调用方法:getParam("name")
返回值:bainian
*/
function getParam(paramName){
        paramValue = "";
        isFound = false;
        if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=")>1) {
            arrSource = unescape(this.location.search).substring(1,this.location.search.length).split("&");
            i = 0;
            while (i < arrSource.length && !isFound)
            {
                if (arrSource[i].indexOf("=") > 0)
                {
                     if (arrSource[i].split("=")[0].toLowerCase()==paramName.toLowerCase())
                     {
                        paramValue = arrSource[i].split("=")[1];
                        isFound = true;
                     }
                }
                i++;
            }   
        }
   return paramValue;
}

String.prototype.trim = function () {
	return this .replace(/^\s /, '' ).replace(/\s $/, '' );
}
