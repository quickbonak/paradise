<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><%=session.getAttribute("name").toString() %> 여기 잠들다.</title>
<style>
	#back{
		background: url("imgs/restInPeace.jpg") no-repeat;
		width: 960px;
		height: 684px;
		position: absolute;
		top: 50%;
		left: 50%;
		z-index: 0;
		margin: -342px 0 0 -480px;
	}
	#rip{
		position: absolute;
		top: 290px;
		left: 265px;
		font-size: 15px;
		font-weight: bold;
		text-align: center;
	}
</style>
</head>
<body>
<div id="back">
	<div id="rip">
		<%=session.getAttribute("name").toString() %><br>
		여기 평화롭게 잠들다.<br><br>
		나는 모든 것을<br>
		갖고자 했지만<br>
		결국 아무것도<br>
		갖지 못했다.
		
	</div>
</div>

</body>
</html>