<%@page import="main.MainMgr"%>
<%@page import="main.MainBean"%>
<%@page contentType="text/html; charset=EUC-KR"%>
<%
	request.setCharacterEncoding("EUC-KR");
	String selectedValue = request.getParameter("selectedValue").toString();
	String name = session.getAttribute("name").toString();
	MainBean bean = new MainBean();
	MainMgr mgr = new MainMgr();
	bean.setName(name);
	bean = mgr.bringAll(bean, 1);
	if(!(bean.getEvent().equals("bear"))){
		response.sendRedirect("../main/main.jsp");
	}
	
	switch(selectedValue){
	case "0":
		bean.setCharStatus(3);
		break;
	case "1":
		bean.setCharStatus(4);
		break;
	}
	
	
	bean.setEvent("");
	mgr.SaveAll(bean);
	
%>
<html>
<head>
<title></title>
<body>
<img src="img/gomsoon.png">
<a href="../main/main.jsp"><input type="button" value="³¡"></a>
</body>
</head>
</html>