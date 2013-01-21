/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hw2;

import java.sql.*;
import hw2.Errors.*;
/**
 *
 * @author Hardik
 * Thanks to sample code provided by Ex6.java
 */
public class DBUtils {
    
    Connection mainConnection = null;
    Statement mainStatement = null;
    ResultSet mainResultSet = null;
    
    public static final String dbName = "hr";
    public static final String dbUsername = "SYS as SYSDBA";
    // <editor-fold>
    public static final String dbPassword = "password";
    // </editor-fold>
    public void connect() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            String URL = "jdbc:oracle:thin:@//localhost:1521/"+dbName;
            mainConnection = DriverManager.getConnection(URL, dbUsername, dbPassword);
            mainStatement = mainConnection.createStatement();
        }
        catch (Exception e) {
            System.out.println("ERROR: "+Errors.DB_CONNECT_ERROR+". Exception: "+e.toString());
            System.exit(Errors.DB_CONNECT_ERROR);
        }
    }
    
    public void insert(String query) {
        System.out.println("Inserting : "+query);

        try {
            mainStatement.executeUpdate(query);
        }
        catch (Exception e) {
            System.out.println("ERROR: "+Errors.DB_INSERT_ERROR+". Exception: "+e.toString());
            System.exit(Errors.DB_INSERT_ERROR);
        }
        System.out.println("Done Inserting! ");
        
    }
    
    public void delete(String type) {
        if(!(type.equalsIgnoreCase("building") || type.equalsIgnoreCase("person") || type.equalsIgnoreCase("ap"))) {
            System.out.println("ERROR TYPE : "+Errors.INVALID_TYPE_ERROR+ " given: "+type);
            return;
        }
        System.out.println("Deleting : "+type);
        try {
            mainStatement.executeUpdate("delete from "+type);
        }
        catch(Exception e) {
            System.out.println("ERROR: "+Errors.DB_DELETE_ERROR+". Exception: "+e.toString());
            System.exit(Errors.DB_DELETE_ERROR);
        }   
    }
    
    public void close() {
        try {
            mainConnection.close();
        }
        catch (Exception e) {
            System.out.println("ERROR: "+Errors.DB_CLOSE_ERROR+". Exception: "+e.toString());
            System.exit(Errors.DB_CLOSE_ERROR);
        }
        System.out.println("Connection closed.");
    }
    
    public ResultSet getResultSet(String query) {
        try {
            mainResultSet = mainStatement.executeQuery(query);
        }
        catch (Exception e) {
            System.out.println("ERROR: "+Errors.DB_QUERY_EXECUTE_ERROR+". Exception: "+e.toString());
            System.exit(Errors.DB_QUERY_EXECUTE_ERROR);
        }
        return mainResultSet;
    }
    
}
