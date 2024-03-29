package communicatorServer.controllers;

import com.google.common.base.Strings;
import communicatorServer.activeUsers.NotificationService;
import communicatorServer.contexts.ControllersContext;
import communicatorServer.controllers.Config.ApiPath;
import communicatorServer.controllers.Config.Controller;
import communicatorServer.models.conversation.ConversationService;
import communicatorServer.models.conversation.Message;
import communicatorServer.models.user.User;
import communicatorServer.models.user.UserService;
import org.bson.types.ObjectId;
import socketServerCommunication.requests.Request;
import socketServerCommunication.responses.Response;

import java.util.List;

@Controller
public class MessageController {
	@ApiPath(path = "/sendMessage")
	public Response sendMessage(Request request) {
		ObjectId userId = request.getUserId();
		
		String friendUserName = request
				.getBodyAsJsonObj()
				.get("userNick")
				.getAsString();
		
		User friendUser = UserService.getUserBy(friendUserName);
		
		if (!UserService.usersAreFriends(friendUser, userId)) {
			return new Response("'body':" + "'Users are not friends'");
		}
		
		String messageContent = request
				.getBodyAsJsonObj()
				.get("message")
				.getAsString();
		
		if (Strings.isNullOrEmpty(messageContent)) {
			return new Response("'body':" + "'Message cannot be empty'");
		}
		
		Message message = ConversationService.addMessages(UserService.getUserBy(userId), friendUser.getId(), messageContent);
		
		NotificationService.notifyAboutMessage(message, friendUser.getId());
		
		return new Response("'body':" + "'git'");
	}
	
	@ApiPath(path = "/getMessages")
	public Response getMessages(Request request) {
		ObjectId userId = request.getUserId();
		
		String friendUserName = request
				.getBodyAsJsonObj()
				.get("userNick")
				.getAsString();
		
		User friendUser = UserService.getUserBy(friendUserName);
		
		if (friendUser == null) {
			return new Response("'body':" + "'User with given name doesn't exists'");
		}
		
		List<Message> messages = ConversationService.getMessages(userId, friendUser.getId());
		
		return new Response(
				ControllersContext.GSON
						.toJson(messages)
		);
	}
}
