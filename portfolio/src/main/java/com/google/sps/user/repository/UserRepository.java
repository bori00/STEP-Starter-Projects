package com.google.sps.user.repository;

import com.google.sps.user.User;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;

public interface UserRepository{
    public void saveUser(User user);

    public boolean isUserDataSaved(String id);

    @Nullable
    public User getUser(String id);

    public HashMap<Integer, User> getAllUsers();
}