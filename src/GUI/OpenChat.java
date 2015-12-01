package GUI;

import javax.swing.*;

import user.Client;

public class OpenChat extends JFrame {
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	private String username;
	private String friendname;
	private MessageBox m;
	
	
	
	public OpenChat(String UserName, String friendName)
	{
		friendname = friendName;
		username = UserName;
	
		
		
		m = new MessageBox();
		m.setUsername(username);
		m.setFriendname(friendname);
		
		JFrame frame = new GUI();
		frame.setSize(600, 400);
		//frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setDefaultCloseOperation(frame.HIDE_ON_CLOSE);
		frame.setTitle("Chat with " + friendname);	
		frame.setContentPane(m);
		frame.setVisible(true);
		
		
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
	public MessageBox getM() {
		return m;
	}

	public void setM(MessageBox m) {
		this.m = m;
	}
	
	
}



