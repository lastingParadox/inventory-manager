/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Zander Preston
 */

package gui;

import data.Item;
import data.ItemList;
import helper.FileHandler;
import helper.Validator;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryManagementApplicationController {

    @FXML private TitledPane fileTitledPane;
    @FXML private VBox homeView;
    @FXML private TableView<Item> itemTable;
    @FXML private TitledPane itemTitledPane;
    @FXML private VBox itemsView;
    @FXML private TableColumn<Item, String> nameColumn;
    @FXML private TextField nameField;
    @FXML private Label nameLengthCounter;
    @FXML private TextField searchField;
    @FXML private TableColumn<Item, Boolean> selectedColumn;
    @FXML private TableColumn<Item, String> serialColumn;
    @FXML private TextField serialField;
    @FXML private Accordion toolAccordion;
    @FXML private TableColumn<Item, BigDecimal> valueColumn;
    @FXML private TextField valueField;

    private final CheckBox selectAllBox = new CheckBox();
    private final ItemList inventory = new ItemList();
    private final Validator validator = new Validator();
    private static final String APPICON = "gui/images/appicon.png";
    private int sortNameCheck = 0;
    private int sortValueCheck = 0;
    private int sortSerialCheck = 0;

    @FXML
    void aboutButtonClicked() throws IOException {
        //Creates a new window for the about menu and shows it to the user.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/about.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("css/about.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle("About");
        stage.setMinWidth(1000);
        stage.setMinHeight(stage.getHeight());
        stage.getIcons().add(new Image(APPICON));
        stage.show();
    }

    @FXML
    void addItemButtonClicked() {
        //Verifies item fields for any input errors and adds the item if there are no invalidations.

        //Validation
        StringBuilder error = new StringBuilder();
        if (validator.verifyName(nameField.getText()) == null) {
            error.append(String.format("An item's name is required and must be between 2 and 256 characters.%n"));
        }
        if (validator.verifySerial(serialField.getText()) == null) {
            error.append(String.format("An item's serial number is required and must be in the format \"A-XXX-XXX-XXX\"%n(A is a letter and X is a letter or a digit).%n"));
        }
        if (validator.verifyUnique(serialField.getText(), inventory.getObservableList()) == null) {
            error.append(String.format("An item's serial number must be unique.%n"));
        }
        if (validator.verifyValue(valueField.getText()) == null) {
            error.append("An item's value is required and must be a number greater than $0.00.");
        }

        //If there's an error, creates an alert popup containing all the errors and returns
        if(!error.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unable to Add Item");
            alert.setContentText(String.format("Unable to add item due to:%n%s", error));
            alert.show();
            return;
        }

        //Adds the new item, clears the item fields, and refreshes the table.
        inventory.addItem(nameField.getText(), valueField.getText(), serialField.getText());
        nameField.setText("");
        valueField.setText("");
        serialField.setText("");

        itemTable.setItems(inventory.getObservableList());
    }

    @FXML
    void clearItemButtonClicked() {
        //Creates an alert popup to verify that the user wants to clear all items.
        //If "yes" is clicked, clears all items from the item list.
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
        itemTable.setItems(inventory.getObservableList());
    }

    @FXML
    void clearSearchButtonClicked() {
        searchField.setText("");
    }

    @FXML
    void closeButtonClicked() {
        Stage stage = (Stage) itemTable.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deleteItemButtonClicked() {
        //Gets all the items that are checked and alerts the user, asking if they want to delete the items selected.
        //If yes is selected, deletes the selected items.

        //Item Acquisition
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

        //If there are items selected, shows the alert. If the user pressed "yes", deletes the items.
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, no);
        alert.showAndWait().ifPresent(type -> {
            if (type == yes)
                inventory.removeItems(removedItems);
        });
        refreshTable();
    }

    @FXML
    void editItemButtonClicked() throws IOException {
        //After this button is clicked, The highlighted item is retrieved and sent to the edit item stage.
        if (itemTable.getSelectionModel().getSelectedItem() == null)
            return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/edit.fxml"));

        startEditScene(itemTable.getSelectionModel().getSelectedItem(), loader);
    }

    @FXML
    void exportHTMLButtonClicked() {
        //Grabs the item list and starts the "exportHTML" method in FileHandler
        Stage stage = (Stage) itemTable.getScene().getWindow();
        FileHandler fileHandler = new FileHandler(inventory.getList());
        fileHandler.fileExportGUI(stage, "html");
    }

    @FXML
    void exportJSONButtonClicked() {
        //Grabs the item list and starts the "exportJSON" method in FileHandler
        Stage stage = (Stage) itemTable.getScene().getWindow();
        FileHandler fileHandler = new FileHandler(inventory.getList());
        fileHandler.fileExportGUI(stage, "json");
    }

    @FXML
    void exportTextButtonClicked() {
        //Grabs the item list and starts the "exportText" method in FileHandler
        Stage stage = (Stage) itemTable.getScene().getWindow();
        FileHandler fileHandler = new FileHandler(inventory.getList());
        fileHandler.fileExportGUI(stage, "text");
    }

    @FXML
    void homeButtonClicked() {
        //When the home button is clicked, sets the home view to be visible and the item view to not be visible.
        homeView.setVisible(true);
        itemsView.setVisible(false);
    }

    @FXML
    void importButtonClicked() {
        //Opens a FileChooser for the user to select a file and imports the file's contents through FileHandler.
        Stage stage = (Stage) itemTable.getScene().getWindow();
        FileChooser fileImport = new FileChooser();
        fileImport.setTitle("Select Inventory");
        fileImport.setInitialDirectory(FileSystemView.getFileSystemView().getDefaultDirectory());
        fileImport.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Inventory Files", "*.html", "*.json", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        //Returns if FileChooser is closed early or if the path doesn't exist.
        File path = fileImport.showOpenDialog(stage);
        if (path == null || !path.exists())
            return;

        FileHandler fileHandler = new FileHandler(path);

        List<Item> items = fileHandler.fileImport();

        //If import failed, notifies the user and returns.
        if (items.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Inventory failed to import.");
            alert.show();
            return;
        }

        //Initializes the table after import for convenience and notifies the user of a successful import.
        inventory.setList(items);
        selectAllBox.setSelected(false);
        toolAccordion.setExpandedPane(itemTitledPane);

        homeView.setVisible(false);
        itemsView.setVisible(true);
        refreshTable();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Inventory successfully imported!");
        alert.show();
    }

    @FXML
    void itemsButtonClicked() {
        //When the items button is clicked, sets the items view to be visible and the home view to not be visible
        itemsView.setVisible(true);
        homeView.setVisible(false);
    }

    @FXML
    void sortNameButtonClicked() {
        //Sorts the items by name in ascending value or descending order depending on the # of times the button's been clicked.
        if(sortNameCheck % 2 == 0)
            inventory.sortByName();
        else
            inventory.inverseSortByName();
        sortNameCheck++;
        itemTable.setItems(inventory.getObservableList());
    }

    @FXML
    void sortSerialButtonClicked() {
        //Sorts the items by serial number in ascending value or descending order depending on the # of times the button's been clicked.
        if(sortSerialCheck % 2 == 0)
            inventory.sortBySerial();
        else
            inventory.inverseSortBySerial();
        sortSerialCheck++;
        itemTable.setItems(inventory.getObservableList());
    }

    @FXML
    void sortValueButtonClicked() {
        //Sorts the items by value in ascending value or descending order depending on the # of times the button's been clicked.
        if(sortValueCheck % 2 == 0)
            inventory.sortByValue();
        else
            inventory.inverseSortByValue();
        sortValueCheck++;
        itemTable.setItems(inventory.getObservableList());
    }

    @FXML
    public void initialize() {
        //Initializes the scene after its created.
        initializeTable();
        toolAccordion.setExpandedPane(fileTitledPane);

        //Ensures that the correct menu displays upon initializing the program
        homeView.setVisible(true);
        itemsView.setVisible(false);

        nameField.textProperty().addListener(((observable, oldValue, newValue) -> nameLengthCounter.setText(String.format("Name Length: %d characters", nameField.getText().length()))));

        //Listener for itemTable to show items based on what name or serial number is input into searchField
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            FilteredList<Item> filteredList = new FilteredList<>(FXCollections.observableArrayList(inventory.getObservableList()));
            filteredList.setPredicate(inventory.createPredicate(newValue));
            SortedList<Item> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(itemTable.comparatorProperty());
            itemTable.setItems(sortedList);
            refreshTable();
        });
    }

    private void editItemRowClicked(Item rowItem) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/edit.fxml"));

        startEditScene(rowItem, loader);
    }

    private void initializeTable() {
        //Initializes the table when the scene is initialized.
        itemTable.setItems(inventory.getObservableList());

        //Creates a use for double-clicking a row.
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

        initializeSelectedColumn();
        initializeNameColumn();
        initializeValueColumn();

        serialColumn.setCellValueFactory(new PropertyValueFactory<>("serial"));
    }

    private void initializeNameColumn() {
        //Initializes the name column when the table is initialized.
        //In particular, creates a wrapped table cell for each name to ensure that long names get their full representation.
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(column ->
                new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            Text text = new Text(item);
                            text.setStyle("-fx-text-alignment:center;-fx-fill:#C6CEF4;");
                            text.wrappingWidthProperty().bind(getTableColumn().widthProperty().subtract(10));
                            setGraphic(text);
                        }
                    }
                });
    }

    private void initializeSelectedColumn() {
        //Initializes the selected column of the itemTable when its initialized.
        selectAllBox.setUserData(selectedColumn);
        selectedColumn.setGraphic(selectAllBox);
        selectedColumn.setEditable(true);

        //Sets the selectedColumn's values and adds a listener for the select all box to select/deselect all items when clicked.
        selectedColumn.setCellValueFactory(param -> param.getValue().getSelected());
        selectedColumn.setCellFactory(column -> new CheckBoxTableCell<>());
        selectAllBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            for(Item item : inventory.getObservableList()) {
                item.setSelected(newValue);
            }
        });
    }

    private void initializeValueColumn() {
        //Initializes the value column when the table is initialized.
        //Sets the format of the value column to be shown in a currency format (USD).
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        DecimalFormat currency = new DecimalFormat("$0.00");
        valueColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(currency.format(item));
                }
            }
        });
    }

    public void refreshTable() {
        itemTable.refresh();
    }

    private void startEditScene(Item item, FXMLLoader loader) throws IOException {
        Parent root = loader.load();

        Stage stage = new Stage();
        EditItemController controller = loader.getController();
        controller.setItemList(inventory);
        controller.setInventoryController(this);
        controller.setItem(item);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("css/main.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle(String.format("Edit %s", controller.getItem().getName()));
        stage.getIcons().add(new Image(APPICON));
        stage.show();
    }

}
