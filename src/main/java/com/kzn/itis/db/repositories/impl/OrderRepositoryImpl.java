package com.kzn.itis.db.repositories.impl;

import com.kzn.itis.db.config.DatabaseConfiguration;
import com.kzn.itis.db.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by yasina on 13.03.15.
 */
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    public OrderRepositoryImpl() {
    }

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedJDBCTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(DataSource dataSource) {
        this.namedJDBCTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final Logger logger = LoggerFactory.getLogger(OrderRepositoryImpl.class);

    public OrderRepositoryImpl(DataSource dataSource) {

      setNamedParameterJdbcTemplate(dataSource);

    }


    @Override
    public void add(Order order) {
        Map parametrs = new HashMap();
        parametrs.put("firstName", order.getFirstName());
        parametrs.put("secondName", order.getLastName());
        namedJDBCTemplate.update("INSERT INTO ORDERS (firstName,secondName) VALUES (:firstName,:secondName)",parametrs);
    }


    @Override
    public void remove() {
        int num = countOfOrders();
        String sql = "DELETE FROM ORDERS WHERE id=:id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", Integer.valueOf(num));
        namedJDBCTemplate.update(sql, namedParameters);
    }


    @Override
    public void update(String newFirstName) {
        int num = countOfOrders();
        String sql = "UPDATE ORDERS SET firstname=:firstname WHERE id=:id";
        MapSqlParameterSource parametrs = new MapSqlParameterSource();
        parametrs.addValue("firstname", newFirstName);
        parametrs.addValue("id", num);
        namedJDBCTemplate.update(sql,parametrs);
    }


    @Override
    public List<Order> getAllOrders() {
        String sqlQuery = "SELECT * FROM ORDERS ";
        List<Order> orders = namedJDBCTemplate.query(sqlQuery, new OrderMapper());
        return orders;
    }

    @Override
    public int countOfOrders() {
        int count = 0;
        String sqlQuery = "SELECT COUNT(*) FROM ORDERS";
        count = namedJDBCTemplate.getJdbcOperations().queryForInt(sqlQuery);

        return count;
    }
}



