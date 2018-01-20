package communicatorServer.activeUsers;

import communicatorServer.contexts.UserConnectionContext;
import communicatorServer.models.user.FriendEntity;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ActiveUsersService {
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
