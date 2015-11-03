package user;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * 
 * @author Anthony
 * <p>
 * User object class.
 * <p>
 * Creates an object that stores all needed user information on the server.
 * This information will be stored in an XML file to be retrieved when the program starts up again.
 *
 */

public class UserObject extends Friend
{	
	private String pw;
	private boolean online;
	private ArrayList<Friend> friendList;

	public UserObject(String login, String pw, String fName, String lName, InetAddress ip)
	{		
		super(login, fName,lName, ip);
		this.pw = pw;
	}	

	public String getPw()
	{
		return pw;
	}
	public void setPw(String pw)
	{
		this.pw = pw;
	}
	public boolean isOnline()
	{
		return online;
	}
	public void setOnline(boolean online)
	{
		this.online = online;
	}
	public ArrayList<Friend> getFriendList()
	{
		return friendList;
	}
	public void setFriendList(ArrayList<Friend> friendList)
	{
		this.friendList = friendList;
	}

}
