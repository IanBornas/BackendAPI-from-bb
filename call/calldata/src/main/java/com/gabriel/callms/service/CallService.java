package com.gabriel.callms.service;
import com.gabriel.callms.model.Call;
public interface CallService {
	Call[] getAll() throws Exception;
	Call get(Integer id) throws Exception;
	Call create(Call call) throws Exception;
	Call update(Call call) throws Exception;
	void delete(Integer id) throws Exception;
}
