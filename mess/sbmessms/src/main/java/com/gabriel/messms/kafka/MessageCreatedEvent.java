package com.gabriel.messms.kafka;

public class MessageCreatedEvent {

    private int messageId;
    private int conversationId;
    private int senderId;
    private String content;
    private String messageType;
    private String sentAt;

    public MessageCreatedEvent() {}

    public MessageCreatedEvent(int messageId, int conversationId, int senderId,
                               String content, String messageType, String sentAt) {
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.content = content;
        this.messageType = messageType;
        this.sentAt = sentAt;
    }

    public int getMessageId() { return messageId; }
    public void setMessageId(int messageId) { this.messageId = messageId; }

    public int getConversationId() { return conversationId; }
    public void setConversationId(int conversationId) { this.conversationId = conversationId; }

    public int getSenderId() { return senderId; }
    public void setSenderId(int senderId) { this.senderId = senderId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getMessageType() { return messageType; }
    public void setMessageType(String messageType) { this.messageType = messageType; }

    public String getSentAt() { return sentAt; }
    public void setSentAt(String sentAt) { this.sentAt = sentAt; }

    @Override
    public String toString() {
        return "MessageCreatedEvent{messageId=" + messageId
                + ", conversationId=" + conversationId
                + ", senderId=" + senderId
                + ", content='" + content + "'"
                + ", messageType='" + messageType + "'"
                + ", sentAt='" + sentAt + "'}";
    }
}
