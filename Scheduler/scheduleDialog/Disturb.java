package scheduleDialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalTime;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import layout.MainScreen;

public class Disturb extends JDialog implements ActionListener, ChangeListener{

	JLabel minute1, minute2;
	JLabel hourFrom, hourTo, minFrom, minTo;
	JLabel disStart, disEnd;
	JSlider minuteAdjust1, minuteAdjust2;
	JSpinner clock1, clock2, morning, evening;
	JCheckBox disApproval;
	String[] morningSpinnerSource = {"1","����","����","2"};	
	JPanel contentPane;
	JPanel disPanel;
	JButton DisturbApproval, DisturbCancel;
	ButtonGroup radioGroup;
	JRadioButton selectSizeBig, selectSizeSmall;
	LocalTime disTimeStart, disTimeEnd;
	SpinnerModel numberModel, numberModel2, morningSpinner, eveningSpinner;
	Object monSave,eveSave;
	
	public Disturb(JFrame owner) {
		setTitle("�˶�����");
		setSize(500, 400);
		setLocationRelativeTo(owner);
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//���ر��� ���۽ð��� '��'�� �����ϴ� �����̴�
		minuteAdjust1 = new JSlider(0,60,0);			// 0���� 60������ ���ݿ��� ����, �⺻��0
		minuteAdjust1.setMinorTickSpacing(5);			// �������ݰ��� 5
		minuteAdjust1.setMajorTickSpacing(30);		// ū ���� ���� 30
		minuteAdjust1.setPaintTicks(true);				// ������ ǥ����
		minuteAdjust1.setBounds(278, 110, 90, 42);
		contentPane.add(minuteAdjust1);

		//���ر��� ����ð��� '��'�� �����ϴ� �����̴�
		minuteAdjust2 = new JSlider(0,60,0);
		minuteAdjust2.setMinorTickSpacing(5);
		minuteAdjust2.setMajorTickSpacing(30);
		minuteAdjust2.setPaintTicks(true);
		minuteAdjust2.setBounds(278, 160, 90, 42);
		contentPane.add(minuteAdjust2);
		
		minuteAdjust1.addChangeListener(this);		//�����̵忡 ������ add
		minuteAdjust2.addChangeListener(this);
		
		disStart = new JLabel("���ر��� ����");
		disStart.setBounds(40, 110, 82, 15);
		contentPane.add(disStart);
		
		disEnd = new JLabel("���ر��� ����");
		disEnd.setBounds(40, 160, 82, 15);
		contentPane.add(disEnd);
		
		//���ǳ��� ������ ����(ó����1, 1����, 12����, 1�� ����)
		//�������� 13�� �� ��� 1, 0�� �� ��� 12�� ��ȯ�Ѵ�.
		//���ǳ��� ������ ����(����,����)
		numberModel = new SpinnerNumberModel(1, 0, 13, 1);
		numberModel2 = new SpinnerNumberModel(1, 0, 13, 1);
		morningSpinner = new SpinnerListModel(morningSpinnerSource);
		eveningSpinner = new SpinnerListModel(morningSpinnerSource);
		morningSpinner.setValue((Object)"����");
		eveningSpinner.setValue((Object)"����");
		numberModel.addChangeListener(this);
		numberModel2.addChangeListener(this);
		morningSpinner.addChangeListener(this);
		eveningSpinner.addChangeListener(this);
		
		//���� '��'���ǳ�
		clock1 = new JSpinner(numberModel);
		clock1.setBounds(193, 110, 39, 22);
		contentPane.add(clock1);
		
		//���� '��'���ǳ�
		clock2 = new JSpinner(numberModel2);
		clock2.setBounds(193, 160, 39, 22);
		contentPane.add(clock2);
		
		//����,���� ���ǳ�
		morning = new JSpinner(morningSpinner);
		morning.setEnabled(false);
		morning.setBounds(132, 110, 49, 22);
		contentPane.add(morning);
		
		evening = new JSpinner(eveningSpinner);
		evening.setEnabled(false);
		evening.setBounds(132, 160, 49, 22);
		contentPane.add(evening);
		
		hourFrom = new JLabel("��");
		hourFrom.setBounds(246, 110, 25, 15);
		contentPane.add(hourFrom);
		
		hourTo = new JLabel("��");
		hourTo.setBounds(246, 160, 25, 15);
		contentPane.add(hourTo);
		
		minFrom = new JLabel("�� ����");
		minFrom.setBounds(400, 110, 50, 15);
		contentPane.add(minFrom);
		
		minTo = new JLabel("�� ����");
		minTo.setBounds(400, 160, 50, 15);
		contentPane.add(minTo);
				
		minute1 = new JLabel("0");
		minute1.setBounds(385, 110, 25, 15);
		contentPane.add(minute1);
		
		minute2 = new JLabel("0");
		minute2.setBounds(385, 160, 25, 15);
		contentPane.add(minute2);
		
		//���ر��� üũ�ڽ�(��۹�ưó�� ����Ͽ� ��ҵ��� Ȱ��ȭ/��Ȱ��ȭ �Ѵ�.
		disApproval = new JCheckBox("���ر��� ����",true);
		disApproval.setBounds(40, 66, 115, 23);
		contentPane.add(disApproval);
		
		disApproval.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(disApproval.isSelected())
					approvalSwitch(true);		//üũ�� ��� ��ҵ��� Ȱ��ȭ
				else
					approvalSwitch(false);	//��Ȱ��ȭ
			}
		});
	
		//���ر��� ������ ���� ��ҵ��� ���δ� �г�(�׵θ��� ���� ����)
		disPanel = new JPanel();
		disPanel.setBounds(20, 48, 440, 170);
		Border disPanelBorder = new TitledBorder(new LineBorder(Color.BLACK), "���ر���"); 
		disPanel.setBorder(disPanelBorder);
		contentPane.add(disPanel);
		
		int selectSizePosition = 250;		//�˶����̾�α��� ũ��, ũ��, �۰� ���̸� �ѹ��� ����
		
		JLabel selectSizeLabel = new JLabel("�˶� ���̾�α��� ũ��");
		selectSizeLabel.setBounds(40, selectSizePosition+5, 139, 15);
		contentPane.add(selectSizeLabel);
		
		selectSizeBig = new JRadioButton("ũ��",true);
		selectSizeBig.setBounds(250, selectSizePosition, 57, 23);
		contentPane.add(selectSizeBig);
		
		selectSizeSmall = new JRadioButton("�۰�");
		selectSizeSmall.setBounds(350, selectSizePosition, 57, 23);
		contentPane.add(selectSizeSmall);

		radioGroup = new ButtonGroup();
		radioGroup.add(selectSizeBig);
		radioGroup.add(selectSizeSmall);
		
		//Ȯ�ι�ư
		DisturbApproval = new JButton("Ȯ��");
		DisturbApproval.setBounds(250, 328, 97, 23);
		contentPane.add(DisturbApproval);
		DisturbApproval.addActionListener(this);
		
		//��ҹ�ư
		DisturbCancel = new JButton("���");
		DisturbCancel.setBounds(350, 328, 97, 23);
		contentPane.add(DisturbCancel);
		DisturbCancel.addActionListener(this);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		approvalSwitch(true);
		validate();

	}
	
	//���ر��� ��Ҹ� �Ѳ����� Ȱ��/��Ȱ�� �ϴ� �޼ҵ�
	public void approvalSwitch(Boolean show) {
		minuteAdjust1.setEnabled(show);
		minuteAdjust2.setEnabled(show);
		clock1.setEnabled(show);
		clock2.setEnabled(show);
		hourFrom.setEnabled(show);
		hourTo.setEnabled(show);
		minFrom.setEnabled(show);
		minTo.setEnabled(show);
		disStart.setEnabled(show);
		disEnd.setEnabled(show);
		minute1.setEnabled(show);
		minute2.setEnabled(show);
		morning.setEnabled(show);
		evening.setEnabled(show);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Object obj = e.getSource();
		minute1.setText( ""+minuteAdjust1.getValue() );	//�����̴��� �����ؼ� �󺧿� ǥ��.
		minute2.setText( ""+minuteAdjust2.getValue() );

		if(Integer.parseInt(numberModel.getValue().toString())>12) {	//12���� Ŀ�� ���, 1�� ǥ��
			numberModel.setValue(1);
		}else if(Integer.parseInt(numberModel.getValue().toString())<1) {	//1���� �۾��� ���, 12�� ǥ��.
			numberModel.setValue(12);
		}
		if(Integer.parseInt(numberModel2.getValue().toString())>12) {
			numberModel2.setValue(1);
		}else if(Integer.parseInt(numberModel2.getValue().toString())<1) {
			numberModel2.setValue(12);
		}
		if(obj==morningSpinner) {
			if(morningSpinner.getValue().equals("1")) {	//1�� �� ��� '����'�� ǥ��
				morningSpinner.setValue((Object)"����");
			}else if(morningSpinner.getValue().equals("2")){	//2�� �� ���, '����'���� ǥ��
				morningSpinner.setValue((Object)"����");
			}
		}
		if(obj==eveningSpinner) {
			if(eveningSpinner.getValue().equals("1")) {
				eveningSpinner.setValue((Object)"����");
			}else if(eveningSpinner.getValue().equals("2")){
				eveningSpinner.setValue((Object)"����");
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		int setClock1 = Integer.parseInt((clock1.getValue().toString()));
		int setClock2 = Integer.parseInt((clock2.getValue().toString()));
		if(obj==DisturbCancel) {
			dispose();	
		}else if(obj==DisturbApproval) {
			if(disApproval.isSelected()) {
				if(morning.getValue().equals("����")) {
					setClock1+=12;
				}
				if(evening.getValue().equals("����")) {
					setClock2+=12;
				}
				disTimeStart =LocalTime.of(setClock1, minuteAdjust1.getValue(), 00);
				disTimeEnd =LocalTime.of(setClock2, minuteAdjust2.getValue(), 00);
				System.out.print(morning.getValue()+"(����)  ");
				System.out.println(evening.getValue()+"(����)");
				System.out.print(clock1.getValue()+"��(����)  ");
				System.out.println(clock2.getValue()+"��(����)");
				System.out.print(minuteAdjust1.getValue()+"��(����)  ");
				System.out.println(minuteAdjust2.getValue()+"��(����)\n");
				System.out.println("���ر��� ����:  "+disTimeStart);
				System.out.println("���ر��� ����:  "+disTimeEnd);
			}
			if (selectSizeBig.isSelected()){
				System.out.println(selectSizeBig.isSelected());
				//MainScreen.alarmSize=true;
			}else {
				System.out.println(selectSizeBig.isSelected());
				//MainScreen.alarmSize=false;
			}
			System.out.println("�˶����̾�α׸� "+/*MainScreen.alarmSize + */"�� ������.");
			dispose();
		}
	}	
}