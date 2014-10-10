<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery-1.4.4.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/position/position.js"></script>


<title>广告位占用状态</title>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
});
</script>
<style>
.treven {
	background: #FFFFFF;
}

.trodd {
	background: #f6f6f6;
}
</style>
</head>

<body>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td>
		<table cellpadding="0" cellspacing="0" border="0" width="100%"
			class="tab_right">
			<tr>
				<td>工作台</td>
				<td>客户</td>
				<td>日程行动</td>
				<td>销售机会</td>
				<td>订单管理</td>
				<td>客服中心</td>
				<td>财务中心</td>
				<td>营销中心</td>
				<td>人力资源中心</td>
				<td>数据统计</td>
				<td>信息门户管理</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td style="padding: 4px;">
		<table cellpadding="0" cellspacing="1" width="100%" class="tablea">
			<tr>
				<td class="formTitle" colspan="99"><span>·广告位占用状态查询</span></td>
			</tr>
			<tr>
				<td class="td_label">广告资产类别：</td>
				<td class="td_input">
					<input class="e_input"
					onfocus="this.className='e_inputFocus'"
					onblur="this.className='e_input'" />
				</td>
				<td class="td_label">广告为所属区域：</td>
				<td class="td_input">
					<input class="e_input"
					onfocus="this.className='e_inputFocus'"
					onblur="this.className='e_input'" />
				</td>
			</tr>
			<tr>
				<td class="formBottom" colspan="99">
					<input name="searchPositionSubmit" id="searchPositionSubmit" type="submit" title="查看" class="b_search" value=""/>
					<input name="addPositionSubmit" id="addPositionSubmit" type="submit" title="添加" class="b_add" value=""/>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td style="padding: 4px;overflow-X:scroll">
			<div style="overflow: auto;">  
				<table cellpadding="0" cellspacing="1" width="100%"
			class="taba_right_list" id="bm">
					<tr>
						<td colspan="23" style="padding-left: 8px; background: url(<%=path %>/images/menu_righttop.png) repeat-x; text-align: left; height: 26px;"><span>·广告位列表</span></td>
					</tr>
					<tr>
						<td height="26px" align="center">序号</td>
						<td>广告位类型编码</td>
						<td>广告位特征值</td>
						<td>广告位中文名称</td>
						<td>广告位英文名称</td>
						<td>区域-左</td>
						<td>区域-右</td>
						<td>区域-高</td>
						<td>区域-宽</td>
						<td>区域-颜色</td>
						<td>素材类型</td>
						<td>是否高清</td>
						<td>是否轮询</td>
						<td>是否叠加</td>
						<td>投放方式</td>
						<td>停留时间</td>
						<td>轮询个数</td>
						<td>价格</td>
						<td>是否赠送</td>
						<td>所属频道</td>
						<td>所属区域</td>
						<td>栏目</td>
						<td>操作</td>
					</tr>
					<tr>
						<td align="center" height="26">0</td>
						<td>1</td>
						<td>2</td>
						<td>3</td>
						<td>4</td>
						<td>5</td>
						<td>6</td>
						<td>7</td>
						<td>8</td>
						<td>9</td>
						<td>10</td>
						<td>11</td>
						<td>12</td>
						<td>13</td>
						<td>14</td>
						<td>15</td>
						<td>16</td>
						<td>17</td>
						<td>18</td>
						<td>19</td>
						<td>20</td>
						<td>21</td>
						<td>
							<input name="deletePosition" type="submit" class="button_delete" value="" title="删除" onfocus=blur() onClick="deletePosition(1)" />
							<input name="modifyPosition" type="submit" class="button_halt" value="" title="修改" onfocus=blur() onClick="modifyPosition(1)" />
							<input name="viewOccupyStatesPosition" type="submit" class="button_start" value="" title="查看占用状态" onfocus=blur() onClick="viewOccupyStatesPosition(1)" />
							<input name="viewOccupyTimePosition" type="submit" class="button_start" value="" title="查看占用时间" onfocus=blur() onClick="viewOccupyTimePosition(1)" />
							<input name="viewPosition" type="submit" class="button_start" value="" title="查看" onfocus=blur() onClick="viewPosition(1)" />
						</td>
					</tr>
					<tr>
						<td height="26px" align="center">0</td>
						<td>1</td>
						<td>2</td>
						<td>3</td>
						<td>4</td>
						<td>5</td>
						<td>6</td>
						<td>7</td>
						<td>8</td>
						<td>9</td>
						<td>10</td>
						<td>11</td>
						<td>12</td>
						<td>13</td>
						<td>14</td>
						<td>15</td>
						<td>16</td>
						<td>17</td>
						<td>18</td>
						<td>19</td>
						<td>20</td>
						<td>21</td>
						<td>
							<input name="deletePosition" type="submit" class="button_delete" value="" title="删除" onfocus=blur() onClick="deletePosition(1)" />
							<input name="modifyPosition" type="submit" class="button_halt" value="" title="修改" onfocus=blur() onClick="modifyPosition(1)" />
							<input name="viewOccupyStatesPosition" type="submit" class="button_start" value="" title="查看占用状态" onfocus=blur() onClick="viewOccupyStatesPosition(1)" />
							<input name="viewOccupyTimePosition" type="submit" class="button_start" value="" title="查看占用时间" onfocus=blur() onClick="viewOccupyTimePosition(1)" />
							<input name="viewPosition" type="submit" class="button_start" value="" title="查看" onfocus=blur() onClick="viewPosition(1)" />
						</td>
					</tr>
					<tr>
						<td height="26px" align="center">0</td>
						<td>1</td>
						<td>2</td>
						<td>3</td>
						<td>4</td>
						<td>5</td>
						<td>6</td>
						<td>7</td>
						<td>8</td>
						<td>9</td>
						<td>10</td>
						<td>11</td>
						<td>12</td>
						<td>13</td>
						<td>14</td>
						<td>15</td>
						<td>16</td>
						<td>17</td>
						<td>18</td>
						<td>19</td>
						<td>20</td>
						<td>21</td>
						<td>
							<input name="deletePosition" type="submit" class="button_delete" value="" title="删除" onfocus=blur() onClick="deletePosition(1)" />
							<input name="modifyPosition" type="submit" class="button_halt" value="" title="修改" onfocus=blur() onClick="modifyPosition(1)" />
							<input name="viewOccupyStatesPosition" type="submit" class="button_start" value="" title="查看占用状态" onfocus=blur() onClick="viewOccupyStatesPosition(1)" />
							<input name="viewOccupyTimePosition" type="submit" class="button_start" value="" title="查看占用时间" onfocus=blur() onClick="viewOccupyTimePosition(1)" />
							<input name="viewPosition" type="submit" class="button_start" value="" title="查看" onfocus=blur() onClick="viewPosition(1)" />
						</td>
					</tr>
					<tr>
						<td height="26px" align="center">0</td>
						<td>1</td>
						<td>2</td>
						<td>3</td>
						<td>4</td>
						<td>5</td>
						<td>6</td>
						<td>7</td>
						<td>8</td>
						<td>9</td>
						<td>10</td>
						<td>11</td>
						<td>12</td>
						<td>13</td>
						<td>14</td>
						<td>15</td>
						<td>16</td>
						<td>17</td>
						<td>18</td>
						<td>19</td>
						<td>20</td>
						<td>21</td>
						<td>
							<input name="deletePosition" type="submit" class="button_delete" value="" title="删除" onfocus=blur() onClick="deletePosition(1)" />
							<input name="modifyPosition" type="submit" class="button_halt" value="" title="修改" onfocus=blur() onClick="modifyPosition(1)" />
							<input name="viewOccupyStatesPosition" type="submit" class="button_start" value="" title="查看占用状态" onfocus=blur() onClick="viewOccupyStatesPosition(1)" />
							<input name="viewOccupyTimePosition" type="submit" class="button_start" value="" title="查看占用时间" onfocus=blur() onClick="viewOccupyTimePosition(1)" />
							<input name="viewPosition" type="submit" class="button_start" value="" title="查看" onfocus=blur() onClick="viewPosition(1)" />
						</td>
					</tr>
					<tr>
						<td height="26px" align="center">0</td>
						<td>1</td>
						<td>2</td>
						<td>3</td>
						<td>4</td>
						<td>5</td>
						<td>6</td>
						<td>7</td>
						<td>8</td>
						<td>9</td>
						<td>10</td>
						<td>11</td>
						<td>12</td>
						<td>13</td>
						<td>14</td>
						<td>15</td>
						<td>16</td>
						<td>17</td>
						<td>18</td>
						<td>19</td>
						<td>20</td>
						<td>21</td>
						<td>
							<input name="deletePosition" type="submit" class="button_delete" value="" title="删除" onfocus=blur() onClick="deletePosition(1)" />
							<input name="modifyPosition" type="submit" class="button_halt" value="" title="修改" onfocus=blur() onClick="modifyPosition(1)" />
							<input name="viewOccupyStatesPosition" type="submit" class="button_start" value="" title="查看占用状态" onfocus=blur() onClick="viewOccupyStatesPosition(1)" />
							<input name="viewOccupyTimePosition" type="submit" class="button_start" value="" title="查看占用时间" onfocus=blur() onClick="viewOccupyTimePosition(1)" />
							<input name="viewPosition" type="submit" class="button_start" value="" title="查看" onfocus=blur() onClick="viewPosition(1)" />
						</td>
					</tr>
					<tr>
						<td height="26px" align="center">0</td>
						<td>1</td>
						<td>2</td>
						<td>3</td>
						<td>4</td>
						<td>5</td>
						<td>6</td>
						<td>7</td>
						<td>8</td>
						<td>9</td>
						<td>10</td>
						<td>11</td>
						<td>12</td>
						<td>13</td>
						<td>14</td>
						<td>15</td>
						<td>16</td>
						<td>17</td>
						<td>18</td>
						<td>19</td>
						<td>20</td>
						<td>21</td>
						<td>
							<input name="deletePosition" type="submit" class="button_delete" value="" title="删除" onfocus=blur() onClick="deletePosition(1)" />
							<input name="modifyPosition" type="submit" class="button_halt" value="" title="修改" onfocus=blur() onClick="modifyPosition(1)" />
							<input name="viewOccupyStatesPosition" type="submit" class="button_start" value="" title="查看占用状态" onfocus=blur() onClick="viewOccupyStatesPosition(1)" />
							<input name="viewOccupyTimePosition" type="submit" class="button_start" value="" title="查看占用时间" onfocus=blur() onClick="viewOccupyTimePosition(1)" />
							<input name="viewPosition" type="submit" class="button_start" value="" title="查看" onfocus=blur() onClick="viewPosition(1)" />
						</td>
					</tr>
					<tr>
						<td height="26px" align="center">0</td>
						<td>1</td>
						<td>2</td>
						<td>3</td>
						<td>4</td>
						<td>5</td>
						<td>6</td>
						<td>7</td>
						<td>8</td>
						<td>9</td>
						<td>10</td>
						<td>11</td>
						<td>12</td>
						<td>13</td>
						<td>14</td>
						<td>15</td>
						<td>16</td>
						<td>17</td>
						<td>18</td>
						<td>19</td>
						<td>20</td>
						<td>21</td>
						<td>
							<input name="deletePosition" type="submit" class="button_delete" value="" title="删除" onfocus=blur() onClick="deletePosition(1)" />
							<input name="modifyPosition" type="submit" class="button_halt" value="" title="修改" onfocus=blur() onClick="modifyPosition(1)" />
							<input name="viewOccupyStatesPosition" type="submit" class="button_start" value="" title="查看占用状态" onfocus=blur() onClick="viewOccupyStatesPosition(1)" />
							<input name="viewOccupyTimePosition" type="submit" class="button_start" value="" title="查看占用时间" onfocus=blur() onClick="viewOccupyTimePosition(1)" />
							<input name="viewPosition" type="submit" class="button_start" value="" title="查看" onfocus=blur() onClick="viewPosition(1)" />
						</td>
					</tr>
					<tr>
						<td align="center">0</td>
						<td>1</td>
						<td>2</td>
						<td>3</td>
						<td>4</td>
						<td>5</td>
						<td>6</td>
						<td>7</td>
						<td>8</td>
						<td>9</td>
						<td>10</td>
						<td>11</td>
						<td>12</td>
						<td>13</td>
						<td>14</td>
						<td>15</td>
						<td>16</td>
						<td>17</td>
						<td>18</td>
						<td>19</td>
						<td>20</td>
						<td>21</td>
						<td>
							<input name="deletePosition" type="submit" class="button_delete" value="" title="删除" onfocus=blur() onClick="deletePosition(1)" />
							<input name="modifyPosition" type="submit" class="button_halt" value="" title="修改" onfocus=blur() onClick="modifyPosition(1)" />
							<input name="viewOccupyStatesPosition" type="submit" class="button_start" value="" title="查看占用状态" onfocus=blur() onClick="viewOccupyStatesPosition(1)" />
							<input name="viewOccupyTimePosition" type="submit" class="button_start" value="" title="查看占用时间" onfocus=blur() onClick="viewOccupyTimePosition(1)" />
							<input name="viewPosition" type="submit" class="button_start" value="" title="查看" onfocus=blur() onClick="viewPosition(1)" />
						</td>
					</tr>
					<tr>
						<td align="center">0</td>
						<td>1</td>
						<td>2</td>
						<td>3</td>
						<td>4</td>
						<td>5</td>
						<td>6</td>
						<td>7</td>
						<td>8</td>
						<td>9</td>
						<td>10</td>
						<td>11</td>
						<td>12</td>
						<td>13</td>
						<td>14</td>
						<td>15</td>
						<td>16</td>
						<td>17</td>
						<td>18</td>
						<td>19</td>
						<td>20</td>
						<td>21</td>
						<td>
							<input name="deletePosition" type="submit" class="button_delete" value="" title="删除" onfocus=blur() onClick="deletePosition(1)" />
							<input name="modifyPosition" type="submit" class="button_halt" value="" title="修改" onfocus=blur() onClick="modifyPosition(1)" />
							<input name="viewOccupyStatesPosition" type="submit" class="button_start" value="" title="查看占用状态" onfocus=blur() onClick="viewOccupyStatesPosition(1)" />
							<input name="viewOccupyTimePosition" type="submit" class="button_start" value="" title="查看占用时间" onfocus=blur() onClick="viewOccupyTimePosition(1)" />
							<input name="viewPosition" type="submit" class="button_start" value="" title="查看" onfocus=blur() onClick="viewPosition(1)" />
						</td>
					</tr>
					<tr>
						<td align="center">0</td>
						<td>1</td>
						<td>2</td>
						<td>3</td>
						<td>4</td>
						<td>5</td>
						<td>6</td>
						<td>7</td>
						<td>8</td>
						<td>9</td>
						<td>10</td>
						<td>11</td>
						<td>12</td>
						<td>13</td>
						<td>14</td>
						<td>15</td>
						<td>16</td>
						<td>17</td>
						<td>18</td>
						<td>19</td>
						<td>20</td>
						<td>21</td>
						<td>
							<input name="deletePosition" type="submit" class="button_delete" value="" title="删除" onfocus=blur() onClick="deletePosition(1)" />
							<input name="modifyPosition" type="submit" class="button_halt" value="" title="修改" onfocus=blur() onClick="modifyPosition(1)" />
							<input name="viewOccupyStatesPosition" type="submit" class="button_start" value="" title="查看占用状态" onfocus=blur() onClick="viewOccupyStatesPosition(1)" />
							<input name="viewOccupyTimePosition" type="submit" class="button_start" value="" title="查看占用时间" onfocus=blur() onClick="viewOccupyTimePosition(1)" />
							<input name="viewPosition" type="submit" class="button_start" value="" title="查看" onfocus=blur() onClick="viewPosition(1)" />
						</td>
					</tr>
		
					<tr>
						<td height="34" colspan="23"
							style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: right;"><a
							href="#">共计10页</a>&nbsp;&nbsp;<a href="#">当前第1/10页</a>&nbsp;&nbsp;<a
							href="#">上一页</a>&nbsp;&nbsp;<a href="#">下一页&nbsp;&nbsp;</a></td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>
<div style="position: absolute; width: 100%; left: 0px; bottom: 0px;">
<table cellpadding="0" cellspacing="0" border="0" class="footer">
	<tr>
		<td>22</td>
	</tr>
</table>
</div>
</body>
</html>