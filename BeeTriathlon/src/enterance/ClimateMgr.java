package enterance;

import java.sql.Connection;
import java.sql.PreparedStatement;

import dbc.DBConnectionMgr;

public class ClimateMgr {
	
	private DBConnectionMgr pool;
	
	public ClimateMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public void ClimateInsert(int temp[],int rain[], int snow[], int disp[], String name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String tableName = name + "Climate";
		try {
			con = pool.getConnection();
			
			sql = "create table "+tableName+"(turn int auto_increment primary key,temp int, rain int, snow int, disp int);";
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			
			for(int i=0; i<temp.length; i++) {
				sql = "insert "+tableName+"(temp,rain,snow,disp) values(?,?,?,?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,temp[i]+"");
				pstmt.setString(2,rain[i]+"");
				pstmt.setString(3,snow[i]+"");
				pstmt.setString(4,disp[i]+"");
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
	}
	
}
