<%@page import="Login.Enter"%>
<%@page import="Login.LoginBean"%>

<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
   
    // 아이디와 암호를 db에 기록하기 위한 엠지알클래스(Enter)와
	// 클래스에 값을 포장단위로 던져주기 위한 빈클래스(LoginBean)를 선언해 엠지알의 registerDB 매서드에 값을 담아 던진다.
	request.setCharacterEncoding("utf-8");
	String id = request.getParameter("id");
	String pass = request.getParameter("pass");
	Enter ent = new Enter();
	LoginBean bean = new LoginBean();
	bean.setId(id);
	bean.setPass(pass);
	String dupleError = "";
	
	if(ent.registerUnique(bean)){
		ent.registerDB(bean);
		bean = ent.LoginDB(bean);
		session.setAttribute("id", id);
		session.setAttribute("idn", bean.getIdn());
		response.sendRedirect("paradise.jsp");
		
	}else{
		dupleError = "아이디가 중복됩니다.";
	}
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
	
	<%=dupleError %>

</body>
</html>