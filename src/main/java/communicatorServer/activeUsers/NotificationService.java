package communicatorServer.activeUsers;

import communicatorServer.contexts.ControllersContext;
import communicatorServer.contexts.UserConnectionContext;
import communicatorServer.models.conversation.Message;
import communicatorServer.models.friend.PendingFriendRequest;
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
		
		String body = ControllersContext.GSON
				.toJson(message);
		
		Response response = new Response(body);
		response.setClientAppApiPath("/messageNotification");
		DataDecryptorEncryptor.getInstance().proceed(response);
		
		UserConnectionContext.sendNotification(userId, response);
	}
	
	public static void notifyAboutStatusChange(User user, boolean status) {
		Set<ObjectId> friendsId = user.getFriendEntities()
				.stream()
				.map(FriendEntity::getUserId)
				.collect(Collectors.toSet());
		
		String body = ControllersContext.GSON
				.toJson(new UserWithStatus(user.getNick(), status));
		
		Response response = new Response(body);
		response.setClientAppApiPath("/userStatus");
		DataDecryptorEncryptor.getInstance().proceed(response);
		
		UserConnectionContext.sendNotification(friendsId, response);
	}
	
	public static void notifyAboutFriendRequest(PendingFriendRequest pendingFriendRequest) {
		User user = UserService.getUserBy(pendingFriendRequest.getRequestingUserId());
		
		Response response = new Response("'askingUser:'" + user.getNick() + "''");
		response.setClientAppApiPath("/newFriendRequest");
		DataDecryptorEncryptor.getInstance().proceed(response);
		
		UserConnectionContext.sendNotification(pendingFriendRequest.getAskedUserId(), response);
	}
	
	public static void notifyAboutFriendStatusChange(PendingFriendRequest pendingFriendRequest, boolean status) {
		if (!status) {
			return; // TODO for now if rejected do nothing
		}
		
		UserWithStatus userWithStatus = ActiveUsersService.getUserWithStatus(pendingFriendRequest.getRequestingUserId());
		Response response = new Response(ControllersContext.GSON
				.toJson(userWithStatus));
		response.setClientAppApiPath("/friendAccepted");
		DataDecryptorEncryptor.getInstance().proceed(response);
		
		UserConnectionContext.sendNotification(pendingFriendRequest.getRequestingUserId(), response);
	}
}
