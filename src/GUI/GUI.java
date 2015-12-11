package GUI;

import javax.swing.*;

public class GUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static BorderLayoutPanel b = new BorderLayoutPanel();
	
	public static void main(String args[]) {
		
		JFrame frame = new GUI();
		frame.setSize(600, 400);
		//frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Chat application");	
		frame.setContentPane(b);
		frame.setVisible(true);
	}
}