package com.google.sps.data;

import com.google.appengine.api.datastore.Entity;
import javax.servlet.http.HttpServletRequest;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.FetchOptions;

public class Comment{
    //personal data
    private String senderId;
    //message 
    private final String message;
    //datastore netity name
    public static final String ENTITY_NAME = "Comment";
    private static final String MESSAGE_PROPERTY = "message";
    private static final String SENDER_ID_PROPERTY = "senderId";

    public Comment(String senderId, String message){
        this.senderId = senderId;
        this.message = message;
    }

    public void saveToDatastore(){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(getCommentEntity());
    }

    private Entity getCommentEntity(){
        Entity commentEntity = new Entity(ENTITY_NAME);
        commentEntity.setProperty(MESSAGE_PROPERTY, message);
        commentEntity.setProperty(SENDER_ID_PROPERTY, senderId);
        return commentEntity;
    }

    public static Comment getCommentFromRequest(String senderId, HttpServletRequest request){
        String message = request.getParameter("comment");
        Comment newComment = new Comment(senderId, message);
        return newComment;
    }

    public String getSenderId(){
        return senderId;
    }

    public String getMessage() {
        return message;
    }
}