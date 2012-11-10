
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;

public class startScreen extends JFrame implements ActionListener {
	JPanel pane = new JPanel();
	JLabel label = new JLabel("Select level of difficulty");
	JButton button = new JButton("Easy");
	JButton button1 = new JButton("Hard");
	startScreen(){
		super("Start Screen");
		setBounds(150,100,200,150);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		button.addActionListener(this);
		button1.addActionListener(this);
		Container con = this.getContentPane();
		con.add(pane); pane.add(label);
		pane.add(button); pane.add(button1);
		setVisible (true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if (source == button){
			setVisible(false);
			RunBitMainEASY.main(null);
		}
		if (source == button1){
			setVisible(false);
			RunBitMain.main(null);
		}
	}
	public static void main(String args[]){
		new startScreen();

	}

	public static void main(Object args) {
		// TODO Auto-generated method stub
		
	}
}
