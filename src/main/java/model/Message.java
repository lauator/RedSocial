package model;

public class Message {
    private Integer messageId;
    private String message;
    private String date;

    private String userId;

    public Message() {
    }

    public Message(Integer messageId, String message, String date, String userId) {
        this.messageId = messageId;
        this.message = message;
        this.date = date;
        this.userId = userId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", message='" + message + '\'' +
                ", date='" + date + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
