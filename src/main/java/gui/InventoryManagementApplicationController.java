/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Zander Preston
 */

package gui;

import data.Item;
import data.ItemList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.math.BigDecimal;

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

    private ItemList inventory;
    private FilteredList<Item> filteredList;

    @FXML
    void addItemButtonClicked() throws IOException {
        /*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        EditItemController controller = loader.getController();
        controller.setItemList(inventory);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Add an Item");
        stage.show();*/
    }

    @FXML
    void clearItemButtonClicked(ActionEvent event) {
        /*
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clear all items");
        alert.setContentText("Are you sure you want to delete all items?");
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, no);
        alert.showAndWait().ifPresent(type -> {
            if (type == yes)
                inventory.clear();
        });*/
    }

    @FXML
    void clearSearchButtonClicked(ActionEvent event) {
        //searchField.setText("");
    }

    @FXML
    void deleteItemButtonClicked(ActionEvent event) {
        /*
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
        });*/
    }

    @FXML
    void editItemButtonClicked(ActionEvent event) throws IOException {
        /*
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
        stage.show();*/
    }

    @FXML
    void exportHTMLButtonClicked() {
        /*
        Stage stage = (Stage) itemTable.getScene().getWindow();
        FileChooser fileExport = new FileChooser();
        fileExport.setTitle("Export Inventory");
        fileExport.setInitialDirectory(FileSystemView.getFileSystemView().getDefaultDirectory());
        fileExport.setInitialFileName("Inventory.html");
        fileExport.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("HTML Files", "*.html"),
                new FileChooser.ExtensionFilter("JSON Files", "*.json"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File path = fileExport.showSaveDialog(stage);
        if (path == null)
            return;

        FileHandler fileHandler = new FileHandler(path, inventory.getList());
        String output = fileHandler.fileExport();

        if (output != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Inventory successfully exported!");
            alert.show();
        }*/
    }

    @FXML
    void exportJSONButtonClicked(ActionEvent event) {
        //Stage "stage" is the current stage
        //Create new FileChooser "fileExport" = new FileChooser()"
        //Set fileExport's title to "Export Inventory"
        //Set fileExport's initialDirectory to default documents directory ("FileSystemView.getFileSystemView().getDefaultDirectory().getPath()")
        //Set fileExport's initialFileName as "Inventory.json"
        //Add the following extensions to fileExport:
            //"JSON Files", "*.json"
            //"HTML Files", "*.html"
            //"Text Files", "*.txt"

        //Create new File "path" set to saveDialog's output (fileExport.showSaveDialog(stage))
        //If path is null, return

        //Create new fileHandler "fileHandler" with path, inventory
        //String output = fileHandler.fileExport()

        //If output is not null:
        //Create new alert of type information
        //Set the content text of alert to "Inventory successfully exported!"
        //Show the alert
    }

    @FXML
    void exportTextButtonClicked(ActionEvent event) {
        //Stage "stage" is the current stage
        //Create new FileChooser "fileExport" = new FileChooser()"
        //Set fileExport's title to "Export Inventory"
        //Set fileExport's initialDirectory to default documents directory ("FileSystemView.getFileSystemView().getDefaultDirectory().getPath()")
        //Set fileExport's initialFileName as "Inventory.txt"
        //Add the following extensions to fileExport:
            //"Text Files", "*.txt"
            //"HTML Files", "*.html"
            //"JSON Files", "*.json"


        //Create new File "path" set to saveDialog's output (fileExport.showSaveDialog(stage))
        //If path is null, return

        //Create new fileHandler "fileHandler" with path, inventory
        //String output = fileHandler.fileExport()

        //If output is not null:
            //Create new alert of type information
            //Set the content text of alert to "Inventory successfully exported!"
            //Show the alert
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
        //Stage "stage" is the current stage
        //Create new FileChooser "fileImport" = new FileChooser()"
        //Set fileImport's title to "Select Inventory"
        //Set fileImport's initialDirectory to default documents directory ("FileSystemView.getFileSystemView().getDefaultDirectory().getPath()")
        //Add the following extensions to fileImport:
            //"HTML Files", "*.html"
            //"JSON Files", "*.json"
            //"Text Files", "*.txt"
            //"All Files", "*.*"

        //Create new File "path" set to openDialog's output (fileImport.showOpenDialog(stage))
        //If path is null, return

        //Create new fileHandler "fileHandler" with path
        //List of items "list" = fileHandler.fileImport()

        //If list is empty:
            //Create new alert of type error
            //Set the content text of alert to "Inventory failed to import."
            //Show the alert
            //return

        //inventory.setList(list)

        //Create new alert of type information
        //Set the content text of alert to "Inventory successfully imported!"
        //Show the alert
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
        //inventory.sortByName()
    }

    @FXML
    void sortSerialButtonClicked(ActionEvent event) {
        //inventory.sortBySerial()
    }

    @FXML
    void sortValueButtonClicked(ActionEvent event) {
        //inventory.sortByValue()
    }

    @FXML
    public void initialize() {
        //Set itemTable items to inventory.getList()
        //Set filteredList items to inventory.getList()

        //Set the row factory of itemTable
            //Create new TableRow of Items "row"
            //row.setOnMouseClicked(event ->
                //If the row is clicked twice and the row is not empty:
                    //Item rowItem = row.getItem()
                    //editItemRowClicked(rowItem)
            //return row

        //Ensures that the correct menu displays upon initializing the program
        homeView.setVisible(true);
        itemsView.setVisible(false);

        //Set the cell value factory of the selected column to item.getSelected()
        //Set the cell factory of selected column to CheckBoxTableCells
        //Add a listener for the selectAllBox when checked:
            //All items in the list have their selected value set to true or false when checked or unchecked

        //Set the cell value factory of the name column to a new PropertyValueFactory "name"
        //Set the cell value factory of the value column to a new PropertyValueFactory "value"
        //Set the cell value factory of the serial column to a new PropertyValueFactory "serial"

        //Add a listener for the searchBox when typed in:
            //Set the predicate of filteredList to inventory.createPredicate(newValue)
            //Set itemTable items to filteredList
    }

    public void editItemRowClicked(Item rowItem) {
        //Create new FXMLLoader loader set to "edit.fxml"
        //Create new Parent root = loader.load()

        //Stage stage = new Stage()

        //Create new EditItemController controller = loader.getController()
        //controller.setItemList(inventory)
        //controller.setItem(rowItem)

        //Scene scene = new Scene(root)
        //Set the stylesheet of the scene to "styles.css"
        //Set the title to "Edit an Item"
        //Show stage
    }

}
