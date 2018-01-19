package socketServerCommunication.requests;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.bson.types.ObjectId;
import org.glassfish.grizzly.websockets.WebSocket;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Request {
	private static final Logger LOGGER = Logger.getLogger(Request.class.getName());
	private static final JsonParser JSON_PARSER = new JsonParser();
	
	private final WebSocket clientWebSocket;
	
	private String rawRequest;
	
	private String apiPath;
	private String JWT;
	private String body;
	
	private ObjectId userId;
	
	public Request(String rawRequest, WebSocket clientWebSocket) {
		this.rawRequest = rawRequest;
		this.clientWebSocket = clientWebSocket;
	}
	
	public WebSocket getClientWebSocket() {
		return clientWebSocket;
	}
	
	public String getRawRequest() {
		return rawRequest;
	}
	
	public void setRawRequest(String rawRequest) {
		this.rawRequest = rawRequest;
	}
	
	public String getApiPath() {
		return apiPath;
	}
	
	public void setApiPath(String apiPath) {
		this.apiPath = apiPath;
	}
	
	public String getJWT() {
		return JWT;
	}
	
	public void setJWT(String JWT) {
		this.JWT = JWT;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public ObjectId getUserId() {
		return userId;
	}
	
	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}
	
	public boolean userIsAuthenticated() {
		return userId != null;
	}
	
	public JsonObject getBodyAsJsonObj() {
		JsonObject jsonObject;
		
		try {
			jsonObject = JSON_PARSER.parse(body).getAsJsonObject();
		} catch (JsonSyntaxException e) { // TODO check it earlier and send err code? || make body type injectable?
			LOGGER.log(Level.INFO, rawRequest + " is not a valid json ; " + e.getMessage());
			jsonObject = new JsonObject();
		}
		
		return jsonObject;
	}
}
