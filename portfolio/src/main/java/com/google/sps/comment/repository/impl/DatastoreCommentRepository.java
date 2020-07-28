package com.google.sps.comment.repository.impl;

import com.google.sps.comment.Comment;
import com.google.sps.comment.repository.CommentRepository;
import org.jetbrains.annotations.Nullable;
import com.google.appengine.api.datastore.Entity;
import javax.servlet.http.HttpServletRequest;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.FetchOptions;
import java.util.List; 
import java.util.ArrayList;

/** Handles the storage of comments using the Datastore API. */ 
public class DatastoreCommentRepository implements CommentRepository{
    public static final String ENTITY_NAME = "Comment";
    private static final String MESSAGE_PROPERTY = "message";
    private static final String SENDER_ID_PROPERTY = "senderId";

    @Override
    public void saveComment(Comment comment){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(getCommentEntity(comment));
    }

    private Entity getCommentEntity(Comment comment){
        Entity commentEntity = new Entity(ENTITY_NAME);
        commentEntity.setProperty(MESSAGE_PROPERTY, comment.getMessage());
        commentEntity.setProperty(SENDER_ID_PROPERTY, comment.getSenderId());
        return commentEntity;
    }

    @Override
    public List<Comment> getAllComments(){
        Query commentsQuery = new Query(ENTITY_NAME);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery commentEntities = datastore.prepare(commentsQuery);
        List<Comment> result = new ArrayList<Comment>();
        for(Entity commentEntity : commentEntities.asIterable()){
            result.add(getCommentFromCommentEntity(commentEntity));
        }
        return result;
    }

    private Comment getCommentFromCommentEntity(Entity commentEntity){
        return new Comment((String) commentEntity.getProperty(SENDER_ID_PROPERTY), (String) commentEntity.getProperty(MESSAGE_PROPERTY));
    }

    @Override
    public List<Comment> getGivenNumberOfComments(int maxNoComments){
        Query commentsQuery = new Query(ENTITY_NAME);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery commentEntities = datastore.prepare(commentsQuery);
        List<Comment> result = new ArrayList<Comment>();
        for(Entity commentEntity : commentEntities.asList(FetchOptions.Builder.withLimit(maxNoComments))){
            result.add(getCommentFromCommentEntity(commentEntity));
        }
        return result;
    }

    @Override
    public void deleteAllComments(){
        Query commentsQuery = new Query(ENTITY_NAME);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery comments = datastore.prepare(commentsQuery);
        for (Entity entity : comments.asIterable()) {
           datastore.delete(entity.getKey());
        }
    }
}