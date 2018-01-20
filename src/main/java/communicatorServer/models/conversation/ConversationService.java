package communicatorServer.models.conversation;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public class ConversationService {
	public static List<Message> getMessages(ObjectId userId1, ObjectId userId2) {
		return ConversationDAO.getByUsersId(userId1, userId2);
	}
	
	public static Message addMessages(ObjectId userIdWriter, ObjectId userIdReciver, String messageData) {
		Message message = new Message();
		message.setDate(new Date());
		message.setUserId(userIdWriter);
		message.setMessage(messageData);
		
		if (!ConversationDAO.conversationExists(userIdWriter, userIdReciver)) {
			Conversation conversation = new Conversation();
			conversation.setUserId1(userIdWriter);
			conversation.setUserId1(userIdReciver);
			ConversationDAO.save(conversation);
		}
		
		ConversationDAO.addMessage(message, userIdReciver);
		
		return message;
	}
}
