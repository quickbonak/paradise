<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	request.setCharacterEncoding("EUC-KR");
	String name = session.getAttribute("name").toString();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>경기를 준비중인 <%=name%></title>
		<style>
		#back{
			background: url("imgs/triathlon.jpg") no-repeat;
			width: 960px;
			height: 684px;
			position: absolute;
			top: 50%;
			left: 50%;
			z-index: 0;
			margin: -342px 0 0 -480px;
		}
		#tri{
			position: absolute;
			top: 590px;
			left: 100px;
			font-size: 20px;
			font-weight: bold;
			color: #66FF66;
			text-align: center;
		}
		</style>
	</head>
	<body>
	<div id="back">
		<div id="tri">
			<%=name%>는 체대를 졸업하였다. 부모님을 찾기 위해 이름을 널리 알려야지! <br>
        	꿀벌 철인 경기에 등록되었습니다. 선수가 10명이 차면 경기가 시작됩니다! <br>
        	다음에 다시 로그인 해주세요!
    	</div>
	</div>
	</body>
</html>