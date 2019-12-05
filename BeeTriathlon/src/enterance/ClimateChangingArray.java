package enterance;

import java.util.Random;

public class ClimateChangingArray {

	int rainfallFrequency, baseTemp, minTemp, maxTemp, nightTemp;
	int dayTemp = 999; // 첫 번째 기온 생성시 if문을 이용하여 기준온도를 낮 온도로 만들기 위함.
	int arrTemp[] = new int[102];
	int arrRain[] = new int[102];
	int arrSnow[] = new int[102];
	int dispRainTemp;
	int dispRain[] = new int[102];
	int isRain = 0;
	int isSnow = 0;
	int snowCount = 0;

	///////// 여기서부터는 기온에 관여하는 매서드들.
	// 새로운 기온을 생성, 10도를 평균으로 가우시안 분포를 이용하여 기준 기온을 생성
	// 계절의 최대, 최저기온은 +-15도로 설정
	// 첫 생성시에는 dayTemp에 baseTemp값을 넣고, 이후에는 최대, 최저 온도만 변화.
	// 낮 기온을 반환.
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

	// 야간 기온을 생성, 낮 기온 기준 평균 10도 전후 가우시안 분포를 이용하여 하락값을 정함.
	// 밤 기온을 반환.
	public int nightHasCome() {
		Random tempNightRand = new Random();
		nightTemp = dayTemp + (int) (tempNightRand.nextGaussian() * 2 - 10);

		return nightTemp;
	}

	// 날이 바뀔 때의 기온 변화. 전날 낮 기온을 기준으로 일반 랜덤범위 -5~+5 만큼 변화.
	// 만약 최저, 최대기온을 벗어난 값을 마주할 경우 기온은 최저, 최대기온으로 고정
	// 계절이 바뀌어 최저, 최대기온에서 5도이상 멀어질 경우 5도의 보정치를 통해 온도범위 안으로 점진적 접근.
	// 낮 기온을 반환.
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

	///////// 여기부터는 강우율에 관여하는 매서드들.
	// 강우확률을 생성, 20%를 평균으로 가우시안 분포 이용하여 산포시킴.
	// 만약 생성된 강우확률이 2% 이하일 경우 2%로 고정.
	// 강우율을 반환
	public int newRainfall() {

		Random rfRand = new Random();
		rainfallFrequency = (int) (rfRand.nextGaussian() * 7 + 20);

		if (rainfallFrequency < 2) {
			rainfallFrequency = 2;
		}

		return rainfallFrequency;
	}

	// 강우율 변화에 따라서 인터페이스에 표시하게 될 글귀를 결정.
	// 결정된 강우율 글귀를 반환
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

	// 비가 오는지를 결정. 위에서 정해진 강우확률을 임의의 변수에 넣고,
	// 이전 차례에서 비가 오고 있었을 경우 강우확률을 10 증가시킨다.
	// 비가 오고 있는지를 결정하는 불린 값을 반환.
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

	// 기본 생성자로 호출되는 즉시 arrTemp[], arrRain[] 배열들에
	// 각 날들의 밤,낮 기온과 강우여부를 기록하여 db에 넘긴다.
	public ClimateChangingArray(String charName) {

		ClimateMgr mgr = new ClimateMgr();

		// 온도 생성. 배열102개 기준, 34개마다 계절 변화.
		for (int i = 0; i < 102; i += 34) {
			arrTemp[i] = this.newClimate();
			arrTemp[i + 1] = this.nightHasCome();
			for (int j = i + 2; j < i + 34; j += 2) {
				arrTemp[j] = this.MorningHasCome();
				arrTemp[j + 1] = this.nightHasCome();
			}
		}

		// 강우 생성. 배열 300개 기준, 100개마다 계절 변화.
		for (int i = 0; i < 102; i += 34) {
			this.newRainfall();
			dispRainTemp = this.displayRainfall();
			for (int j = i; j < i + 34; j++) {
				arrRain[j] = this.isRainfall();
				dispRain[j] = dispRainTemp;
			}
		}

		for (int i = 0; i < 102; i++) {
			// 먼저 눈이 내린 상태인지를 알아야 영하를 비교할지 영상을 비교할지 알 수 있다.
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
