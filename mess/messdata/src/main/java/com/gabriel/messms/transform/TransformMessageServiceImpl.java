package com.gabriel.messms.transform;
import com.gabriel.messms.entity.MessageData;
import com.gabriel.messms.model.Message;
import org.springframework.stereotype.Service;
@Service
public class TransformMessageServiceImpl implements TransformMessageService {
	@Override
	public MessageData transform(Message message){
		MessageData messageData = new MessageData();
		messageData.setId(message.getId());
		messageData.setMessageId(message.getMessageId());
		messageData.setConversationId(message.getConversationId());
		messageData.setSenderId(message.getSenderId());
		messageData.setContent(message.getContent());
		messageData.setMessageType(message.getMessageType());
		messageData.setSentAt(message.getSentAt());
		messageData.setDeliveredAt(message.getDeliveredAt());
		messageData.setReadAt(message.getReadAt());
		messageData.setMediaUrl(message.getMediaUrl());
		messageData.setReplyToMessageId(message.getReplyToMessageId());
		return messageData;
	}
	@Override

	public Message transform(MessageData messageData){;
		Message message = new Message();
		message.setId(messageData.getId());
		message.setMessageId(messageData.getMessageId());
		message.setConversationId(messageData.getConversationId());
		message.setSenderId(messageData.getSenderId());
		message.setContent(messageData.getContent());
		message.setMessageType(messageData.getMessageType());
		message.setSentAt(messageData.getSentAt());
		message.setDeliveredAt(messageData.getDeliveredAt());
		message.setReadAt(messageData.getReadAt());
		message.setMediaUrl(messageData.getMediaUrl());
		message.setReplyToMessageId(messageData.getReplyToMessageId());
		message.setCreated(messageData.getCreated());
		message.setLastUpdated(messageData.getLastUpdated());
		return message;
	}
}
