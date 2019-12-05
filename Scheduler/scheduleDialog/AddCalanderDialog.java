package scheduleDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import connectDB.CalendarInitMgr;
import connectDB.ScheduleListMgr;
import layout.MainScreen;

public class AddCalanderDialog extends JDialog implements ActionListener {
	JButton themeApproval, themeCancel;
	private JPanel contentPane;
	String[] themePackage = { "�⺻ �׸�", "��ȫ����", "�ķ���", "504", "��������", "���󺸶�" };
	JList themeList;
	JScrollPane themeListSP;
	JComboBox themeFont;

	CalendarInitMgr cim;
	ScheduleListMgr slm;
	MainScreen mainScreen;
	JTextArea ta1, ta2;
	JButton[] weekbtn;
	JButton[][] calbtn;

	int guess; // ����Ʈ�� �� �ּҸ� �����ϴ� ����
	private JLabel minorLabel1;
	private JTextField subjectTextField;
	private JCheckBox isShareCheckBox;
	private JLabel warning3;

	public AddCalanderDialog(MainScreen owner) {
		mainScreen = owner;
		setSize(390, 200);
		setLocationRelativeTo(mainScreen);
		setTitle("�� Ķ���� �߰�");

		slm = new ScheduleListMgr();
		
		//���ڸ� ���� �Ұ���, ������� �Ұ���, �ҹ��ڷθ�,
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		themeApproval = new JButton("����");
		themeApproval.setBounds(156, 121, 97, 23);
		contentPane.add(themeApproval);
		themeApproval.addActionListener(this);

		themeCancel = new JButton("���");
		themeCancel.setBounds(262, 121, 97, 23);
		contentPane.add(themeCancel);
		themeCancel.addActionListener(this);
	
		minorLabel1 = new JLabel("Ķ���� �̸�");
		minorLabel1.setBounds(12, 27, 70, 15);
		contentPane.add(minorLabel1);
		
		JLabel warning1 = new JLabel("\u25B7 \uC601\uBB38\uC790 \uD558\uB098 \uC774\uC0C1 \uC785\uB825");
		warning1.setBounds(89, 50, 164, 15);
		contentPane.add(warning1);
		
		subjectTextField = new JTextField();
		subjectTextField.setBounds(89, 24, 270, 21);
		contentPane.add(subjectTextField);
		subjectTextField.setColumns(10);
		
		JLabel warning2 = new JLabel("\u25B7 \uB744\uC5B4\uC4F0\uAE30 \uC5C6\uC774 \uC785\uB825");
		warning2.setBounds(89, 65, 140, 15);
		contentPane.add(warning2);
		
		warning3 = new JLabel("\u25B7 \uC18C\uBB38\uC790\uB9CC \uC785\uB825");
		warning3.setBounds(89, 80, 97, 15);
		contentPane.add(warning3);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		setModal(true);
		setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		 if (obj == themeApproval) {
			 String tmp = subjectTextField.getText();
			 Vector<String> vList = slm.selectScheduleList();
			 for(int i = 0; i<vList.size() ; i++) {
				 if(tmp.equals(vList.get(i))){
					 System.out.println("���� �̸��� Ķ������ �����մϴ�");
					 subjectTextField.setText("");
					 return;
				 }
			 }
			 mainScreen.dispose();
			 cim = new CalendarInitMgr(tmp);
			 mainScreen.setScheduler_name_DB(tmp);
			 dispose();
			 new MainScreen();
		} else if (obj == themeCancel) {
			dispose();
		}
	}
}
