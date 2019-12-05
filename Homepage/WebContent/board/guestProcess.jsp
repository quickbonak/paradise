<%@page import="board.BoardBean"%>
<%@page import="board.BoardMgr"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	request.setCharacterEncoding("utf-8");
	int signal = Integer.parseInt(request.getParameter("guestSignal"));
	int postNum = Integer.parseInt(request.getParameter("postNum"));
	BoardMgr mgr = new BoardMgr();
	BoardBean bean = new BoardBean();
	switch(signal){
	case 1: 
		mgr.deletePost(postNum);
		response.sendRedirect("index.jsp");
		break;
	case 2:
		bean = mgr.getPostByIdx(postNum);
		break;
	}
%>
<link href="css/index.css" rel="stylesheet">
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>글수정</title>

</head>
<body>
	<table class="postcontent" style="border: 1px double #619991; height:350px; width:400px;" id="moditable">
		<form method="post" action="modify.jsp" name="modiForm">
		<input name="postNum" value="<%=postNum %>" style="display:none;">
		<tr><td style="background-color:white; height:20px;"><input type="text" name="title" placeholder="제목" style="width:99%; background-color:#fcfcfc;" value="<%=bean.getTitle() %>"></td></tr><br>
		<tr><td><textarea rows="10" cols="20" name="conten" style="width:99%; height:100%; background-color:#fcfcfc; resize: none;"><%=bean.getConten() %></textarea></td></tr><br>
		<tr><td style="text-align:right; height:30px;"><span class="number"  onclick="modi()">WRITER</span></td></tr>
		</form>
	</table>
	<script>
	function modi(){
		document.modiForm.submit();

	}
	</script>
</body>
</html>