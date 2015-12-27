package user;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import GUI.OpenOffChat;

/**
 * Class that implements all functionality of the client side application, 
 * called from the client GUI
 * 
 * @author Anthony, Willem, Frederik
 *
 */
public class Client
{	
	//the clients ip address
	private InetAddress ip;
	//the clients listenport
	private int inPort;
	//the clients sendport
	private int outPort;
	//the clients login name
	private String user;
	//the clients friendlist
	private List<String> friendList;
	//the ip address of the chat server
	private InetAddress server;
	//the clients listener thread
	private ClientListenerThread clLT;	
	//hashtable of all open sendthreads, for handling multiple chat sessions with different users
	private Hashtable<String, ClientSendThread> threadTable;
	
	/**
	 *  Constructor of the client class
	 *  Sets the minimum required data for the application to fucntion
	 *  
	 * @param inPort this clients listenport
	 * @param outPort this clients sendport
	 * 
	 * @throws UnknownHostException exception thrown if the server ip is not found
	 */
	public Client(int inPort, int outPort) throws UnknownHostException
	{
		this.ip = InetAddress.getLocalHost();
		this.inPort = inPort;
		this.outPort = outPort;		
		this.server = InetAddress.getByName("localhost");
		this.threadTable = new Hashtable<>();
	}
	
/*	public static void main(String[] args) throws IOException
	{}		*/
	/**
	 * Sends a message to an offline user
	 * 
	 * @param friendname the user to receive the message
	 * @param message the message to be sent
	 */
	public void sendOfflineMessage(String friendname, String message)
	{
		try 
		{
			InetAddress addr = server;
			server.ServerInterface ChatServer = (server.ServerInterface)Naming.lookup("rmi://" + addr.getHostAddress() + "/ChatServer");
			
			if(ChatServer.WriteOfflineMessages(friendname, message))
			{
				System.out.println("Bericht is verstuurd naar de server");
			}
			else
			{
				System.out.println("Bericht kon niet worden verstuurd naar de server");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
	}
	/**
	 * Retrieves the stored offline messages from the server and displays them in the GUI (if available)
	 */
	public void readOfflineMessage()
	{
		try 
		{
			InetAddress addr = server;
			server.ServerInterface ChatServer = (server.ServerInterface)Naming.lookup("rmi://" + addr.getHostAddress() + "/ChatServer");
			String message = ChatServer.ReadOfflineMessages(getUser());
			@SuppressWarnings("unused")
			OpenOffChat offChat = null;
			if(!message.isEmpty())
				offChat = new OpenOffChat(message, true);
			//System.out.println(message);	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
	}
	/**
	 * Delete a friend from the clients friendlist
	 * @param friend the friend to be deleted
	 */
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
	/**
	 * Retrieves a list of invites for chatting from the listenerthread
	 * 
	 * @return returns a list of pending chat invites
	 */
	public List<String> CheckInvites()
	{
		List<String> invites = new ArrayList<String>();
		invites = clLT.getInviteList();
		return invites;
	}
	/**
	 * Closes an open chat connection when the chat window is closed.
	 * Retrieves the socket that is being used for the chat and closes it.
	 * 
	 * @param username the user with whom you were chatting
	 */
	public void closeChatConnection(String username)
	{
		ClientSendThread cls = getSocketFromTable(username);
		if(cls != null)
		{
			cls.CloseSocket();
			removeSocketFromTable(username);	
		}
	}
	
	/**
	 * Add a user to your friendlist
	 * 
	 * @param friend the friend you want to add
	 */
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
	
	/**
	 * Start a chat session with the specified user.
	 * Sets the port numbers to open up a socket.
	 * Creates a new sendthread for the chat session.
	 * Stores the sendthread in a hashtable
	 * 
	 * @param userName the user you want to chat with.
	 */
	public void startChat(String userName)
	{
		try 
		{
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
				InetAddress friendaddress = ChatServer.getUserIP(userName);
				ClientSendThread t = new ClientSendThread(getOutPort(), friendaddress);				
				new Thread(t).start();				
				this.putSocketToTable(userName, t);				
			}	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if the selected user is currently online(logged in on the server)
	 * @param user the user to be checked
	 * @return true or false depending on the login status of the specified user
	 */
	public boolean checkOnline(String user)
	{
		boolean online = false;		
		try 
		{
			//Obtain a reference to the object from the registry and typecast it into the appropriate type…
			InetAddress addr = server;
			server.ServerInterface ChatServer = (server.ServerInterface)Naming.lookup("rmi://" + addr.getHostAddress() + "/ChatServer");
			
			if(ChatServer.CheckOnline(user))
			{
				online = true;
			}	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return online;		
	}
	
	/**
	 * Method called when logging in to the server.
	 * Sets the user object parameters like the ones stored on the server
	 * Starts a new listenthread in order to be able to receive chat invites
	 * 
	 * @param user the user who is logging in
	 * @param pass password linked to the specified username
	 * @return true or false depending on succesfull login
	 * @throws UnknownHostException
	 */
	public boolean connectToServer(String user, char[] pass) throws UnknownHostException
	{		
		boolean connected = false;
		try 
		{
			InetAddress addr = server;
			server.ServerInterface ChatServer = (server.ServerInterface)Naming.lookup("rmi://" + addr.getHostAddress() + "/ChatServer");			
			
			if(ChatServer.loginUser(user, pass, this.ip))
			{
				System.out.println("User logged in!");
				this.user = user;
				connected = true;	
				User ik = new User();
				ik.setLogin(user);
				ik.setIp(this.ip);
				ik.setInPort(ChatServer.getUserInPort(user));
				ik.setOutPort(ChatServer.getUserOutPort(user));
				System.out.println("Inport: "+ik.getInPort());
				System.out.println("Outport: "+ik.getOutPort());
				this.inPort = ik.getInPort();
				this.outPort = ik.getOutPort();
				clLT = new ClientListenerThread(getInPort());
				new Thread(clLT).start();
				//new Thread(new ClientListenerThread(getInPort())).start();
				System.out.println("Client : Geconnecteerd: "+ik.toString());
				readOfflineMessage();
			}
			else
			{
				System.out.println("User not logged in");//
				connected = false;
			}	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}				
		return connected;
	}
	
	/**
	 * Sends a message to the specified user.
	 * The sendthread corresponding to the specified user is retrieved and called
	 * 
	 * @param username the user you are chatting with
	 * @param message the message to be sent to the user
	 */
	public void sendMessage(String username, String message)
	{
		ClientSendThread cls = getSocketFromTable(username);
		if(cls!=null)
			cls.setMessage(message);
	}
	
	/**
	 * Retrieves the current users friendlist from the server
	 */
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
	/**
	 * logs the user out on the server
	 */
	public void logOut()
	{
		try 
		{
			InetAddress addr = server;
			server.ServerInterface ChatServer = (server.ServerInterface)Naming.lookup("rmi://" + addr.getHostAddress() + "/ChatServer");
			
			if(ChatServer.logoutUser(this.user))
			{
				System.out.println("User "+this.user +" has logged out!");				
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
	}
	/**
	 * Create a new user account on the server
	 * 
	 * @param user the login the user wants to user
	 * @param fName the users firstname
	 * @param lName the users lastname
	 * @param pass the users password
	 * @return true or false if succesfull or not
	 */
	public boolean newUser(String user, String fName, String lName, char[] pass)
	{
		boolean connected = false;
		try 
		{
			InetAddress addr = server;
			server.ServerInterface ChatServer = (server.ServerInterface)Naming.lookup("rmi://" + addr.getHostAddress() + "/ChatServer");
			
			if(ChatServer.newUser(user, fName, lName, pass, addr))
			{
				System.out.println("User registered!");
				connected = true;
			}
			else
			{
				System.out.println("User not registered");
				connected = false;
			}	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
		return connected;
	}
	
	/**
	 * Retrieves an existing sendthread from a hashtable specified by the username
	 * @param username the key in the hashtable, corresponds with a sendthread
	 * @return the retrieved sendthread
	 */
	public ClientSendThread getSocketFromTable(String username)
	{		
		if(this.threadTable.containsKey(username))
			return this.threadTable.get(username);	
		else
			return null;
	}
	/**
	 * Adds a sendthread to the hashtable
	 * 
	 * @param username the user you are chatting with (key)
	 * @param t the thread used for the chat session (value)
	 */
	public void putSocketToTable(String username, ClientSendThread t)
	{
		this.threadTable.put(username, t);
	}
	/**
	 * removes a thread when a chat session is closed
	 * @param username the key used to retrieve the corresponding thread
	 */
	public void removeSocketFromTable(String username)
	{
		this.threadTable.remove(username);
	}
	
	/**
	 * getters and setters of the client class
	 * 
	 * 
	 */
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