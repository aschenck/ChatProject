package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.crypto.ExemptionMechanismSpi;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import user.Client;
import user.ClientListenerThread;
import user.ClientSendThread;




	public class BorderLayoutPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public Client cl; 
	

		public ActionListener handler = new KnopHandler();
		public void setTextArea(String myString){
			 	txtConversation.append(myString);
			}
		
		public String readTextArea(){	 	
		 	return txtTextToSend.getText();
		}
		
		private JPanel optionPanel, chatPanel;
		
		//OPTIONPANEL
		private JLabel lblUsername, lblPassword, lblFirstName, lblLastName;
		private JTextField txtUsername, txtFirstName, txtLastName;
		private String userName, friendName;
		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getFriendName() {
			return friendName;
		}

		public void setFriendName(String friendName) {
			this.friendName = friendName;
		}

		private JPasswordField txtPassword;
		private JButton btnLoginMenu, btnSignUpMenu, btnExit, btnLogin, btnSignUp, btnBack;
		private JButton btnStartChat, btnAddFriend, btnDeleteFriend, btnCheckInvites, btnLogout;

		@SuppressWarnings("rawtypes")
		private JList lFriendList = new JList();
		@SuppressWarnings("rawtypes")
		private DefaultListModel lmFriendList = new DefaultListModel();
		
		private JButton btnDelete, btnChat, btnAdd, btnBackMenu;
		
		private JTextField txtAddFriend;
		private String user;
		//CHATPANEL
		private JTextField txtTextToSend;
		public JTextArea txtConversation;

		public ArrayList<OpenChat> chats = new ArrayList();
		
		
		
		private JButton btnSendMessage, btnLeaveConversation;
		{
		
		//CLIENT CONNECTION
		try
		{
			cl = new Client(0, 0);
		}
		catch(UnknownHostException e)
		{
		    e.printStackTrace();  				
		}
		}
		
		//SERVER CONNECTION
		
		
		//@SuppressWarnings({ "unchecked", "rawtypes" })
		public BorderLayoutPanel() {
			setLayout(new FlowLayout(FlowLayout.CENTER));
			
			optionPanel = new JPanel();
			chatPanel = new JPanel();
			
			optionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			chatPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			
			optionPanel.setPreferredSize(new Dimension(600, 400));
			chatPanel.setPreferredSize(new Dimension(600, 400));

			add(optionPanel);
			add(chatPanel);

			chatPanel.setVisible(false);			

			//OPTIONPANEL
			optionPanel.add(lblUsername = new JLabel("Username"));
			optionPanel.add(txtUsername = new JTextField());
			optionPanel.add(lblPassword = new JLabel("Password"));
			optionPanel.add(txtPassword = new JPasswordField());
			optionPanel.add(lblFirstName = new JLabel("First name"));
			optionPanel.add(txtFirstName = new JTextField());
			optionPanel.add(lblLastName = new JLabel("Last name"));
			optionPanel.add(txtLastName = new JTextField());
			optionPanel.add(btnLoginMenu = new JButton("Login"));
			optionPanel.add(btnSignUpMenu = new JButton("Sign up"));
			optionPanel.add(btnExit = new JButton("Exit"));
			optionPanel.add(btnLogin = new JButton("Login"));
			optionPanel.add(btnSignUp = new JButton("Sign up"));
			optionPanel.add(btnBack = new JButton("Back"));
			
			optionPanel.add(btnStartChat = new JButton("Start chat"));
			optionPanel.add(btnAddFriend = new JButton("Add friend"));
			optionPanel.add(btnDeleteFriend = new JButton("Delete friend"));
			optionPanel.add(btnCheckInvites = new JButton("Check invites"));
			optionPanel.add(btnLogout = new JButton("Logout"));
			
			optionPanel.add(lFriendList = new JList(lmFriendList));
			optionPanel.add(btnDelete = new JButton("Delete"));
			optionPanel.add(btnChat = new JButton("Chat"));
			optionPanel.add(txtAddFriend = new JTextField());
			optionPanel.add(btnAdd = new JButton("Add"));
			optionPanel.add(btnBackMenu = new JButton("Back"));
			
			lblUsername.setPreferredSize(new Dimension(300, 25));
			lblUsername.setHorizontalAlignment(JLabel.CENTER);
			lblUsername.setVerticalAlignment(JLabel.BOTTOM);
			txtUsername.setPreferredSize(new Dimension(300, 25));
			txtUsername.setHorizontalAlignment(JTextField.CENTER);
			lblPassword.setPreferredSize(new Dimension(300, 25));
			lblPassword.setHorizontalAlignment(JLabel.CENTER);
			lblPassword.setVerticalAlignment(JLabel.BOTTOM);
			txtPassword.setPreferredSize(new Dimension(300, 25));
			txtPassword.setHorizontalAlignment(JTextField.CENTER);
			lblFirstName.setPreferredSize(new Dimension(300, 25));
			lblFirstName.setHorizontalAlignment(JLabel.CENTER);
			lblFirstName.setVerticalAlignment(JLabel.BOTTOM);
			txtFirstName.setPreferredSize(new Dimension(300, 25));
			txtFirstName.setHorizontalAlignment(JTextField.CENTER);
			lblLastName.setPreferredSize(new Dimension(300, 25));
			lblLastName.setHorizontalAlignment(JLabel.CENTER);
			lblLastName.setVerticalAlignment(JLabel.BOTTOM);
			txtLastName.setPreferredSize(new Dimension(300, 25));
			txtLastName.setHorizontalAlignment(JTextField.CENTER);
			btnLoginMenu.setPreferredSize(new Dimension(300, 40));
			btnSignUpMenu.setPreferredSize(new Dimension(300, 40));
			btnExit.setPreferredSize(new Dimension(300, 40));
			btnLogin.setPreferredSize(new Dimension(300, 40));
			btnSignUp.setPreferredSize(new Dimension(300, 40));
			btnBack.setPreferredSize(new Dimension(300, 40));
			
			btnStartChat.setPreferredSize(new Dimension(300, 40));
			btnAddFriend.setPreferredSize(new Dimension(300, 40));
			btnDeleteFriend.setPreferredSize(new Dimension(300, 40));
			btnCheckInvites.setPreferredSize(new Dimension(300, 40));
			btnLogout.setPreferredSize(new Dimension(300, 40));
			
			lFriendList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			lFriendList.setLayoutOrientation(JList.VERTICAL);
			lFriendList.setPreferredSize(new Dimension(300, 80));
			btnDelete.setPreferredSize(new Dimension(300, 40));
			btnChat.setPreferredSize(new Dimension(300, 40));
			txtAddFriend.setPreferredSize(new Dimension(300, 25));
			txtAddFriend.setHorizontalAlignment(JTextField.CENTER);
			btnAdd.setPreferredSize(new Dimension(300, 40));
			btnBackMenu.setPreferredSize(new Dimension(300, 40));

			lblUsername.setVisible(false);
			txtUsername.setVisible(false);
			lblPassword.setVisible(false);
			txtPassword.setVisible(false);
			lblFirstName.setVisible(false);
			txtFirstName.setVisible(false);
			lblLastName.setVisible(false);
			txtLastName.setVisible(false);
			btnLogin.setVisible(false);
			btnSignUp.setVisible(false);
			btnBack.setVisible(false);
			
			btnStartChat.setVisible(false);
			btnAddFriend.setVisible(false);
			btnDeleteFriend.setVisible(false);
			btnCheckInvites.setVisible(false);
			btnLogout.setVisible(false);
			
			lFriendList.setVisible(false);
			btnDelete.setVisible(false);
			btnChat.setVisible(false);
			txtAddFriend.setVisible(false);
			btnAdd.setVisible(false);
			btnBackMenu.setVisible(false);
			
			btnLoginMenu.addActionListener(handler);
			btnSignUpMenu.addActionListener(handler);
			btnExit.addActionListener(handler);
			btnLogin.addActionListener(handler);
			btnSignUp.addActionListener(handler);
			btnBack.addActionListener(handler);
			
			btnStartChat.addActionListener(handler);
			btnAddFriend.addActionListener(handler);
			btnDeleteFriend.addActionListener(handler);
			btnCheckInvites.addActionListener(handler);
			btnLogout.addActionListener(handler);
			
			btnDelete.addActionListener(handler);
			btnChat.addActionListener(handler);
			btnAdd.addActionListener(handler);
			btnBackMenu.addActionListener(handler);
			
			
			//CHATPANEL
			chatPanel.add(btnLeaveConversation = new JButton("Leave conversation"));
			chatPanel.add(txtConversation = new JTextArea());
			chatPanel.add(btnSendMessage = new JButton("Send message"));
			chatPanel.add(txtTextToSend = new JTextField());
			
			
			btnLeaveConversation.setPreferredSize(new Dimension(400, 25));
			txtConversation.setPreferredSize(new Dimension(400, 225));
			txtConversation.setEditable(false);
			btnSendMessage.setPreferredSize(new Dimension(400, 25));
			txtTextToSend.setPreferredSize(new Dimension(400, 25));
			txtTextToSend.setHorizontalAlignment(JTextField.CENTER);
			
			btnLeaveConversation.addActionListener(handler);
			btnSendMessage.addActionListener(handler);
			

			
		}
		
		class KnopHandler implements ActionListener {
			
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
	            

	        	
				//Naar het login menu gaan
	        	if (e.getSource() == btnLoginMenu)
	            {
	            		
	            	
	            	lblUsername.setVisible(true);
	        		txtUsername.setVisible(true);
	        		lblPassword.setVisible(true);
	        		txtPassword.setVisible(true);
	            	btnLoginMenu.setVisible(false);
	            	btnSignUpMenu.setVisible(false);
	            	btnExit.setVisible(false);
	            	btnLogin.setVisible(true);
	        		btnSignUp.setVisible(false);
	        		btnBack.setVisible(true);
	        		
	        		
	        		
	            }
	        	
	        	//Naar het sign in menu gaan
	            if (e.getSource() == btnSignUpMenu)
	            {
	            	lblUsername.setVisible(true);
	        		txtUsername.setVisible(true);
	        		lblPassword.setVisible(true);
	        		txtPassword.setVisible(true);
	        		lblFirstName.setVisible(true);
	        		txtFirstName.setVisible(true);
	        		lblLastName.setVisible(true);
	        		txtLastName.setVisible(true);
	            	btnLoginMenu.setVisible(false);
	            	btnSignUpMenu.setVisible(false);
	            	btnExit.setVisible(false);
	            	btnLogin.setVisible(false);
	        		btnSignUp.setVisible(true);
	        		btnBack.setVisible(true);
	            }
	            
	            //Programma sluiten
	            if (e.getSource() == btnExit)
	            {
	            	System.exit(0);
	            }
	            
	            /*
	             * 
	             * Hier gaan we proberen inteloggen, dus aan de RMI 
	             * een true boolean aanvragen
	             * 
	             * 
	             */
	            if (e.getSource() == btnLogin)
	            {
	            	user = txtUsername.getText();
	            	setUserName(user);
	            	char[] pass = txtPassword.getPassword();
	            	// if(loginRMI(txt.userName.trim(), txt.Paswword.trim())
	            	try
					{
						if (cl.connectToServer(user, pass)) {						
							
							//OPENEN SOCKET
							//RMI JUIST SOCKETS VRAGE
							//cl.setInPort(getsetInPort(txt.userName.trim()));
							//cl.setOutPort(getsetOutPort(txt.userName.trim()));    
							//cl.setInPort(5000);
							//cl.setOutPort(5001);
							//new Thread(new ClientListenerThread(cl.getInPort())).start();							
							lblUsername.setVisible(false);
							txtUsername.setVisible(false);
							lblPassword.setVisible(false);
							txtPassword.setVisible(false);
							btnLoginMenu.setVisible(false);
							btnSignUpMenu.setVisible(false);
							btnExit.setVisible(false);
							btnLogin.setVisible(false);
							btnSignUp.setVisible(false);
							btnBack.setVisible(false);
							btnStartChat.setVisible(true);
							btnAddFriend.setVisible(true);
							btnDeleteFriend.setVisible(true);
							btnCheckInvites.setVisible(true);
							btnLogout.setVisible(true);
							
							
						}
					} catch (UnknownHostException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}       	
	            }
	            if (e.getSource() == btnSignUp)
	            {
	            	String user = txtUsername.getText();
	            	char[] pass = txtPassword.getPassword();
	            	String fName = txtFirstName.getText();
	            	String lName = txtLastName.getText();
	            	
	            	if (cl.newUser(user, fName, lName, pass)) {
	            		lblUsername.setVisible(true);
	            		txtUsername.setVisible(true);
	            		lblPassword.setVisible(true);
	            		txtPassword.setVisible(true);
	            		lblFirstName.setVisible(false);
	            		txtFirstName.setVisible(false);
	            		lblLastName.setVisible(false);
	            		txtLastName.setVisible(false);
	                	btnLoginMenu.setVisible(false);
	                	btnSignUpMenu.setVisible(false);
	                	btnExit.setVisible(false);
	                	btnLogin.setVisible(true);
	            		btnSignUp.setVisible(false);
	            		btnBack.setVisible(true);
	            	}

	            }
	            if (e.getSource() == btnBack)
	            {
	            	lblUsername.setVisible(false);
	        		txtUsername.setVisible(false);
	        		lblPassword.setVisible(false);
	        		txtPassword.setVisible(false);
	        		lblFirstName.setVisible(false);
	        		txtFirstName.setVisible(false);
	        		lblLastName.setVisible(false);
	        		txtLastName.setVisible(false);
	            	btnLoginMenu.setVisible(true);
	            	btnSignUpMenu.setVisible(true);
	            	btnExit.setVisible(true);
	            	btnLogin.setVisible(false);
	        		btnSignUp.setVisible(false);
	        		btnBack.setVisible(false);
	            }
	            if (e.getSource() == btnStartChat)
	            {
	            	lblUsername.setVisible(false);
	        		txtUsername.setVisible(false);
	        		lblPassword.setVisible(false);
	        		txtPassword.setVisible(false);
	            	btnLoginMenu.setVisible(false);
	            	btnSignUpMenu.setVisible(false);
	            	btnExit.setVisible(false);
	            	btnLogin.setVisible(false);
	        		btnSignUp.setVisible(false);
	        		btnBack.setVisible(false);
	        		btnStartChat.setVisible(false);
	        		btnAddFriend.setVisible(false);
	        		btnDeleteFriend.setVisible(false);
	        		btnCheckInvites.setVisible(false);
	        		btnLogout.setVisible(false);
	        		
	        		
	        		lFriendList.setVisible(true);
	        		lmFriendList.clear();
	        		lmFriendList.addElement("Willem");
	        		lmFriendList.addElement("Frederik");
	        		
	        		
	        		/*
	        		lmFriendList.clear();
	            	Iterator i = cl.getFriendList().iterator();
	            	while (i.hasNext()) {
	            		lmFriendList.addElement(i.next());
	            	}
	            	if(!lmFriendList.isEmpty()) {
	            		lFriendList.setModel(lmFriendList);
	            	}
	            	*/
	        		
	        		btnChat.setVisible(true);
	        		btnBackMenu.setVisible(true);
	            }
	            if (e.getSource() == btnAddFriend)
	            {
	            	lblUsername.setVisible(false);
	        		txtUsername.setVisible(false);
	        		lblPassword.setVisible(false);
	        		txtPassword.setVisible(false);
	            	btnLoginMenu.setVisible(false);
	            	btnSignUpMenu.setVisible(false);
	            	btnExit.setVisible(false);
	            	btnLogin.setVisible(false);
	        		btnSignUp.setVisible(false);
	        		btnBack.setVisible(false);
	        		btnStartChat.setVisible(false);
	        		btnAddFriend.setVisible(false);
	        		btnDeleteFriend.setVisible(false);
	        		btnCheckInvites.setVisible(false);
	        		btnLogout.setVisible(false);
	            	
	            	txtAddFriend.setVisible(true);
	            	btnAdd.setVisible(true);
	            	btnBackMenu.setVisible(true);
	            }
	            if (e.getSource() == btnDeleteFriend)
	            {
	            	lblUsername.setVisible(false);
	        		txtUsername.setVisible(false);
	        		lblPassword.setVisible(false);
	        		txtPassword.setVisible(false);
	            	btnLoginMenu.setVisible(false);
	            	btnSignUpMenu.setVisible(false);
	            	btnExit.setVisible(false);
	            	btnLogin.setVisible(false);
	        		btnSignUp.setVisible(false);
	        		btnBack.setVisible(false);
	        		btnStartChat.setVisible(false);
	        		btnAddFriend.setVisible(false);
	        		btnDeleteFriend.setVisible(false);
	        		btnCheckInvites.setVisible(false);
	        		btnLogout.setVisible(false);
	        		
	        		lFriendList.setVisible(true);
	        		lmFriendList.clear();
	        		lmFriendList.addElement("teste");
	        		lmFriendList.addElement("teste2");
	        		
	        		btnDelete.setVisible(true);
	        		btnBackMenu.setVisible(true);
	            }
	            if (e.getSource() == btnCheckInvites)
	            {
	            	lblUsername.setVisible(false);
	        		txtUsername.setVisible(false);
	        		lblPassword.setVisible(false);
	        		txtPassword.setVisible(false);
	            	btnLoginMenu.setVisible(false);
	            	btnSignUpMenu.setVisible(false);
	            	btnExit.setVisible(false);
	            	btnLogin.setVisible(false);
	        		btnSignUp.setVisible(false);
	        		btnBack.setVisible(false);
	        		btnStartChat.setVisible(false);
	        		btnAddFriend.setVisible(false);
	        		btnDeleteFriend.setVisible(false);
	        		btnCheckInvites.setVisible(false);
	        		btnLogout.setVisible(false);
	        		
	        		/*
	        		inviteList = Invites.ShowAllInvites();

	        		lFriendList.setVisible(true);
	        		lmFriendList.clear();
	        		for (int i = 0; i < inviteList.size(); i++) {
	        			lmFriendList.addElement(inviteList.get(i));
	        		}
	        		
	        		*/
	        		
	        		btnChat.setVisible(true);
	        		btnBackMenu.setVisible(true);
	            }
	            if (e.getSource() == btnLogout)
	            {
	            	lblUsername.setVisible(false);
	        		txtUsername.setVisible(false);
	        		lblPassword.setVisible(false);
	        		txtPassword.setVisible(false);
	            	btnLoginMenu.setVisible(true);
	            	btnSignUpMenu.setVisible(true);
	            	btnExit.setVisible(true);
	            	btnLogin.setVisible(false);
	        		btnSignUp.setVisible(false);
	        		btnBack.setVisible(false);
	        		btnStartChat.setVisible(false);
	        		btnAddFriend.setVisible(false);
	        		btnDeleteFriend.setVisible(false);
	        		btnCheckInvites.setVisible(false);
	        		btnLogout.setVisible(false);
	        		
	        		txtUsername.setText("");
	        		txtPassword.setText("");
	            }
	            
	            if (e.getSource() == btnDelete) {
	            	String friendname;
	            	Iterator i = cl.getFriendList().iterator();
	            	while (i.hasNext()) {
	            		lmFriendList.addElement(i.next());
	            	}
	            	lFriendList.setModel(lmFriendList);
	            	try{
	            		System.out.println(lFriendList.getSelectedValue().toString());
	            		friendname = lFriendList.getSelectedValue().toString();
	            		cl.deleteFriend(friendname);
	            	} 
	            	catch(Exception error){
	            		
	            		friendname = "";
	            		System.out.println(error);
	            	}
	            }
	            
	            if (e.getSource() == btnAdd) {
	            	String friendname;
	            	friendname = txtAddFriend.getText();
	            	try{
	            		cl.addFriend(friendname);
	            	} 
	            	catch(Exception error){
	            		
	            		friendname = "";
	            		System.out.println(error);
	            	}
	            }
	            
	            if (e.getSource() == btnBackMenu) {
	            	btnAddFriend.setVisible(true);
	            	btnStartChat.setVisible(true);
	            	btnDeleteFriend.setVisible(true);
	            	btnCheckInvites.setVisible(true);
	            	btnLogout.setVisible(true);
	            	lFriendList.setVisible(false);
	            	btnChat.setVisible(false);
	            	btnDelete.setVisible(false);
	            	txtAddFriend.setVisible(false);
	            	btnAdd.setVisible(false);
	            	btnBackMenu.setVisible(false);
	            }
	            
	            if (e.getSource() == btnChat) {
	            	String friendname;

	            	try{
	            		System.out.println(lFriendList.getSelectedValue().toString());
	            		friendname = lFriendList.getSelectedValue().toString();
	            		setFriendName(friendname);
	            	} 
	            	catch(Exception error){
	            		
	            		friendname = "";
	            		System.out.println(error);
	            	}
	            	
	            	if(friendname != ""){	
	            		//TODO
	            		boolean exsitance = false;
	            		OpenChat chatE = null;
	            		
	            		for(int i1=0; i1<GUI.b.chats.size();i1++){
	                		if(GUI.b.chats.get(i1).getFriendname() == friendname){
	                			chatE = GUI.b.chats.get(i1);
	                			exsitance = true;
	                		}
	                	}    
	            		
	            		if(exsitance == true){
	            			chatE.showMBox(true);
	            		}
	            		else{
	            			OpenChat chat = new OpenChat(user,friendname,cl,true);			        		
			        		chats.add(chat);
	            		}
	            		
	            		
	            		//GET IP WITH RMI            		
	            /*		String ip = "localhost";
	            		
	            		cl.setIp(ip);
	            		optionPanel.setVisible(false);
	            		chatPanel.setVisible(true);
	            		txtConversation.append("Connectie aanvraag gestuurd naar " + friendname + '\n');
	            		txtConversation.append("Wachten op antwoord...." + '\n');*/
	            	}
	            	
	            	
	            }
	            
	            if (e.getSource() == btnLeaveConversation) {
	            	optionPanel.setVisible(true);
	            	chatPanel.setVisible(false);
	            }
	            if (e.getSource() == btnSendMessage) {
	            	if(txtTextToSend.getText().length()>0){
		            	String message = "<" + txtUsername.getText() + ">" + txtTextToSend.getText();
		            	txtConversation.append(message + '\n');
		            	txtTextToSend.setText("");
	            	}
	            }
	        }
		}
	}

