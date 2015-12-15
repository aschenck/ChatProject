package server;

import java.net.InetAddress;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


public interface ServerInterface extends Remote
{
	//FUNCTIONS (named to what it returns)
	public boolean loginUser(String username, char[] password, InetAddress ip)throws RemoteException;	//returns Session id
	public boolean newUser(String login, String firstname, String lastname, char[] password, InetAddress ip )throws RemoteException;
	public boolean logoutUser(String username)throws RemoteException;
	public void addFriend(String myUsername, String friendUsername)throws RemoteException;
	public void deleteFriend(String myUsername, String friendUsername)throws RemoteException;
	public int startChat(String username)throws RemoteException; 
	public boolean sendMessage(String username, int chat, String Message)throws RemoteException;
	public List<String> getFriends(String username) throws RemoteException;
	public String getIpAndPortForInvite(String friendUsername) throws RemoteException;
	public User getUserSettings(String myUserName) throws RemoteException;
	public int getUserOutPort(String userName) throws RemoteException;
	public int getUserInPort(String userName) throws RemoteException;
	public InetAddress getUserIP (String userName) throws RemoteException;
		
	//public List<Friend> getFriends(String username)throws RemoteException;

	//PROCEDURES (named to what it does)
	
}
