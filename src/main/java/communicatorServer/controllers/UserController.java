package communicatorServer.controllers;

import com.google.gson.JsonObject;
import communicatorServer.controllers.Config.ApiPath;
import communicatorServer.controllers.Config.Controller;
import communicatorServer.models.User.User;
import communicatorServer.models.User.UserService;
import socketServerCommunication.requests.Request;
import socketServerCommunication.responses.Response;

@Controller
public class UserController {
	@ApiPath(path = "/register", needAuthenticate = false)
	public Response registerUser(Request request) {
		JsonObject jsonRequest = request.getBodyAsJsonObj();

		User user = UserService.addNewUser( // TODO wrap exception and handle it after controller invocation
				jsonRequest.get("nick").getAsString(),
				jsonRequest.get("password").getAsString()
		);

		Response response = new Response();
		response.setUser(user);

		return response;
	}
	
	@ApiPath(path = "/login", needAuthenticate = false)
	public Response loginUser(Request request) {
		System.out.println("Dupa debag " + request);
		return new Response();
	}
}
