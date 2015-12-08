package user;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.util.Scanner;

import TestUser.Client2;
import server.User;

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
	{}		
	
	public void startChat(String userName)
	{
		try 
		{
			//Obtain a reference to the object from the registry and typecast it into the appropriate type…
			InetAddress addr = InetAddress.getLocalHost();
			server.ServerInterface ChatServer = (server.ServerInterface)Naming.lookup("rmi://" + addr.getHostAddress() + "/ChatServer");
			User u = new User();
			u = ChatServer.startChat(userName);
			if(u.getOutPort() == 0)
			{
	        	System.out.println("De gebruiker " +u.getLogin()+ " is momenteel offline of bestaat niet");
			}
			else
			{
				this.setInPort(u.getInPort());
				this.setOutPort(u.getOutPort());
				this.setIp(u.getIp());
				ThreadCreater();
			}	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void ThreadCreater() throws IOException
	{
		Client2 cl = new Client2(ip, inPort, outPort);
		new Thread(new ClientListenerThread(cl.getInPort())).start();
		new Thread(new ClientSendThread(cl.getOutPort(), cl.getIp())).start();		
	}
	
	public boolean connectToServer(String user, char[] pass) throws UnknownHostException
	{		
		boolean connected = false;
		try 
		{
			//Obtain a reference to the object from the registry and typecast it into the appropriate type…
			InetAddress addr = InetAddress.getLocalHost();
			server.ServerInterface ChatServer = (server.ServerInterface)Naming.lookup("rmi://" + addr.getHostAddress() + "/ChatServer");			
			
			if(ChatServer.loginUser(user, pass, addr))
			{
				System.out.println("User logged in!");
				connected = true;				
			}
			else
			{
				System.out.println("User not logged in");
				connected = false;
			}	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}		
		
		return connected;
	}
	
	public boolean newUser(String user, String fName, String lName, char[] pass)
	{
		boolean connected = false;
		try 
		{
			InetAddress addr = InetAddress.getLocalHost();
			server.ServerInterface ChatServer = (server.ServerInterface)Naming.lookup("rmi://" + addr.getHostAddress() + "/ChatServer");
			
			if(ChatServer.newUser(user, fName, lName, pass, addr))
			{
				System.out.println("User logged in!");
				connected = true;
			}
			else
			{
				System.out.println("User not logged in");
				connected = false;
			}	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
		return connected;
	}
}