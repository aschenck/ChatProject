package GUI;

public class Invite {
	private String username;
	private String ipAddress;
	private String listenPort;
	private String sendPort;
	
	public Invite(String username, String ipAddress, String listenPort, String sendPort) {
		this.username = username;
		this.ipAddress = ipAddress;
		this.listenPort = listenPort;
		this.sendPort = sendPort;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	
	public String getListenPort() {
		return listenPort;
	}
	
	public String getSendPort() {
		return sendPort;
	}
}
