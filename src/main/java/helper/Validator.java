/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Zander Preston
 */

package helper;

public class Validator {

    public String verifyName(String name) {
        //If name is null:
            //return null
        //Else if name.length() is less than 2 or greater than 256:
            //return null
        //return name
        return null;
    }

    public String verifyValue(String value) {
        //Try to parse value as a double
            //If double value is less than zero:
                //return null
            //Else:
                //return value
        //If unable to, return null
        return null;
    }

    public String verifySerial(String serial) {
        //Create new Pattern "pattern" set to compile "[a-zA-z]-[a-zA-z0-9]{3}-[a-zA-z0-9]{3}-[a-zA-z0-9]{3}"
        //Create new Matcher "matcher" set to match serial
        //If matcher.matches() is true:
            //return serial
        //Else:
            //return null
        return null;
    }

}
