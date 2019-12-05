package Login;


// 두개의 스트링 값을 포장해 배달다니기 위한 클래스이다.
public class LoginBean {
	
	String id,pass;
	int idn, isSuccess;
	
	public LoginBean() {
	}
	
	public int getIdn() {
		return idn;
	}

	public void setIdn(int idn) {
		this.idn = idn;
	}

	public int getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getId() {
		return id;
	}

	public void setId(String ok) {
		id = ok;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	

}
