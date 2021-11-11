/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Zander Preston
 */

package data;

import javafx.beans.property.SimpleBooleanProperty;

import java.math.BigDecimal;

public class Item {

    //Create private SimpleBooleanProperty selected set to false
    //Create private String name
    //Create private BigDecimalCurrency value
    //Create private String serialNumber

    Item(String name, String value, String serialNumber) {
        //this name = name
        //this value is a new BigDecimalCurrency with constructor value
        //this serialNumber = serialNumber
    }

    public SimpleBooleanProperty getSelected() {
        //return selected
        return null;
    }

    public boolean getSelectedValue() {
        //return selected.getValue()
        return false;
    }

    public String getName() {
        //return name
        return null;
    }

    public BigDecimal getValue() {
        //return value.getValue()
        return null;
    }

    public String getSerial() {
        //return serial
        return null;
    }

    public void setSelected(boolean selected) {
        //this selected is set to selected
    }

    public void setName(String name) {
        //this name = name
    }

    public void setValue(String value) {
        //this value.setValue(value)
    }

    public void setSerial(String serial) {
        //this serial = serial
    }

}
