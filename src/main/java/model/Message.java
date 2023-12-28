package model;

public class Message {
    private Integer messageId;
    private String username;
    private String message;
    private String date;

    public Message() {
    }

    public Message(Integer messageId, String username, String message, String date) {
        this.messageId = messageId;
        this.username = username;
        this.message = message;
        this.date = date;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
