package com.google.sps.data;

public class Comment{
    //datastore netity name
    public static final String ENTITY_NAME = "Comment";
    //personal data
    private User sender;
    //message 
    private final String message;

    public Comment(User sender, String message){
        this.sender = sender;
        this.message = message;
    }

    public User getSender(){
        return sender;
    }

    public String getMessage() {
        return message;
    }
}