package com.kzn.itis.db.repositories.impl;

import com.kzn.itis.db.model.User;
import java.sql.ResultSet;

public interface UserRepository {

    void add(User user);
    void remove();
    void update(String newUser);
    ResultSet getAllUsers();
    int countOfUsers();

}
