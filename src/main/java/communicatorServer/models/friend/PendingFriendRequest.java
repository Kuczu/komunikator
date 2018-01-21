package communicatorServer.models.friend;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import java.util.Date;

@Entity("friendPendingRequests")
public class PendingFriendRequest {
	public static final String ID = "_id";
	public static final String REQUESTING_USER_ID = "requestingUserId";
	public static final String ASKED_USER_ID = "askedUserId";
	
	@Id
	private ObjectId id;
	
	@Indexed
	private ObjectId requestingUserId;
	private String requestingUserName;

	@Indexed // user to add
	private ObjectId askedUserId;
	private String askedUserName;

	private Date addDate;
	
	public ObjectId getRequestingUserId() {
		return requestingUserId;
	}
	
	public void setRequestingUserId(ObjectId requestingUserId) {
		this.requestingUserId = requestingUserId;
	}
	
	public ObjectId getAskedUserId() {
		return askedUserId;
	}
	
	public void setAskedUserId(ObjectId askedUserId) {
		this.askedUserId = askedUserId;
	}
	
	public String getRequestingUserName() {
		return requestingUserName;
	}
	
	public void setRequestingUserName(String requestingUserName) {
		this.requestingUserName = requestingUserName;
	}
	
	public String getAskedUserName() {
		return askedUserName;
	}
	
	public void setAskedUserName(String askedUserName) {
		this.askedUserName = askedUserName;
	}
	
	public Date getAddDate() {
		return addDate;
	}
	
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
}
