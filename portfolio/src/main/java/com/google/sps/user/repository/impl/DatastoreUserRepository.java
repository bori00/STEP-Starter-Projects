package com.google.sps.user.repository.impl;

import com.google.sps.user.User;
import com.google.sps.user.repository.UserRepository;
import org.jetbrains.annotations.Nullable;
import com.google.appengine.api.datastore.Entity;
import javax.servlet.http.HttpServletRequest;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import java.util.List; 
import java.util.HashMap;

/** Handles the storage of comments using the Datastore API. */ 
public class DatastoreUserRepository implements UserRepository{
    public static final String ENTITY_NAME = "User";
    public static final String FIRST_NAME_PROPERTY = "firstName";
    public static final String LAST_NAME_PROPERTY = "lastName";
    public static final String PHONE_PROPERTY = "phone";
    public static final String EMAIL_PROPERTY = "email";
    public static final String JOB_TITLE_PROPERTY = "jobTitle";

    @Override
    public void saveUser(User user){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(getUserEntity(user));
    }

    private Entity getUserEntity(User user){
        Entity userEntity = new Entity(ENTITY_NAME, user.getId());
        userEntity.setProperty(FIRST_NAME_PROPERTY, user.getFirstName());
        userEntity.setProperty(LAST_NAME_PROPERTY, user.getLastName());
        userEntity.setProperty(EMAIL_PROPERTY, user.getEmail());
        userEntity.setProperty(PHONE_PROPERTY, user.getPhone());
        userEntity.setProperty(JOB_TITLE_PROPERTY, user.getJobTitle());
        return  userEntity;
    }

    @Override
    public boolean isUserDataSaved(String id){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key userKey = KeyFactory.createKey(ENTITY_NAME, id);
        try {
            getUserFromUserEntity(datastore.get(userKey));
            return true;
        }
        catch(EntityNotFoundException e){
            return false;
        }
    }

    @Override 
    @Nullable
    public User getUser(String id){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key userKey = KeyFactory.createKey(ENTITY_NAME, id);
        try {
            return getUserFromUserEntity(datastore.get(userKey));
        }
        catch(EntityNotFoundException e){
            return null;
        }
    }

    @Nullable
    private User getUserFromUserEntity(Entity userEntity){
        if(userEntity==null) return null;
        String id = (String) userEntity.getKey().getName();
        String firstName = (String) userEntity.getProperty(FIRST_NAME_PROPERTY);
        String lastName = (String) userEntity.getProperty(LAST_NAME_PROPERTY);
        String email = (String) userEntity.getProperty(EMAIL_PROPERTY);
        String phone = (String) userEntity.getProperty(PHONE_PROPERTY);
        String jobTitle = (String) userEntity.getProperty(JOB_TITLE_PROPERTY);
        return  new User(id, firstName, lastName, email, phone, jobTitle);
    }

    @Override
    public HashMap<String, User> getAllUsers(){
        Query usersQuery = new Query(ENTITY_NAME);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery userEntities = datastore.prepare(usersQuery);
        HashMap<String, User> result = new HashMap<>();
        for(Entity userEntity : userEntities.asIterable()){
            User newUser = getUserFromUserEntity(userEntity);
            result.put(newUser.getId(), newUser);
        }
        return result;
    }
}