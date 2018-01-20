package communicatorServer.controllers;

import communicatorServer.activeUsers.ActiveUsersService;
import communicatorServer.activeUsers.NotificationService;
import communicatorServer.activeUsers.UserWithStatus;
import communicatorServer.contexts.ControllersContext;
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
		
		if (UserService.usersAreFriends(userToAdd, userId)) {
			return new Response("'body':" + "'Users are already friends'");
		}
		
		if (PendingFriendService.getPendingRequestFor(userId, userToAdd.getId()) != null) {
			return new Response("'body':" + "'Request already is pending'");
		}
		
		PendingFriendRequest pendingFriendRequest = PendingFriendService.addPendingRequest(userId, userToAdd.getId());
		
		NotificationService.notifyAboutFriendRequest(pendingFriendRequest);
		
		return new Response("'status':'git'");
	}
	
	@ApiPath(path = "/confirmFriend")
	public Response confirmFriendship(Request request) {
		ObjectId userId = request.getUserId();
		
		String userNameToAdd = request
				.getBodyAsJsonObj()
				.get("userNick")
				.getAsString();
		
		String requestStatus = request
				.getBodyAsJsonObj()
				.get("status")
				.getAsString();
		
		User user = UserService.getUserBy(userNameToAdd);
		
		PendingFriendRequest pendingRequest = PendingFriendService.getPendingRequestFor(userId, user.getId());
		
		if (pendingRequest == null) {
			return new Response("'body':" + "'There is no request to procced'");
		}
		
		boolean status = requestStatus.equals("accepted");
		
		PendingFriendService.changeStatus(pendingRequest, status);
		
		NotificationService.notifyAboutFriendStatusChange(pendingRequest, status);
		
		if (status) {
			UserWithStatus userWithStatus = ActiveUsersService.getUserWithStatus(user);
			return new Response(ControllersContext.gson
					.toJson(userWithStatus));
		}
		
		return new Response("'body':" + "'git'");
	}
}
