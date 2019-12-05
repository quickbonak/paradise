package connectDB;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JLabel;

public class MemoMgr {
	private DBConnectionMgr pool;
	
	public MemoMgr(String scheduler_name) {
		pool = new DBConnectionMgr("localhost", scheduler_name, "root", "123321");
	}
	
	public MemoMgr(CalendarInitMgr cim) {
		pool = new DBConnectionMgr(cim.getHost(), cim.getSchedule_name(), cim.getUser(), cim.getPassword());
	}
	
	//메모 세이브
	public boolean insertMemo(String content, String label_color, String modified_at) {
		Connection con;
		PreparedStatement pstmt= null;
		String sql = null;
		boolean flag = false;
		
		try {
			con = pool.getConnection();
			sql = "insert into memo (content, label_color, modified_at) values (?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, content);
			pstmt.setString(2, label_color);
			pstmt.setString(3, modified_at);
			
			if(pstmt.executeUpdate()>0) {
				flag = true;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}
	//메모 로드
	public Vector<MemoBean> selectMemo() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<MemoBean> memoList = new Vector<MemoBean>();
		try {
			con = pool.getConnection();
			sql = "select * from memo order by modified_at";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MemoBean bean = new MemoBean();
				bean.setId(rs.getInt("id"));
				bean.setContent(rs.getString("content"));
				bean.setLabel_color(rs.getString("label_color"));
				bean.setModified_at(rs.getString("modified_at"));
				memoList.add(bean);
				
			}

		} catch (Exception e) {
			System.out.println("권한이 없습니다: 메모 불러오기 ");
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return memoList;
	}

	public boolean deleteMemo(int id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "delete from memo where id="+id;
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
	
	public boolean deleteAllMemo() {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "delete from memo;";
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

	public boolean updateMemo(String content, String label_color, String modified_at, String prevText) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "update memo set content = ?, label_color = ?, modified_at = ? where content = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, content);
			pstmt.setString(2, label_color);
			pstmt.setString(3, modified_at);
			pstmt.setString(4, prevText);
			
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
