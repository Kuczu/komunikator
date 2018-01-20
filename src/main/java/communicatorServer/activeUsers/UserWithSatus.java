package communicatorServer.activeUsers;

public class UserWithSatus {
	private String nick;
	private boolean status;
	
	public UserWithSatus(String nick, boolean status) {
		this.nick = nick;
		this.status = status;
	}
	
	public String getNick() {
		return nick;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public boolean isStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
}
