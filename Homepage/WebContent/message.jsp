<%@page import="message.MessageMsg"%>
<%@page import="message.MessageBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
MessageBean bean = new MessageBean();
MessageMsg msg = new MessageMsg();

bean.setSender(request.getParameter("sender"));
bean.setSenderMail(request.getParameter("senderMail"));
bean.setPhoneNumber(request.getParameter("phoneNumber"));
bean.setMsgContent(request.getParameter("msgContent"));

msg.mailDB(bean);
response.sendRedirect("index.jsp");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>