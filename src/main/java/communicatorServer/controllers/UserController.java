package communicatorServer.controllers;

import com.google.gson.JsonObject;
import communicatorServer.contexts.ControllersContext;
import communicatorServer.controllers.Config.ApiPath;
import communicatorServer.controllers.Config.Controller;
import communicatorServer.models.user.User;
import communicatorServer.models.user.UserService;
import socketServerCommunication.requests.Request;
import socketServerCommunication.responses.Response;

@Controller
public class UserController {
	@ApiPath(path = "/register", needAuthenticate = false)
	public Response registerUser(Request request) {
		JsonObject jsonRequest = request.getBodyAsJsonObj();
		
		try {
			User user = UserService.addNewUser( // TODO wrap exception and handle it after controller invocation
					jsonRequest.get("nick").getAsString(),
					jsonRequest.get("password").getAsString()
			);
			
			return new Response(user);
		} catch (Exception e) {
			return new Response("'body':" + "'" + e.getMessage() + "'");
		}
	}
	
	@ApiPath(path = "/login", needAuthenticate = false)
	public Response loginUser(Request request) {
		JsonObject jsonRequest = request.getBodyAsJsonObj();
		
		try {
			User user = UserService.login( // TODO wrap exception and handle it after controller invocation
					jsonRequest.get("nick").getAsString(),
					jsonRequest.get("password").getAsString()
			);
			
			Response response = new Response(user);
			response.setJsonBody("'status':'git'");
			
			return response;
		} catch (Exception e) {
			return new Response("'body':" + "'" + e.getMessage() + "'");
		}
	}
	
	@ApiPath(path = "/friendList")
	public Response getFriendList(Request request) {
		User user = UserService.getUserBy(request.getUserId());
		
		return new Response(
				ControllersContext.gson
						.toJson(user.getFirendsIdList())
		);
	}
}
