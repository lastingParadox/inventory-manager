/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Zander Preston
 */

package gui;

import data.Item;
import data.ItemList;
import helper.FileHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryManagementApplicationController {

    @FXML private Label dateLabel;

    @FXML private VBox homeView;

    @FXML private ListView<Item> itemList;

    @FXML private TableView<Item> itemTable;

    @FXML private VBox itemsView;

    @FXML private TableColumn<Item, String> nameColumn;

    @FXML private TextField searchField;

    @FXML private TableColumn<Item, Boolean> selectedColumn;

    @FXML private TableColumn<Item, String> serialColumn;

    @FXML private Label timeLabel;

    @FXML private Label userLabel;

    @FXML private TableColumn<Item, BigDecimal> valueColumn;

    private final CheckBox selectAllBox = new CheckBox();

    private final ItemList inventory = new ItemList();

    private final FilteredList<Item> filteredList = new FilteredList<>(FXCollections.observableArrayList(inventory.getObservableList()));

    @FXML
    void addItemButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        EditItemController controller = loader.getController();
        controller.setItemList(inventory);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Add an Item");
        stage.show();
    }

    @FXML
    void clearItemButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clear all items");
        alert.setContentText("Are you sure you want to delete all items?");
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, no);
        alert.showAndWait().ifPresent(type -> {
            if (type == yes)
                inventory.clear();
        });
    }

    @FXML
    void clearSearchButtonClicked() {
        searchField.setText("");
    }

    @FXML
    void deleteItemButtonClicked() {
        List<Item> removedItems = new ArrayList<>();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove Selected Items");

        StringBuilder content = new StringBuilder();
        content.append(String.format("Are you sure you want to remove the following item(s)?%n"));

        for(Item item : inventory.getList()) {
            if (item.getSelectedValue()) {
                removedItems.add(item);
                content.append(String.format("%s%n", item.getName()));
            }
        }
        if (String.valueOf(content).equals(String.format("Are you sure you want to remove the following item(s)?%n"))) {
            return;
        }

        alert.setContentText(String.valueOf(content));

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, no);
        alert.showAndWait().ifPresent(type -> {
            if (type == yes)
                inventory.removeItems(removedItems);
        });
    }

    @FXML
    void editItemButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        EditItemController controller = loader.getController();
        controller.setItemList(inventory);
        controller.setItem(itemTable.getSelectionModel().getSelectedItem());

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle(String.format("Edit %s", controller.getItem().getName()));
        stage.show();
    }

    @FXML
    void exportHTMLButtonClicked() {
        Stage stage = (Stage) itemTable.getScene().getWindow();
        FileHandler fileHandler = new FileHandler(inventory.getList());
        fileHandler.fileExportGUI(stage, "html");
    }

    @FXML
    void exportJSONButtonClicked() {
        Stage stage = (Stage) itemTable.getScene().getWindow();
        FileHandler fileHandler = new FileHandler(inventory.getList());
        fileHandler.fileExportGUI(stage, "json");
    }

    @FXML
    void exportTextButtonClicked() {
        Stage stage = (Stage) itemTable.getScene().getWindow();
        FileHandler fileHandler = new FileHandler(inventory.getList());
        fileHandler.fileExportGUI(stage, "text");
    }

    @FXML
    void homeButtonClicked(ActionEvent event) {
        //When the home button is clicked, sets the home view to be visible and the item view to not be visible
        //Code only exists to show off both menus in initial design
        homeView.setVisible(true);
        itemsView.setVisible(false);
    }

    @FXML
    void importButtonClicked(ActionEvent event) {
        Stage stage = (Stage) itemTable.getScene().getWindow();
        FileChooser fileImport = new FileChooser();
        fileImport.setTitle("Select Inventory");
        fileImport.setInitialDirectory(FileSystemView.getFileSystemView().getDefaultDirectory());
        fileImport.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("HTML Files", "*.html"),
                new FileChooser.ExtensionFilter("JSON Files", "*.json"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File path = fileImport.showOpenDialog(stage);
        if (path == null)
            return;

        FileHandler fileHandler = new FileHandler(path);

        List<Item> items = fileHandler.fileImport();

        if (items.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Inventory failed to import.");
            alert.show();
            return;
        }

        inventory.setList(items);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Inventory successfully imported!");
        alert.show();
    }

    @FXML
    void itemsButtonClicked(ActionEvent event) {
        //When the items button is clicked, sets the items view to be visible and the home view to not be visible
        //Also sets the selectAll graphic to exist
        //Code only exists to show off both menus in initial design
        itemsView.setVisible(true);
        homeView.setVisible(false);
        selectAllBox.setUserData(selectedColumn);
        selectedColumn.setGraphic(selectAllBox);
    }

    @FXML
    void sortNameButtonClicked(ActionEvent event) {
        inventory.sortByName();
    }

    @FXML
    void sortSerialButtonClicked(ActionEvent event) {
        inventory.sortBySerial();
    }

    @FXML
    void sortValueButtonClicked(ActionEvent event) {
        inventory.sortByValue();
    }

    @FXML
    public void initialize() {
        itemTable.setItems(inventory.getObservableList());

        itemTable.setRowFactory(table -> {
            TableRow<Item> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Item rowData = row.getItem();
                    try {
                        editItemRowClicked(rowData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        //Ensures that the correct menu displays upon initializing the program
        homeView.setVisible(true);
        itemsView.setVisible(false);

        selectedColumn.setCellValueFactory(param -> param.getValue().getSelected());
        selectedColumn.setCellFactory(column -> new CheckBoxTableCell<>());
        selectAllBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            for(Item item : inventory.getObservableList()) {
                item.setSelected(newValue);
            }
        });

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        serialColumn.setCellValueFactory(new PropertyValueFactory<>("serial"));

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(inventory.createPredicate(newValue));
        });
    }

    public void editItemRowClicked(Item rowItem) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        EditItemController controller = loader.getController();
        controller.setItemList(inventory);
        controller.setItem(rowItem);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle(String.format("Edit %s", controller.getItem().getName()));
        stage.show();
    }

}
