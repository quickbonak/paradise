package bpduo.guest.dto;

import lombok.Data;

@Data
public class GIndexDto {
	
	boolean login;
	String id;
	String pass;
	String idn;
	int postIdx[];
	int numberOfPosts;
	int numberOfRandomPosts;
}
