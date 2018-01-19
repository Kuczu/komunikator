package grizzly;

import communicatorServer.config.ConfigManager;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.websockets.WebSocketAddOn;
import org.glassfish.grizzly.websockets.WebSocketEngine;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GrizzlyMain {
	private static final Logger LOGGER = Logger.getLogger(GrizzlyMain.class.getName());
	private static final String CHAT_ENDPOINT = "/chat";
	
	public static void main(String[] args) throws Exception {
		ConfigManager.initializeConfigContext();
		
//		final HttpServer server = HttpServer.createSimpleServer(null, "127.0.0.1", 7777);
		final HttpServer server = HttpServer.createSimpleServer(null, "10.60.0.84", 7777);
		final WebSocketAddOn addon = new WebSocketAddOn();
		
		for (NetworkListener listener : server.getListeners()) {
			listener.registerAddOn(addon);
		}
		
		WebSocketEngine.getEngine().register("", CHAT_ENDPOINT, new ChatApplication());
		LOGGER.log(Level.INFO, "Registered chat endpoint "  + CHAT_ENDPOINT);
		
		server.start();
		System.in.read();
	}
}
