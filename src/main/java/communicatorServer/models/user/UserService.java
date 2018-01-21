package communicatorServer.models.user;

import com.google.common.base.Strings;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class UserService {
	private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

	private final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public static User getUserBy(ObjectId userId) {
		return UserDAO.getById(userId);
	}
	
	public static User getUserBy(String nick) {
		return UserDAO.getByNick(nick);
	}
	
	public static void addFriend(ObjectId userId1, ObjectId userId2) {
		UserDAO.addFriend(userId1, userId2);
	}
	
	public static User login(String nick, String password) {
		if (Strings.isNullOrEmpty(nick) || Strings.isNullOrEmpty(password)) {
			throw new IllegalArgumentException("Nick and password cannot be empty.");
		}
		
		User user = getUserBy(nick);
		
		if (user != null && passwordEncoder.matches(password, user.getPassword())) {
			return user;
		}
		
		throw new IllegalArgumentException("Incorrect password or username.");
	}
	
	public static User addNewUser(String nick, String password) {
		if (getUserBy(nick) != null) {
			throw new IllegalArgumentException("user with given nick already exists.");
		}
		
		if (Strings.isNullOrEmpty(nick) || Strings.isNullOrEmpty(password)) {
			throw new IllegalArgumentException("Nick and password cannot be empty.");
		}
		
		User user = new User();
		user.setNick(nick);
		user.setPassword(passwordEncoder.encode(password));
		user.setJoinDate(new Date());
		
		UserDAO.save(user);
		
		LOGGER.log(Level.INFO, "Added user: " + user.getNick());
		
		return user;
	}
	
	public static boolean usersAreFriends(User user, ObjectId userId) {
		return user
				.getFirendsIdList()
				.stream()
				.map(FriendEntity::getUserId)
				.collect(Collectors.toList())
				.contains(userId);
	}
	
	public static void addUserMessageActivity(ObjectId userId, ObjectId wroteUserId) {
		UserNewActivity userNewActivity = UserDAO.getByUserId(userId);
		
		if (userNewActivity == null) {
			userNewActivity = new UserNewActivity();
			userNewActivity.setUserId(userId);
		}
		
		userNewActivity
				.getUnreadMessagesUsersName()
				.add(getUserBy(wroteUserId).getNick());
		
		UserDAO.save(userNewActivity);
	}
	
	public static UserNewActivity getMessageActivity(ObjectId userId) {
		return UserDAO.getByUserId(userId);
	}
}
