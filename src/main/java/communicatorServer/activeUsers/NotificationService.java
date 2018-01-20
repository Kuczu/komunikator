package communicatorServer.activeUsers;

import communicatorServer.contexts.ControllersContext;
import communicatorServer.contexts.UserConnectionContext;
import communicatorServer.models.conversation.Message;
import communicatorServer.models.user.FriendEntity;
import communicatorServer.models.user.User;
import communicatorServer.models.user.UserService;
import org.bson.types.ObjectId;
import socketServerCommunication.responses.Response;
import socketServerCommunication.utils.DataDecryptorEncryptor;

import java.util.Set;
import java.util.stream.Collectors;

public class NotificationService {
	public static void notifyAboutMessage(Message message, ObjectId userId) {
		if (!ActiveUsersService.getUserStatus(userId)) {
			UserService.addUserMessageActivity(userId, message.getUserId());
			return;
		}
		
		String body = ControllersContext.gson
				.toJson(message);
		
		Response response = new Response(body);
		response.setClientAppApiPath("/messageNotification");
		DataDecryptorEncryptor.getInstance().proceed(response);
		
		UserConnectionContext.sendNotification(userId, response);
	}
	
	public static void notifyAboutStatusChange(User user, boolean status) {
		Set<ObjectId> friendsId = user.getFirendsIdList()
				.stream()
				.map(FriendEntity::getUserId)
				.collect(Collectors.toSet());
		
		String body = ControllersContext.gson
				.toJson(new UserWithSatus(user.getNick(), status));
		
		Response response = new Response(body);
		response.setClientAppApiPath("/userStatus");
		DataDecryptorEncryptor.getInstance().proceed(response);
		
		UserConnectionContext.sendNotification(friendsId, response);
	}
	
	public static void notifyAboutFriendRequest(ObjectId friendIdToNotify) {
	
	}
	
	public static void notifyAboutFriendStatusChange(/*TODO*/) {
	
	}
}
