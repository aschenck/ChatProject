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
	}
	
	public void run()
	{	
		System.out.println("Send thread started!");
		try
		{			
			String sentence = GUI.GUI.b.txtConversation.getText();;
			
			Socket clientSocket = new Socket(ip, this.outPort);
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			outToServer.writeBytes(sentence + '\n');
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
