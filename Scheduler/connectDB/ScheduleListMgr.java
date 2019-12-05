package connectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class ScheduleListMgr {
	
	private DBConnectionMgr pool;
	
	public ScheduleListMgr() {
		pool = new DBConnectionMgr();//localhost/mysql/root/123321
	}
	
	public Vector<String> selectScheduleList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<String> schedule_list = new Vector<String>();
		try {
			con = pool.getConnection();
			sql = "select schedule_name from schedule_list";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				schedule_list.addElement(rs.getString("schedule_name"));
			}

		} catch (Exception e) {
			System.out.println("스케줄 리스트 접근 실패");
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return schedule_list;
	}
	
	public boolean insertScheduleList(String schedule_name, String access_at) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "insert into schedule_list (schedule_name, access_at) values (?, ?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, schedule_name);
			pstmt.setString(2, access_at);

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
	
	public boolean deleteScheduleList(String schedule_name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		
		try {
			con = pool.getConnection();
			sql = "delete from schedule_list where schedule_name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, schedule_name);
			
			int cnt = pstmt.executeUpdate();
			if (cnt > 0 && deleteSchedule(schedule_name)) {
				flag = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	private boolean deleteSchedule(String schedule_name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "drop database "+schedule_name;
			pstmt = con.prepareStatement(sql);

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
	
	public boolean updateScheduleList(String schedule_name, String access_at) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "update schedule_list set access_at=? where schedule_name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, access_at);
			pstmt.setString(2, schedule_name);
			
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
