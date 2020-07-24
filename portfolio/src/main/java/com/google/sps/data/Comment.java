package com.google.sps.data;

public class Comment{
    //personal data
    private User sender;
    //message 
    private final String message;

    public Comment(User sender, String message){
        this.sender = sender;
        this.message = message;
    }

    public String getSender(){
        return sender;
    }

    public String getMessage() {
        return message;
    }
}