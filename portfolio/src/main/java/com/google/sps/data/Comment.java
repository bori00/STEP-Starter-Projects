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
    private User sender;
    //message 
    private final String message;
    //datastore related info
    public static final String ENTITY_NAME = "Comment";
    private static final String MESSAGE_PROPERTY = "message";
    private static final String SENDER_PROPERTY = "sender";

    public Comment(User sender, String message){
        this.sender = sender;
        this.message = message;
    }

    public void saveToDatastore(){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(getCommentEntity());
    }

    private Entity getCommentEntity(){
        Entity commentEntity = new Entity(Comment.ENTITY_NAME);
        commentEntity.setProperty(User.ID_PROPERTY, sender.getId());
        commentEntity.setProperty(User.FIRST_NAME_PROPERTY, sender.getFirstName());
        commentEntity.setProperty(User.LAST_NAME_PROPERTY, sender.getLastName());
        commentEntity.setProperty(User.EMAIL_PROPERTY, sender.getEmail());
        commentEntity.setProperty(User.PHONE_PROPERTY, sender.getPhone());
        commentEntity.setProperty(User.JOB_TITLE_PROPERTY, sender.getJobTitle());
        commentEntity.setProperty(MESSAGE_PROPERTY, message);
        return commentEntity;
    }

    public static Comment getCommentFromRequest(User sender, HttpServletRequest request){
        String message = request.getParameter("comment");
        Comment newComment = new Comment(sender, message);
        return newComment;
    }

    public User getSender(){
        return sender;
    }

    public String getMessage() {
        return message;
    }
}