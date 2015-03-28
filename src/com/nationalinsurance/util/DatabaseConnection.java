/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nationalinsurance.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ranjit Kaliraj
 */
public class DatabaseConnection {
    
    public static Connection DatabaseConnect(){
        try{
            System.out.println("Class::DatabaseConnection:: Starting database connection.");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://RONZEYT-PC\\SQLEXPRESS:1433;databaseName=national_ins_db;integratedSecurity=true");
            System.out.println("Class::DatabaseConnection:: Connection Established.");
            return connection;
        }
        catch (SQLException | ClassNotFoundException e){
            System.out.println("Class::DatabaseConnection:: Error while establishing connection.");
            return null;
        }
    }
    
    public static void stopConnection(Connection connection){
        try{
            System.out.println("Class::DatabaseConnection:: Starting to close database connection.");
            connection.close();
            System.out.println("Class::DatabaseConnection:: Connection is Successfully Closed.");
        }
        catch (SQLException e){
            System.out.println("Class::DatabaseConnection:: Error while closing connection.");
        }
    }
}