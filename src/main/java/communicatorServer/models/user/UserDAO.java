package communicatorServer.models.user;

import communicatorServer.config.ConfigContext;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

public class UserDAO {
	private static final Datastore DATASTORE = ConfigContext.getDatastore();
	
	public static User getById(ObjectId id) {
		return DATASTORE.createQuery(User.class)
				.field(User.ID)
				.equal(id)
				.get();
	}
	
	public static User getByNick(String nick) {
		return DATASTORE.createQuery(User.class)
				.field(User.NICK)
				.equal(nick)
				.get();
	}
	
	public static void save(User user) {
		DATASTORE.save(user);
	}
	
	public static void addFriend(ObjectId userId1, ObjectId userId2) {
		User user1 = getById(userId1);
		User user2 = getById(userId2);
		
		addFriendForOneUser(user1, user2);
		addFriendForOneUser(user2, user1);
	}
	
	private static void addFriendForOneUser(User user1, User user2) {
		FriendEntity friendEntity = new FriendEntity();
		friendEntity.setNick(user2.getNick());
		friendEntity.setUserId(user2.getId());
		
		Query<User> user1q = DATASTORE.createQuery(User.class).field(User.ID).equal(user1.getId());
		UpdateOperations<User> user1u = DATASTORE.createUpdateOperations(User.class).addToSet(User.FRIENDS_ID_LIST, friendEntity);
		
		DATASTORE.update(user1q, user1u);
	}
}
