package server;

import java.net.InetAddress;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface ServerInterface extends Remote
{
	//FUNCTIONS (named to what it returns)
	public boolean loginUser(String username, String password, InetAddress ip)throws RemoteException;	//returns Session id
	public boolean newUser(String login, String firstname, String lastname, String password, InetAddress ip )throws RemoteException;
	public boolean logoutUser(String username, String password)throws RemoteException;
	public boolean addFriend(String username, String password, String fullname)throws RemoteException;
	public boolean deleteFriend(String username, String password, String fullname)throws RemoteException;
	public User startChat(String username)throws RemoteException; //returns chatid
	public boolean sendMessage(String username, String password, int chat, String Message)throws RemoteException;
	//public ArrayList<Friend> getFriends(String username, String password)throws RemoteException;

	//PROCEDURES (named to what it does)
	
}
