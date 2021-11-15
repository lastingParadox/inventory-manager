/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Zander Preston
 */

package data;

import javafx.beans.property.SimpleBooleanProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Item {

    private final SimpleBooleanProperty selected = new SimpleBooleanProperty(false);
    private String name;
    private BigDecimal value;
    private String serialNumber;

    public Item(String name, String value, String serialNumber) {
        this.name = name;
        this.value = new BigDecimal(value);
        this.value = this.value.setScale(2, RoundingMode.HALF_UP);
        this.serialNumber = serialNumber;
    }

    public SimpleBooleanProperty getSelected() {
        return selected;
    }

    public boolean getSelectedValue() {
        return selected.getValue();
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getSerial() {
        return serialNumber;
    }

    public void setSelected(boolean selected) {
        this.selected.setValue(selected);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = BigDecimal.valueOf(Long.parseLong(value));
    }

    public void setSerial(String serial) {
        this.serialNumber = serial;
    }

}
