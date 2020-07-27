package com.google.sps.data;

import com.google.sps.user.User;
import com.google.appengine.api.datastore.Entity;
import javax.servlet.http.HttpServletRequest;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.FetchOptions;

public class Comment{
    private User sender;
    private final String message;
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
        commentEntity.setProperty("id", sender.getId());
        commentEntity.setProperty("firstName", sender.getFirstName());
        commentEntity.setProperty("lastName", sender.getLastName());
        commentEntity.setProperty("email", sender.getEmail());
        commentEntity.setProperty("phone", sender.getPhone());
        commentEntity.setProperty("jobTitle", sender.getJobTitle());
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