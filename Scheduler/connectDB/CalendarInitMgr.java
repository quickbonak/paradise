package connectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * �����ٷ� ���α׷��� ó�� �������� �� ip:localhost db��: (default) ���̵�: root ��й�ȣ: 123321
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
	 * ó�� ���α׷��� �����ϸ� mysql(DB)->schedule_list(���̺�)ù��° ���� ����
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
			if (rs.next()) {// mysql�� schedule_list�� ����. �� ���̺� �ȿ� ���� �ִ��� Ȯ�� ��
				if (isCalendarEmpty() == true) {
					System.out.println("schedule_list�� �ƹ��� ���ڵ尡 ����");
					insertNewCalendar(schedule_name);

				} else {// schedule_list�� ������ ���ڵ尡 ����
					getFirstTable();// �ش� ���ڵ��� DB�� �ִ��� Ȯ��
					if (isInitiatedCalendar(schedule_name)) {
						System.out.println("�����ͺ��̽� �����");
					} else {// DB�� ������ �����ͺ��̽����� ����
						createNewScheduler(schedule_name);
					}
				}
			} else {// �ʱ� ���� �ƿ� ���� ����
				createDefaultDB();
				insertNewCalendar(schedule_name);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);

		}
	}

	// �� Ķ���� �߰��ϱ�: local, mysql, root, 123321
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
				// schedule_list ���̺� �ȿ� ���� ����-
				// ���� �̸��� ������ ���̽� �ִ��� Ȯ��
				if (isInitiatedCalendar(new_schedule_name)) {// �����ٷ� db ����
					System.out.println("�� �����ٷ��� �̹� �����մϴ�");
				} else {// �����ٷ� db ����: create db, tables
					createNewScheduler(new_schedule_name);
					System.out.println("���ο� �����ٷ� ������");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
	}

	// ���� Ķ���� �����ϱ�
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
			System.out.println("���� Ķ���� ���� ����");
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
	}

	/**
	 * ó�� ���α׷��� �������� ��_default DB�� ������ ���� ����
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
	 * �� Ķ���� �߰��� ȣ�� mySQL.schedule_list ���̺��� ��� �߰�(���� ������ Ķ������ ���������� ����)
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
	 * ������ Ķ������ �ϳ��� �ִ��� Ȯ��
	 * 
	 * @return empty = true: ������ Ķ������ ���� / empty = false: ������ Ķ������ �ϳ� �̻� ����
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
	 * ������ Ķ���� ���(MySQL.schedule_list���̺� ���ڵ�)�� ù��°�� ��ȯ
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
	 * �� Ķ���� �߰� (���� �̸����� �����ͺ��̽� ����)
	 * @param new_scheduler_name : �� Ķ���� �̸�
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
