package user;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import GUI.GUI;

public class ClientListenerEchoThread extends Thread
{
	protected Socket socket;
	private String inviteCode = "0011";
	private String chatCode = "0001";
	private String closeCode = "1111";
	private String inviteUsername;
	private boolean newInvite;	
	private ArrayList<String> inviteList;
	private ClientListenerThread parent;
	
	public ClientListenerEchoThread(Socket clientSocket, ClientListenerThread parent)
	{
		this.socket = clientSocket;
		inviteList = new ArrayList<>();
		this.parent = parent;
	}
	
	public void run()
	{
		//DataOutputStream out = null;
		BufferedReader inFromClient = null;
		try
		{
			  inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
			  //out = new DataOutputStream(socket.getOutputStream());
		}
		catch (IOException e) {
            e.printStackTrace();
            return;
        }		
		String message;
		while(true)
		{
			try
			{
				 message = inFromClient.readLine();
				 if(message != null && !message.isEmpty())
				 {
					 if (message.substring(0, 4).equals(inviteCode)) 
					 {//
						 inviteUsername = message.substring(5);
						 newInvite = true;
						for (int i = 0; i < inviteList.size(); i ++) 
						{
							if (inviteUsername.equals(inviteList.get(i))) 
							{
									newInvite = false;
							}
						}							
						if (newInvite)
						{							
							inviteList.add(inviteUsername);
							parent.setInviteList(inviteList);
						}				        	
			        }
			        else if (message.substring(0, 4).equals(chatCode)) 
			        {
			        	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			    		String current = "["+sdf.format(System.currentTimeMillis())+"]";
			        	message = message.substring(5);
			        	System.out.println(message);
			        	String name = message.substring(1, message.indexOf('>'));
			            for(int i=0; i<GUI.b.chats.size();i++)
			            {
			        		if(GUI.b.chats.get(i).getFriendname().equals(name))
			        		{
			        			GUI.b.chats.get(i).setText(current+message + '\n');
			        			GUI.b.chats.get(i).dropDown();
			        		}
			            }
			        }  
			        else if(message.substring(0, 4).equals(closeCode))
			        {
			        	socket.close();			        	
			        }
				}
			}
			catch (IOException e) 
			{
	            e.printStackTrace();
	            return;
	        }  
		}
	}
}
