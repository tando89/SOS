package com.example.tan089.sos;

import java.util.Date;

/**
 * Created by tan089 on 8/24/2017.
 */

public class SosMessage {
    private String message;
    private String author;
    private long messageTime;
    //Construct for strings
    public SosMessage(String message, String author) {
        this.message = message;
        this.author = author;
        messageTime = new Date().getTime();
    }

    public SosMessage() {
    }

    //Get the strings above
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
