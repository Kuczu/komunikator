package communicatorServer.models.user;

import org.bson.types.ObjectId;

public class FriendEntity {
	private ObjectId userId;
	private String nick;
	
	public ObjectId getUserId() {
		return userId;
	}
	
	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}
	
	public String getNick() {
		return nick;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}
}
