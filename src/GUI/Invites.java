package GUI;

import java.util.Collections;
import java.util.List;

public class Invites extends Thread {
	private List<String> inviteList;
	
	public Invites() {
		this.inviteList = Collections.<String>emptyList();
	}
	
	public void run() {
		while(true) {
			try {
				
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
