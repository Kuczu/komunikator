package communicatorServer.models.friend;

import communicatorServer.models.user.UserService;
import org.bson.types.ObjectId;

import java.util.List;

public class PendingFriendService {
	public static PendingFriendRequest getPendingRequestFor(ObjectId userId1, ObjectId userId2) {
		return PendingFriendDAO.getByUsersId(userId1, userId2);
	}
	
	public static List<PendingFriendRequest> getAllPendingRequestsToAccept(ObjectId userId) {
		return PendingFriendDAO.getByUserId(userId);
	}
	
	public static void changeStatus(PendingFriendRequest pendingFriendRequest, boolean status) {
		if (status) {
			acceptRequest(pendingFriendRequest);
		} else {
			rejectRequest(pendingFriendRequest);
		}
	}
	
	public static void acceptRequest(PendingFriendRequest pendingFriendRequest) {
		UserService.addFriend(pendingFriendRequest.getAskedUserId(), pendingFriendRequest.getRequestingUserId());
		PendingFriendDAO.delete(pendingFriendRequest);
	}
	
	public static void rejectRequest(PendingFriendRequest pendingFriendRequest) {
		PendingFriendDAO.delete(pendingFriendRequest);
	}
	
	public static PendingFriendRequest addPendingRequest(ObjectId requestingUser, ObjectId askedUser) {
		PendingFriendRequest pendingFriendRequest = new PendingFriendRequest();
		pendingFriendRequest.setRequestingUserId(requestingUser);
		pendingFriendRequest.setAskedUserId(askedUser);
		
		PendingFriendDAO.save(pendingFriendRequest);
		
		return pendingFriendRequest;
	}
}
