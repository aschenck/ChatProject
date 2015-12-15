package user;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "user")
@XmlAccessorType (XmlAccessType.FIELD)
public class User
{	
	private String login;
	private String fName;
	private String lName;
	private char[] pw;
	//@XmlElement
	private InetAddress ip;
	private boolean online;
	private int inPort;
	private int outPort;

	@XmlElement(name = "friendList")
	private ArrayList<String> friendList;

	public User(String login, String fName, String lName, char[] pw,  InetAddress ip)
	{		
		this.login = login;
		this.fName = fName;
		this.lName = lName;
		this.pw = pw;
		this.ip = ip;
		this.online = true;
		this.friendList = new ArrayList<String>();
	}
	
	public User()
	{
		this.friendList = new ArrayList<String>();
	}	
	
/*	public int MakeInPort()
	{
		int port;
		port = getOutPort();
		if(port == 65535 )
			port -= 1;
		else
			port += 1;		
		
		return port;
	}
	
	public int MakeOutPort()
	{
		Random rng = new Random();
		int port;
		port = rng.nextInt(16383) + 49152;
		
		return port;
	}*/
	
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
		return ip;
	}

	public void setIp(InetAddress ip)
	{
		this.ip = ip;
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
	
	public void addFriend(String friendUserName)
	{
		friendList.add(friendUserName);
	}
	
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
	
	@Override
	public String toString() 
	{ 
	    return "login: '" + this.login + "', fName: '" + this.fName + "', lName: '" + this.lName + "', IP: '" + this.ip + "'";
	} 
}
