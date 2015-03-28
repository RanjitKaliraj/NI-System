/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nationalinsurance.database;

import com.nationalinsurance.beans.ItemBean;
import com.nationalinsurance.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
/**
 *
 * @author Ronzeyt
 */
public class ItemManager {
    private Connection connection;
    private boolean addStatus;
    private boolean updateStatus;
    private boolean deleteStatus;
    private boolean error;
    
    public ItemManager(){
        System.out.println("ItemManager Class:: Starting Constructor and initializing database connection");
        connection = DatabaseConnection.DatabaseConnect();
    }
    
    //This function add item details in database
    public void add(ItemBean item){
        System.out.println("ItemManager Class:: add: Starting to function to add items");
        System.out.println(item.getName()+item.getUid()+item.getDataType());
        System.out.println("ItemManager Class:: add: Checking if items is already exist or not");
        if (!this.checkItemUID(item.getUid()) && !this.checkItemName(item.getName())){
            System.out.println("ItemManager Class:: add: Item is not already exists");
            try{
                System.out.println("ItemManager Class:: add: Starting SQL actions");
                String sql="INSERT INTO ItemDefination (UID,ItemName,Type) VALUES(?,?,?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, item.getUid());
                statement.setString(2, item.getName());
                statement.setString(3,item.getDataType());
                System.out.println("step1");
                if (statement.executeUpdate()==1){
                    System.out.println("ItemManager Class:: add: item added");
                    this.setAddStatus(true);
                    System.out.println("ItemManager Class:: add: Add status set true");
                    System.out.println("ItemManager Class:: add: Calling initiateFStatus() to add item UID and SID in FormulaStatus table");
                    initiateFStatus(item.getUid());                    
                }  
                else{
                    System.out.println("ItemManager Class:: add: Item add unsuccessful");
                    this.setAddStatus(false);
                    System.out.println("ItemManager Class:: add: Add status set false");
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            System.out.println("ItemManager Class:: add: error occured and setting error status true");
            this.setErrorStatus(true);
        }
    }
    public void setAddStatus(boolean status){
        System.out.println("ItemManager Class:: setAddStatus: setting add status ");
        addStatus = status;
    }
    public boolean isAdded(){
        System.out.println("ItemManager Class:: isAdded: returning add status");
        return addStatus;
    }
    public void setErrorStatus(boolean status){
        System.out.println("ItemManager Class:: setErrorStatus: setting error status ");
        error = status;
    }
    public boolean isError(){
        System.out.println("ItemManager Class:: isError: returning error status ");
        return error;
    }
    
    //this method checks if the item uid is already present in database or not
    public boolean checkItemUID(String uid){
        System.out.println("ItemManager Class:: checkItemUID: starting checking item UID ");
        boolean status = false;
        try{
            System.out.println("ItemManager Class:: checkItemUID: Performing SQL actions");
            String sql="SELECT * FROM ItemDefination WHERE UID=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, uid);
            ResultSet result = statement.executeQuery();
            if (result.next()){
                System.out.println("ItemManager Class:: checkItemUID: UID is present");
                status = true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("ItemManager Class:: checkItemUID: returning check status");
        return status;        
    }
    //this method checks if the item name is already present in database or not
    public boolean checkItemName(String name){
        System.out.println("ItemManager Class:: checkItemName: checkItemName: starting to check item name ");
        boolean status = false;
        try{
            System.out.println("ItemManager Class:: checkItemName: Performing SQL actions ");
            String sql="SELECT * FROM ItemDefination WHERE ItemName=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            if (result.next()){
                System.out.println("ItemManager Class:: checkItemName: Item name is present");
                status = true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("ItemManager Class:: checkItemUID: returning status");
        return status;        
    }
    
    //This method adds SID and UID in Formula Status table
    public void initiateFStatus(String uid){
        System.out.println("ItemManager Class:: initiateFStatus: starting to update FormulaStatus table ");
        int sid = 0;
        try{
            System.out.println("ItemManager Class:: initiateFStatus: Performing SQL actions to get item SID first");
            String sql="SELECT SID FROM ItemDefination WHERE UID=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, uid);
            ResultSet result = statement.executeQuery();
            if (result.next()){
                System.out.println("ItemManager Class:: initiateFStatus: SID achieved");
                sid = result.getInt("SID");
            }
            System.out.println("ItemManager Class:: initiateFStatus: Performing SQL actions to update FormulaStatus table");
            String sql1 = "INSERT INTO FormulaStatus (SID,UID,Status) VALUES(?,?,?)";
            PreparedStatement statement1 = connection.prepareStatement(sql1);
            statement1.setInt(1, sid);
            statement1.setString(2, uid);
            statement1.setBoolean(3, false);
            statement1.executeUpdate();
            System.out.println("ItemManager Class:: initiateFStatus: SQL executed");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }                                                                  
       
    //This function update the item details in database
    public void update(ItemBean item){
        System.out.println("ItemManager Class:: update: Starting to update item");
        try{
            String sql = "UPDATE ItemDefination SET UID=?,ItemName=?,Type=? WHERE SID=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, item.getUid());
            statement.setString(2, item.getName());
            statement.setString(3, item.getDataType());
            statement.setInt(4, item.getSID());
            statement.executeUpdate();
            System.out.println("ItemManager Class:: update: update performed and status set to true");
            this.setUpdateStatus(true);
        }
        catch (Exception e){
        }
    }
    public void setUpdateStatus(boolean status){
        System.out.println("ItemManager Class:: setUpdateStatus: setting update status ");
        updateStatus = status;
    }
    public boolean isUpdated(){
        System.out.println("ItemManager Class:: isUpdated: returning update status ");
        return updateStatus;
    }
    
    //This method delete particular item 
    public void delete(ItemBean item){
        System.out.println("ItemManager Class:: delete: starting to delete item ");
        try{
           String sql = "DELETE FROM ItemDefination WHERE SID=?"; 
           PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, item.getSID());
            statement.executeUpdate();
            System.out.println("ItemManager Class:: delete: setting delete status true");
            this.setDeleteStatus(true);
        }
        catch (Exception e){
        }
    }
    public void setDeleteStatus(boolean status){
        System.out.println("ItemManager Class:: setDeleteStatus: setting delete status ");
        deleteStatus = status;
    }
    public boolean isDeleted(){
        System.out.println("ItemManager Class:: isDeleted: returning delete status ");
        return deleteStatus;
    }
    
    //This method get all item details
    public ArrayList<ItemBean> getItems(){
        System.out.println("ItemManager Class:: getItems: Starting to get items list ");
        //ItemBean item = new ItemBean();
        ArrayList<ItemBean> items = new ArrayList<>();
        try{
            System.out.println("ItemManager Class:: getItems: Performing SQL actions");
           String sql = "SELECT * FROM ItemDefination ORDER BY ItemName";
           PreparedStatement statement = connection.prepareStatement(sql);
           ResultSet result = statement.executeQuery();           
           while (result.next()){
               ItemBean item = new ItemBean();
               System.out.println("ItemManager Class:: getItems: adding result data to arraylist: "+result.getString("ItemName"));
               item.setSID(result.getInt("SID"));
               item.setUid(result.getString("UID"));
               item.setName(result.getString("ItemName"));
               item.setDataType(result.getString("Type"));
               items.add(item);
           }           
        }
        catch (Exception e){
        }
        System.out.println("ItemManager Class:: getItems: Returning item list");
        return items;
    }
    
    //This method returns list of item name of formulate type only
    public ArrayList<String> getFormulateItemNames(){
        System.out.println("ItemManager Class:: getFormulateItemNames: starting to get formulate item names");
        ArrayList<String> names = new ArrayList<>();
        try{
            System.out.println("ItemManager Class:: getFormulateItemNames: performing SQL actions");
            String sql = "SELECT ItemName FROM ItemDefination WHERE Type=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "f");
            ResultSet result = statement.executeQuery();
            while (result.next()){
                System.out.println("ItemManager Class:: getFormulateItemNames: setting result in arraylist name");
                names.add(result.getString("ItemName"));
            }          
        }
        catch (Exception e){
            System.out.println("ItemManager Class:: getFormulateItemNames: Database is Down.");
        }
        System.out.println("ItemManager Class:: getFormulateItemNames: returning list");
        return names;
    }
    
     //This method returns list of item UID of formulate type only
    public ArrayList<String> getFormulateItemUID(){
        System.out.println("ItemManager Class:: getFormulateItemUID: starting to get UID of item");
        ArrayList<String> uid = new ArrayList<>();
        try{
            System.out.println("ItemManager Class:: getFormulateItemUID: performing SQL actions");
            String sql = "SELECT UID FROM ItemDefination WHERE Type=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "f");
            ResultSet result = statement.executeQuery();
            while (result.next()){
                System.out.println("ItemManager Class:: getFormulateItemUID: adding UID in the list");
                uid.add(result.getString("UID"));
            }          
        }
        catch (Exception e){
        }
        System.out.println("ItemManager Class:: getFormulateItemUID: returning list");
        return uid;
    }
    
    //This method returns list of item UID of input type
    public ArrayList<String> getInputItemUID(){
        System.out.println("ItemManager Class:: getInputItemUID: starting to get UID of input item ");
        ArrayList<String> uid = new ArrayList<>();
        try{
            System.out.println("ItemManager Class:: getInputItemUID: performing SQL actions");
            String sql = "SELECT UID FROM ItemDefination WHERE Type=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "i");
            ResultSet result = statement.executeQuery();
            while (result.next()){
                System.out.println("ItemManager Class:: getInputItemUID: adding UID in the list");
                uid.add(result.getString("UID"));
            }          
        }
        catch (Exception e){
        }
        System.out.println("ItemManager Class:: getInputItemUID: returning list");
        return uid;
    }
    
    //This method returns list of item UID of formulate type only
    public ArrayList<String> getCalculateItemUID(){
        System.out.println("ItemManager Class:: getFormulateItemUID: starting to get UID of item");
        ArrayList<String> uid = new ArrayList<>();
        try{
            System.out.println("ItemManager Class:: getFormulateItemUID: performing SQL actions");
            String sql = "SELECT UID FROM ItemDefination WHERE Type=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "c");
            ResultSet result = statement.executeQuery();
            while (result.next()){
                System.out.println("ItemManager Class:: getFormulateItemUID: adding UID in the list");
                uid.add(result.getString("UID"));
            }          
        }
        catch (Exception e){
        }
        System.out.println("ItemManager Class:: getFormulateItemUID: returning list");
        return uid;
    }
    
    
     //This method returns list of item UID of all type 
    public ArrayList<String> getAllItemUID(){
        System.out.println("ItemManager Class:: getAllItemUID: starting to get UID of all item");
        ArrayList<String> uid = new ArrayList<>();
        try{
            System.out.println("ItemManager Class:: getAllItemUID: performing SQL actions");
            String sql = "SELECT UID FROM ItemDefination";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                System.out.println("ItemManager Class:: getAllItemUID: adding UID in the list");
                uid.add(result.getString("UID"));
            }          
        }
        catch (Exception e){
        }
        System.out.println("ItemManager Class:: getAllItemUID: returning list");
        return uid;
    }
    
    
    //This method get the all formula item with status
    public HashMap getFormulaStatus(){
        System.out.println("ItemManager Class:: getFormulateStatus: starting to get formulate item status");
        HashMap<String, Integer> list = new HashMap<>();
        try{
            System.out.println("ItemManager Class:: getFormulateStatus: performing SQL actions");
            String sql = "SELECT * FROM FormulaStatus";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                System.out.println("ItemManager Class:: getFormulateStatus: adding data to the arraylist");
                //list.put(result.getInt("UID"), Integer.toString(result.getInt("Status")));
                String uid = result.getString("UID");
                Integer status = result.getInt("Status");                
                list.put(uid,status);
            }          
        }
        catch (Exception e){
        }
        System.out.println("ItemManager Class:: getFormulateStatus: returning data list");
        return list;
    } 
    
    //This method gets the sid of the particular UID 
    public int getSID(String uid){
        Integer sid = null;
        try{
            String sql = "SELECT SID FROM ItemDefination WHERE UID=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, uid);
            ResultSet result = statement.executeQuery();
            if (result.next()){
                sid = result.getInt(1);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return sid;
    }
    
    //THis function gets the list of input items name.
    public HashMap<String,String> getInputItemsName(){
        HashMap<String,String> list = new HashMap<>();        
        try{
            String sql = "SELECT UID,ItemName FROM ItemDefination";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                list.put(result.getString("UID"),result.getString("ItemName"));
            }
            
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return list;        
    }
    
    
    //This method returns name of formulate item with parameter UID
    public String getFormulateName(String UID){
        String name ="";
        try{
            String sql = "SELECT ItemName FROM ItemDefination WHERE UID=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, UID);
            ResultSet result = statement.executeQuery();
            if (result.next()){
                name = result.getString("ItemName");
            }          
        }
        catch (Exception e){
        }
        return name;
    }
    
}
