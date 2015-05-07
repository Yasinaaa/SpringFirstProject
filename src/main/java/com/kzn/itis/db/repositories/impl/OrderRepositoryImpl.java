package com.kzn.itis.db.repositories.impl;

import com.kzn.itis.db.config.DatabaseConfiguration;
import com.kzn.itis.db.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;


/**
 * Created by yasina on 13.03.15.
 */
public class OrderRepositoryImpl implements OrderRepository {

    private static final Logger logger = LoggerFactory.getLogger(OrderRepositoryImpl.class);
    private Statement orderStatement = null;
    private DatabaseConfiguration config;
    private Connection con;

    public OrderRepositoryImpl(DatabaseConfiguration config) {

        this.config = config;

        try {
            con = DriverManager.getConnection(this.config.getDbUrl());
            orderStatement = con.createStatement();

                String creator = "CREATE TABLE ORDERS ("
                        + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1,INCREMENT BY 1),"
                        + "firstName VARCHAR(256),"
                        + "secondName VARCHAR(256),"
                        + "PRIMARY KEY(id))";

                    orderStatement.execute(creator);

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
    public void add(Order order) {
        String sql = "INSERT INTO ORDERS(firstName,secondName)  VALUES ("
                + "'" + order.getFirstName() + "',"
                + "'" + order.getLastName() + "'"
                + ")";
        try {
            orderStatement = con.createStatement();
            orderStatement.executeUpdate(sql);
            orderStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void remove() {
        int num = countOfOrders();
        String sql = "DELETE FROM ORDERS" +
                " WHERE id=" + countOfOrders();
        try {
            orderStatement = con.createStatement();
            orderStatement.executeUpdate(sql);
            orderStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(String newFirstName) {
        int num = countOfOrders();
        String sql = "UPDATE ORDERS " +
                "SET firstname='" + newFirstName + "'" +
                " WHERE id=" + countOfOrders();
        try {
            orderStatement = con.createStatement();
            orderStatement.executeUpdate(sql);
            orderStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public ResultSet getAllOrders() {
        String sqlQuery = "SELECT * FROM ORDERS ";
        ResultSet res = null;
        try {
            orderStatement = con.createStatement();
            res = orderStatement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
       printText(res);
        return res;
    }

    @Override
    public int countOfOrders() {
        int count = 0;
        String sqlQuery = "SELECT COUNT(*) FROM ORDERS";
        try {
            orderStatement = con.createStatement();
            ResultSet res = orderStatement.executeQuery(sqlQuery);

            while (res.next()) {

                int n = res.getMetaData().getColumnCount();
                for (int i = 1; i <= n; i++) {
                    count = res.getInt(i);
                }

            }
            orderStatement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return count;
    }
}



