package bpduo.main.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import bpduo.main.dto.PageControlDto;

@Service
public class PageControlServiceImpl implements PageControlService {

	@Override
	public PageControlDto checkNGo(PageControlDto PCDto, HttpSession session) throws Exception {
		
		
		//세션에 page 값이 존재한다는 소리는 이전에 암호를 맞추었고, 어떤 페이지로 연결될지도 기억되었다는 의미이다.
		if(session.getAttribute("page")!=null) {
			
			PCDto.setPage(session.getAttribute("page").toString());
			
		//사용자가 이전에 페스워드를 입력하였다면 페스워드를 비교해 보아야 하고, 틀릴경우에는 사용자에게 말하고, 맞으면 해당 페이지로 연결 수순.
		}else if(PCDto.getPass()!=null) {
			
			switch(PCDto.getPass()) {
				
				case "1229" :
					PCDto.setPage("/main/binary");
					session.setAttribute("page", "/main/binary");
					break;
				
				case "0823" :
					PCDto.setPage("/main/paradise");
					session.setAttribute("page", "/main/paradise");
					break;
				
				default :
					PCDto.setPage("/main/index");
					PCDto.setShowText("입장코드가 일치하지 않습니다.");
			}
			
		}else {
			
			PCDto.setPage("/main/index");
			PCDto.setShowText("이력서 보기");
			
		}
			
		return PCDto;
	}

}
