package com.google.sps.data;

import org.jetbrains.annotations.Nullable;

public class User{
    //datastore entity name
    public static final String ENTITY_NAME = "User";
    //personal data
    private final String firstName, lastName, email, phone;
    //job related data: optional
    @Nullable private final String jobTitle;

    public User(String firstName, String lastName, String email, String phone, String jobTitle){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.jobTitle = jobTitle;
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