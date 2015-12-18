package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
	private static final long serialVersionUID = -5165887663036488313L;
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
	
	public boolean WriteOfflineMessages(String username, String message)throws IOException
	{
		boolean written = false;
		File f = new File(username+".txt");
	
		if(f.exists())
		{			
			FileWriter fw = new FileWriter(f, true);
			fw.write(message);
			fw.flush();
			fw.close();
			written = true;
		}
		else
		{
			f.createNewFile();		
			FileWriter fw = new FileWriter(f, true);
			fw.write(message);
			fw.flush();
			fw.close();
			written = true;
		}
		
		return written;
	}
	
	public String ReadOfflineMessages(String username)throws IOException
	{
		String message ="";
		
		File f = new File(username+".txt");
		char[] buffer = new char[(int) f.length()];		
		if(f.exists())
		{			
			FileReader fr = new FileReader(f);
			fr.read(buffer);			
			fr.close();		
			for(char c : buffer)
			{
				message+= c;
			}			
			f.delete();
		}		
		return message;
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
	        		u.setOnline(true);
	        		System.out.println("Server : Geconnecteerd: "+u.toString());
	        	}	        
	        }
	    }	
		if(!found)
			System.out.println("Ongeldige username en wachtwoord");
		else 
		{
			System.out.println("Aangemeld");
			writeUsersXML();
		}
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
		        		        	
		        }
		    }	
	        if(ok)
	        {
	        	userlist.getUsers().add(n);
	        	System.out.println("User created: " + n.toString());
	        	writeUsersXML();
	        }
		}
		else
		{	
			System.out.println("Geen users in lijst, eerste aanmaken..");
			userlist.getUsers().add(n);		
        	writeUsersXML();
        	ok = true;
        	System.out.println("Eerste user created: " + n.toString());
		}
		return ok;
	}
	
	public boolean CheckOnline(String userName)throws RemoteException
	{
		boolean online = false;
		List<User> temp = userlist.getUsers();
		for(User u : temp)
		{			
			if(u.getLogin().equals(userName) && u.getOnline()) 
			{	
				online = true;
				
			}		
		}		
		return online;
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
		/*	else
			{
				ok = false;
				System.out.println("User not found!");	        	
			}*/	
		
		}		
		if(ok)
		{
			userlist.setUsers(temp);;		
			writeUsersXML();
		}
		return ok;
	}

	@Override
	public void addFriend(String myUsername, String friendUsername) throws RemoteException
	{
		boolean exists = false;
		List<String> friends = getFriends(myUsername);
		for(String f: friends)
		{
			if(f.equals(friendUsername))
			{
				System.out.println("Deze vriend bestaat al");
				exists = true;				
			}
		}	
		if(!exists)
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
	        else
	        {
	        	p.setOutPort(0);	        	
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
		List<User> temp = userlist.getUsers();
		for(User u : temp)
		{
	        if(u.getLogin().equals(username))
	        {
	        	//p = u;
	        	return u.getFriendList();
	        }
		}		
		return Collections.<String>emptyList();
	}
	
	private void readUsersXML()
	{
		try 
		{	
			System.out.println("reading from xml");
		
			JAXBContext jaxbContext = JAXBContext.newInstance(Users.class);
	    	Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		    
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

