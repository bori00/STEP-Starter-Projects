package com.google.sps.user;

import org.jetbrains.annotations.Nullable;

/** Stores the data related to one user */
public class User{
    private final String id, firstName, lastName, email, phone;
    @Nullable private final String jobTitle; //job related data: optional

    public User(String id, String firstName, String lastName, String email, String phone, @Nullable String jobTitle){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.jobTitle = jobTitle;
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