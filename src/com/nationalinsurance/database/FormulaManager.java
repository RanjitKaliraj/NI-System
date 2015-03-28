/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nationalinsurance.database;

import com.nationalinsurance.beans.FormulaBean;
import com.nationalinsurance.beans.ItemBean;
import com.nationalinsurance.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ronzeyt
 */
public class FormulaManager {
    private Connection connection;
        
    public FormulaManager(){
        System.out.println("FormulaManager Class:: Starting Constructor and initializing database connection");
        connection = DatabaseConnection.DatabaseConnect();
    }       
        
    //This function add item details in database
    public void add(FormulaBean formula){
        try{
            ArrayList<String> formulaSteps = formula.getFormulaStepsList();
            int sid = formula.getSid();
            System.out.println("hello world: "+sid);
            ArrayList<String> uidList = formula.getReturnItemsList();
            
            //checking the SID of the item to be formulated
            if (this.checkUID(formula.getSid())){ 
                for (int i=0; i<formulaSteps.size();i++){
                    String sql = "INSERT INTO FormulaSteps (SID, Steps, ReturnItem, Formula) VALUES(?,?,?,?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setInt(1, sid);
                    statement.setInt(2, i+1);
                    statement.setString(3, uidList.get(i));
                    statement.setString(4, formulaSteps.get(i));
                    statement.executeUpdate(); 
                }
                formula.setAddStatus(true);     //Setting the add status to true. 
                String sql1 = "UPDATE FormulaStatus SET Status=? WHERE SID=?";
                PreparedStatement statement1 = connection.prepareStatement(sql1);
                statement1.setBoolean(1, true);
                statement1.setInt(2, sid);
                statement1.executeUpdate();
                
            }
            else{
                formula.setAddStatus(false);    //setting add status false
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("machate:   "+formula.isAdded());
    }
    
    //This function delete the formula steps
    public boolean deleteFormula(int sid){
        boolean status = false;
        try{
            String sql = "DELETE FROM FormulaSteps WHERE SID=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, sid);
            statement.executeUpdate(); 
            status = true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return status;
    }
    
    //This function checks the SID of the particular item
    public boolean checkUID(int sid){
        boolean status = false;
        try{
            String sql = "SELECT SID FROM ItemDefination WHERE SID=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, sid);
            ResultSet result = statement.executeQuery();
            if (result.next()){
                status = true;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }  
        return status;
    }
    
    //This method gets the formula steps from the database
    public ArrayList<ArrayList<String>> getFormulaSteps(int sid){
        ArrayList<ArrayList<String>> steps = new ArrayList<>();
        try{
          String sql = "SELECT * FROM FormulaSteps WHERE SID=?";
          PreparedStatement statement = connection.prepareStatement(sql);
          statement.setInt(1, sid);
          ResultSet result = statement.executeQuery();
          while(result.next()){
              ArrayList<String> formula = new ArrayList<>();
              formula.add(result.getString("ReturnItem"));
              formula.add(result.getString("Formula"));
              steps.add(formula);
          }
        }
        catch(SQLException e){
            e.printStackTrace();
        } 
        return steps;
    }
}
