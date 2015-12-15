package GUI;

import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.List;

public class Invites extends Thread {
	private static List<Invite> inviteList;
	private String[] receivedInvite;
	private boolean newInvite;
	//private String receivedInvite;
	ServerSocket listenForInvitessocket;
	
	public Invites() throws IOException {
		this.inviteList = Collections.<Invite>emptyList();
		listenForInvitessocket = new ServerSocket(6789);
	}
	
	public static List<Invite> ShowAllInvites() {
		return inviteList;
	}
	
	public void run() {
		while(true) {
			Socket connectionSocket;
			try {
				connectionSocket = listenForInvitessocket.accept();
				BufferedReader readIncommingInvite = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				receivedInvite = readIncommingInvite.readLine().split(";");
				newInvite = true;
				for (int i = 0; i < inviteList.size(); i ++) {
					if (receivedInvite[0] == inviteList.get(i).getUsername()) {
						newInvite = false;
					}
				}
				if (newInvite) {
					inviteList.add(new Invite(receivedInvite[0], receivedInvite[1], receivedInvite[2], receivedInvite[3]));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
