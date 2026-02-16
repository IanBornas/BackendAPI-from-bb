package com.gabriel.callms.serviceimpl;
import com.gabriel.callms.entity.CallerData;
import com.gabriel.callms.model.Caller;
import com.gabriel.callms.repository.CallerDataRepository;
import com.gabriel.callms.service.CallerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class CallerServiceImpl implements CallerService {
	Logger logger = LoggerFactory.getLogger(CallerServiceImpl.class);
	@Autowired
	CallerDataRepository callerDataRepository;
	@Autowired
	@Override
	public Caller[] getAll() {
		List<CallerData> callersData = new ArrayList<>();
		List<Caller> callers = new ArrayList<>();
		callerDataRepository.findAll().forEach(callersData::add);
		Iterator<CallerData> it = callersData.iterator();
		while(it.hasNext()) {
			CallerData callerData = it.next();
			Caller caller = new Caller();
			caller.setId(callerData.getId());
			caller.setName(callerData.getName());
			callers.add(caller);
		}
		Caller[] array = new Caller[callers.size()];
		for  (int i=0; i<callers.size(); i++){
			array[i] = callers.get(i);
		}
		return array;
	}
	@Override
	public Caller create(Caller caller) {
		logger.info(" add:Input " + caller.toString());
		CallerData callerData = new CallerData();
		callerData.setName(caller.getName());
		callerData = callerDataRepository.save(callerData);
		logger.info(" add:Input " + callerData.toString());
			Caller newCaller = new Caller();
			newCaller.setId(callerData.getId());
			newCaller.setName(callerData.getName());
		return newCaller;
	}

	@Override
	public Caller update(Caller caller) {
		Caller updatedCaller = null;
		int id = caller.getId();
		Optional<CallerData> optional  = callerDataRepository.findById(caller.getId());
		if(optional.isPresent()){
			CallerData originalCallerData = new CallerData();
			originalCallerData.setId(caller.getId());
			originalCallerData.setName(caller.getName());
			originalCallerData.setCreated(optional.get().getCreated());
			CallerData callerData = callerDataRepository.save(originalCallerData);
			updatedCaller = new Caller();
			updatedCaller.setId(callerData.getId());
			updatedCaller.setName(callerData.getName());
			updatedCaller.setCreated(callerData.getCreated());
			updatedCaller.setLastUpdated(callerData.getLastUpdated());
		}
		else {
			logger.error("Caller record with id: " + Integer.toString(id) + " do not exist ");

		}
		return updatedCaller;
	}

	@Override
	public Caller get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		Caller caller = null;
		Optional<CallerData> optional = callerDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			caller = new Caller();
			caller.setId(optional.get().getId());
			caller.setName(optional.get().getName());
			caller.setCreated(optional.get().getCreated());
			caller.setLastUpdated(optional.get().getLastUpdated());
		}
		else {
			logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		}
		return caller;
	}
	@Override
	public void delete(Integer id) {
		Caller caller = null;
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<CallerData> optional = callerDataRepository.findById(id);
		if( optional.isPresent()) {
			CallerData callerDatum = optional.get();
			callerDataRepository.delete(optional.get());
			logger.info(" Successfully deleted Caller record with id: " + Integer.toString(id));
			caller = new Caller();
			caller.setId(optional.get().getId());
			caller.setName(optional.get().getName());
			caller.setCreated(optional.get().getCreated());
			caller.setLastUpdated(optional.get().getLastUpdated());
		}
		else {
			logger.error(" Unable to locate caller with id:" +  Integer.toString(id));
		}
	}
}
