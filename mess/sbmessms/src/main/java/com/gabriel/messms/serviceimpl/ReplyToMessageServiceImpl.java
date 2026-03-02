package com.gabriel.messms.serviceimpl;
import com.gabriel.messms.entity.ReplyToMessageData;
import com.gabriel.messms.model.ReplyToMessage;
import com.gabriel.messms.repository.ReplyToMessageDataRepository;
import com.gabriel.messms.service.ReplyToMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class ReplyToMessageServiceImpl implements ReplyToMessageService {
	Logger logger = LoggerFactory.getLogger(ReplyToMessageServiceImpl.class);
	@Autowired
	ReplyToMessageDataRepository replyToMessageDataRepository;
	@Autowired
	@Override
	public ReplyToMessage[] getAll() {
		List<ReplyToMessageData> replyToMessagesData = new ArrayList<>();
		List<ReplyToMessage> replyToMessages = new ArrayList<>();
		replyToMessageDataRepository.findAll().forEach(replyToMessagesData::add);
		Iterator<ReplyToMessageData> it = replyToMessagesData.iterator();
		while(it.hasNext()) {
			ReplyToMessageData replyToMessageData = it.next();
			ReplyToMessage replyToMessage = new ReplyToMessage();
			replyToMessage.setId(replyToMessageData.getId());
			replyToMessage.setName(replyToMessageData.getName());
			replyToMessages.add(replyToMessage);
		}
		ReplyToMessage[] array = new ReplyToMessage[replyToMessages.size()];
		for  (int i=0; i<replyToMessages.size(); i++){
			array[i] = replyToMessages.get(i);
		}
		return array;
	}
	@Override
	public ReplyToMessage create(ReplyToMessage replyToMessage) {
		logger.info(" add:Input " + replyToMessage.toString());
		ReplyToMessageData replyToMessageData = new ReplyToMessageData();
		replyToMessageData.setName(replyToMessage.getName());
		replyToMessageData = replyToMessageDataRepository.save(replyToMessageData);
		logger.info(" add:Input " + replyToMessageData.toString());
			ReplyToMessage newReplyToMessage = new ReplyToMessage();
			newReplyToMessage.setId(replyToMessageData.getId());
			newReplyToMessage.setName(replyToMessageData.getName());
		return newReplyToMessage;
	}

	@Override
	public ReplyToMessage update(ReplyToMessage replyToMessage) {
		ReplyToMessage updatedReplyToMessage = null;
		int id = replyToMessage.getId();
		Optional<ReplyToMessageData> optional  = replyToMessageDataRepository.findById(replyToMessage.getId());
		if(optional.isPresent()){
			ReplyToMessageData originalReplyToMessageData = new ReplyToMessageData();
			originalReplyToMessageData.setId(replyToMessage.getId());
			originalReplyToMessageData.setName(replyToMessage.getName());
			originalReplyToMessageData.setCreated(optional.get().getCreated());
			ReplyToMessageData replyToMessageData = replyToMessageDataRepository.save(originalReplyToMessageData);
			updatedReplyToMessage = new ReplyToMessage();
			updatedReplyToMessage.setId(replyToMessageData.getId());
			updatedReplyToMessage.setName(replyToMessageData.getName());
			updatedReplyToMessage.setCreated(replyToMessageData.getCreated());
			updatedReplyToMessage.setLastUpdated(replyToMessageData.getLastUpdated());
		}
		else {
			logger.error("ReplyToMessage record with id: " + Integer.toString(id) + " do not exist ");

		}
		return updatedReplyToMessage;
	}

	@Override
	public ReplyToMessage get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		ReplyToMessage replyToMessage = null;
		Optional<ReplyToMessageData> optional = replyToMessageDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			replyToMessage = new ReplyToMessage();
			replyToMessage.setId(optional.get().getId());
			replyToMessage.setName(optional.get().getName());
			replyToMessage.setCreated(optional.get().getCreated());
			replyToMessage.setLastUpdated(optional.get().getLastUpdated());
		}
		else {
			logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		}
		return replyToMessage;
	}
	@Override
	public void delete(Integer id) {
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<ReplyToMessageData> optional = replyToMessageDataRepository.findById(id);
		if( optional.isPresent()) {
			replyToMessageDataRepository.delete(optional.get());
			logger.info(" Successfully deleted ReplyToMessage record with id: " + Integer.toString(id));
		}
		else {
			logger.error(" Unable to locate replyToMessage with id:" +  Integer.toString(id));
		}
	}
}
