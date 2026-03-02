package com.gabriel.messms.serviceimpl;
import com.gabriel.messms.entity.MessageData;
import com.gabriel.messms.model.Message;
import com.gabriel.messms.repository.MessageDataRepository;
import com.gabriel.messms.service.MessageService;
import com.gabriel.messms.transform.TransformMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class MessageServiceImpl implements MessageService {
	Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	@Autowired
	MessageDataRepository messageDataRepository;
	@Autowired
	TransformMessageService tansformMessageService;
	@Override
	public Message[] getAll() {
		List<MessageData> messagesData = new ArrayList<>();
		List<Message> messages = new ArrayList<>();
		messageDataRepository.findAll().forEach(messagesData::add);
		Iterator<MessageData> it = messagesData.iterator();
		while(it.hasNext()) {
			MessageData messageData = it.next();
			Message message = tansformMessageService.transform(messageData);
			messages.add(message);
		}
		Message[] array = new Message[messages.size()];
		for  (int i=0; i<messages.size(); i++){
			array[i] = messages.get(i);
		}
		return array;
	}
	@Override
	public Message create(Message message) {
		logger.info(" add:Input " + message.toString());
		MessageData messageData = tansformMessageService.transform(message);
		messageData = messageDataRepository.save(messageData);
		logger.info(" add:Input " + messageData.toString());
			Message newMessage = tansformMessageService.transform(messageData);
		return newMessage;
	}

	@Override
	public Message update(Message message) {
		Message updatedMessage = null;
		int id = message.getId();
		Optional<MessageData> optional  = messageDataRepository.findById(message.getId());
		if(optional.isPresent()){
			MessageData originalMessageData = tansformMessageService.transform(message);
			originalMessageData.setCreated(optional.get().getCreated());
			MessageData messageData = messageDataRepository.save(originalMessageData);
			updatedMessage = tansformMessageService.transform(messageData);
		}
		else {
			logger.error("Message record with id: " + Integer.toString(id) + " do not exist ");

		}
		return updatedMessage;
	}

	@Override
	public Message get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		Message message = null;
		Optional<MessageData> optional = messageDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			message = tansformMessageService.transform(optional.get());
		}
		else {
			logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		}
		return message;
	}
	@Override
	public void delete(Integer id) {
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<MessageData> optional = messageDataRepository.findById(id);
		if( optional.isPresent()) {
			messageDataRepository.delete(optional.get());
			logger.info(" Successfully deleted Message record with id: " + Integer.toString(id));
		}
		else {
			logger.error(" Unable to locate message with id:" +  Integer.toString(id));
		}
	}
}
