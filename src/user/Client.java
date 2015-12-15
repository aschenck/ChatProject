package user;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.util.Hashtable;
import java.util.List;
import server.User;

public class Client
{	
	private InetAddress ip;
	private int inPort;
	private int outPort;
	private String user;
	private List<String> friendList;
	private InetAddress server;
	
	private Hashtable<String, ClientSendThread> threadTable;
	
	public Client(int inPort, int outPort) throws UnknownHostException
	{
		this.ip = InetAddress.getLocalHost();
		this.inPort = inPort;
		this.outPort = outPort;		
		this.server = InetAddress.getByName("192.168.1.1");
	}
	
	public static void main(String[] args) throws IOException
	{}		
	
	public void deleteFriend(String friend)
	{
		try 
		{
			InetAddress addr = server;
			server.ServerInterface ChatServer = (server.ServerInterface)Naming.lookup("rmi://" + addr.getHostAddress() + "/ChatServer");
			
			ChatServer.deleteFriend(getUser(), friend);		
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}			
	}
	
	public void addFriend(String friend)
	{
		try 
		{
			InetAddress addr = server;
			server.ServerInterface ChatServer = (server.ServerInterface)Naming.lookup("rmi://" + addr.getHostAddress() + "/ChatServer");
			
			ChatServer.addFriend(getUser(), friend);		
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}			
	}
	
	public void startChat(String userName)
	{
		try 
		{
			//Obtain a reference to the object from the registry and typecast it into the appropriate type…
			InetAddress addr = server;
			server.ServerInterface ChatServer = (server.ServerInterface)Naming.lookup("rmi://" + addr.getHostAddress() + "/ChatServer");
			User u = new User();
			u.setOutPort(ChatServer.startChat(userName));
			if(u.getOutPort() == 0)
			{
	        	System.out.println("De gebruiker " +u.getLogin()+ " is momenteel offline of bestaat niet");
			}
			else
			{
				this.setInPort(u.getInPort());
				this.setOutPort(u.getOutPort());
				this.setIp(u.getIp());
				this.putSocketToTable(userName, new ClientSendThread(getOutPort(), getIp()));				
			}	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void ThreadCreater() throws IOException
	{
		//Client2 cl = new Client2(ip, inPort, outPort);
		new Thread(new ClientListenerThread(getInPort())).start();
		new Thread(new ClientSendThread(getOutPort(), getIp())).start();		
	}
	
	public boolean connectToServer(String user, char[] pass) throws UnknownHostException
	{		
		boolean connected = false;
		try 
		{
			//Obtain a reference to the object from the registry and typecast it into the appropriate type…
			InetAddress addr = server;
			server.ServerInterface ChatServer = (server.ServerInterface)Naming.lookup("rmi://" + addr.getHostAddress() + "/ChatServer");			
			
			if(ChatServer.loginUser(user, pass, addr))
			{
				System.out.println("User logged in!");
				this.user = user;
				connected = true;	
				User ik = new User();				
				ik.setInPort(ChatServer.getUserInPort(user));
				ik.setOutPort(ChatServer.getUserOutPort(user));
				System.out.println("Inport: "+ik.getInPort());
				System.out.println("Outport: "+ik.getOutPort());
				this.inPort = ik.getInPort();
				this.outPort = ik.getOutPort();
				new Thread(new ClientListenerThread(getInPort())).start();
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
	
	public void sendMessage(String username, String message)
	{
		getSocketFromTable(username).setMessage(message);
	}
	
	public void getFriends()
	{
		try 
		{
			InetAddress addr = server;
			server.ServerInterface ChatServer = (server.ServerInterface)Naming.lookup("rmi://" + addr.getHostAddress() + "/ChatServer");
			
			setFriendList(ChatServer.getFriends(getUser()));		
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}			
	}
	
	public boolean newUser(String user, String fName, String lName, char[] pass)
	{
		boolean connected = false;
		try 
		{
			InetAddress addr = server;
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
	
	//getters and setters
	public InetAddress getIp()
	{
		return ip;
	}

	public void setIp(InetAddress inetAddress)
	{
		this.ip = inetAddress;
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

	public Hashtable<String, ClientSendThread> getThreadTable()
	{
		return threadTable;
	}

	public void setThreadTable(Hashtable<String, ClientSendThread> threadTable)
	{
		this.threadTable = threadTable;
	}
	
	public ClientSendThread getSocketFromTable(String username)
	{		
		return this.threadTable.get(username);		
	}
	
	public void putSocketToTable(String username, ClientSendThread sendThread)
	{
		this.threadTable.put(username, sendThread);
	}

	public String getUser()
	{
		return user;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public List<String> getFriendList()
	{
		return friendList;
	}

	public void setFriendList(List<String> friendList)
	{
		this.friendList = friendList;
	}
}