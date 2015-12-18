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
	private boolean connected;
	
	public ClientSendThread(int outPort, InetAddress inetAddress)
	{
		this.outPort = outPort;
		this.ip = inetAddress;
		this.message = "";
		connected = true;
	}
	
	public void run()
	{	
		System.out.println("Send thread started!");
		try
		{			
			//String sentence = GUI.GUI.b.txtConversation.getText();;
			
			clientSocket = new Socket(ip, this.outPort);
			System.out.println("SendThread: OutPort: " +this.outPort);
			while(connected)
			{
				if(!getMessage().equals(""))
				{
					if(!getMessage().substring(0, 4).equals("1111"))
					{
						DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
						outToServer.writeBytes(getMessage() + '\n');
						System.out.println("SendThread: MESSAGE SENT" + getMessage());
						setMessage("");
					}
					else
					{
						CloseSocket();
					}
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
			connected = false;
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
