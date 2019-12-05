package bpduo.guest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bpduo.guest.dto.GIndexDto;
import bpduo.guest.dto.GuestDto;
import bpduo.guest.mapper.GuestMapper;

@Service
public class GuestServiceImpl implements GuestService {
	
	@Autowired
	GuestMapper mapper;

	@Override
	public GIndexDto guestIndex(HttpSession session) throws Exception {
		
		GIndexDto bean = new GIndexDto();
		bean.setLogin(false);
		bean.setId("");
		bean.setIdn("0");
		bean.setNumberOfRandomPosts(6);
		if(session.getAttribute("id")!=null){
			bean.setNumberOfRandomPosts(5);
			bean.setLogin(true);
			bean.setId(session.getAttribute("id").toString());
			bean.setIdn(session.getAttribute("idn").toString());
		}
		bean.setNumberOfPosts(mapper.howManyPosts());
		Random random = new Random();
		int[] postIdx = new int[bean.getNumberOfRandomPosts()];
		postIdx[0] = 1;
		for(int i=1; i<postIdx.length; i++){
			postIdx[i] = random.nextInt(bean.getNumberOfPosts());
		}
		bean.setPostIdx(postIdx);
		
		return bean;
	}

	@Override
	public List<GuestDto> getPost(GIndexDto indexDto) throws Exception {
		List<GuestDto> gDto = new ArrayList<GuestDto>();
		int[] postIdx = indexDto.getPostIdx();
		
		for(int i = 0; i<postIdx.length; i++) {
			gDto.add(i, mapper.getPost(postIdx[i]));
		}
		return gDto;
	}

	@Override
	public boolean guestRegister(GuestDto gDto) throws Exception {
		
		//기본값은 패스워드 틀림
		boolean isSuccess=false;
		//중복되는 아이디가 없으면 false가 온다. 반전시키기 위해 ! 사용
		if(!mapper.regiUnique(gDto)) {
			mapper.regiDo(gDto);
			isSuccess=true;
		}
		
		return isSuccess;
	}

	@Override
	public boolean gLogin(GuestDto gDto, HttpSession session) throws Exception {

		boolean success = false;
		List<GuestDto> gList = mapper.guestLogin(gDto);
		for(GuestDto i : gList) {
			if(i.getId().equals(gDto.getId())) {
				session.setAttribute("id", i.getId());
				session.setAttribute("idn", i.getIdn());
				success = true;
			}
		}

		return success;
	}

	@Override
	public void setPost(GuestDto gDto) throws Exception {

		System.out.println(gDto.getId());
		mapper.createPost(gDto);
		
	}

	@Override
	public void delPost(GuestDto gDto) throws Exception {
		mapper.deletePost(gDto);
	}

	@Override
	public void upPost(GuestDto gDto) throws Exception {
		mapper.updatePost(gDto);		
	}

	@Override
	public GuestDto modForm(GuestDto gDto) throws Exception {
			gDto = mapper.upForm(gDto);
			System.out.println("aaaaaaaa"+gDto.getConten());
		return gDto;
	}

}
