package communicatorServer.config;

import socketServerCommunication.requests.Request;
import socketServerCommunication.responses.Response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ControllerInstance {
	private final boolean needAuthenticate;
	private final Object object;
	private final Method methodToInvoke;
	private final Class clazz;
	
	public ControllerInstance(boolean needAuthenticate, Object object, Method methodToInvoke, Class clazz) {
		this.needAuthenticate = needAuthenticate;
		this.object = object;
		this.methodToInvoke = methodToInvoke;
		this.clazz = clazz;
	}
	
	public boolean isAuthenticationNeeded() {
		return needAuthenticate;
	}
	
	public Object getObject() {
		return object;
	}
	
	public Method getMethodToInvoke() {
		return methodToInvoke;
	}
	
	public Class getClazz() {
		return clazz;
	}
	
	public Response invokeControllerMethod(Request body) throws InvocationTargetException, IllegalAccessException {
		return (Response) methodToInvoke.invoke(object, body);
	}
}