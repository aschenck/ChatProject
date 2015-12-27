package user;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * this class sets up an outgoing socket to the person you are chatting with and sends
 * messages entered by the user to this person
 * @author Anthony
 *
 */
public class ClientSendThread implements Runnable
{
	//the port the socket needs to connect with
	private int outPort;
	//the ip address to connect with
	private InetAddress ip;
	//the message to be sent
	private String message;
	//the socket used for the connection
	public Socket clientSocket;
	//boolean to see if the socket is still open
	private boolean connected;
	
	/**
	 * Constructor for this class
	 * @param outPort the port to connect with
	 * @param inetAddress the ip addres to connect with
	 */
	public ClientSendThread(int outPort, InetAddress inetAddress)
	{
		this.outPort = outPort;
		this.ip = inetAddress;
		this.message = "";
		connected = true;
	}
	/**
	 * Code that is executed when the thread is started.
	 * 
	 * A new socket is created with the specified port and ip address.
	 * The message is set in the GUI textfield and sent here.
	 * If the message is not empty it will be send to the chat partner.
	 * If the message contains the closecode "1111" the socket will be closed
	 */
	public void run()
	{	
		System.out.println("Send thread started!");
		try
		{	
			clientSocket = new Socket(ip, this.outPort);
			System.out.println("SendThread: OutPort: " +this.outPort);
			while(connected)
			{
				if(!getMessage().equals(""))
				{
					if(!getMessage().substring(0, 4).equals("1111"))
					{
						DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
						outToServer.writeBytes(getMessage() + '\n');
						System.out.println("SendThread: MESSAGE SENT" + getMessage());
						setMessage("");
					}
					else
					{
						CloseSocket();
					}
				}

			}			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("SendThread: EXIT FROM LOOP");
	}
	
	/**
	 * method to close the current socket and exit from the loop
	 */
	public void CloseSocket()
	{
		try
		{
			connected = false;
			clientSocket.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * getters and setters
	 */
	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}	
}
