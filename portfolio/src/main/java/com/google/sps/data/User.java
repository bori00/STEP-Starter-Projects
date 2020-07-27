package com.google.sps.data;

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
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class User{
    private final String id, firstName, lastName, email, phone;
    @Nullable private final String jobTitle; //job related data: optional
    public static final String ENTITY_NAME = "User";
    public static final String ID_PROPERTY = "userId";
    public static final String FIRST_NAME_PROPERTY = "firstName";
    public static final String LAST_NAME_PROPERTY = "lastName";
    public static final String PHONE_PROPERTY = "phone";
    public static final String EMAIL_PROPERTY = "email";
    public static final String JOB_TITLE_PROPERTY = "jobTitle";

    public User(String id, String firstName, String lastName, String email, String phone, String jobTitle){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.jobTitle = jobTitle;
    }

    public void saveToDatastore(){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(getUserEntity());
    }

    public Entity getUserEntity(){
        Entity userEntity = new Entity(User.ENTITY_NAME);
        userEntity.setProperty(ID_PROPERTY, id);
        userEntity.setProperty(FIRST_NAME_PROPERTY, firstName);
        userEntity.setProperty(LAST_NAME_PROPERTY, lastName);
        userEntity.setProperty(EMAIL_PROPERTY, email);
        userEntity.setProperty(PHONE_PROPERTY, phone);
        userEntity.setProperty(JOB_TITLE_PROPERTY, jobTitle);
        return  userEntity;
    }

    public static boolean isUserDataSaved(String id){
        Query commentsQuery = new Query(User.ENTITY_NAME).setFilter(new Query.FilterPredicate(User.ID_PROPERTY, Query.FilterOperator.EQUAL, id));
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        List<Entity> results = datastore.prepare(commentsQuery).asList(FetchOptions.Builder.withDefaults());
        if(results.size()>0) return true;
        else return false;
    }

    @Nullable
    public static User getUserFromDatastore(String id){
        return getUserFromUserEntity(getSavedUserEntity(id));
    }

    @Nullable
    public static Entity getSavedUserEntity(String id){
        Query commentsQuery = new Query(User.ENTITY_NAME).setFilter(new Query.FilterPredicate(User.ID_PROPERTY, Query.FilterOperator.EQUAL, id));
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        List<Entity> results = datastore.prepare(commentsQuery).asList(FetchOptions.Builder.withDefaults());
        if(results.size()>0) return results.get(0);
        else return null;
    }

    @Nullable
    private static User getUserFromUserEntity(Entity userEntity){
        if(userEntity==null) return null;
        String id = (String) userEntity.getProperty(ID_PROPERTY);
        String firstName = (String) userEntity.getProperty(FIRST_NAME_PROPERTY);
        String lastName = (String) userEntity.getProperty(LAST_NAME_PROPERTY);
        String email = (String) userEntity.getProperty(EMAIL_PROPERTY);
        String phone = (String) userEntity.getProperty(PHONE_PROPERTY);
        String jobTitle = (String) userEntity.getProperty(JOB_TITLE_PROPERTY);
        return  new User(id, firstName, lastName, email, phone, jobTitle);
    }

    public static User getUserFromRequest(HttpServletRequest request){
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String email = getUserEmailFromUserService();
        String phone = request.getParameter("phone");
        String jobTitle = null;
        if(request.getParameterValues("type")!=null){ //box is checked
            jobTitle = request.getParameter("job-title");
        }
        User newUser = new User(getUserIdFromUserService(), firstName, lastName, email, phone, jobTitle);
        return newUser;
    }

    public static String getUserEmailFromUserService(){
        UserService userService = UserServiceFactory.getUserService();
        return userService.getCurrentUser().getEmail();
    }

    public static String getUserIdFromUserService(){
        UserService userService = UserServiceFactory.getUserService();
        return userService.getCurrentUser().getUserId();
    }

    public String getId(){
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Nullable
    public String getJobTitle() {
        return jobTitle;
    }
}