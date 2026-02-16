package com.gabriel.messms.service;
import com.gabriel.messms.model.Sender;
public interface SenderService {
	Sender[] getAll() throws Exception;
	Sender get(Integer id) throws Exception;
	Sender create(Sender sender) throws Exception;
	Sender update(Sender sender) throws Exception;
	void delete(Integer id) throws Exception;
}
