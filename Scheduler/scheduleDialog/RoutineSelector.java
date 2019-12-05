package scheduleDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

public class RoutineSelector extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final ButtonGroup group1 = new ButtonGroup();
	private final ButtonGroup group2 = new ButtonGroup();
	private final ButtonGroup group3 = new ButtonGroup();
	private final ButtonGroup group4 = new ButtonGroup();
	int repeatRadioSelected = 0;
	int monthRadioSelected = 0;
	int yearRadioSelected = 0;
	JCheckBox[] weekCheck = new JCheckBox[7];
	SimpleDateFormat asFormatD = new SimpleDateFormat("yyyyMMdd");
	
	LocalDate today;
	DateTimeFormatter formdd = DateTimeFormatter.ofPattern("dd");
	DateTimeFormatter formMM = DateTimeFormatter.ofPattern("MM");
	DateTimeFormatter formW = DateTimeFormatter.ofPattern("W");
	DateTimeFormatter forme = DateTimeFormatter.ofPattern("e");//달의 몇번째 주
	DateTimeFormatter formE = DateTimeFormatter.ofPattern("E");//달의 몇번째 주


	
	public RoutineSelector(AddSchedule owner) {
		setBounds(100, 100, 475, 345);
		
		Date aa = (Date)AddSchedule.asStartModel.getValue();
		today =  aa.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		
		
		
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel aaLabel1 = new JLabel("반복패턴");
		aaLabel1.setBounds(110, 15, 55, 15);
		contentPanel.add(aaLabel1);
		
		
		
		JLabel aaLabel2 = new JLabel("반복발생 범위");
		aaLabel2.setBounds(310, 30, 85, 15);
		contentPanel.add(aaLabel2);
	
	
		JSpinner repeatAfterSpinner = new JSpinner();
		repeatAfterSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		repeatAfterSpinner.setBounds(330, 135, 48, 22);
		contentPanel.add(repeatAfterSpinner);
	
	
		JLabel aaLabel3 = new JLabel("회");
		aaLabel3.setBounds(385, 130, 17, 15);
		contentPanel.add(aaLabel3);
	
	
		JSpinner untilDateSpinner = new JSpinner();
		untilDateSpinner.setModel(new SpinnerDateModel(new Date(1510153200000L), new Date(-28800000L), new Date(2240575200000L), Calendar.DAY_OF_YEAR));
		untilDateSpinner.setBounds(310, 209, 134, 22);
		contentPanel.add(untilDateSpinner);
		
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JCheckBox routineOnOffCheckBox = new JCheckBox("반복이벤트");
		buttonPane.add(routineOnOffCheckBox);
		routineOnOffCheckBox.setSelected(true);
					
					
						
								
						Panel monthlyPn = new Panel();
						monthlyPn.setBounds(20, 70, 260, 190);
						contentPanel.add(monthlyPn);
						monthlyPn.setLayout(null);
						
						JSpinner monthSpinner11month = new JSpinner();
						monthSpinner11month.setModel(new SpinnerNumberModel(1, 1, 1000, 1));
						monthSpinner11month.setBounds(35, 20, 37, 22);
						monthlyPn.add(monthSpinner11month);
						
						JLabel monthLabel11 = new JLabel("개월 마다");
						monthLabel11.setBounds(75, 23, 60, 15);
						monthlyPn.add(monthLabel11);
						
						//JSpinner monthSpinner12day = new JSpinner();
						JLabel monthSpinner12day = new JLabel(formdd.format(today));
						//monthSpinner12day.setModel(new SpinnerNumberModel(1, 1, 31, 1));
						monthSpinner12day.setBounds(136, 20, 37, 22);
						monthlyPn.add(monthSpinner12day);
						
						JLabel monthLabel122 = new JLabel("일에");
						monthLabel122.setBounds(176, 23, 37, 15);
						monthlyPn.add(monthLabel122);
						
						
							JSpinner monthSpinner2 = new JSpinner();
							monthSpinner2.setModel(new SpinnerNumberModel(1, 1, 1000, 1));
							monthSpinner2.setBounds(35, 90, 37, 22);
							monthlyPn.add(monthSpinner2);
							
							
								JLabel monthLabel2 = new JLabel("개월 마다");
								monthLabel2.setBounds(75, 93, 60, 15);
								monthlyPn.add(monthLabel2);
								
								
		//JComboBox monthComboBox21 = new JComboBox();
		JLabel monthComboBox21 = new JLabel(formW.format(today)+"번째");
		//monthComboBox21.setModel(new DefaultComboBoxModel(new String[] {"1 번째", "2 번째", "3 번째", "4 번째", "5 번째", "마지막"}));
		monthComboBox21.setBounds(45, 122, 65, 21);
		monthlyPn.add(monthComboBox21);
		
		//JComboBox monthComboBox22 = new JComboBox();
		JLabel monthComboBox22 = new JLabel(formE.format(today)+"요일에");
		//monthComboBox22.setModel(new DefaultComboBoxModel(new String[] {"일요일에", "월요일에", "화요일에", "수요일에", "목요일에", "금요일에", "토요일에"}));
		monthComboBox22.setBounds(117, 122, 80, 21);
		monthlyPn.add(monthComboBox22);
		
		
		monthLabel11.setEnabled(true);
		monthLabel122.setEnabled(true);
		monthSpinner11month.setEnabled(true);
		monthSpinner12day.setEnabled(true);
		monthSpinner2.setEnabled(false);
		monthLabel2.setEnabled(false);
		monthComboBox21.setEnabled(false);
		monthComboBox22.setEnabled(false);
		
		JRadioButton monthRadio1 = new JRadioButton("");
		monthRadio1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				monthLabel11.setEnabled(true);
				monthLabel122.setEnabled(true);
				monthSpinner11month.setEnabled(true);
				monthSpinner12day.setEnabled(true);
				monthSpinner2.setEnabled(false);
				monthLabel2.setEnabled(false);
				monthComboBox21.setEnabled(false);
				monthComboBox22.setEnabled(false);
			}
		});
		monthRadio1.setSelected(true);
		group3.add(monthRadio1);
		monthRadio1.setBounds(10, 20, 21, 21);
		monthlyPn.add(monthRadio1);
		
		
			JRadioButton monthRadio2 = new JRadioButton("");
			monthRadio2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					monthLabel11.setEnabled(false);
					monthLabel122.setEnabled(false);
					monthSpinner11month.setEnabled(false);
					monthSpinner12day.setEnabled(false);
					monthSpinner2.setEnabled(true);
					monthLabel2.setEnabled(true);
					monthComboBox21.setEnabled(true);
					monthComboBox22.setEnabled(true);
				}
			});
			group3.add(monthRadio2);
			monthRadio2.setBounds(10, 90, 21, 21);
			monthlyPn.add(monthRadio2);
			monthlyPn.setVisible(false);
					
					
					Panel dailyPn = new Panel();
					dailyPn.setBounds(20, 70, 260, 190);
					contentPanel.add(dailyPn);
					dailyPn.setLayout(null);
					
					JSpinner daySpinner = new JSpinner();
					daySpinner.setModel(new SpinnerNumberModel(1, 1, 1000, 1));
					daySpinner.setBounds(20, 20, 37, 22);
					dailyPn.add(daySpinner);
					
					JLabel dayLabel = new JLabel("일 마다");
					dayLabel.setBounds(65, 19, 56, 22);
					dailyPn.add(dayLabel);
					
					dailyPn.setVisible(true);
					
					Panel weeklyPn = new Panel();
					weeklyPn.setBounds(20, 70, 260, 190);
					contentPanel.add(weeklyPn);
					weeklyPn.setLayout(null);
					
		JSpinner weekSpinner = new JSpinner();
		weekSpinner.setModel(new SpinnerNumberModel(1, 1, 1000, 1));
		weekSpinner.setBounds(20, 20, 37, 22);
		weeklyPn.add(weekSpinner);
		
		
			JLabel weekLabel = new JLabel("주 마다");
			weekLabel.setBounds(62, 23, 57, 15);
			weeklyPn.add(weekLabel);
			
			
			weekCheck[0] = new JCheckBox("일요일");
			weekCheck[0].setBounds(20, 50, 70, 23);
			weeklyPn.add(weekCheck[0]);
			
			weekCheck[1] = new JCheckBox("월요일");
			weekCheck[1].setBounds(90, 50, 70, 23);
			weeklyPn.add(weekCheck[1]);
			
			weekCheck[2] = new JCheckBox("화요일");
			weekCheck[2].setBounds(160, 50, 70, 23);
			weeklyPn.add(weekCheck[2]);
			
			weekCheck[3] = new JCheckBox("수요일");
			weekCheck[3].setBounds(20, 80, 70, 23);
			weeklyPn.add(weekCheck[3]);
			
			weekCheck[4] = new JCheckBox("목요일");
			weekCheck[4].setBounds(90, 80, 70, 23);
			weeklyPn.add(weekCheck[4]);
			
			weekCheck[5] = new JCheckBox("금요일");
			weekCheck[5].setBounds(160, 80, 70, 23);
			weeklyPn.add(weekCheck[5]);
			
			weekCheck[6] = new JCheckBox("토요일");
			weekCheck[6].setBounds(20, 110, 70, 23);
			weeklyPn.add(weekCheck[6]);
			weeklyPn.setVisible(false);
			
			
				
			
			
				
				Panel yearlyPn = new Panel();
				yearlyPn.setBounds(20, 70, 260, 190);
				contentPanel.add(yearlyPn);
				yearlyPn.setLayout(null);
				
				JSpinner yearSpinner11year = new JSpinner();
				yearSpinner11year.setModel(new SpinnerNumberModel(1, 1, 100, 1));
				yearSpinner11year.setBounds(35, 20, 37, 22);
				yearlyPn.add(yearSpinner11year);
				
				JLabel yearLabel11 = new JLabel("년 마다");
				yearLabel11.setBounds(76, 23, 48, 15);
				yearlyPn.add(yearLabel11);
				
				JLabel yearSpinner12month = new JLabel(formMM.format(today));
				yearSpinner12month.setBounds(122, 20, 35, 22);
				yearlyPn.add(yearSpinner12month);
				
				JLabel yearLabel12 = new JLabel("월");
				yearLabel12.setBounds(162, 23, 21, 15);
				yearlyPn.add(yearLabel12);
				
				JLabel yearSpinner13day = new JLabel(formdd.format(today));
				yearSpinner13day.setBounds(180, 20, 35, 22);
				yearlyPn.add(yearSpinner13day);
				
				JLabel yearLabel13 = new JLabel("일에");
				yearLabel13.setBounds(220, 23, 38, 15);
				yearlyPn.add(yearLabel13);
				
				JSpinner yearSpinner21year = new JSpinner();
				yearSpinner11year.setModel(new SpinnerNumberModel(1, 1, 100, 1));
				yearSpinner21year.setBounds(35, 90, 35, 22);
				yearlyPn.add(yearSpinner21year);
				
				JLabel yearLabel21 = new JLabel("년 마다");
				yearLabel21.setBounds(76, 93, 48, 15);
				yearlyPn.add(yearLabel21);
				
				JLabel yearSpinner22month = new JLabel(formMM.format(today));
				yearSpinner22month.setBounds(122, 90, 35, 22);
				yearlyPn.add(yearSpinner22month);
				
				JLabel yearLabel22 = new JLabel("월");
				yearLabel22.setBounds(162, 93, 21, 15);
				yearlyPn.add(yearLabel22);
				
				//JComboBox yearCombo1th = new JComboBox();
				JLabel yearCombo1th = new JLabel(formW.format(today)+"번째");
				//yearCombo1th.setModel(new DefaultComboBoxModel(new String[] {"1번째", "2번째", "3번째", "4번째", "5번째", "마지막"}));
				yearCombo1th.setBounds(45, 122, 65, 21);
				yearlyPn.add(yearCombo1th);
				
				//JComboBox yearCombo2day = new JComboBox();
				JLabel yearCombo2day = new JLabel(formE.format(today)+"요일에");
				//yearCombo2day.setModel(new DefaultComboBoxModel(new String[] {"일요일에", "월요일에", "화요일에", "수요일에", "목요일에", "금요일에", "토요일에"}));
				yearCombo2day.setBounds(122, 122, 80, 21);
				yearlyPn.add(yearCombo2day);
				
				yearSpinner11year.setEnabled(true);
				yearSpinner12month.setEnabled(true);
				yearSpinner13day.setEnabled(true);
				yearLabel11.setEnabled(true);
				yearLabel12.setEnabled(true);
				yearLabel13.setEnabled(true);
				yearSpinner21year.setEnabled(false);
				yearSpinner22month.setEnabled(false);
				yearLabel21.setEnabled(false);
				yearLabel22.setEnabled(false);
				yearCombo1th.setEnabled(false);
				yearCombo2day.setEnabled(false);
				
				JRadioButton yearRadio1 = new JRadioButton("");
				yearRadio1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						yearSpinner11year.setEnabled(true);
						yearSpinner12month.setEnabled(true);
						yearSpinner13day.setEnabled(true);
						yearLabel11.setEnabled(true);
						yearLabel12.setEnabled(true);
						yearLabel13.setEnabled(true);
						yearSpinner21year.setEnabled(false);
						yearSpinner22month.setEnabled(false);
						yearLabel21.setEnabled(false);
						yearLabel22.setEnabled(false);
						yearCombo1th.setEnabled(false);
						yearCombo2day.setEnabled(false);
					}
				});
				yearRadio1.setSelected(true);
				group4.add(yearRadio1);
				yearRadio1.setBounds(10, 20, 21, 21);
				yearlyPn.add(yearRadio1);
				
				JRadioButton yearRadio2 = new JRadioButton("");
				yearRadio2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						yearSpinner11year.setEnabled(false);
						yearSpinner12month.setEnabled(false);
						yearSpinner13day.setEnabled(false);
						yearLabel11.setEnabled(false);
						yearLabel12.setEnabled(false);
						yearLabel13.setEnabled(false);
						yearSpinner21year.setEnabled(true);
						yearSpinner22month.setEnabled(true);
						yearLabel21.setEnabled(true);
						yearLabel22.setEnabled(true);
						yearCombo1th.setEnabled(true);
						yearCombo2day.setEnabled(true);						
					}
				});
				group4.add(yearRadio2);
				yearRadio2.setBounds(10, 90, 21, 21);
				yearlyPn.add(yearRadio2);
				yearlyPn.setVisible(false);
		
		JRadioButton dailyRadio = new JRadioButton("매일");
		dailyRadio.setSelected(true);
		dailyRadio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dailyPn.setVisible(true);
				weeklyPn.setVisible(false);
				monthlyPn.setVisible(false);
				yearlyPn.setVisible(false);
			}
		});
		group1.add(dailyRadio);
		dailyRadio.setBounds(20, 40, 55, 25);
		contentPanel.add(dailyRadio);
			
			
		JRadioButton weeklyRadio = new JRadioButton("매주");
		weeklyRadio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dailyPn.setVisible(false);
				weeklyPn.setVisible(true);
				monthlyPn.setVisible(false);
				yearlyPn.setVisible(false);
				int a = Integer.parseInt(forme.format(today))-1;
				weekCheck[a].setEnabled(false);
				weekCheck[a].setSelected(true);		
			}
			});
		group1.add(weeklyRadio);
		weeklyRadio.setBounds(80, 40, 55, 25);
		contentPanel.add(weeklyRadio);
		
			
		JRadioButton monthlyRadio = new JRadioButton("매달");
		monthlyRadio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dailyPn.setVisible(false);
				weeklyPn.setVisible(false);
				monthlyPn.setVisible(true);
				yearlyPn.setVisible(false);
			}
		});
		group1.add(monthlyRadio);
		monthlyRadio.setBounds(140, 40, 55, 25);
		contentPanel.add(monthlyRadio);
						
		JRadioButton yearlyRadio = new JRadioButton("매년");
		yearlyRadio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dailyPn.setVisible(false);
				weeklyPn.setVisible(false);
				monthlyPn.setVisible(false);
				yearlyPn.setVisible(true);
			}
		});
		group1.add(yearlyRadio);
		yearlyRadio.setBounds(200, 40, 55, 25);
		contentPanel.add(yearlyRadio);
		
		
		Panel rectLinePn = new Panel();
		rectLinePn.setBackground(Color.LIGHT_GRAY);
		rectLinePn.setBounds(19, 69, 262, 192);
		contentPanel.add(rectLinePn);
		

		repeatAfterSpinner.setEnabled(false);
		untilDateSpinner.setEnabled(false);
				
				JRadioButton noEndDateRadio = new JRadioButton("종료일 없음");
				noEndDateRadio.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						repeatAfterSpinner.setEnabled(false);
						untilDateSpinner.setEnabled(false);
					}
				});
				noEndDateRadio.setSelected(true);
				group2.add(noEndDateRadio);
				noEndDateRadio.setBounds(300, 60, 100, 23);
				contentPanel.add(noEndDateRadio);
				
				
					JRadioButton untilDateRadio = new JRadioButton("다음 까지 실행");
					untilDateRadio.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							repeatAfterSpinner.setEnabled(false);
							untilDateSpinner.setEnabled(true);
						}
					});
					untilDateRadio.setBounds(300, 180, 120, 23);
					contentPanel.add(untilDateRadio);
					group2.add(untilDateRadio);
			
			
				JRadioButton repeatAfterRadio = new JRadioButton("횟수 뒤 종료");
				repeatAfterRadio.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						repeatAfterSpinner.setEnabled(true);
						untilDateSpinner.setEnabled(false);
					}
				});
				group2.add(repeatAfterRadio);
				repeatAfterRadio.setBounds(300, 105, 100, 23);
				contentPanel.add(repeatAfterRadio);
		
		
		
				
				
				
		///// 출력문에 선택된 라디오 버튼부터, 세부사항까지 전부 전달되도록 만들었습니다.
		///// 고로, 아래의 부분부터 양식에 맞게 손본 뒤에 데이터를 전달하면 큰 문제가 없을 것입니다.
		JButton confirmButton = new JButton("확인");      //////////////  <<== 여기부터 프로그래밍 하세요!
		confirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
					if(dailyRadio.isSelected()==true) {
						changeCheck();
						AddSchedule.selectStep = 1;
						AddSchedule.dat = Integer.parseInt(daySpinner.getValue().toString());
					}else if(weeklyRadio.isSelected() == true) {
						changeCheck();
						AddSchedule.selectStep = 2;
						AddSchedule.dat = Integer.parseInt(weekSpinner.getValue().toString());
						for (int i = 0; i < AddSchedule.week.length; i++) {
							AddSchedule.week[i] = weekCheck[i].isSelected();
						}
					}else if(monthlyRadio.isSelected() == true) {
						if(monthRadio1.isSelected()==true) {
							changeCheck();
							AddSchedule.selectStep = 3;
							AddSchedule.dat = Integer.parseInt(monthSpinner11month.getValue().toString());
		
						}else if(monthRadio2.isSelected() == true) {
							changeCheck();
							AddSchedule.selectStep = 4;
							AddSchedule.dat = Integer.parseInt(monthSpinner2.getValue().toString());
							int a = Integer.parseInt(forme.format(today))-1;
							AddSchedule.week[a]=true;
					}
					}else if(yearlyRadio.isSelected() == true) {
						if(yearRadio1.isSelected()==true) {
							changeCheck();
							AddSchedule.selectStep = 5;
							AddSchedule.dat = Integer.parseInt(yearSpinner11year.getValue().toString());
					}else if(yearRadio2.isSelected()==true) {
							changeCheck();
							AddSchedule.selectStep = 6;
							AddSchedule.dat = Integer.parseInt(yearSpinner21year.getValue().toString());
							int a = Integer.parseInt(forme.format(today))-1;
							AddSchedule.week[a]=true;
					}						
					}
					
					//우측 선택지
					if(noEndDateRadio.isSelected() == true) {
						changeCheck2();
				}else if(repeatAfterRadio.isSelected() == true) {
						changeCheck2();
						AddSchedule.repeatDat = Integer.parseInt(repeatAfterSpinner.getValue().toString());
				}else if(untilDateRadio.isSelected() == true) {
						changeCheck2();
						AddSchedule.untilDat = Integer.parseInt(asFormatD.format((Date)untilDateSpinner.getValue()));
			}
/*					System.out.println("케이스: " + AddSchedule.selectStep);
					System.out.println("데이터: " + AddSchedule.dat);
					System.out.print("일요일 부터");
					for (int i = 0; i < AddSchedule.week.length; i++) {
						System.out.print(" "+AddSchedule.week[i]);
					}
					System.out.println("\n반복횟수" +AddSchedule.repeatDat);
					System.out.println("반복기간" + AddSchedule.untilDat);*/
					
					dispose();

				}
		});
		confirmButton.setActionCommand("OK");
		buttonPane.add(confirmButton);
		getRootPane().setDefaultButton(confirmButton);

		JButton cancelButton = new JButton("취소");
		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		
		changeCheck();
		changeCheck2();
		
		setModal(true);
		setVisible(true);
		
	}
	
	public void changeCheck() {
		AddSchedule.dat = 0;
		for (int i = 0; i < 7; i++) {
			weekCheck[i].setEnabled(true);
			AddSchedule.week[i]=false;
		}
	}
	public void changeCheck2() {
		AddSchedule.repeatDat = 0;
		AddSchedule.untilDat = 20410101;
	}
	
	
}
