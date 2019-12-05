package scheduleDialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListModel;

public class CheckFocus extends JFrame {

    JList<String> focusedList = null;
    JList<String> list1 = new JList<>(new String[]{"A", "B"});
    JList<String> list2 = new JList<>(new String[]{"1", "2"});
    JList<String> list3;
    		
    DefaultListModel<String> model;
    CheckFocus() {
    	model= new DefaultListModel<String>();
    	for(int i=0; i<5; i++) {
    		model.addElement(""+i);
    	}   
    	list3 = new JList<>(model);
    	
   
        JButton btn = new JButton("Who has focus?");

        btn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {    

                if (focusedList.equals(list1))
                    System.out.println("list1");
                else if (focusedList.equals(list2))
                    System.out.println("list2");
                else
                    System.out.println("none");
            }
        });

        MyFocusListener mfl = new MyFocusListener();
        
        list1.addFocusListener(mfl);
        list2.addFocusListener(mfl);
        list3.addFocusListener(mfl);
        getContentPane().add(list1, BorderLayout.LINE_START);
        getContentPane().add(list2, BorderLayout.LINE_END);
        getContentPane().add(list3, BorderLayout.SOUTH);
        getContentPane().add(btn, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {

        new CheckFocus();
    }


    class MyFocusListener extends FocusAdapter {
    	
        @Override
        public void focusGained(FocusEvent e) {
        	focusedList = (JList<String>) e.getSource();
     
        }
        
        @Override
        public void focusLost(FocusEvent e) {
        	JList OutOfFocus = (JList<String>) e.getSource();
        	
        	System.out.println(OutOfFocus.getSelectedValue());
        	OutOfFocus.clearSelection();
        }
        
        
    }
}