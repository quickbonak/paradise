package bpduo.guest.dto;

import lombok.Data;

@Data
public class GuestDto {
	
	int idx, idn, readcount, isSuccess;
	String id, sdate, title, conten, pass, isLogOut;

}
