/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 *
 * @author Claudinea de Almeida
 */
public class ConnectionFactory {
   // public static final String DRIVER="com.mysql.jdbc.Driver";
    //driver used
    public static final String DRIVER="com.mysql.cj.jdbc.Driver";
   // public static final String URL="jdbc:mysql://localhost:3306/dbnhms"; using im my laptop
   //URL with the IP from Google Cloud 35.234.144.255
    public static final String URL="jdbc:mysql://35.234.144.255:3306/dbnhms?useSSL=false";
    //user for connection
    public static final String USER="root";
    // public static final String PASS=""  localhost;
    //password to access the google cloud
    public static final String PASS="k5JAyDMrGnarHIwi";
    
    
    
    //getting the connection
    public static Connection getConnection(){
        try {
            //using the driver setted before
            Class.forName(DRIVER);
            //getting the connection with the parameters setted before
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Connection error", ex);
        }
    }
    //closing connection
    public static void CloseConnection(Connection con){
        if (con != null){
            try {
                con.close();
            } catch (SQLException ex) {
                System.err.print("Error: "+ ex);
            }
            
        }
    }
    
    public static void CloseConnection(Connection con, PreparedStatement stm){
        if (stm != null){
            try {
                stm.close();
            } catch (SQLException ex) {
                 System.err.print("Error: "+ ex);
            }
            
        }
        
        CloseConnection(con);
    }
    
    public static void CloseConnection(Connection con, PreparedStatement stm, ResultSet res){
        if (res != null){
            try {
                res.close();
            } catch (SQLException ex) {
                 System.err.print("Error: "+ ex);
            }
            
        }
        
        CloseConnection(con,stm);
    }
    
    
}
