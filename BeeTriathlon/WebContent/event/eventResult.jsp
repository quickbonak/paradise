<%@page import="event.EventProcess"%>
<%@page import="main.MainMgr"%>
<%@page import="main.MainBean"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	String eventName = request.getParameter("eventName");
	int eventNum = Integer.parseInt(request.getParameter("eventNum"));
	int eventSelected = Integer.parseInt(request.getParameter("selectedValue"));
	
	String name = session.getAttribute("name").toString();
	MainBean bean = new MainBean();
	MainMgr mgr = new MainMgr();
	bean.setName(name);
	bean = mgr.bringAll(bean, 0);
	
	bean = new EventProcess().doEvent(bean, eventName, eventNum, eventSelected);
	String eventContext = bean.getEvent();
	bean.setEvent("");
	mgr.SaveAll(bean);
	
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
	</form>
	<table id="choice" onclick="toTheMain()">
		<tr id="contexttd">
			<td>
				<%=eventContext %>
			</td>
		</tr>
	</table>
</div>

<script>

	function toTheMain(){
		location.href = "../main/main.jsp";
	}
</script>
</body>
</html>