/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Zander Preston
 */

package gui;

import data.Item;
import data.ItemList;
import helper.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditItemController {

    @FXML
    private Button addItemButton;

    @FXML
    private Button editItemButton;

    @FXML
    private Label nameCharCounter;

    @FXML
    private TextField nameField;

    @FXML
    private TextField serialField;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField valueField;

    private ItemList inventory;
    private Item item;
    private Validator validator;

    @FXML
    void addItemButtonClicked(ActionEvent event) {
        //StringBuilder error is a new StringBuilder
        //If (validator.verifyName.equals(null)):
            //Append "An item's name is required and must be between 2 and 256 characters.%n" to error
        //If (validator.verifySerial.equals(null)):
            //Append "An item's serial number is required and must be in the format "A-XXX-XXX-XXX"%n(A is a letter and X is a letter or a digit).%n" to error
        //If (validator.verifyValue.equals(null)):
            //Append "An item's value is required and must a number greater than $0.00." to error

        //If error is not empty:
            //New alert is up type ERROR
            //Set title of alert to "Unable to Add Item"
            //Set context text to "Unable to add item due to:%n" + String value of error
            //Show alert
            //return

        //Add new Item with nameField.getText(), valueField.getText(), serialField.getText() to inventory
        //Get stage
        //Close stage
    }

    @FXML
    void deleteItemButtonClicked(ActionEvent event) {
        //Create new Alert "alert" as a confirmation type
        //Set the title of the alert to "Delete 'item.getName()"
        //Set the text of the alert to "Are you sure you want to delete 'item.getName()'?"

        //ButtonType yes is a new ButtonType of "yes"
        //ButtonType no is a new ButtonType of "cancel_close"
        //Get the button types and set them all in the alert

        //Show the alert and wait for a button to be pressed
            //If yes:
                //items.remove(item)

        //Get stage
        //Close stage
    }

    @FXML
    void editItemButtonClicked(ActionEvent event) {
        //StringBuilder error is a new StringBuilder
        //If (validator.verifyName.equals(null)):
            //Append "An item's name is required and must be between 2 and 256 characters.%n" to error
        //If (validator.verifySerial.equals(null)):
            //Append "An item's serial number is required and must be in the format "A-XXX-XXX-XXX"%n(A is a letter and X is a letter or a digit).%n" to error
        //If (validator.verifyValue.equals(null)):
            //Append "An item's value is required and must a number greater than $0.00." to error

        //If error is not empty:
            //New alert is up type ERROR
            //Set title of alert to "Unable to Add Item"
            //Set context text to "Unable to add item due to:%n" + String value of error
            //Show alert
            //return

        //item.setName(nameField.getText()), item.setValue(valueField.getText()), item.setSerial(serialField.getText())
        //Get stage
        //Close stage
    }

    @FXML
    void onNameFieldFill(ActionEvent event) {
        //Used if user commits before clicking "Add/Edit item"
        //If (validator.verifyName(nameField.getText()) == null):
            //Alert alert is of type ERROR
            //Set title of alert to "Invalid name"
            //Set context text to "An item's name is required and must be between 2 and 256 characters."
            //Show alert
    }

    @FXML
    void onSerialFieldFill(ActionEvent event) {
        //Used if user commits before clicking "Add/Edit item"
        //If (validator.verifySerial(serialField.getText()) == null):
            //Alert alert is of type ERROR
            //Set title of alert to "Invalid Serial Number"
            //Set context text to "An item's serial number is required and must be in the format "A-XXX-XXX-XXX"%n(A is a letter and X is a letter or a digit)."
            //Show alert
    }

    @FXML
    void onValueFieldFill(ActionEvent event) {
        //Used if user commits before clicking "Add/Edit item"
        //If (validator.verifySerial(valueField.getText()) == null):
            //Alert alert is of type ERROR
            //Set title of alert to "Invalid Value"
            //Set context text to "An item's value is required and must a number greater than $0.00."
            //Show alert
    }

    @FXML
    public void initialize() {
        //If item = null:
            //Set titleLabel text to "Adding Item"
            //addItemButton is made visible
            //editItemButton is made invisible
        //Else:
            //Set titleLabel text to "Editing 'item.getName()'"
            //editItemButton is made visible
            //addItemButton is made invisible

        //Add a listener for nameField:
            //Set nameCharCounter's text to "Count: %d characters" with the number of characters being the newField's text length
            //If the text is over 256 characters:
                //Set nameField's text to a substring from index 0 to 256
    }

    public ItemList getItemList() {
        return inventory;
    }

    public Item getItem() {
        return item;
    }

    public void setItemList(ItemList items) {
        inventory = items;
    }

    public void setItem(Item item) {
        this.item = item;
    }

}