package com.google.sps.user.repository;

import com.google.sps.user.User;
import com.google.sps.user.repository.impl.DatastoreUserRepository;
import org.jetbrains.annotations.Nullable;

public class UserRepositoryFactory{
    public static enum UserRepositoryType{
        DATASTORE
    }

    public UserRepository getUserRepository(UserRepositoryType repositoryType){
        if(repositoryType == null) {
            throw new IllegalArgumentException("repositoryType can't be null!");
        }
        switch(repositoryType){
            case DATASTORE:
                return new DatastoreUserRepository();
            default:
                throw new IllegalArgumentException("not an existing respository type");
        }
    }
}