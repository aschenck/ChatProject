package user;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientSendThread implements Runnable
{
	private int outPort;
	private String ip, message;
	
	public ClientSendThread(int outPort, String ip)
	{
		this.outPort = outPort;
		this.ip = ip;
		this.message = "";
	}
	
	public void run()
	{	
		System.out.println("Send thread started!");
		try
		{			
			//String sentence = GUI.GUI.b.txtConversation.getText();;
			
			Socket clientSocket = new Socket(ip, this.outPort);
			if(!getMessage().equals(""))
			{
				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
				outToServer.writeBytes(getMessage() + '\n');
				setMessage("");
			}
		}
		catch (IOException e)
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
