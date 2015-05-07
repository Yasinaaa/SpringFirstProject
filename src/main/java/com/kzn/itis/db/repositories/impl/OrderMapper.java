package com.kzn.itis.db.repositories.impl;

import com.kzn.itis.db.model.Order;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yasina on 27.03.15.
 */
public class OrderMapper implements RowMapper<Order> {
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setFirstName(rs.getString("firstName"));
            order.setLastName(rs.getString("secondName"));
            return order;
            //
        }
    }