/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nationalinsurance.services;

import com.nationalinsurance.beans.FormulaBean;
import com.nationalinsurance.database.FormulaManager;
import com.nationalinsurance.database.ItemManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Ronzeyt
 */
public class FormulaDefination {
    private String message;
    
    //This function adds formula to the database
    public void addFormula(ArrayList<String> formulaList, String uid,ArrayList<String> returnUidList){
        FormulaBean formulaBean = new FormulaBean();
        FormulaManager formulaManager = new FormulaManager();
        
        formulaBean.setFormulaStepsList(formulaList);
        formulaBean.setReturnItemsList(returnUidList);
        formulaBean.setSid(this.getSID(uid));   //setting the sid of the selected uid
        
        //adding formulasteps in the database
        formulaManager.add(formulaBean);
        
        if (formulaBean.isAdded()){
            String msg = "";
            for (int j=0;j<formulaList.size();j++){
                msg = msg.concat("  "+returnUidList.get(j)+"="+formulaList.get(j)+"\n");
            }            
            this.setStatusMessage("Success! Formula Successfully Added.\n"+msg);
        }
        else{
            this.setStatusMessage("Error! Formula Already added for selected Item.");
        }
    }
    
    //This function adds formula to the database
    //@param formulaList, uid and returnUidlist
    public void updateFormula(ArrayList<String> formulaList, String uid,ArrayList<String> returnUidList){
        FormulaBean formulaBean = new FormulaBean();
        FormulaManager formulaManager = new FormulaManager();
        
        formulaBean.setFormulaStepsList(formulaList);
        formulaBean.setReturnItemsList(returnUidList);
        formulaBean.setSid(this.getSID(uid));   //setting the sid of the selected uid
        
        //deleting the previous formula steps
        if (formulaManager.deleteFormula(this.getSID(uid))){
            //adding formulasteps in the database if the deletion is successful
            formulaManager.add(formulaBean);
        }        
        
        if (formulaBean.isAdded()){
            String msg = "";
            for (int j=0;j<formulaList.size();j++){
                msg = msg.concat("  "+returnUidList.get(j)+"="+formulaList.get(j)+"\n");
            }            
            this.setStatusMessage("Success! Formula Successfully Updated.\n"+msg);
        }
        else{
            this.setStatusMessage("Error! Formula Already updated for selected Item.");
        }
    }
    
    //this function sets the status message
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
    
    //This method gets the SID of the specific UID
    //it is used in addFormula() function.
    public int getSID(String uid){
        ItemManager item = new ItemManager();
        return item.getSID(uid);        
    }
}
