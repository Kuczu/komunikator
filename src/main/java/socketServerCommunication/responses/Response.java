package socketServerCommunication.responses;

import communicatorServer.models.User.User;

public class Response { // TODO add jsonobject inside instead of strings jsonbody, jwt etc
	public static final String MAGIC_START = "MAGIC_START";
	public static final String MAGIC_STOP = "MAGIC_START";
	
	private String jsonBody;
	private String encodedData;
	private String JWT;
	
	private User user;
	
	public Response() {
	}
	
	public Response(String jsonBody) {
		this.jsonBody = jsonBody;
	}
	
	public String getJsonBody() {
		return jsonBody;
	}
	
	public void setJsonBody(String jsonBody) {
		this.jsonBody = jsonBody;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getJWT() {
		return JWT;
	}
	
	public void setJWT(String JWT) {
		this.JWT = JWT;
	}
	
	public String getEncodedData() {
		return encodedData;
	}
	
	public void setEncodedData(String encodedData) {
		this.encodedData = encodedData;
	}
	
	public String getResponseToSend() {
		return Response.MAGIC_START + " " + encodedData + " " + Response.MAGIC_STOP + "\n";
	}
}
