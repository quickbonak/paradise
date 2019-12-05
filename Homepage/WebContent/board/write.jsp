<%@page import="board.BoardBean"%>
<%@page import="board.BoardMgr"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<% 
	request.setCharacterEncoding("utf-8");
	String title = request.getParameter("title");
	String conten = request.getParameter("conten");
	int idn = Integer.parseInt(session.getAttribute("idn").toString());
	BoardMgr boardMgr = new BoardMgr();
	BoardBean bean = new BoardBean();
	bean.setTitle(title);
	bean.setConten(conten);
	bean.setIdn(idn);
	boardMgr.writeDB(bean);
	response.sendRedirect("index.jsp");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>