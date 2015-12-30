package GUI;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import user.Client;
/**
 * This class opens a new interface for each individual chat session
 * 
 * @author Anthony, Willem, Frederik
 * @version 1.0
 */

public class OpenChat extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String friendname;
	private MessageBox m;
	private JFrame frame = new GUI();
	
	/**
	 * Constructor of the class
	 * 
	 * @param UserName
	 * @param friendName
	 * @param client
	 * @param a
	 * @param online
	 * @param openedFromInvite
	 */
	public OpenChat(String UserName, String friendName, Client client, boolean a, boolean online, boolean openedFromInvite)
	{
		friendname = friendName;
		username = UserName;		
		
		m = new MessageBox(username,friendname, client, online, openedFromInvite);	
		
		frame.setSize(600, 400);
		//frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setTitle("Chat with " + friendname);	
		frame.setContentPane(m);
		frame.setVisible(a);	
		frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.out.println("Closed");
                m.sendCloseMessage();
                client.closeChatConnection(friendName);
                e.getWindow().dispose();
            }
        });
	}
	
	/**
	 * Getters and setters of the class
	 */
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