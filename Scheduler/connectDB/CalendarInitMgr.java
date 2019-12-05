package connectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 스케줄러 프로그램을 처음 실행했을 때 ip:localhost db명: (default) 아이디: root 비밀번호: 123321
 */

public class CalendarInitMgr extends DBConnectionMgr {

	private DBConnectionMgr pool;
	private String host;
	private String schedule_name;
	private String user;
	private String password;
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat sdf_daylong = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 처음 프로그램을 실행하면 mysql(DB)->schedule_list(테이블)첫번째 것을 실행
	 */
	public CalendarInitMgr() {
		setHost("localhost");
		setSchedule_name("mysql");
		setUser("root");
		setPassword("123321");
		pool = new DBConnectionMgr(host, schedule_name, user, password);

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			con = pool.getConnection();
			sql = "show tables in mysql like 'schedule_list';";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			schedule_name = "default_scheduler";
			if (rs.next()) {// mysql에 schedule_list가 있음. 이 테이블 안에 값이 있는지 확인 중
				if (isCalendarEmpty() == true) {
					System.out.println("schedule_list에 아무런 레코드가 없음");
					insertNewCalendar(schedule_name);

				} else {// schedule_list에 생성된 레코드가 있음
					getFirstTable();// 해당 레코드의 DB가 있는지 확인
					if (isInitiatedCalendar(schedule_name)) {
						System.out.println("데이터베이스 연결됨");
					} else {// DB가 없으면 데이터베이스부터 생성
						createNewScheduler(schedule_name);
					}
				}
			} else {// 초기 설정 아예 없는 상태
				createDefaultDB();
				insertNewCalendar(schedule_name);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);

		}
	}

	// 새 캘린더 추가하기: local, mysql, root, 123321
	public CalendarInitMgr(String new_schedule_name) {
		System.out.println(new_schedule_name);
		pool = new DBConnectionMgr();
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = pool.getConnection();
			sql = "select * from schedule_list where schedule_name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, new_schedule_name);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				System.out.println("add new calendar failed");
			} else {
				insertNewCalendar(new_schedule_name);
				// schedule_list 테이블 안에 값이 있음-
				// 같은 이름의 데이터 베이스 있는지 확인
				if (isInitiatedCalendar(new_schedule_name)) {// 스케줄러 db 존재
					System.out.println("이 스케줄러는 이미 존재합니다");
				} else {// 스케줄러 db 없음: create db, tables
					createNewScheduler(new_schedule_name);
					System.out.println("새로운 스케줄러 생성됨");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
	}

	// 공유 캘린더 접속하기
	public CalendarInitMgr(String host, String database, String id, String pass) {
		setHost(host);
		setSchedule_name(database);
		setUser(id);
		setPassword(pass);
		pool = new DBConnectionMgr(this.host, schedule_name, user, password);

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			con = pool.getConnection();
			sql = "show tables in " + database;
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

		} catch (Exception e) {
			System.out.println("공유 캘린더 접속 실패");
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
	}

	/**
	 * 처음 프로그램을 실행했을 때_default DB가 없으면 새로 생성
	 */
	private boolean createDefaultDB() {
		String db_default = null;
		pool = new DBConnectionMgr("localhost", "mysql", "root", "123321");

		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "create table schedule_list(id int auto_increment primary key, schedule_name varchar(20), access_at datetime);";
			pstmt = con.prepareStatement(sql);
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

	/**
	 * 새 캘린더 추가시 호출 mySQL.schedule_list 테이블에서 목록 추가(아직 실제로 캘린더가 생성되지는 않음)
	 */
	private boolean insertNewCalendar(String new_scheduler_name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "insert into schedule_list (schedule_name, access_at) values (?, ?);";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, new_scheduler_name);
			pstmt.setString(2, sdf.format(new Date()));
			int cnt = pstmt.executeUpdate();
			if (cnt > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}

		createNewScheduler(schedule_name);
		return flag;
	}

	/**
	 * 생성된 캘린더가 하나라도 있는지 확인
	 * 
	 * @return empty = true: 생성된 캘린더가 없음 / empty = false: 생성된 캘린더가 하나 이상 있음
	 * 
	 */
	private boolean isCalendarEmpty() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean empty = false;

		try {
			con = pool.getConnection();
			sql = "select count(*) from schedule_list";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) > 0)
					empty = false;
				else
					empty = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return empty;
	}

	/**
	 * 생성된 캘린더 목록(MySQL.schedule_list테이블 레코드)중 첫번째를 반환
	 */
	private void getFirstTable() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			con = pool.getConnection();
			sql = "select schedule_name from schedule_list order by access_at desc limit 1";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				setSchedule_name(rs.getString("schedule_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
	}

	private boolean isInitiatedCalendar(String scheduler_name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		boolean initiated = false;
		try {
			con = pool.getConnection();
			sql = "show databases like '" + scheduler_name + "';";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getString(1) != null)
					initiated = true;
				else
					initiated = false;
			}

		} catch (Exception e) {
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return initiated;

	}

	/**
	 * 새 캘린더 추가 (받은 이름으로 데이터베이스 생성)
	 * @param new_scheduler_name : 새 캘린더 이름
	 */
	private boolean createNewScheduler(String new_scheduler_name) {
		pool = new DBConnectionMgr("localhost", "mysql", "root", "123321");
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;

		try {
			con = pool.getConnection();
			sql = "create database " + new_scheduler_name + ";";

			pstmt = con.prepareStatement(sql);
			/* pstmt.setString(1, new_scheduler_name); */

			if (pstmt.executeUpdate() > 0) {
				flag = true;
			}

			createTableSetting(new_scheduler_name);
			createTableSchedule(new_scheduler_name);
			createTableMemo(new_scheduler_name);
			createTableAlarm(new_scheduler_name);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}

		return flag;

	}

	private boolean createTableSetting(String new_schedule_name) {
		pool = new DBConnectionMgr("localhost", new_schedule_name, "root", "123321");

		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;

		try {
			con = pool.getConnection();
			sql = "create table setting( id int auto_increment primary key, themeNo int, font varchar(10), disturb_from datetime, disturb_to datetime );";
			pstmt = con.prepareStatement(sql);

			if (pstmt.executeUpdate() > 0) {
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}

		return flag;
	}

	private boolean createTableSchedule(String new_schedule_name) {

		pool = new DBConnectionMgr("localhost", new_schedule_name, "root", "123321");

		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;

		try {
			con = pool.getConnection();
			sql = "create table schedule(id int auto_increment primary key, title varchar(20) not null, content text, time_from datetime, time_to datetime, location varchar(20), modified_at datetime, tmp1 int, tmp2 int, tmp3 int);";
			pstmt = con.prepareStatement(sql);

			if (pstmt.executeUpdate() > 0) {
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}

		return flag;
	}

	private boolean createTableMemo(String new_schedule_name) {
		pool = new DBConnectionMgr("localhost", new_schedule_name, "root", "123321");

		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;

		try {
			con = pool.getConnection();
			sql = "create table memo( id int auto_increment primary key, content varchar(400), label_color varchar(10), modified_at datetime, tmp1 int, tmp2 int, tmp3 int);";
			pstmt = con.prepareStatement(sql);

			if (pstmt.executeUpdate() > 0) {
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}

		return flag;
	}

	private boolean createTableAlarm(String new_schedule_name) {
		pool = new DBConnectionMgr("localhost", new_schedule_name, "root", "123321");
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;

		try {
			con = pool.getConnection();
			sql = "create table alarm( id int auto_increment primary key, start_alarm datetime, schedule_title varchar(50) )";
																													
			pstmt = con.prepareStatement(sql);

			if (pstmt.executeUpdate() > 0) {
				flag = true;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}

		return flag;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getSchedule_name() {
		return schedule_name;
	}

	public void setSchedule_name(String schedule_name) {
		this.schedule_name = schedule_name;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
