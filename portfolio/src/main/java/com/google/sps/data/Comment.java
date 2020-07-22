package com.google.sps.data;

import org.jetbrains.annotations.Nullable;

public class Comment{
    //personal data
    private final String firstName, lastName, email, phone;
    //job related data: optional
    @Nullable private final String jobTitle;
    //message 
    private final String message;

    public Comment(String firstName, String lastName, String email, String phone, String message, String jobTitle){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.message = message;
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

    public String getJobTitle() {
        return jobTitle;
    }

    public String getMessage() {
        return message;
    }
}