package socketServerCommunication;

import communicatorServer.contexts.UserConnectionContext;
import communicatorServer.models.User.User;
import socketServerCommunication.requests.ConnectionRequestStatus;
import socketServerCommunication.requests.Request;
import socketServerCommunication.requests.SocketRequestParser;
import socketServerCommunication.responses.Response;
import socketServerCommunication.config.SocketContextsProvider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSocketHandler implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(ClientSocketHandler.class.getName());
	
	private User user;
	
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
//		private InputStreamReader in;
	
	public ClientSocketHandler(Socket socket) {
		this.clientSocket = socket;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUserAndSubscribe(User user) {
		this.user = user;
		UserConnectionContext.addLoggedUser(user.getId(), this);
	}
	
	public void unsubscribe() {
		UserConnectionContext.deleteUserSocket(user.getId(), this);
	}
	
	@Override
	public void run() {
		
		LOGGER.log(Level.INFO, "Starting new connection at: " + clientSocket.toString());
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			InputStream socketInputStream = clientSocket.getInputStream();
			in = new BufferedReader(new InputStreamReader(socketInputStream));
//				in = new InputStreamReader(socketInputStream);
			
			String input;
			while (!clientSocket.isClosed()) {
				input = in.readLine();
//					System.out.print((char)socketInputStream.read());
//					System.out.print(socketInputStream.available() + " ");
				
				if (input == null) {
					break;
				}
				
				System.out.println(input);
				
				SocketRequestParser requestParser = SocketContextsProvider.getSocketRequestParser();
				requestParser.setRawRequest(input);
				requestParser.preParse();
				
				if (requestParser.getConnectionRequestStatus() == ConnectionRequestStatus.INVALID) {
					LOGGER.log(Level.WARNING, "Request is INVALID");
					out.write("Błąd\n");
					out.flush();
					continue;
				}
				
				System.out.println(new String(Base64.getDecoder().decode(requestParser.getRequestBody())));
				
				if (requestParser.getConnectionRequestStatus() == ConnectionRequestStatus.DISCONNECT) {
					break;
				}
				
				Request request = new Request(requestParser.getRequestBody(), this); // TODO make it injectable
				Response response = SocketContextsProvider.REQUEST_RESPONSE_MANAGER.proceedRequest(request);
				
				out.write(response.getResponseToSend());
				out.flush();
			}
			
			in.close();
			out.close();
			clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			unsubscribe();
			LOGGER.log(Level.INFO, "Ended connection: " + clientSocket.toString());
		}
	}
	
	public void sendUserData(String jsonData) {
	
	}
	
}
