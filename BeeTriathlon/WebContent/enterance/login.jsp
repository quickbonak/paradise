<%@page import="enterance.LoginMgr"%>
<%@page import="enterance.RegisterBean"%>
<%@page contentType="text/html; charset=EUC-KR"%>
<%
		request.setCharacterEncoding("EUC-KR");
%>

<%
	RegisterBean bean = new RegisterBean();
	LoginMgr mgr = new LoginMgr();
	String id =request.getParameter("id");
	String pass =request.getParameter("pass");
	
	
%>	

<% 
	bean.setName(id);
	bean.setPass(pass);
		
	if(mgr.LoginProcess(bean)){
		session.setAttribute("name", id);
		response.sendRedirect("../main/main.jsp");
	}else{
		
		out.println("<script>alert('캐릭터명 혹은 암호가 일치하지 않습니다.'); history.back();</script>");
	}
%>
