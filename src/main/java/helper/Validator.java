/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Zander Preston
 */

package helper;

import data.Item;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public String verifyName(String name) {
        if (name.length() < 2 || name.length() > 256)
            return null;
        else
            return name;
    }

    public String verifyValue(String value) {
        try {
            if (Double.parseDouble(value) < 0)
                return null;
            else
                return value;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String verifySerial(String serial) {
        Pattern pattern = Pattern.compile("[A-z]-[A-z0-9]{3}-[A-z0-9]{3}-[A-z0-9]{3}");
        Matcher matcher = pattern.matcher(serial);

        if (matcher.matches()) {
            return serial;
        }
        else {
            return null;
        }
    }

    public String verifyUnique(String serial, ObservableList<Item> inventory) {
        for (Item item : inventory) {
            if (item.getSerial().equals(serial))
                return null;
        }
        return serial;
    }

}
