<%@page import="main.ResultMgr"%>
<%@page import="main.MainProcess"%>
<%@page import="main.MainMgr"%>
<%@page import="main.MainBean"%>
<%@page contentType="text/html; charset=EUC-KR"%>


<%
	request.setCharacterEncoding("EUC-KR");

	int tempCalc;
	int reVisit=1;
	if(request.getParameter("isVisit")==null){
		reVisit = 0;
	}
	
	String name = session.getAttribute("name").toString();
	MainBean bean = new MainBean();
	MainMgr mgr = new MainMgr();
	bean.setName(name);
	bean = mgr.bringAll(bean, reVisit);
	int triFinished = 0;
	
	if(bean.getCharStatus()==8){
		ResultMgr rMgr = new ResultMgr();
		triFinished = rMgr.onYourMarks(bean);
		bean.setCharStatus(9);
		mgr.SaveAll(bean);
	}
	
	if(triFinished==0){
		response.sendRedirect("wait4race.jsp");
	}else if(triFinished==1){
		response.sendRedirect("race.jsp");
	}
	
%> 

<html>
	<head>
		<title>��⸦ �غ����� <%=name%></title>
		<style>
		#back{
			background: url("imgs/triathlon.jpg") no-repeat;
			width: 960px;
			height: 684px;
			position: absolute;
			top: 50%;
			left: 50%;
			z-index: 0;
			margin: -342px 0 0 -480px;
		}
		#tri{
			position: absolute;
			top: 590px;
			left: 100px;
			font-size: 20px;
			font-weight: bold;
			color: #66FF66;
			text-align: center;
		}
		</style>
	</head>
	<body>
	<div id="back">
		<div id="tri">
			<%=name%>�� ü�븦 �����Ͽ�, ������ �յΰ� �ִ�! <br>
        	������ 10���� ���� ��Ⱑ ���۵˴ϴ�!
    	</div>
	</div>
	</body>
</html>