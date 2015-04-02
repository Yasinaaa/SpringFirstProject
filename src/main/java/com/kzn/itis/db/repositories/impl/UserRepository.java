package com.kzn.itis.db.repositories.impl;

import com.kzn.itis.db.model.User;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

public interface UserRepository {


    void add(User user);
    void remove();
    void update(String newUser, int age);
    List<User> getAllUsers();
    int countOfUsers();

}
