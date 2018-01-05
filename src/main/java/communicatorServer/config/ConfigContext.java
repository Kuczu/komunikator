package communicatorServer.config;

import org.mongodb.morphia.Datastore;

import java.util.Map;

public class ConfigContext {
	private static Map<String, ControllerInstance> mapApiPathToController;
	private static Datastore datastore;
	
	static void init(Map<String, ControllerInstance> mapApiPathToController, Datastore datastore) {
		ConfigContext.mapApiPathToController = mapApiPathToController;
		ConfigContext.datastore = datastore;
	}
	
	public static ControllerInstance getControllerInstanceForApiPath(String apiPath) {
		return mapApiPathToController.get(apiPath);
	}
	
	public static Datastore getDatastore() {
		return datastore;
	}
}
