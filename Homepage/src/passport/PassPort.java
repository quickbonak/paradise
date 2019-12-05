package passport;

public class PassPort {
	//LJ
	final String ljpass = "1229";
	final String ljpage = "binary.jsp";
	//NK
	final String nkpass = "0823";
	final String nkpage = "/paradise/portfolio1.html";
	String giveBack = "wrong";
	
	public String passCheck(String pass) {
		
			switch(pass) {
			case ljpass:
				giveBack=ljpage;
				break;
			case nkpass:
				giveBack=nkpage;
				break;
			}
		
		return giveBack;
	}
}
