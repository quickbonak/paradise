package scheduleDialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalTime;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import layout.MainScreen;

public class Disturb extends JDialog implements ActionListener, ChangeListener{

	JLabel minute1, minute2;
	JLabel hourFrom, hourTo, minFrom, minTo;
	JLabel disStart, disEnd;
	JSlider minuteAdjust1, minuteAdjust2;
	JSpinner clock1, clock2, morning, evening;
	JCheckBox disApproval;
	String[] morningSpinnerSource = {"1","오전","오후","2"};	
	JPanel contentPane;
	JPanel disPanel;
	JButton DisturbApproval, DisturbCancel;
	ButtonGroup radioGroup;
	JRadioButton selectSizeBig, selectSizeSmall;
	LocalTime disTimeStart, disTimeEnd;
	SpinnerModel numberModel, numberModel2, morningSpinner, eveningSpinner;
	Object monSave,eveSave;
	
	public Disturb(JFrame owner) {
		setTitle("알람설정");
		setSize(500, 400);
		setLocationRelativeTo(owner);
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//방해금지 시작시간의 '분'을 조절하는 슬라이더
		minuteAdjust1 = new JSlider(0,60,0);			// 0에서 60사이의 간격에서 조정, 기본값0
		minuteAdjust1.setMinorTickSpacing(5);			// 작은눈금간격 5
		minuteAdjust1.setMajorTickSpacing(30);		// 큰 눈금 간격 30
		minuteAdjust1.setPaintTicks(true);				// 눈금을 표시함
		minuteAdjust1.setBounds(278, 110, 90, 42);
		contentPane.add(minuteAdjust1);

		//방해금지 종료시간의 '분'을 조절하는 슬라이더
		minuteAdjust2 = new JSlider(0,60,0);
		minuteAdjust2.setMinorTickSpacing(5);
		minuteAdjust2.setMajorTickSpacing(30);
		minuteAdjust2.setPaintTicks(true);
		minuteAdjust2.setBounds(278, 160, 90, 42);
		contentPane.add(minuteAdjust2);
		
		minuteAdjust1.addChangeListener(this);		//슬라이드에 리스너 add
		minuteAdjust2.addChangeListener(this);
		
		disStart = new JLabel("방해금지 시작");
		disStart.setBounds(40, 110, 82, 15);
		contentPane.add(disStart);
		
		disEnd = new JLabel("방해금지 종료");
		disEnd.setBounds(40, 160, 82, 15);
		contentPane.add(disEnd);
		
		//스피너의 레인지 지정(처음값1, 1에서, 12까지, 1씩 증가)
		//레인지가 13이 될 경우 1, 0이 될 경우 12를 반환한다.
		//스피너의 레인지 지정(오전,오후)
		numberModel = new SpinnerNumberModel(1, 0, 13, 1);
		numberModel2 = new SpinnerNumberModel(1, 0, 13, 1);
		morningSpinner = new SpinnerListModel(morningSpinnerSource);
		eveningSpinner = new SpinnerListModel(morningSpinnerSource);
		morningSpinner.setValue((Object)"오전");
		eveningSpinner.setValue((Object)"오전");
		numberModel.addChangeListener(this);
		numberModel2.addChangeListener(this);
		morningSpinner.addChangeListener(this);
		eveningSpinner.addChangeListener(this);
		
		//시작 '시'스피너
		clock1 = new JSpinner(numberModel);
		clock1.setBounds(193, 110, 39, 22);
		contentPane.add(clock1);
		
		//종료 '시'스피너
		clock2 = new JSpinner(numberModel2);
		clock2.setBounds(193, 160, 39, 22);
		contentPane.add(clock2);
		
		//오전,오후 스피너
		morning = new JSpinner(morningSpinner);
		morning.setEnabled(false);
		morning.setBounds(132, 110, 49, 22);
		contentPane.add(morning);
		
		evening = new JSpinner(eveningSpinner);
		evening.setEnabled(false);
		evening.setBounds(132, 160, 49, 22);
		contentPane.add(evening);
		
		hourFrom = new JLabel("시");
		hourFrom.setBounds(246, 110, 25, 15);
		contentPane.add(hourFrom);
		
		hourTo = new JLabel("시");
		hourTo.setBounds(246, 160, 25, 15);
		contentPane.add(hourTo);
		
		minFrom = new JLabel("분 부터");
		minFrom.setBounds(400, 110, 50, 15);
		contentPane.add(minFrom);
		
		minTo = new JLabel("분 까지");
		minTo.setBounds(400, 160, 50, 15);
		contentPane.add(minTo);
				
		minute1 = new JLabel("0");
		minute1.setBounds(385, 110, 25, 15);
		contentPane.add(minute1);
		
		minute2 = new JLabel("0");
		minute2.setBounds(385, 160, 25, 15);
		contentPane.add(minute2);
		
		//방해금지 체크박스(토글버튼처럼 사용하여 요소들을 활성화/비활성화 한다.
		disApproval = new JCheckBox("방해금지 설정",true);
		disApproval.setBounds(40, 66, 115, 23);
		contentPane.add(disApproval);
		
		disApproval.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(disApproval.isSelected())
					approvalSwitch(true);		//체크된 경우 요소들을 활성화
				else
					approvalSwitch(false);	//비활성화
			}
		});
	
		//방해금지 설정에 대한 요소들을 감싸는 패널(테두리와 라벨을 붙임)
		disPanel = new JPanel();
		disPanel.setBounds(20, 48, 440, 170);
		Border disPanelBorder = new TitledBorder(new LineBorder(Color.BLACK), "방해금지"); 
		disPanel.setBorder(disPanelBorder);
		contentPane.add(disPanel);
		
		int selectSizePosition = 250;		//알람다이얼로그의 크기, 크게, 작게 높이를 한번에 정함
		
		JLabel selectSizeLabel = new JLabel("알람 다이얼로그의 크기");
		selectSizeLabel.setBounds(40, selectSizePosition+5, 139, 15);
		contentPane.add(selectSizeLabel);
		
		selectSizeBig = new JRadioButton("크게",true);
		selectSizeBig.setBounds(250, selectSizePosition, 57, 23);
		contentPane.add(selectSizeBig);
		
		selectSizeSmall = new JRadioButton("작게");
		selectSizeSmall.setBounds(350, selectSizePosition, 57, 23);
		contentPane.add(selectSizeSmall);

		radioGroup = new ButtonGroup();
		radioGroup.add(selectSizeBig);
		radioGroup.add(selectSizeSmall);
		
		//확인버튼
		DisturbApproval = new JButton("확인");
		DisturbApproval.setBounds(250, 328, 97, 23);
		contentPane.add(DisturbApproval);
		DisturbApproval.addActionListener(this);
		
		//취소버튼
		DisturbCancel = new JButton("취소");
		DisturbCancel.setBounds(350, 328, 97, 23);
		contentPane.add(DisturbCancel);
		DisturbCancel.addActionListener(this);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		approvalSwitch(true);
		validate();

	}
	
	//방해금지 요소를 한꺼번에 활성/비활성 하는 메소드
	public void approvalSwitch(Boolean show) {
		minuteAdjust1.setEnabled(show);
		minuteAdjust2.setEnabled(show);
		clock1.setEnabled(show);
		clock2.setEnabled(show);
		hourFrom.setEnabled(show);
		hourTo.setEnabled(show);
		minFrom.setEnabled(show);
		minTo.setEnabled(show);
		disStart.setEnabled(show);
		disEnd.setEnabled(show);
		minute1.setEnabled(show);
		minute2.setEnabled(show);
		morning.setEnabled(show);
		evening.setEnabled(show);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Object obj = e.getSource();
		minute1.setText( ""+minuteAdjust1.getValue() );	//슬라이더를 감지해서 라벨에 표시.
		minute2.setText( ""+minuteAdjust2.getValue() );

		if(Integer.parseInt(numberModel.getValue().toString())>12) {	//12보다 커질 경우, 1로 표시
			numberModel.setValue(1);
		}else if(Integer.parseInt(numberModel.getValue().toString())<1) {	//1보다 작아질 경우, 12로 표시.
			numberModel.setValue(12);
		}
		if(Integer.parseInt(numberModel2.getValue().toString())>12) {
			numberModel2.setValue(1);
		}else if(Integer.parseInt(numberModel2.getValue().toString())<1) {
			numberModel2.setValue(12);
		}
		if(obj==morningSpinner) {
			if(morningSpinner.getValue().equals("1")) {	//1이 될 경우 '오후'로 표시
				morningSpinner.setValue((Object)"오후");
			}else if(morningSpinner.getValue().equals("2")){	//2가 될 경우, '오전'으로 표시
				morningSpinner.setValue((Object)"오전");
			}
		}
		if(obj==eveningSpinner) {
			if(eveningSpinner.getValue().equals("1")) {
				eveningSpinner.setValue((Object)"오후");
			}else if(eveningSpinner.getValue().equals("2")){
				eveningSpinner.setValue((Object)"오전");
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		int setClock1 = Integer.parseInt((clock1.getValue().toString()));
		int setClock2 = Integer.parseInt((clock2.getValue().toString()));
		if(obj==DisturbCancel) {
			dispose();	
		}else if(obj==DisturbApproval) {
			if(disApproval.isSelected()) {
				if(morning.getValue().equals("오후")) {
					setClock1+=12;
				}
				if(evening.getValue().equals("오후")) {
					setClock2+=12;
				}
				disTimeStart =LocalTime.of(setClock1, minuteAdjust1.getValue(), 00);
				disTimeEnd =LocalTime.of(setClock2, minuteAdjust2.getValue(), 00);
				System.out.print(morning.getValue()+"(시작)  ");
				System.out.println(evening.getValue()+"(종료)");
				System.out.print(clock1.getValue()+"시(시작)  ");
				System.out.println(clock2.getValue()+"시(종료)");
				System.out.print(minuteAdjust1.getValue()+"분(시작)  ");
				System.out.println(minuteAdjust2.getValue()+"분(종료)\n");
				System.out.println("방해금지 시작:  "+disTimeStart);
				System.out.println("방해금지 종료:  "+disTimeEnd);
			}
			if (selectSizeBig.isSelected()){
				System.out.println(selectSizeBig.isSelected());
				//MainScreen.alarmSize=true;
			}else {
				System.out.println(selectSizeBig.isSelected());
				//MainScreen.alarmSize=false;
			}
			System.out.println("알람다이얼로그를 "+/*MainScreen.alarmSize + */"로 설정함.");
			dispose();
		}
	}	
}