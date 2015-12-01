package server;

import java.net.InetAddress;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface ServerInterface extends Remote
{
	//FUNCTIONS (named to what it returns)
	public boolean loginUser(String username, char[] password, InetAddress ip)throws RemoteException;	//returns Session id
	public boolean newUser(String login, String firstname, String lastname, char[] password, InetAddress ip )throws RemoteException;
	public boolean logoutUser(String username)throws RemoteException;
	public boolean addFriend(String username)throws RemoteException;
	public boolean deleteFriend(String username)throws RemoteException;
	public User startChat(String username)throws RemoteException; //returns chatid
	public boolean sendMessage(String username, int chat, String Message)throws RemoteException;
	//public ArrayList<Friend> getFriends(String username, String password)throws RemoteException;

	//PROCEDURES (named to what it does)
	
}
