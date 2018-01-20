package communicatorServer.models.conversation;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import java.util.ArrayList;
import java.util.List;

@Entity("conversations")
public class Conversation {
	public static final String ID = "_id";
	public static final String USER_ID_1 = "userId1";
	public static final String USER_ID_2 = "userId2";
	public static final String MESSAGES = "messages";
	
	@Id
	private ObjectId id;
	
	@Indexed
	private ObjectId userId1;
	@Indexed
	private ObjectId userId2;
	private List<Message> messages;
	
	public ObjectId getId() {
		return id;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public ObjectId getUserId1() {
		return userId1;
	}
	
	public void setUserId1(ObjectId userId1) {
		this.userId1 = userId1;
	}
	
	public ObjectId getUserId2() {
		return userId2;
	}
	
	public void setUserId2(ObjectId userId2) {
		this.userId2 = userId2;
	}
	
	public List<Message> getMessages() {
		if (messages == null) {
			return new ArrayList<>(0);
		}
		return messages;
	}
	
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
}
