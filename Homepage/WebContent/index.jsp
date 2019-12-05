<%@page import="passport.PassPort"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String showText = "포트폴리오 보기";
	if(session.getAttribute("thePage")!=null){
		
		String thePage	= session.getAttribute("thePage").toString();
		RequestDispatcher rd = request.getRequestDispatcher(thePage);
		rd.forward(request, response);
	}
	

	if(request.getParameter("pass")!=null){
		PassPort passPort = new PassPort();
		String thePage = passPort.passCheck(request.getParameter("pass"));
		if(thePage.equals("wrong")){
			showText="암호가 틀렸습니다.";
		}else if(thePage.equals("/paradise/portfolio1.html")){
			response.sendRedirect(thePage);
		}else{
			session.setAttribute("thePage", thePage);
			RequestDispatcher rd = request.getRequestDispatcher(thePage);
			rd.forward(request, response);
		}
	}
%>
<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>포트폴리오 대문</title>

<!-- Bootstrap core CSS -->
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom fonts for this template -->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link href="https://fonts.googleapis.com/css?family=Yeon+Sung"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/agency.min.css" rel="stylesheet">

</head>

<body id="page-top">

	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
		<div class="container"></div>
	</nav>

	<!-- Header -->
	<header class="masthead">
		<div class="container">
			<form method="post" name="loginForm" action="index.jsp">
				<div class="intro-text">
					<div class="intro-heading text-uppercase">
						<%=showText %><br> <input type="password" name="pass" placeholder="암호입력" style="width: 30%; font-size: 30px; height: 40px;"><br>
					</div>

					<a class="btn btn-primary btn-xs  js-scroll-trigger" onclick="login()">CONTINUE</a>
				</div>
			</form>
		</div>
	</header>


	<!-- Bootstrap core JavaScript -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Plugin JavaScript -->
	<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Contact form JavaScript -->
	<script src="js/jqBootstrapValidation.js"></script>
	<script src="js/contact_me.js"></script>

	<!-- Custom scripts for this template -->
	<script src="js/agency.min.js"></script>

	<script>
		function login(){
			document.loginForm.submit();
		}
	</script>

</body>

</html>