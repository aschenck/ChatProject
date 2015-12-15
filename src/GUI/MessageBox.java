package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import user.Client;


	public class MessageBox extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JPanel chatPanel;
		private String friendname;
		public Client cl;

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
		{
		try
		{
			cl = new Client(0, 0);
		}
		catch(UnknownHostException e)
		{
		    e.printStackTrace();  				
		}
		}
				
				
		public ActionListener handler = new KnopHandler();
		
		public MessageBox() {
			
			cl.startChat(friendname);
			setLayout(new FlowLayout(FlowLayout.CENTER));
			
			chatPanel = new JPanel();
			
			chatPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			
			chatPanel.setPreferredSize(new Dimension(600, 400));

			add(chatPanel);
			
			chatPanel.add(txtConversation = new JTextArea());
			chatPanel.add(btnSendMessage = new JButton("Send message"));
			chatPanel.add(txtTextToSend = new JTextField());
			
			
			txtConversation.setPreferredSize(new Dimension(400, 225));
			txtConversation.setEditable(false);
			btnSendMessage.setPreferredSize(new Dimension(400, 25));
			txtTextToSend.setPreferredSize(new Dimension(400, 25));
			txtTextToSend.setHorizontalAlignment(JTextField.CENTER);
			
			btnSendMessage.addActionListener(handler);
			

			
		}
		
		class KnopHandler implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
            
				if (e.getSource() == btnSendMessage) {
	            	if(txtTextToSend.getText().length()>0){
		            	String message = "<" + username + ">" + txtTextToSend.getText();
		            	System.out.println(message);          	
		            	txtConversation.append(message + '\n');
		            	cl.sendMessage(friendname,"0001:" + message);
		            	txtTextToSend.setText("");
	            	}
	            }
	        }
		}
	}

