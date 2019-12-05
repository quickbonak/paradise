<%@page import="main.MainMgr"%>
<%@page import="main.MainBean"%>
<%@page import="java.util.Random"%>
<%@page contentType="text/html; charset=EUC-KR"%>
<%
		request.setCharacterEncoding("EUC-KR");


	String name = session.getAttribute("name").toString();
	MainBean bean = new MainBean();
	MainMgr mgr = new MainMgr();
	bean.setName(name);
	bean = mgr.bringAll(bean, 1);
	
	
	if(!(bean.getEvent().equals("chestbox"))){
		response.sendRedirect("../main/main.jsp");
	}

	
		Random random = new Random();
		int chance = random.nextInt(100);
	if(60>chance){
		out.println("µ·");
	}else if(90>chance){
		out.println("Àºµµ³¢");
	}else{
		out.println("±Ýµµ³¢");
	}
	
	
	bean.setEvent("");
	mgr.SaveAll(bean);
	response.sendRedirect("../main/main.jsp");
%>
