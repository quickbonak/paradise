<%@page import="java.util.Random"%>
<%@page import="board.BoardBean"%>
<%@page import="board.BoardMgr"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<link href="https://fonts.googleapis.com/css?family=Sunflower:300" rel="stylesheet">
<%


	request.setCharacterEncoding("utf-8");
	BoardMgr boardMgr = new BoardMgr();
	BoardBean bean = new BoardBean();
	boolean isLogin = false;
	String id = "";
	int idn = 0;
	if(session.getAttribute("id")!=null){
		isLogin = true;
		id = session.getAttribute("id").toString();
		idn = Integer.parseInt(session.getAttribute("idn").toString());
	}
	
	int numberOfPosts = boardMgr.howManyPost();
	int numOfRandomPosts = 5;
	if(isLogin==false)numOfRandomPosts = 6;
	Random random = new Random();
	int[] postIdx = new int[numOfRandomPosts];
	postIdx[0] = 1;
	for(int i=1; i<postIdx.length; i++){
		postIdx[i] = random.nextInt(numberOfPosts)+1;
	}
	   
%>


<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" ,initial-scale="1.0" />
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="css/index.css">
<link href="https://fonts.googleapis.com/css?family=Great+Vibes" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=BenchNine:700">
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="js/jquery-2.1.3.min.js"></script>
<script src="js/jquery.backstretch.min.js"></script>
<script src="js/index.js"></script>

</head>

<body>
	<table id="registerform">
	<form method="post" name="registerForm" action="register.jsp"  id="join">
		<tr>
			<td><input type="text" name="id" placeholder="아이디" style="width:147px; height:27px;"></td>
			<td style="color:white;">WELCOME</td>
				
		</tr>
		
		<tr>
			<td><input type="password" name="pass" id="pass1" placeholder="비밀번호" style="margin-top:-4px; height:27px;"></td>
			<td><input type="button" value="회원가입" onclick="register()" style="width:80px; text-align:center; margin-left:0px; margin-top:-5px" class='ghost-button'></td>	
			
		</tr>
		
		<tr>
			<td><input type="password" id="pass2" placeholder="비밀번호확인" style="margin-top:-4px; height:27px;"></td>
			<td><input type="button" value="취소" onclick="registeracancel()" style="width:80px; margin-top:-5px; margin-right:100px;" class='ghost-button' ></td>
		</tr>
		
	</form>
	</table>
	<div id="numberfull">
		<!--<p>JIN-FRONTEND(JAVASCRIPT)</p>-->
		<span id="space">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span>
		<span id="hire" onclick="location.href='../index.jsp'">&nbsp; Binary</span>
		 
		<table id="loginbox">
			<tr>
				<form method="post" action="login.jsp" id="login" name="loginForm">
				<td>
					<%
						if(isLogin){
							out.print(id+"님 환영합니다.");
						}else{
							out.print("<input type='text' name='id' placeholder='아이디' style='width:150px; height:30px;'><br><input type='password' name='pass' placeholder='비밀번호' style='width:150px; height:30px;'>");
						}
					%>
				</td>
				<td>
					<input type='text' name='isLogOut' id='isLogOut' value='0' style='display:none'>
					<%
						if(isLogin){
							out.print("<input type='button' value='로그아웃' onclick='doLogOut()' class='ghost-button' sdtyle='background-color:white; padding:20px 20px;'>");
						}else{
							out.print("<input type='submit' value='로그인' class='ghost-button' style='width:80px; height:32px;'><br>" + 
						"<input type='button' value='회원가입' id='registerbt' onclick='registerform()' class='ghost-button' style='width:80px; height:32px;'>");
						}
					%>
				</td>
				
				</form>
			</tr>
		</table>
	</div>

	<div id="backgr">
		<!-- 로그인 양식 -->
			

			<!-- 회원가입 양식 -->
			
			
		
		<div id="header" onmouseover="over()" onmouseout="out()"></div>
	</div>
	<div id="backgrbottom"></div>

	<!-- 컨텐츠 -->
	<div id="middle">
	
		<div class="naeyoung" <%if(isLogin==false){out.print("style='display: none;'");} %>>
		<table class="postcontent" style="border: 1px double #619991;" id="writetable">
			<form method="post" action="write.jsp" name="writeForm">
			<tr><td style="background-color:white; height:20px;"><input type="text" name="title" placeholder="제목" style="width:100%; background-color:#fcfcfc;"></td></tr><br>
			<tr><td><textarea rows="10" cols="20" name="conten" style="width:100%; height:100%; background-color:#fcfcfc; resize: none;"></textarea></td></tr><br>
			<tr><td style="text-align:right; height:50px;"><span class="number" id="writer" onclick="writePost()" style="height:20px; font-size:20px;"> &nbsp;&nbsp;WRITE &nbsp;&nbsp;</span></td></tr>
			
			</form>
		</table>
		</div>
		<form method="post" action="guestProcess.jsp" name="guestForm">
		<input name="guestSignal" id="guestSignal" style="display:none;" value="0">
		<input name="postNum" id="postNum" style="display:none;" value="0">
		<%
		for(int i=0; i<postIdx.length; i++){
			bean = boardMgr.getPost(postIdx[i]);
			String modDelete = "";
			if(bean.getIdn()==idn)modDelete="<tr><td colspan='3' style='height:20px; text-align:right; background-color:#30474f;'><span onclick='guestModify("+bean.getIdx()+")' class='number'>MODIFY</span><span onclick='guestDelete("+bean.getIdx()+")' class='number'>DELETE</span></td></tr>";
			out.print("<div class='naeyoung'><table class='postcontent' id='postcontent'>" +
			"<tr><td colspan='3' style='height:20px; background-color:#619991;color:white; padding-left:7px; font-size:30px;'>" + bean.getTitle() + "</td></tr>" +
			"<tr style='background-color:#30474f; color:white;'><td style='height:20px;' class='nwd'>번호: " + bean.getIdx() +"</td><td style='height:20px;' class='nwd'>글쓴이: " + bean.getId() + 
			"</td><td style='height:20px; padding:6px 0px 6px 5px;' colspan='2'  class='nwd'>" + bean.getsDate() +
			"</td></tr><tr><td colspan='3' style='padding-left:0px;'><textarea class='contentArea' style='background-color:#fcfcfc; font-size:20px;'>" + bean.getConten() + "</textarea>" +
			"</td></tr>" + modDelete + "</table></div>");
		}
		%>
		</form>
		
	</div>
	<div id="footer">
		<div>
		<p id="footermail" style= "padding:10px 0;">ljlj122@naver.com</p>
			
		</div>
	</div>
</body>
</html>
