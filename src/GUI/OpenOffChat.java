package GUI;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import user.Client;

public class OpenOffChat extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String friendname;
	private String message;
	private OffMessageBox m;
	private JFrame frame = new GUI();
	
	public OpenOffChat(String mess,boolean a)
	{
		message = mess;
		
		m = new OffMessageBox(message);	
		
		frame.setSize(450, 300);
		//frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setTitle("Offline Chat");	
		frame.setContentPane(m);
		frame.setVisible(a);
	}
	
	public void setText(String text){
		m.txtConversation.append(text);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void showMBox(boolean a){
		frame.setVisible(a);
	}
	public void dropDown(){
		m.dropDown();
	}	
	public String getFriendname() {
		return friendname;
	}

	public void setFriendname(String friendname) {
		this.friendname = friendname;
	}
}