package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dbc.DBConnectionMgr;

public class MainMgr {
		
	private DBConnectionMgr pool;
	
	public MainMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public MainBean bringAll(MainBean bean, int reVisit) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String tableName = bean.getName() + "climate";
		
		int specie[] = new int[3];
		int status[] = new int[3];
		int passedTurn[] = new int[3];
		int harvestTurn[] = new int[3];
		int tempResult[] = new int[3];
		int waterResult[] = new int[3];
		int optimalTemp[] = new int[3];
		int fertilized[] = new int[3];
		int waterContent[] = new int[3];
		int waterReduction[] = new int[3];
		int dieTemp[] = new int[3];
		int dieWater[] = new int[3];
		
		try {
			con = pool.getConnection();
			sql = "select * from player where name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getName());
			rs = pstmt.executeQuery();
			
			
			rs.next();
			bean.setCharStatus(Integer.parseInt(rs.getString("charStatus").toString()));
			bean.setTurn(Integer.parseInt(rs.getString("turn").toString()));
			bean.setSpeed(Integer.parseInt(rs.getString("speed").toString()));
			bean.setPower(Integer.parseInt(rs.getString("power").toString()));
			bean.setFatigue(Integer.parseInt(rs.getString("fatigue").toString()));
			bean.setLifeCount(Integer.parseInt(rs.getString("lifeCount").toString()));
			bean.setFood(Integer.parseInt(rs.getString("food").toString()));
			bean.setLog(Integer.parseInt(rs.getString("log").toString()));
			bean.setFirewood(Integer.parseInt(rs.getString("firewood").toString()));
			bean.setEvent(rs.getString("event").toString());
			bean.setHoneyPollution(Integer.parseInt(rs.getString("honeyPollution").toString()));
			bean.setFishPollution(Integer.parseInt(rs.getString("fishPollution").toString()));
			
			specie[0] = Integer.parseInt(rs.getString("specie1").toString()); 
			status[0] = Integer.parseInt(rs.getString("status1").toString()); 
			passedTurn[0] = Integer.parseInt(rs.getString("passedTurn1").toString()); 
			harvestTurn[0] = Integer.parseInt(rs.getString("harvestTurn1").toString());
			tempResult[0] = Integer.parseInt(rs.getString("tempResult1").toString()); 
			waterResult[0] = Integer.parseInt(rs.getString("waterResult1").toString()); 
			optimalTemp[0] = Integer.parseInt(rs.getString("optimalTemp1").toString()); 
			fertilized[0] = Integer.parseInt(rs.getString("fertilized1").toString());
			waterContent[0] = Integer.parseInt(rs.getString("waterContent1").toString());
			waterReduction[0] = Integer.parseInt(rs.getString("waterReduction1").toString()); 
			dieTemp[0] = Integer.parseInt(rs.getString("dieTemp1").toString()); 
			dieWater[0] = Integer.parseInt(rs.getString("dieWater1").toString());
			
			specie[1] = Integer.parseInt(rs.getString("specie2").toString()); 
			status[1] = Integer.parseInt(rs.getString("status2").toString()); 
			passedTurn[1] = Integer.parseInt(rs.getString("passedTurn2").toString()); 
			harvestTurn[1] = Integer.parseInt(rs.getString("harvestTurn2").toString());
			tempResult[1] = Integer.parseInt(rs.getString("tempResult2").toString()); 
			waterResult[1] = Integer.parseInt(rs.getString("waterResult2").toString());
			optimalTemp[1] = Integer.parseInt(rs.getString("optimalTemp2").toString());
			fertilized[1] = Integer.parseInt(rs.getString("fertilized2").toString());
			waterContent[1] = Integer.parseInt(rs.getString("waterContent2").toString());
			waterReduction[1] = Integer.parseInt(rs.getString("waterReduction2").toString()); 
			dieTemp[1] = Integer.parseInt(rs.getString("dieTemp2").toString()); 
			dieWater[1] = Integer.parseInt(rs.getString("dieWater2").toString());
			
			specie[2] = Integer.parseInt(rs.getString("specie3").toString()); 
			status[2] = Integer.parseInt(rs.getString("status3").toString()); 
			passedTurn[2] = Integer.parseInt(rs.getString("passedTurn3").toString()); 
			harvestTurn[2] = Integer.parseInt(rs.getString("harvestTurn3").toString());
			tempResult[2] = Integer.parseInt(rs.getString("tempResult3").toString()); 
			waterResult[2] = Integer.parseInt(rs.getString("waterResult3").toString()); 
			optimalTemp[2] = Integer.parseInt(rs.getString("optimalTemp3").toString()); 
			fertilized[2] = Integer.parseInt(rs.getString("fertilized3").toString());
			waterContent[2] = Integer.parseInt(rs.getString("waterContent3").toString());
			waterReduction[2] = Integer.parseInt(rs.getString("waterReduction3").toString()); 
			dieTemp[2] = Integer.parseInt(rs.getString("dieTemp3").toString()); 
			dieWater[2] = Integer.parseInt(rs.getString("dieWater3").toString());
			
			bean.setSpecie(specie);
			bean.setStatus(status);
			bean.setPassedTurn(passedTurn);
			bean.setHarvestTurn(harvestTurn);
			bean.setTempResult(tempResult);
			bean.setWaterContent(waterContent);
			bean.setWaterResult(waterResult);
			bean.setOptimalTemp(optimalTemp);
			bean.setFertilized(fertilized);
			bean.setWaterReduction(waterReduction);
			bean.setDieTemp(dieTemp);
			bean.setDieWater(dieWater);
			pstmt.close();
			rs.close();
			
			sql = "select * from "+tableName+" where turn=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getTurn()+reVisit+"");
			rs = pstmt.executeQuery();
			
			
			rs.next();
			bean.setTemperature(Integer.parseInt(rs.getString("temp").toString()));
			bean.setIsRain(Integer.parseInt(rs.getString("rain").toString()));
			bean.setIsWinter(Integer.parseInt(rs.getString("snow").toString()));
			bean.setDispRain(Integer.parseInt(rs.getString("disp").toString()));
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return bean;
	}
	
	public void SaveAll(MainBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;

		int specie[];
		int status[];
		int passedTurn[];
		int harvestTurn[];
		int tempResult[];
		int waterResult[];
		int optimalTemp[];
		int fertilized[];
		int waterContent[];
		int waterReduction[];
		int dieTemp[];
		int dieWater[];
		
		specie = bean.getSpecie();
		status = bean.getStatus();
		passedTurn = bean.getPassedTurn();
		harvestTurn = bean.getHarvestTurn();
		tempResult = bean.getTempResult();
		waterResult = bean.getWaterResult();
		optimalTemp = bean.getOptimalTemp();
		fertilized = bean.getFertilized();
		waterContent = bean.getWaterContent();
		waterReduction = bean.getWaterReduction();
		dieTemp = bean.getDieTemp();
		dieWater = bean.getDieWater();
		
		try {
			con = pool.getConnection();
			sql = "update player set charStatus=?, turn=?, speed=?, power=?, fatigue=?, lifeCount=?, food=?, log=?, firewood=?, event=?, honeyPollution=?, fishPollution=?, specie1=?, status1=?, passedTurn1=?, harvestTurn1=?, tempResult1=?, waterResult1=?, optimalTemp1=?, fertilized1=?, waterContent1=?, waterReduction1=?, dieTemp1=?, dieWater1=?, specie2=?, status2=?, passedTurn2=?, harvestTurn2=?, tempResult2=?, waterResult2=?, optimalTemp2=?, fertilized2=?, waterContent2=?, waterReduction2=?, dieTemp2=?, dieWater2=?, specie3=?, status3=?, passedTurn3=?, harvestTurn3=?, tempResult3=?, waterResult3=?, optimalTemp3=?, fertilized3=?, waterContent3=?, waterReduction3=?, dieTemp3=?, dieWater3=? where name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getCharStatus()+"");
			pstmt.setString(2, bean.getTurn()+"");
			pstmt.setString(3, bean.getSpeed()+"");
			pstmt.setString(4, bean.getPower()+"");
			pstmt.setString(5, bean.getFatigue()+"");
			pstmt.setString(6, bean.getLifeCount()+"");
			pstmt.setString(7, bean.getFood()+"");
			pstmt.setString(8, bean.getLog()+"");
			pstmt.setString(9, bean.getFirewood()+"");
			pstmt.setString(10, bean.getEvent());
			pstmt.setString(11, bean.getHoneyPollution()+"");
			pstmt.setString(12, bean.getFishPollution()+"");
			
			// 5번부터 37번까지 각각 밭3개 배열의 값을 채우기 위함. 항목이 는다면 면밀하게 살펴볼 것
			for(int i=0; i<3; i++) {
			pstmt.setString(i*12+13, specie[i]+"");
			pstmt.setString(i*12+14, status[i]+"");
			pstmt.setString(i*12+15, passedTurn[i]+"");
			pstmt.setString(i*12+16, harvestTurn[i]+"");
			pstmt.setString(i*12+17, tempResult[i]+"");
			pstmt.setString(i*12+18, waterResult[i]+"");
			pstmt.setString(i*12+19, optimalTemp[i]+"");
			pstmt.setString(i*12+20, fertilized[i]+"");
			pstmt.setString(i*12+21, waterContent[i]+"");
			pstmt.setString(i*12+22, waterReduction[i]+"");
			pstmt.setString(i*12+23, dieTemp[i]+"");
			pstmt.setString(i*12+24, dieWater[i]+"");
			}
			
			pstmt.setString(49, bean.getName());
			
			
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
	}
	
	
	
	public void FinalResult(MainBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;

		
		
		try {
			con = pool.getConnection();
			sql = "";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getCharStatus()+"");
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
	}
}
