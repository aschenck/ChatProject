package user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
	
	public ArrayList<String> getInviteList()
	{
		return inviteList;
	}

	public void setInviteList(ArrayList<String> inviteList)
	{
		this.inviteList = inviteList;
	}

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
		inviteList = new ArrayList<>();
	}
	
	public void run()
	{
		System.out.println("Listen thread started!");
		ServerSocket sock;
		try
		{
			sock = new ServerSocket(this.inPort);
			Socket connectionSocket = sock.accept();
			System.out.println(this.inPort);
			while(true)
			{
				//Socket connectionSocket = sock.accept();
	            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); 
	            String message = inFromClient.readLine();
	            setRxText(message);	            
	            System.out.println("Received: " + getRxText());
	            
	            if (message.substring(0, 4).equals(inviteCode)) {
	            	inviteUsername = message.substring(5);
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
	            else if (message.substring(0, 4).equals(chatCode)) {
	            	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            		String current = "["+sdf.format(System.currentTimeMillis())+"]";
	            	message = message.substring(5);
	            	System.out.println(message);
	            	String name = message.substring(1, message.indexOf('>'));
		            for(int i=0; i<GUI.b.chats.size();i++){
	            		if(GUI.b.chats.get(i).getFriendname().equals(name)){
	            			GUI.b.chats.get(i).setText(current+message + '\n');
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
