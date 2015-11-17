package user;

import java.io.IOException;
import java.util.Scanner;

public class Client
{	
	private String ip;
	private int inPort;
	private int outPort;
	
	public Client(String ip, int inPort, int outPort)
	{
		this.ip = ip;
		this.inPort = inPort;
		this.outPort = outPort;		
	}
	
	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public int getInPort()
	{
		return inPort;
	}

	public void setInPort(int inPort)
	{
		this.inPort = inPort;
	}

	public int getOutPort()
	{
		return outPort;
	}

	public void setOutPort(int outPort)
	{
		this.outPort = outPort;
	}
	
	public static void main(String[] args) throws IOException
	{
		Scanner kb = new Scanner(System.in);
		Client cl = new Client("192.168.1.2", 5001, 5000);	
		new Thread(new ClientListenerThread(cl.getInPort())).start();		
		String input = "";
		input = kb.nextLine();
		/*
		if (input.matches("connect"))		
		{
			System.out.println("Trying to connect to client2");
			new Thread(new ClientSendThread(cl.getOutPort(), cl.getIp())).start();
		}
		*/		
	}		
}

