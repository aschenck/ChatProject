package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Server extends UnicastRemoteObject implements ServerInterface 
{
	//Initialize the users list
	private Users userlist = new Users();

	protected Server() throws RemoteException 
	{
		//Read user from xml to list
		readUsersXML();
	}

	public int SetOutPort(User u)
	{
		int port;
		port = u.getInPort();		
		return port;
	}
	
	public int MakeClientListenerPort()
	{
		Random rng = new Random();
		int port;
		port = rng.nextInt(16383) + 49152;
		
		return port;
	}
	
	@Override
	public boolean loginUser(String username, char[] password, InetAddress ip) throws RemoteException 
	{			
		readUsersXML();
		boolean found = false;		
		List<User> temp = userlist.getUsers();		
		for(User u : temp)
		{						
	        if(u.getLogin().equals(username) )
	        {
	        	//System.out.println("Huidige user: "+ u.getLogin());
	        	if( Arrays.equals(u.getPw(), password))
	        	{	        		
	        		found = true;
	        		u.setIp(ip);
	        		u.setInPort(MakeClientListenerPort());
	        	}	        
	        }
	    }	
		if(!found)
			System.out.println("Ongeldige username en wachtwoord");
		else 
			System.out.println("Aangemeld");
		return found;
	}

	@Override
	public boolean newUser(String login, String firstname, String lastname, char[] password, InetAddress ip )throws RemoteException
	{
		boolean ok = false;
		List<User> temp = userlist.getUsers();	
		User n = new User(login, firstname, lastname, password, ip);
		// CHECK IF USERNAME ALLREADY EXSIST	
		if(!temp.isEmpty())
		{
			for(User u : temp)
			{			
		        if(n.getLogin().equals(u.getLogin()) )
		        {	
		        	ok = false;
		        	System.out.println("username already exists!");
		        }
		        else
		        {
		        	ok = true;
		        	System.out.println("User created: " + n.toString());	        	
		        }
		        if(ok)
		        {
		        	userlist.getUsers().add(n);		
		        	writeUsersXML();
		        }
		    }		
		}
		else
		{
			userlist.getUsers().add(n);		
        	writeUsersXML();
        	ok = true;
		}
		return ok;
	}

	@Override
	public boolean logoutUser(String username) throws RemoteException 
	{
		boolean ok = false;
		List<User> temp = userlist.getUsers();	
		for(User u : temp)
		{			
			if(u.getLogin().equals(username)) 
			{	
				ok = true;
				u.setOnline(false);
				System.out.println("User " + username + " has logged out!");
			}
			else
			{
				ok = false;
				System.out.println("User not found!");	        	
			}
			if(ok)
			{
				userlist.setUsers(temp);;		
				writeUsersXML();
			}
		}		
		return ok;
	}

	@Override
	public void addFriend(String myUsername, String friendUsername) throws RemoteException
	{
		List<User> temp = userlist.getUsers();
		User p = new User();
		for(User u : temp)
		{
	        if(u.getLogin().equals(myUsername))
	        {
	        	p = u;
	        	p.addFriend(friendUsername);
	        	writeUsersXML();
	        }
		}
	}
	
	public int getUserInPort(String userName) throws RemoteException
	{
		int port = 0;
		List<User> temp = userlist.getUsers();		
		for(User u : temp)
		{
	        if(u.getLogin().equals(userName))
	        {
	        	port = u.getInPort();
	        }
		}		
		
		return port;
	}
	
	public int getUserOutPort(String userName)throws RemoteException
	{
		int port = 0;
		List<User> temp = userlist.getUsers();
		for(User u : temp)
		{
	        if(u.getLogin().equals(userName))
	        {
	        	port = u.getOutPort();
	        }
		}		
		
		return port;
	}
	
	public InetAddress getUserIP (String userName) throws RemoteException
	{
		InetAddress ip = null;
		List<User> temp = userlist.getUsers();
		for(User u : temp)
		{
	        if(u.getLogin().equals(userName))
	        {
	        	ip = u.getIp();
	        }
		}		
		return ip;		
	}
	
	public User getUserSettings(String myUserName) throws RemoteException
	{
		List<User> temp = userlist.getUsers();
		User p = new User();
		for(User u : temp)
		{
	        if(u.getLogin().equals(myUserName))
	        {
	        	p = u;
	        }
		}
		return p;
	}

	@Override
	public void deleteFriend(String myUsername, String friendUsername) throws RemoteException
	{
		List<User> temp = userlist.getUsers();
		User p = new User();
		for(User u : temp)
		{
	        if(u.getLogin().equals(myUsername))
	        {
	        	p = u;
	        	p.deleteFriend(friendUsername);
	        	writeUsersXML();
	        }
		}
	}
	
	
	@Override
	public String getIpAndPortForInvite(String friendUsername) throws RemoteException {
		List<User> temp = userlist.getUsers();
		User p = new User();
		for(User u : temp)
		{
	        if(u.getLogin().equals(friendUsername))
	        {
	        	p = u;
	        	return p.getIp() + ":" + p.getInPort();
	        }
		}
		return "Offline";
	}

	@Override
	public int startChat(String username) throws RemoteException 
	{
		List<User> temp = userlist.getUsers();			
		User p = new User();
		for(User u : temp)
		{			
	        if(u.getLogin().equals(username) && u.getOnline())
	        {	
	        	p = u;
	        	SetOutPort(p);
	        	System.out.println("Listen poort van  -" + p.getLogin()+ "- = " + p.getOutPort()); 
	        }
	    }		
		return p.getInPort();
	}

	@Override
	public boolean sendMessage(String username, int chat, String Message) throws RemoteException 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getFriends(String username) throws RemoteException
	{
		// TODO Auto-generated method stub
		List<User> temp = userlist.getUsers();
		User p = new User();
		for(User u : temp)
		{
	        if(u.getLogin().equals(username))
	        {
	        	p = u;
	        	return p.getFriendList();
	        }
		}		
		return Collections.<String>emptyList();
	}
	
	/*private boolean authorizeUser(String username, char[] password)
	{		
		return true;
	}*/
	
	private void readUsersXML()
	{
		try 
		{	
			System.out.println("reading from xml");
		
			JAXBContext jaxbContext = JAXBContext.newInstance(Users.class);
	    	Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	     
	    	//We had written this file in marshalling example
	    
			userlist = (Users) jaxbUnmarshaller.unmarshal( new File("userlist.xml") );
			System.out.println("reading from xml success!");
		} 
		catch (JAXBException e) 
		{
			System.out.println("Problem reading from xml");
			System.out.println("File does not exsit");
			//e.printStackTrace();
		}		
	}
	
	private void writeUsersXML()
	{
		System.out.println("write to xml started");
		
		try 
		{
			JAXBContext jaxbContext = JAXBContext.newInstance(Users.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		 
		    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		     
		    //DEBUG
		    //Marshal the employees list in console
		    //jaxbMarshaller.marshal(userlist, System.out);
		    
		    //Marshal to file
		    OutputStream os = new FileOutputStream("userlist.xml");
		    jaxbMarshaller.marshal(userlist, os);
		    System.out.println("write to xml success!");
		    
		}
		catch ( JAXBException e ) 
		{
			System.out.println("Error object to xml");
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("File to write xml not found");
			e.printStackTrace();
		}
	}
}

