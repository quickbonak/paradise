package bpduo.main.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bpduo.main.dto.PageControlDto;
import bpduo.main.service.PageControlService;

@Controller
public class MainController {
	
	@Autowired
	PageControlService pcs;
	
	//첫 입장시 메인 페이지에, 이후에 암호입력시 특정 페이지로 연결되도록 한다.
	@RequestMapping("/")
	public ModelAndView mainPage(PageControlDto PCDto, HttpSession session) throws Exception{
		
		// 세션과 파라메터에서 값을 받아 어느 페이지로 연결될지, 첫 화면에 어떤 테스트를 띄울지를 결정.
		PCDto = pcs.checkNGo(PCDto, session);
		
		//PCDto에 어떤 페이지로 연결될지 지정된 페이지로 연결.
		ModelAndView mv = new ModelAndView(PCDto.getPage());
		
		//첫 입장이나 암호가 틀렸을 시에는 보여줄 문장이 다르게 존재하므로 해당 값이 들어왔는지를 검사 후 뷰에다가 넘긴다.
		if(PCDto.getShowText()!=null) {
			mv.addObject("showText", PCDto.getShowText());
		}
		
		return mv;
	}

}
