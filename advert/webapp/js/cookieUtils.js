/**
 * COOKIE_USER_ID 		| 存放cookie  的用户的Id
 * COOKIE_SESSION_ID	| 存放cookie  的sessionId
 * COOKIE_USER_NAME     | 存放cookie  的用户的名字
 * 
 ****************************************/


/**
 * 取出 cookie
 * @param offset
 * @return
 */
function GetCookieVal(offset)
// 获得Cookie解码后的值
{
	var endstr = document.cookie.indexOf(";", offset);
	if (endstr == -1)
		endstr = document.cookie.length;
	return unescape(document.cookie.substring(offset, endstr));
}

/**
 * 存入cookie
 * 
 * @param name    key值
 * @param value   value值
 * @return
 */
function SetCookie(name, value)
// 设定Cookie值
{
	var expdate = new Date();
	var argv = SetCookie.arguments;
	var argc = SetCookie.arguments.length;
	var expires = (argc > 2) ? argv[2] : null;
	var path = (argc > 3) ? argv[3] : null;
	var domain = (argc > 4) ? argv[4] : null;
	var secure = (argc > 5) ? argv[5] : false;
	if (expires != null)
		expdate.setTime(expdate.getTime() + (expires * 1000));
	document.cookie = name + "=" + escape(value)
			+ ((expires == null) ? "" : ("; expires=" + expdate.toGMTString()))
			+ ((path == null) ? "" : ("; path=" + path))
			+ ((domain == null) ? "" : ("; domain=" + domain))
			+ ((secure == true) ? "; secure" : "");
}

/**
 * 删除Cookie
 * 
 * @param name   键值
 * @return
 */
function DelCookie(name)
// 删除Cookie
{
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = GetCookie(name);
	document.cookie = name + "=" + cval + "; expires=" + exp.toGMTString();
}

/**
 * 获得Cookie的原始值
 * @param name
 * @return
 */
function GetCookie(name)
// 获得Cookie的原始值
{
	var arg = name + "=";
	var alen = arg.length;
	var clen = document.cookie.length;
	var i = 0;
	while (i < clen) {
		var j = i + alen;
		if (document.cookie.substring(i, j) == arg)
			return GetCookieVal(j);
		i = document.cookie.indexOf(" ", i) + 1;
		if (i == 0)
			break;
	}
	return null;
}


/***********************************************************************/

/************************************************************************
|    函数名称： setCookie                                                |
|    函数功能： 设置cookie函数                                            |
|    入口参数： name：cookie名称；value：cookie值                        |
|    维护记录： Spark(创建）                                            |
|	 例子如下：setCookie('Method',match);                                         |
*************************************************************************/
function setCookie(name, value) 
{ 
    var argv = setCookie.arguments; 
    var argc = setCookie.arguments.length; 
    var expires = (argc > 2) ? argv[2] : null; 
    if(expires!=null) 
    { 
        var LargeExpDate = new Date (); 
        LargeExpDate.setTime(LargeExpDate.getTime() + (expires*1000*3600*24));         
    } 
    document.cookie = name + "=" + escape (value)+((expires == null) ? "" : ("; expires=" +LargeExpDate.toGMTString())); 
}
/************************************************************************
|    函数名称： getCookie                                                |
|    函数功能： 读取cookie函数                                            |
|    入口参数： Name：cookie名称                                            |
|    维护记录： Spark(创建）   
|	 例子如下：  getCookie('Method')                                       |
*************************************************************************/
function getCookie(Name) 
{ 
    var search = Name + "=" 
    if(document.cookie.length > 0) 
    { 
        offset = document.cookie.indexOf(search) 
        if(offset != -1) 
        { 
            offset += search.length 
            end = document.cookie.indexOf(";", offset) 
            if(end == -1) end = document.cookie.length 
            return unescape(document.cookie.substring(offset, end)) 
        } 
        else return "" 
    } 
} 

/************************************************************************
|    函数名称： deleteCookie                                            |
|    函数功能： 删除cookie函数                                            |
|    入口参数： Name：cookie名称                                        |
|    维护记录： Spark(创建）                                        |
|	 例子如下： deleteCookie('Method');                                        |
*************************************************************************/    
function deleteCookie(name) 
{ 
	 var expdate = new Date(); 
	 expdate.setTime(expdate.getTime() - (86400 * 1000 * 1)); 
	setCookie(name, "", expdate); 
} 
