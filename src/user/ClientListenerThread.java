package user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientListenerThread implements Runnable
{	
	private int inPort;
	private String rxText;
	
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
			while(true)
			{
				Socket connectionSocket = sock.accept();
//				System.out.println("Someone wants to chat");
//				System.out.println("Type 'connect' to chat");
	            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));  
	            setRxText(inFromClient.readLine());
	            System.out.println("Received: " + getRxText());     
  			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			


	}

}
