package com.gabriel.callms.service;
import com.gabriel.callms.model.Caller;
public interface CallerService {
	Caller[] getAll() throws Exception;
	Caller get(Integer id) throws Exception;
	Caller create(Caller caller) throws Exception;
	Caller update(Caller caller) throws Exception;
	void delete(Integer id) throws Exception;
}
