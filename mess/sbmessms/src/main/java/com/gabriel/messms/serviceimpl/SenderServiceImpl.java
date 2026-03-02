package com.gabriel.messms.serviceimpl;
import com.gabriel.messms.entity.SenderData;
import com.gabriel.messms.model.Sender;
import com.gabriel.messms.repository.SenderDataRepository;
import com.gabriel.messms.service.SenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class SenderServiceImpl implements SenderService {
	Logger logger = LoggerFactory.getLogger(SenderServiceImpl.class);
	@Autowired
	SenderDataRepository senderDataRepository;
	@Autowired
	@Override
	public Sender[] getAll() {
		List<SenderData> sendersData = new ArrayList<>();
		List<Sender> senders = new ArrayList<>();
		senderDataRepository.findAll().forEach(sendersData::add);
		Iterator<SenderData> it = sendersData.iterator();
		while(it.hasNext()) {
			SenderData senderData = it.next();
			Sender sender = new Sender();
			sender.setId(senderData.getId());
			sender.setName(senderData.getName());
			senders.add(sender);
		}
		Sender[] array = new Sender[senders.size()];
		for  (int i=0; i<senders.size(); i++){
			array[i] = senders.get(i);
		}
		return array;
	}
	@Override
	public Sender create(Sender sender) {
		logger.info(" add:Input " + sender.toString());
		SenderData senderData = new SenderData();
		senderData.setName(sender.getName());
		senderData = senderDataRepository.save(senderData);
		logger.info(" add:Input " + senderData.toString());
			Sender newSender = new Sender();
			newSender.setId(senderData.getId());
			newSender.setName(senderData.getName());
		return newSender;
	}

	@Override
	public Sender update(Sender sender) {
		Sender updatedSender = null;
		int id = sender.getId();
		Optional<SenderData> optional  = senderDataRepository.findById(sender.getId());
		if(optional.isPresent()){
			SenderData originalSenderData = new SenderData();
			originalSenderData.setId(sender.getId());
			originalSenderData.setName(sender.getName());
			originalSenderData.setCreated(optional.get().getCreated());
			SenderData senderData = senderDataRepository.save(originalSenderData);
			updatedSender = new Sender();
			updatedSender.setId(senderData.getId());
			updatedSender.setName(senderData.getName());
			updatedSender.setCreated(senderData.getCreated());
			updatedSender.setLastUpdated(senderData.getLastUpdated());
		}
		else {
			logger.error("Sender record with id: " + Integer.toString(id) + " do not exist ");

		}
		return updatedSender;
	}

	@Override
	public Sender get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		Sender sender = null;
		Optional<SenderData> optional = senderDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			sender = new Sender();
			sender.setId(optional.get().getId());
			sender.setName(optional.get().getName());
			sender.setCreated(optional.get().getCreated());
			sender.setLastUpdated(optional.get().getLastUpdated());
		}
		else {
			logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		}
		return sender;
	}
	@Override
	public void delete(Integer id) {
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<SenderData> optional = senderDataRepository.findById(id);
		if( optional.isPresent()) {
			senderDataRepository.delete(optional.get());
			logger.info(" Successfully deleted Sender record with id: " + Integer.toString(id));
		}
		else {
			logger.error(" Unable to locate sender with id:" +  Integer.toString(id));
		}
	}
}
