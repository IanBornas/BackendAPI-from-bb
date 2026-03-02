package com.gabriel.messms.serviceimpl;
import com.gabriel.messms.entity.ConversationData;
import com.gabriel.messms.model.Conversation;
import com.gabriel.messms.repository.ConversationDataRepository;
import com.gabriel.messms.service.ConversationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class ConversationServiceImpl implements ConversationService {
	Logger logger = LoggerFactory.getLogger(ConversationServiceImpl.class);
	@Autowired
	ConversationDataRepository conversationDataRepository;
	@Autowired
	@Override
	public Conversation[] getAll() {
		List<ConversationData> conversationsData = new ArrayList<>();
		List<Conversation> conversations = new ArrayList<>();
		conversationDataRepository.findAll().forEach(conversationsData::add);
		Iterator<ConversationData> it = conversationsData.iterator();
		while(it.hasNext()) {
			ConversationData conversationData = it.next();
			Conversation conversation = new Conversation();
			conversation.setId(conversationData.getId());
			conversation.setName(conversationData.getName());
			conversations.add(conversation);
		}
		Conversation[] array = new Conversation[conversations.size()];
		for  (int i=0; i<conversations.size(); i++){
			array[i] = conversations.get(i);
		}
		return array;
	}
	@Override
	public Conversation create(Conversation conversation) {
		logger.info(" add:Input " + conversation.toString());
		ConversationData conversationData = new ConversationData();
		conversationData.setName(conversation.getName());
		conversationData = conversationDataRepository.save(conversationData);
		logger.info(" add:Input " + conversationData.toString());
			Conversation newConversation = new Conversation();
			newConversation.setId(conversationData.getId());
			newConversation.setName(conversationData.getName());
		return newConversation;
	}

	@Override
	public Conversation update(Conversation conversation) {
		Conversation updatedConversation = null;
		int id = conversation.getId();
		Optional<ConversationData> optional  = conversationDataRepository.findById(conversation.getId());
		if(optional.isPresent()){
			ConversationData originalConversationData = new ConversationData();
			originalConversationData.setId(conversation.getId());
			originalConversationData.setName(conversation.getName());
			originalConversationData.setCreated(optional.get().getCreated());
			ConversationData conversationData = conversationDataRepository.save(originalConversationData);
			updatedConversation = new Conversation();
			updatedConversation.setId(conversationData.getId());
			updatedConversation.setName(conversationData.getName());
			updatedConversation.setCreated(conversationData.getCreated());
			updatedConversation.setLastUpdated(conversationData.getLastUpdated());
		}
		else {
			logger.error("Conversation record with id: " + Integer.toString(id) + " do not exist ");

		}
		return updatedConversation;
	}

	@Override
	public Conversation get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		Conversation conversation = null;
		Optional<ConversationData> optional = conversationDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			conversation = new Conversation();
			conversation.setId(optional.get().getId());
			conversation.setName(optional.get().getName());
			conversation.setCreated(optional.get().getCreated());
			conversation.setLastUpdated(optional.get().getLastUpdated());
		}
		else {
			logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		}
		return conversation;
	}
	@Override
	public void delete(Integer id) {
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<ConversationData> optional = conversationDataRepository.findById(id);
		if( optional.isPresent()) {
			conversationDataRepository.delete(optional.get());
			logger.info(" Successfully deleted Conversation record with id: " + Integer.toString(id));
		}
		else {
			logger.error(" Unable to locate conversation with id:" +  Integer.toString(id));
		}
	}
}
