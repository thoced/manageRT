/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Thonon
 */
public class ConnectionSQL 
{
    private static Connection con;
    
    private static String url = "jdbc:mysql://localhost:3306/db_rad";
    private static String user = "paquot";
    private static String password = "paquotolivier";
    
    public ConnectionSQL() throws SQLException, ClassNotFoundException
    {
      
    }
    
    public static void Connect() throws SQLException, ClassNotFoundException
    {
      // For Mysql
       Class.forName("com.mysql.jdbc.Driver");
        // For hsqldb
        //Class.forName("org.hsqldb.jdbcDriver");
        // For SqlLite
        //Class.forName("org.sqlite.JDBC");
       con = DriverManager.getConnection(url, user, password);
        
    }
    
    public static Connection getCon() {
        return con;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        ConnectionSQL.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        ConnectionSQL.password = password;
    }

   
    
    
    
}
