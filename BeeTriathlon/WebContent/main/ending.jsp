<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	request.setCharacterEncoding("EUC-KR");
	String name = session.getAttribute("name").toString();
	int grade = Integer.parseInt(session.getAttribute("grade").toString());
	String context="";
	
	switch(grade){
	case 1:
		context="<div class='title'><p>��ȸ</p></div><p>��� ���� ���� ��⿡�� ����� ������ �ŵξ���.</p><p>�׿� ���� �������� ���� �� �� �ִ� ��Ư�� ��ѱ� ����� ��� Ƽ���� �����޾Ҵ�.</p><p>�����̴� ��ٷ� �θ�Կ��� ������ �޶� ���Ͽ���.</p><p>��Ư�� ��ѱ�� ���� ������ ������ �ִ� ��ѱ�鿡�� �ڷ��Ľø� ������ ������ �θ���� �������� �ľ��ߴ�.</p><p>��ħ��, �����̴� ���ƿö���.</p><p>����,�ƺ� �����̰� ���Կ�!</p>";
		break;
	case 2:
		context="<div class='title'><p>�޸���� ������ �ʾ�!</p></div><p>��Ư�� ��ѱ� Ƽ���� �������� ���� �����̴� ���ǿ� �������� �ٽ� �� �� �����غ���� ���� �Ծ���.</p> <p>���� �� ü������ ����ϱ� ���� 2��� �־��� �ҷ��� ��ݰ� �������� �翬�� ������ ���� �����ڵ��� ��α����� �ｺ���� ���ȴ�.</p><p> ���� ���� ����� ���� �ֹε鿡�� �ｺ���� ����� �����ߴ�.</p><p>5�� �� ���ƿ� ���� ��⸦ ���� �����̴� ���õ� �޸���.</p><p>�� �θ���� ������ ���ž�!</p>";
		break;
	case 3:
		context="<div class='title'><p>����</p></div><p>�����ϴ� ���� �־��̶� ������ ������ �ŵ� �����̴� ���� ���� �Ѵ��� ���´�.</p><p>���� ������ Ȳ���ذ����� �������� ���ο� ������ �������� ����� �־���.</p><p>������������ �ڽ����� �������� ���� �̰��� ��� ���̴�.</p><p>������ �������� �ҹɸ����� ������ �θ� �ŵ� �� �ִ� �����̾���.</p><p>������ ���̶�� Ȳ��Ƽ���� �� �� �־�!</p><p>���� ������ ������ Ƣ����.</p><p>�����̴� �� ��� �ҹɸ��� �ڰ����� ����ߴ�.</p><p>�׳�, �����̴� ������ �������� �ٶ󺸸� ����� ���� �߰ſ� ������ ��ȴ�.</p><p>����,����,�ƺ� ���� ã�� ���Կ�! ���ݸ� ��ٷ��ּ���!</p>";
		break;
	}
	
%>

<html>
	<head>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<meta charset="EUC-KR">
	<title>Insert title here</title>
	<style>
		body {
	  width: 100%;
	  height: 100%;
	  background: url("imgs/ending<%=grade%>.jpg") no-repeat center top ;
	  background-size: contain;
	  overflow: hidden;
	}
	body{
	}
	
	
	.star-wars {
	  display: flex;
	  justify-content: center;
	  position: relative;
	  height: 800px;
	  color: #000000;
	  font-family: 'Pathway Gothic One', sans-serif;
	  font-size: 500%;
	  font-weight: 600;
	  letter-spacing: 6px;
	  line-height: 150%;
	  perspective: 400px;
	  text-align: justify;
	}
	
	.crawl {
	  position: relative;
	  top: 99999px;
	  transform-origin: 50% 100%;
	  animation: crawl 60s linear;
	}
	
	.crawl > .title {
	  font-size: 90%;
	  text-align: center;
	}
	
	.crawl > .title h1 {
	  margin: 0 0 100px;
	  text-transform: uppercase;
	}
	
	@keyframes crawl {
		0% {
	    top: -100px;
	    transform: rotateX(20deg)  translateZ(0);
	  }
		100% { 
	    top: -6000px;
	    transform: rotateX(25deg) translateZ(-2500px);
	  }
	}
	
	.button{
		top: 90%;
		left: 70%;
		position:absolute;
		z-index: 10;
	}
	</style>
	
	</head>
	<body>
	<a href="../enterance/login.html"><input type="button" value="===��ŵ�ϱ�==="  class="button"></a>
	
	<section class="star-wars">
	  <div class="crawl">
	   <%=context %>
	  </div>
	</section>
	<script>
		setTimeout(function() { location.href="../enterance/login.html";}, 30000)
	</script>
	</body>
</html>