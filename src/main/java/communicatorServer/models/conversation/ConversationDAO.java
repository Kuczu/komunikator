package communicatorServer.models.conversation;

import com.google.common.collect.Lists;
import communicatorServer.config.ConfigContext;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class ConversationDAO {
	private static final Datastore DATASTORE = ConfigContext.getDatastore();
	
	public static void save(Conversation conversation) {
		DATASTORE.save(conversation);
	}
	
	public static Boolean conversationExists(ObjectId userId, ObjectId userId2) {
		Query<Conversation> query = DATASTORE.createQuery(Conversation.class);
		
		List<ObjectId> usersId = Lists.newArrayList(userId, userId2);
		
		query.and(
				query.criteria(Conversation.USER_ID_1).hasAnyOf(usersId),
				query.criteria(Conversation.USER_ID_2).hasAnyOf(usersId)
		);
		
		return query.count() == 1;
	}
	
	public static Conversation getByUsersId(ObjectId userId, ObjectId userId2) {
		Query<Conversation> query = DATASTORE.createQuery(Conversation.class);
		
		List<ObjectId> usersId = Lists.newArrayList(userId, userId2);
		
		query.and(
				query.criteria(Conversation.USER_ID_1).hasAnyOf(usersId),
				query.criteria(Conversation.USER_ID_2).hasAnyOf(usersId)
		);
		
		return query.get();
	}
}
