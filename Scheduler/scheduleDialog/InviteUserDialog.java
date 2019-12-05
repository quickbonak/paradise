package scheduleDialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import connectDB.UserBean;
import connectDB.UserMgr;
import layout.MainScreen;


// ȸ�� �ʴ� UI, Ȯ�ι�ư�� ������ ���� ��ºθ� ���� �ȴ�.

public class InviteUserDialog extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	JButton send, cancel;
	private JPasswordField passwordField;
	private JTextField idTextField;
	private JTextField emailTextField;
	JCheckBox select, insert, update, delete;
	String mailContent, calendarName, ipAdress;	
	UserMgr umg;
	UserBean userBean;
	MainScreen main;
	
	public InviteUserDialog(MainScreen owner) {
		main = owner;
		
		setBounds(100, 100, 270, 300);
		setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel minorLabel1 = new JLabel("ID");
		minorLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		minorLabel1.setBounds(10, 20, 70, 15);
		contentPanel.add(minorLabel1);
		
		JLabel minorLabel2 = new JLabel("Password");
		minorLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		minorLabel2.setBounds(10, 50, 70, 15);
		contentPanel.add(minorLabel2);
		
		JLabel minorLabel3 = new JLabel("����");
		minorLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
		minorLabel3.setBounds(10, 80, 70, 15);
		contentPanel.add(minorLabel3);
		
		JLabel minorLabel4 = new JLabel("E-mail");
		minorLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
		minorLabel4.setBounds(10, 187, 70, 15);
		contentPanel.add(minorLabel4);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(92, 47, 150, 21);
		contentPanel.add(passwordField);
		
		idTextField = new JTextField();
		idTextField.setBounds(92, 17, 150, 21);
		contentPanel.add(idTextField);
		idTextField.setColumns(10);
		
		insert = new JCheckBox("���� �߰�");
		insert.setBounds(88, 76, 115, 23);
		contentPanel.add(insert);
		
		select = new JCheckBox("���� ����");
		select.setBounds(88, 101, 115, 23);
		contentPanel.add(select);
		
		update = new JCheckBox("���� ����");
		update.setBounds(88, 126, 115, 23);
		contentPanel.add(update);
		
		delete = new JCheckBox("���� ����");
		delete.setBounds(88, 151, 115, 23);
		contentPanel.add(delete);
		
		emailTextField = new JTextField();
		emailTextField.setBounds(92, 184, 150, 21);
		contentPanel.add(emailTextField);
		emailTextField.setColumns(10);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		
		send = new JButton("������");
		send.addActionListener(this);
		buttonPane.add(send);
		
		cancel = new JButton("���");
		cancel.addActionListener(this);
		buttonPane.add(cancel);
	
		add(buttonPane, BorderLayout.SOUTH);
		
		
		setTitle("ȸ�� �ʴ�");
		setModal(true);
		setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton obj = (JButton) e.getSource();
		if(obj == send) {
			if(idTextField.getText().isEmpty() || passwordField.getText().isEmpty() || emailTextField.getText().isEmpty()) {
				System.out.println("�Է��� �ٽ� Ȯ�����ּ���");
				return;
			}
			String id = idTextField.getText();
			String password = passwordField.getText();
			String email = emailTextField.getText();
			
			userBean = new UserBean();
			userBean.setId(id);
			userBean.setPassword(password);
			userBean.setEmail(email);
			userBean.setSchedule_name(main.getScheduler_name_DB());
			
			umg = new UserMgr();
			
			try {           
				InetAddress ip = InetAddress.getLocalHost();
				ipAdress = ip.getHostAddress();
				umg.createUser(idTextField.getText(), passwordField.getText());
			
				if(insert.isSelected()) {//�߰�
					System.out.println("�����μ�Ʈ");
					umg.grantInsert(userBean);
					umg.grantInsertMemo(userBean);
					userBean.setCanSelect(true);
				}
				if(select.isSelected()) {
					umg.grantSelect(userBean);
					umg.grantSelectMemo(userBean);
					userBean.setCanSelect(true);
				}
				if(update.isSelected()) {
					umg.grantUpdate(userBean);
					umg.grantUpdateMemo(userBean);
					userBean.setCanUpdate(true);
				}
				if(delete.isSelected()) {
					umg.grantDelete(userBean);
					umg.grantDeleteMemo(userBean);
					userBean.setCanDelete(true);
				}
				
			}      
			catch (Exception e1) {    
				System.out.println("ȸ���� ����� �� �����ϴ�. ���̵�� ��й�ȣ�� Ȯ���ϼ���");
			}
			
			calendarName = main.getScheduler_name_DB();
			mailContent = ("IP : "+ipAdress+"\ncalendar Name : "+calendarName+"\nID : " + idTextField.getText()+"\nPassword : " + passwordField.getText()+"\n\n\n"+"�� �� : \n���� �߰� " +
					insert.isSelected() +"\n���� ���� " + select.isSelected() +
					 "\n���� ���� " + update.isSelected() + "\n���� ���� " + delete.isSelected()+"\n");
			Gmail_Mail.send("Ķ���� ���� ���̵� �ο��Ǿ����ϴ�.", mailContent, emailTextField.getText());
			dispose();
		} else if (obj == cancel)
			dispose();
		
	}
}
