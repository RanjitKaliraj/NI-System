/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nationalinsurance.services;

import com.nationalinsurance.beans.ItemBean;
import com.nationalinsurance.database.FormulaManager;
import com.nationalinsurance.database.ItemManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Ronzeyt
 */
public class DefinationService {

    private String message;
    
    //This method add the item defination details
    public void addItem(ItemBean item){
        System.out.println("DefinationService Class:: addItem: starting adding items");
        if (item.getName().equals("") || item.getUid().equals("") || item.getDataType().equals("")){
            System.out.println("DefinationService Class:: addItem: fields are empty");
            this.setStatusMessage("Input fields are invalid. Try again!");
        }
        else{
            ItemManager itemManager = new ItemManager();
            System.out.println("DefinationService Class:: addItem: Calling database function add() to store data");
            itemManager.add(item);
            if (itemManager.isError()){
                this.setStatusMessage("ERROR: Name/UID already exist.");
                System.out.println("DefinationService Class:: addItem: entry already exists");
            }
            else if (itemManager.isAdded()){
                String type = "";
                if (item.getDataType().equals("i")){
                    type = "User Input";
                }
                else if(item.getDataType().equals("c")){
                    type = "Calculate";
                }
                else if (item.getDataType().equals("f")){
                    type = "Formulate";
                }
                
                this.setStatusMessage("Successful: Item added.\n"+"   Name: "+item.getName()+"\n"+"   UID: "+item.getUid()+"\n"+"   Data Type: "+type+"\n");
                System.out.println("DefinationService Class:: addItem: item added");
            }
            else{
                this.setStatusMessage("Unsuccessful: error while adding item. Try again.");
                System.out.println("DefinationService Class:: addItem: item add unsuccessful");
            }
        }
    }
    
    public void setStatusMessage(String msg){
        System.out.println("DefinationService Class:: setStatusMessage: starting setting status messge");
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("E hh:mm:ss a");
        message = df.format(date)+ ":: " + msg;
    }
    
    //This method display the message
    public String displayMessage(){
        System.out.println("DefinationService Class:: displayMessage: returning status message");
        return message;
    }
    
    //This method gets item name and uid and store the combined name in arraylist
    public ArrayList<String> getFormulateItemLists(){
        System.out.println("DefinationService Class:: getFormulateItemsLists: starting list of formulate items");
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> uid = new ArrayList<>();
        HashMap<String, Integer> formulateList = new HashMap<>();
        
        System.out.println("DefinationService Class:: getFormulateItemsLists: Instantiating new ItemManager() object");
        ItemManager item = new ItemManager();
        
        System.out.println("DefinationService Class:: getFormulateItemsLists: getting Formulate item Names");
        name = item.getFormulateItemNames();
        
        System.out.println("DefinationService Class:: getFormulateItemsLists: getting Formulate item UID");
        uid = item.getFormulateItemUID();
        
        System.out.println("DefinationService Class:: getFormulateItemsLists: getting Formulate item Status");
        formulateList = item.getFormulaStatus();
        
        
        for (int i=0; i<name.size();i++){
            String statusMsg = "";
            try{
                System.out.println("DefinationService Class:: getFormulateItemsLists: setting Undefined tag");
                Integer status = (int) formulateList.get(uid.get(i));
                //status = hm.get(uid.get(i));
                //System.out.println("printing: "+status);
                if (status.equals(0)){
                    statusMsg=" (Undefined)";
                }
            }
            catch (NullPointerException e){  
                System.out.println("DefinationService Class::getFormulateItemsLists: null pointer exception ");
                statusMsg = "";
            }           
            
            String formulateItem = name.get(i)+"/"+uid.get(i)+statusMsg;
            System.out.println("DefinationService Class::getFormulateItemsLists: Setting and adding formulate item name. ");
            list.add(formulateItem);
        }
        System.out.println("DefinationService Class::getFormulateItemsLists: returning list of formulate items");
        return list;
    }
    
    //This method gets the list of all Items and its details
    public ArrayList<ItemBean> getItemsList(){
        System.out.println("DefinationService Class::displayItemsList: starting function to list all items ");
        ArrayList<ItemBean> allItems = new ArrayList<>();
        ItemManager item = new ItemManager();            
        allItems = item.getItems();        
        return allItems;       
    }
    
    //This function gets and returns all item UID lists
    public ArrayList<String> getUIDList(){
        ArrayList<String> list = new ArrayList<>();
        ItemManager item = new ItemManager();
        
        list = item.getAllItemUID();
        return list;
    }
    
    //This function gets and returns all item UID lists
    public ArrayList<String> getFormulateUIDList(){
        ArrayList<String> list = new ArrayList<>();
        ItemManager item = new ItemManager();
        
        list = item.getFormulateItemUID();
        return list;
    }
    
    //This function gets the input uid lists
    public ArrayList<String> getInputUIDList(){
        ArrayList<String> list = new ArrayList<>();
        ItemManager item = new ItemManager();
        
        list = item.getInputItemUID();
        return list;
    }
    
    //This function gets and returns all item UID lists
    public ArrayList<String> getCalculateUIDList(){
        ArrayList<String> list = new ArrayList<>();
        ItemManager item = new ItemManager();
        
        list = item.getCalculateItemUID();
        return list;
    } 
    
    //This method gets and returns the formulaList
    public ArrayList<String> getFormulaSteps(String uid){
        ArrayList<String> formulaSteps = new ArrayList<>();
        ItemManager itemManager = new ItemManager();
        int sid = itemManager.getSID(uid);
        
        FormulaManager formulaManager = new FormulaManager();
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        list = formulaManager.getFormulaSteps(sid);
        
        for(int i=0; i<list.size();i++){
            formulaSteps.add(list.get(i).toString());
        }
        return formulaSteps;
    }
}
