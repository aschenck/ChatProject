package user;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class UserSocket
{
	Socket clSocket;
	
	public UserSocket(InetAddress ip, int port) throws IOException
	{
		clSocket = new Socket(ip, port);
	}

	public Socket getClSocket()
	{
		return clSocket;
	}

	public void setClSocket(Socket clSocket)
	{
		this.clSocket = clSocket;
	}
}