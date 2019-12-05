package scheduleDialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import connectDB.*;
import layout.MainScreen;

public class Theme_set extends JDialog implements ActionListener, ListSelectionListener {
	JButton themeApproval, themeCancel, themeDefault, themePreview;
	JPanel contentPane;
	String[] themePackage = { "기본 테마", "분홍봉봉", "파랭이", "다크브라운", "젤리공장", "보라보라" };
	JList<String> themeList;
	JScrollPane themeListSP;


	int weekInt, zoomInNum;
	MainScreen mainScreen;
	Vector<JComponent> coloredComponents;
	JPanel sidePanel, cenPanel, cenPanel1, cenPanel2, cenPanel2_1, cenPanel2_2, cenPanel3, cenPanel4, sidePanel1,
			sidePanel1_1, sidePanel1_2, sidePanel2, sidePanel3, cenPanel4_1;
	JButton[] weekbtn;
	JButton[][] calbtn;
	JCheckBox weatherchk;

	Boolean didPreview = false; // 미리보기를 했는지 검사하는 변수
	int guess; // 리스트에서 선택한 인덱스를 저장하는 변수
	int choiceTheme; // 지정한 테마(확정)를 저장 

	// {0 패널공통 }, {1 테두리색 }, {2 평일 }, {3 주말 } {4 일요일 }, { 5 내달 }, {6 외달 },{7클릭색깔},
	// ,{8요일컬러평일},{9요일컬러주말},{10요일컬러공휴일},{11달력 일자색깔},{12달력 휴일색깔},{13클릭색깔(글자)}
	public static String setColor[][] = {
			{ "#FFFFF5", "#F9F9F9", "#969696", "#0000FF", "#FF00000", "#FFFFFF", "#969696", "#00FFFF", "#FFFFFF",
					"#FFFFFF", "#FFFFFF", "#000000", "#FF6464", "#FFFFFF" },
			// 분홍
			{ "#FBE9E7", "#FC99CF", "#FFCCBC", "#FF8A65", "#FF6E40", "#FFF3E0", "#FBE9E7", "#F981BF", "#994A79",
					"#F2E8EE", "#F2E8EE", "#994A79", "#82051E", "#F9F9F9" },
			// 파랑
			{ "#F9F9F9", "#3C7DB2", "#021E44", "#073468", "#B488E", "#A4C5F9", "#F7F8F9", "#1858BA", "#F9F9F9",
					"#F9F9F9", "#F9F9F9", "#092A8C", "#000000", "#FFFFFF" },
			// 504
			{ "#545353", "#32424F", "#475159", "#212121", "#111E16", "#3D2D33", "#2F332F", "#000000", "#F9F9F9",
					"#F9F9F9", "#F9F9F9", "#A09A", "#F7BE76", "#FFFFFF" },
			// 젤리공장
			{ "#8DFD7D", "#72F9F9", "#83EFF5", "#EAE646", "#F459A3", "#EAE646", "#76F2C0", "#50FC25", "#A3C1",
					"#EAE646", "#F459A3", "#F459A3", "#910A0A", "#000000" },
			// 보라
			{ "#F4ECF7", "#D2B4DE", "#D2B4DE", "#AF7AC5", "#633974", "#512E5F", "#F5EEF8", "#4A235A", "#4E2A68",
					"#F9F9F9", "#F9F9F9", "#EC7063", "#F7021A", "#000000" } };

	public Theme_set(MainScreen owner, Vector<JComponent> vector) {
		mainScreen = owner;
		coloredComponents = vector;
		choiceTheme = mainScreen.getChoiceTheme();
		zoomInNum = mainScreen.getZoominNum();
		
		weekInt = 7;

		getColoredComponents();

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		themeApproval = new JButton("결정");
		themeApproval.setBounds(226, 228, 97, 23);
		contentPane.add(themeApproval);
		themeApproval.addActionListener(this);

		themeCancel = new JButton("취소");
		themeCancel.setBounds(325, 228, 97, 23);
		contentPane.add(themeCancel);
		themeCancel.addActionListener(this);

		themeList = new JList(themePackage);
		themeListSP = new JScrollPane(themeList);
		themeListSP.setBounds(12, 45, 303, 93);
		contentPane.add(themeListSP);
		themeList.addListSelectionListener(this);

		themeDefault = new JButton("기본설정");
		themeDefault.setBounds(325, 42, 97, 23);
		contentPane.add(themeDefault);
		themeDefault.addActionListener(this);

		themePreview = new JButton("미리보기");
		themePreview.setBounds(325, 115, 97, 23);
		contentPane.add(themePreview);
		themePreview.addActionListener(this);

		JLabel themeLabeltheme = new JLabel("테마");
		themeLabeltheme.setBounds(12, 10, 137, 15);
		contentPane.add(themeLabeltheme);

		setSize(450, 300);
		setLocationRelativeTo(owner);
		setTitle("테마 설정");
		setModal(true);
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}

	public void getColoredComponents() {
		sidePanel = (JPanel) coloredComponents.get(0);
		cenPanel = (JPanel) coloredComponents.get(1);
		cenPanel1 = (JPanel) coloredComponents.get(2);
		cenPanel2 = (JPanel) coloredComponents.get(3);
		cenPanel2_1 = (JPanel) coloredComponents.get(4);
		cenPanel2_2 = (JPanel) coloredComponents.get(5);
		cenPanel3 = (JPanel) coloredComponents.get(6);
		cenPanel4 = (JPanel) coloredComponents.get(7);
		sidePanel1 = (JPanel) coloredComponents.get(8);
		sidePanel1_1 = (JPanel) coloredComponents.get(9);
		sidePanel1_2 = (JPanel) coloredComponents.get(10);
		sidePanel2 = (JPanel) coloredComponents.get(11);
		sidePanel3 = (JPanel) coloredComponents.get(12);
		cenPanel4_1 = (JPanel) coloredComponents.get(13);

		weekbtn = new JButton[weekInt];

		for (int i = 14; i < (weekbtn.length + 14); i++) {
			weekbtn[i - 14] = (JButton) coloredComponents.get(i);
		}

		calbtn = new JButton[zoomInNum][weekInt];
		int idx = 21;
		for (int i = 0; i < (zoomInNum); i++) {
			for (int j = 0; j < weekInt; j++) {
				calbtn[i][j] = (JButton) coloredComponents.get(idx);
				idx++;
			}
		}
		weatherchk = (JCheckBox) coloredComponents.get(idx);
		
	}

	public void theme_Set_Save() {
		SettingMgr setting = new SettingMgr(mainScreen.getScheduler_name_DB());
		setting.saveTheme(choiceTheme);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		Object obj = e.getSource();
		if (obj == themeList) {
			guess = themeList.getSelectedIndex();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == themeDefault) {
			didPreview = true;
			themeList.setSelectedIndex(0);
			guess = 0;
			theme_set_color(guess);
		} else if (obj == themePreview) {
			didPreview = true;
			theme_set_color(guess);
		} else if (obj == themeApproval) {
			didPreview = false;
			theme_set_color(guess);
			setChoiceTheme(guess);
			dispose();
			theme_Set_Save();
		} else if (obj == themeCancel) {
			if (didPreview) {
				guess = choiceTheme;
				theme_set_color(guess);
			}
			didPreview = false;
			dispose();
		}
	}

	public void theme_set_color(int guess) {

		sidePanel.setBackground(Color.decode(setColor[guess][0]));

		LineBorder lb = new LineBorder(Color.decode(setColor[guess][1]));
		sidePanel.setBorder(lb);
		sidePanel2.setBorder(lb);
		sidePanel3.setBorder(lb);

		sidePanel.setBackground(Color.decode(setColor[guess][0]));
		sidePanel1.setBackground(Color.decode(setColor[guess][0]));

		sidePanel1_1.setBackground(Color.decode(setColor[guess][0]));
		sidePanel1_2.setBackground(Color.decode(setColor[guess][0]));
		sidePanel2.setBackground(Color.decode(setColor[guess][0]));
		sidePanel3.setBackground(Color.decode(setColor[guess][0]));

		cenPanel.setBackground(Color.decode(setColor[guess][0]));
		cenPanel1.setBackground(Color.decode(setColor[guess][0]));
		cenPanel2.setBackground(Color.decode(setColor[guess][0]));
		cenPanel2_1.setBackground(Color.decode(setColor[guess][0]));
		cenPanel2_2.setBackground(Color.decode(setColor[guess][0]));
		cenPanel3.setBackground(Color.decode(setColor[guess][0]));
		cenPanel4.setBackground(Color.decode(setColor[guess][0]));
		cenPanel4_1.setBackground(Color.decode(setColor[guess][0]));

		for (int i = 1; i < 6; i++) {
			weekbtn[i].setBackground(Color.decode(setColor[guess][2]));
			weekbtn[i].setForeground(Color.decode(setColor[guess][8]));
		}

		weekbtn[0].setBackground(Color.decode(setColor[guess][4]));
		weekbtn[6].setBackground(Color.decode(setColor[guess][3]));
		weekbtn[0].setForeground(Color.decode(setColor[guess][10]));
		weekbtn[6].setForeground(Color.decode(setColor[guess][9]));

		for (int i = 0; i < zoomInNum; i++) {
			for (int j = 0; j < weekInt; j++) {
				if (calbtn[i][j].getName() == "내달") {
					calbtn[i][j].setBackground(Color.decode(setColor[guess][5]));
					calbtn[i][j].setForeground(Color.decode(setColor[guess][11]));
				} else if (calbtn[i][j].getName() == "외달") {
					calbtn[i][j].setBackground(Color.decode(setColor[guess][6]));
					calbtn[i][j].setForeground(Color.decode(setColor[guess][11]));
				} else if (calbtn[i][j].getName() == "공휴일") {
					calbtn[i][j].setBackground(Color.decode(setColor[guess][4]));
					calbtn[i][j].setForeground(Color.decode(setColor[guess][12]));
				}
			}
		}	
		weatherchk.setBackground(Color.decode(setColor[guess][0]));
	}


	
	public int getChoiceTheme() {
		return choiceTheme;
	}

	public void setChoiceTheme(int choiceTheme) {
		this.choiceTheme = choiceTheme;
	}
}
