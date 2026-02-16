package com.gabriel.messms.model;
import lombok.Data;
import java.util.Date;

@Data
public class Message{
	private int id;
	private int messageId;
	private int conversationId;
	private int senderId;
	private String content;
	private String messageType;
	private Date sentAt;
	private Date deliveredAt;
	private Date readAt;
	private String mediaUrl;
	private int replyToMessageId;
	private Date lastUpdated;
	private Date created;
	@Override
	public String toString(){
		return name;
	}
}
