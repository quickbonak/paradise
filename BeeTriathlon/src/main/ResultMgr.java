package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import dbc.DBConnectionMgr;

public class ResultMgr {
	
	private DBConnectionMgr pool;
	
	public ResultMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	
	//ĳ���� �̸�, ���ǵ�, �Ŀ��� ��� ���̺� ����Ѵ�.
	public int onYourMarks(MainBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Random random = new Random();
		int triFinished = 0;

		try {
			con = pool.getConnection();
			sql = "insert finalresult(name, speed, power) values(?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getSpeed()+"");
			pstmt.setString(3, bean.getPower()+"");
			pstmt.executeUpdate();
			pstmt.close();
			
			ResultBean rBean = new ResultBean();
			rBean.setName(bean.getName());
			rBean.setSpeed(bean.getSpeed());
			rBean.setPower(bean.getPower());
			
			//idx�� ���ڸ� ��ȸ�Ͽ� ���� 10���� �����Ǵ��� Ȯ��, ��� ������ �ҷ����� �������� ���Ѵ�.
			sql = "select idx from finalresult";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.last();
			int numOfPlayers = rs.getRow();
			pstmt.close();
			rs.close();
			if(numOfPlayers<10) {
				//10���� �ȵ� ��� ������
			}else if(numOfPlayers==10) {
				//���� 10���� �� �ҷ��ͼ� ��⸦ �ѹ��� ������, ��� ����� ��Ͻ�Ű�� ���� �ؾ��Ѵ�.
				ResultBean[] players = new ResultBean[10];
				for (int i = 0; i < 10; i++) {
					players[i]= new ResultBean();
				}
				int[] tempIdx = new int[10];
				int[] hikePoten = new int[10];
				int[] chinupPoten = new int[10];
				int[] swimPoten = new int[10];

				sql = "select idx, name, speed, power from finalresult order by idx asc";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				for (int i = 0; i < 10; i++) {
					rs.next();
					tempIdx[i]=Integer.parseInt(rs.getString("idx").toString());
					players[i].setName(rs.getString("name").toString());
					players[i].setSpeed(Integer.parseInt(rs.getString("speed").toString()));
					players[i].setPower(Integer.parseInt(rs.getString("power").toString()));
				}
				pstmt.close();
				rs.close();

				FinalProcess fProcess = new FinalProcess();
		
				for (int i = 0; i < players.length; i++) {
					hikePoten[i] = 10;
					chinupPoten[i] = 10;
					swimPoten[i] = 10;
					for (int j = 0; j < players.length; j++) {
						if(players[i].getSpeed()>players[j].getSpeed())
							hikePoten[i]--;
						if(players[i].getPower()>players[j].getPower())
							hikePoten[i]--;
						if(players[i].getSpeed()+players[i].getPower()>players[j].getSpeed()+players[j].getPower())
							hikePoten[i]--;
					}
					players[i]=fProcess.makeRecord(players[i], hikePoten[i], chinupPoten[i], swimPoten[i]);
					
				}
				
				for (int i = 0; i < 10; i++) {
					players[i].setHikerank(10);
					players[i].setChinuprank(10);
					players[i].setSwimrank(10);
					for (int j = 0; j < 10; j++) {
						if(players[i].getHike()<players[j].getHike())
							players[i].setHikerank(players[i].getHikerank()-1);
						//�ΰ��̴� ���� ���� ���� ���� ��
						if(players[i].getChinup()>players[j].getChinup())
							players[i].setChinuprank(players[i].getChinuprank()-1);
						if(players[i].getSwim()<players[j].getSwim())
							players[i].setSwimrank(players[i].getSwimrank()-1);
					}
					
					players[i].setTotalpoints(fProcess.totPointCalculate(players[i].getHikerank(), players[i].getChinuprank(), players[i].getSwimrank()));
				}
				
				for (int i = 0; i < 10; i++) {

					players[i].setFinalrank(10);
					int[] playerIdx = new int[9];
					
					for (int j = 0; j < 10; j++) {
						if(players[i].getTotalpoints()>players[j].getTotalpoints())
							players[i].setFinalrank(players[i].getFinalrank()-1);
						if(i>j) {
							playerIdx[j]=tempIdx[j];
						}else if(i<j){
							playerIdx[j-1]=tempIdx[j];
						}
					}
					players[i].setPlayeridx(playerIdx);
					this.savePlayer(players[i]);
				}
				triFinished = 1;
				
			}else if(numOfPlayers>10) {
				// ������ ���� 9���� �ҷ��ͼ� �ش� ������ idx�� bean�� ���
				int[] playerIdx = new int[9];
				int[] tempThPlayer = new int[9];
				int tempNum;
				
				sql = "select idx from finalresult order by idx asc";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				for (int i = 0; i < 9; i++) {
					tempNum = random.nextInt(numOfPlayers-i-1)+1;
					int dupleCount = 0;
					int lastCount = 0;
					boolean goLoop = true;
					
					while(goLoop) {
						for (int j = 0; j < i; j++) {
							if(tempNum+lastCount>=tempThPlayer[j]) {
								dupleCount++;
							}
						}
						if(lastCount==dupleCount)goLoop=false;
						lastCount=dupleCount;
						dupleCount=0;
					}
					
					tempThPlayer[i]=tempNum+lastCount;
					rs.absolute(tempThPlayer[i]);
					playerIdx[i]=Integer.parseInt(rs.getString("idx").toString());
				}
				rBean.setPlayeridx(playerIdx);
				
				pstmt.close();
				rs.close();
				
				//���ߵ� �������� ����� �迭�� �Է�, ���� ���ǵ��� �񱳷�ũ ����
				int hikePoten = 10;
				int chinupPoten = 10;
				int swimPoten = 10;
				int[] hikeRecord = new int[9];
				int[] chinupRecord = new int[9];
				int[] swimRecord = new int[9];
				int[] totalPoints = new int[9];
				for (int i = 0; i < 9; i++) {
					sql = "select * from finalresult where idx=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, playerIdx[i]+"");
					rs = pstmt.executeQuery();
					rs.next();
					if(rBean.getSpeed()>(Integer.parseInt(rs.getString("speed").toString())))
						hikePoten--;
					if(rBean.getPower()>(Integer.parseInt(rs.getString("power").toString())))
						chinupPoten--;
					if(rBean.getSpeed()+rBean.getPower()>Integer.parseInt(rs.getString("speed").toString())+Integer.parseInt(rs.getString("power").toString()))
						swimPoten--;
					hikeRecord[i] = Integer.parseInt(rs.getString("hike").toString());
					chinupRecord[i] = Integer.parseInt(rs.getString("chinup").toString());
					swimRecord[i] = Integer.parseInt(rs.getString("swim").toString());
					totalPoints[i] = Integer.parseInt(rs.getString("totalpoints").toString());
					pstmt.close();
					rs.close();
				}
				// �񱳷�ũ �������� �� ���� ��ϻ���
				FinalProcess fProcess = new FinalProcess();
				rBean = fProcess.makeRecord(rBean, hikePoten, chinupPoten, swimPoten);
				
				//  ��ϰ� ������ �����Ͽ� �޴� ���� ��, �� ������ �������� �����ϰ�, �������
				rBean.setHikerank(10);
				rBean.setChinuprank(10);
				rBean.setSwimrank(10);
				for (int i = 0; i < playerIdx.length; i++) {
					if(rBean.getHike()<hikeRecord[i])
						rBean.setHikerank(rBean.getHikerank()-1);
					//�ΰ��̴� ���� ���� ���� ���� ��
					if(rBean.getChinup()>chinupRecord[i])
						rBean.setChinuprank(rBean.getChinuprank()-1);
					if(rBean.getSwim()<swimRecord[i])
						rBean.setSwimrank(rBean.getSwimrank()-1);
				}
				
				rBean.setTotalpoints(fProcess.totPointCalculate(rBean.getHikerank(), rBean.getChinuprank(), rBean.getSwimrank()));
				
				rBean.setFinalrank(10);
				for (int i = 0; i < playerIdx.length; i++) {
					if(rBean.getTotalpoints()>totalPoints[i])
						rBean.setFinalrank(rBean.getFinalrank()-1);
				}
				//����� db�� ���
				this.savePlayer(rBean);
				triFinished = 1;
				// ��� ���������� ������ ����� �߽����� ������ ��ϰ� �̸��� UI�� ǥ���Ѵ�.
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
		return triFinished;
	}
	
	//�ڽ��� ���� ���� 10���� �����Ͽ� �ҷ��´�.
	
	public void savePlayer(ResultBean rBean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int[] tempPlayerIdx = new int[9];
		tempPlayerIdx = rBean.getPlayeridx();
		
		try {
			con = pool.getConnection();
			sql = "update finalresult set hike=?, hikerank=?, chinup=?, chinuprank=?, swim=?, swimrank=?, totalpoints=?, finalrank=?, player1idx=?, player2idx=?, player3idx=?, player4idx=?, player5idx=?, player6idx=?, player7idx=?, player8idx=?, player9idx=? where name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, rBean.getHike()+"");
			pstmt.setString(2, rBean.getHikerank()+"");
			pstmt.setString(3, rBean.getChinup()+"");
			pstmt.setString(4, rBean.getChinuprank()+"");
			pstmt.setString(5, rBean.getSwim()+"");
			pstmt.setString(6, rBean.getSwimrank()+"");
			pstmt.setString(7, rBean.getTotalpoints()+"");
			pstmt.setString(8, rBean.getFinalrank()+"");

			for(int i=0; i<9; i++) {
				pstmt.setString(i+9, tempPlayerIdx[i]+"");
			}
			pstmt.setString(18, rBean.getName());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
	}
}
