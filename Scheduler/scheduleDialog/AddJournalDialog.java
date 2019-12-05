package scheduleDialog;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;

public class AddJournalDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField journalSubject;

	public AddJournalDialog(JFrame owner) {
		setTitle("일기장 추가");
		setBounds(100, 100, 545, 411);
		setVisible(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		FileDialog addImageDialog = new FileDialog(this, "이미지 첨부",	FileDialog.LOAD);
		
		journalSubject = new JTextField();
		journalSubject.setBounds(55, 10, 320, 21);
		contentPanel.add(journalSubject);
		journalSubject.setColumns(10);
		
			JSpinner journalDate = new JSpinner();
			journalDate.setModel(new SpinnerDateModel());
			journalDate.setEditor(new JSpinner.DateEditor(journalDate, "yyyy년MM월dd일"));
			journalDate.setBounds(388, 10, 129, 21);
			contentPanel.add(journalDate);
		
			
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(12, 41, 505, 288);
				contentPanel.add(scrollPane);
				
					JTextArea journalText = new JTextArea();
					scrollPane.setViewportView(journalText);
					journalText.setLineWrap(true);
			
		
		
		JLabel label = new JLabel("제 목");
		label.setBounds(20, 10, 35, 21);
		contentPanel.add(label);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
				JLabel fileDirectionLabel = new JLabel("");
				buttonPane.add(fileDirectionLabel);
			
			{
				JButton addImageBtn = new JButton("이미지 첨부");
				addImageBtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						addImageDialog.setVisible(true);
						//fileDirectionLabel.setText(addImageDialog.getFile());
					}
				});
				buttonPane.add(addImageBtn);
			}
			{
				JButton confirmBtn = new JButton("확 인");
				confirmBtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						System.out.println(journalDate.getValue().toString());
						System.out.println(journalSubject.getText());
						System.out.println(journalText.getText());
						System.out.println(addImageDialog.getFile());
					}
				});
				confirmBtn.setActionCommand("OK");
				buttonPane.add(confirmBtn);
				getRootPane().setDefaultButton(confirmBtn);
			}
			{
				JButton cancelBtn = new JButton("취 소");
				cancelBtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dispose();
					}
				});
				cancelBtn.setActionCommand("Cancel");
				buttonPane.add(cancelBtn);
			}
		}
	}
}
