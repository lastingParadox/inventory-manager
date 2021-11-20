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
    private int sortNameCheck = 0;
    private int sortValueCheck = 0;
    private int sortSerialCheck = 0;

    @FXML
    void addItemButtonClicked() {
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
            error.append("An item's value is required and must a number greater than $0.00.");
        }

        if(!error.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unable to Add Item");
            alert.setContentText(String.format("Unable to add item due to:%n%s", error));
            alert.show();
            return;
        }

        inventory.addItem(nameField.getText(), valueField.getText(), serialField.getText());
        refreshTable();
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
        refreshTable();
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
        refreshTable();
    }

    @FXML
    void editItemButtonClicked() throws IOException {
        if (itemTable.getSelectionModel().getSelectedItem() == null)
            return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        EditItemController controller = loader.getController();
        controller.setItemList(inventory);
        controller.setInventoryController(this);
        controller.setItem(itemTable.getSelectionModel().getSelectedItem());

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("edit.css")).toExternalForm());
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
    void homeButtonClicked() {
        //When the home button is clicked, sets the home view to be visible and the item view to not be visible
        //Code only exists to show off both menus in initial design
        homeView.setVisible(true);
        itemsView.setVisible(false);
    }

    @FXML
    void importButtonClicked() {
        Stage stage = (Stage) itemTable.getScene().getWindow();
        FileChooser fileImport = new FileChooser();
        fileImport.setTitle("Select Inventory");
        fileImport.setInitialDirectory(FileSystemView.getFileSystemView().getDefaultDirectory());
        fileImport.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Inventory Files", "*.html", "*.json", "*.txt"),
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
        if(sortNameCheck % 2 == 0)
            inventory.sortByName();
        else
            inventory.inverseSortByName();
        sortNameCheck++;
    }

    @FXML
    void sortSerialButtonClicked() {
        if(sortSerialCheck % 2 == 0)
            inventory.sortBySerial();
        else
            inventory.inverseSortBySerial();
        sortSerialCheck++;
    }

    @FXML
    void sortValueButtonClicked() {
        if(sortValueCheck % 2 == 0)
            inventory.sortByValue();
        else
            inventory.inverseSortByValue();
        sortValueCheck++;
    }

    @FXML
    public void initialize() {
        initializeTable();
        toolAccordion.setExpandedPane(fileTitledPane);

        //Ensures that the correct menu displays upon initializing the program
        homeView.setVisible(true);
        itemsView.setVisible(false);

        nameField.textProperty().addListener(((observable, oldValue, newValue) -> nameLengthCounter.setText(String.format("Name Length: %d characters", nameField.getText().length()))));

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            FilteredList<Item> filteredList = new FilteredList<>(FXCollections.observableArrayList(inventory.getObservableList()));
            filteredList.setPredicate(inventory.createPredicate(newValue));
            SortedList<Item> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(itemTable.comparatorProperty());
            itemTable.setItems(sortedList);
            refreshTable();
        });
    }

    public void initializeTable() {
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

        initializeSelectedColumn();
        initializeNameColumn();
        initializeValueColumn();

        serialColumn.setCellValueFactory(new PropertyValueFactory<>("serial"));
    }

    public void initializeSelectedColumn() {
        selectAllBox.setUserData(selectedColumn);
        selectedColumn.setGraphic(selectAllBox);
        selectedColumn.setEditable(true);

        selectedColumn.setCellValueFactory(param -> param.getValue().getSelected());
        selectedColumn.setCellFactory(column -> new CheckBoxTableCell<>());
        selectAllBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            for(Item item : inventory.getObservableList()) {
                item.setSelected(newValue);
            }
        });
    }

    public void initializeNameColumn() {
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

    public void initializeValueColumn() {
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

    public void editItemRowClicked(Item rowItem) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        EditItemController controller = loader.getController();
        controller.setItemList(inventory);
        controller.setInventoryController(this);
        controller.setItem(rowItem);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("edit.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle(String.format("Edit %s", controller.getItem().getName()));
        stage.show();
    }

}
