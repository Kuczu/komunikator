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
	@Indexed // user to add
	private ObjectId askedUserId;
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
	
	public Date getAddDate() {
		return addDate;
	}
	
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
}
