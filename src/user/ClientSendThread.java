package user;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSendThread implements Runnable
{
	private int outPort;
	private InetAddress ip;
	private String message;
	public Socket clientSocket;
	
	public ClientSendThread(int outPort, InetAddress inetAddress)
	{
		this.outPort = outPort;
		this.ip = inetAddress;
		this.message = "";
	}
	
	public void run()
	{	
		System.out.println("Send thread started!");
		try
		{			
			//String sentence = GUI.GUI.b.txtConversation.getText();;
			
			clientSocket = new Socket(ip, this.outPort);
			System.out.println("SendThread: OutPort: " +this.outPort);
			while(true)
			{
				if(!getMessage().equals(""))
				{
					DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
					outToServer.writeBytes(getMessage() + '\n');
					System.out.println("SendThread: MESSAGE SENT" + getMessage());
					setMessage("");
				}
			}			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("SendThread: EXIT FROM LOOP");
	}
	
	public void CloseSocket()
	{
		try
		{
			clientSocket.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}	
}
