/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author ANDJELA
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private final Connection connection;
    
    
    private DatabaseConnection() throws SQLException
    {
        //Class.forname()
        String url="jdbc:mysql://127.0.0.1:3306/gomoku";
        String dbUser="root";
        String dbPassword="";
        connection=DriverManager.getConnection(url, dbUser, dbPassword);
        connection.setAutoCommit(false);
    }
    public static DatabaseConnection getInstance() throws SQLException
    {
        if(instance==null)
            instance=new DatabaseConnection();
        return instance;
    }
    public Connection getConnection()
    {
        return connection;
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }
}
