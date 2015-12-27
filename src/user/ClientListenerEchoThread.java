package user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import GUI.GUI;
/**
 * Class that creates separate listen threads for each active chat session
 * 
 * @author Anthony
 *
 */
public class ClientListenerEchoThread extends Thread
{
	//the socket used for the connection
	protected Socket socket;
	//codes determining the type of message received
	private String inviteCode = "0011";
	private String chatCode = "0001";
	private String closeCode = "1111";
	//the user that invites you to a chat
	private String inviteUsername;
	//is it a new invite?
	private boolean newInvite;	
	//list of open invites
	private ArrayList<String> inviteList;
	//the parent thread, needed for managing the global invitelist
	private ClientListenerThread parent;
	
	/**
	 * the constructor of this class
	 * 
	 * @param clientSocket sets the socket
	 * @param parent the class that started this thread
	 */
	public ClientListenerEchoThread(Socket clientSocket, ClientListenerThread parent)
	{
		this.socket = clientSocket;
		inviteList = new ArrayList<>();
		this.parent = parent;
	}
	/**
	 * Code that is executed when the thread is started.
	 * Creates a buffered reader to receive incoming messages
	 * The thread keeps checking if any new messages have arrived until it receives the closecode "1111" 
	 */
	public void run()
	{		
		BufferedReader inFromClient = null;
		try
		{
			  inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream())); 			 
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
