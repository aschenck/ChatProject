package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

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

	@Override
	public boolean loginUser(String username, String password) throws RemoteException 
	{
		boolean found = false;
		
		List<User> temp = userlist.getUsers();
		
		for(User u : temp)
		{			
	        if(u.getLogin().equals(username) && u.getPw().equals(password))
	        {
	        	found = true;
	        }
	        else
	        {
	        	System.out.println(u.getLogin());
	        	System.out.println(u.getPw());
	        }
	    }
		
		return found;
	}

	@Override
	public boolean newUser(String login, String firstname, String lastname, String password, InetAddress ip )throws RemoteException
	{
		// CHECK IF USERNAME ALLREADY EXSIST
		
		// CREATE NEW USER
		User temp = new User(login, firstname, lastname, password, ip);
		// ADD TEMP USER TO USERS COLLECTION
		System.out.println(temp.toString());
		
		userlist.getUsers().add(temp);
		
		
		// WRITE USERS TO XML
		writeUsersXML();
		return false;
	}

	@Override
	public boolean logoutUser(String username, String password) throws RemoteException 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addFriend(String username, String password, String fullname) throws RemoteException 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteFriend(String username, String password, String fullname) throws RemoteException 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User startChat(String username) throws RemoteException 
	{
		List<User> temp = userlist.getUsers();			
		User p = new User();
		for(User u : temp)
		{			
	        if(u.getLogin().equals(username) && u.getOnline())
	        {	
	        	p = u;
	        }
	        else
	        {
	        	p.setOutPort(0);
	        }
	    }		
		return p;
	}

	@Override
	public boolean sendMessage(String username, String password, int chat, String Message) throws RemoteException 
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*@Override
	public ArrayList<Friend> getFriends(String username, String password) throws RemoteException 
	{
		// TODO Auto-generated method stub
		return null;
	}*/
	
	private boolean authorizeUser(String username, String password)
	{
		
		return true;
	}
	
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

