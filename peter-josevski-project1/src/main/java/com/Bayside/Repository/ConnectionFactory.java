package com.Bayside.Repository;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection createConnection() throws SQLException {
        Driver postgresDriver = new Driver();
        DriverManager.registerDriver(postgresDriver);

        String url = "jdbc:postgresql://127.0.0.1:5432/postgres";




        String username = System.getenv("postgres");
        String password = System.getenv("peter99");

        Connection connectionObject = DriverManager.getConnection(url, username, password);

        return connectionObject;
    }

}
