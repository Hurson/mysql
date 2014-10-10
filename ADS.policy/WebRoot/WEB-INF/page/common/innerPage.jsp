<%--
  Created by IntelliJ IDEA.
  User: Hemeijin
  Date: 2011-7-23
  Time: 15:04:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>内页模板</title>
    <link id="maincss" href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet" type="text/css" media="all"/>
    <script type="text/javascript" defer="defer">
        function dosubmit(){
            var action=document.getElementById("getAction").value;
        }
    </script>
</head>
<body class="mainBody" style="text-align:left">
<label class="titlefont">当前位置:首页 >> 系统管理 >> 用户修改</label>

<div class="searchBar" id="searchBar">
    <form action="welcome.action" method="post" id="queryForm">
        <fieldset>
            <legend style="font-size:14px">查询条件</legend>

            <table border="0" cellspacing="0" cellpadding="2">
                <col width="150"/>
                <col width="100"/>
                <col/>
                <tr>
                    <td><label style="font-size:12px">查询条件 </label></td>
                    <td>
                        mingcheng<input name="sopGroup.name" maxlength="11" type="text" class="txt"/>
                        id<input name="sopGroup.id" maxlength="11" type="text" class="txt"/>
                    <td nowrap="nowrap">
                        <div>
                            <input type="submit" class="button" onclick="" value="查 询"/>
                            <input type="reset" class="button" onclick="" value="重 置"/>
                        </div>
                    </td>
                </tr>
            </table>
        </fieldset>
    </form>
</div>
<br/>

<div id="formContainer">
        <div class="detailTitleDiv"><label style="font-size:14px">系统用户信息列表</label></div>
        <div style="text-align: left;font-size:12px;padding: 0 5px 0 5px;line-height: 200%;color: #003399;">
            共找到3条记录,显示所有记录
        </div>
        <div class="formDiv">
            <table width="100%" border="0" cellspacing="1" cellpadding="1">
                <col width="80"/>
                <col width="120"/>
                <col width="80"/>
                <col/>

                <tr class="detailTitleDiv">
                    <td class="formTdField" style="text-align:center;"><img
                            src="<%=request.getContextPath()%>/css/img/icon_correct.gif" alt="">
                    </td>
                    <td class="formTdField">用户名</td>
                    <td class="formTdField">用户全称</td>
                    <td class="formTdField">电话</td>
                    <td class="formTdField">电子邮件</td>
                    <td class="formTdField">角色名称</td>
                    <td class="formTdField">状态</td>
                    <td class="formTdField">操作</td>
                </tr>

                <tr>
                    <td class="formTdField" style="text-align:center;">
                        <div><input type="checkbox" id="checkbox1" name="checkbox"></div>
                    </td>
                    <td class="formTdField">hemeijin</td>
                    <td class="formTdField">何美劲</td>
                    <td class="formTdField">15999565334</td>
                    <td class="formTdField">hemeijin126@126.com</td>
                    <td class="formTdField">系统管理员</td>
                    <td class="formTdField">已开通</td>
                    <td class="formTdField"><img src="<%=request.getContextPath()%>/css/img/icon_incorrect.gif" alt="">
                    </td>
                </tr>

                <tr>
                    <td class="formTdField" style="text-align:center;">
                        <div><input type="checkbox" id="checkbox2" name="checkbox"></div>
                    </td>
                    <td class="formTdField">hemeijin</td>
                    <td class="formTdField">何美劲</td>
                    <td class="formTdField">15999565334</td>
                    <td class="formTdField">hemeijin126@126.com</td>
                    <td class="formTdField">系统管理员</td>
                    <td class="formTdField">已开通</td>
                    <td class="formTdField"><img src="<%=request.getContextPath()%>/css/img/icon_incorrect.gif" alt="">
                    </td>
                </tr>

                <tr>
                    <td class="formTdField" style="text-align:center;">
                        <div><input type="checkbox" id="checkbox3" name="checkbox"></div>
                    </td>
                    <td class="formTdField">hemeijin</td>
                    <td class="formTdField">何美劲</td>
                    <td class="formTdField">15999565334</td>
                    <td class="formTdField">hemeijin126@126.com</td>
                    <td class="formTdField">系统管理员</td>
                    <td class="formTdField">已开通</td>
                    <td class="formTdField"><img src="<%=request.getContextPath()%>/css/img/icon_incorrect.gif" alt="">
                    </td>
                </tr>

                <tr>
                    <td class="formTdField" style="text-align:center;">
                        <div><input type="checkbox" id="checkbox4" name="checkbox"></div>
                    </td>
                    <td class="formTdField">hemeijin</td>
                    <td class="formTdField">何美劲</td>
                    <td class="formTdField">15999565334</td>
                    <td class="formTdField">hemeijin126@126.com</td>
                    <td class="formTdField">系统管理员</td>
                    <td class="formTdField">已开通</td>
                    <td class="formTdField"><img src="<%=request.getContextPath()%>/css/img/icon_incorrect.gif" alt="">
                    </td>
                </tr>

                <tr>
                    <td class="formTdField" style="text-align:center;">
                        <div><input type="checkbox" id="checkbox5" name="checkbox"></div>
                    </td>
                    <td class="formTdField">hemeijin</td>
                    <td class="formTdField">何美劲</td>
                    <td class="formTdField">15999565334</td>
                    <td class="formTdField">hemeijin126@126.com</td>
                    <td class="formTdField">系统管理员</td>
                    <td class="formTdField">已开通</td>
                    <td class="formTdField"><img src="<%=request.getContextPath()%>/css/img/icon_incorrect.gif" alt="">
                    </td>
                </tr>

                <tr>
                    <td class="formTdField" style="text-align:center;">
                        <div><input type="checkbox" id="checkbox6" name="checkbox"></div>
                    </td>
                    <td class="formTdField">hemeijin</td>
                    <td class="formTdField">何美劲</td>
                    <td class="formTdField">15999565334</td>
                    <td class="formTdField">hemeijin126@126.com</td>
                    <td class="formTdField">系统管理员</td>
                    <td class="formTdField">已开通</td>
                    <td class="formTdField"><img src="<%=request.getContextPath()%>/css/img/icon_incorrect.gif" alt="">
                    </td>
                </tr>

                <tr>
                    <td></td>
                    <td colspan="6">
                        <div class="formButton">
                            <div><img src='<%=request.getContextPath()%>/css/img/page_up.gif'/><a href="innerPage.jsp#"><font
                                    style="font-size:12px">上一页</font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
                                    href="innerPage.jsp#"><font style="font-size:12px">下一页</font></a><img
                                    src='<%=request.getContextPath()%>/css/img/page_down.gif'/></div>
                        </div>
                    </td>
                    <td></td>
                </tr>
            </table>
        </div>
</div>
<div style="text-align: left;padding: 0 5px 0 5px;line-height: 200%;color: #003399;">&nbsp;</div>
<div>
    <form>
        <div class="formDiv">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <col width="400"/>
                <col/>

                <tr>
                    <td>
                        <div class="formButton" style="text-align:center;">
                            <input type="button" class="button" onclick="" value="删除已选"/>
                        </div>
                    </td>
                    <td>
                        <div class="formButton" style="text-align:left;">
                            <input type="button" class="button" onclick="" value="添加"/>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
<br/>
<fieldset>
    <legend><label style="font-size:14px">温馨提示</label></legend>
    <ol>
        <li style="font-size:12px">温馨提示内容1
        </li>
        <li style="font-size:12px">温馨提示内容2
        </li>

    </ol>
</fieldset>
</body>
</html>