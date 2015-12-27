package user;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Class that creates the listenerthreads
 * 
 * @author Anthony
 *
 */

public class ClientListenerThread implements Runnable
{	
	//the listenport for the client
	private int inPort;
	//the message received 
	private String rxText;
	//list of all active listen threads
	private ArrayList<ClientListenerEchoThread> socketsList;
	//list of all invites
	private ArrayList<String> inviteList;

	/**
	 * the constructor for this class
	 * 
	 * @param inPort the port number on which this threads socket will operate
	 */
	public ClientListenerThread(int inPort)
	{
		this.inPort = inPort;
		inviteList = new ArrayList<>();
	}

	/**
	 * Code that is executed when this thread is started.
	 * 
	 * A serversocket is created and whenever a new connection is received, it dispatches this connection to a new ClientListenerEchoThread
	 * in order to be able to manage multiple chat sessions.
	 * 
	 */
	@SuppressWarnings("resource")
	public void run()
	{
		ServerSocket serverSocket = null;
        Socket socket = null;
        try 
        {
            serverSocket = new ServerSocket(inPort);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        while (true)
        {
            try 
            {
                socket = serverSocket.accept();
            } 
            catch (IOException e) 
            {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            new ClientListenerEchoThread(socket, this).start();
        }
	}
	
	/**
	 * getters and setters for this class
	 * 
	 */

	public ArrayList<String> getInviteList()
	{
		return inviteList;
	}

	public void setInviteList(ArrayList<String> inviteList)
	{
		this.inviteList = inviteList;
	}

	public String getRxText()
	{
		return rxText;
	}

	public void setRxText(String rxText)
	{
		this.rxText = rxText;
	}

	public ArrayList<ClientListenerEchoThread> getSocketsList()
	{
		return socketsList;
	}

	public void setSocketsList(ArrayList<ClientListenerEchoThread> socketsList)
	{
		this.socketsList = socketsList;
	}
}
