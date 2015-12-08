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
	private String ip;
	private int inPort;
	private int outPort;
	private String user;
	private List<String> friendList;
	
	private Hashtable<String, ClientSendThread> threadTable;
	
	public Client(String ip, int inPort, int outPort)
	{
		this.ip = ip;
		this.inPort = inPort;
		this.outPort = outPort;		
	}
	
	public static void main(String[] args) throws IOException
	{}		
	
	public void deleteFriend(String friend)
	{
		try 
		{
			InetAddress addr = InetAddress.getLocalHost();
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
			InetAddress addr = InetAddress.getLocalHost();
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
			//Obtain a reference to the object from the registry and typecast it into the appropriate type�
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
		Client2 cl = new Client2(ip, inPort, outPort);
		new Thread(new ClientListenerThread(cl.getInPort())).start();
		new Thread(new ClientSendThread(cl.getOutPort(), cl.getIp())).start();		
	}
	
	public boolean connectToServer(String user, char[] pass) throws UnknownHostException
	{		
		boolean connected = false;
		try 
		{
			//Obtain a reference to the object from the registry and typecast it into the appropriate type�
			InetAddress addr = InetAddress.getLocalHost();
			server.ServerInterface ChatServer = (server.ServerInterface)Naming.lookup("rmi://" + addr.getHostAddress() + "/ChatServer");			
			
			if(ChatServer.loginUser(user, pass, addr))
			{
				System.out.println("User logged in!");
				this.user = user;
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
	
	public void sendMessage(String username, String message)
	{
		getSocketFromTable(username).setMessage(message);
	}
	
	public void getFriends()
	{
		try 
		{
			InetAddress addr = InetAddress.getLocalHost();
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
	
	//getters and setters
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