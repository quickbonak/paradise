<%@page import="main.RecordMgr"%>
<%@page import="main.RecordBean"%>
<%@page contentType="text/html; charset=EUC-KR"%>
<%
	request.setCharacterEncoding("EUC-KR");

	RecordBean records = new RecordBean();
	RecordMgr recMgr = new RecordMgr();
	String name = session.getAttribute("name").toString();
	records = recMgr.loadRecords(name);
	int[] playerIdx = records.getPlayerIdx();
	String[] playerName = records.getName();
	int[][] hikeRecords = records.getHikeRecords();
	int[][] chinupRecords = records.getChinupRecords();
	int[][] swimRecords = records.getSwimRecords();
	int[][] allRecords = records.getAllRecords();
	
	//밀리초를 정상시간으로 바꾸어주는 메커니즘
	String[] hikeTime = new String[10];
	String[] swimTime = new String[10];
	int min, sec, msec;
	for(int i=0; i<10; i++){
		min = (int)Math.floor((double)hikeRecords[i][1]/3600);
		sec = (int)Math.floor((double)(hikeRecords[i][1]%3600)/60);
		msec = ((hikeRecords[i][1]%3600)%60);
		hikeTime[i] = min+"분 "+sec+"초 "+msec;
		
		min = (int)Math.floor((double)swimRecords[i][1]/3600);
		sec = (int)Math.floor((double)(swimRecords[i][1]%3600)/60);
		msec = ((swimRecords[i][1]%3600)%60);
		swimTime[i] = min+"분 "+sec+"초 "+msec;
	}
	
	String ending="";
	
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>철인 3종경기</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<style>
	table, td{
		border: 1px solid;
		background-color: white;
		text-align: center;
	}
	td{
		width: 70px;
		height: 20px;
	}
	td:nth-child(2){
		width:110px;
	}
	
	.btnNext{
		position: absolute;
		width: 100px;
		height: 100px;
		font-size: 20pt;
		top: 564px;
		left: 840px;
	}
	.thisplayer{
		color: red;
	}
	.scoreBack{
		width: 960px;
		height: 684px;
		position: absolute;
		top: 50%;
		left: 50%;
		z-index: 0;
		margin: -342px 0 0 -480px;
		display: none;
	}
	.scoreTable{
		position: absolute;
		top: 100px;
		left: 600px;
	}
	#hike{
		background: url("imgs/triathlon1.jpg") no-repeat;
		display: inline;
	}
	#chinup{
		background: url("imgs/triathlon2.jpg") no-repeat;
		display: none;
	}
	#swim{
		background: url("imgs/triathlon3.jpg") no-repeat;
		display: none;
	}
	#total{
		background: url("imgs/triathlon4.jpg") no-repeat;
		display: none;
	}
	.cohike, .cochin, .coswim{
		position: absolute;
	}
	#cohike1{
		top: 350px;
		left: 150px;
	}
	#cohike2{
		top: 500px;
		left: 160px;
	}
	#cohike3{
		top: 450px;
		left: 270px;
	}
	#cohike4{
		top: 600px;
		left: 170px;
	}
	#cohike5{
		top: 390px;
		left: 230px;
	}
	#cohike6{
		top: 430px;
		left: 100px;
	}
	#cohike7{
		top: 410px;
		left: 170px;
	}
	#cohike8{
		top: 330px;
		left: 220px;
	}
	#cohike9{
		top: 550px;
		left: 90px;
	}
	#cohike10{
		top: 570px;
		left: 230px;
	}
	#cochin1{
		top: 350px;
		left: 116px;
	}
	#cochin2{
		top: 350px;
		left: 214px;
	}
	#cochin3{
		top: 350px;
		left: 312px;
	}
	#cochin4{
		top: 350px;
		left: 410px;
	}
	#cochin5{
		top: 350px;
		left: 508px;
	}
	#cochin6{
		top: 469px;
		left: 62px;
	}
	#cochin7{
		top: 469px;
		left: 160px;
	}
	#cochin8{
		top: 469px;
		left: 258px;
	}
	#cochin9{
		top: 469px;
		left: 356px;
	}
	#cochin10{
		top: 469px;
		left: 454px;
	}
	#coswim1{
		top: 400px;
		left: 100px;
	}
	#coswim2{
		top: 460px;
		left: 170px;
	}
	#coswim3{
		top: 500px;
		left: 350px;
	}
	#coswim4{
		top: 520px;
		left: 110px;
	}
	#coswim5{
		top: 410px;
		left: 280px;
	}
	#coswim6{
		top: 450px;
		left: 650px;
	}
	#coswim7{
		top: 480px;
		left: 520px;
	}
	#coswim8{
		top: 420px;
		left: 450px;
	}
	#coswim9{
		top: 520px;
		left: 600px;
	}
	#coswim10{
		top: 200px;
		left: 100px;
	}
</style>
</head>
<body>

	<div class="scoreBack" id="hike">
		<table class="scoreTable">
			<tr>
				<td colspan="4">
					크로스 컨트리 12km
				</td>
			</tr>
			<tr>
				<td>
					선수이름
				</td>
				<td>
					기록
				</td>
				<td>
					순위
				</td>
				<td>
					특이사항
				</td>
			</tr>
			<%
				for(int i=0; i<10 ;i++){
					for(int j=0; j<10; j++){
						if(hikeRecords[i][0]==playerIdx[j]){
							if(playerName[j].equals(name)){
								out.print("<tr class='thisplayer'><td>"+playerName[j]+"</td>");
							}else{
								out.print("<tr><td>"+playerName[j]+"</td>");
							}
						}
					}
					out.print("<td>"+hikeTime[i]+"</td><td>"+(i+1)+"</td>");
					if(i==0){
						out.print("<td>산악왕</td></tr>");
					}else{
						out.print("<td></td></tr>");
					}
					
				}
			%>
		</table>
		<img src="mong/cocohike3.gif" class="cohike" id="cohike1">
		<img src="mong/cocohike4.gif" class="cohike" id="cohike2">
		<img src="mong/cocohike3.gif" class="cohike" id="cohike3">
		<img src="mong/cocohike4.gif" class="cohike" id="cohike4">
		<img src="mong/cocohike3.gif" class="cohike" id="cohike5">
		<img src="mong/cocohike4.gif" class="cohike" id="cohike6">
		<img src="mong/cocohike3.gif" class="cohike" id="cohike7">
		<img src="mong/cocohike4.gif" class="cohike" id="cohike8">
		<img src="mong/cocohike3.gif" class="cohike" id="cohike9">
		<img src="mong/cocohike4.gif" class="cohike" id="cohike10">
		<input type="button" name ="btnNext" class="btnNext" value="▶">
	</div>
	
	<div class="scoreBack" id="chinup">
		<table class="scoreTable">
			<tr>
				<td colspan="4">
					철봉 10분
				</td>
			</tr>
			<tr>
				<td>
					선수이름
				</td>
				<td>
					기록
				</td>
				<td>
					순위
				</td>
				<td>
					특이사항
				</td>
			</tr>
			<%
				for(int i=0; i<10 ;i++){
					for(int j=0; j<10; j++){
						if(chinupRecords[i][0]==playerIdx[j]){
							if(playerName[j].equals(name)){
								out.print("<tr class='thisplayer'><td>"+playerName[j]+"</td>");
							}else{
								out.print("<tr><td>"+playerName[j]+"</td>");
							}
						}
					}
					out.print("<td>"+chinupRecords[i][1]+" 회</td><td>"+(i+1)+"</td>");
					if(i==0){
						out.print("<td>철왕</td></tr>");
					}else{
						out.print("<td></td></tr>");
					}
					
				}
			%>
		</table>
		<img src="mong/cocochin3.gif" class="cochin" id="cochin1">
		<img src="mong/cocochin4.gif" class="cochin" id="cochin2">
		<img src="mong/cocochin3.gif" class="cochin" id="cochin3">
		<img src="mong/cocochin4.gif" class="cochin" id="cochin4">
		<img src="mong/cocochin3.gif" class="cochin" id="cochin5">
		<img src="mong/cocochin4.gif" class="cochin" id="cochin6">
		<img src="mong/cocochin3.gif" class="cochin" id="cochin7">
		<img src="mong/cocochin4.gif" class="cochin" id="cochin8">
		<img src="mong/cocochin3.gif" class="cochin" id="cochin9">
		<img src="mong/cocochin4.gif" class="cochin" id="cochin10">
		<input type="button" name ="btnNext" class="btnNext" value="▶">
	</div>
	
	<div class="scoreBack" id="swim">
		<table class="scoreTable">
			<tr>
				<td colspan="4">
					수영 3km
				</td>
			</tr>
			<tr>
				<td>
					선수이름
				</td>
				<td>
					기록
				</td>
				<td>
					순위
				</td>
				<td>
					특이사항
				</td>
			</tr>
			<%
				for(int i=0; i<10 ;i++){
					for(int j=0; j<10; j++){
						if(swimRecords[i][0]==playerIdx[j]){
							if(playerName[j].equals(name)){
								out.print("<tr class='thisplayer'><td>"+playerName[j]+"</td>");
							}else{
								out.print("<tr><td>"+playerName[j]+"</td>");
							}
						}
					}
					out.print("<td>"+swimTime[i]+"</td><td>"+(i+1)+"</td>");
					if(i==0){
						out.print("<td>해상왕</td></tr>");
					}else{
						out.print("<td></td></tr>");
					}
					
				}
			%>
		</table>
		<img src="mong/cocoswim3.gif" class="coswim" id="coswim1">
		<img src="mong/cocoswim4.gif" class="coswim" id="coswim2">
		<img src="mong/cocoswim3.gif" class="coswim" id="coswim3">
		<img src="mong/cocoswim4.gif" class="coswim" id="coswim4">
		<img src="mong/cocoswim3.gif" class="coswim" id="coswim5">
		<img src="mong/cocoswim4.gif" class="coswim" id="coswim6">
		<img src="mong/cocoswim3.gif" class="coswim" id="coswim7">
		<img src="mong/cocoswim4.gif" class="coswim" id="coswim8">
		<img src="mong/cocoswim3.gif" class="coswim" id="coswim9">
		<img src="mong/cocoswim4.gif" class="coswim" id="coswim10">
		<input type="button" name ="btnNext" class="btnNext" value="▶">
	</div>
	
	<div class="scoreBack" id="total">
		<table class="scoreTable">
			<tr>
				<td colspan="4">
					최종순위
				</td>
			</tr>
			<tr>
				<td>
					선수이름
				</td>
				<td>
					총점수
				</td>
				<td>
					순위
				</td>
				<td>
					특이사항
				</td>
			</tr>
			<%
				for(int i=0; i<10 ;i++){
					if(playerName[i].equals(name)){
						out.print("<tr class='thisplayer'>");
						if(allRecords[i][8]<4){
							session.setAttribute("grade", "1");
						}else if(allRecords[i][8]<8){
							session.setAttribute("grade", "2");
						}else{
							session.setAttribute("grade", "3");
						}
					}else{
						out.print("<tr>");
					}
					out.print("<td>"+playerName[i]+"</td><td>"+allRecords[i][7]+"</td><td>"+(i+1)+"</td>");
					if(i==0){
						out.print("<td>금메달</td></tr>");
					}else if(i==1){
						out.print("<td>은메달</td></tr>");
					}else if(i==2){
						out.print("<td>동메달</td></tr>");
					}else{
						out.print("<td></td></tr>");
					}
					
				}
			%>
		</table>
		<input type="button" name ="btnNext" class="btnNext" value="▶">
	</div>
	
	<script>
		$(document).ready(function(){
			var nak = 0;

		$(".btnNext").click(function(){
			
			switch(nak){
				case 0:
					$("#hike").hide();
					$("#chinup").show();
					nak++;
						break;
				case 1:
					$("#chinup").hide();
					$("#swim").show();
					nak++;
						break;
				case 2:
					$("#swim").hide();
					$("#total").show();
					nak++;
						break;
				case 3:
					location.replace("ending.jsp");			
			}
		});
		});
	</script>

</body>
</html>