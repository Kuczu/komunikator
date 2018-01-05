package communicatorServer.config;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigManager {
	private static final Logger LOGGER = Logger.getLogger(ConfigManager.class.getName());
	
	private static final String MONGO_HOST = "localhost";
	private static final int MONGO_PORT = 27017;
	private static final String MONGO_DB_NAME = "communicator";
	
	private static Datastore datastore;
	
	private static Map<String, ControllerInstance> mapApiPathToController;
	private static boolean ARE_CONTROLLERS_CREATED = false;
	
	public static void initializeConfigContext() throws IllegalAccessException, IOException, InvocationTargetException, InstantiationException  {
		createControllers();
		createDBConnection();
		
		ConfigContext.init(mapApiPathToController, datastore);
	}
	
	private static void createControllers() throws IllegalAccessException, IOException, InvocationTargetException, InstantiationException {
		if (ARE_CONTROLLERS_CREATED) {
			throw new IllegalAccessException("controllers are already created!");
		}
		
		mapApiPathToController = new ControllerConfigurator().createControllers();
		
		ARE_CONTROLLERS_CREATED = true;
	}
	
	private static void createDBConnection() {
		MongoClient mongoClient = new MongoClient(MONGO_HOST , MONGO_PORT);
		//If mongo is in secure mode auth is required!
		
		Morphia morphia = new Morphia();
		morphia.mapPackage("communicatorServer.models");
		
		Datastore datastore = morphia.createDatastore(mongoClient, MONGO_DB_NAME);
		datastore.ensureIndexes();
		
		ConfigManager.datastore = datastore;
		LOGGER.log(Level.INFO, "Connected to " + MONGO_DB_NAME + " on port " + MONGO_PORT);
	}
}
