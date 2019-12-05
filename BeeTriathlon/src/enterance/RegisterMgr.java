package enterance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dbc.DBConnectionMgr;

public class RegisterMgr {

	private DBConnectionMgr pool;

	public RegisterMgr() {
		pool = DBConnectionMgr.getInstance();
	}

	// 추가
	public boolean RegisterInsert(RegisterBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "insert player(name,pass) values(?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getPass());
			if (pstmt.executeUpdate() == 1)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}

	// 고유 이름 검사
	public boolean RegisterUniqueName(RegisterBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "select count(*) as idc from player where name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getName());
			rs = pstmt.executeQuery();

			rs.next();
			if (rs.getString("idc").equals("1")) {
				flag = true;
				return flag;
			} else {
				flag = false;
				return flag;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return flag;
	}

}
