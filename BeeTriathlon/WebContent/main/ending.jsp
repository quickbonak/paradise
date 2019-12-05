<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	request.setCharacterEncoding("EUC-KR");
	String name = session.getAttribute("name").toString();
	int grade = Integer.parseInt(session.getAttribute("grade").toString());
	String context="";
	
	switch(grade){
	case 1:
		context="<div class='title'><p>재회</p></div><p>명망 높은 국제 경기에서 우수한 성적을 거두었다.</p><p>그에 대한 보상으로 어디든 갈 수 있는 영특한 비둘기 여행권 골든 티켓을 수여받았다.</p><p>꼬몽이는 곧바로 부모님에게 데려다 달라 말하였다.</p><p>영특한 비둘기는 세계 곳곳에 포진해 있는 비둘기들에게 텔레파시를 보내어 꼬몽이 부모님의 소재지를 파악했다.</p><p>마침내, 꼬몽이는 날아올랐다.</p><p>엄마,아빠 꼬몽이가 갈게요!</p>";
		break;
	case 2:
		context="<div class='title'><p>달리기는 끝나지 않아!</p></div><p>영특한 비둘기 티켓을 쟁취하지 못한 꼬몽이는 실의에 빠졌지만 다시 한 번 도전해보기로 마음 먹었다.</p> <p>조금 더 체계적인 운동을하기 위해 2등에게 주어진 소량의 상금과 꼬몽이의 사연에 감복한 동네 참가자들의 기부금으로 헬스장을 차렸다.</p><p> 도움에 대한 감사로 동네 주민들에게 헬스장을 무료로 개방했다.</p><p>5년 뒤 돌아올 국제 경기를 위해 꼬몽이는 오늘도 달린다.</p><p>꼭 부모님을 만나고 말거야!</p>";
		break;
	case 3:
		context="<div class='title'><p>갱생</p></div><p>부진하다 못해 최악이라 일컫을 성적을 거둔 꼬몽이는 술에 빠져 한달을 보냈다.</p><p>몸과 마음은 황폐해갔지만 꼬몽이의 새로운 감각이 스물스물 깨어나고 있었다.</p><p>폭음으로인해 자신조차 인지하지 못한 미각이 깨어난 것이다.</p><p>꼬몽이 마을에서 소믈리에는 막대한 부를 거둘 수 있는 직종이었다.</p><p>막대한 돈이라면 황금티켓을 살 수 있어!</p><p>번뜩 뇌리에 전류가 튀었다.</p><p>꼬몽이는 그 길로 소믈리에 자격증을 취득했다.</p><p>그날, 꼬몽이는 빛나는 보름달을 바라보며 희망이 서린 뜨거운 눈물을 흘렸다.</p><p>으흑,엄마,아빠 제가 찾아 갈게요! 조금만 기다려주세요!</p>";
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
	<a href="../enterance/login.html"><input type="button" value="===스킵하기==="  class="button"></a>
	
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