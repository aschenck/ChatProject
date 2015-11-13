package user;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.Naming;
import java.util.Scanner;

import server.User;

//import TestUser.ServerInterface;

public class Client2
{	
	private static String server;
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
		this.setServer("localhost");
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
	
	public static String getServer()
	{
		return server;
	}

	public void setServer(String server)
	{
		Client2.server = server;
	}	
	
	public static void newUser()
	{
		Scanner kb = new Scanner(System.in);
		System.out.println("Username:");
		String user = "";
		user = kb.nextLine();
		System.out.println("Password:");
		String pass = "";
		pass = kb.nextLine();
		System.out.println("First name:");
		String fName = "";
		fName = kb.nextLine();
		System.out.println("Last name:");
		String lName = "";
		lName = kb.nextLine();
		try 
		{
			InetAddress addr = InetAddress.getLocalHost();
			server.ServerInterface ChatServer = (server.ServerInterface)Naming.lookup("rmi://" + addr.getHostAddress() + "/ChatServer");
			
			if(ChatServer.newUser(user, fName, lName, pass, addr))
			{
				System.out.println("User logged in!");
			}
			else
			{
				System.out.println("User not logged in");
			}	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		kb.close();
	}
	
	public static void connectToServer()
	{
		Scanner kb = new Scanner(System.in);
		System.out.println("Username:");
		String user = "";
		user = kb.nextLine();
		System.out.println("Password:");
		String pass = "";
		pass = kb.nextLine();
		try 
		{
			//Obtain a reference to the object from the registry and typecast it into the appropriate type…
			InetAddress addr = InetAddress.getLocalHost();
			server.ServerInterface ChatServer = (server.ServerInterface)Naming.lookup("rmi://" + addr.getHostAddress() + "/ChatServer");
			
			if(ChatServer.loginUser(user, pass))
			{
				System.out.println("User logged in!");
			}
			else
			{
				System.out.println("User not logged in");
			}	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		kb.close();
	}
	
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
				this.setInPort(u.getOutPort());
				this.setOutPort(u.getInPort());
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
	
	public static void main(String[] args) throws IOException
	{
		newUser();
		connectToServer();
	}	
}

