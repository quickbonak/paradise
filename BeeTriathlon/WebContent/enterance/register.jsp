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
		out.print("아이디가 중복됩니다.");
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
		<a href="../main/main.jsp"><input type="button" value="===스킵하기==="  class="button"></a>
		
		<section class="star-wars">
		  <div class="crawl">
		    <div class="title">
		      <p>꼬몽이의 비극</p>
		      <h1>여정의 시작</h1>
		    </div>
		    
		    <p>꼬몽이는 부모님과 벌레마을에서 평화롭게 살고 있었다.</p>
		 <p>어스름한 어느 저녁이었다. </p>
		<p>항해를 마치고 귀향하던 갈매기가 벌레마을을 지나가고 있었다. </p>
		<p>산책을 하고 있던 꼬몽이 가족이 출출했던 갈매기의 눈에 띄었다.</p>
		 <p>갈매기는 지체 없이 하강했다. 곧바로 가족을 집어 삼켰다. </p>
		<p>혼란에 빠진 부모님은 꼬몽이를 끌어안고 바들바들 떨었다. </p>
		<p>“이대로 있다가는 전부 죽을 것 같아.”아빠가 소리쳤다.</p>
		<p>“애라도 살려야겠어요.”엄마의 울부짖음이었다.</p>
		<p>마침맞게 갈매기의 입이 살짝 벌어졌다.</p> 
		<p>틈 사이로 흘러 들어온 바람이 부모님의 시선을 모았다.시선이 맞붙었다.</p> 
		<p>누가 먼저랄 것도 없이  품 안에 뒀던 꼬몽이를 입 밖으로 내던졌다.</p>
		
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