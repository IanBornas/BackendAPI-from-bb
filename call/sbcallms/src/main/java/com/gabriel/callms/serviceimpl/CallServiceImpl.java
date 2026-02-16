package com.gabriel.callms.serviceimpl;
import com.gabriel.callms.entity.CallData;
import com.gabriel.callms.model.Call;
import com.gabriel.callms.repository.CallDataRepository;
import com.gabriel.callms.service.CallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class CallServiceImpl implements CallService {
	Logger logger = LoggerFactory.getLogger(CallServiceImpl.class);
	@Autowired
	CallDataRepository callDataRepository;
	@Autowired
	@Override
	public Call[] getAll() {
		List<CallData> callsData = new ArrayList<>();
		List<Call> calls = new ArrayList<>();
		callDataRepository.findAll().forEach(callsData::add);
		Iterator<CallData> it = callsData.iterator();
		while(it.hasNext()) {
			CallData callData = it.next();
			Call call = new Call();
			call.setId(callData.getId());
			call.setName(callData.getName());
			calls.add(call);
		}
		Call[] array = new Call[calls.size()];
		for  (int i=0; i<calls.size(); i++){
			array[i] = calls.get(i);
		}
		return array;
	}
	@Override
	public Call create(Call call) {
		logger.info(" add:Input " + call.toString());
		CallData callData = new CallData();
		callData.setName(call.getName());
		callData = callDataRepository.save(callData);
		logger.info(" add:Input " + callData.toString());
			Call newCall = new Call();
			newCall.setId(callData.getId());
			newCall.setName(callData.getName());
		return newCall;
	}

	@Override
	public Call update(Call call) {
		Call updatedCall = null;
		int id = call.getId();
		Optional<CallData> optional  = callDataRepository.findById(call.getId());
		if(optional.isPresent()){
			CallData originalCallData = new CallData();
			originalCallData.setId(call.getId());
			originalCallData.setName(call.getName());
			originalCallData.setCreated(optional.get().getCreated());
			CallData callData = callDataRepository.save(originalCallData);
			updatedCall = new Call();
			updatedCall.setId(callData.getId());
			updatedCall.setName(callData.getName());
			updatedCall.setCreated(callData.getCreated());
			updatedCall.setLastUpdated(callData.getLastUpdated());
		}
		else {
			logger.error("Call record with id: " + Integer.toString(id) + " do not exist ");

		}
		return updatedCall;
	}

	@Override
	public Call get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		Call call = null;
		Optional<CallData> optional = callDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			call = new Call();
			call.setId(optional.get().getId());
			call.setName(optional.get().getName());
			call.setCreated(optional.get().getCreated());
			call.setLastUpdated(optional.get().getLastUpdated());
		}
		else {
			logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		}
		return call;
	}
	@Override
	public void delete(Integer id) {
		Call call = null;
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<CallData> optional = callDataRepository.findById(id);
		if( optional.isPresent()) {
			CallData callDatum = optional.get();
			callDataRepository.delete(optional.get());
			logger.info(" Successfully deleted Call record with id: " + Integer.toString(id));
			call = new Call();
			call.setId(optional.get().getId());
			call.setName(optional.get().getName());
			call.setCreated(optional.get().getCreated());
			call.setLastUpdated(optional.get().getLastUpdated());
		}
		else {
			logger.error(" Unable to locate call with id:" +  Integer.toString(id));
		}
	}
}
