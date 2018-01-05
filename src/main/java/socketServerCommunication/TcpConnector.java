package socketServerCommunication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TcpConnector {
	private static final Logger LOGGER = Logger.getLogger(TcpConnector.class.getName());
	
	private ServerSocket serverSocket;
	
	public void start(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		LOGGER.log(Level.INFO, "Listening...");
		
		while (true) {
			try {
				Socket newSocket = serverSocket.accept();
				new ClientSocketHandler(newSocket).run();
			} catch (Exception e) {
				LOGGER.log(Level.WARNING, "Connection crashed, " + e);
			}
		}
	}

	public void stop() throws IOException {
		serverSocket.close();
	}
}
