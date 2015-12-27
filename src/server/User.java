package server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

/**
 * Class that stores users information
 * 
 * @param login the login of the user
 * @param fName the first name of the user
 * @param lName the last name of the user
 * @param pw the password of the user
 * @param ip the ip of the user
 * @param online online status of the user
 * @param inPort the listenport of the user
 * @param outPort the sendport of the user
 * @param friendlist a list of all the friends the user currently has added
 *
 * 
 * @author Anthony, Willem, Frederik
 *
 */
@XmlRootElement(name = "user")
@XmlAccessorType (XmlAccessType.FIELD)
public class User
{	
	private String login;
	private String fName;
	private String lName;
	private char[] pw;
	//@XmlElement
	private String ip;
	private boolean online;
	private int inPort;
	private int outPort;

	@XmlElement(name = "friendList")
	private ArrayList<String> friendList;

	/**
	 * Constructor of the user class
	 * @param login
	 * @param fName
	 * @param lName
	 * @param pw
	 * @param ip
	 */
	public User(String login, String fName, String lName, char[] pw,  InetAddress ip)
	{		
		this.login = login;
		this.fName = fName;
		this.lName = lName;
		this.pw = pw;
		this.ip = ip.getHostAddress();
		this.online = true;
		this.friendList = new ArrayList<String>();
	}
	
	/**
	 * default constructor of the class
	 */
	public User()
	{	
		this.friendList = new ArrayList<String>();
	}	
	
	/**
	 * Getters and setters of the class
	 * 
	 */
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

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getfName()
	{
		return fName;
	}

	public void setfName(String fName)
	{
		this.fName = fName;
	}

	public String getlName()
	{
		return lName;
	}

	public void setlName(String lName)
	{
		this.lName = lName;
	}

	public char[] getPw()
	{
		return pw;
	}
	public void setPw(char[] pw)
	{
		this.pw = pw;
	}
	
	public InetAddress getIp()
	{
		try
		{
			return InetAddress.getByName(ip);
		} catch (UnknownHostException e)
		{			
			e.printStackTrace();
			return null;
		}
	}

	public void setIp(InetAddress ip)
	{
		this.ip = ip.getHostAddress();
	}

	public boolean getOnline()
	{
		return online;
	}
	
	public void setOnline(boolean online)
	{
		this.online = online;
	}
	
	public List<String> getFriendList()
	{
		return friendList;
	}
	
	/**
	 * Add a friend to the friendlist
	 * @param friendUserName the name of the friend to add
	 */
	public void addFriend(String friendUserName)
	{
		friendList.add(friendUserName);
	}
	
	/**
	 * Delete a friend from the friendlist
	 * 
	 * @param friendUserName the friend to delete
	 */
	public void deleteFriend(String friendUserName)
	{
		for (int i = 0; i < friendList.size(); i++)
		{
			if (friendList.get(i).equals(friendUserName))
			{
				friendList.remove(i);
			}
		}		
	}
	
	/**
	 * converts a user object details to a string
	 */
	@Override
	public String toString() 
	{ 
	    return "login: '" + this.login + "', fName: '" + this.fName + "', lName: '" + this.lName + "', IP: '" + this.ip + "'";
	} 
}
