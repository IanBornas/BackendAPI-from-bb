package com.gabriel.convms.transform;
import com.gabriel.convms.entity.ConversationData;
import com.gabriel.convms.model.Conversation;
public interface TransformConversationService {
	ConversationData transform(Conversation conversation);
	Conversation transform(ConversationData conversationData);
}
