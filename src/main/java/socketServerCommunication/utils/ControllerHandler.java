package socketServerCommunication.utils;

import communicatorServer.config.ConfigContext;
import communicatorServer.config.ControllerInstance;
import socketServerCommunication.requests.Request;
import socketServerCommunication.responses.Response;

import java.lang.reflect.InvocationTargetException;

public class ControllerHandler {
	public Response proceed(Request request) throws InvocationTargetException, IllegalAccessException {
		ControllerInstance controllerInstance = ConfigContext.getControllerInstanceForApiPath(request.getApiPath());
		
		if (controllerInstance.isAuthenticationNeeded() != request.userIsAuthenticated()) {
			throw new IllegalCallerException("User is not authenticated!"); // TODO
		}
		
		return controllerInstance.invokeControllerMethod(request);
	}
	//TODO
}
