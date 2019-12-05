package bpduo.guest.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import bpduo.guest.dto.GuestDto;

@Mapper
public interface GuestMapper {
	int howManyPosts() throws Exception;
	GuestDto getPost(int nthPost) throws Exception;
	boolean regiUnique(GuestDto gDto) throws Exception;
	void regiDo(GuestDto gDto) throws Exception;
	List<GuestDto> guestLogin(GuestDto gDto) throws Exception;
	void createPost(GuestDto gDto) throws Exception;
	void deletePost(GuestDto gDto) throws Exception;
	void updatePost(GuestDto gDto) throws Exception;
	GuestDto upForm(GuestDto gDto) throws Exception;
}
