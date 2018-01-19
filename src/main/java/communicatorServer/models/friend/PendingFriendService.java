package communicatorServer.models.friend;

import org.bson.types.ObjectId;

public class PendingFriendService {
	public static PendingFriendRequest getPendingRequestFor(ObjectId userId1, ObjectId userId2) {
		return PendingFriendDAO.getByUsersId(userId1, userId2);
	}
	
	public static void addPendingRequest(ObjectId requestingUser, ObjectId askedUser) {
		PendingFriendRequest pendingFriendRequest = new PendingFriendRequest();
		pendingFriendRequest.setRequestingUserId(requestingUser);
		pendingFriendRequest.setAskedUserId(askedUser);
		
		PendingFriendDAO.save(pendingFriendRequest);
	}
}
