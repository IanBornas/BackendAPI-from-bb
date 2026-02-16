package com.gabriel.callms.model;
import lombok.Data;
import java.util.Date;

@Data
public class CallSignal{
	private int id;
	private int callId;
	private int callerId;
	private int receiverId;
	private String callType;
	private String signalStatus;
	private Date timestamp;
	private Date lastUpdated;
	private Date created;
	@Override
	public String toString(){
		return name;
	}
}
