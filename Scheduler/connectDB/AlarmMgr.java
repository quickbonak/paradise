package connectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class AlarmMgr {
	private DBConnectionMgr pool;

	public AlarmMgr(String scheduler_name) {
		pool = new DBConnectionMgr("localhost", scheduler_name, "root", "123321");
	}

	public boolean insertAlarm(String start_alarm, String alarm_title) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "insert into alarm (start_alarm, schedule_title) values (?, ?)";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, start_alarm);
			pstmt.setString(2, alarm_title);

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
	
	public boolean deleteAlarm(int id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "delete from alarm where id =?";
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
	
	
	public Vector<AlarmBean> selectAlarm() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<AlarmBean> vAlarm = new Vector<>();
		try {
			con = pool.getConnection();
			sql = "select start_alarm, schedule_title from alarm";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				AlarmBean bean = new AlarmBean();
				bean.setStart_alarm(rs.getString(1));
				bean.setSchedule_title(rs.getString(2));
				vAlarm.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return vAlarm;
	}
}
