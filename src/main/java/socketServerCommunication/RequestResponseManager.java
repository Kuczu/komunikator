package socketServerCommunication;

import communicatorServer.models.user.User;
import communicatorServer.models.user.UserService;
import socketServerCommunication.requests.Request;
import socketServerCommunication.requests.RequestProcessor;
import socketServerCommunication.responses.Response;
import socketServerCommunication.responses.ResponseProcessor;
import socketServerCommunication.utils.ControllerHandler;

import java.lang.reflect.InvocationTargetException;

public class RequestResponseManager {
	private final ResponseProcessor responseProcessor;
	private final RequestProcessor requestProcessor;
	private final ControllerHandler controllerHandler;
	
	public RequestResponseManager(ResponseProcessor responseProcessor, RequestProcessor requestProcessor, ControllerHandler controllerHandler) {
		this.responseProcessor = responseProcessor;
		this.requestProcessor = requestProcessor;
		this.controllerHandler = controllerHandler;
	}
	
	public Response proceedRequest(Request request) throws InvocationTargetException, IllegalAccessException {
		requestProcessor.proceed(request);
		
		Response response = controllerHandler.proceed(request);
		
		if (response.getUser() == null && request.getUserId() != null) {
			User user = UserService.getUserBy(request.getUserId());
			response.setUser(user);
		}
		
		if (response.getClientAppApiPath() == null) {
			response.setClientAppApiPath(request.getApiPath());
		}
		
		responseProcessor.proceed(response);
		
		return response;
	}
}
