package com.gabriel.callms.serviceimpl;
import com.gabriel.callms.entity.CallSignalData;
import com.gabriel.callms.model.CallSignal;
import com.gabriel.callms.repository.CallSignalDataRepository;
import com.gabriel.callms.service.CallSignalService;
import com.gabriel.callms.transform.TransformCallSignalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class CallSignalServiceImpl implements CallSignalService {
	Logger logger = LoggerFactory.getLogger(CallSignalServiceImpl.class);
	@Autowired
	CallSignalDataRepository callSignalDataRepository;
	@Autowired
	TransformCallSignalService tansformCallSignalService;
	@Override
	public CallSignal[] getAll() {
		List<CallSignalData> callSignalsData = new ArrayList<>();
		List<CallSignal> callSignals = new ArrayList<>();
		callSignalDataRepository.findAll().forEach(callSignalsData::add);
		Iterator<CallSignalData> it = callSignalsData.iterator();
		while(it.hasNext()) {
			CallSignalData callSignalData = it.next();
			CallSignal callSignal = tansformCallSignalService.transform(callSignalData);
			callSignals.add(callSignal);
		}
		CallSignal[] array = new CallSignal[callSignals.size()];
		for  (int i=0; i<callSignals.size(); i++){
			array[i] = callSignals.get(i);
		}
		return array;
	}
	@Override
	public CallSignal create(CallSignal callSignal) {
		logger.info(" add:Input " + callSignal.toString());
		CallSignalData callSignalData = tansformCallSignalService.transform(callSignal);
		callSignalData = callSignalDataRepository.save(callSignalData);
		logger.info(" add:Input " + callSignalData.toString());
			CallSignal newCallSignal = tansformCallSignalService.transform(callSignalData);
		return newCallSignal;
	}

	@Override
	public CallSignal update(CallSignal callSignal) {
		CallSignal updatedCallSignal = null;
		int id = callSignal.getId();
		Optional<CallSignalData> optional  = callSignalDataRepository.findById(callSignal.getId());
		if(optional.isPresent()){
			CallSignalData originalCallSignalData = tansformCallSignalService.transform(callSignal);
			originalCallSignalData.setCreated(optional.get().getCreated());
			CallSignalData callSignalData = callSignalDataRepository.save(originalCallSignalData);
			updatedCallSignal = tansformCallSignalService.transform(callSignalData);
		}
		else {
			logger.error("CallSignal record with id: " + Integer.toString(id) + " do not exist ");

		}
		return updatedCallSignal;
	}

	@Override
	public CallSignal get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		CallSignal callSignal = null;
		Optional<CallSignalData> optional = callSignalDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			callSignal = tansformCallSignalService.transform(optional.get());
		}
		else {
			logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		}
		return callSignal;
	}
	@Override
	public void delete(Integer id) {
		CallSignal callSignal = null;
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<CallSignalData> optional = callSignalDataRepository.findById(id);
		if( optional.isPresent()) {
			CallSignalData callSignalDatum = optional.get();
			callSignalDataRepository.delete(optional.get());
			logger.info(" Successfully deleted CallSignal record with id: " + Integer.toString(id));
			callSignal = tansformCallSignalService.transform(optional.get());
		}
		else {
			logger.error(" Unable to locate callSignal with id:" +  Integer.toString(id));
		}
	}
}
