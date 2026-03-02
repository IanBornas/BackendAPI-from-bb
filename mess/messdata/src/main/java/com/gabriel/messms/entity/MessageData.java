package com.gabriel.messms.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "Message_data")
public class MessageData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date lastUpdated;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date created;
}
