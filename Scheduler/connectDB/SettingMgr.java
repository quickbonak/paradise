package connectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import layout.MainScreen;

public class SettingMgr {
	
	private DBConnectionMgr pool;
	private String schedule_name;
	
	public SettingMgr(){
		this("default_scheduler");
	}
	public SettingMgr(String schedule_name) {
		pool = new DBConnectionMgr("localhost", schedule_name, "root", "123321");	
	}
	public SettingMgr(CalendarInitMgr pool) {	
		this.pool = new DBConnectionMgr(pool.getHost(), pool.getSchedule_name(), pool.getUser(), pool.getPassword());

	}
	
	//초기설정이 없을 경우
	private boolean insertSetting() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean flag = false;

		try {
			con = pool.getConnection();
			sql = "insert into setting (themeNo) values (0)";
			pstmt = con.prepareStatement(sql);
		
			if(pstmt.executeUpdate()>0) {
				flag = true;
			}
			
		} catch (Exception e) {
			System.out.println("공유 캘린더는 테마 변경이 불가능합니다");
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return flag;
	}
	
	public int setUpTheme() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int themeNo = 0; 
		try {
			con = pool.getConnection();
			sql = "select themeNo from setting";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				themeNo = rs.getInt("themeNo");
			}else {
				System.out.println("저장된 테마가 없습니다");
				insertSetting();
			}
		} catch (Exception e) {
			System.out.println("공유 캘린더는 테마를 불러올 수 없습니다");
			
		} 

		return themeNo;
		
	}

	public boolean saveTheme(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "update setting set themeNo="+num;
			pstmt = con.prepareStatement(sql);

			int cnt = pstmt.executeUpdate();
			if (cnt > 0)
				flag = true;
		} catch (Exception e) {
			System.out.println("권한이 없습니다 : 테마 저장");
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
}
