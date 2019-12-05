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

// ��ȯ�ϴ� Ķ���� ����Ʈ�� �ε���(0����~����)�� Ȯ�ι�ư�� ���� �ÿ� ����ϵ��� �����Ͽ���.
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

		confirmButton = new JButton("Ȯ ��");
		confirmButton.addActionListener(this);

		deleteButton = new JButton("�� ��");
		deleteButton.addActionListener(this);

		cancelButton = new JButton("�� ��");
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

		setTitle("�ٸ� Ķ���� ����");
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
				System.out.println("���� �����ִ� Ķ������ ������ �� �����ϴ�");
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
