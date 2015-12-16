package GUI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class GUI extends JFrame {
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
		frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.out.println("Closed");
                b.Close();
                e.getWindow().dispose();
            }
        });
	}
}