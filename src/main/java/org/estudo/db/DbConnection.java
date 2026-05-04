package org.estudo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    public static Connection openConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/db", "db", "123");
    }
}
