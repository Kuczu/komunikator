package communicatorServer.contexts;

import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import org.bson.types.ObjectId;
import socketServerCommunication.ClientSocketHandler;

import java.util.Set;

public class UserConnectionContext {
	private static final SetMultimap<ObjectId, ClientSocketHandler> MAP_OF_LOGGED_USERS = MultimapBuilder.hashKeys(100).hashSetValues().build();
	
	public synchronized static void addLoggedUser(ObjectId userId, ClientSocketHandler socket) {
		MAP_OF_LOGGED_USERS.put(userId, socket);
	}
	
	public synchronized static void deleteUserSocket(ObjectId userID, ClientSocketHandler socket) {
		MAP_OF_LOGGED_USERS.get(userID).remove(socket);
	}
	
	public synchronized static Set<ClientSocketHandler> getUserSockets(ObjectId userId) {
		return MAP_OF_LOGGED_USERS.get(userId);
	}
}
