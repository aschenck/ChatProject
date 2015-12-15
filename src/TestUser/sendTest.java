package TestUser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class sendTest
{
	private int outPort;
	private String ip, message;
	public static void main(String[] args)
	{
		try {
			String sentence;
			Socket sendsocket = new Socket("localhost", 49651);
			//outToServer leest uitgaande data op clientSocket
			DataOutputStream outToServer = new DataOutputStream(sendsocket.getOutputStream());
			
			//Input
			sentence = "0001:<Frederik> Yoooo";
			outToServer.writeBytes(sentence + '\n');
			System.out.println("Sent to server: " + sentence);
			sendsocket.close();
				
			}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("<CLIENT> Error");
		}					
	}

}
