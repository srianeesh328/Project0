package org.example.DAOs;

import org.example.models.User;

import java.util.ArrayList;

public interface UserDAOInterface {

    ArrayList<User> getUser();

    public User getUserById(int id);

    User insertUser(User a);

    public User updateUserDetails(User a);
}
