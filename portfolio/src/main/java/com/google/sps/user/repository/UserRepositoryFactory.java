package com.google.sps.user.repository;

import com.google.sps.user.User;
import com.google.sps.user.repository.impl.DatastoreUserRepository;
import org.jetbrains.annotations.Nullable;

public class UserRepositoryFactory{
    public UserRepository getUserRepository(String repositoryType){
        if(repositoryType==null) {
            throw new IllegalArgumentException("repositoryType can't be null!");
        }
        switch(repositoryType){
            case "Datastore":
                return new DatastoreUserRepository();
            default:
                throw new IllegalArgumentException("not an existing respository type");
        }
    }
}