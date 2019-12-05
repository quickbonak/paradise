package scheduleDialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

public class routineSelector2 extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final ButtonGroup group1 = new ButtonGroup();
	private final ButtonGroup group2 = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			routineSelector2 dialog = new routineSelector2();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public routineSelector2() {
		setBounds(100, 100, 450, 345);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel aaLabel1 = new JLabel("반복패턴");
			aaLabel1.setBounds(110, 15, 55, 15);
			contentPanel.add(aaLabel1);
		}
		
		{
			JLabel aaLabel2 = new JLabel("반복발생 범위");
			aaLabel2.setBounds(310, 15, 85, 15);
			contentPanel.add(aaLabel2);
		}
		{
			JRadioButton noEndDateRadio = new JRadioButton("종료일 없음");
			group2.add(noEndDateRadio);
			noEndDateRadio.setBounds(300, 50, 100, 23);
			contentPanel.add(noEndDateRadio);
		}
		{
			JRadioButton repeatAfterRadio = new JRadioButton("횟수 뒤 종료");
			group2.add(repeatAfterRadio);
			repeatAfterRadio.setBounds(300, 80, 120, 23);
			contentPanel.add(repeatAfterRadio);
		}
		{
			JRadioButton untilDateRadio = new JRadioButton("다음 까지 종료");
			group2.add(untilDateRadio);
			untilDateRadio.setBounds(300, 140, 120, 23);
			contentPanel.add(untilDateRadio);
		}
		{
			JSpinner repeatAfterSpinner = new JSpinner();
			repeatAfterSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
			repeatAfterSpinner.setBounds(331, 110, 48, 22);
			contentPanel.add(repeatAfterSpinner);
		}
		{
			JLabel aaLabel3 = new JLabel("회");
			aaLabel3.setBounds(385, 113, 17, 15);
			contentPanel.add(aaLabel3);
		}
		{
			JSpinner spinner_1 = new JSpinner();
			spinner_1.setModel(new SpinnerDateModel(new Date(1510153200000L), null, null, Calendar.DAY_OF_YEAR));
			spinner_1.setBounds(310, 169, 95, 22);
			contentPanel.add(spinner_1);
		}
		
		Panel yearlyPn = new Panel();
		yearlyPn.setBounds(20, 70, 260, 190);
		contentPanel.add(yearlyPn);
		yearlyPn.setLayout(null);
		
		Panel monthlyPn = new Panel();
		monthlyPn.setBounds(20, 70, 260, 190);
		contentPanel.add(monthlyPn);
		monthlyPn.setLayout(null);
		
		JSpinner monthSpinner1 = new JSpinner();
		monthSpinner1.setBounds(35, 20, 37, 22);
		monthlyPn.add(monthSpinner1);
		
		JLabel monthLabel1 = new JLabel("\uAC1C\uC6D4 \uB9C8\uB2E4");
		monthLabel1.setBounds(75, 23, 60, 15);
		monthlyPn.add(monthLabel1);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(85, 48, 37, 22);
		monthlyPn.add(spinner);
		
		JLabel lblNewLabel = new JLabel("\uC77C\uC5D0");
		lblNewLabel.setBounds(126, 51, 37, 15);
		monthlyPn.add(lblNewLabel);
		{
			JRadioButton rdbtnNewRadioButton = new JRadioButton("");
			rdbtnNewRadioButton.setBounds(10, 20, 21, 21);
			monthlyPn.add(rdbtnNewRadioButton);
		}
		{
			JRadioButton radioButton = new JRadioButton("");
			radioButton.setBounds(10, 100, 21, 21);
			monthlyPn.add(radioButton);
		}
		{
			JSpinner monthSpinner2 = new JSpinner();
			monthSpinner2.setBounds(35, 100, 37, 22);
			monthlyPn.add(monthSpinner2);
		}
		{
			JLabel monthLabel2 = new JLabel("\uAC1C\uC6D4 \uB9C8\uB2E4");
			monthLabel2.setBounds(75, 103, 60, 15);
			monthlyPn.add(monthLabel2);
		}
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1 \uBC88\uC9F8", "2 \uBC88\uC9F8", "3 \uBC88\uC9F8", "4 \uBC88\uC9F8", "5 \uBC88\uC9F8", "\uB9C8\uC9C0\uB9C9"}));
		comboBox.setBounds(45, 132, 65, 21);
		monthlyPn.add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"\uC77C\uC694\uC77C\uC5D0", "\uC6D4\uC694\uC77C\uC5D0", "\uD654\uC694\uC77C\uC5D0", "\uC218\uC694\uC77C\uC5D0", "\uBAA9\uC694\uC77C\uC5D0", "\uAE08\uC694\uC77C\uC5D0", "\uD1A0\uC694\uC77C\uC5D0"}));
		comboBox_1.setBounds(117, 132, 80, 21);
		monthlyPn.add(comboBox_1);
		
		Panel dailyPn = new Panel();
		dailyPn.setBounds(20, 70, 260, 190);
		contentPanel.add(dailyPn);
		dailyPn.setLayout(null);
		
		JSpinner daySpinner = new JSpinner();
		daySpinner.setModel(new SpinnerNumberModel(1, 1, 1000, 1));
		daySpinner.setBounds(20, 20, 37, 22);
		dailyPn.add(daySpinner);
		{
			JLabel dayLabel = new JLabel("\uC77C \uB9C8\uB2E4");
			dayLabel.setBounds(65, 19, 56, 22);
			dailyPn.add(dayLabel);
		}
		
			Panel weeklyPn = new Panel();
			weeklyPn.setBounds(20, 70, 260, 190);
			contentPanel.add(weeklyPn);
			weeklyPn.setLayout(null);
			{
				JSpinner weekSpinner = new JSpinner();
				weekSpinner.setModel(new SpinnerNumberModel(1, 1, 1000, 1));
				weekSpinner.setBounds(20, 20, 37, 22);
				weeklyPn.add(weekSpinner);
			}
			{
				JLabel weekLabel = new JLabel("\uC8FC \uB9C8\uB2E4");
				weekLabel.setBounds(69, 23, 57, 15);
				weeklyPn.add(weekLabel);
			}
			
			JCheckBox day0 = new JCheckBox("\uC77C\uC694\uC77C");
			day0.setBounds(20, 50, 70, 23);
			weeklyPn.add(day0);
			
			JCheckBox day1 = new JCheckBox("\uC6D4\uC694\uC77C");
			day1.setBounds(90, 50, 70, 23);
			weeklyPn.add(day1);
			
			JCheckBox day2 = new JCheckBox("\uD654\uC694\uC77C");
			day2.setBounds(160, 50, 70, 23);
			weeklyPn.add(day2);
			
			JCheckBox day3 = new JCheckBox("\uC218\uC694\uC77C");
			day3.setBounds(20, 80, 70, 23);
			weeklyPn.add(day3);
			
			JCheckBox day4 = new JCheckBox("\uBAA9\uC694\uC77C");
			day4.setBounds(90, 80, 70, 23);
			weeklyPn.add(day4);
			
			JCheckBox day5 = new JCheckBox("\uAE08\uC694\uC77C");
			day5.setBounds(160, 80, 70, 23);
			weeklyPn.add(day5);
			
			JCheckBox day6 = new JCheckBox("\uD1A0\uC694\uC77C");
			day6.setBounds(20, 110, 70, 23);
			weeklyPn.add(day6);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JCheckBox routineOnOffCheckBox = new JCheckBox("반복이벤트");
				buttonPane.add(routineOnOffCheckBox);
				routineOnOffCheckBox.setSelected(true);
			}
			{
				JButton confirmButton = new JButton("확인");
				confirmButton.setActionCommand("OK");
				buttonPane.add(confirmButton);
				getRootPane().setDefaultButton(confirmButton);
			}
			{
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
			dailyPn.setVisible(false);
			weeklyPn.setVisible(false);
			monthlyPn.setVisible(false);
			yearlyPn.setVisible(false);
			{
				JRadioButton dailyRadio = new JRadioButton("매일");
				dailyRadio.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dailyPn.setVisible(true);
						weeklyPn.setVisible(false);
						monthlyPn.setVisible(false);
						yearlyPn.setVisible(false);
					}
				});
				group1.add(dailyRadio);
				dailyRadio.setBounds(20, 40, 55, 25);
				contentPanel.add(dailyRadio);
			}
			{
				JRadioButton weeklyRadio = new JRadioButton("매주");
				weeklyRadio.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dailyPn.setVisible(false);
						weeklyPn.setVisible(true);
						monthlyPn.setVisible(false);
						yearlyPn.setVisible(false);
					}
				});
				group1.add(weeklyRadio);
				weeklyRadio.setBounds(80, 40, 55, 25);
				contentPanel.add(weeklyRadio);
			}
			{
				JRadioButton monthlyRadio = new JRadioButton("매달");
				monthlyRadio.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dailyPn.setVisible(false);
						weeklyPn.setVisible(false);
						monthlyPn.setVisible(true);
						yearlyPn.setVisible(false);
					}
				});
				group1.add(monthlyRadio);
				monthlyRadio.setBounds(140, 40, 55, 25);
				contentPanel.add(monthlyRadio);
			}
			{
				JRadioButton yearlyRadio = new JRadioButton("매년");
				yearlyRadio.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dailyPn.setVisible(false);
						weeklyPn.setVisible(false);
						monthlyPn.setVisible(false);
						yearlyPn.setVisible(true);
					}
				});
				group1.add(yearlyRadio);
				yearlyRadio.setBounds(200, 40, 55, 25);
				contentPanel.add(yearlyRadio);
			}
		}
	}
}
