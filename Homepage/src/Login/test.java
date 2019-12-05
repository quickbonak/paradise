package Login;

import Login.LoginBean;

public class test {

	public static void main(String[] args) {
		Enter inyo = new Enter();
		LoginBean bean = new LoginBean();
		bean.setId("kkk");
		bean.setPass("22");
		
		inyo.registerDB(bean);
	}

}
