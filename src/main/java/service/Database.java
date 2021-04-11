package service;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database database_instance = null;
    private Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/eticket";
    private final String user = System.getenv("eticketdb_user");
    private final String password = System.getenv("eticketdb_password");

    private Database() {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static Database getInstance() {
        try {
            if(database_instance == null || database_instance.connection.isClosed()) {
                database_instance =  new Database();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return database_instance;
    }
}
