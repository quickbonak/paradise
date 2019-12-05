package layout;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import connectDB.AlarmMgr;
import connectDB.CalendarInitMgr;
import connectDB.MemoBean;
import connectDB.MemoMgr;
import connectDB.ScheduleBean;
import connectDB.ScheduleMgr;
import connectDB.SettingMgr;
import scheduleDialog.AddCalanderDialog;
import scheduleDialog.AddJournalDialog;
import scheduleDialog.AddMemoDialog;
import scheduleDialog.AddSchedule;
import scheduleDialog.Alarm;
import scheduleDialog.Disturb;
import scheduleDialog.InviteUserDialog;
import scheduleDialog.Login;
import scheduleDialog.OpenAnotherCalendar;
import scheduleDialog.SearchSchedule;
import scheduleDialog.Theme_set;
import scheduleDialog.UserManagementDiaglog;

//////////////////////////////////////////////////////////////////////////////////////
@SuppressWarnings("serial")
public class MainScreen extends JFrame
		implements ActionListener, FocusListener, ItemListener, ListSelectionListener {
	Color label_color;
	int zoominNum;
	final int weekInt = 7;
	JButton weekbtn[], calbtn[][], imgBtnLeft, imgBtnRight;
	JCheckBox weatherchk;
	HintTextField tf1, textField;
	JTextArea ta1;
	JButton sideSaveBtn = new JButton("SAVE");
	JButton sideDeleteBtn = new JButton("DELETE");
	JButton sideDeleteAllBtn = new JButton("DELETE ALL");
	JList<String> calList[][], memoList;
	int memoIndex;
	JButton memoEditBtn = new JButton("EDIT");
	JButton memoDeleteBtn = new JButton("DELETE");
	JButton memoDeleteAllBtn = new JButton("DELETE ALL");
	
	JComboBox<String> mapCombo; //지도 줌
	public static String mapZoom = "17"; //지도 줌 기본값
	DefaultListModel<String>[][] dlm_schedule;
	DefaultListModel<String> dlm_memo;
	String currentCal;
	JLabel jlab;
	int year, month, day;
	int weatherOn;
	int listIndex;
	JButton datebtn1, datebtn2, datebtn3, reduction, zoom, currentDate;
	JComboBox<String> comboYear, comboMonth;

	private JButton button, searchbtn;
	
	private GoogleApiEx1 googleAPI;
	private JLabel googleMap;
	private String calWeek[] = { "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" };
	private Calendar cal;
	String listColor[] = new String[20];

	// MenuBar Field
	JMenuBar menubar;
	JMenu[] menu;
	String[] str_menu = { "추가", "로그인", "검색", "설정", "회원관리" };
	String[][] str_menu_item = { { "새 캘린더", "새 일정", "새 메모" }, { "다른 캘린더 열기", "공유 캘린더 접속" }, { "일정 검색" },
			{ "테마 설정" }, { "회원 초대", "회원 삭제" } };
	JMenuItem[][] menu_item;

	JPanel sidePanel, sidePanel1, sidePanel1_1, sidePanel1_2, sidePanel2, sidePanel3;
	JPanel cenPanel, cenPanel1, cenPanel2, cenPanel2_1, cenPanel2_2, cenPanel3, cenPanel4, cenPanel4_1;

	// setting_Theme
	int choiceTheme;
	Vector<JComponent> coloredComponents = new Vector<JComponent>();

	int selectedI = -1;
	int selectedJ = -1;
	int listSelectedI, listDeleteIdx;
	String listBackColor;
	// cardLayout
	CardLayout card;
	JPanel card_Top_Panel, card_monthly;
	Card_Weekly card_weekly;
	Card_Daily card_Daily;

	// connectDB
	static String scheduler_name_DB;
	static CalendarInitMgr cim;
	MemoMgr memoMgr;
	SettingMgr setting;
	AlarmMgr alarmMgr;
	MyCellRenderer renderer;
	ScheduleMgr scheduleMgr;
	Vector<MemoBean> vMemoList;
	Vector<ScheduleBean> vScheduleList;
	
	private Calendar today;
	String scYY ,endYY, scMM, endMM, scDD, endDD ;
	private int sci; //새 일정 추가할때 일정 추가 위치 구하기위한 변수
	private int scj; //새 일정 추가할때 일정 추가 위치 구하기위한 변수
	//////////////////////////////////////////////////////////////////////////////////////
	public MainScreen() {

		Dimension dim = new Dimension(1400, 790);
		this.setPreferredSize(dim);
		pack();
		
		cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		cal.set(year, month, 1);
		
		zoominNum =6;

		setCustomizedCalendar();
		
		display_cal();
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		validate();
	
	}

	public void setCustomizedCalendar() {
		
		if(cim.getHost()=="localhost") {
			setting = new SettingMgr(scheduler_name_DB);
			memoMgr = new MemoMgr(scheduler_name_DB);
			scheduleMgr = new ScheduleMgr(scheduler_name_DB);
			alarmMgr = new AlarmMgr(scheduler_name_DB);
		}
		else {
			setting = new SettingMgr(cim);
			memoMgr = new MemoMgr(cim);
			scheduleMgr = new ScheduleMgr(cim);
		}
		choiceTheme = setting.setUpTheme();
		
		setMenuPanel();
		setCenPanel();
		setSidePanel();
		setJlab();
		
		loadMemo();
		
		setTitle(scheduler_name_DB);
		setInitializeColor();

	}
	
	public void setMenuPanel() {
		menubar = new JMenuBar();
		menu = new JMenu[str_menu.length];
		for (int i = 0; i < str_menu.length; i++) {
			menu[i] = new JMenu(str_menu[i]);
			menubar.add(menu[i]);
		}
		menu_item = new JMenuItem[str_menu.length][str_menu_item[0].length];
		for (int i = 0; i < str_menu_item.length; i++) {
			for (int j = 0; j < str_menu_item[i].length; j++) {
				menu_item[i][j] = new JMenuItem(str_menu_item[i][j]);
				menu[i].add(menu_item[i][j]);
				menu_item[i][j].addActionListener(this);
			}
		}
		setJMenuBar(menubar);
	}

	public void setSidePanel() {

		sidePanel = new JPanel(); // 사이드 편집창 메모장 패널
		sidePanel1 = new JPanel(); // 사이드 편집창 메모장 패널
		sidePanel1_1 = new JPanel(); // 사이드 편집창 메모장 패널
		sidePanel1_2 = new JPanel(); // 사이드 편집창 메모장 패널
		sidePanel2 = new JPanel(); // 사이드 편집창 메모장 패널
		sidePanel3 = new JPanel(); // 사이드 편집창 메모장 패널
		// 사이드 패널 크기
		sidePanel.setPreferredSize(new Dimension(300, 600));

		sidePanel.setLayout(new GridLayout(3, 1));
		sidePanel.add(sidePanel1);
		sidePanel.add(sidePanel2);
		sidePanel.add(sidePanel3);
		
		setPlaceHolder();
		button = new JButton("검색");
		googleAPI = new GoogleApiEx1();
		googleMap = new JLabel();
		mapCombo = new JComboBox<>();
		for (int i = 1; i < 21; i++) {
			mapCombo.addItem(i+"");	
		}
		mapCombo.setSelectedItem(mapZoom+"");
		mapZoom = (String) (mapCombo.getSelectedItem());
		
		mapCombo.addItemListener(this);
		button.addActionListener(this);
		tf1.addActionListener(this);
		textField.addActionListener(this);
		
		sidePanel1.setLayout(new BorderLayout());
		sidePanel1_1.add(textField);
		sidePanel1_1.add(mapCombo);
		sidePanel1_1.add(button);
		sidePanel1_2.add(googleMap);
		sidePanel1.add(sidePanel1_1, BorderLayout.NORTH);
		sidePanel1.add(sidePanel1_2, BorderLayout.CENTER);

		ta1 = new JTextArea(13, 26);
		ta1.setLineWrap(true);

		// 왼쪽 사이드2 패널 레이아웃 설정
		sidePanel2.add(sideSaveBtn);
		sidePanel2.add(sideDeleteBtn);
		sidePanel2.add(sideDeleteAllBtn);
		sideSaveBtn.addActionListener(this);
		sideDeleteBtn.addActionListener(this);
		sideDeleteAllBtn.addActionListener(this);
		
		
		dlm_memo = new DefaultListModel<String>();
		memoList = new JList (dlm_memo);
		renderer = new MyCellRenderer();
		memoList.setCellRenderer(renderer);
		memoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
		sidePanel3.add(memoList);
		sidePanel3.add(memoEditBtn);
		sidePanel3.add(memoDeleteBtn);
		sidePanel3.add(memoDeleteAllBtn);
		
		memoEditBtn.addActionListener(this);
		memoDeleteBtn.addActionListener(this);
		memoDeleteAllBtn.addActionListener(this);
		memoList.addListSelectionListener(this);
		
	
		GridBagLayout gbl_sidepanel2 = new GridBagLayout();
		gbl_sidepanel2.columnWidths = new int[] { 0, 3 };
		gbl_sidepanel2.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_sidepanel2.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_sidepanel2.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		sidePanel2.setLayout(gbl_sidepanel2);
		// 제목 편집창 레이아웃
		GridBagConstraints gbc_tf1 = new GridBagConstraints();
		gbc_tf1.gridwidth = 3;
		gbc_tf1.gridheight = 1;
		gbc_tf1.fill = GridBagConstraints.HORIZONTAL;
		gbc_tf1.gridx = 0;
		gbc_tf1.gridy = 0;
		sidePanel2.add(tf1, gbc_tf1);
		
		tf1.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		// 내용 편집창 레이아웃
		GridBagConstraints gbc_ta1 = new GridBagConstraints();
		gbc_ta1.gridwidth = 3;
		gbc_ta1.gridheight = 4;
		gbc_ta1.fill = GridBagConstraints.BOTH;
		gbc_ta1.gridx = 0;
		gbc_ta1.gridy = 1;
		sidePanel2.add(ta1, gbc_ta1);
		// SAVE 버튼 레이아웃
		GridBagConstraints gbc_sideSaveBtn = new GridBagConstraints();
		gbc_sideSaveBtn.fill = GridBagConstraints.BOTH;
		gbc_sideSaveBtn.gridx = 0;
		gbc_sideSaveBtn.gridy = 5;
		sidePanel2.add(sideSaveBtn, gbc_sideSaveBtn);
		// DELETE 버튼 레이아웃
		GridBagConstraints gbc_sideDeleteBtn = new GridBagConstraints();
		gbc_sideDeleteBtn.fill = GridBagConstraints.BOTH;
		gbc_sideDeleteBtn.gridx = 1;
		gbc_sideDeleteBtn.gridy = 5;
		sidePanel2.add(sideDeleteBtn, gbc_sideDeleteBtn);
		// DELETEALL 버튼 레이아웃
		GridBagConstraints gbc_sideDeleteAllBtn = new GridBagConstraints();
		gbc_sideDeleteAllBtn.fill = GridBagConstraints.BOTH;
		gbc_sideDeleteAllBtn.gridx = 2;
		gbc_sideDeleteAllBtn.gridy = 5;
		sidePanel2.add(sideDeleteAllBtn, gbc_sideDeleteAllBtn);

		//*********************************
		GridBagLayout gbl_sidepanel3 = new GridBagLayout();
		gbl_sidepanel3.columnWidths = new int[] { 0, 5 };
		gbl_sidepanel3.rowHeights = new int[] { 0, 0, 0, 0,0,0,0 };
		gbl_sidepanel3.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_sidepanel3.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		sidePanel3.setLayout(gbl_sidepanel3);

		// 메모리스트 레이아웃
		GridBagConstraints gbc_memoList = new GridBagConstraints();
		gbc_memoList.gridwidth = 4;
		gbc_memoList.gridheight = 6;
		gbc_memoList.fill = GridBagConstraints.BOTH;
		gbc_memoList.gridx = 0;
		gbc_memoList.gridy = 1;
		sidePanel3.add(memoList, gbc_memoList);
		// EDIT 버튼 레이아웃
		GridBagConstraints gbc_memoEditBtn = new GridBagConstraints();
		gbc_memoEditBtn.fill = GridBagConstraints.BOTH;
		gbc_memoEditBtn.gridx = 0;
		gbc_memoEditBtn.gridy = 7;
		sidePanel3.add(memoEditBtn, gbc_memoEditBtn);
		// DELETE 버튼 레이아웃
		GridBagConstraints gbc_memoDeleteBtn = new GridBagConstraints();
		gbc_memoDeleteBtn.fill = GridBagConstraints.BOTH;
		gbc_memoDeleteBtn.gridx = 1;
		gbc_memoDeleteBtn.gridy = 7;
		sidePanel3.add(memoDeleteBtn, gbc_memoDeleteBtn);
		// DELETEALL 버튼 레이아웃
		GridBagConstraints gbc_memoDeleteAllBtn = new GridBagConstraints();
		gbc_memoDeleteAllBtn.fill = GridBagConstraints.BOTH;
		gbc_memoDeleteAllBtn.gridx = 2;
		gbc_memoDeleteAllBtn.gridy = 7;
		sidePanel3.add(memoDeleteAllBtn, gbc_memoDeleteAllBtn);
	
		
		add(BorderLayout.WEST, sidePanel);
	}


	public JList<String> getMemoList() {
		return memoList;
	}

	public void loadMemo() {
		vMemoList = memoMgr.selectMemo();
		Iterator<MemoBean> it = vMemoList.iterator();
		while(it.hasNext()) {
			MemoBean bean = it.next();
			dlm_memo.addElement(bean.getContent());
			
	
		}
	}
	
	public void loadSchedule() {

		vScheduleList = scheduleMgr.selectScheduleMonthly(year, month+1);
		Iterator<ScheduleBean> it = vScheduleList.iterator();
		while(it.hasNext()) {
			ScheduleBean bean = it.next();
		
			for (int i = 0; i < zoominNum; i++) {
				for (int j = 0; j < weekInt; j++) {
					String date = bean.getTime_from().substring(8, 10);
					String dateEnd = bean.getTime_to().substring(8, 10);
					String dateM = bean.getTime_from().substring(5, 7);
					String dateEndM = bean.getTime_to().substring(5, 7);
					String dateY = bean.getTime_from().substring(0, 4);
					String dateEndY = bean.getTime_to().substring(0, 4);
					JButton btn = calbtn[i][j];
					if(date.indexOf("0")==0) {
						date = date.substring(1);
					}else if (dateM.indexOf("0")==0) {
						dateM = dateM.substring(1);
					}else if(dateY.equals(year+"")&&dateM.equals((month+1)+"")&&date.equals(calbtn[i][j].getText()) && (btn.getName()=="내달" || btn.getName()=="공휴일")){
							dlm_schedule[i][j].addElement(bean.getTitle());
						}else if (dateEnd.indexOf("0")==0) {
							dateEnd = dateEnd.substring(1);
						}else if (dateEndM.indexOf("0")==0) {
							dateEndM = dateEndM.substring(1);
						}else if (dateEndY.equals(year+"")&&dateEndM.equals((month+1)+"")&&dateEnd.equals(calbtn[i][j].getText()) && (btn.getName()=="내달" || btn.getName()=="공휴일") ) {
							dlm_schedule[i][j].addElement(bean.getTitle());
					}
				}
			}
		}
	}
	
	public void setCenPanel1() {
		cenPanel1 = new JPanel(); // 일주월선택 패널
		cenPanel1.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

		datebtn1 = new JButton("일");
		datebtn2 = new JButton("주");
		datebtn3 = new JButton("월");

		reduction = new JButton("확대");
		zoom = new JButton("축소");
		currentDate = new JButton("현재 날짜");

		reduction.addActionListener(this);
		zoom.addActionListener(this);
		currentDate.addActionListener(this);
		datebtn1.addActionListener(this);
		datebtn2.addActionListener(this);
		datebtn3.addActionListener(this);

		cenPanel1.add(datebtn1);
		cenPanel1.add(datebtn2);
		cenPanel1.add(datebtn3);
		cenPanel1.add(reduction);
		cenPanel1.add(zoom);
		cenPanel1.add(currentDate);

		cenPanel.add(cenPanel1);
	}

	public void setCenPanel2() {
		cenPanel2 = new JPanel(); // 현재 날짜 표시 패널
		cenPanel2.setLayout(new GridLayout(0, 2));

		cenPanel2_1 = new JPanel();
		cenPanel2_1.setLayout(new FlowLayout(FlowLayout.LEFT));
		cenPanel2_2 = new JPanel();
		cenPanel2_2.setLayout(new FlowLayout(FlowLayout.RIGHT));

		imgBtnLeft = new JButton(new ImageIcon("layout/img1.png"));
		
		imgBtnRight = new JButton(new ImageIcon("layout/img2.png"));

		imgBtnLeft.setBorderPainted(false);
		imgBtnLeft.setFocusPainted(false);
		imgBtnRight.setBorderPainted(false);
		imgBtnRight.setFocusPainted(false);

		imgBtnLeft.addActionListener(this);
		imgBtnRight.addActionListener(this);

		jlab = new JLabel(currentCal);

		comboYear = new JComboBox<>();
		comboMonth = new JComboBox<>();

		for (int i = 1970; i < 2041; i++)
			comboYear.addItem(i + " 년");
		for (int i = 1; i < 13; i++)
			comboMonth.addItem(i + " 월");

		comboYear.setSelectedIndex(year - 1970);
		comboMonth.setSelectedIndex(month);

		searchbtn = new JButton("날짜 검색");
		searchbtn.addActionListener(this);

		weatherchk = new JCheckBox("날씨 정보");
		weatherchk.addItemListener(this);

		cenPanel2_1.add(imgBtnLeft);
		cenPanel2_1.add(imgBtnRight);
		cenPanel2_1.add(jlab);

		cenPanel2_2.add(weatherchk);
		cenPanel2_2.add(comboYear);
		cenPanel2_2.add(comboMonth);
		cenPanel2_2.add(searchbtn);

		cenPanel2.add(cenPanel2_1);
		cenPanel2.add(cenPanel2_2);

		cenPanel.add(cenPanel2);
		validate();
	}

	public void setMonthView_Initialize() {
		cenPanel3 = new JPanel(); // 주일 표시 패널
		cenPanel3.setLayout(new GridLayout(1, weekInt));

		cenPanel4 = new JPanel(); // 달력패널
		cenPanel4.setLayout(new GridLayout());

		cenPanel4_1 = new JPanel();
		cenPanel4_1.setLayout(new GridLayout(zoominNum, weekInt));

		weekbtn = new JButton[weekInt];
		for (int i = 0; i < weekInt; i++) {
			weekbtn[i] = new JButton(calWeek[i]);
			weekbtn[i].setBorderPainted(false);
			weekbtn[i].setOpaque(true);
			weekbtn[i].setFocusable(false);
			weekbtn[i].setHorizontalAlignment(SwingConstants.LEFT);
			cenPanel3.add(weekbtn[i]);
		}

		calbtn = new JButton[zoominNum][weekInt];

		JScrollPane[][] jsp = new JScrollPane[zoominNum][weekInt];
		dlm_schedule = new DefaultListModel[zoominNum][weekInt];
		calList = new JList[zoominNum][weekInt];
		for (int i = 0; i < zoominNum; i++) {
			for (int j = 0; j < weekInt; j++) {
				calbtn[i][j] = new JButton();
				calbtn[i][j].setOpaque(true);
				calbtn[i][j].addActionListener(this);
				dlm_schedule[i][j] = new DefaultListModel<String>();
				calList[i][j] = new JList(dlm_schedule[i][j]);
				jsp[i][j] = new JScrollPane();
				
				calList[i][j].addFocusListener(this);
				calList[i][j].addMouseListener(new ListMouseAdaper(this));
				
				jsp[i][j].setViewportView(calList[i][j]);

				Panel p = new Panel();

				GridBagLayout gbl_panel = new GridBagLayout();
				gbl_panel.columnWidths = new int[] { 0 };
				gbl_panel.rowHeights = new int[] { 3 };
				gbl_panel.columnWeights = new double[] { 1.0 };
				gbl_panel.rowWeights = new double[] { 0.0, 0.0 };
				p.setLayout(gbl_panel);

				GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
				gbc_btnNewButton.gridwidth = 0;
				gbc_btnNewButton.fill = GridBagConstraints.BOTH;
				gbc_btnNewButton.gridx = 0;
				gbc_btnNewButton.gridy = 0;
				p.add(calbtn[i][j], gbc_btnNewButton);

				GridBagConstraints gbc_List = new GridBagConstraints();
				gbc_List.weighty = 1.0;
				gbc_List.weightx = 1.0;
				gbc_List.gridheight = 2;
				gbc_List.fill = GridBagConstraints.BOTH;
				gbc_List.gridx = 0;
				gbc_List.gridy = 1;
				p.add(jsp[i][j], gbc_List);
				cenPanel4_1.add(p);
			}
		}

		cenPanel4.add(cenPanel4_1);
		card_monthly = new JPanel();
		card_monthly.setLayout(new BorderLayout());
		card_monthly.add(cenPanel3, BorderLayout.NORTH);
		card_monthly.add(cenPanel4, BorderLayout.CENTER);
	}

	public void setCardView_Initialize() {
		card = new CardLayout();

		card_Top_Panel = new JPanel();
		card_Top_Panel.setLayout(card);

		card_weekly = new Card_Weekly(this);
		card_weekly.change_value(choiceTheme);
		card_Daily = new Card_Daily(this);
		card_Daily.change_value(choiceTheme);

		card_Top_Panel.add(card_monthly, "month");
		card_Top_Panel.add(card_weekly, "week");
		card_Top_Panel.add(card_Daily, "day");
		cenPanel.add(card_Top_Panel);

		card.show(card_Top_Panel, "month");
	}

	public void setCenPanel() {
		cenPanel = new JPanel();
		cenPanel.setLayout(new BoxLayout(cenPanel, BoxLayout.Y_AXIS));
		setCenPanel1();
		setCenPanel2();
		setMonthView_Initialize();
		setCardView_Initialize();

		add(BorderLayout.CENTER, cenPanel);

	}

	public void setPlaceHolder() {
		tf1 = new HintTextField("선택한 일정 제목을 입력하세요");
		tf1.setColumns(10);
		textField = new HintTextField("Google 지도 검색");
		textField.setColumns(15);
	}
	
	public void setInitializeColor() {
		LineBorder lb = new LineBorder(colorDeco(1));

		sidePanel.setBackground(colorDeco(0));
		sidePanel1.setBackground(colorDeco(0));

		sidePanel1_1.setBackground(colorDeco(0));
		sidePanel1_2.setBackground(colorDeco(0));
		sidePanel2.setBackground(colorDeco(0));
		sidePanel3.setBackground(colorDeco(0));

		cenPanel.setBackground(colorDeco(0));
		cenPanel1.setBackground(colorDeco(0));
		cenPanel2.setBackground(colorDeco(0));
		cenPanel2_1.setBackground(colorDeco(0));
		cenPanel2_2.setBackground(colorDeco(0));
		cenPanel3.setBackground(colorDeco(0));
		cenPanel4.setBackground(colorDeco(0));
		cenPanel4_1.setBackground(colorDeco(0));

		sidePanel.setBorder(lb);
		sidePanel1.setBorder(lb);
		sidePanel2.setBorder(lb);
		sidePanel3.setBorder(lb);

		for (int idx = 0; idx < weekInt; idx++) {
			weekbtn[idx].setBackground(colorDeco(2));
			weekbtn[idx].setForeground(colorDeco(8));
		}
		weekbtn[0].setBackground(colorDeco(4));
		weekbtn[0].setForeground(colorDeco(9));
		weekbtn[6].setBackground(colorDeco(3));
		weekbtn[6].setForeground(colorDeco(10));

		for (int idx = 0; idx < zoominNum; idx++) {
			for (int jdx = 0; jdx < weekInt; jdx++) {
				calbtn[idx][jdx].setBackground(colorDeco(5));
				calbtn[idx][jdx].setForeground(colorDeco(11));
			}
		}

		weatherchk.setBackground(colorDeco(0));
	}

	public void setColoredComponents() {
		coloredComponents.addElement(sidePanel);
		coloredComponents.addElement(cenPanel);
		coloredComponents.addElement(cenPanel1);
		coloredComponents.addElement(cenPanel2);
		coloredComponents.addElement(cenPanel2_1);
		coloredComponents.addElement(cenPanel2_2);
		coloredComponents.addElement(cenPanel3);
		coloredComponents.addElement(cenPanel4);
		coloredComponents.addElement(sidePanel1);
		coloredComponents.addElement(sidePanel1_1);
		coloredComponents.addElement(sidePanel1_2);
		coloredComponents.addElement(sidePanel2);
		coloredComponents.addElement(sidePanel3);
		coloredComponents.addElement(cenPanel4_1);

		for (int i = 0; i < weekbtn.length; i++) {
			coloredComponents.addElement(weekbtn[i]);
		}

		for (int i = 0; i < zoominNum; i++) {
			for (int j = 0; j < weekInt; j++) {
				coloredComponents.addElement(calbtn[i][j]);
			}
		}

		coloredComponents.addElement(weatherchk);
	}

	class MyCellRenderer implements ListCellRenderer {
		JLabel label;

		public MyCellRenderer() {
			label = new JLabel();
			label.setOpaque(true);
		}
		
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			vMemoList = memoMgr.selectMemo();
				for (int i = 0; i <vMemoList.size(); i++) {
					listColor[i] = vMemoList.get(i).getLabel_color();
					if (index == i) {
						if(index!=-1)
						label.setText(value.toString());
						label.setBackground(Color.decode(listColor[i]));		
					}
				}
			return label;
		}		
	}

	public Color colorDeco(int elementNumber) {
		Color deco = Color.decode(Theme_set.setColor[choiceTheme][elementNumber]);
		return deco;
	}
	
	public void searchDate() {
		for (int i = 0; i < zoominNum; i++) {
			for (int j = 0; j < weekInt; j++) {
				dlm_schedule[i][j].removeAllElements();
			}
		}
		validate();
		year = comboYear.getSelectedIndex() + 1970;
		month = comboMonth.getSelectedIndex();
		cal.set(year, month, 1);
		setJlab();
		display_cal();
	}

	public void currentDate() {
		for (int i = 0; i < zoominNum; i++) {
			for (int j = 0; j < weekInt; j++) {
				dlm_schedule[i][j].removeAllElements();
				calbtn[i][j].setForeground(colorDeco(11)); // 휴일 버튼 글자색원래대로
			}
		}
		validate();
		today = Calendar.getInstance();
		year = today.get(Calendar.YEAR);
		month = today.get(Calendar.MONTH);
		day = today.get(Calendar.DATE) - 1;
		cal.set(year, month, 1);
		setJlab();
		display_cal();
		calImgBtn();
		comboYear.setSelectedIndex(year - 1970);
		comboMonth.setSelectedIndex(month);
	}

	public void setJlab() {
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		currentCal = year + "년 " + (month + 1) + "월";
		jlab.setText(currentCal);
	}

	public void display_cal() {
		int StartDate = cal.get(Calendar.DAY_OF_WEEK) - 1;
		int MaxDate = cal.getActualMaximum(Calendar.DATE);
		int date_Now = 1;// 현재 날짜를 나타낸다.
		int cntsum = 0;
		int af = 1;
		for (int j1 = 1; j1 < 7; j1++) {
			if (date_Now == 1 && j1 < StartDate)
				cntsum = cntsum + 1; // 이전 달 마지막날 구하기 위한 변수
		}
		for (int i = 0; i < zoominNum; i++) {
			for (int j = 0; j < weekInt; j++) {
				if (date_Now == 1 && j < StartDate) {// 1일이 일요일이 아니라면 요일까지 이전달 날짜
					cal.set(year, month - 1, 1);
					int last_Date = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
					calbtn[i][j].setText(last_Date - cntsum + ""); // 빈칸은 이전달 마지막 주 부터 채우기
					cntsum--;
					cal.set(year, month, 1);
					calbtn[i][j].setBackground(colorDeco(6));
					calbtn[i][j].setForeground(colorDeco(11));
					calbtn[i][j].setName("외달");
				} else if (date_Now > MaxDate) {// 날짜가 마지막 날짜보다 커지면
					calbtn[i][j].setText(af + "");
					af++;
					calbtn[i][j].setBackground(colorDeco(6));
					calbtn[i][j].setForeground(colorDeco(11));
					calbtn[i][j].setName("외달");

				} else {
					calbtn[i][j].setBackground(colorDeco(5));
					calbtn[i][j].setForeground(colorDeco(11));
					calbtn[i][j].setText(date_Now + "");
					calbtn[i][j].setName("내달");

					date_Now++;
				}
			}
		}
		
		appendHoliday();
		loadSchedule();
		if (weatherOn ==2) {
			Calendar weatherday = Calendar.getInstance();
			int weather_year = weatherday.get(Calendar.YEAR);
			int weather_month = weatherday.get(Calendar.MONTH);
			if(year==weather_year&&(month)==(weather_month)) {
			weather();
			}
		}
		for (int i = 0; i < zoominNum; i++) {
			for (int j = 0; j < weekInt; j++) {
				Calendar to = Calendar.getInstance();
				int btncolor =Integer.parseInt(calbtn[i][j].getText());// 오늘 날짜 버튼 색넣을 위치 
				int todate = to.get(Calendar.DATE); //오늘 날짜
				int toyear = to.get(Calendar.YEAR);
				int tomonth = (to.get(Calendar.MONTH)+1);
				if (tomonth==(month+1)&&toyear==year&&btncolor==todate&&(calbtn[i][j].getName()=="내달" || calbtn[i][j].getName()=="공휴일")) {
						calbtn[i][j].setBackground(colorDeco(7));	//달력 생성시 오늘날짜 색 변경
						calbtn[i][j].setForeground(colorDeco(13));
				}
			}
		}
	}

	public void appendHoliday() {
		AppendHoliday holidays = new AppendHoliday(year);
		Enumeration<LocalDate> el = holidays.ht.keys();
		while (el.hasMoreElements()) {
			LocalDate ld = el.nextElement();
			if (ld.getMonthValue() == month + 1) {
				for (int i = 0; i < zoominNum; i++) {
					for (int j = 0; j < weekInt; j++) {
						if (Integer.parseInt(calbtn[i][j].getText()) == ld.getDayOfMonth() && calbtn[i][j].getName() == "내달") {
							calbtn[i][j].setBackground(colorDeco(4));
							calbtn[i][j].setForeground(colorDeco(12));
							calbtn[i][j].setName("공휴일");
							dlm_schedule[i][j].addElement(holidays.ht.get(ld));
						}
					}
				}
			}
		}
	}

	public void weather() {
		parserTest bringwet = new parserTest();
		int a = 0;
		int w = 2;
		Calendar today_weather = Calendar.getInstance();
		int todayI = today_weather.get(Calendar.WEEK_OF_MONTH)-1;
		int todayJ = today_weather.get(Calendar.DAY_OF_WEEK)-1;
		
		if(weatherOn==2) {
			try {
				if (year == today_weather.get(Calendar.YEAR) && month == today_weather.get(Calendar.MONTH)) {
					while (a < 7) {
						dlm_schedule[todayI][todayJ].addElement(
								"날씨 : " + bringwet.weatresult[w].toString() + " " + bringwet.tempresult[w] + "°C");
						todayJ = todayJ + 1;
						if (todayJ >6) {
							todayI = todayI + 1;
							todayJ = 0;
						} else if (todayI > 5) {
							todayI = 0;
						}
						w = w + 2;
						a++;
					}
				}
			} catch (Exception e) {e.printStackTrace();}
		}
	}

	public void dateZoom() {
		cenPanel3.removeAll();
		cenPanel4.removeAll();
		card_Top_Panel.removeAll();
		cenPanel.removeAll();
		cenPanel.add(cenPanel1);
		cenPanel.add(cenPanel2);
		setMonthView_Initialize();
		setCardView_Initialize();
		setInitializeColor();

		setJlab();
		display_cal();
		validate();
	}

	public void calImgBtn() {
		for (int i = 0; i < zoominNum; i++) 
			for (int j = 0; j < weekInt; j++) 
				dlm_schedule[i][j].removeAllElements();
	
		cal.set(year, month, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		setJlab();
		display_cal();

		validate();

	}

	//이중 for문으로 버튼 1개 찾기
	public void calbtnClicked(Object clicked) {
		JButton btn = (JButton) clicked;
		for (int i = 0; i < zoominNum; i++) {
			for (int j = 0; j < weekInt; j++) {
				if(btn == calbtn[i][j]) {
					selectedI = i;
					selectedJ = j;
					calbtn[i][j].setBackground(colorDeco(7));// 버튼 선택했을 때
					calbtn[i][j].setForeground(colorDeco(13));
				}else {
					calbtn[i][j].setBackground(colorDeco(5));// 버튼 선택해제 했을 때
					calbtn[i][j].setForeground(colorDeco(11));
					Calendar to = Calendar.getInstance();
					int btncolor =Integer.parseInt(calbtn[i][j].getText());// 오늘 날짜 버튼 색넣을 위치 
					int todate = to.get(Calendar.DATE); //오늘 날짜
					int toyear = to.get(Calendar.YEAR);
					int tomonth = (to.get(Calendar.MONTH)+1);
					if (tomonth==(month+1)&&toyear==year&&btncolor==todate&&(calbtn[i][j].getName()=="내달" || calbtn[i][j].getName()=="공휴일")) {
							calbtn[i][j].setBackground(colorDeco(7));//클릭시에도 오늘 날짜 색변경 안되게
							calbtn[i][j].setForeground(colorDeco(13));
					}
					if (calbtn[i][j].getName() == "외달")
						calbtn[i][j].setBackground(colorDeco(6));
					if (tomonth==(month+1)&&toyear==year&&btncolor==todate&&(calbtn[i][j].getName()=="내달" || calbtn[i][j].getName()=="공휴일")) {
						calbtn[i][j].setBackground(colorDeco(7));//클릭시에도 오늘 날짜 색변경 안되게
						calbtn[i][j].setForeground(colorDeco(13));
				}
					else if (calbtn[i][j].getName() == "공휴일")
						calbtn[i][j].setBackground(colorDeco(4));// 일요일
					if (tomonth==(month+1)&&toyear==year&&btncolor==todate&&(calbtn[i][j].getName()=="내달" || calbtn[i][j].getName()=="공휴일")) {
						calbtn[i][j].setBackground(colorDeco(7));	//클릭시에도 오늘 날짜 색변경 안되게
						calbtn[i][j].setForeground(colorDeco(13));
				}
				}
			}
		}
	}
	
	public void quickInsertSchedule(JButton clicked) {
		String date_tmp;
		if(clicked.getName()=="내달"||clicked.getName()=="공휴일") {
			date_tmp = year+"-"+(month+1)+"-"+clicked.getText(); 
		}else if (clicked.getName()=="외달" && Integer.parseInt(clicked.getText())>15 ) {
			date_tmp = year+"-"+month+"-"+clicked.getText();
		}else if (clicked.getName()=="외달" && Integer.parseInt(clicked.getText())<16) {
			date_tmp = year+"-"+(month+2)+"-"+clicked.getText();
		}else {
			System.out.println("에러 : 일정 추가 - 날짜 지정");
			date_tmp = null;
		}

		ScheduleBean bean = new ScheduleBean();
		bean.setTitle(tf1.getText());
		bean.setContent(ta1.getText());
		bean.setTime_from(date_tmp);
		bean.setTime_to(date_tmp);
		bean.setLocation(textField.getText());
		bean.setModified_at(CalendarInitMgr.sdf.format(new Date()));
		scheduleMgr.insertSchedule(bean);
		
		System.out.println("인서트 퀵");
		dlm_schedule[selectedI][selectedJ].addElement(tf1.getText());
		tf1.setText("");
		ta1.setText("");
		textField.setText("");
		tf1.requestFocus();
	}
	
	public void quickDeleteSchedule(JButton clicked, String tmp) {
		String date_tmp;
		String day = clicked.getText();
		if(Integer.parseInt(day)<10) {
			day = "0"+day;
		}
		if(clicked.getName()=="내달"||clicked.getName()=="공휴일") {
			date_tmp = year+"-"+(month+1)+"-"+day;
		}else if (clicked.getName()=="외달" && Integer.parseInt(day)>15 ) {
			date_tmp = year+"-"+month+"-"+day;
		}else if (clicked.getName()=="외달" && Integer.parseInt(day)<16) {
			date_tmp = year+"-0"+(month+2)+"-"+day;
		}else {
			System.out.println("에러 : 일정 삭제 - 날짜 지정");
			date_tmp = null;
		}
		
		Vector<ScheduleBean> vSchList = scheduleMgr.selectScheduleDaily(date_tmp);
		for (int i = 0; i < vSchList.size(); i++) {
			ScheduleBean bean = vSchList.get(i);
				if(bean.getTitle().equals(tmp)) {
					int id = bean.getId();
					scheduleMgr.deleteSchedule(id);
					dlm_schedule[selectedI][selectedJ].remove(listIndex);
					
				}
		}
		validate();
	}
	
	public void quickDeleteAllSchedule(JButton clicked) {
		String date_tmp;
		String day = clicked.getText();
		if(Integer.parseInt(day)<10) {
			day = "0"+day;
		}
		
		if(clicked.getName()=="내달"||clicked.getName()=="공휴일") {
			date_tmp = year+"-"+(month+1)+"-"+day;
		}else if (clicked.getName()=="외달" && Integer.parseInt(day)>15 ) {
			date_tmp = year+"-"+month+"-"+day;
		}else if (clicked.getName()=="외달" && Integer.parseInt(day)<16) {
			date_tmp = year+"-"+(month+2)+"-"+day;
		}else {
			System.out.println("에러 : 일정 삭제 - 날짜 지정");
			date_tmp = null;
		}

		Vector<ScheduleBean> vSchList = scheduleMgr.selectScheduleDaily(date_tmp);
		for (int i = 0; i < vSchList.size(); i++) {
			ScheduleBean bean = vSchList.get(i);
			int id = bean.getId();
			scheduleMgr.deleteSchedule(id);
		}	
		dlm_schedule[selectedI][selectedJ].removeAllElements();
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj instanceof JMenuItem) {
			JMenuItem tmp = (JMenuItem) obj;
			switch (tmp.getText()) {
			case "새 캘린더":
				new AddCalanderDialog(this);
				break;
			case "새 일정":
				AddSchedule ASD = new AddSchedule(this, AddSchedule.ADD, new ScheduleBean());
				try {
					String scYMD = ASD.getAddDate1(); //스피너 시작시각
					String endYMD = ASD.getAddDate2();
					scYY = scYMD.substring(0, 4);
					scMM = scYMD.substring(4, 6);
					if (scMM.charAt(0)=='0')
						scMM =scYMD.substring(5, 6);
					scDD = scYMD.substring(6, 8); 
					if (scDD.charAt(0)=='0') 
						scDD= scYMD.substring(7, 8);
					endYY = endYMD.substring(0, 4);
					endMM = endYMD.substring(4, 6);
					if (endMM.charAt(0)=='0')
						endMM = endYMD.substring(5, 6);
					endDD = endYMD.substring(6, 8);
					if (endDD.charAt(0)=='0')
						endDD= endYMD.substring(7, 8);
					for (int i = 0; i < zoominNum; i++) {
						for (int j = 0; j < weekInt; j++) {
							if (calbtn[i][j].getText().equals(scDD)&&(calbtn[i][j].getName()=="내달" || calbtn[i][j].getName()=="공휴일")) {
								sci = i;
								scj = j;
									if(year==Integer.parseInt(scYY)&&(month+1)==Integer.parseInt(scMM)) {
										dlm_schedule[sci][scj].addElement(ASD.getTitleText());
									}
								}else if (calbtn[i][j].getText().equals(endDD)&&(calbtn[i][j].getName()=="내달" || calbtn[i][j].getName()=="공휴일")) {
									sci = i;
									scj = j;
									if (year==Integer.parseInt(endYY)&&(month+1)==Integer.parseInt(endMM)) {
										dlm_schedule[sci][scj].addElement(ASD.getTitleText());
								}
							}
						}
					}
				} catch (NullPointerException e2) {
				}
				break;
			case "공유 캘린더 접속":
				Login login = new Login(this);
				break;
			case "새 메모":
				AddMemoDialog amd = new AddMemoDialog(this, 1, "","");
			break;
			case "새 일기":
				new AddJournalDialog(this);
				break;
			case "다른 캘린더 열기":
				OpenAnotherCalendar oacd = new OpenAnotherCalendar(this);	
				break;
			case "일정 검색":
				new SearchSchedule(this);
				break;
			case "알람 설정":
				new Disturb(this);
				break;
			case "테마 설정":
				setColoredComponents();
				Theme_set ts = new Theme_set(this, coloredComponents);
				choiceTheme = ts.getChoiceTheme();
				dateZoom();
				if(!cenPanel2_1.isVisible()) 
					cenPanel2_1.setVisible(true);
	
				card_weekly.change_value(choiceTheme);
				card_Daily.change_value(choiceTheme);
				dateZoom();
				validate();
				break;
			case "회원 초대":
				new InviteUserDialog(this);
				break;
			case "회원 삭제":
				new UserManagementDiaglog();
				break;
			}

		} else if (obj == button||obj == textField) {
				setMap(textField.getText(),mapZoom);
				textField.requestFocus();		
			} else if (obj == imgBtnLeft) {
				month--;
				calImgBtn();
			} else if (obj == imgBtnRight) {
				month++;
				calImgBtn();
			} else if (obj == currentDate) {
				currentDate();
			} else if (obj == searchbtn) {
				searchDate();
			} else if (obj == zoom) {
				if (card_Daily.isVisible()!=true&&card_weekly.isVisible()!=true) {
					if (zoominNum < 6) {
						zoominNum++;
						dateZoom();
					}
				}
			} else if (obj == reduction) {
				if (card_Daily.isVisible()!=true&&card_weekly.isVisible()!=true) {
					if (zoominNum > 2) {
						zoominNum--;
						dateZoom();
					}
				}
			} else if (obj == datebtn1) {
				card.show(card_Top_Panel, "day");
				cenPanel2_1.setVisible(false);
			} else if (obj == datebtn2) {
				card.show(card_Top_Panel, "week");
				cenPanel2_1.setVisible(false);
			} else if (obj == datebtn3) {
				card.show(card_Top_Panel, "month");
				cenPanel2_1.setVisible(true);
			} else if (obj == sideSaveBtn || obj ==tf1) {
				if(selectedI != -1 &&selectedJ != -1) {
					JButton clicked = calbtn[selectedI][selectedJ];
					quickInsertSchedule(clicked);
				}
			} else if (obj == sideDeleteBtn) {
				if(selectedI != -1 && selectedJ != -1) {
					String tmp = dlm_schedule[selectedI][selectedJ].getElementAt(listIndex);
					JButton clicked = calbtn[selectedI][selectedJ];
					quickDeleteSchedule(clicked, tmp);
					listIndex = -1;
				}
			} else if (obj == sideDeleteAllBtn) {
				if(selectedI != -1 &&selectedJ != -1) {
					JButton clicked = calbtn[selectedI][selectedJ];
					quickDeleteAllSchedule(clicked);
				}
			} else if (obj == memoEditBtn) {
				System.out.println(listBackColor);
				new AddMemoDialog(this, 2, dlm_memo.get(listSelectedI),listBackColor);
			} else if (obj == memoDeleteBtn) {
				memoMgr.deleteMemo(listDeleteIdx);
				dlm_memo.remove(listSelectedI);
			} else if (obj == memoDeleteAllBtn) {
				memoMgr.deleteAllMemo();
				dlm_memo.removeAllElements();
				vMemoList.trimToSize();
				dlm_memo.trimToSize();
			} else {
				calbtnClicked(obj);
			}
			validate();	 
	}
	
	@Override
	public void focusGained(FocusEvent e) {
	JList list = (JList) e.getSource();		
		for (int i = 0; i < zoominNum; i++) {
			for (int j = 0; j < weekInt; j++) {
				if (list == calList[i][j]) {
					selectedI = i;
					selectedJ = j;
					calbtnClicked(calbtn[selectedI][selectedJ]);
					listIndex = calList[i][j].getSelectedIndex();
				}
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		JList OutOfFocus = (JList) e.getSource();
		OutOfFocus.clearSelection();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object obj = e.getSource();
		if (obj instanceof JComboBox) {
				JComboBox tmp = (JComboBox) obj;
			if (tmp == mapCombo) {
				mapZoom = mapCombo.getSelectedItem().toString();
				System.out.println(mapCombo.getSelectedItem().toString());
			}
			setMap(textField.getText(),mapZoom);
			textField.requestFocus();	
		}else if(obj instanceof JCheckBox){
				JCheckBox tmp = (JCheckBox) obj;
				if (tmp.isSelected()) {
					weatherOn = 2;
					currentDate();
				}
				else {
					weatherOn++;
					currentDate();

			}
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList<String> obj = (JList<String>) e.getSource();
		if(obj == memoList && !e.getValueIsAdjusting()) {
			listSelectedI = memoList.getSelectedIndex();
			for (int i =0; i<vMemoList.size();i++) {
				if(vMemoList.get(i).getContent().equals(memoList.getSelectedValue())) {
					listDeleteIdx = vMemoList.get(i).getId();
					listBackColor = vMemoList.get(i).getLabel_color();
				}
			}
		}
	}

	public static void main(String[] args) {
		cim = new CalendarInitMgr();
		scheduler_name_DB = cim.getSchedule_name();
		MainScreen main = new MainScreen();
		Alarm alarm = new Alarm(main);
		Thread thread = new Thread(alarm);
		thread.start();

	}

	
	public void setMap(String location,String mapZoom) {
		googleAPI.downloadMap(location,mapZoom);
		googleMap.setIcon(googleAPI.getMap(location));
		googleAPI.fileDelete(location);
	}
	
	/*Getter & Setter */
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getZoominNum() {
		return zoominNum;
	}

	public void setZoominNum(int zoominNum) {
		this.zoominNum = zoominNum;
	}

	public int getWeekInt() {
		return weekInt;
	}
	public void setChoiceTheme(int choiceTheme) {
		this.choiceTheme = choiceTheme;
	}
	public int getChoiceTheme() {
		return choiceTheme;
	}
	public String getScheduler_name_DB() {
		return scheduler_name_DB;
	}

	public void setScheduler_name_DB(String scheduler_name_DB) {
		this.scheduler_name_DB = scheduler_name_DB;
	}

	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}

	public static String getMapZoom() {
		return mapZoom;
	}

	public static CalendarInitMgr getCim() {
		return cim;
	}

	public static void setCim(CalendarInitMgr cim) {
		MainScreen.cim = cim;
	}

	public static void setMapZoom(String mapZoom) {
		MainScreen.mapZoom = mapZoom;
	}

	public DefaultListModel<String> getDlm_memo() {
		return dlm_memo;
	}

	public void setDlm_memo(DefaultListModel<String> dlm_memo) {
		this.dlm_memo = dlm_memo;
	}

	public MemoMgr getMemoMgr() {
		return memoMgr;
	}

	public void setMemoMgr(MemoMgr memoMgr) {
		this.memoMgr = memoMgr;
	}

	public ScheduleMgr getScheduleMgr() {
		return scheduleMgr;
	}

	public void setScheduleMgr(ScheduleMgr scheduleMgr) {
		this.scheduleMgr = scheduleMgr;	
	}
	
	public AlarmMgr getAlarmMgr() {
		return alarmMgr;
	}

	class ListMouseAdaper extends MouseAdapter{
		MainScreen main;
		public ListMouseAdaper(MainScreen owner){
			main = owner;
		}
		
		public void mouseClicked(MouseEvent e) {
			JList list = (JList) e.getSource();
			
			if(e.getClickCount()==2) {
				System.out.println("더블 클릭");
				System.out.println( "calList[selectedI][selectedJ]"+selectedI+" : "+selectedJ );
					
				int listSelectedI = calList[selectedI][selectedJ].getSelectedIndex();
				String listDate = " "+year+(month+1)+calbtn[selectedI][selectedJ].getText();
				System.out.println(listSelectedI+" "+listDate);

				Vector<ScheduleBean> vSchList = scheduleMgr.selectScheduleMonthly(year, month+1);
				ScheduleBean bean = null;
				for (int i = 0; i < vSchList.size(); i++) {
					bean = vSchList.get(i);
					if(bean.getTitle().equals(dlm_schedule[selectedI][selectedJ].getElementAt(listSelectedI))) {
						System.out.println("일정 더블클릭됨: "+bean.getTitle()+ ", "+listSelectedI);
						textField.setText(bean.getLocation());
						break;
					}
				}
				System.out.println(listSelectedI+": listSelectedI");
				System.out.println(listDate+": listDate");
				
				
		
				new AddSchedule(main, AddSchedule.EDIT, bean);
				
				list.clearSelection();
			}
		}
	}
}


