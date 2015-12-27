package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
/**
 * This is the server interface class, it defines the methods that are accessible by RMI
 * from the client application.
 * 
 * @author Anthony, Willem, Frederik
 * @version 1.0
 *
 */
public interface ServerInterface extends Remote
{
	//login an existing user
	public boolean loginUser(String username, char[] password, InetAddress ip)throws RemoteException;	
	//create a new user account and store it in xml
	public boolean newUser(String login, String firstname, String lastname, char[] password, InetAddress ip )throws RemoteException;
	//log a user out
	public boolean logoutUser(String username)throws RemoteException;
	//the user can add other users to their friendlist
	public void addFriend(String myUsername, String friendUsername)throws RemoteException;
	//and remove them aswell
	public void deleteFriend(String myUsername, String friendUsername)throws RemoteException;
	//set up the TCP connection with the specified user
	public int startChat(String username)throws RemoteException; 
	//return the friendlist of a user
	public List<String> getFriends(String username) throws RemoteException;
	//public String getIpAndPortForInvite(String friendUsername) throws RemoteException;	
	//public User getUserSettings(String myUserName) throws RemoteException;
	//set the default out port of the client
	public int getUserOutPort(String userName) throws RemoteException;
	//return the listenport to the client that is assigned by the server
	public int getUserInPort(String userName) throws RemoteException;
	//returns the ip address of the person you are trying to chat with
	public InetAddress getUserIP (String userName) throws RemoteException;	
	//check to see if a user is currently online
	public boolean CheckOnline(String userName)throws RemoteException;	
	//returns any messages send to the user while the user was offline
	public String ReadOfflineMessages(String username)throws RemoteException, FileNotFoundException, IOException;
	//method to send messages to offline users, messages are stored in a text file
	public boolean WriteOfflineMessages(String username, String message)throws IOException;	
}
