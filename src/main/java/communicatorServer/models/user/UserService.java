package communicatorServer.models.user;

import com.google.common.base.Strings;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

public class UserService {
	private final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public static User getUserBy(ObjectId userId) {
		return UserDAO.getById(userId);
	}
	
	public static User getUserBy(String nick) {
		return UserDAO.getByNick(nick);
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
		
		return user;
	}
}
