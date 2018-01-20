package communicatorServer.activeUsers;

import communicatorServer.contexts.UserConnectionContext;
import communicatorServer.models.user.FriendEntity;
import communicatorServer.models.user.User;
import communicatorServer.models.user.UserService;
import org.bson.types.ObjectId;
import org.glassfish.grizzly.websockets.WebSocket;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ActiveUsersService {
	public static void logout(WebSocket webSocket) {
		logout(UserConnectionContext.getUserId(webSocket), webSocket);
	}
	
	public static void logout(ObjectId userId, WebSocket webSocket) {
		logout(UserService.getUserBy(userId), webSocket);
	}
	
	public static void logout(User user, WebSocket webSocket) {
		if (user == null) {
			return;
		}
		
		UserConnectionContext.deleteUserSocket(user.getId(), webSocket);
		
		if (!getUserStatus(user.getId())) {
			NotificationService.notifyAboutStatusChange(user, false);
		}
	}
	
	public static boolean getUserStatus(ObjectId userId) {
		Set<ObjectId> loggedUsersId = UserConnectionContext.getLoggedUsersId();
		
		return loggedUsersId.contains(userId);
	}
	
	public static List<UserWithSatus> getUsersWithStatus(Collection<FriendEntity> friendEntities) {
		Set<ObjectId> loggedUsersId = UserConnectionContext.getLoggedUsersId();
		
		return friendEntities
				.stream()
				.map(e -> new UserWithSatus(
						e.getNick(),
						loggedUsersId.contains(e.getUserId()))
				).collect(Collectors.toList());
	}
}
