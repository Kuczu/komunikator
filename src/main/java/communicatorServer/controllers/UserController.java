package communicatorServer.controllers;

import com.google.gson.JsonObject;
import communicatorServer.activeUsers.ActiveUsersService;
import communicatorServer.activeUsers.NotificationService;
import communicatorServer.contexts.ControllersContext;
import communicatorServer.controllers.Config.ApiPath;
import communicatorServer.controllers.Config.Controller;
import communicatorServer.models.friend.PendingFriendRequest;
import communicatorServer.models.friend.PendingFriendService;
import communicatorServer.models.user.User;
import communicatorServer.models.user.UserNewActivity;
import communicatorServer.models.user.UserService;
import socketServerCommunication.requests.Request;
import socketServerCommunication.responses.Response;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class UserController {
	private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
	
	@ApiPath(path = "/register", needAuthenticate = false)
	public Response registerUser(Request request) {
		JsonObject jsonRequest = request.getBodyAsJsonObj();
		
		try {
			User user = UserService.addNewUser( // TODO wrap exception and handle it after controller invocation
					jsonRequest.get("nick").getAsString(),
					jsonRequest.get("password").getAsString()
			);
			
			return new Response(user);
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return new Response("'body':" + "'" + e.getMessage() + "'");
		}
	}
	
	@ApiPath(path = "/login", needAuthenticate = false)
	public Response loginUser(Request request) {
		JsonObject jsonRequest = request.getBodyAsJsonObj();
		
		try {
			User user = UserService.login( // TODO wrap exception and handle it after controller invocation
					jsonRequest.get("nick").getAsString(),
					jsonRequest.get("password").getAsString()
			);
			
			Response response = new Response(user);
			response.setJsonBody("'status':'git'");
			
			NotificationService.notifyAboutStatusChange(user, true);
			
			return response;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return new Response("'body':" + "'" + e.getMessage() + "'");
		}
	}
	
	@ApiPath(path = "/friendList")
	public Response getFriendList(Request request) {
		User user = UserService.getUserBy(request.getUserId());
		
		return new Response(
				ControllersContext.GSON
						.toJson(ActiveUsersService
								.getUsersWithStatus(user.getFriendEntities())
						)
		);
	}
	
	@ApiPath(path = "/lastActivity")
	public Response getLastActivity(Request request) {
		List<PendingFriendRequest> pendingFriendRequests = PendingFriendService.getAllPendingRequestsToAccept(request.getUserId());
		UserNewActivity messageActivity = UserService.getMessageActivity(request.getUserId());
		
		JsonObject jsonObject = new JsonObject();
		
		jsonObject.addProperty("friendRequests", ControllersContext.GSON
					.toJson(pendingFriendRequests));
		
		if (messageActivity != null) {
			jsonObject.addProperty("messagesUsersName", ControllersContext.GSON
					.toJson(messageActivity.getUnreadMessagesUsersName()));
			
			UserService.delete(messageActivity);
		}
		
		return new Response(ControllersContext.GSON.toJson(jsonObject));
	}
}
