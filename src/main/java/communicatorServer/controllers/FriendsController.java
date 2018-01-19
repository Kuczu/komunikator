package communicatorServer.controllers;

import communicatorServer.controllers.Config.ApiPath;
import communicatorServer.controllers.Config.Controller;
import communicatorServer.models.friend.PendingFriendRequest;
import communicatorServer.models.friend.PendingFriendService;
import communicatorServer.models.user.User;
import communicatorServer.models.user.UserService;
import org.bson.types.ObjectId;
import socketServerCommunication.requests.Request;
import socketServerCommunication.responses.Response;

import java.util.Objects;

@Controller
public class FriendsController {
	@ApiPath(path = "/addToFriend")
	public Response addToFriend(Request request) {
		ObjectId userId = request.getUserId();
		
		String userNameToAdd = request
				.getBodyAsJsonObj()
				.get("userNick")
				.getAsString();
		
		User userToAdd = UserService.getUserBy(userNameToAdd);
		
		if (userToAdd == null || Objects.equals(userId, userToAdd.getId())) {
			return new Response("'body':" + "'User with given name doesn't exists'");
		}
		
		if (userToAdd.getFirendsIdList().contains(userId)) {
			return new Response("'body':" + "'Users are already friends'");
		}
		
		if (PendingFriendService.getPendingRequestFor(userId, userToAdd.getId()) != null) {
			return new Response("'body':" + "'Request already is pending'");
		}
		
		PendingFriendService.addPendingRequest(userId, userToAdd.getId());
		
		// TODO notify user
		
		return new Response("'status':'git'");
	}
	
	@ApiPath(path = "/confirmFriend")
	public Response confirmFriendship(Request request) {
		ObjectId userId = request.getUserId();
		
		String userNameToAdd = request
				.getBodyAsJsonObj()
				.get("userNick")
				.getAsString();
		
		String status = request
				.getBodyAsJsonObj()
				.get("status")
				.getAsString();
		
		User user = UserService.getUserBy(userNameToAdd);
		
		PendingFriendRequest pendingRequest = PendingFriendService.getPendingRequestFor(userId, user.getId());
		
		if (pendingRequest == null) {
			return new Response("'body':" + "'There is no request to procced'");
		}
		
		if (status.equals("accepted")) {
			PendingFriendService.acceptRequest(pendingRequest);
		} else {
			PendingFriendService.rejectRequest(pendingRequest);
		}
		
		return new Response("'body':" + "'git'");
	}
}
