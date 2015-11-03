package user;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientSendThread implements Runnable
{
	private int outPort;	
	
	public ClientSendThread(int outPort)
	{
		this.outPort = outPort;
	}
	
	public void run()
	{	
		System.out.println("Send thread started!");
		try
		{	
			while(true)
			{
				String sentence;
				BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
				Socket clientSocket = new Socket("localhost", this.outPort);
				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
				sentence = inFromUser.readLine();
				outToServer.writeBytes(sentence + '\n');
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
