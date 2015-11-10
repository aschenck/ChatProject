package TestUser;

import java.net.InetAddress;
import java.rmi.*;

public class Client
{
	public static void main(String[] args)
	{
		String HOST = "192.168.116.1";
		int sessionId;
		
		try 
		{
			//Obtain a reference to the object from the registry and typecast it into the appropriate type…
			ServerInterface ChatServer = (ServerInterface)Naming.lookup("rmi://" + HOST + "/ChatServer");
			
			if(ChatServer.loginUser("jef", "gys"))
			{
				System.out.println("User logged in!");
			}
			else
			{
				System.out.println("User not logged in");
			}
			
			//InetAddress addr = InetAddress.getLocalHost();
			//ChatServer.newUser("jef", "firstname1", "lastname1", "gys", addr);
			//ChatServer.newUser("login2", "firstname2", "lastname2", "passwordhash2", addr);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
}
