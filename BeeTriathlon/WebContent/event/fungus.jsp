<%@page import="main.MainMgr"%>
<%@page import="main.MainBean"%>
<%@page import="java.util.Random"%>
<%@page contentType="text/html; charset=EUC-KR"%>
<%
		request.setCharacterEncoding("EUC-KR");


	String name = session.getAttribute("name").toString();
	MainBean bean = new MainBean();
	MainMgr mgr = new MainMgr();
	bean.setName(name);
	bean = mgr.bringAll(bean, 1);
	
	
	if(!(bean.getEvent().equals("fungus"))){
		response.sendRedirect("../main/main.jsp");
	}

	int chosen = Integer.parseInt(request.getParameter("selectedValue"));
	Random random = new Random(); 
	if(chosen==0){
		if(40>random.nextInt(100)){
			out.println("ÇÇ·Î Áõ°¡");
		}else{
			out.println("´É·ÂÄ¡ ¾÷");
		}
	
	}else if(chosen==1){
		if(40>random.nextInt(100)){
			out.println("µ¶¹ö¼¸ È¹µæ");
		}else{
			out.println("¹ö¼¸ È¹µæ");
		}
	}
	
	
	bean.setEvent("");
	mgr.SaveAll(bean);
	response.sendRedirect("../main/main.jsp");
%>

