package com.gabriel.messms.transform;
import com.gabriel.messms.entity.MessageData;
import com.gabriel.messms.model.Message;
public interface TransformMessageService {
	MessageData transform(Message message);
	Message transform(MessageData messageData);
}
