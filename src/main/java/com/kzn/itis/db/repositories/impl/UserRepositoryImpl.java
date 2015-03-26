package com.kzn.itis.db.repositories.impl;

import com.kzn.itis.db.config.DatabaseConfiguration;
import com.kzn.itis.db.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created by yasina on 13.03.15.
 */
public class UserRepositoryImpl  implements UserRepository{

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private Statement userStm = null;
    private DatabaseConfiguration config;
    private Connection con;

    public UserRepositoryImpl(DatabaseConfiguration config) {

        this.config = config;

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


    }

    private void printText(ResultSet res) {
        try {
            while (res.next()) {
                String s = "";
                int n = res.getMetaData().getColumnCount();
                for (int i = 1; i <= n; i++) {
                    s += res.getString(i) + " ";
                }
                System.out.println(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(User user) {

        String sql = "INSERT INTO USERS(lastname,firstname,age) VALUES ("
                + "'" + user.getLastName() + "',"
                + "'" + user.getFirstName() + "',"
                + user.getAge() + ")";
        try {
            userStm = con.createStatement();
            userStm.executeUpdate(sql);
            userStm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove() {

        int num = countOfUsers();
        String sql = "DELETE FROM USERS" +
                " WHERE id=" + countOfUsers();
        try {
            userStm = con.createStatement();
            userStm.executeUpdate(sql);
            userStm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String newUser) {
        int num = countOfUsers();
        String sql = "UPDATE USERS " +
                "SET firstname='" + newUser + "'" +
                " WHERE id=" + countOfUsers();
        try {
            userStm = con.createStatement();
            userStm.executeUpdate(sql);
            userStm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ResultSet getAllUsers() {

        String sqlQuery = "SELECT * FROM USERS";
        ResultSet res = null;
        try {
            userStm = con.createStatement();
            res = userStm.executeQuery(sqlQuery);
            printText(res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;

    }

    @Override
    public int countOfUsers() {
        int count = 0;
        String sqlQuery = "SELECT COUNT(*) FROM USERS";
        try {
            userStm = con.createStatement();
            ResultSet res = userStm.executeQuery(sqlQuery);
            while (res.next()) {

                int n = res.getMetaData().getColumnCount();
                for (int i = 1; i <= n; i++) {
                    count = res.getInt(i);
                }
            }
            userStm.close();

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return count;
    }
}


