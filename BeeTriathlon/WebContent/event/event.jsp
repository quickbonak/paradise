<%@page import="event.EventStart"%>
<%@page import="event.EventBean"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	String eventName = request.getParameter("eventName");
	EventBean eBean = new EventStart().eventSelect(eventName);
%>

<html>
<head>
<title></title>
<style>

#canvas{
	background: url("imgs/<%=eventName %>.jpg") no-repeat;
	position: absolute;
	top:50%;
	left:50%;
	width: 960px;
	height: 684px;
	margin:-342px 0px 0px -480px;
	z-index:0;
}

#choice{
	position: absolute;
	top: 80%;
	left: 50%;
	width: 600px;
	height: 100px;
	margin: -50px 0px 0px -300px;
	z-index: 10;
	background-color: white;
	opacity: 0.9;
	text-align: center;
}

#contexttd{
	background-color: khaki;
}

#sv, #eNum, #eName{
	position : absolute;
	display: none;
}

</style>
</head>
<body>

<div id="canvas">
	<form method="post" action="eventResult.jsp" id="fomfom">
		<input type="text" name="eventName" id="eName" value=<%=eBean.getName() %>>
		<input type="text" name="eventNum" id="eNum" value=<%=eBean.getNumber() %>>
		<input type="text" name="selectedValue" id="sv" value="">
	</form>
	<table id="choice">
		<tr id="contexttd">
			<td colspan="2">
				<%=eBean.getContext() %>
			</td>
		</tr>
		<tr>
			<td onclick="selectOne()" title="<%=eBean.getT1() %>">
				<%=eBean.getS1() %>
			</td>
		</tr>
		<tr>
			<td onclick="selectTwo()" title="<%=eBean.getT2() %>">
				<%=eBean.getS2() %>
			</td>
		</tr>
	</table>
</div>

<script>

	function selectOne(){
		document.getElementById("sv").value = "1";
		document.getElementById("fomfom").submit();
	}
	
	function selectTwo(){
		document.getElementById("sv").value = "2";
		document.getElementById("fomfom").submit();
	}
</script>
</body>
</html>