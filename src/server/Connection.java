package server;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Connection 
{
	public static void main(String[] args)
	{
		try
		{
			// Create a reference to an implementation object…
			Server ServerObj = new Server();
			System.out.println("Object Created");
			
			// Op poort 1099 stelt de RMI het ServerObj ter beschikking  
			// Het ServerObj is een instantie van de Server class en iplementeert de ServerInterface
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.bind("ChatServer", ServerObj); 
		
			// SHOW IP ADDRESS OF SERVER IN CONSOLE
			InetAddress IP=InetAddress.getLocalHost();
			System.out.println(IP.toString());
			
			System.out.println("Binding complete");
		}
		catch (Exception e)
		{
			System.out.println("Binding failed");
			e.printStackTrace();
		}
		
	}
}
