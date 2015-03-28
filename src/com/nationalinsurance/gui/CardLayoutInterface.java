/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nationalinsurance.gui;

/**
 *
 * @author Ronzeyt
 */
public interface CardLayoutInterface {
    public void show(String cardName);
    public void displayBackButton(boolean status);
    public String getBackButtonValue();
    public void setBackButtonValue(String backButtonValue);
    //public void displayHomeButton(boolean status);
    //public void formulaItemDisplayPanel();
}
