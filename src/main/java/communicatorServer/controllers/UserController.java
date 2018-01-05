package communicatorServer.controllers;

import com.google.gson.Gson;
import communicatorServer.controllers.Config.ApiPath;
import communicatorServer.controllers.Config.Controller;
import communicatorServer.models.User.User;
import org.bson.types.ObjectId;
import socketServerCommunication.requests.Request;
import socketServerCommunication.responses.Response;

import java.util.Date;

@Controller
public class UserController {
	@ApiPath(path = "/register", needAuthenticate = false)
	public Response registerUser(Request request) {
		User user = new  User();
		user.setId(new ObjectId());
		user.setJoinDate(new Date());
		user.setNick("dupa");
		
		Gson gson = new Gson();
		System.out.println("Dupa debag " + request.getBody());
		System.out.println(gson.toJson(user));
		System.out.println(gson.toJson(user));
		return new Response(gson.toJson(user));
	}
	
	@ApiPath(path = "/login", needAuthenticate = false)
	public Response loginUser(Request request) {
		System.out.println("Dupa debag " + request);
		return new Response();
	}
}
