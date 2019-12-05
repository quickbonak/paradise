<%@page import="Login.Enter"%>
<%@page import="Login.LoginBean"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%

	// 로그인 페이지로부터 정보를 받아와 db와 교신하고 성공여부를 판별하는 엠지알클래스(Enter)와
	// 클래스에 값을 포장단위로 던져주기 위한 빈클래스(LoginBean)를 선언해 엠지알의 LoginDB 매서드에 값을 담아 던져
	// 인트형 변수에다가 성공 여부에 대한 결과를 받는다.

	request.setCharacterEncoding("utf-8");
	String isLogOut = request.getParameter("isLogOut");
	if(isLogOut.equals("1")){
		session.removeAttribute("id");
		session.removeAttribute("idn");
		response.sendRedirect("paradise.jsp");
	}
	String id = request.getParameter("id");
	String pass = request.getParameter("pass");
	Enter enter = new Enter();
	LoginBean bean = new LoginBean();
	bean.setId(id);
	bean.setPass(pass);
	bean = enter.LoginDB(bean);
	
	// 성공여부의 메시지 출력을 위한 변수를 선언하고, 인트값으로 넘어온 성공여부에 따라 출력 결과를 설정한다.
	String suc = "";
	
	if(enter.registerUnique(bean)){
		suc="아이디가 존재하지 않습니다.";
		session.removeAttribute("id");
		session.removeAttribute("idn");
	}else{
		if(bean.getIsSuccess()==1){
			// 로그인이 성공했으므로, 어떤 아이디로 로그인이 되어있는 상태인지 기억시키기 위해 세션에 id값을 넘긴다.
			// 우리의 경우 편하게 id 값만 넘겼지만, 실제로는 해킹방지를 위해 암호화된 id 값을 넘긴다.
			session.removeAttribute("id");
			session.removeAttribute("idn");
			session.setAttribute("id", id);
			session.setAttribute("idn", bean.getIdn());
			response.sendRedirect("paradise.jsp");
		}else{
			suc="암호가 틀렸습니다.";
			session.removeAttribute("id");
			session.removeAttribute("idn");
			
		}
	}
	
	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
	<%=session.getAttribute("idn") %> <br>
	<%=suc %>
</body>
</html>