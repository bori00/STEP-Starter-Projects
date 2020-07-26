package com.google.sps.data;

public class Comment{
    //personal data
    private int senderId;
    //message 
    private final String message;
    //datastore netity name
    public static final String ENTITY_NAME = "Comment";
    private static final String MESSAGE_PROPERTY = "message";
    private static final String SENDER_ID_PROPERTY = "senderId";

    public Comment(private senderId, String message){
        this.senderId = senderId;
        this.message = message;
    }

    public void saveToDataStore(){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(getCommentEntity());
    }

    private Entity getUserEntity(int id){
        Entity commentEntity = new Entity(ENTITY_NAME);
        commentEntity.setProperty(MESSAGE_PROPERTY, message);
        commentEntity.setProperty(SENDER_ID_PROPERTY, senderId);
        return  commentEntity;
    }

    public int getSenderId(){
        return senderId;
    }

    public String getMessage() {
        return message;
    }
}