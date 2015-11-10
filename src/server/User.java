package server;

import java.net.InetAddress;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "user")
public class User
{	
	private String login;
	private String fName;
	private String lName;
	private String pw;
	@XmlElement
	private String ip;
	private boolean online;

	public User() 
	{
		super();
	}

	public User(String login, String fName, String lName, String pw,  InetAddress ip)
	{		
		this.login = login;
		this.fName = fName;
		this.lName = lName;
		this.pw = pw;
		this.ip = ip.getHostAddress();
		this.online = true;
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

	public String getPw()
	{
		return pw;
	}
	public void setPw(String pw)
	{
		this.pw = pw;
	}
	
	public String getIp()
	{
		return ip;
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
	
	@Override
	public String toString() 
	{ 
	    return "login: '" + this.login + "', fName: '" + this.fName + "', lName: '" + this.lName + "', IP: '" + this.ip + "'";
	} 
}
