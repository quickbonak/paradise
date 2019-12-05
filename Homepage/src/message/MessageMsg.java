package message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import board.BoardBean;
import dbc.DBConnectionMgr;

public class MessageMsg {
	private DBConnectionMgr pool;
	Connection con = null;
	PreparedStatement pstmt = null;
	String sql = null;
	ResultSet rs = null;
	
	public MessageMsg() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public void mailDB(MessageBean bean) {
		try {
            //db와 통신 연결을 한다.
			con = pool.getConnection();
            // 던질 sql 쿼리를 기입한다. ?는 이후 과정을 통해 다른 문자로 대체되기 위해 쓴다.
			sql = "insert into messageleejin(sender, senderMail, phoneNumber, msgContent) values(?, ?, ?, ?)";
            // sql 쿼리를 PreparedStatement 클래스에 넣어 준비시킨다.
			pstmt = con.prepareStatement(sql);
            // 첫번째 물음표에 bean.getId() 라는 매서드를 실행하고 리턴받은 값을 넣겠다는 뜻.
			pstmt.setString(1, bean.getSender());
			pstmt.setString(2, bean.getSenderMail());
			pstmt.setString(3, bean.getPhoneNumber());
			pstmt.setString(4, bean.getMsgContent());
			
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
	
	
}
