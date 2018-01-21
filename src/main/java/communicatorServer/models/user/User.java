package communicatorServer.models.user;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity("users")
public class User {
	public static final String ID = "_id";
	public static final String NICK = "nick";
	public static final String PASSWORD = "password";
	public static final String JOIN_DATE = "joinDate";
	public static final String FRIENDS_ID_LIST = "friendEntities";
	
	@Id
	private ObjectId id;
	
	private String nick;
	private String password;
	private Date joinDate;
	
	@Embedded
	private List<FriendEntity> friendEntities;
	
	public ObjectId getId() {
		return id;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getJoinDate() {
		return joinDate;
	}
	
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
	public List<FriendEntity> getFriendEntities() {
		if (friendEntities == null) {
			this.friendEntities = new ArrayList<>();
		}
		
		return friendEntities;
	}
	
	public void setFriendEntities(List<FriendEntity> friendEntities) {
		this.friendEntities = new ArrayList<>(friendEntities);
	}
}
