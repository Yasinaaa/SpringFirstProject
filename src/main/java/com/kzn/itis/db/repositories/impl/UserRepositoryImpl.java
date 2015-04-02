package com.kzn.itis.db.repositories.impl;

import com.kzn.itis.db.config.DatabaseConfiguration;
import com.kzn.itis.db.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * Created by yasina on 13.03.15.
 */
@Repository
public class UserRepositoryImpl  implements UserRepository{

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private Statement userStm = null;
    private DatabaseConfiguration config;
    private Connection con;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public UserRepositoryImpl() {
    }

    public UserRepositoryImpl (DataSource dataSource) {

        setDataSource(dataSource);

       /* this.config = config;
        try {
            con = DriverManager.getConnection(this.config.getDbUrl());
            userStm = con.createStatement();
            String creator = "CREATE TABLE USERS("
                    + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1,INCREMENT BY 1),"
                    + "lastname VARCHAR(256),"
                    + "firstname VARCHAR(256),"
                    + "age INTEGER not NULL,"
                    + "PRIMARY KEY(id))";
            userStm.execute(creator);
        } catch (SQLException e) {
            if (!e.getSQLState().equals("X0Y32")) {
                e.printStackTrace();
            }
        }
*/
    }

    @Override
    public void add(User user) {

        String sql = "INSERT INTO USERS(lastname,firstname,age) VALUES (?,?,?)";
        jdbcTemplate.update(sql, user.getLastName(), user.getLastName(), user.getAge());

    }

    @Override
    public void remove() {
        int num = countOfUsers();
        String sql = "DELETE FROM USERS WHERE id=?";
        jdbcTemplate.update(sql,countOfUsers());
    }

    @Override
    public void update(String newUser, int age) {
        int num = countOfUsers();
        String sql = "UPDATE USERS SET firstname=? ,age=? WHERE id=?";
        jdbcTemplate.update(sql,newUser,age,countOfUsers());

    }

    @Override
    public List<User> getAllUsers() {
        String sqlQuery = "SELECT * FROM USERS";
        List<User> users = jdbcTemplate.query(sqlQuery, new UserMapper());
        return users;
    }

    @Override
    public int countOfUsers() {
        int count = 0;
        String sqlQuery = "SELECT COUNT(*) FROM USERS";
        count = jdbcTemplate.queryForList(sqlQuery).size();
        return count;
    }
}


