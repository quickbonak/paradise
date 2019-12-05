package Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dbc.DBConnectionMgr;

//!!!!!!!!! 이 클래스는 다른데서는 다른 방식을 쓰므로 절대적으로 외울 필요는 없고, 원리적인 이해를 위해서 그때그때 보고 참고해.
public class Enter {
    
    
	// DBConnectionMgr 클래스를 담을 수 있는 pool 이라는 이름의 메모리 빈 공간을 생성시킨다.
	private DBConnectionMgr pool;
    
	public Enter() {
        // pool 에다가 db와 교신을 위한 도구들을 탑재시킨다.
		pool = DBConnectionMgr.getInstance();
	}

	public boolean registerUnique(LoginBean bean) {
		boolean isUnique = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
    
		
		try {
			con = pool.getConnection();
			sql = "select count(*) as dupleCount from homeregister where id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getId());
			rs = pstmt.executeQuery();
			rs.next();
			if(rs.getString("dupleCount").toString().equals("0")) {
				isUnique=true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
		return isUnique;
	}
	
	public void registerDB(LoginBean bean) {
        
        // db와의 통신을 위한 연결로 확보를 위해 쓰임. 
		Connection con = null;
        // db에 값을 던지기 전까지 각종 준비를 시키는 것에 쓰임.
		PreparedStatement pstmt = null;
        // PreparedStatement를 통해 쿼리를 db에다가 던지기 전, 임시로 쿼리문자를 저장시키기 위해 쓰임.
		String sql = null;
		try {
            //db와 통신 연결을 한다.
			con = pool.getConnection();
            // 던질 sql 쿼리를 기입한다. ?는 이후 과정을 통해 다른 문자로 대체되기 위해 쓴다.
			sql = "insert into homeregister(id,pass) values(?, md5(?))";
            // sql 쿼리를 PreparedStatement 클래스에 넣어 준비시킨다.
			pstmt = con.prepareStatement(sql);
            // 첫번째 물음표에 bean.getId() 라는 매서드를 실행하고 리턴받은 값을 넣겠다는 뜻.
			pstmt.setString(1, bean.getId());
			pstmt.setString(2, bean.getPass());
            // 준비가 끝난 sql쿼리를 db에 전달해 실행시키고, 성공적이면 1이라는 리턴값을 돌려주는 클래스이다. 지금은 반환값을 쓸 생각은 없으므로 변수에 반환값을 저장하거나 하지는 않았다.
			pstmt.executeUpdate();
		} catch (Exception e) {
            // try 과정에서 에러가 발생할 경우 발생한 에러를 출력해주는 작업을 함.
			e.printStackTrace();
		} finally {
            // try 작업이 다 끝나면 무조건 작동하는 finally 부분으로, 여기에서는 db와 연결한 통신을 해제하는데 쓰인다.
			pool.freeConnection(con, pstmt);
		}

	}
	
	public LoginBean LoginDB(LoginBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
        // db에 쿼리를 던져 결과값을 가져오는 작업까지 필요하므로, 결과값을 담아둘 ResultSet 이라는 형식의 클래스가 필요함.
		ResultSet rs = null;
		String sql = null;
        // 로그인 성공상태를 알릴 신호로 쓰기위해 리턴값이 담길 변수를 선언한다. 여기서 우리는 0을 메서드가 잘 실행되지 않아 값이 변화가 없음을 눈치채기 위한 기본신호로서 썼다.
		bean.setIsSuccess(0);
		
		try {
			con = pool.getConnection();
			sql = "select id,idn from homeregister where pass = md5(?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getPass());
            // ResultSet에 쿼리가 실행되고 난 결과값이 담긴다.
			rs = pstmt.executeQuery();
            // next()를 통해 결과값행의 한행 아래로 커서를 내린다.
            // 아이디와 일치하는 db값이 있다면 next() 매서드를 실행했을 때 값이 존재할테니 1이 반환되어 if절이 실행되고,
            // 일치하는 아이디가 없을경우 결과값이 없을테니 next()로 한줄 넘겼을 때 받을 값이 없고 0이 반환되어 else 절이 실행된다.
			while(rs.next()) {
				String dbId = rs.getString("id").toString();
				if(dbId.equals(bean.getId())) {
					bean.setIdn(Integer.parseInt(rs.getString("idn").toString()));
					bean.setIsSuccess(1);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
        // 로그인 성공여부를 0,1,2,3 이라는 숫자를 통해 판별시켜줄 인트형 신호를 리턴시킨다.
        // 말 그대로 신호로 쓰이는 역할일 뿐이므로, 위에서 0,1,2,3이 아닌 다른 숫자로 했다면
        // 신호를 받는 측에서도 그 규칙에 따라 신호를 판별하면 된다.
        // 예를들어 로그인 성공신호를 99로 정했다면 받는쪽도 99와 일치할경우 로그인 성공 작업을 하면 된다는 것.
		return bean;
	}
	
	

}
