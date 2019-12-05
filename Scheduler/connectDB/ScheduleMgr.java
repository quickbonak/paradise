package connectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Vector;

public class ScheduleMgr {
	private DBConnectionMgr pool;
	public ScheduleMgr(String scheduler_name) {
		pool = new DBConnectionMgr("localhost", scheduler_name, "root", "123321");
	}
	
	public ScheduleMgr(CalendarInitMgr cim) {
		pool = new DBConnectionMgr(cim.getHost(), cim.getSchedule_name(), cim.getUser(), cim.getPassword());
	}
	
	
	public boolean insertSchedule(ScheduleBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "insert into schedule (title, content, time_from, time_to, location, modified_at)"
					+ "values (?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getTitle());
			pstmt.setString(2, bean.getContent());
			pstmt.setString(3, bean.getTime_from());
			pstmt.setString(4, bean.getTime_to());
			pstmt.setString(5, bean.getLocation());
			pstmt.setString(6, bean.getModified_at());
			
			int cnt = pstmt.executeUpdate();
			if (cnt > 0)
				flag = true;
		} catch (Exception e) {
			System.out.println("일정을 추가할 수 있는 권한이 없습니다");
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}

	public boolean deleteSchedule(int id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "delete from schedule where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			int cnt = pstmt.executeUpdate();
			if (cnt > 0)
				flag = true;
		} catch (Exception e) {
			System.out.println("일정을 삭제할 수 있는 권한이 없습니다");
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	public Vector<ScheduleBean> selectSchedule() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ScheduleBean> vScdList = new Vector<>();
		try {
			con = pool.getConnection();
			//id, title, content, time_from, time_to, location, modified_at
			sql = "select * from schedule";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ScheduleBean scheduleBean = new ScheduleBean();
				scheduleBean.setId(rs.getInt("id"));
				scheduleBean.setContent(rs.getString("title"));
				scheduleBean.setContent(rs.getString("content"));
				scheduleBean.setTime_from(rs.getString("time_from"));
				scheduleBean.setTime_to(rs.getString("titme_to"));
				scheduleBean.setLocation(rs.getString("location"));
				scheduleBean.setModified_at(rs.getString("modified_at"));
				vScdList.addElement(scheduleBean);
			}

		} catch (Exception e) {
			System.out.println("일정을 볼 수 있는 권한이 없습니다");
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return vScdList;
	}
	
	public Vector<ScheduleBean> selectScheduleMonthly(int year, int month) {//*8월이면 month=7 20171115
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ScheduleBean> vScdList = new Vector<>();
		try {
			con = pool.getConnection();
			sql = "select * from schedule where (time_from between ? and ?) or (time_to between ? and ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, year+"-"+month+"-01");
			pstmt.setString(2, year+"-"+month+"-31");
			pstmt.setString(3, year+"-"+month+"-01");
			pstmt.setString(4, year+"-"+month+"-31");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ScheduleBean scheduleBean = new ScheduleBean();
				scheduleBean.setId(rs.getInt("id"));
				scheduleBean.setTitle(rs.getString("title"));
				scheduleBean.setContent(rs.getString("content"));
				scheduleBean.setTime_from(rs.getString("time_from"));
				scheduleBean.setTime_to(rs.getString("time_to"));
				scheduleBean.setLocation(rs.getString("location"));
				scheduleBean.setModified_at(rs.getString("modified_at"));
				vScdList.addElement(scheduleBean);
			}

		} catch (Exception e) {
			System.out.println("일정을 볼 수 있는 권한이 없습니다");
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return vScdList;
	}

	public Vector<ScheduleBean> selectScheduleWeekly(LocalDate everySunday, LocalDate evenySatday){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ScheduleBean> vScdList = new Vector<>();
		try {
			con = pool.getConnection();
			sql = "select * from schedule where (time_from between ? and ?) or (time_to between ? and ?) order by time_from, time_to";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, everySunday.toString());
			pstmt.setString(2, evenySatday.toString());
			pstmt.setString(3, everySunday.toString());
			pstmt.setString(4, evenySatday.toString());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ScheduleBean scheduleBean = new ScheduleBean();
				scheduleBean.setId(rs.getInt("id"));
				scheduleBean.setTitle(rs.getString("title"));
				scheduleBean.setContent(rs.getString("content"));
				scheduleBean.setTime_from(rs.getString("time_from"));
				scheduleBean.setTime_to(rs.getString("time_to"));
				scheduleBean.setLocation(rs.getString("location"));
				scheduleBean.setModified_at(rs.getString("modified_at"));
				vScdList.addElement(scheduleBean);
			}

		} catch (Exception e) {
			System.out.println("일정을 볼 수 있는 권한이 없습니다");
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return vScdList;
	}
	public Vector<ScheduleBean> selectScheduleDaily(LocalDate day){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ScheduleBean> vScdList = new Vector<>();
		try {
			con = pool.getConnection();
			sql = "select * from schedule where (time_from between ? and ?) or (time_to between ? and ?) order by time_from, time_to";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, day.toString());
			pstmt.setString(2, day.toString()+" 23:59:59");
			pstmt.setString(3, day.toString());
			pstmt.setString(4, day.toString()+" 23:59:59");

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ScheduleBean scheduleBean = new ScheduleBean();
				scheduleBean.setId(rs.getInt("id"));
				scheduleBean.setTitle(rs.getString("title"));
				scheduleBean.setContent(rs.getString("content"));
				scheduleBean.setTime_from(rs.getString("time_from"));
				scheduleBean.setTime_to(rs.getString("time_to"));
				scheduleBean.setLocation(rs.getString("location"));
				scheduleBean.setModified_at(rs.getString("modified_at"));
				vScdList.addElement(scheduleBean);
			}

		} catch (Exception e) {
			System.out.println("일정을 볼 수 있는 권한이 없습니다");
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return vScdList;
	}
	
	
	public Vector<ScheduleBean> selectScheduleDaily(String day){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ScheduleBean> vScdList = new Vector<>();
		try {
			con = pool.getConnection();
			sql = "select * from schedule where (time_from between ? and ?) or (time_to between ? and ?) order by time_from, time_to";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, day);
			pstmt.setString(2, day+" 23:59:59");
			pstmt.setString(3, day.toString());
			pstmt.setString(4, day.toString()+" 23:59:59");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ScheduleBean scheduleBean = new ScheduleBean();
				scheduleBean.setId(rs.getInt("id"));
				scheduleBean.setTitle(rs.getString("title"));
				scheduleBean.setContent(rs.getString("content"));
				scheduleBean.setTime_from(rs.getString("time_from"));
				scheduleBean.setTime_to(rs.getString("time_to"));
				scheduleBean.setLocation(rs.getString("location"));
				scheduleBean.setModified_at(rs.getString("modified_at"));
				vScdList.addElement(scheduleBean);
			}

		} catch (Exception e) {
			System.out.println("일정을 볼 수 있는 권한이 없습니다");
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return vScdList;
	}
	
	public Vector<ScheduleBean> searchSchedule(String query){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ScheduleBean> vSchList = new Vector<>();
		try {
			con = pool.getConnection();
			sql = query;
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				ScheduleBean bean = new ScheduleBean();
				bean.setTitle(rs.getString(1));//title
				bean.setContent(rs.getString(2));
				bean.setTime_from(rs.getString(3));
				bean.setTime_to(rs.getString(4));
				bean.setLocation(rs.getString(5));
				vSchList.add(bean);
			}
			
		} catch (Exception e) {
			System.out.println("일정을 볼 수 있는 권한이 없습니다");
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return vSchList;
		
	}
	
}
