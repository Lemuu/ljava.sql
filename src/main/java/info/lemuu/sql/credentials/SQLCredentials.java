package info.lemuu.sql.credentials;

public class SQLCredentials {
	
	private final String host, user, password;
	private final int port;
	
	public SQLCredentials(String host, int port, String user, String password) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
	}

	public String getHost() {
		return this.host;
	}
	public int getPort() {
		return this.port;
	}
	public String getUser() {
		return this.user;
	}
	public String getPassword() {
		return this.password;
	}

}