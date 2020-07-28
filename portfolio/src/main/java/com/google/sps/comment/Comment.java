package com.google.sps.comment;

import com.google.sps.user.User;
import com.google.appengine.api.datastore.Entity;
import javax.servlet.http.HttpServletRequest;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.FetchOptions;


/** Stores the data related to one comment */
public class Comment{
    private String senderId;
    private final String message;

    public Comment(String senderId, String message){
        this.senderId = senderId;
        this.message = message;
    }

    public String getSenderId(){
        return senderId;
    }

    public String getMessage() {
        return message;
    }
}