package bpduo.guest.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bpduo.guest.dto.GIndexDto;
import bpduo.guest.dto.GuestDto;
import bpduo.guest.service.GuestService;

@Controller
public class GuestController {
	
	@Autowired
	GuestService gService;
	
	
	@RequestMapping("/guest")
	public ModelAndView guestPage(HttpSession session) throws Exception{
		
		GIndexDto gIndex = gService.guestIndex(session);
		List<GuestDto> gPosts = gService.getPost(gIndex);
		
		ModelAndView mv = new ModelAndView("/guest/index");
		mv.addObject("gIndex", gIndex);
		mv.addObject("gPosts", gPosts);
		return mv;
		
	}
	
	
	@RequestMapping("/guest/register")
	public String guestRegister(GuestDto gDto, HttpSession session) throws Exception{
		
		String goToPage = "/guest/caution-dupleid";
		
		//등록 성공시 true, 아이디 중복시 false
		if(gService.guestRegister(gDto)) {
			goToPage = "redirect:/guest";
		}
		
		return goToPage;
	}
	
	
	@RequestMapping("/guest/login")
	public String guestLogin(GuestDto gDto, HttpSession session) throws Exception{
		
		String goToPage = "redirect:/guest";
		String isLogOut = gDto.getIsLogOut();
		
		//로그아웃 작업인가?
		if(isLogOut.equals("1")){
			session.removeAttribute("id");
			session.removeAttribute("idn");
		}else {
			//로그인이 실패했을 경우
			if(!gService.gLogin(gDto, session)) {
				goToPage = "/guest/caution-loginfail";
			};
		}
		
		return goToPage;
	}
	
	
	@RequestMapping("/guest/createPost")
	public String createPost(GuestDto gDto, HttpSession session) throws Exception{
		
			gDto.setIdn(Integer.parseInt(session.getAttribute("idn").toString()));
			gDto.setId(session.getAttribute("id").toString());
			gService.setPost(gDto);
			
		return "redirect:/guest";
	}
	
	
	@RequestMapping("/guest/deletePost")
	public String deletePost(GuestDto gDto, HttpSession session) throws Exception{
		gService.delPost(gDto);
		return "redirect:/guest";
	}
	
	@RequestMapping("/guest/updatePost")
	public String updatePost(GuestDto gDto, HttpSession session) throws Exception{
		gService.upPost(gDto);
		return "redirect:/guest";
	}
	
	@RequestMapping("/guest/updateForm")
	public ModelAndView updateForm(GuestDto gDto, HttpSession session) throws Exception{
		GuestDto uPost = gService.modForm(gDto);
		
		ModelAndView mv = new ModelAndView("/guest/updatePost");
		mv.addObject("post", uPost);
		return mv;
		
	}
	
	
	
	/*
	// 업데이트 포스트의 페이지를 표현하는 부분에 심각한 문제가 생겼다. 모델엔 뷰를 사용해서 값을 받아와서 글과 제목을 띄워야 한다.
	@RequestMapping("/guest/modDel")
	public String updatePost(GuestDto gDto, HttpSession session, @RequestParam String guestSignal, @RequestParam String postNum) throws Exception{
		
		
		String goToPage = "redirect:/guest";
		//신호를 구별해서 게스트 시그널이 1일 경우 삭제작업을, 2일 경우 수정 작업페이지로 보내야한다. 3일 경우에 실질적 수정작업. 포스트넘은 지우거나 수정할 글의 번호(idx)이다.
		gDto.setIdx(Integer.parseInt(postNum));
		switch(guestSignal) {
			case "1":
					gService.delPost(gDto);
				break;
				
			case "2":
					goToPage = "redirect:/guest/updatePost";
				break;
				
			case "3":
					gService.upPost(gDto);
				break;
		}
		
		return goToPage;
	}
	*/
}