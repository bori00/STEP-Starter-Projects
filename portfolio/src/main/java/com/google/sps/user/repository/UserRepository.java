package com.google.sps.user.repository;

import com.google.sps.user.User;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;

/** Handles the storage of User data */
public interface UserRepository {
    public void saveUser(User user);

    public boolean isUserDataSaved(String id);

    @Nullable
    public User getUser(String id);

    public HashMap<String, User> getAllUsers();
}