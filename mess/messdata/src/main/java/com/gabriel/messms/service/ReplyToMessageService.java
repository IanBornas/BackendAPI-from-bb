package com.gabriel.messms.service;
import com.gabriel.messms.model.ReplyToMessage;
public interface ReplyToMessageService {
	ReplyToMessage[] getAll() throws Exception;
	ReplyToMessage get(Integer id) throws Exception;
	ReplyToMessage create(ReplyToMessage replyToMessage) throws Exception;
	ReplyToMessage update(ReplyToMessage replyToMessage) throws Exception;
	void delete(Integer id) throws Exception;
}
