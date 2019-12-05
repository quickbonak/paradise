package scheduleDialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import connectDB.AlarmBean;
import connectDB.AlarmMgr;
import layout.MainScreen;

public class Alarm extends JDialog implements Runnable {

	LocalDateTime getNow;
	AlarmMgr alarmMgr;
	Vector<AlarmBean> vAlarm;
	AlarmBean bean;
	MainScreen main;
	boolean flag = true;

	public Alarm(MainScreen owner) {
		main = owner;

		setLayout(new BorderLayout());
		setSize(300, 200);
		setLocationRelativeTo(owner);
		alarmMgr = main.getAlarmMgr();

		vAlarm = alarmMgr.selectAlarm();
		JButton alarmStop = new JButton("알람 끄기");
		alarmStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				flag = false;
				//alarmMgr.deleteAlarm(bean.getId());
				
				dispose();
			}
		});
		add(BorderLayout.SOUTH, alarmStop);
		run();
	}

	public void alarmRun() {
		setTitle("알람");
		setModal(true);
		setVisible(true);
	}

	@Override
	public void run() {
		try {
			while (flag) {
				getNow = LocalDateTime.now();
				String moment = getNow.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

				for (int i = 0; i < vAlarm.size(); i++) {
					bean = vAlarm.get(i);
					if (getNow.format(AddSchedule.dTF).equals(bean.getStart_alarm().substring(0, 19))) {
						JLabel message = new JLabel(bean.getSchedule_title());
						message.setHorizontalAlignment(SwingConstants.CENTER);
						add(message, BorderLayout.CENTER);
						alarmRun();						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
