package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dbc.DBConnectionMgr;

public class RecordMgr {
	
	private DBConnectionMgr pool;
	
	public RecordMgr() {
		pool = DBConnectionMgr.getInstance();
	}

	public RecordBean loadRecords(String name){
		
		ResultBean rBean = new ResultBean();
		rBean.setName(name);
		RecordBean recBean = new RecordBean();
		String[] playerName = new String[10];
		int[] playerIdx = new int[10];
		int[][] hikeRecords = new int [10][3];
		int[][] chinupRecords = new int [10][3];
		int[][] swimRecords = new int [10][3];
		int[][] allRecords = new int[10][9];
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			con = pool.getConnection();
			sql = "select * from finalresult where name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			rs.next();
			playerIdx[0] = Integer.parseInt(rs.getString("idx").toString());
			playerIdx[1] = Integer.parseInt(rs.getString("player1idx").toString());
			playerIdx[2] = Integer.parseInt(rs.getString("player2idx").toString());
			playerIdx[3] = Integer.parseInt(rs.getString("player3idx").toString());
			playerIdx[4] = Integer.parseInt(rs.getString("player4idx").toString());
			playerIdx[5] = Integer.parseInt(rs.getString("player5idx").toString());
			playerIdx[6] = Integer.parseInt(rs.getString("player6idx").toString());
			playerIdx[7] = Integer.parseInt(rs.getString("player7idx").toString());
			playerIdx[8] = Integer.parseInt(rs.getString("player8idx").toString());
			playerIdx[9] = Integer.parseInt(rs.getString("player9idx").toString());
			
			pstmt.close();
			rs.close();
			
			sql = "select * from finalresult where idx=? OR idx=? OR idx=? OR idx=? OR idx=? OR idx=? OR idx=? OR idx=? OR idx=? OR idx=? order by totalpoints desc";
			pstmt = con.prepareStatement(sql);
			for (int i = 0; i < 10; i++) {
				pstmt.setString(i+1, playerIdx[i]+"");
			}
			rs = pstmt.executeQuery();
			// 플레이어 10명에다 idx, 종목 3개 기록과 랭크, 그리고 총점수와 총랭크를 가져온다. 총점수순
			for (int i = 0; i < 10; i++) {
				rs.next();
				playerIdx[i] = Integer.parseInt(rs.getString("idx").toString());
				playerName[i] = rs.getString("name").toString();
				allRecords[i][0] = Integer.parseInt(rs.getString("idx").toString());
				allRecords[i][1] = Integer.parseInt(rs.getString("hike").toString());
				allRecords[i][2] = Integer.parseInt(rs.getString("hikerank").toString());
				allRecords[i][3] = Integer.parseInt(rs.getString("chinup").toString());
				allRecords[i][4] = Integer.parseInt(rs.getString("chinuprank").toString());
				allRecords[i][5] = Integer.parseInt(rs.getString("swim").toString());
				allRecords[i][6] = Integer.parseInt(rs.getString("swimrank").toString());
				allRecords[i][7] = Integer.parseInt(rs.getString("totalpoints").toString());
				allRecords[i][8] = Integer.parseInt(rs.getString("finalrank").toString());
			}
			pstmt.close();
			rs.close();
			recBean.setPlayerIdx(playerIdx);
			recBean.setName(playerName);
			recBean.setAllRecords(allRecords);
			
			sql = "select idx, hike, hikerank from finalresult where idx=? OR idx=? OR idx=? OR idx=? OR idx=? OR idx=? OR idx=? OR idx=? OR idx=? OR idx=? order by hike asc";
			pstmt = con.prepareStatement(sql);
			for (int i = 0; i < 10; i++) {
				pstmt.setString(i+1, playerIdx[i]+"");
			}
			rs = pstmt.executeQuery();
			for (int i = 0; i < 10; i++) {
				rs.next();
				hikeRecords[i][0] = Integer.parseInt(rs.getString("idx").toString());
				hikeRecords[i][1] = Integer.parseInt(rs.getString("hike").toString());
				hikeRecords[i][2] = Integer.parseInt(rs.getString("hikerank").toString());
			}
			pstmt.close();
			rs.close();
			recBean.setHikeRecords(hikeRecords);
			
			sql = "select idx, chinup, chinuprank from finalresult where idx=? OR idx=? OR idx=? OR idx=? OR idx=? OR idx=? OR idx=? OR idx=? OR idx=? OR idx=? order by chinup desc";
			pstmt = con.prepareStatement(sql);
			for (int i = 0; i < 10; i++) {
				pstmt.setString(i+1, playerIdx[i]+"");
			}
			rs = pstmt.executeQuery();
			for (int i = 0; i < 10; i++) {
				rs.next();
				chinupRecords[i][0] = Integer.parseInt(rs.getString("idx").toString());
				chinupRecords[i][1] = Integer.parseInt(rs.getString("chinup").toString());
				chinupRecords[i][2] = Integer.parseInt(rs.getString("chinuprank").toString());
			}
			pstmt.close();
			rs.close();
			recBean.setChinupRecords(chinupRecords);
			
			sql = "select idx, swim, swimrank from finalresult where idx=? OR idx=? OR idx=? OR idx=? OR idx=? OR idx=? OR idx=? OR idx=? OR idx=? OR idx=? order by swim asc";
			pstmt = con.prepareStatement(sql);
			for (int i = 0; i < 10; i++) {
				pstmt.setString(i+1, playerIdx[i]+"");
			}
			rs = pstmt.executeQuery();
			for (int i = 0; i < 10; i++) {
				rs.next();
				swimRecords[i][0] = Integer.parseInt(rs.getString("idx").toString());
				swimRecords[i][1] = Integer.parseInt(rs.getString("swim").toString());
				swimRecords[i][2] = Integer.parseInt(rs.getString("swimrank").toString());
			}
			pstmt.close();
			rs.close();
			recBean.setSwimRecords(swimRecords);
						
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
		return recBean;
	}

}
