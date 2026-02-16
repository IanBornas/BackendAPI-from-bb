package com.gabriel.callms.service;
import com.gabriel.callms.model.CallSignal;
public interface CallSignalService {
	CallSignal[] getAll() throws Exception;
	CallSignal get(Integer id) throws Exception;
	CallSignal create(CallSignal callSignal) throws Exception;
	CallSignal update(CallSignal callSignal) throws Exception;
	void delete(Integer id) throws Exception;
}
