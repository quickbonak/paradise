<%@page import="enterance.ClimateChangingArray"%>
<%@page import="enterance.RegisterMgr"%>
<%@page import="enterance.RegisterBean"%>
<%@page contentType="text/html; charset=EUC-KR"%>
<%
		request.setCharacterEncoding("EUC-KR");
%>
<%
	RegisterBean bean= new RegisterBean();
	RegisterMgr mgr= new RegisterMgr();
	
	String name = request.getParameter("name");
	String pass = request.getParameter("pass");

	bean.setName(name);
	bean.setPass(pass);
	
	
	if(mgr.RegisterUniqueName(bean)){
		out.print("���̵� �ߺ��˴ϴ�.");
	}else{
		mgr.RegisterInsert(bean);
		new ClimateChangingArray(name);
		session.setAttribute("name", name);
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
		  background: url("img/story1.jpg") no-repeat center top ;
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
		  color: #e95d66;
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
		<a href="../main/main.jsp"><input type="button" value="===��ŵ�ϱ�==="  class="button"></a>
		
		<section class="star-wars">
		  <div class="crawl">
		    <div class="title">
		      <p>�������� ���</p>
		      <h1>������ ����</h1>
		    </div>
		    
		    <p>�����̴� �θ�԰� ������������ ��ȭ�Ӱ� ��� �־���.</p>
		 <p>����� ��� �����̾���. </p>
		<p>���ظ� ��ġ�� �����ϴ� ���űⰡ ���������� �������� �־���. </p>
		<p>��å�� �ϰ� �ִ� ������ ������ �����ߴ� ���ű��� ���� �����.</p>
		 <p>���ű�� ��ü ���� �ϰ��ߴ�. ��ٷ� ������ ���� ���״�. </p>
		<p>ȥ���� ���� �θ���� �����̸� ����Ȱ� �ٵ�ٵ� ������. </p>
		<p>���̴�� �ִٰ��� ���� ���� �� ����.���ƺ��� �Ҹ��ƴ�.</p>
		<p>���ֶ� ����߰ھ��.�������� ���¢���̾���.</p>
		<p>��ħ�°� ���ű��� ���� ��¦ ��������.</p> 
		<p>ƴ ���̷� �귯 ���� �ٶ��� �θ���� �ü��� ��Ҵ�.�ü��� �ºپ���.</p> 
		<p>���� ������ �͵� ����  ǰ �ȿ� �״� �����̸� �� ������ ��������.</p>
		
		  </div>
		</section>
		<script>
			setTimeout(function() { location.href="regi2.html";}, 38000)
		</script>
		</body>
		</html>
		<%
	}
%>