package grizzly;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.websockets.WebSocketAddOn;
import org.glassfish.grizzly.websockets.WebSocketEngine;

import java.io.IOException;

public class GrizzlyMain {
	public static void main(String[] args) throws IOException {
		final HttpServer server = HttpServer.createSimpleServer(null, "127.0.0.1", 8080);
		final WebSocketAddOn addon = new WebSocketAddOn();
		
		for (NetworkListener listener : server.getListeners()) {
			listener.registerAddOn(addon);
		}
		
		WebSocketEngine.getEngine().register("", "/test", new ChatApplication());
		server.start();
		System.in.read();
	}
}
