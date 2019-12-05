package bpduo.main.service;

import javax.servlet.http.HttpSession;

import bpduo.main.dto.PageControlDto;

public interface PageControlService {
	PageControlDto checkNGo(PageControlDto PCDto, HttpSession session) throws Exception;
}
