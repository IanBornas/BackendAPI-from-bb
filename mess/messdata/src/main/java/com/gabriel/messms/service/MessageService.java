package com.gabriel.messms.service;
import com.gabriel.messms.model.Message;
public interface MessageService {
	Message[] getAll() throws Exception;
	Message get(Integer id) throws Exception;
	Message create(Message message) throws Exception;
	Message update(Message message) throws Exception;
	void delete(Integer id) throws Exception;
}
