package com.gabriel.convms.model;
import lombok.Data;
import java.util.Date;

@Data
public class Conversation{
	private int id;
	private int conversationId;
	private String conversationName;
	private String conversationType;
	private Date createdAt;
	private int creatorId;
	private Date lastUpdated;
	private Date created;
	@Override
	public String toString(){
		return name;
	}
}
