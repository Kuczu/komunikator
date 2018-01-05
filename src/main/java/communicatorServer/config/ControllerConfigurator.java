package communicatorServer.config;

import com.google.common.reflect.ClassPath;
import communicatorServer.controllers.Config.ApiPath;
import communicatorServer.controllers.Config.Controller;
import socketServerCommunication.requests.Request;
import socketServerCommunication.responses.Response;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerConfigurator {
	private static final Logger LOGGER = Logger.getLogger(ControllerConfigurator.class.getName());
	
	private Map<String, ControllerInstance> MAP_API_PATH_TO_CONTROLLER = new HashMap<>();
	
	public Map<String, ControllerInstance> createControllers() throws IllegalAccessException, IOException, InvocationTargetException, InstantiationException {
		ClassPath classpath = ClassPath.from(ClassLoader.getSystemClassLoader());
		
		for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClassesRecursive("communicatorServer")) {
			Class clazz = classInfo.load();
			if (clazz.isAnnotationPresent(Controller.class)) {
				parseControllerClass(clazz);
			}
		}
		
		return MAP_API_PATH_TO_CONTROLLER;
	}
	
	private void parseControllerClass(Class<?> controllerClazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
		Object instance = controllerClazz.getConstructors()[0].newInstance();
		
		for (Method method : controllerClazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(ApiPath.class)) {
				parseControllerMethod(controllerClazz, instance, method);
			}
		}
	}
	
	private void parseControllerMethod(Class<?> controllerClazz, Object instance, Method method) {
		ControllerInstance controllerInstanceReplaced;
		Parameter[] parameters = method.getParameters();
		
		if (!method.getReturnType().equals(Response.class)) {
			throw new IllegalArgumentException("In class " + controllerClazz.getName() + " method " + method.getName() +
					" has ApiPath annotation but doesn't return Response!");
		}
		
		if (parameters.length != 1 || parameters[0].getType() != Request.class) {
			throw new IllegalArgumentException("In class " + controllerClazz.getName() + " method " + method.getName() +
					" has ApiPath annotation but doesn't have 1 argument or doesn't have String parameter!");
		}
		
		String apiPath = method.getAnnotation(ApiPath.class).path();
		boolean needAuthenticate = method.getAnnotation(ApiPath.class).needAuthenticate();
		
		ControllerInstance controllerInstance = new ControllerInstance(needAuthenticate, instance, method, controllerClazz);
		
		controllerInstanceReplaced = MAP_API_PATH_TO_CONTROLLER.put(apiPath, controllerInstance);
		if (controllerInstanceReplaced != null) {
			throw new IllegalArgumentException("In class: " + controllerClazz.getName() + " method: " + method.getName() +
					" has the same api mapping as class: " + controllerInstanceReplaced.getClazz().getName() +
					" in method: " + controllerInstanceReplaced.getMethodToInvoke().getName());
		}
		
		LOGGER.log(Level.INFO, "Added apiPath: " + apiPath + " with class: " + controllerClazz.getName() + " ; NEED_AUTH: " + needAuthenticate);
	}
}
