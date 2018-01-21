package grizzly;

import communicatorServer.activeUsers.ActiveUsersService;
import communicatorServer.contexts.UserConnectionContext;
import org.glassfish.grizzly.websockets.DataFrame;
import org.glassfish.grizzly.websockets.WebSocket;
import org.glassfish.grizzly.websockets.WebSocketApplication;
import socketServerCommunication.config.SocketContextsProvider;
import socketServerCommunication.requests.Request;
import socketServerCommunication.responses.Response;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatApplication extends WebSocketApplication {
	private static final Logger LOGGER = Logger.getLogger(ChatApplication.class.getName());
	
	public ChatApplication() {
		super();
	}
	
	@Override
	public void onConnect(WebSocket socket) {
		LOGGER.log(Level.INFO, "New connection: " + socket.toString());
		super.onConnect(socket);
	}
	
	@Override
	public void onClose(WebSocket socket, DataFrame frame) {
		LOGGER.log(Level.INFO, "Ended connection: " + socket.toString());
		super.onClose(socket, frame);
		ActiveUsersService.logout(socket);
	}
	
	@Override
	public void onMessage(WebSocket socket, String text) {
		System.out.println("onMessage recived: " + text + " s: " + socket); // TODO remove
		Request request = new Request(text, socket);
		
		try {
			Response response = SocketContextsProvider.REQUEST_RESPONSE_MANAGER.proceedRequest(request);
			System.out.println("response: " + response.getResponseToSend());
			socket.send(response.getResponseToSend());
		} catch (Exception e) {
			e.printStackTrace();
			socket.send("error");
		}
	}
}
