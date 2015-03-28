/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nationalinsurance.beans;

import java.util.ArrayList;

/**
 *
 * @author Ronzeyt
 */
public class FormulaBean{
    
    public static final String PROP_SAMPLE_PROPERTY = "sampleProperty";
    private ArrayList<String> formulaStepsList;
    private int sid;
    private String uid;
    private ArrayList<String> returnItemsList;
    private boolean addStatus;

    /**
     * @return the formulaStepsList
     */
    public ArrayList<String> getFormulaStepsList() {
        return formulaStepsList;
    }

    /**
     * @param formulaStepsList the formulaStepsList to set
     */
    public void setFormulaStepsList(ArrayList<String> formulaStepsList) {
        this.formulaStepsList = formulaStepsList;
    }

    /**
     * @return the returnItemsList
     */
    public ArrayList<String> getReturnItemsList() {
        return returnItemsList;
    }

    /**
     * @param returnItemsList the returnItemsList to set
     */
    public void setReturnItemsList(ArrayList<String> returnItemsList) {
        this.returnItemsList = returnItemsList;
    }
 
    /**
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * @return the sid
     */
    public int getSid() {
        return sid;
    }

    /**
     * @return the addStatus
     */
    public boolean isAdded() {
        return addStatus;
    }

    /**
     * @param addStatus the addStatus to set
     */
    public void setAddStatus(boolean addStatus) {
        this.addStatus = addStatus;
    }

    /**
     * @param sid the sid to set
     */
    public void setSid(int sid) {
        this.sid = sid;
    }
}
