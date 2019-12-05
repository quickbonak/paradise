package bpduo.guest.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import bpduo.guest.dto.GIndexDto;
import bpduo.guest.dto.GuestDto;

public interface GuestService {
	
	GIndexDto guestIndex(HttpSession session) throws Exception;
	List<GuestDto> getPost(GIndexDto indexDto) throws Exception;
	boolean guestRegister(GuestDto gDto) throws Exception;
	boolean gLogin(GuestDto gDto, HttpSession session) throws Exception;
	void setPost(GuestDto gDto) throws Exception;
	void delPost(GuestDto gDto) throws Exception;
	void upPost(GuestDto gDto) throws Exception;
	GuestDto modForm(GuestDto gDto) throws Exception;

}