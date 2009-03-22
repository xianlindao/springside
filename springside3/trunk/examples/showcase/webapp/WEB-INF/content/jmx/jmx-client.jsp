<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>JMX演示用例</title>
	<%@ include file="/common/meta.jsp"%>
	<link href="${ctx}/css/default.css" type="text/css" rel="stylesheet"/>
	<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript">
		//动态提交表单保存服务器配置.
		function saveConfig(){
			$.get("jmx-client!saveConfig.action?" + $("form").serialize(), function(data){
				$('#saveMessage').text(data).show().fadeOut(2000);
			});
		}

		//动态获取服务器最新配置,返回JSON对象.
		function updateConfig(){
			$.getJSON("jmx-client!updateConfig.action", function(data){
				$('#nodeName').val(data.nodeName);
				$('input:radio[name=statisticsEnabled]').val([data.statisticsEnabled]);
				$('#updateMessage').text(data.message).show().fadeOut(2000);
			});
		}
	
		//动态获取服务器最新状态,返回JSON对象.
		function updateStatics(){
			$.getJSON("jmx-client!updateStatics.action", function(data) {
				$('#queryUserCount').val(data.queryUserCount);
				$('#modifyUserCount').val(data.modifyUserCount);
				$('#deleteUserCount').val(data.deleteUserCount);
			});
		}
	</script>
</head>
<body>
	<h3>JMX演示用例</h3>
	<p>技术说明：演示MBean代理、MXBean代理及直接读取属性三种模式。<br/>
	客户端亦可使用JConsole, 远程连接URL为 service:jmx:rmi:///jndi/rmi://localhost:1099/showcase<br/></p>
	<p>用户故事：使用JMX动态配置系统变量并实时监控系统运行统计。</p>
	<h4>系统配置</h4>
	<form id="configForm">
	<table>
		<tr>
			<td>服务器节点名:</td>
			<td><input id="nodeName" name="nodeName" value="${nodeName}" /></td>
		</tr>
		<tr>
			<td>是否收集统计信息:</td>
			<td><s:radio id="statisticsEnabled" name="statisticsEnabled" value="statisticsEnabled" list="#{'true':'是', 'false':'否'}" theme="simple"/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="button" value="保存配置" onclick="saveConfig();" />&nbsp;&nbsp;
				<input type="button" value="刷新配置" onclick="updateConfig();" />
				<span id="saveMessage"></span><span id="updateMessage"></span>
			</td>
		</tr>
	</table>
	</form>

	<table  cellspacing="20">
	<tr>
	<td>
	<h4>系统运行统计</h4>
	
	<table>
		<tr>
			<td>查询用户列表次数:</td>
			<td>
				<input id="queryUserCount" value="${serverStatistics.queryUserCount}" readonly="readonly" size="5"/> 
			</td>
		</tr>
		<tr>
			<td>更改用户次数:</td>
			<td>
				<input id="modifyUserCount" value="${serverStatistics.modifyUserCount}" readonly="readonly" size="5"/> 
			</td>
		</tr>
		<tr>
			<td>删除用户次数:</td>
			<td>
				<input id="deleteUserCount" value="${serverStatistics.deleteUserCount}" readonly="readonly" size="5"/> 
			</td>
		</tr>
		<tr>
			<td colspan="2"><input type="button" value="刷新状态" onclick="updateStatics();" /></td>
		</tr>
	</table>
	</td>
	<td valign="top">
	<h4>Hibernate运行统计</h4>
	<p>
		打开数据库连接:${hibernateStatistics.sessionOpenCount}<br/>
		关闭数据库连接:${hibernateStatistics.sessionCloseCount}
	</p>
	</td>
	</tr>
	</table>

	<div><a href="#/" target="_blank">打开新窗口并行操作</a>、<a href="${ctx}/">返回首页</a></div>
</body>
</html>