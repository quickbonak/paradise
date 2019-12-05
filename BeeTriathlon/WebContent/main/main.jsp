<%@page import="main.MainProcess"%>
<%@page import="main.MainMgr"%>
<%@page import="main.MainBean"%>
<%@page contentType="text/html; charset=EUC-KR"%>


<%
	request.setCharacterEncoding("EUC-KR");

	int tempCalc;
	int reVisit = 1;
	if (request.getParameter("isVisit") == null) {
		reVisit = 0;
	}

	String name = session.getAttribute("name").toString();
	MainBean bean = new MainBean();
	MainMgr mgr = new MainMgr();
	bean.setName(name);
	bean = mgr.bringAll(bean, reVisit);

	if (bean.getCharStatus() == 0) {
		response.sendRedirect("restInPeace.jsp");
	} else if (bean.getCharStatus() == 8) {
		response.sendRedirect("triathlon.jsp");
	} else if (bean.getCharStatus() == 9) {
		response.sendRedirect("race.jsp");
	}

	if (reVisit == 1) {
		String behavior = request.getParameter("behavior");
		MainProcess mainProcess = new MainProcess();
		bean = mainProcess.doIt(bean, behavior);
		mgr.SaveAll(bean);
	}

	if (!(bean.getEvent().equals(""))) {
		response.sendRedirect("../event/event.jsp?eventName=" + bean.getEvent());
	}

	String days;
	if (bean.getTurn() % 2 == 1) {
		days = bean.getTurn() / 2 + 1 + "일 낮";
	} else {
		days = bean.getTurn() / 2 + "일 밤";
	}

	int[] specie = bean.getSpecie();
	int[] status = bean.getStatus();
	int[] passedTurn = bean.getPassedTurn();
	int[] harvestTurn = bean.getHarvestTurn();
	int[] tempResult = bean.getTempResult();
	int[] waterResult = bean.getWaterResult();
	int[] optimalTemp = bean.getOptimalTemp();
	int[] fertilized = bean.getFertilized();
	int[] waterContent = bean.getWaterContent();
	int[] waterReduction = bean.getWaterReduction();
	int[] dieTemp = bean.getDieTemp();
	int[] dieWater = bean.getDieWater();
%>

<html>
	<head>
		<title><%=name%>의 철인 도전기</title>
		<link href="https://fonts.googleapis.com/css?family=Jua" rel="stylesheet"> 
		<link rel="stylesheet" type="text/css" href="./main.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script src="main.js"></script>
		<style>
			#background{
			  width: 960px;
			  height: 760px;
			  background: url(<%
			  if(bean.getTurn()%2==1){
			  	if(bean.getIsWinter()==1){
			      	out.print("'0WD/background.png'");
			  	}else{
			      	out.print("'0SD/background.png'");
			  	}
			  }else{
			  	if(bean.getIsWinter()==1){
			      	out.print("'0WN/background.png'");
			  	}else{
			      	out.print("'0SN/background.png'");
			  	}
			  }
			  %>) no-repeat center top;
			  position: absolute;
			  top: 50%;
			  left: 50%;
			  z-index: 0;
			  margin: -380px 0 0 -480px;
			}
			
			#needle00{
				position: absolute;
				left: <%tempCalc = 139*passedTurn[0]/harvestTurn[0]+4;
					if(tempCalc<4){
				out.print("4");
			}else if(tempCalc>143){
				out.print("143");
			}else{
				out.print(tempCalc);
			}
				%>px;
				top: 45px;
				z-index:80;
			}
			#needle10{
				position: absolute;
				left: <%tempCalc = 139*passedTurn[1]/harvestTurn[1]+4;
					if(tempCalc<4){
				out.print("4");
			}else if(tempCalc>143){
				out.print("143");
			}else{
				out.print(tempCalc);
			}
				%>px;
				top: 45px;
				z-index:80;
			}
			#needle20{
				position: absolute;
				left: <%tempCalc = 139*passedTurn[2]/harvestTurn[2]+4;
					if(tempCalc<4){
				out.print("4");
			}else if(tempCalc>143){
				out.print("143");
			}else{
				out.print(tempCalc);
			}
				%>px;
				top: 45px;
				z-index:80;
			}
			
			

			#needle01{
				position: absolute;
				left: <%tempCalc = 139*tempResult[0]*waterResult[0]/passedTurn[0]/passedTurn[0]+4;
					if(tempCalc<4){
				out.print("4");
			}else if(tempCalc>143){
				out.print("143");
			}else{
				out.print(tempCalc);
			}
				%>px;
				top: 97px;
			}
			#needle11{
				position: absolute;
				left: <%tempCalc = 139*tempResult[1]*waterResult[1]/passedTurn[1]/passedTurn[1]+4;
					if(tempCalc<4){
				out.print("4");
			}else if(tempCalc>143){
				out.print("143");
			}else{
				out.print(tempCalc);
			}
				%>px;
				top: 97px;
			}
			#needle21{
				position: absolute;
				left: <%tempCalc = 139*tempResult[2]*waterResult[2]/passedTurn[2]/passedTurn[2]+4;
					if(tempCalc<4){
				out.print("4");
			}else if(tempCalc>143){
				out.print("143");
			}else{
				out.print(tempCalc);
			}
				%>px;
				top: 97px;
			}
			
			#needle02{
				position: absolute;
				left: <%tempCalc =(int)((bean.getTemperature()-optimalTemp[0])*4.34+73.5);
					if(tempCalc<4){
						out.print("4");
					}else if(tempCalc>143){
						out.print("143");
					}else{
						out.print(tempCalc);
					}
				%>px;
				top: 149px;
			}
			#needle12{
				position: absolute;
				left: <%tempCalc =(int)((bean.getTemperature()-optimalTemp[1])*4.34+73.5);
					if(tempCalc<4){
						out.print("4");
					}else if(tempCalc>143){
						out.print("143");
					}else{
						out.print(tempCalc);
					}
				%>px;
				top: 149px;
			}
			#needle22{
				position: absolute;
				left: <%tempCalc =(int)((bean.getTemperature()-optimalTemp[2])*4.34+73.5);
					if(tempCalc<4){
						out.print("4");
					}else if(tempCalc>143){
						out.print("143");
					}else{
						out.print(tempCalc);
					}
				%>px;
				top: 149px;
			}
			
			#needle03{
				position: absolute;
				left: <%tempCalc = (int)(waterContent[0]*1.39+4);
					if(tempCalc<4){
				out.print("4");
			}else if(tempCalc>143){
				out.print("143");
			}else{
				out.print(tempCalc);
			}
				%>px;
				top: 201px;
			}
			#needle13{
				position: absolute;
				left: <%tempCalc = (int)(waterContent[1]*1.39+4);
					if(tempCalc<4){
				out.print("4");
			}else if(tempCalc>143){
				out.print("143");
			}else{
				out.print(tempCalc);
			}
				%>px;
				top: 201px;
			}
			#needle23{
				position: absolute;
				left: <%tempCalc = (int)(waterContent[2]*1.39+4);
					if(tempCalc<4){
				out.print("4");
			}else if(tempCalc>143){
				out.print("143");
			}else{
				out.print(tempCalc);
			}
				%>px;
				top: 201px;
			}
			
			#charname{
				<%
					if(bean.getLifeCount()<5){
						out.print("color: red;");
					}else if(bean.getLifeCount()<10){
						out.print("color: yellow;");
					}
				%>
			}
			#fatigue{
				<%
					if(bean.getFatigue()>85){
						out.print("color: red;");
					}else if(bean.getFatigue()>45){
						out.print("color: yellow;");
					}
				%>
			}
		</style>
		
	</head>
	<body id="bodybody">
        <table id="background">
            <tr>
                <td>
                	<table id="bottombar">
                		<tr>
                			<td colspan="2" class="charname" id="charname">
                				<%=bean.getName() %>
                			</td>
                			<td class="texts" id="fatigue">
                				피로도
                			</td>
                			<td class="numbers">
                				<%=bean.getFatigue() %>
                			</td>
                			<td class="texts">
                				식량
                			</td>
                			<td class="numbers">
                				<%=bean.getFood() %>
                			</td>
                			<td class="texts">
                				기온
                			</td>
                			<td class="numbers">
                				<%=bean.getTemperature() %>
                			</td>
                			<td class="rightside">
                				<%=days %>
                			</td>
                		</tr>
                		<tr>
                			<td class="texts">
                				스피드
                			</td>
                			<td class="numbers">
                				<%=bean.getSpeed() %>
                			</td>
                			<td class="texts">
                				파워
                			</td>
                			<td class="numbers">
                				<%=bean.getPower() %>
                			</td>
                			<td class="texts">
                				장작
                			</td>
                			<td class="numbers">
                				<%=bean.getLog() %>
                			</td>
                			<td class="texts">
                				땔감
                			</td>
                			<td class="numbers">
                				<%=bean.getFirewood() %>
                			</td>
                			<td class="rightside">
                				<form method="post" action="main.jsp">
                					<input type="hidden" name="isVisit" value="1">
                					<input type="hidden" name="behavior" id="behavior" value="">
                					<input type="submit" id="endTurn" value="▶▶▶">
                				</form>
                			</td>
                		</tr>
                	</table>
                	
                	<img src="mong/tr-chinup.gif" id="trchinup">
                	<img src="mong/cocochin<%=bean.getCharStatus() %>.gif" id="cochin">
                	
                	<img src="mong/cocofish<%=bean.getCharStatus() %>.gif" id="cofish">
                	
                	<img src="mong/tr-hike.gif" id="trhike">
                	<img src="mong/cocohike<%=bean.getCharStatus() %>.gif" id="cohike">
                	
                	<img src="tent2.gif" id="shop">
                	
                	<img src="house2.gif" id="house">
                	<img src="mong/tr-house.gif" id="trhouse">
                	<img src="mong/cocozzz.gif" id="zzz">
                	
                	<img src="mong/tr-swim.gif" id="trswim">
                	<img src="mong/cocoswim<%=bean.getCharStatus() %>.gif" id="coswim">
                	
                	<img src="mong/tr-lumberjack.gif" id="trlumberjack">
                	<img src="mong/cocolum<%=bean.getCharStatus() %>.gif" id="colum">
                	
                	<img src="mong/tr-woodchop.gif" id="trwoodchop">
                	<img src="mong/cocochop<%=bean.getCharStatus() %>.gif" id="cochop">
                	
                	<img src="mong/tr-beekeep.gif" id="trbeekeep">
                	<img src="mong/cocobee<%=bean.getCharStatus() %>.gif" id="cobee">
                	
                	<span id="field1">
                	<img src="1Dayfield/<%
                	out.print(specie[0]+""+status[0]);
                	%>.gif">
                	<img src="mong/coco<%=bean.getCharStatus() %>.gif" id="co53">
                	</span>
                	<img src="mong/tr-field.gif" id="trfield1">
                	
                	<span id="field2">
                	<img src="1Dayfield/<%
                	out.print(specie[1]+""+status[1]);
                	%>.gif">
                	<img src="mong/coco<%=bean.getCharStatus() %>.gif" id="co63">
                	</span>
                	<img src="mong/tr-field.gif" id="trfield2">
                	
                	<span id="field3">
                	<img src="1Dayfield/<%
                	out.print(specie[2]+""+status[2]);
                	%>.gif">
                	<img src="mong/coco<%=bean.getCharStatus() %>.gif" id="co73">
                	</span>
                	<img src="mong/tr-field.gif" id="trfield3">
                	
                	
			        <%
			        	if(bean.getIsRain()==1){
			        		if(bean.getTemperature()<=0){
			        			out.print("<img src='snow.gif' id='fallthings'>");
			        		}else{
			        			out.print("<img src='rain.gif' id='fallthings'>");
			        		}
			        	}
			        %>
                	
			        <%
						if(bean.getTurn()%2==1){
							out.print("<img src='1Dayfield/haenim.gif' id='downupthings'>");
						}else{
							out.print("<img src='1Nightfield/dalnim.gif' id='downupthings'>");
						}
			        %>
                	
                	<table id="menu1">
                		<tr>
                			<td>
                				<img src="farmstatus/top<%
                					if(status[0]==0){
                						out.print("9");
                					}else{
                						out.print(specie[0]);
                					}
                				%>.png">
                			</td>
                			<td id="fieldmenu0" rowspan="3" valign="top">
                				<%
                					if(status[0]==0){
                						out.print("<img src='farmstatus/btnsowing.png' id='sowi0'><br>");
                					}
                					if(status[0]!=0&&status[0]!=4){
                						out.print("<img src='farmstatus/btnspraying.png' id='spra0'> <br><img src='farmstatus/btndraining.png' id='drai0'><br>");
                					}
                					if(fertilized[0]==0&&status[0]!=0){
                						out.print("<img src='farmstatus/btnfertilizing.png' id='fert0'><br>");
                					}
                					if(status[0]==4){
                						out.print("<img src='farmstatus/btnharvesting.png' id='harv0'><br>");
                					}
                				%>
                			</td>
                		</tr>
                		<tr>
                			<td>
								<img src="farmstatus/growth.png"><br>
								<%
									if(status[0]==0){
										out.print("<img src='farmstatus/msg1.png'>");
									}else if (status[0]==4){
										out.print("<img src='farmstatus/msg2.png'>");
									}else{
										out.print("<img src='farmstatus/msg0.png'>");
									}
								%>
								<img src="farmstatus/needle.gif" id="needle00">
                			</td>
                		</tr>
                		<tr>
                			<td>
								<img src="farmstatus/fertility.png"><br>
								<%
									if(fertilized[0]==0){
										out.print("<img src='farmstatus/msg3.png'>");
									}else{
										out.print("<img src='farmstatus/msg4.png'>");
									}
								%>
								<img src="farmstatus/needle.gif" id="needle01">
                			</td>
                		</tr>
                		<tr>
                			<td>
                				<%
                					if(fertilized[0]==3){
										out.print("<img src='farmstatus/optimaltemp1.png'><br>");
                					}else{
										out.print("<img src='farmstatus/optimaltemp.png'><br>");
                					}
                				%>
								<%
									if(dieTemp[0]==1){
										out.print("<img src='farmstatus/msg6.png'>");
									}else if(dieTemp[0]==2){
										out.print("<img src='farmstatus/msg5.png'>");
									}else{
										out.print("<img src='farmstatus/msg0.png'>");
									}
								%>
								<img src="farmstatus/needle.gif" id="needle02">
                			</td>
                		</tr>
                		<tr>
                			<td>
								<img src="farmstatus/optimalwater.png"><br>
								<%
									if(dieWater[0]==1){
										out.print("<img src='farmstatus/msg6.png'>");
									}else if(dieWater[0]==2){
										out.print("<img src='farmstatus/msg5.png'>");
									}else{
										out.print("<img src='farmstatus/msg0.png'>");
									}
								%>
								<img src="farmstatus/needle.gif" id="needle03">
                			</td>
                		</tr>
                	</table>
                	<table id="menusowing0">
                		<tr>
                			<td valign="top">
								<img src="farmstatus/seed0.png" id="sow00">
								<img src="farmstatus/seed1.png" id="sow01">
								<img src="farmstatus/seed2.png" id="sow02">
								<img src="farmstatus/seed3.png" id="sow03">
								<img src="farmstatus/seed4.png" id="sow04">
								<img src="farmstatus/seedmsg0.png" id="sowmsg0">
								<img src="farmstatus/seedmsg1.png" id="sowmsg1">
								<img src="farmstatus/seedmsg2.png" id="sowmsg2">
								<img src="farmstatus/seedmsg3.png" id="sowmsg3">
								<img src="farmstatus/seedmsg4.png" id="sowmsg4">
                			</td>
                		</tr>
                	</table>
                	
                	<table id="menu2">
                		<tr>
                			<td>
                				<img src="farmstatus/top<%
                					if(status[1]==0){
                						out.print("9");
                					}else{
                						out.print(specie[1]);
                					}
                				%>.png">
                			</td>
                			<td id="fieldmenu2" rowspan="3" valign="top">
                				<%
                					if(status[1]==0){
                						out.print("<img src='farmstatus/btnsowing.png' id='sowi1'><br>");
                					}
                					if(status[1]!=0&&status[1]!=4){
                						out.print("<img src='farmstatus/btnspraying.png' id='spra1'> <br><img src='farmstatus/btndraining.png' id='drai1'><br>");
                					}
                					if(fertilized[1]==0&&status[1]!=0){
                						out.print("<img src='farmstatus/btnfertilizing.png' id='fert1'><br>");
                					}
                					if(status[1]==4){
                						out.print("<img src='farmstatus/btnharvesting.png' id='harv1'><br>");
                					}
                				%>
                			</td>
                		</tr>
                		<tr>
                			<td>
								<img src="farmstatus/growth.png"><br>
								<%
									if(status[1]==0){
										out.print("<img src='farmstatus/msg1.png'>");
									}else if (status[1]==4){
										out.print("<img src='farmstatus/msg2.png'>");
									}else{
										out.print("<img src='farmstatus/msg0.png'>");
									}
								%>
								<img src="farmstatus/needle.gif" id="needle10">
                			</td>
                		</tr>
                		<tr>
                			<td>
								<img src="farmstatus/fertility.png"><br>
								<%
									if(fertilized[1]==0){
										out.print("<img src='farmstatus/msg3.png'>");
									}else{
										out.print("<img src='farmstatus/msg4.png'>");
									}
								%>
								<img src="farmstatus/needle.gif" id="needle11">
                			</td>
                		</tr>
                		<tr>
                			<td>
                				<%
                					if(fertilized[1]==3){
										out.print("<img src='farmstatus/optimaltemp1.png'><br>");
                					}else{
										out.print("<img src='farmstatus/optimaltemp.png'><br>");
                					}
                				%>
								<%
									if(dieTemp[1]<=1){
										out.print("<img src='farmstatus/msg6.png'>");
									}else if(dieTemp[1]<=3){
										out.print("<img src='farmstatus/msg5.png'>");
									}else{
										out.print("<img src='farmstatus/msg0.png'>");
									}
								%>
								<img src="farmstatus/needle.gif" id="needle12">
                			</td>
                		</tr>
                		<tr>
                			<td>
								<img src="farmstatus/optimalwater.png"><br>
								<%
									if(dieWater[1]==1){
										out.print("<img src='farmstatus/msg6.png'>");
									}else if(dieWater[1]==2){
										out.print("<img src='farmstatus/msg5.png'>");
									}else{
										out.print("<img src='farmstatus/msg0.png'>");
									}
								%>
								<img src="farmstatus/needle.gif" id="needle13">
                			</td>
                		</tr>
                	</table><table id="menusowing1">
                		<tr>
                			<td valign="top">
								<img src="farmstatus/seed0.png" id="sow10">
								<img src="farmstatus/seed1.png" id="sow11">
								<img src="farmstatus/seed2.png" id="sow12">
								<img src="farmstatus/seed3.png" id="sow13">
								<img src="farmstatus/seed4.png" id="sow14">
								<img src="farmstatus/seedmsg0.png" id="sowmsg0">
								<img src="farmstatus/seedmsg1.png" id="sowmsg1">
								<img src="farmstatus/seedmsg2.png" id="sowmsg2">
								<img src="farmstatus/seedmsg3.png" id="sowmsg3">
								<img src="farmstatus/seedmsg4.png" id="sowmsg4">
                			</td>
                		</tr>
                	</table>
                	
                	<table id="menu3">
                		<tr>
                			<td>
                				<img src="farmstatus/top<%
                					if(status[2]==0){
                						out.print("9");
                					}else{
                						out.print(specie[2]);
                					}
                				%>.png">
                			</td>
                			<td id="fieldmenu2" rowspan="3" valign="top">
                				<%
                					if(status[2]==0){
                						out.print("<img src='farmstatus/btnsowing.png' id='sowi2'><br>");
                					}
                					if(status[2]!=0&&status[2]!=4){
                						out.print("<img src='farmstatus/btnspraying.png' id='spra2'> <br><img src='farmstatus/btndraining.png' id='drai2'><br>");
                					}
                					if(fertilized[2]==0&&status[2]!=0){
                						out.print("<img src='farmstatus/btnfertilizing.png' id='fert2'><br>");
                					}
                					if(status[2]==4){
                						out.print("<img src='farmstatus/btnharvesting.png' id='harv2'><br>");
                					}
                				%>
                			</td>
                		</tr>
                		<tr>
                			<td>
								<img src="farmstatus/growth.png"><br>
								<%
									if(status[2]==0){
										out.print("<img src='farmstatus/msg1.png'>");
									}else if (status[2]==4){
										out.print("<img src='farmstatus/msg2.png'>");
									}else{
										out.print("<img src='farmstatus/msg0.png'>");
									}
								%>
								<img src="farmstatus/needle.gif" id="needle20">
                			</td>
                		</tr>
                		<tr>
                			<td>
								<img src="farmstatus/fertility.png"><br>
								<%
									if(fertilized[2]==0){
										out.print("<img src='farmstatus/msg3.png'>");
									}else{
										out.print("<img src='farmstatus/msg4.png'>");
									}
								%>
								<img src="farmstatus/needle.gif" id="needle21">
                			</td>
                		</tr>
                		<tr>
                			<td>
                				<%
                					if(fertilized[2]==3){
										out.print("<img src='farmstatus/optimaltemp1.png'><br>");
                					}else{
										out.print("<img src='farmstatus/optimaltemp.png'><br>");
                					}
                				%>
								<%
									if(dieTemp[2]<=1){
										out.print("<img src='farmstatus/msg6.png'>");
									}else if(dieTemp[2]<=3){
										out.print("<img src='farmstatus/msg5.png'>");
									}else{
										out.print("<img src='farmstatus/msg0.png'>");
									}
								%>
								<img src="farmstatus/needle.gif" id="needle22">
                			</td>
                		</tr>
                		<tr>
                			<td>
								<img src="farmstatus/optimalwater.png"><br>
								<%
									if(dieWater[2]==1){
										out.print("<img src='farmstatus/msg6.png'>");
									}else if(dieWater[2]==2){
										out.print("<img src='farmstatus/msg5.png'>");
									}else{
										out.print("<img src='farmstatus/msg0.png'>");
									}
								%>
								<img src="farmstatus/needle.gif" id="needle23">
                			</td>
                		</tr>
                	</table>
                	<table id="menusowing2">
                		<tr>
                			<td valign="top">
								<img src="farmstatus/seed0.png" id="sow20">
								<img src="farmstatus/seed1.png" id="sow21">
								<img src="farmstatus/seed2.png" id="sow22">
								<img src="farmstatus/seed3.png" id="sow23">
								<img src="farmstatus/seed4.png" id="sow24">
								<img src="farmstatus/seedmsg0.png" id="sowmsg0">
								<img src="farmstatus/seedmsg1.png" id="sowmsg1">
								<img src="farmstatus/seedmsg2.png" id="sowmsg2">
								<img src="farmstatus/seedmsg3.png" id="sowmsg3">
								<img src="farmstatus/seedmsg4.png" id="sowmsg4">
                			</td>
                		</tr>
                	</table>
                	<table id="menu7">
                		<tr>
                			<td>
								<img src="farmstatus/btnswimming.png" id="swim"><br>
								<img src="farmstatus/btnfishing.png" id="fish">
                			</td>
                		</tr>
                	</table>
                </td>
            </tr>
        </table>

	</body>
</html>