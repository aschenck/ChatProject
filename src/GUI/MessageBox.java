package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import user.Client;

public class MessageBox extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel chatPanel;
	private String friendname;
	private boolean online;
	private boolean openFromInvite;
	
	public String getFriendname() {
		return friendname;
	}

	public void setFriendname(String friendname) {
		this.friendname = friendname;
	}

	private String username;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	//CHATPANEL
	private JTextField txtTextToSend;
	public JTextArea txtConversation;		

	private JButton btnSendMessage;
	private Client cl;				
			
	public ActionListener handler = new KnopHandler();
	

	public MessageBox(String un ,String fn, Client client, boolean online, boolean invite) 
	{
		username = un;
		friendname = fn;
		cl = client;
		cl.startChat(friendname);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setOnline(online);
		this.setOpenFromInvite(invite);
		this.online = online;
		
		chatPanel = new JPanel();
		
		chatPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		chatPanel.setPreferredSize(new Dimension(600, 400));

		add(chatPanel);
		
		txtConversation = new JTextArea(15,35);
		JScrollPane scroll = new JScrollPane (txtConversation);
		scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		chatPanel.add(scroll);
		chatPanel.add(btnSendMessage = new JButton("Send message"));
		chatPanel.add(txtTextToSend = new JTextField());
		
		//txtConversation.setPreferredSize(new Dimension(400, 225));
		txtConversation.setEditable(false);
		btnSendMessage.setPreferredSize(new Dimension(400, 25));
		txtTextToSend.setPreferredSize(new Dimension(400, 25));
		txtTextToSend.setHorizontalAlignment(JTextField.CENTER);
		
		btnSendMessage.addActionListener(handler);		
		
		if(!openFromInvite)
		{
			cl.sendMessage(friendname, "0011:" + cl.getUser());
		}
	}
	public void sendCloseMessage()
	{
		cl.sendMessage(friendname, "1111:");
	}
	
	public boolean isOpenFromInvite()
	{
		return openFromInvite;
	}

	public void setOpenFromInvite(boolean openFromInvite)
	{
		this.openFromInvite = openFromInvite;
	}

	public boolean isOnline()
	{
		return online;
	}

	public void setOnline(boolean online)
	{
		this.online = online;
	}
	
	public void dropDown()
	{
		txtConversation.setCaretPosition(txtConversation.getDocument().getLength());
	}

	class KnopHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
        
			if (e.getSource() == btnSendMessage) {
            	if(txtTextToSend.getText().length()>0)
            	{
            		if(online)
            		{
		            	String message = "<" + username + ">" + txtTextToSend.getText();
		            	System.out.println("0001:online" + message);    
	            		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	            		String current = sdf.format(System.currentTimeMillis());
		            	txtConversation.append("["+current+"]"+message + '\n');
		            	dropDown();
		            	cl.sendMessage(friendname,"0001:" + message);
		            	txtTextToSend.setText("");
            		}
            		else
            		{
		            	String message = "<" + username + ">" + txtTextToSend.getText();
		            	System.out.println("0001:" + message);    
	            		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	            		String current = sdf.format(System.currentTimeMillis());
		            	txtConversation.append("["+current+"]"+message + '\n');
		            	dropDown();
		            	cl.sendOfflineMessage(friendname, message+ '\n');
		            	txtTextToSend.setText("");
            			
            		}
            	}
            }
        }
	}
}

