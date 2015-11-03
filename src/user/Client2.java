package user;

import java.io.IOException;
import java.util.Scanner;

public class Client2
{	
	private String ip;
	private int inPort;
	private int outPort;
	private static boolean connected;
	
	public Client2(String ip, int inPort, int outPort)
	{
		this.ip = ip;
		this.inPort = inPort;
		this.outPort = outPort;		
		Client2.connected = false;		
	}
	
	public static boolean isConnected()
	{
		return connected;
	}

	public static void setConnected(boolean connected)
	{
		Client2.connected = connected;
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
		Client2 cl = new Client2("192.168.1.1", 5000, 5001);	
		new Thread(new ClientListenerThread(cl.getInPort())).start();		
		String input = "";
		input = kb.nextLine();
		if (input.matches("connect"))		
		{
			System.out.println("Trying to connect to client2");
			new Thread(new ClientSendThread(cl.getOutPort(), cl.getIp())).start();
		}
	}		
}

