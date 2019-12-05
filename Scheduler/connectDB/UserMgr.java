package connectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class UserMgr {
	private DBConnectionMgr pool;
	
	public UserMgr() {
		pool = new DBConnectionMgr();//local, mysql, root, 123321
	}
	
	public boolean createUser(String id, String pass) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "create user ? identified by ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pass);		
			int cnt = pstmt.executeUpdate();
			if (cnt > 0)
				flag = true;
		} catch (Exception e) {
			System.out.println("회원 등록을 할 수 없습니다. 다른 아이디로 설정해주세요");
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
		
	public boolean grantSelect(UserBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		UserBean userBean = bean;
		String tmp = bean.getSchedule_name();
		try {
			con = pool.getConnection();
			sql = "grant select on "+tmp+".schedule to ? identified by ?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, userBean.getId());
			pstmt.setString(2, userBean.getPassword());
			
			int cnt = pstmt.executeUpdate();
			if (cnt > 0) 	
				flag= true;	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	public boolean grantSelectMemo(UserBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		UserBean userBean = bean;
		String tmp = bean.getSchedule_name();
		try {
			con = pool.getConnection();
			sql = "grant select on "+tmp+".memo to ? identified by ?";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, userBean.getId());
			pstmt.setString(2, userBean.getPassword());
			if(pstmt.executeUpdate()>0) {
				flag=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	public boolean grantInsert(UserBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		UserBean userBean = bean;
		String tmp = userBean.getSchedule_name();
		try {
			con = pool.getConnection();
			sql = "grant insert on "+tmp+".schedule to ? identified by ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userBean.getId());
			pstmt.setString(2, userBean.getPassword());
			
			int cnt = pstmt.executeUpdate();
			if (cnt > 0 ) {
				flag = true;
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	public boolean grantInsertMemo(UserBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		UserBean userBean = bean;
		String tmp = userBean.getSchedule_name();
		try {
			con = pool.getConnection();
			sql = "grant insert on "+tmp+".memo to ? identified by ?";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, userBean.getId());
			pstmt.setString(2, userBean.getPassword());
			if(pstmt.executeUpdate()>0) {
				flag=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	public boolean grantUpdate(UserBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		UserBean userBean = bean;
		String tmp = userBean.getSchedule_name();
		try {
			con = pool.getConnection();
			sql = "grant update on "+tmp+".schedule to ? identified by ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userBean.getId());
			pstmt.setString(2, userBean.getPassword());
			
			int cnt = pstmt.executeUpdate();
			if (cnt > 0) {
				flag = true;
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	public boolean grantUpdateMemo(UserBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		UserBean userBean = bean;
		String tmp = userBean.getSchedule_name();
		try {
			con = pool.getConnection();
			sql = "grant update on "+tmp+".memo to ? identified by ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userBean.getId());
			pstmt.setString(2, userBean.getPassword());
			
			if(pstmt.executeUpdate()>0) {
				flag=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	public boolean grantDelete(UserBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		UserBean userBean = bean;
		String tmp = userBean.getSchedule_name();
		try {
			con = pool.getConnection();
			sql = "grant delete on "+tmp+".schedule to ? identified by ?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, userBean.getId());
			pstmt.setString(2, userBean.getPassword());
			
			int cnt = pstmt.executeUpdate();
			if (cnt > 0 ) 
				
				flag = true;
			
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	public boolean grantDeleteMemo(UserBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		UserBean userBean = bean;
		String tmp = userBean.getSchedule_name();
		try {
			con = pool.getConnection();
			sql = "grant delete on "+tmp+".memo to ? identified by ?";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, userBean.getId());
			pstmt.setString(2, userBean.getPassword());
			if(pstmt.executeUpdate()>0) {
				flag=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}

	public Vector<String> selectUser() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<String> userList = new Vector<>();
		try {
			con = pool.getConnection();
			sql = "select user from user where host not like 'local%'"; 
					
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while(rs.next()) {
				userList.addElement(rs.getString("User"));
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return userList;
	}
	public boolean deleteUser(String user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "delete from user where user=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user);
			int cnt = pstmt.executeUpdate();
			if (cnt > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
}
