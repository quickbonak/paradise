package scheduleDialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import connectDB.CalendarInitMgr;
import connectDB.ScheduleListMgr;
import layout.MainScreen;

// 반환하는 캘린더 리스트의 인덱스(0부터~숫자)를 확인버튼을 누를 시에 출력하도록 수정하였다.
public class OpenAnotherCalendar extends JDialog implements ActionListener {

	JButton confirmButton, deleteButton, cancelButton;
	String changeschedule;

	MainScreen main;
	JList<String> scheduleList;
	DefaultListModel<String> scheduleListModel;
	ScheduleListMgr slm;
	Vector<String> schedule_list;

	public OpenAnotherCalendar(MainScreen owner) {
		main = owner;
		setLayout(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		scheduleList = new JList<String>();
		scheduleList.setBounds(12, 10, 260, 258);
		contentPanel.add(scheduleList);

		scheduleListModel = new DefaultListModel<String>();
		scheduleList.setModel(scheduleListModel);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

		confirmButton = new JButton("확 인");
		confirmButton.addActionListener(this);

		deleteButton = new JButton("삭 제");
		deleteButton.addActionListener(this);

		cancelButton = new JButton("취 소");
		cancelButton.addActionListener(this);

		buttonPane.add(confirmButton);
		buttonPane.add(deleteButton);
		buttonPane.add(cancelButton);

		slm = new ScheduleListMgr();
		schedule_list = slm.selectScheduleList();
		for (int i = 0; i < schedule_list.size(); i++) {
			scheduleListModel.addElement(schedule_list.get(i));
		}

		add(contentPanel, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.SOUTH);

		setTitle("다른 캘린더 열기");
		setBounds(100, 100, 300, 350);
		setModal(true);
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		String tmp = scheduleList.getSelectedValue();
		
		if (obj == confirmButton) {
			if(tmp != null) {
				changeschedule = tmp;
				main.dispose();
				main.setScheduler_name_DB(changeschedule);
				slm.updateScheduleList(changeschedule, CalendarInitMgr.sdf.format(new Date()));
				new MainScreen();
			} 
			dispose();
		} else if (obj == deleteButton) {
			if (tmp.equals(main.getScheduler_name_DB())) {
				System.out.println("현재 열려있는 캘린더를 삭제할 수 없습니다");
				return;
			}
			scheduleListModel.removeElement(tmp);
			scheduleListModel.trimToSize();
			slm.deleteScheduleList(tmp);
		} else if (obj == cancelButton) {
			dispose();

		}
	}

	public String getChangeschedule() {
		return changeschedule;
	}

	public void setChangeschedule(String changeschedule) {
		this.changeschedule = changeschedule;
	}
}
