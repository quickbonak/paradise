package main;

import java.util.Random;

public class FinalProcess {
	Random random = new Random();

	public ResultBean makeRecord(ResultBean rBean, int hikePoten, int chinupPoten, int swimPoten) {

		// 종목잠재력순위, 최고기록, 최저기록을 넘겨 기록을 낸다.
		rBean.setHike(this.aRecord(hikePoten, 144000, 180000));
		rBean.setChinup(this.aRecord(chinupPoten, 450, 250));
		rBean.setSwim(this.aRecord(swimPoten, 126000, 162000));

		return rBean;
	}

	public int aRecord(int potential, int bestRecord, int worstRecord) {

		int lastRecord = bestRecord;

		if (bestRecord < worstRecord) {
			int initialGap = random.nextInt(worstRecord - bestRecord);
			lastRecord = bestRecord + initialGap - random.nextInt(initialGap) * (10 - potential + 1) / 10;
		} else {
			int initialGap = random.nextInt(bestRecord - worstRecord);
			lastRecord = bestRecord - initialGap + random.nextInt(initialGap) * (10 - potential + 1) / 10;
		}

		return lastRecord;
	}

	public int totPointCalculate(int hikeRank, int chinupRank, int swimRank) {
		int totPoint = this.aPoint(hikeRank) + this.aPoint(chinupRank) + this.aPoint(swimRank);
		return totPoint;
	}

	public int aPoint(int aRank) {
		int aPoint = 0;

		switch (aRank) {
		case 1:
			aPoint = 25;
			break;
		case 2:
			aPoint = 18;
			break;
		case 3:
			aPoint = 15;
			break;
		case 4:
			aPoint = 12;
			break;
		case 5:
			aPoint = 10;
			break;
		case 6:
			aPoint = 8;
			break;
		case 7:
			aPoint = 6;
			break;
		case 8:
			aPoint = 4;
			break;
		case 9:
			aPoint = 2;
			break;
		case 10:
			aPoint = 1;
			break;
		}

		return aPoint;
	}

}
