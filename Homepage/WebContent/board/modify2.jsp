<%@page import="board.BoardBean"%>
<%@page import="board.BoardMgr"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	request.setCharacterEncoding("utf-8");
	int postNum = Integer.parseInt(request.getParameter("postNum"));
	String title = request.getParameter("title");
	String conten = request.getParameter("conten");
	BoardMgr boardMgr = new BoardMgr();
	BoardBean bean = new BoardBean();
	bean.setTitle(title);
	bean.setConten(conten);
	bean.setIdx(postNum);
	boardMgr.updatePost(bean);
	response.sendRedirect("paradise.jsp");
	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>