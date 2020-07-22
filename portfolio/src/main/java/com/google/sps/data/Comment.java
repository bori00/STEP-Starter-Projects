package com.google.sps.data;

public class Comment{
    //personal data
    final private String firstName, lastName, email, phone;
    //job related data: optional
    private boolean jobRelated=false;
    private String jobTitle="";
    //message 
    final private String message;

    public Comment(String firstName, String lastName, String email, String phone, String message){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.message = message;
    }

    public void setJobRelated(){
        this.jobRelated = true;
    }

    public void addJobTitle(String jobTitle){
        this.jobTitle = jobTitle;
    }
}