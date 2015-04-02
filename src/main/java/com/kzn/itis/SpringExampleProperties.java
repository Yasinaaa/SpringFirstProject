package com.kzn.itis;

import com.kzn.itis.db.config.DatabaseConfiguration;
import com.kzn.itis.db.model.Order;
import com.kzn.itis.db.model.User;
import com.kzn.itis.db.repositories.impl.OrderRepository;
import com.kzn.itis.db.repositories.impl.OrderRepositoryImpl;
import com.kzn.itis.db.repositories.impl.UserRepository;
import com.kzn.itis.db.repositories.impl.UserRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

    }


    protected void testDB(){

        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        }catch (Exception e){
            e.printStackTrace();
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


    public static void main(String... args) throws SQLException {

        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        SpringExampleProperties main = (SpringExampleProperties)context.getBean("exampleApp");
        main.run();

        UserRepository userRepository = context.getBean("userRepositoryImpl", UserRepositoryImpl.class);
        //OrderRepository orderRepository = context.getBean("orderRepositoryImpl", OrderRepositoryImpl.class);

       // UserRepositoryImpl userRepository = new UserRepositoryImpl();
       // OrderRepositoryImpl orderRepository = new OrderRepositoryImpl();

        userRepository.add(new User("Zalyalova", "Yasina", 19));
        userRepository.add(new User("Nikitina", "Ekaterina", 19));

        System.out.println("All users count is");
        System.out.println(userRepository.countOfUsers());

        System.out.println("All users");
        userRepository.getAllUsers();

        User one = new User("Sytdikov","Ruzal",19);
        User two = new User("Zagulova","Maria",19);

        System.out.println("Insert two user");
        userRepository.add(one);
        userRepository.add(two);

        System.out.println("Now users count is");
        System.out.println(userRepository.countOfUsers());

        System.out.println("All users");
        userRepository.getAllUsers();

        System.out.println("Remove last user");
        userRepository.remove();

        System.out.println("Update last user");
        userRepository.update("Gulnaz", 30);

        System.out.println("All users");
        userRepository.getAllUsers();

        System.out.println("Now users count is");
        System.out.println(userRepository.countOfUsers());

   /*     System.out.println("/////////////////Orders////////////////");
        orderRepository.add(new Order("Ahmetshina", "Alsu"));
        orderRepository.add(new Order("Valiev", "Damir"));

        System.out.println("All orders count is");
        System.out.println(orderRepository.countOfOrders());

        System.out.println("All orders");
        orderRepository.getAllOrders();

        Order some1 = new Order("Ahmadeeva","Venera");
        Order some2 = new Order("Gureva","Yana");

        System.out.println("Insert two order");
        orderRepository.add(some1);
        orderRepository.add(some2);

        System.out.println("Now orders count is");
        System.out.println(orderRepository.countOfOrders());

        System.out.println("All orders");
        orderRepository.getAllOrders();

        System.out.println("Remove last order");
        orderRepository.remove();

        System.out.println("Update last order");
        orderRepository.update("Gulnaz");

        System.out.println("All orders");
        orderRepository.getAllOrders();

        System.out.println("Now orders count is");
        System.out.println(orderRepository.countOfOrders());*/

       // User user = new User("Ayrat", "Natfullin", 31);

        //userRepository.add(user);

//        int userCount = userRepository.countOfUsers();

  //      logger.info("Количество пользователей: " + userCount);

    }
}
