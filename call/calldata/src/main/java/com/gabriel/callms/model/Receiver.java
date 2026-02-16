package com.gabriel.callms.model;
import lombok.Data;
import java.util.Date;

@Data
public class Receiver{
	private int id;
	private String name;
	private Date lastUpdated;
	private Date created;
}
