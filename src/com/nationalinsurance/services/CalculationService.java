/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nationalinsurance.services;

import com.nationalinsurance.database.FormulaManager;
import com.nationalinsurance.database.ItemManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

//these import are for converting string formula to math expression
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 *
 * @author Ronzeyt
 */
public class CalculationService {
    
    //This function gets all the formulate items to display is calculation page
    public ArrayList<String> getFormulateItems(){
        ItemManager itemManager = new ItemManager();
        ArrayList<String> itemName = itemManager.getFormulateItemNames();
        ArrayList<String> itemUid = itemManager.getFormulateItemUID();
        
        HashMap<String,Integer> status = new HashMap<>();
        status = itemManager.getFormulaStatus();
        ArrayList<String> list = new ArrayList<>();
        for(int i=0; i<itemName.size();i++){
            if (status.get(itemUid.get(i))==1){
                String item = itemName.get(i)+"("+itemUid.get(i)+")";
                list.add(item);
            }
            else{
                String item = itemName.get(i)+"("+itemUid.get(i)+")"+" - undefined";
                list.add(item);
            }
        }
        return list;
    }   
    
    //This method calculate selected formula.
    public void calculateFormula(String formulateUid){
        ItemManager itemManager = new ItemManager();
        int sid = itemManager.getSID(formulateUid);
        
        
    }
    
    //THis function get the input items of the selected formula item
    public ArrayList<String> getInputItems(String formulateUid){
        ItemManager itemManager = new ItemManager();
        int sid = itemManager.getSID(formulateUid);
        
        //getting formula steps and storing the input items of formula in array
        FormulaManager formulaManager = new FormulaManager();
        ArrayList<ArrayList<String>> formulaSteps = formulaManager.getFormulaSteps(sid);        
        String inputItemNames = "";
         for (int i=0;i<formulaSteps.size();i++){
             inputItemNames = inputItemNames.concat(formulaSteps.get(i).get(1)+"+");
         }
        String[] list = inputItemNames.split("([\\d\\W]+)");  
        ArrayList<String> newInputList = new ArrayList<>();
        
        //eliminating the repeated input items.
        for (String value : list){
            if (!newInputList.contains(value)){
            newInputList.add(value);
            }
            System.out.println(value);
        }
        
        ArrayList<String> inputItems = new ArrayList<>();    //array to be return from this method          
        ArrayList<String> inputItemList = itemManager.getInputItemUID();        //getting list of input UID 
        HashMap<String,String> itemsName = new HashMap<>();
        itemsName = itemManager.getInputItemsName();
                
        for(int i=0;i<newInputList.size();i++){
            for(int j=0;j<inputItemList.size();j++){
                if (newInputList.get(i).equals(inputItemList.get(j))){     //checking if the UID is type of input or not
                    String name = itemsName.get(newInputList.get(i));     //getting the input item name.
                    name = name.concat("("+newInputList.get(i)+")");       //adding input UID to the name
                    inputItems.add(name); 
                    break;                   
                }
            }
        }
        return inputItems;       
    }
    
    
    //this function gets the name of the formulate UID
    public String getFormulateName(String uid){
        ItemManager itemManager = new ItemManager();
        String name = itemManager.getFormulateName(uid);
        return name;
    }
    
    //This function calculates the formula of selected Item
    public double calculateFormula(HashMap<String,Double> value, String uid){
        double result = 0.0;        //final result to return
        
        //this collection is used to store value from the form
        //also it is used to add returnItem values.
        HashMap<String,Double> calculateValues = new HashMap<>();
        calculateValues = value;   
                
        //getting SID of this UID from the parameter
        ItemManager itemManager = new ItemManager();
        int sid = itemManager.getSID(uid);
        
        //getting list of input items
        ArrayList<String> inputUids = itemManager.getInputItemUID();
        //getting list of calculate items
        ArrayList<String> calculateUids = itemManager.getCalculateItemUID();
        
        //getting formula steps arraylist
        FormulaManager formula = new FormulaManager();
        ArrayList<ArrayList<String>> formulaList = formula.getFormulaSteps(sid);        
        
        //looping througn each formula steps while calculating the value of each step and adding to hashmap
        int size = formulaList.size();
        for (int i=0;i<size;i++){
            String CalculateItem = formulaList.get(i).get(0);       //getting return items
            String formula_Expression = formulaList.get(i).get(1);  //getting formula step
            //formula_Expression.
            for(int j=0;j<inputUids.size();j++){
                if(formula_Expression.contains(inputUids.get(j))){      //if the expression contains each input value from input value arraylist
                    double valueOfUID = calculateValues.get(inputUids.get(j));      //get the value of that input uid
                    formula_Expression = formula_Expression.replace(inputUids.get(j), Double.toString(valueOfUID));      //replacing the UID with value from the form(hashmap)        
                }
            }
            for(int j=0;j<calculateUids.size();j++){
                //checking if the expression contains calculate type UID of not if yes then replace with its value.
                if(formula_Expression.contains(calculateUids.get(j))){
                    double valueOfCalculateUID = calculateValues.get(calculateUids.get(j));      //get the value of that input uid
                    formula_Expression = formula_Expression.replace(calculateUids.get(j), Double.toString(valueOfCalculateUID));      //replacing the UID with value from the form(hashmap)        
                }
            }
                
            //converting String type formula_Expression to math expression and calculating the total
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
              
            //calculating the total value of this returnItem formula step.
            Double total = 0.0;
            try {
                total = (Double) engine.eval(formula_Expression);
            } catch (ScriptException ex) {
                Logger.getLogger(CalculationService.class.getName()).log(Level.SEVERE, null, ex);
            }
            calculateValues.put(CalculateItem, total);      //storing the returnItem value in hashmap           
        }
        result = calculateValues.get(uid);      //getting the total value 
        return result;
    }
}
