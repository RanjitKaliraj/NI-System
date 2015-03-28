/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nationalinsurance.beans;

/**
 *
 * @author Ronzeyt
 */
public class ItemBean {
    private String name;
    private String uid;
    private String dataType;
    private int SID;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the SID
     */
    public int getSID() {
        return SID;
    }

    /**
     * @param SID the SID to set
     */
    public void setSID(int SID) {
        this.SID = SID;
    }
    
        
}
