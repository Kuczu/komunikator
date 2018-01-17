package communicatorServer.models.User;

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
	
	public static User addNewUser(String nick, String password) {
		if (getUserBy(nick) != null) {
			throw new IllegalArgumentException("User with given nick already exists.");
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
