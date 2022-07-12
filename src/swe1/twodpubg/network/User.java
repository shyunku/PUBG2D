package swe1.twodpubg.network;

public class User {
	private String userName;
	private String ip;

	public User(String userName, String ip) {
		this.userName = userName;
		this.ip = ip;
	}
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
