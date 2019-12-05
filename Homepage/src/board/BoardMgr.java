package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dbc.DBConnectionMgr;

public class BoardMgr {
	private DBConnectionMgr pool;
	
	public BoardMgr() {
		pool = DBConnectionMgr.getInstance();
	}

	Connection con = null;
	PreparedStatement pstmt = null;
	String sql = null;
	ResultSet rs = null;
	
	
	public void writeDB(BoardBean bean) {
		
		try {
            //db와 통신 연결을 한다.
			con = pool.getConnection();
            // 던질 sql 쿼리를 기입한다. ?는 이후 과정을 통해 다른 문자로 대체되기 위해 쓴다.
			sql = "insert into homeboard(idn,title,conten) values(?,?,?)";
            // sql 쿼리를 PreparedStatement 클래스에 넣어 준비시킨다.
			pstmt = con.prepareStatement(sql);
            // 첫번째 물음표에 bean.getId() 라는 매서드를 실행하고 리턴받은 값을 넣겠다는 뜻.
			pstmt.setString(1, ""+(bean.getIdn()));
			pstmt.setString(2, bean.getTitle());
			pstmt.setString(3, bean.getConten());
            // 준비가 끝난 sql쿼리를 db에 전달해 실행시키고, 성공적이면 1이라는 리턴값을 돌려주는 클래스이다. 지금은 반환값을 쓸 생각은 없으므로 변수에 반환값을 저장하거나 하지는 않았다.
			pstmt.executeUpdate();
		} catch (Exception e) {
            // try 과정에서 에러가 발생할 경우 발생한 에러를 출력해주는 작업을 함.
			e.printStackTrace();
		} finally {
            // try 작업이 다 끝나면 무조건 작동하는 finally 부분으로, 여기에서는 db와 연결한 통신을 해제하는데 쓰인다.
			pool.freeConnection(con, pstmt);
		}

	}

	public BoardBean getPost(int nthPost) {
		
		BoardBean bean = new BoardBean();

		try {
			con = pool.getConnection();
			sql = "select idx, idn, id, sdate, readcount, title, conten  from homeboard natural join homeregister order by idx desc";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.absolute(nthPost);
			bean.setIdx(Integer.parseInt(rs.getString("idx").toString()));	
			bean.setIdn(Integer.parseInt(rs.getString("idn").toString()));
			bean.setId(rs.getString("id").toString());
			bean.setsDate(rs.getString("sdate").toString());
			bean.setReadCount(Integer.parseInt(rs.getString("readcount").toString()));
			bean.setTitle(rs.getString("title").toString());
			bean.setConten(rs.getString("conten").toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
		return bean;
	}
	
	
	public int howManyPost() {
		int countPost = 0;
		
		try {
			con = pool.getConnection();
			sql = "select count(idx) as countNum from homeboard";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			countPost=Integer.parseInt(rs.getString("countNum").toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
		return countPost;
	}
	
	public void deletePost(int idx){
		
		try {
			con = pool.getConnection();
			sql = "delete from homeboard where idx = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ""+idx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
	}
	
	public void updatePost(BoardBean bean) {
		try {
			con = pool.getConnection();
			sql = "update homeboard set title = ?, conten = ? where idx= ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getTitle());
			pstmt.setString(2, bean.getConten());
			pstmt.setString(3, bean.getIdx()+"");
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
	}
	
	public BoardBean getPostByIdx(int idx) {
		BoardBean bean = new BoardBean();
		try {
			con = pool.getConnection();
			sql = "select idx, idn, id, sdate, readcount, title, conten  from homeboard natural join homeregister where idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, idx+"");
			rs = pstmt.executeQuery();
			rs.next();
			bean.setIdx(Integer.parseInt(rs.getString("idx").toString()));
			bean.setIdn(Integer.parseInt(rs.getString("idn").toString()));
			bean.setId(rs.getString("id").toString());
			bean.setsDate(rs.getString("sdate").toString());
			bean.setReadCount(Integer.parseInt(rs.getString("readcount").toString()));
			bean.setTitle(rs.getString("title").toString());
			bean.setConten(rs.getString("conten").toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
		return bean;
	}
	
}
