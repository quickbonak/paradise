package scheduleDialog;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import connectDB.CalendarInitMgr;
import connectDB.DBConnectionMgr;
import layout.MainScreen;

public class Login extends JDialog implements ActionListener{

	private final JPanel contentPanel = new JPanel();
	private JPasswordField passwordField;
	private JTextField textField_ip, textField_CN, textField_id;

	private JLabel label_1, label_2, label_3, label_4;
	private JButton button, btnCancel;
	private CalendarInitMgr pool;
	MainScreen main;
	
	public Login(MainScreen owner) {
		main = owner;
		
		setTitle("공유 스케줄러 로그인");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		
		/*아이피 주소*/
		label_1 = new JLabel("IP 주소 :");
		label_1.setBounds(76, 24, 76, 30);
		contentPanel.add(label_1);
		
		textField_ip = new JTextField();
		////
		textField_ip.setText("113.198.238.");
		textField_ip.setColumns(10);
		textField_ip.setBounds(153, 24, 200, 30);
		contentPanel.add(textField_ip);
		
		/*캘린더이름*/
		label_2 = new JLabel("Calendar Name  :");
		label_2.setBounds(44, 64, 108, 30);
		contentPanel.add(label_2);
		
		textField_CN = new JTextField();
		textField_CN.setText("default_scheduler");
		textField_CN.setColumns(10);
		textField_CN.setBounds(153, 64, 200, 30);
		contentPanel.add(textField_CN);
		
		/*아이디*/
		label_3 = new JLabel("아이디 :");
		label_3.setBounds(76, 104, 76, 30);
		contentPanel.add(label_3);
		
		textField_id = new JTextField();
	
		textField_id.setBounds(153, 104, 200, 30);
		contentPanel.add(textField_id);
		textField_id.setColumns(10);
		
		/*비밀번호*/
		label_4 = new JLabel("비밀번호  :");
		label_4.setBounds(76, 144, 76, 30);
		contentPanel.add(label_4);
		
		passwordField = new JPasswordField();
		passwordField.setText("123321");
		passwordField.setBounds(153, 144, 200, 30);
		contentPanel.add(passwordField);
		
		button = new JButton("접속");
		button.setBounds(189, 193, 76, 30);
		contentPanel.add(button);
		button.addActionListener(this);
		
		btnCancel = new JButton("취소");
		btnCancel.setBounds(275, 193, 76, 30);
		contentPanel.add(btnCancel);
		btnCancel.addActionListener(this);
		
		setModal(true);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == button) {
			String ip_address = textField_ip.getText();
			String db_name = textField_CN.getText();
			String user = textField_id.getText();
			char[] password = passwordField.getPassword();
			String pw="";
			for (int i =0; i<password.length; i++) {
				pw += Character.toString(password[i]);
			}
			pool = new CalendarInitMgr(ip_address, db_name, user, pw);
			main.dispose();
			main.setScheduler_name_DB(db_name);
			MainScreen.setCim(pool);
			dispose();
			//MainScreen.setCim(pool);
			new MainScreen();
		}else if (obj ==btnCancel) {
			dispose();
		}
	}



}
