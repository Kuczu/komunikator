package communicatorServer.models.user;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import java.util.HashSet;
import java.util.Set;

@Entity("UserNewActivities")
public class UserNewActivity {
	public static final String ID = "_id";
	public static final String USER_ID = "userId";
	public static final String UNREAD_MESSAGES_USER_IDS = "unreadMessagesUsersName";
	
	@Id
	private ObjectId id;
	
	@Indexed
	private ObjectId userId;
	
	@Embedded
	private Set<String> unreadMessagesUsersName;
	
	public ObjectId getId() {
		return id;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public ObjectId getUserId() {
		return userId;
	}
	
	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}
	
	public Set<String> getUnreadMessagesUsersName() {
		if (unreadMessagesUsersName == null) {
			this.unreadMessagesUsersName = new HashSet<>();
		}
		
		return unreadMessagesUsersName;
	}
	
	public void setUnreadMessagesUsersName(Set<String> unreadMessagesUsersName) {
		this.unreadMessagesUsersName = unreadMessagesUsersName;
	}
}
