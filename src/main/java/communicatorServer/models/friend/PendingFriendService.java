package communicatorServer.models.friend;

import communicatorServer.models.user.UserService;
import org.bson.types.ObjectId;

public class PendingFriendService {
	public static PendingFriendRequest getPendingRequestFor(ObjectId userId1, ObjectId userId2) {
		return PendingFriendDAO.getByUsersId(userId1, userId2);
	}
	
	public static void acceptRequest(PendingFriendRequest pendingFriendRequest) {
		UserService.addFriend(pendingFriendRequest.getAskedUserId(), pendingFriendRequest.getRequestingUserId());
		PendingFriendDAO.delete(pendingFriendRequest);
	}
	
	public static void rejectRequest(PendingFriendRequest pendingFriendRequest) {
		PendingFriendDAO.delete(pendingFriendRequest);
	}
	
	public static void addPendingRequest(ObjectId requestingUser, ObjectId askedUser) {
		PendingFriendRequest pendingFriendRequest = new PendingFriendRequest();
		pendingFriendRequest.setRequestingUserId(requestingUser);
		pendingFriendRequest.setAskedUserId(askedUser);
		
		PendingFriendDAO.save(pendingFriendRequest);
	}
}
