/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Zander Preston
 */

package gui;

import data.Item;
import data.ItemList;
import helper.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class EditItemController {

    @FXML private Label nameCharCounter;
    @FXML private TextField nameField;
    @FXML private TextField serialField;
    @FXML private Label titleLabel;
    @FXML private TextField valueField;

    private ItemList inventory;
    private Item item;
    private final Validator validator = new Validator();
    private InventoryManagementApplicationController inventoryController = null;

    @FXML
    void deleteItemButtonClicked() {
        //Verifies that the user wants to delete the item and if so, deletes the item and closes the window.
        if (item == null)
            return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(String.format("Delete %s", item.getName()));
        alert.setContentText(String.format("Are you sure you want to delete %s?", item.getName()));

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, no);

        alert.showAndWait().ifPresent(type -> {
            if (type == yes) {
                List<Item> itemAdd = new ArrayList<>();
                itemAdd.add(item);
                inventory.removeItems(itemAdd);
                Stage stage = (Stage) titleLabel.getScene().getWindow();
                stage.close();
            }

        });
    }

    @FXML
    void editItemButtonClicked() {
        //Validates the name, value, and serial fields and then adds the item to the inventory. Closes the window afterwards.
        StringBuilder error = new StringBuilder();
        if (validator.verifyName(nameField.getText()) == null) {
            error.append(String.format("An item's name is required and must be between 2 and 256 characters.%n"));
        }
        if (validator.verifySerial(serialField.getText()) == null) {
            error.append(String.format("An item's serial number is required and must be in the format \"A-XXX-XXX-XXX\"%n(A is a letter and X is a letter or a digit).%n"));
        }
        if (validator.verifyUnique(serialField.getText(), inventory.getObservableList()) == null && !serialField.getText().equals(item.getSerial())) {
            error.append(String.format("An item's serial number must be unique.%n"));
        }
        if (validator.verifyValue(valueField.getText()) == null) {
            error.append("An item's value is required and must a number greater than $0.00.");
        }

        if(!error.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unable to Edit Item");
            alert.setContentText(String.format("Unable to edit item due to:%n%s", error));
            alert.show();
            return;
        }

        item.setName(nameField.getText());
        item.setValue(valueField.getText());
        item.setSerial(serialField.getText());

        inventoryController.getTable().setItems(inventory.getObservableList());
        inventoryController.refreshTable();

        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onNameFieldFill() {
        //If the user decides to commit (press enter) the name field, displays an error the value entered is invalid.
        if (validator.verifyName(nameField.getText()) == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Name");
            alert.setContentText("An item's name is required and must be between 2 and 256 characters.");
            alert.show();
        }
    }

    @FXML
    void onSerialFieldFill() {
        //If the user decides to commit (press enter) the value field, displays an error the value entered is invalid.
        if (validator.verifySerial(serialField.getText()) == null || validator.verifyUnique(serialField.getText(), inventory.getObservableList()) == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Serial Number");
            alert.setContentText("An item's serial number is required and must be a unique value in the format \"A-XXX-XXX-XXX\"%n(A is a letter and X is a letter or a digit).");
            alert.show();
        }
    }

    @FXML
    void onValueFieldFill() {
        //If the user decides to commit (press enter) the serial field, displays an error the value entered is invalid.
        if (validator.verifyValue(valueField.getText()) == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Value");
            alert.setContentText("An item's value is required and must a number greater than $0.00.");
            alert.show();
        }
    }

    @FXML
    public void initialize() {
        //Adds a listener to the name field to update the character counter whenever the scene is initialized.
        nameField.textProperty().addListener(((observable, oldValue, newValue) -> {
            nameCharCounter.setText(String.format("Count: %d characters", nameField.getText().length()));
            if (nameField.getText().length() > 256) {
                nameField.setText(nameField.getText().substring(0, 256));
            }
        }));

    }

    public Item getItem() {
        return item;
    }

    public void setItemList(ItemList items) {
        inventory = items;
    }

    public void setItem(Item item) {
        //Sets the text fields to the edited item and the title to "Editing item.getName()"
        this.item = item;
        titleLabel.setText(String.format("Editing %s", item.getName()));
        nameField.setText(item.getName());
        valueField.setText(item.getValue().toString());
        serialField.setText(item.getSerial());
    }

    public void setInventoryController(InventoryManagementApplicationController inventoryController) {
        //Grabs the main controller only to refresh the table upon adding or deleting an item.
        this.inventoryController = inventoryController;
    }

}