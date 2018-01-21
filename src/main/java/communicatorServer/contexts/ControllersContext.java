package communicatorServer.contexts;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import communicatorServer.config.DateSerializer;
import communicatorServer.config.ObjectIdSerializer;
import org.bson.types.ObjectId;

import java.util.Date;

public class ControllersContext {
	public static final Gson GSON;
	
	static {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(new TypeToken<ObjectId>() {	}.getType(), new ObjectIdSerializer());
		gsonBuilder.registerTypeAdapter(new TypeToken<Date>() {	}.getType(), new DateSerializer());
		GSON = gsonBuilder.create();
	}
}
