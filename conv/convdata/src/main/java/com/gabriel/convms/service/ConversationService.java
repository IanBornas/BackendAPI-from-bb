package com.gabriel.convms.service;
import com.gabriel.convms.model.Conversation;
public interface ConversationService {
	Conversation[] getAll() throws Exception;
	Conversation get(Integer id) throws Exception;
	Conversation create(Conversation conversation) throws Exception;
	Conversation update(Conversation conversation) throws Exception;
	void delete(Integer id) throws Exception;
}
