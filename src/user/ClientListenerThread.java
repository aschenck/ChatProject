package user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import GUI.GUI;

public class ClientListenerThread implements Runnable
{	
	private int inPort;
	private String rxText;
	
	private ArrayList<String> inviteList;
	private String inviteUsername;
	private boolean newInvite;
	
	private String inviteCode = "0011";
	private String chatCode = "0001";
	
	public String getRxText()
	{
		return rxText;
	}

	public void setRxText(String rxText)
	{
		this.rxText = rxText;
	}

	public ClientListenerThread(int inPort)
	{
		this.inPort = inPort;
	}
	
	public void run()
	{
		System.out.println("Listen thread started!");
		ServerSocket sock;
		try
		{
			sock = new ServerSocket(this.inPort);
			System.out.println(this.inPort);
			while(true)
			{
				Socket connectionSocket = sock.accept();
	            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); 
	            String message = inFromClient.readLine();
	            setRxText(message);	            
	            System.out.println("Received: " + getRxText());
	            
	            if (message.substring(0, 3).equals(inviteCode)) {
	            	inviteUsername = message.substring(4);
	            	newInvite = true;
					for (int i = 0; i < inviteList.size(); i ++) {
						if (inviteUsername.equals(inviteList.get(i))) {
							newInvite = false;
						}
					}
					if (newInvite) {
						inviteList.add(inviteUsername);
					}
	            	
	            }
	            else if (message.substring(0, 3).equals(chatCode)) {
	            	message = message.substring(4);
	            	String name = message.substring(1, message.indexOf('>'));
		            for(int i=0; i<GUI.b.chats.size();i++){
	            		if(GUI.b.chats.get(i).getFriendname().equals(name)){
	            			GUI.b.chats.get(i).setText(getRxText() + '\n');
	            		}
	            	}
	            }        
  			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
