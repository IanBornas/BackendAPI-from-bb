package com.gabriel.userms.model;
import lombok.Data;
import java.util.Date;

@Data
public class User{
	private int id;
	private int userId;
	private String username;
	private String displayName;
	private String email;
	private String avatarUrl;
	private boolean online;
	private Date lastSeen;
	private String statusMessage;
	private String deviceToken;
	private Date lastUpdated;
	private Date created;
}
