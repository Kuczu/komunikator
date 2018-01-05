package communicatorServer.models.User;

import org.bson.types.ObjectId;

public class UserService {
	public static User getUserById(ObjectId userId) {
		return UserDAO.getById(userId);
	}
}
