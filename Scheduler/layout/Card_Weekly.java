package layout;

import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

public class Card_Weekly extends JPanel implements ActionListener{
	
	JPanel weeklyPanel = new JPanel();
	JPanel weeklyPanelFirst = new JPanel();	//일정표0번이 들어가는 패널
	JPanel weeklyPanelNorth = new JPanel();	//버튼 3개가 들어가는 패널
	JLabel[] weeklyLabel = new JLabel[25];
	String[] weeklyLabelText = {"", "오전 6시","오전 7시","오전 8시","오전 9시","오전 10시","오전 11시","오후 12시","오후 1시",
			"오후 2시","오후3시",	"오후 4시", "오후 5시", "오후 6시", "오후 7시", "오후 8시", "오후 9시", "오후 10시", "오후 11시", 
			"오전 0시", "오전 1시", "오전 2시", "오전 3시","오전 4시", "오전 5시"};
	String[] moniText = {"낮","밤"};
	JList[][] weeklyTA = new JList[7][25];
	JScrollPane[][] jsp = new JScrollPane[7][25];
	DefaultListModel<String>[][] weeklyTAmodel = new DefaultListModel[7][25]; 
	JButton 	weeklyTitle,weeklyLeft, weeklyRight;
	Boolean weeklyTitleToggle = true;		//weeklyTitle을 토글처럼 사용하기 위해 설정한 변수. true낮false밤
	LocalDate today;
	DateTimeFormatter weeklyFormat = DateTimeFormatter.ofPattern("yy년 MM월 W주");
	DateTimeFormatter weeklyFormat2 = DateTimeFormatter.ofPattern("MM월 dd일 E요일");
	GridBagLayout weeklyGblPanel;
	GridBagConstraints gbc;		//gridBagConstraints의 좌표를 저장하는 변수. First는 가장 위쪽패널 전용
	int clock, clock2;		//패널에 뿌릴 시간대를 정하는 변수
	int choiceTheme;
	
	MainScreen mainScreen;
	int year, month;
	JButton[] weekbtn = new JButton[7];

	public Card_Weekly(MainScreen main) {
		mainScreen = main;
		year = mainScreen.getYear();
		month = mainScreen.getMonth()+1;
		choiceTheme = main.getChoiceTheme();
		scheduleMgr = mainScreen.getScheduleMgr();
		
		setLayout(new BorderLayout(0, 0));
		today = LocalDate.now().with(previousOrSame(SUNDAY));				//이번주 일요일 날짜 get
		weeklyTitle = new JButton();//날짜, 낮,밤을 표시할 버튼(상단라벨)
		weeklyLeft = new JButton("<-");
		weeklyRight = new JButton("->");
		weeklyTitle.setBackground(Color.decode(Theme_set.setColor[choiceTheme][0]));
		weeklyTitle.setFont(new Font("굴림", Font.BOLD, 22));					//버튼(상단 라벨) 크기
		weeklyLeft.setHorizontalAlignment(SwingConstants.LEFT);
		weeklyTitle.setHorizontalAlignment(SwingConstants.CENTER);
		weeklyRight.setHorizontalAlignment(SwingConstants.RIGHT);
		add(weeklyPanelNorth, BorderLayout.NORTH);
		weeklyPanelNorth.add(weeklyLeft);
		weeklyPanelNorth.add(weeklyTitle);
		weeklyPanelNorth.add(weeklyRight);
		weeklyPanelNorth.setBackground(Color.decode(Theme_set.setColor[choiceTheme][1]));

		weeklyGblPanel = new GridBagLayout();
		weeklyGblPanel.columnWidths = new int[] {0, 0, 2};
		weeklyGblPanel.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		weeklyGblPanel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		weeklyGblPanel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		weeklyPanel.setLayout(weeklyGblPanel);
		weeklyPanelFirst.setLayout(new GridLayout(1,7));
		weeklyPanel.setBackground(Color.decode(Theme_set.setColor[choiceTheme][0]));

		weeklyTitle.addActionListener(this);
		weeklyLeft.addActionListener(this);
		weeklyRight.addActionListener(this);
		
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(1, 1, 1, 1);
		
		//첫줄은 패널을 따로 만들어서, 낮,밤이 바뀌어도 지워지지 않게 처리함.
		weeklyLabel[0] = new JLabel(weeklyLabelText[0]);

		for (int i = 0; i < 7; i++) {
			weekbtn[i] = new JButton();
			weekbtn[i].setBorderPainted(false);
			weekbtn[i].setOpaque(true);
			weekbtn[i].setFocusable(false);
			weekbtn[i].setHorizontalAlignment(SwingConstants.LEFT);
			weekbtn[i].setBackground(Color.decode(Theme_set.setColor[choiceTheme][2]));			
			weekbtn[i].setForeground(Color.decode(Theme_set.setColor[choiceTheme][8]));			
			weeklyPanelFirst.add(weekbtn[i]);
		}
			weekbtn[0].setBackground(Color.decode(Theme_set.setColor[choiceTheme][4]));
			weekbtn[0].setForeground(Color.decode(Theme_set.setColor[choiceTheme][9]));
			weekbtn[6].setBackground(Color.decode(Theme_set.setColor[choiceTheme][3]));
			weekbtn[6].setForeground(Color.decode(Theme_set.setColor[choiceTheme][10]));
			
			for(int k = 0 ; k <7 ; k++) {
				for (int i = 0; i < 25; i++) {
					weeklyTAmodel[k][i] = new DefaultListModel();
					jsp[k][i] = new JScrollPane();
				}
			}
			
		moni(weeklyTitleToggle);
	
	}
	public int getChoiceTheme() {
		return choiceTheme;
	}

	public void setChoiceTheme(int choiceTheme) {
		this.choiceTheme = choiceTheme;
	}

	String dbTitle;
	Date dbTime1 , dbTime2;
	DateFormat formDbTime  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s");
	SimpleDateFormat formToday = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat formBetween = new SimpleDateFormat("HH");
	ScheduleMgr scheduleMgr;
	SimpleDateFormat asFormatS = new SimpleDateFormat("yyyyMMddHHmm");
	long[] today3 = new long[25];
	Date today2, today4, today5;
	
	
	public Date asDate(LocalDate localDate) {
		    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public void loadSchedule(LocalDate today,int index) {

		weeklyLabel[clock] = new JLabel(weeklyLabelText[clock]);

		gbc.gridx = 0;				//열
		gbc.gridy = index;			//행
		weeklyPanel.add(weeklyLabel[clock], gbc);
		for (int k = 0; k < 7; k++) {
			LocalDate today6 = today.plusDays(k);
			today2 = asDate(today6);
			Vector<ScheduleBean> vSchList = scheduleMgr.selectScheduleDaily(today6);
			weeklyTA[k][clock] = new JList(weeklyTAmodel[k][clock]);
			weeklyTA[k][clock].setBackground(Color.decode(Theme_set.setColor[choiceTheme][0]));
			gbc.gridx = 1+k;
			jsp[k][clock] = new JScrollPane(weeklyTA[k][clock]);
			weeklyPanel.add(jsp[k][clock], gbc);
			
			for (int i = 0; i < vSchList.size(); i++) {
				ScheduleBean	sb = vSchList.get(i);
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
					weeklyTAmodel[k][clock].addElement(dbTitle+" 시작");
					//System.out.println(k+dbTitle);
				}
				if(b2) {
					weeklyTAmodel[k][clock].addElement(dbTitle+" 종료");
					//System.out.println(k+dbTitle);
				}
			}
		}
	}
	
	public boolean between(Date date, Date dateStart, Date dateEnd) {
	    if (date != null && dateStart != null && dateEnd != null) {
	        if (date.after(dateStart) && date.before(dateEnd)) 
	            return true;
	        else 
	            return false;        
	    }
	    return false;
	}
	
	public void setFirstLine() {
		gbc.gridx=0;
		gbc.gridy=0;
		weeklyPanel.add(weeklyPanelFirst,gbc);
		weeklyPanel.add(weeklyLabel[0],gbc);
		gbc.gridx=1;
		gbc.gridwidth = 7;
		weeklyPanel.add(weeklyPanelFirst,gbc);
		gbc.gridwidth = 1;
	}

	//패널에 낮,밤 시간별 일정을 뿌려준다.
	public void moni(boolean pour) {		
		int monimoni = pour ? 0:1;					//moniText에 붙일 변수(낮,밤)
		
		weeklyTitle.setText(weeklyFormat.format(today)+" "+moniText[monimoni]);
		weeklyPanel.removeAll();
		remove(weeklyPanel);
		add(weeklyPanel, BorderLayout.CENTER);
		for(int k = 0 ; k <7 ; k++) {
			for (int i = 0; i < 25; i++) {
				weeklyTAmodel[k][i].removeAllElements();
			}
		}
		
		setFirstLine();
		clock = pour ? 1:13;		//true면 1부터, false면 13부터
		clock2 = clock+ 12;
		weeklyTitleToggle  = pour ? false: true;		//true면 false, false면 true
		
		for (int i = 0; i < 7; i++) {
			weekbtn[i].setText(weeklyFormat2.format(today.plusDays(i)));
		}

		for (int i=1; i < 13; i++) {
			loadSchedule(today,i);
			clock++;
		}
		validate();
	}

	public void change_value(int choiceTheme) {
		this.choiceTheme = choiceTheme;
		changeTheme();
	}

	public void changeTheme() {
		weeklyTitle.setBackground(Color.decode(Theme_set.setColor[choiceTheme][0]));
		weeklyPanel.setBackground(Color.decode(Theme_set.setColor[choiceTheme][0]));
	/*	for (int i = 1; i < clock2; i++) {
			for (int j = 1; j <6; j++) {
				weeklyTA[j][i].setBackground(Color.decode(Theme_set.setColor[choiceTheme][2]));				
			}
			weeklyTA[0][i].setBackground(Color.decode(Theme_set.setColor[choiceTheme][4]));	
			weeklyTA[6][i].setBackground(Color.decode(Theme_set.setColor[choiceTheme][3]));	
		}*/
		for (int i = 1; i < 6; i++) {
			weekbtn[i].setBackground(Color.decode(Theme_set.setColor[choiceTheme][2]));			
			weekbtn[i].setForeground(Color.decode(Theme_set.setColor[choiceTheme][8]));			
		}
		weekbtn[0].setBackground(Color.decode(Theme_set.setColor[choiceTheme][4]));
		weekbtn[0].setForeground(Color.decode(Theme_set.setColor[choiceTheme][9]));
		weekbtn[6].setBackground(Color.decode(Theme_set.setColor[choiceTheme][3]));
		weekbtn[6].setForeground(Color.decode(Theme_set.setColor[choiceTheme][10]));
		
		validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj.equals(weeklyTitle)) {
			moni(weeklyTitleToggle);
		}if(obj.equals(weeklyLeft)){
			today = today.minusWeeks(1);
			moni(!(weeklyTitleToggle));
		}if(obj.equals(weeklyRight)){
			today = today.plusWeeks(1);
			moni(!(weeklyTitleToggle));
		}
	}
}
