package scheduleDialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import connectDB.UserMgr;

public class UserManagementDiaglog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	String tempSelectedID = "";
	UserMgr umg;
	JList<String> userList;
	
	public UserManagementDiaglog() {
		setTitle("ȸ �� �� ��");
		setBounds(100, 100, 300, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setVisible(true);
		
		// ���Ⱑ ȸ�� ����Ʈ DB���� ������ �־���� �κ�.
		userList = new JList();
		userList.setModel(new DefaultListModel<>());
		DefaultListModel<String> lm = (DefaultListModel)userList.getModel();
		
		umg = new UserMgr();
		Vector<String> user = umg.selectUser();
		for (int i = 0; i < user.size(); i++) {
			lm.addElement(user.get(i));
		}
		
		
		Panel authorModifyPanel = new Panel();
		authorModifyPanel.setBounds(0, 0, 284, 328);
		contentPanel.add(authorModifyPanel);
		authorModifyPanel.setLayout(null);
		authorModifyPanel.setVisible(false);
		
		JLabel minorLabel2 = new JLabel("���� ����");
		minorLabel2.setBounds(80, 30, 57, 15);
		authorModifyPanel.add(minorLabel2);
		
		JCheckBox addScheduleChk = new JCheckBox("���� �߰�");
		addScheduleChk.setBounds(50, 60, 115, 23);
		authorModifyPanel.add(addScheduleChk);
		
		JCheckBox showScheduleChk = new JCheckBox("���� ����");
		showScheduleChk.setBounds(50, 90, 115, 23);
		authorModifyPanel.add(showScheduleChk);
		
		JCheckBox modifyScheduleChk = new JCheckBox("���� ����");
		modifyScheduleChk.setBounds(50, 120, 115, 23);
		authorModifyPanel.add(modifyScheduleChk);
		
		JCheckBox removeScheduleChk = new JCheckBox("���� ����");
		removeScheduleChk.setBounds(50, 150, 115, 23);
		authorModifyPanel.add(removeScheduleChk);
		
		
		
		JButton modifyCancelButton = new JButton("�� ��");
		modifyCancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				authorModifyPanel.setVisible(false);
			}
		});
		modifyCancelButton.setBounds(110, 190, 70, 23);
		authorModifyPanel.add(modifyCancelButton);
		
		userList.setBounds(12, 35, 260, 283);
		contentPanel.add(userList);
		
		JLabel minorLabel1 = new JLabel("ȸ�� ���");
		minorLabel1.setBounds(12, 10, 57, 15);
		contentPanel.add(minorLabel1);
		
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton modifyConfirmButton = new JButton("Ȯ ��");
		modifyConfirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				///////// ���Ѽ��� �г��� Ȯ�� ��ư ////////
				System.out.println("���õ� userList ID : " + tempSelectedID);  // ���õ� ���� ID
				// ���õ� ���� äũ�ڽ� ��ư��
				System.out.println("���� �߰� : " + addScheduleChk.isSelected() +"   ���� ���� : " + showScheduleChk.isSelected() +
						"   ���� ���� : " + modifyScheduleChk.isSelected() + "   ���� ���� : "+ removeScheduleChk.isSelected());
				authorModifyPanel.setVisible(false);
			}
		});
		modifyConfirmButton.setBounds(30, 190, 70, 23);
		authorModifyPanel.add(modifyConfirmButton);
	
		JButton deleteUserButton = new JButton("ȸ�� ����");
		deleteUserButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedUserIndex = userList.getSelectedIndex();
				
				umg.deleteUser(lm.getElementAt(selectedUserIndex));
				lm.remove(selectedUserIndex); // ���� ���̵� ����Ʈ�󿡼� ����				
			}
		});
		deleteUserButton.setActionCommand("OK");
		buttonPane.add(deleteUserButton);
		getRootPane().setDefaultButton(deleteUserButton);
		
		JButton cancelButton = new JButton("���");
		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		
	}
}
