package com.kzn.itis.db.repositories.impl;

import com.kzn.itis.db.config.DatabaseConfiguration;
import com.kzn.itis.db.model.Order;
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
public class OrderRepositoryImpl implements OrderRepository {

    public OrderRepositoryImpl() {
    }

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private Statement orderStatement = null;
    private DatabaseConfiguration config;
    private Connection con;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final Logger logger = LoggerFactory.getLogger(OrderRepositoryImpl.class);

    public OrderRepositoryImpl(DataSource dataSource) {

        setDataSource(dataSource);

      /*  this.config = config;
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
        }*/

                /*String creator = "CREATE TABLE ORDERS ("
                        + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1,INCREMENT BY 1),"
                        + "firstName VARCHAR(256),"
                        + "secondName VARCHAR(256),"
                        + "PRIMARY KEY(id))";*/


    }


    @Override
    public void add(Order order) {
        jdbcTemplate.update("INSERT INTO USERS VALUES (?,?)",
                order.getFirstName(),
                order.getLastName());
    }


    @Override
    public void remove() {
        int num = countOfOrders();
        String sql = "DELETE FROM ORDERS WHERE id=?";
        jdbcTemplate.update(sql,countOfOrders());
    }


    @Override
    public void update(String newFirstName) {
        int num = countOfOrders();
        String sql = "UPDATE ORDERS SET firstname=? WHERE id=?";
        jdbcTemplate.update(sql,newFirstName,countOfOrders());
    }


    @Override
    public List<Order> getAllOrders() {
        String sqlQuery = "SELECT * FROM ORDERS ";
        List<Order> orders = jdbcTemplate.query(sqlQuery, new OrderMapper());
        return orders;
    }

    @Override
    public int countOfOrders() {
        int count = 0;
        String sqlQuery = "SELECT COUNT(*) FROM ORDERS";
        count = jdbcTemplate.queryForList(sqlQuery).size();
        return count;
    }
}



