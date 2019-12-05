package enterance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dbc.DBConnectionMgr;

public class LoginMgr {

	private DBConnectionMgr pool;

	public LoginMgr() {
		pool = DBConnectionMgr.getInstance();
	}

	public boolean LoginProcess(RegisterBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "select * from player where name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getName());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String result = rs.getString("pass").toString();

				flag = (result.equals(bean.getPass()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return flag;
	}

}
