package com.gabriel.callms.service;
import com.gabriel.callms.model.Receiver;
public interface ReceiverService {
	Receiver[] getAll() throws Exception;
	Receiver get(Integer id) throws Exception;
	Receiver create(Receiver receiver) throws Exception;
	Receiver update(Receiver receiver) throws Exception;
	void delete(Integer id) throws Exception;
}
