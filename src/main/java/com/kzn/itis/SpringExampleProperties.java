package com.kzn.itis;

import com.kzn.itis.db.config.DatabaseConfiguration;
import com.kzn.itis.db.model.Order;
import com.kzn.itis.db.model.User;
import com.kzn.itis.db.repositories.impl.OrderRepositoryImpl;
import com.kzn.itis.db.repositories.impl.UserRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.*;

/**
 *
 */
public class SpringExampleProperties {

    private static final Logger logger = LoggerFactory.getLogger(SpringExampleProperties.class);

    @Autowired
    private DatabaseConfiguration config;

    public DatabaseConfiguration getConfig() {
        return config;
    }

    public void setConfig(DatabaseConfiguration config) {
        this.config = config;
    }

    private OrderRepositoryImpl orderDAO;
    private UserRepositoryImpl userDAO;
    private Statement stmt;

    public void run() {
        logger.info("Welcome to Example Application");
        logger.info("url=" + config.getDbUrl());
        logger.info("username=" + config.getDbUser());

        testDB();

        orderDAO = new OrderRepositoryImpl(config);
        userDAO = new UserRepositoryImpl(config);

        userDAO.add(new User("Zalyalova","Yasina",19));
        userDAO.add(new User("Nikitina","Ekaterina",19));

        System.out.println("All users count is");
        System.out.println(userDAO.countOfUsers());

        System.out.println("All users");
        userDAO.getAllUsers();

        User one = new User("Sytdikov","Ruzal",19);
        User two = new User("Zagulova","Maria",19);

         System.out.println("Insert two user");
         userDAO.add(one);
         userDAO.add(two);

        System.out.println("Now users count is");
        System.out.println(userDAO.countOfUsers());

        System.out.println("All users");
        userDAO.getAllUsers();

        System.out.println("Remove last user");
        userDAO.remove();

        System.out.println("Update last user");
        userDAO.update("Gulnaz");

        System.out.println("All users");
        userDAO.getAllUsers();

        System.out.println("Now users count is");
        System.out.println(userDAO.countOfUsers());

        System.out.println("/////////////////Orders////////////////");
        orderDAO.add(new Order("Ahmetshina", "Alsu"));
        orderDAO.add(new Order("Valiev","Damir"));

        System.out.println("All orders count is");
        System.out.println(orderDAO.countOfOrders());

        System.out.println("All orders");
        orderDAO.getAllOrders();

        Order some1 = new Order("Ahmadeeva","Venera");
        Order some2 = new Order("Gureva","Yana");

        System.out.println("Insert two order");
        orderDAO.add(some1);
        orderDAO.add(some2);

        System.out.println("Now orders count is");
        System.out.println(orderDAO.countOfOrders());

        System.out.println("All orders");
        orderDAO.getAllOrders();

        System.out.println("Remove last order");
        orderDAO.remove();

        System.out.println("Update last order");
        orderDAO.update("Gulnaz");

        System.out.println("All orders");
        orderDAO.getAllOrders();

        System.out.println("Now orders count is");
        System.out.println(orderDAO.countOfOrders());





    }


    protected void testDB(){

        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void main(String... args) throws SQLException {

        AbstractApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        SpringExampleProperties main = (SpringExampleProperties)context.getBean("exampleApp");
        main.run();
    }
}
