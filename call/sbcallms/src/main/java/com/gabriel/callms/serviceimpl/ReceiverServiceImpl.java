package com.gabriel.callms.serviceimpl;
import com.gabriel.callms.entity.ReceiverData;
import com.gabriel.callms.model.Receiver;
import com.gabriel.callms.repository.ReceiverDataRepository;
import com.gabriel.callms.service.ReceiverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class ReceiverServiceImpl implements ReceiverService {
	Logger logger = LoggerFactory.getLogger(ReceiverServiceImpl.class);
	@Autowired
	ReceiverDataRepository receiverDataRepository;
	@Autowired
	@Override
	public Receiver[] getAll() {
		List<ReceiverData> receiversData = new ArrayList<>();
		List<Receiver> receivers = new ArrayList<>();
		receiverDataRepository.findAll().forEach(receiversData::add);
		Iterator<ReceiverData> it = receiversData.iterator();
		while(it.hasNext()) {
			ReceiverData receiverData = it.next();
			Receiver receiver = new Receiver();
			receiver.setId(receiverData.getId());
			receiver.setName(receiverData.getName());
			receivers.add(receiver);
		}
		Receiver[] array = new Receiver[receivers.size()];
		for  (int i=0; i<receivers.size(); i++){
			array[i] = receivers.get(i);
		}
		return array;
	}
	@Override
	public Receiver create(Receiver receiver) {
		logger.info(" add:Input " + receiver.toString());
		ReceiverData receiverData = new ReceiverData();
		receiverData.setName(receiver.getName());
		receiverData = receiverDataRepository.save(receiverData);
		logger.info(" add:Input " + receiverData.toString());
			Receiver newReceiver = new Receiver();
			newReceiver.setId(receiverData.getId());
			newReceiver.setName(receiverData.getName());
		return newReceiver;
	}

	@Override
	public Receiver update(Receiver receiver) {
		Receiver updatedReceiver = null;
		int id = receiver.getId();
		Optional<ReceiverData> optional  = receiverDataRepository.findById(receiver.getId());
		if(optional.isPresent()){
			ReceiverData originalReceiverData = new ReceiverData();
			originalReceiverData.setId(receiver.getId());
			originalReceiverData.setName(receiver.getName());
			originalReceiverData.setCreated(optional.get().getCreated());
			ReceiverData receiverData = receiverDataRepository.save(originalReceiverData);
			updatedReceiver = new Receiver();
			updatedReceiver.setId(receiverData.getId());
			updatedReceiver.setName(receiverData.getName());
			updatedReceiver.setCreated(receiverData.getCreated());
			updatedReceiver.setLastUpdated(receiverData.getLastUpdated());
		}
		else {
			logger.error("Receiver record with id: " + Integer.toString(id) + " do not exist ");

		}
		return updatedReceiver;
	}

	@Override
	public Receiver get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		Receiver receiver = null;
		Optional<ReceiverData> optional = receiverDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			receiver = new Receiver();
			receiver.setId(optional.get().getId());
			receiver.setName(optional.get().getName());
			receiver.setCreated(optional.get().getCreated());
			receiver.setLastUpdated(optional.get().getLastUpdated());
		}
		else {
			logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		}
		return receiver;
	}
	@Override
	public void delete(Integer id) {
		Receiver receiver = null;
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<ReceiverData> optional = receiverDataRepository.findById(id);
		if( optional.isPresent()) {
			ReceiverData receiverDatum = optional.get();
			receiverDataRepository.delete(optional.get());
			logger.info(" Successfully deleted Receiver record with id: " + Integer.toString(id));
			receiver = new Receiver();
			receiver.setId(optional.get().getId());
			receiver.setName(optional.get().getName());
			receiver.setCreated(optional.get().getCreated());
			receiver.setLastUpdated(optional.get().getLastUpdated());
		}
		else {
			logger.error(" Unable to locate receiver with id:" +  Integer.toString(id));
		}
	}
}
