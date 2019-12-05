package layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import connectDB.ScheduleBean;
import connectDB.ScheduleMgr;
import scheduleDialog.Theme_set;

class Card_Daily extends JPanel implements ActionListener {
	
	JPanel dailyPanel = new JPanel();
	JPanel dailyPanelFirst = new JPanel();	//일정표0번이 들어가는 패널
	JPanel dailyPanelNorth = new JPanel();	//버튼 3개가 들어가는 패널
	JLabel[] dailyLabel = new JLabel[25];
	String[] dailyLabelText = {"오늘 일정", "오전 0시", "오전 1시", "오전 2시", "오전 3시",	"오전 4시", "오전 5시","오전 6시","오전 7시",
			"오전 8시","오전 9시","오전 10시","오전 11시","오후 12시","오후 1시", "오후 2시","오후3시",
			"오후 4시", "오후 5시", "오후 6시", "오후 7시", "오후 8시", "오후 9시", "오후 10시", "오후 11시"};
	String[] moniText = {"낮","밤"};
	JList[] dailyTA = new JList[25];
	JScrollPane[] jsp = new JScrollPane[25];
	DefaultListModel[] dailyTAmodel = new DefaultListModel[25];
	JButton 	dailyTitle,dailyLeft, dailyRight;
	Boolean dailyTitleToggle = true;		//dailyTitle을 토글처럼 사용하기 위해 설정한 변수. true낮false밤
	LocalDate today;
	Date today2;
	//Date today2 = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
	GridBagLayout dailyGblPanel;
	GridBagConstraints gbc;		//gridBagConstraints의 좌표를 저장하는 변수. First는 가장 위쪽패널 전용
	int clock, clock2;		//패널에 뿌릴 시간대를 정하는 변수
	int choiceTheme;
	
	MainScreen mainScreen;
	int year, month;
	
	String dbTitle;
	Date dbTime1 , dbTime2;
	DateFormat formDbTime  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s");
	SimpleDateFormat formToday = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat formBetween = new SimpleDateFormat("HH");

	public Card_Daily(MainScreen main) {
		mainScreen = main;
		year = mainScreen.getYear();
		month = mainScreen.getMonth()+1;
		choiceTheme = mainScreen.getChoiceTheme();
		setLayout(new BorderLayout(0, 0));
		today = LocalDate.now();				//오늘 날짜 get
		dailyTitle = new JButton();			//날짜, 낮,밤을 표시할 버튼(상단라벨)
		dailyLeft = new JButton("<-");
		dailyRight = new JButton("->");
		dailyTitle.setBackground(Color.decode(Theme_set.setColor[choiceTheme][0]));
		dailyTitle.setFont(new Font("굴림", Font.BOLD, 22));					//버튼(상단 라벨) 크기
		dailyLeft.setHorizontalAlignment(SwingConstants.LEFT);
		dailyTitle.setHorizontalAlignment(SwingConstants.CENTER);
		dailyRight.setHorizontalAlignment(SwingConstants.RIGHT);
		add(dailyPanelNorth, BorderLayout.NORTH);
		dailyPanelNorth.add(dailyLeft);
		dailyPanelNorth.add(dailyTitle);
		dailyPanelNorth.add(dailyRight);
		dailyPanelNorth.setBackground(Color.decode(Theme_set.setColor[choiceTheme][1]));
	
		dailyGblPanel = new GridBagLayout();
		dailyGblPanel.columnWidths = new int[] {0, 0, 2};
		dailyGblPanel.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2};
		dailyGblPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		dailyGblPanel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		dailyPanel.setLayout(dailyGblPanel);
		dailyPanelFirst.setLayout(new BorderLayout());
		dailyPanel.setBackground(Color.decode(Theme_set.setColor[choiceTheme][0]));

		dailyTitle.addActionListener(this);
		dailyLeft.addActionListener(this);
		dailyRight.addActionListener(this);
		
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		//첫줄은 패널을 따로 만들어서, 낮,밤이 바뀌어도 지워지지 않게 처리함.
		dailyLabel[0] = new JLabel(dailyLabelText[0]);
		
		dailyTA[0] = new JList();
		dailyTAmodel[0] = new DefaultListModel();
		dailyPanelFirst.add(BorderLayout.WEST,dailyLabel[0]);
		dailyPanelFirst.add(BorderLayout.CENTER,dailyTA[0]);

		dailyPanelFirst.setBackground(Color.decode(Theme_set.setColor[choiceTheme][0]));
		for (int i = 0; i < dailyLabel.length; i++) {
			dailyTAmodel[i] = new DefaultListModel();
		}

		scheduleMgr = main.getScheduleMgr();
		
		moni(dailyTitleToggle);

	}
	
	ScheduleMgr scheduleMgr;
	SimpleDateFormat asFormatS = new SimpleDateFormat("yyyyMMddHHmm");
	long[] today3 = new long[25];
	Date today4, today5;
	
	  public Date asDate(LocalDate localDate) {
		    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		  }
	
	public void loadSchedule(LocalDate today,int index) {
		Vector<ScheduleBean> vScheList = scheduleMgr.selectScheduleDaily(today);
		today2 = asDate(today);
		
		dailyLabel[clock] = new JLabel(dailyLabelText[clock]);

		gbc.gridx = 0;				//열
		gbc.gridy = index;			//행
		dailyPanel.add(dailyLabel[clock], gbc);
		
		dailyTA[clock] = new JList(dailyTAmodel[clock]);
		 jsp[clock] = new JScrollPane(dailyTA[clock]);
		dailyTA[clock].setBackground(Color.decode(Theme_set.setColor[choiceTheme][0]));
		gbc.gridx = 1;
		dailyPanel.add(jsp[clock], gbc);
		
		for (int i = 0; i < vScheList.size(); i++) {
			ScheduleBean	sb = vScheList.get(i);
			dbTitle = sb.getTitle();
			try {
				dbTime1 = formDbTime.parse(sb.getTime_from());	//레코드의 일정 시작시간
				dbTime2 = formDbTime.parse(sb.getTime_to());		//일정 종료시간
			} catch (ParseException e) {
				e.printStackTrace();
			}
			for (int j = 0; j < today3.length; j++) {
				int jj=j*100;
				today3[j] =Long.valueOf(formToday.format(today2))*10000+jj;
			}

			try {
				today4 = asFormatS.parse(String.valueOf(today3[clock-1]));
				today5 = asFormatS.parse(String.valueOf(today3[clock]));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			boolean b1 = between(dbTime1,today4,today5);
			boolean b2 = between(dbTime2,today4,today5);

			if(b1) {
				dailyTAmodel[clock].addElement(dbTitle+" 시작");
			}
			if(b2) {
				dailyTAmodel[clock].addElement(dbTitle+" 종료");
			}

		}
	}
	
	public boolean between(Date date, Date dateStart, Date dateEnd) {
	    if (date != null && dateStart != null && dateEnd != null) {
	        if (date.after(dateStart) && date.before(dateEnd)) {
	            return true;
	        }
	        else {
	            return false;
	        }
	    }
	    return false;
	}
	
/*	public void appendRecord() {
		
		loadSchedule(today);

		
	}*/
	
	public void setFirstLine() {
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridwidth=2;
		dailyPanel.add(dailyPanelFirst,gbc);
		gbc.gridwidth=1;
	}

	//패널에 낮,밤 시간별 일정을 뿌려준다.
	public void moni(boolean pour) {		

		int monimoni = pour ? 0:1;					//moniText에 붙일 변수(낮,밤)
		
		dailyTitle.setText(today.toString()+" "+moniText[monimoni]);
		dailyPanel.removeAll();
		remove(dailyPanel);
		add(dailyPanel, BorderLayout.CENTER);
		for (int i = 0; i < 25; i++) {
			dailyTAmodel[i].removeAllElements();
		}

		
		setFirstLine();

		clock = pour ? 1:13;		//true면 1부터, false면 13부터
		clock2 = clock+12;
		dailyTitleToggle  = pour ? false: true;		//true면 false, false면 true

		for (int i=1; i < 13; i++) {
			loadSchedule(today, i);
			clock++;
		}

		validate();
	}

	public void change_value(int choiceTheme) {
		this.choiceTheme = choiceTheme;
		changeTheme();
	}

	public void changeTheme() {
		dailyTitle.setBackground(Color.decode(Theme_set.setColor[choiceTheme][0]));
		dailyPanel.setBackground(Color.decode(Theme_set.setColor[choiceTheme][0]));
		for (int i = 0; i < clock2; i++) {
			dailyTA[i].setBackground(Color.decode(Theme_set.setColor[choiceTheme][0]));
		}
		dailyPanelFirst.setBackground(Color.decode(Theme_set.setColor[choiceTheme][0]));
		validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj.equals(dailyTitle)) {		//오늘날짜버튼
			moni(dailyTitleToggle);
		}if(obj.equals(dailyLeft)){		//왼쪽 버튼
			today = today.minusDays(1);

			moni(!(dailyTitleToggle));

		}if(obj.equals(dailyRight)){		//오른쪽 버튼
			today = today.plusDays(1);
			moni(!(dailyTitleToggle));
			
		}
	}
}