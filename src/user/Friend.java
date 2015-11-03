package user;

import java.net.InetAddress;

public class Friend
{
	private String login;
	private String fName;
	private String lName;
	private InetAddress ip;
	
	public Friend(String login, String fName, String lName, InetAddress ip)
	{
		this.login = login;
		this.fName =fName;
		this.lName = lName;
		this.ip = ip;
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

	public InetAddress getIp()
	{
		return ip;
	}

	public void setIp(InetAddress ip)
	{
		this.ip = ip;
	}
}
