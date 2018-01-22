package communicatorServer.activeUsers;

import communicatorServer.contexts.UserConnectionService;
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
		logout(UserConnectionService.getUserId(webSocket), webSocket);
	}
	
	public static void logout(ObjectId userId, WebSocket webSocket) {
		logout(UserService.getUserBy(userId), webSocket);
	}
	
	public static void logout(User user, WebSocket webSocket) {
		if (user == null) {
			return;
		}
		
		UserConnectionService.deleteUserSocket(user.getId(), webSocket);
		
//		if (!getUserStatus(user.getId())) {
			NotificationService.notifyAboutStatusChange(user, false);
//		}
	}
	
	public static UserWithStatus getUserWithStatus(User user) {
		return new UserWithStatus(user.getNick(), getUserStatus(user.getId()));
	}
	
	public static UserWithStatus getUserWithStatus(ObjectId userId) {
		return getUserWithStatus(UserService.getUserBy(userId));
	}
	
	public static boolean getUserStatus(ObjectId userId) {
		Set<ObjectId> loggedUsersId = UserConnectionService.getLoggedUsersId();
		
		return loggedUsersId.contains(userId);
	}
	
	public static List<UserWithStatus> getUsersWithStatus(Collection<FriendEntity> friendEntities) {
		Set<ObjectId> loggedUsersId = UserConnectionService.getLoggedUsersId();
		
		return friendEntities
				.stream()
				.map(e -> new UserWithStatus(
						e.getNick(),
						loggedUsersId.contains(e.getUserId()))
				).collect(Collectors.toList());
	}
}
