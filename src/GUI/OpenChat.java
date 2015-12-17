package GUI;


import javax.swing.*;
import user.Client;

public class OpenChat extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String friendname;
	public String getFriendname() {
		return friendname;
	}

	public void setFriendname(String friendname) {
		this.friendname = friendname;
	}

	private MessageBox m;
	private JFrame frame = new GUI();
	
	
	
	public OpenChat(String UserName, String friendName, Client client, boolean a, boolean online)
	{
		friendname = friendName;
		username = UserName;
		
		
		m = new MessageBox(username,friendname,client);	
		
		frame.setSize(600, 400);
		//frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		frame.setTitle("Chat with " + friendname);	
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
	public MessageBox getM() {
		return m;
	}

	public void setM(MessageBox m) {
		this.m = m;
	}
	
	public void showMBox(boolean a){
		frame.setVisible(a);
	}	
}