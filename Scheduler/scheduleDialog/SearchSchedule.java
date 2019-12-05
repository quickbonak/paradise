package scheduleDialog;

import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.next;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Vector;

import javax.naming.directory.SearchResult;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import connectDB.ScheduleBean;
import connectDB.ScheduleMgr;
import layout.MainScreen;

public class SearchSchedule extends JDialog implements ActionListener, ItemListener, TemporalAdjuster, FocusListener{
	
	JButton searchApproval, searchCancel;
	JComboBox selectPeriod , selectColumm;

	private final JPanel contentPanel = new JPanel();
	private JLabel lblNewLabel;
	private JTextField searchField, inputPeriod, inputPeriod2;
	LocalDate today = LocalDate.now();
	
	String[] dummy1 = {"모두 검색","제목","위치","내용"};
	String[] dummy2 = {"1주일","1개월","1분기","1년","5년","이번주","이번달","올해"};
	String[] toolTipText = {"일정 제목", "일정 내용", "일정 시작시간", "일정 종료시간", "위치"};
	JTextField searchText;
	
	
	//JTextArea searchResult;
	JList[] searchResult;
	DefaultListModel<String>[] dlm_search;
	ScheduleMgr scheduleMgr;
	public SearchSchedule(MainScreen owner) {
		setSize(532, 550);
		setLocationRelativeTo(owner);
		setTitle("일정 검색");
		setVisible(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		scheduleMgr = owner.getScheduleMgr();
		
		
		JPanel listPanel = new JPanel(new GridLayout(1, 5));
		
		searchResult = new JList[5];
		dlm_search = new DefaultListModel[5];
		
		
		//searchResult = new JTextArea();
		for(int i = 0; i<5 ; i++) {
			dlm_search[i] = new DefaultListModel<String>(); 
			searchResult[i] = new JList<String>(dlm_search[i]);
			searchResult[i].setToolTipText(toolTipText[i]);
			searchResult[i].addFocusListener(this);
			listPanel.add(searchResult[i]);
		}
		searchResult[0].setFixedCellWidth(10);
	
		//searchResult.setFocusable(false);
		JScrollPane resultScroll = new JScrollPane();
		resultScroll.setBounds(20, 150, 476, 310);
		contentPanel.add(resultScroll);
		resultScroll.setViewportView(listPanel);
	
	
		lblNewLabel = new JLabel("이 문장으로 찾기");
		lblNewLabel.setBounds(20, 20, 99, 15);
		contentPanel.add(lblNewLabel);
		
		JLabel label = new JLabel("이 분야에서");
		label.setBounds(20, 60, 88, 15);
		contentPanel.add(label);
		
		JLabel label_1 = new JLabel("이 기간으로");
		label_1.setBounds(20, 105, 88, 15);
		contentPanel.add(label_1);
		
		searchField = new JTextField();
		searchField.setBounds(131, 20, 369, 21);
		contentPanel.add(searchField);
		searchField.setColumns(10);
		
		selectColumm = new JComboBox<String>(dummy1);
		selectColumm.setBounds(129, 60, 100, 21);
		contentPanel.add(selectColumm);
		selectColumm.addItemListener(this);
		
		selectPeriod = new JComboBox<String>(dummy2);
		selectPeriod.setBounds(128, 100, 100, 21);
		contentPanel.add(selectPeriod);
		selectPeriod.addItemListener(this);
		
		//기간 시작(텍스트필드)
		inputPeriod = new JTextField(today.toString());
		inputPeriod.setBounds(261, 100, 88, 21);
		contentPanel.add(inputPeriod);
		inputPeriod.setColumns(10);
		
		//기간 끝(텍스트 필드)
		inputPeriod2 = new JTextField(today.plusDays(7).toString());
		inputPeriod2.setBounds(412, 100, 88, 21);
		contentPanel.add(inputPeriod2);
		
		JLabel periodLabel = new JLabel("에서");
		periodLabel.setBounds(361, 105, 45, 15);
		contentPanel.add(periodLabel);

		//확인,취소버튼
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		searchApproval = new JButton("검색");
		searchApproval.setActionCommand("OK");
		buttonPane.add(searchApproval);
		getRootPane().setDefaultButton(searchApproval);
		searchApproval.addActionListener(this);

		searchCancel = new JButton("취소");
		searchCancel.setActionCommand("Cancel");
		buttonPane.add(searchCancel);
		searchCancel.addActionListener(this);
		
		searchText = new JTextField("Search");
		searchText.setForeground(Color.GRAY);
		searchText.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (searchText.getText().equals("Search")) {
		            searchText.setText("");
		            searchText.setForeground(Color.BLACK);
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (searchText.getText().isEmpty()) {
		            searchText.setForeground(Color.GRAY);
		            searchText.setText("Search");
		        }
		    }
		    });

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		String sqlString ="";
		if(obj == searchCancel) {
			dispose();
		}else if(obj == searchApproval) {
			for(int i = 0; i<dlm_search.length; i++) {
				dlm_search[i].removeAllElements();
			}
			String isWhere[] = {"title like '%"+searchField.getText()+"%' or content like '%"+searchField.getText()+"%' or location like '%"+searchField.getText()+"%'"
										,"title like '%"+searchField.getText()+"%'"
										,"location like '%"+searchField.getText()+"%'"
										,"content like '%"+searchField.getText()+"%'"};
			for (int i = 0; i < dummy1.length; i++) {
				if (selectColumm.getSelectedIndex()==i) {
					sqlString= "select title, content, time_from, time_to, location from schedule where ("+isWhere[i]+") and (time_from between '"
											+inputPeriod.getText()+"' and '"+inputPeriod2.getText()+" 23:59:59' or time_to between '"
											+inputPeriod.getText() + "' and '"+inputPeriod2.getText()+" 23:59:59') order by time_from;";
					break;
				}
			}
			Vector<ScheduleBean> vSchList = scheduleMgr.searchSchedule(sqlString);
		
			for (int i = 0; i < vSchList.size(); i++) {
				ScheduleBean bean = vSchList.get(i);
				
				if (bean.getTitle()==null) {
					dlm_search[0].addElement("(제목 없음)");
				} else
					dlm_search[0].addElement(bean.getTitle());
				if (bean.getContent()==null) {
					dlm_search[1].addElement("(내용 없음)");
				} else
					dlm_search[1].addElement(bean.getContent());
				if (bean.getTime_from()==null){
					dlm_search[2].addElement("(시작 시간 없음)");
				} else
					dlm_search[2].addElement(bean.getTime_from());
				if (bean.getTime_to()==null) {
					dlm_search[3].addElement("  (종료 시간 없음)");
				} else
					dlm_search[3].addElement("  "+bean.getTime_to());
				if (bean.getLocation()==null) {
					dlm_search[4].addElement("  (위치 없음)");
				} else
					dlm_search[4].addElement("  "+bean.getLocation());		
			}
			
			sqlString ="";
		
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		switch(selectPeriod.getSelectedIndex()) {
			case 0:	//1주일
				inputPeriod.setText(today.toString());
				inputPeriod2.setText(today.plusDays(7).toString());
				break;
			case 1:	//1개월
				inputPeriod.setText(today.toString());
				inputPeriod2.setText(today.plusMonths(1).toString());
				break;
			case 2:	//1분기
				inputPeriod.setText(today.toString());
				inputPeriod2.setText(today.plusMonths(3).toString());
				break;
			case 3:	//1년
				inputPeriod.setText(today.toString());
				inputPeriod2.setText(today.plusYears(1).toString());
				break;
			case 4:	//5년
				inputPeriod.setText(today.toString());
				inputPeriod2.setText(today.plusYears(5).toString());
				break;
			case 5:	//이번주
				inputPeriod.setText(today.with(previousOrSame(SUNDAY)).toString());
				inputPeriod2.setText(today.with(next(SUNDAY)).toString());
				break;
			case 6:	//이번달
				inputPeriod.setText(today.with(firstDayOfMonth()).toString());
				inputPeriod2.setText(today.with(lastDayOfMonth()).toString());
				break;
			case 7:	//올해
				inputPeriod.setText(today.withDayOfYear(1).toString());
				inputPeriod2.setText(today.withDayOfYear(365).toString());
				break;
		}
		searchField.requestFocus();
	}

	@Override
	public Temporal adjustInto(Temporal temporal) {
		return temporal.plus(2, ChronoUnit.DAYS);	
	}

	@Override
	public void focusGained(FocusEvent e) {
		
	}
	@Override
	public void focusLost(FocusEvent e) {
		JList obj = (JList)e.getSource();
		obj.clearSelection();
	}
}
