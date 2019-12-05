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
	GridBagConstraints asGbc = new GridBagConstraints(); // ū �׸����
	GridBagConstraints asCon = new GridBagConstraints(); // �����׸����
	private JTextField inputTitle, inputLocation;
	String[] asLabelTx = { "��  �� :", "��  ġ :", "���� �ð� :", "���� �ð� :" };
	String[] asAlarmStepList = { "�ð� ��", "�� ��", "�� ��" };
	JComboBox<String> asAlarmStep = new JComboBox(asAlarmStepList);
	JButton asApproval, asCancel, asSearch;
	JSpinner asStart, asEnd, asAlarmSpin;
	static SpinnerDateModel asStartModel; // RoutineSelector���� ����ϹǷ�, static����
	SpinnerDateModel asEndModel;
	SpinnerNumberModel asAlarmModel;
	JCheckBox asSetAlarm, asAllday, asRoutine;
	JCheckBox asAlarmMinute, asAlarmClock, asAlarmDay;
	Date asStartDate, asStartDate2, asEndDate, asEndDate2, asStartSpin, asEndSpin;

	SimpleDateFormat asFormatD = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat asFormatT = new SimpleDateFormat("hhmm");
	SimpleDateFormat addForm = new SimpleDateFormat("HHmm00"); // db�� �ð��� �����ϱ� ���� ����
	SimpleDateFormat asFormatS = new SimpleDateFormat("yyyyMMddHHmm");
	SimpleDateFormat asFormatMM = new SimpleDateFormat("MM"); // main���� ������ calBtn I,J�� ���ϱ�����
	SimpleDateFormat asFormatdd = new SimpleDateFormat("dd");
	SimpleDateFormat asFormatYYYY = new SimpleDateFormat("YYYY"); // main���� ������ calBtn I,J�� ���ϱ�����
	DateTimeFormatter asFormatL = DateTimeFormatter.ofPattern("yyyyMMdd");
	DateTimeFormatter asFormatW = DateTimeFormatter.ofPattern("e");// ����(���� 1~7)
	DateTimeFormatter asFormatTh = DateTimeFormatter.ofPattern("W");// ���� ���° ��

	MainScreen main;
	LocalDate startDate; // �ݺ����꿡 ����� date����
	LocalDate endDate;
	String alarmView;
	String addDate1, addDate2; // ���ڵ忡 ���� ��¥,�ð���
	AlarmMgr alarmMgr;
	ScheduleBean deleteBean;

	long code = System.currentTimeMillis(); // �ݺ����ڵ��� �ڵ尪�� ����.
	int recordCount = 0; // �ݺ����ڵ��� ������ �˻��ϴ� ����.
	int alarmReturn;
	// RoutineSelectorŬ�������� ����ϱ� ���� static����
	static int selectStep = 0; // �ݺ����� Ÿ��.
	static int dat = 0; // �ݺ������� �ʿ��� �����͸� �޴� ����. (�߿�)
	static int repeatDat = 0; // �ݺ�Ƚ��
	static Boolean[] week = new Boolean[7]; // �ݺ��ؾ� �� ����
	static int untilDat = 20410101; // �ִ� 2041�����. �ݺ��� �ð�

	public static final int ADD = 1;
	public static final int EDIT = 2;

	public AddSchedule(MainScreen main, int MODE, ScheduleBean editSchedule) {
		this.main = main;
		deleteBean = editSchedule;
		mode = MODE;
		asStartSpin = new Date();
		try {// ���� ����ð��� ���� ���۽ð����� 1�ð� ���� �ð���� ����
			asEndSpin = asFormatS.parse(String.valueOf(Long.parseLong(asFormatS.format(asStartSpin)) + 100));
		} catch (ParseException e) {
		}
		// System.out.println(Long.parseLong(asFormatS.format(asStartSpin)));
		asStartModel = new SpinnerDateModel(asStartSpin, null, null, Calendar.DAY_OF_MONTH);
		asEndModel = new SpinnerDateModel(asEndSpin, null, null, Calendar.DAY_OF_MONTH);

		for (int i = 0; i < week.length; i++) {
			week[i] = false;
		}

		int posiX = main.getX();// mainScreen�� ���۸ʿ� ������ �ʵ��� �������� ��ġ��Ų��.
		int posiY = main.getY();

		setBounds(posiX + 310, posiY + 55, 550, 520);

		getContentPane().setLayout(new BorderLayout());

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(buttonPane, BorderLayout.SOUTH);

		asApproval = new JButton("����"); // �����ư
		buttonPane.add(asApproval);
		getRootPane().setDefaultButton(asApproval);
		asApproval.addActionListener(this);

		asCancel = new JButton("���"); // ��ҹ�ư
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

		asGbc.anchor = GridBagConstraints.FIRST_LINE_START; // ���̺��� �������� ���Ľ�Ŵ
		asGbc.insets = new Insets(8, 8, 8, 8);
		asGbc.gridx = 0;

		for (int i = 0; i < asLabel.length; i++) {
			asLabel[i] = new JLabel(asLabelTx[i]);
			asGbc.gridy = i;
			asGblPanel.add(asLabel[i], asGbc);
		}

		// ���� �Է�ĭ
		inputTitle = new JTextField();
		asGbc.fill = GridBagConstraints.HORIZONTAL;
		asGbc.gridx = 1;
		asGbc.gridy = 0;
		asGblPanel.add(inputTitle, asGbc);

		// ��ġ �Է�ĭ
		inputLocation = new JTextField();
		asGbc.gridy = 1;
		asGblPanel.add(inputLocation, asGbc);

		// ���� ���۳�¥ ���ǳ�
		asStart = new JSpinner();
		asStart.setModel(asStartModel);
		asStart.setEditor(new JSpinner.DateEditor(asStart, "yyyy�� MM�� dd�� a hh�� mm��"));
		// asStart.setEditor(new JSpinner.DateEditor(asStart, "yyyy�� MM�� dd�� HH�� mm��"));
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
		// ���� ���ᳯ¥ ���ǳ�
		asEnd = new JSpinner();
		asEnd.setModel(asEndModel);
		// ���� ���۳�¥���� 1�ð��� ���Ѵ�.
		asEnd.setEditor(new JSpinner.DateEditor(asEnd, "yyyy�� MM�� dd�� a hh�� mm��"));
		// asEnd.setEditor(new JSpinner.DateEditor(asEnd, "yyyy�� MM�� dd�� HH�� mm��"));
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

		// Ķ������ ���� �޺��ڽ�. ���� x��°�� ��ȯ��.

		asSetAlarm = new JCheckBox("�˶�");
		asGbc.gridx = 2;
		asGbc.gridy = 0;
		asGblPanel.add(asSetAlarm, asGbc);
		// ���� �� �˻�
		asSearch = new JButton("�˻�");
		asSearch.setPreferredSize(new Dimension(20, 20));
		asGbc.gridx = 2;
		asGbc.gridy = 1;
		asGblPanel.add(asSearch, asGbc);
		// �Ϸ����� üũ�ڽ�. üũ�ϸ� 0�ú��� 24�ñ����� ������ ��.
		asAllday = new JCheckBox("�Ϸ�����");
		asGbc.gridx = 2;
		asGbc.gridy = 2;
		asGblPanel.add(asAllday, asGbc);

		// �ݺ����� üũ�ڽ�
		asRoutine = new JCheckBox("�ݺ�����");
		asGbc.gridy = 3;
		asGblPanel.add(asRoutine, asGbc);

		// ������ ���� ������ �ִ� �ؽ�Ʈ ������
		panel.add(asTA);
		asTA.setBorder(new LineBorder(Color.GRAY, 2));
		asTA.setLineWrap(true);

		// �׸��� �� ���̾ƿ� ����
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

		asAlarmSpin.setEnabled(false); // �˶� �ð����ǳ� ��Ȱ��ȭ

		scheduleMgr = main.getScheduleMgr();
		alarmMgr = main.getAlarmMgr();
		if (MODE == ADD ) {
			setTitle("�� ���� �߰�");
		} else if (MODE == EDIT && editSchedule != null) {
			setTitle("���� ����");
			ScheduleBean bean = editSchedule;
			inputTitle.setText(bean.getTitle());
			asTA.setText(bean.getContent());

			try {
				asStartModel.setValue(CalendarInitMgr.sdf.parse(bean.getTime_from()));
				asEndModel.setValue(CalendarInitMgr.sdf.parse(bean.getTime_to()));
			} catch (ParseException e) {
			}
			inputLocation.setText(bean.getLocation());

			System.out.println("���۽ð� :" + addDate1);
			System.out.println("����ð� :" + addDate2);


		}

		setModal(true);
		setVisible(true);

	}

	// �����ͺ��̽� ���ڵ忡 ���� �����
	ScheduleMgr scheduleMgr;
	public void addToDB() {
		addDate1 = asFormatL.format(startDate) + addForm.format(asStartSpin); // �ð���(HHmm00)�� ����
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
			System.out.println("�˶� �ð�: "+ldt);
			alarmMgr.insertAlarm(ldt.toString(), inputTitle.getText());
			System.out.println("�˶� ������");
		}
	
		recordCount++; // ����� ���ڵ��� ������ �˻���.
	
	}
	
	public void updateToDB() {
		addDate1 = asFormatL.format(startDate) + addForm.format(asStartSpin); // �ð���(HHmm00)�� ����
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
			System.out.println("�˶� �ð�: "+ldt);
			alarmMgr.insertAlarm(ldt.toString(), inputTitle.getText());
			System.out.println("�˶� ������");
		}
	
		recordCount++; // ����� ���ڵ��� ������ �˻���.
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(asSetAlarm)) { // �˶� ��ư
			if (asSetAlarm.isSelected()) {
				asAlarmSpin.setEnabled(true); // �˶��ð����� ���ǳʸ� Ȱ��ȭ
			} else {
				asAlarmSpin.setEnabled(false); // ��Ȱ��ȭ
			}

		} else if (obj == asSearch) { // ���۸� �˻� ��ư
			main.setMap(inputLocation.getText(), main.mapZoom);

		} else if (obj.equals(asCancel)) { // ��ҹ�ư
			dispose();

			// Ȯ�ι�ư. ������ ��ĭ�̸� ������ �Էµ��� ����.
		} else if (obj.equals(asApproval) && !(inputTitle.getText().isEmpty())) {

			int step = asAlarmStep.getSelectedIndex();

			// �޺��ڽ��� select ������ ��,��,�Ͽ� �ش��ϴ� ���� ���Ѵ�.
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
			// ���� ������ ���./10*10�� �ؼ� 1�� �ڸ��� ������.
			alarmReturn = Integer.parseInt(asAlarmSpin.getValue().toString()) * step / 10 * 10;
			//alarmReturn = alarmReturn < 10 ? 10 : alarmReturn; // 0�� ���, 10���� �����.
			alarmView = asSetAlarm.isSelected() ? "�˶�: " + asSetAlarm.isSelected() + " " + alarmReturn + "�� ��" : "�˶�����";

			// LocalDate�� �̿��Ͽ� ����
			asStartSpin = asStartModel.getDate(); // ���ǳʿ��� ����Ʈ�� ����
			asEndSpin = asEndModel.getDate();
			startDate = asStartSpin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // LocalDate�������� ����
			endDate = asEndSpin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			Period until = startDate.until(endDate);
			int dateTH = Integer.parseInt(asFormatTh.format(startDate).toString()); // ���°��
			int dateWeek = Integer.parseInt(asFormatW.format(startDate).toString()) - 1; // �����
			dateWeek = dateWeek == 0 ? 7 : dateWeek; // 0�� ���, 7�� �ٲ۴�. Date�� 1-�������̰�
			DayOfWeek inweek = DayOfWeek.of(dateWeek); // DayOfWeek�� 1-�Ͽ����̹Ƿ� �ٲ������.

			Boolean forever = repeatDat == 0 ? true : false; // �ݺ�Ƚ���� 0ȸ�� Ƚ�����Ѿ��� �ݺ��Ѵ�.
			Boolean firstDay = true; // ù������ �˻��ϴ� ����
			int finish = 0; // �ݺ�Ƚ���� �����Ǿ� ���� ���, �� ������ üũ�ؼ� break�Ѵ�.
	
			do {// �����ͺ��̽��� ������ �Է��ϴ� ����. �ݵ�� �ѹ��� �����Ͽ��� �ϹǷ�, do while�� ���
				// System.out.println("do-while start");
				switch (selectStep) {
				case 0: // 1ȸ ����
					// System.out.println("case 0");
					if(mode ==AddSchedule.ADD)
						addToDB();
					else 
						updateToDB();
					break;
				case 1: // ���� ����
					// System.out.println("case 1");
					startDate = startDate.plusDays(dat); // dat: �� ����
					endDate = endDate.plusDays(dat);
					if(mode ==AddSchedule.ADD)
						addToDB();
					else 
						updateToDB();
					finish++;
					break;
				case 2: // ���� ����
					if (firstDay) { // ù���� �Ͽ��Ϸ� �ٲ۴�.
						startDate = startDate.with(previousOrSame(SUNDAY));
						endDate = startDate.plusDays(until.getDays()); // ���� �ִ� startDate���� ���ݸ�ŭ ��,��,���� �����ش�.
						endDate = endDate.plusMonths(until.getMonths());
						endDate = endDate.plusYears(until.getYears());
					}
					for (int i = 0; i < 7; i++) { // 1������ ��������鼭, true�� �� ���Ͽ� ���ڵ� �Է�
						if (week[i]) {
							if(mode ==AddSchedule.ADD)
								addToDB();
							else 
								updateToDB();
						}
						startDate = startDate.plusDays(1);
						endDate = endDate.plusDays(1);
					}
					startDate = startDate.plusWeeks(dat - 1); // for������ 1����ġ�� �������Ƿ�, 1�� ���ش�.
					endDate = endDate.plusWeeks(dat - 1);
					break;
				case 3: // �ſ�����
					if(mode ==AddSchedule.ADD)
						addToDB();
					else 
						updateToDB();
					startDate = startDate.plusMonths(dat);
					endDate = endDate.plusMonths(dat);
					break;
				case 4: // �ſ����� + ������ �����
					if(mode ==AddSchedule.ADD)
						addToDB();
					else 
						updateToDB();
					// dateTH: ��°��, inweek: ����
					startDate = startDate.plusMonths(dat).with(dayOfWeekInMonth(dateTH, inweek));
					endDate = startDate.plusDays(until.getDays());
					endDate = endDate.plusMonths(until.getMonths());
					endDate = endDate.plusYears(until.getYears());
					break;
				case 5: // �ų�����
					if(mode ==AddSchedule.ADD)
						addToDB();
					else 
						updateToDB();
					startDate = startDate.plusYears(dat);
					endDate = endDate.plusYears(dat);
					break;
				case 6: // �ų����� + ��� ������ ������
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

				// forever�� false�� ���, ���ѹݺ����� �ʰ� ���� �ݺ�Ƚ���� �����Ѵ�.
				if (!(forever)) {
					if (finish >= repeatDat) {
						break;
					}
				}
				firstDay = false;
			}
			// �ݺ������̸� ���� && untilDat�� ������ ��¥���� ����(�⺻�� 2041��1��1��)
			while (asRoutine.isSelected() && Integer.parseInt(asFormatL.format(startDate)) < untilDat);

			titleText = inputTitle.getText();
			
			
			dispose();

		} else if (obj.equals(asAllday)) { // �Ϸ����� üũ�ڽ�. ��,���� 0000���� �����, �����ϸ� ����ġ��Ŵ.
			if (asAllday.isSelected()) { // �Ϸ����� üũ�ڽ� true
				asStartDate = (Date) asStart.getValue(); // ���ǳ��� value���� date�� ����
				asEndDate = (Date) asEnd.getValue();
				int same = Integer.parseInt(asFormatD.format(asEndDate)); // yyyyMMdd
				int same2 = Integer.parseInt(asFormatD.format(asStartDate));

				// �Ϸ����� üũ�ڽ� Ŭ�� ��, �����ϰ� �������� ���ٸ� �������� +1���ش�.
				if (same == same2) {
					same++;
				}
				// �� ���� 0���� ���� ������ ����.(üũ�� ���¿�����, ������ �����ؾ� �ϹǷ�)
				try {
					asStartSpin = asFormatS.parse(same2 + "0000");
					asEndSpin = asFormatS.parse(same + "0000");
				} catch (ParseException e1) {
					dispose();
				}
				// ��¥�� �Է°����ϵ��� ���ǳʸ� ������.
				asStart.setEditor(new JSpinner.DateEditor(asStart, "yyyy�� MM�� dd��"));
				asEnd.setEditor(new JSpinner.DateEditor(asEnd, "yyyy�� MM�� dd��"));
				asEnd.setValue(asEndSpin);
			} else { // �Ϸ����� üũ�ڽ� false
				asStartDate2 = (Date) asStart.getValue();
				asEndDate2 = (Date) asEnd.getValue();
				try {
					asStartSpin = asFormatS.parse(asFormatD.format(asStartDate2) + asFormatT.format(asStartDate));
					asEndSpin = asFormatS.parse(asFormatD.format(asEndDate2) + asFormatT.format(asEndDate));
				} catch (ParseException e1) {
					dispose();
				}
				asStart.setEditor(new JSpinner.DateEditor(asStart, "yyyy�� MM�� dd�� a hh�� mm��"));
				asEnd.setEditor(new JSpinner.DateEditor(asEnd, "yyyy�� MM�� dd�� a hh�� mm��"));
				asStart.setValue(asStartSpin); // setEditor�� ���õ� ���ǳʴ� 00�� 00���� �ǹǷ�
				asEnd.setValue(asEndSpin); // setValue�� ���� �ٽ� �ִ´�.
			}
		} else if (obj.equals(asRoutine)) {// �ݺ����� üũ�ڽ� �̺�Ʈ
			if (asRoutine.isSelected()) // �������ڸ��� �ݺ����� ���̾�α� ����
				new RoutineSelector(this);
			else { // �ݺ����� �ʴ� 1ȸ�� �������� ����
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