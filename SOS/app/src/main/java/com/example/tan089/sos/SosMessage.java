package com.example.tan089.sos;

/**
 * Created by tan089 on 8/24/2017.
 */

public class SosMessage {
    private String message;
    private String author;
    //Construct for strings
    public SosMessage(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public SosMessage() {
    }

    //Get the strings above
    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}
