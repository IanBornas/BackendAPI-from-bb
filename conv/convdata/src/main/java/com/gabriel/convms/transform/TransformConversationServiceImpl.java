package com.gabriel.convms.transform;
import com.gabriel.convms.entity.ConversationData;
import com.gabriel.convms.model.Conversation;
import org.springframework.stereotype.Service;
@Service
public class TransformConversationServiceImpl implements TransformConversationService {
	@Override
	public ConversationData transform(Conversation conversation){
		ConversationData conversationData = new ConversationData();
		conversationData.setId(conversation.getId());
		conversationData.setConversationId(conversation.getConversationId());
		conversationData.setConversationName(conversation.getConversationName());
		conversationData.setConversationType(conversation.getConversationType());
		conversationData.setCreatedAt(conversation.getCreatedAt());
		conversationData.setCreatorId(conversation.getCreatorId());
		return conversationData;
	}
	@Override

	public Conversation transform(ConversationData conversationData){;
		Conversation conversation = new Conversation();
		conversation.setId(conversationData.getId());
		conversation.setConversationId(conversationData.getConversationId());
		conversation.setConversationName(conversationData.getConversationName());
		conversation.setConversationType(conversationData.getConversationType());
		conversation.setCreatedAt(conversationData.getCreatedAt());
		conversation.setCreatorId(conversationData.getCreatorId());
		conversation.setCreated(conversationData.getCreated());
		conversation.setLastUpdated(conversationData.getLastUpdated());
		return conversation;
	}
}
