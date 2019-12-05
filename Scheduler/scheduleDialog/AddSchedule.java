package scheduleDialog;

import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.dayOfWeekInMonth;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import connectDB.AlarmMgr;
import connectDB.CalendarInitMgr;
import connectDB.ScheduleBean;
import connectDB.ScheduleMgr;
import layout.MainScreen;

public class AddSchedule extends JDialog implements ActionListener {
	String titleText;
	int asAlarmSpinset = 1;
	
	public static DateTimeFormatter dTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public String getTitleText() {
		return titleText;
	}
	int mode;
	Panel asConditionPanel = new Panel();
	JTextArea asTA = new JTextArea();
	JLabel[] asLabel = new JLabel[4];
	GridBagConstraints asGbc = new GridBagConstraints(); // 큰 그리드백
	GridBagConstraints asCon = new GridBagConstraints(); // 작은그리드백
	private JTextField inputTitle, inputLocation;
	String[] asLabelTx = { "제  목 :", "위  치 :", "시작 시각 :", "종료 시각 :" };
	String[] asAlarmStepList = { "시간 전", "분 전", "일 전" };
	JComboBox<String> asAlarmStep = new JComboBox(asAlarmStepList);
	JButton asApproval, asCancel, asSearch;
	JSpinner asStart, asEnd, asAlarmSpin;
	static SpinnerDateModel asStartModel; // RoutineSelector에서 사용하므로, static지정
	SpinnerDateModel asEndModel;
	SpinnerNumberModel asAlarmModel;
	JCheckBox asSetAlarm, asAllday, asRoutine;
	JCheckBox asAlarmMinute, asAlarmClock, asAlarmDay;
	Date asStartDate, asStartDate2, asEndDate, asEndDate2, asStartSpin, asEndSpin;

	SimpleDateFormat asFormatD = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat asFormatT = new SimpleDateFormat("hhmm");
	SimpleDateFormat addForm = new SimpleDateFormat("HHmm00"); // db에 시간을 저장하기 위한 포맷
	SimpleDateFormat asFormatS = new SimpleDateFormat("yyyyMMddHHmm");
	SimpleDateFormat asFormatMM = new SimpleDateFormat("MM"); // main으로 보내서 calBtn I,J값 구하기위함
	SimpleDateFormat asFormatdd = new SimpleDateFormat("dd");
	SimpleDateFormat asFormatYYYY = new SimpleDateFormat("YYYY"); // main으로 보내서 calBtn I,J값 구하기위함
	DateTimeFormatter asFormatL = DateTimeFormatter.ofPattern("yyyyMMdd");
	DateTimeFormatter asFormatW = DateTimeFormatter.ofPattern("e");// 요일(숫자 1~7)
	DateTimeFormatter asFormatTh = DateTimeFormatter.ofPattern("W");// 달의 몇번째 주

	MainScreen main;
	LocalDate startDate; // 반복연산에 사용할 date변수
	LocalDate endDate;
	String alarmView;
	String addDate1, addDate2; // 레코드에 넣을 날짜,시간값
	AlarmMgr alarmMgr;
	ScheduleBean deleteBean;

	long code = System.currentTimeMillis(); // 반복레코드의 코드값은 같게.
	int recordCount = 0; // 반복레코드의 갯수를 검사하는 변수.
	int alarmReturn;
	// RoutineSelector클래스에서 사용하기 위해 static지정
	static int selectStep = 0; // 반복설정 타입.
	static int dat = 0; // 반복설정에 필요한 데이터를 받는 변수. (중요)
	static int repeatDat = 0; // 반복횟수
	static Boolean[] week = new Boolean[7]; // 반복해야 할 요일
	static int untilDat = 20410101; // 최대 2041년까지. 반복할 시간

	public static final int ADD = 1;
	public static final int EDIT = 2;

	public AddSchedule(MainScreen main, int MODE, ScheduleBean editSchedule) {
		this.main = main;
		deleteBean = editSchedule;
		mode = MODE;
		asStartSpin = new Date();
		try {// 일정 종료시간을 일정 시작시간보다 1시간 지난 시간대로 설정
			asEndSpin = asFormatS.parse(String.valueOf(Long.parseLong(asFormatS.format(asStartSpin)) + 100));
		} catch (ParseException e) {
		}
		// System.out.println(Long.parseLong(asFormatS.format(asStartSpin)));
		asStartModel = new SpinnerDateModel(asStartSpin, null, null, Calendar.DAY_OF_MONTH);
		asEndModel = new SpinnerDateModel(asEndSpin, null, null, Calendar.DAY_OF_MONTH);

		for (int i = 0; i < week.length; i++) {
			week[i] = false;
		}

		int posiX = main.getX();// mainScreen의 구글맵에 가리지 않도록 포지션을 위치시킨다.
		int posiY = main.getY();

		setBounds(posiX + 310, posiY + 55, 550, 520);

		getContentPane().setLayout(new BorderLayout());

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(buttonPane, BorderLayout.SOUTH);

		asApproval = new JButton("저장"); // 저장버튼
		buttonPane.add(asApproval);
		getRootPane().setDefaultButton(asApproval);
		asApproval.addActionListener(this);

		asCancel = new JButton("취소"); // 취소버튼
		buttonPane.add(asCancel);
		asCancel.addActionListener(this);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel asGblPanel = new JPanel();
		panel.add(asGblPanel);
		asGblPanel.setLayout(null);
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[] { 0, 0, 0 };
		gbl.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		asGblPanel.setLayout(gbl);

		asGbc.anchor = GridBagConstraints.FIRST_LINE_START; // 레이블을 왼쪽으로 정렬시킴
		asGbc.insets = new Insets(8, 8, 8, 8);
		asGbc.gridx = 0;

		for (int i = 0; i < asLabel.length; i++) {
			asLabel[i] = new JLabel(asLabelTx[i]);
			asGbc.gridy = i;
			asGblPanel.add(asLabel[i], asGbc);
		}

		// 제목 입력칸
		inputTitle = new JTextField();
		asGbc.fill = GridBagConstraints.HORIZONTAL;
		asGbc.gridx = 1;
		asGbc.gridy = 0;
		asGblPanel.add(inputTitle, asGbc);

		// 위치 입력칸
		inputLocation = new JTextField();
		asGbc.gridy = 1;
		asGblPanel.add(inputLocation, asGbc);

		// 일정 시작날짜 스피너
		asStart = new JSpinner();
		asStart.setModel(asStartModel);
		asStart.setEditor(new JSpinner.DateEditor(asStart, "yyyy년 MM월 dd일 a hh시 mm분"));
		// asStart.setEditor(new JSpinner.DateEditor(asStart, "yyyy년 MM월 dd일 HH시 mm분"));
		asGbc.gridy = 2;
		asGblPanel.add(asStart, asGbc);

		asStart.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				long comparison1 = Long.valueOf(asFormatS.format(asStart.getValue()));
				long comparison2 = Long.valueOf(asFormatS.format(asEnd.getValue()));
				if(comparison1>=comparison2) {
					comparison2=comparison1+1;
				}
				try {
					asEnd.setValue(asFormatS.parse(String.valueOf(comparison2)));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				System.out.println(1);
			}
		});
		// 일정 종료날짜 스피너
		asEnd = new JSpinner();
		asEnd.setModel(asEndModel);
		// 일정 시작날짜에서 1시간을 더한다.
		asEnd.setEditor(new JSpinner.DateEditor(asEnd, "yyyy년 MM월 dd일 a hh시 mm분"));
		// asEnd.setEditor(new JSpinner.DateEditor(asEnd, "yyyy년 MM월 dd일 HH시 mm분"));
		asEnd.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				long comparison1 = Long.valueOf(asFormatS.format(asStart.getValue()));
				long comparison2 = Long.valueOf(asFormatS.format(asEnd.getValue()));
				if(comparison1>=comparison2) {
					comparison2=comparison1+1;
				}
				try {
					asEnd.setValue(asFormatS.parse(String.valueOf(comparison2)));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				System.out.println(1);
			}
		});
		
		asGbc.gridy = 3;
		asGblPanel.add(asEnd, asGbc);

		// 캘린더를 고르는 콤보박스. 값은 x번째를 반환함.

		asSetAlarm = new JCheckBox("알람");
		asGbc.gridx = 2;
		asGbc.gridy = 0;
		asGblPanel.add(asSetAlarm, asGbc);
		// 구글 맵 검색
		asSearch = new JButton("검색");
		asSearch.setPreferredSize(new Dimension(20, 20));
		asGbc.gridx = 2;
		asGbc.gridy = 1;
		asGblPanel.add(asSearch, asGbc);
		// 하루종일 체크박스. 체크하면 0시부터 24시까지의 일정이 됨.
		asAllday = new JCheckBox("하루종일");
		asGbc.gridx = 2;
		asGbc.gridy = 2;
		asGblPanel.add(asAllday, asGbc);

		// 반복일정 체크박스
		asRoutine = new JCheckBox("반복일정");
		asGbc.gridy = 3;
		asGblPanel.add(asRoutine, asGbc);

		// 일정에 대한 설명을 넣는 텍스트 에리어
		panel.add(asTA);
		asTA.setBorder(new LineBorder(Color.GRAY, 2));
		asTA.setLineWrap(true);

		// 그리드 백 레이아웃 설정
		GridBagLayout asConditionPanelLay = new GridBagLayout();
		asConditionPanel.setLayout(new GridBagLayout());
		asGbc.gridx = 3;
		asGbc.gridy = 0;
		asGbc.gridheight = 5;
		asGblPanel.add(asConditionPanel, asGbc);

		asConditionPanelLay.columnWidths = new int[] { 1, 0, 0, 0 };
		asConditionPanelLay.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		asConditionPanelLay.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		asConditionPanelLay.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };

		asAlarmSpin = new JSpinner();
		asAlarmSpin.setModel(asAlarmModel = new SpinnerNumberModel(1, 1, null, 1));
		asCon.fill = GridBagConstraints.HORIZONTAL;
		asCon.gridx = 0;
		asCon.gridy = 0;
		asConditionPanel.add(asAlarmSpin, asCon);

		asAlarmStep.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(asAlarmStep.getSelectedIndex());
				if(asAlarmStep.getSelectedIndex()==1) {
					asAlarmSpinset=10;
				}else {
					asAlarmSpinset=1;
				}
				asAlarmSpin.setModel(asAlarmModel = new SpinnerNumberModel(asAlarmSpinset, asAlarmSpinset, null, asAlarmSpinset));
				
			}
		});
		
		asCon.gridx = 1;
		asConditionPanel.add(asAlarmStep, asCon);

		asAllday.addActionListener(this);
		asRoutine.addActionListener(this);
		asSetAlarm.addActionListener(this);
		asSearch.addActionListener(this);

		asAlarmSpin.setEnabled(false); // 알람 시간스피너 비활성화

		scheduleMgr = main.getScheduleMgr();
		alarmMgr = main.getAlarmMgr();
		if (MODE == ADD ) {
			setTitle("새 일정 추가");
		} else if (MODE == EDIT && editSchedule != null) {
			setTitle("일정 편집");
			ScheduleBean bean = editSchedule;
			inputTitle.setText(bean.getTitle());
			asTA.setText(bean.getContent());

			try {
				asStartModel.setValue(CalendarInitMgr.sdf.parse(bean.getTime_from()));
				asEndModel.setValue(CalendarInitMgr.sdf.parse(bean.getTime_to()));
			} catch (ParseException e) {
			}
			inputLocation.setText(bean.getLocation());

			System.out.println("시작시간 :" + addDate1);
			System.out.println("종료시간 :" + addDate2);


		}

		setModal(true);
		setVisible(true);

	}

	// 데이터베이스 레코드에 넣을 내용들
	ScheduleMgr scheduleMgr;
	public void addToDB() {
		addDate1 = asFormatL.format(startDate) + addForm.format(asStartSpin); // 시간값(HHmm00)을 더함
		addDate2 = asFormatL.format(endDate) + addForm.format(asEndSpin);

		ScheduleBean bean = new ScheduleBean();
		bean.setTitle(inputTitle.getText());
		bean.setContent(asTA.getText());
		bean.setTime_from(addDate1);
		bean.setTime_to(addDate2);
		bean.setLocation(inputLocation.getText());
		bean.setModified_at(CalendarInitMgr.sdf.format(new Date()));
		scheduleMgr.insertSchedule(bean);

		if(asSetAlarm.isSelected()) {
			LocalDateTime ldt = LocalDateTime.parse(CalendarInitMgr.sdf.format(asStartModel.getValue()), dTF).minusMinutes(alarmReturn);	
			System.out.println("알람 시간: "+ldt);
			alarmMgr.insertAlarm(ldt.toString(), inputTitle.getText());
			System.out.println("알람 설정됨");
		}
	
		recordCount++; // 기록한 레코드의 갯수를 검사함.
	
	}
	
	public void updateToDB() {
		addDate1 = asFormatL.format(startDate) + addForm.format(asStartSpin); // 시간값(HHmm00)을 더함
		addDate2 = asFormatL.format(endDate) + addForm.format(asEndSpin);
		
		scheduleMgr.deleteSchedule(deleteBean.getId());
		
		ScheduleBean bean = new ScheduleBean();
		bean.setTitle(inputTitle.getText());
		bean.setContent(asTA.getText());
		bean.setTime_from(addDate1);
		bean.setTime_to(addDate2);
		bean.setLocation(inputLocation.getText());
		bean.setModified_at(CalendarInitMgr.sdf.format(new Date()));
		scheduleMgr.insertSchedule(bean);

		if(asSetAlarm.isSelected()) {
			LocalDateTime ldt = LocalDateTime.parse(CalendarInitMgr.sdf.format(asStartModel.getValue()), dTF).minusMinutes(alarmReturn);	
			System.out.println("알람 시간: "+ldt);
			alarmMgr.insertAlarm(ldt.toString(), inputTitle.getText());
			System.out.println("알람 설정됨");
		}
	
		recordCount++; // 기록한 레코드의 갯수를 검사함.
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(asSetAlarm)) { // 알람 버튼
			if (asSetAlarm.isSelected()) {
				asAlarmSpin.setEnabled(true); // 알람시간조절 스피너를 활성화
			} else {
				asAlarmSpin.setEnabled(false); // 비활성화
			}

		} else if (obj == asSearch) { // 구글맵 검색 버튼
			main.setMap(inputLocation.getText(), main.mapZoom);

		} else if (obj.equals(asCancel)) { // 취소버튼
			dispose();

			// 확인버튼. 제목이 빈칸이면 일정이 입력되지 않음.
		} else if (obj.equals(asApproval) && !(inputTitle.getText().isEmpty())) {

			int step = asAlarmStep.getSelectedIndex();

			// 콤보박스의 select 값으로 시,분,일에 해당하는 분을 곱한다.
			switch (asAlarmStep.getSelectedIndex()) {
			case 0:
				step = 60;
				break;
			case 1:
				step = 1;
				break;
			case 2:
				step = 1440;
				break;
			}
			// 위의 값으로 계산./10*10을 해서 1의 자리는 버린다.
			alarmReturn = Integer.parseInt(asAlarmSpin.getValue().toString()) * step / 10 * 10;
			//alarmReturn = alarmReturn < 10 ? 10 : alarmReturn; // 0일 경우, 10으로 만든다.
			alarmView = asSetAlarm.isSelected() ? "알람: " + asSetAlarm.isSelected() + " " + alarmReturn + "분 전" : "알람없음";

			// LocalDate를 이용하여 연산
			asStartSpin = asStartModel.getDate(); // 스피너에서 데이트값 추출
			asEndSpin = asEndModel.getDate();
			startDate = asStartSpin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // LocalDate형식으로 변경
			endDate = asEndSpin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			Period until = startDate.until(endDate);
			int dateTH = Integer.parseInt(asFormatTh.format(startDate).toString()); // 몇번째주
			int dateWeek = Integer.parseInt(asFormatW.format(startDate).toString()) - 1; // 몇요일
			dateWeek = dateWeek == 0 ? 7 : dateWeek; // 0일 경우, 7로 바꾼다. Date는 1-월요일이고
			DayOfWeek inweek = DayOfWeek.of(dateWeek); // DayOfWeek는 1-일요일이므로 바꿔줘야함.

			Boolean forever = repeatDat == 0 ? true : false; // 반복횟수가 0회면 횟수제한없이 반복한다.
			Boolean firstDay = true; // 첫날인지 검사하는 변수
			int finish = 0; // 반복횟수가 지정되어 있을 경우, 이 변수를 체크해서 break한다.
	
			do {// 데이터베이스에 일정을 입력하는 영역. 반드시 한번은 실행하여야 하므로, do while문 사용
				// System.out.println("do-while start");
				switch (selectStep) {
				case 0: // 1회 일정
					// System.out.println("case 0");
					if(mode ==AddSchedule.ADD)
						addToDB();
					else 
						updateToDB();
					break;
				case 1: // 매일 일정
					// System.out.println("case 1");
					startDate = startDate.plusDays(dat); // dat: 일 간격
					endDate = endDate.plusDays(dat);
					if(mode ==AddSchedule.ADD)
						addToDB();
					else 
						updateToDB();
					finish++;
					break;
				case 2: // 매주 일정
					if (firstDay) { // 첫날을 일요일로 바꾼다.
						startDate = startDate.with(previousOrSame(SUNDAY));
						endDate = startDate.plusDays(until.getDays()); // 원래 있던 startDate와의 간격만큼 연,월,일을 더해준다.
						endDate = endDate.plusMonths(until.getMonths());
						endDate = endDate.plusYears(until.getYears());
					}
					for (int i = 0; i < 7; i++) { // 1주일을 흘려보내면서, true가 된 요일에 레코드 입력
						if (week[i]) {
							if(mode ==AddSchedule.ADD)
								addToDB();
							else 
								updateToDB();
						}
						startDate = startDate.plusDays(1);
						endDate = endDate.plusDays(1);
					}
					startDate = startDate.plusWeeks(dat - 1); // for문으로 1주일치가 지났으므로, 1을 빼준다.
					endDate = endDate.plusWeeks(dat - 1);
					break;
				case 3: // 매월일정
					if(mode ==AddSchedule.ADD)
						addToDB();
					else 
						updateToDB();
					startDate = startDate.plusMonths(dat);
					endDate = endDate.plusMonths(dat);
					break;
				case 4: // 매월일정 + 몇주의 몇요일
					if(mode ==AddSchedule.ADD)
						addToDB();
					else 
						updateToDB();
					// dateTH: 몇째주, inweek: 요일
					startDate = startDate.plusMonths(dat).with(dayOfWeekInMonth(dateTH, inweek));
					endDate = startDate.plusDays(until.getDays());
					endDate = endDate.plusMonths(until.getMonths());
					endDate = endDate.plusYears(until.getYears());
					break;
				case 5: // 매년일정
					if(mode ==AddSchedule.ADD)
						addToDB();
					else 
						updateToDB();
					startDate = startDate.plusYears(dat);
					endDate = endDate.plusYears(dat);
					break;
				case 6: // 매년일정 + 몇월 몇주의 월요일
					// System.out.println("case 6");
					if(mode ==AddSchedule.ADD)
						addToDB();
					else 
						updateToDB();
					startDate = startDate.plusYears(dat).with(dayOfWeekInMonth(dateTH, inweek));
					endDate = startDate.plusDays(until.getDays());
					endDate = endDate.plusMonths(until.getMonths());
					endDate = endDate.plusYears(until.getYears());
					break;
				}

				// forever가 false일 경우, 무한반복하지 않고 정한 반복횟수만 수행한다.
				if (!(forever)) {
					if (finish >= repeatDat) {
						break;
					}
				}
				firstDay = false;
			}
			// 반복실행이면 실행 && untilDat에 지정한 날짜까지 실행(기본은 2041년1월1일)
			while (asRoutine.isSelected() && Integer.parseInt(asFormatL.format(startDate)) < untilDat);

			titleText = inputTitle.getText();
			
			
			dispose();

		} else if (obj.equals(asAllday)) { // 하루종일 체크박스. 시,분을 0000으로 만들고, 해제하면 원위치시킴.
			if (asAllday.isSelected()) { // 하루종일 체크박스 true
				asStartDate = (Date) asStart.getValue(); // 스피너의 value값을 date로 저장
				asEndDate = (Date) asEnd.getValue();
				int same = Integer.parseInt(asFormatD.format(asEndDate)); // yyyyMMdd
				int same2 = Integer.parseInt(asFormatD.format(asStartDate));

				// 하루종일 체크박스 클릭 후, 시작일과 종료일이 같다면 종료일을 +1해준다.
				if (same == same2) {
					same++;
				}
				// 시 분을 0으로 만들어서 변수에 저장.(체크된 상태에서는, 실제로 적용해야 하므로)
				try {
					asStartSpin = asFormatS.parse(same2 + "0000");
					asEndSpin = asFormatS.parse(same + "0000");
				} catch (ParseException e1) {
					dispose();
				}
				// 날짜만 입력가능하도록 스피너를 세팅함.
				asStart.setEditor(new JSpinner.DateEditor(asStart, "yyyy년 MM월 dd일"));
				asEnd.setEditor(new JSpinner.DateEditor(asEnd, "yyyy년 MM월 dd일"));
				asEnd.setValue(asEndSpin);
			} else { // 하루종일 체크박스 false
				asStartDate2 = (Date) asStart.getValue();
				asEndDate2 = (Date) asEnd.getValue();
				try {
					asStartSpin = asFormatS.parse(asFormatD.format(asStartDate2) + asFormatT.format(asStartDate));
					asEndSpin = asFormatS.parse(asFormatD.format(asEndDate2) + asFormatT.format(asEndDate));
				} catch (ParseException e1) {
					dispose();
				}
				asStart.setEditor(new JSpinner.DateEditor(asStart, "yyyy년 MM월 dd일 a hh시 mm분"));
				asEnd.setEditor(new JSpinner.DateEditor(asEnd, "yyyy년 MM월 dd일 a hh시 mm분"));
				asStart.setValue(asStartSpin); // setEditor로 세팅된 스피너는 00시 00분이 되므로
				asEnd.setValue(asEndSpin); // setValue로 값을 다시 넣는다.
			}
		} else if (obj.equals(asRoutine)) {// 반복일정 체크박스 이벤트
			if (asRoutine.isSelected()) // 선택하자마자 반복일정 다이얼로그 생성
				new RoutineSelector(this);
			else { // 반복하지 않는 1회성 일정으로 변경
				selectStep = 0;
			}
		}
	}
	
	
	public String getAddDate1() {
		return addDate1;
	}
	public String getAddDate2() {
		return addDate2;
	}
}