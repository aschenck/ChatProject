package GUI;


import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.*;

import user.Client;
import user.Client2;
import user.ClientListenerThread;
import user.ClientSendThread;

public class GUI extends JFrame {
	
	public static void main(String args[]) {
		
		JFrame frame = new GUI();
		frame.setSize(600, 400);
		//frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Chat application");
		frame.setContentPane(new BorderLayoutPanel());
		frame.setVisible(true);
	}
}

public setTEXT(String a){
	
}
class BorderLayoutPanel extends JPanel {
	private JPanel optionPanel, chatPanel;
	
	//OPTIONPANEL
	private JLabel lblUsername, lblPassword;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JButton btnLoginMenu, btnSignUpMenu, btnExit, btnLogin, btnSignUp, btnBack;
	private JButton btnStartChat, btnAddFriend, btnDeleteFriend, btnCheckInvites, btnLogout;

	private JList lFriendList = new JList();
	private DefaultListModel lmFriendList = new DefaultListModel();
	
	private JButton btnDelete, btnChat, btnAdd, btnBackMenu;
	
	private JTextField txtAddFriend;
	
	//CHATPANEL
	private JTextField txtTextToSend;
	public JTextArea txtConversation;

	private JButton btnSendMessage, btnLeaveConversation;
	Client cl = new Client("IP", 0, 0);
	
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

		ActionListener handler = new KnopHandler();

		//OPTIONPANEL
		optionPanel.add(lblUsername = new JLabel("Username"));
		optionPanel.add(txtUsername = new JTextField());
		optionPanel.add(lblPassword = new JLabel("Password"));
		optionPanel.add(txtPassword = new JPasswordField());
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
        public void actionPerformed(ActionEvent e) {
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
            if (e.getSource() == btnSignUpMenu)
            {
            	lblUsername.setVisible(true);
        		txtUsername.setVisible(true);
        		lblPassword.setVisible(true);
        		txtPassword.setVisible(true);
            	btnLoginMenu.setVisible(false);
            	btnSignUpMenu.setVisible(false);
            	btnExit.setVisible(false);
            	btnLogin.setVisible(false);
        		btnSignUp.setVisible(true);
        		btnBack.setVisible(true);
            }
            if (e.getSource() == btnExit)
            {
            	System.exit(0);
            }
            if (e.getSource() == btnLogin)
            {
            	Client cl = new Client("IP", 5000, 5001);	
            	if (true) {
            		
            		
            		//OPENEN SOCKET
            		//RMI JUIST SOCKETS VRAGE
                	cl.setInPort(5000);
                	cl.setOutPort(5001);
            		new Thread(new ClientListenerThread(cl.getInPort())).start();	
            		
            		
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
            	else {
            		
            	}
            }
            if (e.getSource() == btnSignUp)
            {
            	if (true) {
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
            	else {
            		
            	}
            }
            if (e.getSource() == btnBack)
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

        		lFriendList.setVisible(true);
        		lmFriendList.clear();
        		lmFriendList.addElement("teste");
        		lmFriendList.addElement("teste2");
        		
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
            }
            
            if (e.getSource() == btnDelete) {
            	
            }
            
            if (e.getSource() == btnAdd) {
            	
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
            	String username;
            	
            	try{
            	System.out.println(lFriendList.getSelectedValue().toString());
            	username = lFriendList.getSelectedValue().toString();
            	} 
            	catch(Exception error){
            		username = "";
            		System.out.println(error);
            		}
            	
            	if(username != ""){	
            		
            		//GET IP WITH RMI            		
            		String ip = "localhost";
            		
            		cl.setIp(ip);

            		System.out.println("Trying to connect to " + ip);
            		new Thread(new ClientSendThread(cl.getOutPort(), cl.getIp())).start();	
            		
            		optionPanel.setVisible(false);
            		chatPanel.setVisible(true);
            	}
            	
            	
            }
            
            if (e.getSource() == btnLeaveConversation) {
            	optionPanel.setVisible(true);
            	chatPanel.setVisible(false);
            }
            if (e.getSource() == btnSendMessage) {
            	
            }
        }
	}
}