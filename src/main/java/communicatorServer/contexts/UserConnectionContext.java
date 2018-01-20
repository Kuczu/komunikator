package communicatorServer.contexts;

import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import org.bson.types.ObjectId;
import org.glassfish.grizzly.websockets.WebSocket;
import socketServerCommunication.responses.Response;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class UserConnectionContext {
	private static final SetMultimap<ObjectId, WebSocket> USERID_TO_SOCKETS_MAP = MultimapBuilder
			.hashKeys(100)
			.hashSetValues()
			.build();
	
	private static final HashMap<WebSocket, ObjectId> SOCKET_TO_USERID = new HashMap<>(100);
	
	public synchronized static void addLoggedUser(ObjectId userId, WebSocket socket) {
		USERID_TO_SOCKETS_MAP.put(userId, socket);
		SOCKET_TO_USERID.put(socket, userId);
	}
	
	public synchronized static void deleteUserSocket(ObjectId userId, WebSocket socket) {
		USERID_TO_SOCKETS_MAP.get(userId).remove(socket);
		SOCKET_TO_USERID.remove(socket);
	}
	
	public synchronized static void deleteUserSocket(WebSocket socket) {
		ObjectId userId = SOCKET_TO_USERID.remove(socket);
		USERID_TO_SOCKETS_MAP.get(userId).remove(socket);
	}
	
	public synchronized static ObjectId getUserId(WebSocket socket) {
		return SOCKET_TO_USERID.get(socket);
	}
	
	public synchronized static Set<WebSocket> getUserSockets(ObjectId userId) {
		return USERID_TO_SOCKETS_MAP.get(userId);
	}
	
	public synchronized static Set<ObjectId> getLoggedUsersId() {
		return Collections.unmodifiableSet(USERID_TO_SOCKETS_MAP.keySet());
	}
	
	public synchronized static Set<ObjectId> sendNotification(Set<ObjectId> usersToNotify, Response response) {
		Set<ObjectId> inactiveUserTds = new HashSet<>();
		
		for (ObjectId userId : usersToNotify) {
			boolean userStatus = sendNotification(userId, response);
			
			if (!userStatus) {
				inactiveUserTds.add(userId);
			}
		}
		
		return inactiveUserTds;
	}
	
	public synchronized static boolean sendNotification(ObjectId userIdToNotify, Response response) {
		Set<WebSocket> userSockets = USERID_TO_SOCKETS_MAP.get(userIdToNotify);
		
		if (userSockets == null) {
			return false;
		}
		
		for (WebSocket userSocket : userSockets) {
			userSocket.send(response.getResponseToSend());
		}
		
		return true;
	}
}
