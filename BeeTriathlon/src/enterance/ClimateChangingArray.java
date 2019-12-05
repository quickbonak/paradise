package enterance;

import java.util.Random;

public class ClimateChangingArray {

	int rainfallFrequency, baseTemp, minTemp, maxTemp, nightTemp;
	int dayTemp = 999; // ù ��° ��� ������ if���� �̿��Ͽ� ���ؿµ��� �� �µ��� ����� ����.
	int arrTemp[] = new int[102];
	int arrRain[] = new int[102];
	int arrSnow[] = new int[102];
	int dispRainTemp;
	int dispRain[] = new int[102];
	int isRain = 0;
	int isSnow = 0;
	int snowCount = 0;

	///////// ���⼭���ʹ� ��¿� �����ϴ� �ż����.
	// ���ο� ����� ����, 10���� ������� ����þ� ������ �̿��Ͽ� ���� ����� ����
	// ������ �ִ�, ��������� +-15���� ����
	// ù �����ÿ��� dayTemp�� baseTemp���� �ְ�, ���Ŀ��� �ִ�, ���� �µ��� ��ȭ.
	// �� ����� ��ȯ.
	public int newClimate() {

		Random baseTempRand = new Random();
		baseTemp = (int) (baseTempRand.nextGaussian() * 5 + 10);
		minTemp = baseTemp - 15;
		maxTemp = baseTemp + 15;

		if (dayTemp == 999) {
			dayTemp = baseTemp;
		}

		return dayTemp;
	}

	// �߰� ����� ����, �� ��� ���� ��� 10�� ���� ����þ� ������ �̿��Ͽ� �϶����� ����.
	// �� ����� ��ȯ.
	public int nightHasCome() {
		Random tempNightRand = new Random();
		nightTemp = dayTemp + (int) (tempNightRand.nextGaussian() * 2 - 10);

		return nightTemp;
	}

	// ���� �ٲ� ���� ��� ��ȭ. ���� �� ����� �������� �Ϲ� �������� -5~+5 ��ŭ ��ȭ.
	// ���� ����, �ִ����� ��� ���� ������ ��� ����� ����, �ִ������� ����
	// ������ �ٲ�� ����, �ִ��¿��� 5���̻� �־��� ��� 5���� ����ġ�� ���� �µ����� ������ ������ ����.
	// �� ����� ��ȯ.
	public int MorningHasCome() {
		Random dayTempRand = new Random();
		dayTemp = dayTemp + dayTempRand.nextInt(10) - 5;

		if ((dayTemp - minTemp) < 5) {
			dayTemp += 5;
		} else if (dayTemp < minTemp) {
			dayTemp = minTemp;
		} else if (dayTemp - maxTemp > 5) {
			dayTemp -= 5;
		} else if (dayTemp > maxTemp) {
			dayTemp = maxTemp;
		}

		return dayTemp;
	}

	///////// ������ʹ� �������� �����ϴ� �ż����.
	// ����Ȯ���� ����, 20%�� ������� ����þ� ���� �̿��Ͽ� ������Ŵ.
	// ���� ������ ����Ȯ���� 2% ������ ��� 2%�� ����.
	// �������� ��ȯ
	public int newRainfall() {

		Random rfRand = new Random();
		rainfallFrequency = (int) (rfRand.nextGaussian() * 7 + 20);

		if (rainfallFrequency < 2) {
			rainfallFrequency = 2;
		}

		return rainfallFrequency;
	}

	// ������ ��ȭ�� ���� �������̽��� ǥ���ϰ� �� �۱͸� ����.
	// ������ ������ �۱͸� ��ȯ
	public int displayRainfall() {
		int displayRf;

		if (rainfallFrequency < 5) {
			displayRf = 0;
		} else if (rainfallFrequency < 15) {
			displayRf = 1;
		} else if (rainfallFrequency < 25) {
			displayRf = 2;
		} else if (rainfallFrequency < 35) {
			displayRf = 3;
		} else {
			displayRf = 4;
		}

		return displayRf;
	}

	// �� �������� ����. ������ ������ ����Ȯ���� ������ ������ �ְ�,
	// ���� ���ʿ��� �� ���� �־��� ��� ����Ȯ���� 10 ������Ų��.
	// �� ���� �ִ����� �����ϴ� �Ҹ� ���� ��ȯ.
	public int isRainfall() {
		int modifiedFrequency = rainfallFrequency;
		if (isRain == 1) {
			modifiedFrequency += 10;
		}

		isRain = 0;
		Random rainfallBorder = new Random();
		if (modifiedFrequency >= rainfallBorder.nextInt(100)) {
			isRain = 1;
		}

		return isRain;
	}

	// �⺻ �����ڷ� ȣ��Ǵ� ��� arrTemp[], arrRain[] �迭�鿡
	// �� ������ ��,�� ��°� ���쿩�θ� ����Ͽ� db�� �ѱ��.
	public ClimateChangingArray(String charName) {

		ClimateMgr mgr = new ClimateMgr();

		// �µ� ����. �迭102�� ����, 34������ ���� ��ȭ.
		for (int i = 0; i < 102; i += 34) {
			arrTemp[i] = this.newClimate();
			arrTemp[i + 1] = this.nightHasCome();
			for (int j = i + 2; j < i + 34; j += 2) {
				arrTemp[j] = this.MorningHasCome();
				arrTemp[j + 1] = this.nightHasCome();
			}
		}

		// ���� ����. �迭 300�� ����, 100������ ���� ��ȭ.
		for (int i = 0; i < 102; i += 34) {
			this.newRainfall();
			dispRainTemp = this.displayRainfall();
			for (int j = i; j < i + 34; j++) {
				arrRain[j] = this.isRainfall();
				dispRain[j] = dispRainTemp;
			}
		}

		for (int i = 0; i < 102; i++) {
			// ���� ���� ���� ���������� �˾ƾ� ���ϸ� ������ ������ ������ �� �� �ִ�.
			if (isSnow == 0) {
				if (arrTemp[i] < 0) {
					snowCount++;
					if (snowCount == 3) {
						isSnow = 1;
						snowCount = 0;
					}
				} else {
					snowCount = 0;
				}
			} else if (isSnow == 1) {
				if (arrTemp[i] > 0) {
					snowCount++;
					if (snowCount == 3) {
						isSnow = 0;
						snowCount = 0;
					}
				} else {
					snowCount = 0;
				}
			}
			arrSnow[i] = isSnow;
		}

		mgr.ClimateInsert(arrTemp, arrRain, arrSnow, dispRain, charName);

	}
}
