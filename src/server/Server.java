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
/**
 * This is the server class, implementing all methods from the interface and additional features.
 * 
 * 
 * @author Anthony, Willem, Frederik
 * @version 1.0
 *
 */

public class Server extends UnicastRemoteObject implements ServerInterface 
{
	private static final long serialVersionUID = -5165887663036488313L;
	//Initialize the users list
	private Users userlist = new Users();

	/**
	 * Constructor of the server class
	 * Calls readUsersXML() method to so all registered users can be accessed
	 * @throws RemoteException
	 */
	protected Server() throws RemoteException 
	{
		//Read user from xml to list
		readUsersXML();
	}
	/**
	 * Sets the users outport = the chat partners inport
	 * 
	 * @param u the user you want to chat with
	 * @return returns the current listenport of the user you want to chat with and sets it as your outport
	 */
	public int SetOutPort(User u)
	{
		int port;
		port = u.getInPort();		
		return port;
	}
	
	/**
	 * This method generates a port number in the 'free' range of port numbers on a random basis.
	 * The method is called whenever a user logs in to the client, in order for the client to be
	 * ready to accept incoming chat requests.
	 * 
	 * @return returns the inport(or listenport) for the client in the range between 49152 - 65535
	 *
	 */
	public int MakeClientListenerPort() 
	{
		Random rng = new Random();
		int port;
		port = rng.nextInt(16383) + 49152;		
		return port;
	}
	
	/**
	 * Method to send an offline message to a user.
	 * The messages are stored in a text file, named after the user you are sending the messages to.
	 * 
	 * If the file already exists, additional messages are appended to the file.
	 * If the file doesn't exist, it is created.
	 * 
	 * @param username the name of the user you are sending messages to.
	 * @param message the message you are sending the user
	 * @return returns true or false depending on the succes of the sending of the message
	 */
	
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
	/**
	 * Method that returns the stored offline messages for a certain user
	 * The method is called everytime a user logs in.
	 * 
	 * If there are no messages, nothing is returned ( an empty string).
	 * Otherwise the file is read and the contents are passed to the client.
	 * After a file has been read, the file is deleted in order to avoid sending the same message twice.
	 * 
	 * @param username the user for which there are messages stored
	 * @return returns a string containing the offline message (if available)
	 */
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
	
	/**
	 * Method to login a user.
	 * The server checks if the user exists, and verifies if the entered password corresponds with the one stored on the server.
	 * The ip address of the user is added aswell, so other users can connect to the user.
	 * @param username the name of the user logging in.
	 * @param password the password of the user logging in.
	 * @param ip the ip address of the user logging in.
	 * 
	 * @return returns true or false dependant on whether the user provided correct login information.
	 * 
	 */
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
	
	/**
	 * Method to create a new user account.
	 * If no users currently exists, it also creates a new userlist object.
	 * After adding a user, the userlist object is marshalled to an xml file for storage.
	 * 
	 * @param login the login the new user wants to use
	 * @param firstname the firstname of the user
	 * @param latname the lastname of the user
	 * @param password the password the user wants to use
	 * @param ip the current ip address of the user
	 * 
	 * @return returns true or false depending whether the account could be created or not (the selected username already exists)
	 * 
	 */
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
	
	/**
	 * Method to check if a user is online (logged in) or not
	 * 
	 * @param userName the user you want to check
	 * @return returns true or false depending on whether the user is online or not
	 */
	
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

	/**
	 * Method to log out a user.
	 * The new status is written to the xml file.
	 * 
	 * @param username the user to be logged out
	 * @return true if logout was succesful, false if unsuccessful
	 */
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
		}		
		if(ok)
		{
			userlist.setUsers(temp);;		
			writeUsersXML();
		}
		return ok;
	}
	
	/**
	 * Method to add a user to your friendlist.
	 * Checks to see if the user is already on your friendlist before adding to prevent duplicates.
	 * When succesfully added, the friendlist is updated and written to xml.
	 * 
	 * @param myUsername the user who is adding a friend
	 * @param friendUsername the user you want to add to your friendlist
	 * 
	 */
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
	/**
	 * Method to retrieve the current users listenport
	 * 
	 * @param userName the name of the user
	 * @return returns the listenport number of the user
	 */
	
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
	
	/**
	 * Method to retrieve the current users sendport
	 * 
	 * @param userName the name of the user
	 * @return returns the sendport number of the user
	 */
	public int getUserOutPort(String userName)
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
	
	/**
	 * Method to retrieve the ip address of the user you are trying to chat with.
	 * 
	 * @param userName the user you want to chat with
	 * @return returns the ip address of the user you want to chat with
	 */
	
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
		}		//
		return ip;		
	}
	
	/*public User getUserSettings(String myUserName) throws RemoteException
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
	}*/

	/**
	 * Method to remove a user from your friendlist.
	 * Updates the xml after removal
	 * 
	 * @param myUsername the name of the user who wants to delete a friend
	 * @param friendUsername the name of the user you want to delete
	 */
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
	
	
/*	@Override
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
	}*/

	/**
	 * Method to initialize a chat session.
	 * Checks if the user you want to chat with is currently online, otherwise an offline message will be sent.
	 * 
	 * @param username the user you want to chat with.
	 * @return the listenport of the user you want to chat with
	 *
	 */
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
	/**
	 * Method to retrieve a list of your current friends
	 * 
	 * @param username the user who wants to retrieve his friendlist
	 * @return returns a list of the users who have been added as friends.
	 * if no friends exist, an empty list is returned
	 */
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
	
	/**
	 * Method to unmarshal all the registered users and be stored in an object for retrieval/modification purposes
	 */
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
	
	/**
	 * Method to marshal the users to xml in order to maintain an up-to-date copy on disk
	 */
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

