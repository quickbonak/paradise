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
		setTitle("회 원 삭 제");
		setBounds(100, 100, 300, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setVisible(true);
		
		// 여기가 회원 리스트 DB에서 가져와 넣어야할 부분.
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
		
		JLabel minorLabel2 = new JLabel("권한 수정");
		minorLabel2.setBounds(80, 30, 57, 15);
		authorModifyPanel.add(minorLabel2);
		
		JCheckBox addScheduleChk = new JCheckBox("일정 추가");
		addScheduleChk.setBounds(50, 60, 115, 23);
		authorModifyPanel.add(addScheduleChk);
		
		JCheckBox showScheduleChk = new JCheckBox("일정 보기");
		showScheduleChk.setBounds(50, 90, 115, 23);
		authorModifyPanel.add(showScheduleChk);
		
		JCheckBox modifyScheduleChk = new JCheckBox("일정 변경");
		modifyScheduleChk.setBounds(50, 120, 115, 23);
		authorModifyPanel.add(modifyScheduleChk);
		
		JCheckBox removeScheduleChk = new JCheckBox("일정 삭제");
		removeScheduleChk.setBounds(50, 150, 115, 23);
		authorModifyPanel.add(removeScheduleChk);
		
		
		
		JButton modifyCancelButton = new JButton("취 소");
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
		
		JLabel minorLabel1 = new JLabel("회원 목록");
		minorLabel1.setBounds(12, 10, 57, 15);
		contentPanel.add(minorLabel1);
		
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton modifyConfirmButton = new JButton("확 인");
		modifyConfirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				///////// 권한수정 패널의 확인 버튼 ////////
				System.out.println("선택된 userList ID : " + tempSelectedID);  // 선택된 유저 ID
				// 선택된 일정 채크박스 버튼들
				System.out.println("일정 추가 : " + addScheduleChk.isSelected() +"   일정 보기 : " + showScheduleChk.isSelected() +
						"   일정 변경 : " + modifyScheduleChk.isSelected() + "   일정 삭제 : "+ removeScheduleChk.isSelected());
				authorModifyPanel.setVisible(false);
			}
		});
		modifyConfirmButton.setBounds(30, 190, 70, 23);
		authorModifyPanel.add(modifyConfirmButton);
	
		JButton deleteUserButton = new JButton("회원 삭제");
		deleteUserButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedUserIndex = userList.getSelectedIndex();
				
				umg.deleteUser(lm.getElementAt(selectedUserIndex));
				lm.remove(selectedUserIndex); // 유저 아이디를 리스트상에서 삭제				
			}
		});
		deleteUserButton.setActionCommand("OK");
		buttonPane.add(deleteUserButton);
		getRootPane().setDefaultButton(deleteUserButton);
		
		JButton cancelButton = new JButton("취소");
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
