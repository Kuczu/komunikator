package communicatorServer.models.User;

import communicatorServer.config.ConfigContext;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

public class UserDAO {
	private static final Datastore DATASTORE = ConfigContext.getDatastore();
	
	public static User getById(ObjectId id) {
		return DATASTORE.createQuery(User.class)
				.field(User.ID)
				.equal(id)
				.get();
	}
	
	public static void save(User user) {
		DATASTORE.save(user);
	}
}
