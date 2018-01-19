package communicatorServer.models.friend;

import com.google.common.collect.Lists;
import communicatorServer.config.ConfigContext;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class PendingFriendDAO {
	private static final Datastore DATASTORE = ConfigContext.getDatastore();
	
	public static List<PendingFriendRequest> getByUserId(ObjectId userId) {
		Query<PendingFriendRequest> query = DATASTORE.createQuery(PendingFriendRequest.class);
		
		query.or(
				query.criteria(PendingFriendRequest.ASKED_USER_ID).equal(userId),
				query.criteria(PendingFriendRequest.REQUESTING_USER_ID).equal(userId)
		);
		
		return query.asList();
	}
	
	public static PendingFriendRequest getByUsersId(ObjectId userId, ObjectId userId2) {
		Query<PendingFriendRequest> query = DATASTORE.createQuery(PendingFriendRequest.class);
		
		List<ObjectId> usersId = Lists.newArrayList(userId, userId2);
		
		query.and(
				query.criteria(PendingFriendRequest.ASKED_USER_ID).hasAnyOf(usersId),
				query.criteria(PendingFriendRequest.REQUESTING_USER_ID).hasAnyOf(usersId)
		);
		
		return query.get();
	}
	
	public static void save(PendingFriendRequest friendPendingRequest) {
		DATASTORE.save(friendPendingRequest);
	}
	
	public static void delete(PendingFriendRequest pendingFriendRequest) {
		DATASTORE.delete(pendingFriendRequest);
	}
}
