package com.kzn.itis.db.repositories.impl;

import com.kzn.itis.db.model.Order;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by yasina on 21.03.15.
 */
public interface OrderRepository {


    void add(Order order);
    void remove();
    void update(String newOrder);
    List<Order> getAllOrders();
    int countOfOrders();

}
