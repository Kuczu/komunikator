package communicatorServer.models.conversation;

import org.bson.types.ObjectId;

import java.util.Date;

public class Message {
	private String message;
	private ObjectId userId;
	private String userName;
	private Date date;
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public ObjectId getUserId() {
		return userId;
	}
	
	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
}
