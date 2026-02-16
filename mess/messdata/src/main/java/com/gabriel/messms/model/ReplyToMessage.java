package com.gabriel.messms.model;
import lombok.Data;
import java.util.Date;

@Data
public class ReplyToMessage{
	private int id;
	private String name;
	private Date lastUpdated;
	private Date created;
}
