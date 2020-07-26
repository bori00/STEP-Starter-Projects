package com.google.sps.data;

import org.jetbrains.annotations.Nullable;

public class User{
    //personal data
    private final String firstName, lastName, email, phone;
    //job related data: optional
    @Nullable private final String jobTitle;
     //datastore entity name
    public static final String ENTITY_NAME = "User";
    public static final String ID_PROPERTY = "id";
    public static final String FISRTNAME_PROPERTY = "firstName";
    public static final String LASTNAME_PROPERTY = "lastName";
    public static final String PHONE_PROPERTY = "phone";
    public static final String JOB_TITLE_PROPERTY = "jobTitle";

    public User(String firstName, String lastName, String email, String phone, String jobTitle){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.jobTitle = jobTitle;
    }

    public void saveToDataStore(int id){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(getUserEntity(id));
    }

    private Entity getUserEntity(int id){
        Entity userEntity = new Entity(User.ENTITY_NAME);
        userEntity.setProperty(ID_PROPERTY, id);
        userEntity.setProperty(FIRST_NAME_PROPERTY, firstName);
        userEntity.setProperty(LAST_NAME_PROPERTY, lastName);
        userEntity.setProperty(EMAIL_PROPERTY, email);
        userEntity.setProperty(PHONE_PROPERTY, phone);
        userEntity.setProperty(JOB_TITLE_PROPERTY, jobTitle);
        return  userEntity;
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