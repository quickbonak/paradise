<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><%=session.getAttribute("name").toString() %> 졸업!</title>
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
		<%=session.getAttribute("name").toString() %> 무사히 살아남다!<br>
		운동을 열심히 했지만, 꿀벌대학 최고의 두뇌로서 살아갈 줄 그 누가 알았을까?<br>
		THE END
	</div>
</div>

</body>
</html>