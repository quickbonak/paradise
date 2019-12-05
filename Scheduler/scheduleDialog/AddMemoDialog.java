package scheduleDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import connectDB.CalendarInitMgr;
import connectDB.MemoBean;
import connectDB.MemoMgr;
import layout.MainScreen;

public class AddMemoDialog extends JDialog implements ActionListener {

	JPanel contentPanel = new JPanel();
	private JButton colorBtn1, colorBtn2, colorBtn3, colorBtn4;
	private JButton confirm, cancel;
	private JTextArea memoText;
	final static int ADD = 1;
	final static int EDIT = 2;
	
	MainScreen main;
	int mode;
	MemoMgr memoMgr;
	Vector<MemoBean> vMemoList;
	
	public AddMemoDialog(MainScreen owner, int MODE, String memoEdit, String listBackColor) {

		main = owner;
		mode = MODE;
		memoMgr = main.getMemoMgr();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);

		colorBtn1 = new JButton("");
		colorBtn1.addActionListener(this);
		colorBtn1.setBackground(new Color(255, 255, 255));
		colorBtn1.setBounds(20, 15, 70, 25);
		contentPanel.add(colorBtn1);

		colorBtn2 = new JButton("");
		colorBtn2.addActionListener(this);
		colorBtn2.setBackground(new Color(240, 230, 140));
		colorBtn2.setBounds(97, 15, 70, 25);
		contentPanel.add(colorBtn2);

		colorBtn3 = new JButton("");
		colorBtn3.addActionListener(this);
		colorBtn3.setBackground(new Color(255, 182, 193));
		colorBtn3.setBounds(174, 15, 70, 25);
		contentPanel.add(colorBtn3);

		colorBtn4 = new JButton("");
		colorBtn4.addActionListener(this);
		colorBtn4.setBackground(new Color(175, 238, 238));

		colorBtn4.setBounds(251, 15, 70, 25);
		contentPanel.add(colorBtn4);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

		confirm = new JButton("확인");
		confirm.addActionListener(this);
		confirm.setBackground(new Color(255, 245, 238));

		cancel = new JButton("취소");
		cancel.setBackground(new Color(255, 245, 238));
		cancel.addActionListener(this);

		buttonPane.add(confirm);
		buttonPane.add(cancel);

		add(contentPanel, BorderLayout.CENTER);

		JLabel limitCharLabel = new JLabel("/ 400");
		limitCharLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		limitCharLabel.setBounds(263, 295, 57, 15);
		contentPanel.add(limitCharLabel);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		memoText = new JTextArea();
		memoText.setLineWrap(true);
		memoText.setBounds(21, 40, 300, 248);

		contentPanel.add(memoText);
		memoText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (memoText.getText().getBytes().length > 400) {
					memoText.setText(memoText.getText().substring(0,
							memoText.getText().length() - (memoText.getText().getBytes().length - 400)));
					;

				}
				limitCharLabel.setText(memoText.getText().getBytes().length + " / 400");
			}
		});
		
		if (mode == ADD)
			setTitle("새 메모 추가");
		else if (mode == EDIT) {
			setTitle("메모 편집");
			memoText.setText(memoEdit);
			memoText.setBackground(Color.decode(listBackColor));
		}
		
		
		 vMemoList= memoMgr.selectMemo();
		
		setLocationRelativeTo(owner);
		setBounds(100, 100, 360, 395);
		setModalityType(DEFAULT_MODALITY_TYPE);
		setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();	
		if (btn == confirm && mode == ADD) {
			if(!memoText.getText().isEmpty() && memoText.getText().length()<=200) {
				Color colorOut = memoText.getBackground();
				Date time = new Date();
				saveMemo(memoText.getText(), colorOut, time);
				dispose();
			}		
		} else if (btn==confirm && mode ==EDIT) {
			int editIdx = main.getMemoList().getSelectedIndex();
			String prevText = main.getMemoList().getSelectedValue();
			System.out.println(editIdx +" idx");
			if(!memoText.getText().isEmpty() && memoText.getText().length()<=200) {
				Color colorOut = memoText.getBackground();
				Date time = new Date();
				editMemo(memoText.getText(), colorOut, time, editIdx, prevText);
				dispose();
			}		
			
		} else if (btn == colorBtn1) {
			memoText.setBackground(colorBtn1.getBackground());
		} else if (btn == colorBtn2) {
			memoText.setBackground(colorBtn2.getBackground());
		} else if (btn == colorBtn3) {
			memoText.setBackground(colorBtn3.getBackground());
		} else if (btn == colorBtn4) {
			memoText.setBackground(colorBtn4.getBackground());
		} else if (btn == cancel) {
			dispose();
		}

	}

	public void saveMemo(String content, Color label_color, Date time) {
		boolean flag = true;
		for(int i = 0; i<vMemoList.size() ; i++) {
			MemoBean mb = vMemoList.get(i);
			if(mb.getContent().equals(content)){
				System.out.println("중복된 메모가 있습니다. 메모 등록 불가능");
				flag = false;
				return;
			}
		}
		if(flag) {
			String modified_at = CalendarInitMgr.sdf.format(time);
			String hex = String.format("#%02x%02x%02x", label_color.getRed(), label_color.getGreen(),
					label_color.getBlue());
			
			memoMgr.insertMemo(content, hex, modified_at);
			main.getDlm_memo().addElement(memoText.getText());
		}
	}
	
	public void editMemo(String content, Color label_color, Date time, int idx, String prevText) {
		boolean flag = true;
		for(int i = 0; i<vMemoList.size() ; i++) {
			MemoBean mb = vMemoList.get(i);
			if(mb.getContent().equals(content)){
				System.out.println("중복된 메모가 있습니다. 메모 등록 불가능");
				flag = false;
				return;
			}
		}
		if(flag) {
			String modified_at = CalendarInitMgr.sdf.format(time);
			String hex = String.format("#%02x%02x%02x", label_color.getRed(), label_color.getGreen(),
					label_color.getBlue());
						
			memoMgr.updateMemo(content, hex, modified_at, prevText);
			main.getDlm_memo().remove(idx);
			main.getDlm_memo().addElement(memoText.getText());
		}
	}
	
	public JTextArea getMemoText() {
		return memoText;
	}
}
