package communicatorServer.models.conversation;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConversationService {
	public static List<Message> getMessages(ObjectId userId1, ObjectId userId2) {
		Conversation conversation = ConversationDAO.getByUsersId(userId1, userId2);
		
		if (conversation != null) {
			return conversation.getMessages();
		}
		
		return new ArrayList<>(0);
	}
	
	public static Message addMessages(ObjectId userIdWriter, ObjectId userIdReciver, String messageData) {
		Message message = new Message();
		message.setDate(new Date());
		message.setUserId(userIdWriter);
		message.setMessage(messageData);
		
		Conversation conversation = ConversationDAO.getByUsersId(userIdWriter, userIdReciver);
		
		if (conversation == null) {
			conversation = new Conversation();
			conversation.setUserId1(userIdWriter);
			conversation.setUserId2(userIdReciver);
		}
		
		conversation.getMessages().add(message);
		ConversationDAO.save(conversation);
		
		return message;
	}
}
